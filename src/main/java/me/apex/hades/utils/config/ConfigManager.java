package me.apex.hades.utils.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ConfigManager {
    private int maxPunishmentVL;
    private boolean punishmentsEnabled, punishmentBroadcast;
    private List<String> banMessages = new ArrayList<>(), banCommands = new ArrayList<>();
    private String alertMessage;
}