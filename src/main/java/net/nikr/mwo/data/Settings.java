/*
 * Copyright 2016 Niklas Kyster Rasmussen
 *
 * This file is part of MWO Drop Deck.
 *
 * MWO Drop Deck is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * MWO Drop Deck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MWO Drop Deck; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package net.nikr.mwo.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Settings {
	private int dropdeck;
	private int maxWeight = 0;
	private int minWeight = Integer.MAX_VALUE;
	private final Map<Integer, List<Mech>> clanMechs;
	private final Map<Integer, List<Mech>> isMechs;

	public Settings() {
		clanMechs = new HashMap<Integer, List<Mech>>();
		isMechs = new HashMap<Integer, List<Mech>>();
	}

	public void addMech(Mech mech) {
		getList(mech).add(mech);
	}

	public boolean removeMech(Mech mech) {
		return getList(mech).remove(mech);
	}

	public int getDropDeck() {
		return dropdeck;
	}

	public void setDropDeck(int dropdeck) {
		this.dropdeck = dropdeck;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public int getMinWeight() {
		return minWeight;
	}

	public void resetMinMax() {
		maxWeight = 0;
		minWeight = Integer.MAX_VALUE;
	}

	public void updateMinMax(int tons) {
		this.maxWeight = Math.max(this.maxWeight, tons);
		this.minWeight = Math.min(this.minWeight, tons);
	}

	public Map<Integer, List<Mech>> getClanMechs() {
		return clanMechs;
	}

	public Map<Integer, List<Mech>> getIsMechs() {
		return isMechs;
	}

	private List<Mech> getList(Mech mech) {
		Map<Integer, List<Mech>> map;
		if (mech.isClan()) {
			map = clanMechs;
		} else {
			map = isMechs;
		}
		int index = mech.getTon();
		List<Mech> list = map.get(index);
		if (list == null) { //Add new ton list
			list = new ArrayList<Mech>();
			map.put(index, list);
		}
		return list;
	}
}
