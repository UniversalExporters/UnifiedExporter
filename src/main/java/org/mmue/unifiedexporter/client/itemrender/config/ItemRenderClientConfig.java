package org.mmue.unifiedexporter.client.itemrender.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import org.mmue.unifiedexporter.api.IConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemRenderClientConfig implements IConfig {
    public final Configuration cfg;
    public static final int DEFAULT_MAIN_BLOCK_SIZE = 128;
    public static final int DEFAULT_GRID_BLOCK_SIZE = 32;
    public static final int DEFAULT_MAIN_ENTITY_SIZE = 512;
    public static final int DEFAULT_GRID_ENTITY_SIZE = 128;
    public static final int DEFAULT_PLAYER_SIZE = 1024;
    public int mainBlockSize = DEFAULT_MAIN_BLOCK_SIZE;
    public int gridBlockSize = DEFAULT_GRID_BLOCK_SIZE;
    public int mainEntitySize = DEFAULT_MAIN_ENTITY_SIZE;
    public int gridEntitySize = DEFAULT_GRID_ENTITY_SIZE;
    public int playerSize = DEFAULT_PLAYER_SIZE;
    public float renderScale = 1.0F;
    public boolean exportVanilla = true;
    public List<String> blacklist = new ArrayList<>();

    public ItemRenderClientConfig(File cfgDir) {
        this.cfg = new Configuration(cfgDir.toPath().resolve("unifiedexporter_itemexport.cfg").toFile());
        cfg.load();
        sync();
    }

    @Override
    public void sync() {
        mainBlockSize = cfg.get(Configuration.CATEGORY_GENERAL, "RenderBlockMain", DEFAULT_MAIN_BLOCK_SIZE, I18n.format("itemrender.cfg.mainblock")).getInt();
        gridBlockSize = cfg.get(Configuration.CATEGORY_GENERAL, "RenderBlockGrid", DEFAULT_GRID_BLOCK_SIZE, I18n.format("itemrender.cfg.gridblock")).getInt();
        mainEntitySize = cfg.get(Configuration.CATEGORY_GENERAL, "RenderEntityMain", DEFAULT_MAIN_ENTITY_SIZE, I18n.format("itemrender.cfg.mainentity")).getInt();
        gridEntitySize = cfg.get(Configuration.CATEGORY_GENERAL, "RenderEntityGrid", DEFAULT_GRID_ENTITY_SIZE, I18n.format("itemrender.cfg.gridentity")).getInt();
        playerSize = cfg.get(Configuration.CATEGORY_GENERAL, "RenderPlayer", DEFAULT_PLAYER_SIZE, I18n.format("itemrender.cfg.player")).getInt();
        exportVanilla = cfg.get(Configuration.CATEGORY_GENERAL, "ExportVanilla", true, I18n.format("itemrender.cfg.vanilla")).getBoolean();
        blacklist = Arrays.asList(cfg.get(Configuration.CATEGORY_GENERAL, "BlackList", new String[]{}, I18n.format("itemrender.cfg.blacklist")).getStringList());
        if (cfg.hasChanged())
            cfg.save();
    }
}
