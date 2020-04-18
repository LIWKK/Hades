package me.apex.hades.processors;

import me.apex.hades.objects.User;

public enum OptifineProcessor {
    INSTANCE;

    public void processOptifine(User user) {
        float deltaYaw = user.getDeltaYaw();
        float lastDeltaYaw = user.getLastDeltaYaw();

        float deltaPitch = user.getDeltaPitch();
        float lastDeltaPitch = user.getLastDeltaPitch();

        float yawDiff = Math.abs(deltaYaw - lastDeltaYaw);
        float lastYawDiff = user.getLastYawDiff();
        user.setLastYawDiff(yawDiff);

        float pitchDiff = Math.abs(deltaPitch - lastDeltaPitch);
        float lastPitchDiff = user.getLastPitchDiff();
        user.setLastPitchDiff(pitchDiff);

        float yawSmoothness = Math.abs(yawDiff - lastYawDiff) % 360F;
        float pitchSmoothness = Math.abs(pitchDiff - lastPitchDiff) % 180F;

        if ((yawSmoothness < 0.08F && yawSmoothness > 0.0F) || (pitchSmoothness < 0.01F && pitchSmoothness > 0.0F))
            user.setOptifineTicks(user.getOptifineTicks() + 1);
        else
            user.setOptifineTicks(user.getOptifineTicks() > 0 ? user.getOptifineTicks() - 0.2D : 0);
    }
}
