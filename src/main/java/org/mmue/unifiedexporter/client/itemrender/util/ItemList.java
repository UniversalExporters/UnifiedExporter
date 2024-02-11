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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import org.mmue.unifiedexporter.UnifiedExporter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Code from NEI
 *
 * @author Chickenbones
 */
public class ItemList {
    /**
     * Fields are replaced atomically and contents never modified.
     */
    static volatile List<ItemStack> items = new ArrayList<>();

    private static void damageSearch(Item item, List<ItemStack> permutations) {
        HashSet<String> damageIconSet = new HashSet<>();
        for (int damage = 0; damage < 16; damage++)
            try {
                ItemStack stack = new ItemStack(item, 1, damage);
                IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
                String name = concatenatedDisplayName(stack);
                String s = name + "@" + (model == null ? 0 : model.hashCode());
                if (!damageIconSet.contains(s)) {
                    damageIconSet.add(s);
                    permutations.add(stack);
                }
            } catch (Exception e) {
                UnifiedExporter.LOGGER.error(
                        "Error occurred while executing damage search for item " +
                                item.getRegistryName().toString(),
                        e
                );
            }
    }

    private static String concatenatedDisplayName(ItemStack itemstack) {
        List<String> list = itemDisplayNameMultiline(itemstack);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String name : list) {
            if (first) {
                first = false;
            } else {
                sb.append("#");
            }
            sb.append(name);
        }
        return TextFormatting.getTextWithoutFormattingCodes(sb.toString());
    }

    private static List<String> itemDisplayNameMultiline(ItemStack itemstack) {
        List<String> nameList = null;
        try {
            nameList = itemstack.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL);
        } catch (Throwable ignored) {
        }

        if (nameList == null)
            nameList = new ArrayList<>();

        if (nameList.isEmpty())
            nameList.add("Unnamed");

        if (nameList.get(0) == null || nameList.get(0).equals(""))
            nameList.set(0, "Unnamed");

        nameList.set(0, itemstack.getRarity().color.toString() + nameList.get(0));
        for (int i = 1; i < nameList.size(); i++)
            nameList.set(i, "§7" + nameList.get(i));

        return nameList;
    }

    public static void updateList() {
        LinkedList<ItemStack> items = new LinkedList<>();
        NonNullList<ItemStack> permutations = NonNullList.create();
        ListMultimap<Item, ItemStack> itemMap = ArrayListMultimap.create();
        final long startTime = System.currentTimeMillis();
        Item.REGISTRY.spliterator().forEachRemaining(item -> {
            if (item == null) return;
            try {
                permutations.clear();
                for (CreativeTabs tab : item.getCreativeTabs())
                    item.getSubItems(tab, permutations);
                if (permutations.isEmpty())
                    damageSearch(item, permutations);
                items.addAll(permutations);
                itemMap.putAll(item, permutations);
            } catch (Exception e) {
                UnifiedExporter.LOGGER.error("Error occurred while updating item list", e);
            }
        });
        UnifiedExporter.LOGGER.info("Updated item list, time used: " + (System.currentTimeMillis() - startTime));
        ItemList.items = items;
    }
}
