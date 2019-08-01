package site.teamo.wdrop.core;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.common.util.app.WDropPluginApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WDropContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WDropContainer.class);

    private static WDropContainer wDropContainer = new WDropContainer();

    private Map<String, WDropContext> contextMap;

    private WDropContainer() {
        this.contextMap = new ConcurrentHashMap<>();
    }

    private WDropPluginDataSource wDropPluginDataSource;

    public void load() {
        List<Plugin> pluginList = wDropPluginDataSource.getAllPlugin();
        for (Plugin plugin : pluginList) {
            if (contextMap.containsKey(plugin.getContextPath())) {
                contextMap.get(plugin.getContextPath()).installPlugin(plugin);
            } else {
                contextMap.put(plugin.getContextPath(), new WDropContext(plugin.getContextPath()));
            }
        }
    }

    public void cleanAndLoad() {
        for (Map.Entry<String, WDropContext> entry : contextMap.entrySet()) {
            entry.getValue().destroy();
        }
        contextMap.clear();
        load();
    }

    public static WDropContainer getInstance() {
        return wDropContainer;
    }

    public static JSONObject execute(String contextPath, String url, Map<String, String> queryMap, JSONObject para) {
        WDropContext wDropContext = wDropContainer.contextMap.get(contextPath);
        WDropPluginApp wDropPluginApp = null;
        try {
            wDropPluginApp = wDropContext.getPluginApp(url);
            JSONObject result = wDropPluginApp.webMain(queryMap, para);
            return result;
        } catch (Exception e) {
            LOGGER.error("plugin execute error!", e);
        }
        return new JSONObject();
    }

    public WDropPluginDataSource getWDropPluginDataSource() {
        return wDropPluginDataSource;
    }

    public void setWDropPluginDataSource(WDropPluginDataSource wDropPluginDataSource) {
        this.wDropPluginDataSource = wDropPluginDataSource;
    }
}
