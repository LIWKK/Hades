package me.apex.hades.utils.boundingbox.box;


import lombok.Getter;
import me.apex.hades.utils.boundingbox.box.boxes.BlockBox1_7_R4;
import me.apex.hades.utils.boundingbox.box.boxes.BlockBox1_8_R1;
import me.apex.hades.utils.boundingbox.box.boxes.BlockBox1_8_R2;
import me.apex.hades.utils.boundingbox.box.boxes.BlockBox1_8_R3;
import me.apex.hades.utils.boundingbox.box.reflection.ProtocolVersion;

@Getter
public class BlockBoxManager {
    private BlockBox blockBox;

    public BlockBoxManager() {
        String version = ProtocolVersion.getGameVersion().getServerVersion().replaceAll("v", "");
        if (version.equalsIgnoreCase("1_7_R4")) {
            blockBox = new BlockBox1_7_R4();
        } else if (version.equalsIgnoreCase("1_8_R1")) {
            blockBox = new BlockBox1_8_R1();
        } else if (version.equalsIgnoreCase("1_8_R2")) {
            blockBox = new BlockBox1_8_R2();
        } else if (version.equalsIgnoreCase("1_8_R3")) {
            blockBox = new BlockBox1_8_R3();
        }
    }
}
