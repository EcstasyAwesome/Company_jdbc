package com.company.util;

import javax.validation.constraints.NotNull;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SettingsXmlReader {

    private static final String SETTINGS = SettingsXmlReader.class.getClassLoader().getResource("/").getPath() + "settings.xml";

    public static String getValue(@NotNull String settings) throws XMLStreamException, FileNotFoundException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = factory.createXMLStreamReader(SETTINGS, new FileInputStream(SETTINGS));
        String result = null;
        while (streamReader.hasNext()) {
            streamReader.next();
            if (streamReader.isStartElement() && streamReader.getLocalName().equalsIgnoreCase(settings)) {
                streamReader.next();
                result = streamReader.getText();
            }
        }
        streamReader.close();
        return result;
    }
}