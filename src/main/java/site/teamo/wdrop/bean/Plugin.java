package site.teamo.wdrop.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
