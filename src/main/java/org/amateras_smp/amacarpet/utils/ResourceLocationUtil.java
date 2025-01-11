// Copyright (c) 2025 The AmaCarpet Authors
// This file is part of the AmaCarpet project and is licensed under the terms of
// the GNU Lesser General Public License, version 3.0. See the LICENSE file for details.

package org.amateras_smp.amacarpet.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

public class ResourceLocationUtil
{
    public static ResourceLocation ofVanilla(String id)
    {
        //#if MC >= 12100
        //$$ return ResourceLocation.of(id);
        //#else
        return new ResourceLocation(id);
        //#endif
    }

    public static ResourceLocation of(String namespace, String path)
    {
        //#if MC >= 12100
        //$$ return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //#elseif MC == 12006
        //$$ return ResourceLocation.tryBuild(namespace, path);
        //#else
        return new ResourceLocation(namespace, path);
        //#endif
    }

    public static ResourceLocation id(Block block)
    {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static ResourceLocation id(Fluid fluid)
    {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    public static ResourceLocation id(EntityType<?> entityType)
    {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public static ResourceLocation id(BlockEntityType<?> blockEntityType)
    {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntityType);
    }

    public static ResourceLocation id(PoiType poiType)
    {
        return BuiltInRegistries.POINT_OF_INTEREST_TYPE.getKey(poiType);
    }

}
