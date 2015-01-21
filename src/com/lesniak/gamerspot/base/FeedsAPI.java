package com.lesniak.gamerspot.base;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.lesniak.gamerspot.utilities.DatabaseAccessor;

@Api(name="feedsAPI", version="v1")
public class FeedsAPI {
	
	@ApiMethod(name = "getAll")
	public ArrayList<Feed> getAll() {
		return DatabaseAccessor.getAll();
	}
	
	@ApiMethod(name = "getByService")
	public ArrayList<Feed> getByService(@Named("serviceName") String servicename) {
		return DatabaseAccessor.getAllByService(servicename.toUpperCase());
	}

}
