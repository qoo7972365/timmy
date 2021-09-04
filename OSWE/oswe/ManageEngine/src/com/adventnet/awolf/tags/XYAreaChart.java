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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYAreaChart
/*     */   extends BaseTag
/*     */ {
/*  37 */   private JFreeChart chart = null;
/*     */   
/*     */ 
/*     */ 
/*  41 */   public DatasetProducer getDataSet() { return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer); }
/*     */   
/*  43 */   private Paint seriesPaintColor = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Paint getSeriesPaintColor()
/*     */   {
/*  50 */     return this.seriesPaintColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSeriesPaintColor(Paint v)
/*     */   {
/*  59 */     this.seriesPaintColor = v;
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  65 */       setNodata(false);
/*  66 */       this.chart = generateXYAreaChart();
/*     */       
/*     */ 
/*     */ 
/*  70 */       if (this.chart == null)
/*     */       {
/*  72 */         String widthToSet = this.width;
/*  73 */         if (widthToSet == null) {
/*  74 */           widthToSet = "300";
/*     */         }
/*  76 */         String heightToSet = this.height;
/*  77 */         if (heightToSet == null) {
/*  78 */           heightToSet = "200";
/*     */         }
/*     */         
/*  81 */         JspWriter out = this.pageContext.getOut();
/*  82 */         out.println("<table class=\"no-graph\"><tr><td class=\"disabledtext\" align=center>" + this.nodatamessage + "</td></tr></table>");
/*  83 */         setNodata(true);
/*  84 */         return 0;
/*     */       }
/*     */       
/*     */ 
/*  88 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  92 */       ee.printStackTrace(); }
/*  93 */     return 0;
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
/* 104 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 117 */       JspWriter out = this.pageContext.getOut();
/* 118 */       Font font = null;
/* 119 */       if (getNodata())
/*     */       {
/* 121 */         return 6;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 126 */       XYPlot plot = this.chart.getXYPlot();
/* 127 */       XYItemRenderer renderer = plot.getRenderer();
/*     */       
/* 129 */       if (this.seriesPaintColor != null)
/*     */       {
/* 131 */         renderer.setSeriesPaint(0, this.seriesPaintColor);
/*     */       }
/*     */       else
/*     */       {
/* 135 */         Paint[] bar_chart_colors = BAR_CHART_COLORS;
/* 136 */         String selectedskin = (String)this.pageContext.getAttribute("selectedskin", 3);
/* 137 */         if (selectedskin != null)
/*     */         {
/* 139 */           Hashtable barcolors = (Hashtable)this.pageContext.getAttribute("barcolors", 4);
/* 140 */           if (barcolors != null)
/*     */           {
/* 142 */             Object temp = (Paint[])barcolors.get(selectedskin);
/* 143 */             if (temp != null)
/*     */             {
/* 145 */               bar_chart_colors = (Paint[])temp;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 151 */         float[] fillhsbvals = Color.RGBtoHSB(27, 80, 140, null);
/* 152 */         renderer.setSeriesPaint(0, Color.getHSBColor(fillhsbvals[0], fillhsbvals[1], fillhsbvals[2]));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 157 */       renderer.setToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})", new SimpleDateFormat("HH:mm d-MMM-yyyy"), new DecimalFormat("#,##0.00")));
/*     */       
/* 159 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 160 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 162 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/* 164 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/*     */       
/* 166 */       if (this.link != null)
/*     */       {
/* 168 */         out.println("<a href=\"" + this.link + "\">");
/*     */         
/* 170 */         out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/* 171 */         out.println("</a>");
/*     */       }
/*     */       else
/*     */       {
/* 175 */         out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 181 */       ee.printStackTrace();
/*     */     }
/* 183 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 187 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 193 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 195 */     return temptimezone;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\XYAreaChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */