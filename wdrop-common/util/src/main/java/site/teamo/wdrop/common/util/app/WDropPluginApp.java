package site.teamo.wdrop.common.util.app;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface WDropPluginApp {
    JSONObject webMain(Map<String, String> queryMap, JSONObject para);
}
