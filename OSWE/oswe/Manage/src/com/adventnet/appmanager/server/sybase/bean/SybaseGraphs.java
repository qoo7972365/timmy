/*     */ package com.adventnet.appmanager.server.sybase.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ public class SybaseGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  21 */   private int resid = -1;
/*  22 */   private int dbresid = -1;
/*  23 */   private String type = "default";
/*  24 */   private String resourcename = null;
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  29 */     long curtime = System.currentTimeMillis();
/*  30 */     long queryval = curtime - 21600000L;
/*  31 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  32 */     ResultSet rs = null;
/*  33 */     if (this.type.equals("CONNECTION")) {
/*  34 */       String query = "select * from AM_ManagedObjectData where AM_ManagedObjectData.RESID='" + this.resid + "' and AM_ManagedObjectData.RESPONSETIME <> '-1'";
/*     */       try {
/*  36 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/*  39 */         e.printStackTrace();
/*     */       }
/*  41 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*  42 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.oracle.graph.connectiontime"), Minute.class);
/*     */       try {
/*  44 */         while (rs.next()) {
/*  45 */           long time = rs.getLong(1);
/*  46 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try {
/*  48 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("RESPONSETIME"));
/*     */           }
/*     */           catch (Exception ex) {
/*  51 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*  54 */         col.addSeries(ts1);
/*  55 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/*  58 */         col.addSeries(ts1);
/*  59 */         e.printStackTrace();
/*     */       }
/*  61 */       int[] x = { 0 };
/*  62 */       return new SubSeriesDataset(col, x);
/*     */     }
/*  64 */     if (this.type.equals("MEMORYDETAILS"))
/*     */     {
/*  66 */       String query = "select USEDMEMORY,COLLECTIONTIME from AM_SYBASEMEMORYDETAILS where AM_SYBASEMEMORYDETAILS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/*  69 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/*  72 */         e.printStackTrace();
/*     */       }
/*  74 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/*  75 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Used Memory (KB)"), Minute.class);
/*     */       try {
/*  77 */         while (rs.next()) {
/*  78 */           long time = rs.getLong(1);
/*  79 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  82 */             ts1.addOrUpdate(new Minute(d), rs.getLong("USEDMEMORY"));
/*     */           }
/*     */           catch (Exception ex) {
/*  85 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*  88 */         col1.addSeries(ts1);
/*  89 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/*  92 */         col1.addSeries(ts1);
/*  93 */         e.printStackTrace();
/*     */       }
/*  95 */       int[] x = { 0 };
/*  96 */       return new SubSeriesDataset(col1, x);
/*     */     }
/*  98 */     if (this.type.equals("REMOTECONNECTION"))
/*     */     {
/* 100 */       String query = "select ACTIVEREMOTECONNECTIONS,COLLECTIONTIME from AM_SYBASECONNECTIONDETAILS where AM_SYBASECONNECTIONDETAILS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/* 103 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 106 */         e.printStackTrace();
/*     */       }
/* 108 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 109 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.sybase.activeremoteconnections.text"), Minute.class);
/*     */       try {
/* 111 */         while (rs.next()) {
/* 112 */           long time = rs.getLong(1);
/* 113 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try {
/* 115 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("ACTIVEREMOTECONNECTIONS"));
/*     */           }
/*     */           catch (Exception ex) {
/* 118 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 121 */         col1.addSeries(ts1);
/* 122 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/* 125 */         col1.addSeries(ts1);
/* 126 */         e.printStackTrace();
/*     */       }
/* 128 */       int[] x = { 0 };
/* 129 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 131 */     if (this.type.equals("USERCONNECTION"))
/*     */     {
/* 133 */       String query = "select ACTIVEUSERCONNECTIONS,COLLECTIONTIME from AM_SYBASECONNECTIONDETAILS where AM_SYBASECONNECTIONDETAILS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/* 136 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 139 */         e.printStackTrace();
/*     */       }
/* 141 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 142 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.sybase.activeuserconnections.text"), Minute.class);
/*     */       try {
/* 144 */         while (rs.next()) {
/* 145 */           long time = rs.getLong(1);
/* 146 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try {
/* 148 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("ACTIVEUSERCONNECTIONS"));
/*     */           }
/*     */           catch (Exception ex) {
/* 151 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 154 */         col1.addSeries(ts1);
/* 155 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/* 158 */         col1.addSeries(ts1);
/* 159 */         e.printStackTrace();
/*     */       }
/* 161 */       int[] x = { 0 };
/* 162 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 164 */     if (this.type.equals("DBSIZE")) {
/* 165 */       DefaultPieDataset ds = new DefaultPieDataset();
/* 166 */       String query = "select * from AM_SYBASEDATABASEDETAILS where PARENTRESOURCEID='" + this.resid + "'";
/*     */       try {
/* 168 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 169 */         double size = 0.0D;
/* 170 */         for (int i = 0; rs.next(); i++) {
/* 171 */           if (i <= 5) {
/* 172 */             ds.setValue(FormatUtil.getTrimmedText(rs.getString("NAME"), 20), formatnumber(rs.getDouble("SIZE")));
/*     */           } else {
/* 174 */             size += rs.getDouble("SIZE");
/* 175 */             if (rs.isLast()) {
/* 176 */               ds.setValue("Other Databases", formatnumber(size));
/*     */             }
/*     */           }
/*     */         }
/* 180 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/* 183 */         e.printStackTrace();
/*     */       }
/* 185 */       return ds;
/*     */     }
/*     */     
/* 188 */     return null;
/*     */   }
/*     */   
/*     */   public int getresid() {
/* 192 */     return this.resid;
/*     */   }
/*     */   
/*     */   public void setresid(int resource) {
/* 196 */     this.resid = resource;
/*     */   }
/*     */   
/*     */   public int getdbresid() {
/* 200 */     return this.dbresid;
/*     */   }
/*     */   
/*     */   public void setdbresid(int resource) {
/* 204 */     this.dbresid = resource;
/*     */   }
/*     */   
/*     */   public String gettype() {
/* 208 */     return this.type;
/*     */   }
/*     */   
/*     */   public void settype(String cat) {
/* 212 */     this.type = cat;
/*     */   }
/*     */   
/*     */   public void setresourcename(String name) {
/* 216 */     this.resourcename = name;
/*     */   }
/*     */   
/*     */ 
/* 220 */   public String getresourcename() { return this.resourcename; }
/*     */   
/*     */   public double formatnumber(double d) {
/* 223 */     double value = 0.0D;
/*     */     try {
/* 225 */       String temp = new Double(d * 100.0D).longValue() + "";
/* 226 */       if (temp.length() < 2) {
/* 227 */         temp = "0" + temp;
/*     */       }
/* 229 */       temp = temp.substring(0, temp.length() - 2) + "." + temp.substring(temp.length() - 2, temp.length());
/* 230 */       value = Double.parseDouble(temp);
/*     */     } catch (Exception e) {
/* 232 */       e.printStackTrace();
/*     */     }
/* 234 */     return value;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet set)
/*     */   {
/*     */     try
/*     */     {
/* 241 */       if (set != null)
/*     */       {
/* 243 */         AMConnectionPool.closeStatement(set);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 248 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\sybase\bean\SybaseGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */