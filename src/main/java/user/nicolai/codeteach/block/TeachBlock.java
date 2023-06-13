package user.nicolai.codeteach.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import user.nicolai.codeteach.entity.ModBlockEntities;
import user.nicolai.codeteach.entity.TeachBlockEntity;

public class TeachBlock extends BaseEntityBlock {


    //TeachBlock constructor
    public TeachBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    //Bruger funktionen fra TeachBlockEntity til at smide dens inventar, hvis den bliver smadret
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TeachBlockEntity) {
                ((TeachBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    //Åbner menuen, hvis man klikker på den
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player p, InteractionHand hand, BlockHitResult result) {
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            if (!level.isClientSide()) { //Sikre sig man ikke er på client siden
                BlockEntity entity = p.getLevel().getBlockEntity(pos);
                if (entity instanceof TeachBlockEntity) {
                    NetworkHooks.openScreen((ServerPlayer) p, (TeachBlockEntity) entity, pos);
                } else {
                    throw new IllegalStateException("FEJL");
                }
            }
        }
        return super.use(state, level, pos, p, hand, result);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeachBlockEntity(pos, state);
    }

    //Opretter tick event
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.TEACH_BLOCK_ENTITY.get(), TeachBlockEntity::tick);
    }


}
