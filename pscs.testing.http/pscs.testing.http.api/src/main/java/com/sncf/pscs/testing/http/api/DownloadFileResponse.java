package com.sncf.pscs.testing.http.api;

import java.io.File;

public interface DownloadFileResponse {

	void file(File file);

	void failedNotFound();
}
