package com.github.towardthestars.localspecialties.environment.attribute;

public abstract class IntEnvAttribute extends AbstractEnvAttribute<Integer>
{
    public IntEnvAttribute(String name){
        super(name, Integer.class);
    }
}
