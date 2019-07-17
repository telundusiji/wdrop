package site.teamo.wdrop.core;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.bean.Plugin;

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

    private void load() {
        List<Plugin> pluginList = new ArrayList<>();
        for (Plugin plugin : pluginList) {
            if (contextMap.containsKey(plugin.getContextPath())) {
                contextMap.get(plugin.getContextPath()).installPlugin(plugin);
            } else {
                contextMap.put(plugin.getContextPath(), new WDropContext(plugin.getContextPath()));
            }
        }
    }

    private void cleanAndLoad() {
        for (Map.Entry<String, WDropContext> entry : contextMap.entrySet()) {
            entry.getValue().destroy();
        }
        contextMap.clear();
        load();
    }

    private void update(Plugin plugin) {
        if (!contextMap.containsKey(plugin.getContextPath())) {

        }

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
}
