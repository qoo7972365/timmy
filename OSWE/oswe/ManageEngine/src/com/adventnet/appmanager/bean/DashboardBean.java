/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.util.AppManagerUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
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
/*     */ 
/*     */ public class DashboardBean
/*     */   implements DatasetProducer
/*     */ {
/*     */   public static final int LINE = 1;
/*     */   public static final int BAR = 2;
/*     */   public static final int HIGH = 1;
/*     */   public static final int LOW = 2;
/*     */   private String attributeid;
/*     */   private String role;
/*     */   private String owner;
/*     */   private int charttype;
/*     */   private int criticalcriteria;
/*  46 */   private String attrDisplayName = "";
/*     */   
/*  48 */   Object data = null;
/*  49 */   boolean isDataPresent = false;
/*     */   
/*  51 */   PreparedStatement displayNameQuery = null;
/*  52 */   PreparedStatement displayNameQuery1 = null;
/*     */   
/*     */   public DashboardBean() {
/*  55 */     try { this.displayNameQuery = AMConnectionPool.getConnection().prepareStatement("select displayname from AM_ManagedObject where resourceid=?");
/*  56 */       this.displayNameQuery1 = AMConnectionPool.getConnection().prepareStatement("select displayname from AM_ManagedObject where RESOURCENAME=?");
/*  57 */     } catch (Exception exc) { exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*  61 */   public void setAttrDisplayName(String attrDisplayName) { this.attrDisplayName = attrDisplayName; }
/*     */   
/*     */   public String getAttrDisplayName()
/*     */   {
/*  65 */     return this.attrDisplayName;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAttribute(String attrid, int charttype, int criticalcriteria, String own, String rol)
/*     */   {
/*  71 */     this.owner = own;
/*  72 */     this.role = rol;
/*  73 */     this.attributeid = attrid;
/*  74 */     this.charttype = charttype;
/*  75 */     this.criticalcriteria = criticalcriteria;
/*  76 */     String query = "";
/*     */     
/*  78 */     if (this.attributeid.indexOf("|") != -1) {
/*  79 */       String attribid = this.attributeid;
/*  80 */       attribid = this.attributeid.substring(0, this.attributeid.indexOf("|"));
/*  81 */       query = "select a.DISPLAYNAME from AM_ATTRIBUTES a ,AM_ATTRIBUTES_EXT ae where a.ATTRIBUTEID=" + attribid + " and a.ATTRIBUTEID=ae.ATTRIBUTEID";
/*     */     }
/*     */     else {
/*  84 */       query = "select a.DISPLAYNAME from AM_ATTRIBUTES a ,AM_ATTRIBUTES_EXT ae where a.ATTRIBUTEID=" + this.attributeid + " and a.ATTRIBUTEID=ae.ATTRIBUTEID";
/*     */     }
/*  86 */     ResultSet rs = null;
/*     */     try {
/*  88 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  89 */       if (rs.next()) {
/*  90 */         setAttrDisplayName(rs.getString("DISPLAYNAME"));
/*     */       }
/*  92 */       closeResultSet(rs);
/*     */     } catch (Exception e) {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  99 */   public boolean isDataPresent() { return isDataPresent(null); }
/*     */   
/*     */   public boolean isDataPresent(HttpServletRequest request) {
/* 102 */     Object obj = null;
/* 103 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 105 */       obj = getGraphObject(new HashMap(), request);
/*     */     }
/*     */     else
/*     */     {
/* 109 */       obj = getGraphObject(new HashMap());
/*     */     }
/* 111 */     this.data = obj;
/* 112 */     return this.isDataPresent;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) {
/* 116 */     return this.data;
/*     */   }
/*     */   
/*     */ 
/* 120 */   public Object getGraphObject(Map map) { return getGraphObject(map, null); }
/*     */   
/*     */   public Object getGraphObject(Map map, HttpServletRequest request) {
/*     */     try {
/* 124 */       String attribid = this.attributeid;
/* 125 */       if (this.attributeid.indexOf("|") != -1) {
/* 126 */         attribid = this.attributeid.substring(0, this.attributeid.indexOf("|"));
/*     */       }
/*     */       
/* 129 */       String query = "select DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,a.RESOURCETYPE,EXPRESSION from AM_ATTRIBUTES a ,AM_ATTRIBUTES_EXT ae where a.ATTRIBUTEID=" + attribid + " and a.ATTRIBUTEID=ae.ATTRIBUTEID";
/* 130 */       System.out.println("QUERY 000000012 " + query);
/* 131 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 132 */       if (rs.next()) {
/* 133 */         String tablename = rs.getString("DATATABLE");
/* 134 */         String resid = rs.getString("RESID_COL");
/* 135 */         String colname = rs.getString("VALUE_COL");
/* 136 */         String coltime = rs.getString("COLTIME_VAL");
/* 137 */         String type = rs.getString("RESOURCETYPE");
/* 138 */         String attribCol = rs.getString("ATTID_COL");
/* 139 */         String expression = rs.getString("EXPRESSION");
/* 140 */         String colnameAlias = rs.getString("VALUE_COL");
/* 141 */         colname = DBQueryUtil.escapeColumn(colname, attribid);
/* 142 */         if (this.charttype == 1)
/*     */         {
/* 144 */           long curtime = System.currentTimeMillis();
/* 145 */           long beforesixhours = curtime - 21600000L;
/*     */           
/* 147 */           ArrayList topfive = null;
/* 148 */           if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 150 */             topfive = getTop5Resource(tablename, colname, coltime, resid, type, this.charttype, this.criticalcriteria, attribCol, attribid, request);
/*     */           }
/*     */           else
/*     */           {
/* 154 */             topfive = getTop5Resource(tablename, colname, coltime, resid, type, this.charttype, this.criticalcriteria, attribCol, attribid);
/*     */           }
/* 156 */           String condition = getCondition(resid, topfive);
/* 157 */           if (!condition.equals("")) {
/* 158 */             condition = condition + " and ";
/*     */           }
/* 160 */           query = "select " + resid + "," + colname + expression + " as " + colnameAlias + "," + coltime + " from " + tablename + " where " + colname + ">=0 and " + condition + coltime + ">" + beforesixhours + " and " + coltime + "<=" + curtime + " order by " + resid + "," + coltime;
/* 161 */           if (!"-1".equals(attribCol))
/*     */           {
/* 163 */             query = "select " + resid + "," + colname + expression + " as " + colnameAlias + "," + coltime + " from " + tablename + " where " + colname + ">=0 and " + attribCol + "=" + attribid + " and " + condition + coltime + ">" + beforesixhours + " and " + coltime + "<=" + curtime + " order by " + resid + "," + coltime;
/*     */           }
/*     */           
/* 166 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/* 167 */           HashMap tmap = new HashMap();
/* 168 */           String resourceid = "";
/* 169 */           String dispname = "";
/*     */           
/*     */ 
/* 172 */           boolean bool = false;
/* 173 */           while (rs1.next())
/*     */           {
/* 175 */             if (!condition.equals(""))
/*     */             {
/* 177 */               bool = true;
/*     */             }
/* 179 */             double value = rs1.getDouble(colnameAlias);
/* 180 */             String rid = rs1.getString(resid);
/* 181 */             long ctime = rs1.getLong(coltime);
/*     */             
/* 183 */             if (!resourceid.equals(rid))
/*     */             {
/* 185 */               if (resid.equals("RESOURCENAME"))
/*     */               {
/* 187 */                 dispname = getDisplayName(rid, 1);
/*     */               }
/*     */               else
/*     */               {
/* 191 */                 dispname = getDisplayName(rid, 0);
/*     */               }
/* 193 */               resourceid = rid;
/*     */             }
/*     */             
/* 196 */             int lengthOfTrimmedString = 20;
/* 197 */             lengthOfTrimmedString = AppManagerUtil.getWebclientDisplayLength(lengthOfTrimmedString);
/*     */             
/* 199 */             if (dispname.length() > lengthOfTrimmedString)
/*     */             {
/* 201 */               dispname = dispname.substring(0, lengthOfTrimmedString) + "...";
/*     */             }
/* 203 */             TimeSeries t = null;
/* 204 */             t = (TimeSeries)tmap.get(rid);
/* 205 */             if (t == null) {
/* 206 */               t = new TimeSeries(dispname, Minute.class);
/* 207 */               tmap.put(rid, t);
/*     */             }
/* 209 */             t.addOrUpdate(new Minute(new Date(ctime)), value);
/*     */           }
/*     */           
/* 212 */           this.isDataPresent = bool;
/*     */           
/*     */ 
/* 215 */           closeResultSet(rs1);
/*     */           
/* 217 */           int[] index = new int[tmap.size()];
/* 218 */           TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 219 */           Iterator iter = tmap.keySet().iterator();
/* 220 */           int i = 0;
/* 221 */           while (iter.hasNext()) {
/* 222 */             tsc.addSeries((TimeSeries)tmap.get((String)iter.next()));
/* 223 */             index[i] = i;
/* 224 */             i++;
/*     */           }
/* 226 */           return new SubSeriesDataset(tsc, index);
/*     */         }
/*     */         
/* 229 */         if (this.charttype == 2) {
/* 230 */           long curtime = System.currentTimeMillis();
/* 231 */           long beforeonehour = curtime - 3600000L;
/*     */           
/* 233 */           String orderby = "DESC";
/* 234 */           if (this.criticalcriteria == 2) {
/* 235 */             orderby = "ASC";
/*     */           }
/*     */           
/* 238 */           String restypes = getResourceTypes(this.attributeid);
/* 239 */           if ((!this.role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */           {
/* 241 */             query = "select mo.displayname," + tablename + "." + resid + ",AVG(" + colname + ") as avr from " + tablename + ",AM_ManagedObject mo where " + tablename + "." + resid + " in (mo.RESOURCEID,mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colname + " >=0 and " + coltime + ">" + beforeonehour + " and " + coltime + "<=" + curtime + " group by " + resid + " order by avr " + orderby;
/* 242 */             if (!"-1".equals(attribCol))
/*     */             {
/* 244 */               query = "select mo.displayname," + tablename + "." + resid + ",AVG(" + colname + ") as avr from " + tablename + ",AM_ManagedObject mo where " + tablename + "." + resid + " in (mo.RESOURCEID,mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colname + " >=0 and " + attribCol + "=" + attribid + " and " + coltime + ">" + beforeonehour + " and " + coltime + "<=" + curtime + " group by " + resid + " order by avr " + orderby;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 249 */             String condition = "";
/* 250 */             if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */             {
/* 252 */               Vector resVector = EnterpriseUtil.filterResourceIds(request);
/* 253 */               condition = EnterpriseUtil.getCondition("mo.RESOURCEID", resVector);
/*     */             }
/*     */             else
/*     */             {
/* 257 */               condition = Constants.getQueryCondition("mo.RESOURCEID", this.owner);
/*     */             }
/* 259 */             query = "select mo.displayname," + tablename + "." + resid + ",AVG(" + colname + ") as avr from " + tablename + ",AM_ManagedObject mo where " + tablename + "." + resid + " in (mo.RESOURCEID,mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colname + " >=0 and " + coltime + ">" + beforeonehour + " and " + coltime + "<=" + curtime + " and " + condition + " group by " + resid + " order by avr " + orderby;
/* 260 */             if (!"-1".equals(attribCol))
/*     */             {
/* 262 */               query = "select mo.displayname," + tablename + "." + resid + ",AVG(" + colname + ") as avr from " + tablename + ",AM_ManagedObject mo where " + tablename + "." + resid + " in (mo.RESOURCEID,mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colname + " >=0 and " + attribCol + "=" + attribid + " and " + coltime + ">" + beforeonehour + " and " + coltime + "<=" + curtime + " and " + condition + " group by " + resid + " order by avr " + orderby;
/*     */             }
/*     */           }
/* 265 */           query = DBQueryUtil.getTopNValues(query, 5);
/* 266 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/* 267 */           DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 268 */           boolean bool = false;
/* 269 */           while (rs1.next()) {
/* 270 */             bool = true;
/* 271 */             barset.setValue((int)rs1.getDouble("avr"), "", rs1.getString("displayname"));
/*     */           }
/*     */           
/* 274 */           this.isDataPresent = bool;
/* 275 */           closeResultSet(rs1);
/*     */           
/* 277 */           return barset;
/*     */         }
/*     */       }
/* 280 */       rs.close();
/*     */     } catch (Exception e) {
/* 282 */       e.printStackTrace();
/*     */     }
/* 284 */     return null;
/*     */   }
/*     */   
/*     */ 
/* 288 */   private ArrayList getTop5Resource(String tablenamecol, String colnamecol, String coltimecol, String residcol, String montype, int charttype, int criticalcriteria, String attribCol, String attribid) { return getTop5Resource(tablenamecol, colnamecol, coltimecol, residcol, montype, charttype, criticalcriteria, attribCol, attribid, null); }
/*     */   
/*     */   private ArrayList getTop5Resource(String tablenamecol, String colnamecol, String coltimecol, String residcol, String montype, int charttype, int criticalcriteria, String attribCol, String attribid, HttpServletRequest request) {
/* 291 */     long end = System.currentTimeMillis();
/* 292 */     long start = 0L;
/* 293 */     if (charttype == 1) {
/* 294 */       start = end - 21600000L;
/*     */     }
/*     */     else {
/* 297 */       start = end - 3600000L;
/*     */     }
/* 299 */     ArrayList toret = new ArrayList();
/* 300 */     String orderby = "DESC";
/* 301 */     if (criticalcriteria == 2) {
/* 302 */       orderby = "ASC";
/*     */     }
/* 304 */     ResultSet rs = null;
/* 305 */     String query = "";
/* 306 */     colnamecol = DBQueryUtil.escapeColumn(colnamecol, attribid);
/* 307 */     String restypes = getResourceTypes(this.attributeid);
/* 308 */     if ((!this.role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 310 */       if (residcol.equalsIgnoreCase("RESOURCENAME"))
/*     */       {
/* 312 */         query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/* 313 */         if (!"-1".equals(attribCol))
/*     */         {
/* 315 */           query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + attribCol + "=" + attribid + " and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 320 */         query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCEID) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/* 321 */         if (!"-1".equals(attribCol))
/*     */         {
/* 323 */           query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCEID) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + attribCol + "=" + attribid + " and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 328 */       String condition = "";
/* 329 */       if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */       {
/* 331 */         Vector resVector = EnterpriseUtil.filterResourceIds(request);
/* 332 */         condition = EnterpriseUtil.getCondition("mo.RESOURCEID", resVector);
/*     */       }
/*     */       else
/*     */       {
/* 336 */         condition = Constants.getQueryCondition("mo.RESOURCEID", this.owner);
/*     */       }
/*     */       
/* 339 */       if (residcol.equalsIgnoreCase("RESOURCENAME"))
/*     */       {
/* 341 */         query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " and " + condition + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/* 342 */         if (!"-1".equals(attribCol))
/*     */         {
/* 344 */           query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCENAME) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + attribCol + "=" + attribid + " and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " and " + condition + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 349 */         query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCEID) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " and " + condition + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/* 350 */         if (!"-1".equals(attribCol))
/*     */         {
/* 352 */           query = "select " + tablenamecol + "." + residcol + ",AVG(" + colnamecol + ") as avr from " + tablenamecol + ",AM_ManagedObject mo where " + tablenamecol + "." + residcol + " in (mo.RESOURCEID) and mo.type in (" + restypes + ") and " + colnamecol + ">=0 and " + attribCol + "=" + attribid + " and " + coltimecol + ">" + start + " and " + coltimecol + "<=" + end + " and " + condition + " group by " + tablenamecol + "." + residcol + " order by avr " + orderby;
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 357 */       query = DBQueryUtil.getTopNValues(query, 5);
/* 358 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 359 */       while (rs.next()) {
/* 360 */         toret.add(rs.getString(residcol));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 364 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 367 */       closeResultSet(rs);
/*     */     }
/*     */     
/* 370 */     return toret;
/*     */   }
/*     */   
/*     */ 
/*     */   private String getResourceTypes(String attributeid)
/*     */   {
/* 376 */     StringTokenizer tok = new StringTokenizer(attributeid, "|");
/* 377 */     boolean first = true;
/* 378 */     String attrid = "";
/* 379 */     while (tok.hasMoreTokens()) {
/* 380 */       String t = tok.nextToken();
/* 381 */       if (first) {
/* 382 */         attrid = attrid + t;
/* 383 */         first = false;
/*     */       }
/*     */       else {
/* 386 */         attrid = attrid + "," + t;
/*     */       }
/*     */     }
/*     */     
/* 390 */     String q = "select resourcetype from AM_ATTRIBUTES where attributeid in (" + attrid + ")";
/* 391 */     ResultSet rs = null;
/*     */     try {
/* 393 */       rs = AMConnectionPool.executeQueryStmt(q);
/* 394 */       first = true;
/* 395 */       String allrestype = "";
/* 396 */       while (rs.next()) {
/* 397 */         String temp = rs.getString("resourcetype");
/* 398 */         if (first) {
/* 399 */           allrestype = allrestype + "'" + temp + "'";
/* 400 */           first = false;
/*     */         }
/*     */         else {
/* 403 */           allrestype = allrestype + "," + "'" + temp + "'";
/*     */         }
/*     */       }
/* 406 */       rs.close();
/* 407 */       return allrestype;
/*     */     } catch (Exception e) {
/* 409 */       e.printStackTrace();
/*     */     }
/* 411 */     return null;
/*     */   }
/*     */   
/*     */   private String getDisplayName(String resid, int check)
/*     */   {
/* 416 */     ResultSet rs = null;
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 422 */       if (check == 0)
/*     */       {
/* 424 */         this.displayNameQuery.setLong(1, Long.valueOf(resid).longValue());
/* 425 */         rs = this.displayNameQuery.executeQuery();
/*     */       }
/*     */       else
/*     */       {
/* 429 */         this.displayNameQuery1.setString(1, resid);
/* 430 */         rs = this.displayNameQuery1.executeQuery();
/*     */       }
/* 432 */       if (rs.next()) {
/* 433 */         return rs.getString("displayname");
/*     */       }
/*     */     } catch (Exception e) {
/* 436 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 439 */       closeResultSet(rs);
/*     */     }
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
/* 452 */     return "Unknown";
/*     */   }
/*     */   
/*     */   public void finalize()
/*     */   {
/*     */     try {
/* 458 */       super.finalize();
/* 459 */       this.displayNameQuery.close();
/* 460 */       this.displayNameQuery1.close();
/* 461 */     } catch (Throwable exc) { exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/* 465 */   private String getCondition(String colname, ArrayList resids) { StringBuffer buf = new StringBuffer("");
/* 466 */     int size = resids.size();
/* 467 */     if (size > 0) {
/* 468 */       buf.append(colname + " IN ('");
/* 469 */       for (int i = 0; i < size; i++) {
/* 470 */         String resid = (String)resids.get(i);
/* 471 */         if (i == 0) {
/* 472 */           buf.append(resid);
/*     */         }
/*     */         else {
/* 475 */           buf.append("','" + resid);
/*     */         }
/*     */       }
/* 478 */       buf.append("')");
/*     */     }
/* 480 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs) {
/* 484 */     if (rs != null) {
/*     */       try {
/* 486 */         rs.close();
/*     */       } catch (Exception e) {
/* 488 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\DashboardBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */