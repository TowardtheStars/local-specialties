package com.github.towardthestars.localspecialties.environment;

import com.github.towardthestars.localspecialties.util.interpolation.periodic.CubicSplinePeriodicInterpolation;
import com.github.towardthestars.localspecialties.util.interpolation.IInterpolation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Seasons
{
    public static final List<Season> SEASONS = new ArrayList<>();
    private static IInterpolation temperatureInterpolation;
    private static IInterpolation rainfallInterpolation;

    @Getter
    private static long period;

    static void generatePhaseFunc()
    {
        temperatureInterpolation = new CubicSplinePeriodicInterpolation();
        period = 0;
        Double[] xs = new Double[SEASONS.size() + 1];
        Double[] temperatures = new Double[SEASONS.size() + 1];
        Double[] rainfalls = new Double[SEASONS.size() + 1];
        int i = 0;
        for (Season season : SEASONS)
        {
            period += season.length;
            temperatures[i] = (double)season.temperature;
            rainfalls[i] = (double) season.rainfall;
            xs[i] = (double) season.length;
            i++;
        }
        xs[SEASONS.size()] = xs[0];
        temperatures[SEASONS.size()] = temperatures[0];
        rainfalls[SEASONS.size()] = rainfalls[0];

        for (i = 1; i < xs.length; i++)
        {
            xs[i] = xs[i] + xs[i - 1];
        }
        double buffer = xs[0] *.5;
        for (i = 0; i < xs.length; i++)
        {
            xs[i] = xs[i] - SEASONS.get(i).length * .5 - buffer;
        }
        temperatureInterpolation.load(xs, temperatures);
        rainfallInterpolation.load(xs, rainfalls);
    }

    static double phaseToTemp(float phase)
    {
        return 0;
    }

    public static float getTemperature(long time)
    {
        double inDay = time / 24000.0;
        return temperatureInterpolation.apply(inDay).floatValue();
    }

    public static float getRainfall(long time)
    {
        double inDay = time / 24000.0;
        return rainfallInterpolation.apply(inDay).floatValue();
    }

}
