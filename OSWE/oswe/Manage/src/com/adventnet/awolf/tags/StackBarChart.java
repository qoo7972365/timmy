/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.chart.ChartInfo.StackedCustomRenderer;
/*     */ import com.adventnet.awolf.chart.ChartInfo.StackedCustomRenderer2D;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.CategoryLabelPositions;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.labels.ItemLabelAnchor;
/*     */ import org.jfree.chart.labels.ItemLabelPosition;
/*     */ import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
/*     */ import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.renderer.category.BarRenderer;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.chart.title.LegendTitle;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.urls.StandardCategoryURLGenerator;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackBarChart
/*     */   extends BaseTag
/*     */ {
/*     */   private Paint[] barcolors;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public Paint[] getBarcolors()
/*     */   {
/*  54 */     return this.barcolors;
/*     */   }
/*     */   
/*     */   public DatasetProducer getDataSet() {
/*  58 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBarcolors(Paint[] v)
/*     */   {
/*  66 */     this.barcolors = v;
/*     */   }
/*     */   
/*     */   public StackBarChart()
/*     */   {
/*  46 */     this.barcolors = null;
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
/*  71 */     this.chart = null;
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  77 */       DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*  78 */       CategoryDataset dataset = (CategoryDataset)dsp.produceDataset(null);
/*  79 */       if (dataset != null)
/*     */       {
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
/* 129 */         if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */         {
/* 131 */           setNodata(false);
/* 132 */           List columnkeys = dataset.getColumnKeys();
/* 133 */           List rowkeys = dataset.getRowKeys();
/* 134 */           DefaultCategoryDataset newdataset = new DefaultCategoryDataset();
/* 135 */           for (int i = 0; i < columnkeys.size(); i++)
/*     */           {
/* 137 */             String key = (String)columnkeys.get(i);
/*     */             
/* 139 */             for (int j = 0; j < rowkeys.size(); j++)
/*     */             {
/*     */ 
/*     */ 
/* 143 */               String rowkey = (String)rowkeys.get(j);
/*     */               try {
/* 145 */                 Number value = dataset.getValue(rowkey, key);
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
/* 156 */                 if (key.contains("_#$@_resid="))
/*     */                 {
/* 158 */                   String temp = key.substring(0, key.indexOf("_#$@_resid="));
/* 159 */                   newdataset.setValue(value, rowkey, temp);
/*     */                 }
/* 161 */                 else if (key.contains("_#$_resid="))
/*     */                 {
/* 163 */                   String temp = key.substring(0, key.indexOf("_#$_resid="));
/* 164 */                   newdataset.setValue(value, rowkey, temp);
/*     */                 }
/*     */                 else
/*     */                 {
/* 168 */                   newdataset.setValue(value, rowkey, key);
/*     */                 }
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 173 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 179 */           dataset = newdataset;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 184 */         String widthToSet = this.width;
/* 185 */         if (widthToSet == null) {
/* 186 */           widthToSet = "300";
/*     */         }
/* 188 */         String heightToSet = this.height;
/* 189 */         if (heightToSet == null) {
/* 190 */           heightToSet = "150";
/*     */         }
/*     */         
/* 193 */         JspWriter out = this.pageContext.getOut();
/* 194 */         out.println("<table class=\"no-graph\"><tr><td class=\"disabledtext\" align=center>" + this.nodatamessage + "</td></tr></table>");
/* 195 */         setNodata(true);
/* 196 */         return 0;
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 201 */         if (!this.twoDimensionBar)
/*     */         {
/* 203 */           this.chart = ChartFactory.createStackedBarChart3D("", this.xAxisLabel, this.yAxisLabel, dataset, PlotOrientation.VERTICAL, this.legend.equals("true"), true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 216 */           this.chart = ChartFactory.createStackedBarChart("", this.xAxisLabel, this.yAxisLabel, dataset, PlotOrientation.VERTICAL, this.legend.equals("true"), true, false);
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */ 
/*     */ 
/* 229 */         e.printStackTrace();
/*     */       }
/* 231 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 235 */       ee.printStackTrace(); }
/* 236 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 247 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 257 */       JspWriter out = this.pageContext.getOut();
/* 258 */       Font font = null;
/* 259 */       if (getNodata())
/*     */       {
/* 261 */         return 6;
/*     */       }
/*     */       
/* 264 */       TextTitle customTitle = new TextTitle(this.chartTitle, new Font(this.msFont, 1, 11));
/* 265 */       customTitle.setTextAlignment(HorizontalAlignment.CENTER);
/* 266 */       this.chart.setTitle(customTitle);
/* 267 */       if (this.twoDimensionBar)
/*     */       {
/* 269 */         this.chart.getCategoryPlot().setRangeGridlinesVisible(true);
/* 270 */         this.chart.getCategoryPlot().setDomainGridlinesVisible(true);
/*     */       }
/* 272 */       CategoryPlot plot = this.chart.getCategoryPlot();
/* 273 */       CategoryAxis axis = plot.getDomainAxis();
/* 274 */       ValueAxis domainaxis = plot.getRangeAxis();
/* 275 */       domainaxis.setTickLabelPaint(Color.GRAY);
/* 276 */       axis.setTickLabelPaint(Color.GRAY);
/* 277 */       domainaxis.setTickLabelFont(new Font("VERDANA", 0, 10));
/* 278 */       domainaxis.setLabelPaint(Color.GRAY);
/* 279 */       axis.setLabelPaint(Color.GRAY);
/* 280 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 282 */         axis.setTickLabelFont(new Font("VERDANA", 0, 10));
/*     */       }
/* 284 */       else if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("zh_CN")))
/*     */       {
/* 286 */         domainaxis.setTickLabelPaint(Color.BLACK);
/* 287 */         axis.setTickLabelPaint(Color.BLACK);
/* 288 */         domainaxis.setTickLabelFont(new Font(this.msFont, 1, 10));
/* 289 */         axis.setTickLabelFont(new Font(this.msFont, 1, 10));
/* 290 */         domainaxis.setLabelPaint(Color.BLACK);
/* 291 */         axis.setLabelPaint(Color.BLACK);
/*     */       }
/*     */       
/* 294 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 296 */         axis.setLabelFont(Font.decode(this.msFont));
/* 297 */         domainaxis.setLabelFont(Font.decode(this.msFont));
/*     */       }
/* 299 */       if (this.labelRotation)
/*     */       {
/* 301 */         axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
/*     */       }
/*     */       else
/*     */       {
/* 305 */         axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
/*     */       }
/* 307 */       axis.setMaximumCategoryLabelLines(2);
/*     */       
/*     */ 
/* 310 */       CategoryItemRenderer renderer = null;
/* 311 */       if (this.barcolors == null)
/*     */       {
/* 313 */         if (!this.twoDimensionBar)
/*     */         {
/* 315 */           renderer = new ChartInfo.StackedCustomRenderer(BAR_CHART_UP_DOWN_COLORS);
/*     */         }
/*     */         else
/*     */         {
/* 319 */           renderer = new ChartInfo.StackedCustomRenderer2D(BAR_CHART_UP_DOWN_COLORS);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 324 */       else if (!this.twoDimensionBar)
/*     */       {
/* 326 */         renderer = new ChartInfo.StackedCustomRenderer(this.barcolors);
/*     */       }
/*     */       else
/*     */       {
/* 330 */         renderer = new ChartInfo.StackedCustomRenderer2D(this.barcolors);
/*     */       }
/*     */       
/*     */ 
/* 334 */       if (this.baseItemLabel)
/*     */       {
/* 336 */         ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966D);
/* 337 */         renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
/* 338 */         renderer.setBasePositiveItemLabelPosition(itemlabelposition);
/*     */       }
/* 340 */       renderer.setItemLabelsVisible(true);
/* 341 */       renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
/*     */       
/* 343 */       ((BarRenderer)renderer).setMaximumBarWidth(0.05D);
/* 344 */       if (this.ismaxBarWidthSet)
/*     */       {
/* 346 */         ((BarRenderer)renderer).setMaximumBarWidth(this.maxBarWidth);
/*     */       }
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
/* 361 */       if ("true".equals(this.legend)) {
/* 362 */         LegendTitle legend = this.chart.getLegend();
/* 363 */         if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */         {
/* 365 */           legend.setItemFont(new Font("VERDANA", 0, 11));
/*     */         }
/* 367 */         legend.setItemPaint(Color.GRAY);
/* 368 */         legend.setMargin(new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D));
/* 369 */         legend.setBorder(0.25D, 0.25D, 0.25D, 0.25D);
/*     */       }
/*     */       
/* 372 */       if ((this.datasetproducer != null) && (this.datasetproducer.equals("diskgraph")))
/*     */       {
/* 374 */         List cat = plot.getCategories();
/* 375 */         if (cat != null)
/*     */         {
/* 377 */           if (cat.size() > 8)
/*     */           {
/* 379 */             axis.setVisible(false);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 384 */       this.chart.setBackgroundPaint(Color.white);
/* 385 */       ValueAxis rangeAxis = plot.getRangeAxis();
/* 386 */       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 387 */       rangeAxis.setLowerMargin(0.15D);
/* 388 */       rangeAxis.setUpperMargin(0.15D);
/* 389 */       if (this.url)
/*     */       {
/* 391 */         BarURLGenerator sau = new BarURLGenerator();
/* 392 */         DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/* 393 */         CategoryDataset dataset = (CategoryDataset)dsp.produceDataset(null);
/* 394 */         if (dataset != null)
/*     */         {
/* 396 */           sau.original = dataset;
/*     */         }
/* 398 */         renderer.setItemURLGenerator(sau);
/*     */       }
/* 400 */       plot.setRenderer(renderer);
/* 401 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 402 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 404 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 405 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 410 */       ee.printStackTrace();
/*     */     }
/* 412 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static class BarURLGenerator
/*     */     extends StandardCategoryURLGenerator
/*     */   {
/* 423 */     CategoryDataset original = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String generateURL(CategoryDataset dataset, int series, int category)
/*     */     {
/* 434 */       if (this.original != null)
/*     */       {
/* 436 */         dataset = this.original;
/*     */       }
/* 438 */       String keyname = (String)((DefaultIntervalCategoryDataset)dataset).getColumnKey(category);
/* 439 */       System.out.println("$$$$$$$$$$$ StackBarChart ==============>" + keyname);
/* 440 */       keyname = findReplace(keyname, "'", "\\'");
/*     */       
/* 442 */       if (keyname.contains("_#$@_resid="))
/*     */       {
/* 444 */         String temp = keyname.substring(keyname.indexOf("_#$@_resid=") + 11);
/*     */         
/* 446 */         return "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + temp + "&period=0&Report=true&resourceType=Monitors&resid=" + temp;
/*     */       }
/*     */       
/*     */ 
/* 450 */       return "javascript:alert('" + series + "','" + category + "','" + dataset + ");";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private String findReplace(String str, String find, String replace)
/*     */     {
/* 460 */       String des = new String();
/* 461 */       while (str.indexOf(find) != -1) {
/* 462 */         des = des + str.substring(0, str.indexOf(find));
/* 463 */         des = des + replace;
/* 464 */         str = str.substring(str.indexOf(find) + find.length());
/*     */       }
/* 466 */       des = des + str;
/* 467 */       return des;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\StackBarChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */