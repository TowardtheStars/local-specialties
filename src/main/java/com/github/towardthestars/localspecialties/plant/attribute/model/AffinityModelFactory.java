package com.github.towardthestars.localspecialties.plant.attribute.model;
import com.github.towardthestars.localspecialties.plant.attribute.affinity_model.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AffinityModelFactory
{

    public static IAffinityModel create(String name)
    {
        switch (name)
        {
            case "5points":
                return new ModelNumber5Point();
            case "3points":
                return new ModelNumber3Point();
            case "quadratic":
                return new ModelNumberQuadratic();
            case "boolean":
                return new ModelBoolean();
        }
        return null;
    }

    public static IAffinityModel createWithArgs(String name, Object... args)
    {
        IAffinityModel model = create(name);
        if (model != null)
        {
            return model.withArgs(args);
        }
        return null;
    }

}
