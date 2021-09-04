/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
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
/*     */ public class AS400DiskSpaceGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  31 */   private String resourcename = "default";
/*  32 */   private String category = "default";
/*  33 */   private String entity = "default";
/*  34 */   private String resourceId = "default";
/*  35 */   private String toremove = "DiskUtilization-";
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet rs1;
/*     */   private ResultSet free;
/*     */   private ResultSet used;
/*  41 */   final String[] seriesNames = { FormatUtil.getString("am.webclient.oracle.graph.usedspace"), FormatUtil.getString("am.webclient.oracle.graph.freespace") };
/*     */   DefaultIntervalCategoryDataset ds;
/*     */   
/*     */   public AS400DiskSpaceGraph() {
/*  45 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException {
/*  49 */     long maxtime = 0L;
/*  50 */     long freespace = 0L;
/*  51 */     long usedspace = 0L;
/*  52 */     long totalspace = 0L;
/*  53 */     long coltime = 0L;
/*     */     
/*  55 */     String query = "select max(collectiontime) as coltime from AM_AS400_CONFIGURATION where RESOURCEID=" + this.resourceId;
/*     */     try {
/*  57 */       this.rs1 = AMConnectionPool.executeQueryStmt(query);
/*  58 */       if (this.rs1.next()) {
/*  59 */         coltime = this.rs1.getLong(1);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  64 */       e.printStackTrace();
/*     */     }
/*     */     
/*  67 */     String tsquery = "select mo.DISPLAYNAME,bi.DRIVE_PER from AM_AS400_DISK bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resourceId + " and bi.collectiontime=" + coltime + " ORDER BY bi.DRIVE_PER DESC";
/*     */     try
/*     */     {
/*  70 */       this.rs = AMConnectionPool.executeQueryStmt(tsquery);
/*  71 */       String iname = null;
/*  72 */       Vector v = new Vector();
/*  73 */       for (int j = 0; this.rs.next(); j++) {
/*  74 */         if (j <= 9) {
/*  75 */           iname = this.rs.getString(1);
/*  76 */           v.add(iname.substring(iname.indexOf(this.toremove) + this.toremove.length()));
/*     */         }
/*     */       }
/*  79 */       this.rs.close();
/*  80 */       if (v.size() == 0) {
/*  81 */         return this.ds;
/*     */       }
/*  83 */       String[] categories = new String[v.size()];
/*  84 */       Long[][] startValues = new Long[this.seriesNames.length][categories.length];
/*  85 */       Long[][] endValues = new Long[this.seriesNames.length][categories.length];
/*     */       
/*     */ 
/*  88 */       this.rs = AMConnectionPool.executeQueryStmt(tsquery);
/*  89 */       int i = 0;
/*  90 */       for (int j = 0; this.rs.next(); j++)
/*     */       {
/*  92 */         if (j <= 9) {
/*  93 */           categories[i] = ((String)v.get(i));
/*  94 */           startValues[0][i] = new Long(0L);
/*  95 */           endValues[0][i] = new Long(this.rs.getLong(2));
/*  96 */           startValues[1][i] = new Long(0L);
/*  97 */           endValues[1][i] = new Long(100L - this.rs.getLong(2));
/*  98 */           i += 1;
/*     */         }
/*     */       }
/* 101 */       this.rs.close();
/* 102 */       this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, categories, startValues, endValues);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 106 */       e.printStackTrace();
/* 107 */       return null;
/*     */     }
/* 109 */     return this.ds;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 117 */     return "tablespacegraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 122 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 126 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getResourceId() {
/* 130 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resource) {
/* 134 */     this.resourceId = resource;
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


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\AS400DiskSpaceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */