package me.apex.hades;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.event.EventManager;
import me.apex.hades.listeners.BukkitEvents;
import me.apex.hades.listeners.PacketListener;
import me.apex.hades.tinyprotocol.api.TinyProtocolHandler;
import me.apex.hades.tinyprotocol.api.packets.reflections.Reflections;
import me.apex.hades.tinyprotocol.api.packets.reflections.types.WrappedField;
import me.apex.hades.user.UserManager;
import me.apex.hades.utils.boundingbox.block.BlockUtil;
import me.apex.hades.utils.boundingbox.box.BlockBoxManager;
import me.apex.hades.utils.boundingbox.box.impl.BoundingBoxes;
import me.apex.hades.utils.config.ConfigLoader;
import me.apex.hades.utils.config.ConfigManager;
import me.apex.hades.utils.math.MathUtil;
import me.apex.hades.utils.math.RunUtils;
import me.apex.hades.utils.reflection.CraftReflection;
import me.apex.hades.utils.version.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Getter
@Setter
public class HadesPlugin extends JavaPlugin {

    @Getter
    public static HadesPlugin instance;
    public static UserManager userManager;
    public static String bukkitVersion;
    public static VersionUtil versionUtil;
    private BlockBoxManager blockBoxManager;
    private BoundingBoxes boxes;
    private ScheduledExecutorService executorService;
    private TinyProtocolHandler tinyProtocolHandler;
    private Map<UUID, List<Entity>> entities = new ConcurrentHashMap<>();
    private WrappedField entityList = Reflections.getNMSClass("World").getFieldByName("entityList");
    private EventManager eventManager = new EventManager();
    private ConfigManager configManager = new ConfigManager();
    private ConfigLoader configLoader = new ConfigLoader();

    @Override
    public void onEnable() {
        instance = this;
        userManager = new UserManager();

        configLoader.load();

        tinyProtocolHandler = new TinyProtocolHandler();
        getServer().getPluginManager().registerEvents(new BukkitEvents(), HadesPlugin.instance);

        new MathUtil();
        new BlockUtil();

        bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        versionUtil = new VersionUtil();

        executorService = Executors.newSingleThreadScheduledExecutor();
        this.blockBoxManager = new BlockBoxManager();
        this.boxes = new BoundingBoxes();

        eventManager.registerListeners(new PacketListener(), this);

        Bukkit.getOnlinePlayers().forEach(player -> TinyProtocolHandler.getInstance().addChannel(player));

        RunUtils.taskTimer(() -> {
            for (World world : Bukkit.getWorlds()) {
                Object vWorld = CraftReflection.getVanillaWorld(world);

                List<Object> vEntities = Collections.synchronizedList(HadesPlugin.getInstance().getEntityList().get(vWorld));

                List<Entity> bukkitEntities = vEntities.parallelStream().map(CraftReflection::getBukkitEntity).collect(Collectors.toList());

                HadesPlugin.getInstance().getEntities().put(world.getUID(), bukkitEntities);
            }
        }, 2L, 2L);


    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> TinyProtocolHandler.getInstance().removeChannel(player));

        userManager.getUsers().forEach(user -> {
            TinyProtocolHandler.getInstance().removeChannel(user.getPlayer());

            user.getCheckList().forEach(check -> eventManager.unregisterListener(check));
        });

        executorService.shutdownNow();
    }
}
