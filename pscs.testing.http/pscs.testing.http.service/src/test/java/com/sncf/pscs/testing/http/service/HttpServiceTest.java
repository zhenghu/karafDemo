package com.sncf.pscs.testing.http.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.sncf.pscs.testing.http.api.DeleteFileResponse;
import com.sncf.pscs.testing.http.api.DownloadFileResponse;
import com.sncf.pscs.testing.http.api.SaveFileResponse;

public class HttpServiceTest {

	@Rule
	public TemporaryFolder saveFolder = new TemporaryFolder();

	private HttpService service;

	@Before
	public void setUp() {
		service = new HttpService(saveFolder.getRoot().getAbsolutePath());
	}

	@Test
	public void
			saveFile_saves_file_and_informs_file_saved() throws Exception {
		// Given
		final String saveDirectory = saveFolder.getRoot().getAbsolutePath();
		final String fileName = "fileToSave.wav";
		final String expectedSavedFilePath = saveDirectory + "/" + fileName;
		InputStream fileContent = getClass().getClassLoader().getResourceAsStream(fileName);
		SaveFileResponse saveFileResponse = mock(SaveFileResponse.class);

		// When
		service.saveFile(fileName, fileContent, saveFileResponse);

		// Then
		assertThat(new File(expectedSavedFilePath)).exists();
		verify(saveFileResponse).saved();
	}

	@Test
	public void saveFile_informs_failure() throws Exception {
		// Given
		final String impossibleDirectory = "this is an impossible directory path";
		HttpService misConfiguredService = new HttpService(impossibleDirectory);
		final String fileName = "fileToSave.wav";
		InputStream fileContent = getClass().getClassLoader().getResourceAsStream(fileName);
		SaveFileResponse saveFileResponse = mock(SaveFileResponse.class);

		// When
		misConfiguredService.saveFile(fileName, fileContent, saveFileResponse);

		// Then
		verify(saveFileResponse).failed();
	}

	@Test
	public void saveFile_informs_file_already_exists() throws Exception {
		// Given
		final String fileName = "fileToSave.wav";
		InputStream fileContent = getClass().getClassLoader().getResourceAsStream(fileName);
		SaveFileResponse saveFileResponse = mock(SaveFileResponse.class);

		// When
		service.saveFile(fileName, fileContent, saveFileResponse);
		service.saveFile(fileName, fileContent, saveFileResponse);

		// Then
		verify(saveFileResponse).failedAlreadyExists();
	}

	@Test
	public void deleteFile_informs_file_deleted() throws Exception {
		// Given
		final String saveDirectory = saveFolder.getRoot().getAbsolutePath();
		final String fileName = "fileToSave.wav";
		final String expectedSavedFilePath = saveDirectory + "/" + fileName;
		DeleteFileResponse deleteFileResponse = mock(DeleteFileResponse.class);
		SaveFileResponse saveFileResponse = mock(SaveFileResponse.class);
		InputStream fileContent = getClass().getClassLoader().getResourceAsStream(fileName);
		service.saveFile(fileName, fileContent, saveFileResponse);

		// When
		service.deleteFile(fileName, deleteFileResponse);

		// Then
		verify(deleteFileResponse).deleted();
		assertThat(new File(expectedSavedFilePath)).doesNotExist();
	}

	@Test
	public void deleteFile_informs_file_not_found() throws Exception {
		// Given
		String fileName = "fileToSave.wav";
		DeleteFileResponse deleteFileResponse = mock(DeleteFileResponse.class);

		// When
		service.deleteFile(fileName, deleteFileResponse);

		// Then
		verify(deleteFileResponse).failedNotFound();
	}

	@Test
	public void downloadFile_gives_file_to_download() {
		// Given
		final String saveDirectory = saveFolder.getRoot().getAbsolutePath();
		final String fileName = "fileToSave.wav";
		final String expectedSavedFilePath = saveDirectory + "/" + fileName;
		File savedFile = new File(expectedSavedFilePath);
		SaveFileResponse saveFileResponse = mock(SaveFileResponse.class);
		InputStream fileContent = getClass().getClassLoader().getResourceAsStream(fileName);
		service.saveFile(fileName, fileContent, saveFileResponse);
		DownloadFileResponse downloadFileResponse = mock(DownloadFileResponse.class);

		// When
		service.downloadFile(fileName, downloadFileResponse);

		// Then
		verify(downloadFileResponse).file(refEq(savedFile));
	}

	@Test
	public void downloadFile_inform_file_not_found() {
		// Given
		final String fileName = "fileToSave.wav";
		DownloadFileResponse downloadFileResponse = mock(DownloadFileResponse.class);

		// When
		service.downloadFile(fileName, downloadFileResponse);

		// Then
		verify(downloadFileResponse).failedNotFound();
	}
}
