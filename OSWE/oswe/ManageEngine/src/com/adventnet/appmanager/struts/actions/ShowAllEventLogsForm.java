/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.validator.DynaValidatorForm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShowAllEventLogsForm
/*     */   extends DynaValidatorForm
/*     */ {
/*  21 */   AMConnectionPool pool = AMConnectionPool.getInstance();
/*  22 */   Properties monitorDetails = new Properties();
/*  23 */   public String owner = "";
/*  24 */   public String selectedMonitorName = "";
/*     */   
/*     */   public Properties getWindowsMonitors(String resourceid)
/*     */   {
/*  28 */     return getWindowsMonitors(resourceid, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getWindowsMonitors(String resourceid, HttpServletRequest request)
/*     */   {
/*  34 */     String windowsMonitorsQuery = "(select RESOURCEID,RESOURCENAME from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE where AM_ManagedResourceType.SUBGROUP='Windows') union (select mo.RESOURCEID as RESOURCEID,mo.DISPLAYNAME as RESOURCENAME from  AM_ManagedObject as mo,AM_VM_GUESTOSMAPPING as vmos where  mo.RESOURCEID=vmos.RESID and vmos.GUESTOS like '%Windows%')";
/*  35 */     if (Constants.sqlManager) {
/*  36 */       windowsMonitorsQuery = "select RESOURCEID, RESOURCENAME from AM_ManagedObject where  AM_ManagedObject.TYPE='Windows 2003' and AM_ManagedObject.RESOURCEID in (select HOSTID from SQLDBM_SQL_HOST_MAPPING)";
/*     */     }
/*  38 */     Vector resIds_vector = ClientDBUtil.getResourceIdentity(this.owner, request, null);
/*  39 */     ResultSet rs1 = null;
/*  40 */     if (Constants.sqlManager) {
/*  41 */       StringBuilder resID = new StringBuilder();
/*  42 */       if (resIds_vector.size() != 0)
/*     */       {
/*  44 */         for (int i = 0; i < resIds_vector.size(); i++)
/*     */         {
/*  46 */           resID.append((String)resIds_vector.get(i)).append(",");
/*     */         }
/*  48 */         resID.deleteCharAt(resID.length() - 1);
/*     */       }
/*  50 */       if ((resID != null) && (resID.length() > 0)) {
/*  51 */         String query = "select HOSTID from SQLDBM_SQL_HOST_MAPPING where SQLID in (" + resID.toString() + ")";
/*     */         try {
/*  53 */           rs1 = AMConnectionPool.executeQueryStmt(query);
/*  54 */           resIds_vector = new Vector();
/*  55 */           while (rs1.next()) {
/*  56 */             resIds_vector.add(rs1.getString(1));
/*     */           }
/*     */         } catch (SQLException e) {
/*  59 */           e.printStackTrace();
/*     */         } finally {
/*  61 */           if (rs1 != null)
/*     */           {
/*  63 */             AMConnectionPool.closeStatement(rs1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/*  69 */       ResultSet rs = AMConnectionPool.executeQueryStmt(windowsMonitorsQuery);
/*  70 */       String key = "";
/*  71 */       String value = "";
/*     */       
/*  73 */       while (rs.next())
/*     */       {
/*  75 */         key = rs.getString(1);
/*  76 */         value = rs.getString(2);
/*  77 */         if (key.equals(resourceid))
/*     */         {
/*  79 */           this.selectedMonitorName = value;
/*  80 */           set("selectedMonitorName", this.selectedMonitorName);
/*     */         }
/*  82 */         if (resIds_vector.size() != 0) {
/*  83 */           if (resIds_vector.contains(key)) {
/*  84 */             this.monitorDetails.setProperty(key, value);
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*  95 */         else if (!value.equalsIgnoreCase("WindowsAzure-TEMPLATE"))
/*     */         {
/*  97 */           this.monitorDetails.setProperty(key, value);
/*     */         }
/*     */       }
/*     */       
/* 101 */       if (rs != null)
/*     */       {
/* 103 */         rs.close();
/*     */       }
/*     */     } catch (SQLException e) {
/* 106 */       System.out.println(":::::::EXCEPTION OCCURED IN getWindowsMonitors.");
/* 107 */       e.printStackTrace();
/*     */     }
/* 109 */     return this.monitorDetails;
/*     */   }
/*     */   
/* 112 */   public void setOwner(String user) { this.owner = user; }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowAllEventLogsForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */