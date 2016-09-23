package factorycraft.tileentity;

import factorycraft.Vectori;
import net.minecraft.item.ItemStack;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

public class ItemStackPipeObject
{

    public Vectori endPoint;
    public ItemStack itemStack;
    public float location;

    public ItemStackPipeObject(ItemStack stack, float location)
    {
        this.itemStack = stack;
        this.location = location;
        this.endPoint = new Vectori(0, 0, 0);
    }

    public float increment(float factor)
    {
        location += factor;
        if (location > 1)
        {
            location = 1;
        }
        return location;
    }


}
