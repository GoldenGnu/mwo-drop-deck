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

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.nikr.mwo.data.Mech;
import net.nikr.mwo.data.Settings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Niklas
 */
public class DataWriter extends AbstractXmlWriter {
	private DataWriter() { }

	public static boolean save(final Settings settings) {
		DataWriter writer = new DataWriter();
		return writer.write(settings);
	}

	private boolean write(final Settings settings) {
		Document xmldoc;
		try {
			xmldoc = getXmlDocument("data");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Failed to save data file.\r\n");
			System.exit(-1);
			return false;
		}
		writeSettings(xmldoc, settings);
		writeIsMechs(xmldoc, settings.getIsMechs());
		writeClanMechs(xmldoc, settings.getClanMechs());
		
		try {
			writeXmlFile(xmldoc, FileUtil.getMechsXml(false).getAbsolutePath());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Failed to save data file.\r\n");
			System.exit(-1);
			return false;
		}
		return true;
	}

	private void writeSettings(Document xmldoc, Settings settings) {
		Element settingsNode = xmldoc.createElementNS(null, "settings");
		xmldoc.getDocumentElement().appendChild(settingsNode);
		settingsNode.setAttribute("dropdeck", String.valueOf(settings.getDropDeck()));
	}

	private void writeIsMechs(Document xmldoc, Map<Integer, List<Mech>> mechs) {
		Element isNode = xmldoc.createElementNS(null, "is");
		xmldoc.getDocumentElement().appendChild(isNode);
		writeMechs(xmldoc, isNode, mechs, false);
	}

	private void writeClanMechs(Document xmldoc, Map<Integer, List<Mech>> mechs) {
		Element clanNode = xmldoc.createElementNS(null, "clan");
		xmldoc.getDocumentElement().appendChild(clanNode);
		writeMechs(xmldoc, clanNode, mechs, true);
	}

	private void writeMechs(Document xmldoc, Element parentNode, Map<Integer, List<Mech>> mechs, boolean clan) {
		for (Map.Entry<Integer, List<Mech>> entry : mechs.entrySet()) {
			Integer tons = entry.getKey();
			for (Mech mechName : entry.getValue()) {
				Element systemNode = xmldoc.createElementNS(null, "mech");
				systemNode.setAttributeNS(null, "tons", String.valueOf(tons));
				systemNode.setAttributeNS(null, "name", mechName.getName());
				systemNode.setAttributeNS(null, "clan", String.valueOf(clan));
				parentNode.appendChild(systemNode);
			}
		}
	}
}
