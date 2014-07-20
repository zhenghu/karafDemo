package com.sncf.pscs.testing.http.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sncf.pscs.testing.http.api.DeleteFileResponse;

public class DeleteFileResponseWeb implements DeleteFileResponse {

	private ResponseBuilder responseBuilder;

	@Override
	public void deleted() {
		responseBuilder = Response.ok();
	}

	@Override
	public void failedNotFound() {
		responseBuilder = Response.status(Response.Status.NOT_FOUND);
	}

	public Response response() {
		return responseBuilder.build();
	}
}
