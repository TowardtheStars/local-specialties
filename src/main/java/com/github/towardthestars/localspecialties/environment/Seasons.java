package com.github.towardthestars.localspecialties.environment;

import com.github.towardthestars.localspecialties.config.Configs;
import com.github.towardthestars.localspecialties.util.interpolation.periodic.IPeriodicInterpolation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Seasons
{
    public static final List<Season> SEASONS = new ArrayList<>();
    private static IPeriodicInterpolation temperatureInterpolation;
    private static IPeriodicInterpolation rainfallInterpolation;

    @Getter
    private static long period;

    private static void generatePhaseFunc()
    {
//        temperatureInterpolation = Configs.SEASON.TEMPERATURE_SMOOTH.create();
//        rainfallInterpolation = Configs.SEASON.RAINFALL_SMOOTH.create();
        period = 0;
        Double[] xs = new Double[SEASONS.size() + 1];
        Double[] temperatures = new Double[SEASONS.size() + 1];
        Double[] rainfalls = new Double[SEASONS.size() + 1];
        int i = 0;

        // 从配置项里面加载特征点
        for (Season season : SEASONS)
        {
            period += season.length;
            temperatures[i] = (double)season.temperature;
            rainfalls[i] = (double) season.rainfall;
            xs[i] = (double) season.length;
            i++;
        }

        // 季节是周期性的, 将最后一个季节特征点设置为与第一个季节相同
        xs[SEASONS.size()] = xs[0];
        temperatures[SEASONS.size()] = temperatures[0];
        rainfalls[SEASONS.size()] = rainfalls[0];

        // 对季节长度进行累加, 得到季节特征点的时间坐标
        for (i = 1; i < xs.length; i++)
        {
            xs[i] = xs[i] + xs[i - 1];
        }

        // 将特征点移到季节的中间
        double buffer = xs[0] *.5;
        for (i = 0; i < SEASONS.size(); i++)
        {
            xs[i] = xs[i] - SEASONS.get(i).length * .5  // 减去对应季节长度的 0.5 倍, 将点移到中间
                    - buffer;                           // 减去第一个季节特征点的偏移量(0.5 倍长), 使开始点为 0
        }
        // 对于最后一个特征点, 由于其和第一个季节的配置完全相同, 故直接减去第一个季节的长度即可
        xs[SEASONS.size()] -= SEASONS.get(0).length;

        // calculate interpolation
        temperatureInterpolation.load(xs, temperatures);
        rainfallInterpolation.load(xs, rainfalls);
    }

    static double phaseToTemp(float phase)
    {
        // TODO: 制作 MC 温度 (phase) 到实际温度的转换函数
        return 0;
    }

    public static float getTemperature(long time)
    {
        double inDay = time / 24000.0;
        return (float) temperatureInterpolation.apply(inDay);
    }

    public static float getRainfall(long time)
    {
        double inDay = time / 24000.0;
        return (float) rainfallInterpolation.apply(inDay);
    }

    public static void load()
    {
        generatePhaseFunc();
    }

}
