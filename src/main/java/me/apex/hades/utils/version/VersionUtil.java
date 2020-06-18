package me.apex.hades.utils.version;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.HadesPlugin;

@Setter
@Getter
public class VersionUtil {

    private Version version;

    public VersionUtil() {
        String bukkitVersion = HadesPlugin.bukkitVersion;
        if (bukkitVersion.contains("1_8")) version = Version.V1_8;
        if (bukkitVersion.contains("1_7")) version = Version.V1_7;
    }

    public enum Version {
        V1_8,
        V1_7
    }
}
