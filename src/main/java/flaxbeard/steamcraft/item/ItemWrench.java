package flaxbeard.steamcraft.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import flaxbeard.steamcraft.api.IWrenchable;
import flaxbeard.steamcraft.tile.TileEntitySteamPipe;

public class ItemWrench extends Item {
	
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xO, float yO, float zO)
    {
    	if (!world.isRemote) {
			if (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) instanceof IWrenchable) {
				boolean result = ((IWrenchable)world.getBlock(x, y, z)).onWrench(stack, player, world, x, y, z, side, xO, yO, zO);
				if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IWrenchable) {
					((IWrenchable)world.getTileEntity(x, y, z)).onWrench(stack, player, world, x, y, z, side, xO, yO, zO);

				}
				if (result == true) world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, "steamcraft:wrench", 2.0F, 0.9F);
				return result;
			}
			else if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IWrenchable) {
				boolean result = ((IWrenchable)world.getTileEntity(x, y, z)).onWrench(stack, player, world, x, y, z, side, xO, yO, zO);
				if (result == true && (!player.isSneaking() || !(world.getTileEntity(x, y, z) instanceof TileEntitySteamPipe))) world.playSoundEffect(x+0.5F, y+0.5F, z+0.5F, "steamcraft:wrench", 2.0F, 0.9F);
				return result;
			}
    	}
		return false;
    }

    
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xO, float yO, float zO)
    {
		return false;
    }
}
