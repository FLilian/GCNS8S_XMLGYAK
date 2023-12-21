package hu.domparse.GCNS8S;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;
import java.io.*;

public class DomReadGCNS8S {
    public static void main(String[] args) {
        try {
            // XML fájl beolvasása
            File xmlFile = new File("C:\\Users\\flask\\IdeaProjects\\DOMParseGCNS8S\\src\\Sources\\facebookGCNS8S.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Gyökérelem és prolog kiírása
            Element rootElement = doc.getDocumentElement();
            String rootName = rootElement.getTagName();
            NamedNodeMap rootAttributeMap = rootElement.getAttributes();

            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            System.out.println("<" + rootName + getAttributesAsString(rootAttributeMap) + ">\n");

            // User elemek feldolgozása
            NodeList userNodes = doc.getElementsByTagName("User");
            NodeList postNodes = doc.getElementsByTagName("Post");
            NodeList imageNodes = doc.getElementsByTagName("Image");
            NodeList commentNodes = doc.getElementsByTagName("Comment");
            NodeList groupNodes = doc.getElementsByTagName("Group");
            NodeList signedUpNodes = doc.getElementsByTagName("SignedUp");

            printNodeList(userNodes, 1);
            printNodeList(postNodes, 1);
            printNodeList(imageNodes, 1);
            printNodeList(commentNodes, 1);
            printNodeList(groupNodes, 1);
            printNodeList(signedUpNodes, 1);

            System.out.println("</" + rootName + ">");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // NodeList tartalmának kiírása
    private static void printNodeList(NodeList nodeList, int indent) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            printNode(node, indent);
            System.out.println(" ");
        }
    }

    // Egy Node tartalmának kiírása
    private static void printNode(Node node, int indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String nodeName = element.getTagName();
            NamedNodeMap attributeMap = element.getAttributes();

            // Kiírja az elem nyitó tagjét és attribútumait
            System.out.print(getIndentString(indent));
            System.out.print("<" + nodeName + getAttributesAsString(attributeMap) + ">");

            // Tartalom kiírása
            NodeList children = element.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                // Ha csak szöveg van az elemben, azt kiírja
                System.out.print(children.item(0).getNodeValue());
            } else {
                // Külön sorban kiírja az elem tartalmát
                System.out.println();
                for (int i = 0; i < children.getLength(); i++) {
                    printNode(children.item(i), indent + 1);
                }
                System.out.print(getIndentString(indent));
            }

            // Elem lezárása
            System.out.println("</" + nodeName + ">");
        }
    }

    // Attribútumok kiírása String formában
    private static String getAttributesAsString(NamedNodeMap attributeMap) {
        StringBuilder attributes = new StringBuilder();
        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attribute = attributeMap.item(i);
            attributes.append(" ")
                    .append(attribute.getNodeName())
                    .append("=\"")
                    .append(attribute.getNodeValue())
                    .append("\"");
        }
        return attributes.toString();
    }

    // Szóköz karakterekkel való indentálás előállítása
    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  "); // 2 spaces per indent level
        }
        return sb.toString();
    }
}
