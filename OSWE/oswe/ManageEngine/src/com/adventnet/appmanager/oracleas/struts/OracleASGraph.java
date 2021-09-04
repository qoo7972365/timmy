/*     */ package com.adventnet.appmanager.oracleas.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ public class OracleASGraph implements com.adventnet.awolf.data.DatasetProducer, java.io.Serializable
/*     */ {
/*  25 */   String resid = null;
/*  26 */   String parameter = null;
/*  27 */   ArrayList p = new ArrayList();
/*  28 */   Properties resData = new Properties();
/*     */   
/*     */   public ArrayList getProperties() {
/*  31 */     return this.p;
/*     */   }
/*     */   
/*     */   public void setProperties(ArrayList al)
/*     */   {
/*  36 */     this.p = al;
/*     */   }
/*     */   
/*     */   public Properties getHTTPResponseData()
/*     */   {
/*  41 */     return this.resData;
/*     */   }
/*     */   
/*     */   public void setHTTPResponseData(Properties p)
/*     */   {
/*  46 */     this.resData = p;
/*     */   }
/*     */   
/*     */   public void setParameter(String resid, String parameter)
/*     */   {
/*  51 */     this.resid = resid;
/*  52 */     this.parameter = parameter;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) throws DatasetProduceException
/*     */   {
/*  57 */     long curTime = System.currentTimeMillis();
/*  58 */     long sixhoursbefore = curTime - 21600000L;
/*     */     
/*  60 */     if (this.parameter.equals("httprequest"))
/*     */     {
/*  62 */       ResultSet rs = null;
/*  63 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.oracleas.throughput"), Minute.class);
/*     */       
/*  65 */       String query = "select REQUESTTHROUGHPUT,COLLECTIONTIME from AM_OAS_HTTPServerData where AM_OAS_HTTPServerData.RESOURCEID=" + this.resid + " and COLLECTIONTIME>=" + sixhoursbefore + " and COLLECTIONTIME <=" + curTime;
/*     */       try {
/*  67 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  68 */         ArrayList d1 = new ArrayList();
/*  69 */         while (rs.next())
/*     */         {
/*  71 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*  72 */           int value = rs.getInt("REQUESTTHROUGHPUT");
/*  73 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*  74 */           d1.add(new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  77 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  80 */         closeResultSet(rs);
/*     */       }
/*  82 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  83 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/*  85 */     if (this.parameter.equals("httpresponse"))
/*     */     {
/*  87 */       ResultSet rs = null;
/*  88 */       TimeSeries ts = new TimeSeries("Average Response Time", Minute.class);
/*     */       
/*  90 */       String query = "select AVGREQUESTTIME,COLLECTIONTIME from AM_OAS_HTTPServerData where AM_OAS_HTTPServerData.RESOURCEID=" + this.resid + " and COLLECTIONTIME>=" + sixhoursbefore + " and COLLECTIONTIME <=" + curTime;
/*     */       try {
/*  92 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  93 */         while (rs.next())
/*     */         {
/*  95 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*  96 */           int value = rs.getInt("AVGREQUESTTIME");
/*  97 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 100 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 103 */         closeResultSet(rs);
/*     */       }
/* 105 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 106 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 108 */     if (this.parameter.equals("processstat"))
/*     */     {
/* 110 */       ResultSet rs = null;
/* 111 */       DefaultPieDataset ds = new DefaultPieDataset();
/*     */       try {
/* 113 */         String query = "select max(collectiontime) from AM_OAS_ProcessData,AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=" + this.resid + " and AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.CHILDID=AM_OAS_ProcessData.RESOURCEID;";
/* 114 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 115 */         long time = 0L;
/*     */         
/* 117 */         if (rs.next()) {
/* 118 */           time = rs.getLong(1);
/*     */         }
/* 120 */         closeResultSet(rs);
/*     */         
/* 122 */         query = "select * from AM_OAS_HostData where RESOURCEID=" + this.resid + " and COLLECTIONTIME=" + time;
/* 123 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 124 */         double freememory = 0.0D;
/* 125 */         double usedmemory = 0.0D;
/* 126 */         if (rs.next())
/*     */         {
/* 128 */           freememory = rs.getDouble("FREEMEMORY");
/* 129 */           usedmemory = rs.getDouble("USEDMEMORY");
/* 130 */           ds.setValue(FormatUtil.getString("am.webclient.oracleas.freememory"), freememory);
/*     */         }
/* 132 */         query = "select AM_OAS_ProcessData.*,m2.displayname from AM_OAS_ProcessData,AM_ManagedObject m1,AM_ManagedObject m2,AM_PARENTCHILDMAPPER where m1.RESOURCEID=" + this.resid + " and AM_PARENTCHILDMAPPER.PARENTID=m1.RESOURCEID and AM_PARENTCHILDMAPPER.CHILDID=AM_OAS_ProcessData.RESOURCEID and COLLECTIONTIME=" + time + " and m2.resourceid=AM_OAS_ProcessData.RESOURCEID;";
/* 133 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 134 */         double totalprocessmemory = 0.0D;
/* 135 */         ArrayList data = new ArrayList();
/* 136 */         Properties p; while (rs.next())
/*     */         {
/* 138 */           p = new Properties();
/* 139 */           String pname = rs.getString("displayname");
/*     */           
/* 141 */           double memused = rs.getDouble("MEMORYUSED");
/* 142 */           p.put("name", pname);
/* 143 */           p.put("usedmemory", new Double(memused));
/* 144 */           p.put("resourceid", rs.getString("RESOURCEID"));
/*     */           
/* 146 */           if (memused != -1.0D)
/*     */           {
/* 148 */             ds.setValue(pname, memused);
/* 149 */             totalprocessmemory += memused;
/*     */           }
/* 151 */           data.add(p);
/*     */         }
/* 153 */         if ((usedmemory != 0.0D) && (totalprocessmemory != 0.0D)) {
/* 154 */           ds.setValue(FormatUtil.getString("am.webclient.oracleas.others"), usedmemory - totalprocessmemory);
/*     */         }
/* 156 */         setProperties(data);
/* 157 */         return ds;
/*     */       } catch (Exception e) {
/* 159 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 162 */         closeResultSet(rs);
/*     */       }
/*     */     } else { HashMap data;
/* 165 */       if (this.parameter.equals("activesession"))
/*     */       {
/* 167 */         ResultSet rs = null;
/*     */         try {
/* 169 */           String query = "select wmo.displayname as webappname,data.activesession,data.collectiontime,pmo.displayname as processname from AM_PARENTCHILDMAPPER pcm,AM_PARENTCHILDMAPPER pcm1,AM_WAR war,AM_OAS_WebAppData data,AM_ManagedObject pmo,AM_ManagedObject wmo where pcm.childid=war.warid and data.resourceid=war.warid and pmo.resourceid=pcm.parentid and war.warid=wmo.resourceid and pcm1.childid=pmo.resourceid and pcm1.parentid=" + this.resid + " and collectiontime>" + sixhoursbefore + " and collectiontime<" + curTime + " order by data.activesession desc ";
/* 170 */           String query1 = DBQueryUtil.getTopNValues(query, 10);
/* 171 */           rs = AMConnectionPool.executeQueryStmt(query1);
/* 172 */           AMLog.debug(this.resid + "==query1 for Webapplications ==" + query1);
/* 173 */           ArrayList webapps = new ArrayList();
/* 174 */           data = new HashMap();
/* 175 */           while (rs.next())
/*     */           {
/* 177 */             String webappname = rs.getString("webappname");
/* 178 */             String process = rs.getString("processname");
/* 179 */             String key = webappname + "#" + process;
/* 180 */             if (!webapps.contains(key))
/*     */             {
/* 182 */               webapps.add(key);
/* 183 */               HashMap p = new HashMap();
/* 184 */               data.put(key, p);
/*     */             }
/* 186 */             HashMap p1 = (HashMap)data.get(key);
/* 187 */             Date d = new Date(rs.getLong("collectiontime"));
/* 188 */             p1.put(d, new Integer(rs.getInt("activesession")));
/*     */           }
/*     */           
/* 191 */           TimeSeriesCollection col = new TimeSeriesCollection();
/* 192 */           int size = webapps.size();
/* 193 */           for (int i = 0; i < size; i++)
/*     */           {
/* 195 */             TimeSeries ts = new TimeSeries((String)webapps.get(i), Minute.class);
/* 196 */             HashMap temp = (HashMap)data.get(webapps.get(i));
/* 197 */             Iterator keys = temp.keySet().iterator();
/* 198 */             while (keys.hasNext())
/*     */             {
/* 200 */               Date d = (Date)keys.next();
/* 201 */               ts.addOrUpdate(new Minute(d), ((Integer)temp.get(d)).intValue());
/*     */             }
/* 203 */             col.addSeries(ts);
/*     */           }
/* 205 */           int[] arr = new int[size];
/* 206 */           for (int i = 0; i < size; i++)
/*     */           {
/* 208 */             arr[i] = i;
/*     */           }
/* 210 */           return new SubSeriesDataset(col, arr);
/*     */         } catch (Exception e) {
/* 212 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 215 */           closeResultSet(rs);
/*     */         }
/*     */       }
/* 218 */       else if (this.parameter.equals("throughput"))
/*     */       {
/* 220 */         String query = "select REQUESTTHROUGHPUT,COLLECTIONTIME from AM_OAS_WebAppData where collectiontime>" + sixhoursbefore + " and collectiontime<" + curTime + " and resourceid=" + this.resid;
/* 221 */         ResultSet rs = null;
/*     */         try
/*     */         {
/* 224 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 225 */           TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.oracleas.throughput"), Minute.class);
/* 226 */           while (rs.next())
/*     */           {
/* 228 */             Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 229 */             ts.addOrUpdate(new Minute(d), rs.getInt("REQUESTTHROUGHPUT"));
/*     */           }
/* 231 */           TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 232 */           return new SubSeriesDataset(tsc, 0);
/*     */         } catch (Exception e) {
/* 234 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 237 */           closeResultSet(rs);
/*     */         }
/*     */       }
/* 240 */       else if (this.parameter.equals("httpresponsecode"))
/*     */       {
/* 242 */         DefaultPieDataset ds = new DefaultPieDataset();
/* 243 */         String query = "select max(COLLECTIONTIME) from AM_OAS_TOMCATRESPONSESTATS  where  RESOURCEID=" + this.resid;
/* 244 */         ResultSet rs = null;
/*     */         try {
/* 246 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 247 */           long time = 0L;
/* 248 */           if (rs.next()) {
/* 249 */             time = rs.getLong(1);
/*     */           }
/* 251 */           rs.close();
/*     */           
/*     */ 
/*     */ 
/* 255 */           rs = DBQueryUtil.executeQueryStmt("am.Oracleas.graph.query", new String[] { time + "", this.resid });
/* 256 */           Properties p = new Properties();
/* 257 */           if (rs.next()) {
/* 258 */             p.put("1XX", new Long(rs.getLong(1)));
/* 259 */             p.put("2XX", new Long(rs.getLong(2)));
/* 260 */             p.put("3XX", new Long(rs.getLong(3)));
/* 261 */             p.put("4XX", new Long(rs.getLong(4)));
/* 262 */             p.put("5XX", new Long(rs.getLong(5)));
/*     */             
/* 264 */             ds.setValue("1XX", ((Long)p.get("1XX")).longValue());
/* 265 */             ds.setValue("2XX", ((Long)p.get("2XX")).longValue());
/* 266 */             ds.setValue("3XX", ((Long)p.get("3XX")).longValue());
/* 267 */             ds.setValue("4XX", ((Long)p.get("4XX")).longValue());
/* 268 */             ds.setValue("5XX", ((Long)p.get("5XX")).longValue());
/*     */           }
/* 270 */           setHTTPResponseData(p);
/*     */         }
/*     */         catch (Exception e) {
/* 273 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 277 */           closeResultSet(rs);
/*     */         }
/*     */         
/* 280 */         return ds;
/*     */       } }
/* 282 */     return null;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/* 287 */     if (rs != null) {
/*     */       try
/*     */       {
/* 290 */         rs.close();
/*     */       } catch (Exception e) {
/* 292 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\oracleas\struts\OracleASGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */