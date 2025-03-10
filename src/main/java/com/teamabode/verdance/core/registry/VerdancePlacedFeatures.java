package com.teamabode.verdance.core.registry;

import com.teamabode.verdance.Verdance;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class VerdancePlacedFeatures {
    public static final ResourceKey<PlacedFeature> FLOWER_MULBERRY_FOREST = createKey("flower_mulberry_forest");
    public static final ResourceKey<PlacedFeature> MULBERRY = createKey("mulberry");
    public static final ResourceKey<PlacedFeature> MULBERRY_CHECKED = createKey("mulberry_checked");
    public static final ResourceKey<PlacedFeature> PATCH_CANTALOUPE = createKey("patch_cantaloupe");
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_VEGETATION = createKey("shrublands_vegetation");
    public static final ResourceKey<PlacedFeature> FLOWER_VIOLET = createKey("flower_violet");

    public static void register(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, FLOWER_MULBERRY_FOREST, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.FLOWER_MULBERRY_FOREST), List.of(
                NoiseThresholdCountPlacement.of(-0.25d, 1, 5),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome()
        ));
        PlacementUtils.register(context, MULBERRY, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.MULBERRY), List.of(
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(
                        VerdanceBlocks.MULBERRY_SAPLING.defaultBlockState(),
                        Vec3i.ZERO
                ))
        ));
        PlacementUtils.register(context, MULBERRY_CHECKED, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.MULBERRY), List.of(
                NoiseBasedCountPlacement.of(25, 10.0d, 0.5d),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(
                        VerdanceBlocks.MULBERRY_SAPLING.defaultBlockState(),
                        Vec3i.ZERO
                )),
                BiomeFilter.biome()
        ));
        PlacementUtils.register(context, PATCH_CANTALOUPE, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.PATCH_CANTALOUPE), List.of(
                RarityFilter.onAverageOnceEvery(400),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                BiomeFilter.biome()
        ));

        // We eventually will need a SHRUBLANDS_VEGETATION configured feature, that chooses between the flowering variants
        PlacementUtils.register(context, SHRUBLANDS_VEGETATION, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.PATCH_YELLOW_FLOWERING_SHRUB), List.of(
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        ));
        PlacementUtils.register(context, FLOWER_VIOLET, configuredFeatures.getOrThrow(VerdanceConfiguredFeatures.FLOWER_VIOLET), List.of(
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        ));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Verdance.id(name));
    }
}
