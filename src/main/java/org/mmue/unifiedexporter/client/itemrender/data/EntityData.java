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

import com.google.gson.annotations.SerializedName;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.mmue.unifiedexporter.client.itemrender.util.ExportUtils;
import org.mmue.unifiedexporter.util.i18n.LocaleHelper;

/**
 * Created by Meow J on 8/17/2015.
 *
 * @author Meow J
 */
public class EntityData {
    private final String name;
    private final String englishName;
    private final String mod;
    private final String registerName;
    @SerializedName("Icon")
    private final String icon;
    private transient EntityEntry entity;

    public EntityData(EntityEntry entity) {
        final String[] names = LocaleHelper.getNames(String.format("entity.%s.name", entity.getName()));
        name = names[0];
        englishName = names[1];
        mod = entity.getRegistryName().getNamespace();
        registerName = entity.getRegistryName().toString();
        icon = ExportUtils.INSTANCE.getEntityIcon(entity);
        this.entity = entity;
    }

    public EntityEntry getEntity() {
        return entity;
    }
}
