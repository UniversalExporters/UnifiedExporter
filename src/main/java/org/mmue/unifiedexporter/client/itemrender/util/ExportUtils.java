/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package org.mmue.unifiedexporter.client.itemrender.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.mmue.unifiedexporter.UnifiedExporter;
import org.mmue.unifiedexporter.client.itemrender.ItemRender;
import org.mmue.unifiedexporter.client.itemrender.data.EntityData;
import org.mmue.unifiedexporter.client.itemrender.data.ItemData;
import org.mmue.unifiedexporter.config.ClientConfig;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Meow J on 8/17/2015.
 *
 * @author Meow J
 */
public class ExportUtils {
    public static ExportUtils INSTANCE;

    private final FBOHelper fboSmall = new FBOHelper(32);
    ;
    private final FBOHelper fboLarge = new FBOHelper(128);
    private final FBOHelper fboEntity = new FBOHelper(200);
    private final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
    private final Map<String, List<ItemData>> itemDataMap = new HashMap<>();
    private final Map<String, List<EntityData>> entityDataMap = new HashMap<>();

    public String getType(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ItemBlock) ? "Block" : "Item";
    }

    public String getSmallIcon(ItemStack itemStack) {
        return Renderer.getItemBase64(itemStack, fboSmall, itemRenderer);
    }

    public String getLargeIcon(ItemStack itemStack) {
        return Renderer.getItemBase64(itemStack, fboLarge, itemRenderer);
    }

    public String getEntityIcon(EntityEntry entity) {
        return Renderer.getEntityBase64(entity, fboEntity);
    }

    private String getItemOwner(ItemStack itemStack) {
        ResourceLocation registryName = itemStack.getItem().getRegistryName();
        return registryName == null ? "unnamed" : registryName.getNamespace();
    }

    private String getEntityOwner(EntityEntry entity) {
        ResourceLocation registryName = entity.getRegistryName();
        return registryName == null ? "unnamed" : registryName.getNamespace();
    }

    public void exportMods(@Nullable String modId) {
        ItemRender.running = true;
        final long startTime = System.currentTimeMillis();
        final boolean specific = modId != null;
        Minecraft minecraft = FMLClientHandler.instance().getClient();
        itemDataMap.clear();
        entityDataMap.clear();

        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().disableHtmlEscaping().create();
        for (ItemStack itemStack : ItemList.items) {
            if (itemStack == null) continue;
            final String ownerModId = getItemOwner(itemStack);
            if (specific && !ownerModId.equals(modId)) continue;
            if (!specific && ownerModId.equals("minecraft") && !ClientConfig.INSTANCE.itemRender.exportVanilla)
                continue;
            try {
                getOrCreateItemDataListForModId(ownerModId).add(new ItemData(itemStack));
            } catch (Exception e) {
                UnifiedExporter.LOGGER.error("Error occurred while exporting item " + itemStack.getItem().getRegistryName().toString(), e);
            }
        }
        for (EntityEntry entity : ForgeRegistries.ENTITIES) {
            if (entity == null) continue;
            final String ownerModId = getEntityOwner(entity);
            if (specific && !ownerModId.equals(modId)) continue;
            if (!specific && ownerModId.equals("minecraft") && !ClientConfig.INSTANCE.itemRender.exportVanilla)
                continue;
            try {
                getOrCreateEntityDataListForModId(ownerModId).add(new EntityData(entity));
            } catch (Exception e) {
                UnifiedExporter.LOGGER.error("Error occurred while exporting entity " + entity.getRegistryName().toString(), e);
            }
        }

        File export;
        File export1;
        for (final Map.Entry<String, List<ItemData>> entry : itemDataMap.entrySet()) {
            final String modid = entry.getKey();
            export = new File(minecraft.gameDir, String.format("export/" + modid + "_item.json", modid.replaceAll("[^A-Za-z0-9()\\[\\]]", "")));
            if (!export.getParentFile().exists()) export.getParentFile().mkdirs();
            try {
                if (!export.exists()) export.createNewFile();
                PrintWriter pw = new PrintWriter(export, "UTF-8");
                for (ItemData data : entry.getValue()) {
                    pw.println(gson.toJson(data));
                }
                pw.close();
            } catch (IOException e) {
                UnifiedExporter.LOGGER.error("IOException caught while saving item exports.", e);
            }
        }
        for (final Map.Entry<String, List<EntityData>> entry : entityDataMap.entrySet()) {
            final String modid = entry.getKey();
            export1 = new File(minecraft.gameDir, String.format("export/" + modid + "_entity.json", modid.replaceAll("[^A-Za-z0-9()\\[\\]]", "")));
            if (!export1.getParentFile().exists()) export1.getParentFile().mkdirs();
            try {
                if (!export1.exists()) export1.createNewFile();
                PrintWriter pw1 = new PrintWriter(export1, "UTF-8");
                for (EntityData data : entry.getValue()) {
                    pw1.println(gson.toJson(data));
                }
                pw1.close();
            } catch (IOException e) {
                UnifiedExporter.LOGGER.error("IOException caught while saving entity exports.", e);
            }
        }
        ItemRender.running = false;
        if (minecraft.player != null)
            minecraft.player.sendStatusMessage(
                    new TextComponentTranslation(
                            "msg.unifiedexporter.itemrender.finished",
                            String.valueOf((System.currentTimeMillis() - startTime) / 1000.0)
                    ), false);
    }

    private List<ItemData> getOrCreateItemDataListForModId(String modId) {
        if (!itemDataMap.containsKey(modId)) itemDataMap.put(modId, new ArrayList<>());
        return itemDataMap.get(modId);
    }

    private List<EntityData> getOrCreateEntityDataListForModId(String modId) {
        if (!entityDataMap.containsKey(modId)) entityDataMap.put(modId, new ArrayList<>());
        return entityDataMap.get(modId);
    }
}
