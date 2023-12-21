package hu.domparse.GCNS8S;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class DomQueryGCNS8S {
        public static void main(String[] args) {
            try {
                File inputFile = new File("C:\\Users\\flask\\IdeaProjects\\DOMParseGCNS8S\\src\\Sources\\facebookGCNS8S.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                // 1. Az users összes adatának lekérdezése
                NodeList userList = doc.getElementsByTagName("User");
                System.out.println("Az users összes adata:");
                for (int i = 0; i < userList.getLength(); i++) {
                    Node node = userList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element user = (Element) node;
                        System.out.println("UserID: " + user.getAttribute("UserID"));
                        System.out.println("Name: " + user.getElementsByTagName("Name").item(0).getTextContent());
                        System.out.println("Email: " + user.getElementsByTagName("Email").item(0).getTextContent());
                        System.out.println("UserThemes:");
                        NodeList userThemeList = user.getElementsByTagName("UserTheme");
                        for (int j = 0; j < userThemeList.getLength(); j++) {
                            System.out.println("  " + userThemeList.item(j).getTextContent());
                        }
                        Element address = (Element) user.getElementsByTagName("Address").item(0);
                        System.out.println("Address:");
                        System.out.println("  ZipCode: " + address.getElementsByTagName("ZipCode").item(0).getTextContent());
                        System.out.println("  City: " + address.getElementsByTagName("City").item(0).getTextContent());
                        System.out.println("  StreetAddress: " + address.getElementsByTagName("StreetAddress").item(0).getTextContent());
                        System.out.println();
                    }
                }

                // 2. Az 1-es UserID-jú post text-jének és idejének lekérdezése
                String userIDToQueryPost = "1";
                NodeList postList = doc.getElementsByTagName("Post");
                for (int i = 0; i < postList.getLength(); i++) {
                    Node node = postList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element post = (Element) node;
                        if (post.getAttribute("UserID").equals(userIDToQueryPost)) {
                            System.out.println("1 UserID-jú post adatai:");
                            System.out.println("Text: " + post.getElementsByTagName("Text").item(0).getTextContent());
                            System.out.println("Date: " + post.getElementsByTagName("Date").item(0).getTextContent());
                            System.out.println();
                            break;
                        }
                    }
                }

                // 3. A 102 PostID-júhoz tartozó kommentek lekérdezése
                String postIDToQueryComments = "102";
                NodeList commentList = doc.getElementsByTagName("Comment");
                for (int i = 0; i < commentList.getLength(); i++) {
                    Node node = commentList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element comment = (Element) node;
                        if (comment.getAttribute("PostID").equals(postIDToQueryComments)) {
                            System.out.println("102 PostID-júhoz tartozó komment adatai:");
                            System.out.println("CommentID: " + comment.getAttribute("CommentID"));
                            System.out.println("Date: " + comment.getElementsByTagName("ComDate").item(0).getTextContent());
                            System.out.println();
                        }
                    }
                }

                // 4. A 2000x2000-nél nagyobb felbontású képek nevének lekérdezése
                int resolutionThreshold = 2000;
                NodeList imageList = doc.getElementsByTagName("Image");
                System.out.println("2000x2000-nél nagyobb felbontású képek nevei:");
                for (int i = 0; i < imageList.getLength(); i++) {
                    Node node = imageList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element image = (Element) node;
                        String resolution = image.getElementsByTagName("Resolution").item(0).getTextContent();
                        String[] resolutionParts = resolution.split("x");
                        int width = Integer.parseInt(resolutionParts[0]);
                        int height = Integer.parseInt(resolutionParts[1]);
                        if (width > resolutionThreshold || height > resolutionThreshold) {
                            System.out.println(image.getElementsByTagName("ImgName").item(0).getTextContent());
                        }
                    }
                }

                // 5. Az már meglévő lekérdezés
                // 5. Példa: A legtöbb taggal rendelkező csoport lekérdezése
                NodeList groupList = doc.getElementsByTagName("Group");
                Map<String, Integer> groupMembersCount = new HashMap<>();
                for (int i = 0; i < groupList.getLength(); i++) {
                    Element groupElement = (Element) groupList.item(i);
                    String groupName = groupElement.getElementsByTagName("GroupName").item(0).getTextContent();
                    int membersNum = Integer.parseInt(groupElement.getElementsByTagName("MembersNum").item(0).getTextContent());
                    groupMembersCount.put(groupName, membersNum);
                }

                String groupWithMostMembers = findGroupWithMostMembers(groupMembersCount);

                if (groupWithMostMembers != null) {
                    System.out.println("### Group with most members: " + groupWithMostMembers + " ###");
                } else {
                    System.out.println("### There is no clear answer. Multiple groups have the same maximum number of members. ###");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    private static String findGroupWithMostMembers(Map<String, Integer> groupMembersCount) {
        String groupWithMostMembers = null;
        int maxMembers = -1;

        for (Map.Entry<String, Integer> entry : groupMembersCount.entrySet()) {
            if (entry.getValue() > maxMembers) {
                groupWithMostMembers = entry.getKey();
                maxMembers = entry.getValue();
            } else if (entry.getValue() == maxMembers) {
                groupWithMostMembers = null;
            }
        }

        return groupWithMostMembers;
    }
}


