package gardensofthedead.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

// TODO cleanup a bit more maybe
public class ModSurfaceRules {

    private static final SurfaceRules.RuleSource GRAVEL = block(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource NETHERRACK = block(Blocks.NETHERRACK);
    private static final SurfaceRules.RuleSource SOUL_SAND = block(Blocks.SOUL_SAND);
    private static final SurfaceRules.RuleSource SOUL_SOIL = block(Blocks.SOUL_SOIL);
    private static final SurfaceRules.RuleSource NETHER_WART_BLOCK = block(Blocks.NETHER_WART_BLOCK);
    private static final SurfaceRules.RuleSource CRIMSON_NYLIUM = block(Blocks.CRIMSON_NYLIUM);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource aboveLava = yBlockCheck(VerticalAnchor.absolute(31), 0);

        SurfaceRules.RuleSource soulblightForest = sequence(
                ifTrue(
                        UNDER_CEILING,
                        sequence(
                                ifTrue(noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.3D), SOUL_SAND),
                                ifTrue(noiseCondition(Noises.NETHER_STATE_SELECTOR, -0.2D), SOUL_SOIL),
                                NETHERRACK
                        )
                ),
                ifTrue(
                        UNDER_FLOOR,
                        sequence(
                                gravelBeach(),
                                ifTrue(noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.3D), SOUL_SAND),
                                SOUL_SOIL
                        )
                ),
                NETHERRACK
        );

        SurfaceRules.RuleSource whistlingWoods = sequence(
                ifTrue(ON_FLOOR,
                        ifTrue(not(noiseCondition(Noises.NETHERRACK, 0.4D)),
                                ifTrue(aboveLava,
                                        sequence(
                                                ifTrue(noiseCondition(Noises.NETHER_WART, 1.17D), NETHER_WART_BLOCK),
                                                CRIMSON_NYLIUM
                                        )
                                )
                        )
                ),
                NETHERRACK
        );

        return sequence(
                ifTrue(isBiome(ModBiomes.SOULBLIGHT_FOREST), soulblightForest),
                ifTrue(isBiome(ModBiomes.WHISTLING_WOODS), whistlingWoods)
        );
    }

    private static SurfaceRules.RuleSource gravelBeach() {
        SurfaceRules.ConditionSource above30 = yStartCheck(VerticalAnchor.absolute(30), 0);
        SurfaceRules.ConditionSource below35 = not(yStartCheck(VerticalAnchor.absolute(35), 0));
        SurfaceRules.ConditionSource patchNoise = noiseCondition(Noises.PATCH, -0.012D);
        return ifTrue(patchNoise,
                ifTrue(above30, ifTrue(below35, GRAVEL))
        );
    }

    private static SurfaceRules.RuleSource block(Block block) {
        return state(block.defaultBlockState());
    }
}
