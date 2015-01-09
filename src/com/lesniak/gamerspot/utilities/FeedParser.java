package com.lesniak.gamerspot.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lesniak.gamerspot.base.Feed;

public final class FeedParser {

	private static final String junkText = "<p style=\"padding:5px;background:#ffffcc;";
	
	public static ArrayList<Feed> parseDocuments(HashMap<String, Document> documentMap){
		
		int errors = 0;
		
		ArrayList<Feed> feedList = new ArrayList<Feed>();
		
		Iterator<String> documentIterator = documentMap.keySet().iterator();
		
		while(documentIterator.hasNext()) {
			
			String key = documentIterator.next();
			Document document = documentMap.get(key);
			
			if(document!=null && (document.getElementsByTagName("item"))!=null) {
				
				NodeList itemNodesList = document.getElementsByTagName("item");
				
				for(int i=0; i<itemNodesList.getLength(); i++) {
					
					Feed feed = new Feed();
					
					Node node = itemNodesList.item(i);

	                if (node.getNodeType() == Node.ELEMENT_NODE) {

	                    Element element = (Element) node;
	                    
	                    try{
	                    	feed.setTitle(retrieveTitle(element));
	                        feed.setLink(retrieveLink(element));
	                        feed.setDescription(retrievedDescription(element));
	        				feed.setGuid(retrieveGuid(element));
	        				feed.setPubDate(retrieveDate(element));
	        				feed.setCreator(retrieveCreator(element, key));
	                        feed.setService(key.toUpperCase());

	                        feedList.add(feed);
	                    }
	                    catch(Exception e) {
	                    	errors++;
	                    }
	                    
	                }
				}
				
			}
			
//			System.out.println((document == null) ? "DOCUMENT NULL" : "DOCUMENT EXISTS");
//			 System.out.println(key + "\nSUCCESS: " + feedList.size() + "\nERRORS: " + errors);
		}
		
		return feedList;
	}
	
	private static String retrieveTitle(Element element){
		return element.getElementsByTagName("title").item(0).getTextContent();
	}
	
	private static String retrieveLink(Element element){
		return element.getElementsByTagName("link").item(0).getTextContent();
	}

	private static String retrievedDescription(Element element){
		String description = element.getElementsByTagName("description").item(0).getTextContent();

        if (description.contains(junkText)) {
            String substring = description.substring(description.indexOf(junkText), description.length());
            description = description.replace(substring, "");
        }
        
        return description;
	}
	
	private static String retrieveGuid(Element element){
		String guid;
		NodeList guidNodeList = element.getElementsByTagName("guid");

        if (guidNodeList == null | guidNodeList.getLength() < 1) {
            guid = retrieveLink(element);
        } else {
            guid = guidNodeList.item(0).getTextContent();
        }
        
        return guid;
	}
	
	private static String retrieveDate(Element element){
		NodeList dateNodeList = element.getElementsByTagName("pubDate");

        if(dateNodeList == null || dateNodeList.getLength()<1) {
        	dateNodeList = element.getElementsByTagName("a10:updated");
        }
        
        return dateNodeList.item(0).getTextContent();   
	}
	
	private static String retrieveCreator(Element element, String defaultValue){
		String creator;
		NodeList creatorNodeList = element.getElementsByTagName("dc:creator");

        if (creatorNodeList == null | creatorNodeList.getLength() < 1) {
        	creator = defaultValue;
        } else {
        	creator = creatorNodeList.item(0).getTextContent();
        }
        
        return creator;
	}
}
