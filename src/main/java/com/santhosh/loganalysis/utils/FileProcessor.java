package com.santhosh.loganalysis.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class FileProcessor {
    public static String[] readLinesFromFile(String filePath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        ClassLoader classLoader = FileProcessor.class.getClassLoader();
        BufferedReader reader = new BufferedReader(new FileReader(new File(classLoader.getResource(filePath).getFile())));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines.toArray(new String[0]);
    }
    public static String xmlToString(String filePath) throws IOException, SAXException, TransformerException, ParserConfigurationException {
        ClassLoader classLoader = FileProcessor.class.getClassLoader();
        File xmlFile = new File(classLoader.getResource(filePath).getFile());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

        return stringWriter.getBuffer().toString();
    }
}