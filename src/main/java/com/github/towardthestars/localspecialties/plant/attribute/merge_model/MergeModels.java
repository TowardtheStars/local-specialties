package com.github.towardthestars.localspecialties.plant.attribute.merge_model;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class MergeModels
{
    public static final DefaultedRegistry<IMergeModel> MERGE_MODEL = new DefaultedRegistry<>(LocalSpecialties.getIdentifier("mul").toString());

    public static final IMergeModel ADD = new AffinityMergeAdd();
    public static final IMergeModel MUL = new AffinityMergeMultiply();

    public static void registerAll()
    {
        Registry.register(MERGE_MODEL, LocalSpecialties.getIdentifier("mul"), MUL);
        Registry.register(MERGE_MODEL, LocalSpecialties.getIdentifier("add"), ADD);
    }
}
