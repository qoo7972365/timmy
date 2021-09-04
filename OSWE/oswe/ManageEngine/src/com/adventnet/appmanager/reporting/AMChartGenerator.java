/*     */ package com.adventnet.appmanager.reporting;
/*     */ 
/*     */ import dori.jasper.engine.JRDataSource;
/*     */ import dori.jasper.engine.JasperExportManager;
/*     */ import dori.jasper.engine.JasperFillManager;
/*     */ import dori.jasper.engine.JasperPrint;
/*     */ import dori.jasper.engine.JasperReport;
/*     */ import dori.jasper.engine.util.JRLoader;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Day;
/*     */ import org.jfree.data.time.Hour;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMChartGenerator
/*     */ {
/*     */   public static BufferedImage generateResponseTimeChartImage(Properties data, int width, int height, int xaxisLegend)
/*     */     throws Exception
/*     */   {
/*  66 */     Properties perfData = (Properties)data.get("PERF");
/*  67 */     ArrayList time = (ArrayList)perfData.get("time");
/*  68 */     int size = time.size();
/*  69 */     if (size == 0)
/*     */     {
/*  71 */       return null;
/*     */     }
/*  73 */     ArrayList values = (ArrayList)perfData.get("values");
/*     */     
/*  75 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*  76 */     TimeSeries ts0 = null;
/*  77 */     TimeSeries ts1 = null;
/*  78 */     TimeSeries ts2 = null;
/*  79 */     if (xaxisLegend == 1)
/*     */     {
/*  81 */       ts0 = new TimeSeries("Min", Minute.class);
/*  82 */       ts0.setMaximumItemCount(2);
/*  83 */       ts1 = new TimeSeries("Max", Minute.class);
/*  84 */       ts1.setMaximumItemCount(2);
/*  85 */       ts2 = new TimeSeries("Avg", Minute.class);
/*  86 */       ts2.setMaximumItemCount(2);
/*     */     }
/*  88 */     else if (xaxisLegend == 2)
/*     */     {
/*  90 */       ts0 = new TimeSeries("Min", Hour.class);
/*  91 */       ts0.setMaximumItemCount(2);
/*  92 */       ts1 = new TimeSeries("Max", Hour.class);
/*  93 */       ts1.setMaximumItemCount(2);
/*  94 */       ts2 = new TimeSeries("Avg", Hour.class);
/*  95 */       ts2.setMaximumItemCount(2);
/*     */ 
/*     */     }
/*  98 */     else if (xaxisLegend == 3)
/*     */     {
/* 100 */       ts0 = new TimeSeries("Min", Day.class);
/* 101 */       ts0.setMaximumItemCount(2);
/* 102 */       ts1 = new TimeSeries("Max", Day.class);
/* 103 */       ts1.setMaximumItemCount(2);
/* 104 */       ts2 = new TimeSeries("Avg", Day.class);
/* 105 */       ts2.setMaximumItemCount(2);
/*     */     }
/*     */     
/* 108 */     TimeSeries ts3 = null;
/* 109 */     String legend = "";
/* 110 */     if (xaxisLegend == 1)
/*     */     {
/* 112 */       ts3 = new TimeSeries("Response Time", Minute.class);
/* 113 */       legend = "Time(Min)";
/*     */     }
/* 115 */     else if (xaxisLegend == 2)
/*     */     {
/* 117 */       ts3 = new TimeSeries("Response Time", Hour.class);
/* 118 */       legend = "Time(Hours)";
/*     */     }
/* 120 */     else if (xaxisLegend == 3)
/*     */     {
/* 122 */       ts3 = new TimeSeries("Response Time", Day.class);
/* 123 */       legend = "Time(Days)";
/*     */     }
/* 125 */     double min = ((Double)data.get("MIN")).doubleValue();
/* 126 */     if (xaxisLegend == 1) {
/* 127 */       ts0.add(new Minute(new Date(((Long)time.get(0)).longValue())), min);
/* 128 */     } else if (xaxisLegend == 2) {
/* 129 */       ts0.add(new Hour(new Date(((Long)time.get(0)).longValue())), min);
/* 130 */     } else if (xaxisLegend == 3)
/* 131 */       ts0.add(new Day(new Date(((Long)time.get(0)).longValue())), min);
/* 132 */     if (size != 1)
/*     */     {
/* 134 */       if (xaxisLegend == 1) {
/* 135 */         ts0.add(new Minute(new Date(((Long)time.get(size - 1)).longValue())), min);
/* 136 */       } else if (xaxisLegend == 2) {
/* 137 */         ts0.add(new Hour(new Date(((Long)time.get(size - 1)).longValue())), min);
/* 138 */       } else if (xaxisLegend == 3)
/* 139 */         ts0.add(new Day(new Date(((Long)time.get(size - 1)).longValue())), min);
/*     */     }
/* 141 */     double max = ((Double)data.get("MAX")).doubleValue();
/* 142 */     if (xaxisLegend == 1) {
/* 143 */       ts1.add(new Minute(new Date(((Long)time.get(0)).longValue())), max);
/* 144 */     } else if (xaxisLegend == 2) {
/* 145 */       ts1.add(new Hour(new Date(((Long)time.get(0)).longValue())), max);
/* 146 */     } else if (xaxisLegend == 3)
/* 147 */       ts1.add(new Day(new Date(((Long)time.get(0)).longValue())), max);
/* 148 */     if (size != 1)
/*     */     {
/* 150 */       if (xaxisLegend == 1) {
/* 151 */         ts1.add(new Minute(new Date(((Long)time.get(size - 1)).longValue())), max);
/* 152 */       } else if (xaxisLegend == 2) {
/* 153 */         ts1.add(new Hour(new Date(((Long)time.get(size - 1)).longValue())), max);
/* 154 */       } else if (xaxisLegend == 3)
/* 155 */         ts1.add(new Day(new Date(((Long)time.get(size - 1)).longValue())), max);
/*     */     }
/* 157 */     double avg = ((Double)data.get("AVG")).doubleValue();
/* 158 */     if (xaxisLegend == 1) {
/* 159 */       ts2.add(new Minute(new Date(((Long)time.get(0)).longValue())), avg);
/* 160 */     } else if (xaxisLegend == 2)
/* 161 */       ts2.add(new Hour(new Date(((Long)time.get(0)).longValue())), avg);
/* 162 */     if (xaxisLegend == 3)
/* 163 */       ts2.add(new Day(new Date(((Long)time.get(0)).longValue())), avg);
/* 164 */     if (size != 1)
/*     */     {
/* 166 */       if (xaxisLegend == 1) {
/* 167 */         ts2.add(new Minute(new Date(((Long)time.get(size - 1)).longValue())), avg);
/* 168 */       } else if (xaxisLegend == 2) {
/* 169 */         ts2.add(new Hour(new Date(((Long)time.get(size - 1)).longValue())), avg);
/* 170 */       } else if (xaxisLegend == 3)
/* 171 */         ts2.add(new Day(new Date(((Long)time.get(size - 1)).longValue())), avg);
/*     */     }
/* 173 */     ts3.setMaximumItemCount(10);
/* 174 */     for (int i = 0; i < size; i++)
/*     */     {
/* 176 */       if (xaxisLegend == 1) {
/* 177 */         ts3.add(new Minute(new Date(((Long)time.get(i)).longValue())), ((Double)values.get(i)).doubleValue());
/* 178 */       } else if (xaxisLegend == 2) {
/* 179 */         ts3.add(new Hour(new Date(((Long)time.get(i)).longValue())), ((Double)values.get(i)).doubleValue());
/* 180 */       } else if (xaxisLegend == 3)
/* 181 */         ts3.add(new Day(new Date(((Long)time.get(i)).longValue())), ((Double)values.get(i)).doubleValue());
/*     */     }
/* 183 */     col.addSeries(ts0);
/* 184 */     col.addSeries(ts1);
/* 185 */     col.addSeries(ts2);
/* 186 */     col.addSeries(ts3);
/* 187 */     int[] x = { 0, 1, 2, 3 };
/* 188 */     SubSeriesDataset s = new SubSeriesDataset(col, x);
/* 189 */     JFreeChart jf = ChartFactory.createTimeSeriesChart(null, legend, "ResponseTime(ms)", s, true, true, false);
/* 190 */     jf.setBackgroundPaint(Color.white);
/*     */     
/* 192 */     BufferedImage image = jf.createBufferedImage(width, height);
/* 193 */     return image;
/*     */   }
/*     */   
/*     */   public static void fillReport(String jasperFile, HashMap parameters, JRDataSource source) throws Exception
/*     */   {
/* 198 */     File reportFile = new File(jasperFile);
/* 199 */     JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
/* 200 */     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
/* 201 */     JasperExportManager.exportReportToHtmlFile(jasperPrint, "./reports/Result.html");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\AMChartGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */