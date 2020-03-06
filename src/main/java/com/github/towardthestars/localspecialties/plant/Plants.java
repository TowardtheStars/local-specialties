package com.github.towardthestars.localspecialties.plant;

import com.github.towardthestars.localspecialties.BlockLoader;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class Plants
{
    public static final BiMap<Item, PlantBlockBase> PLANTING_MAP = HashBiMap.create();
    public static final Map<PlantBlockBase, Block> WITHERING_MAP = new HashMap<>();


    public static void registerAll()
    {
        PLANTING_MAP.put(Items.WHEAT_SEEDS, BlockLoader.WHEAT);
        WITHERING_MAP.put(BlockLoader.WHEAT, Blocks.GRASS);
    }
}
