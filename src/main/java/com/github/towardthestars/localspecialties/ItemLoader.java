package com.github.towardthestars.localspecialties;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemLoader
{
//    public static final BlockItem FARMLAND = new BlockItem(BlockLoader.FARMLAND, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static void register(ResourceLocation identifier, Item item)
    {
        Registry.register(Registry.ITEM, identifier, item);
    }

    public static void registerAll()
    {
//        register(LocalSpecialties.getIdentifier("farmland"), FARMLAND);
    }
}
