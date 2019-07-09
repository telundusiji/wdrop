package site.teamo.wdrop.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.exception.WDropException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WDropContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropContext.class);
    private String contextPath;
    private Map<String, Plugin> pluginMap;
    private Map<String, WDropClassLoader> pluginClassLoaderMap;
    private Object lock = new Object();

    public WDropContext(String contextPath) {
        this.contextPath = contextPath;
        pluginMap = new ConcurrentHashMap<>();
        pluginClassLoaderMap = new ConcurrentHashMap<>();
    }

    public void installPlugin(Plugin plugin) {
        synchronized (lock) {
            checkPlugin(plugin);
            /**
             * 校验唯一键 exist
             */
            if (existPlugin(plugin)) {
                throw new RuntimeException("plugin has been registered,install failed");
            }
            /**
             * 存入 pluginInfoMap
             */
            String url = plugin.getUrl();
            pluginMap.put(url, plugin);
            try {
                /**
                 * 存入 pluginClassLoaderMap
                 */
                pluginClassLoaderMap.put(url, WDropClassLoader.newWDropClassLoader(plugin.getLibPath(), Thread.currentThread().getContextClassLoader()));
            } catch (MalformedURLException e) {
                //异常回滚
                removePlugin(plugin);
                throw new RuntimeException("load plugin jar error,install failed");
            }
        }
    }

    public void uninstallPlugin(Plugin plugin) {
        synchronized (lock) {
            checkPlugin(plugin);
            removePlugin(plugin);
        }
    }

    public boolean isExist(Plugin plugin) {
        synchronized (lock) {
            checkPlugin(plugin);
            return existPlugin(plugin);
        }

    }

    public void destroy(){
        synchronized (lock){
            pluginMap.clear();
            for(Map.Entry<String,WDropClassLoader> entry : pluginClassLoaderMap.entrySet()){
                try {
                    entry.getValue().close();
                } catch (WDropException e) {
                    LOGGER.error("close WDropClassLoader exception",e);
                }
            }
            pluginClassLoaderMap.clear();
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
        if (pluginMap.containsKey(url)) {
            pluginMap.remove(url);
        }
        if (pluginClassLoaderMap.containsKey(url)) {
            try {
                pluginClassLoaderMap.remove(url).close();
            } catch (WDropException e) {
                LOGGER.error("uninstall plugin close WDropClassLoader error", e);
            }
        }
    }

    private boolean existPlugin(Plugin plugin) {
        String url = plugin.getUrl();
        boolean flagPluginMapContainsKey = pluginMap.containsKey(url);
        boolean flagPluginClassLoaderMapContainsKey = pluginClassLoaderMap.containsKey(url);
        if (flagPluginClassLoaderMapContainsKey != flagPluginMapContainsKey) {
            removePlugin(plugin);
        }
        return flagPluginMapContainsKey && flagPluginClassLoaderMapContainsKey;
    }
}
