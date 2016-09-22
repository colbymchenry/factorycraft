package factorycraft.client.render;

import factorycraft.FactoryCraft;
import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;

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

        for(ItemStack stack : tilePipe.getItems().keySet())
        {
            glPushMatrix();
            EntityItem entItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, stack);
            entItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            // TODO: Update this based off the direction the pipe is facing. Now it can go in the facing direction, but then it jumps from start to end, gotta find out what direction the pipe is flowing... maybe take the fromPipe and it's x,z difference to this ones
            if(tilePipe.getDirection() == ForgeDirection.SOUTH)
            {
                glTranslatef((float) x + tilePipe.getItems().get(stack), (float) y + 0.8F, (float) z + 0.5F);
            } else if (tilePipe.getDirection() == ForgeDirection.EAST)
            {
                glTranslatef((float) x + 0.5F, (float) y + 0.8F, (float) z - tilePipe.getItems().get(stack));
            } else if (tilePipe.getDirection() == ForgeDirection.NORTH)
            {
                glTranslatef((float) x - tilePipe.getItems().get(stack), (float) y + 0.8F, (float) z + 0.5F);
            } else if (tilePipe.getDirection() == ForgeDirection.WEST)
            {
                glTranslatef((float) x + 0.5F, (float) y + 0.8F, (float) z + tilePipe.getItems().get(stack));
            }
            glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            glPopMatrix();
        }
    }
}
