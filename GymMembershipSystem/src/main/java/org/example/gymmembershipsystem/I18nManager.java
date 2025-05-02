package org.example.gymmembershipsystem;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nManager {
    private static ResourceBundle bundle;

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    public static String get(String key) {
        if (bundle == null) {
            setLocale(Locale.ENGLISH); // default fallback
        }
        return bundle.getString(key);
    }
}
