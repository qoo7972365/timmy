/*     */ package com.adventnet.appmanager.client.wsm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSMGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  24 */   String resourceid = null;
/*  25 */   String instanceid = null;
/*  26 */   String parameter = null;
/*  27 */   HashMap props = new HashMap();
/*     */   
/*     */   public void setParameter(String resid, String insid, String param)
/*     */   {
/*  31 */     this.resourceid = resid;
/*  32 */     this.instanceid = insid;
/*  33 */     this.parameter = param;
/*     */   }
/*     */   
/*     */   public void setParameter(String resid, String param) {
/*  37 */     this.resourceid = resid;
/*  38 */     this.parameter = param;
/*     */   }
/*     */   
/*     */   private void setProperty(String key, String value)
/*     */   {
/*  43 */     this.props.put(key, value);
/*     */   }
/*     */   
/*     */   public String getProperty(String key)
/*     */   {
/*  48 */     return (String)this.props.get(key);
/*     */   }
/*     */   
/*     */   public String getMethodName(String insid)
/*     */   {
/*  53 */     String query = "select name from AM_WSM_Operation o,AM_WSM_Instance i where i.id=" + insid + " and i.operationid=o.id";
/*  54 */     ResultSet rs = null;
/*     */     try {
/*  56 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  57 */       if (rs.next())
/*     */       {
/*  59 */         String methodname = rs.getString("name");
/*  60 */         return methodname;
/*     */       }
/*     */     } catch (SQLException e) {
/*  63 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/*  66 */       closeResultSet(rs);
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) throws DatasetProduceException
/*     */   {
/*  73 */     if (this.parameter.equals("urlresponsetime"))
/*     */     {
/*  75 */       TimeSeries ts = new TimeSeries("Response Time", Minute.class);
/*  76 */       String query = "select * from AM_WSM_WebServiceData where resourceid=" + this.resourceid + " order by collectiontime desc";
/*  77 */       ResultSet rs = null;
/*     */       try {
/*  79 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  80 */         while (rs.next())
/*     */         {
/*  82 */           long coltime = rs.getLong("collectiontime");
/*  83 */           int exectime = rs.getInt("executiontime");
/*  84 */           ts.addOrUpdate(new Minute(new Date(coltime)), exectime);
/*     */         }
/*  86 */         TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  87 */         return new SubSeriesDataset(tsc, 0);
/*     */       } catch (Exception e) {
/*  89 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  92 */         closeResultSet(rs);
/*     */       }
/*     */     } else {
/*  95 */       if (this.parameter.equals("methodexecution"))
/*     */       {
/*     */ 
/*  98 */         TimeSeriesCollection tsc = new TimeSeriesCollection(getInstanceTimeSeries(this.instanceid, 1));
/*  99 */         return new SubSeriesDataset(tsc, 0); }
/*     */       int i;
/* 101 */       if (this.parameter.equals("allmethods"))
/*     */       {
/* 103 */         String query = "select i.id from AM_WSM_OperationWSDLMap owm,AM_WSM_Instance i,AM_WSM_Operation o where owm.resourceid=" + this.resourceid + " and owm.operationid=i.operationid and i.operationid=o.id order by o.name,i.id";
/* 104 */         ResultSet rs = null;
/*     */         try {
/* 106 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 107 */           TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 108 */           int size = 0;
/* 109 */           while (rs.next())
/*     */           {
/* 111 */             String insid = rs.getString("id");
/* 112 */             tsc.addSeries(getInstanceTimeSeries(insid, size + 1));
/* 113 */             size++;
/*     */           }
/* 115 */           int[] arr = new int[size];
/* 116 */           i = 0; for (int len = arr.length; i < len; i++)
/*     */           {
/* 118 */             arr[i] = i;
/*     */           }
/* 120 */           return new SubSeriesDataset(tsc, arr);
/*     */         } catch (Exception e) {
/* 122 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 125 */           closeResultSet(rs);
/*     */         }
/*     */       }
/* 128 */       else if (this.parameter.equals("xsltAttrGraph"))
/*     */       {
/* 130 */         String[] attr_details = DBUtil.getAttributeDetails(this.resourceid);
/* 131 */         TimeSeries ts = new TimeSeries(attr_details[1], Minute.class);
/* 132 */         String query = "select VALUE,COLLECTIONTIME from AM_CAM_COLUMNAR_DATA where ROWID=" + this.instanceid + " and ATTRIBUTEID=" + this.resourceid + " order by COLLECTIONTIME desc";
/* 133 */         ResultSet rs = null;
/*     */         try {
/* 135 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 136 */           while (rs.next())
/*     */           {
/* 138 */             long coltime = rs.getLong("COLLECTIONTIME");
/* 139 */             int value = Integer.parseInt(rs.getString("VALUE"));
/* 140 */             ts.addOrUpdate(new Minute(new Date(coltime)), value);
/*     */           }
/* 142 */           TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 143 */           return new SubSeriesDataset(tsc, 0);
/*     */         } catch (Exception e) {
/* 145 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 148 */           closeResultSet(rs);
/*     */         }
/*     */       } }
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   private TimeSeries getInstanceTimeSeries(String insid, int serialNum)
/*     */   {
/* 156 */     String query = "select collectiontime,executiontime from AM_WSM_OperationData where instanceid=" + insid + " order by collectiontime";
/*     */     
/*     */ 
/* 159 */     ResultSet rs = null;
/* 160 */     TimeSeries ts = new TimeSeries(serialNum + "." + getInstanceName(insid), Minute.class);
/*     */     try {
/* 162 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 163 */       while (rs.next())
/*     */       {
/*     */ 
/* 166 */         Date d = new Date(rs.getLong("collectiontime"));
/* 167 */         int value = rs.getInt("executiontime");
/* 168 */         ts.addOrUpdate(new Minute(d), Integer.valueOf(value));
/*     */       }
/*     */     } catch (Exception e) {
/* 171 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 174 */       closeResultSet(rs);
/*     */     }
/* 176 */     return ts;
/*     */   }
/*     */   
/*     */   private String getInstanceName(String insid)
/*     */   {
/* 181 */     String query = "select displayname from AM_ManagedObject where resourceid=" + insid;
/* 182 */     ResultSet rs = null;
/*     */     try {
/* 184 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 185 */       if (rs.next()) {
/* 186 */         return rs.getString("displayname");
/*     */       }
/*     */     } catch (Exception e) {
/* 189 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 192 */       closeResultSet(rs);
/*     */     }
/* 194 */     return "";
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/* 199 */     if (rs != null) {
/*     */       try {
/* 201 */         rs.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\wsm\WSMGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */