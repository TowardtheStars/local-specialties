package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.util.IStatisticsScheme;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public abstract class PlantAttribute<ATTR_RET_TYPE> implements IForgeRegistryEntry<PlantAttribute>
{
    @Getter @Setter
    @NonNull
    private IStatisticsScheme<ATTR_RET_TYPE> defaultScheme;

    public abstract void apply(World worldIn, BlockPos pos, BlockState state, ATTR_RET_TYPE value);

    private ResourceLocation name;
    /**
     * Sets a unique name for this Item. This should be used for uniquely identify the instance of the Item.
     * This is the valid replacement for the atrocious 'getUnlocalizedName().substring(6)' stuff that everyone does.
     * Unlocalized names have NOTHING to do with unique identifiers. As demonstrated by vanilla blocks and items.
     * <p>
     * The supplied name will be prefixed with the currently active mod's modId.
     * If the supplied name already has a prefix that is different, it will be used and a warning will be logged.
     * <p>
     * If a name already exists, or this Item is already registered in a registry, then an IllegalStateException is thrown.
     * <p>
     * Returns 'this' to allow for chaining.
     *
     * @param name Unique registry name
     * @return This instance
     */
    @Override
    public PlantAttribute setRegistryName(ResourceLocation name)
    {
        this.name = name;
        return this;
    }

    /**
     * A unique identifier for this entry, if this entry is registered already it will return it's official registry name.
     * Otherwise it will return the name set in setRegistryName().
     * If neither are valid null is returned.
     *
     * @return Unique identifier or null.
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return this.name;
    }

    /**
     * Determines the type for this entry, used to look up the correct registry in the global registries list as there can only be one
     * registry per concrete class.
     *
     * @return Root registry type.
     */
    @Override
    public Class<PlantAttribute> getRegistryType()
    {
        return PlantAttribute.class;
    }
}
