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
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public abstract class AbstractXmlReader {

	protected Element getDocumentElement(final String filename, final boolean fileLock) throws Exception {
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document doc;
		FileInputStream is = null;
		File file = new File(filename);
		try {
			is = new FileInputStream(file);
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			return doc.getDocumentElement();
		} catch (SAXException ex) {
			throw new Exception(ex.getMessage(), ex);
		} catch (ParserConfigurationException ex) {
			throw new Exception(ex.getMessage(), ex);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					throw new Exception(ex.getMessage(), ex);
				}
			}
		}
	}

	
}
