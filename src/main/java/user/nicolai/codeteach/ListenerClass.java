package user.nicolai.codeteach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import user.nicolai.codeteach.container.TeachContainer;
import user.nicolai.codeteach.entity.ModBlockEntities;
import user.nicolai.codeteach.entity.TeachBlockEntity;

import static user.nicolai.codeteach.container.TeachContainer.teachContainer;
import static user.nicolai.codeteach.init.BlockInit.TEACH_BLOCK;

public class ListenerClass {



    @SubscribeEvent
    public void bookClose(PlayerContainerEvent.Close event) {
        Player p = event.getEntity();
        Level level = event.getEntity().getLevel();
            if (event.getContainer() instanceof TeachContainer) {
                TeachBlockEntity blockEntity = teachContainer.blockEntity;
                if (level.isClientSide()){
                    p.sendSystemMessage(Component.literal(blockEntity.toString()));
                }
            }
        }
        /*

        event.getContainer().getSlot(0).getItem()
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
    */

}
