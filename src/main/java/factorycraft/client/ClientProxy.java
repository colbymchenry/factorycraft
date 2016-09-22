package factorycraft.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import factorycraft.CommonProxy;
import factorycraft.client.render.RenderPipe;
import factorycraft.tileentity.TileEntityPipe;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(final FMLPreInitializationEvent event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipe.class, new RenderPipe());
    }

    @Override
    public void init(final FMLInitializationEvent event)
    {

    }
}
