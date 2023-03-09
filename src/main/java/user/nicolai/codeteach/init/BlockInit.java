package user.nicolai.codeteach.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import user.nicolai.codeteach.CodeTeach;
import user.nicolai.codeteach.block.TeachBlock;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CodeTeach.MODID);

    public static final RegistryObject<Block> TEACH_BLOCK = BLOCKS.register("teach_block", () -> new TeachBlock(BlockBehaviour.Properties.of(Material.STONE).friction(0.5f)));




}
