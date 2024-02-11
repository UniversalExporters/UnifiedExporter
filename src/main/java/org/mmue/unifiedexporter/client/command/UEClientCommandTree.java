package org.mmue.unifiedexporter.client.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import org.mmue.unifiedexporter.client.itemrender.ItemRender;
import org.mmue.unifiedexporter.client.itemrender.command.ItemRenderCommand;

public class UEClientCommandTree extends CommandTreeBase {

    public UEClientCommandTree() {
        if (ItemRender.gl32_enabled) addSubcommand(new ItemRenderCommand());
    }


    @Override
    public String getName() {
        return "unifiedexporterc";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "cmd.unifiedexporter.help";
    }
}
