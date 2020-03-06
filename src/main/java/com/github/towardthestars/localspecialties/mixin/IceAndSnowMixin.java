package com.github.towardthestars.localspecialties.mixin;

import com.github.towardthestars.localspecialties.environment.attribute.EnvAttributes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.block.Block.dropStacks;

@Mixin({IceBlock.class, SnowBlock.class})
public class IceAndSnowMixin
{
    @Inject(at = @At("HEAD"), method = "scheduledTick")
    private void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci)
    {
        if (state.getBlock() == Blocks.SNOW)
        {
            if (world.getLightLevel(LightType.BLOCK, pos) > 11
                || EnvAttributes.TEMPERATURE.getAttribute(world, pos) >= 0.15
            ) {
                dropStacks(state, world, pos);
                world.removeBlock(pos, false);
            }
            ci.cancel();
        }
        else if (state.getBlock() == Blocks.ICE)
        {
            if
            (
                    world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)
                            || EnvAttributes.TEMPERATURE.getAttribute(world, pos) >= 0.15
            )
                this.melt(state, world, pos);
            ci.cancel();
        }
    }


    private void melt(BlockState state, World world, BlockPos pos)
    {
        if (world.dimension.doesWaterVaporize()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.updateNeighbor(pos, Blocks.WATER, pos);
        }
    }
}
