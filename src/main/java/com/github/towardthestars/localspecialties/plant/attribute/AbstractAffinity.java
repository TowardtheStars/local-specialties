package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.environment.attribute.EnvAttribute;

public abstract class AbstractAffinity implements IAttributeAffinity
{
    EnvAttribute attribute;

    public AbstractAffinity(EnvAttribute attribute)
    {
        this.attribute = attribute;
    }

    @Override
    public EnvAttribute getAttachedAttribute()
    {
        return attribute;
    }

}
