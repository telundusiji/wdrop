package site.teamo.wdrop.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.bean.Plugin;

public class PluginProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginProvider.class);

    public String insertPlugin(Plugin plugin){
        String sql = new SQL(){
            {
                INSERT_INTO("wdrop_plugin");
                if(StringUtils.isNotBlank(plugin.getPluginId())){
                    VALUES("plugin_id",plugin.getPluginId());
                }else {
                    throw new RuntimeException("'plugin_id' is not allowed to be empty");
                }

                if(StringUtils.isNotBlank(plugin.getPluginName())){
                    VALUES("plugin_name",plugin.getPluginName());
                }else {
                    throw new RuntimeException("'plugin_name' is not allowed to be empty");
                }

                if(StringUtils.isNotBlank(plugin.getContextPath())){
                    VALUES("context_path",plugin.getContextPath());
                }else {
                    throw new RuntimeException("'context_path' is not allowed to be empty");
                }

                if (StringUtils.isNotBlank(plugin.getUrl())&&plugin.getUrl().contains(plugin.getContextPath())){
                    VALUES("url",plugin.getUrl());
                }else {
                    LOGGER.error("url:{};contextPath:{}",plugin.getUrl(),plugin.getContextPath());
                    throw new RuntimeException("'url' is not allowed to be empty and 'url' must contain 'context_path'");
                }

                if(StringUtils.isNotBlank(plugin.getLibPath())){
                    VALUES("lib_path",plugin.getLibPath());
                }else {
                    throw new RuntimeException("'lib_path' is not allowed to be empty");
                }

                if(StringUtils.isNotBlank(plugin.getClassName())){
                    VALUES("class_name",plugin.getClassName());
                }else {
                    throw new RuntimeException("'class_name' is not allowed to be empty");
                }
            }
        }.toString();
        LOGGER.info("\nMETHOD:{}\nSQL:{}","insertPlugin",sql);
        return sql;
    }
}
