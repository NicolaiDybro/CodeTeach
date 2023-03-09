package user.nicolai.codeteach.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;
import user.nicolai.codeteach.container.DisplayTeachScreen;
import user.nicolai.codeteach.container.TeachContainer;

public class ContainerInit {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CodeTeach.MODID);

    public static final RegistryObject<MenuType<TeachContainer>> TEACH_CONTAINER = CONTAINERS.register("teach", ()-> new MenuType<TeachContainer>(TeachContainer::new));
    private ContainerInit() {

    }




}
