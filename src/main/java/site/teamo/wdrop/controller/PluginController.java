package site.teamo.wdrop.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.teamo.wdrop.bean.Plugin;
import site.teamo.wdrop.dto.PluginUploadRequest;
import site.teamo.wdrop.tool.PropertiesReader;
import site.teamo.wdrop.util.WDropConstant;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/plugin")
public class PluginController {

    @PostMapping(value = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject upload(@Valid PluginUploadRequest pluginUploadRequest){
        try {
            Plugin plugin = pluginUploadRequest.toPluginBean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}
