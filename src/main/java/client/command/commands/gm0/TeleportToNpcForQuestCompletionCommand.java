/*
    This file is part of the HeavenMS Maple Story Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation version 3 as published by the Free Software
    Foundation.
*/
package client.command.commands.gm0;

import client.Character;
import client.Client;
import client.command.Command;
import provider.Data;
import provider.DataProviderFactory;
import provider.DataTool;
import provider.wz.WZFiles;
import server.life.NPC;
import server.maps.FieldLimit;
import server.maps.MapleMap;
import server.maps.MiniDungeonInfo;
import server.quest.Quest;

public class TeleportToNpcForQuestCompletionCommand extends Command {
    private static final Data NPC_LOCATIONS = DataProviderFactory.getDataProvider(WZFiles.ETC).getData("NpcLocation.img");

    {
        setDescription("Teleport to the NPC needed to complete an active quest.");
    }

    @Override
    public void execute(Client client, String[] params) {
        Character player = client.getPlayer();
        if (params.length != 1) {
            player.yellowMessage("Syntax: @teleportToNpcForQuestCompletion <questId>");
            return;
        }

        final int questId;
        try {
            questId = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            player.yellowMessage("Quest ID must be a number.");
            return;
        }

        if (!player.isAlive()) {
            player.dropMessage(1, "This command cannot be used when you're dead.");
            return;
        }

        if (!player.isGM() && (player.getEventInstance() != null || MiniDungeonInfo.isDungeonMap(player.getMapId()) || FieldLimit.CANNOTMIGRATE.check(player.getMap().getFieldLimit()))) {
            player.dropMessage(1, "This command can not be used in this map.");
            return;
        }

        if (questId <= 0 || player.getQuestStatus(questId) != 1) {
            player.dropMessage(5, "Quest " + questId + " is not active.");
            return;
        }

        Quest quest = Quest.getInstance(questId);
        int npcId = quest.getNpcRequirement(true);
        if (npcId < 0) {
            player.dropMessage(5, "Quest " + questId + " does not have a quest completion NPC.");
            return;
        }

        Data npcLocations = NPC_LOCATIONS.getChildByPath(String.valueOf(npcId));
        if (npcLocations == null) {
            player.dropMessage(5, "Could not find a map for the quest completion NPC.");
            return;
        }

        for (Data location : npcLocations.getChildren()) {
            int mapId = DataTool.getInt(location);
            if (mapId < 0) {
                continue;
            }

            MapleMap map = client.getChannelServer().getMapFactory().getMap(mapId);
            NPC npc = map.getNPCById(npcId);
            if (npc != null) {
                player.saveLocationOnWarp();
                player.changeMap(map, npc.getPosition());
                player.dropMessage(5, "Teleported to " + npc.getName() + " for quest " + questId + ".");
                return;
            }
        }

        player.dropMessage(5, "Could not find a map containing the quest completion NPC.");
    }
}
