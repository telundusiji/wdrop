package site.teamo.wdrop.core;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@RestController
public class WDropController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WDropController.class);

    @RequestMapping(value = "/WDrop/**",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject wDrop(HttpServletRequest request, HttpServletResponse response){
        JSONObject para = resolveRequest(request);
        LOGGER.info("\n--REQUEST:{}\n--QUERY:{}\n--PARAMETER:{}",request.getRequestURL().toString(),request.getQueryString(),para.toJSONString());
        return new JSONObject();
    }

    private JSONObject resolveRequest(HttpServletRequest request){
        InputStream in = null;
        try {
            in = request.getInputStream();
            String para = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            JSONObject jsonObject = JSONObject.parseObject(para);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }
}
