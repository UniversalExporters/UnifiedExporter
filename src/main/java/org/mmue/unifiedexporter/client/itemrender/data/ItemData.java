/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package org.mmue.unifiedexporter.client.itemrender.data;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.mmue.unifiedexporter.client.itemrender.util.ExportUtils;
import org.mmue.unifiedexporter.util.i18n.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meow J on 8/17/2015.
 *
 * @author Meow J
 */
public class ItemData {
    private final String name;
    private final String englishName;
    private String registerName;
    private int metadata;
    private String OredictList;
    private String CreativeTabName;
    private String type;
    private int maxStackSize;
    private int maxDurability;
    private final String smallIcon;
    private final String largeIcon;
    private transient ItemStack itemStack;


    public ItemData(ItemStack stack) {
        final String[] names = LocaleHelper.getNames(stack.getItem().getUnlocalizedNameInefficiently(stack) + ".name");
        name = names[0];
        englishName = names[1];
        registerName = stack.getItem().getRegistryName().toString();
        metadata = stack.getMetadata();
        List<String> list = new ArrayList<>();
        if (!stack.isEmpty()) {
            for (int i : OreDictionary.getOreIDs(stack)) {
                String ore = OreDictionary.getOreName(i);
                list.add(ore);
            }
            OredictList = list.toString();
        }
        final CreativeTabs tab = stack.getItem().getCreativeTab();
        CreativeTabName = tab == null ? "" : LocaleHelper.getNames(tab.getTranslationKey())[0];
        type = ExportUtils.INSTANCE.getType(stack);
        maxStackSize = stack.getMaxStackSize();
        maxDurability = stack.getMaxDamage() + 1;
        smallIcon = ExportUtils.INSTANCE.getSmallIcon(stack);
        largeIcon = ExportUtils.INSTANCE.getLargeIcon(stack);

        this.itemStack = stack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
