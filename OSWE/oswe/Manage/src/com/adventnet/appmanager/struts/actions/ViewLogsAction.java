/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewLogsAction
/*     */   extends Action
/*     */ {
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  38 */     String reqType = request.getParameter("reqType");
/*  39 */     if ("ARCHIVEDATADETAILS".equalsIgnoreCase(reqType))
/*     */     {
/*  41 */       return generateArchiveDataDetails(request, response);
/*     */     }
/*  43 */     String homeDir = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath() + File.separator + "logs";
/*  44 */     String folderName = "";
/*  45 */     List<String> stackList = new ArrayList();
/*  46 */     folderName = (request.getParameter("folderName") != null) && (request.getParameter("folderName") != "") ? homeDir + File.separator + request.getParameter("folderName") : homeDir;
/*  47 */     if ((folderName.indexOf("..\\") != -1) || (folderName.indexOf("../") != -1) || (!new File(folderName).exists())) {
/*  48 */       return new ActionForward("/jsp/formpages/Error404.jsp");
/*     */     }
/*  50 */     explore(folderName, request);
/*  51 */     if ((request.getParameter("folderName") != null) && (request.getParameter("folderName") != ""))
/*     */     {
/*  53 */       String folderPath = request.getParameter("folderName");
/*  54 */       while (folderPath.indexOf('\\') >= 0)
/*     */       {
/*  56 */         if (folderPath.lastIndexOf('\\') == 0)
/*     */         {
/*  58 */           stackList.add(folderPath);
/*  59 */           folderPath = "";
/*     */         }
/*     */         else
/*     */         {
/*  63 */           stackList.add(folderPath.substring(0, folderPath.indexOf('\\', 1)));
/*  64 */           folderPath = folderPath.substring(folderPath.indexOf('\\', 1), folderPath.length());
/*     */         }
/*     */       }
/*  67 */       request.setAttribute("URLSTACK", stackList);
/*     */     }
/*  69 */     return mapping.findForward("viewLogsPage");
/*     */   }
/*     */   
/*     */   public ActionForward generateArchiveDataDetails(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  74 */     int StartRange = Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId());
/*  75 */     int endRange = EnterpriseUtil.getDistributedEndResourceId();
/*  76 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/*  78 */       int probeId = Integer.parseInt(request.getParameter("probeId"));
/*  79 */       if (probeId > 0)
/*     */       {
/*  81 */         StartRange = probeId * EnterpriseUtil.RANGE;
/*  82 */         endRange = StartRange + EnterpriseUtil.RANGE;
/*     */       }
/*     */       else
/*     */       {
/*  86 */         return null;
/*     */       }
/*     */     }
/*     */     
/*  90 */     printArchiveDataDetails(StartRange, endRange, response);
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   private void printArchiveDataDetails(int startRange, int endRange, HttpServletResponse response) throws Exception
/*     */   {
/*  96 */     if ((startRange > 0) && (endRange > 0))
/*     */     {
/*  98 */       PrintWriter out = null;
/*     */       try {
/* 100 */         out = response.getWriter();
/* 101 */         response.setContentType("application/msword");
/* 102 */         out.println(new StringBuilder("ARCHIVE DATA DETAILS FOR THE RANGE ").append(startRange).append(" to ").append(endRange));
/* 103 */         String sql = "select DISTINCT(ARCHIVEDDATA_TABLENAME) from AM_ATTRIBUTES_EXT where ISARCHIVEING=1 or REPORTS_ENABLED=1";
/* 104 */         ResultSet rs = null;
/*     */         try
/*     */         {
/* 107 */           rs = AMConnectionPool.executeQueryStmt(sql);
/* 108 */           int totRepTablesDataCount = 0;
/* 109 */           Map<String, Integer> repTableMap = new HashMap(25, 0.5F);
/* 110 */           while (rs.next())
/*     */           {
/* 112 */             String repTableName = rs.getString("ARCHIVEDDATA_TABLENAME");
/* 113 */             if (!repTableName.equals("AM_MinMaxAvgData"))
/*     */             {
/* 115 */               String selectQuery = "select count(*) as COUNT from " + repTableName + " where RESID>= " + startRange + " and RESID<" + endRange;
/* 116 */               int count = 0;
/* 117 */               ResultSet rs1 = null;
/*     */               try
/*     */               {
/* 120 */                 rs1 = AMConnectionPool.executeQueryStmt(selectQuery);
/* 121 */                 if (rs1.next())
/*     */                 {
/* 123 */                   count = rs1.getInt("COUNT");
/* 124 */                   if (count > 0)
/*     */                   {
/* 126 */                     repTableMap.put(repTableName, Integer.valueOf(count));
/* 127 */                     totRepTablesDataCount += count;
/*     */                   }
/*     */                 }
/*     */               }
/*     */               finally {}
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 137 */           out.println("TOTAL ARCHIVED DATA COUNT : " + totRepTablesDataCount);
/* 138 */           out.println("\n\nTABULAR DETAILS");
/* 139 */           out.println("---------------------\n");
/* 140 */           for (Map.Entry<String, Integer> entry : repTableMap.entrySet())
/*     */           {
/* 142 */             out.println(" " + entry.getValue() + " -> " + (String)entry.getKey());
/*     */           }
/*     */           
/* 145 */           out.println("\n\nGRANULAR DETAILS");
/* 146 */           out.println("---------------------\n");
/*     */           
/* 148 */           for (Map.Entry<String, Integer> entry : repTableMap.entrySet())
/*     */           {
/* 150 */             out.println(new StringBuilder("\nTABLE : ").append((String)entry.getKey()).append(" SIZE: ").append(entry.getValue()));
/* 151 */             String query = "select DURATION,min(ARCHIVEDTIME ) MIN ,max(ARCHIVEDTIME ) MAX ,count(*) COUNT from " + (String)entry.getKey() + " where RESID >" + startRange + " and RESID <= " + endRange + " group by DURATION";
/* 152 */             ResultSet rs1 = null;
/*     */             try
/*     */             {
/* 155 */               rs1 = AMConnectionPool.executeQueryStmt(query);
/* 156 */               while (rs1.next())
/*     */               {
/* 158 */                 out.println("\tDURATION : " + rs1.getString("DURATION") + " -> MIN " + new Date(rs1.getLong("MIN")) + " ; MAX " + new Date(rs1.getLong("MAX")) + " COUNT : " + rs1.getInt("COUNT"));
/*     */               }
/*     */             }
/*     */             finally {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 167 */           printFineGranular(out, repTableMap, startRange, endRange);
/*     */         }
/*     */         finally
/*     */         {
/* 171 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 176 */         if (out != null)
/*     */         {
/* 178 */           out.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void printFineGranular(PrintWriter out, Map<String, Integer> tablesMap, int startRange, int endRange)
/*     */     throws Exception
/*     */   {
/* 187 */     out.println("\n FINER DETAILS");
/* 188 */     out.println("================\n");
/* 189 */     for (Map.Entry<String, Integer> entry : tablesMap.entrySet())
/*     */     {
/* 191 */       String query = "select DURATION,min(ARCHIVEDTIME ) MIN ,max(ARCHIVEDTIME ) MAX ,count(*) COUNT from " + (String)entry.getKey() + " where RESID >" + startRange + " and RESID <= " + endRange + " group by DURATION";
/* 192 */       ResultSet rs1 = null;
/* 193 */       ResultSet rs2 = null;
/*     */       try
/*     */       {
/* 196 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 197 */         out.println(new StringBuilder("\n TABLE : ").append((String)entry.getKey()));
/* 198 */         out.println("-----------------");
/* 199 */         while (rs1.next())
/*     */         {
/* 201 */           long min = rs1.getLong("MIN");
/* 202 */           long max = rs1.getLong("MAX");
/* 203 */           int duration = rs1.getInt("DURATION");
/* 204 */           if ((min > 0L) && (max > 0L) && (duration > 0))
/*     */           {
/* 206 */             long time = min - 86400000L;
/* 207 */             boolean breakThis = false;
/* 208 */             while (!breakThis)
/*     */             {
/* 210 */               Properties oneDayTimeInterval = getOneDayTimeInterval(time);
/* 211 */               long startTime = Long.parseLong(oneDayTimeInterval.getProperty("startTime"));
/* 212 */               long endTime = Long.parseLong(oneDayTimeInterval.getProperty("endTime"));
/* 213 */               if (endTime > max)
/*     */               {
/* 215 */                 breakThis = true;
/*     */               }
/* 217 */               query = "select DURATION,min(ARCHIVEDTIME ) MIN ,max(ARCHIVEDTIME ) MAX ,count(*) COUNT from " + (String)entry.getKey() + " where RESID >" + startRange + " and RESID <= " + endRange + " and ARCHIVEDTIME >= " + startTime + " and ARCHIVEDTIME< " + endTime + " and DURATION = " + duration + " group by DURATION";
/* 218 */               rs2 = AMConnectionPool.executeQueryStmt(query);
/* 219 */               while (rs2.next())
/*     */               {
/* 221 */                 out.println("TIME : " + new Date(startTime) + "DURATION : " + rs1.getString("DURATION") + " -> MIN " + new Date(rs2.getLong("MIN")) + " ; MAX " + new Date(rs2.getLong("MAX")) + " COUNT : " + rs2.getInt("COUNT"));
/*     */               }
/*     */               
/* 224 */               time += 86400000L;
/*     */             }
/* 226 */             out.println("  \n");
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */       }
/*     */       finally
/*     */       {
/* 234 */         AMConnectionPool.closeStatement(rs1);
/* 235 */         AMConnectionPool.closeStatement(rs2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static Properties getOneDayTimeInterval(long time)
/*     */   {
/* 242 */     Properties toReturn = new Properties();
/* 243 */     Date currentTimeAsDate = new Date(time);
/* 244 */     Calendar cldr = Calendar.getInstance();
/* 245 */     cldr.setTime(currentTimeAsDate);
/* 246 */     cldr.set(11, 0);
/* 247 */     cldr.set(13, 0);
/* 248 */     cldr.set(14, 0);
/* 249 */     cldr.set(12, 0);
/* 250 */     toReturn.setProperty("endTime", cldr.getTimeInMillis() + "");
/* 251 */     cldr.roll(5, false);
/* 252 */     toReturn.setProperty("startTime", cldr.getTimeInMillis() + "");
/* 253 */     return toReturn;
/*     */   }
/*     */   
/*     */   public static void explore(String folderLocation, HttpServletRequest request) {
/* 257 */     List<CustomFile> folders = new ArrayList();
/* 258 */     List<CustomFile> files = new ArrayList();
/* 259 */     File[] filesArray = new File(folderLocation).listFiles(new FilenameFilter()
/*     */     {
/*     */       public boolean accept(File dir, String filename) {
/* 262 */         return !filename.endsWith(".lck");
/*     */       }
/* 264 */     });
/* 265 */     String folderName = (request.getParameter("folderName") != null) && (request.getParameter("folderName") != "") ? request.getParameter("folderName") : "";
/* 266 */     CustomFile custFile = null;
/* 267 */     for (File file : filesArray)
/*     */     {
/* 269 */       custFile = new CustomFile(file);
/* 270 */       custFile.setFilePath(folderName + File.separator + file.getName());
/* 271 */       if (file.isDirectory())
/*     */       {
/* 273 */         folders.add(custFile);
/*     */       }
/*     */       else
/*     */       {
/* 277 */         files.add(custFile);
/*     */       }
/*     */     }
/*     */     
/* 281 */     String colNameToSort = "dateTime";
/* 282 */     Comparator nameComparator = new Comparator()
/*     */     {
/*     */       public int compare(Object custFile1Obj, Object custFile2Obj)
/*     */       {
/* 286 */         String custFile1 = ((CustomFile)custFile1Obj).getFileName();
/* 287 */         String custFile2 = ((CustomFile)custFile2Obj).getFileName();
/* 288 */         return custFile1.compareToIgnoreCase(custFile2);
/*     */       }
/* 290 */     };
/* 291 */     Comparator compare = null;
/* 292 */     if ((request.getParameter("colName") != null) && (request.getParameter("colName") != ""))
/*     */     {
/* 294 */       colNameToSort = request.getParameter("colName");
/* 295 */       request.getSession().setAttribute("colName", colNameToSort);
/*     */     }
/* 297 */     else if (request.getSession().getAttribute("colName") != null)
/*     */     {
/* 299 */       colNameToSort = (String)request.getSession().getAttribute("colName");
/*     */     }
/*     */     
/* 302 */     compare = colNameToSort.equals("files") ? nameComparator : null;
/*     */     
/* 304 */     Collections.sort(folders, compare);
/* 305 */     Collections.sort(files, compare);
/* 306 */     request.setAttribute("folders", folders);
/* 307 */     request.setAttribute("files", files);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ViewLogsAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */