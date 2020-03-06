package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.plant.attribute.scheme.GrowthScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.HarvestScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.ViabilityScheme;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public class PlantAttributes
{
    static final BiMap<String, PlantAttribute> ATTRIBUTE_MAP = HashBiMap.create(3);
    public static final PlantAttribute<Integer> GROWTH = new PlantAttribute<>(GrowthScheme.fromProb(1, 0));
    public static final PlantAttribute<Float> HARVEST = new PlantAttribute<>(HarvestScheme.fromExpVar(1, 0.1f));
    public static final PlantAttribute<Boolean> VIABILITY = new PlantAttribute<>(ViabilityScheme.fromP(1f));
//    public static final PlantAttribute<EnumDiseaseType> DISEASE = new PlantAttribute<>("disease", null);

    public static PlantAttribute getPlantAttribute(String name)
    {
        return ATTRIBUTE_MAP.get(name);
    }

    public static void registerAttribute(String id, PlantAttribute attribute)
    {
        ATTRIBUTE_MAP.put(id, attribute);
    }

    public static void registerAll()
    {
        registerAttribute("growth", GROWTH);
        registerAttribute("harvest", HARVEST);
        registerAttribute("viability", VIABILITY);
    }


}
