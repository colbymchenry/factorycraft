package factorycraft.fluid;

import net.minecraftforge.fluids.Fluid;

public class FluidOil extends Fluid
{

    public static final FluidOil instance = new FluidOil();
    public static final String name = "oil";

    private FluidOil()
    {
        super(name);
    }
}