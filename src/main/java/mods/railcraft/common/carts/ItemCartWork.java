/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.carts;

import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.plugins.forge.LootPlugin;
import net.minecraft.init.Items;

/**
 * Created by CovertJaguar on 8/30/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ItemCartWork extends ItemCart {
    public ItemCartWork(IRailcraftCartContainer cart) {
        super(cart);
    }

    @Override
    public void defineRecipes() {
        CraftingPlugin.addRecipe(getStack(),
                "B",
                "M",
                'B', "workbench",
                'M', Items.MINECART);
    }

    @Override
    public void initializeDefinintion() {
        LootPlugin.addLoot(getCartType(), 1, 1, LootPlugin.Type.RAILWAY);
    }
}