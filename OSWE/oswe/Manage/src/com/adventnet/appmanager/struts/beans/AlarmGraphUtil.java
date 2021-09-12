/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
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
/*     */ public class AlarmGraphUtil
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  30 */   private String viewId = "NoData";
/*  31 */   private long collectionTime = 0L;
/*  32 */   private String haid = null;
/*  33 */   private String monitor = null;
/*  34 */   private Vector resourceids = null;
/*     */   
/*     */ 
/*     */   public AlarmGraphUtil() {}
/*     */   
/*     */ 
/*     */   public AlarmGraphUtil(String viewId)
/*     */   {
/*  42 */     this.viewId = viewId;
/*     */   }
/*     */   
/*     */   public AlarmGraphUtil(String id, String type, Vector ids)
/*     */   {
/*  47 */     this.haid = id;
/*  48 */     this.monitor = type;
/*  49 */     this.resourceids = ids;
/*     */   }
/*     */   
/*     */   public AlarmGraphUtil(String viewId, Vector ids)
/*     */   {
/*  54 */     this.viewId = viewId;
/*  55 */     this.resourceids = ids;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParam(String viewId)
/*     */   {
/*  62 */     this.viewId = viewId;
/*     */   }
/*     */   
/*     */ 
/*     */   public long getLastDataCollectedTime()
/*     */   {
/*  68 */     return this.collectionTime;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  73 */     Object toReturn = null;
/*     */     
/*  75 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  76 */     result.addValue(new Integer(0), "", FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical"));
/*  77 */     result.addValue(new Integer(0), "", FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning"));
/*  78 */     result.addValue(new Integer(0), "", FormatUtil.getString("Clear"));
/*  79 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     
/*  81 */     if ((this.viewId != null) && (!this.viewId.equals("NoData")))
/*     */     {
/*     */ 
/*     */ 
/*  85 */       ArrayList poolNames = new ArrayList();
/*  86 */       Properties data = new Properties();
/*     */       
/*     */ 
/*  89 */       String wherecondition = " ";
/*  90 */       if ((this.viewId.equals("Alerts.2")) || (this.viewId.equals("Alerts.21")) || (this.viewId.equals("Alerts.24")) || (this.viewId.equals("Alerts.25")))
/*     */       {
/*  92 */         wherecondition = " and MODTIME > " + (System.currentTimeMillis() - 3600000L);
/*     */       }
/*  94 */       else if ((this.viewId.equals("Alerts.4")) || (this.viewId.equals("Alerts.41")) || (this.viewId.equals("Alerts.44")) || (this.viewId.equals("Alerts.45")))
/*     */       {
/*  96 */         wherecondition = " and MODTIME > " + (System.currentTimeMillis() - 86400000L);
/*     */       }
/*     */       
/*  99 */       String query = null;
/* 100 */       if (this.resourceids != null)
/*     */       {
/* 102 */         query = "select case severity when 5 then 'Clear' when 4 THEN 'Warning'  when 3 THEN 'Major'  when 2 THEN 'Minor'   when 1 Then 'Critical' END , count(*) from Alert  where  groupname='AppManager' and  " + DependantMOUtil.getCondition("SOURCE", this.resourceids) + " " + wherecondition + " group by severity";
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 107 */         query = "select case severity when 5 then 'Clear' when 4 THEN 'Warning'  when 3 THEN 'Major'  when 2 THEN 'Minor'   when 1 Then 'Critical' END , count(*) from Alert  where  groupname='AppManager'  " + wherecondition + " group by severity";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 113 */       ResultSet set = null;
/*     */       try
/*     */       {
/* 116 */         set = AMConnectionPool.executeQueryStmt(query);
/*     */         
/* 118 */         int i = 0;
/* 119 */         while (set.next())
/*     */         {
/*     */ 
/* 122 */           result.addValue(new Integer(set.getInt(2)), "", FormatUtil.getString(set.getString(1)));
/* 123 */           i++;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 134 */           if (set != null) {
/* 135 */             set.close();
/*     */           }
/*     */         } catch (Exception e) {
/* 138 */           e.printStackTrace();
/*     */         }
/*     */         
/* 141 */         toReturn = result;
/*     */       }
/*     */       catch (Exception exp)
/*     */       {
/* 128 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 134 */           if (set != null) {
/* 135 */             set.close();
/*     */           }
/*     */         } catch (Exception e) {
/* 138 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 146 */       return toReturn;
/*     */     }
/* 148 */     if ((this.haid != null) || (this.monitor != null))
/*     */     {
/*     */ 
/* 151 */       String query = "select case severity when 5 then 'Clear' when 4 THEN 'Warning'  when 3 THEN 'Major'  when 2 THEN 'Minor'   when 1 Then 'Critical' END , count(*) from Alert  where  groupname='AppManager' and " + DependantMOUtil.getCondition("SOURCE", this.resourceids) + " group by severity";
/*     */       
/* 153 */       ResultSet set = null;
/*     */       try
/*     */       {
/* 156 */         set = AMConnectionPool.executeQueryStmt(query);
/*     */         
/* 158 */         int i = 0;
/* 159 */         while (set.next())
/*     */         {
/* 161 */           result.addValue(new Integer(set.getInt(2)), "", FormatUtil.getString(set.getString(1)));
/* 162 */           i++;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 173 */           if (set != null) {
/* 174 */             set.close();
/*     */           }
/*     */         } catch (Exception e) {
/* 177 */           e.printStackTrace();
/*     */         }
/*     */         
/* 180 */         toReturn = result;
/*     */       }
/*     */       catch (Exception exp)
/*     */       {
/* 167 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 173 */           if (set != null) {
/* 174 */             set.close();
/*     */           }
/*     */         } catch (Exception e) {
/* 177 */           e.printStackTrace();
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 184 */       toReturn = result;
/* 185 */       return toReturn;
/*     */     }
/*     */     
/* 188 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 200 */     return "DataProducer";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\AlarmGraphUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */