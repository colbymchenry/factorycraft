package factorycraft.client.render;

import factorycraft.FactoryCraft;
import factorycraft.tileentity.ItemStackPipeObject;
import factorycraft.tileentity.TileEntityPipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
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


        if (tilePipe.direction == ForgeDirection.NORTH)
        {
            glRotatef(90f, 0f, 1f, 0f);
        }

        if (tilePipe.direction == ForgeDirection.SOUTH)
        {
            glRotatef(90f * 3, 0f, 1f, 0f);
        }

        if (tilePipe.direction == ForgeDirection.WEST)
        {
            glRotatef(90f * 2, 0f, 1f, 0f);
        }

        glScalef(1f, 1f, 1f);
        glTranslatef(-0.5f, -0.5f, -0.5f);
        glPushMatrix();
        modelStraight.renderAll();
        glPopMatrix();
        glPopMatrix();

        for (ItemStackPipeObject itemStackPipeObject : tilePipe.getItems())
        {
            glPushMatrix();
            EntityItem entItem = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, itemStackPipeObject.itemStack);
            entItem.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;

            glTranslatef((float) x + (itemStackPipeObject.endPoint.x > tileEntity.xCoord ? itemStackPipeObject.location : itemStackPipeObject.endPoint.x < tileEntity.xCoord ? -itemStackPipeObject.location + 1f : 0.5f), (float) y + 0.8f,

                    (float) z + (itemStackPipeObject.endPoint.z > tileEntity.zCoord ? itemStackPipeObject.location : itemStackPipeObject.endPoint.z < tileEntity.zCoord ? -itemStackPipeObject.location + 1f : 0.5f));

            glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(entItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            glPopMatrix();
        }

    }
}
