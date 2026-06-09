package net.lumi.lidarmod.item.custom;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LidarItem extends Item {
    public LidarItem(Settings settings) {
        super(settings);
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

                if (valuableBlock(blockState)) {
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

    boolean valuableBlock(BlockState blockState) {
        return (blockState.isOf(Blocks.IRON_ORE) || blockState.isOf(Blocks.COAL_ORE));
    }

    // Tooltips
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.lidarmod.lidar.tooltip1"));
        tooltip.add(Text.translatable("tooltip.lidarmod.lidar.tooltip2"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
