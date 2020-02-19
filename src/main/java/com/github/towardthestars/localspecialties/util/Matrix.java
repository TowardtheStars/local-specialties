package com.github.towardthestars.localspecialties.util;

import com.google.common.base.Preconditions;
import lombok.Getter;

public class Matrix
{
    private double[][] data;
    @Getter
    private int colSize;
    @Getter
    private int rowSize;

    public Matrix(int row, int col)
    {
        this.data = new double[row][col];
        this.rowSize = row;
        this.colSize = col;
    }

    public void set(int row, int col, double value)
    {
        this.data[row][col] = value;
    }

    public double get(int row, int col)
    {
        return data[row][col];
    }

    public double[] getRow(int row)
    {
        return data[row];
    }

    public void linearRowTransform(int rowSrc, int rowDst, double coeff)
    {
        for (int i = 0; i < this.colSize; i++)
        {
            this.data[rowDst][i] += coeff * this.data[rowSrc][i];
        }
    }

    public void linearColTransform(int colSrc, int colDst, double coeff)
    {
        for (int i = 0; i < this.rowSize; i++)
        {
            this.data[i][colDst] += coeff * this.data[i][colSrc];
        }
    }

    public Matrix swapRow(int row1, int row2)
    {
        double temp;
        for (int i = 0; i < this.colSize; i++)
        {
            temp = data[row1][i];
            data[row1][i] = data[row2][i];
            data[row2][i] = temp;
        }
        return this;
    }

    public static Matrix identity(int n)
    {
        Matrix m = new Matrix(n, n);
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                m.data[i][j] = (i == j ? 1 : 0);
            }
        }
        return m;
    }

    public Matrix scale(double a)
    {
        for (int i = 0; i < rowSize; i++)
        {
            for (int j = 0; j < colSize; j++)
            {
                data[i][j] *= a;
            }
        }
        return this;
    }

    public enum JointMethod{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Matrix joint (Matrix matrix, JointMethod method)
    {
        int newRow, newCol;
        int originL = 0, originT = 0;
        int mL = 0, mT = 0;
        if (method == JointMethod.LEFT || method == JointMethod.RIGHT)
        {
            Preconditions.checkArgument(matrix.rowSize == this.rowSize);
            newRow = this.rowSize;
            newCol = matrix.colSize + this.colSize;
        }
        else
        {
            Preconditions.checkArgument(matrix.colSize == this.colSize);
            newRow = this.rowSize + matrix.rowSize;
            newCol = this.colSize;
        }
        switch (method)
        {
            case RIGHT:
                mL = this.colSize;
                break;
            case LEFT:
                originL = matrix.colSize;
                break;
            case UP:
                originT = matrix.rowSize;
                break;
            case DOWN:
                mT = this.rowSize;
                break;
        }
        Matrix result = new Matrix(newRow, newCol);
        for (int i = 0; i < this.rowSize; i++)
        {
            System.arraycopy(this.data[i], 0, result.data[i + originT], originL, this.colSize);
        }

        for (int i = 0; i < matrix.rowSize; i++)
        {
            System.arraycopy(matrix.data[i], 0, result.data[i + mT], mL, matrix.colSize);
        }

        return result;
    }
}
