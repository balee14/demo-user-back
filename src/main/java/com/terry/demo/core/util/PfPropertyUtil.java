package com.terry.demo.core.util;



import com.terry.demo.core.config.ApplicationContextConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;

@Log4j2
public class PfPropertyUtil {

    public static String getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

    public static String getProperty(String propertyName, String defaultValue) {

        ApplicationContext applicationContext = ApplicationContextConfig.getApplicationContext();
        if(applicationContext.getEnvironment().getProperty(propertyName) == null) {
            log.warn(propertyName + " properties was not loaded.");
            return "";
        } else {
            defaultValue = applicationContext.getEnvironment().getProperty(propertyName).toString();
        }
        return defaultValue;
    }

}
