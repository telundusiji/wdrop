package site.teamo.wdrop.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.teamo.wdrop.bean.Plugin;

@RestController
@RequestMapping("/plugin")
public class PluginController {

    @PostMapping(value = "/upload",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public JSONObject upload(@RequestBody Plugin plugin){
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }
}
