package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.text.DecimalFormat;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.PieChart;

@RestController
public class DemoController {
    
    private final PieChart pieChart;

    public DemoController(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    @GetMapping("/bar")
    public void generateBarChart(HttpServletResponse response) throws IOException {
        // 데이터셋 생성
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Sales", "January");
        dataset.addValue(20, "Sales", "February");
        dataset.addValue(15, "Sales", "March");
        dataset.addValue(25, "Sales", "April");
        dataset.addValue(30, "Sales", "May");

        // 바 차트 생성
        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Sales",
                "Month",
                "Sales",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // 응답을 이미지로 변환
        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), barChart, 800, 600);
    }

    @GetMapping("/pie")
    public void generatePieChart(HttpServletResponse response) throws IOException {
        // 데이터셋 생성
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Apple", 30);
        dataset.setValue("Samsung", 25);
        dataset.setValue("Xiaomi", 15);
        dataset.setValue("Oppo", 10);
        dataset.setValue("Others", 20);

        // 파이 차트 생성
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Smartphone Market Share", // 제목
                dataset,                   // 데이터셋
                true,                      // 범례 표시 여부
                true,                      // 툴팁 사용 여부
                false                      // URL 생성 여부
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Apple", new Color(255, 102, 102));    // 빨강
        plot.setSectionPaint("Samsung", new Color(102, 255, 102));  // 초록
        plot.setSectionPaint("Xiaomi", new Color(102, 102, 255));   // 파랑
        plot.setSectionPaint("Oppo", new Color(255, 255, 102));     // 노랑
        plot.setSectionPaint("Others", new Color(102, 255, 255));   // 청록

        plot.setSimpleLabels(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {1} ({2})", new DecimalFormat("#,##0"), new DecimalFormat("0.0%")));

        // 응답을 이미지로 변환
        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), pieChart, 800, 600);
    }

    @GetMapping("/pie2d")
    public ResponseEntity<byte[]> getPieChart2D() throws IOException {
        JFreeChart chart = pieChart.createPieChart2D();

        return createImageResponse(chart);
    }

    @GetMapping("/pie3d")
    public ResponseEntity<byte[]> getPieChart3D() throws IOException {
        JFreeChart chart = pieChart.createPieChart3D();

        return createImageResponse(chart);
    }

    private ResponseEntity<byte[]> createImageResponse(JFreeChart chart) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = chart.createBufferedImage(800, 600);
        ChartUtils.writeBufferedImageAsPNG(outputStream, image);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
