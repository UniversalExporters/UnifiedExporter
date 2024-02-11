package org.mmue.unifiedexporter.proxies;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.mmue.unifiedexporter.client.command.UEClientCommandTree;
import org.mmue.unifiedexporter.client.itemrender.ItemRender;
import org.mmue.unifiedexporter.config.ClientConfig;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ClientConfig.init(event.getModConfigurationDirectory());
        ItemRender.preInit();
        ClientCommandHandler.instance.registerCommand(new UEClientCommandTree());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ItemRender.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ItemRender.postInit();
    }
}
