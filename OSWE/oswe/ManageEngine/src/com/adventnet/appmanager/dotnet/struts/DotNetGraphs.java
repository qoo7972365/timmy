/*     */ package com.adventnet.appmanager.dotnet.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DotNetGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  25 */   private String resourcename = "default";
/*  26 */   private String category = "default";
/*  27 */   private String entity = "default";
/*     */   private AMConnectionPool pool;
/*  29 */   private String attributeid = null;
/*     */   private ResultSet rs;
/*  31 */   private Hashtable graphsdata = new Hashtable();
/*     */   private int resourceid;
/*     */   
/*  34 */   public DotNetGraphs() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public void clearGraphsData()
/*     */   {
/*  38 */     this.graphsdata.clear();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  43 */     if (this.category.equals("mem"))
/*     */     {
/*  45 */       return (SubSeriesDataset)this.graphsdata.get("mem");
/*     */     }
/*  47 */     if (this.category.equals("locks"))
/*     */     {
/*  49 */       return (SubSeriesDataset)this.graphsdata.get("locks");
/*     */     }
/*  51 */     if (this.category.equals("thread"))
/*     */     {
/*  53 */       return (SubSeriesDataset)this.graphsdata.get("thread");
/*     */     }
/*  55 */     if (this.category.equals("excep"))
/*     */     {
/*  57 */       return (SubSeriesDataset)this.graphsdata.get("excep");
/*     */     }
/*  59 */     if (this.category.equals("con"))
/*     */     {
/*  61 */       return (SubSeriesDataset)this.graphsdata.get("con");
/*     */     }
/*  63 */     if (this.category.equals("bytes"))
/*     */     {
/*  65 */       return (SubSeriesDataset)this.graphsdata.get("bytes");
/*     */     }
/*  67 */     if (this.category.equals("trans"))
/*     */     {
/*  69 */       return (SubSeriesDataset)this.graphsdata.get("trans");
/*     */     }
/*  71 */     if (this.category.equals("sess"))
/*     */     {
/*  73 */       return (SubSeriesDataset)this.graphsdata.get("sess");
/*     */     }
/*  75 */     if (this.category.equals("req"))
/*     */     {
/*  77 */       return (SubSeriesDataset)this.graphsdata.get("req");
/*     */     }
/*  79 */     if (this.category.equals("jit"))
/*     */     {
/*  81 */       return (SubSeriesDataset)this.graphsdata.get("jit");
/*     */     }
/*  83 */     if (this.category.equals("security"))
/*     */     {
/*  85 */       return (SubSeriesDataset)this.graphsdata.get("security");
/*     */     }
/*     */     
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public void updateproduceDataset() {
/*  92 */     long curtime = System.currentTimeMillis();
/*  93 */     long caltime = curtime - 3600000L;
/*  94 */     String query = "select * from AM_DOTNET_PERFORMANCE where RESOURCEID=" + getresourceid() + "  and COLLECTIONTIME >=" + caltime;
/*  95 */     TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.logicalthread"), Minute.class);
/*  96 */     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.phythread"), Minute.class);
/*  97 */     TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.quelength"), Minute.class);
/*  98 */     TimeSeries ts3 = new TimeSeries(FormatUtil.getString("Heap Size"), Minute.class);
/*  99 */     TimeSeries ts4 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.exceptionspermin"), Minute.class);
/* 100 */     TimeSeries ts5 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.connpermin"), Minute.class);
/* 101 */     TimeSeries ts6 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.bytessent"), Minute.class);
/* 102 */     TimeSeries ts7 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.bytesreceived"), Minute.class);
/* 103 */     TimeSeries ts8 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.pertimeinjit"), Minute.class);
/* 104 */     TimeSeries ts9 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.runcheck"), Minute.class);
/* 105 */     TimeSeriesCollection col = new TimeSeriesCollection();
/* 106 */     TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 107 */     TimeSeriesCollection col2 = new TimeSeriesCollection();
/* 108 */     TimeSeriesCollection col3 = new TimeSeriesCollection();
/* 109 */     TimeSeriesCollection col4 = new TimeSeriesCollection();
/* 110 */     TimeSeriesCollection col5 = new TimeSeriesCollection();
/* 111 */     TimeSeriesCollection col6 = new TimeSeriesCollection();
/* 112 */     TimeSeriesCollection col7 = new TimeSeriesCollection();
/*     */     try {
/* 114 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 115 */       while (this.rs.next())
/*     */       {
/* 117 */         Date d = new Date(this.rs.getLong("COLLECTIONTIME"));
/*     */         try
/*     */         {
/* 120 */           if (this.rs.getLong("LOGICALTHREADS") > -1L)
/*     */           {
/* 122 */             ts.add(new Minute(d), this.rs.getLong("LOGICALTHREADS"));
/*     */           }
/* 124 */           if (this.rs.getLong("PHYSICALTHREADS") > -1L)
/*     */           {
/* 126 */             ts1.add(new Minute(d), this.rs.getLong("PHYSICALTHREADS")); }
/* 127 */           if (this.rs.getLong("QUEUELENGTH") > -1L)
/*     */           {
/* 129 */             ts2.add(new Minute(d), this.rs.getLong("QUEUELENGTH")); }
/* 130 */           if (this.rs.getLong("CURRENTMEMORY") > -1L)
/*     */           {
/* 132 */             ts3.add(new Minute(d), this.rs.getLong("CURRENTMEMORY") / 1048576L); }
/* 133 */           if (this.rs.getLong("EXCEPTIONSPERMIN") > -1L)
/*     */           {
/* 135 */             ts4.add(new Minute(d), this.rs.getLong("EXCEPTIONSPERMIN")); }
/* 136 */           if (this.rs.getLong("CONNECTIONSPERMIN") > -1L)
/*     */           {
/* 138 */             ts5.add(new Minute(d), this.rs.getLong("CONNECTIONSPERMIN")); }
/* 139 */           if (this.rs.getLong("BYTESSENTPERMIN") > -1L)
/*     */           {
/* 141 */             ts6.add(new Minute(d), this.rs.getLong("BYTESSENTPERMIN"));
/*     */           }
/* 143 */           if (this.rs.getLong("BYTESRECEIVEDPERMIN") > -1L)
/*     */           {
/* 145 */             ts7.add(new Minute(d), this.rs.getLong("BYTESRECEIVEDPERMIN"));
/*     */           }
/* 147 */           if (this.rs.getLong("RUNTIMECHECKSPERMIN") > -1L)
/*     */           {
/* 149 */             ts9.add(new Minute(d), this.rs.getLong("RUNTIMECHECKSPERMIN"));
/*     */           }
/* 151 */           if (this.rs.getLong("JIT") > -1L)
/*     */           {
/* 153 */             ts8.add(new Minute(d), this.rs.getLong("JIT"));
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
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
/*     */       try
/*     */       {
/* 169 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */       col.addSeries(ts);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 163 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 169 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 178 */     col.addSeries(ts1);
/* 179 */     col1.addSeries(ts2);
/* 180 */     col2.addSeries(ts3);
/* 181 */     col3.addSeries(ts4);
/* 182 */     col4.addSeries(ts5);
/* 183 */     col5.addSeries(ts6);
/* 184 */     col5.addSeries(ts7);
/* 185 */     col6.addSeries(ts8);
/* 186 */     col7.addSeries(ts9);
/* 187 */     int[] x = new int[2];
/* 188 */     x[0] = 0;
/* 189 */     x[1] = 1;
/* 190 */     int[] y = new int[1];
/* 191 */     y[0] = 0;
/* 192 */     this.graphsdata.put("thread", new SubSeriesDataset(col, x));
/* 193 */     this.graphsdata.put("locks", new SubSeriesDataset(col1, y));
/* 194 */     this.graphsdata.put("mem", new SubSeriesDataset(col2, y));
/* 195 */     this.graphsdata.put("excep", new SubSeriesDataset(col3, y));
/* 196 */     this.graphsdata.put("con", new SubSeriesDataset(col4, y));
/* 197 */     this.graphsdata.put("bytes", new SubSeriesDataset(col5, x));
/* 198 */     this.graphsdata.put("jit", new SubSeriesDataset(col6, y));
/* 199 */     this.graphsdata.put("security", new SubSeriesDataset(col7, y));
/*     */   }
/*     */   
/*     */   public void updateproduceDatasetForApps()
/*     */   {
/* 204 */     long curtime = System.currentTimeMillis();
/* 205 */     long caltime = curtime - 3600000L;
/* 206 */     String query = "select * from AM_DOTNET_APPSTATS where RESOURCEID=" + getresourceid() + "  and COLLECTIONTIME >=" + caltime;
/* 207 */     TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.activesession"), Minute.class);
/* 208 */     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.requestpermin"), Minute.class);
/* 209 */     TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.quereq"), Minute.class);
/* 210 */     TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.transactionpermin"), Minute.class);
/* 211 */     TimeSeries ts6 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.bytessent"), Minute.class);
/* 212 */     TimeSeries ts7 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.bytesreceived"), Minute.class);
/*     */     
/* 214 */     TimeSeriesCollection col = new TimeSeriesCollection();
/* 215 */     TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 216 */     TimeSeriesCollection col2 = new TimeSeriesCollection();
/* 217 */     TimeSeriesCollection col3 = new TimeSeriesCollection();
/*     */     try {
/* 219 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 220 */       while (this.rs.next())
/*     */       {
/* 222 */         Date d = new Date(this.rs.getLong("COLLECTIONTIME"));
/*     */         try
/*     */         {
/* 225 */           if (this.rs.getLong("ACTIVESESSIONS") > -1L)
/*     */           {
/* 227 */             ts.add(new Minute(d), this.rs.getLong("ACTIVESESSIONS"));
/*     */           }
/* 229 */           if (this.rs.getLong("REQUESTSPERMIN") > -1L)
/*     */           {
/* 231 */             ts1.add(new Minute(d), this.rs.getLong("REQUESTSPERMIN")); }
/* 232 */           if (this.rs.getLong("QUEUEDREQUESTS") > -1L)
/*     */           {
/* 234 */             ts2.add(new Minute(d), this.rs.getLong("QUEUEDREQUESTS")); }
/* 235 */           if (this.rs.getLong("TRANSACTIOSPERMIN") > -1L)
/*     */           {
/* 237 */             ts3.add(new Minute(d), this.rs.getLong("TRANSACTIOSPERMIN")); }
/* 238 */           if (this.rs.getLong("BYTESSENTPERMIN") > -1L)
/*     */           {
/* 240 */             ts6.add(new Minute(d), this.rs.getLong("BYTESSENTPERMIN")); }
/* 241 */           if (this.rs.getLong("BYTESRECEIVEDPERMIN") > -1L)
/*     */           {
/* 243 */             ts7.add(new Minute(d), this.rs.getLong("BYTESRECEIVEDPERMIN"));
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
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
/*     */       try
/*     */       {
/* 259 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 267 */       col.addSeries(ts);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 253 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 259 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 268 */     col1.addSeries(ts1);
/* 269 */     col1.addSeries(ts2);
/* 270 */     col2.addSeries(ts3);
/* 271 */     col3.addSeries(ts6);
/* 272 */     col3.addSeries(ts7);
/* 273 */     int[] x = new int[2];
/* 274 */     x[0] = 0;
/* 275 */     x[1] = 1;
/* 276 */     int[] y = new int[1];
/* 277 */     y[0] = 0;
/* 278 */     this.graphsdata.put("sess", new SubSeriesDataset(col, y));
/* 279 */     this.graphsdata.put("req", new SubSeriesDataset(col1, x));
/* 280 */     this.graphsdata.put("trans", new SubSeriesDataset(col2, y));
/* 281 */     this.graphsdata.put("bytes", new SubSeriesDataset(col3, x));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 288 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 292 */     return "exchangegraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 297 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 301 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 305 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 309 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 313 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 317 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public String getAttributeid() {
/* 321 */     return this.attributeid;
/*     */   }
/*     */   
/*     */   public void setAttributeid(String ent) {
/* 325 */     this.attributeid = ent;
/*     */   }
/*     */   
/*     */   public void setresourceid(int id)
/*     */   {
/* 330 */     this.resourceid = id;
/*     */   }
/*     */   
/*     */   public int getresourceid() {
/* 334 */     return this.resourceid;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\dotnet\struts\DotNetGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */