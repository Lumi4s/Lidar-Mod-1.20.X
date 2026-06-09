package net.lumi.lidarmod.item.custom;

import net.lumi.lidarmod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LidarItem extends Item {
    public LidarItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult target = user.raycast(100, 5, true);
        HitResult.Type type = target.getType();
        Vec3d pos = target.getPos();
        BlockPos blockPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

        user.sendMessage(Text.literal(String.valueOf(type)
                + ": " + String.valueOf(pos)
                + ": " + world.getBlockState(blockPos).getBlock().asItem().getName().getString()
        ));

        return super.use(world, user, hand);
    }

    // Used when Right-clicked on block
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState blockState = context.getWorld().getBlockState(positionClicked.down(i));

                if (isDetectableBlock(blockState)) {
                    outputValuableCoordinates(positionClicked.down(i), player, blockState.getBlock());

                    foundBlock = true;

                    break;
                }
            }

            if (!foundBlock) {
                player.sendMessage(Text.literal("No Valuables Found!"));
            }
        }

        return ActionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), false);
    }

    boolean isDetectableBlock(BlockState blockState) {
        return !blockState.isIn(ModTags.Blocks.LIDAR_NOT_DETECTABLE_BLOCKS);
    }

    // Tooltips
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.lidarmod.lidar.tooltip1"));
        tooltip.add(Text.translatable("tooltip.lidarmod.lidar.tooltip2"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
