package me.apex.hades.tinyprotocol.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.apex.hades.tinyprotocol.api.packets.reflections.types.WrappedField;

@Getter
@AllArgsConstructor
public class PacketField<T> {
    private WrappedField field;
    private T value;
}
