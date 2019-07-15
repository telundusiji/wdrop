package site.teamo.wdrop.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plugin {
    private String pluginId;
    private String pluginName;
    private String className;
    private String url;
    private String libPath;
    private String contextPath;
    private List<MultipartFile> pluginJars;
}
