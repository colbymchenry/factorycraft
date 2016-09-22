package factorycraft.block;

import factorycraft.tileentity.PipeItem;
import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPipe extends BlockContainer
{

    public static final BlockPipe instance = new BlockPipe();
    public static final String name = "pipe";

    private BlockPipe()
    {
        super(Material.iron);
        setCreativeTab(CreativeTabs.tabBlock);
        setBlockName(name);
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int meta)
    {
        return new TileEntityPipe();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemInHand)
    {
        if (world.getTileEntity(x, y, z) != null)
        {
            int rot = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            ((TileEntityPipe) world.getTileEntity(x, y, z)).setDirection(rot == 0 ? ForgeDirection.SOUTH : rot == 1 ? ForgeDirection.WEST : rot == 2 ? ForgeDirection.SOUTH : ForgeDirection.WEST);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if(player.getHeldItem() != null)
        {
            TileEntityPipe tileEntityPipe = (TileEntityPipe) world.getTileEntity(x, y, z);
            tileEntityPipe.getPipeItems().add(new PipeItem(player.getHeldItem(), 0f));
        }

        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
