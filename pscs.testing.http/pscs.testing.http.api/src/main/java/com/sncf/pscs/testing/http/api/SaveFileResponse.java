package com.sncf.pscs.testing.http.api;

public interface SaveFileResponse {

	void saved();

	void failed();

	void failedAlreadyExists();
}
