/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.labels.StandardXYToolTipGenerator;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackedXYAreaChart
/*     */   extends BaseTag
/*     */ {
/*  34 */   private JFreeChart chart = null;
/*     */   
/*     */ 
/*     */ 
/*  38 */   public DatasetProducer getDataSet() { return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer); }
/*     */   
/*  40 */   private Paint seriesPaintColor = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Paint getSeriesPaintColor()
/*     */   {
/*  47 */     return this.seriesPaintColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSeriesPaintColor(Paint v)
/*     */   {
/*  56 */     this.seriesPaintColor = v;
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  62 */       setNodata(false);
/*  63 */       this.chart = generateStackedXYAreaChart();
/*  64 */       if (this.chart == null)
/*     */       {
/*  66 */         String widthToSet = this.width;
/*  67 */         if (widthToSet == null) {
/*  68 */           widthToSet = "300";
/*     */         }
/*  70 */         String heightToSet = this.height;
/*  71 */         if (heightToSet == null) {
/*  72 */           heightToSet = "200";
/*     */         }
/*     */         
/*  75 */         JspWriter out = this.pageContext.getOut();
/*  76 */         out.println("<table class=\"no-graph\"><tr><td class=\"disabledtext\" align=center>" + this.nodatamessage + "</td></tr></table>");
/*  77 */         setNodata(true);
/*  78 */         return 0;
/*     */       }
/*     */       
/*     */ 
/*  82 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  86 */       ee.printStackTrace(); }
/*  87 */     return 0;
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
/*  98 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 108 */       JspWriter out = this.pageContext.getOut();
/* 109 */       Font font = null;
/* 110 */       if (getNodata())
/*     */       {
/* 112 */         return 6;
/*     */       }
/*     */       
/* 115 */       XYPlot plot = this.chart.getXYPlot();
/* 116 */       XYItemRenderer renderer = plot.getRenderer();
/* 117 */       if (this.seriesPaintColor != null)
/*     */       {
/* 119 */         renderer.setSeriesPaint(0, this.seriesPaintColor);
/*     */       }
/*     */       else
/*     */       {
/* 123 */         Paint[] bar_chart_colors = BAR_CHART_COLORS;
/* 124 */         String selectedskin = (String)this.pageContext.getAttribute("selectedskin", 3);
/* 125 */         if (selectedskin != null)
/*     */         {
/* 127 */           Hashtable barcolors = (Hashtable)this.pageContext.getAttribute("barcolors", 4);
/* 128 */           if (barcolors != null)
/*     */           {
/* 130 */             Object temp = (Paint[])barcolors.get(selectedskin);
/* 131 */             if (temp != null)
/*     */             {
/* 133 */               bar_chart_colors = (Paint[])temp;
/*     */             }
/*     */           }
/*     */         }
/* 137 */         float[] fillhsbvals = Color.RGBtoHSB(27, 80, 140, null);
/* 138 */         renderer.setSeriesPaint(0, Color.getHSBColor(fillhsbvals[0], fillhsbvals[1], fillhsbvals[2]));
/*     */       }
/* 140 */       renderer.setToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})", new SimpleDateFormat("HH:mm d-MMM-yyyy"), new DecimalFormat("#,##0.00")));
/*     */       
/* 142 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 143 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 145 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/* 147 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 148 */       if (this.link != null)
/*     */       {
/* 150 */         out.println("<a href=\"" + this.link + "\">");
/*     */         
/* 152 */         out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/* 153 */         out.println("</a>");
/*     */       }
/*     */       else
/*     */       {
/* 157 */         out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */       }
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 162 */       ee.printStackTrace();
/*     */     }
/* 164 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 168 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 174 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 176 */     return temptimezone;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\StackedXYAreaChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */