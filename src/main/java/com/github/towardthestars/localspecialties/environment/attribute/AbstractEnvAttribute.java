package com.github.towardthestars.localspecialties.environment.attribute;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class AbstractEnvAttribute<T> implements EnvAttribute<T>
{
    String name;
    Class<T> type;

    AbstractEnvAttribute(String name, Class<T> clazz){
        this.name = name;
        this.type = clazz;
    }

    public String getName()
    {
        return this.name;
    }

    public Class<T> getType()
    {
        return this.type;
    }

    public abstract T getAttribute(IWorld world, BlockPos pos);

}
