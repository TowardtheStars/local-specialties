package com.github.towardthestars.localspecialties.environment.soil;

import com.github.towardthestars.localspecialties.plant.INutritionConsumer;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.HoeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.*;

public class BlockFarmland
//        extends FarmlandBlock
{

//    public BlockFarmland(Settings settings)
//    {
//        super(settings);
//        this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, 0).with(FERTILITY, 0));
//    }

//    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
//        return SHAPE;
//    }
//
//    public static IntProperty FERTILITY = IntProperty.of("fertility", 0, 7);
//
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
//    {
//        builder.add(BlockFarmland.FERTILITY);
//        builder.add(BlockFarmland.MOISTURE);
//    }
//
//    @Override
//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
//    {
//        if (!state.canPlaceAt(world, pos)) {
//            setToDirt(state, world, pos);
//        } else {
//            scheduledTickDryingOrWatering(state, world, pos, random);
//            scheduledTickConsumeNutrition(state, world, pos, random);
//        }
//    }
//
//    /**
//     * 随机刻根据周围水方块数量更新土壤湿度
//     * @param state 土壤方块状态
//     * @param world 土壤所在世界
//     * @param pos 土壤位置
//     * @param random RNG
//     */
//    protected void scheduledTickDryingOrWatering(BlockState state, ServerWorld world, BlockPos pos, Random random)
//    {
//        int moisturePresent = state.get(MOISTURE);
//        int targetMoisture = getMoistureFromWaterNearby(world, pos);
//        if (targetMoisture == 0 && !world.hasRain(pos.up())) {
//            if (moisturePresent > 0) {
//                world.setBlockState(pos, state.with(MOISTURE, moisturePresent - 1), 2);
//            } else if (!hasCrop(world, pos)) {
//                setToDirt(state, world, pos);
//            }
//        } else if (targetMoisture > moisturePresent) {
//            world.setBlockState(pos, state.with(MOISTURE, targetMoisture), 2);
//        }
//    }
//
//    /**
//     * 随机刻消耗土壤养分和水分
//     * @param state 土壤方块状态
//     * @param world 土壤所在世界
//     * @param pos 土壤位置
//     * @param random RNG
//     */
//    protected void scheduledTickConsumeNutrition(BlockState state, ServerWorld world, BlockPos pos, Random random)
//    {
//        if (hasCrop(world, pos))
//        {
//            BlockState cropState = world.getBlockState(pos.up());
//            INutritionConsumer crop = (INutritionConsumer) cropState.getBlock();
//            int fertility = state.get(FERTILITY);
//            int moisture = state.get(MOISTURE);
//            if (crop.shouldConsumeFertility(world, pos, cropState, random))
//            {
//                if (fertility > 0)
//                {
//                    world.setBlockState(pos, state.with(FERTILITY, fertility - 1));
//                }
//                else
//                {
//                    setToDirt(state, world, pos);
//                }
//            }
//            if (crop.shouldConsumeMoisture(world, pos, cropState, random))
//            {
//                if (moisture > 0)
//                {
//                    world.setBlockState(pos, state.with(MOISTURE, state.get(MOISTURE) - 1));
//                }
//                else
//                {
//                    setToDirt(state, world, pos);
//                }
//            }
//        }
//    }
//
//    public static void setToDirt(BlockState state, World world, BlockPos pos) {
//        int fertility = state.get(FERTILITY);
//        world.setBlockState(
//                pos,
//                pushEntitiesUpBeforeBlockChange(
//                        state, FarmLandHelper.getDirtStateForFertility(fertility), world, pos
//                )
//        );
//
//    }
//
//    /**
//     * 用于检测是否有消耗土壤养分和水分的作物存在
//     * @param world 土壤所在世界
//     * @param pos 土壤位置
//     * @return 是否存在消耗养分的方块
//     */
//    protected static boolean hasCrop(BlockView world, BlockPos pos) {
//        Block block = world.getBlockState(pos.up()).getBlock();
//        return block instanceof INutritionConsumer;
//    }
//
//    protected static int getMoistureFromWaterNearby(WorldView worldView, BlockPos pos) {
//        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();
//        // 水的多少决定土地的湿润程度
//        final int waterToMoisture = 3;
//        // 只要有一格水, 就能使土地湿润, 故初始值设为 (比值 - 1)
//        int waterCount = waterToMoisture - 1;
//        BlockPos blockPos;
//        // 当足够使土地达到最大湿润时, 即可停止计数
//        while (var2.hasNext() && waterCount < waterToMoisture * 7) {
//            blockPos = (BlockPos)var2.next();
//            waterCount += worldView.getFluidState(blockPos).matches(FluidTags.WATER) ? 1 : 0;
//        }
//        return MathHelper.clamp(waterCount / waterToMoisture, 0, 7);
//    }
//
//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
//    {
//
//        if (player.getStackInHand(hand).getItem() instanceof BoneMealItem)
//        {
//            world.setBlockState(pos, state.with(FERTILITY, MathHelper.clamp(state.get(FERTILITY) + 2, 0, 7)));
//            player.getStackInHand(hand).decrement(1);
//            if (!world.isClient) {
//                world.playLevelEvent(2005, pos, 0);
//            }
//            return ActionResult.SUCCESS;
//        }
//        else if (player.getStackInHand(hand).getItem() instanceof HoeItem)
//        {
//            // 松土: 土壤肥力均摊
//            Iterator<BlockPos> iterator = BlockPos.iterate(-1, 1, 0, 0, -1, 1).iterator();
//            BlockPos targetPos;
//            BlockState targetState;
//            int sourceFertility = getFertility(state) - 1;
//            boolean shouldDistribute = false;
//            while (iterator.hasNext())
//            {
//                targetPos = iterator.next();
//                targetState = world.getBlockState(targetPos);
//                if (targetState.getBlock() instanceof BlockFarmland)
//                {
//                    int targetFertility = getFertility(targetState);
//                    if (targetFertility < sourceFertility)
//                    {
//                        // 不用 clamp, 因为目标土壤肥力 < 源土壤肥力 <= 7 - 1 = 6
//                        // 所以目标土壤肥力 + 1 <= 6
//                        world.setBlockState(targetPos, targetState.with(FERTILITY, targetFertility + 1), 2);
//                        shouldDistribute = true;
//                    }
//                }
//            }
//
//            if (shouldDistribute)
//            {
//                world.setBlockState(pos, state.with(FERTILITY, sourceFertility), 2);
//            }
//            return ActionResult.SUCCESS;
//        }
//        return ActionResult.PASS;
//    }
//
//    public static int getFertility(BlockState state)
//    {
//        if (state.getBlock() instanceof BlockFarmland)
//        {
//            return state.get(FERTILITY);
//        }
//        else
//        {
//            return FarmLandHelper.getFarmlandForDirt(state).get(FERTILITY);
//        }
//    }
//
//    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
//        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
//            setToDirt(world.getBlockState(pos), world, pos);
//        }
//
//        super.onLandedUpon(world, pos, entity, distance);
//    }
}
