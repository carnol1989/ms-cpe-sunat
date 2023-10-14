package com.epsgrau.pe.helper;

import com.epsgrau.pe.model.OrdenPago;
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

public class OrdenPagoExcelHelper {

    static String[] HEADERS = {"ID", "ZONA", "LOCALIDAD", "SERIE", "NUMERO", "FEC. EMISION", "SUBTOTAL", "IMPUESTO",
            "OTROS", "TOTAL", "RUC", "RAZON SOCIAL", "DOCUMENTO", "AÑO", "MES", "USUARIO", "FEC. USO", "FEC. CONFORM.",
            "USUARIO CONFORM.", "ESTADO CONFORM.", "OBSERVACION CONFORM.", "OBSERVACION COMPROBANTE", "FEC. REVISION",
            "NRO. ORDEN", "UNIDAD USUARIO", "UNID. USUAR. CONFORM.", "ESTADO MOV.", "USUARIO MOV.", "FEC. MOVIM.",
            "UNID. MOV.", "AÑO RC", "MES RC", "FUENTE FINANCIAMIENTO", "SUB FUENTE FINANCIAMIENTO"};
    static String SHEET = "reporteComprobantesPagoManual";

    public static ByteArrayInputStream ordenesToExcel(List<OrdenPago> ordenes) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (OrdenPago ordenPago : ordenes) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(ordenPago.getId());
                row.createCell(1).setCellValue(ordenPago.getZona());
                row.createCell(2).setCellValue(ordenPago.getLocalidad());
                row.createCell(3).setCellValue(ordenPago.getSerie());
                row.createCell(4).setCellValue(ordenPago.getNumero());
                row.createCell(5).setCellValue(ordenPago.getFechaEmision());
                row.createCell(6).setCellValue(ordenPago.getSubtotal());
                row.createCell(7).setCellValue(ordenPago.getImpuesto());
                row.createCell(8).setCellValue(ordenPago.getOtros());
                row.createCell(9).setCellValue(ordenPago.getTotal());
                row.createCell(10).setCellValue(ordenPago.getProveedor());
                row.createCell(11).setCellValue(ordenPago.getTipoDocumentoPago());
                row.createCell(12).setCellValue(ordenPago.getAnio());
                row.createCell(13).setCellValue(ordenPago.getMes());
                row.createCell(14).setCellValue(ordenPago.getUsuario());
                row.createCell(15).setCellValue(ordenPago.getFecUso());
                row.createCell(16).setCellValue(ordenPago.getFecConform());
                row.createCell(17).setCellValue(ordenPago.getUsuarioConform());
                row.createCell(18).setCellValue(ordenPago.getEstadoConform());
                row.createCell(19).setCellValue(ordenPago.getObservacionConform());
                row.createCell(20).setCellValue(ordenPago.getObservacionComprobante());
                row.createCell(21).setCellValue(ordenPago.getFecRevision());
                row.createCell(22).setCellValue(ordenPago.getNroOrden());
                row.createCell(23).setCellValue(ordenPago.getUnidadUsuario());
                row.createCell(24).setCellValue(ordenPago.getUnidadUsuarioConform());
                row.createCell(25).setCellValue(ordenPago.getEstadoMov());
                row.createCell(26).setCellValue(ordenPago.getEstadoMov());
                row.createCell(27).setCellValue(ordenPago.getUsuarioMov());
                row.createCell(28).setCellValue(ordenPago.getFecMovim());
                row.createCell(29).setCellValue(ordenPago.getUnidMov());
                row.createCell(30).setCellValue(ordenPago.getAnioRc());
                row.createCell(31).setCellValue(ordenPago.getMesRc());
                row.createCell(32).setCellValue(ordenPago.getFuenteFinanciamiento());
                row.createCell(33).setCellValue(ordenPago.getSubFuenteFinanciamiento());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<OrdenPago> excelToOrdenesPago(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//            System.out.println("physicalNumberOfRows: " + physicalNumberOfRows);

            List<OrdenPago> ordenesPago = new ArrayList<OrdenPago>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                int rowNum = currentRow.getRowNum();
//                System.out.println("rowNum: " + rowNum);

                // skip header
                if (rowNum > 0 && (rowNum < (physicalNumberOfRows - 1))) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    OrdenPago ordenPago = new OrdenPago();

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        int columnIndex = currentCell.getColumnIndex();
//                        System.out.println("columnIndex: " + columnIndex);

                        switch (columnIndex) {
                            case 0:
                                ordenPago.setId(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 1:
                                ordenPago.setZona(currentCell.getStringCellValue());
                                break;

                            case 2:
                                ordenPago.setLocalidad(currentCell.getStringCellValue());
                                break;

                            case 3:
                                ordenPago.setSerie(currentCell.getStringCellValue().trim());
                                break;

                            case 4:
                                ordenPago.setNumero(currentCell.getStringCellValue().trim());
                                break;

                            case 5:
                                ordenPago.setFechaEmision(currentCell.getStringCellValue());
                                break;

                            case 6:
                                ordenPago.setSubtotal(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 7:
                                ordenPago.setImpuesto(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 8:
                                ordenPago.setOtros(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 9:
                                ordenPago.setTotal(String.valueOf(currentCell.getNumericCellValue()));
                                break;

                            case 10:
                                ordenPago.setNumeroDocumentoIdentidad(currentCell.getStringCellValue());
                                break;

                            case 11:
                                ordenPago.setProveedor(currentCell.getStringCellValue());
                                break;

                            case 12:
                                ordenPago.setTipoDocumentoPago(currentCell.getStringCellValue());
                                break;

                            case 13:
                                ordenPago.setAnio(currentCell.getStringCellValue());
                                break;

                            case 14:
                                ordenPago.setMes(currentCell.getStringCellValue());
                                break;

                            case 15:
                                ordenPago.setUsuario(currentCell.getStringCellValue());
                                break;

                            case 16:
                                ordenPago.setFecUso(currentCell.getStringCellValue());
                                break;

                            case 17:
                                ordenPago.setFecConform(currentCell.getStringCellValue());
                                break;

                            case 18:
                                ordenPago.setUsuarioConform(currentCell.getStringCellValue());
                                break;

                            case 19:
                                ordenPago.setEstadoConform(currentCell.getStringCellValue());
                                break;

                            case 20:
                                ordenPago.setObservacionConform(currentCell.getStringCellValue());
                                break;

                            case 21:
                                ordenPago.setObservacionComprobante(currentCell.getStringCellValue());
                                break;

                            case 22:
                                ordenPago.setFecRevision(currentCell.getStringCellValue());
                                break;

                            case 23:
                                ordenPago.setNroOrden(currentCell.getStringCellValue());
                                break;

                            case 24:
                                ordenPago.setUnidadUsuario(currentCell.getStringCellValue());
                                break;

                            case 25:
                                ordenPago.setUnidadUsuarioConform(currentCell.getStringCellValue());
                                break;

                            case 26:
                                ordenPago.setEstadoMov(currentCell.getStringCellValue());
                                break;

                            case 27:
                                ordenPago.setUsuarioMov(currentCell.getStringCellValue());
                                break;

                            case 28:
                                ordenPago.setFecMovim(currentCell.getStringCellValue());
                                break;

                            case 29:
                                ordenPago.setUnidMov(currentCell.getStringCellValue());
                                break;

                            case 30:
                                ordenPago.setAnioRc(currentCell.getStringCellValue());
                                break;

                            case 31:
                                ordenPago.setMesRc(currentCell.getStringCellValue());
                                break;

                            case 32:
                                ordenPago.setFuenteFinanciamiento(currentCell.getStringCellValue());
                                break;

                            case 33:
                                ordenPago.setSubFuenteFinanciamiento(currentCell.getStringCellValue());
                                break;

                            default:
                                break;
                        }
                    }

                    ordenesPago.add(ordenPago);
                }
            }

            workbook.close();

            return ordenesPago;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}