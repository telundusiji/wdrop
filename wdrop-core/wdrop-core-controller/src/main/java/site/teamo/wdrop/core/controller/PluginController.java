package site.teamo.wdrop.core.controller;

/**
 * GJmMUorrNbHfisxQKPW1
 * https://filexc.hikvision.com/filexc/www/?a=d&i=DBXumnWlmW
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.teamo.wdrop.core.base.WDropContainer;
import site.teamo.wdrop.core.bean.Plugin;
import site.teamo.wdrop.core.data.dao.PluginMapper;
import site.teamo.wdrop.core.data.dto.PluginUploadRequest;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/plugin")
public class PluginController {

    @Autowired
    private PluginMapper pluginMapper;

    @PostMapping(value = "/upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject upload(@Valid PluginUploadRequest pluginUploadRequest){
        try {
            Plugin plugin = pluginUploadRequest.toPluginBean();
            pluginMapper.saveToDb(plugin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}
