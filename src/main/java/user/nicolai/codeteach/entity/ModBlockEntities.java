package user.nicolai.codeteach.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;
import user.nicolai.codeteach.init.BlockInit;

public class ModBlockEntities {

    //Opret liste med alle block entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CodeTeach.MODID);

    //Opret teach block entity
    public static final RegistryObject<BlockEntityType<TeachBlockEntity>> TEACH_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("teach_block_entity", () -> BlockEntityType.Builder.of(TeachBlockEntity::new, BlockInit.TEACH_BLOCK.get()).build(null));

    //Funktion der opretter dem i spillet
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
