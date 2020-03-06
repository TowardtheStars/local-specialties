package com.github.towardthestars.localspecialties.util;

import java.util.Random;

public interface IStatisticsScheme<ResultType>
{
    IStatisticsScheme<ResultType> withExpectation(float expectation);
    IStatisticsScheme<ResultType> withVariance(float variance);
    float getExpectation();
    float getVariance();

    ResultType roll(Random random);

    IStatisticsScheme<ResultType> copy();
    float clampExp(float exp);
    float clampVar(float var);
}
