package com.github.towardthestars.localspecialties.plant.attribute.affinity_model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class ModelNumber5Point extends ModelNumber
{
    @Getter
    double leftZeroPoint;
    @Getter
    double rightZeroPoint;
    @Getter
    double leftOnePoint;
    @Getter
    double rightOnePoint;
    @Getter
    double middlePeakPoint;
    @Getter
    double peakPointValue;


    private double leftDelta01;
    private double leftDelta1Peak;
    private double rightDeltaPeak1;
    private double rightDelta10;
    private double peakValue_1;

    @Override
    public ModelNumber withArgs(Object... argv)
    {
        Double[] args = (Double[]) argv;
        this.leftZeroPoint = args[0];
        this.leftOnePoint = args[1];
        this.middlePeakPoint = args[2];
        this.rightOnePoint = args[3];
        this.rightZeroPoint = args[4];
        this.peakPointValue = args[5];
        this.refresh();
        return this;
    }

    private ModelNumber5Point(double leftZeroPoint, double leftOnePoint, double middlePeakPoint, double peakPointValue, double rightOnePoint, double rightZeroPoint)
    {
        this.leftZeroPoint = leftZeroPoint;
        this.leftOnePoint = leftOnePoint;
        this.middlePeakPoint = middlePeakPoint;
        this.peakPointValue = peakPointValue;
        this.rightOnePoint = rightOnePoint;
        this.rightZeroPoint = rightZeroPoint;
        refresh();
    }

    public static ModelNumber5Point of(double... values)
    {
        Preconditions.checkArgument(values.length == 6);
        return new ModelNumber5Point(values[0], values[1], values[2], values[3], values[4], values[5]);
    }

    public void setLeftOnePoint(double leftOnePoint)
    {
        this.leftOnePoint = leftOnePoint;
        refresh();
    }

    public void setLeftZeroPoint(double leftZeroPoint)
    {
        this.leftZeroPoint = leftZeroPoint;
        refresh();
    }

    public void setRightZeroPoint(double rightZeroPoint)
    {
        this.rightZeroPoint = rightZeroPoint;
        refresh();
    }

    public void setRightOnePoint(double rightOnePoint)
    {
        this.rightOnePoint = rightOnePoint;
        refresh();
    }

    public void setMiddlePeakPoint(double middlePeakPoint)
    {
        this.middlePeakPoint = middlePeakPoint;
        refresh();
    }

    public void setPeakPointValue(double peakPointValue)
    {
        this.peakPointValue = peakPointValue;
        refresh();
    }

    private void refresh()
    {
        leftDelta01 = this.leftOnePoint - this.leftZeroPoint;
        leftDelta1Peak = this.middlePeakPoint - this.leftOnePoint;
        rightDeltaPeak1 = this.rightOnePoint - this.middlePeakPoint;
        rightDelta10 = this.rightZeroPoint - this.rightOnePoint;
        peakValue_1 = peakPointValue - 1;
    }

    @Override
    public double getMultiplier(Number envValue)
    {
        double x = envValue.doubleValue();
        if (x < middlePeakPoint)
        {
            if (x > leftOnePoint)
            {
                return peakValue_1 * (x - leftOnePoint) / leftDelta1Peak + getBase();
            }
            else
            {
                if (x > leftZeroPoint)
                {
                    return (x - leftZeroPoint) / leftDelta01 + getBase();
                }
            }
        }
        else
        {
            if (x < rightOnePoint)
            {
                return peakValue_1 * (x - rightOnePoint) / rightDeltaPeak1 + getBase();
            }
            else
            {
                if (x < rightZeroPoint)

                {
                    return (rightZeroPoint - x) / rightDelta10 + getBase();
                }
            }
        }
        return getBase();
    }
}
