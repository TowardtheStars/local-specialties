package com.github.towardthestars.localspecialties.util.interpolation.periodic;

import com.github.towardthestars.localspecialties.util.Maths;
import com.github.towardthestars.localspecialties.util.Matrix;

import java.util.Arrays;

public class CubicSplinePeriodicInterpolation extends AbstractPeriodicInterpolation
{
    private double[] s1, s2, s3;

    /**
     *
     * 三弯矩法
     */
    @Override
    protected void reload()
    {
        double[] muList = new double[n + 1];
        double[] lambdaList = new double[n + 1];
        double[] dList = Maths.differenceQuotient(this.xs, this.ys, 2);
        dList = Arrays.copyOf(dList, n);
        dList[n - 1] = (this.ys[n] - this.ys[n - 1]) / (this.xs[n] - this.xs[n - 1]);
        for (int i = 1; i < n; i++)
        {
            lambdaList[i] = hList[i] / (hList[i] + hList[i - 1]);
            muList[i] = 1 - lambdaList[i];
        }
        lambdaList[n] = hList[0] / (hList[0] + hList[n - 1]);
        muList[n] = 1 - lambdaList[n];

        // 构建三弯矩法的系数矩阵 A
        Matrix coeff = Matrix.identity(n);

        coeff = coeff.scale(2);
        for (int i = 1; i < n; i++)
        {
            coeff.set(i - 1, i, lambdaList[i]);
            coeff.set(i, i - 1, muList[i + 2]);
        }
        coeff.set(n - 1, 0, lambdaList[n]);
        coeff.set(0, n - 1, muList[1]);

        // 构建三弯矩法的系数矩阵 B
        Matrix column = new Matrix(n, 1);
        for (int i = 0; i < n; i++)
        {
            column.set(i, 0, dList[i] * 6);
        }

        // 并和 ( A  B )
        coeff = coeff.joint(column, Matrix.JointMethod.RIGHT);

        // 高斯消元法解方程 AX = B
        // 上三角化
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                coeff.linearRowTransform(i, j, -coeff.get(j, i) / coeff.get(i, i));
            }
        }

        // 对角化
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n + 1; j++)
            {
                coeff.linearColTransform(i, j, -coeff.get(i, j) / coeff.get(i, i));
            }
        }

        // 算出二阶导数值, M_0 ~ M_n <==> MList[0] ~ MList[n]

        double[] MList = new double[n + 1];
        for (int i = 0; i < n; i++)
        {
            MList[i + 1] = coeff.get(i, n) / coeff.get(i, i);
        }

        // 周期性边界条件
        MList[0] = MList[n];

        /*
         * S(x) = s_{k1}(x - x_k)^3 + s_{k2}(x - x_k)^2 + s_{k3}(x - x_k) + y_k
         * k = 0, 1, 2, ..., n - 1
         * (x \in [x_k, x_{k + 1}])
         * s_{k{r}} <==> s{r}, r = 1, 2, 3
         */
        s1 = new double[n];
        s2 = new double[n];
        s3 = new double[n];

        for (int i = 0; i < n; i++)
        {
            s1[i] = (MList[i + 1] - MList[i]) / (6 * hList[i]);
            s2[i] = MList[i] * .5;
            s3[i] = (ys[i + 1] - ys[i]) / hList[i] - (2 * MList[i] + MList[i + 1]) / (6 * hList[i]);
        }
    }

    @Override
    public double apply(double x)
    {
        int k = 0;
        for (int i = 0; i < n + 1; i++)
        {
            if (x > xs[i])
            {
                k = i;
                break;
            }
        }
        double delta = x - xs[k];
        return s1[k] * Math.pow(delta, 3) + s2[k] * Math.pow(delta, 2) + s3[k] * delta + ys[k];
    }
}
