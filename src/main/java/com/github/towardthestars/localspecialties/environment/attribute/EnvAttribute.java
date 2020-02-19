package com.github.towardthestars.localspecialties.environment.attribute;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public interface EnvAttribute<T>
{
    String getName();
    Class<T> getType();
    T getAttribute(IWorld world, BlockPos pos);
}
