package com.github.towardthestars.localspecialties.plant.attribute;


import com.github.towardthestars.localspecialties.environment.attribute.IEnvAttribute;
import com.github.towardthestars.localspecialties.plant.attribute.affinity_model.IAffinityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * 某一项环境变量对植物某个生长变量的影响
 * @param <ENV_ATTR_RET_TYPE> 环境变量类型
 */
@AllArgsConstructor
public class Affinity<ENV_ATTR_RET_TYPE>
{
    @Getter
    private final IEnvAttribute<ENV_ATTR_RET_TYPE> boundAttribute;
    @Getter
    private final IAffinityModel<ENV_ATTR_RET_TYPE> affinityModel;
    public final EnumSchemeParameterType parameterType;

    public double getModifier(IWorld world, BlockPos pos)
    {
        return affinityModel.getMultiplier(boundAttribute.getAttribute(world, pos));
    }


//    public Text toText()
//    {
//        return null; // TODO
//    }
//
//    public Text toSimpleText()
//    {
//        return null; // TODO
//    }
}
