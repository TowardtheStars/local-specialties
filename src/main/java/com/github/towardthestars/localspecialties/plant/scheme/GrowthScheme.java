package com.github.towardthestars.localspecialties.plant.scheme;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;

import java.util.Random;

/**
 * Plants grow according to this scheme
 * This determines how fast plants can grow
 * Plant should grow at normal speed under most circumstances
 * Slower in unsuitable env, and Faster in perfect env
 */
public class GrowthScheme implements IStatisticsScheme<Integer>
{
    /**
     * probability[i] is the probability for returning (i+1) as result (for i = 0, 1)
     *
     */
    private float[] probability = new float[2];
    private float exp, var;

    private GrowthScheme()
    {
        this.probability[0] = 0;
        this.probability[1] = 0;
        this.updateStat();
    }

    private GrowthScheme(float p1, float p2)
    {
        this.probability[0] = p1;
        this.probability[1] = p2;
        this.updateStat();
    }

    public static GrowthScheme fromProb(float p1, float p2)
    {
        return new GrowthScheme(p1, p2);
    }

    public static GrowthScheme fromExpVar(float exp, float var)
    {
        return (GrowthScheme) new GrowthScheme().setExpVar(exp, var);
    }

    private void solve()
    {
        probability[0] = exp * (-exp + 2.0f) - var;
        probability[1] = exp * (.5f * exp - .5f) + .5f * var;
    }

    @Override
    public Integer roll(Random random)
    {
        float rand = random.nextFloat();
        if (rand <= probability[1])
        {
            return 2;
        }else {
            rand -= probability[1];
            if (rand <= probability[0]){
                return 1;
            }
        }
        return 0;
    }

    public IStatisticsScheme setExpVar(float expectation, float variance)
    {
        exp = expectation;
        var = variance;
        solve();
        return this;
    }

    private void updateStat(){
        this.exp = probability[0] + 2 * probability[1];
        this.var = exp * exp + exp * (2 * probability[0] + 4 * probability[1]) + 4 * probability[1] + probability[0];
    }

    public GrowthScheme setProbability(float probability1, float probability2)
    {
        this.probability[0] = probability1;
        this.probability[1] = probability2;
        this.updateStat();
        return this;
    }

    @Override
    public IStatisticsScheme setExpectation(float expectation)
    {
        return this.setExpVar(expectation, var);
    }

    @Override
    public IStatisticsScheme setVariance(float variance)
    {
        return this.setExpVar(exp, variance);
    }

    @Override
    public float getExpectation()
    {
        return exp;
    }

    @Override
    public float getVariance()
    {
        return var;
    }

    public GrowthScheme copy(){
        return new GrowthScheme(probability[0], probability[1]);
    }
}
