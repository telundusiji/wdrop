package site.teamo.wdrop.core.data.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import site.teamo.wdrop.core.bean.Plugin;
import site.teamo.wdrop.core.data.dao.provider.PluginProvider;

import java.util.List;

@Component
@Mapper
public interface PluginMapper {

    @InsertProvider(type= PluginProvider.class,method = "insertPlugin")
    int saveToDb(Plugin plugin);

    @Select("select * from wdrop_plugin")
    @Results({
            @Result(property = "pluginId",column = "plugin_id"),
            @Result(property = "pluginName",column = "plugin_name"),
            @Result(property = "className",column = "class_name"),
            @Result(property = "url",column = "url"),
            @Result(property = "libPath",column = "lib_path"),
            @Result(property = "contextPath",column = "context_path")
    })
    List<Plugin> getAll();
}
