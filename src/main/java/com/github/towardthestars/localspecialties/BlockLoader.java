package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.soil.BlockFarmland;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockLoader
{

    public static final Block FARMLAND = new BlockFarmland(Block.Settings.copy(Blocks.FARMLAND));


    public static void register(Identifier identifier, Block block)
    {
        Registry.register(Registry.BLOCK, identifier, block);
    }

    public static void registerAll()
    {
        register(LocalSpecialties.getIdentifier("farmland"), FARMLAND);
    }
}
