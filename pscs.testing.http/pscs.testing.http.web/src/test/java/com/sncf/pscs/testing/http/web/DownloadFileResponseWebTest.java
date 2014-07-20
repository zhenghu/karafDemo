package com.sncf.pscs.testing.http.web;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;

import javax.ws.rs.core.Response;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

public class DownloadFileResponseWebTest {

	@Test
	public void file_builds_ok_with_file() {
		DownloadFileResponseWeb response = new DownloadFileResponseWeb();
		File downloadedFile = mock(File.class);
		response.file(downloadedFile);
		Response restResponse = response.response();
		assertThat(restResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(restResponse.getEntity()).isEqualTo(downloadedFile);
	}

	@Test
	public void failedNotFound_builds_not_found() {
		DownloadFileResponseWeb response = new DownloadFileResponseWeb();
		response.failedNotFound();
		Assertions.assertThat(response.response().getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}
}
