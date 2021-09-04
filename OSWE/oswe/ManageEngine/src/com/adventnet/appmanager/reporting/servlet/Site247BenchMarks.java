/*     */ package com.adventnet.appmanager.reporting.servlet;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Site247BenchMarks
/*     */   extends HttpServlet
/*     */ {
/*  23 */   private ServletContext servletContext = null;
/*  24 */   private ServletConfig config = null;
/*     */   
/*     */ 
/*     */   public void init(ServletConfig sConfig)
/*     */     throws ServletException
/*     */   {
/*  30 */     super.init(sConfig);
/*  31 */     this.servletContext = sConfig.getServletContext();
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  37 */     PrintWriter out = response.getWriter();
/*     */     try
/*     */     {
/*  40 */       StringBuilder sb = null;
/*  41 */       Hashtable downtimeHash; String key; Iterator it; if (request.getAttribute("data") != null)
/*     */       {
/*  43 */         ArrayList respData = (ArrayList)request.getAttribute("data");
/*  44 */         if ((respData != null) && (respData.size() > 0))
/*     */         {
/*  46 */           sb = new StringBuilder();
/*  47 */           int size = respData.size();
/*  48 */           for (int i = 0; i < size; i++)
/*     */           {
/*  50 */             ArrayList row = (ArrayList)respData.get(i);
/*  51 */             String dispName = (String)row.get(0);
/*  52 */             Double avgRespTime = new Double((String)row.get(3));
/*  53 */             avgRespTime = Double.valueOf(avgRespTime.doubleValue() / 1000.0D);
/*     */             
/*  55 */             sb.append(dispName);
/*  56 */             sb.append(',');
/*  57 */             sb.append(avgRespTime);
/*  58 */             sb.append('\n');
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  65 */         downtimeHash = ReportUtilities.getAvailabilityHistoryByType("RBM", 3, "admin", "admin");
/*  66 */         key = null;
/*  67 */         sb = new StringBuilder();
/*  68 */         for (it = downtimeHash.keySet().iterator(); it.hasNext();)
/*     */         {
/*  70 */           key = (String)it.next();
/*     */           
/*  72 */           ArrayList downtimes = (ArrayList)downtimeHash.get(key);
/*  73 */           for (int i = 0; i < downtimes.size(); i++)
/*     */           {
/*  75 */             Hashtable hash = (Hashtable)downtimes.get(i);
/*  76 */             String status = (String)hash.get("STATUS");
/*  77 */             if (status.equals("UNAVAILABLE"))
/*     */             {
/*  79 */               String dispName = key.substring(key.indexOf("#") + 1);
/*  80 */               Long type = (Long)hash.get("TYPE");
/*  81 */               Date endtime = (Date)hash.get("ENDTIME");
/*  82 */               sb.append(dispName);
/*  83 */               sb.append(',');
/*  84 */               sb.append(type);
/*  85 */               sb.append(',');
/*  86 */               sb.append(((Date)hash.get("STARTTIME")).getTime());
/*  87 */               sb.append(',');
/*  88 */               sb.append(((Date)hash.get("ENDTIME")).getTime());
/*  89 */               sb.append('\n');
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  96 */       response.setContentType("text/csv; charset=UTF-8");
/*  97 */       response.setHeader("Content-disposition", "attachment;filename=\"data.csv\"");
/*     */       
/*  99 */       out.println(sb.toString());
/* 100 */       out.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 107 */       if (out != null)
/*     */       {
/* 109 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 120 */     doPost(request, response);
/*     */   }
/*     */   
/*     */   public void destroy() {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\servlet\Site247BenchMarks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */