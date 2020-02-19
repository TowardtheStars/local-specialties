package com.github.towardthestars.localspecialties.plant.scheme;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.Random;

/**
 * Plants will wither according to this scheme
 * Plants will wither in extreme env,
 * And should have little chance under any suitable, even perfect env
 * This should be able to switched on/off in config files
 */
public class WitheringScheme implements IStatisticsScheme<Boolean>
{
    private float chance;
    @Override
    public IStatisticsScheme setExpectation(float expectation)
    {
        if (expectation > 1 || expectation < 0)
        {
            throw new ValueException("Expectation for WitheringScheme should be between 0 and 1");
        }
        this.chance = expectation;
        return this;
    }

    @Override
    public IStatisticsScheme setVariance(float variance)
    {
        if (variance > 0.25 || variance < 0)
        {
            throw new ValueException("Variance for WitheringScheme should be between 0 and 0.25");
        }
        this.chance = (float)(1 - Math.sqrt(1 - 4 * variance) / 2);
        return null;
    }

    @Override
    public float getExpectation()
    {
        return this.chance;
    }

    @Override
    public float getVariance()
    {
        return this.chance * (1 - this.chance);
    }

    @Override
    public Boolean roll(Random random)
    {
        return random.nextFloat() < this.chance;
    }
}
