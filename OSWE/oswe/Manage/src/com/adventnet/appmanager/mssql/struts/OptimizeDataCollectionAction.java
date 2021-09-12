/*     */ package com.adventnet.appmanager.mssql.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.OptimizeDataCollectionUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptimizeDataCollectionAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward updatedcComponentStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  28 */     String resourceID = request.getParameter("resourceID");
/*  29 */     String dcComponentName = request.getParameter("dcComponentsSelect");
/*     */     try {
/*  31 */       boolean status = true;
/*  32 */       String pollingstatus = request.getParameter("pollingStatus");
/*  33 */       if ((pollingstatus != null) && (pollingstatus.equals("-1"))) {
/*  34 */         status = false;
/*     */       }
/*  36 */       String pollingInterval = pollingstatus;
/*  37 */       if ("0".equals(resourceID)) {
/*  38 */         Vector<String> resIDs = DBUtil.getResIdsOfGivenResTypes("MSSQL-DB-server");
/*  39 */         Iterator<String> it = resIDs.iterator();
/*  40 */         while (it.hasNext()) {
/*  41 */           OptimizeDataCollectionUtil.updateDBForComponentName("-1", dcComponentName, status, pollingInterval, (String)it.next());
/*     */         }
/*     */       } else {
/*  44 */         OptimizeDataCollectionUtil.updateDBForComponentName("-1", dcComponentName, status, pollingInterval, resourceID);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  48 */       e.printStackTrace();
/*     */     }
/*  50 */     return new ActionForward("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=" + resourceID + "&showMsgNonConf=true&dcComponentName=" + dcComponentName, true);
/*     */   }
/*     */   
/*     */   public ActionForward updatePollingIntervalForId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*     */     try {
/*  55 */       String dcComponentName = request.getParameter("dcComponentName");
/*  56 */       int pollingInterval = Integer.parseInt(request.getParameter("pollingInterval"));
/*  57 */       String resourceIdsOfDB = request.getParameter("resourceIds");
/*  58 */       String resourceID = request.getParameter("resourceID");
/*  59 */       String noOfDatabasesSelected = request.getParameter("noOfDatabasesSelected");
/*  60 */       boolean status = pollingInterval >= 0;
/*  61 */       if (noOfDatabasesSelected.equals(OptimizeDataCollectionUtil.getNumberOfDatabases(resourceID)))
/*     */       {
/*  63 */         OptimizeDataCollectionUtil.updateDBForComponentName("-1", dcComponentName, status, String.valueOf(pollingInterval), resourceID);
/*     */       } else {
/*  65 */         OptimizeDataCollectionUtil.updateDBForComponentName(resourceIdsOfDB, dcComponentName, status, String.valueOf(pollingInterval), resourceID);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  69 */       e.printStackTrace();
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getConfigureTypeAndTotalDBs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*  75 */     String resourceID = request.getParameter("resourceID");
/*  76 */     String dcComponentName = request.getParameter("dcComponentName");
/*  77 */     String getCountOfDatabases = request.getParameter("getCount");
/*  78 */     String configureType = null;
/*  79 */     if (resourceID.equals("0")) {
/*  80 */       configureType = "MonitorType";
/*     */     }
/*     */     else {
/*  83 */       configureType = OptimizeDataCollectionUtil.getConfigureType(resourceID, dcComponentName);
/*     */     }
/*  85 */     JSONObject jsonobj = new JSONObject();
/*     */     try {
/*  87 */       jsonobj.put("configureType", configureType);
/*  88 */       if (getCountOfDatabases.equals("true")) {
/*  89 */         String totalDatabases = OptimizeDataCollectionUtil.getNumberOfDatabases(resourceID);
/*  90 */         jsonobj.put("totalDatabases", totalDatabases);
/*     */       }
/*     */     } catch (JSONException e) {
/*  93 */       e.printStackTrace();
/*     */     }
/*  95 */     PrintWriter out = null;
/*     */     try {
/*  97 */       out = response.getWriter();
/*     */     } catch (IOException e) {
/*  99 */       e.printStackTrace();
/*     */     }
/* 101 */     response.setContentType("application/json");
/* 102 */     response.setHeader("Cache-Control", "no-cache");
/* 103 */     out.println(jsonobj);
/* 104 */     out.flush();
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getLevelOfComponent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 109 */     String dcComponentName = request.getParameter("dcComponentName");
/* 110 */     String levelOfComponent = OptimizeDataCollectionUtil.getLevelOfComponent(dcComponentName);
/* 111 */     JSONObject jsonobj = new JSONObject();
/*     */     try {
/* 113 */       jsonobj.put("levelOfComponent", levelOfComponent);
/*     */     } catch (JSONException e) {
/* 115 */       e.printStackTrace();
/*     */     }
/* 117 */     PrintWriter out = null;
/*     */     try {
/* 119 */       out = response.getWriter();
/*     */     } catch (IOException e) {
/* 121 */       e.printStackTrace();
/*     */     }
/* 123 */     response.setContentType("application/json");
/* 124 */     response.setHeader("Cache-Control", "no-cache");
/* 125 */     out.println(jsonobj);
/* 126 */     out.flush();
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\mssql\struts\OptimizeDataCollectionAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */