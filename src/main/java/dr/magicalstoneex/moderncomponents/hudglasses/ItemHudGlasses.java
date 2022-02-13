package dr.magicalstoneex.moderncomponents.hudglasses;

import dr.magicalstoneex.moderncomponents.ModernComponents;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.HudModelContainer;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.IHudModelContainer;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.Storage;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.Model;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static org.lwjgl.opengl.GL11.GL_POLYGON;

public class ItemHudGlasses extends ItemArmor {

    @CapabilityInject(IHudModelContainer.class)
    public static Capability<IHudModelContainer> modelContainer;

    public ItemHudGlasses(ArmorMaterial materialIn) {
        super(materialIn, materialIn.ordinal(), EntityEquipmentSlot.HEAD);
        CapabilityManager.INSTANCE.register(IHudModelContainer.class, new Storage(), () -> new HudModelContainer());
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType.equals(EntityEquipmentSlot.HEAD) && entity instanceof EntityPlayer;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,  NBTTagCompound nbt) {
        return new ICapabilityProvider() {

            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == modelContainer;
            }

            private final IHudModelContainer cap = new HudModelContainer();

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                if(capability == modelContainer){
                    return (T) cap;
                }
                return null;
            }
        };
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
        super.renderHelmetOverlay(stack, player, resolution, partialTicks);
        IHudModelContainer cap = stack.<IHudModelContainer>getCapability(modelContainer, null);
        int[] color = cap.getBackgroundColor();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder drawer = tessellator.getBuffer();
        double high = resolution.getScaledHeight_double();
        double wide = resolution.getScaledWidth_double();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        drawer.begin(GL_POLYGON, POSITION_COLOR);
        drawer.pos(0.0d, 0.0d, 0.0d).color(0, 0, 0, 0).endVertex();
        drawer.pos(0.0d, high, 0.0d).color(0, 0, 0, 0).endVertex();
        drawer.pos(wide, high, 0.0d).color(0, 0, 0, 0).endVertex();
        drawer.pos(wide, 0.0d, 0.0d).color(color[0], color[1], color[2], color[3]).endVertex();
        for (Model modelToRender : cap.getModels()) {
            Vec3d oneVertex = modelToRender.getOneVertex();
            Vec3d oppositeVertex = modelToRender.getOppositeVertex();
            drawBlock(drawer, color, oneVertex, oppositeVertex);
        }
        tessellator.draw();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
    }

    private void drawBlock(BufferBuilder drawer, int[] color, Vec3d oneVertex, Vec3d oppositeVertex){

        drawer.pos(oneVertex.x, oneVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oppositeVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oneVertex.y, oneVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();

        drawer.pos(oneVertex.x, oneVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oneVertex.y, oppositeVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();

        drawer.pos(oneVertex.x, oneVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oneVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oppositeVertex.y, oneVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();

        drawer.pos(oppositeVertex.x, oneVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oneVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oneVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();

        drawer.pos(oneVertex.x, oneVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oneVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oneVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oneVertex.y, oneVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();

        drawer.pos(oneVertex.x, oppositeVertex.y, oneVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oneVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oppositeVertex.z).color(0, 0, 0, 0).endVertex();
        drawer.pos(oppositeVertex.x, oppositeVertex.y, oneVertex.z).color(color[0], color[1], color[2], color[3]).endVertex();
    }

    protected void setBackgroundColor(ItemStack stack, int r, int g, int b, int a) {
        stack.getTagCompound().getCompoundTag(ModernComponents.MODID).setIntArray("glasses_color", new int[]{r, g, b, a});

    }


}
