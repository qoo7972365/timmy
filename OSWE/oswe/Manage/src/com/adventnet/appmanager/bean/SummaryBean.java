/*      */ package com.adventnet.appmanager.bean;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.awolf.data.DatasetProduceException;
/*      */ import com.adventnet.awolf.data.DatasetProducer;
/*      */ import java.io.Serializable;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import org.jfree.data.category.DefaultCategoryDataset;
/*      */ import org.jfree.data.general.SubSeriesDataset;
/*      */ import org.jfree.data.time.Hour;
/*      */ import org.jfree.data.time.Minute;
/*      */ import org.jfree.data.time.TimeSeries;
/*      */ import org.jfree.data.time.TimeSeriesCollection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SummaryBean
/*      */   implements DatasetProducer, Serializable
/*      */ {
/*   40 */   private String resourcename = "default";
/*      */   
/*   42 */   private String category = "default";
/*      */   
/*   44 */   private String entity = "default";
/*      */   
/*      */   private AMConnectionPool pool;
/*      */   
/*   48 */   private String resid = null;
/*      */   
/*   50 */   private String attributeid = null;
/*      */   
/*   52 */   private String Tattribute = null;
/*      */   
/*   54 */   private String starttime = null;
/*      */   
/*   56 */   private String endtime = null;
/*      */   
/*   58 */   private String dailyRptStarttime = null;
/*      */   
/*   60 */   private String dailyRptEndtime = null;
/*      */   
/*      */   private ResultSet rs;
/*      */   
/*   64 */   private boolean archivedforUrl = false;
/*      */   
/*   66 */   private boolean compareUrls = false;
/*      */   
/*   68 */   private boolean comparision = false;
/*      */   
/*   70 */   private boolean compareRows = false;
/*      */   
/*   72 */   private boolean tables = false;
/*      */   
/*   74 */   private ArrayList resids = null;
/*      */   
/*   76 */   private ArrayList attids = null;
/*      */   
/*   78 */   private String period = null;
/*      */   
/*   80 */   private String methodName = null;
/*      */   
/*   82 */   private String toappend = "";
/*      */   
/*   84 */   private Hashtable bhrDetails = new Hashtable();
/*      */   
/*   86 */   private Map urlvalues = null;
/*      */   
/*   88 */   private boolean urldata = false;
/*      */   
/*   90 */   private boolean isXYAreaChart = false;
/*      */   
/*   92 */   private ArrayList barData = null;
/*      */   
/*   94 */   private String unit = "";
/*      */   
/*   96 */   private boolean rbmScriptwise = false;
/*      */   
/*   98 */   private String tableName = null;
/*      */   
/*  100 */   LinkedHashMap<String, Object> timeseriesBHdata = null;
/*      */   
/*      */ 
/*      */   public SummaryBean()
/*      */   {
/*  105 */     this.pool = AMConnectionPool.getInstance();
/*      */   }
/*      */   
/*      */   public Object produceDataset(Map params)
/*      */     throws DatasetProduceException
/*      */   {
/*  111 */     ResultSet set = null;
/*  112 */     TimeSeries ts = null;
/*  113 */     ResultSet rst = null;
/*  114 */     String query = null;
/*  115 */     String dailyRptCondition = null;
/*  116 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(this.attributeid);
/*  117 */     String archivedTableName = this.attributeid != null ? (String)attribDetails.get(0) : "";
/*  118 */     String expression = this.attributeid != null ? (String)attribDetails.get(1) : "";
/*      */     
/*  120 */     if (this.barData == null)
/*      */     {
/*      */       try
/*      */       {
/*  124 */         ts = new TimeSeries(getEntity(), Hour.class);
/*      */         
/*      */ 
/*  127 */         if (isCompareUrls())
/*      */         {
/*  129 */           int h = 1;
/*      */           
/*  131 */           ResultSet rs = null;
/*  132 */           TimeSeriesCollection col = new TimeSeriesCollection();
/*  133 */           LinkedHashMap<String, Object> ctimeAttrvalueMap = this.timeseriesBHdata;
/*      */           
/*  135 */           if (ctimeAttrvalueMap == null) {
/*  136 */             ctimeAttrvalueMap = getBHfilterData();
/*      */           }
/*      */           
/*      */           try
/*      */           {
/*  141 */             for (String resId : ctimeAttrvalueMap.keySet())
/*      */             {
/*  143 */               ArrayList timeseriesData = (ArrayList)ctimeAttrvalueMap.get(resId);
/*  144 */               int datasize = timeseriesData.size();
/*  145 */               String dName = timeseriesData.get(0).toString();
/*  146 */               String temp = dName;
/*  147 */               if ((dName != null) && (dName.length() >= 45))
/*      */               {
/*  149 */                 temp = dName.substring(0, 20) + ".. .." + dName.substring(dName.length() - 20);
/*      */               }
/*  151 */               dName = temp;
/*  152 */               if (isRBMScriptWise())
/*      */               {
/*  154 */                 if ((this.attributeid.equals("8112")) || (this.attributeid.equals("8122")))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  161 */                   String qry = "select c.DISPLAYNAME from AM_PARENTCHILDMAPPER a,AM_RBMDATA b, AM_RBMAGENTDATA c where a. PARENTID=b.RESOURCEID and b.AGENTID=c.AGENTID and a.PARENTID=" + resId;
/*  162 */                   if (this.attributeid.equals("8122"))
/*      */                   {
/*  164 */                     qry = "select c.DISPLAYNAME from AM_PARENTCHILDMAPPER a,AM_RBMDATA b, AM_RBMAGENTDATA c where a. PARENTID=b.RESOURCEID and b.AGENTID=c.AGENTID and a.CHILDID=" + resId;
/*      */                   }
/*  166 */                   rst = AMConnectionPool.executeQueryStmt(qry);
/*  167 */                   if (rst.next())
/*      */                   {
/*  169 */                     dName = rst.getString(1);
/*      */                   }
/*      */                 }
/*      */               }
/*  173 */               dName = h + "." + dName;
/*  174 */               h++;
/*  175 */               TimeSeries ts1 = new TimeSeries(dName, Minute.class);
/*  176 */               int l = 0;
/*  177 */               long restime = 0L;
/*  178 */               long ctime = 0L;
/*  179 */               for (int x = 1; x < datasize; x++)
/*      */               {
/*  181 */                 ArrayList valueAndColtime = (ArrayList)timeseriesData.get(x);
/*  182 */                 restime = ((Long)valueAndColtime.get(0)).longValue();
/*  183 */                 ctime = ((Long)valueAndColtime.get(1)).longValue();
/*  184 */                 Date d = new Date(ctime);
/*      */                 try
/*      */                 {
/*  187 */                   if (restime != -1L)
/*      */                   {
/*  189 */                     l += 1;
/*  190 */                     if (("JVM".equalsIgnoreCase(getResourceName())) || (this.attributeid.indexOf("3608") != -1))
/*      */                     {
/*      */ 
/*  193 */                       restime /= 1048576L;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  200 */                     ts1.addOrUpdate(new Minute(d), restime);
/*      */                   }
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/*  205 */                   e.printStackTrace();
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  210 */               if (l == 1)
/*      */               {
/*  212 */                 Date d1 = new Date(ctime + 60000L);
/*  213 */                 ts1.addOrUpdate(new Minute(d1), restime);
/*      */               }
/*  215 */               if (ts1 != null)
/*      */               {
/*  217 */                 col.addSeries(ts1);
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */           catch (Exception es)
/*      */           {
/*  224 */             es.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*  228 */             if (rs != null)
/*      */             {
/*  230 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*  232 */             if (rst != null)
/*      */             {
/*  234 */               AMConnectionPool.closeStatement(rst);
/*      */             }
/*      */           }
/*  237 */           int[] x = new int[col.getSeriesCount()];
/*  238 */           for (int j = 0; j < col.getSeriesCount(); j++)
/*      */           {
/*  240 */             x[j] = j;
/*      */           }
/*  242 */           return new SubSeriesDataset(col, x);
/*      */         }
/*  244 */         if ((this.attributeid != null) && ((this.attributeid.equalsIgnoreCase("219")) || (this.attributeid.equalsIgnoreCase("319")) || (this.attributeid.equalsIgnoreCase("519")) || (this.attributeid.equalsIgnoreCase("235")) || (this.attributeid.equalsIgnoreCase("525")) || (this.attributeid.equalsIgnoreCase("35")) || (this.attributeid.equalsIgnoreCase("213")) || (this.attributeid.equalsIgnoreCase("508")) || (this.attributeid.equalsIgnoreCase("711")) || (this.attributeid.equalsIgnoreCase("2617")) || (this.attributeid.equalsIgnoreCase("2619"))))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  252 */           int k = 0;
/*  253 */           TimeSeriesCollection col = new TimeSeriesCollection();
/*      */           
/*      */ 
/*  256 */           query = "select  AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as Name,(TOTAL)/(TOTALCOUNT) as Average,ARCHIVEDTIME from " + archivedTableName + ",AM_ManagedObject,AM_PARENTCHILDMAPPER  where AM_PARENTCHILDMAPPER.PARENTID='" + this.resid + "' and " + archivedTableName + ".ATTRIBUTEID='" + this.attributeid + "' and  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  and  AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and  MINVALUE>=0 and MAXVALUE>=0 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' order by RESOURCEID";
/*      */           try
/*      */           {
/*  259 */             set = AMConnectionPool.executeQueryStmt(query);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  263 */             e.printStackTrace();
/*      */           }
/*      */           try
/*      */           {
/*  267 */             TimeSeries ts1 = null;
/*  268 */             int r1 = 0;
/*  269 */             while (set.next())
/*      */             {
/*  271 */               int resid = set.getInt("RESOURCEID");
/*  272 */               if (r1 != resid)
/*      */               {
/*  274 */                 r1 = resid;
/*  275 */                 if (ts1 != null)
/*      */                 {
/*  277 */                   col.addSeries(ts1);
/*      */                 }
/*  279 */                 String name = set.getString("Name");
/*  280 */                 ts1 = new TimeSeries(name, Hour.class);
/*  281 */                 k++;
/*      */               }
/*  283 */               long time = set.getLong(4);
/*  284 */               Date d = new Date(time);
/*      */               try
/*      */               {
/*  287 */                 if (set.getLong(3) != -1L) {
/*  288 */                   ts1.addOrUpdate(new Hour(d), set.getLong(3));
/*      */                 }
/*      */               }
/*      */               catch (Exception e) {
/*  292 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*  295 */             if (ts1 != null)
/*      */             {
/*  297 */               col.addSeries(ts1);
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  302 */             e.printStackTrace();
/*      */           }
/*  304 */           int[] x = new int[k];
/*  305 */           for (int j = 0; j < k; j++)
/*      */           {
/*  307 */             x[j] = j;
/*      */           }
/*  309 */           return new SubSeriesDataset(col, x);
/*      */         }
/*  311 */         if (isCompareRows())
/*      */         {
/*  313 */           int k = 0;
/*  314 */           ResultSet rs = null;
/*  315 */           String query2 = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.TYPE from AM_ManagedObject,AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID IN (" + this.resid + ") and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (" + this.attributeid + ")ORDER BY AM_ManagedObject.RESOURCEID";
/*  316 */           int h = 1;
/*  317 */           set = AMConnectionPool.executeQueryStmt(query2);
/*  318 */           TimeSeriesCollection col = new TimeSeriesCollection();
/*  319 */           if (isTables())
/*      */           {
/*  321 */             while (set.next())
/*      */             {
/*  323 */               String resID = set.getString("RESOURCEID");
/*  324 */               String dname = set.getString("DISPLAYNAME");
/*  325 */               String type = set.getString("TYPE");
/*  326 */               long sixHourBefore = System.currentTimeMillis() - 21600000L;
/*  327 */               if (isArchivedforUrl())
/*      */               {
/*      */ 
/*  330 */                 query = "select RESID,ATTRIBUTEID,ARCHIVEDTIME as COLLECTIONTIME,MINVALUE,MAXVALUE,(TOTAL/TOTALCOUNT) as RESPONSETIME from " + archivedTableName + " where RESID IN (" + resID + ") and ATTRIBUTEID IN (" + this.attributeid + ") and DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' order by ARCHIVEDTIME desc";
/*      */ 
/*      */ 
/*      */               }
/*  334 */               else if ((this.tableName != null) && (this.tableName.startsWith("AM_Script_Numeric_Data")))
/*      */               {
/*  336 */                 query = "select VALUE,COLLECTIONTIME from " + this.tableName + " where RESOURCEID=" + resID + " and ATTRIBUTEID=" + this.attributeid + " and collectiontime >=" + sixHourBefore + " and COLLECTIONTIME <=" + System.currentTimeMillis() + " and value is not NULL";
/*      */               }
/*      */               else
/*      */               {
/*  340 */                 query = "select VALUE,COLLECTIONTIME from AM_SCRIPT_TABULAR_NUMERIC_DATA" + this.toappend + " where RESID=" + resID + " and ATTRIBUTEID=" + this.attributeid + " and collectiontime >=" + sixHourBefore + " and COLLECTIONTIME <=" + System.currentTimeMillis() + " and value is not NULL";
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/*  347 */                 rs = AMConnectionPool.executeQueryStmt(query);
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*  351 */                 e.printStackTrace();
/*      */               }
/*      */               try
/*      */               {
/*  355 */                 String temp = null;
/*      */                 
/*  357 */                 if (type.indexOf("QueryMonitor") != -1)
/*      */                 {
/*  359 */                   temp = dname;
/*      */                 }
/*      */                 else
/*      */                 {
/*  363 */                   temp = FormatUtil.getString(dname);
/*      */                 }
/*  365 */                 if ((dname != null) && (dname.length() >= 45))
/*      */                 {
/*  367 */                   temp = dname.substring(0, 20) + ".. .." + dname.substring(dname.length() - 20);
/*      */                 }
/*  369 */                 dname = temp;
/*  370 */                 dname = h + "." + dname;
/*  371 */                 TimeSeries ts1 = new TimeSeries(dname, Minute.class);
/*  372 */                 int l = 0;
/*  373 */                 long coltime = 0L;
/*      */                 
/*  375 */                 double rtimed = 0.0D;
/*  376 */                 while (rs.next())
/*      */                 {
/*  378 */                   long ctime = rs.getLong("COLLECTIONTIME");
/*  379 */                   coltime = ctime;
/*  380 */                   Date d = new Date(ctime);
/*      */                   try
/*      */                   {
/*  383 */                     if (rs.getLong("VALUE") != -1L)
/*      */                     {
/*  385 */                       l += 1;
/*      */                       
/*  387 */                       rtimed = rs.getDouble("VALUE");
/*      */                       
/*      */ 
/*      */ 
/*  391 */                       ts1.addOrUpdate(new Minute(d), rs.getDouble("VALUE"));
/*      */                     }
/*      */                   }
/*      */                   catch (Exception e)
/*      */                   {
/*  396 */                     e.printStackTrace();
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*  401 */                 if (l == 1)
/*      */                 {
/*  403 */                   Date d1 = new Date(coltime + 60000L);
/*      */                   
/*  405 */                   ts1.addOrUpdate(new Minute(d1), rtimed);
/*      */                 }
/*  407 */                 if (ts1 != null)
/*      */                 {
/*  409 */                   col.addSeries(ts1);
/*      */                 }
/*  411 */                 k++;
/*  412 */                 h++;
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*  416 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  422 */           while (set.next())
/*      */           {
/*  424 */             String attributeid1 = set.getString("ATTRIBUTEID");
/*  425 */             String dname = FormatUtil.getString(set.getString(3));
/*  426 */             long sixHourBefore = System.currentTimeMillis() - 21600000L;
/*  427 */             if (isArchivedforUrl())
/*      */             {
/*      */ 
/*  430 */               query = "select RESID,ATTRIBUTEID,ARCHIVEDTIME as COLLECTIONTIME,MINVALUE" + expression + " as MINVALUE,MAXVALUE" + expression + " as MAXVALUE,(TOTAL/TOTALCOUNT)" + expression + " as RESPONSETIME from " + archivedTableName + " where RESID IN (" + this.resid + ") and ATTRIBUTEID IN (" + attributeid1 + ") and DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' order by ARCHIVEDTIME desc";
/*      */             }
/*      */             else
/*      */             {
/*  434 */               query = "select VALUE,COLLECTIONTIME from AM_Script_Numeric_Data" + this.toappend + " where RESOURCEID=" + this.resid + " and ATTRIBUTEID=" + attributeid1 + " and collectiontime >=" + sixHourBefore + " and COLLECTIONTIME <=" + System.currentTimeMillis() + " and value is not NULL";
/*      */             }
/*      */             try
/*      */             {
/*  438 */               rs = AMConnectionPool.executeQueryStmt(query);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  442 */               e.printStackTrace();
/*      */             }
/*      */             try
/*      */             {
/*  446 */               String temp = dname;
/*  447 */               if ((dname != null) && (dname.length() >= 45))
/*      */               {
/*  449 */                 temp = dname.substring(0, 20) + ".. .." + dname.substring(dname.length() - 20);
/*      */               }
/*  451 */               dname = temp;
/*  452 */               dname = h + "." + dname;
/*  453 */               TimeSeries ts1 = new TimeSeries(dname, Minute.class);
/*  454 */               int l = 0;
/*  455 */               long coltime = 0L;
/*  456 */               long rtime = 0L;
/*  457 */               while (rs.next())
/*      */               {
/*  459 */                 long ctime = rs.getLong("COLLECTIONTIME");
/*  460 */                 coltime = ctime;
/*  461 */                 Date d = new Date(ctime);
/*      */                 try
/*      */                 {
/*  464 */                   if (rs.getLong("VALUE") != -1L)
/*      */                   {
/*  466 */                     l += 1;
/*  467 */                     rtime = rs.getLong("VALUE");
/*  468 */                     ts1.addOrUpdate(new Minute(d), rs.getLong("VALUE"));
/*      */                   }
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/*  473 */                   e.printStackTrace();
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  478 */               if (l == 1)
/*      */               {
/*  480 */                 Date d1 = new Date(coltime + 60000L);
/*  481 */                 ts1.addOrUpdate(new Minute(d1), rtime);
/*      */               }
/*  483 */               if (ts1 != null)
/*      */               {
/*  485 */                 col.addSeries(ts1);
/*      */               }
/*  487 */               k++;
/*  488 */               h++;
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  492 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*  496 */           if (rs != null)
/*  497 */             AMConnectionPool.closeStatement(rs);
/*  498 */           int[] x = new int[k];
/*  499 */           for (int j = 0; j < k; j++)
/*      */           {
/*  501 */             x[j] = j;
/*      */           }
/*  503 */           return new SubSeriesDataset(col, x);
/*      */         }
/*  505 */         if (isUrldata())
/*      */         {
/*  507 */           int g = 0;
/*  508 */           TimeSeriesCollection col = new TimeSeriesCollection();
/*  509 */           Map m1 = getUrlvalues();
/*  510 */           Collection c = m1.keySet();
/*  511 */           Iterator itr = c.iterator();
/*  512 */           while (itr.hasNext())
/*      */           {
/*  514 */             String key = itr.next().toString();
/*  515 */             String dname = ReportDataUtilities.getLabelName(key);
/*  516 */             TimeSeries ts1 = new TimeSeries(dname, Minute.class);
/*  517 */             List a1 = (List)m1.get(key);
/*  518 */             for (int i = 0; i < a1.size(); i++)
/*      */             {
/*  520 */               String data1 = a1.get(i).toString();
/*  521 */               if (!data1.equals("-"))
/*      */               {
/*  523 */                 if (data1.indexOf("#") != -1)
/*      */                 {
/*  525 */                   String[] tempData = data1.split("#");
/*      */                   
/*  527 */                   Date d = new Date(Long.parseLong(tempData[0]));
/*  528 */                   ts1.addOrUpdate(new Minute(d), Double.parseDouble(tempData[1]));
/*      */                 }
/*      */               }
/*      */             }
/*  532 */             col.addSeries(ts1);
/*  533 */             g++;
/*      */           }
/*  535 */           int[] x = new int[g];
/*  536 */           for (int j = 0; j < g; j++)
/*      */           {
/*  538 */             x[j] = j;
/*      */           }
/*  540 */           return new SubSeriesDataset(col, x);
/*      */         }
/*  542 */         if (DBUtil.isEUMParent(this.resid))
/*      */         {
/*  544 */           Vector<String> agentIds = new Vector();
/*  545 */           ParentChildRelationalUtil.getAllChildMOs(agentIds, this.resid);
/*  546 */           TimeSeriesCollection col = new TimeSeriesCollection();
/*  547 */           dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "'";
/*  548 */           if (this.dailyRptStarttime != null)
/*      */           {
/*      */ 
/*  551 */             dailyRptCondition = " and (" + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' OR " + archivedTableName + ".DURATION=2 and ARCHIVEDTIME >='" + this.dailyRptStarttime + "' and ARCHIVEDTIME <='" + this.dailyRptEndtime + "')";
/*      */           }
/*  553 */           for (int i = 0; i < agentIds.size(); i++)
/*      */           {
/*      */ 
/*      */ 
/*  557 */             query = "select AM_ManagedObject.DISPLAYNAME as Name,((TOTAL)/(TOTALCOUNT)) " + expression + " as Average,ARCHIVEDTIME from " + archivedTableName + ",AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and " + archivedTableName + ".ATTRIBUTEID ='" + this.attributeid + "' and AM_ManagedObject.RESOURCEID='" + (String)agentIds.get(i) + "'  and MINVALUE>=0 and MAXVALUE>=0 " + dailyRptCondition;
/*  558 */             TimeSeries ts1 = null;
/*      */             try
/*      */             {
/*  561 */               set = AMConnectionPool.executeQueryStmt(query);
/*  562 */               while (set.next())
/*      */               {
/*  564 */                 if (ts1 == null)
/*      */                 {
/*  566 */                   ts1 = new TimeSeries(set.getString(1), Hour.class);
/*      */                 }
/*  568 */                 Date d = new Date(set.getLong(3));
/*      */                 try
/*      */                 {
/*  571 */                   if (set.getLong(2) != -1L)
/*      */                   {
/*  573 */                     ts1.addOrUpdate(new Hour(d), set.getLong(2));
/*      */                   }
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/*  578 */                   e.printStackTrace();
/*      */                 }
/*      */               }
/*  581 */               col.addSeries(ts1);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  585 */               e.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*  589 */               AMConnectionPool.closeResultSet(set);
/*      */             }
/*      */           }
/*  592 */           if (getXYAreaChart())
/*      */           {
/*  594 */             return col;
/*      */           }
/*  596 */           int[] x = new int[col.getSeriesCount()];
/*  597 */           for (int j = 0; j < col.getSeriesCount(); j++)
/*      */           {
/*  599 */             x[j] = j;
/*      */           }
/*  601 */           return new SubSeriesDataset(col, x);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  608 */         dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "'";
/*  609 */         if (this.dailyRptStarttime != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  615 */           dailyRptCondition = " and (" + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' OR " + archivedTableName + ".DURATION=2 and ARCHIVEDTIME >='" + this.dailyRptStarttime + "' and ARCHIVEDTIME <='" + this.dailyRptEndtime + "')";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  620 */         query = "select AM_ManagedObject.DISPLAYNAME as Name,((TOTAL)/(TOTALCOUNT)) " + expression + " as Average,ARCHIVEDTIME from " + archivedTableName + ",AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and " + archivedTableName + ".ATTRIBUTEID ='" + this.attributeid + "' and AM_ManagedObject.RESOURCEID='" + this.resid + "'  and MINVALUE>=0 and MAXVALUE>=0 " + dailyRptCondition;
/*  621 */         set = AMConnectionPool.executeQueryStmt(query);
/*  622 */         while (set.next())
/*      */         {
/*  624 */           Date d = new Date(set.getLong(3));
/*      */           try
/*      */           {
/*  627 */             if (set.getLong(2) != -1L) {
/*  628 */               ts.addOrUpdate(new Hour(d), set.getLong(2));
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  632 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  635 */         TimeSeriesCollection col = new TimeSeriesCollection(ts);
/*  636 */         if (getXYAreaChart())
/*      */         {
/*  638 */           return col;
/*      */         }
/*  640 */         return new SubSeriesDataset(col, 0);
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  645 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  651 */           if (set != null) {
/*  652 */             AMConnectionPool.closeStatement(set);
/*      */           }
/*      */         }
/*      */         catch (Exception es) {
/*  656 */           es.printStackTrace();
/*      */         }
/*      */       }
/*  659 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  663 */     DefaultCategoryDataset barset = new DefaultCategoryDataset();
/*      */     try
/*      */     {
/*  666 */       for (int i = 0; i < this.barData.size(); i++)
/*      */       {
/*  668 */         if (i >= 10) {
/*      */           break;
/*      */         }
/*      */         
/*  672 */         ArrayList rowData = (ArrayList)this.barData.get(i);
/*  673 */         float value = new Float(rowData.get(3).toString()).floatValue();
/*  674 */         if ((this.unit != null) && (this.unit.equals("MB")))
/*      */         {
/*  676 */           if ("501".equals(rowData.get(7).toString()))
/*      */           {
/*  678 */             value /= 1024.0F;
/*      */           }
/*      */           else
/*      */           {
/*  682 */             value /= 1048576.0F;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  688 */         DecimalFormatSymbols dfs = new DecimalFormatSymbols();
/*  689 */         dfs.setDecimalSeparator('.');
/*  690 */         DecimalFormat df = new DecimalFormat("#.##", dfs);
/*  691 */         value = Float.valueOf(df.format(value)).floatValue();
/*  692 */         int attID = Integer.parseInt(rowData.get(7).toString());
/*  693 */         String dispName = rowData.get(0).toString();
/*  694 */         if ((attID == 319) || (attID == 219) || (attID == 519) || (attID == 35) || (attID == 525) || (attID == 235) || (attID == 213) || (attID == 508) || (attID == 2619) || (attID == 2617))
/*      */         {
/*      */ 
/*  697 */           dispName = rowData.get(8).toString() + "_" + dispName;
/*      */         }
/*  699 */         else if ((attID == 2625) || (attID == 2626)) {
/*  700 */           String dname = ReportUtil.getDisplayNameForThridLevelAttribute(rowData.get(4).toString());
/*  701 */           dispName = dname + "_" + dispName;
/*      */         }
/*  703 */         else if (rowData.size() >= 9)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  713 */           dispName = rowData.get(8).toString();
/*      */         }
/*      */         
/*  716 */         barset.setValue(value, "", dispName + "_#$_resid=" + rowData.get(4).toString() + "#" + rowData.get(7).toString());
/*      */       }
/*  718 */       return barset;
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  722 */       es.printStackTrace();
/*      */     }
/*  724 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public LinkedHashMap<String, Object> getBHfilterData()
/*      */   {
/*  730 */     return getBHfilterData(false);
/*      */   }
/*      */   
/*      */   public String getQueryforAttribute(String attributeid, String resourceid)
/*      */   {
/*  735 */     String query = null;
/*  736 */     return query;
/*      */   }
/*      */   
/*      */   public boolean hasExpired(Map params, Date since) {
/*  740 */     return true;
/*      */   }
/*      */   
/*      */   public String getProducerId() {
/*  744 */     return "dummygraph";
/*      */   }
/*      */   
/*      */   public String getResourceName() {
/*  748 */     return this.resourcename;
/*      */   }
/*      */   
/*      */   public void setResourceName(String resource) {
/*  752 */     this.resourcename = resource;
/*      */   }
/*      */   
/*      */   public String getCategory() {
/*  756 */     return this.category;
/*      */   }
/*      */   
/*      */   public void setCategory(String cat) {
/*  760 */     this.category = cat;
/*      */   }
/*      */   
/*      */   public String getEntity() {
/*  764 */     return this.entity;
/*      */   }
/*      */   
/*      */   public void setEntity(String ent) {
/*  768 */     this.entity = ent;
/*      */   }
/*      */   
/*      */   public String getAttributeid() {
/*  772 */     return this.attributeid;
/*      */   }
/*      */   
/*      */   public void setAttributeid(String ent) {
/*  776 */     this.attributeid = ent;
/*      */   }
/*      */   
/*      */   public String getResid() {
/*  780 */     return this.resid;
/*      */   }
/*      */   
/*      */   public void setResid(String resid) {
/*  784 */     this.resid = resid;
/*      */   }
/*      */   
/*      */   public String getStarttime() {
/*  788 */     return this.starttime;
/*      */   }
/*      */   
/*      */   public void setStarttime(String starttime) {
/*  792 */     this.starttime = starttime;
/*      */   }
/*      */   
/*      */   public String getEndtime() {
/*  796 */     return this.endtime;
/*      */   }
/*      */   
/*      */   public void setEndtime(String endtime) {
/*  800 */     this.endtime = endtime;
/*      */   }
/*      */   
/*      */   public String getDailyRptStarttime() {
/*  804 */     return this.dailyRptStarttime;
/*      */   }
/*      */   
/*      */   public void setDailyRptStarttime(String dailyRptStarttime) {
/*  808 */     this.dailyRptStarttime = dailyRptStarttime;
/*      */   }
/*      */   
/*      */   public String getDailyRptEndtime() {
/*  812 */     return this.dailyRptEndtime;
/*      */   }
/*      */   
/*      */   public void setDailyRptEndtime(String dailyRptEndtime) {
/*  816 */     this.dailyRptEndtime = dailyRptEndtime;
/*      */   }
/*      */   
/*      */   public boolean isComparision() {
/*  820 */     return this.comparision;
/*      */   }
/*      */   
/*      */   public void setComparision(boolean comparision) {
/*  824 */     this.comparision = comparision;
/*      */   }
/*      */   
/*      */   public boolean isArchivedforUrl() {
/*  828 */     return this.archivedforUrl;
/*      */   }
/*      */   
/*      */   public void setArchivedforUrl(boolean archivedforUrl) {
/*  832 */     this.archivedforUrl = archivedforUrl;
/*      */   }
/*      */   
/*      */   public ArrayList getResids() {
/*  836 */     return this.resids;
/*      */   }
/*      */   
/*      */   public void setResids(ArrayList aresids) {
/*  840 */     this.resids = aresids;
/*      */   }
/*      */   
/*      */   public ArrayList getAttids() {
/*  844 */     return this.attids;
/*      */   }
/*      */   
/*      */   public void setAttids(ArrayList aattids) {
/*  848 */     this.attids = aattids;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isCompareUrls()
/*      */   {
/*  857 */     return this.compareUrls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCompareUrls(boolean compareUrls)
/*      */   {
/*  867 */     this.compareUrls = compareUrls;
/*      */   }
/*      */   
/*      */   public boolean isCompareRows() {
/*  871 */     return this.compareRows;
/*      */   }
/*      */   
/*      */   public void setCompareRows(boolean compareRows) {
/*  875 */     this.compareRows = compareRows;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRBMScriptWise()
/*      */   {
/*  884 */     return this.rbmScriptwise;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRBMScriptWise(boolean res)
/*      */   {
/*  894 */     this.rbmScriptwise = res;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPeriod()
/*      */   {
/*  903 */     return this.period;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPeriod(String period)
/*      */   {
/*  913 */     this.period = period;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMethodName()
/*      */   {
/*  922 */     return this.methodName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMethodName(String methodName)
/*      */   {
/*  932 */     this.methodName = methodName;
/*      */   }
/*      */   
/*      */   public void setToappend(String toappend) {
/*  936 */     this.toappend = toappend;
/*      */   }
/*      */   
/*      */   public String getToappend() {
/*  940 */     return this.toappend;
/*      */   }
/*      */   
/*      */   public boolean isTables() {
/*  944 */     return this.tables;
/*      */   }
/*      */   
/*      */   public void setTables(boolean tables) {
/*  948 */     this.tables = tables;
/*      */   }
/*      */   
/*      */   public void setBhrDetails(Hashtable bhrDetails) {
/*  952 */     this.bhrDetails = bhrDetails;
/*      */   }
/*      */   
/*      */   public boolean isUrldata() {
/*  956 */     return this.urldata;
/*      */   }
/*      */   
/*      */   public void setUrldata(boolean urldata) {
/*  960 */     this.urldata = urldata;
/*      */   }
/*      */   
/*      */   public Map getUrlvalues() {
/*  964 */     return this.urlvalues;
/*      */   }
/*      */   
/*      */   public void setUrlvalues(Map urlvalues) {
/*  968 */     this.urlvalues = urlvalues;
/*      */   }
/*      */   
/*      */   public void setXYAreaChart(boolean isXYAreaChart) {
/*  972 */     this.isXYAreaChart = isXYAreaChart;
/*      */   }
/*      */   
/*      */   public boolean getXYAreaChart() {
/*  976 */     return this.isXYAreaChart;
/*      */   }
/*      */   
/*      */   public void setBarData(ArrayList barData) {
/*  980 */     this.barData = barData;
/*      */   }
/*      */   
/*      */   public ArrayList getBarData() {
/*  984 */     return this.barData;
/*      */   }
/*      */   
/*      */   public void setUnit(String unit) {
/*  988 */     this.unit = unit;
/*      */   }
/*      */   
/*      */   public String getUnit() {
/*  992 */     return this.unit;
/*      */   }
/*      */   
/*      */   public String getTableName() {
/*  996 */     return this.tableName;
/*      */   }
/*      */   
/*      */   public void setTableName(String tableName) {
/* 1000 */     this.tableName = tableName;
/*      */   }
/*      */   
/*      */   public void setTimeSeriesData(LinkedHashMap<String, Object> timeseriesBHdata)
/*      */   {
/* 1005 */     this.timeseriesBHdata = timeseriesBHdata;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LinkedHashMap<String, Object> getBHfilterData(boolean minMaxAvgNeeded)
/*      */   {
/* 1021 */     String query = null;
/* 1022 */     String dailyRptCondition = null;
/* 1023 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(this.attributeid);
/* 1024 */     String archivedTableName = this.attributeid != null ? (String)attribDetails.get(0) : "";
/* 1025 */     String expression = this.attributeid != null ? (String)attribDetails.get(1) : "";
/* 1026 */     long sixHourBefore = System.currentTimeMillis() - 21600000L;
/* 1027 */     Vector resIdsVector = new Vector();
/* 1028 */     HashMap resIdDispNameMap = fetchResourcesForRpt(resIdsVector);
/* 1029 */     LinkedHashMap hashdata = new LinkedHashMap();
/*      */     
/*      */ 
/* 1032 */     if ((getPeriod() != null) && (getPeriod().equals("14")))
/*      */     {
/* 1034 */       if ((getCategory() != null) && (getCategory().equals("CPU Utilization")))
/*      */       {
/* 1036 */         query = "select AM_ManagedObject.RESOURCEID AS RESID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,sum(CPUUTIL)/count(*) as RESPONSETIME,FLOOR(COLLECTIONTIME/600000) as c ,((FLOOR(COLLECTIONTIME/600000))*600000) as COLLECTIONTIME,AM_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID from HostCpuMemDataCollected,AM_ManagedObject,AM_ATTRIBUTES  where AM_ManagedObject.RESOURCEID=HostCpuMemDataCollected.RESOURCEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE  and AM_ManagedObject.RESOURCEID IN (" + this.resid + ")  and AM_ATTRIBUTES.ATTRIBUTEID in (1357,1377,1457,1473,708,1394,1107,1207,1307,1657,1957,1937,807) and COLLECTIONTIME>=" + getStarttime() + " and COLLECTIONTIME<=" + getEndtime() + "  group by c,RESID order by RESID,COLLECTIONTIME desc";
/*      */       }
/* 1038 */       if ((getCategory() != null) && (getCategory().equals("Memory Usage")))
/*      */       {
/* 1040 */         query = "select AM_ManagedObject.RESOURCEID AS RESID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,sum(PHYMEMUTIL)/count(*) as RESPONSETIME,FLOOR(COLLECTIONTIME/600000) as c ,((FLOOR(COLLECTIONTIME/600000))*600000) as COLLECTIONTIME,AM_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID from HostCpuMemDataCollected,AM_ManagedObject,AM_ATTRIBUTES  where AM_ManagedObject.RESOURCEID=HostCpuMemDataCollected.RESOURCEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE  and AM_ManagedObject.RESOURCEID IN (" + this.resid + ")  and AM_ATTRIBUTES.ATTRIBUTEID in (1352,1372,1452,1472,702,1392,1102,1202,803,1302,1652,1952,1932) and COLLECTIONTIME>=" + getStarttime() + " and COLLECTIONTIME<=" + getEndtime() + "  group by c,RESID order by RESID,COLLECTIONTIME desc";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1045 */     else if (isArchivedforUrl())
/*      */     {
/* 1047 */       if (("generateHAResponseTimeReport".equals(getMethodName())) && ("AM_RESPONSETIME_MinMaxAvgData".equals(archivedTableName)))
/*      */       {
/* 1049 */         archivedTableName = "AM_TEMP_RESPONSETIME_MinMaxAvgData";
/*      */       }
/*      */       
/* 1052 */       dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "'";
/* 1053 */       if (this.dailyRptStarttime != null)
/*      */       {
/* 1055 */         dailyRptCondition = " and (" + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >='" + this.starttime + "' and ARCHIVEDTIME <='" + this.endtime + "' OR " + archivedTableName + ".DURATION=2 and ARCHIVEDTIME >='" + this.dailyRptStarttime + "' and ARCHIVEDTIME <='" + this.dailyRptEndtime + "')";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1062 */       query = "select RESID,ATTRIBUTEID,ARCHIVEDTIME as COLLECTIONTIME,case when ATTRIBUTEID = 501 then  (MINVALUE*1024) else (MINVALUE" + expression + ") end as MINVALUE,case when ATTRIBUTEID = 501 then  (MAXVALUE*1024) else (MAXVALUE" + expression + ") end as MAXVALUE,case when ATTRIBUTEID = 501 then  ((TOTAL/TOTALCOUNT)*1024) else (TOTAL/TOTALCOUNT)" + expression + " end as RESPONSETIME from " + archivedTableName + " where  " + DBUtil.getCondition(" RESID", resIdsVector) + "  and ATTRIBUTEID IN (" + this.attributeid + ") " + dailyRptCondition + " order by  RESID , ARCHIVEDTIME desc";
/*      */     }
/*      */     else
/*      */     {
/* 1066 */       query = "select RESID,RESPONSETIME,COLLECTIONTIME from AM_ManagedObjectData where COLLECTIONTIME >=" + sixHourBefore + " and COLLECTIONTIME <=" + System.currentTimeMillis() + " and " + DBUtil.getCondition(" RESID", resIdsVector) + " and RESPONSETIME<>-1 order by RESID , COLLECTIONTIME desc";
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1072 */       AMLog.debug("SummaryBean : Compare URL query : " + query);
/* 1073 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*      */ 
/* 1076 */       ArrayList singleResourceDetails = new ArrayList();
/* 1077 */       boolean forbHr = false;
/* 1078 */       if ((this.bhrDetails != null) && (this.bhrDetails.size() != 0))
/*      */       {
/* 1080 */         forbHr = true;
/*      */       }
/* 1082 */       Calendar cal = Calendar.getInstance();
/* 1083 */       Hashtable tNameHash = new Hashtable();
/* 1084 */       HashMap<String, ArrayList<Object>> minmaxdataAll = new HashMap();
/* 1085 */       while (this.rs.next())
/*      */       {
/* 1087 */         boolean isbHrdata = false;
/*      */         try
/*      */         {
/* 1090 */           if ((this.bhrDetails != null) && (this.bhrDetails.size() != 0))
/*      */           {
/* 1092 */             cal.setTimeInMillis(this.rs.getLong("COLLECTIONTIME"));
/* 1093 */             int weekday = cal.get(7) - 1;
/* 1094 */             int hour = cal.get(11);
/* 1095 */             int BHr_Start = ((Integer)this.bhrDetails.get(weekday + "_StHour")).intValue();
/* 1096 */             int BHr_End = ((Integer)this.bhrDetails.get(weekday + "_EndHour")).intValue();
/* 1097 */             if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*      */             {
/* 1099 */               isbHrdata = true;
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1105 */           AMLog.debug("############# Error in Bhour ############" + ex.toString());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1110 */         if ((!forbHr) || (isbHrdata))
/*      */         {
/* 1112 */           String resid = this.rs.getString("RESID");
/* 1113 */           long val = this.rs.getLong("RESPONSETIME");
/* 1114 */           long ctime = this.rs.getLong("COLLECTIONTIME");
/* 1115 */           long attridFrmDB = -1L;
/* 1116 */           if (isArchivedforUrl()) {
/* 1117 */             attridFrmDB = this.rs.getLong("ATTRIBUTEID");
/*      */           }
/* 1119 */           String hsname = resIdDispNameMap.get(resid).toString();
/* 1120 */           String TName = "";
/* 1121 */           if ((tNameHash != null) && (tNameHash.containsKey(resid)))
/*      */           {
/* 1123 */             TName = (String)tNameHash.get(resid);
/*      */           }
/*      */           else
/*      */           {
/* 1127 */             TName = DBUtil.getTypeName(Integer.parseInt(resid));
/* 1128 */             tNameHash.put(resid, TName);
/*      */           }
/* 1130 */           if ("JDBC".equalsIgnoreCase(TName))
/*      */           {
/* 1132 */             String PName = DBUtil.getDisplayName(Integer.parseInt(resid));
/* 1133 */             PName = FormatUtil.getTrimmedText(PName, 20);
/* 1134 */             hsname = PName + "-" + hsname;
/*      */           }
/* 1136 */           ArrayList valueAndColtime = new ArrayList();
/*      */           
/* 1138 */           if (!singleResourceDetails.contains(hsname))
/*      */           {
/* 1140 */             singleResourceDetails = new ArrayList();
/* 1141 */             singleResourceDetails.add(hsname);
/*      */           }
/* 1143 */           valueAndColtime.add(Long.valueOf(val));
/* 1144 */           valueAndColtime.add(Long.valueOf(ctime));
/* 1145 */           singleResourceDetails.add(valueAndColtime);
/*      */           
/* 1147 */           if (!hashdata.containsKey(resid))
/*      */           {
/* 1149 */             hashdata.put(resid, singleResourceDetails);
/* 1150 */             if (minMaxAvgNeeded) {
/* 1151 */               ArrayList minmaxdataIndividual = new ArrayList();
/* 1152 */               minmaxdataIndividual.add(hsname);
/* 1153 */               minmaxdataIndividual.add(Long.valueOf(val));
/* 1154 */               minmaxdataIndividual.add(Long.valueOf(val));
/* 1155 */               minmaxdataIndividual.add(Long.valueOf(val));
/* 1156 */               minmaxdataIndividual.add(resid);
/* 1157 */               minmaxdataIndividual.add(Long.valueOf(ctime));
/* 1158 */               minmaxdataIndividual.add(Long.valueOf(ctime));
/* 1159 */               if (isArchivedforUrl()) {
/* 1160 */                 minmaxdataIndividual.add(Long.valueOf(attridFrmDB));
/*      */               }
/* 1162 */               minmaxdataIndividual.add(Long.valueOf(1L));
/* 1163 */               minmaxdataAll.put(resid, minmaxdataIndividual);
/*      */             }
/* 1165 */           } else if (minMaxAvgNeeded)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1170 */             ArrayList<Object> tempRptValues = (ArrayList)minmaxdataAll.get(resid);
/* 1171 */             long minVal = ((Long)tempRptValues.get(1)).longValue();
/* 1172 */             long maxVal = ((Long)tempRptValues.get(2)).longValue();
/* 1173 */             long sum = ((Long)tempRptValues.get(3)).longValue();
/* 1174 */             long count = ((Long)tempRptValues.get(8)).longValue();
/* 1175 */             long minctime = ((Long)tempRptValues.get(5)).longValue();
/* 1176 */             long maxctime = ((Long)tempRptValues.get(6)).longValue();
/*      */             
/* 1178 */             if (val < minVal) {
/* 1179 */               tempRptValues.set(1, Long.valueOf(val));
/* 1180 */             } else if (val > maxVal) {
/* 1181 */               tempRptValues.set(2, Long.valueOf(val));
/*      */             }
/*      */             
/* 1184 */             if (ctime < minctime) {
/* 1185 */               tempRptValues.set(5, Long.valueOf(ctime));
/* 1186 */             } else if (ctime > maxctime) {
/* 1187 */               tempRptValues.set(6, Long.valueOf(ctime));
/*      */             }
/*      */             
/* 1190 */             tempRptValues.set(3, Long.valueOf(sum + val));
/* 1191 */             tempRptValues.set(8, Long.valueOf(++count));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1196 */       if (!minmaxdataAll.isEmpty())
/*      */       {
/* 1198 */         hashdata.put("MINMAXAVGDATA", minmaxdataAll);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1203 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1207 */     return hashdata;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap fetchResourcesForRpt(Vector resIdsVector)
/*      */   {
/* 1220 */     ResultSet set = null;
/* 1221 */     String resourceDetailsQry = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID IN (" + this.resid + ") and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (" + this.attributeid + ") ORDER BY AM_ManagedObject.RESOURCEID";
/* 1222 */     HashMap map = new HashMap();
/*      */     try {
/* 1224 */       set = AMConnectionPool.executeQueryStmt(resourceDetailsQry);
/*      */       
/* 1226 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/* 1227 */       while (set.next())
/*      */       {
/* 1229 */         String resID = set.getString("RESOURCEID");
/* 1230 */         String dname = set.getString("DISPLAYNAME");
/* 1231 */         if ((this.attributeid != null) && (this.attributeid.indexOf("711") != -1))
/*      */         {
/* 1233 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 1234 */           dname = FormatUtil.findReplace(dname, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 1235 */           String[] temp1 = dname.split(":");
/* 1236 */           if ((temp1[0] != null) && (alldisplayname.get(temp1[0]) != null))
/*      */           {
/* 1238 */             String s1 = alldisplayname.get(temp1[0]).toString();
/* 1239 */             if ((s1 != null) && (temp1.length > 1))
/*      */             {
/* 1241 */               dname = s1 + ":" + temp1[1];
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1246 */             AMLog.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DISK UTILIZATION: NULL in DBUtil.getDisplayNameForDisk() &&&&&&&&&&&&");
/* 1247 */             AMLog.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& DBUtil.getDisplayNameForDisk() -------->" + alldisplayname);
/* 1248 */             AMLog.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& NULL for Key : temp1[0] -------->" + temp1[0]);
/*      */           }
/*      */         }
/* 1251 */         if (this.attributeid.indexOf(",") != -1)
/*      */         {
/* 1253 */           String[] TempAttributeid = this.attributeid.split(",");
/* 1254 */           this.Tattribute = TempAttributeid[0];
/*      */         }
/*      */         else
/*      */         {
/* 1258 */           this.Tattribute = this.attributeid;
/*      */         }
/* 1260 */         if (allSecondLevelAttribute.contains(this.Tattribute))
/*      */         {
/* 1262 */           String displayname = ReportUtil.getDisplayNameForAttribute(Integer.parseInt(resID));
/* 1263 */           dname = displayname + "_" + dname;
/*      */         }
/* 1265 */         map.put(resID, dname);
/* 1266 */         resIdsVector.add(resID);
/*      */       }
/*      */     } catch (Exception exc) {
/* 1269 */       exc.printStackTrace();
/*      */     } finally {
/* 1271 */       if (set != null) {
/* 1272 */         AMConnectionPool.closeResultSet(set);
/*      */       }
/*      */     }
/* 1275 */     return map;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\SummaryBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */