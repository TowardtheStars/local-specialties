package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.plant.CropBlock;
import com.github.towardthestars.localspecialties.plant.PlantBlockBase;
import com.github.towardthestars.localspecialties.environment.soil.BlockFarmland;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockLoader
{

    public static final Block FARMLAND = new BlockFarmland(Block.Settings.copy(Blocks.FARMLAND));
    public static final PlantBlockBase WHEAT = new CropBlock(Block.Settings.copy(Blocks.WHEAT).nonOpaque());

    public static void register(Identifier identifier, Block block)
    {
        Registry.register(Registry.BLOCK, identifier, block);
    }

    public static void registerAll()
    {
        register(LocalSpecialties.getIdentifier("farmland"), FARMLAND);
        register(LocalSpecialties.getIdentifier("wheat"), WHEAT);
    }

    public static void setRenderLayer(RenderLayer layer, Block... blocks)
    {
        for (Block block : blocks)
        {
            BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
        }

    }

    public static void clientInit()
    {
        setRenderLayer(RenderLayer.getCutout(), WHEAT);
    }
}
