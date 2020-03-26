package com.github.towardthestars.localspecialties.plant.attribute.merge_model;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.Registries;
import net.minecraft.util.registry.Registry;

public class MergeModels
{
    public static void registerAll()
    {
        Registry.register(Registries.MERGE_MODEL, LocalSpecialties.getIdentifier("mul"), AffinityMergeMultiply.class);
        Registry.register(Registries.MERGE_MODEL, LocalSpecialties.getIdentifier("add"), AffinityMergeAdd.class);
    }
}
