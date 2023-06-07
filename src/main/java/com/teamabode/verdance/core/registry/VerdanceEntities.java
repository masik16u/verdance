package com.teamabode.verdance.core.registry;

import com.teamabode.verdance.Verdance;
import com.teamabode.verdance.common.entity.silkmoth.SilkMoth;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

public class VerdanceEntities {

    public static final EntityType<SilkMoth> SILK_MOTH = register(
            "silk_moth",
            FabricEntityTypeBuilder.createMob()
                    .spawnGroup(MobCategory.CREATURE)
                    .entityFactory(SilkMoth::new)
                    .dimensions(EntityDimensions.scalable(0.7f, 0.7f))
                    .defaultAttributes(SilkMoth::createSilkMothAttributes)
                    .spawnRestriction(SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules)
                    .build()
    );

    private static <E extends Entity, T extends EntityType<E>> EntityType<E> register(String name, T entity) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Verdance.MOD_ID, name), entity);
    }

    public static void register() {

    }
}
