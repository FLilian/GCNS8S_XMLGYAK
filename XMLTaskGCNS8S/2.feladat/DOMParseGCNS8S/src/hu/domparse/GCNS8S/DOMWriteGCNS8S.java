package hu.domparse.GCNS8S;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMWriteGCNS8S {

    public static void main(String[] args) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("facebook");
            rootElement.setAttribute("xmlns", "file://C:/Users/flask/IdeaProjects/DOMParseGCNS8S/src/Sources/scheme_GCNS8S.xsd");
            doc.appendChild(rootElement);

            // Create and append User elements
            createAndAppendUser(doc, rootElement, "1", "Lili", "john@example.com", Arrays.asList("Theme1", "Theme1.2", "Theme1.3"),
                    "3600", "Budapest", "Main Street 6");

            createAndAppendUser(doc, rootElement, "2", "Jane Smith", "jane@example.com", Arrays.asList("Theme2", "Theme2.1"),
                    "7400", "New York", "First Avenue 4");

            createAndAppendUser(doc, rootElement, "3", "Bob Johnson", "bob@example.com", Arrays.asList("Theme3"),
                    "8900", "London", "Oxford street 5");

            // Create and append Post elements
            createAndAppendPost(doc, rootElement, "101", "This is the first post.", "2023-01-01", "5");
            createAndAppendPost(doc, rootElement, "102", "Another post here.", "2023-02-01", "8");
            createAndAppendPost(doc, rootElement, "103", "Yet another post.", "2023-03-01", "12");

            // Create and append Image elements
            createAndAppendImage(doc, rootElement, "201", "image1.jpg", "1024", "1920x1080");
            createAndAppendImage(doc, rootElement, "202", "image2.jpg", "2048", "2560x1440");
            createAndAppendImage(doc, rootElement, "203", "image3.jpg", "4096", "3840x2160");

            // Create and append Comment elements
            createAndAppendComment(doc, rootElement, "301", "101", "1", "Comment 1", "2023-01-05", "3");
            createAndAppendComment(doc, rootElement, "302", "102", "2", "Comment 2", "2023-02-10", "1");
            createAndAppendComment(doc, rootElement, "303", "103", "3", "Comment 3", "2023-03-15", "7");

            // Create and append Group elements
            createAndAppendGroup(doc, rootElement, "401", "Developers", "Programming enthusiasts", "10");
            createAndAppendGroup(doc, rootElement, "402", "Photographers", "Passionate about photography", "8");
            createAndAppendGroup(doc, rootElement, "403", "Travelers", "Exploring the world", "15");

            // Create and append SignedUp elements
            createAndAppendSignedUp(doc, rootElement, "401", "1", "2023-01-01");
            createAndAppendSignedUp(doc, rootElement, "402", "2", "2023-02-01");
            createAndAppendSignedUp(doc, rootElement, "403", "3", "2023-03-01");

            // Print to console
            printToConsole(doc);

            // Write to XML file
            writeToXmlFile(doc, "XMLGCNS8S.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XML dokumentum kiírása a konzolra
    private static void printToConsole(Document doc) {
        try {
            // Formázó beállítások, beleértve az UTF-8 karakterkódolást
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  // Ez beállítja az UTF-8 karakterkódolást
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // XML kód kiírása a konzolra
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper methods to create and append elements

    private static void createAndAppendUser(Document doc, Element rootElement, String userID, String name,
                                            String email, List<String> userThemes, String zipCode, String city, String streetAddress) {

        // Create User element
        Element userElement = doc.createElement("User");
        userElement.setAttribute("UserID", userID);

        // Create and append child elements of User
        appendChildElement(doc, userElement, "Name", name);
        appendChildElement(doc, userElement, "Email", email);

        // Create and append multiple UserTheme elements
        for (String userTheme : userThemes) {
            appendChildElement(doc, userElement, "UserTheme", userTheme);
        }

        // Create and append Address element
        Element addressElement = doc.createElement("Address");
        appendChildElement(doc, addressElement, "ZipCode", zipCode);
        appendChildElement(doc, addressElement, "City", city);
        appendChildElement(doc, addressElement, "StreetAddress", streetAddress);

        // Append Address element to User
        userElement.appendChild(addressElement);

        // Append User element to root element
        rootElement.appendChild(userElement);
    }

    private static void createAndAppendPost(Document doc, Element rootElement, String postID, String text, String date, String likesNumber) {
        // Create Post element
        Element postElement = doc.createElement("Post");
        postElement.setAttribute("PostID", postID);

        // Create and append child elements of Post
        appendChildElement(doc, postElement, "Text", text);
        appendChildElement(doc, postElement, "Date", date);
        appendChildElement(doc, postElement, "LikesNumber", likesNumber);

        // Append Post element to root element
        rootElement.appendChild(postElement);
    }

    private static void createAndAppendImage(Document doc, Element rootElement, String imageID, String imgName, String imgSize, String resolution) {
        // Create Image element
        Element imageElement = doc.createElement("Image");
        imageElement.setAttribute("ImageID", imageID);

        // Create and append child elements of Image
        appendChildElement(doc, imageElement, "ImgName", imgName);
        appendChildElement(doc, imageElement, "ImgSize", imgSize);
        appendChildElement(doc, imageElement, "Resolution", resolution);

        // Append Image element to root element
        rootElement.appendChild(imageElement);
    }

    private static void createAndAppendComment(Document doc, Element rootElement, String commentID, String postID, String userID,
                                               String comText, String comDate, String likesNum) {
        // Create Comment element
        Element commentElement = doc.createElement("Comment");
        commentElement.setAttribute("CommentID", commentID);
        commentElement.setAttribute("PostID", postID);
        commentElement.setAttribute("UserID", userID);

        // Create and append child elements of Comment
        appendChildElement(doc, commentElement, "ComText", comText);
        appendChildElement(doc, commentElement, "ComDate", comDate);
        appendChildElement(doc, commentElement, "LikesNum", likesNum);

        // Append Comment element to root element
        rootElement.appendChild(commentElement);
    }

    private static void createAndAppendGroup(Document doc, Element rootElement, String groupID, String groupName,
                                             String description, String membersNum) {
        // Create Group element
        Element groupElement = doc.createElement("Group");
        groupElement.setAttribute("GroupID", groupID);

        // Create and append child elements of Group
        appendChildElement(doc, groupElement, "GroupName", groupName);
        appendChildElement(doc, groupElement, "Description", description);
        appendChildElement(doc, groupElement, "MembersNum", membersNum);

        // Append Group element to root element
        rootElement.appendChild(groupElement);
    }

    private static void createAndAppendSignedUp(Document doc, Element rootElement, String groupID, String userID, String joinDate) {
        // Create SignedUp element
        Element signedUpElement = doc.createElement("SignedUp");
        signedUpElement.setAttribute("GroupID", groupID);
        signedUpElement.setAttribute("UserID", userID);

        // Create and append child element of SignedUp
        appendChildElement(doc, signedUpElement, "JoinDate", joinDate);

        // Append SignedUp element to root element
        rootElement.appendChild(signedUpElement);
    }

    private static void appendChildElement(Document doc, Element parentElement, String childName, String childValue) {
        Element childElement = doc.createElement(childName);
        childElement.appendChild(doc.createTextNode(childValue));
        parentElement.appendChild(childElement);
    }

    // Write the XML document to a file
    private static void writeToXmlFile(Document doc, String filename) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(filename));

            transformer.transform(source, result);
            System.out.println("XML written to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
