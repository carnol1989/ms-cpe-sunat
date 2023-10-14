package com.epsgrau.pe.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epsgrau.pe.model.CajaChica;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CajaChicaExcelHelper {

    static String[] HEADERS = {"FECHA", "TIPO DOCUMENTO", "SERIE-NUMERO", "NOTA", "RUC/DNI", "PROVEEDOR", "V. VENTA",
            "IMPUESTO", "V. TOTAL", "CTA. CBLE", "C. COSTO"};
    static String SHEET = "ReporteCajaChica";

    public static ByteArrayInputStream cajaChicaToExcel(List<CajaChica> cajaChicas) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (CajaChica cajaChica : cajaChicas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(cajaChica.getFecha());
                row.createCell(1).setCellValue(cajaChica.getTipoDocumentoPago());
                row.createCell(2).setCellValue(cajaChica.getSerie() + "-" + cajaChica.getNumero());
                row.createCell(3).setCellValue(cajaChica.getNota());
                row.createCell(4).setCellValue(cajaChica.getNumeroDocumentoIdentidad());
                row.createCell(5).setCellValue(cajaChica.getProveedor());
                row.createCell(6).setCellValue(cajaChica.getValorVenta());
                row.createCell(7).setCellValue(cajaChica.getImpuesto());
                row.createCell(8).setCellValue(cajaChica.getValorTotal());
                row.createCell(9).setCellValue(cajaChica.getCuentaContable());
                row.createCell(10).setCellValue(cajaChica.getCentroCosto());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<CajaChica> excelToCajaChicas(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println("physicalNumberOfRows: " + physicalNumberOfRows);

            List<CajaChica> cajaChicas = new ArrayList<CajaChica>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                int rowNum = currentRow.getRowNum();
                System.out.println("rowNum: " + rowNum);

                // skip header
                if (rowNum > 5 && (rowNum < (physicalNumberOfRows - 1))) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    CajaChica cajaChica = new CajaChica();

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        int columnIndex = currentCell.getColumnIndex();
                        System.out.println("columnIndex: " + columnIndex);

                        switch (columnIndex) {
                            case 0:
                                cajaChica.setFecha(currentCell.getStringCellValue());
                                break;

                            case 1:
                                cajaChica.setTipoDocumentoPago(currentCell.getStringCellValue());
                                break;

                            case 2:
                                String serieNumero = currentCell.getStringCellValue();
                                System.out.println("serieNumero: " + serieNumero);
                                String [] arrSerieNumero = serieNumero.split("-");
                                cajaChica.setSerie(arrSerieNumero[0].toUpperCase().trim());
                                cajaChica.setNumero(arrSerieNumero[1].trim());
                                break;

                            case 3:
                                cajaChica.setNota(currentCell.getStringCellValue());
                                break;

                            case 5:
                                cajaChica.setNumeroDocumentoIdentidad(currentCell.getStringCellValue());
                                break;

                            case 6:
                                cajaChica.setProveedor(currentCell.getStringCellValue());
                                break;

                            case 7:
                                cajaChica.setValorVenta(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 9:
                                cajaChica.setImpuesto(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 11:
                                cajaChica.setValorTotal(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 12:
                                cajaChica.setCuentaContable(currentCell.getStringCellValue());
                                break;

                            case 14:
                                cajaChica.setCentroCosto(currentCell.getStringCellValue());
                                break;

                            default:
                                break;
                        }
                    }

                    cajaChicas.add(cajaChica);
                }
            }

            workbook.close();

            return cajaChicas;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
