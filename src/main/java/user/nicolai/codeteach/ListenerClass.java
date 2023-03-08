package user.nicolai.codeteach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ListenerClass {
    @SubscribeEvent
    public void blockBreak(final BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        if (block.equals(Blocks.DIRT)) {
            Player p = event.getPlayer();
            p.sendSystemMessage(Component.literal("test"));
        }
    }

    @SubscribeEvent
    public void bookCLose(PlayerContainerEvent.Close event) {
        Player p = event.getEntity();

    }
}
