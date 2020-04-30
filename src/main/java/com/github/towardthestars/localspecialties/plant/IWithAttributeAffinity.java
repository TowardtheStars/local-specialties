package com.github.towardthestars.localspecialties.plant;

import com.github.towardthestars.localspecialties.plant.attribute.AttributeAffinityManager;

public interface IWithAttributeAffinity
{
    AttributeAffinityManager getManager();

    /**
     *
     * @param manager manager to bind
     * @return this
     */
    IWithAttributeAffinity setManager(AttributeAffinityManager manager);
}
