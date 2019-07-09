package site.teamo.wdrop.core;

import site.teamo.wdrop.bean.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WDropContainer {
    private Map<String,WDropContext> contextMap;

    public WDropContainer() {
        this.contextMap = new ConcurrentHashMap<>();
    }

    private void load(){
        List<Plugin> pluginList = new ArrayList<>();
        for(Plugin plugin:pluginList){
            if(contextMap.containsKey(plugin.getContextPath())){
                contextMap.get(plugin.getContextPath()).installPlugin(plugin);
            }else {
                contextMap.put(plugin.getContextPath(),new WDropContext(plugin.getContextPath()));
            }
        }
    }

    private void cleanAndLoad(){
        for (Map.Entry<String,WDropContext> entry:contextMap.entrySet()){
            entry.getValue().destroy();
        }
        contextMap.clear();
        load();
    }

    private void update(Plugin plugin){
        if(!contextMap.containsKey(plugin.getContextPath())){

        }

    }
}
