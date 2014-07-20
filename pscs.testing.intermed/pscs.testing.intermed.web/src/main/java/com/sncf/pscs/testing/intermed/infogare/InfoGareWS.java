/**
 * 
 */
package com.sncf.pscs.testing.intermed.infogare;

import java.io.UnsupportedEncodingException;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import com.sncf.pscs.testing.intermed.utils.XMLutils;

import pscs.testing.intermed.infogare.Reponse;
import pscs.testing.intermed.simulator.CorrespondanceInfoGenerator;

/**
 * REST service that allows return the correspondante information to pscs.
 * 
 * @author ZHU12373
 * 
 */

@WebService
@Path("/")
public class InfoGareWS {

	private String exampleFileStr = "";

	@GET
	@Path("/{codeGare}")
	public Response getInfoGare(@PathParam("codeGare") final String codeGare, @QueryParam("user") final String user, @QueryParam("app") final String app,
			@QueryParam("version") final String version, @QueryParam("dateGare") final String dateGare, @QueryParam("numTrain") final String numTrain,
			@QueryParam("sens") final String sens, @QueryParam("informationConjoncturelle") final boolean informationConjoncturelle) {

		Response response;
		CorrespondanceInfoGenerator correspondanceInfoGenerator = new CorrespondanceInfoGenerator();
		String infoGare = correspondanceInfoGenerator.getInfoGare(codeGare, exampleFileStr);

		response = Response
				.status(200)
				.entity(infoGare).build();

		return response;

	}

	@PUT
	@Path("/example")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putFileName(Reponse exampleFile) {
		
		XMLutils xmLutils = new XMLutils();
		try {
			exampleFileStr = xmLutils.marshal(exampleFile);
		} catch (UnsupportedEncodingException e) {
			return Response.status(Status.BAD_REQUEST).entity(e).build();
		} catch (JAXBException e) {
			return Response.status(Status.BAD_REQUEST).entity(e).build();
		}
		
		if (exampleFileStr.isEmpty()) {
           return Response.status(Response.Status.BAD_REQUEST).entity("le file est vide.").build();
		}
				        
		return Response.status(Response.Status.OK).build();
	}
}
