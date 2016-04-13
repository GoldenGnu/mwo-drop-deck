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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import net.nikr.mwo.Program;
import net.nikr.mwo.data.Mech;
import net.nikr.mwo.images.Images;
import net.nikr.mwo.util.DictionaryMap;


public class DropDeckPanel {
	private final JPanel jPanel;
	private final GroupLayout layout;
	private final JSlider jMech1;
	private final JSlider jMech2;
	private final JSlider jMech3;
	private final JSlider jMech4;
	private final JSlider jEmpty;
	private final JLabel jFree;
	private final JLabel jWeightInfo1;
	private final JLabel jWeightInfo2;
	private final JLabel jWeightInfo3;
	private final JLabel jWeightInfo4;
	private final JToggleButton jShowInfo;
	private final JToggleButton jLock;
	private final JMenuItem jAddMech;
	private final JMenuItem jDeleteMech;
	private final JMenuItem jDropDeck;
	private final JToggleButton jLock1;
	private final JToggleButton jLock2;
	private final JToggleButton jLock3;
	private final JToggleButton jLock4;
	private final JToggleButton jShowInfo1;
	private final JToggleButton jShowInfo2;
	private final JToggleButton jShowInfo3;
	private final JToggleButton jShowInfo4;
	private final JTextArea jIs1;
	private final JTextArea jIs2;
	private final JTextArea jIs3;
	private final JTextArea jIs4;
	private final JTextArea jClan1;
	private final JTextArea jClan2;
	private final JTextArea jClan3;
	private final JTextArea jClan4;
	private final JScrollPane jIs1Scroll;
	private final JScrollPane jIs2Scroll;
	private final JScrollPane jIs3Scroll;
	private final JScrollPane jIs4Scroll;
	private final JScrollPane jClan1Scroll;
	private final JScrollPane jClan2Scroll;
	private final JScrollPane jClan3Scroll;
	private final JScrollPane jClan4Scroll;
	private final JLabel jCopyright;
	private final JLabel jLicense;
	private final JLabel jVersion;

	private final Program program;
	private final List<Integer> tons;

	private boolean validating = false;

