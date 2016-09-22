package factorycraft.tileentity;

import factorycraft.BlockUtil;
import factorycraft.EnergyProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.Map;

public class TileEntityPipe extends TileEntity
{

    public static final String name = "tileentitypipe";

    private TileEntityPipe transferFrom;
    private ForgeDirection direction;
    private boolean powered;
    private long startTime = System.currentTimeMillis();

    // TODO: Can't use stacks as it won't allow more than one of the item
    private Map<ItemStack, Float> items = new HashMap<>();

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
                    transfer();
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
                Map<ItemStack, Float> newValues = new HashMap<ItemStack, Float>();
                for (ItemStack stack : getTransferFrom().getItems().keySet())
                {
                    newValues.put(stack, getTransferFrom().getItems().get(stack) + 0.05F);

                    if (newValues.get(stack) >= 1.0F)
                    {
                        items.put(stack, 0F);
                        newValues.remove(stack);
                    }
                }

                getTransferFrom().setItems(newValues);

                transfer();
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

    public Map<ItemStack, Float> getItems()
    {
        return items;
    }

    public void setItems(Map<ItemStack, Float> items)
    {
        this.items = items;
    }

    public void transfer()
    {
        TileEntity[] surroundingTiles = BlockUtil.getSurroundingTiles(this);

        for (int i = surroundingTiles.length - 1; i > -1; i--)
        {
            if (surroundingTiles[i] != getTransferFrom() && surroundingTiles[i] instanceof TileEntityPipe)
            {
                ((TileEntityPipe) surroundingTiles[i]).setTransferFrom(this);
            }
        }
    }

}
