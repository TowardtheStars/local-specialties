package com.github.towardthestars.localspecialties.plant;


import com.github.towardthestars.localspecialties.plant.attribute.AffinityInfo;
import com.github.towardthestars.localspecialties.plant.attribute.AttributeAffinityManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class PlantBlockBase extends Block implements INutritionConsumer, IAgedPlantBlock
{
    AttributeAffinityManager affinityManager = new AttributeAffinityManager();

    PlantBlockBase(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        scheduledTickGrow(state, world, pos, random);
        scheduledTickWithering(state, world, pos, random);
    }

    protected void scheduledTickGrow(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!this.isRipe(state))
        {
            int growth = this.affinityManager.getGrowth(state, world, pos, random);
            growth += this.getAge(state);
            if (growth > maxAge())
            {
                world.setBlockState(pos, this.getBlockStateWithAge(maxAge()));
            }
            else
            {
                world.setBlockState(pos, this.getBlockStateWithAge(growth), 2);
            }
        }
    }

    protected void scheduledTickWithering(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (this.affinityManager.shouldWither(state, world, pos, random))
        {
            world.setBlockState(pos, Plants.WITHERING_MAP.getOrDefault(this, Blocks.AIR).getDefaultState());
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> origin = super.getDroppedStacks(state, builder);
        List<ItemStack> result = new ArrayList<>();
        Random random;
        try
        {
            Field field = builder.getClass().getDeclaredField("random");
            field.setAccessible(true);
            random = (Random) field.get(builder);
        }catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
            return origin;
        }
        for (ItemStack stack :
                origin)
        {
            System.out.println(stack.toString());
        }
        float multiplier = this.affinityManager.getHarvest(state, builder.getWorld(), builder.get(LootContextParameters.POSITION), random);
        System.out.println(String.format("Multiplier: %f", multiplier));
        int integerMul = (int)Math.floor(multiplier);
        float chance = multiplier - integerMul;
        for (int i = 0; i < integerMul; i++)
        {
            result.addAll(origin);
        }

        for (ItemStack stack : origin)
        {
            if (random.nextFloat() > chance)
            {
                result.add(stack);
            }
        }

        return result;
    }

    @Override
    public BlockState getBlockStateWithAge(int age)
    {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(this.getAgeProperty());
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(Plants.PLANTING_MAP.inverse().get(this));
    }

    public abstract AffinityInfo getAffinityInfo();
}
