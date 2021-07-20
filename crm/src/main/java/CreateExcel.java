import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 唐坤
 * 2021/7/1 0001
 */
public class CreateExcel {
    public static void main(String[] args) {
        OutputStream os = null;
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFCellStyle style = wb.createCellStyle();
        HSSFSheet sheet = wb.createSheet("学生列表");
        HSSFRow row = sheet.createRow(0);

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);

        style.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 1; i <= 100; i++) {
            row = sheet.createRow(i);

            cell = row.createCell(0);
            cell.setCellValue(i);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("张三" + i);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(20 + i);
            cell.setCellStyle(style);


        }
        try {
            os = new FileOutputStream("d:\\student.xls");
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        System.out.println("============结束===============");



    }
}
