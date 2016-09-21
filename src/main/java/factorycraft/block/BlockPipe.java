package factorycraft.block;

import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPipe extends BlockContainer
{

    public static final BlockPipe instance = new BlockPipe();
    public static final String name = "pipe";

    private BlockPipe()
    {
        super(Material.iron);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int meta)
    {
        return new TileEntityPipe();
    }

    @Override
    public void onNeighborChange(final IBlockAccess world, final int x, final int y, final int z, final int tileX, final int tileY, final int tileZ)
    {
        TileEntity tileEntity = world.getTileEntity(tileX, tileY, tileZ);

        if(!(tileEntity instanceof TileEntityPipe))
        {
            return;
        }

        TileEntityPipe thisPipe = (TileEntityPipe) world.getTileEntity(x, y, z);

        TileEntityPipe[] surroundingPipes = TileEntityPipe.getSurroundingPipes(thisPipe);

        for(TileEntityPipe pipe : surroundingPipes)
        {
            if(pipe == tileEntity)
            {
                ((TileEntityPipe) tileEntity).setDirection(thisPipe.getDirection());
                break;
            }
        }

        ((TileEntityPipe) tileEntity).setTransferFrom(thisPipe);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        // TODO: Work on reversing the direction.
        System.out.println("CALLED");
        ((TileEntityPipe) world.getTileEntity(x, y, z)).setInTransit(new ItemStack(Items.blaze_rod));
        return super.onBlockActivated(world, x, y, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
    }
}
