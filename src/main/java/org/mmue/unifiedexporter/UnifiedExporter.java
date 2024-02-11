package org.mmue.unifiedexporter;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mmue.unifiedexporter.proxies.IProxy;

@Mod(modid = UnifiedExporter.MODID, useMetadata = true)
public class UnifiedExporter {
    public static final String MODID = "unifiedexporter";
    public static final String MODNAME = "UnifiedExporter";
    public static final Logger LOGGER = LogManager.getLogger(MODNAME);

    @SidedProxy(
            clientSide = "org.mmue.unifiedexporter.proxies.ClientProxy",
            serverSide = "org.mmue.unifiedexporter.proxies.ServerProxy"
    )
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
