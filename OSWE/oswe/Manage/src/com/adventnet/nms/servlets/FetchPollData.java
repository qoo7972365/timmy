/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.management.log.LogUser;
/*     */ import com.adventnet.nms.poll.CollectedData;
/*     */ import com.adventnet.nms.poll.MultiplePolledData;
/*     */ import com.adventnet.nms.poll.PollAPI;
/*     */ import com.adventnet.nms.poll.PolledData;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class FetchPollData extends javax.servlet.http.HttpServlet
/*     */ {
/*     */   public String getServletInfo()
/*     */   {
/*  26 */     return "This servlet returns an applet tag for poll data graphs";
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  42 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
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
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  58 */     Hashtable localHashtable = new Hashtable();
/*  59 */     MultiplePolledData localMultiplePolledData = null;
/*  60 */     Object localObject1 = null;
/*     */     
/*  62 */     paramHttpServletResponse.setStatus(200);
/*  63 */     paramHttpServletResponse.setContentType("text/html");
/*  64 */     Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
/*  65 */     while (localEnumeration.hasMoreElements())
/*     */     {
/*  67 */       str1 = (String)localEnumeration.nextElement();
/*  68 */       str2 = paramHttpServletRequest.getParameter(str1);
/*     */       
/*  70 */       if (str2 == null) { str2 = "-";
/*     */       }
/*  72 */       localHashtable.put(str1, str2);
/*     */     }
/*     */     
/*  75 */     String str1 = (String)localHashtable.get("PDATA");
/*  76 */     if (str1 == null)
/*     */     {
/*  78 */       errorPage("Parameter name not specified ", paramHttpServletRequest, paramHttpServletResponse);
/*  79 */       return;
/*     */     }
/*     */     
/*  82 */     String str2 = GenericUtility.replace(str1, "__tab__", "\t");
/*     */     
/*     */     try
/*     */     {
/*  86 */       str2 = java.net.URLDecoder.decode(str2);
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*  90 */       System.err.println(" Exception in decoding :" + localException1);
/*     */     }
/*     */     
/*  93 */     String str3 = (String)localHashtable.get("MULTIPLE");
/*  94 */     boolean bool = false;
/*     */     PollAPI localPollAPI;
/*  96 */     try { if (str3.equals("true")) {
/*  97 */         localPollAPI = GenericUtility.getPollAPI();
/*  98 */         PolledData localPolledData = localPollAPI.getPolledData(str2);
/*  99 */         bool = localPolledData.getIsMultiplePolledData();
/* 100 */         if (bool) {
/* 101 */           localMultiplePolledData = (MultiplePolledData)localPolledData;
/*     */         }
/*     */         else {
/* 104 */           localObject1 = localPolledData;
/*     */         }
/*     */       } else {
/* 107 */         localPollAPI = GenericUtility.getPollAPI();
/* 108 */         localObject1 = localPollAPI.getPolledData(str2);
/*     */       }
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 113 */       localException2.printStackTrace();
/* 114 */       NmsLogMgr.MISCUSER.log("Remote exception: " + localException2.getMessage(), 1);
/* 115 */       NmsLogMgr.MISCERR.fail("", localException2);
/* 116 */       errorPage("Error getting Poll access.", paramHttpServletRequest, paramHttpServletResponse);
/* 117 */       return;
/*     */     }
/* 119 */     if ((localObject1 == null) && (localMultiplePolledData == null))
/*     */     {
/* 121 */       com.adventnet.nms.commonfe.GenericFEAPIImpl.log("FetchPollData Error getting PolledData: " + str2);
/* 122 */       errorPage("FetchPollData Error getting PolledData: " + str2, paramHttpServletRequest, paramHttpServletResponse);
/* 123 */       return;
/*     */     }
/* 125 */     String str4 = (String)localHashtable.get("STARTTIME");
/* 126 */     String str5 = (String)localHashtable.get("ENDTIME");
/* 127 */     long l1 = 0L;long l2 = 0L;
/* 128 */     if ((str4 != null) && (str5 != null))
/*     */     {
/*     */       try
/*     */       {
/* 132 */         l1 = Long.parseLong(str4);
/*     */ 
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1)
/*     */       {
/* 137 */         System.err.println("Error while parsing start Time in FetchPolledData: : " + localNumberFormatException1.getMessage());
/*     */       }
/*     */       try
/*     */       {
/* 141 */         l2 = Long.parseLong(str5);
/*     */ 
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException2)
/*     */       {
/* 146 */         System.err.println("Error while parsing end Time in FetchPolledData: " + localNumberFormatException2.getMessage());
/*     */       }
/* 148 */       if ((l1 == 0L) || (l2 == 0L))
/*     */       {
/* 150 */         l1 = System.currentTimeMillis() - 86400000L;
/* 151 */         l2 = System.currentTimeMillis();
/*     */       }
/*     */     }
/* 154 */     Vector localVector = null;
/* 155 */     CollectedData localCollectedData = null;
/*     */     Object localObject2;
/* 157 */     Object localObject3; Object localObject4; int i; Object localObject5; try { String str6 = (String)localHashtable.get("INDEX");
/*     */       
/* 159 */       if ((str6.equals("-2")) && (str3.equals("true")))
/*     */       {
/* 161 */         localVector = localPollAPI.getInstances(localMultiplePolledData);
/* 162 */         localObject2 = paramHttpServletResponse.getWriter();
/* 163 */         if (localHashtable == null) { ((PrintWriter)localObject2).println(" Error getting Parameters.");
/*     */         }
/*     */         else {
/* 166 */           localObject3 = paramHttpServletRequest.getPathTranslated();
/* 167 */           localObject4 = localObject3;
/* 168 */           if (localVector.size() != 0)
/*     */           {
/* 170 */             ((PrintWriter)localObject2).println("OKOKOK");
/* 171 */             for (i = 0; i < localVector.size(); i++)
/*     */             {
/* 173 */               localObject5 = (String)localVector.elementAt(i);
/* 174 */               ((PrintWriter)localObject2).println((String)localObject5 + " ");
/*     */             }
/*     */           }
/* 177 */           ((PrintWriter)localObject2).flush();
/* 178 */           ((PrintWriter)localObject2).close();
/*     */         }
/*     */       }
/* 181 */       else if (str3.equals("true"))
/*     */       {
/* 183 */         localCollectedData = localPollAPI.getCollectedData(str6, str2, l1, l2);
/*     */       }
/*     */       else
/*     */       {
/* 187 */         localCollectedData = localPollAPI.getCollectedValues(str2, l1, l2);
/*     */       }
/*     */     }
/*     */     catch (Exception localException3)
/*     */     {
/* 192 */       localException3.printStackTrace();
/*     */     }
/* 194 */     if (localCollectedData == null)
/*     */     {
/* 196 */       errorPage("FetchPollData Error getting collected data: " + str2, paramHttpServletRequest, paramHttpServletResponse);
/* 197 */       return;
/*     */     }
/*     */     
/* 200 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/* 201 */     if (localHashtable == null) { localPrintWriter.println(" Error getting Parameters.");
/*     */     }
/*     */     else {
/* 204 */       localObject2 = paramHttpServletRequest.getPathTranslated();
/* 205 */       localObject3 = localObject2;
/* 206 */       if (localCollectedData != null)
/*     */       {
/* 208 */         localPrintWriter.println("OKOKOK");
/* 209 */         localObject4 = (Long[])localCollectedData.getTimes();
/* 210 */         i = localCollectedData.getType();
/* 211 */         int j; if (i == 1)
/*     */         {
/* 213 */           localPrintWriter.println("LONG");
/* 214 */           localObject5 = (Long[])localCollectedData.getValues();
/* 215 */           for (j = 0; j < localObject4.length; j++)
/*     */           {
/* 217 */             localPrintWriter.println(localObject4[j].toString() + " " + localObject5[j].toString());
/*     */           }
/*     */         }
/* 220 */         else if (i == 2)
/*     */         {
/* 222 */           localPrintWriter.println("STRING");
/* 223 */           localObject5 = (String[])localCollectedData.getValues();
/* 224 */           for (j = 0; j < localObject4.length; j++)
/*     */           {
/* 226 */             localPrintWriter.println(localObject4[j].toString() + " " + localObject5[j]);
/*     */           }
/*     */         }
/*     */       }
/* 230 */       localPrintWriter.flush();
/* 231 */       localPrintWriter.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void errorPage(String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws IOException
/*     */   {
/* 245 */     PrintWriter localPrintWriter = new PrintWriter(paramHttpServletResponse.getOutputStream());
/* 246 */     localPrintWriter.println(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\FetchPollData.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */