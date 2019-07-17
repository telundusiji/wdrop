package site.teamo.wdrop.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.dao.provider.PluginProvider;

@Component
@Mapper
public interface PluginMapper {

    @InsertProvider(type= PluginProvider.class,method = "insertPlugin")
    int saveToDb(Plugin plugin);
}
