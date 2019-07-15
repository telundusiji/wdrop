package site.teamo.wdrop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.tool.FileUtil;
import site.teamo.wdrop.tool.IDFactory;
import site.teamo.wdrop.tool.PropertiesReader;
import site.teamo.wdrop.util.WDropConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginUploadRequest {
    @NotBlank(message = "Attribute 'pluginName' cannot be empty")
    private String pluginName;
    @NotBlank(message = "Attribute 'className' cannot be empty")
    private String className;
    @NotBlank(message = "Attribute 'url' cannot be empty")
    @Pattern(regexp = "(/[0-9a-zA-Z]*)*", message = "Attribute 'url' is not a valid URL")
    private String url;
    @NotBlank(message = "Attribute 'contextPath' cannot be empty")
    @Pattern(regexp = "^/[0-9a-zA-Z]*", message = "Attribute 'contextPath' is not a valid URL")
    private String contextPath;
    @NotEmpty(message = "Attribute 'pluginJars' cannot be empty")

    private List<MultipartFile> pluginJars;

    public Plugin toPluginBean(String libRootPath) throws IOException {
        if (StringUtils.isBlank(libRootPath)) {
            throw new RuntimeException("jar lib path is blank");
        }
        String pluginLibPath = libRootPath + File.separatorChar + pluginName;
        File libDir = new File(pluginLibPath);
        if (libDir.exists()) {
            FileUtil.deleteDir(libDir);
        }
        libDir.mkdirs();
        if (pluginJars != null) {
            for (MultipartFile multipartFile : pluginJars) {
                if (!multipartFile.getOriginalFilename().endsWith(".jar")) {
                    throw new RuntimeException("this file " + multipartFile.getOriginalFilename() + " is not a jar");
                }
                File jarFile = new File(pluginLibPath + File.separatorChar + multipartFile.getOriginalFilename());
                multipartFile.transferTo(jarFile);
            }
        }
        return Plugin.builder()
                .pluginId(IDFactory.createId())
                .pluginName(pluginName)
                .className(className)
                .url(contextPath + url)
                .contextPath(contextPath)
                .libPath(pluginLibPath)
                .build();
    }

    public Plugin toPluginBean() throws IOException {
        String osName = System.getProperty("os.name");
        if (StringUtils.startsWithIgnoreCase(osName, "win")) {
            return toPluginBean(PropertiesReader.readValueWithDefault(WDropConstant.APPLICATION_CONFIG, "plugin.jar.root.path.windows", "C:\\opt\\plugin"));
        } else {
            return toPluginBean(PropertiesReader.readValueWithDefault(WDropConstant.APPLICATION_CONFIG, "plugin.jar.root.path.linux", "/opt/plugin"));
        }
    }

    public static void main(String[] args) {
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            System.out.println(roots[i]);
        }
    }
}
