package com.github.towardthestars.localspecialties;


import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockLoader
{

//    public static final Block FARMLAND = new BlockFarmland(Block.Settings.copy(Blocks.FARMLAND));
//    public static final PlantBlockBase WHEAT = new CropBlock(Block.Settings.copy(Blocks.WHEAT).nonOpaque());

    private static void register(ResourceLocation identifier, Block block)
    {
        Registry.register(Registry.BLOCK, identifier, block);
    }

    static void registerAll()
    {
//        register(LocalSpecialties.getIdentifier("farmland"), FARMLAND);
//        register(LocalSpecialties.getIdentifier("wheat"), WHEAT);
    }

}
