package org.amateras_smp.amacarpet.utils;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.world.poi.PointOfInterestType;

public class IdentifierUtil
{
    public static Identifier ofVanilla(String id)
    {
        //#if MC >= 12100
        //$$ return Identifier.of(id);
        //#else
        return new Identifier(id);
        //#endif
    }

    public static Identifier of(String namespace, String path)
    {
        //#if MC >= 12100
        //$$ return Identifier.of(namespace, path);
        //#else
        return new Identifier(namespace, path);
        //#endif
    }

    public static Identifier id(Block block)
    {
        return Registries.BLOCK.getId(block);
    }

    public static Identifier id(Fluid fluid)
    {
        return Registries.FLUID.getId(fluid);
    }

    public static Identifier id(EntityType<?> entityType)
    {
        return Registries.ENTITY_TYPE.getId(entityType);
    }

    public static Identifier id(BlockEntityType<?> blockEntityType)
    {
        return Registries.BLOCK_ENTITY_TYPE.getId(blockEntityType);
    }

    public static Identifier id(PointOfInterestType poiType)
    {
        return Registries.POINT_OF_INTEREST_TYPE.getId(poiType);
    }
}
