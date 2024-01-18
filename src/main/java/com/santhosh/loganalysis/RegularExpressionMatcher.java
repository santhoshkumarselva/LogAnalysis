package main.java.com.santhosh.loganalysis;

import utils.FileProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionMatcher {
    public static void matchRegularExpressionsWithLines(String[] lines, String xmlContent) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xmlContent));
            Document doc = builder.parse(src);
            NodeList expressions = doc.getElementsByTagName("expression");
            NodeList notFoundStrings = doc.getElementsByTagName("not-found");

            int previousMatchedLine = -1; // Initialize with -1 to indicate no previous match
            boolean issueOccured = false;
            for (int i = 0; i < expressions.getLength(); i++) {
                String pattern = expressions.item(i).getTextContent();
                String notFoundString = notFoundStrings.item(i).getTextContent();
                Pattern p = Pattern.compile(pattern);
                boolean matched = false;

                for (int j = previousMatchedLine + 1; j < lines.length; j++) {
                    String line = lines[j];
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        matched = true;
                        System.out.println(line);
                        previousMatchedLine = j; // Update the previous matched line
                        break;
                    }
                }

                if (!matched) {
                    System.out.println(notFoundString);
                    issueOccured = true;
                    break; // Stop checking further regular expressions
                }
            }
            if(!issueOccured) System.out.println("\nNo issue observed from the logs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String[] lines = FileProcessor.readLinesFromFile("wired-connection.log");
        String xmlString;
        try {
            xmlString = FileProcessor.xmlToString("wired_carplay_connection.xml");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        matchRegularExpressionsWithLines(lines, xmlString);
    }
}