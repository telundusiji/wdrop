package site.teamo.wdrop.common.tool;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.UUID;

public class IDFactory {
    private static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static String createId(String prefix){
        return prefix+"-"+createId();
    }

    public static String createId(){
        return fastDateFormat.format(System.currentTimeMillis())+"-"+ UUID.randomUUID().toString().substring(24);
    }
}
