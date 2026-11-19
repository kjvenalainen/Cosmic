/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.server.channel.handlers;

import client.Character;
import client.Client;
import config.YamlConfig;
import net.AbstractPacketHandler;
import net.packet.InPacket;
import server.CashShop;
import tools.PacketCreator;

/**
 * @author Terry Han (Acrylic)
 */
public final class TouchingCashShopHandler extends AbstractPacketHandler {
    @Override
    public final void handlePacket(InPacket p, Client c) {
        Character player = c.getPlayer();
        boolean chargeRequest = p.available() == 1 && p.readUnsignedByte() == 1;
        CashShop cashShop = player.getCashShop();
        int freeNxCreditPerClick = YamlConfig.config.server.FREE_NX_CREDIT_PER_CLICK;

        if (chargeRequest && cashShop.isOpened() && freeNxCreditPerClick > 0) {
            int balance = cashShop.getCash(CashShop.NX_CREDIT);
            if (balance <= Integer.MAX_VALUE - freeNxCreditPerClick) {
                cashShop.gainCash(CashShop.NX_CREDIT, freeNxCreditPerClick);
                player.saveCharToDB(true);
                c.sendPacket(PacketCreator.serverNotice(5, "NX Credit added."));
            } else {
                c.sendPacket(PacketCreator.serverNotice(5, "NX Credit balance limit reached."));
            }
        }

        c.sendPacket(PacketCreator.showCash(player));
    }
}
