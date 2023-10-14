package com.epsgrau.pe.helper;

import com.epsgrau.pe.model.OrdenPago;
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

public class RegistroCompraExcelHelper {

    static String[] HEADERS = {"FECHA", "CODIGO", "SERIE", "NUMERO", "AÑO", "MES", "RUC", "RAZON SOCIAL",
            "VALOR VENTA", "VALOR IMPUESTO", "OTROS MONTOS", "TOTAL", "ZONA", "FECHA REVISION", "N° ORDEN",
            "FECHA REVISION", "ID COMPROBANTE", "N° ORDEN (NOTA)", "N° DETRACCION", "FEC. DETRACCION", "XML"};

    static String SHEET = "ReporteEjecutadoMensual";

    public static ByteArrayInputStream registroComprasToExcel(List<RegistroCompra> lista) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (RegistroCompra item : lista) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getFecha());
                row.createCell(1).setCellValue(item.getCodigo());
                row.createCell(2).setCellValue(item.getSerie());
                row.createCell(3).setCellValue(item.getNumero());
                row.createCell(4).setCellValue(item.getAnio());
                row.createCell(5).setCellValue(item.getMes());
                row.createCell(6).setCellValue(item.getNumeroDocumentoIdentidad());
                row.createCell(7).setCellValue(item.getProveedor());
                row.createCell(8).setCellValue(item.getValorVenta());
                row.createCell(9).setCellValue(item.getValorImpuesto());
                row.createCell(10).setCellValue(item.getOtrosMontos());
                row.createCell(11).setCellValue(item.getTotal());
                row.createCell(12).setCellValue(item.getZona());
                row.createCell(13).setCellValue(item.getFechaRevision());
                row.createCell(14).setCellValue(item.getNroOrden());
                row.createCell(15).setCellValue(item.getFechaRevision2());
                row.createCell(16).setCellValue(item.getIdComprobante());
                row.createCell(17).setCellValue(item.getNroOrdenNota());
                row.createCell(18).setCellValue(item.getNroDetraccion());
                row.createCell(19).setCellValue(item.getFechaDetraccion());
                row.createCell(20).setCellValue(item.getXml());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<RegistroCompra> excelToRegistroCompras(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println("physicalNumberOfRows: " + physicalNumberOfRows);

            List<RegistroCompra> lista = new ArrayList<RegistroCompra>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                int rowNum = currentRow.getRowNum();
                System.out.println("rowNum: " + rowNum);

                // skip header
                if (rowNum > 0 && (rowNum < (physicalNumberOfRows - 1))) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    RegistroCompra item = new RegistroCompra();

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        int columnIndex = currentCell.getColumnIndex();
                        System.out.println("columnIndex: " + columnIndex);

                        switch (columnIndex) {
                            case 0:
                                item.setFecha(currentCell.getStringCellValue());
                                break;

                            case 1:
                                item.setCodigo(currentCell.getStringCellValue());
                                break;

                            case 2:
                                item.setSerie(currentCell.getStringCellValue().trim());
                                break;

                            case 3:
                                item.setNumero(currentCell.getStringCellValue().trim());
                                break;

                            case 4:
                                item.setAnio(currentCell.getStringCellValue());
                                break;

                            case 5:
                                item.setMes(currentCell.getStringCellValue());
                                break;

                            case 6:
                                item.setNumeroDocumentoIdentidad(currentCell.getStringCellValue());
                                break;

                            case 7:
                                item.setProveedor(currentCell.getStringCellValue());
                                break;

                            case 8:
                                item.setValorVenta(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 9:
                                item.setValorImpuesto(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 10:
                                item.setOtrosMontos(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 11:
                                item.setTotal(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 12:
                                item.setZona(currentCell.getStringCellValue());
                                break;

                            case 13:
                                item.setFechaRevision(currentCell.getStringCellValue());
                                break;

                            case 14:
                                item.setNroOrden(currentCell.getStringCellValue());
                                break;

                            case 15:
                                item.setFechaRevision2(currentCell.getStringCellValue());
                                break;

                            case 16:
                                item.setIdComprobante(currentCell.getStringCellValue());
                                break;

                            case 17:
                                item.setNroOrdenNota(currentCell.getStringCellValue());
                                break;

                            case 18:
                                item.setNroDetraccion(currentCell.getStringCellValue());
                                break;

                            case 19:
                                item.setFechaDetraccion(currentCell.getStringCellValue());
                                break;

                            case 20:
                                item.setXml(currentCell.getStringCellValue());
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