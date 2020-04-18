package me.apex.hades.processor;

import me.apex.hades.Hades;
import me.apex.hades.data.User;
import me.apex.hades.utils.TaskUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VPNProcessor {

    private static Map<String, Boolean> ips = new HashMap();

    public static void checkForVPN(User user)
    {
        if(user.getPlayer().hasPermission("hades.bypassvpn")) return;
        if(isIPCached(user.data.ip))
        {
            if(isVPN(user.data.ip))
            {
                //Kick Player
                user.kick(Hades.instance.getConfig().getString("vpn-kick-message").replace("%newline%", "\n"));
                return;
            }
        }

        TaskUtils.run(() -> {
            try{
                if(checkIp(user.data.ip.split(":")[0].replace("/", "")))
                    user.kick(Hades.instance.getConfig().getString("vpn-kick-message").replace("%newline%", "\n"));
            }catch (Exception ignored) { }
        });
    }

    private static boolean isIPCached(String ip)
    {
        return ips.containsKey(ip);
    }

    private static boolean isVPN(String ip)
    {
        return ips.get(ip);
    }

    private static boolean checkIp(String ip) throws Exception
    {
        if(isIPCached(ip))
            return ips.get(ip);

        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://api.iplegit.com/info?ip=" + ip), StandardCharsets.UTF_8));

        if(Boolean.parseBoolean(json.get("bad").toString())) {
            ips.put(ip, true);
            return true;
        }

        ips.put(ip, false);
        return false;
    }

}
