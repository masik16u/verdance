package com.teamabode.verdance.core.data.server;

import com.teamabode.verdance.core.misc.datagen.VerdanceFamilies;
import com.teamabode.verdance.core.registry.VerdanceBlocks;
import com.teamabode.verdance.core.registry.VerdanceItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class VerdanceBlockLootTableProvider extends FabricBlockLootTableProvider {
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = {0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public VerdanceBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    public void generate() {
        mulberry();
        shrub();
        stucco();
        cantaloupe();
        silkWormEggs();
    }

    private void shrub() {
        this.add(VerdanceBlocks.SHRUB, block -> {
            var lootItem = LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)));

            return createShearsDispatchTable(block, this.applyExplosionDecay(block, lootItem));
        });
    }

    private void mulberry() {
        dropSelf(VerdanceBlocks.MULBERRY_LOG);
        dropSelf(VerdanceBlocks.MULBERRY_WOOD);
        dropSelf(VerdanceBlocks.STRIPPED_MULBERRY_LOG);
        dropSelf(VerdanceBlocks.STRIPPED_MULBERRY_WOOD);
        dropSelf(VerdanceBlocks.MULBERRY_PLANKS);
        dropSelf(VerdanceBlocks.MULBERRY_STAIRS);
        add(VerdanceBlocks.MULBERRY_SLAB, this::createSlabItemTable);
        dropSelf(VerdanceBlocks.MULBERRY_FENCE);
        dropSelf(VerdanceBlocks.MULBERRY_FENCE_GATE);
        add(VerdanceBlocks.MULBERRY_DOOR, this::createDoorTable);
        dropSelf(VerdanceBlocks.MULBERRY_TRAPDOOR);
        dropSelf(VerdanceBlocks.MULBERRY_PRESSURE_PLATE);
        dropSelf(VerdanceBlocks.MULBERRY_BUTTON);
        add(VerdanceBlocks.MULBERRY_LEAVES, this::createMulberryLeaves);
        add(VerdanceBlocks.FLOWERING_MULBERRY_LEAVES, this::createFloweringMulberryLeaves);
        dropSelf(VerdanceBlocks.MULBERRY_SAPLING);
        dropPottedContents(VerdanceBlocks.POTTED_MULBERRY_SAPLING);
        dropSelf(VerdanceBlocks.MULBERRY_SIGN);
        dropSelf(VerdanceBlocks.MULBERRY_HANGING_SIGN);
        //dropSelf(VerdanceBlocks.MULBERRY_CABINET);
    }

    private LootTable.Builder createMulberryLeaves(Block leafBlock) {
        var lootItem = LootItem.lootTableItem(Items.STICK);

        return createSilkTouchOrShearsDispatchTable(
                leafBlock,
                this.applyExplosionCondition(leafBlock, lootItem).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))
        );
    }

    private LootTable.Builder createFloweringMulberryLeaves(Block leafBlock) {
        return createMulberryLeaves(leafBlock).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(leafBlock, LootItem.lootTableItem(VerdanceItems.MULBERRY).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))));
    }

    private void stucco() {
        addStucco(VerdanceFamilies.WHITE_STUCCO);
        addStucco(VerdanceFamilies.LIGHT_GRAY_STUCCO);
        addStucco(VerdanceFamilies.GRAY_STUCCO);
        addStucco(VerdanceFamilies.BLACK_STUCCO);
        addStucco(VerdanceFamilies.BROWN_STUCCO);
        addStucco(VerdanceFamilies.RED_STUCCO);
        addStucco(VerdanceFamilies.ORANGE_STUCCO);
        addStucco(VerdanceFamilies.YELLOW_STUCCO);
        addStucco(VerdanceFamilies.LIME_STUCCO);
        addStucco(VerdanceFamilies.GREEN_STUCCO);
        addStucco(VerdanceFamilies.CYAN_STUCCO);
        addStucco(VerdanceFamilies.LIGHT_BLUE_STUCCO);
        addStucco(VerdanceFamilies.BLUE_STUCCO);
        addStucco(VerdanceFamilies.PURPLE_STUCCO);
        addStucco(VerdanceFamilies.MAGENTA_STUCCO);
        addStucco(VerdanceFamilies.PINK_STUCCO);
    }

    private void addStucco(BlockFamily stuccoFamily) {
        dropSelf(stuccoFamily.getBaseBlock());
        dropSelf(stuccoFamily.get(BlockFamily.Variant.STAIRS));
        add(stuccoFamily.get(BlockFamily.Variant.SLAB), this::createSlabItemTable);
        dropSelf(stuccoFamily.get(BlockFamily.Variant.WALL));
    }

    private void cantaloupe() {
        add(VerdanceBlocks.CANTALOUPE, block -> {
            var lootItem = LootItem.lootTableItem(VerdanceItems.CANTALOUPE_SLICE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 4.0f))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply(LimitCount.limitCount(IntRange.upperBound(4)));
            return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, lootItem));
        });
        add(VerdanceBlocks.CANTALOUPE_STEM, block -> this.createStemDrops(block, VerdanceItems.CANTALOUPE_SEEDS));
        add(VerdanceBlocks.ATTACHED_CANTALOUPE_STEM, block -> this.createAttachedStemDrops(block, VerdanceItems.CANTALOUPE_SEEDS));
    }

    private void silkWormEggs() {
        add(VerdanceBlocks.SILKWORM_EGGS, BlockLootSubProvider::createSilkTouchOnlyTable);
    }

    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        super.generate(consumer);
    }
}
