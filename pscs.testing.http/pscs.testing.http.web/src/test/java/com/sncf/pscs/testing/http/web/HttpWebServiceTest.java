package com.sncf.pscs.testing.http.web;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.InputStream;

import org.junit.Test;

import com.sncf.pscs.testing.http.api.DeleteFileResponse;
import com.sncf.pscs.testing.http.api.DownloadFileResponse;
import com.sncf.pscs.testing.http.api.Http;
import com.sncf.pscs.testing.http.api.SaveFileResponse;

public class HttpWebServiceTest {

	/**
	 * Mock for Http class
	 */
	private class HttpMock implements Http {

		private boolean fileSaved = false;
		private boolean fileDeleted = false;
		private boolean hasFile = false;

		@Override
		public void saveFile(String fileName, InputStream fileContent, SaveFileResponse saveFileResponse) {
			saveFileResponse.saved();
			fileSaved = true;
		}

		@Override
		public void deleteFile(String fileName, DeleteFileResponse deleteFileResponse) {
			deleteFileResponse.deleted();
			fileDeleted = true;
		}

		@Override
		public void downloadFile(String fileName, DownloadFileResponse downloadFileResponse) {
			downloadFileResponse.file(null);
			hasFile = true;
		}

		public boolean fileSaved() {
			return fileSaved;
		}

		public boolean fileDeleted() {
			return fileDeleted;
		}

		public boolean hasFile() {
			return hasFile;
		}
	}

	private HttpMock httpService = new HttpMock();

	@Test
	public void upload_should_save_file() {
		// Given
		HttpWebService webService = new HttpWebService(httpService);
		InputStream fileContent = mock(InputStream.class);
		String fileName = "upload1";

		// When
		webService.uploadFile(fileName, fileContent);

		// Then
		assertThat(httpService.fileSaved()).isTrue();
	}

	@Test
	public void delete_should_remove_file() {
		// Given
		HttpWebService webService = new HttpWebService(httpService);
		String fileName = "upload1";

		// When
		webService.deleteFile(fileName);

		// Then
		assertThat(httpService.fileDeleted()).isTrue();
	}

	@Test
	public void download_should_return_file() {
		HttpWebService webService = new HttpWebService(httpService);
		String fileName = "upload1";

		// When
		webService.downloadFile(fileName);

		// Then
		assertThat(httpService.hasFile()).isTrue();
	}
}
