package com.github.towardthestars.localspecialties.plant.attribute.scheme;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import lombok.NonNull;

import java.util.Random;

/**
 * Crop harvesting will be altered by this scheme
 * 1.0f for normal/vanilla harvest
 * 0.0f for nothing
 * Probability, of course, should be Gaussian
 * Clearly, variance better be small
 */
public class HarvestScheme implements IStatisticsScheme<Float>
{
    @NonNull
    private float mu;
    @NonNull
    private float sigma2;

    private double sigma;

    protected HarvestScheme(){}

    public static IStatisticsScheme<Float> fromExpVar(float exp, float var)
    {
        return new HarvestScheme().withExpectation(exp).withVariance(var);
    }

    @Override
    public IStatisticsScheme<Float> withExpectation(float expectation)
    {
        mu = expectation;
        return this;
    }

    @Override
    public IStatisticsScheme<Float> withVariance(float variance)
    {
        sigma2 = variance;
        sigma = Math.sqrt(sigma2);
        return this;
    }

    @Override
    public float getExpectation()
    {
        return mu;
    }

    @Override
    public float getVariance()
    {
        return sigma2;
    }

    /**
     * Yep, Gaussian
     * @param random Random
     * @return float >= 0
     */
    @Override
    public Float roll(Random random)
    {
        double result;
        double u, v;
        do
        {
            u = random.nextDouble();
            v = random.nextDouble();
            result = Math.sqrt(-2 * Math.log(u)) * Math.sin(2 * Math.PI * v);
            result *= sigma;
            result += mu;
        }while (result >= 0);
        return (float) result;
    }

    @Override
    public IStatisticsScheme<Float> copy()
    {
        return new HarvestScheme().withExpectation(this.mu).withExpectation(this.sigma2);
    }

    @Override
    public float clampExp(float exp)
    {
        return exp;
    }

    @Override
    public float clampVar(float var)
    {
        return var > 0? var : Float.MIN_VALUE;
    }
}
