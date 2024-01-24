package com.santhosh.loganalysis;

import com.santhosh.loganalysis.utils.FileProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionMatcher {
    public static String matchRegularExpressionsWithLines(String[] lines, String xmlContent) throws Exception {
        StringBuilder output = new StringBuilder();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xmlContent));
        Document doc = builder.parse(src);
        NodeList expressions = doc.getElementsByTagName("expression");
        NodeList notFoundStrings = doc.getElementsByTagName("not-found");

        int previousMatchedLine = -1; // Initialize with -1 to indicate no previous match
        boolean issueOccurred = false;
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
                    output.append(line).append("\n");
                    previousMatchedLine = j; // Update the previous matched line
                    break;
                }
            }

            if (!matched) {
                output.append("\n").append(notFoundString).append("\n");
                issueOccurred = true;
                break; // Stop checking further regular expressions
            }
        }
        if (!issueOccurred) {
            output.append("\nNo issue observed from the logs");
        }
        return output.toString();
    }

    public String analyse(String fileName, File useCase) throws IOException {
        String[] lines = FileProcessor.readLinesFromFile(fileName);
        String xmlString;
        try {
            xmlString = FileProcessor.xmlToString(useCase);
            return matchRegularExpressionsWithLines(lines, xmlString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}