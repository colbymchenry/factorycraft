package factorycraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import factorycraft.block.BlockEngine;
import factorycraft.block.BlockFuel;
import factorycraft.block.BlockOil;
import factorycraft.block.BlockPipe;
import factorycraft.fluid.FluidFuel;
import factorycraft.fluid.FluidOil;
import factorycraft.item.BucketFuel;
import factorycraft.item.BucketOil;
import factorycraft.tileentity.TileEntityEngine;
import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

@Mod(modid = FactoryCraft.MODID, version = FactoryCraft.VERSION, name = FactoryCraft.NAME)
public final class FactoryCraft
{

    public static final String MODID = "factorycraft";
    public static final String VERSION = "1.0";
    public static final String NAME = "FactoryCraft";

    @Mod.Instance(FactoryCraft.MODID)
    public static FactoryCraft instance;

    @SidedProxy(serverSide = "factorycraft.CommonProxy", clientSide = "factorycraft.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

        // register oil
        FluidRegistry.registerFluid(FluidOil.instance);
        GameRegistry.registerBlock(BlockOil.instance, BlockOil.name);
        GameRegistry.registerItem(BucketOil.instance, BucketOil.name);
        FluidContainerRegistry.registerFluidContainer(FluidOil.instance, new ItemStack(BucketOil.instance), new ItemStack(Items.bucket));
        MinecraftForge.EVENT_BUS.register(BucketOil.instance);

        // register fuel
        FluidRegistry.registerFluid(FluidFuel.instance);
        GameRegistry.registerBlock(BlockFuel.instance, BlockFuel.name);
        GameRegistry.registerItem(BucketFuel.instance, BucketFuel.name);
        FluidContainerRegistry.registerFluidContainer(FluidFuel.instance, new ItemStack(BucketFuel.instance), new ItemStack(Items.bucket));
        MinecraftForge.EVENT_BUS.register(BucketFuel.instance);

        // register pipe
        GameRegistry.registerTileEntity(TileEntityPipe.class, "tileentitypipe");
        GameRegistry.registerBlock(BlockPipe.instance, BlockPipe.name);

        // register engine
        GameRegistry.registerTileEntity(TileEntityEngine.class, TileEntityEngine.name);
        GameRegistry.registerBlock(BlockEngine.instance, BlockEngine.name);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

}
