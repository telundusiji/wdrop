package site.teamo.wdrop.core.base;

import org.springframework.stereotype.Component;
import site.teamo.wdrop.core.util.SpringUtil;

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
