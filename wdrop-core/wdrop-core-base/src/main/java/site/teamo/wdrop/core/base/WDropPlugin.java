package site.teamo.wdrop.core.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.core.bean.Plugin;


public class WDropPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropPlugin.class);

    public enum WDropPluginStatus{
        GREEN,
        YELLOW,
        RED;
    }

    private Plugin plugin;
    private WDropPluginStatus status;
    private WDropClassLoader wDropClassLoader;

    private WDropPlugin(){}

    public static WDropPlugin createWDropPlugin(Plugin plugin){
        WDropPlugin wDropPlugin = new WDropPlugin();
        wDropPlugin.status = WDropPluginStatus.GREEN;
        try {
            wDropPlugin.wDropClassLoader = WDropClassLoader.newWDropClassLoader(plugin.getLibPath(), Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            LOGGER.error("create WDropClassLoader error,plugin:{}",JSON.toJSONString(plugin));
            wDropPlugin.status=WDropPluginStatus.YELLOW;
        }
        wDropPlugin.plugin = plugin;
        return wDropPlugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public WDropPluginStatus getStatus() {
        return status;
    }

    public WDropClassLoader getwDropClassLoader() {
        return wDropClassLoader;
    }

    public void destroy(){
        wDropClassLoader.close();
    }
}
