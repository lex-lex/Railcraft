/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.plugins.forge;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class WorldPlugin {

    public static IBlockState getBlockState(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    public static Block getBlock(IBlockAccess world, BlockPos pos) {
        return getBlockState(world, pos).getBlock();
    }

    @Nullable
    public static TileEntity getBlockTile(IBlockAccess world, BlockPos pos) {
        if (pos.getY() < 0)
            return null;
        return world.getTileEntity(pos);
    }

    public static Optional<TileEntity> getTileEntity(IBlockAccess world, BlockPos pos) {
        return Optional.ofNullable(getBlockTile(world, pos));
    }

    public static <T extends TileEntity> Optional<T> getTileEntity(IBlockAccess world, BlockPos pos, Class<T> tileClass) {
        TileEntity tileEntity = getBlockTile(world, pos);
        if (tileClass.isInstance(tileEntity))
            return Optional.of(tileClass.cast(tileEntity));
        return Optional.empty();
    }

    public static Material getBlockMaterial(IBlockAccess world, BlockPos pos) {
        return getBlockState(world, pos).getMaterial();
    }

    public static boolean isBlockLoaded(World world, BlockPos pos) {
        return world.isBlockLoaded(pos);
    }

    public static boolean isAreaLoaded(World world, BlockPos pos1, BlockPos pos2) {
        return world.isAreaLoaded(pos1, pos2);
    }

    public static boolean isBlockAir(IBlockAccess world, BlockPos pos) {
        return world.isAirBlock(pos);
    }

    public static boolean isBlockAir(IBlockAccess world, BlockPos pos, IBlockState state) {
        return state.getBlock().isAir(state, world, pos);
    }

    public static boolean isBlockAt(IBlockAccess world, BlockPos pos, @Nullable Block block) {
        return block != null && block == getBlock(world, pos);
    }

    public static boolean setBlockState(World world, BlockPos pos, IBlockState blockState) {
        return world.setBlockState(pos, blockState);
    }

    public static boolean setBlockState(World world, BlockPos pos, IBlockState blockState, int update) {
        return world.setBlockState(pos, blockState, update);
    }

    public static boolean setBlockToAir(World world, BlockPos pos) {
        return world.setBlockToAir(pos);
    }

    public static boolean destroyBlock(World world, BlockPos pos, boolean dropBlock) {
        return world.destroyBlock(pos, dropBlock);
    }

    public static void notifyBlocksOfNeighborChange(World world, BlockPos pos, Block block) {
        if (world != null && block != null)
            world.notifyNeighborsOfStateChange(pos, block);
    }

    public static void notifyBlocksOfNeighborChangeOnSide(World world, BlockPos pos, Block block, EnumFacing side) {
        pos = pos.offset(side);
        world.notifyNeighborsOfStateChange(pos, block);
    }

    public static void markBlockForUpdate(World world, BlockPos pos) {
        IBlockState state = getBlockState(world, pos);
        markBlockForUpdate(world, pos, state);
    }

    public static void markBlockForUpdate(World world, BlockPos pos, IBlockState state) {
        markBlockForUpdate(world, pos, state, state);
    }

    public static void markBlockForUpdate(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        world.notifyBlockUpdate(pos, oldState, newState, 3);
    }

    public static void addBlockEvent(World world, BlockPos pos, Block block, int key, int value) {
        if (world != null && block != null)
            world.addBlockEvent(pos, block, key, value);
    }

    @Nullable
    public static BlockPos findBlock(World world, BlockPos pos, int distance, Predicate<IBlockState> matcher) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int yy = y - distance; yy < y + distance; yy++) {
            for (int xx = x - distance; xx < x + distance; xx++) {
                for (int zz = z - distance; zz < z + distance; zz++) {
                    BlockPos test = new BlockPos(xx, yy, zz);
                    if (matcher.test(getBlockState(world, test)))
                        return test;
                }
            }
        }
        return null;
    }

}
