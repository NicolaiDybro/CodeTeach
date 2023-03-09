package user.nicolai.codeteach.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;
import user.nicolai.codeteach.init.ContainerInit;

public class TeachContainer extends AbstractContainerMenu{


    public TeachContainer(@Nullable MenuType<?> p_38851_, int p_38852_) {
        super(p_38851_, p_38852_);
    }

    public TeachContainer(int i, Inventory inventory) {
        this(i, inventory, new ItemStackHandler(27), BlockPos.ZERO, new SimpleContainerData(0));
    }

    public TeachContainer(int i, Inventory inventory, ItemStackHandler itemStackHandler, BlockPos zero, SimpleContainerData simpleContainerData) {
        super(ContainerInit.TEACH_CONTAINER.get(), i);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
