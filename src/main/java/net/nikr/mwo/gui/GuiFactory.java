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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.nikr.mwo.images.Images;
import net.nikr.mwo.util.DictionaryMap;


public class GuiFactory {
	public static JSlider createSlider(final DropDeckPanel dropDeckPanel) {
		Dictionary<Integer, JLabel> labelTable = new DictionaryMap<Integer, JLabel>();
		for (int i = 0; i < dropDeckPanel.getTons().size(); i++) {
			labelTable.put(i, new JLabel(String.valueOf(dropDeckPanel.getTons().get(i))));
		}

		final JSlider jSlider = new JSlider(JSlider.VERTICAL, 0, dropDeckPanel.getTons().size() - 1, 0);
		jSlider.setLabelTable(labelTable);
		jSlider.setPaintLabels(true);
		jSlider.setPaintTicks(true);
		jSlider.setPaintTrack(true);
		jSlider.setSnapToTicks(true);
		jSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				dropDeckPanel.validateSliders(jSlider);
			}
		});
		return jSlider;
	}

	public static JToggleButton createLockButton(final DropDeckPanel dropDeckPanel, final JSlider jSlider) {
		JToggleButton jButton = new JToggleButton();
		jButton.setToolTipText("Lock/Unlcok");
		jButton.setSelectedIcon(Images.LOCKED.getIcon());
		jButton.setIcon(Images.UNLOCKED.getIcon());
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jSlider.setEnabled(!jSlider.isEnabled());
				dropDeckPanel.updateLock();
			}
		});
		return jButton;
	}

	public static JToggleButton createShowButton(final int index, final DropDeckPanel dropDeckPanel) {
		JToggleButton jButton = new JToggleButton(Images.INFO.getIcon());
		jButton.setToolTipText("Show/Hide");
		jButton.setSelectedIcon(Images.INFO_SELECTED.getIcon());
		jButton.setSelected(false);
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dropDeckPanel.updateShow(index);
			}
		});
		return jButton;
	}

	public static JTextArea createTextArea(JPanel jPanel) {
		JTextArea jTextArea = new JTextArea();
		jTextArea.setFont(jPanel.getFont());
		jTextArea.setBackground(jPanel.getBackground());
		jTextArea.setForeground(jPanel.getForeground());
		jTextArea.setEditable(false);
		return jTextArea;
	}
	
	public static JLabel createLabel(boolean visible) {
		JLabel jLabel = new JLabel();
		jLabel.setVisible(visible);
		jLabel.setBorder(BorderFactory.createTitledBorder("Weight"));
		jLabel.setHorizontalAlignment(JLabel.CENTER);
		return jLabel;
	}

	public static JScrollPane createScroll(Component view, String title) {
		JScrollPane jScroll = new JScrollPane(view);
		jScroll.setVisible(false);
		jScroll.setBorder(BorderFactory.createTitledBorder(title));
		return jScroll;
	}
}
