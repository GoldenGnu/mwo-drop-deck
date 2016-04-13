/*
 * Copyright 2016 Niklas Kyster Rasmussen
 *
 * Original code from jEveAssets (https://github.com/GoldenGnu/jeveassets)
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.w3c.dom.Node;


public final class AttributeGetters {

	private AttributeGetters() { }

	public static boolean haveAttribute(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		return attributeNode != null;
	}

	public static String getString(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return "";
		}
		return attributeNode.getNodeValue();
	}

	public static Date getDate(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return new Date();
		}
		String dTemp;
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			dTemp = attributeNode.getNodeValue();
			date = format.parse(dTemp);
		} catch (ParseException ex) { }
		try {
			dTemp = attributeNode.getNodeValue();
			date = new Date(Long.parseLong(dTemp));
		} catch (NumberFormatException ex) { }
		if (date != null) {
			return date;
		} else {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return new Date();
		}
	}

	public static int getInt(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
		String sTemp = attributeNode.getNodeValue();
		int nTemp;
		try {
			nTemp = Integer.parseInt(sTemp);
			return nTemp;
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
	}

	public static long getLong(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
		String sTemp = attributeNode.getNodeValue();
		long nTemp;
		try {
			nTemp = safeStringToLong(sTemp); //Long.parseLong(sTemp);
			return nTemp;
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
	}

	public static double getDouble(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
		String sTemp = attributeNode.getNodeValue();
		double nTemp;
		try {
			nTemp = Double.valueOf(sTemp); //Long.parseLong(sTemp);
			return nTemp;
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
	}

	public static float getFloat(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
		String sTemp = attributeNode.getNodeValue();
		float nTemp;
		try {
			nTemp = Float.valueOf(sTemp); //Long.parseLong(sTemp);
			return nTemp;
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return -1;
		}
	}

	public static boolean getBoolean(final Node node, final String attributeName) {
		Node attributeNode = node.getAttributes().getNamedItem(attributeName);
		if (attributeNode == null) {
			JOptionPane.showMessageDialog(null, "Failed to parse attribute from node: " + node.getNodeName() + " > " + attributeName, "Critical Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return false;
		}
		String sTemp = attributeNode.getNodeValue();
		return (sTemp.equals("true") || sTemp.equals("1"));
	}


	private static Long safeStringToLong(final String s) {
		int nE = s.indexOf("E");
		if (nE == -1) {
			nE = s.indexOf("e");
		}
		if (nE == -1) {
			return Long.parseLong(s);
		}
		String sFirstNumber = s.substring(0, nE);
		String sLastNumber = s.substring(nE + 2);
		double nFirstNumber = Double.parseDouble(sFirstNumber);
		double nLastNumber = Double.parseDouble(sLastNumber);

		long nOutput = 10;
		for (int a = 1; a < nLastNumber; a++) {
			nOutput = nOutput * 10;
		}
		nOutput = (long) Math.ceil(nFirstNumber * nOutput);
		return nOutput;
	}
}
