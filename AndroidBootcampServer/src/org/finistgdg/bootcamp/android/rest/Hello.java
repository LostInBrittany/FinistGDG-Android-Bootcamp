package org.finistgdg.bootcamp.android.rest;

import javax.ws.rs.*;

@Path("helloservice")
public class Hello {

	@GET
	@Path("hello")
	@Produces("application/xml")
	public String getHello() {

		return "Hello World!\n";
	}

	@GET
	@Path("echo")
	@Produces("application/xml")
	public String getEcho(@DefaultValue("") @QueryParam("echo") String pEcho) {
		return "Hello " + pEcho+"\n";
	}
}
