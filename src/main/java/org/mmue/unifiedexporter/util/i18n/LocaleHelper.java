package org.mmue.unifiedexporter.util.i18n;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Locale;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LocaleHelper {
    public static final Map<String, Locale> langMap = new HashMap<>();

    public static Locale getLocale(String lang) {
        if (!langMap.containsKey(lang)) {
            final Locale locale = new Locale();
            locale.loadLocaleDataFiles(Minecraft.getMinecraft().getResourceManager(), Collections.singletonList(lang));
            langMap.put(lang, locale);
            return locale;
        }
        return langMap.get(lang);
    }

    public static String[] getNames(String key) {
        final String[] names = new String[2];
        names[0] = "";
        names[1] = "";
        final Locale cn = getLocale("zh_cn");
        final Locale en = getLocale("en_us");
        boolean hasCn = false;
        final Object[] dummy = new Object[0];
        if (cn.hasKey(key)) {
            names[0] = cn.formatMessage(key, dummy);
            hasCn = true;
        }
        if (en.hasKey(key)) names[hasCn ? 1 : 0] = en.formatMessage(key, dummy);
        return names;
    }
}
