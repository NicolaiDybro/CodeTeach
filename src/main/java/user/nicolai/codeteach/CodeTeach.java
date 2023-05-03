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

    // Define mod id in a common place for everything to reference
    public static final String MODID = "codeteach";
    // Directly reference a slf4j logger

    public CodeTeach() {


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ContainerInit.CONTAINERS.register(modEventBus);

    }




    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ContainerInit.TEACH_CONTAINER.get(), DisplayTeachScreen::new);
        }
    }
}
