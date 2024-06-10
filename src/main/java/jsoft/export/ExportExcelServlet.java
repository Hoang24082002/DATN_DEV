package jsoft.export;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/exportExcel")
public class ExportExcelServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo một workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một sheet mới
        Sheet sheet = workbook.createSheet("Sheet1");

        // Tạo một row (hàng) tại vị trí thứ 0 và thêm các tiêu đề cột
        Row headerRow = sheet.createRow(0);
        String[] columnHeaders = {"ID", "Name", "Age", "Email"};
        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
        }

        // Thêm một vài dòng dữ liệu
        Object[][] data = {
            {1, "John Doe", 30, "john@example.com"},
            {2, "Jane Smith", 25, "jane@example.com"},
            {3, "Bob Johnson", 35, "bob@example.com"}
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                if (rowData[i] instanceof String) {
                    cell.setCellValue((String) rowData[i]);
                } else if (rowData[i] instanceof Integer) {
                    cell.setCellValue((Integer) rowData[i]);
                }
            }
        }

        // Đặt tên file và content type
        String fileName = "example.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // Xuất file Excel ra OutputStream
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }

        // Đóng workbook
        workbook.close();
    }
}
