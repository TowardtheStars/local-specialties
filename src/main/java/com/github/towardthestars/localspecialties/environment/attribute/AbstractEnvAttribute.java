package com.github.towardthestars.localspecialties.environment.attribute;

import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class AbstractEnvAttribute<T> implements EnvAttribute<T>
{
    @Getter
    private String name;

    @Getter
    private Class<T> type;

    AbstractEnvAttribute(String name, Class<T> clazz){
        this.name = name;
        this.type = clazz;
    }

    public abstract T getAttribute(IWorld world, BlockPos pos);

}
