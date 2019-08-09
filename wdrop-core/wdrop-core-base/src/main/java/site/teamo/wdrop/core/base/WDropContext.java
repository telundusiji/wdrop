package site.teamo.wdrop.core.base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.common.util.app.WDropPluginApp;
import site.teamo.wdrop.core.bean.Plugin;
import site.teamo.wdrop.core.exception.WDropException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WDropContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropContext.class);
    private String contextPath;
    private Map<String, WDropPlugin> wDropPluginMap;

    private Object lock = new Object();

    public WDropContext(String contextPath) {
        this.contextPath = contextPath;
        wDropPluginMap = new ConcurrentHashMap<>();
    }

    public void installPlugin(Plugin plugin) throws Exception {
        synchronized (lock) {
            checkPlugin(plugin);
            /**
             * 校验唯一键 exist
             */
            if (wDropPluginMap.containsKey(plugin.getUrl())) {
                throw new RuntimeException("plugin has been registered,install failed");
            }
            /**
             * 存入 pluginInfoMap
             */
            String url = plugin.getUrl();
            wDropPluginMap.put(url, WDropPlugin.createWDropPlugin(plugin));
        }
    }

    public WDropPluginApp getPluginApp(String url) throws Exception {
        WDropPlugin wDropPlugin = wDropPluginMap.get(url);
        if(wDropPlugin.getWDropPluginApp()!=null){
            return wDropPlugin.getWDropPluginApp();
        }
        WDropClassLoader pluginClassLoader = wDropPlugin.getWDropClassLoader();
        Class pa = Class.forName(wDropPlugin.getPlugin().getClassName(), true, pluginClassLoader);
        LOGGER.info("plugin app main class name:{}", pa.getName());
        return (WDropPluginApp) pa.newInstance();
    }

    public void uninstallPlugin(Plugin plugin) {
        synchronized (lock) {
            checkPlugin(plugin);
            removePlugin(plugin);
        }
    }

    public void destroy() {
        synchronized (lock) {
            for (Map.Entry<String, WDropPlugin> entry : wDropPluginMap.entrySet()) {
                try {
                    entry.getValue().destroy();
                } catch (WDropException e) {
                    LOGGER.error("destroy wDropPlugin error,plugin url:{}",entry.getKey(),e);
                }
            }
            wDropPluginMap.clear();
        }
    }

    private void checkPlugin(Plugin plugin) {
        /**
         * 校验参数null
         */
        if (plugin == null) {
            throw new RuntimeException("plugin is null,install failed");
        }
        /**
         * 校验唯一键 blank
         */
        String url = plugin.getUrl();
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("plugin url is blank,install failed");
        }
    }

    private void removePlugin(Plugin plugin) {
        String url = plugin.getUrl();
        if (wDropPluginMap.containsKey(url)) {
            WDropPlugin wDropPlugin = wDropPluginMap.remove(url);
            wDropPlugin.destroy();
        }
    }
}
