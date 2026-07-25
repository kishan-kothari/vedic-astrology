package com.astrology.report;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates an HTML report using Freemarker templates.
 */
public class HtmlReportGenerator {
    private final Configuration freemarkerConfig;
    private final ChartRenderer chartRenderer;

    public HtmlReportGenerator() {
        this.freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);
        this.freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        this.freemarkerConfig.setDefaultEncoding("UTF-8");
        this.chartRenderer = new ChartRenderer();
    }

    public String generateReport(ReportData data) throws IOException {
        try {
            Template template = freemarkerConfig.getTemplate("chart-report.ftl");
            
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("report", data);
            if (data.getBirthChart() != null) {
                templateData.put("northIndianChartSvg", chartRenderer.renderNorthIndianSvg(data.getBirthChart(), 300, 300));
                templateData.put("southIndianChartSvg", chartRenderer.renderSouthIndianSvg(data.getBirthChart(), 300, 300));
            }
            
            StringWriter writer = new StringWriter();
            template.process(templateData, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new IOException("Failed to generate HTML report", e);
        }
    }
}
