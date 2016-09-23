package factorycraft.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import factorycraft.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TileEntityPipe extends TileEntity
{

    private TileEntityPipe parentPipe;
    public ForgeDirection direction;
    private List<ItemStackPipeObject> items = Lists.newArrayList(); // size = how much flow
    private int maxItems = 10; // max flow

    @Override
    public void updateEntity()
    {
        List<TileEntityPipe> connections = getConnections();
        connections.remove(parentPipe);
        TileEntityPipe lowestLevelPipe = getLowestLevelPipe(connections);

        if (items.isEmpty())
        {
            parentPipe = null;
        }

        if (connections.isEmpty())
        {
            if (parentPipe != null)
            {
                Iterator<ItemStackPipeObject> iterator = items.iterator();
                while (iterator.hasNext())
                {
                    ItemStackPipeObject pipeObject = iterator.next();
                    pipeObject.endPoint.x = parentPipe.xCoord < xCoord ? xCoord + 1 : parentPipe.xCoord > xCoord ? xCoord - 1 : xCoord;
                    pipeObject.endPoint.y = parentPipe.yCoord - pipeObject.endPoint.y;
                    pipeObject.endPoint.z = parentPipe.zCoord < zCoord ? zCoord + 1 : parentPipe.zCoord > zCoord ? zCoord - 1 : zCoord;

                    pipeObject.increment(0.02f);

                    if (pipeObject.location >= 1f)
                    {
                        pipeObject.location = 1f;
                    }
                }
            }
            return;
        }

        Iterator<ItemStackPipeObject> iterator = items.iterator();
        while (iterator.hasNext())
        {
            ItemStackPipeObject pipeObject = iterator.next();

            if (pipeObject.endPoint.x == 0 && pipeObject.endPoint.y == 0 && pipeObject.endPoint.z == 0)
            {
                pipeObject.endPoint.x = lowestLevelPipe.xCoord;
                pipeObject.endPoint.y = lowestLevelPipe.yCoord;
                pipeObject.endPoint.z = lowestLevelPipe.zCoord;
            }

            pipeObject.increment(0.02f);

            if (pipeObject.location >= 1f)
            {

                lowestLevelPipe = (TileEntityPipe) getWorldObj().getTileEntity(pipeObject.endPoint.x, pipeObject.endPoint.y, pipeObject.endPoint.z);

                if (lowestLevelPipe.addItem(pipeObject))
                {
                    pipeObject.location = 0f;
                    iterator.remove();
                    lowestLevelPipe.parentPipe = this;

                    List<TileEntityPipe> connections1 = lowestLevelPipe.getConnections();
                    connections1.remove(lowestLevelPipe.parentPipe);
                    TileEntityPipe lowestLevelPipe1 = lowestLevelPipe.getLowestLevelPipe(connections1);

                    pipeObject.endPoint.x = lowestLevelPipe1.xCoord;
                    pipeObject.endPoint.y = lowestLevelPipe1.yCoord;
                    pipeObject.endPoint.z = lowestLevelPipe1.zCoord;
                }
            }
        }
    }

    public boolean addItem(ItemStackPipeObject pipeObject)
    {
        if (items.size() < maxItems)
        {
            items.add(pipeObject);
            return true;
        }

        return false;
    }

    public TileEntityPipe getLowestLevelPipe(List<TileEntityPipe> pipes)
    {
        if (pipes.isEmpty())
        {
            return this;
        }

        TileEntityPipe lowestLevelPipe = pipes.get(0);
        Map<TileEntityPipe, Integer> itemsToPipes = Maps.newHashMap();

        for (TileEntityPipe pipe : pipes)
        {
            int itemsGoingToPipe = 0;
            for (ItemStackPipeObject pipeObject : items)
            {
                if (pipeObject.endPoint.x == pipe.xCoord &&
                        pipeObject.endPoint.y == pipe.yCoord &&
                        pipeObject.endPoint.z == pipe.zCoord)
                {
                    itemsGoingToPipe++;
                }
            }

            itemsToPipes.put(pipe, itemsGoingToPipe);
        }

        for (TileEntityPipe pipe : itemsToPipes.keySet())
        {
            if (itemsToPipes.get(pipe) < itemsToPipes.get(lowestLevelPipe))
            {
                lowestLevelPipe = pipe;
            }
        }

        return lowestLevelPipe;
    }
    // TODO: CLEAN UP CODE

    private List<TileEntityPipe> getConnections()
    {
        TileEntity[] surroundingTiles = BlockUtil.getSurroundingTiles(this);
        List<TileEntityPipe> pipes = Lists.newArrayList();

        for (TileEntity te : surroundingTiles)
        {
            if (te instanceof TileEntityPipe)
            {
                pipes.add((TileEntityPipe) te);
            }
        }

        return pipes;
    }

    public ItemStackPipeObject[] getItems()
    {
        return items.toArray(new ItemStackPipeObject[items.size()]);
    }
}
