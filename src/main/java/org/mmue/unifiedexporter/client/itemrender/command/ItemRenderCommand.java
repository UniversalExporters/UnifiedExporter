package org.mmue.unifiedexporter.client.itemrender.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.mmue.unifiedexporter.client.itemrender.ItemRender;
import org.mmue.unifiedexporter.client.itemrender.util.ExportUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ItemRenderCommand extends CommandBase {
    @Override
    public String getName() {
        return "itemrender";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/itemrender || /itemrender <modid>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (!ItemRender.running) {
            sender.sendMessage(new TextComponentTranslation("msg.unifiedexporter.itemrender.start"));
            ExportUtils.INSTANCE.exportMods(args.length > 0 ? args[0] : null);
        } else {
            sender.sendMessage(new TextComponentTranslation("msg.unifiedexporter.itemrender.running"));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length != 1) return Collections.emptyList();
        return Loader.instance().getModList().stream()
                .map(ModContainer::getModId)
                .collect(Collectors.toList());
    }
}
