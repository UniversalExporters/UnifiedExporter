/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */
package org.mmue.unifiedexporter.client.itemrender;


import org.lwjgl.opengl.GLContext;
import org.mmue.unifiedexporter.client.itemrender.util.ExportUtils;
import org.mmue.unifiedexporter.client.itemrender.util.ItemList;
import org.mmue.unifiedexporter.util.i18n.LocaleHelper;

public class ItemRender {
    public static boolean gl32_enabled = false;
    public static boolean running = false;

    public static void preInit() {
        gl32_enabled = GLContext.getCapabilities().OpenGL32;
    }

    public static void init() {
        if (gl32_enabled) ExportUtils.INSTANCE = new ExportUtils();
    }

    public static void postInit() {
        ItemList.updateList();
        LocaleHelper.getLocale("en_us");
        LocaleHelper.getLocale("zh_cn");
    }


}
