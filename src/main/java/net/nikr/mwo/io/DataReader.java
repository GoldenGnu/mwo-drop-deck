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
package net.nikr.mwo.io;

import javax.swing.JOptionPane;
import net.nikr.mwo.data.Mech;
import net.nikr.mwo.data.Settings;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public final class DataReader extends AbstractXmlReader {

	private DataReader() { }

	public static void load(Settings settings) {
		DataReader reader = new DataReader();
		reader.read(settings);
	}

	private void read(Settings settings) {
		try {
			Element element = getDocumentElement(FileUtil.getMechsXml(true).getAbsolutePath(), false);
			parse(element, settings);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Failed to load data file.\r\n");
			System.exit(-1);
		}
	}

	private void parse(final Element element, Settings settings) {
		//Settings
		NodeList settingsNodes = element.getElementsByTagName("settings");
		if (settingsNodes.getLength() == 1) {
			Element settingsElement = (Element) settingsNodes.item(0);
			parseSettings(settingsElement, settings);
		}

		//IS Mechs
		NodeList isNodes = element.getElementsByTagName("is");
		if (isNodes.getLength() == 1) {
			Element isElement = (Element) isNodes.item(0);
			parseMechs(isElement, settings);
		}

		//Clan Mechs
		NodeList clanNodes = element.getElementsByTagName("clan");
		if (clanNodes.getLength() == 1) {
			Element clanElement = (Element) clanNodes.item(0);
			parseMechs(clanElement, settings);
		}
		
		
	}

	private void parseSettings(final Element element, Settings settings) {
		int maxtonnage = AttributeGetters.getInt(element, "dropdeck");
		settings.setDropDeck(maxtonnage);
	}

	private void parseMechs(final Element element, Settings settings) {
		NodeList nodes = element.getElementsByTagName("mech");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element mechElement = (Element) nodes.item(i);
			int tons = AttributeGetters.getInt(mechElement, "tons");
			settings.updateMinMax(tons);
			String name = AttributeGetters.getString(mechElement, "name");
			boolean clan = AttributeGetters.getBoolean(mechElement, "clan");
			settings.addMech(new Mech(tons, name, clan));
		}
	}
}
