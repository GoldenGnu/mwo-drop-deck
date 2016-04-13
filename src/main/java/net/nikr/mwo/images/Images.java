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
package net.nikr.mwo.images;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public enum Images {
	ALL_INFO ("all_info.png"),
	ALL_INFO_SELECTED ("all_info_selected.png"),
	ALL_LOCKED ("all_locked.png"),
	ALL_UNLOCKED ("all_unlocked.png"),
	INFO ("info.png"),
	INFO_SELECTED ("info_selected.png"),
	LOCKED ("locked.png"),
	UNLOCKED ("unlocked.png"),
	ADD ("add.png"),
	DELETE ("delete.png"),
	DROPDECK ("dropdeck.png"),
	DROPDOWN ("dropdown.png"),
	;

	private final String filename;   // in kilograms
	private BufferedImage image = null;
	private Icon icon;

	Images(final String filename) {
		this.filename = filename;
	}

	public Icon getIcon() {
		load();
		return icon;
	}

	public Image getImage() {
		load();
		return image;
	}

	public String getFilename() {
		return filename;
	}

	private boolean load() {
		if (image == null) {
			image = getBufferedImage(filename);
			icon = new ImageIcon(image);
		}
		return (image != null);
	}

	public static boolean preload() {
		int count = 0;
		boolean ok = true;
		for (Images i : Images.values()) {
			if (!i.load()) {
				ok = false;
			}
			count++;
		}
		return ok;
	}

	public static BufferedImage getBufferedImage(final String s) {
		try {
			java.net.URL imgURL = Images.class.getResource(s);
			if (imgURL != null) {
				return ImageIO.read(imgURL);
			} else {
				
			}
		} catch (IOException ex) {
			
		}
		return null;
	}
}
