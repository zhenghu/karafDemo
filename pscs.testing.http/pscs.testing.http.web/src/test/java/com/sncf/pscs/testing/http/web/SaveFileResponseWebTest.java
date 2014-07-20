package com.sncf.pscs.testing.http.web;

import static org.fest.assertions.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class SaveFileResponseWebTest {

	@Test
	public void saved_builds_ok() throws Exception {
		SaveFileResponseWeb response = new SaveFileResponseWeb();
		response.saved();
		assertThat(response.response().getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	public void failed_builds_internal_error() throws Exception {
		SaveFileResponseWeb response = new SaveFileResponseWeb();
		response.failed();
		assertThat(response.response().getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

	@Test
	public void failedAlreadyExists_builds_conflict() throws Exception {
		SaveFileResponseWeb response = new SaveFileResponseWeb();
		response.failedAlreadyExists();
		assertThat(response.response().getStatus()).isEqualTo(Response.Status.CONFLICT.getStatusCode());
	}

}
