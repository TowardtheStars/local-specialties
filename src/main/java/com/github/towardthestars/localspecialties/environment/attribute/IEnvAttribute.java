package com.github.towardthestars.localspecialties.environment.attribute;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IEnvAttribute<T> extends IForgeRegistryEntry<IEnvAttribute>
{
    Class<T> getType();
    T getAttribute(IWorld world, BlockPos pos);
}
