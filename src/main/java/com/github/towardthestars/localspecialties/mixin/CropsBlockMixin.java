package com.github.towardthestars.localspecialties.mixin;

import com.github.towardthestars.localspecialties.plant.INutritionConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(CropsBlock.class)
public abstract class CropsBlockMixin extends BushBlock implements IGrowable, INutritionConsumer
{
    protected CropsBlockMixin(Properties properties)
    {
        super(properties);
    }

    @Shadow public abstract boolean isMaxAge(BlockState state);

    @Shadow
    public abstract boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient);

    @Shadow
    public abstract boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state);

    @Shadow
    public abstract void grow(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_);

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);
    }
}
