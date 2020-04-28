package com.github.towardthestars.localspecialties.mixin;

import com.github.towardthestars.localspecialties.LocalSpecialties;
import com.github.towardthestars.localspecialties.environment.soil.FarmLandHelper;
import com.github.towardthestars.localspecialties.environment.soil.LSProperties;
import com.github.towardthestars.localspecialties.plant.INutritionConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Random;

@Mixin({FarmlandBlock.class})
public class FarmlandBlockMixin extends Block
{
    @Shadow @Final public static IntegerProperty MOISTURE;
    private static final IntegerProperty FERTILITY = LSProperties.FERTILITY;

    public FarmlandBlockMixin(Properties properties)
    {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void defaultBlockStateInjector(Properties builder, CallbackInfo ci)
    {
        this.setDefaultState(
                this.stateContainer.getBaseState()
                        .with(FERTILITY, 4)
                        .with(MOISTURE, 0)
        );
    }


    /**
     * @author TowardtheStars
     */
    @Override
    @Overwrite
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(MOISTURE, LSProperties.FERTILITY);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            setToDirt(state, worldIn, pos);
        } else {
            scheduledTickDryingOrWatering(state, worldIn, pos, rand);
            scheduledTickConsumeNutrition(state, worldIn, pos, rand);
        }
    }

    /**
     * 随机刻根据周围水方块数量更新土壤湿度
     * @param state 土壤方块状态
     * @param world 土壤所在世界
     * @param pos 土壤位置
     * @param random RNG
     */
    protected void scheduledTickDryingOrWatering(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        int moisturePresent = state.get(MOISTURE);
        int targetMoisture = getMoistureFromWaterNearby(world, pos);
        if (targetMoisture == 0 && !world.isRainingAt(pos.up())) {
            if (moisturePresent > 0) {
                world.setBlockState(pos, state.with(MOISTURE, moisturePresent - 1), 2);
            } else if (!hasCrop(world, pos)) {
                setToDirt(state, world, pos);
            }
        } else if (targetMoisture > moisturePresent) {
            world.setBlockState(pos, state.with(MOISTURE, targetMoisture), 2);
        }
    }

    /**
     * 随机刻消耗土壤养分和水分
     * @param state 土壤方块状态
     * @param world 土壤所在世界
     * @param pos 土壤位置
     * @param random RNG
     */
    protected void scheduledTickConsumeNutrition(BlockState state, ServerWorld world, BlockPos pos, Random random)
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
                    world.setBlockState(pos, state.with(FERTILITY, fertility - 1), 3);
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

    private static void setToDirt(BlockState state, World world, BlockPos pos) {
        int fertility = state.get(FERTILITY);
        BlockState dirt = FarmLandHelper.getDirtStateForFertility(fertility);
        LocalSpecialties.LOGGER.debug(String.format("Get dirt state for fertility %d: " + dirt.toString(), fertility));
        world.setBlockState(
                pos,
                nudgeEntitiesWithNewState(
                        state, dirt, world, pos
                ),
                3
        );

    }

    /**
     * 用于检测是否有消耗土壤养分和水分的作物存在
     * @param world 土壤所在世界
     * @param pos 土壤位置
     * @return 是否存在消耗养分的方块
     */
    protected boolean hasCrop(ServerWorld world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        return block instanceof INutritionConsumer;
    }

    protected int getMoistureFromWaterNearby(World worldView, BlockPos pos) {
        Iterator var2 = BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();
        // 水的多少决定土地的湿润程度
        final int waterToMoisture = 3;
        // 只要有一格水, 就能使土地湿润, 故初始值设为 (比值 - 1)
        int waterCount = waterToMoisture - 1;
        BlockPos blockPos;
        // 当足够使土地达到最大湿润时, 即可停止计数
        while (var2.hasNext() && waterCount < waterToMoisture * 7) {
            blockPos = (BlockPos)var2.next();
            waterCount += worldView.getFluidState(blockPos).isTagged(FluidTags.WATER) ? 1 : 0;
        }
        return MathHelper.clamp(waterCount / waterToMoisture, 0, 7);
    }

    public int getFertility(BlockState state)
    {
        return state.get(FERTILITY);
    }

}
