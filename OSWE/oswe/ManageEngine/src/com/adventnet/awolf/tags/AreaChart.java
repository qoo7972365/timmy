/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.DatasetUtilities;
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
/*     */ public class AreaChart
/*     */   extends BaseTag
/*     */ {
/*  37 */   private JFreeChart chart = null;
/*     */   
/*     */   public DatasetProducer getDataSet()
/*     */   {
/*  41 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  47 */       setNodata(false);
/*  48 */       DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */       
/*  50 */       CategoryDataset dataset = DatasetUtilities.createCategoryDataset("Series ", "Type ", new double[0][0]);
/*     */       
/*  52 */       if (dataset == null)
/*     */       {
/*  54 */         String widthToSet = this.width;
/*  55 */         if (widthToSet == null) {
/*  56 */           widthToSet = "300";
/*     */         }
/*  58 */         String heightToSet = this.height;
/*  59 */         if (heightToSet == null) {
/*  60 */           heightToSet = "150";
/*     */         }
/*     */         
/*  63 */         JspWriter out = this.pageContext.getOut();
/*  64 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + this.nodatamessage + "</td></tr></table>");
/*  65 */         setNodata(true);
/*  66 */         return 0;
/*     */       }
/*     */       
/*     */ 
/*  70 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  74 */       ee.printStackTrace(); }
/*  75 */     return 0;
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
/*  86 */     return 6;
/*     */   }
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
/*  98 */       this.chart = generateAreaChart();
/*  99 */       JspWriter out = this.pageContext.getOut();
/* 100 */       Font font = null;
/* 101 */       if (getNodata())
/*     */       {
/* 103 */         return 6;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 108 */       Paint[] bar_chart_colors = BAR_CHART_COLORS;
/* 109 */       String selectedskin = (String)this.pageContext.getAttribute("selectedskin", 3);
/* 110 */       if (selectedskin != null)
/*     */       {
/* 112 */         Hashtable barcolors = (Hashtable)this.pageContext.getAttribute("barcolors", 4);
/* 113 */         if (barcolors != null)
/*     */         {
/* 115 */           Object temp = (Paint[])barcolors.get(selectedskin);
/* 116 */           if (temp != null)
/*     */           {
/* 118 */             bar_chart_colors = (Paint[])temp;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 123 */       CategoryPlot plot = this.chart.getCategoryPlot();
/* 124 */       CategoryItemRenderer renderer = plot.getRenderer();
/* 125 */       renderer.setSeriesPaint(0, bar_chart_colors[0]);
/* 126 */       DefaultCategoryDataset dset = (DefaultCategoryDataset)plot.getDataset();
/*     */       
/*     */ 
/* 129 */       List cats = plot.getCategories();
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
/* 154 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 155 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 157 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/* 159 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 160 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 165 */       ee.printStackTrace();
/*     */     }
/* 167 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 171 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 177 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 179 */     return temptimezone;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\AreaChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */