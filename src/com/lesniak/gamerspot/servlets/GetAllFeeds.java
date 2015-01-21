package com.lesniak.gamerspot.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lesniak.gamerspot.base.Feed;
import com.lesniak.gamerspot.utilities.DatabaseAccessor;

@SuppressWarnings("serial")
public class GetAllFeeds extends HttpServlet {

	private PrintWriter out;
	private String queriedService = null;
	private ArrayList<Feed> result;
	private String resultAsJSON;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		init(req, resp);
		
		result = (queriedService == null || queriedService.equals("")) 
				? DatabaseAccessor.getAll() 
				: DatabaseAccessor.getAllByService(queriedService);
				
		resultAsJSON = new Gson().toJson(result);
		
		out.write(resultAsJSON);
	}
	
	private void init(HttpServletRequest req, HttpServletResponse respIn) throws IOException {
		queriedService = (String) req.getParameter("service");
		out = respIn.getWriter();
	}

}
