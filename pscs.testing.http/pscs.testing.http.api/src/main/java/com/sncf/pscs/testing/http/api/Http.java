package com.sncf.pscs.testing.http.api;

import java.io.InputStream;

public interface Http {

	void saveFile(String fileName, InputStream fileContent, SaveFileResponse saveFileResponse);

	void deleteFile(String fileName, DeleteFileResponse deleteFileResponse);
	
	void downloadFile(String fileName, DownloadFileResponse downloadFileResponse);
}
