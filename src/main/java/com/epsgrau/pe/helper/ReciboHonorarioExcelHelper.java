package com.epsgrau.pe.helper;

import com.epsgrau.pe.model.ReciboHonorario;
import com.epsgrau.pe.model.RegistroCompra;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReciboHonorarioExcelHelper {

    static String[] HEADERS = {"NRO", "AP PATERNO", "AP MATERNO", "NOMBRES", "COD", "RUC", "TIPO", "SERIE", "NUMERO",
            "BASE", "IMPONIBLE", "RETENCION", "NETO", "FEC. EMISION", "FEC. PAGO", "IMP.", "DOCUMENTO", "TIPO 2",
            "RAZON SOCIAL", "ORDEN / ASIENTO"};

    static String SHEET = "reporteReciboHonorarios";

    public static ByteArrayInputStream reciboHonorariosToExcel(List<ReciboHonorario> lista) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1, nro = 1;
            for (ReciboHonorario item : lista) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(nro);
                row.createCell(1).setCellValue(item.getApPaterno());
                row.createCell(2).setCellValue(item.getApMaterno());
                row.createCell(3).setCellValue(item.getNombres());
                row.createCell(4).setCellValue(item.getCod());
                row.createCell(5).setCellValue(item.getNumeroDocumentoIdentidad());
                row.createCell(6).setCellValue(item.getTipo());
                row.createCell(7).setCellValue(item.getSerie());
                row.createCell(8).setCellValue(item.getNumero());
                row.createCell(9).setCellValue(item.getBaseImponible());
                row.createCell(10).setCellValue(item.getRetencion());
                row.createCell(11).setCellValue(item.getNeto());
                row.createCell(12).setCellValue(item.getFecEmision());
                row.createCell(13).setCellValue(item.getFecPago());
                row.createCell(14).setCellValue(item.getImp());
                row.createCell(15).setCellValue(item.getDocumento());
                row.createCell(16).setCellValue(item.getTipo2());
                row.createCell(17).setCellValue(item.getProveedor());
                row.createCell(18).setCellValue(item.getOrdenAsiento());

                nro++;
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<ReciboHonorario> excelToReciboHonorarios(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println("physicalNumberOfRows: " + physicalNumberOfRows);

            List<ReciboHonorario> lista = new ArrayList<ReciboHonorario>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                int rowNum = currentRow.getRowNum();
                System.out.println("rowNum: " + rowNum);

                // skip header
                if (rowNum > 3 && (rowNum < (physicalNumberOfRows - 1))) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    ReciboHonorario item = new ReciboHonorario();

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        int columnIndex = currentCell.getColumnIndex();
                        System.out.println("columnIndex: " + columnIndex);

                        switch (columnIndex) {
                            case 0:
                                item.setNumero(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 1:
                                item.setApPaterno(currentCell.getStringCellValue());
                                break;

                            case 2:
                                item.setApMaterno(currentCell.getStringCellValue().trim());
                                break;

                            case 3:
                                item.setNombres(currentCell.getStringCellValue().trim());
                                break;

                            case 4:
                                item.setCod(currentCell.getStringCellValue());
                                break;

                            case 5:
                                item.setNumeroDocumentoIdentidad(currentCell.getStringCellValue());
                                break;

                            case 6:
                                item.setTipo(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 7:
                                item.setSerie(currentCell.getStringCellValue().trim());
                                break;

                            case 8:
                                item.setNumero(currentCell.getStringCellValue().trim());
                                break;

                            case 9:
                                item.setBaseImponible(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 10:
                                item.setRetencion(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 11:
                                item.setNeto(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 12:
                                item.setFecEmision(currentCell.getStringCellValue());
                                break;

                            case 13:
                                item.setFecPago(currentCell.getStringCellValue());
                                break;

                            case 14:
                                item.setImp(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 15:
                                item.setDocumento(currentCell.getStringCellValue());
                                break;

                            case 16:
                                item.setTipo2(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 17:
                                item.setProveedor(currentCell.getStringCellValue());
                                break;

                            case 18:
                                item.setOrdenAsiento(currentCell.getStringCellValue());
                                break;

                            default:
                                break;
                        }
                    }

                    lista.add(item);
                }
            }

            workbook.close();

            return lista;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}