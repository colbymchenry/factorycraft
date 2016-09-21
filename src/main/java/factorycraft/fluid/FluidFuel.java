package factorycraft.fluid;

import net.minecraftforge.fluids.Fluid;

public class FluidFuel extends Fluid
{

    public static final FluidFuel instance = new FluidFuel();
    public static final String name = "fuel";

    private FluidFuel()
    {
        super(name);
    }
}