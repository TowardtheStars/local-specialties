package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.plant.CropBlock;
import com.github.towardthestars.localspecialties.plant.PlantBlockBase;
import com.github.towardthestars.localspecialties.environment.soil.BlockFarmland;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockLoader
{

    public static final Block FARMLAND = new BlockFarmland(Block.Settings.copy(Blocks.FARMLAND));
    public static final PlantBlockBase WHEAT = new CropBlock(Block.Settings.copy(Blocks.WHEAT).nonOpaque());

    private static void register(ResourceLocation identifier, Block block)
    {
        Registry.register(Registry.BLOCK, identifier, block);
    }

    static void registerAll()
    {
        register(LocalSpecialties.getIdentifier("farmland"), FARMLAND);
        register(LocalSpecialties.getIdentifier("wheat"), WHEAT);
    }

    private static void setRenderLayer(RenderLayer layer, Block... blocks)
    {
        for (Block block : blocks)
        {
            BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
        }

    }

    static void clientInit()
    {
        setRenderLayer(RenderLayer.getCutout(), WHEAT);
    }
}
