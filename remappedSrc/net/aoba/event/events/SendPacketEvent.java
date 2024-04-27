/*
* Aoba Hacked Client
* Copyright (C) 2019-2024 coltonk9043
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.aoba.event.events;

import java.util.ArrayList;
import java.util.List;
import net.aoba.event.listeners.AbstractListener;
import net.aoba.event.listeners.SendPacketListener;
import net.minecraft.network.packet.Packet;

public class SendPacketEvent extends AbstractEvent {

	private Packet<?> packet;
	
	public SendPacketEvent(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> GetPacket(){
		return packet;
	}
	
	@Override
	public void Fire(ArrayList<? extends AbstractListener> listeners) {
		for(AbstractListener listener : List.copyOf(listeners)) {
			SendPacketListener sendPacketListener = (SendPacketListener) listener;
			sendPacketListener.OnSendPacket(this);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<SendPacketListener> GetListenerClassType() {
		return SendPacketListener.class;
	}
}
