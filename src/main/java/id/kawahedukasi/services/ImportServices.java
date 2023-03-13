package id.kawahedukasi.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.model.Item;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ImportServices {
    @Transactional
    public Response importExcel(FileFormDTO request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(0));

        List<Item> toPersist = new ArrayList<>();

        for (Row row : sheet) {
            Item item = new Item();
            item.name = row.getCell(0).getStringCellValue();
            item.count = row.getCell(0).getStringCellValue();
            item.price = row.getCell(0).getStringCellValue();
            item.type = row.getCell(0).getStringCellValue();
            item.description = row.getCell(0).getStringCellValue();
            toPersist.add(item);
        }
        Item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
    @Transactional
    public Response importCSV(FileFormDTO request) throws IOException, CsvValidationException {
        File file = File.createTempFile("", "");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(request.file);

        CSVReader reader = new CSVReader(new FileReader(file));
        String [] nextLine;
        reader.skip(1);
        while ((nextLine = reader.readNext()) != null){
            for (String token : nextLine) {
                String[] strings = line.split(",");
            }
            System.out.println("/n");
        }
        Item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
}
