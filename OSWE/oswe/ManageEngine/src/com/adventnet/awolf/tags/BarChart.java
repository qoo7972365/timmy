/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.chart.ChartInfo.CustomRenderer;
/*     */ import com.adventnet.awolf.chart.ChartInfo.CustomRenderer2D;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import java.awt.Paint;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.swing.ImageIcon;
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
/*     */ import org.jfree.chart.renderer.category.BarRenderer3D;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.urls.StandardCategoryURLGenerator;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarChart
/*     */   extends BaseTag
/*     */ {
/*     */   private JFreeChart chart;
/*     */   private boolean onedimention;
/*     */   private Paint[] barcolor;
/*     */   
/*     */   public DatasetProducer getDataSet()
/*     */   {
/*  62 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */   public BarChart()
/*     */   {
/*  58 */     this.chart = null;
/*  59 */     this.onedimention = true;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  65 */     this.barcolor = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Paint[] getBarcolor()
/*     */   {
/*  73 */     return this.barcolor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBarcolor(Paint[] v)
/*     */   {
/*  82 */     this.barcolor = v;
/*     */   }
/*     */   
/*     */   private CategoryDataset updateDataSet(double maxvalue, CategoryDataset dataset)
/*     */   {
/*  87 */     DefaultCategoryDataset newdataset = new DefaultCategoryDataset();
/*  88 */     double maxScale = maxvalue + maxvalue * 5.0D / 100.0D;
/*  89 */     double maxScaleValue = 100.0D;
/*  90 */     if (getGraphscale().equals("userdefined")) {
/*  91 */       maxScaleValue = Double.valueOf(getMaxscale()).doubleValue();
/*     */     }
/*  93 */     List columnkeys = dataset.getColumnKeys();
/*  94 */     List rowkeys = dataset.getRowKeys();
/*  95 */     for (int i = 0; i < columnkeys.size(); i++) {
/*  96 */       String key = (String)columnkeys.get(i);
/*  97 */       for (int j = 0; j < rowkeys.size(); j++) {
/*  98 */         String rowkey = (String)rowkeys.get(j);
/*  99 */         Number value = dataset.getValue(rowkey, key);
/* 100 */         if (value.doubleValue() > maxScaleValue) {
/* 101 */           value = Double.valueOf(value.doubleValue() * maxScaleValue / maxScale);
/* 102 */           DecimalFormat df = new DecimalFormat("#.###");
/* 103 */           double scale = maxScale / maxScaleValue;
/* 104 */           scale = Double.valueOf(df.format(scale)).doubleValue();
/*     */           
/* 106 */           key = MessageFormat.format(NmsUtil.GetString("am.mypage.widgets.customscale.text"), new Object[] { key, String.valueOf(scale) });
/*     */         }
/* 108 */         newdataset.setValue(value, rowkey, key);
/*     */       }
/*     */     }
/* 111 */     dataset = newdataset;
/* 112 */     return dataset;
/*     */   }
/*     */   
/*     */   private CategoryDataset updateDataSetValues(CategoryDataset dataset) {
/* 116 */     Number num = getMaximumValue(dataset);
/* 117 */     if (num.doubleValue() > Double.valueOf(getMaxscale()).doubleValue()) {
/* 118 */       setModifyRangeValues(true);
/* 119 */       dataset = updateDataSet(num.doubleValue(), dataset);
/*     */     }
/*     */     
/* 122 */     return dataset;
/*     */   }
/*     */   
/*     */ 
/*     */   private Number getMaximumValue(CategoryDataset dataset)
/*     */   {
/* 128 */     Number maxNum = Integer.valueOf(0);
/* 129 */     List columnkeys = dataset.getColumnKeys();
/* 130 */     List rowkeys = dataset.getRowKeys();
/*     */     
/* 132 */     for (int i = 0; i < columnkeys.size(); i++) {
/* 133 */       String key = (String)columnkeys.get(i);
/* 134 */       for (int j = 0; j < rowkeys.size(); j++) {
/* 135 */         String rowkey = (String)rowkeys.get(j);
/* 136 */         Number value = dataset.getValue(rowkey, key);
/* 137 */         if (value.doubleValue() > maxNum.doubleValue()) {
/* 138 */           maxNum = value;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 143 */     return maxNum;
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 150 */       setNodata(false);
/* 151 */       setModifyRangeValues(false);
/* 152 */       DatasetProducer dsp = null;
/* 153 */       CategoryDataset dataset = null;
/*     */       try
/*     */       {
/* 156 */         dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */       } catch (Exception ep) {
/* 158 */         ep.printStackTrace();
/*     */       }
/*     */       try {
/* 161 */         dataset = (CategoryDataset)dsp.produceDataset(null);
/*     */       } catch (Throwable e) {
/* 163 */         e.printStackTrace();
/*     */       }
/* 165 */       if (dataset != null)
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
/*     */ 
/*     */ 
/* 217 */         if ((dataset instanceof DefaultCategoryDataset))
/*     */         {
/* 219 */           List columnkeys = dataset.getColumnKeys();
/* 220 */           List rowkeys = dataset.getRowKeys();
/* 221 */           DefaultCategoryDataset newdataset = new DefaultCategoryDataset();
/* 222 */           for (int i = 0; i < columnkeys.size(); i++)
/*     */           {
/* 224 */             String key = (String)columnkeys.get(i);
/*     */             
/* 226 */             for (int j = 0; j < rowkeys.size(); j++)
/*     */             {
/*     */ 
/*     */ 
/* 230 */               String rowkey = (String)rowkeys.get(j);
/* 231 */               Number value = dataset.getValue(rowkey, key);
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
/* 242 */               if (key.contains("_#$@_resid="))
/*     */               {
/* 244 */                 String temp = key.substring(0, key.indexOf("_#$@_resid="));
/* 245 */                 newdataset.setValue(value, rowkey, temp);
/*     */               }
/* 247 */               else if (key.contains("_#$_resid="))
/*     */               {
/* 249 */                 String temp = key.substring(0, key.indexOf("_#$_resid="));
/* 250 */                 newdataset.setValue(value, rowkey, temp);
/*     */               }
/*     */               else
/*     */               {
/* 254 */                 newdataset.setValue(value, rowkey, key);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 262 */           dataset = newdataset;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 267 */         JspWriter out = this.pageContext.getOut();
/* 268 */         String widthToSet = this.width;
/* 269 */         if (widthToSet == null) {
/* 270 */           widthToSet = "300";
/*     */         }
/* 272 */         String heightToSet = this.height;
/* 273 */         if (heightToSet == null) {
/* 274 */           heightToSet = "150";
/*     */         }
/* 276 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + this.nodatamessage + "</td></tr></table>");
/* 277 */         setNodata(true);
/* 278 */         return 0;
/*     */       }
/*     */       
/* 281 */       if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */       {
/* 283 */         this.onedimention = false;
/*     */       }
/*     */       
/* 286 */       if ((getGraphscale() != null) && (
/* 287 */         (getGraphscale().equals("userdefined")) || (getGraphscale().equals("autoscale")))) {
/* 288 */         if (getGraphscale().equals("autoscale")) {
/* 289 */           setMinscale("0");
/* 290 */           setMaxscale("100");
/*     */         }
/* 292 */         dataset = updateDataSetValues(dataset);
/*     */       }
/*     */       
/*     */ 
/* 296 */       if (!this.twoDimensionBar)
/*     */       {
/* 298 */         this.chart = ChartFactory.createBarChart3D("", this.xAxisLabel, this.yAxisLabel, dataset, PlotOrientation.VERTICAL, this.legend.equals("true"), true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 311 */         this.chart = ChartFactory.createBarChart("", this.xAxisLabel, this.yAxisLabel, dataset, PlotOrientation.VERTICAL, this.legend.equals("true"), true, false);
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
/* 323 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 327 */       ee.printStackTrace(); }
/* 328 */     return 0;
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
/* 339 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 349 */       JspWriter out = this.pageContext.getOut();
/* 350 */       if (getNodata())
/*     */       {
/* 352 */         return 6;
/*     */       }
/*     */       
/* 355 */       if (this.chart == null) {
/* 356 */         return 6;
/*     */       }
/* 358 */       TextTitle customTitle = new TextTitle(this.chartTitle, new Font(this.msFont, 1, 11));
/* 359 */       customTitle.setTextAlignment(HorizontalAlignment.CENTER);
/* 360 */       this.chart.setTitle(customTitle);
/* 361 */       CategoryPlot plot = this.chart.getCategoryPlot();
/* 362 */       CategoryAxis axis = plot.getDomainAxis();
/* 363 */       ValueAxis domainaxis = plot.getRangeAxis();
/* 364 */       if ((this.labelRotation) && (!this.skipLabelRotation))
/*     */       {
/* 366 */         axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
/*     */       }
/* 368 */       else if (!this.skipLabelRotation)
/*     */       {
/* 370 */         axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
/*     */       }
/* 372 */       if (getModifyRangeValues()) {
/* 373 */         domainaxis.setRange(Double.valueOf(getMinscale()).doubleValue(), Double.valueOf(getMaxscale()).doubleValue());
/*     */       }
/*     */       
/*     */ 
/* 377 */       axis.setLowerMargin(0.02D);
/* 378 */       axis.setCategoryMargin(0.1D);
/* 379 */       axis.setUpperMargin(0.01D);
/*     */       
/*     */ 
/* 382 */       axis.setMaximumCategoryLabelLines(2);
/* 383 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 385 */         axis.setLabelFont(Font.decode(this.msFont));
/* 386 */         domainaxis.setLabelFont(Font.decode(this.msFont));
/*     */       }
/* 388 */       CategoryItemRenderer renderer = null;
/* 389 */       Hashtable colors = (Hashtable)this.pageContext.findAttribute("color");
/* 390 */       if (colors != null)
/*     */       {
/* 392 */         Paint[] bar_chart_colors = new Paint[BAR_CHART_COLORS.length];
/* 393 */         System.arraycopy(BAR_CHART_COLORS, 0, bar_chart_colors, 0, BAR_CHART_COLORS.length);
/* 394 */         Enumeration enumeration = colors.keys();
/* 395 */         int i = 0;
/* 396 */         while (enumeration.hasMoreElements())
/*     */         {
/* 398 */           String id = (String)enumeration.nextElement();
/* 399 */           String color = (String)colors.get(id);
/* 400 */           if (Integer.parseInt(id) < BAR_CHART_COLORS.length) {
/* 401 */             bar_chart_colors[Integer.parseInt(id)] = new Color(Integer.parseInt(color.substring(1, 3), 16), Integer.parseInt(color.substring(3, 5), 16), Integer.parseInt(color.substring(5, 7), 16));
/*     */           }
/*     */         }
/* 404 */         if (!this.twoDimensionBar)
/*     */         {
/* 406 */           renderer = new ChartInfo.CustomRenderer(bar_chart_colors);
/*     */         }
/*     */         else
/*     */         {
/* 410 */           renderer = new ChartInfo.CustomRenderer2D(bar_chart_colors);
/*     */         }
/*     */         
/*     */       }
/* 414 */       else if (this.barcolor != null)
/*     */       {
/* 416 */         if (!this.twoDimensionBar)
/*     */         {
/* 418 */           renderer = new ChartInfo.CustomRenderer(this.barcolor);
/*     */         }
/*     */         else
/*     */         {
/* 422 */           renderer = new ChartInfo.CustomRenderer2D(this.barcolor);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 427 */         Paint[] bar_chart_colors = BAR_CHART_COLORS;
/* 428 */         String selectedskin = (String)this.pageContext.getAttribute("selectedskin", 3);
/* 429 */         if (selectedskin != null)
/*     */         {
/* 431 */           Hashtable barcolors = (Hashtable)this.pageContext.getAttribute("barcolors", 4);
/* 432 */           if (barcolors != null)
/*     */           {
/* 434 */             Object temp = (Paint[])barcolors.get(selectedskin);
/* 435 */             if (temp != null)
/*     */             {
/* 437 */               bar_chart_colors = (Paint[])temp;
/*     */             }
/*     */           }
/*     */         }
/* 441 */         renderer = new ChartInfo.CustomRenderer(bar_chart_colors);
/* 442 */         if (!this.twoDimensionBar)
/*     */         {
/* 444 */           renderer = new ChartInfo.CustomRenderer(bar_chart_colors);
/*     */         }
/*     */         else
/*     */         {
/* 448 */           renderer = new ChartInfo.CustomRenderer2D(bar_chart_colors);
/*     */         }
/*     */       }
/*     */       
/* 452 */       if (!this.twoDimensionBar)
/*     */       {
/* 454 */         ((ChartInfo.CustomRenderer)renderer).setDimention(this.onedimention);
/*     */       }
/*     */       else
/*     */       {
/* 458 */         ((ChartInfo.CustomRenderer2D)renderer).setDimention(this.onedimention);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 463 */       if (this.baseItemLabel)
/*     */       {
/* 465 */         ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966D);
/* 466 */         renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
/* 467 */         renderer.setBasePositiveItemLabelPosition(itemlabelposition);
/*     */       }
/* 469 */       renderer.setItemLabelsVisible(true);
/* 470 */       renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator()
/*     */       {
/*     */         public String generateToolTip(CategoryDataset dataset, int series, int item)
/*     */         {
/* 474 */           if ((dataset instanceof DefaultCategoryDataset))
/*     */           {
/*     */ 
/*     */ 
/* 478 */             Number num = ((DefaultCategoryDataset)dataset).getValue(series, item);
/*     */             
/* 480 */             if ((num instanceof Double))
/*     */             {
/* 482 */               num = new Float(String.valueOf(num));
/* 483 */               float f = num.floatValue();
/* 484 */               float val = Math.round(f * 100.0F) / 100.0F;
/* 485 */               num = new Float(val);
/*     */             }
/*     */             
/* 488 */             return ((DefaultCategoryDataset)dataset).getColumnKey(item) + " = " + num;
/*     */           }
/* 490 */           return "";
/*     */         }
/*     */       });
/* 493 */       if (!this.twoDimensionBar)
/*     */       {
/* 495 */         ((BarRenderer3D)renderer).setMaximumBarWidth(0.05D);
/*     */       }
/*     */       else
/*     */       {
/* 499 */         ((BarRenderer)renderer).setMaximumBarWidth(0.1D);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 504 */       this.chart.setBackgroundPaint(Color.white);
/* 505 */       ImageIcon bgIcon = new ImageIcon(System.getProperty("webnms.rootdir") + "/images/Blue/BG_Outline2.jpg");
/* 506 */       Image bgImage = bgIcon.getImage();
/*     */       
/* 508 */       if (!this.twoDimensionBar)
/*     */       {
/* 510 */         ImageIcon icon = new ImageIcon(System.getProperty("webnms.rootdir") + "/images/new_bg3.gif");
/* 511 */         Image logo = icon.getImage();
/* 512 */         this.chart.getCategoryPlot().setBackgroundImage(logo);
/* 513 */         this.chart.getCategoryPlot().setRangeGridlinesVisible(false);
/* 514 */         this.chart.getCategoryPlot().setDomainGridlinesVisible(false);
/* 515 */         this.chart.setBackgroundImageAlpha(1.0F);
/*     */       }
/*     */       else
/*     */       {
/* 519 */         this.chart.getCategoryPlot().setRangeGridlinesVisible(true);
/* 520 */         this.chart.getCategoryPlot().setDomainGridlinesVisible(true);
/*     */       }
/* 522 */       ValueAxis rangeAxis = plot.getRangeAxis();
/*     */       
/* 524 */       rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
/* 525 */       rangeAxis.setLowerMargin(0.15D);
/* 526 */       rangeAxis.setUpperMargin(0.15D);
/* 527 */       rangeAxis.setTickLabelPaint(Color.GRAY);
/* 528 */       rangeAxis.setTickLabelFont(new Font("VERDANA", 0, 10));
/* 529 */       rangeAxis.setLabelPaint(Color.GRAY);
/* 530 */       axis.setTickLabelPaint(Color.GRAY);
/* 531 */       axis.setLabelPaint(Color.GRAY);
/* 532 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 534 */         axis.setTickLabelFont(new Font("VERDANA", 0, 10));
/*     */       }
/* 536 */       else if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("zh_CN")))
/*     */       {
/* 538 */         rangeAxis.setTickLabelPaint(Color.BLACK);
/* 539 */         rangeAxis.setTickLabelFont(new Font(this.msFont, 1, 10));
/* 540 */         rangeAxis.setLabelPaint(Color.BLACK);
/* 541 */         axis.setTickLabelPaint(Color.BLACK);
/* 542 */         axis.setLabelPaint(Color.BLACK);
/* 543 */         axis.setTickLabelFont(new Font(this.msFont, 1, 10));
/*     */       }
/*     */       
/* 546 */       if (this.url)
/*     */       {
/* 548 */         BarURLGenerator sau = new BarURLGenerator();
/* 549 */         DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/* 550 */         CategoryDataset dataset = (CategoryDataset)dsp.produceDataset(null);
/* 551 */         if (dataset != null)
/*     */         {
/* 553 */           sau.original = dataset;
/*     */         }
/* 555 */         renderer.setItemURLGenerator(sau);
/*     */       }
/* 557 */       plot.setRenderer(renderer);
/* 558 */       BarRenderer barrenderer = (BarRenderer)plot.getRenderer();
/* 559 */       barrenderer.setItemMargin(0.001D);
/* 560 */       if (this.ismaxBarWidthSet)
/*     */       {
/* 562 */         barrenderer.setMaximumBarWidth(this.maxBarWidth);
/*     */       }
/* 564 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 565 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 567 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 568 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 573 */       ee.printStackTrace();
/*     */     }
/* 575 */     return 6;
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
/* 586 */     CategoryDataset original = null;
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
/* 597 */       if (this.original != null)
/*     */       {
/* 599 */         dataset = this.original;
/*     */       }
/* 601 */       if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */       {
/* 603 */         Number value = ((DefaultIntervalCategoryDataset)dataset).getEndValue(series, category);
/* 604 */         String keyname = (String)((DefaultIntervalCategoryDataset)dataset).getColumnKey(category);
/* 605 */         keyname = findReplace(keyname, "'", "\\'");
/* 606 */         return "javascript:fnCallLink('" + keyname + "','" + value + "')";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 611 */       Number value = ((DefaultCategoryDataset)dataset).getValue(series, category);
/* 612 */       String keyname = (String)((DefaultCategoryDataset)dataset).getColumnKey(category);
/*     */       
/* 614 */       keyname = findReplace(keyname, "'", "\\'");
/*     */       
/* 616 */       if (keyname.contains("_#$@_resid="))
/*     */       {
/* 618 */         String temp = keyname.substring(keyname.indexOf("_#$@_resid=") + 11);
/*     */         
/* 620 */         return "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + temp + "&period=0&Report=true&resourceType=Monitors&resid=" + temp;
/*     */       }
/*     */       
/*     */ 
/* 624 */       if (keyname.contains("_#$_resid="))
/*     */       {
/* 626 */         String temp = keyname.substring(keyname.indexOf("_#$_resid=") + 10);
/* 627 */         String resid = temp.substring(0, temp.indexOf("#"));
/* 628 */         String attributeid = temp.substring(temp.indexOf("#") + 1);
/* 629 */         return "/showHistoryData.do?method=getData&resourceid=" + resid + "&attributeid=" + attributeid + "&period=0";
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
/* 641 */       return "javascript:fnCallLink('" + keyname + "','" + value + "')";
/*     */     }
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
/*     */     private String findReplace(String str, String find, String replace)
/*     */     {
/* 655 */       String des = new String();
/* 656 */       while (str.indexOf(find) != -1) {
/* 657 */         des = des + str.substring(0, str.indexOf(find));
/* 658 */         des = des + replace;
/* 659 */         str = str.substring(str.indexOf(find) + find.length());
/*     */       }
/* 661 */       des = des + str;
/* 662 */       return des;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\BarChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */