package user.nicolai.codeteach.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;

public class ItemInit {

    //Opret liste med alle items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CodeTeach.MODID);

    //Opret den nye item
    public static final RegistryObject<BlockItem> TEACH_BLOCK_ITEM = ITEMS.register("teach_item", () -> new BlockItem(BlockInit.TEACH_BLOCK.get(), new Item.Properties()));
}
