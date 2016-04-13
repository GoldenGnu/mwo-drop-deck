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
package net.nikr.mwo.gui;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import net.nikr.mwo.Program;
import net.nikr.mwo.data.Mech;


public class InputUtil {

	public static Mech addMech(Program program) {
		//Name
		Set<String> mechNames = new TreeSet<>();
		for (List<Mech> mechs : program.getSettings().getIsMechs().values()) {
			for (Mech mech : mechs) {
				mechNames.add(mech.getName().toLowerCase());
			}
		}
		for (List<Mech> mechs : program.getSettings().getClanMechs().values()) {
			for (Mech mech : mechs) {
				mechNames.add(mech.getName().toLowerCase());
			}
		}
		String mechName = InputUtil.getUniqueInput(program, "", mechNames);
		if (mechName == null) {
			return null;
		}
		//Ton
		Integer ton = InputUtil.getIntegerInput(program, "", "Enter mech ton:", "Add Mech");
		if (ton == null) {
			return null;
		}
		//Faction
		String[] options = {"IS", "Clan"};
		int faction = JOptionPane.showOptionDialog(program.getMainWindow().getFrame(), "Select Faction", "Add Mech", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (faction == JOptionPane.CLOSED_OPTION) {
			return null;
		}
		String factionName = options[faction];
		//Ask to confirm
		int returnValue = JOptionPane.showConfirmDialog(program.getMainWindow().getFrame(), 
						  "• " + mechName + "\r\n• " + ton + "t\r\n• " + factionName
				, "Add Mech", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (returnValue != JOptionPane.OK_OPTION) {
			return null;
		}

		return new Mech(ton, mechName, (faction != 0));
	}

	public static Mech deleteMech(Program program) {
		//Faction
		String[] options = {"IS", "Clan"};
		int faction = JOptionPane.showOptionDialog(program.getMainWindow().getFrame(), "Select Faction", "Delete Mech", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (faction == JOptionPane.CLOSED_OPTION) {
			return null;
		}
		//Name
		Set<Mech> mechNames = new TreeSet<>();
		if (faction == 0) { //IS
			for (List<Mech> mechs : program.getSettings().getIsMechs().values()) {
				mechNames.addAll(mechs);
			}
		} else { //Clan
			for (List<Mech> mechs : program.getSettings().getClanMechs().values()) {
				mechNames.addAll(mechs);
			}
		}
		if (mechNames.isEmpty()) {
			JOptionPane.showMessageDialog(program.getMainWindow().getFrame(), "No mechs found. Nothing to delete", "Delete Mech", JOptionPane.PLAIN_MESSAGE);
			return null;
		}

		Object[] arrMechNames = mechNames.toArray();
		Mech mech = (Mech) JOptionPane.showInputDialog(program.getMainWindow().getFrame(), "Select mech to delete:", "Delete Mech", JOptionPane.PLAIN_MESSAGE, null, arrMechNames, arrMechNames[0]);

		if (mech == null) {
			return null;
		}
		//Ask to confirm
		int returnValue = JOptionPane.showConfirmDialog(program.getMainWindow().getFrame(), 
					"• " + mech.getName() + "\r\n•  " + mech.getTon() + "t\r\n• " +mech.getFaction()
				, "Delete Mech", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (returnValue != JOptionPane.YES_OPTION) {
			return null;
		}
		return mech;
	}

	public static Integer setDropDeckLimit(Program program) {
		return getIntegerInput(program, String.valueOf(program.getSettings().getDropDeck()), "Enter new drop deck ton limit:", "Drop Deck");
	}

	public static Integer getIntegerInput(Program program, String s, String msg, String title) {
		String value = (String) JOptionPane.showInputDialog(program.getMainWindow().getFrame(), msg, title, JOptionPane.PLAIN_MESSAGE, null, null, s);
		if (value != null) {
			try {
				Integer i = Integer.valueOf(value);
				if(i % 5 == 0) {
					return i;
				} else {
					JOptionPane.showMessageDialog(program.getMainWindow().getFrame(), "The number needs to be divisible by 5", "Bad Input", JOptionPane.WARNING_MESSAGE);
					return getIntegerInput(program, value, msg, title);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(program.getMainWindow().getFrame(), "Not a valid number", "Bad Input", JOptionPane.WARNING_MESSAGE);
				return getIntegerInput(program, value, msg, title);
			}
		} else {
			return null;
		}
	}

	public static String getUniqueInput(Program program, String s, Set<String> existing) {
		String value = (String) JOptionPane.showInputDialog(program.getMainWindow().getFrame(), "Enter mech name:", "Add Mech", JOptionPane.PLAIN_MESSAGE, null, null, s);
		if (value != null) {
			if (existing.contains(value.toLowerCase())) {
				JOptionPane.showMessageDialog(program.getMainWindow().getFrame(), "Mech already exist", "Add Mech", JOptionPane.WARNING_MESSAGE);
				return getUniqueInput(program, value, existing);
			} else {
				return value;
			}
		} else {
			return null;
		}
	}
}
