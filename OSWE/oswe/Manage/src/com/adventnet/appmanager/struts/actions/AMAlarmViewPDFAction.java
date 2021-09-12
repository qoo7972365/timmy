/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.fe.common.CustomViewException;
/*     */ import com.adventnet.nms.severity.SeverityInfo;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
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
/*     */ public class AMAlarmViewPDFAction
/*     */   extends Action
/*     */ {
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException, CustomViewException
/*     */   {
/*  40 */     String entityID = request.getParameter("selectedEntity");
/*  41 */     String entityStr = null;
/*  42 */     String resourceStr = null;
/*  43 */     if (entityID != null)
/*     */     {
/*  45 */       String[] temp = entityID.split(",");
/*  46 */       for (String entity : temp)
/*     */       {
/*  48 */         String resId = entity.split("_")[0];
/*  49 */         if (resourceStr == null)
/*     */         {
/*  51 */           resourceStr = resId;
/*     */         }
/*     */         else
/*     */         {
/*  55 */           resourceStr = resourceStr + "," + resId;
/*     */         }
/*     */         
/*  58 */         if (entityStr == null)
/*     */         {
/*  60 */           entityStr = "'" + entity + "'";
/*     */         }
/*     */         else
/*     */         {
/*  64 */           entityStr = entityStr + "," + "'" + entity + "'";
/*     */         }
/*     */       }
/*     */       
/*  68 */       String moQuery = "SELECT RESOURCEID, DISPLAYNAME FROM AM_ManagedObject WHERE RESOURCEID IN (" + resourceStr + ")";
/*  69 */       String eventQuery = "SELECT TO_TIMESTAMP(TTIME/1000) AS TIME,SEVERITY,TEXT,ENTITY,SOURCE FROM Event WHERE ENTITY IN (" + entityStr + ") ORDER BY TTIME DESC";
/*     */       
/*  71 */       if (DBQueryUtil.isMssql())
/*     */       {
/*  73 */         Calendar ucal = Calendar.getInstance();
/*  74 */         ucal.setTimeInMillis(0L);
/*  75 */         String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*  76 */         eventQuery = "SELECT DATEADD(ss,TTIME/1000,'" + unixStartTime + "') AS TIME,SEVERITY,TEXT,ENTITY,SOURCE FROM Event WHERE ENTITY IN (" + entityStr + ") ORDER BY TTIME DESC";
/*     */       }
/*  78 */       else if (DBQueryUtil.isMysql())
/*     */       {
/*  80 */         eventQuery = "SELECT FROM_UNIXTIME(TTIME/1000) AS TIME,SEVERITY,TEXT,ENTITY,SOURCE FROM Event WHERE ENTITY IN (" + entityStr + ") ORDER BY TTIME DESC";
/*     */       }
/*  82 */       HashMap<String, String> resourceNameMap = new HashMap();
/*  83 */       HashMap<String, ArrayList<Properties>> hash = new HashMap();
/*  84 */       ResultSet rs = null;
/*  85 */       int eventCount = 50;
/*  86 */       HashMap<String, Integer> entityCnt = new HashMap();
/*     */       
/*     */       try
/*     */       {
/*  90 */         rs = AMConnectionPool.executeQueryStmt(moQuery);
/*  91 */         while (rs.next())
/*     */         {
/*  93 */           resourceNameMap.put(rs.getString(1), rs.getString(2));
/*     */         }
/*  95 */         AMConnectionPool.closeStatement(rs);
/*     */         
/*  97 */         rs = AMConnectionPool.executeQueryStmt(eventQuery);
/*  98 */         while (rs.next())
/*     */         {
/* 100 */           String time = rs.getString(1);
/* 101 */           String severity = SeverityInfo.getInstance().getName(rs.getInt(2));
/* 102 */           String text = rs.getString(3);
/* 103 */           String entity = rs.getString(4);
/* 104 */           String source = (String)resourceNameMap.get(rs.getString(5));
/* 105 */           Integer countObj = (Integer)entityCnt.get(entity);
/* 106 */           if (countObj == null)
/*     */           {
/* 108 */             countObj = new Integer(1);
/* 109 */             entityCnt.put(entity, countObj);
/*     */           } else {
/* 111 */             if (countObj.intValue() == eventCount) {
/*     */               continue;
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 117 */             countObj = new Integer(countObj.intValue() + 1);
/* 118 */             entityCnt.put(entity, countObj);
/*     */           }
/* 120 */           text = FormatUtil.findReplace(text, "<br>", "\n");
/* 121 */           text = FormatUtil.findReplace(text, "<ol>", "");
/* 122 */           text = FormatUtil.findReplace(text, "</ol>", "");
/* 123 */           text = FormatUtil.findReplace(text, "<li>", "");
/* 124 */           text = FormatUtil.findReplace(text, "</li>", "");
/*     */           
/* 126 */           ArrayList<Properties> a = (ArrayList)hash.get(entity);
/* 127 */           if (a == null)
/*     */           {
/* 129 */             a = new ArrayList();
/* 130 */             hash.put(entity, a);
/*     */           }
/* 132 */           Properties prop = new Properties();
/* 133 */           prop.setProperty("SEVERITY", severity);
/* 134 */           prop.setProperty("TIME", time);
/* 135 */           prop.setProperty("TEXT", text);
/* 136 */           prop.setProperty("SOURCE", source);
/* 137 */           a.add(prop);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 142 */         ex.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 146 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       
/* 149 */       String reportType = request.getParameter("reportType");
/* 150 */       if ("pdf".equalsIgnoreCase(reportType))
/*     */       {
/* 152 */         request.setAttribute("sp-report-type", "pdf");
/*     */       }
/*     */       else
/*     */       {
/* 156 */         request.setAttribute("sp-report-type", "excel");
/*     */       }
/*     */       
/* 159 */       request.setAttribute("data", hash);
/* 160 */       request.setAttribute("report-type-template", "report.eventlist");
/*     */     }
/* 162 */     return mapping.findForward("eventlist.success.pdf");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AMAlarmViewPDFAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */