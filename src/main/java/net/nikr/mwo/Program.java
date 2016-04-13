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
package net.nikr.mwo;

import net.nikr.mwo.gui.MainWindow;
import net.nikr.mwo.data.Settings;
import net.nikr.mwo.io.DataReader;
import net.nikr.mwo.io.DataWriter;


public class Program {

	private final MainWindow mainWindow;
	private final Settings settings;

	public Program() {
		settings = new Settings();
		DataReader.load(settings);
		mainWindow = new MainWindow(this);
		mainWindow.show();
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public Settings getSettings() {
		return settings;
	}

	public void saveSettings() {
		DataWriter.save(settings);
	}
}
