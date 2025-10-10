package gardensofthedead.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRules {

    private static final SurfaceRules.RuleSource GRAVEL = block(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource NETHERRACK = block(Blocks.NETHERRACK);
    private static final SurfaceRules.RuleSource SOUL_SAND = block(Blocks.SOUL_SAND);
    private static final SurfaceRules.RuleSource SOUL_SOIL = block(Blocks.SOUL_SOIL);
    private static final SurfaceRules.RuleSource NETHER_WART_BLOCK = block(Blocks.NETHER_WART_BLOCK);
    private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = block(Blocks.CRIMSON_NYLIUM);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource aboveOrAtLava = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
        SurfaceRules.ConditionSource above30 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
        SurfaceRules.ConditionSource below35 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
        SurfaceRules.ConditionSource patchNoise = SurfaceRules.noiseCondition(Noises.PATCH, -0.012D);
        SurfaceRules.ConditionSource netherrackNoise = SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.4D);
        SurfaceRules.RuleSource gravelBeach = SurfaceRules.ifTrue(patchNoise, SurfaceRules.ifTrue(above30, SurfaceRules.ifTrue(below35, GRAVEL)));
        SurfaceRules.ConditionSource soulBlightNetherrackNoise = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, -0.2D);
        SurfaceRules.ConditionSource soulBlightSoulSandNoise = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.3D);
        SurfaceRules.ConditionSource netherWartNoise = SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17D);

        // TODO cleanup this a bit more
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SOULBLIGHT_FOREST),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.UNDER_CEILING,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(soulBlightSoulSandNoise, SOUL_SAND),
                                                SurfaceRules.ifTrue(soulBlightNetherrackNoise, SOUL_SOIL),
                                                NETHERRACK
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.UNDER_FLOOR,
                                        SurfaceRules.sequence(
                                                gravelBeach,
                                                SurfaceRules.ifTrue(soulBlightSoulSandNoise, SOUL_SAND),
                                                SOUL_SOIL
                                        )
                                ),
                                NETHERRACK
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.WHISTLING_WOODS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                        SurfaceRules.ifTrue(SurfaceRules.not(netherrackNoise),
                                                SurfaceRules.ifTrue(aboveOrAtLava,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(netherWartNoise, NETHER_WART_BLOCK),
                                                                CRIMSON_NYLIUM
                                                        )
                                                )
                                        )
                                ),
                                NETHERRACK
                        )
                )
        );
    }

    private static SurfaceRules.RuleSource block(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
