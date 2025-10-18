package gardensofthedead.fabric;

import gardensofthedead.GardensOfTheDead;
import gardensofthedead.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class GardensOfTheDeadFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        GardensOfTheDead.init();
        GardensOfTheDead.addSurfaceRules();

        ModItems.addCompostables(CompostingChanceRegistry.INSTANCE::add);
    }
}
