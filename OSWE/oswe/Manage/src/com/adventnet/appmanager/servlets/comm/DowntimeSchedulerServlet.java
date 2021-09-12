/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.util.DowntimeScheduleUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.utils.client.URITree;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.LinkedList;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class DowntimeSchedulerServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  41 */     doGet(request, response);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  48 */     System.out.println("############--------------->DowntimeSchedulerServlet : request.getParameterMap()" + request.getParameterMap());
/*     */     
/*  50 */     PrintWriter out = response.getWriter();
/*  51 */     String status = "Success";
/*  52 */     String responseCode = "3111";
/*  53 */     String method = request.getParameter("action-method");
/*     */     try {
/*  55 */       if ("create".equals(method))
/*     */       {
/*  57 */         createMaintenanceTask(request, response);
/*     */       }
/*  59 */       else if ("delete".equals(method))
/*     */       {
/*  61 */         deleteMaintenanceTask(request, response);
/*     */       }
/*  63 */       else if ("edit".equals(method))
/*     */       {
/*  65 */         editMaintenanceTask(request, response);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  71 */       ee.printStackTrace();
/*  72 */       status = "Failed";
/*  73 */       responseCode = "3333";
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  78 */       String xmlResp = URITree.generateXML(request, response, status, responseCode);
/*  79 */       System.out.println("DowntimeSchedulerServlet : xmlResp : ========================> " + xmlResp);
/*  80 */       out.println(xmlResp);
/*  81 */       out.close();
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  85 */       ee.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void createMaintenanceTask(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  91 */     ResultSet rs = null;
/*  92 */     ArrayList rows = new ArrayList();
/*     */     
/*  94 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  95 */     String taskID = request.getParameter("TASKID");
/*  96 */     String taskname = request.getParameter("TASKNAME");
/*  97 */     String taskdescription = request.getParameter("TASKDESCRIPTION");
/*  98 */     String taskstatus = request.getParameter("STATUS");
/*  99 */     String taskmethod = request.getParameter("TYPE");
/* 100 */     String timezone = request.getParameter("TIMEZONE");
/* 101 */     int tasktype = Integer.parseInt(taskmethod);
/* 102 */     int status = Integer.parseInt(taskstatus);
/* 103 */     String id = "-1";
/* 104 */     String starttime = "";
/* 105 */     String endtime = "";
/* 106 */     String effectfrom = "";
/* 107 */     String insertquery_type = "";
/* 108 */     boolean name_exist = false;
/*     */     
/* 110 */     String[] allResIDs = request.getParameter("RESIDS").split(",");
/* 111 */     String[] resIDs = filterResidForMAS(allResIDs, Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId()));
/* 112 */     if ((resIDs != null) && (resIDs.length > 0))
/*     */     {
/* 114 */       System.out.println("DowntimeSchedulerServlet :resIDs[0]: =======================>" + resIDs[0]);
/* 115 */       if (tasktype == 1)
/*     */       {
/* 117 */         starttime = request.getParameter("STARTTIME");
/* 118 */         endtime = request.getParameter("ENDTIME");
/* 119 */         effectfrom = request.getParameter("EFFECTFROMTIME");
/* 120 */         DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), starttime, endtime, effectfrom, null, resIDs, null, taskID, timezone);
/*     */       }
/* 122 */       else if (tasktype == 2)
/*     */       {
/* 124 */         ArrayList weeklyDetList = new ArrayList();
/* 125 */         int count = 1;
/*     */         
/*     */ 
/* 128 */         count = Integer.parseInt(request.getParameter("numbers"));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */         insertquery_type = " ";
/* 135 */         for (int i = 0; i < count; i++)
/*     */         {
/*     */ 
/* 138 */           String startDay = request.getParameter("startDay(" + i + ")");
/* 139 */           String startTime = request.getParameter("startTime(" + i + ")");
/* 140 */           String endDay = request.getParameter("endDay(" + i + ")");
/* 141 */           String endTime = request.getParameter("endTime(" + i + ")");
/* 142 */           Hashtable weeklyDetHash = new Hashtable();
/* 143 */           weeklyDetHash.put("STARTDAY", startDay);
/* 144 */           weeklyDetHash.put("STARTTIME", startTime);
/* 145 */           weeklyDetHash.put("ENDDAY", endDay);
/* 146 */           weeklyDetHash.put("ENDTIME", endTime);
/* 147 */           weeklyDetList.add(weeklyDetHash);
/*     */         }
/*     */         
/* 150 */         DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), null, null, null, weeklyDetList, resIDs, null, taskID, timezone);
/*     */       }
/*     */       else
/*     */       {
/* 154 */         starttime = request.getParameter("CUSTOMSTARTTIME");
/* 155 */         endtime = request.getParameter("CUSTOMENDTIME");
/* 156 */         DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), starttime, endtime, null, null, resIDs, null, taskID, timezone);
/*     */       }
/*     */       
/*     */ 
/* 160 */       DataCollectionControllerUtil.updateMaintenanceTable();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void editMaintenanceTask(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 171 */     ResultSet rs = null;
/* 172 */     ArrayList rows = new ArrayList();
/*     */     
/* 174 */     String taskid = request.getParameter("TASKID");
/* 175 */     String taskname = request.getParameter("TASKNAME");
/* 176 */     String taskdescription = request.getParameter("TASKDESCRIPTION");
/* 177 */     String taskstatus = request.getParameter("STATUS");
/* 178 */     String taskmethod = request.getParameter("TYPE");
/* 179 */     String timezone = request.getParameter("TIMEZONE");
/* 180 */     int tasktype = Integer.parseInt(taskmethod);
/* 181 */     int status = 1;
/* 182 */     String id = "-1";
/* 183 */     String starttime = "";
/* 184 */     String endtime = "";
/* 185 */     String effectfrom = "";
/* 186 */     String insertquery_type = "";
/*     */     
/* 188 */     String previous_taskname = "";
/* 189 */     String previous_taskdescription = "";
/* 190 */     int previous_taskstatus = 1;
/* 191 */     int previous_tasktype = 1;
/*     */     
/*     */ 
/* 194 */     String[] allResIDs = request.getParameter("RESIDS").split(",");
/* 195 */     String[] resIDs = filterResidForMAS(allResIDs, Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId()));
/* 196 */     if ((resIDs != null) && (resIDs.length > 0))
/*     */     {
/* 198 */       if (tasktype == 1)
/*     */       {
/* 200 */         starttime = request.getParameter("STARTTIME");
/* 201 */         endtime = request.getParameter("ENDTIME");
/* 202 */         effectfrom = request.getParameter("EFFECTFROMTIME");
/* 203 */         DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), starttime, endtime, effectfrom, null, resIDs, timezone);
/*     */       }
/* 205 */       else if (tasktype == 2)
/*     */       {
/* 207 */         ArrayList weeklyDetList = new ArrayList();
/* 208 */         int count = 1;
/*     */         
/*     */ 
/* 211 */         count = Integer.parseInt(request.getParameter("numbers"));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 217 */         insertquery_type = " ";
/* 218 */         for (int i = 0; i < count; i++)
/*     */         {
/*     */ 
/* 221 */           String startDay = request.getParameter("startDay(" + i + ")");
/* 222 */           String startTime = request.getParameter("startTime(" + i + ")");
/* 223 */           String endDay = request.getParameter("endDay(" + i + ")");
/* 224 */           String endTime = request.getParameter("endTime(" + i + ")");
/* 225 */           Hashtable weeklyDetHash = new Hashtable();
/* 226 */           weeklyDetHash.put("STARTDAY", startDay);
/* 227 */           weeklyDetHash.put("STARTTIME", startTime);
/* 228 */           weeklyDetHash.put("ENDDAY", endDay);
/* 229 */           weeklyDetHash.put("ENDTIME", endTime);
/* 230 */           weeklyDetList.add(weeklyDetHash);
/*     */         }
/*     */         
/* 233 */         DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), null, null, null, weeklyDetList, resIDs, timezone);
/*     */       }
/*     */       else
/*     */       {
/* 237 */         starttime = request.getParameter("CUSTOMSTARTTIME");
/* 238 */         endtime = request.getParameter("CUSTOMENDTIME");
/* 239 */         DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, Integer.parseInt(taskstatus), Integer.parseInt(taskmethod), starttime, endtime, null, null, resIDs, timezone);
/*     */       }
/*     */       try
/*     */       {
/* 243 */         DataCollectionControllerUtil.updateMaintenanceTable();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 247 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void deleteMaintenanceTask(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 254 */     String[] id = request.getParameter("TASKID").split(",");
/* 255 */     DowntimeScheduleUtil.deleteDownTimeScheduler(id, true);
/*     */     
/*     */ 
/* 258 */     DataCollectionControllerUtil.updateMaintenanceTable();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String[] filterResidForMAS(String[] resIDs, int startRESID)
/*     */   {
/* 268 */     LinkedList<String> result = new LinkedList();
/* 269 */     for (int i = 0; i < resIDs.length; i++)
/*     */     {
/* 271 */       int tempID = Integer.parseInt(resIDs[i]);
/* 272 */       int diff = tempID - startRESID;
/* 273 */       if ((diff > 0) && (diff < 10000000))
/*     */       {
/* 275 */         result.add(String.valueOf(tempID));
/*     */       }
/*     */     }
/* 278 */     if ((result != null) && (result.size() > 0))
/*     */     {
/* 280 */       String[] retArray = (String[])result.toArray(new String[result.size()]);
/* 281 */       return retArray;
/*     */     }
/*     */     
/*     */ 
/* 285 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\DowntimeSchedulerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */