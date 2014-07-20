package com.sncf.pscs.testing.http.web;

import java.io.File;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sncf.pscs.testing.http.api.DownloadFileResponse;

public class DownloadFileResponseWeb implements DownloadFileResponse {

	private ResponseBuilder responseBuilder;

	@Override
	public void file(File file) {
		responseBuilder = Response.ok().entity(file);
	}

	@Override
	public void failedNotFound() {
		responseBuilder = Response.status(Response.Status.NOT_FOUND.getStatusCode());
	}

	public Response response() {
		return responseBuilder.build();
	}

}
