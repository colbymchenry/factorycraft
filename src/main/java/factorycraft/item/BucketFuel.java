package factorycraft.item;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import factorycraft.FactoryCraft;
import factorycraft.block.BlockFuel;
import factorycraft.block.BlockOil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketFuel extends ItemBucket
{

    public static final BucketFuel instance = new BucketFuel();
    public static final String name = "bucketFuel";

    private BucketFuel()
    {
        super(BlockFuel.instance);
        setContainerItem(Items.bucket);
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setTextureName(FactoryCraft.MODID + ":" + name);
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        if (event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) == BlockFuel.instance)
        {
            event.world.setBlock(event.target.blockX, event.target.blockY, event.target.blockZ, Blocks.air);
            event.result = new ItemStack(BucketFuel.instance);
            event.setResult(Event.Result.ALLOW);
        }
    }

}
