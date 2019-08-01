package site.teamo.wdrop.core;

import site.teamo.wdrop.bean.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WDropBootstrap {
    /**
     * 核心启动引导
     * @param args
     */
    public static void main(String[] args){
        WDropContainer wDropContainer = WDropContainer.getInstance();

        wDropContainer.setWDropPluginDataSource(new WDropPluginDataSource() {
            @Override
            public List<Plugin> getAllPlugin() {
                return new ArrayList<>();
            }
        });
        wDropContainer.load();
    }
}
