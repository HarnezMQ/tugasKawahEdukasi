package id.kawahedukasi.controller;

import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.model.Item;
import id.kawahedukasi.services.ExportServices;
import id.kawahedukasi.services.ImportServices;
import id.kawahedukasi.services.ItemServices;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {
    @Inject
    ItemServices itemServices;
    @Inject
    ImportServices importServices;

    @Inject
    ExportServices exportServices;

    @GET
    public Response getAllItem(){
        return itemServices.getAllItem();
    }

    @GET
    @Path("/export")
    @Produces("application/pdf")
    public Response export() throws JRException {
        return exportServices.exportItem();
    }
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id, Map<String, Object> request){
        return itemServices.getById(id, request);
    }

    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormDTO request) throws IOException {
        return importServices.importExcel(request);
    }
    @POST
    public Response post(Map<String, Object> request){
        return itemServices.post(request);
    }
    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request){
       return itemServices.put(id, request);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id, Map<String, Object> request){
        return itemServices.delete(id, request);
    }

}
