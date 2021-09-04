/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Font;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
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
/*     */ public class SparkSeries
/*     */   extends BaseTag
/*     */ {
/*     */   String listname;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public SparkSeries()
/*     */   {
/*  44 */     this.listname = null;
/*     */     
/*  46 */     this.chart = null;
/*     */   }
/*     */   
/*     */   public DatasetProducer getDataSet() {
/*  50 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
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
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 105 */       ArrayList list = (ArrayList)this.pageContext.findAttribute(this.listname);
/* 106 */       this.chart = generateSparkSeriesChart(list);
/* 107 */       JspWriter out = this.pageContext.getOut();
/* 108 */       Font font = null;
/* 109 */       if (getNodata())
/*     */       {
/* 111 */         return 6;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 118 */       ChartRenderingInfo info = new ChartRenderingInfo();
/*     */       
/*     */ 
/* 121 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 123 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/*     */ 
/* 126 */       ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/* 127 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 132 */       ee.printStackTrace();
/*     */     }
/* 134 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 138 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 144 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 146 */     return temptimezone;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getListName()
/*     */   {
/* 153 */     return this.listname;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setListName(String list1)
/*     */   {
/* 159 */     this.listname = list1;
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
/*     */ 
/*     */   static class BarURLGenerator
/*     */     extends StandardCategoryURLGenerator
/*     */   {
/*     */     public String generateURL(CategoryDataset dataset, int series, int category)
/*     */     {
/* 181 */       return "javascript:alert('" + series + "','" + category + "','" + dataset + ");";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\SparkSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */