package com.sncf.pscs.testing.http.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sncf.pscs.testing.http.api.SaveFileResponse;

public class SaveFileResponseWeb implements SaveFileResponse {

	private ResponseBuilder responseBuilder;

	@Override
	public void saved() {
		responseBuilder = Response.ok();
	}

	@Override
	public void failed() {
		responseBuilder = Response.serverError();
	}

	@Override
	public void failedAlreadyExists() {
		responseBuilder = Response.status(Response.Status.CONFLICT);
	}

	public Response response() {
		return responseBuilder.build();
	}
}
