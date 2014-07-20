package com.sncf.pscs.testing.http.web;

import java.io.InputStream;

import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sncf.pscs.testing.http.api.Http;

@WebService
@Path("/")
public class HttpWebService {

	private Http httpService;

	public HttpWebService(Http httpService) {
		this.httpService = httpService;
	}

	@PUT
	@Path("/{name}")
	public Response uploadFile(@PathParam("name") String name,
			InputStream fileContent) {
		SaveFileResponseWeb saveFileResponse = new SaveFileResponseWeb();
		httpService.saveFile(name, fileContent, saveFileResponse);
		return saveFileResponse.response();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@PathParam("name") String name) {
		DownloadFileResponseWeb downloadFileResponse = new DownloadFileResponseWeb();
		httpService.downloadFile(name, downloadFileResponse);
		return downloadFileResponse.response();
	}

	@DELETE
	@Path("/{name}")
	public Response deleteFile(@PathParam("name") String name) {
		DeleteFileResponseWeb deleteFileResponse = new DeleteFileResponseWeb();
		httpService.deleteFile(name, deleteFileResponse);
		return deleteFileResponse.response();
	}
}
