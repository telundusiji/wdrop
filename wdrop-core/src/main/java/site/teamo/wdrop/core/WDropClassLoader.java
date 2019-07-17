package site.teamo.wdrop.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.wdrop.exception.WDropErrorCode;
import site.teamo.wdrop.exception.WDropException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class WDropClassLoader extends URLClassLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(WDropClassLoader.class);
    public WDropClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public WDropClassLoader(URL[] urls) {
        super(urls);
    }

    public WDropClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public void close(){
        try {
            Field nativeLibraries = ClassLoader.class.getDeclaredField("nativeLibraries");
            nativeLibraries.setAccessible(true);
            Vector vector = (Vector) nativeLibraries.get(this);
            for (Object o:vector){
                Method finalize = o.getClass().getDeclaredMethod("finalize");
                finalize.setAccessible(true);
                finalize.invoke(o);
                super.close();
            }
        } catch (Exception e) {
            throw new WDropException(WDropErrorCode.CLASS_LOADER_ERROR,"class loader close error",e);
        }

    }

    public static WDropClassLoader newWDropClassLoader(String libPath,ClassLoader parent) throws MalformedURLException {
        if(StringUtils.isEmpty(libPath)){
            throw new WDropException(WDropErrorCode.CLASS_LOADER_ERROR,"libPath is null");
        }
        LOGGER.info("create WDropClassLoader:{}",libPath);
        File[] files = new File(libPath).listFiles((dir, name)->name.endsWith(".jar"));
        if(files==null){
            return new WDropClassLoader(new URL[]{},parent);
        }
        List<URL> urls = new ArrayList<>();
        for(File f:files){
            urls.add(f.toURI().toURL());
        }
        URL[] a = new URL[urls.size()];
        urls.toArray(a);
        return new WDropClassLoader(a,parent);
    }
}
