package com.github.towardthestars.localspecialties.plant.attribute.scheme;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

/**
 * Plants will wither according to this scheme
 * Plants will wither in extreme env,
 * And should have little chance under any suitable, even perfect env
 * This should be able to switched on/off in config files
 */
public class ViabilityScheme implements IStatisticsScheme<Boolean>
{
    private float chance;

    protected ViabilityScheme(){}

    /**
     * Chance for true
     * @param p chance for true
     * @return An IStatisticsScheme<Boolean> with chance of p to get true
     */
    public static IStatisticsScheme<Boolean> fromP(float p)
    {
        return new ViabilityScheme().withExpectation(p);
    }

    @Override
    public IStatisticsScheme<Boolean> withExpectation(float expectation)
    {
        if (expectation > 1 || expectation < 0)
        {
            throw new ValueException("Expectation for WitheringScheme should be between 0 and 1");
        }
        this.chance = expectation;
        return this;
    }

    @Override
    public IStatisticsScheme<Boolean> withVariance(float variance)
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

    @Override
    public IStatisticsScheme<Boolean> copy()
    {
        return new ViabilityScheme().withExpectation(this.chance);
    }

    @Override
    public float clampExp(float exp)
    {
        return MathHelper.clamp(exp, 0, 1);
    }

    @Override
    public float clampVar(float var)
    {
        return MathHelper.clamp(var, 0, .25f);
    }
}
