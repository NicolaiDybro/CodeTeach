package user.nicolai.codeteach.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.nicolai.codeteach.container.TeachContainer;

public class TeachBlockEntity extends BlockEntity implements MenuProvider {
    private final ContainerData data;
    public int counter = 0;
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public TeachBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TEACH_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int p_39284_) {
                return 0;
            }

            @Override
            public void set(int p_39285_, int p_39286_) {

            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Teach Block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TeachContainer(id, inventory, this, this.data);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public TeachBlockEntity getEntity() {
        return getEntity();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TeachBlockEntity entity) {
        if (entity.itemHandler.getStackInSlot(0).getItem() == Items.WRITTEN_BOOK) {
            ItemStack item = entity.itemHandler.getStackInSlot(0);
            CompoundTag tag = item.getTag();
            ListTag listTag = (ListTag) tag.get("pages");
            BlockPos pos = blockPos;

            for (Tag page : listTag) {
                String tekst = StringUtils.removeEnd(page.getAsString().replaceFirst("\\{\"text\":\"", ""), "\"}");
                String[] linjer = tekst.split("\\\\n");

                for (String linje : linjer) {
                    if (linje.contains("break")) {
                        if (linje.contains("above")) {
                            level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                        }
                        if (linje.contains("above")) {
                            level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
                        }
                        if (linje.contains("north")) {
                            level.setBlockAndUpdate(pos.north(), Blocks.AIR.defaultBlockState());
                        }
                        if (linje.contains("south")) {
                            level.setBlockAndUpdate(pos.south(), Blocks.AIR.defaultBlockState());
                        }
                        if (linje.contains("west")) {
                            level.setBlockAndUpdate(pos.west(), Blocks.AIR.defaultBlockState());
                        }
                        if (linje.contains("east")) {
                            level.setBlockAndUpdate(pos.east(), Blocks.AIR.defaultBlockState());
                        }
                    }
                    if (linje.contains("move-north")) {
                        level.removeBlock(entity.getBlockPos(), true);
                        level.setBlockAndUpdate(pos.north(), entity.getBlockState());
                        TeachBlockEntity teachBlockEntity = (TeachBlockEntity) level.getBlockEntity(pos.north());
                        teachBlockEntity.itemHandler.setStackInSlot(0, item);
                        pos = pos.north();
                    }
                    if (linje.contains("move-south")) {
                        level.removeBlock(entity.getBlockPos(), true);
                        level.setBlockAndUpdate(pos.south(), entity.getBlockState());
                        TeachBlockEntity teachBlockEntity = (TeachBlockEntity) level.getBlockEntity(pos.south());
                        teachBlockEntity.itemHandler.setStackInSlot(0, item);
                        pos = pos.south();

                    }
                    if (linje.contains("move-west")) {
                        level.removeBlock(entity.getBlockPos(), true);
                        level.setBlockAndUpdate(pos.west(), entity.getBlockState());
                        TeachBlockEntity teachBlockEntity = (TeachBlockEntity) level.getBlockEntity(pos.west());
                        teachBlockEntity.itemHandler.setStackInSlot(0, item);
                        pos = pos.west();
                    }
                    if (linje.contains("move-east")) {
                        level.removeBlock(entity.getBlockPos(), true);
                        level.setBlockAndUpdate(pos.east(), entity.getBlockState());
                        TeachBlockEntity teachBlockEntity = (TeachBlockEntity) level.getBlockEntity(pos.east());
                        teachBlockEntity.itemHandler.setStackInSlot(0, item);
                        pos = pos.east();
                    }
                    level.updateNeighborsAt(pos, blockState.getBlock());
                }

            }
        }
    }

}