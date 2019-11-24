package zeroneye.powah.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import zeroneye.lib.inventory.slot.SlotBase;
import zeroneye.powah.block.transmitter.PlayerTransmitterTile;

import javax.annotation.Nullable;

public class PlayerTransmitterContainer extends PowahContainer<PlayerTransmitterTile> {
    public final int slots;

    public PlayerTransmitterContainer(@Nullable ContainerType<?> containerType, int id, PlayerInventory playerInventory, PacketBuffer buffer, int slots) {
        super(containerType, id, playerInventory, buffer);
        this.slots = slots;
        addContainer(playerInventory, getInv());
    }

    public PlayerTransmitterContainer(@Nullable ContainerType<?> containerType, int id, PlayerInventory playerInventory, PlayerTransmitterTile tile, int slots) {
        super(containerType, id, playerInventory, tile);
        this.slots = slots;
        addContainer(playerInventory, tile);
    }

    public static PlayerTransmitterContainer create(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        return new PlayerTransmitterContainer(IContainers.PLAYER_TRANSMITTER, i, playerInventory, buffer, 1);
    }

    public static PlayerTransmitterContainer createDim(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        return new PlayerTransmitterContainer(IContainers.PLAYER_TRANSMITTER_DIM, i, playerInventory, buffer, 2);
    }

    private void addContainer(PlayerInventory playerInventory, PlayerTransmitterTile tile) {
        if (this.slots > 1) {
            addSlot(new SlotBase(tile, 0, 69, 37));
            addSlot(new SlotBase(tile, 1, 69 + 23, 37));
        } else {
            addSlot(new SlotBase(tile, 0, 80, 37));
        }
        addPlayerInv(playerInventory, 8, 146, 88);
    }
}