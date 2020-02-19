package com.github.towardthestars.localspecialties.soil;

import net.minecraft.block.Block;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class BlockFarmland extends Block
{
    public BlockFarmland(Settings settings)
    {
        super(settings);
    }

    public static final IntProperty MOISTURE = Properties.MOISTURE;
    public static final IntProperty FERTILITY = IntProperty.of("fertility", 0, 7);
}
