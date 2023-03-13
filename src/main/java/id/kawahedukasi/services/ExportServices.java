package id.kawahedukasi.services;

import id.kawahedukasi.model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ExportServices {
    public Response exportItem() throws JRException {
        File file = new File("src/main/resources/InvoiceItem.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Item.listAll());

        //Map<String, Object> param = new HashMap<>();
        //param.put("DATASOURCE", jrBeanCollectionDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok().type("application/pdf").entity(jasperResult).build();
    }
}
