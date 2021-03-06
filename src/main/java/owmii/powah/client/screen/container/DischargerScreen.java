package owmii.powah.client.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import owmii.lib.client.screen.container.AbstractEnergyScreen;
import owmii.lib.logistics.energy.Energy;
import owmii.lib.util.Util;
import owmii.powah.block.discharger.EnergyDischargerTile;
import owmii.powah.client.screen.Textures;
import owmii.powah.inventory.DischargerContainer;

public class DischargerScreen extends AbstractEnergyScreen<EnergyDischargerTile, DischargerContainer> {
    public DischargerScreen(DischargerContainer container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title, Textures.DISCHARGER);
    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        super.drawBackground(matrix, partialTicks, mouseX, mouseY);
        Textures.DISCHARGER_GAUGE.drawScalableW(matrix, this.te.getEnergy().subSized(), this.guiLeft + 6, this.guiTop + 6);
    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        int a = (int) (255.0D * 1.0D * 0.4D) << 24;
        Energy e = this.te.getEnergy();
        String s = Util.addCommas(e.getStored()) + "/" + Util.numFormat(e.getCapacity()) + " FE";
        this.font.drawString(matrix, s, 12, 13.0F, a);
        this.font.drawString(matrix, Util.numFormat(e.getMaxExtract()) + " FE/t", 12, 27.0F, a);
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }
}
