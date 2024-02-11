package org.mmue.unifiedexporter.client.command;

import com.google.common.collect.Lists;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import org.mmue.unifiedexporter.client.itemrender.ItemRender;
import org.mmue.unifiedexporter.client.itemrender.command.ItemRenderCommand;

import java.util.List;

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
        return "cmd.unifiedexporter.client.help";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("uexc");
    }
}
