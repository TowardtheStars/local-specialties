package com.github.towardthestars.localspecialties.environment.attribute;

public abstract class BooleanEnvAttribute extends AbstractEnvAttribute<Boolean>
{
    public BooleanEnvAttribute(String name)
    {
        super(name, Boolean.class);
    }
}
