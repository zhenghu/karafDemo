package com.sncf.pscs.testing.http.web;

import static org.fest.assertions.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class DeleteFileResponseWebTest {

	@Test
	public void deleted_builds_ok() {
		DeleteFileResponseWeb response = new DeleteFileResponseWeb();
		response.deleted();
		assertThat(response.response().getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	public void failedNotFound_builds_not_found() {
		DeleteFileResponseWeb response = new DeleteFileResponseWeb();
		response.failedNotFound();
		assertThat(response.response().getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}
}
