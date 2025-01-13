package at.helpch.placeholderapi.expansion.server.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RegionFormatter {


    /**
     * Format the region with the given format
     *
     * @param format format
     * @return {@code null} if the format is empty or invalid.
     */
    public @Nullable RegionizedParams formatRegion(@NotNull final String format, @NotNull final OfflinePlayer offlinePlayer) {
        if (format.trim().isEmpty()) {
            return null;
        }

        String trimmedFormat = format.trim();

        if (!trimmedFormat.startsWith("region_")) {
            return null;
        }

        if (trimmedFormat.startsWith("region_player_")) {
            if (!offlinePlayer.isOnline()) {
                return null;
            }

            Player player = offlinePlayer.getPlayer();
            if (player == null) {
                return null;
            }

            Location location = player.getLocation();

            return new RegionizedParams(
                    location.getWorld(),
                    location.getBlockX() >> 4,
                    location.getBlockZ() >> 4,
                    trimmedFormat.substring("region_player_".length())
            );

        }

        if (!trimmedFormat.startsWith("region_{")){
            return null;
        }

        int index = trimmedFormat.indexOf("}");
        if (index < 0) {
            return null;
        }

        if (trimmedFormat.indexOf(index + 1) != '_'){
            return null;
        }

        String[] regionChunk = trimmedFormat.substring("region_{".length(), index).split(",");
        if (regionChunk.length != 3) {
            return null;
        }

        World world = Bukkit.getWorld(regionChunk[0].trim());
        if (world == null) {
            return null;
        }

        int x,z;
        try {
            x = Integer.parseInt(regionChunk[1]);
            z = Integer.parseInt(regionChunk[2]);
        } catch (NumberFormatException e){
            return null;
        }

        return new RegionizedParams(
                world,
                x,
                z,
                trimmedFormat.substring(index + 1)
        );

    }

    public final static class RegionizedParams {

        private final World world;
        private final int x;
        private final int z;
        private final String params;

        private RegionizedParams(World world, int x, int z, String params){
            this.world = world;
            this.x = x;
            this.z = z;
            this.params = params;
        }

        public World getWorld() {
            return world;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }

        public String getParams() {
            return params;
        }
    }
}
