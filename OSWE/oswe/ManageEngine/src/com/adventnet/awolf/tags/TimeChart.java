/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Font;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.urls.StandardCategoryURLGenerator;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimeChart
/*     */   extends BaseTag
/*     */ {
/*     */   private JFreeChart chart;
/*     */   
/*     */   public TimeChart()
/*     */   {
/*  30 */     this.chart = null;
/*     */   }
/*     */   
/*     */   public DatasetProducer getDataSet() {
/*  34 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  40 */       setNodata(false);
/*     */       
/*     */ 
/*  43 */       this.chart = generateTimeChart();
/*     */       
/*  45 */       if (this.chart == null)
/*     */       {
/*  47 */         String widthToSet = this.width;
/*  48 */         if (widthToSet == null) {
/*  49 */           widthToSet = "300";
/*     */         }
/*  51 */         String heightToSet = this.height;
/*  52 */         if (heightToSet == null) {
/*  53 */           heightToSet = "150";
/*     */         }
/*     */         
/*  56 */         JspWriter out = this.pageContext.getOut();
/*  57 */         out.println("<table class=\"no-graph\"><tr><td class=\"disabledtext\" align=center>" + this.nodatamessage + "</td></tr></table>");
/*  58 */         setNodata(true);
/*  59 */         return 0;
/*     */       }
/*     */       
/*     */ 
/*  63 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  67 */       ee.printStackTrace(); }
/*  68 */     return 0;
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
/*  79 */     return 6;
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
/*  92 */       JspWriter out = this.pageContext.getOut();
/*  93 */       Font font = null;
/*  94 */       if (getNodata())
/*     */       {
/*  96 */         return 6;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 104 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 106 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/* 108 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 109 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 114 */       ee.printStackTrace();
/*     */     }
/* 116 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 120 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 126 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 128 */     return temptimezone;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static class BarURLGenerator
/*     */     extends StandardCategoryURLGenerator
/*     */   {
/*     */     public String generateURL(CategoryDataset dataset, int series, int category)
/*     */     {
/* 149 */       return "javascript:alert('" + series + "','" + category + "','" + dataset + ");";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\TimeChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */