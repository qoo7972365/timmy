/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import com.adventnet.nms.util.NmsUtil;
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
/*     */ import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
/*     */ import org.jfree.chart.plot.MultiplePiePlot;
/*     */ import org.jfree.chart.plot.PiePlot;
/*     */ import org.jfree.chart.urls.StandardCategoryURLGenerator;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.TableOrder;
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
/*     */ public class MultiPieChart
/*     */   extends BaseTag
/*     */ {
/*     */   private Paint[] barcolors;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public Paint[] getBarcolors()
/*     */   {
/*  52 */     return this.barcolors;
/*     */   }
/*     */   
/*     */   public DatasetProducer getDataSet() {
/*  56 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBarcolors(Paint[] v)
/*     */   {
/*  64 */     this.barcolors = v;
/*     */   }
/*     */   
/*     */   public MultiPieChart()
/*     */   {
/*  44 */     this.barcolors = null;
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
/*  69 */     this.chart = null;
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  76 */       DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*  77 */       CategoryDataset dataset = (CategoryDataset)dsp.produceDataset(null);
/*     */       
/*  79 */       if (dataset != null)
/*     */       {
/*  81 */         setNodata(false);
/*     */         
/*  83 */         int sizeofmonitors = ((DefaultIntervalCategoryDataset)dataset).getSeriesCount();
/*  84 */         if (sizeofmonitors > 4)
/*     */         {
/*  86 */           this.width = "800";
/*  87 */           this.height = "300";
/*     */         }
/*     */         else
/*     */         {
/*  91 */           this.width = "400";
/*  92 */           this.height = "250";
/*     */         }
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
/* 114 */         if ((dataset instanceof DefaultCategoryDataset))
/*     */         {
/*     */ 
/* 117 */           List columnkeys = ((DefaultCategoryDataset)dataset).getColumnKeys();
/* 118 */           List rowkeys = ((DefaultCategoryDataset)dataset).getRowKeys();
/* 119 */           DefaultCategoryDataset newdataset = new DefaultCategoryDataset();
/* 120 */           for (int i = 0; i < columnkeys.size(); i++)
/*     */           {
/* 122 */             String key = (String)columnkeys.get(i);
/* 123 */             for (int j = 0; j < rowkeys.size(); j++)
/*     */             {
/* 125 */               if (key.length() > 10)
/*     */               {
/* 127 */                 String rowkey = (String)rowkeys.get(j);
/* 128 */                 Number value = ((DefaultCategoryDataset)dataset).getValue(rowkey, key);
/* 129 */                 newdataset.addValue(value, rowkey, key.substring(0, 9) + "..");
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 134 */                 String rowkey = (String)rowkeys.get(j);
/* 135 */                 Number value = ((DefaultCategoryDataset)dataset).getValue(rowkey, key);
/* 136 */                 newdataset.addValue(value, rowkey, key);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 143 */           dataset = newdataset;
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 149 */         String widthToSet = this.width;
/* 150 */         if (widthToSet == null) {
/* 151 */           widthToSet = "0";
/*     */         }
/* 153 */         String heightToSet = this.height;
/* 154 */         if (heightToSet == null) {
/* 155 */           heightToSet = "0";
/*     */         }
/*     */         
/* 158 */         JspWriter out = this.pageContext.getOut();
/* 159 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + this.nodatamessage + "</td></tr></table>");
/* 160 */         setNodata(true);
/* 161 */         return 0;
/*     */       }
/*     */       
/* 164 */       this.chart = ChartFactory.createMultiplePieChart("", dataset, TableOrder.BY_ROW, this.legend.equals("true"), true, false);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 172 */       this.chart.setBorderVisible(false);
/* 173 */       return 1;
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/* 177 */       ee.printStackTrace(); }
/* 178 */     return 0;
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
/* 189 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 200 */       JspWriter out = this.pageContext.getOut();
/* 201 */       Font font = null;
/* 202 */       if (getNodata())
/*     */       {
/* 204 */         return 6;
/*     */       }
/*     */       
/*     */ 
/* 208 */       MultiplePiePlot mp = (MultiplePiePlot)this.chart.getPlot();
/* 209 */       JFreeChart cha = mp.getPieChart();
/* 210 */       cha.setBorderVisible(false);
/* 211 */       this.chart.setBorderVisible(false);
/* 212 */       this.chart.setBackgroundPaint(Color.white);
/* 213 */       mp.setBackgroundPaint(Color.white);
/* 214 */       mp.setOutlinePaint(Color.white);
/* 215 */       mp.setOutlineStroke(null);
/*     */       
/* 217 */       System.out.println("*********** MULTIPIECHART ***********");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 222 */       PiePlot plot1 = (PiePlot)mp.getPieChart().getPlot();
/* 223 */       plot1.setSectionPaint(NmsUtil.GetString("am.webclient.hometab.monitorssnapshot.key.up"), new Color(0, 255, 0));
/* 224 */       plot1.setSectionPaint(NmsUtil.GetString("am.webclient.hometab.monitorssnapshot.key.down"), new Color(255, 0, 0));
/* 225 */       plot1.setSectionPaint(NmsUtil.GetString("am.webclient.dashboard.availability.legend4.text"), new Color(0, 102, 204));
/* 226 */       plot1.setSectionPaint(NmsUtil.GetString("am.webclient.dashboard.availability.legend5.text"), new Color(255, 0, 255));
/* 227 */       plot1.setInsets(new RectangleInsets(5.0D, 5.0D, 5.0D, 5.0D));
/* 228 */       plot1.setForegroundAlpha(1.0F);
/* 229 */       plot1.setOutlinePaint(Color.white);
/* 230 */       if ((System.getProperty("locale") != null) && (System.getProperty("locale").equalsIgnoreCase("en_US")))
/*     */       {
/* 232 */         plot1.setLabelFont(Font.decode(this.msFont));
/*     */       }
/*     */       
/* 235 */       plot1.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
/*     */       
/* 237 */       plot1.setShadowXOffset(0.0D);
/* 238 */       plot1.setShadowYOffset(0.0D);
/* 239 */       plot1.setLabelBackgroundPaint(Color.white);
/* 240 */       plot1.setShadowPaint(Color.white);
/*     */       
/* 242 */       plot1.setLabelLinkMargin(0.0D);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 247 */       this.pageContext.removeAttribute("color");
/*     */       
/* 249 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 250 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 252 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 253 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/* 258 */       ee.printStackTrace();
/*     */     }
/* 260 */     return 6;
/*     */   }
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
/*     */   static class BarURLGenerator
/*     */     extends StandardCategoryURLGenerator
/*     */   {
/*     */     public String generateURL(CategoryDataset dataset, int series, int category)
/*     */     {
/* 277 */       return "javascript:alert('" + series + "','" + category + "','" + dataset + ");";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\MultiPieChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */