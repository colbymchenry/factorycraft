package factorycraft.block;

import factorycraft.FactoryCraft;
import factorycraft.fluid.FluidOil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockOil extends BlockFluidClassic
{

    public static final BlockOil instance = new BlockOil();
    public static final String name = "oil";

    protected IIcon stillIcon, flowingIcon;

    private BlockOil()
    {
        super(FluidOil.instance, Material.water);
        setBlockTextureName(FactoryCraft.MODID + ":" + name + "_still");
        setBlockName(name);
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(FactoryCraft.MODID + ":" + name + "_still");
        flowingIcon = register.registerIcon(FactoryCraft.MODID + ":" + name + "_flow");
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, x, y, z);
    }

}
