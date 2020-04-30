package com.github.towardthestars.localspecialties.mixin;

import com.github.towardthestars.localspecialties.plant.ICrop;
import com.github.towardthestars.localspecialties.plant.IWithAttributeAffinity;
import com.github.towardthestars.localspecialties.plant.attribute.AttributeAffinityManager;
import com.github.towardthestars.localspecialties.plant.attribute.PlantAttributes;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(CropsBlock.class)
public abstract class CropsBlockMixin extends BushBlock implements IGrowable, ICrop
{
    protected CropsBlockMixin(Properties properties)
    {
        super(properties);
    }

    /**
     * IGrowable Start
     */
    @Shadow
    public abstract boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient);

    @Shadow
    public abstract boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state);

    @Shadow
    public abstract void grow(ServerWorld p_225535_1_, Random p_225535_2_, BlockPos p_225535_3_, BlockState p_225535_4_);

    /*
     * IGrowable End
     * =================================================================
     * INutritionConsumer Start
     */

    @Shadow @Final public static IntegerProperty AGE;

    @Shadow public abstract int getMaxAge();

    @Override
    public int consumedFertility(World world, BlockPos pos, BlockState state, Random random)
    {
        // TODO: 使用配置文件数据
        return random.nextFloat() < 0.05 ? 1 : 0;
    }

    @Override
    public int consumedMoisture(World world, BlockPos pos, BlockState state, Random random)
    {
        // TODO: 使用配置文件数据
        return random.nextFloat() < 0.05 ? 1 : 0;
    }

    /*
     * INutritionConsumer End
     * ============================================================================================
     * IWithAttributeAffinity Start
     */
    @Getter
    @Setter
    private AttributeAffinityManager affinityManager;
    @Override
    public AttributeAffinityManager getManager()
    {
        return affinityManager;
    }

    /**
     * @param manager manager to bind
     * @return this
     */
    @Override
    public IWithAttributeAffinity setManager(AttributeAffinityManager manager)
    {
        this.affinityManager = manager;
        return this;
    }

    /*
     * IWithAttributeAffinity End
     */

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        int growth = affinityManager.getPlantAttributeValue(PlantAttributes.GROWTH, state, worldIn, pos, rand);
        if
        (
                this.getAge(state) < this.getMaxAge() &&
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, growth != 0)
        )
        {
            PlantAttributes.GROWTH.apply(worldIn, pos, state, growth);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }

        if (!affinityManager.getPlantAttributeValue(PlantAttributes.VIABILITY, state, worldIn, pos, rand))
        {
            PlantAttributes.VIABILITY.apply(worldIn, pos, state, true);
        }
    }

    @Shadow
    protected abstract int getAge(BlockState state);

}
