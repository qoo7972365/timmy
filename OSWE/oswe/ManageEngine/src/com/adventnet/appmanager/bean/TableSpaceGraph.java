/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableSpaceGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  21 */   private String resourcename = "default";
/*  22 */   private String category = "default";
/*  23 */   private String entity = "default";
/*     */   
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet resultset;
/*     */   private ResultSet free;
/*     */   private ResultSet used;
/*  30 */   final String[] seriesNames = { FormatUtil.getString("am.webclient.oracle.graph.freespace"), FormatUtil.getString("am.webclient.oracle.graph.usedspace") };
/*     */   DefaultIntervalCategoryDataset ds;
/*     */   
/*     */   public TableSpaceGraph() {
/*  34 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  39 */     long maxtime = 0L;
/*  40 */     long detailsmaxtime = 0L;
/*  41 */     long freespace = 0L;
/*  42 */     long usedspace = 0L;
/*  43 */     long totalspace = 0L;
/*  44 */     String query = "select max(COLLECTIONTIME) from TableSpaceStatus where RESOURCENAME = '" + this.resourcename + "'";
/*     */     try {
/*  46 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {
/*  49 */       e.printStackTrace();
/*     */     }
/*     */     try {
/*  52 */       if (this.rs.next())
/*     */       {
/*  54 */         maxtime = this.rs.getLong(1);
/*     */       }
/*  56 */       if (this.rs != null)
/*     */       {
/*  58 */         AMConnectionPool.closeStatement(this.rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/*  64 */     String querymaxtime = "select max(COLLECTIONTIME) from TableSpaceDetails where RESOURCENAME = '" + this.resourcename + "'";
/*     */     try {
/*  66 */       this.resultset = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {
/*  69 */       e.printStackTrace();
/*     */     }
/*     */     try {
/*  72 */       if (this.resultset.next())
/*     */       {
/*  74 */         detailsmaxtime = this.resultset.getLong(1);
/*     */       }
/*  76 */       if (this.resultset != null)
/*     */       {
/*  78 */         AMConnectionPool.closeStatement(this.resultset);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/*     */ 
/*  85 */     String tsquery = DBQueryUtil.getTopNValues("select TableSpaceStatus.TABLESPACENAME,TableSpaceStatus.FREEBYTES,(TableSpaceDetails.ALLOCATEDBYTES-TableSpaceStatus.FREEBYTES) USEDBYTES from TableSpaceStatus,TableSpaceDetails where TableSpaceStatus.RESOURCENAME = '" + this.resourcename + "' and TableSpaceStatus.COLLECTIONTIME=" + maxtime + " and TableSpaceDetails.COLLECTIONTIME=" + detailsmaxtime + "  and TableSpaceDetails.RESOURCENAME=TableSpaceStatus.RESOURCENAME and TableSpaceDetails.TABLESPACENAME=TableSpaceStatus.TABLESPACENAME order by TableSpaceStatus.FREEBYTES", 10);
/*     */     try {
/*  87 */       this.rs = AMConnectionPool.executeQueryStmt(tsquery);
/*  88 */       Vector v1 = new Vector();
/*  89 */       Vector v2 = new Vector();
/*  90 */       Vector v3 = new Vector();
/*  91 */       while (this.rs.next()) {
/*  92 */         v1.add(this.rs.getString(1));
/*  93 */         v2.add(this.rs.getString(3));
/*  94 */         v3.add(this.rs.getString(2));
/*     */       }
/*  96 */       String[] categories = new String[v1.size()];
/*  97 */       Long[][] startValues = new Long[this.seriesNames.length][categories.length];
/*  98 */       Long[][] endValues = new Long[this.seriesNames.length][categories.length];
/*  99 */       for (int i = 0; i < categories.length; i++) {
/* 100 */         categories[i] = ((String)v1.get(i));
/* 101 */         startValues[0][i] = new Long(0L);
/* 102 */         endValues[0][i] = new Long(Long.parseLong((String)v3.get(i)) / 1048576L);
/* 103 */         startValues[1][i] = new Long(0L);
/* 104 */         endValues[1][i] = new Long(Long.parseLong((String)v2.get(i)) / 1048576L);
/*     */       }
/* 106 */       if (this.rs != null)
/*     */       {
/* 108 */         AMConnectionPool.closeStatement(this.rs);
/*     */       }
/* 110 */       this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, categories, startValues, endValues);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 114 */       e.printStackTrace();
/*     */     }
/* 116 */     return this.ds;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 125 */     return "tablespacegraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 130 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 134 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 138 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 142 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 146 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 150 */     this.entity = ent;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\TableSpaceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */