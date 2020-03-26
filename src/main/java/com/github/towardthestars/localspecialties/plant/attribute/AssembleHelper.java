package com.github.towardthestars.localspecialties.plant.attribute;

import com.github.towardthestars.localspecialties.Registries;
import com.github.towardthestars.localspecialties.plant.PlantBlockBase;
import com.github.towardthestars.localspecialties.plant.attribute.affinity_model.AffinityModels;
import com.github.towardthestars.localspecialties.plant.attribute.affinity_model.IAffinityModel;
import com.github.towardthestars.localspecialties.plant.attribute.merge_model.AffinityMergeMultiply;
import com.github.towardthestars.localspecialties.plant.attribute.merge_model.IMergeModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.experimental.Delegate;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AssembleHelper
{
    public static final Gson gson = new Gson();

    public static final Map<Identifier, AttributeAffinityManagerAssembler> ATTRIBUTE_AFFINITY_MANAGER_MAP = new HashMap<>();

    public static AttributeAffinityManagerAssembler fromFile(File file) throws FileNotFoundException
    {
        FileReader reader = new FileReader(file);
        JsonObject obj = gson.fromJson(reader, JsonObject.class);
        AttributeAffinityManagerAssembler manager = new AttributeAffinityManagerAssembler();
        for (Map.Entry<String, JsonElement> entry :
                obj.entrySet())
        {
            manager.put(new Identifier(entry.getKey()), gson.fromJson(entry.getValue(), MergeModelAssembler.class));
        }
        return manager;
    }

    public static void attachAffinityManager()
    {
        for (Map.Entry<Identifier, AttributeAffinityManagerAssembler> entry :
                ATTRIBUTE_AFFINITY_MANAGER_MAP.entrySet())
        {
            Block block = Registry.BLOCK.get(entry.getKey());
            if (block instanceof PlantBlockBase)
            {
                ((PlantBlockBase) block).setAffinityManager(entry.getValue().build());
            }
        }
    }

    interface IAssembler<T>{
        T build();
    }

    static class AttributeAffinityManagerAssembler implements IAssembler<AttributeAffinityManager>
    {
        @Delegate
        final Map<Identifier, MergeModelAssembler> modelAssemblerMap = new HashMap<>();

        @Override
        public AttributeAffinityManager build()
        {
            AttributeAffinityManager manager = new AttributeAffinityManager();
            forEach(((identifier, mergeModelAssembler) -> {
                PlantAttribute attribute = Registries.PLANT_ATTRIBUTE.get(identifier);
                IMergeModel mergeModel = mergeModelAssembler.build();
                manager.setPlantAttributeAffinityModel(attribute, mergeModel);
            }));
            return manager;
        }
    }

    static class MergeModelAssembler implements IAssembler<IMergeModel>
    {
        @SerializedName("merge_model")
        String id;
        @SerializedName("affinity")
        AffinityAssembler[] affinityAssemblers;

        @Override
        public IMergeModel build()
        {
            IMergeModel model;
            try
            {
                model = Registries.MERGE_MODEL.get(new Identifier(id)).newInstance();
            } catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
                model = new AffinityMergeMultiply();
            }
            for (AffinityAssembler affinityAssembler:
                 affinityAssemblers)
            {
                model.add(affinityAssembler.build());
            }
            return model;
        }
    }

    static class AffinityAssembler implements IAssembler<Affinity>
    {
        @SerializedName("environment")
        String boundAttribute;
        @SerializedName("parameter")
        EnumSchemeParameterType schemeParameterType;
        @SerializedName("affinity_model")
        AffinityModelAssembler affinityModelAssembler;

        @Override
        @SuppressWarnings("unchecked")
        public Affinity build()
        {
            return new Affinity(
                    Registries.ENV_ATTRIBUTE.get(new Identifier(boundAttribute)),
                    affinityModelAssembler.build(),
                    schemeParameterType
            );
        }
    }

    static class AffinityModelAssembler implements IAssembler<IAffinityModel>
    {
        @SerializedName("model")
        String id;
        @SerializedName("args")
        Object[] args;

        @Override
        public IAffinityModel build()
        {
            return AffinityModels.createWithArgs(new Identifier(id), args);
        }
    }


}
