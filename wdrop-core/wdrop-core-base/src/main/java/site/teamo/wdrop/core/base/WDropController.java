package site.teamo.wdrop.core.base;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

@RestController
public class WDropController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WDropController.class);

    @RequestMapping(value = "/WDrop/{contextPath}/**", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject wDrop(@PathVariable String contextPath, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("\n--REQUEST:{}\n--URI:{}\n--QUERY:{}", request.getRequestURL().toString(), request.getRequestURI(), request.getQueryString());
        contextPath = "/" + contextPath;
        String url = request.getRequestURI().replace("/WDrop" , "");
        JSONObject para = resolveRequest(request);
        Map<String, String> queryMap = resolveQueryString(request.getQueryString());
        LOGGER.info("\n--CONTEXT_PATH:{}\n--URL:{}\n--QUERY_MAP:{}\n--PARAMETER:{}", contextPath, url, JSONObject.toJSONString(queryMap), para.toJSONString());
        return WDropContainer.execute(contextPath, url, queryMap, para);
    }

    private JSONObject resolveRequest(HttpServletRequest request) {
        InputStream in = null;
        try {
            in = request.getInputStream();
            String para = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            JSONObject jsonObject = JSONObject.parseObject(para);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    private Map<String, String> resolveQueryString(String queryString) {
        Map<String, String> queryMap = new HashMap<>();
        if (StringUtils.isBlank(queryString)) {
            return queryMap;
        }
        List<String> keyValue = new ArrayList<>();
        if (StringUtils.contains(queryString, "&")) {
            String[] kvs = queryString.split("&");
            keyValue.addAll(Arrays.asList(kvs));
        } else {
            keyValue.add(queryString);
        }
        for (String kv : keyValue) {
            if (!kv.contains("=")) {
                continue;
            }
            String[] kav = kv.split("=");
            if (kav.length != 2) {
                continue;
            }
            queryMap.put(kav[0], kav[1]);
        }
        return queryMap;
    }
}
