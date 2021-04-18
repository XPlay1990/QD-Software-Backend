package com.qd.portal.excel

import com.qd.portal.edi.service.EdiConnectionListService
import com.qd.portal.edi.statistics.EdiStatisticsService
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xddf.usermodel.*
import org.apache.poi.xddf.usermodel.chart.*
import org.apache.poi.xssf.usermodel.*
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service


/**
 * @author : j_ada
 * @since : 02.01.2020, Do.
 **/
@Service
class ExcelService(
    private val ediConnectionListService: EdiConnectionListService,
    private val ediStatisticsService: EdiStatisticsService
) {

    fun createEdiConnectionsExcelRepresentation(isAdmin: Boolean): XSSFWorkbook {
        logger.info("Creating Excel file")

        val xssfWorkbook = XSSFWorkbook()
        createTable(xssfWorkbook, isAdmin)
        createStatisticStatusChart(xssfWorkbook, isAdmin)

//        FileOutputStream("EdiConnections.xlsx").use { fileOut -> xssfWorkbook.write(fileOut) }
        logger.info("Creating Excel file finished")
        return xssfWorkbook
    }

    fun createTable(xssfWorkbook: XSSFWorkbook, isAdmin: Boolean) {
        logger.info("Creating Excel table")
        // get table data
        val allEdiConnections = ediConnectionListService.findAllEdiConnections(isAdmin)

        val sheet = xssfWorkbook.createSheet("EdiConnections")
        // Set which area the table should be placed in

        val reference = xssfWorkbook.creationHelper.createAreaReference(
            CellReference(0, 0), CellReference(allEdiConnections.size + 1, 4)
        )
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
            createRow(
                sheet, index + 1, mutableListOf(
                    ediConnection.customer.name, ediConnection.supplier.name,
                    ediConnection.status.name, developerName,
                    ediConnection.updateTime.toString()
                )
            )
        }

        table.columns.forEach { tableColumn ->
            sheet.autoSizeColumn(tableColumn.columnIndex)
        }
        logger.info("Creating Excel table finished")
    }

    private fun createRow(sheet: XSSFSheet, rowIndex: Int, values: MutableList<String>) {
        val row = sheet.createRow(rowIndex)
        values.forEachIndexed { colIndex, value ->
            val cell = row.createCell(colIndex)
            cell.setCellValue(value)
        }
    }

    fun createStatisticStatusChart(xssfWorkbook: XSSFWorkbook, isAdmin: Boolean) {
        logger.info("Creating Excel status chart")
        val sheet = xssfWorkbook.createSheet("Statistics");

        // Get Status Data
        val statusStatistics = ediStatisticsService.getStatusStatistics(isAdmin)
        val statusRow = sheet.createRow(0);
        val valueRow = sheet.createRow(1);
        statusStatistics.entries.forEachIndexed { colIndex, statusStatistic ->
            statusRow.createCell(colIndex).setCellValue(statusStatistic.key.toString())
            valueRow.createCell(colIndex).setCellValue(statusStatistic.value.toDouble())
        }

        val drawing: XSSFDrawing = sheet.createDrawingPatriarch()
        val anchor: XSSFClientAnchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 25, 40) as XSSFClientAnchor

        val chart: XSSFChart = drawing.createChart(anchor)
        chart.setTitleText("Status statistics")
        val legend: XDDFChartLegend = chart.orAddLegend
        legend.position = LegendPosition.RIGHT

        val bottomAxis: XDDFCategoryAxis = chart.createCategoryAxis(AxisPosition.BOTTOM)
        bottomAxis.setTitle("status")

        val leftAxis: XDDFValueAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("#");
        leftAxis.crosses = AxisCrosses.AUTO_ZERO
        // category axis crosses the value axis between the strokes and not midpoint the strokes
        leftAxis.crossBetween = AxisCrossBetween.BETWEEN;

        val xs = XDDFDataSourcesFactory.fromStringCellRange(
            sheet,
            CellRangeAddress(0, 0, 0, statusStatistics.size - 1)
        )
        val ys: XDDFNumericalDataSource<Double> = XDDFDataSourcesFactory.fromNumericCellRange(
            sheet, CellRangeAddress(1, 1, 0, statusStatistics.size - 1)
        )


        val data: XDDFBarChartData = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis) as XDDFBarChartData
        val series1: XDDFBarChartData.Series = data.addSeries(xs, ys) as XDDFBarChartData.Series
        series1.setTitle("status", null)
//        series1.isSmooth = false
        data.barDirection = BarDirection.COL
        chart.plot(data)

        solidLineSeries(data, 0, PresetColor.CHARTREUSE);

        logger.info("Creating Excel status chart finished")
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