	public DropDeckPanel(final Program program) {
		this.program = program;

		tons = new ArrayList<Integer>();
		for (int i = program.getSettings().getMinWeight(); i <= program.getSettings().getMaxWeight(); i = i + 5) {
			tons.add(i);
		}

		jPanel = new JPanel();
		layout = new GroupLayout(jPanel);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		jPanel.setLayout(layout);

		jMech1 = GuiFactory.createSlider(this);
		jMech2 = GuiFactory.createSlider(this);
		jMech3 = GuiFactory.createSlider(this);
		jMech4 = GuiFactory.createSlider(this);
		jEmpty = new JSlider();

		jLock1 = GuiFactory.createLockButton(this, jMech1);
		jLock2 = GuiFactory.createLockButton(this, jMech2);
		jLock3 = GuiFactory.createLockButton(this, jMech3);
		jLock4 = GuiFactory.createLockButton(this, jMech4);

		jShowInfo1 = GuiFactory.createShowButton(1, this);
		jShowInfo2 = GuiFactory.createShowButton(2, this);
		jShowInfo3 = GuiFactory.createShowButton(3, this);
		jShowInfo4 = GuiFactory.createShowButton(4, this);

		jIs1 = GuiFactory.createTextArea(jPanel);
		jIs2 = GuiFactory.createTextArea(jPanel);
		jIs3 = GuiFactory.createTextArea(jPanel);
		jIs4 = GuiFactory.createTextArea(jPanel);

		jClan1 = GuiFactory.createTextArea(jPanel);
		jClan2 = GuiFactory.createTextArea(jPanel);
		jClan3 = GuiFactory.createTextArea(jPanel);
		jClan4 = GuiFactory.createTextArea(jPanel);

		jIs1Scroll = GuiFactory.createScroll(jIs1, "IS");
		jIs2Scroll = GuiFactory.createScroll(jIs2, "IS");
		jIs3Scroll = GuiFactory.createScroll(jIs3, "IS");
		jIs4Scroll = GuiFactory.createScroll(jIs4, "IS");

		jClan1Scroll = GuiFactory.createScroll(jClan1, "Clan");
		jClan2Scroll = GuiFactory.createScroll(jClan2, "Clan");
		jClan3Scroll = GuiFactory.createScroll(jClan3, "Clan");
		jClan4Scroll = GuiFactory.createScroll(jClan4, "Clan");

		jFree = GuiFactory.createLabel(true);
		jFree.setOpaque(true);
		jFree.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		jShowInfo = new JToggleButton(Images.ALL_INFO.getIcon());
		jShowInfo.setSelectedIcon(Images.ALL_INFO_SELECTED.getIcon());
		jShowInfo.setToolTipText("Show/Hide All");
		jShowInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				show();
			}
		});
		jShowInfo.setSelected(false);

		jLock = new JToggleButton(Images.ALL_UNLOCKED.getIcon());
		jLock.setSelectedIcon(Images.ALL_LOCKED.getIcon());
		jLock.setToolTipText("Lock/Unlock All");
		jLock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jLock.isSelected()) {
					jLock1.setSelected(true);
					jLock2.setSelected(true);
					jLock3.setSelected(true);
					jLock4.setSelected(true);
					jMech1.setEnabled(false);
					jMech2.setEnabled(false);
					jMech3.setEnabled(false);
					jMech4.setEnabled(false);
				} else {
					jLock1.setSelected(false);
					jLock2.setSelected(false);
					jLock3.setSelected(false);
					jLock4.setSelected(false);
					jMech1.setEnabled(true);
					jMech2.setEnabled(true);
					jMech3.setEnabled(true);
					jMech4.setEnabled(true);
				}
			}
		});
		jLock.setSelected(false);

		JDropDownButton jDropDownButton = new JDropDownButton(Images.DROPDOWN.getIcon());
		jDropDownButton.setToolTipText("Data Tools");
		jDropDownButton.setPopupHorizontalAlignment(JDropDownButton.LEFT);
		jDropDownButton.setPopupVerticalAlignment(JDropDownButton.BOTTOM);

		jAddMech = new JMenuItem("Add Mech", Images.ADD.getIcon());
		jAddMech.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMech();
			}
		});
		jDropDownButton.add(jAddMech, false);

		jDeleteMech = new JMenuItem("Delete Mech", Images.DELETE.getIcon());
		jDeleteMech.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMech();
			}
		});
		jDropDownButton.add(jDeleteMech, false);

		jDropDeck = new JMenuItem("Set drop deck limit", Images.DROPDECK.getIcon());
		jDropDeck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDropDeckLimit();
			}
		});
		jDropDownButton.add(jDropDeck, false);

		jWeightInfo1 = GuiFactory.createLabel(false);
		jWeightInfo2 = GuiFactory.createLabel(false);
		jWeightInfo3 = GuiFactory.createLabel(false);
		jWeightInfo4 = GuiFactory.createLabel(false);

		jCopyright = GuiFactory.createLabel(true);
		jCopyright.setBorder(null);
		jCopyright.setText("Copyright 2016 Niklas Kyster Rasmussen");

		jLicense = GuiFactory.createLabel(true);
		jLicense.setBorder(null);
		jLicense.setText("License: GNU General Public License 2.0");

		jVersion = GuiFactory.createLabel(true);
		jVersion.setBorder(null);
		jVersion.setText("Version: 1.1.0");

		layout.setHorizontalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jShowInfo)
						.addComponent(jLock)
						.addComponent(jDropDownButton)
					)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jFree, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
					)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jLock1)
							.addComponent(jShowInfo1)
							.addComponent(jMech1)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jWeightInfo1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
							.addComponent(jIs1Scroll, 150, 150, Integer.MAX_VALUE)
							.addComponent(jClan1Scroll, 150, 150, Integer.MAX_VALUE)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jLock2)
							.addComponent(jShowInfo2)
							.addComponent(jMech2)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jWeightInfo2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
							.addComponent(jIs2Scroll, 150, 150, Integer.MAX_VALUE)
							.addComponent(jClan2Scroll, 150, 150, Integer.MAX_VALUE)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jLock3)
							.addComponent(jShowInfo3)
							.addComponent(jMech3)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jWeightInfo3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
							.addComponent(jIs3Scroll, 150, 150, Integer.MAX_VALUE)
							.addComponent(jClan3Scroll, 150, 150, Integer.MAX_VALUE)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jLock4)
							.addComponent(jShowInfo4)
							.addComponent(jMech4)
						)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(jWeightInfo4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
							.addComponent(jIs4Scroll, 150, 150, Integer.MAX_VALUE)
							.addComponent(jClan4Scroll, 150, 150, Integer.MAX_VALUE)
						)
					)
				)
			)
			.addComponent(jCopyright, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
			.addComponent(jLicense, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
			.addComponent(jVersion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE)
		);
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
					.addComponent(jShowInfo, 22, 22, 22)
					.addComponent(jLock, 22, 22, 22)
					.addComponent(jDropDownButton, 22, 22, 22)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(jFree, 22, 22, 22)
				)
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
							.addComponent(jWeightInfo1)
							.addComponent(jWeightInfo2)
							.addComponent(jWeightInfo3)
							.addComponent(jWeightInfo4)
						)
						.addGroup(layout.createParallelGroup()
							.addComponent(jIs1Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jIs2Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jIs3Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jIs4Scroll, 200, 200, Integer.MAX_VALUE)
						)
						.addGroup(layout.createParallelGroup()
							.addComponent(jClan1Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jClan2Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jClan3Scroll, 200, 200, Integer.MAX_VALUE)
							.addComponent(jClan4Scroll, 200, 200, Integer.MAX_VALUE)
						)
					)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
							.addComponent(jShowInfo1)
							.addComponent(jShowInfo2)
							.addComponent(jShowInfo3)
							.addComponent(jShowInfo4)
						)
						.addGroup(layout.createParallelGroup()
							.addComponent(jLock1)
							.addComponent(jLock2)
							.addComponent(jLock3)
							.addComponent(jLock4)
						)
						.addGroup(layout.createParallelGroup()
							.addComponent(jMech1, 400, 400, GroupLayout.DEFAULT_SIZE)
							.addComponent(jMech2)
							.addComponent(jMech3)
							.addComponent(jMech4)
						)
					)
				)
				.addComponent(jCopyright)
				.addComponent(jLicense)
				.addComponent(jVersion)
		);
		validateSliders();
	}

	public List<Integer> getTons() {
		return tons;
	}

	public JPanel getPanel() {
		return jPanel;
	}

	private void show() {
		boolean show = jShowInfo.isSelected();
		jShowInfo1.setSelected(show);
		jShowInfo2.setSelected(show);
		jShowInfo3.setSelected(show);
		jShowInfo4.setSelected(show);
		updateShow(show, jIs1Scroll, jClan1Scroll, jWeightInfo1);
		updateShow(show, jIs2Scroll, jClan2Scroll, jWeightInfo2);
		updateShow(show, jIs3Scroll, jClan3Scroll, jWeightInfo3);
		updateShow(show, jIs4Scroll, jClan4Scroll, jWeightInfo4);
	}

	private void setDropDeckLimit() {
		Integer dropDeck = InputUtil.setDropDeckLimit(program);
		if (dropDeck != null) {
			program.getSettings().setDropDeck(dropDeck);
			program.saveSettings();
			validateSliders();
		}
	}

	private void deleteMech() {
		Mech mech = InputUtil.deleteMech(program);
		if (mech == null) {
			return;
		}
		program.getSettings().removeMech(mech);
		updateMinMax();
		program.saveSettings();
	}

	private void addMech() {
		Mech mech = InputUtil.addMech(program);
		if (mech == null) {
			return;
		}
		program.getSettings().addMech(mech);
		updateMinMax();
		program.saveSettings();
	}

	private void updateMinMax() {
		//Update Min/Max ton
		program.getSettings().resetMinMax();
		for (Map.Entry<Integer, List<Mech>> entry : program.getSettings().getIsMechs().entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				program.getSettings().updateMinMax(entry.getKey());
			}
		}
		for (Map.Entry<Integer, List<Mech>> entry : program.getSettings().getClanMechs().entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty()) {
				program.getSettings().updateMinMax(entry.getKey());
			}
		}
		//Save ton, before reset
		int ton1 = getTons(jMech1);
		int ton2 = getTons(jMech2);
		int ton3 = getTons(jMech3);
		int ton4 = getTons(jMech4);

		//Update ton array
		tons.clear();
		for (int i = program.getSettings().getMinWeight(); i <= program.getSettings().getMaxWeight(); i = i + 5) {
			tons.add(i);
		}
		//Update JSlider labels
		Dictionary<Integer, JLabel> labelTable = new DictionaryMap<Integer, JLabel>();
		for (int i = 0; i < tons.size(); i++) {
			labelTable.put(i, new JLabel(String.valueOf(tons.get(i))));
		}

		validating = true;
		jMech1.setMaximum(tons.size() - 1);
		jMech1.setLabelTable(labelTable);
		setTons(jMech1, ton1);

		jMech2.setMaximum(tons.size() - 1);
		jMech2.setLabelTable(labelTable);
		setTons(jMech2, ton2);

		jMech3.setMaximum(tons.size() - 1);
		jMech3.setLabelTable(labelTable);
		setTons(jMech3, ton3);

		jMech4.setMaximum(tons.size() - 1);
		jMech4.setLabelTable(labelTable);
		setTons(jMech4, ton4);
		validating = false;
		validateSliders();
	}

	public void updateLock() {
		jLock.setSelected(jLock1.isSelected()
				&& jLock2.isSelected()
				&& jLock3.isSelected()
				&& jLock4.isSelected());
	}

	public void updateShow(int index) {
		jShowInfo.setSelected(jShowInfo1.isSelected()
						&& jShowInfo2.isSelected()
						&& jShowInfo3.isSelected()
						&& jShowInfo4.isSelected());
		switch(index) {
			case 1:
				updateShow(jShowInfo1, jIs1Scroll, jClan1Scroll, jWeightInfo1);
				break;
			case 2:
				updateShow(jShowInfo2, jIs2Scroll, jClan2Scroll, jWeightInfo2);
				break;
			case 3:
				updateShow(jShowInfo3, jIs3Scroll, jClan3Scroll, jWeightInfo3);
				break;
			case 4:
				updateShow(jShowInfo4, jIs4Scroll, jClan4Scroll, jWeightInfo4);
				break;
		}
	}

	private void updateShow(JToggleButton jShow, JScrollPane jIs, JScrollPane jClan, JLabel jWeight) {
		updateShow(jShow.isSelected(), jIs, jClan, jWeight);
	}

	private void updateShow(boolean show, JScrollPane jIs, JScrollPane jClan, JLabel jWeight) {
		jIs.setVisible(show);
		jClan.setVisible(show);
		jWeight.setVisible(show);
		program.getMainWindow().pack();
	}

	private void validateSliders() {
		validateSliders(jEmpty);
	}

	public void validateSliders(JSlider jSource) {
		if (validating) { //Ignore all updates while we're validating
			return;
		}
		validating = true; //We're now validating

		int free = getFree(); //Get deficit/excess tons
		int min = program.getSettings().getMinWeight(); //Max tons
		int max = program.getSettings().getMaxWeight(); //Min tons
		//Add editable values
		List<Value> values = new ArrayList<Value>();
		if (!jSource.equals(jMech1) && !jLock1.isSelected()) {
			values.add(new Value(min, max, getTons(jMech1), 1));
		}
		if (!jSource.equals(jMech2) && !jLock2.isSelected()) {
			values.add(new Value(min, max, getTons(jMech2), 2));
		}
		if (!jSource.equals(jMech3) && !jLock3.isSelected()) {
			values.add(new Value(min, max, getTons(jMech3), 3));
		}
		if (!jSource.equals(jMech4) && !jLock4.isSelected()) {
			values.add(new Value(min, max, getTons(jMech4), 4));
		}
		Comparator<Value> comparator;
		if (free > 0) {
			comparator = new PositiveComparator();
		} else {
			comparator = new NegativeComparator();
		}
		if (values.isEmpty()) { //Nothing is editable (except for source)
			if (free > 0) {
				//Unused Tons - No problem, we can bellow ton limit (will be shown in status)
			} else if (jSource.equals(jEmpty)) {
				//Over ton limit - No source, no reset possible (will be shown in status)
			} else {
				//Over ton limit - reset source
				setTons(jSource, getTons(jSource) + free); //Function is safe - can deal with values above max and bellow min
			}
		} else { //At least one slider is editable
			while (free != 0) { //Distribute deficit/excess
				Collections.sort(values, comparator);
				boolean updated = false;
				for (Value value : values){ //Search for first free tons
					if (free > 0 && value.canIncrease()) { //excess
						free = free - 5;
						value.increase();
						updated = true;
						break;
					} else if (free < 0 && value.canDecrease()) { //deficit
						free = free + 5;
						value.decrease();
						updated = true;
						break;
					}
				}
				if (!updated) { //Didn't find anywhere to distribute the deficit/excess
					if (jSource.equals(jEmpty)) {
						//No source - no reset possible (will be shown in status)
					} else { //Reset sourse
						int newValue = getTons(jSource) + free;
						setTons(jSource, newValue); //Function is safe - can deal with values above max and bellow min
						
					}
					break;
				}
			}
			//Update sliders with new values
			for (Value value : values) {
				switch (value.getIndex()) {
					case 1:
						setTons(jMech1, value.getValue());
						break;
					case 2:
						setTons(jMech2, value.getValue());
						break;
					case 3:
						setTons(jMech3, value.getValue());
						break;
					case 4:
						setTons(jMech4, value.getValue());
						break;
				}
			}
		}
		//Update IS mechs
		updateIsGroup(jMech1, jIs1);
		updateIsGroup(jMech2, jIs2);
		updateIsGroup(jMech3, jIs3);
		updateIsGroup(jMech4, jIs4);
		//Update Clan mechs
		updateClanGroup(jMech1, jClan1);
		updateClanGroup(jMech2, jClan2);
		updateClanGroup(jMech3, jClan3);
		updateClanGroup(jMech4, jClan4);
		//Update weight
		updateLabel(jMech1, jWeightInfo1);
		updateLabel(jMech2, jWeightInfo2);
		updateLabel(jMech3, jWeightInfo3);
		updateLabel(jMech4, jWeightInfo4);
		//Update status
		free = getFree();
		if (free > 0) {
			jFree.setText("Unused Weight: " + free + "t");
			jFree.setBackground(new Color(255, 255, 200));
		} else if (free < 0) {
			jFree.setText("Too much weight: " + free + "t");
			jFree.setBackground(new Color(255, 200, 200));
		} else {
			jFree.setText("OK");
			jFree.setBackground(new Color(200, 255, 200));
		}
		validating = false; //Done validating
	}

	private void updateLabel(JSlider jMech, JLabel jWeight) {
		jWeight.setText(getTons(jMech) + "t");
	}

	private void updateIsGroup(JSlider jMech, JTextArea jGroup) {
		int mech = getTons(jMech);
		updateGroup(jGroup, program.getSettings().getIsMechs().get(mech));
	}
	private void updateClanGroup(JSlider jMech, JTextArea jGroup) {
		int mech = getTons(jMech);
		updateGroup(jGroup, program.getSettings().getClanMechs().get(mech));
	}

	private void updateGroup(JTextArea jGroup, List<Mech> mechs) {
		StringBuilder builder = new StringBuilder();
		if (mechs != null) {
			for (Mech mech : mechs) {
				builder.append(mech.getName());
				builder.append("\r\n\r\n");
			}
		}
		jGroup.setText(builder.toString());
	}

	private int getFree() {
		return program.getSettings().getDropDeck() - (getTons(jMech1) + getTons(jMech2) + getTons(jMech3) + getTons(jMech4));
	}

	private int getTons(JSlider jSlider) {
		return tons.get(jSlider.getValue());
	}

	private void setTons(JSlider jSlider, int weight) {
		if (tons.contains(weight)) { //New value is valid
			jSlider.setValue(tons.indexOf(weight));
		} else if (tons.isEmpty()) {
			//Do nothing
		} else if (weight < tons.get(0)) { //New value bellow min => set value to lowest possible
			jSlider.setValue(0);
		} else if (weight > tons.get(tons.size() - 1)) {  //New value above max => set value to hihgest possible
			jSlider.setValue(tons.size() - 1);
		}
	}

	private static class Value implements Comparable<Value>{
		private final int min;
		private final int max;
		private int value;
		private final int index;

		public Value(int min, int max, int value, int index) {
			this.min = min;
			this.max = max;
			this.value = value;
			this.index = index;
		}

		public boolean canIncrease() {
			return value < max;
		}

		public boolean canDecrease() {
			return value > min;
		}

		public void increase() {
			value = value + 5;
		}

		public void decrease() {
			value = value - 5;
		}

		public Integer getValue() {
			return value;
		}

		public Integer getIndex() {
			return index;
		}

		@Override
		public int compareTo(Value o) {
			int compared = o.getValue().compareTo(getValue());
			if (compared != 0) {
				return compared;
			} else {
				return getIndex().compareTo(o.getIndex());
			}
		}
	}

	private static class PositiveComparator implements Comparator<Value> {

		@Override
		public int compare(Value o1, Value o2) {
			int compared = o1.getValue().compareTo(o2.getValue());
			if (compared != 0) {
				return compared;
			} else {
				return o1.getIndex().compareTo(o2.getIndex());
			}
		}
		
	}

	private static class NegativeComparator implements Comparator<Value> {

		@Override
		public int compare(Value o1, Value o2) {
			int compared = o2.getValue().compareTo(o1.getValue());
			if (compared != 0) {
				return compared;
			} else {
				return o1.getIndex().compareTo(o2.getIndex());
			}
		}
	}
}
