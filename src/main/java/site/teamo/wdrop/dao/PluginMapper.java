package site.teamo.wdrop.dao;

import org.springframework.stereotype.Component;
import site.teamo.wdrop.bean.Plugin;

@Component
public interface PluginMapper {

    int saveToDb(Plugin plugin);
}
