package user.nicolai.codeteach.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;
import user.nicolai.codeteach.container.DisplayTeachScreen;
import user.nicolai.codeteach.container.TeachContainer;

public class ContainerInit {

    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CodeTeach.MODID);

    public static final RegistryObject<MenuType<TeachContainer>> TEACH_CONTAINER =
            registerMenuType(TeachContainer::new, "teach_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return CONTAINERS.register(name, () -> IForgeMenuType.create(factory));
    }


}
