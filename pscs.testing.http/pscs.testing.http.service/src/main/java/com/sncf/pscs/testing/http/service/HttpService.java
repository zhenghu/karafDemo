package com.sncf.pscs.testing.http.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sncf.pscs.testing.http.api.DeleteFileResponse;
import com.sncf.pscs.testing.http.api.DownloadFileResponse;
import com.sncf.pscs.testing.http.api.Http;
import com.sncf.pscs.testing.http.api.SaveFileResponse;

public class HttpService implements Http {

	private static final int COPY_CHUNK_SIZE = 1024;
	/**
	 * Directory in which files are saved.
	 */
	private String saveDirectory;
	private static final Logger LOG = Logger.getLogger(HttpService.class);

	public HttpService(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}

	@Override
	public void saveFile(String fileName, InputStream fileContent, SaveFileResponse saveFileResponse) {
		File file = new File(absoluteFilePathFor(fileName));
		if (file.exists()) {
			saveFileResponse.failedAlreadyExists();
			return;
		}
		try {
			copyFile(fileContent, file);
			LOG.info("File saved " + fileName);
			saveFileResponse.saved();
		} catch (IOException e) {
			LOG.error("Failed to save file " + fileName, e);
			saveFileResponse.failed();
		}
	}

	private String absoluteFilePathFor(String fileName) {
		return saveDirectory + "/" + fileName;
	}

	private void copyFile(InputStream fileContent, File file)
			throws IOException {
		FileOutputStream output = new FileOutputStream(file);
		try {
			int read;
			byte bytes[] = new byte[COPY_CHUNK_SIZE];
			while ((read = fileContent.read(bytes)) != -1) {
				output.write(bytes, 0, read);
			}
		} finally {
			output.close();
		}
	}

	@Override
	public void deleteFile(String fileName, DeleteFileResponse deleteFileResponse) {
		File file = new File(absoluteFilePathFor(fileName));
		if (!file.exists()) {
			deleteFileResponse.failedNotFound();
			return;
		}
		file.delete();
		deleteFileResponse.deleted();
	}

	@Override
	public void downloadFile(String fileName, DownloadFileResponse downloadFileResponse) {
		File fileToDownload = new File(absoluteFilePathFor(fileName));
		if (!fileToDownload.exists()) {
			downloadFileResponse.failedNotFound();
			return;
		}
		downloadFileResponse.file(fileToDownload);
	}
}
