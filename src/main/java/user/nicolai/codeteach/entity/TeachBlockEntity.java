package user.nicolai.codeteach.entity;

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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public TeachBlockEntity(BlockPos pos, BlockState state) { //Opret block entity baseret på dens position og state
        super(ModBlockEntities.TEACH_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int p_39284_) { return 0;}
            @Override
            public void set(int p_39285_, int p_39286_) {}
            @Override
            public int getCount() {
                return 0;
            }
        };
    }
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
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


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void removeAndUpdateBlock(BlockPos pos, Level world, BlockPos newPos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity != null) { //Sikre sig at blokken er i rækkevidde
            CompoundTag tc = tileEntity.saveWithoutMetadata(); //Gemmer data omkring blokken
            world.removeBlockEntity(pos); //Fjerner entity
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState()); //Sætter blok til luft (Fjerner den)
            world.setBlockAndUpdate(newPos, tileEntity.getBlockState()); //Sætter den nye blok
            tileEntity.load(tc); //Uploader data til den nye
            tileEntity.setChanged(); //Gemmer data
        }
    }

    private boolean stop = false;
    //Tick event der kører konstant
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TeachBlockEntity entity) {
        //Sikre sig at man har at gøre med en bog
        if (entity.itemHandler.getStackInSlot(0).getItem() == Items.WRITTEN_BOOK) {
            ItemStack item = entity.itemHandler.getStackInSlot(0);
            CompoundTag tag = item.getTag();
            ListTag listTag = (ListTag) tag.get("pages"); //Gemmer siderne i bogen
            BlockPos pos = blockPos;
            if (entity.stop) {
                return;
            }
            for (Tag page : listTag) { //Læser alle siderne i gennem
                String tekst = StringUtils.removeEnd(page.getAsString().replaceFirst("\\{\"text\":\"", ""), "\"}");
                String[] linjer = tekst.split("\\\\n");
                for (String linje : linjer) { //Læser alle linjerne i gennem
                    if (!level.isClientSide) { //Sikre sig at man er server side
                        if (linje.contains("break")) { //Smadre en blok
                            if (linje.contains("below")) {
                                level.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
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
                        if (linje.contains("move-below")) { //Flytte sig en blok
                            removeAndUpdateBlock(pos, level, pos.below());
                            pos = pos.below();
                        }
                        if (linje.contains("move-above")) {
                            removeAndUpdateBlock(pos, level, pos.above());
                            pos = pos.above();
                        }
                        if (linje.contains("move-north")) {
                            removeAndUpdateBlock(pos, level, pos.north());
                            pos = pos.north();
                        }
                        if (linje.contains("move-south")) {
                            removeAndUpdateBlock(pos, level, pos.south());
                            pos = pos.south();
                        }
                        if (linje.contains("move-west")) {
                            removeAndUpdateBlock(pos, level, pos.west());
                            pos = pos.west();
                        }
                        if (linje.contains("move-east")) {
                            removeAndUpdateBlock(pos, level, pos.east());
                            pos = pos.east();
                        }
                        if (linje.contains("pblock-above: ")) { //Placer en blok
                            BlockState state = getMat(linje.replaceFirst("pblock-above: ", ""));
                            level.setBlockAndUpdate(pos.above(), state);
                        }
                        if (linje.contains("pblock-below: ")) {
                            BlockState state = getMat(linje.replaceFirst("pblock-below: ", ""));
                            level.setBlockAndUpdate(pos.below(), state);
                        }
                        if (linje.contains("pblock-north: ")) {
                            BlockState state = getMat(linje.replaceFirst("pblock-north: ", ""));
                            level.setBlockAndUpdate(pos.north(), state);
                        }
                        if (linje.contains("pblock-south: ")) {
                            BlockState state = getMat(linje.replaceFirst("pblock-south: ", ""));
                            level.setBlockAndUpdate(pos.south(), state);
                        }
                        if (linje.contains("pblock-west: ")) {
                            BlockState state = getMat(linje.replaceFirst("pblock-west: ", ""));
                            level.setBlockAndUpdate(pos.west(), state);
                        }
                        if (linje.contains("pblock-east: ")) {
                            BlockState state = getMat(linje.replaceFirst("pblock-east: ", ""));
                            level.setBlockAndUpdate(pos.east(), state);
                        }
                        if (linje.contains("stop")) {
                            TeachBlockEntity teachBlockEntity = (TeachBlockEntity) level.getBlockEntity(pos);
                            teachBlockEntity.stop = true;
                        }
                    }
                }
            }
            //Fjerner bogen i inventory, hvis der skulle ske en fejl
            TeachBlockEntity tentity = (TeachBlockEntity) level.getBlockEntity(pos);
            tentity.itemHandler.setStackInSlot(0, item);
        }
    }

    public static BlockState getMat(String s)
    {
        s = s.toUpperCase();
        return switch (s) {
            case "AIR" -> Blocks.AIR.defaultBlockState();
            case "BARRIER" -> Blocks.BARRIER.defaultBlockState();
            case "CACTUS" -> Blocks.CACTUS.defaultBlockState();
            case "CAKE" -> Blocks.CAKE.defaultBlockState();
            case "CLAY" -> Blocks.CLAY.defaultBlockState();
            case "STONE" -> Blocks.STONE.defaultBlockState();
            case "COBBLESTONE" -> Blocks.COBBLESTONE.defaultBlockState();
            case "DIRT" -> Blocks.DIRT.defaultBlockState();
            case "FIRE" -> Blocks.FIRE.defaultBlockState();
            case "GLASS" -> Blocks.GLASS.defaultBlockState();
            case "ICE" -> Blocks.ICE.defaultBlockState();
            case "LAVA" -> Blocks.LAVA.defaultBlockState();
            case "LEAVES" -> Blocks.OAK_LEAVES.defaultBlockState();
            case "PISTON" -> Blocks.PISTON.defaultBlockState();
            case "PORTAL" -> Blocks.END_PORTAL.defaultBlockState();
            case "SAND" -> Blocks.SAND.defaultBlockState();
            case "SNOW" -> Blocks.SNOW.defaultBlockState();
            case "SPONGE" -> Blocks.SPONGE.defaultBlockState();
            case "WATER" -> Blocks.WATER.defaultBlockState();
            case "WEB" -> Blocks.COBWEB.defaultBlockState();
            case "WOOD" -> Blocks.OAK_WOOD.defaultBlockState();
            default -> null;
        };

    }



}