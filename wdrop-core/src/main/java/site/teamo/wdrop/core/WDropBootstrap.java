package site.teamo.wdrop.core;

import org.springframework.stereotype.Component;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.util.SpringUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class WDropBootstrap {


    /**
     * 核心启动引导
     * @param args
     */
    public static void main(String[] args){
        WDropContainer wDropContainer = WDropContainer.getInstance();
        WDropPluginDataSource wDropPluginDataSource = SpringUtil.getApplicationContext().getBean("wDropPluginDataSource",WDropPluginDataSource.class);
        wDropContainer.setWDropPluginDataSource(wDropPluginDataSource);
        wDropContainer.load();
    }
}
