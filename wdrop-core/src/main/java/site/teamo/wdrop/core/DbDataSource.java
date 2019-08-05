package site.teamo.wdrop.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.dao.PluginMapper;

import java.util.List;

@Component
public class DbDataSource implements WDropPluginDataSource {
    @Autowired
    private PluginMapper pluginMapper;
    @Override
    public List<Plugin> getAllPlugin() {
        return pluginMapper.getAll();
    }
}
