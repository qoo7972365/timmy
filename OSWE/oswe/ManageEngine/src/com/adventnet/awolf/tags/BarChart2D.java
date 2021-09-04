/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import java.awt.Paint;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.swing.ImageIcon;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.renderer.category.BarRenderer;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ 
/*     */ public class BarChart2D extends BaseTag
/*     */ {
/*     */   private JFreeChart chart;
/*     */   private boolean onedimention;
/*     */   
/*     */   public BarChart2D()
/*     */   {
/*  33 */     this.chart = null;
/*  34 */     this.onedimention = true;
/*     */   }
/*     */   
/*  37 */   public DatasetProducer getDataSet() { return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer); }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  43 */       setNodata(false);
/*  44 */       DatasetProducer dsp = null;
/*  45 */       CategoryDataset dataset = null;
/*     */       try
/*     */       {
/*  48 */         dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */       } catch (Exception ep) {
/*  50 */         ep.printStackTrace();
/*     */       }
/*     */       try {
/*  53 */         dataset = (CategoryDataset)dsp.produceDataset(null);
/*     */       } catch (Throwable e) {
/*  55 */         e.printStackTrace();
/*     */       }
/*  57 */       if (dataset != null)
/*     */       {
/*  59 */         if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */         {
/*  61 */           List categories = ((DefaultIntervalCategoryDataset)dataset).getCategories();
/*  62 */           String[] modified = new String[categories.size()];
/*  63 */           for (int i = 0; i < categories.size(); i++)
/*     */           {
/*  65 */             String key = (String)categories.get(i);
/*  66 */             if (key.length() > 10)
/*     */             {
/*  68 */               modified[i] = (key.substring(0, 9) + "..");
/*     */             }
/*     */             else
/*     */             {
/*  72 */               modified[i] = key;
/*     */             }
/*     */           }
/*  75 */           ((DefaultIntervalCategoryDataset)dataset).setCategoryKeys(modified);
/*     */         }
/*  77 */         if ((dataset instanceof DefaultCategoryDataset))
/*     */         {
/*  79 */           List columnkeys = ((DefaultCategoryDataset)dataset).getColumnKeys();
/*  80 */           List rowkeys = ((DefaultCategoryDataset)dataset).getRowKeys();
/*  81 */           DefaultCategoryDataset newdataset = new DefaultCategoryDataset();
/*  82 */           for (int i = 0; i < columnkeys.size(); i++)
/*     */           {
/*  84 */             String key = (String)columnkeys.get(i);
/*  85 */             for (int j = 0; j < rowkeys.size(); j++)
/*     */             {
/*  87 */               if (key.length() > 10)
/*     */               {
/*  89 */                 String rowkey = (String)rowkeys.get(j);
/*  90 */                 Number value = ((DefaultCategoryDataset)dataset).getValue(rowkey, key);
/*  91 */                 newdataset.addValue(value, rowkey, key.substring(0, 9) + "..");
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/*  96 */                 String rowkey = (String)rowkeys.get(j);
/*  97 */                 Number value = ((DefaultCategoryDataset)dataset).getValue(rowkey, key);
/*  98 */                 newdataset.addValue(value, rowkey, key);
/*     */               }
/*     */             }
/*     */           }
/* 102 */           dataset = newdataset;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 107 */         JspWriter out = this.pageContext.getOut();
/* 108 */         String widthToSet = this.width;
/* 109 */         if (widthToSet == null) {
/* 110 */           widthToSet = "300";
/*     */         }
/* 112 */         String heightToSet = this.height;
/* 113 */         if (heightToSet == null) {
/* 114 */           heightToSet = "150";
/*     */         }
/* 116 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + this.nodatamessage + "</td></tr></table>");
/* 117 */         setNodata(true);
/* 118 */         return 0;
/*     */       }
/*     */       
/* 121 */       if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */       {
/* 123 */         this.onedimention = true;
/*     */       }
/*     */       
/* 126 */       this.chart = org.jfree.chart.ChartFactory.createBarChart("", this.xAxisLabel, this.yAxisLabel, dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
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
/* 138 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 142 */       ee.printStackTrace(); }
/* 143 */     return 0;
/*     */   }
/*     */   
/*     */   public int doAfterBody() throws JspException
/*     */   {
/* 148 */     return 6;
/*     */   }
/*     */   
/*     */   public int doEndTag() throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 155 */       JspWriter out = this.pageContext.getOut();
/* 156 */       if (getNodata())
/*     */       {
/* 158 */         return 6;
/*     */       }
/*     */       
/* 161 */       if (this.chart == null) {
/* 162 */         return 6;
/*     */       }
/*     */       
/* 165 */       CategoryPlot plot = this.chart.getCategoryPlot();
/* 166 */       CategoryAxis axis = plot.getDomainAxis();
/* 167 */       ValueAxis domainaxis = plot.getRangeAxis();
/* 168 */       axis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
/* 169 */       axis.setLowerMargin(0.02D);
/* 170 */       axis.setCategoryMargin(0.1D);
/* 171 */       axis.setUpperMargin(0.02D);
/*     */       
/*     */ 
/* 174 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 176 */         axis.setLabelFont(Font.decode(this.msFont));
/* 177 */         domainaxis.setLabelFont(Font.decode(this.msFont));
/*     */       }
/* 179 */       CategoryItemRenderer renderer = null;
/* 180 */       Hashtable colors = (Hashtable)this.pageContext.findAttribute("color");
/* 181 */       if (colors != null)
/*     */       {
/* 183 */         Paint[] bar_chart_colors = new Paint[BAR_CHART_COLORS.length];
/* 184 */         System.arraycopy(BAR_CHART_COLORS, 0, bar_chart_colors, 0, BAR_CHART_COLORS.length);
/* 185 */         Enumeration enumeration = colors.keys();
/* 186 */         int i = 0;
/* 187 */         while (enumeration.hasMoreElements())
/*     */         {
/* 189 */           String id = (String)enumeration.nextElement();
/* 190 */           String color = (String)colors.get(id);
/* 191 */           if (Integer.parseInt(id) < BAR_CHART_COLORS.length)
/*     */           {
/* 193 */             bar_chart_colors[Integer.parseInt(id)] = new Color(Integer.parseInt(color.substring(1, 3), 16), Integer.parseInt(color.substring(3, 5), 16), Integer.parseInt(color.substring(5, 7), 16));
/*     */           }
/*     */         }
/*     */         
/* 197 */         renderer = new CustomMultipleRenderer(new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.orange, Color.cyan, Color.magenta, Color.blue });
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 203 */         Paint[] bar_chart_colors = BAR_CHART_COLORS;
/* 204 */         String selectedskin = (String)this.pageContext.getAttribute("selectedskin", 3);
/* 205 */         if (selectedskin != null)
/*     */         {
/* 207 */           Hashtable barcolors = (Hashtable)this.pageContext.getAttribute("barcolors", 4);
/* 208 */           if (barcolors != null)
/*     */           {
/* 210 */             Object temp = (Paint[])barcolors.get(selectedskin);
/* 211 */             if (temp != null)
/*     */             {
/* 213 */               bar_chart_colors = (Paint[])temp;
/*     */             }
/*     */           }
/*     */         }
/* 217 */         renderer = new CustomMultipleRenderer(new Paint[] { Color.red, Color.blue, Color.green, Color.yellow, Color.orange, Color.cyan, Color.magenta, Color.blue });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 225 */       renderer.setBaseItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
/* 226 */       renderer.setItemLabelsVisible(true);
/* 227 */       renderer.setToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator()
/*     */       {
/*     */         public String generateToolTip(CategoryDataset dataset, int series, int item)
/*     */         {
/* 231 */           if ((dataset instanceof DefaultCategoryDataset))
/*     */           {
/* 233 */             Number num = ((DefaultCategoryDataset)dataset).getValue(series, item);
/* 234 */             if ((num instanceof Double))
/*     */             {
/* 236 */               num = new Float(String.valueOf(num));
/* 237 */               float f = num.floatValue();
/* 238 */               float val = Math.round(f * 100.0F) / 100.0F;
/* 239 */               num = new Float(val);
/*     */             }
/* 241 */             return ((DefaultCategoryDataset)dataset).getColumnKey(item) + " = " + num;
/*     */           }
/* 243 */           return "";
/*     */         }
/* 245 */       });
/* 246 */       ((BarRenderer)renderer).setMaximumBarWidth(60.0D);
/* 247 */       ((BarRenderer)renderer).setDrawBarOutline(true);
/* 248 */       ((BarRenderer)renderer).setItemMargin(0.001D);
/*     */       
/* 250 */       renderer.setOutlinePaint(Color.black);
/* 251 */       ((BarRenderer)renderer).setDrawBarOutline(true);
/*     */       
/* 253 */       this.chart.setBackgroundPaint(Color.white);
/* 254 */       ImageIcon bgIcon = new ImageIcon(System.getProperty("webnms.rootdir") + "/images/Blue/BG_Outline2.jpg");
/* 255 */       Image bgImage = bgIcon.getImage();
/*     */       
/* 257 */       ImageIcon icon = new ImageIcon(System.getProperty("webnms.rootdir") + "/images/new_bg3.gif");
/* 258 */       Image logo = icon.getImage();
/* 259 */       this.chart.getCategoryPlot().setBackgroundImage(logo);
/* 260 */       this.chart.getCategoryPlot().setRangeGridlinesVisible(true);
/* 261 */       this.chart.getCategoryPlot().setDomainGridlinesVisible(true);
/* 262 */       this.chart.setBackgroundImageAlpha(1.0F);
/* 263 */       ValueAxis rangeAxis = plot.getRangeAxis();
/* 264 */       rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
/* 265 */       rangeAxis.setLowerMargin(0.15D);
/* 266 */       rangeAxis.setUpperMargin(0.15D);
/*     */       
/* 268 */       if (this.url)
/*     */       {
/* 270 */         BarURLGenerator sau = new BarURLGenerator();
/* 271 */         DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/* 272 */         CategoryDataset dataset = (CategoryDataset)dsp.produceDataset(null);
/* 273 */         if (dataset != null)
/*     */         {
/* 275 */           sau.original = dataset;
/*     */         }
/* 277 */         renderer.setItemURLGenerator(sau);
/*     */       }
/* 279 */       plot.setRenderer(renderer);
/* 280 */       BarRenderer barrenderer = (BarRenderer)plot.getRenderer();
/*     */       
/* 282 */       barrenderer.setItemMargin(0.001D);
/* 283 */       if (this.ismaxBarWidthSet)
/*     */       {
/* 285 */         barrenderer.setMaximumBarWidth(this.maxBarWidth);
/*     */       }
/* 287 */       org.jfree.chart.ChartRenderingInfo info = new org.jfree.chart.ChartRenderingInfo();
/* 288 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 290 */       org.jfree.chart.ChartUtilities.writeImageMap(new java.io.PrintWriter(out), ret, info, false);
/* 291 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 296 */       ee.printStackTrace();
/*     */     }
/* 298 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static class BarURLGenerator
/*     */     extends org.jfree.chart.urls.StandardCategoryURLGenerator
/*     */   {
/* 306 */     CategoryDataset original = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String generateURL(CategoryDataset dataset, int series, int category)
/*     */     {
/* 314 */       if (this.original != null)
/*     */       {
/* 316 */         dataset = this.original;
/*     */       }
/* 318 */       if ((dataset instanceof DefaultIntervalCategoryDataset))
/*     */       {
/* 320 */         Number value = ((DefaultIntervalCategoryDataset)dataset).getEndValue(series, category);
/* 321 */         String keyname = (String)((DefaultIntervalCategoryDataset)dataset).getColumnKey(category);
/* 322 */         keyname = findReplace(keyname, "'", "\\'");
/* 323 */         return "javascript:fnCallLink('" + keyname + "','" + value + "')";
/*     */       }
/*     */       
/*     */ 
/* 327 */       Number value = ((DefaultCategoryDataset)dataset).getValue(series, category);
/* 328 */       String keyname = (String)((DefaultCategoryDataset)dataset).getColumnKey(category);
/* 329 */       keyname = findReplace(keyname, "'", "\\'");
/* 330 */       return "javascript:fnCallLink('" + keyname + "','" + value + "')";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private String findReplace(String str, String find, String replace)
/*     */     {
/* 341 */       String des = new String();
/* 342 */       while (str.indexOf(find) != -1) {
/* 343 */         des = des + str.substring(0, str.indexOf(find));
/* 344 */         des = des + replace;
/* 345 */         str = str.substring(str.indexOf(find) + find.length());
/*     */       }
/* 347 */       des = des + str;
/* 348 */       return des;
/*     */     }
/*     */   }
/*     */   
/*     */   class CustomMultipleRenderer extends BarRenderer
/*     */   {
/* 354 */     private Paint[] colors = null;
/*     */     
/* 356 */     public CustomMultipleRenderer(Paint[] colors) { this.colors = colors; }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Paint getItemPaint(int row, int column)
/*     */     {
/* 363 */       return this.colors[(column % this.colors.length)];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\BarChart2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */