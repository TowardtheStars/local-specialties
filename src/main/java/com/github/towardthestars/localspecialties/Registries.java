package com.github.towardthestars.localspecialties;

import com.github.towardthestars.localspecialties.environment.attribute.EnvAttribute;
import com.github.towardthestars.localspecialties.plant.attribute.PlantAttribute;
import com.github.towardthestars.localspecialties.plant.attribute.affinity_model.IAffinityModel;
import com.github.towardthestars.localspecialties.plant.attribute.merge_model.AbstractAffinityMergeModel;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.SimpleRegistry;

public class Registries
{
    public static final SimpleRegistry<EnvAttribute> ENV_ATTRIBUTE = new SimpleRegistry<>();
    public static final SimpleRegistry<PlantAttribute> PLANT_ATTRIBUTE = new SimpleRegistry<>();
    public static final DefaultedRegistry<Class<? extends IAffinityModel>> AFFINITY_MODEL
            = new DefaultedRegistry<>(LocalSpecialties.getIdentifier("default0").toString());
    public static final DefaultedRegistry<Class<? extends AbstractAffinityMergeModel>> MERGE_MODEL
            = new DefaultedRegistry<>(LocalSpecialties.getIdentifier("mul").toString());
}
