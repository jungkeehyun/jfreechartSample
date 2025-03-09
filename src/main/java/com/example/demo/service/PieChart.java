package com.example.demo.service;

import java.text.DecimalFormat;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;


@Service
public class PieChart {

    public JFreeChart createPieChart2D() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Java", 40);
        dataset.setValue("Python", 30);
        dataset.setValue("C++", 15);
        dataset.setValue("JavaScript", 10);
        dataset.setValue("Go", 5);

        JFreeChart chart = ChartFactory.createPieChart(
                "프로그래밍 언어 점유율 (2D)", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Java", new Color(255, 102, 102));
        plot.setSectionPaint("Python", new Color(102, 255, 102));
        plot.setSectionPaint("C++", new Color(102, 102, 255));
        plot.setSectionPaint("JavaScript", new Color(255, 255, 102));
        plot.setSectionPaint("Go", new Color(102, 255, 255));

        plot.setSimpleLabels(true);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} : {1} ({2})",
                new DecimalFormat("#,##0"),
                new DecimalFormat("0.0%"))
        );

        return chart;
    }

    public JFreeChart createPieChart3D() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Apple", 35);
        dataset.setValue("Samsung", 25);
        dataset.setValue("Xiaomi", 15);
        dataset.setValue("Huawei", 10);
        dataset.setValue("Other", 15);

        // 차트 전체 설정
        JFreeChart chart = ChartFactory.createPieChart3D(
                "스마트폰 시장 점유율 (3D)", dataset, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);

        // 차트 제목 스타일
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 22));
        chart.getTitle().setPaint(Color.BLACK);  
        chart.getTitle().setBackgroundPaint(Color.WHITE);

        // 범례(legend)
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setItemPaint(Color.BLACK);
            chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 16)); // 범례 폰트 크기 설정
            chart.getLegend().setPosition(RectangleEdge.BOTTOM);
            chart.getLegend().setLegendItemGraphicEdge(RectangleEdge.LEFT);
        }

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);  // 플롯 배경 흰색
        plot.setOutlinePaint(null);  // 경계선 제거
        plot.setStartAngle(270);
        plot.setForegroundAlpha(0.6f);
        plot.setDepthFactor(0.1);

        // 범례(plot.legend)
        plot.setLegendItemShape(new Rectangle(10, 10));
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}건 ({2})   "));

        // 데이터 레이블 배경을 흰색으로 설정
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}건 ({2})"));
        plot.setLabelFont(new Font("Arial", Font.BOLD, 18));  // 라벨 폰트 크기 설정
        plot.setLabelBackgroundPaint(Color.WHITE); // 라벨 배경 흰색
        plot.setLabelOutlinePaint(null); // 라벨 테두리 제거
        plot.setLabelShadowPaint(null); // 라벨 그림자 제거
        plot.setLabelPaint(Color.BLACK); // 글자색 검정

        // 각 세션별 색상 설정
        plot.setSectionPaint("Apple", new Color(229, 115, 115));  // #E57373 (딥 코랄)
        plot.setSectionPaint("Samsung", new Color(121, 134, 203)); // #7986CB (네이비 블루)
        plot.setSectionPaint("Xiaomi", new Color(255, 183, 77));  // #FFB74D (소프트 오렌지)
        plot.setSectionPaint("Huawei", new Color(129, 199, 132)); // #81C784 (민트 그린)
        plot.setSectionPaint("Other", new Color(176, 190, 197));  // #B0BEC5 (차콜 그레이)

        return chart;
    }
}
