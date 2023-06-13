package user.nicolai.codeteach;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import user.nicolai.codeteach.container.DisplayTeachScreen;
import user.nicolai.codeteach.entity.ModBlockEntities;
import user.nicolai.codeteach.init.BlockInit;
import user.nicolai.codeteach.init.ContainerInit;
import user.nicolai.codeteach.init.ItemInit;
@Mod(CodeTeach.MODID)
public class CodeTeach {

    //Definer MODID baseret på, hvad mod'en hedder
    public static final String MODID = "codeteach";

    public CodeTeach() {

        //Får fat i event manager
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(modEventBus); //Register alle bloks til spillet
        ItemInit.ITEMS.register(modEventBus); //Register alle items til spillet

        ModBlockEntities.register(modEventBus); //Register alle blockentities til spillet
        ContainerInit.CONTAINERS.register(modEventBus); //Register alle containers til spillet

    }

    //Opretter skærmen, så snart man kommer ind på spillet
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ContainerInit.TEACH_CONTAINER.get(), DisplayTeachScreen::new);
        }
    }
}
