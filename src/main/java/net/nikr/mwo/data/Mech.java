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

import java.util.Objects;


public class Mech implements Comparable<Mech>{
	private final int ton;
	private final String name;
	private final boolean clan;

	public Mech(int ton, String name, boolean clan) {
		this.ton = ton;
		this.name = name;
		this.clan = clan;
	}

	public int getTon() {
		return ton;
	}

	public String getName() {
		return name;
	}

	public boolean isClan() {
		return clan;
	}

	public String getFaction() {
		if (clan) {
			return "Clan";
		} else {
			return "IS";
		}
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + this.ton;
		hash = 67 * hash + Objects.hashCode(this.name);
		hash = 67 * hash + (this.clan ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Mech other = (Mech) obj;
		if (this.ton != other.ton) {
			return false;
		}
		if (this.clan != other.clan) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Mech o) {
		return getName().compareToIgnoreCase(o.getName());
	}
}
