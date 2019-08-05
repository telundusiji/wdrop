package site.teamo.wdrop.common.tool;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    public static Map<String, String> readToMap(String fileName) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("file name is blank");
        }
        if (!StringUtils.endsWith(fileName, ".properties")) {
            throw new RuntimeException("file name is not end with properties");
        }
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                throw new RuntimeException("read file input stream is null,make sure the file is exists");
            }
            properties.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return new HashMap<String, String>((Map) properties);
    }

    public static String readValueWithDefault(String fileName,String key,String defaultValue){
        try {
            Map<String,String> properties = readToMap(fileName);
            String value = properties.get(key);
            if(StringUtils.isBlank(value)){
                return defaultValue;
            }
            return value;
        }catch (Throwable e){
            LOGGER.warn("read file {} key {} error,default value is {}",fileName,key,defaultValue);
            return defaultValue;
        }
    }
}
