package factorycraft.tileentity;

import net.minecraft.item.ItemStack;

public class PipeItem
{
    private ItemStack stack;
    private float count;

    public PipeItem(ItemStack stack, float count)
    {
        this.stack = stack;
        this.count = count;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public float getCount()
    {
        return count;
    }

    public void setCount(float count)
    {
        this.count = count;
    }

    public void setStack(ItemStack stack)
    {
        this.stack = stack;
    }
}