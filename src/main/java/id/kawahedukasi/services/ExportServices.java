package id.kawahedukasi.services;

import com.opencsv.CSVWriter;
import id.kawahedukasi.model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ExportServices {
    public Response exportPDFItem() throws JRException {
        File file = new File("src/main/resources/InvoiceItem.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Item.listAll());

        //Map<String, Object> param = new HashMap<>();
        //param.put("DATASOURCE", jrBeanCollectionDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok().type("application/pdf").entity(jasperResult).build();
    }
    public Response exportExcelItem() throws IOException {

        ByteArrayOutputStream outputStream = excelItem();

//        Content-Disposition: attachment; filename="name_of_excel_file.xls"
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"list-item.xlsx\"")
                .entity(outputStream.toByteArray()).build();

    }
    public ByteArrayOutputStream excelItem() throws IOException {
        List<Item> itemList = Item.listAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("list-item");

        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("type");
        row.createCell(5).setCellValue("description");
        row.createCell(5).setCellValue("createdAt");
        row.createCell(5).setCellValue("updateAt");

        for(Item item : itemList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(item.id);
            row.createCell(1).setCellValue(item.name);
            row.createCell(2).setCellValue(item.count);
            row.createCell(3).setCellValue(item.price);
            row.createCell(4).setCellValue(item.type);
            row.createCell(5).setCellValue(item.description);
            row.createCell(5).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            row.createCell(5).setCellValue(item.updateAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }
    public Response exportCSVItem() throws IOException {
        List<Item> itemList = Item.listAll();
        File file = File.createTempFile("temp", "");
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "count", "price", "type", "description", "createdAt", "updateAt"};
        writer.writeNext(headers);
        for(Item item : itemList) {
            String[] data = {
                    item.id.toString(),
                    item.name,
                    item.count,
                    item.price,
                    item.type,
                    item.description,
                    item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                    item.updateAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            };
            writer.writeNext(data);
        }
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"list-item.csv\"")
                .entity(file).build();
    }
}
