/*     */ package com.adventnet.appmanager.server.wlogic.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetDataFromDB
/*     */ {
/*     */   public Properties getServerStaticData(String resID)
/*     */   {
/*  18 */     ResultSet res = null;
/*  19 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  20 */     String query = "select * from AM_WLS_ServerDetails where RESID=" + resID;
/*  21 */     data = new Properties();
/*     */     try
/*     */     {
/*  24 */       res = AMConnectionPool.executeQueryStmt(query);
/*  25 */       if (res.next())
/*     */       {
/*  27 */         data.setProperty("version", res.getString("VERSION"));
/*  28 */         data.setProperty("state", res.getString("STATE"));
/*  29 */         data.setProperty("activationTime", res.getString("ACTIVATIONTIME"));
/*  30 */         data.setProperty("port", res.getString("PORT"));
/*  31 */         data.setProperty("restarts", res.getString("RESTARTS"));
/*     */       }
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
/*  49 */       return data;
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/*  35 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  41 */         if (res != null)
/*     */         {
/*  43 */           res.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Properties getJVMPerformanceData(String resID)
/*     */   {
/*  54 */     long collectionTime = getMaxCollectionTime("select max(COLLECTIONTIME) from AM_JVMData where ID=" + resID);
/*     */     
/*  56 */     long oneHourBefore = collectionTime - 3600000L;
/*  57 */     ResultSet rs1 = null;
/*  58 */     ResultSet rs = null;
/*  59 */     ResultSet rs2 = null;
/*  60 */     ResultSet rs3 = null;
/*  61 */     String query2 = DBQueryUtil.getTopNValues("select MAXFREEMEMORY,MAXFREEMEMORYPERCENTAGE from AM_JVMFreeMemoryData where ID=" + resID + " order by collectiontime desc ", 1);
/*     */     
/*     */ 
/*  64 */     props = new Properties();
/*  65 */     if (collectionTime != -1L)
/*     */     {
/*  67 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  68 */       String query1 = DBQueryUtil.getTopNValues("select HEAPSIZECURRENT,HEAPFREECURRENT from AM_JVMData where ID=" + resID + " order by collectiontime desc ", 1);
/*     */       
/*  70 */       String query = "select min(HEAPSIZECURRENT),max(HEAPSIZECURRENT),avg(HEAPSIZECURRENT) from AM_JVMData where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + collectionTime + " and ID=" + resID + " group by ID";
/*     */       
/*     */       try
/*     */       {
/*  74 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  75 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/*  76 */         rs3 = AMConnectionPool.executeQueryStmt("select  Value from AM_JBOSS_DETAILS where RESOURCEID=" + resID + " and AttributeName='MaxMemory'");
/*     */         
/*  78 */         if (rs1.next())
/*     */         {
/*  80 */           long total = (rs1.getLong(1) + rs1.getLong(2)) / 1024L;
/*  81 */           props.setProperty("total", String.valueOf(total));
/*     */         }
/*  83 */         rs2 = AMConnectionPool.executeQueryStmt(query2);
/*  84 */         if (rs.next())
/*     */         {
/*  86 */           long min = rs.getLong(1) / 1024L;
/*  87 */           long max = rs.getLong(2) / 1024L;
/*  88 */           long avg = rs.getLong(3) / 1024L;
/*     */           
/*  90 */           props.setProperty("min", String.valueOf(min));
/*  91 */           props.setProperty("max", String.valueOf(max));
/*  92 */           props.setProperty("avg", String.valueOf(avg));
/*     */         }
/*     */         
/*  95 */         if (rs3.next())
/*     */         {
/*  97 */           long maxmem = rs3.getLong(1);
/*  98 */           props.setProperty("maxmemoryjvm", String.valueOf(maxmem));
/*     */         }
/*     */         
/* 101 */         if (rs2.next())
/*     */         {
/* 103 */           double maxfreemem = rs2.getDouble(1);
/* 104 */           int maxfreemempercent = rs2.getInt(2);
/* 105 */           DecimalFormat twoDForm = new DecimalFormat("#.##");
/* 106 */           Double maxfreememD = Double.valueOf(twoDForm.format(maxfreemem));
/* 107 */           props.setProperty("maxfreemem", String.valueOf(maxfreememD));
/* 108 */           props.setProperty("maxfreemempercent", String.valueOf(maxfreemempercent));
/*     */         }
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
/* 158 */         return props;
/*     */       }
/*     */       catch (Exception exp)
/*     */       {
/* 113 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 119 */           if (rs != null)
/*     */           {
/*     */ 
/* 122 */             rs.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */         
/*     */         try
/*     */         {
/* 129 */           if (rs1 != null)
/*     */           {
/* 131 */             rs1.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */         
/*     */         try
/*     */         {
/* 138 */           if (rs2 != null)
/*     */           {
/* 140 */             rs2.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 148 */           if (rs3 != null)
/*     */           {
/* 150 */             rs3.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Properties getResponseTimePerformanceData(String resID)
/*     */   {
/* 163 */     long collectionTime = getMaxCollectionTime("select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + resID);
/*     */     
/* 165 */     long oneHourBefore = collectionTime - 3600000L;
/* 166 */     ResultSet rs = null;
/* 167 */     props = new Properties();
/* 168 */     if (collectionTime != -1L)
/*     */     {
/* 170 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 171 */       String query = "select min(RESPONSETIME),max(RESPONSETIME),sum(RESPONSETIME)/count(*) from AM_ManagedObjectData where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + collectionTime + " and RESID=" + resID + " and RESPONSETIME <> -1 group by RESID";
/*     */       
/*     */       try
/*     */       {
/* 175 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 176 */         if (rs.next())
/*     */         {
/* 178 */           props.setProperty("min", String.valueOf(rs.getLong(1)));
/* 179 */           props.setProperty("max", String.valueOf(rs.getLong(2)));
/* 180 */           props.setProperty("avg", String.valueOf(rs.getLong(3)));
/*     */         }
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
/* 200 */         return props;
/*     */       }
/*     */       catch (Exception exp)
/*     */       {
/* 185 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 191 */           if (rs != null)
/*     */           {
/* 193 */             rs.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final long getMaxCollectionTime(String query)
/*     */   {
/* 205 */     collectionTime = 0L;
/* 206 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 207 */     ResultSet set = null;
/*     */     try
/*     */     {
/* 210 */       set = AMConnectionPool.executeQueryStmt(query);
/* 211 */       if (set.next()) {}
/*     */       
/* 213 */       return set.getLong(1);
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 217 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 223 */         if (set != null)
/*     */         {
/* 225 */           set.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList getManagedWebApplications(String applicationName)
/*     */   {
/* 238 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 239 */     ResultSet set = null;
/* 240 */     String query = "select distinct(WLSWebAppData.WEBAPPNAME),Applications.RESOURCENAME from WLSWebAppData,Applications,ManagedJ2EEApplications where WLSWebAppData.WEBAPPNAME=Applications.APPNAME and ManagedJ2EEApplications.ID=Applications.ID and ManagedJ2EEApplications.NAME='" + applicationName + "'";
/* 241 */     toReturn = new ArrayList();
/*     */     try
/*     */     {
/* 244 */       set = AMConnectionPool.executeQueryStmt(query);
/* 245 */       while (set.next())
/*     */       {
/* 247 */         Properties props = new Properties();
/* 248 */         props.setProperty("webAppName", set.getString(1));
/* 249 */         props.setProperty("resourceName", set.getString(2));
/* 250 */         toReturn.add(props);
/*     */       }
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
/* 269 */       return toReturn;
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 254 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 260 */         if (set != null)
/*     */         {
/* 262 */           set.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Properties getDetailsFromInetService(String resourceName)
/*     */   {
/* 275 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 276 */     ResultSet set = null;
/* 277 */     String query = "select InetService.TARGETNAME,InetService.PORTNO,InetService.PROTOCOLVERSION from InetService where NAME='" + resourceName + "'";
/* 278 */     toReturn = new Properties();
/*     */     try
/*     */     {
/* 281 */       set = AMConnectionPool.executeQueryStmt(query);
/* 282 */       if (set.next())
/*     */       {
/* 284 */         toReturn.setProperty("targetName", set.getString(1));
/* 285 */         toReturn.setProperty("portno", set.getString(2));
/* 286 */         toReturn.setProperty("clientType", set.getString(3));
/*     */       }
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
/* 305 */       return toReturn;
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 290 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 296 */         if (set != null)
/*     */         {
/* 298 */           set.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\wlogic\bean\GetDataFromDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */