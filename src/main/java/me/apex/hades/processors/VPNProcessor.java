package me.apex.hades.processors;

import java.util.HashMap;
import java.util.Map;

import me.apex.hades.Hades;
import me.apex.hades.objects.User;

public enum VPNProcessor {
    INSTANCE;

    private Map<String, Boolean> ips = new HashMap();

    public boolean ProcessVPN(User user) {
        if (user.getPlayer().hasPermission(Hades.getInstance().basePermission + ".bypassvpn")) return false;
        if (ips.containsKey(user.getAddress())) {
            if (ips.get(user.getAddress())) {
                return true;
            }
        } else {
            try {
                if (scan(user.getAddress()))
                    return true;
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private boolean scan(String in) throws Exception {
        /*JSONObject json = new JSONObject(IOUtils.toString(new URL("https://api.iplegit.com/info?ip=" + in), StandardCharsets.UTF_8));
        if (Boolean.parseBoolean(json.get("bad").toString())) {
            ips.put(in, true);
            return true;
        }

        ips.put(in, false);
        return false;*/
    	return false;
    }

}
