/*     */ package com.adventnet.awolf.tags;
/*     */ 
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import com.adventnet.awolf.data.UrlProducer;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.awt.Font;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.data.general.DefaultPieDataset;
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
/*     */ public class AMWolf
/*     */   extends BaseTag
/*     */ {
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  39 */     return 1;
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
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/*  54 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  59 */   public DatasetProducer getDataSet() { return (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer); }
/*     */   
/*  61 */   String classname = null;
/*  62 */   DefaultPieDataset data = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  71 */       JspWriter out = this.pageContext.getOut();
/*  72 */       Font font = null;
/*  73 */       DatasetProducer dsp = (DatasetProducer)this.pageContext.findAttribute(this.datasetproducer);
/*  74 */       this.classname = dsp.toString();
/*  75 */       String widthToSet = this.width;
/*  76 */       if (widthToSet == null) {
/*  77 */         widthToSet = "300";
/*     */       }
/*  79 */       String heightToSet = this.height;
/*  80 */       if (heightToSet == null) {
/*  81 */         heightToSet = "150";
/*     */       }
/*     */       try
/*     */       {
/*  85 */         widthToSet = String.valueOf(Integer.parseInt(widthToSet) - 5);
/*  86 */         heightToSet = String.valueOf(Integer.parseInt(heightToSet) - 5);
/*     */       }
/*     */       catch (NumberFormatException ne)
/*     */       {
/*  90 */         ne.printStackTrace();
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  95 */         this.data = ((DefaultPieDataset)dsp.produceDataset(null));
/*  96 */         if (this.data == null) {
/*  97 */           throw new NullPointerException();
/*     */         }
/*     */       } catch (IllegalStateException exp) {
/* 100 */         exp.printStackTrace();
/* 101 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + exp.getMessage() + "</td></tr></table>");
/* 102 */         return 6;
/*     */       } catch (NullPointerException exp) {
/* 104 */         exp.printStackTrace();
/* 105 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + NmsUtil.GetString("am.webclient.manager.slatab.nodatamessage.text") + "</td></tr></table>");
/* 106 */         return 6;
/*     */       }
/*     */       catch (Throwable th) {
/* 109 */         th.printStackTrace();
/* 110 */         out.println("<table class=\"grayfullborder\" width=" + widthToSet + " height=" + heightToSet + "><tr><td class=\"bodytextbold\" align=center>" + "An error occured while generating the graph. Please create a support information file and send it to appmanager-support@manageengine.com. <a href=adminAction.do?method=sendSupport>Create</a>" + "</td></tr></table>");
/* 111 */         return 6;
/*     */       }
/*     */       
/*     */ 
/* 115 */       JFreeChart chart = generatePieChart(this.data);
/*     */       
/* 117 */       this.urls = ((Hashtable)this.pageContext.findAttribute("url"));
/* 118 */       if (this.urls == null)
/*     */       {
/*     */ 
/* 121 */         if ((dsp instanceof UrlProducer))
/*     */         {
/* 123 */           this.urls = ((Hashtable)((UrlProducer)dsp).produceUrl(null));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 129 */       ChartRenderingInfo info = new ChartRenderingInfo();
/* 130 */       String ret = getChartImage(chart, Integer.parseInt(this.width), Integer.parseInt(this.height), ((HttpServletRequest)this.pageContext.getRequest()).getSession(), info);
/*     */       
/* 132 */       this.pageContext.getRequest().setAttribute("ChartImagePath", ret);
/*     */       
/* 134 */       String cht = ret;
/* 135 */       String first = null;
/* 136 */       if ((first != null) && (first.equalsIgnoreCase("true")))
/*     */       {
/* 138 */         cht = "../" + ret;
/* 139 */         ChartUtilities.writeImageMap(new PrintWriter(out), cht, info, false);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 145 */         ChartUtilities.writeImageMap(new PrintWriter(out), ret, info, false);
/*     */       }
/*     */       
/* 148 */       out.println("<img src=/" + ret + " border=\"0\"  USEMAP=\"#" + ret + "\">");
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*     */ 
/*     */ 
/* 156 */       ee.printStackTrace();
/*     */     }
/* 158 */     return 6;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Hashtable getColors()
/*     */   {
/* 164 */     if (this.classname.indexOf("GetWLSGraph") != -1)
/*     */     {
/* 166 */       Hashtable colors = new Hashtable();
/*     */       try {
/* 168 */         List keys = this.data.getKeys();
/* 169 */         String legendValue = null;
/* 170 */         Iterator iterator = keys.iterator();
/* 171 */         int i = 0;
/* 172 */         while (iterator.hasNext()) {
/* 173 */           legendValue = (String)iterator.next();
/* 174 */           if (legendValue.startsWith(NmsUtil.GetString("am.reporttab.availablityreport.downtime.text"))) {
/* 175 */             colors.put(Integer.toString(i), "#FF0000");
/*     */           }
/*     */           
/* 178 */           if (legendValue.startsWith(NmsUtil.GetString("am.webclient.historydata.uptime.text"))) {
/* 179 */             colors.put(Integer.toString(i), "#00FF00");
/*     */           }
/*     */           
/* 182 */           if (legendValue.startsWith(NmsUtil.GetString("am.reporttab.availablityreport.unmanaged.text"))) {
/* 183 */             colors.put(Integer.toString(i), "#0066CC");
/*     */           }
/*     */           
/* 186 */           if (legendValue.startsWith(NmsUtil.GetString("am.reporttab.availablityreport.scheduled.text"))) {
/* 187 */             colors.put(Integer.toString(i), "#FF00FF");
/*     */           }
/* 189 */           if (legendValue.startsWith(NmsUtil.GetString("am.webclient.hometab.monitorssnapshot.key.clear"))) {
/* 190 */             colors.put(Integer.toString(i), "#00FF00");
/*     */           }
/* 192 */           if (legendValue.startsWith(NmsUtil.GetString("am.webclient.hometab.monitorssnapshot.key.critical"))) {
/* 193 */             colors.put(Integer.toString(i), "#FF0000");
/*     */           }
/*     */           
/* 196 */           if (legendValue.startsWith(NmsUtil.GetString("am.webclient.hometab.monitorssnapshot.key.warning"))) {
/* 197 */             colors.put(Integer.toString(i), "#FF8C00");
/*     */           }
/*     */           
/*     */ 
/* 201 */           i++;
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 205 */         e.printStackTrace();
/*     */       }
/* 207 */       return colors;
/*     */     }
/* 209 */     return (Hashtable)this.pageContext.findAttribute("color");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\awolf\tags\AMWolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */