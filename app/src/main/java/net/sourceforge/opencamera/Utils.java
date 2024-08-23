package net.sourceforge.opencamera;

import android.os.Build;

import java.lang.reflect.Method;
import java.util.Locale;

public class Utils {
    public static String getProperty(String key, String defaultValue) {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
            return (String) get.invoke(null, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
