package com.github.towardthestars.localspecialties.environment.attribute;

import net.minecraft.util.StringIdentifiable;

public abstract class EnumEnvAttribute<T extends Enum<T> & StringIdentifiable> extends AbstractEnvAttribute<T>
{

    EnumEnvAttribute(String name, Class<T> type)
    {
        super(name, type);
    }
}
