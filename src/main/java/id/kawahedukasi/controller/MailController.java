package id.kawahedukasi.controller;


import id.kawahedukasi.services.MailServices;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/mail")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MailController {
    @Inject
    MailServices mailServices;
    @POST
    public Response sendEmail(Map<String, Object> request){
        mailServices.sendEmail(request.get("email").toString());
        return Response.ok(new HashMap<>()).build();
    }
    @POST
    @Path("/excel")
    public Response sendExcelToEmail(Map<String, Object> request) throws IOException {
        mailServices.sendExcelTOEmail(request.get("email").toString());
        return Response.ok(new HashMap<>()).build();
    }
}
