package site.teamo.wdrop.plugin.test;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.common.util.app.WDropPluginApp;

import java.util.Date;
import java.util.Map;

public class TestApp implements WDropPluginApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestApp.class);
    @Override
    public JSONObject webMain(Map<String, String> queryMap, JSONObject para) {
        LOGGER.info(JSONObject.toJSONString(queryMap));
        LOGGER.info(para.toJSONString());
        para.put("msg","请求成功"+new Date());
        return para;
    }
}
