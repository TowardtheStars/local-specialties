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
    public static final BiMap<Item, Block> PLANTING_MAP = HashBiMap.create();
    public static final Map<Block, Block> WITHERING_MAP = new HashMap<>();


    public static void registerItems() {
        PLANTING_MAP.put(Items.WHEAT_SEEDS, Blocks.WHEAT);
    }

    public static void registerBlocks() {
        WITHERING_MAP.put(Blocks.WHEAT, Blocks.GRASS);
    }
}
