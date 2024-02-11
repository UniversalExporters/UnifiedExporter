package org.mmue.unifiedexporter.event;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.mmue.unifiedexporter.UnifiedExporter;
import org.mmue.unifiedexporter.config.ClientConfig;

@Mod.EventBusSubscriber
public class ForgeEventHandler {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (!event.getModID().equals(UnifiedExporter.MODID)) return;
        if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) ClientConfig.syncConfig();
    }
}
