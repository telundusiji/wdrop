package site.teamo.wdrop.core.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.common.util.app.WDropPluginApp;
import site.teamo.wdrop.core.bean.Plugin;

import java.net.MalformedURLException;


public class WDropPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropPlugin.class);

    public enum WDropPluginStatus {
        GREEN,
        YELLOW,
        RED;
    }

    private Plugin plugin;
    private WDropPluginStatus status;
    private WDropClassLoader wDropClassLoader;
    private WDropPluginApp wDropPluginApp;

    private WDropPlugin() {
    }

    public static WDropPlugin createWDropPlugin(Plugin plugin) throws Exception {
        WDropPlugin wDropPlugin = new WDropPlugin();
        wDropPlugin.status = WDropPluginStatus.GREEN;
        wDropPlugin.wDropClassLoader = WDropClassLoader.newWDropClassLoader(plugin.getLibPath(), Thread.currentThread().getContextClassLoader());
        try {

            Class pa = Class.forName(wDropPlugin.getPlugin().getClassName(), true, wDropPlugin.wDropClassLoader);
            LOGGER.info("plugin app main class name:{}", pa.getName());
            wDropPlugin.wDropPluginApp = (WDropPluginApp) pa.newInstance();
        } catch (Throwable e) {
            LOGGER.error("create plugin app error,plugin:{}", JSON.toJSONString(plugin));
            wDropPlugin.status = WDropPluginStatus.YELLOW;
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

    public WDropClassLoader getWDropClassLoader() {
        return wDropClassLoader;
    }

    public WDropPluginApp getWDropPluginApp() {
        return wDropPluginApp;
    }

    public void destroy() {
        wDropClassLoader.close();
    }
}
