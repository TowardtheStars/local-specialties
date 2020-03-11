package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ModelEnum<ENUM_TYPE extends Enum<ENUM_TYPE>> implements IAffinityModel<ENUM_TYPE>
{
    @Delegate
    private final Map<Enum<ENUM_TYPE>, Float> map = new HashMap<>();

    @Override
    public double getMultiplier(ENUM_TYPE envValue)
    {
        return map.getOrDefault(envValue, 1f);
    }

    @Override
    public Class getType()
    {
        return Enum.class;
    }

    @Override
    public IAffinityModel<ENUM_TYPE> withArgs(Object... args)
    {
        for (int i = 0; i < args.length - 1; i+=2)
        {
            map.put((ENUM_TYPE) args[i], (float) args[i + 1]);
        }
        return this;
    }
}
