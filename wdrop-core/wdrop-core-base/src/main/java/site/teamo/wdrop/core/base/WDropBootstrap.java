package site.teamo.wdrop.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.teamo.wdrop.core.util.SpringUtil;

import javax.annotation.PostConstruct;

@Component
public class WDropBootstrap {

    @Autowired
    private WDropPluginDataSource wDropPluginDataSource;

    /**
     * 核心启动引导
     * @param args
     */
    public static void main(String[] args){

    }

    @PostConstruct
    public void init(){
        WDropContainer wDropContainer = WDropContainer.getInstance();
        wDropContainer.setWDropPluginDataSource(wDropPluginDataSource);
        wDropContainer.load();
    }
}
