package factorycraft.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEngine extends Block
{

    public static final BlockEngine instance = new BlockEngine();

    private BlockEngine()
    {
        super(Material.iron);
    }
}
