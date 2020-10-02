package com.zeylin.pricetracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zeylin.pricetracker.dao.PriceDAO;
import com.zeylin.pricetracker.dto.PriceReportDto;
import com.zeylin.pricetracker.exceptions.InvalidArgumentException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final PriceDAO priceDAO;

    public ReportServiceImpl(PriceDAO priceDAO) {
        this.priceDAO = priceDAO;
    }

    public byte[] generateMonthlyReport(Integer month) {
        try {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                monthlyReportPdf(byteArrayOutputStream, month);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Can't generate pdf report", e);
        }
    }

    private void monthlyReportPdf(OutputStream outputStream, Integer month) throws IOException, JRException, URISyntaxException {
        if (month > 12 || month < 1) {
            throw new InvalidArgumentException("Invalid month: " + month);
        }

        List<PriceReportDto> dataSourceList = new ArrayList<>();
        dataSourceList.add(new PriceReportDto());
        List<PriceReportDto> dbList = priceDAO.listByMonth(month);
        dataSourceList.addAll(dbList);
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(dataSourceList);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("DataParam", itemsJRBean);

        URL resource = ReportService.class.getResource("/reports/monthly_subreport.jasper");
        File file = Paths.get(resource.toURI()).toFile();
        parameters.put("SUBREPORT_EXPR", file.getAbsolutePath());

        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String reportDate = format.format(LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();

        root.put("month_name", monthName);
        root.put("report_date", reportDate);

        String jsonString = root.toString();
        JRDataSource dataSource = loadDataSource(jsonString);
        JasperReport jasperReport = loadReport("/reports/monthly_report.jasper");
        jasperPrint(outputStream, parameters, dataSource, jasperReport);
    }

    private static JRDataSource loadDataSource(String jsonString) throws IOException, JRException {
        try (InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8))) {
            return new JsonDataSource(stream);
        }
    }

    private static JasperReport loadReport(String reportName) throws IOException, JRException {
        try (InputStream resourceAsStream = ReportService.class.getResourceAsStream(reportName)) {
            return (JasperReport) JRLoader.loadObject(resourceAsStream);
        }
    }

    private void jasperPrint(OutputStream outputStream, HashMap<String, Object> parameters, JRDataSource dataSource, JasperReport jasperReport) throws JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        exporter.setConfiguration(reportConfig);
        exporter.exportReport();
    }

}
