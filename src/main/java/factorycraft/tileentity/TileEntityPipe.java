package factorycraft.tileentity;

import factorycraft.BlockUtil;
import factorycraft.EnergyProvider;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityPipe extends TileEntity
{

    public static final String name = "tileentitypipe";

    private TileEntityPipe transferFrom;
    private ForgeDirection direction;
    private boolean powered;
    private long startTime = System.currentTimeMillis();

    // TODO: Can't use stacks as it won't allow more than one of the item
    private List<PipeItem> pipeItems = new ArrayList<>();

    @Override
    public void readFromNBT(final NBTTagCompound tag)
    {
        super.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(final NBTTagCompound tag)
    {
        super.writeToNBT(tag);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (((System.currentTimeMillis() - startTime) / 1000) % 1 == 0)
        {
        } else
        {
            return;
        }

        // update based off engine speed
        if (getTransferFrom() == null)
        {
            // find engine and update power status
            TileEntity[] surroundingTiles = BlockUtil.getSurroundingTiles(this);

            for (int i = surroundingTiles.length - 1; i > -1; i--)
            {
                if (surroundingTiles[i] instanceof EnergyProvider)
                {
                    setPowered(true);
                    getPipesToTransferTo();
                    return;
                }
            }

            // set powered to false if there is no transferFrom pipe
            setPowered(false);
        }


        if (getTransferFrom() != null)
        {
            // if transfer from isn't powered, it's time to turn off all power and remove the transfer from pipe
            if (!getTransferFrom().isPowered())
            {
                setTransferFrom(null);
                setPowered(false);
                return;
            }

            setPowered(getTransferFrom().isPowered());

            // if it is powered transfer the product
            if (isPowered())
            {
                // TODO: find a more elegant way to do this, this mod needs to be very optimized and clean and simple

                List<TileEntityPipe> transferTo = getPipesToTransferTo();


                // TODO: Fix odd overhang at junction
                List<PipeItem> toRemove = new ArrayList<>();
                for (PipeItem pipeItem : pipeItems)
                {
                    pipeItem.setCount(pipeItem.getCount() + 0.05f > 1f ? 1f : pipeItem.getCount() + 0.05f);
                    if(pipeItem.getCount() >= 1f)
                    {
                        toRemove.add(pipeItem);
                        pipeItem.setCount(0f);
                        if(!transferTo.isEmpty())
                        {
                            transferTo.get(0).getPipeItems().add(pipeItem);
                        }
                    }
                }

                pipeItems.removeAll(toRemove);

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

    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    public ForgeDirection getDirection()
    {
        return direction;
    }

    public boolean isPowered()
    {
        return powered;
    }

    public void setPowered(boolean powered)
    {
        this.powered = powered;
    }

    public TileEntityPipe getTransferFrom()
    {
        return transferFrom;
    }

    public void setTransferFrom(TileEntityPipe transferFrom)
    {
        this.transferFrom = transferFrom;
    }

    public List<PipeItem> getPipeItems()
    {
        return pipeItems;
    }

    public List<TileEntityPipe> getPipesToTransferTo()
    {
        TileEntity[] surroundingTiles = BlockUtil.getSurroundingTiles(this);
        List<TileEntityPipe> transferTo = new ArrayList<>();

        for (int i = surroundingTiles.length - 1; i > -1; i--)
        {
            if (surroundingTiles[i] != getTransferFrom() && surroundingTiles[i] instanceof TileEntityPipe)
            {
                ((TileEntityPipe) surroundingTiles[i]).setTransferFrom(this);
                transferTo.add((TileEntityPipe) surroundingTiles[i]);
            }
        }

        return transferTo;
    }

    public Vector3f getFlow()
    {
        if (getTransferFrom() != null)
        {
            switch (getDirection())
            {
                case SOUTH:
                {
                    return new Vector3f(getTransferFrom().xCoord > xCoord ? -1 : 1, 0.5F, 0.5F);
                }
                case WEST:
                {
                    return new Vector3f(0.5F, 0.5F, getTransferFrom().zCoord > zCoord ? -1 : 1);
                }
            }
        }

        return new Vector3f(0.5f, 0.5f, 0.5f);
    }
}
