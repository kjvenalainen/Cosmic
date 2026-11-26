/*
    This file is part of the HeavenMS Maple Story Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation version 3 as published by the Free Software
    Foundation.
*/
package client.command.commands.gm0;

import client.Character;
import client.Client;
import client.command.Command;
import constants.id.MapId;
import server.maps.FieldLimit;
import server.maps.MapleMap;
import server.maps.MiniDungeonInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TownCommand extends Command {
    {
        setDescription("List visited towns or teleport to one by name or map ID.");
    }

    @Override
    public void execute(Client client, String[] params) {
        Character player = client.getPlayer();
        List<MapleMap> visitedTowns = getVisitedTowns(player, client);

        if (params.length == 0) {
            if (visitedTowns.isEmpty()) {
                player.dropMessage(5, "You have not visited any towns yet.");
                return;
            }

            player.dropMessage(5, "Visited towns (use @town <name or map ID> to travel):");
            for (MapleMap town : visitedTowns) {
                player.dropMessage(5, getTownLabel(town) + " (map ID " + town.getId() + ")");
            }
            return;
        }

        if (!player.isAlive()) {
            player.dropMessage(5, "You cannot travel to a town while dead.");
            return;
        }

        if (player.getEventInstance() != null || MiniDungeonInfo.isDungeonMap(player.getMapId())
                || FieldLimit.CANNOTMIGRATE.check(player.getMap().getFieldLimit())) {
            player.dropMessage(5, "You cannot travel to a town from this map.");
            return;
        }

        String requestedTown = joinStringFrom(params, 0).trim();
        List<MapleMap> matches = findTowns(visitedTowns, requestedTown);
        if (matches.isEmpty()) {
            player.dropMessage(5, "That town is not in your visited towns list. Use @town to see available towns.");
            return;
        }
        if (matches.size() > 1) {
            player.dropMessage(5, "Several visited towns match that name. Use a map ID:");
            for (MapleMap town : matches) {
                player.dropMessage(5, getTownLabel(town) + " (map ID " + town.getId() + ")");
            }
            return;
        }

        MapleMap target = matches.get(0);

        if (!player.getVisitedTownMapIds().contains(target.getId()) || !target.isTown()) {
            player.dropMessage(5, "You can only travel to towns you have visited.");
            return;
        }

        if (FieldLimit.CANNOTVIPROCK.check(target.getFieldLimit())
                || (target.getForcedReturnId() != MapId.NONE && !MapId.isMapleIsland(target.getId()))) {
            player.dropMessage(5, "You cannot travel to that town.");
            return;
        }

        if (player.getMapId() == target.getId()) {
            player.dropMessage(5, "You are already in " + getTownLabel(target) + ".");
            return;
        }

        player.saveLocationOnWarp();
        player.changeMap(target, target.getRandomPlayerSpawnpoint());
        player.dropMessage(5, "Traveling to " + getTownLabel(target) + ".");
    }

    private static List<MapleMap> getVisitedTowns(Character player, Client client) {
        List<MapleMap> towns = new ArrayList<>();
        for (int mapId : player.getVisitedTownMapIds()) {
            MapleMap map = client.getChannelServer().getMapFactory().getMap(mapId);
            if (map != null && map.isTown()) {
                towns.add(map);
            }
        }

        towns.sort(Comparator.comparing(TownCommand::getTownLabel, String.CASE_INSENSITIVE_ORDER)
                .thenComparingInt(MapleMap::getId));
        return towns;
    }

    private static List<MapleMap> findTowns(List<MapleMap> visitedTowns, String requestedTown) {
        int requestedMapId = -1;
        try {
            requestedMapId = Integer.parseInt(requestedTown);
        } catch (NumberFormatException ignored) {
            // Name lookup below.
        }

        List<MapleMap> matches = new ArrayList<>();
        for (MapleMap town : visitedTowns) {
            if (town.getId() == requestedMapId || matchesTownName(town, requestedTown)) {
                matches.add(town);
            }
        }

        return matches;
    }

    private static boolean matchesTownName(MapleMap town, String requestedTown) {
        return getTownLabel(town).equalsIgnoreCase(requestedTown)
                || equalsIgnoreCase(town.getStreetName(), requestedTown)
                || equalsIgnoreCase(town.getMapName(), requestedTown);
    }

    private static String getTownLabel(MapleMap town) {
        String streetName = valueOrEmpty(town.getStreetName());
        String mapName = valueOrEmpty(town.getMapName());
        if (streetName.isBlank()) {
            return mapName.isBlank() ? "Town" : mapName;
        }
        if (mapName.isBlank() || streetName.equalsIgnoreCase(mapName)) {
            return streetName;
        }
        return streetName + " - " + mapName;
    }

    private static boolean equalsIgnoreCase(String left, String right) {
        return left != null && left.equalsIgnoreCase(right);
    }

    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
