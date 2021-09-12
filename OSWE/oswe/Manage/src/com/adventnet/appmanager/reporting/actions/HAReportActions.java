/*     */ package com.adventnet.appmanager.reporting.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.reporting.AMChartGenerator;
/*     */ import com.adventnet.appmanager.reporting.AMDataSet;
/*     */ import com.adventnet.appmanager.reporting.AM_HADataSource;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HAReportActions
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward generateHourlyHAReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  31 */     ActionMessages messages = new ActionMessages();
/*  32 */     ActionErrors errors = new ActionErrors();
/*  33 */     int haID = -1;
/*  34 */     long endTime = 0L;
/*  35 */     long lastDCTime = 0L;
/*     */     try
/*     */     {
/*  38 */       haID = Integer.parseInt(request.getParameter("haid"));
/*     */     }
/*     */     catch (NumberFormatException ex) {
/*  41 */       ex.printStackTrace();
/*  42 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("report.failed.params", "Resource ID not obtained"));
/*  43 */       saveErrors(request, errors);
/*  44 */       request.setAttribute("endTime", null);
/*  45 */       request.setAttribute("haid", null);
/*  46 */       request.setAttribute("param", "test");
/*  47 */       return mapping.getInputForward();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  52 */       endTime = Long.parseLong(request.getParameter("endTime"));
/*     */     }
/*     */     catch (Exception ex) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/*  62 */       generateCurrentHourHAReport(haID, request, endTime, lastDCTime);
/*  63 */       request.setAttribute("haid", String.valueOf(haID));
/*  64 */       request.setAttribute("param", null);
/*  65 */       return mapping.findForward("report.success");
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  69 */       ee.printStackTrace();
/*  70 */       request.setAttribute("haid", String.valueOf(haID));
/*  71 */       request.setAttribute("endTime", String.valueOf(0L));
/*  72 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("report.failed.exception", ee.toString()));
/*  73 */       saveErrors(request, errors);
/*  74 */       request.setAttribute("param", "test"); }
/*  75 */     return mapping.getInputForward();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final void generateCurrentHourHAReport(int haID, HttpServletRequest request, long endTime, long lastDCTime)
/*     */     throws Exception
/*     */   {
/*  83 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  84 */     ResultSet set = null;
/*  85 */     long maxTimeinRawTable = 0L;
/*  86 */     long minTimeinRawTable = 0L;
/*  87 */     long count = 0L;
/*  88 */     String query = "";
/*  89 */     if (endTime == 0L)
/*     */     {
/*  91 */       query = "select max(COLLECTIONTIME),min(COLLECTIONTIME),count(*) from AM_ManagedObjectData";
/*     */     }
/*     */     else
/*     */     {
/*  95 */       long oneHourBeforeTime = endTime - 3600000L;
/*  96 */       query = "select max(COLLECTIONTIME),min(COLLECTIONTIME),count(*) from AM_ManagedObjectData where COLLECTIONTIME  <=" + endTime + " and COLLECTIONTIME >=" + oneHourBeforeTime;
/*     */     }
/*     */     try
/*     */     {
/* 100 */       set = AMConnectionPool.executeQueryStmt(query);
/* 101 */       if (set.next())
/*     */       {
/* 103 */         maxTimeinRawTable = set.getLong(1);
/* 104 */         minTimeinRawTable = set.getLong(2);
/* 105 */         count = set.getLong(3);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 110 */       closeResultSet(set);
/*     */     }
/* 112 */     String name = "";
/* 113 */     String owner = "";
/* 114 */     String createdDate = "";
/*     */     try
/*     */     {
/* 117 */       String query1 = "select AM_ManagedObject.RESOURCENAME as NAME,AM_HOLISTICAPPLICATION.OWNER as Owner,AM_HOLISTICAPPLICATION.CREATIONDATE as createdDate from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=" + haID;
/* 118 */       set = AMConnectionPool.executeQueryStmt(query1);
/* 119 */       if (set.next())
/*     */       {
/* 121 */         name = set.getString(1);
/* 122 */         owner = set.getString(2);
/* 123 */         createdDate = set.getString(3);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 128 */       closeResultSet(set);
/*     */     }
/* 130 */     if (count == 0L)
/*     */     {
/* 132 */       throw new Exception("No Data available to generate Report for " + name);
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (maxTimeinRawTable - minTimeinRawTable > 3600000L)
/* 137 */       minTimeinRawTable = maxTimeinRawTable - 3600000L;
/* 138 */     request.setAttribute("endTime", String.valueOf(minTimeinRawTable));
/*     */     
/* 140 */     AMDataSet data = getDataFromAM_ManagedObjectData(haID, maxTimeinRawTable, minTimeinRawTable);
/*     */     
/*     */ 
/* 143 */     HashMap parameters = new HashMap();
/* 144 */     parameters.put("Title", "Summary Report of " + name);
/* 145 */     parameters.put("StartTime", new Date(minTimeinRawTable));
/* 146 */     parameters.put("EndTime", new Date(maxTimeinRawTable));
/* 147 */     AM_HADataSource dataSource = new AM_HADataSource(data);
/* 148 */     AMChartGenerator.fillReport("./reports/HAReports.xml.jasper", parameters, dataSource);
/*     */   }
/*     */   
/*     */ 
/*     */   private final AMDataSet getDataFromAM_ManagedObjectData(int haID, long maxTime, long minTime)
/*     */     throws Exception
/*     */   {
/* 155 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 156 */     ResultSet set = null;
/* 157 */     AMDataSet ds = new AMDataSet();
/*     */     try
/*     */     {
/* 160 */       String query = "select RESID,min(RESPONSETIME) as minData,max(RESPONSETIME) as maxData,avg(RESPONSETIME) as avgData from AM_ManagedObjectData,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObjectData.RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haID + " and COLLECTIONTIME between " + minTime + " and " + maxTime + " group by RESID";
/* 161 */       set = AMConnectionPool.executeQueryStmt(query);
/* 162 */       double min = 0.0D;
/* 163 */       double max = 0.0D;
/* 164 */       double avg = 0.0D;
/* 165 */       int i = 0;
/* 166 */       while (set.next())
/*     */       {
/* 168 */         ds.set(i, 1, String.valueOf(set.getDouble(2)));
/* 169 */         ds.set(i, 2, String.valueOf(set.getDouble(3)));
/* 170 */         ds.set(i, 3, String.valueOf(set.getDouble(4)));
/* 171 */         i++;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 176 */       closeResultSet(set);
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 181 */       String query = "select DISPLAYNAME,RESID,AVAILABILITY,HEALTH,count(*) from AM_ManagedObjectData,AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObjectData.RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haID + " and AM_ManagedObjectData.RESID=AM_ManagedObject.RESOURCEID and COLLECTIONTIME between " + minTime + " and " + maxTime + "  group by RESID,AVAILABILITY,HEALTH";
/* 182 */       int resID = 0;
/* 183 */       int totalCount = 0;
/* 184 */       int uptimeCount = 0;
/* 185 */       int downtimeCount = 0;
/* 186 */       int clearCount = 0;
/* 187 */       int warningCount = 0;
/* 188 */       int criticalCount = 0;
/* 189 */       set = AMConnectionPool.executeQueryStmt(query);
/* 190 */       boolean gotData = false;
/* 191 */       int row = 0;
/* 192 */       String resourceName = null;
/* 193 */       String prevResourceName = null;
/* 194 */       while (set.next())
/*     */       {
/* 196 */         gotData = true;
/* 197 */         int curresID = set.getInt("RESID");
/* 198 */         resourceName = set.getString("DISPLAYNAME");
/* 199 */         if (resID == 0)
/* 200 */           resID = curresID;
/* 201 */         if (resID != curresID)
/*     */         {
/* 203 */           double availability = uptimeCount / totalCount * 100;
/* 204 */           double clear = clearCount / totalCount * 100;
/* 205 */           double critical = criticalCount / totalCount * 100;
/* 206 */           double warning = warningCount / totalCount * 100;
/* 207 */           ds.set(row, 0, prevResourceName);
/* 208 */           ds.set(row, 4, String.valueOf(clear));
/* 209 */           ds.set(row, 5, String.valueOf(critical));
/* 210 */           ds.set(row, 6, String.valueOf(warning));
/* 211 */           ds.set(row, 7, String.valueOf(availability));
/*     */           
/* 213 */           resID = curresID;
/* 214 */           totalCount = 0;
/* 215 */           uptimeCount = 0;
/* 216 */           downtimeCount = 0;
/* 217 */           clearCount = 0;
/* 218 */           warningCount = 0;
/* 219 */           criticalCount = 0;
/* 220 */           row++;
/*     */         }
/* 222 */         int availability = set.getInt("AVAILABILITY");
/* 223 */         int count = set.getInt("count(*)");
/* 224 */         totalCount += count;
/* 225 */         if (availability == 0)
/*     */         {
/* 227 */           criticalCount += count;
/* 228 */           clearCount = 0;
/* 229 */           warningCount = 0;
/* 230 */           downtimeCount += count;
/*     */         }
/* 232 */         else if (availability == 1)
/*     */         {
/* 234 */           uptimeCount += count;
/* 235 */           int health = set.getInt("HEALTH");
/* 236 */           if (health == 1)
/*     */           {
/* 238 */             criticalCount = count;
/*     */ 
/*     */           }
/* 241 */           else if (health == 4)
/*     */           {
/* 243 */             warningCount = count;
/*     */           }
/* 245 */           else if (health == 5)
/*     */           {
/*     */ 
/* 248 */             clearCount = count;
/*     */           }
/*     */         }
/* 251 */         prevResourceName = resourceName;
/*     */       }
/* 253 */       if (gotData)
/*     */       {
/* 255 */         double availability = uptimeCount / totalCount * 100;
/* 256 */         double clear = clearCount / totalCount * 100;
/* 257 */         double critical = criticalCount / totalCount * 100;
/* 258 */         double warning = warningCount / totalCount * 100;
/* 259 */         ds.set(row, 0, resourceName);
/* 260 */         ds.set(row, 4, String.valueOf(clear));
/* 261 */         ds.set(row, 5, String.valueOf(critical));
/* 262 */         ds.set(row, 6, String.valueOf(warning));
/* 263 */         ds.set(row, 7, String.valueOf(availability));
/*     */       }
/*     */       
/*     */     }
/*     */     finally
/*     */     {
/* 269 */       closeResultSet(set);
/*     */     }
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
/* 292 */     ds.print();
/* 293 */     return ds;
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
/*     */   private final void closeResultSet(ResultSet set)
/*     */   {
/* 366 */     if (set != null)
/*     */     {
/*     */       try
/*     */       {
/* 370 */         set.close();
/*     */       }
/*     */       catch (Exception ex) {
/* 373 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\actions\HAReportActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */