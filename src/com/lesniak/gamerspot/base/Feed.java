package com.lesniak.gamerspot.base;

import java.io.Serializable;

public class Feed implements Serializable{
	
	private String guid;
	private String title;
	private String link;
	private String description;
	private String pubDate;
	private String creator;
	private String service;
	
	public Feed(){}
	
	public Feed(String guid, String title, String link, String description, String pubDate, String creator, String service) {
		this.guid = guid;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.creator = creator;
		this.service = service;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
	@Override
	public String toString(){
		return getTitle();
	}

}
