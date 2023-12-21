package hu.domparse.GCNS8S;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;


public class DomModifyGCNS8S {

    public static void main(String[] args) {
        try {
            Document doc = readXmlDocument("C:\\Users\\flask\\IdeaProjects\\DOMParseGCNS8S\\src\\Sources\\facebookGCNS8S.xml");

            // Módosítások
            modifyUser(doc, 2);
            modifyPosts(doc);
            addNewUser(doc);
            modifyGroupMembers(doc);
            modifyCommentText(doc);

            // Kiírás XML formában
            printDocument(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document readXmlDocument(String fileName) throws Exception {
        File inputFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputFile);
    }

    private static void modifyUser(Document doc, int userId) {
        NodeList userList = doc.getElementsByTagName("User");

        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                int currentUserId = Integer.parseInt(userElement.getAttribute("UserID"));

                if (userId == currentUserId) {
                    // Módosítjuk a felhasználó nevét, példa: "ModifiedName by Lili"
                    userElement.getElementsByTagName("Name").item(0).setTextContent("ModifiedName by Lili");
                    break; // Kilépünk a ciklusból, mivel a keresett felhasználót megtaláltuk
                }
            }
        }
    }



    private static void modifyPosts(Document doc) {
        NodeList postList = doc.getElementsByTagName("Post");

        for (int i = 0; i < postList.getLength(); i++) {
            Node postNode = postList.item(i);
            if (postNode.getNodeType() == Node.ELEMENT_NODE) {
                Element postElement = (Element) postNode;


                // Módosítjuk a Postok szövegét, példa: "Modified by User"
                postElement.getElementsByTagName("Text").item(0).setTextContent("Modified by User");
            }
        }
    }

    private static void addNewUser(Document doc) {
        Element rootElement = doc.getDocumentElement();

        // Új User elem hozzáadása
        Element newUserElement = doc.createElement("User");
        newUserElement.setAttribute("UserID", "8");

        // Name
        Element nameElement = doc.createElement("Name");
        nameElement.appendChild(doc.createTextNode("New User"));
        newUserElement.appendChild(nameElement);

        // UserTheme
        Element themeElement = doc.createElement("UserTheme");
        themeElement.appendChild(doc.createTextNode("New Theme"));
        newUserElement.appendChild(themeElement);

        // Email
        Element emailElement = doc.createElement("Email");
        emailElement.appendChild(doc.createTextNode("new.user@example.com"));
        newUserElement.appendChild(emailElement);

        // Address
        Element addressElement = doc.createElement("Address");

        Element zipCodeElement = doc.createElement("ZipCode");
        zipCodeElement.appendChild(doc.createTextNode("12345"));
        addressElement.appendChild(zipCodeElement);

        Element cityElement = doc.createElement("City");
        cityElement.appendChild(doc.createTextNode("New City"));
        addressElement.appendChild(cityElement);

        Element streetAddressElement = doc.createElement("StreetAddress");
        streetAddressElement.appendChild(doc.createTextNode("New Street 1"));
        addressElement.appendChild(streetAddressElement);

        newUserElement.appendChild(addressElement);

        rootElement.appendChild(newUserElement);
    }


    private static void modifyGroupMembers(Document doc) {
        NodeList groupList = doc.getElementsByTagName("Group");

        for (int i = 0; i < groupList.getLength(); i++) {
            Node groupNode = groupList.item(i);
            if (groupNode.getNodeType() == Node.ELEMENT_NODE) {
                Element groupElement = (Element) groupNode;

                // Növeljük a GroupMembers számát 1-gyel, ha 4-nél alacsonyabb
                int groupMembers = Integer.parseInt(groupElement.getElementsByTagName("MembersNum").item(0).getTextContent());
                if (groupMembers < 4) {
                    groupElement.getElementsByTagName("MembersNum").item(0).setTextContent(String.valueOf(groupMembers + 1));
                }
            }
        }
    }

    private static void modifyCommentText(Document doc) {
        NodeList commentList = doc.getElementsByTagName("Comment");

        for (int i = 0; i < commentList.getLength(); i++) {
            Node commentNode = commentList.item(i);
            if (commentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element commentElement = (Element) commentNode;

                // Módosítjuk a CommentText értékét "Modified by Lili"-re
                commentElement.getElementsByTagName("ComText").item(0).setTextContent("Modified by Lili");
            }
        }
    }


    private static void printDocument(Document doc) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String output = writer.getBuffer().toString();
        System.out.println(output);
    }
    }



