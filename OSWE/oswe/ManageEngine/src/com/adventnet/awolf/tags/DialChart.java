/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.awt.Font;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.urls.StandardCategoryURLGenerator;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.DefaultValueDataset;
/*     */ 
/*     */ public class DialChart
/*     */   extends BaseTag
/*     */ {
/*     */   private JFreeChart chart;
/*     */   
/*     */   public DialChart()
/*     */   {
/*  24 */     this.chart = null;
/*     */   }
/*     */   
/*     */   public DatasetProducer getDataSet() {
/*  28 */     return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*     */   }
/*     */   
/*     */   public int doStartTag() throws JspException
/*     */   {
/*     */     try {
/*  34 */       setNodata(false);
/*  35 */       if (Boolean.parseBoolean(this.eumDial))
/*     */       {
/*  37 */         this.chart = createEumMeterChart();
/*     */       }
/*     */       else
/*     */       {
/*  41 */         this.chart = createCustomMeterChart();
/*     */       }
/*     */       
/*  44 */       if (this.chart == null)
/*     */       {
/*  46 */         String widthToSet = this.width;
/*  47 */         if (widthToSet == null) {
/*  48 */           widthToSet = "300";
/*     */         }
/*  50 */         String heightToSet = this.height;
/*  51 */         if (heightToSet == null) {
/*  52 */           heightToSet = "150";
/*     */         }
/*     */         
/*  55 */         JspWriter out = this.pageContext.getOut();
/*  56 */         out.println("<table class=\"no-graph-dial\" ><tr><td class=\"disabledtext\" align=center>" + this.nodatamessage + "</td></tr></table>");
/*  57 */         setNodata(true);
/*  58 */         return 0;
/*     */       }
/*  60 */       DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*  61 */       DefaultValueDataset dataset = (DefaultValueDataset)dsp.produceDataset(null);
/*     */       
/*     */ 
/*  64 */       return 1;
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  68 */       ee.printStackTrace(); }
/*  69 */     return 0;
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
/*  80 */     return 6;
/*     */   }
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
/*  94 */       JspWriter out = this.pageContext.getOut();
/*  95 */       Font font = null;
/*  96 */       if (getNodata())
/*     */       {
/*  98 */         return 6;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 105 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 106 */       String ret = getChartImage(this.chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 108 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/*     */ 
/* 111 */       if (this.link != null)
/*     */       {
/* 113 */         out.println("<a href=\"javascript:void(0)\" onClick=\"" + this.link + "\">");
/*     */         
/* 115 */         out.println("<img src=/" + ret + " border=\"0\">");
/* 116 */         out.println("</a>");
/*     */       }
/*     */       else
/*     */       {
/* 120 */         out.println("<img src=/" + ret + " border=\"0\">");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 126 */       ee.printStackTrace();
/*     */     }
/* 128 */     return 6;
/*     */   }
/*     */   
/*     */   public Hashtable getColors() {
/* 132 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */   
/*     */ 
/*     */   public String getTimezoneid()
/*     */   {
/* 138 */     String temptimezone = (String)this.pageContext.findAttribute("timezone");
/*     */     
/* 140 */     return temptimezone;
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
/* 161 */       return "javascript:alert('" + series + "','" + category + "','" + dataset + ");";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\DialChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */