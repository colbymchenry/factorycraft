package factorycraft.tileentity;

import factorycraft.block.BlockPipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.Sys;

public class TileEntityPipe extends TileEntity
{

    public static final String name = "tileentitypipe";

    private TileEntityPipe transferFrom;
    private ForgeDirection direction;

    private int testCount = 0;

    private ItemStack inTransit;

    @Override
    public void readFromNBT(final NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        String[] transferFromTag = tag.getString("transferFrom").split(",");
        transferFrom = (TileEntityPipe) getWorldObj().getTileEntity(Integer.valueOf(transferFromTag[0]),
                Integer.valueOf(transferFromTag[1]), Integer.valueOf(transferFromTag[2]));
    }

    @Override
    public void writeToNBT(final NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        if (transferFrom != null)
        {
            tag.setString("transferFrom", transferFrom.xCoord + "," + transferFrom.yCoord + "," + transferFrom.zCoord);
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        testCount = testCount + 1 > 51 ? 0 : testCount + 1;

        if (testCount == 50)
        {
            if (getSurroundingPipes(this).length > 0)
            {
                boolean foundPipe = false;
                for (TileEntityPipe pipe : getSurroundingPipes(this))
                {
                    if (pipe != transferFrom)
                    {
                        getWorldObj().setBlock(pipe.xCoord, pipe.yCoord + 1, zCoord, Blocks.leaves);
                        if(inTransit != null)
                        {
                            pipe.setInTransit(inTransit);
                            inTransit = null;
                            foundPipe = true;
                        }
                        break;
                    }
                }

                if (!foundPipe && inTransit != null)
                {
                    getWorldObj().spawnEntityInWorld(new EntityItem(getWorldObj(), xCoord, yCoord + 1, zCoord, inTransit));
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return super.getDescriptionPacket();
    }

    @Override
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
    }

    public void setInTransit(ItemStack inTransit)
    {
        this.inTransit = inTransit;
    }

    public ItemStack getInTransit()
    {
        return inTransit;
    }

    public TileEntityPipe getTransferFrom()
    {
        return transferFrom;
    }

    public void setTransferFrom(TileEntityPipe transferFrom)
    {
        this.transferFrom = transferFrom;
    }

    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    public ForgeDirection getDirection()
    {
        return direction;
    }

    public static TileEntityPipe[] getSurroundingPipes(TileEntityPipe tileEntity)
    {
        int left = tileEntity.xCoord - 1;
        int right = tileEntity.xCoord + 1;
        int forward = tileEntity.zCoord + 1;
        int backward = tileEntity.zCoord - 1;
        int top = tileEntity.yCoord + 1;
        int bottom = tileEntity.yCoord - 1;

        TileEntityPipe[] pipes = new TileEntityPipe[6];

        int count = 0;
        if (tileEntity.getWorldObj().getBlock(left, tileEntity.yCoord, tileEntity.zCoord) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(left, tileEntity.yCoord, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getBlock(right, tileEntity.yCoord, tileEntity.zCoord) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(right, tileEntity.yCoord, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, forward) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, forward);
        }

        if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, backward) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, backward);
        }

        if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord, top, tileEntity.zCoord) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, top, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getBlock(tileEntity.xCoord, bottom, tileEntity.zCoord) == BlockPipe.instance)
        {
            pipes[count++] = (TileEntityPipe) tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, bottom, tileEntity.zCoord);
        }

        TileEntityPipe[] toReturn = new TileEntityPipe[count];
        for (int i = 0; i < count; i++)
        {
            toReturn[i] = pipes[i];
        }

        return toReturn;
    }
}
