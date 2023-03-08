package user.nicolai.codeteach.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CodeTeach.MODID);

    public static final RegistryObject<BlockItem> TEACH_BLOCK_ITEM = ITEMS.register("teach_item", () -> new BlockItem(BlockInit.TEACH_BLOCK.get(), new Item.Properties()));

}
