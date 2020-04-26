package com.github.towardthestars.localspecialties.environment.attribute;


import net.minecraft.util.IStringSerializable;

public abstract class EnumEnvAttribute<T extends Enum<T> & IStringSerializable> extends AbstractEnvAttribute<T>
{

    public EnumEnvAttribute(String name, Class<T> type)
    {
        super(name, type);
    }
}
