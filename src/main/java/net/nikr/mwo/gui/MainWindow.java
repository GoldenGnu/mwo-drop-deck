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

import javax.swing.JFrame;
import net.nikr.mwo.Program;


public class MainWindow {
	private final JFrame jFrame;
	private final Program program;

	public MainWindow(Program program) {
		this.program = program;

		this.jFrame = new JFrame();
		jFrame.setTitle("MWO Drop Deck");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);

		DropDeckPanel dropDeckPanel = new DropDeckPanel(program);
		jFrame.getContentPane().add(dropDeckPanel.getPanel());
		
	}

	public void show() {
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public void pack() {
		jFrame.pack();;
	}

	public JFrame getFrame() {
		return jFrame;
	}
}
