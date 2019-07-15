package site.teamo.wdrop.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.bean.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class WDropPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropPlugin.class);

    public void initPlugin(String pluginPath){
        File[] plugins = new File(pluginPath).listFiles();
        for(File plugin : plugins){
            File[] pluginInfoFile = plugin.listFiles((dir,name)->name.endsWith(".json"));
            if(pluginInfoFile==null||pluginInfoFile.length!=1){
                continue;
            }
            try {
                String pluginJson = FileUtils.readFileToString(pluginInfoFile[0], Charset.forName("UTF-8"));
                Plugin pluginBean = JSON.parseObject(pluginJson,Plugin.class);
                pluginBean.setLibPath(plugin.getPath());
            } catch (IOException e) {
                LOGGER.error("read plugin info error,{}",pluginInfoFile[0].getPath());
                continue;
            }
        }
    }
}
