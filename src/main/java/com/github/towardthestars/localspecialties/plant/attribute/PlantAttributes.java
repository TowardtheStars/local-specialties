package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.Registries;
import com.github.towardthestars.localspecialties.plant.Plants;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.GrowthScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.HarvestScheme;
import com.github.towardthestars.localspecialties.plant.attribute.scheme.ViabilityScheme;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class PlantAttributes
{

    public static final PlantAttribute<Integer> GROWTH = new PlantAttribute<Integer>(GrowthScheme.fromProb(1, 0))
    {
        @Override
        public void apply(World worldIn, BlockPos pos, BlockState state, Integer value)
        {
            worldIn.setBlockState(pos, state.with(CropsBlock.AGE, MathHelper.clamp(state.get(CropsBlock.AGE) + value, 0, 7)));
        }

    };
    public static final PlantAttribute<Float> HARVEST = new PlantAttribute<Float>(HarvestScheme.fromExpVar(1, 0.1f))
    {
        @Override
        public void apply(World worldIn, BlockPos pos, BlockState state, Float value)
        {

        }
    };
    public static final PlantAttribute<Boolean> VIABILITY = new PlantAttribute<Boolean>(ViabilityScheme.fromP(1f))
    {
        @Override
        public void apply(World worldIn, BlockPos pos, BlockState state, Boolean value)
        {
            if (value)
                worldIn.setBlockState(pos, Plants.WITHERING_MAP.get(state.getBlock()).getDefaultState());
        }
    };
//    public static final PlantAttribute<EnumDiseaseType> DISEASE = new PlantAttribute<>("disease", null);


    private static void registerAttribute(String id, PlantAttribute attribute)
    {
        Registry.register(Registries.PLANT_ATTRIBUTE, LocalSpecialties.getIdentifier(id), attribute);
    }

    public static void registerAll()
    {
        registerAttribute("growth", GROWTH);
        registerAttribute("harvest", HARVEST);
        registerAttribute("viability", VIABILITY);
    }


}
