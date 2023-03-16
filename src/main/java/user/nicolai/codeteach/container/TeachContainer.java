package user.nicolai.codeteach.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import user.nicolai.codeteach.entity.TeachBlockEntity;
import user.nicolai.codeteach.init.ContainerInit;

public class TeachContainer extends AbstractContainerMenu{

    public final TeachBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    public TeachContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public TeachContainer(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ContainerInit.TEACH_CONTAINER.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (TeachBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;
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
