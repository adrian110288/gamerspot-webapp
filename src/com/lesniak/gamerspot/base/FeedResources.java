package com.lesniak.gamerspot.base;

import java.util.HashMap;

public final class FeedResources {

	private static final HashMap<String, String> feedUrls = new HashMap<>(20);

	static {
		feedUrls.put("gamespot", "http://www.gamespot.com/feeds/news/");
		feedUrls.put("joystiq", "http://www.joystiq.com/rss.xml");
		feedUrls.put("pcgamer", "http://www.pcgamer.com/feed/");
		//feedUrls.put("polygon", "http://www.polygon.com/rss/index.xml");
		feedUrls.put("eurogamer", "http://www.eurogamer.net/?format=rss&type=news");
		feedUrls.put("n4g", "http://n4g.com/rss/news?channel=&sort=latest");
		feedUrls.put("totalxbox", "http://www.totalxbox.com/rss/feed.php?type=1&amp;priority=1&amp;limit=20");
		feedUrls.put("ps4daily", "http://feeds.feedburner.com/PS4Daily");
		feedUrls.put("psblogeu", "http://blog.eu.playstation.com/feed/");
		feedUrls.put("ign", "http://feeds.ign.com/ign/news?format=xml");
		feedUrls.put("vg24/7", "http://www.vg247.com/feed/");
		feedUrls.put("incgamers", "http://www.incgamers.com/feed");
		

//		feedUrls.put("psuk", "http://uk.playstation.com/rss/news/en_GB/");
//		feedUrls.put("nintendomagazine", "http://www.officialnintendomagazine.co.uk/rss/feed.php?type=1&amp;limit=10");		
//		feedUrls.put("nintendolife", "http://www.nintendolife.com/feeds/news");
//		feedUrls.put("mobilegamesfaqs", "http://www.mobilegamefaqs.com/rss/mgfs_news.rss");
//		feedUrls.put("gamezeboipad", "http://www.gamezebo.com/ipad/rss");
//		feedUrls.put("gamezeboiphone", "http://www.gamezebo.com/iphone/rss");
//		feedUrls.put("gamezeboandroid", "http://www.gamezebo.com/android/rss");
	}

	public FeedResources(){}

	public static HashMap<String, String> getFeedUrlsList(){
		return feedUrls;
	}
}
