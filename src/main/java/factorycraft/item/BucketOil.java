package factorycraft.item;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import factorycraft.FactoryCraft;
import factorycraft.block.BlockOil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketOil extends ItemBucket
{

    public static final BucketOil instance = new BucketOil();
    public static final String name = "bucketOil";

    private BucketOil()
    {
        super(BlockOil.instance);
        setContainerItem(Items.bucket);
        setMaxStackSize(1);
        setUnlocalizedName(name);
        setTextureName(FactoryCraft.MODID + ":" + name);
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        if (event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) == BlockOil.instance)
        {
            event.world.setBlock(event.target.blockX, event.target.blockY, event.target.blockZ, Blocks.air);
            event.result = new ItemStack(BucketOil.instance);
            event.setResult(Event.Result.ALLOW);
        }
    }

}
