package factorycraft.client.render;

import factorycraft.FactoryCraft;
import factorycraft.tileentity.PipeItem;
import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class RenderPipe extends TileEntitySpecialRenderer
{

    ResourceLocation texStraight, texCorner;
    IModelCustom modelStraight, modelCorner;

    public RenderPipe()
    {
        texStraight = new ResourceLocation(FactoryCraft.MODID, "textures/blocks/pipe.png");
        modelStraight = AdvancedModelLoader.loadModel(new ResourceLocation(FactoryCraft.MODID, "models/blocks/pipe.obj"));

        texCorner = new ResourceLocation(FactoryCraft.MODID, "textures/blocks/pipe_corner.png");
        modelCorner = AdvancedModelLoader.loadModel(new ResourceLocation(FactoryCraft.MODID, "models/blocks/pipe_corner.obj"));
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks)
    {
        if (tileEntity == null || !(tileEntity instanceof TileEntityPipe))
        {
            return;
        }

        TileEntityPipe tilePipe = (TileEntityPipe) tileEntity;

        bindTexture(texStraight);

        glPushMatrix();
        glTranslated(x, y, z);
        glTranslatef(0.5f, 0.5f, 0.5f);


        if (tilePipe.getDirection() == ForgeDirection.NORTH)
        {
            glRotatef(90f, 0f, 1f, 0f);
        }

        if (tilePipe.getDirection() == ForgeDirection.SOUTH)
        {
            glRotatef(90f * 3, 0f, 1f, 0f);
        }

        if (tilePipe.getDirection() == ForgeDirection.WEST)
        {
            glRotatef(90f * 2, 0f, 1f, 0f);
        }

        glScalef(1f, 1f, 1f);
        glTranslatef(-0.5f, -0.5f, -0.5f);
        glPushMatrix();
        modelStraight.renderAll();
        glPopMatrix();
        glPopMatrix();

        for (PipeItem pipeItem : tilePipe.getPipeItems())
        {
            glPushMatrix();
            EntityItem entItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, pipeItem.getStack());
            entItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            Vector3f flow = tilePipe.getFlow();
            // TODO: Update this based off the direction the pipe is facing. Now it can go in the facing direction, but then it jumps from start to end, gotta find out what direction the pipe is flowing... maybe take the fromPipe and it's x,z difference to this ones
            tileEntity.getWorldObj().setBlock(tilePipe.xCoord, tilePipe.yCoord-1, tilePipe.zCoord, Blocks.diamond_block);
            glTranslatef((float) x + (flow.getX() == 1 ? pipeItem.getCount() : flow.getX() == -1 ? -pipeItem.getCount() : flow.getX()),
                    (float) y + flow.getY() + 0.2f,
                    (float) z+ (flow.getZ() == 1 ? pipeItem.getCount() : flow.getZ() == -1 ? -pipeItem.getCount() : flow.getZ()));
            glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            glPopMatrix();
        }
    }
}
