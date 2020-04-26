package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.Registries;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.GrowthScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.HarvestScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.ViabilityScheme;
import net.minecraft.util.registry.Registry;


public class PlantAttributes
{

    public static final PlantAttribute<Integer> GROWTH = new PlantAttribute<>(GrowthScheme.fromProb(1, 0));
    public static final PlantAttribute<Float> HARVEST = new PlantAttribute<>(HarvestScheme.fromExpVar(1, 0.1f));
    public static final PlantAttribute<Boolean> VIABILITY = new PlantAttribute<>(ViabilityScheme.fromP(1f));
//    public static final PlantAttribute<EnumDiseaseType> DISEASE = new PlantAttribute<>("disease", null);


    private static void registerAttribute(String id, PlantAttribute attribute)
    {
        Registry.register(Registries.PLANT_ATTRIBUTE, LocalSpecialties.getIdentifier(id), attribute);
    }

    public static void registerAll()
    {
        registerAttribute("growth", GROWTH);
        registerAttribute("harvest", HARVEST);
        registerAttribute("viability", VIABILITY);
    }


}
