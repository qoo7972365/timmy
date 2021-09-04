/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.chart.ChartInfo;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.BodyTag;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.servlet.ChartDeleter;
/*     */ import org.jfree.chart.servlet.ServletUtilities;
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
/*     */ public abstract class BaseTag
/*     */   extends ChartInfo
/*     */   implements BodyTag
/*     */ {
/*  37 */   private BodyTagSupport support = new BodyTagSupport();
/*     */   
/*  39 */   protected PageContext pageContext = null;
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getChartImage(JFreeChart chart, int width, int height, HttpSession session, ChartRenderingInfo info)
/*     */     throws IOException
/*     */   {
/*  46 */     String tempDir = getTempDir();
/*     */     
/*     */ 
/*     */ 
/*  50 */     String fileName = null;
/*  51 */     int newheight = 0;
/*  52 */     int newwidth = 0;
/*  53 */     String zoomedfile = null;
/*  54 */     if (this.zoomLevel != -1.0F)
/*     */     {
/*  56 */       float inc = height * this.zoomLevel / 100.0F;
/*  57 */       newheight = height + (int)inc;
/*     */       
/*  59 */       inc = width * this.zoomLevel / 100.0F;
/*  60 */       newwidth = width + (int)inc;
/*     */     }
/*     */     
/*  63 */     if (info == null)
/*     */     {
/*  65 */       fileName = ServletUtilities.saveChartAsPNG(chart, width, height, session);
/*  66 */       if (this.zoomLevel != -1.0F)
/*     */       {
/*  68 */         zoomedfile = ServletUtilities.saveChartAsPNG(chart, newwidth, newheight, session);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/*  74 */       fileName = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
/*  75 */       if (this.zoomLevel != -1.0F)
/*     */       {
/*  77 */         zoomedfile = ServletUtilities.saveChartAsPNG(chart, newwidth, newheight, info, session);
/*     */       }
/*     */     }
/*  80 */     addChart(fileName);
/*  81 */     if (zoomedfile != null)
/*  82 */       addChart(zoomedfile);
/*  83 */     if (this.zoomLevel != -1.0F)
/*     */     {
/*  85 */       System.out.println("New file name is " + zoomedfile);
/*  86 */       this.pageContext.setAttribute(getZoomName(), zoomedfile);
/*     */     }
/*  88 */     return "webclient/temp/" + fileName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addChart(String filename)
/*     */   {
/* 100 */     ChartDeleter cd = (ChartDeleter)this.pageContext.findAttribute("JFreeChart_Deleter");
/* 101 */     cd.addChart(filename);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBodyContent(BodyContent arg0)
/*     */   {
/* 109 */     this.support.setBodyContent(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void doInitBody()
/*     */     throws JspException
/*     */   {
/* 117 */     this.support.doInitBody();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 126 */     return this.support.doAfterBody();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPageContext(PageContext arg0)
/*     */   {
/* 133 */     this.support.setPageContext(arg0);
/* 134 */     this.pageContext = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParent(Tag arg0)
/*     */   {
/* 142 */     this.support.setParent(arg0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tag getParent()
/*     */   {
/* 150 */     return this.support.getParent();
/*     */   }
/*     */   
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 157 */     return this.support.doStartTag();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/* 165 */     return this.support.doEndTag();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/* 172 */     this.support.release();
/*     */   }
/*     */   
/*     */   public abstract DatasetProducer getDataSet();
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\BaseTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */