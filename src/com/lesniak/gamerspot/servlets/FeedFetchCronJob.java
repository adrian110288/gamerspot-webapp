package com.lesniak.gamerspot.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.lesniak.gamerspot.base.Feed;
import com.lesniak.gamerspot.base.FeedResources;
import com.lesniak.gamerspot.utilities.DatabaseAccessor;
import com.lesniak.gamerspot.utilities.FeedParser;

@SuppressWarnings("serial")
public class FeedFetchCronJob extends HttpServlet {

	private static Logger logger = Logger.getLogger(FeedFetchCronJob.class.getName());
	private HashMap<String, String> feedUrls;
	private PrintWriter out;
	private HashMap<String, Document> documentsList;
	private ArrayList<Feed> feedList;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		init(resp);
		performJob();
	}

	private void init(HttpServletResponse respIn) throws IOException{
		out = respIn.getWriter();
		feedUrls = FeedResources.getFeedUrlsList();
	}

	private void performJob(){

		long startTime = System.currentTimeMillis();

		documentsList = getDocumentList();
		feedList = FeedParser.parseDocuments(documentsList);
		int rowsInserted = DatabaseAccessor.persistFeeds(feedList);

		long finishTime = System.currentTimeMillis();
		double timeElapsed =((finishTime-startTime)/1000.0);


		logger.info(feedList.size() + " new feeds\n"+rowsInserted+" rows inserted to database\n" + timeElapsed + "s.");
	}

	private HashMap<String, Document> getDocumentList(){
		return downloadDocuments();
	}

	private HashMap<String, Document> downloadDocuments() {

		HashMap<String, Document> documentsListTemp = new HashMap<>();

		try {
			Iterator<String> iterator = feedUrls.keySet().iterator();

			DocumentBuilder documentBuilder = getDocumentBuilder();
			URL url;
			URLConnection conn;

			while(iterator.hasNext()) {

				String urlEntry = iterator.next();
				url = new URL(feedUrls.get(urlEntry).toString());



				Document doc = getDocumentFromUrl(url, documentBuilder);
				if (doc!=null) documentsListTemp.put(urlEntry, doc);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return documentsListTemp;
	}

	private Document getDocumentFromUrl(URL urlIn, DocumentBuilder builderIn) {

		URLConnection conn = getConnectionForURL(urlIn, 1000);
		Document tempDoc = null;

		try (InputStream is = conn.getInputStream()){
			tempDoc = builderIn.parse(is);
			
		} catch (SAXException|IOException e) {
			System.out.println("getDocumentFromUrl ERROR on URL " + urlIn);
			e.printStackTrace();
		}

		return tempDoc;
	}

	private URLConnection getConnectionForURL(URL urlIn, int defaultTimeout) {

		URLConnection conn = null;
		try {
			conn = urlIn.openConnection();
			conn.setReadTimeout(defaultTimeout);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private DocumentBuilder getDocumentBuilder(){

		DocumentBuilder documentBuilder = null;

		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return documentBuilder;
	}
}
