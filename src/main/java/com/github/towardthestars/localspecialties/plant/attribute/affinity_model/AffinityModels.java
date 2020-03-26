package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.Registries;
import lombok.NoArgsConstructor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@NoArgsConstructor
public class AffinityModels
{


    public static void registerAll() {
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("default0"), ModelDefault0.class);
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("default1"), ModelDefault1.class);
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("5points"), ModelNumber5Point.class);
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("3points"), ModelNumber3Point.class);
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("quadratic"), ModelNumberQuadratic.class);
        Registry.register(Registries.AFFINITY_MODEL, LocalSpecialties.getIdentifier("boolean"), ModelBoolean.class);
    }

    public static IAffinityModel create(ResourceLocation id)
    {
        try
        {
            return Objects.requireNonNull(Registries.AFFINITY_MODEL.getOrDefault(id)).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return new ModelDefault0();
    }

    public static IAffinityModel createWithArgs(ResourceLocation id, Object... args)
    {
        return create(id).withArgs(args);
    }

}
