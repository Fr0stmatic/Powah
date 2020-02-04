package owmii.powah.block.solarpanel;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import owmii.lib.block.TileBase;
import owmii.lib.util.Misc;
import owmii.lib.util.Time;
import owmii.powah.block.ITiles;
import owmii.powah.block.Tier;

import javax.annotation.Nullable;

public class SolarPanelTile extends TileBase.EnergyProvider<Tier, SolarPanelBlock> {
    private boolean canSeeSunLight;

    public SolarPanelTile(Tier variant) {
        super(ITiles.SOLAR_PANEL, variant);
    }

    public SolarPanelTile() {
        this(Tier.STARTER);
    }

    @Override
    public void readSync(CompoundNBT compound) {
        super.readSync(compound);
        this.canSeeSunLight = compound.getBoolean("CanSeeSunLight");
    }

    @Override
    public CompoundNBT writeSync(CompoundNBT compound) {
        compound.putBoolean("CanSeeSunLight", this.canSeeSunLight);
        return super.writeSync(compound);
    }

    @Override
    protected boolean postTicks(World world) {
        if (isRemote()) return false;
        if (!Time.isDay(world) && this.canSeeSunLight || this.ticks % 40L == 0L) {
            this.canSeeSunLight = Time.isDay(world) && Misc.canBlockSeeSky(world, this.pos);
            markDirtyAndSync();
        }

        boolean flag = false;

        if (!this.energy.isFull()) {
            if (this.canSeeSunLight) {
                this.energy.produce(defaultGeneration());
                flag = true;
            }
        }
        return super.postTicks(world) || flag;
    }

    public boolean canSeeSunLight() {
        return this.canSeeSunLight;
    }

    @Override
    public boolean keepEnergy() {
        return true;
    }

    @Override
    public boolean isEnergyPresent(@Nullable Direction side) {
        return Direction.DOWN.equals(side);
    }
}