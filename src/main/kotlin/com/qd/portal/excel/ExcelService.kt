package com.qd.portal.excel

import com.qd.portal.edi.service.EdiConnectionListService
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xddf.usermodel.*
import org.apache.poi.xddf.usermodel.chart.*
import org.apache.poi.xssf.usermodel.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.FileOutputStream


/**
 * @author : j_ada
 * @since : 02.01.2020, Do.
 **/
@Service
class ExcelService(private val ediConnectionListService: EdiConnectionListService) {

    fun createEdiConnectionsExcelRepresentation(isAdmin: Boolean): XSSFWorkbook {
        val xssfWorkbook = XSSFWorkbook()
        createTable(xssfWorkbook, isAdmin)
        createStatisticGraphs(xssfWorkbook, isAdmin)

        FileOutputStream("EdiConnections.xlsx").use { fileOut -> xssfWorkbook.write(fileOut) }
        return xssfWorkbook
    }

    fun createTable(xssfWorkbook: XSSFWorkbook, isAdmin: Boolean) {
        // get table data
        val allEdiConnections = ediConnectionListService.findAllEdiConnections(isAdmin)

        val sheet = xssfWorkbook.createSheet("EdiConnections")
        // Set which area the table should be placed in

        val reference = xssfWorkbook.creationHelper.createAreaReference(
                CellReference(0, 0), CellReference(allEdiConnections.size + 1, 4))
        // Create
        val table = sheet.createTable(reference)
        table.name = "EdiConnections"
        table.displayName = "EdiConnections"
        // For now, create the initial style in a low-level way
        table.ctTable.addNewTableStyleInfo()
        table.ctTable.tableStyleInfo.name = "TableStyleMedium2"
        // Style the table
        val style = table.style as XSSFTableStyleInfo
        style.name = "TableStyleMedium2"
        style.setFirstColumn(false)
        style.setLastColumn(false)
        style.isShowRowStripes = true
        style.isShowColumnStripes = false

        // Set the values for the table
        createRow(sheet, 0, mutableListOf("Kunde", "Lieferant", "Status", "Entwickler", "Zuletzt geändert"))
        allEdiConnections.forEachIndexed { index, ediConnection ->
            val developerName =
                    if (ediConnection.assignedDeveloper != null) {
                        "${ediConnection.assignedDeveloper!!.firstName} ${ediConnection.assignedDeveloper!!.lastName}"
                    } else {
                        "X"
                    }
            createRow(sheet, index + 1, mutableListOf(ediConnection.customer.name, ediConnection.supplier.name,
                    ediConnection.status.name, developerName,
                    ediConnection.updateTime.toString()))
        }

        table.columns.forEach { tableColumn ->
            sheet.autoSizeColumn(tableColumn.columnIndex)
        }
    }

    private fun createRow(sheet: XSSFSheet, rowIndex: Int, values: MutableList<String>) {
        val row = sheet.createRow(rowIndex)
        values.forEachIndexed { colIndex, value ->
            val cell = row.createCell(colIndex)
            cell.setCellValue(value)
        }
    }

    fun createStatisticGraphs(xssfWorkbook: XSSFWorkbook, isAdmin: Boolean) {
        logger.info("Creating Excel file")
        val sheet = xssfWorkbook.createSheet("Statistics");
        val NUM_OF_ROWS = 3;
        val NUM_OF_COLUMNS = 10;

        // Create a row and put some cells in it. Rows are 0 based.
        var row: Row;
        var cell: Cell
        for (rowIndex in 0..NUM_OF_ROWS) {
            row = sheet.createRow(rowIndex);
            for (colIndex in 0..NUM_OF_COLUMNS) {
                cell = row.createCell(colIndex);
                cell.setCellValue(colIndex * (rowIndex + 1.0));
            }
        }

        val drawing: XSSFDrawing = sheet.createDrawingPatriarch()
        val anchor: XSSFClientAnchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15) as XSSFClientAnchor

        val chart: XSSFChart = drawing.createChart(anchor)
        val legend: XDDFChartLegend = chart.orAddLegend
        legend.position = LegendPosition.TOP_RIGHT

        val bottomAxis: XDDFValueAxis = chart.createValueAxis(AxisPosition.BOTTOM)
        bottomAxis.setTitle("x")
        val leftAxis: XDDFValueAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("y");
        leftAxis.crosses = AxisCrosses.AUTO_ZERO;

        val xs: XDDFDataSource<Double> = XDDFDataSourcesFactory.fromNumericCellRange(
                sheet, CellRangeAddress(0, 0, 0, NUM_OF_COLUMNS - 1))
        val ys1: XDDFNumericalDataSource<Double> = XDDFDataSourcesFactory.fromNumericCellRange(
                sheet, CellRangeAddress(1, 1, 0, NUM_OF_COLUMNS - 1))
        val ys2: XDDFNumericalDataSource<Double> = XDDFDataSourcesFactory.fromNumericCellRange(
                sheet, CellRangeAddress(2, 2, 0, NUM_OF_COLUMNS - 1))


        val data: XDDFScatterChartData = chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis) as XDDFScatterChartData
        val series1: XDDFScatterChartData.Series = data.addSeries(xs, ys1) as XDDFScatterChartData.Series
        series1.setTitle("2x", null)
        series1.isSmooth = false
        val series2: XDDFScatterChartData.Series = data.addSeries(xs, ys2) as XDDFScatterChartData.Series
        series2.setTitle("3x", null)
        chart.plot(data);

        solidLineSeries(data, 0, PresetColor.CHARTREUSE);
        solidLineSeries(data, 1, PresetColor.TURQUOISE);

        // Write the output to a file
    }

    private fun solidLineSeries(data: XDDFChartData, index: Int, color: PresetColor) {
        val fill = XDDFSolidFillProperties(XDDFColor.from(color));
        val line = XDDFLineProperties();
        line.fillProperties = fill;
        val series = data.getSeries(index)
        val properties: XDDFShapeProperties? = series.shapeProperties;
        if (properties != null) {
            properties.lineProperties = line
        };
        series.shapeProperties = properties;
    }


    companion object { //static
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}