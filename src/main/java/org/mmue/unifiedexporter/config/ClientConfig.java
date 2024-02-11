package org.mmue.unifiedexporter.config;

import org.mmue.unifiedexporter.api.IConfig;
import org.mmue.unifiedexporter.client.itemrender.config.ItemRenderClientConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientConfig {
    public static ClientConfig INSTANCE;
    private static final List<IConfig> cfgs = new ArrayList<>();
    public final ItemRenderClientConfig itemRender;

    private ClientConfig(File cfgDir) {
        this.itemRender = appendConfig(new ItemRenderClientConfig(cfgDir));
    }

    public static void init(File cfgDir) {
        if (INSTANCE == null) INSTANCE = new ClientConfig(cfgDir);
    }

    public static void syncConfig() {
        for (final IConfig s : cfgs) s.sync();
    }

    private <T extends IConfig> T appendConfig(T config) {
        cfgs.add(config);
        return config;
    }
}
