package factorycraft.block;


import factorycraft.tileentity.TileEntityEngine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEngine extends BlockContainer
{

    public static final BlockEngine instance = new BlockEngine();
    public static final String name = "engine";

    private BlockEngine()
    {
        super(Material.iron);
        setCreativeTab(CreativeTabs.tabBlock);
        setBlockName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityEngine();
    }
}
