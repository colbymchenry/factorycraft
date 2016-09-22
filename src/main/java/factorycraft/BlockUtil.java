package factorycraft;

import net.minecraft.tileentity.TileEntity;

public class BlockUtil
{

    public static TileEntity[] getSurroundingTiles(TileEntity tileEntity)
    {
        int left = tileEntity.xCoord - 1;
        int right = tileEntity.xCoord + 1;
        int forward = tileEntity.zCoord + 1;
        int backward = tileEntity.zCoord - 1;
        int top = tileEntity.yCoord + 1;
        int bottom = tileEntity.yCoord - 1;

        TileEntity[] adjacentTiles = new TileEntity[6];

        int count = 0;
        if (tileEntity.getWorldObj().getTileEntity(left, tileEntity.yCoord, tileEntity.zCoord) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(left, tileEntity.yCoord, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getTileEntity(right, tileEntity.yCoord, tileEntity.zCoord) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(right, tileEntity.yCoord, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, forward) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, forward);
        }

        if (tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, backward) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, backward);
        }

        if (tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, top, tileEntity.zCoord) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, top, tileEntity.zCoord);
        }

        if (tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, bottom, tileEntity.zCoord) != null)
        {
            adjacentTiles[count++] = tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, bottom, tileEntity.zCoord);
        }

        TileEntity[] toReturn = new TileEntity[count];
        for (int i = 0; i < count; i++)
        {
            toReturn[i] = adjacentTiles[i];
        }

        return toReturn;
    }


}
