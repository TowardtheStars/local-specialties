package com.github.towardthestars.localspecialties.soil;

import com.github.towardthestars.localspecialties.plant.INutritionConsumer;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.*;

public class BlockFarmland extends FarmlandBlock
{
    public BlockFarmland(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, 0).with(FERTILITY, 0));
    }

    public static IntProperty FERTILITY = IntProperty.of("fertility", 0, 7);

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(BlockFarmland.FERTILITY);
        builder.add(BlockFarmland.MOISTURE);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (!state.canPlaceAt(world, pos)) {
            setToDirt(state, world, pos);
        } else {
            scheduledTickDryingOrWatering(state, world, pos, random);
            scheduledTickConsumeNutrition(state, world, pos, random);
        }
    }

    private void scheduledTickDryingOrWatering(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        int i = state.get(MOISTURE);
        int j = getMoistureFromWaterNearby(world, pos);
        if (j == 0 && !world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, state.with(MOISTURE, i - 1), 2);
            } else if (!hasCrop(world, pos)) {
                setToDirt(state, world, pos);
            }
        } else if (j > state.get(MOISTURE)) {
            world.setBlockState(pos, state.with(MOISTURE, j), 2);
        }
    }

    private void scheduledTickConsumeNutrition(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (hasCrop(world, pos))
        {
            BlockState cropState = world.getBlockState(pos.up());
            INutritionConsumer crop = (INutritionConsumer) cropState.getBlock();
            int fertility = state.get(FERTILITY);
            int moisture = state.get(MOISTURE);
            if (crop.shouldConsumeFertility(world, pos, cropState, random))
            {
                if (fertility > 0)
                {
                    world.setBlockState(pos, state.with(FERTILITY, fertility - 1));
                }
                else
                {
                    setToDirt(state, world, pos);
                }
            }
            if (crop.shouldConsumeMoisture(world, pos, cropState, random))
            {
                if (moisture > 0)
                {
                    world.setBlockState(pos, state.with(MOISTURE, state.get(MOISTURE) - 1));
                }
                else
                {
                    setToDirt(state, world, pos);
                }
            }
        }
    }

    public static void setToDirt(BlockState state, World world, BlockPos pos) {
        int fertility = state.get(FERTILITY);
        world.setBlockState(
                pos,
                pushEntitiesUpBeforeBlockChange(
                        state, FarmLandHelper.getDirtStateForFertility(fertility), world, pos
                )
        );

    }

    private static boolean hasCrop(BlockView world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }

    private static int getMoistureFromWaterNearby(WorldView worldView, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();
        int waterCount = 0;
        BlockPos blockPos;
        while (var2.hasNext() && waterCount < 21) {
            blockPos = (BlockPos)var2.next();
            waterCount += worldView.getFluidState(blockPos).matches(FluidTags.WATER) ? 1 : 0;
        }
        return MathHelper.clamp(waterCount / 3, 0, 7);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {

        if (player.getStackInHand(hand).getItem() instanceof BoneMealItem)
        {
            world.setBlockState(pos, state.with(FERTILITY, MathHelper.clamp(state.get(FERTILITY) + 2, 0, 7)));
            player.getStackInHand(hand).decrement(1);
            if (!world.isClient) {
                world.playLevelEvent(2005, pos, 0);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            setToDirt(world.getBlockState(pos), world, pos);
        }

        super.onLandedUpon(world, pos, entity, distance);
    }
}
