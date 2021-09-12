/*     */ package com.adventnet.appmanager.oracle.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OracleBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  28 */   private String resourcename = "default";
/*  29 */   private long maxcollectiontime = 0L;
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*  32 */   Properties sharedsgaproperties = new Properties();
/*  33 */   Properties sgaext = new Properties();
/*  34 */   private ManagedApplication mo = new ManagedApplication();
/*  35 */   private static Properties oracleUITableDetails = new Properties();
/*  36 */   String parameter = null;
/*     */   
/*  38 */   public OracleBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/*  42 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/*  46 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public void setParameter(String parameter, String name)
/*     */   {
/*  51 */     this.parameter = parameter;
/*  52 */     this.resourcename = name;
/*     */   }
/*     */   
/*  55 */   public void setmaxcollectiontime(String resourcename) { String query = "select max(COLLECTIONTIME) from InstanceStatus where RESOURCENAME='" + resourcename + "'";
/*     */     try {
/*  57 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  58 */       if (this.rs.next()) {
/*  59 */         this.maxcollectiontime = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  64 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/*  67 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getInstanceDetails(String resourcename)
/*     */   {
/*  74 */     String query = "select VERSION,STARTUPTIME,CREATED,OPENMODE,LOGMODE from InstanceDetails where RESOURCENAME ='" + resourcename + "'";
/*  75 */     Properties p = new Properties();
/*     */     try {
/*  77 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  78 */       if (this.rs.next()) {
/*  79 */         p.setProperty("VERSION", this.rs.getString("VERSION"));
/*  80 */         p.setProperty("STARTUPTIME", this.rs.getString("STARTUPTIME"));
/*  81 */         p.setProperty("CREATED", this.rs.getString("CREATED"));
/*  82 */         p.setProperty("OPENMODE", this.rs.getString("OPENMODE"));
/*  83 */         p.setProperty("LOGMODE", this.rs.getString("LOGMODE"));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  88 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     }
/*     */     
/*  94 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getInstanceStatus(String resourcename) {
/*  98 */     Properties p = new Properties();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 107 */       String oldinstancestatus1 = "select DBSIZE , AVGEXECS , AVGUSERS ,READS , WRITES , BLOCKSIZE from InstanceStatus where RESOURCENAME = '" + resourcename + "' and COLLECTIONTIME = " + this.maxcollectiontime;
/*     */       
/*     */ 
/* 110 */       String instancestatus1 = "select * from InstanceStatus where RESOURCENAME = '" + resourcename + "' and COLLECTIONTIME = " + this.maxcollectiontime;
/*     */       
/*     */ 
/*     */ 
/* 114 */       FormatUtil.printQueryChange("OracleBean.java", oldinstancestatus1, instancestatus1);
/*     */       
/* 116 */       this.rs = AMConnectionPool.executeQueryStmt(instancestatus1);
/* 117 */       if (this.rs.next()) {
/* 118 */         p.setProperty("DBSIZE", String.valueOf(this.rs.getLong("DBSIZE")));
/* 119 */         p.setProperty("AVGEXECS", String.valueOf(this.rs.getLong("AVGEXECS")));
/* 120 */         p.setProperty("AVGUSERS", String.valueOf(this.rs.getLong("AVGUSERS")));
/* 121 */         p.setProperty("READS", String.valueOf(this.rs.getLong("NOOFREADS")));
/* 122 */         p.setProperty("WRITES", String.valueOf(this.rs.getLong("WRITES")));
/* 123 */         p.setProperty("BLOCKSIZE", String.valueOf(this.rs.getLong("BLOCKSIZE")));
/*     */       }
/* 125 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/* 128 */       e.printStackTrace();
/*     */     }
/* 130 */     return p;
/*     */   }
/*     */   
/*     */   public ArrayList getTableSpaceDetails(String resourcename) {
/* 134 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 137 */       String tsd = "select TABLESPACENAME , ALLOCATEDBYTES , ALLOCATEDBLOCKS , DATAFILES from TableSpaceDetails where RESOURCENAME = '" + resourcename + "' and COLLECTIONTIME = " + this.maxcollectiontime + " order by TABLESPACENAME";
/* 138 */       a = this.mo.getPropertiesList(tsd);
/*     */     }
/*     */     catch (Exception e) {
/* 141 */       e.printStackTrace();
/*     */     }
/* 143 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getTableSpaceStatus(String resourcename)
/*     */   {
/* 148 */     ArrayList a = new ArrayList();
/*     */     try {
/* 150 */       String tss = "select TableSpaceStatus.TABLESPACENAME,TableSpaceStatus.STATUS,TableSpaceStatusExt.USEDBYTES,TableSpaceStatus.FREEBYTES,TableSpaceStatus.FREEBLOCKS,TableSpaceStatus.NOOFREADS,TableSpaceStatus.WRITES,TableSpaceStatus.READTIME,TableSpaceStatus.WRITETIME,AM_ManagedObject.RESOURCEID,COALESCE(TableSpaceStatusExt.READSPERMIN,'0') as READSPERMIN,COALESCE(TableSpaceStatusExt.WRITESPERMIN,'0') as WRITESPERMIN,COALESCE(TableSpaceStatusExt.PERCENTAGEFREEBYTES,'0') as PERCENTAGEFREEBYTES, COALESCE(TableSpaceStatusExt.PERCENTAGEUSEDBYTES,'0') as PERCENTAGEUSEDBYTES from TableSpaceStatus,AM_ManagedObject left outer join TableSpaceStatusExt on  TableSpaceStatusExt.TABLESPACEID = AM_ManagedObject.RESOURCEID where  TableSpaceStatus.resourcename='" + resourcename + "' and TableSpaceStatusExt.collectiontime=" + this.maxcollectiontime + " and TableSpaceStatus.collectiontime=" + this.maxcollectiontime + " and AM_ManagedObject.resourcename =" + DBQueryUtil.concat(new String[] { "'" + resourcename + ":'", "TableSpaceStatus.TABLESPACENAME" }) + " order by TableSpaceStatusExt.PERCENTAGEFREEBYTES desc";
/* 151 */       a = this.mo.getPropertiesList(tss);
/*     */     }
/*     */     catch (Exception e) {
/* 154 */       e.printStackTrace();
/*     */     }
/* 156 */     return a;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList getTableSpaceFreebytes(String resourcename)
/*     */   {
/* 162 */     ArrayList a = new ArrayList();
/*     */     try {
/* 164 */       String tss = DBQueryUtil.getTopNValues("select TableSpaceStatus.TABLESPACENAME,TableSpaceStatus.STATUS,TableSpaceStatus.FREEBYTES,TableSpaceStatus.FREEBLOCKS,TableSpaceStatus.NOOFREADS,TableSpaceStatus.WRITES,TableSpaceStatus.READTIME,TableSpaceStatus.WRITETIME,AM_ManagedObject.RESOURCEID,COALESCE(TableSpaceStatusExt.READSPERMIN,'0') as READSPERMIN,COALESCE(TableSpaceStatusExt.WRITESPERMIN,'0') as WRITESPERMIN,COALESCE(TableSpaceStatusExt.PERCENTAGEFREEBYTES,'0') as PERCENTAGEFREEBYTES from TableSpaceStatus,AM_ManagedObject left outer join TableSpaceStatusExt on  TableSpaceStatusExt.TABLESPACEID = AM_ManagedObject.RESOURCEID where  TableSpaceStatus.resourcename='" + resourcename + "' and TableSpaceStatusExt.collectiontime=" + this.maxcollectiontime + " and TableSpaceStatus.collectiontime=" + this.maxcollectiontime + " and AM_ManagedObject.resourcename =" + DBQueryUtil.concat(new String[] { "'" + resourcename + ":'", "TableSpaceStatus.TABLESPACENAME" }) + " order by PERCENTAGEFREEBYTES", 5);
/* 165 */       a = this.mo.getPropertiesList(tss);
/*     */     }
/*     */     catch (Exception e) {
/* 168 */       e.printStackTrace();
/*     */     }
/* 170 */     return a;
/*     */   }
/*     */   
/*     */   public Properties getSgaDetails(String resourcename)
/*     */   {
/* 175 */     String sgad = "select * from SgaDetails where RESOURCENAME = '" + resourcename + "'";
/*     */     try {
/* 177 */       this.rs = AMConnectionPool.executeQueryStmt(sgad);
/* 178 */       if (this.rs.next())
/*     */       {
/*     */ 
/* 181 */         this.sharedsgaproperties.setProperty("BUFFERCACHESIZE", String.valueOf(this.rs.getLong("BUFFERCACHESIZE")));
/* 182 */         this.sharedsgaproperties.setProperty("SHAREDPOOLSIZE", String.valueOf(this.rs.getLong("SHAREDPOOLSIZE")));
/* 183 */         this.sharedsgaproperties.setProperty("REDOLOGBUFFERSIZE", String.valueOf(this.rs.getLong("REDOLOGBUFFERSIZE")));
/* 184 */         this.sharedsgaproperties.setProperty("LIBRARYCACHESIZE", String.valueOf(this.rs.getLong("LIBRARYCACHESIZE")));
/* 185 */         this.sharedsgaproperties.setProperty("DATADICTCACHESIZE", String.valueOf(this.rs.getLong("DATADICTCACHESIZE")));
/* 186 */         this.sharedsgaproperties.setProperty("SQLAREASIZE", String.valueOf(this.rs.getLong("SQLAREASIZE")));
/* 187 */         this.sharedsgaproperties.setProperty("FIXEDSIZE", String.valueOf(this.rs.getLong("FIXEDSIZE")));
/*     */       }
/* 189 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/* 192 */       e.printStackTrace();
/*     */     }
/* 194 */     return this.sharedsgaproperties;
/*     */   }
/*     */   
/*     */   public Properties getSgaDetailsExt(String resourceid) {
/* 198 */     String sgad = "select * from SgaDetailsExt  where RESOURCEID = '" + resourceid + "'";
/*     */     try {
/* 200 */       this.rs = AMConnectionPool.executeQueryStmt(sgad);
/* 201 */       if (this.rs.next())
/*     */       {
/*     */ 
/* 204 */         this.sgaext.setProperty("JAVAPOOL", String.valueOf(this.rs.getFloat("JAVAPOOLALLOCATEDBYTES")));
/* 205 */         this.sgaext.setProperty("LARGEPOOL", String.valueOf(this.rs.getFloat("LARGEPOOLALLOCATEDBYTES")));
/*     */       }
/*     */       
/* 208 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/* 211 */       e.printStackTrace();
/*     */     }
/* 213 */     return this.sgaext;
/*     */   }
/*     */   
/*     */   public Properties getSgaStatus(String resourcename)
/*     */   {
/* 218 */     Properties p = new Properties();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 230 */       String sgas = "select * from SgaStatus where RESOURCENAME  = '" + resourcename + "' and COLLECTIONTIME = " + this.maxcollectiontime;
/* 231 */       this.rs = AMConnectionPool.executeQueryStmt(sgas);
/* 232 */       if (this.rs.next()) {
/* 233 */         String sga = String.valueOf(this.rs.getLong("FREEMEMORYSIZE"));
/*     */         
/* 235 */         p.setProperty("FREEMEMORYSIZE", String.valueOf(this.rs.getLong("FREEMEMORYSIZE")));
/* 236 */         p.setProperty("BUFFERHITRATIO", String.valueOf(this.rs.getInt("BUFFERHITRATIO")));
/* 237 */         p.setProperty("DICTIONARYHITRATIO", String.valueOf(this.rs.getInt("DICTIONARYHITRATIO")));
/* 238 */         p.setProperty("LIBRARYHITRATIO", String.valueOf(this.rs.getInt("LIBRARYHITRATIO")));
/*     */       }
/* 240 */       closeResultSet(this.rs);
/*     */     }
/*     */     catch (Exception e) {
/* 243 */       e.printStackTrace();
/*     */     }
/* 245 */     return p;
/*     */   }
/*     */   
/* 248 */   public ArrayList getDataFiles(String resourcename) { ArrayList a = new ArrayList();
/*     */     try {
/* 250 */       String dfs = "select DataFiles.FILE_NAME,DataFiles.TABLESPACENAME,DataFiles.STATUS,DataFiles.CREATEBYTES,DataFiles.NOOFREADS,DataFiles.WRITES,DataFiles.AVGRDTIME,DataFiles.AVGWRTIME,DataFilesExt.AUTOEXTEND,AM_ManagedObject.RESOURCEID from DataFiles,AM_ManagedObject left outer join DataFilesExt on AM_ManagedObject.RESOURCEID=DataFilesExt.FILEID where DataFiles.RESOURCENAME = '" + resourcename + "' and DataFiles.COLLECTIONTIME =" + this.maxcollectiontime + " and DataFilesExt.COLLECTIONTIME =" + this.maxcollectiontime + " and AM_ManagedObject.RESOURCENAME=" + DBQueryUtil.concat(new String[] { "'" + resourcename + ":'", "DataFiles.FILE_NAME" }) + " order by DataFiles.FILE_NAME,DataFiles.TABLESPACENAME";
/* 251 */       a = this.mo.getPropertiesList(dfs);
/*     */     }
/*     */     catch (Exception e) {
/* 254 */       e.printStackTrace();
/*     */     }
/* 256 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getSessionInfo(String resourceid)
/*     */   {
/* 261 */     ArrayList a = new ArrayList();
/*     */     try {
/* 263 */       String sess = "select RESOURCEID,COLLECTIONTIME,COMPONENTNAME,SESSIONID,STATUS,MACHINE,USERNAME,ELAPSEDTIME,CPUUSED,MEMORYSORTS,TABLESCANS,PHYSICALREADS,LOGICALREADS,DISKSORTS,BLOCKSCHANGED,CHAINEDROWS,COMMITS,CURSORS ,BUFFERCACHEHITRATIO from SessionInfo where COLLECTIONTIME = (select max(collectiontime) from SessionInfo where  RESOURCEID = '" + resourceid + "') and RESOURCEID='" + resourceid + "'";
/* 264 */       a = this.mo.getPropertiesList(sess);
/*     */     }
/*     */     catch (Exception e) {
/* 267 */       e.printStackTrace();
/*     */     }
/* 269 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getSessionSummary(String resourceid) {
/* 273 */     ArrayList a = new ArrayList();
/*     */     try {
/* 275 */       String sess_details = "select RESOURCEID,MACHINE,PROGRAM,STATUS,COUNT,COLLECTIONTIME from SessionInfoExt where COLLECTIONTIME = (select max(collectiontime) from SessionInfoExt where  RESOURCEID = '" + resourceid + "')  and RESOURCEID = " + resourceid;
/* 276 */       a = this.mo.getPropertiesList(sess_details);
/*     */     }
/*     */     catch (Exception e) {
/* 279 */       e.printStackTrace();
/*     */     }
/* 281 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getRollbackData(String resourcename) {
/* 285 */     ArrayList a = new ArrayList();
/*     */     try {
/* 287 */       String sess = "select RollbackData.RESOURCENAME,SEGMENTNAME,TABLESPACENAME,INITIALEXTENT,NEXTEXTENT,MINEXTENT,MAXEXTENT,STATUS,CURRENTSIZE,BLOCKS,GETS,WAITS,HWMSIZE,SHRINKS,WRAPS,EXTEND,COLLECTIONTIME,RESOURCEID from RollbackData,AM_ManagedObject where RollbackData.COLLECTIONTIME ='" + this.maxcollectiontime + "'  and RollbackData.RESOURCENAME = '" + resourcename + "' and AM_ManagedObject.RESOURCENAME =" + DBQueryUtil.concat(new String[] { "'" + resourcename + ":'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" });
/* 288 */       a = this.mo.getPropertiesList(sess);
/*     */     }
/*     */     catch (Exception e) {
/* 291 */       e.printStackTrace();
/*     */     }
/* 293 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getSessionWaits(String resourceid)
/*     */   {
/* 298 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 301 */       String sess_wait = "select RESOURCEID,SID,USERNAME,EVENT,WAIT_TIME,SECONDS_IN_WAIT,EVENT_OCCURRENCE,STATE,COLLECTIONTIME from OracleSessionWaits where OracleSessionWaits.COLLECTIONTIME = (select max(collectiontime) from OracleSessionWaits where  RESOURCEID = '" + resourceid + "')  and OracleSessionWaits.RESOURCEID = " + resourceid + " order by EVENT";
/* 302 */       a = this.mo.getPropertiesList(sess_wait);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 306 */       e.printStackTrace();
/*     */     }
/* 308 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getBufferGets(String resourceid)
/*     */   {
/* 313 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 316 */       String buffergets = "select RESOURCEID,SQLID,BUFFERGETS,EXECUTIONS,BUFFERGETSPEREXEC,QUERY,COLLECTIONTIME from OracleBufferGets where OracleBufferGets.COLLECTIONTIME = (select max(collectiontime) from OracleBufferGets where  RESOURCEID = '" + resourceid + "') and OracleBufferGets.RESOURCEID = " + resourceid + " order by BUFFERGETSPEREXEC desc";
/* 317 */       a = this.mo.getPropertiesList(buffergets);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 321 */       e.printStackTrace();
/*     */     }
/* 323 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getDiskReads(String resourceid)
/*     */   {
/* 328 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 331 */       String diskreads = "select RESOURCEID,SQLID,DISKREADS,EXECUTIONS,DISKREADSPEREXEC,QUERY,COLLECTIONTIME from OracleDiskReads where OracleDiskReads.COLLECTIONTIME = (select max(collectiontime) from OracleDiskReads where  RESOURCEID = '" + resourceid + "') and OracleDiskReads.RESOURCEID = " + resourceid + " order by DISKREADSPEREXEC desc";
/* 332 */       a = this.mo.getPropertiesList(diskreads);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 336 */       e.printStackTrace();
/*     */     }
/* 338 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getBlockers(String resourceid)
/*     */   {
/* 343 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 346 */       String blockers = "select RESOURCEID,SID,SERIAL,MACHINE,PROGRAM,LOCKWAIT,COLLECTIONTIME from OracleBlockers where COLLECTIONTIME =(select max(collectiontime) from OracleBlockers where  RESOURCEID = '" + resourceid + "')  and RESOURCEID = " + resourceid;
/* 347 */       a = this.mo.getPropertiesList(blockers);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 351 */       e.printStackTrace();
/*     */     }
/* 353 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getWaiters(String resourceid)
/*     */   {
/* 358 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 361 */       String waiters = "select RESOURCEID,WAITING_SESSION,HOLDING_SESSION,LOCK_TYPE,MODE_HELD,MODE_REQUESTED,LOCK_ID1,LOCK_ID2,COLLECTIONTIME from OracleWaiters where COLLECTIONTIME =(select max(collectiontime) from OracleWaiters where  RESOURCEID = '" + resourceid + "')  and RESOURCEID = " + resourceid;
/* 362 */       a = this.mo.getPropertiesList(waiters);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 366 */       e.printStackTrace();
/*     */     }
/* 368 */     return a;
/*     */   }
/*     */   
/*     */   public ArrayList getLocks(String resourceid)
/*     */   {
/* 373 */     ArrayList a = new ArrayList();
/*     */     try
/*     */     {
/* 376 */       String locks = "select RESOURCEID,OBJECT_NAME,SESSION_ID,SERIAL,LOCK_MODE,STATUS,SPID,LOGON_TIME,LAST_CALL_MINUTE,COLLECTIONTIME  from OracleLocks where COLLECTIONTIME =(select max(collectiontime) from OracleLocks where  RESOURCEID = '" + resourceid + "')  and RESOURCEID = " + resourceid;
/* 377 */       a = this.mo.getPropertiesList(locks);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 381 */       e.printStackTrace();
/*     */     }
/* 383 */     return a;
/*     */   }
/*     */   
/*     */   protected String findReplace(String str, String find, String replace)
/*     */   {
/* 388 */     if ((str == null) || (find == null) || (replace == null)) { return null;
/*     */     }
/* 390 */     String des = new String();
/* 391 */     while (str.indexOf(find) != -1) {
/* 392 */       des = des + str.substring(0, str.indexOf(find));
/* 393 */       des = des + replace;
/* 394 */       str = str.substring(str.indexOf(find) + find.length());
/*     */     }
/* 396 */     des = des + str;
/* 397 */     return des;
/*     */   }
/*     */   
/* 400 */   public ArrayList getPGAStatsPerProcess(String resourceid, String resourcename, String topN) { ArrayList processList = new ArrayList();
/*     */     try {
/* 402 */       String pgaPerProcess = "select AM_ORACLE_PROCESS.RESOURCEID,SPID,PROGRAM, PGA_USED, PGA_ALLOCATED,MAX_PGA, FREEABLE_PGA  from  AM_ORACLE_PROCESS where collectiontime=(select max(collectiontime) from AM_ORACLE_PROCESS where  RESOURCEID = '" + resourceid + "') and RESOURCEID=" + resourceid;
/* 403 */       ArrayList pList = this.mo.getResultSetAsMap(pgaPerProcess);
/* 404 */       Iterator iterateAllProcess = pList.iterator();
/* 405 */       ArrayList pgaPerProcessList = new ArrayList();
/* 406 */       processList.add(pgaPerProcessList);
/* 407 */       LinkedHashMap graphValues = new LinkedHashMap();
/* 408 */       processList.add(graphValues);
/* 409 */       while (iterateAllProcess.hasNext()) {
/*     */         try {
/* 411 */           LinkedHashMap processDetails = (LinkedHashMap)iterateAllProcess.next();
/* 412 */           pgaPerProcessList.add(processDetails);
/* 413 */           String displayName = (Long)processDetails.get("SPID") + "-" + (String)processDetails.get("PROGRAM");
/* 414 */           String pgaUsed = (Long)processDetails.get("PGA_USED") + "";
/* 415 */           graphValues.put(displayName, pgaUsed);
/*     */         }
/*     */         catch (Exception e) {
/* 418 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 423 */       e.printStackTrace();
/*     */     }
/* 425 */     return processList;
/*     */   }
/*     */   
/* 428 */   public void getPGAStats(String parentResourceID, ArrayList processList) { Properties pgaStats = new Properties();
/* 429 */     String pgaStatsQuery = "select AM_ManagedObject.RESOURCEID,RESOURCEUSEDBYPROCESSES, CACHE_HIT_PERCENTAGE, TOTAL_PGA_USED,TOTAL_PGA_ALLOCATED, MAX_PGA_ALLOCATED, TOTAL_FREEABLE_PGA, PGA_FREED_BACK from AM_ManagedObject left outer join AM_ORACLE_PGA_STATS on AM_ManagedObject.RESOURCEID=AM_ORACLE_PGA_STATS.RESOURCEID where collectiontime=" + this.maxcollectiontime + " and AM_ManagedObject.RESOURCEID=" + parentResourceID;
/*     */     
/* 431 */     ArrayList pList = this.mo.getPropertiesList(pgaStatsQuery);
/* 432 */     if (pList.size() > 0) {
/* 433 */       pgaStats = (Properties)pList.get(0);
/* 434 */       String encodeURL = URLEncoder.encode("/showresource.do?resourceid=" + parentResourceID + "&method=showResourceForResourceID&Datatype=2");
/* 435 */       String configAlarms = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + parentResourceID + "&attributeIDs=2913,2914,2915,2916,2917,2918,2919&attributeToSelect=2913";
/* 436 */       pgaStats.put("ALARMS", configAlarms);
/* 437 */       pgaStats.put("ENCODEURL", encodeURL);
/* 438 */       getResourceLimitForProcesses(pgaStats);
/*     */     }
/* 440 */     Properties alert = new Properties();
/* 441 */     ArrayList attribIDs = new ArrayList();
/* 442 */     ArrayList resIDs = new ArrayList();
/* 443 */     for (int i = 2913; i <= 2919; i++) {
/* 444 */       attribIDs.add(i + "");
/*     */     }
/* 446 */     resIDs.add(parentResourceID);
/* 447 */     alert = FaultUtil.getStatus(resIDs, attribIDs);
/* 448 */     pgaStats.put("ALERT-STATUS", alert);
/* 449 */     processList.add(pgaStats);
/*     */   }
/*     */   
/* 452 */   public void getResourceLimitForProcesses(Properties pgaStats) { LinkedHashMap pieGraphValues = new LinkedHashMap();
/*     */     try {
/* 454 */       long resourceusedByProcess = ((Long)pgaStats.get("RESOURCEUSEDBYPROCESSES")).longValue();
/* 455 */       pieGraphValues.put("Resource being used by processes", resourceusedByProcess + "");
/* 456 */       pieGraphValues.put("Free resource", 100L - resourceusedByProcess + "");
/* 457 */       pgaStats.put("RESOURCE-LIMIT", pieGraphValues);
/*     */     }
/*     */     catch (Exception e) {
/* 460 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void getJobDetails(ArrayList jobDetails, Properties allJobs, String parentResourceID) {
/* 465 */     LinkedHashMap jobProps = new LinkedHashMap();
/* 466 */     Properties alert = new Properties();
/* 467 */     ArrayList attribIDs = new ArrayList();
/* 468 */     ArrayList resIDs = new ArrayList();
/* 469 */     String resourceid = (Long)allJobs.get("RESOURCEID") + "";
/* 470 */     attribIDs.add("2901");
/* 471 */     resIDs.add(resourceid);
/* 472 */     alert = FaultUtil.getStatus(resIDs, attribIDs);
/* 473 */     jobProps.put("RESOURCEID", resourceid);
/* 474 */     jobProps.put("JOB_NAME", allJobs.get("JOB_NAME"));
/* 475 */     jobProps.put("CURRENT_STATE", allJobs.get("CURRENT_STATE"));
/* 476 */     jobProps.put("LAST_RUN_STATE", ((String)allJobs.get("LAST_RUN_STATE")).trim().equals("") ? "-" : allJobs.get("LAST_RUN_STATE"));
/* 477 */     jobProps.put("LAST_START_TIME", ((String)allJobs.get("LAST_START_TIME")).trim().equals("") ? "-" : allJobs.get("LAST_START_TIME"));
/* 478 */     jobProps.put("LAST_RUN_DURATION", allJobs.get("LAST_RUN_DURATION").equals("NULL") ? "-" : allJobs.get("LAST_RUN_DURATION"));
/* 479 */     jobProps.put("NEXT_RUN_TIME", ((String)allJobs.get("NEXT_RUN_TIME")).trim().equals("") ? "-" : allJobs.get("NEXT_RUN_TIME"));
/* 480 */     String severity = alert.getProperty(resourceid + "#" + "2901");
/* 481 */     String img = ReportUtilities.getSeverityImageForHealth(severity);
/* 482 */     if (img.indexOf("#") != -1) {
/* 483 */       img = img.substring(img.indexOf("#") + 1, img.length());
/*     */     }
/* 485 */     String health = "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=2901')\">" + img + "</a>";
/* 486 */     jobProps.put("HEALTH", health);
/* 487 */     String encodeURL = URLEncoder.encode("/showresource.do?resourceid=" + parentResourceID + "&method=showResourceForResourceID&Datatype=2");
/* 488 */     String configAlarms = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=2901,2903,2904,2908,2909,2910,2911,2912&attributeToSelect=2901";
/* 489 */     jobProps.put("ALARMS", configAlarms);
/* 490 */     jobProps.put("ENCODEURL", encodeURL);
/* 491 */     jobDetails.add(jobProps);
/*     */   }
/*     */   
/* 494 */   public void getJobSummary(ArrayList jobDetails, Properties allJobs, String parentResourceID) { LinkedHashMap jobProps = new LinkedHashMap();
/* 495 */     jobProps.put("RESOURCEID", allJobs.get("RESOURCEID"));
/* 496 */     jobProps.put("JOB_NAME", allJobs.get("JOB_NAME"));
/* 497 */     jobProps.put("RUN_COUNT", allJobs.get("RUN_COUNT"));
/* 498 */     jobProps.put("FAILURE_COUNT", allJobs.get("FAILURE_COUNT"));
/* 499 */     jobProps.put("RETRY_COUNT", allJobs.get("RETRY_COUNT"));
/* 500 */     jobProps.put("ELASPED_TIME", allJobs.get("ELASPED_TIME").equals("NULL") ? "-" : allJobs.get("ELASPED_TIME"));
/* 501 */     jobProps.put("JOB_ENABLED", allJobs.get("JOB_ENABLED"));
/* 502 */     jobDetails.add(jobProps);
/*     */   }
/*     */   
/* 505 */   public ArrayList getScheduledJobs(String resourceid, String resourcename) { ArrayList returnList = new ArrayList();
/* 506 */     ArrayList backUpJobList = getBackUpJobDetails(resourceid, resourcename);
/* 507 */     returnList.add(backUpJobList);
/* 508 */     return returnList;
/*     */   }
/*     */   
/* 511 */   public ArrayList getCorruptedBlockData(String resourcename, String resourceId) { String blocksCorrupted = "select AM_ORACLE_BLOCK_CORRUPTION.RESOURCEID,FILE_NUMBER,BLOCK_NUMBER,NUMBER_OF_CORRUPTED_BLOCKS,CORRUPTION_TYPE  from AM_ManagedObject left outer join AM_ORACLE_BLOCK_CORRUPTION on AM_ManagedObject.RESOURCEID=AM_ORACLE_BLOCK_CORRUPTION.RESOURCEID join AM_PARENTCHILDMAPPER  on AM_ORACLE_BLOCK_CORRUPTION.RESOURCEID=CHILDID where type='OracleBlockCorruption' and collectiontime=" + this.maxcollectiontime + " and PARENTID=" + resourceId;
/*     */     
/* 513 */     ArrayList corruptedList = this.mo.getResultSetAsMap(blocksCorrupted);
/* 514 */     return corruptedList;
/*     */   }
/*     */   
/* 517 */   public ArrayList getOracleMonitorDisplayDetails(String parentResourceid, String query, Properties props) { return getOracleMonitorDisplayDetails(parentResourceid, query, props, null); }
/*     */   
/*     */   public ArrayList getOracleMonitorDisplayDetails(String parentResourceid, String query, Properties props, ArrayList resourceIdList) {
/* 520 */     ArrayList toreturn = new ArrayList();
/*     */     try {
/* 522 */       toreturn = this.mo.getResultSetAsMap(query);
/* 523 */       boolean iterateRowsAndAddColumns = props.get("iterateRowsAndAddColumns") != null;
/* 524 */       if (iterateRowsAndAddColumns) {
/* 525 */         Iterator iterateList = toreturn.iterator();
/* 526 */         ArrayList attribIDs = new ArrayList();
/* 527 */         ArrayList resIDs = new ArrayList();
/* 528 */         Properties alert = new Properties();
/* 529 */         String configureAlarmForAttIds = props.get("configureAlarmForAttIds") != null ? (String)props.get("configureAlarmForAttIds") : null;
/* 530 */         String healthId = props.get("HEALTHID") != null ? (String)props.get("HEALTHID") : null;
/* 531 */         String firstAttribute = props.get("firstAttribute") != null ? (String)props.get("firstAttribute") : null;
/* 532 */         boolean collectResourceIds = props.get("collectResourceIds") != null;
/* 533 */         while (iterateList.hasNext()) {
/*     */           try {
/* 535 */             LinkedHashMap valuesMap = (LinkedHashMap)iterateList.next();
/* 536 */             String resourceid = (Long)valuesMap.get("RESOURCEID") + "";
/* 537 */             if (healthId != null) {
/* 538 */               attribIDs.add(healthId);
/* 539 */               resIDs.add(resourceid);
/* 540 */               alert = FaultUtil.getStatus(resIDs, attribIDs);
/* 541 */               String severity = alert.getProperty(resourceid + "#" + healthId);
/* 542 */               String img = ReportUtilities.getSeverityImageForHealth(severity);
/* 543 */               if (img.indexOf("#") != -1) {
/* 544 */                 img = img.substring(img.indexOf("#") + 1, img.length());
/*     */               }
/* 546 */               String health = "<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthId + "')\">" + img + "</a>";
/* 547 */               valuesMap.put("HEALTH", health);
/*     */             }
/* 549 */             if (configureAlarmForAttIds != null) {
/* 550 */               String configAlarms = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + configureAlarmForAttIds + "&attributeToSelect=" + firstAttribute;
/* 551 */               valuesMap.put("ALARMS", configAlarms);
/*     */             }
/* 553 */             if (collectResourceIds) {
/* 554 */               if (resourceIdList == null) {
/* 555 */                 resourceIdList = new ArrayList();
/*     */               }
/* 557 */               resourceIdList.add(resourceid);
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 561 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 567 */       e.printStackTrace();
/*     */     }
/* 569 */     return toreturn;
/*     */   }
/*     */   
/* 572 */   public Properties getUITableDetails(String resourceType) { if (!oracleUITableDetails.containsKey(resourceType)) {
/* 573 */       Properties props = new Properties();
/* 574 */       if (resourceType.equals("OracleSegments")) {
/* 575 */         props.put("iterateRowsAndAddColumns", "true");
/* 576 */         props.put("configureAlarmForAttIds", "2951,2955,2956,2957");
/* 577 */         props.put("firstAttribute", "2955");
/* 578 */         props.put("HEALTHID", "2951");
/* 579 */         oracleUITableDetails.put("OracleSegments", props);
/*     */       }
/* 581 */       else if (resourceType.equals("OracleBackUpJobs")) {
/* 582 */         props.put("iterateRowsAndAddColumns", "true");
/* 583 */         props.put("configureAlarmForAttIds", "2931,2935,2937");
/* 584 */         props.put("firstAttribute", "2933");
/* 585 */         props.put("HEALTHID", "2931");
/* 586 */         oracleUITableDetails.put("OracleBackUpJobs", props);
/*     */       }
/* 588 */       else if (resourceType.equals("OracleDiskGroups")) {
/* 589 */         props.put("iterateRowsAndAddColumns", "true");
/* 590 */         props.put("configureAlarmForAttIds", "2961,2964,2966,2967,2968");
/* 591 */         props.put("firstAttribute", "2965");
/* 592 */         props.put("HEALTHID", "2961");
/* 593 */         props.put("collectResourceIds", "YES");
/* 594 */         oracleUITableDetails.put("OracleDiskGroups", props);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 604 */     return (Properties)oracleUITableDetails.get(resourceType);
/*     */   }
/*     */   
/* 607 */   public ArrayList getTableSpaceExtents(String resourcename, String parentResourceid) { String objectExtents = "select AM_ORACLE_SEGMENTS.RESOURCEID,OWNER,TABLESPACE_NAME,SEGMENT_NAME,SEGMENT_TYPE,EXTENTS,MAX_EXTENTS,NEXT_EXTENT  from AM_ManagedObject left outer join AM_ORACLE_SEGMENTS on AM_ManagedObject.RESOURCEID=AM_ORACLE_SEGMENTS.RESOURCEID join AM_PARENTCHILDMAPPER  on AM_ORACLE_SEGMENTS.RESOURCEID=CHILDID where type='OracleSegments' and collectiontime=" + this.maxcollectiontime + " and PARENTID=" + parentResourceid;
/*     */     
/* 609 */     return getOracleMonitorDisplayDetails(parentResourceid, objectExtents, getUITableDetails("OracleSegments"));
/*     */   }
/*     */   
/* 612 */   public ArrayList getASMDiskGroupDetails(String resourcename, String parentResourceid) { String asmDiskGroup = "select AM_ORACLE_DISKGROUPS.RESOURCEID,GROUP_NUMBER ,NAME,STATE,DISK_GROUP_TYPE,TOTAL_MEM,FREE_MEM,USED_MEM_PERCENTAGE  from AM_ManagedObject left outer join AM_ORACLE_DISKGROUPS on AM_ManagedObject.RESOURCEID=AM_ORACLE_DISKGROUPS.RESOURCEID join AM_PARENTCHILDMAPPER  on AM_ORACLE_DISKGROUPS.RESOURCEID=CHILDID where type='OracleDiskGroups' and collectiontime=" + this.maxcollectiontime + " and PARENTID=" + parentResourceid;
/* 613 */     ArrayList diskGroupIds = new ArrayList();
/* 614 */     ArrayList diskGroupsList = getOracleMonitorDisplayDetails(parentResourceid, asmDiskGroup, getUITableDetails("OracleDiskGroups"), diskGroupIds);
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
/* 635 */     ArrayList toreturn = new ArrayList();
/* 636 */     Iterator iterateList = diskGroupsList.iterator();
/* 637 */     while (iterateList.hasNext()) {
/*     */       try {
/* 639 */         LinkedHashMap diskGrpProps = (LinkedHashMap)iterateList.next();
/*     */         
/*     */ 
/* 642 */         ArrayList returnList = new ArrayList();
/* 643 */         toreturn.add(returnList);
/* 644 */         returnList.add(diskGrpProps);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 648 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 651 */     return toreturn;
/*     */   }
/*     */   
/* 654 */   public ArrayList getBackUpJobDetails(String parentResourceid, String resourcename) { String backUpJobs = "select  SESSION_KEY,INPUT_TYPE,LAST_BACKUP_TIME,STATUS,TIME_TAKEN,BACKUP_SIZE from AM_ORACLEBACKUPJOBS where COLLECTIONTIME = (select max(collectiontime) from AM_ORACLEBACKUPJOBS where  RESOURCEID = '" + parentResourceid + "') and RESOURCEID =" + parentResourceid;
/*     */     
/* 656 */     ArrayList backUpJobList = this.mo.getResultSetAsMap(backUpJobs);
/* 657 */     return backUpJobList;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) throws DatasetProduceException {
/* 661 */     DefaultPieDataset ds = new DefaultPieDataset();
/*     */     
/*     */     try
/*     */     {
/* 665 */       if (this.parameter.equals("httprequest"))
/*     */       {
/* 667 */         getSgaDetails(this.resourcename);
/* 668 */         if (!this.sharedsgaproperties.isEmpty())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 673 */           String BUFFERCACHESIZE = this.sharedsgaproperties.getProperty("BUFFERCACHESIZE");
/* 674 */           String SHAREDPOOLSIZE = this.sharedsgaproperties.getProperty("SHAREDPOOLSIZE");
/* 675 */           String REDOLOGBUFFERSIZE = this.sharedsgaproperties.getProperty("REDOLOGBUFFERSIZE");
/* 676 */           String LIBRARYCACHESIZE = this.sharedsgaproperties.getProperty("LIBRARYCACHESIZE");
/* 677 */           String DATADICTCACHESIZE = this.sharedsgaproperties.getProperty("DATADICTCACHESIZE");
/* 678 */           String SQLAREASIZE = this.sharedsgaproperties.getProperty("SQLAREASIZE");
/* 679 */           String FIXEDSIZE = this.sharedsgaproperties.getProperty("FIXEDSIZE");
/* 680 */           double formatbytes = 1048576.0D;
/* 681 */           double buffercachesize = Double.parseDouble(BUFFERCACHESIZE);
/* 682 */           double sharedpoolsize = Double.parseDouble(SHAREDPOOLSIZE);
/* 683 */           double redologsize = Double.parseDouble(REDOLOGBUFFERSIZE);
/* 684 */           double librarycachesize = Double.parseDouble(LIBRARYCACHESIZE);
/* 685 */           double datadictcachesize = Double.parseDouble(DATADICTCACHESIZE);
/* 686 */           double sqlareasize = Double.parseDouble(SQLAREASIZE);
/* 687 */           double fixedsize = Double.parseDouble(FIXEDSIZE);
/*     */           
/* 689 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.buffercachesize"), buffercachesize / formatbytes);
/* 690 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.sharedpoolsize"), sharedpoolsize / formatbytes);
/* 691 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.Redologbuffersize"), redologsize / formatbytes);
/* 692 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.Librarycachesize"), librarycachesize / formatbytes);
/* 693 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.Datadictionarycachesize"), datadictcachesize / formatbytes);
/* 694 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.sqlareasize"), sqlareasize / formatbytes);
/* 695 */           ds.setValue(FormatUtil.getString("am.webclient.oracle.fixedareasize"), fixedsize / formatbytes);
/*     */           
/* 697 */           m = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*     */       Map m;
/* 703 */       e.printStackTrace();
/*     */     }
/*     */     
/* 706 */     return ds;
/*     */   }
/*     */   
/*     */ 
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/*     */     try
/*     */     {
/* 714 */       if (rs != null)
/*     */       {
/* 716 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 721 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\oracle\bean\OracleBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */