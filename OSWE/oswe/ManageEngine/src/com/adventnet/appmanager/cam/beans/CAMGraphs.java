/*     */ package com.adventnet.appmanager.cam.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class CAMGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  29 */   private int attributeID = -1;
/*  30 */   private String type = "";
/*  31 */   private String attributeName = null;
/*  32 */   private int resourceid = -1;
/*  33 */   private boolean customtype = false;
/*  34 */   private String baseid = "-1";
/*     */   
/*     */ 
/*     */   public void setParam(int attributeID)
/*     */   {
/*  39 */     this.attributeID = attributeID;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setParam(int attributeID, String type, String attributeName)
/*     */   {
/*  45 */     this.type = type;
/*  46 */     this.attributeID = attributeID;
/*  47 */     this.attributeName = attributeName;
/*     */   }
/*     */   
/*     */   public void setParam(int attributeID, String type, String attributeName, int resourceid)
/*     */   {
/*  52 */     this.type = type;
/*  53 */     this.attributeID = attributeID;
/*  54 */     this.attributeName = attributeName;
/*  55 */     this.resourceid = resourceid;
/*     */   }
/*     */   
/*     */   public void setParam(int attributeID, String type, String attributeName, int resourceid, boolean customtype)
/*     */   {
/*  60 */     this.type = type;
/*  61 */     this.attributeID = attributeID;
/*  62 */     this.attributeName = attributeName;
/*  63 */     this.resourceid = resourceid;
/*  64 */     this.customtype = customtype;
/*     */   }
/*     */   
/*     */   public void setParam(int attributeID, String type, String attributeName, int resourceid, boolean customtype, String baseid) {
/*  68 */     this.type = type;
/*  69 */     this.attributeID = attributeID;
/*  70 */     this.attributeName = attributeName;
/*  71 */     this.resourceid = resourceid;
/*  72 */     this.customtype = customtype;
/*  73 */     this.baseid = baseid;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  79 */     Object toReturn = null;
/*     */     
/*  81 */     if (this.type.equals("Generic WMI"))
/*     */     {
/*     */ 
/*  84 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  85 */       ResultSet rs1 = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/*  90 */         long max_collectiontime = 0L;
/*  91 */         long oneHourBefore = 0L;
/*     */         try
/*     */         {
/*  94 */           String max_query = "select max(collectiontime) from AM_CAM_COLUMNAR_DATA where attributeid=" + this.attributeID + " and ROWID=" + this.resourceid;
/*  95 */           ResultSet rs2 = AMConnectionPool.executeQueryStmt(max_query);
/*     */           
/*  97 */           if (rs2.next())
/*     */           {
/*  99 */             max_collectiontime = rs2.getLong(1);
/* 100 */             oneHourBefore = max_collectiontime - 3600000L;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */           rs2.close();
/*     */         }
/*     */         catch (SQLException sqlexc)
/*     */         {
/* 111 */           sqlexc.printStackTrace();
/*     */         }
/*     */         catch (Exception nfe)
/*     */         {
/* 115 */           nfe.printStackTrace();
/*     */         }
/*     */         
/* 118 */         String query = null;
/*     */         
/* 120 */         query = "select VALUE,COLLECTIONTIME from AM_CAM_COLUMNAR_DATA where attributeid=" + this.attributeID + " and ROWID=" + this.resourceid + "  and value is not NULL";
/* 121 */         long collectiontime = 0L;
/* 122 */         double value = 0.0D;
/* 123 */         String strValue = "";
/*     */         
/* 125 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 126 */         TimeSeries ts = new TimeSeries(FormatUtil.getString(this.attributeName), Minute.class);
/* 127 */         while (rs1.next()) {
/*     */           try
/*     */           {
/* 130 */             strValue = rs1.getString(1);
/* 131 */             value = Double.parseDouble(strValue);
/* 132 */             collectiontime = rs1.getLong(2);
/* 133 */             ts.addOrUpdate(new Minute(new Date(collectiontime)), value);
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 137 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/* 141 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 142 */         toReturn = new SubSeriesDataset(col, 0);
/*     */       }
/*     */       catch (SQLException sqlexc)
/*     */       {
/* 146 */         sqlexc.printStackTrace();
/*     */       }
/*     */       catch (Exception exc2)
/*     */       {
/* 150 */         exc2.printStackTrace();
/*     */       }
/* 152 */       return toReturn;
/*     */     }
/* 154 */     if ((this.type.equals("Script Monitor")) || (this.customtype))
/*     */     {
/* 156 */       String toappend = "";
/* 157 */       if (this.customtype)
/*     */       {
/* 159 */         toappend = "_" + this.baseid;
/*     */       }
/* 161 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 162 */       ResultSet rs1 = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 167 */         long max_collectiontime = 0L;
/* 168 */         long oneHourBefore = 0L;
/*     */         try
/*     */         {
/* 171 */           String max_query = "select max(collectiontime) from AM_Script_Numeric_Data" + toappend + " where attributeid=" + this.attributeID + " and RESOURCEID=" + this.resourceid;
/* 172 */           ResultSet rs2 = AMConnectionPool.executeQueryStmt(max_query);
/*     */           
/* 174 */           if (rs2.next())
/*     */           {
/* 176 */             max_collectiontime = rs2.getLong(1);
/* 177 */             oneHourBefore = max_collectiontime - 3600000L; }
/*     */           long collectiontime;
/* 179 */           if (rs2.next())
/*     */           {
/* 181 */             collectiontime = rs1.getLong(1);
/*     */           }
/*     */           
/* 184 */           rs2.close();
/*     */         }
/*     */         catch (SQLException sqlexc)
/*     */         {
/* 188 */           sqlexc.printStackTrace();
/*     */         }
/*     */         catch (Exception nfe)
/*     */         {
/* 192 */           nfe.printStackTrace();
/*     */         }
/*     */         
/* 195 */         String query = null;
/*     */         
/*     */ 
/* 198 */         query = "select value,collectiontime from AM_Script_Numeric_Data" + toappend + " where attributeid=" + this.attributeID + " and RESOURCEID=" + this.resourceid + " and collectiontime >=" + oneHourBefore + " and value is not NULL";
/* 199 */         long collectiontime = 0L;
/* 200 */         double value = 0.0D;
/*     */         
/* 202 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 203 */         TimeSeries ts = new TimeSeries(FormatUtil.getString(this.attributeName), Minute.class);
/* 204 */         while (rs1.next())
/*     */         {
/* 206 */           value = rs1.getDouble(1);
/* 207 */           collectiontime = rs1.getLong(2);
/* 208 */           ts.addOrUpdate(new Minute(new Date(collectiontime)), value);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 213 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 214 */         toReturn = new SubSeriesDataset(col, 0);
/*     */       }
/*     */       catch (SQLException sqlexc)
/*     */       {
/* 218 */         sqlexc.printStackTrace();
/*     */       }
/*     */       catch (Exception exc2)
/*     */       {
/* 222 */         exc2.printStackTrace();
/*     */       }
/* 224 */       return toReturn;
/*     */     }
/*     */     
/* 227 */     if (this.type.equals("file"))
/*     */     {
/* 229 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 230 */       ResultSet rs1 = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 235 */         long max_collectiontime = 0L;
/* 236 */         long oneHourBefore = 0L;
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
/* 257 */         String query = null;
/* 258 */         query = "select SIZE,COLLECTIONTIME from AM_FILE where RESOURCEID=" + this.resourceid + " and SIZE >= 0 order by COLLECTIONTIME desc";
/* 259 */         double size = 0.0D;
/* 260 */         long collectiontime = 0L;
/* 261 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 262 */         TimeSeries ts = new TimeSeries(FormatUtil.getString(this.attributeName), Minute.class);
/* 263 */         while (rs1.next())
/*     */         {
/* 265 */           size = rs1.getDouble(1);
/* 266 */           collectiontime = rs1.getLong(2);
/* 267 */           ts.addOrUpdate(new Minute(new Date(collectiontime)), size);
/*     */         }
/*     */         
/* 270 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 271 */         toReturn = new SubSeriesDataset(col, 0);
/* 272 */         rs1.close();
/*     */       }
/*     */       catch (SQLException sqlexc)
/*     */       {
/* 276 */         sqlexc.printStackTrace();
/*     */       }
/*     */       catch (Exception exc2) {}
/*     */       
/*     */ 
/*     */ 
/* 282 */       return toReturn;
/*     */     }
/* 284 */     if (this.type.equals("directory"))
/*     */     {
/*     */ 
/* 287 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 288 */       ResultSet rs1 = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 293 */         long max_collectiontime = 0L;
/* 294 */         long oneHourBefore = 0L;
/* 295 */         String query = null;
/* 296 */         query = "select SIZE,COLLECTIONTIME from AM_DIR where RESOURCEID=" + this.resourceid + " and SIZE >= 0 order by COLLECTIONTIME desc";
/* 297 */         long collectiontime = 0L;
/* 298 */         double size = 0.0D;
/*     */         
/* 300 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 301 */         TimeSeries ts = new TimeSeries(FormatUtil.getString(this.attributeName), Minute.class);
/* 302 */         while (rs1.next())
/*     */         {
/* 304 */           size = rs1.getDouble(1);
/* 305 */           collectiontime = rs1.getLong(2);
/* 306 */           ts.addOrUpdate(new Minute(new Date(collectiontime)), size);
/*     */         }
/* 308 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 309 */         toReturn = new SubSeriesDataset(col, 0);
/* 310 */         rs1.close();
/*     */       }
/*     */       catch (SQLException sqlexc)
/*     */       {
/* 314 */         sqlexc.printStackTrace();
/*     */       }
/*     */       catch (Exception exc2) {}
/*     */       
/*     */ 
/*     */ 
/* 320 */       return toReturn;
/*     */     }
/* 322 */     if (this.type.equals("Ping Monitor"))
/*     */     {
/*     */ 
/* 325 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 326 */       ResultSet rs1 = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 331 */         long max_collectiontime = 0L;
/* 332 */         long oneHourBefore = 0L;
/* 333 */         String query = null;
/* 334 */         if (this.attributeName.equals("Round Trip Time")) {
/* 335 */           query = "select RTT,COLLECTIONTIME from AM_PINGDATA where RESOURCEID=" + this.resourceid + " order by COLLECTIONTIME desc";
/*     */         }
/*     */         else {
/* 338 */           query = "select PACKLOSS_PERCENT,COLLECTIONTIME from AM_PINGDATA where RESOURCEID=" + this.resourceid + " and PACKLOSS_PERCENT != -1 order by COLLECTIONTIME desc";
/*     */         }
/* 340 */         long collectiontime = 0L;
/* 341 */         double value = 0.0D;
/* 342 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/* 343 */         TimeSeries ts = new TimeSeries(FormatUtil.getString(this.attributeName), Minute.class);
/* 344 */         while (rs1.next())
/*     */         {
/* 346 */           value = rs1.getDouble(1);
/* 347 */           collectiontime = rs1.getLong(2);
/* 348 */           ts.addOrUpdate(new Minute(new Date(collectiontime)), value);
/*     */         }
/* 350 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 351 */         toReturn = new SubSeriesDataset(col, 0);
/* 352 */         rs1.close();
/*     */       }
/*     */       catch (SQLException sqlexc)
/*     */       {
/* 356 */         sqlexc.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 366 */     if (!this.type.equals("Ping Monitor"))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/* 372 */         Map attribInfo = CAMDBUtil.getAttributeInfo(this.attributeID);
/*     */         
/* 374 */         ResultSet rs = null;
/*     */         
/* 376 */         List attribValues = CAMDBUtil.getDataForNumericDataLineGraph(this.attributeID);
/* 377 */         boolean dataCollected = true;
/*     */         
/* 379 */         if (dataCollected) {
/* 380 */           TimeSeries ts = new TimeSeries(FormatUtil.getString((String)attribInfo.get("DISPLAYNAME")), Minute.class);
/* 381 */           int size = attribValues.size();
/* 382 */           Map row = null;
/* 383 */           for (int i = 0; i < size; i++) {
/* 384 */             row = (Map)attribValues.get(i);
/* 385 */             Date d = new Date(Long.parseLong((String)row.get("COLLECTIONTIME")));
/* 386 */             ts.addOrUpdate(new Minute(d), Long.parseLong((String)row.get("VALUE")));
/*     */           }
/* 388 */           TimeSeriesCollection col = new TimeSeriesCollection(ts);
/* 389 */           toReturn = new SubSeriesDataset(col, 0);
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 396 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 399 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 405 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 409 */     return "CAMDataProducer";
/*     */   }
/*     */   
/*     */   public static void main(String[] arg) throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 416 */       CAMGraphs cam = new CAMGraphs();
/* 417 */       cam.setParam(10003);
/* 418 */       AMConnectionPool cp = AMConnectionPool.getNewConnectionPool("jdbc:mysql://localhost/RMEDB", "root", "", "org.gjt.mm.mysql.Driver");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 423 */       cam.produceDataset(new HashMap());
/*     */     }
/*     */     catch (Exception ex) {
/* 426 */       System.err.println("Could not connect to the database. Message : " + ex.getMessage() + ". Exiting...");
/* 427 */       System.exit(1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\cam\beans\CAMGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */