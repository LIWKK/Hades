package me.apex.hades.utils.boundingbox.box.impl;

import me.apex.hades.utils.boundingbox.box.BoundingBox;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Collections;
import java.util.List;

public class RegularBox extends BlockBox {
    public RegularBox(Material material, BoundingBox original) {
        super(material, original);
    }

    @Override
    List<BoundingBox> getBox(Block block) {
        return Collections.singletonList(getOriginal().add(block.getLocation().toVector()));
    }
}
