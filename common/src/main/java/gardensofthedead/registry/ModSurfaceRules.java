package gardensofthedead.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

// TODO cleanup a bit more
public class ModSurfaceRules extends SurfaceRules {

    private static final RuleSource GRAVEL = block(Blocks.GRAVEL);
    private static final RuleSource NETHERRACK = block(Blocks.NETHERRACK);
    private static final RuleSource SOUL_SAND = block(Blocks.SOUL_SAND);
    private static final RuleSource SOUL_SOIL = block(Blocks.SOUL_SOIL);
    private static final RuleSource NETHER_WART_BLOCK = block(Blocks.NETHER_WART_BLOCK);
    private static final RuleSource CRIMSON_NYLIUM = block(Blocks.CRIMSON_NYLIUM);

    public static RuleSource makeRules() {
        ConditionSource aboveLava = yBlockCheck(VerticalAnchor.absolute(31), 0);

        RuleSource soulblightForest = ifTrue(isBiome(ModBiomes.SOULBLIGHT_FOREST),
                sequence(
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
                )
        );

        RuleSource whistlingWoods = ifTrue(isBiome(ModBiomes.WHISTLING_WOODS),
                sequence(
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
                )
        );

        return sequence(soulblightForest, whistlingWoods);
    }

    private static RuleSource gravelBeach() {
        ConditionSource above30 = yStartCheck(VerticalAnchor.absolute(30), 0);
        ConditionSource below35 = not(yStartCheck(VerticalAnchor.absolute(35), 0));
        ConditionSource patchNoise = noiseCondition(Noises.PATCH, -0.012D);
        return ifTrue(patchNoise,
                ifTrue(above30, ifTrue(below35, GRAVEL))
        );
    }

    private static RuleSource block(Block block) {
        return state(block.defaultBlockState());
    }
}
