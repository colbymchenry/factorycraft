package factorycraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

}
