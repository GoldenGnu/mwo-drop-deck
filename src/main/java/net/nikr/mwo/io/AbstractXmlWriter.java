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

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;


public abstract class AbstractXmlWriter {

	protected Document getXmlDocument(final String rootname) throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
			return impl.createDocument(null, rootname, null);
		} catch (ParserConfigurationException ex) {
			throw new Exception(ex.getMessage(), ex);
		}
	}

	protected void writeXmlFile(final Document doc, final String filename) throws Exception {
		writeXmlFile(doc, filename, "UTF-8");
	}

	private void writeXmlFile(final Document doc, final String filename, final String encoding) throws Exception {
		DOMSource source = new DOMSource(doc);
		FileOutputStream outputStream = null;
		File file = new File(filename);
		try {
			//Save file
			outputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, encoding);
			// result
			Result result = new StreamResult(outputStreamWriter);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			transformer.transform(source, result);
		} catch (FileNotFoundException ex) {
			throw new Exception(ex.getMessage(), ex);
		} catch (TransformerConfigurationException ex) {
			throw new Exception(ex.getMessage(), ex);
		} catch (TransformerException ex) {
			throw new Exception(ex.getMessage(), ex);
		} catch (UnsupportedEncodingException ex) {
			throw new Exception(ex.getMessage(), ex);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ex) {
					throw new Exception(ex.getMessage(), ex);
				}
			}
		}
	}
}
