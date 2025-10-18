package gardensofthedead.neoforge;

import gardensofthedead.GardensOfTheDead;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(GardensOfTheDead.MOD_ID)
public class GardensOfTheDeadNeoForge {

    public GardensOfTheDeadNeoForge(IEventBus modBus) {
        GardensOfTheDead.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            new GardensOfTheDeadNeoForgeClient(modBus);
        }

        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(GardensOfTheDead::addSurfaceRules);
    }
}
