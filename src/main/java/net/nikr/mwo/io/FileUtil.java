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

import java.io.File;
import java.net.URL;


public class FileUtil {

	private static final String MECHS_XML = "data" + File.separator + "mechs.xml";

	private static File getLocalFile(final String filename, final boolean dynamic) {
		File temp;
		File file;
		if (dynamic) {
			File userDir = new File(System.getProperty("user.home", "."));
			temp = new File(userDir.getAbsolutePath() + File.separator + ".mwo_drop_deck");
			file = new File(temp.getAbsolutePath() + File.separator + filename);
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
		} else {
			URL location = net.nikr.mwo.Program.class.getProtectionDomain().getCodeSource().getLocation();
			try {
				temp = new File(location.toURI());
			} catch (Exception ex) {
				temp = new File(location.getPath());
			}
			file = new File(temp.getParentFile().getAbsolutePath() + File.separator + filename);
		}
		return file;
	}

	public static File getMechsXml(boolean read) {
		if (read) {
			File file = getLocalFile(MECHS_XML, true); 
			if (file.exists()) { //If user dir file exist, that is what we use
				return file;
			} else { //Otherwise use program dir
				return getLocalFile(MECHS_XML, false);
			}
		} else {
			File file = getLocalFile(MECHS_XML, false);
			if (file.canWrite() && file.canRead()) { //If program dir is writable use program dir
				return file;
			} else { //Otherwise use user dir
				return getLocalFile(MECHS_XML, true);
			}
		}
	}
}
