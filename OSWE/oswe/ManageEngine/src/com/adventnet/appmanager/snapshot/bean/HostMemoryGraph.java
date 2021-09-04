/*     */ package com.adventnet.appmanager.snapshot.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.data.time.TimeSeries;
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
/*     */ public class HostMemoryGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*     */   private AMConnectionPool pool;
/*  29 */   long memuse = 0L;
/*  30 */   long memfree = 0L;
/*  31 */   private String category = "default";
/*  32 */   private String entity = "default";
/*     */   private ResultSet rs;
/*     */   private int i;
/*  35 */   private String appname = null;
/*     */   private String haid;
/*  37 */   private TimeSeries ts = null;
/*  38 */   private Vector HostResourceCount = new Vector();
/*  39 */   private Vector HostResourceId = new Vector();
/*  40 */   final String[] seriesNames = { "Memory Used", "Memory Free" };
/*     */   DefaultIntervalCategoryDataset ds;
/*  42 */   String[] categories = null;
/*  43 */   Long[][] startValues = (Long[][])null;
/*  44 */   Long[][] endValues = (Long[][])null;
/*     */   
/*     */   public HostMemoryGraph()
/*     */   {
/*  48 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  54 */     String mainQuery = "select AM_ManagedObject.ResourceName, AM_ManagedObject.ResourceId from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=" + this.haid + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ManagedObject.Type ='Node'";
/*     */     try
/*     */     {
/*  57 */       this.rs = AMConnectionPool.executeQueryStmt(mainQuery);
/*  58 */       while (this.rs.next())
/*     */       {
/*     */ 
/*  61 */         String hostname = this.rs.getString(1);
/*  62 */         this.HostResourceCount.add(hostname);
/*  63 */         String hostid = this.rs.getString(2);
/*  64 */         this.HostResourceId.add(hostid);
/*     */       }
/*     */       
/*     */ 
/*  68 */       this.categories = new String[this.HostResourceCount.size()];
/*  69 */       this.startValues = new Long[this.seriesNames.length][this.categories.length];
/*  70 */       this.endValues = new Long[this.seriesNames.length][this.categories.length];
/*     */       
/*  72 */       for (this.i = 0; this.i < this.HostResourceCount.size();)
/*     */       {
/*  74 */         this.categories[this.i] = ((String)this.HostResourceId.get(this.i));
/*  75 */         long collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from HostDataCollected where RESOURCENAME like '%" + (String)this.HostResourceCount.get(this.i) + "%'");
/*  76 */         if (collectionTime != -1L)
/*     */         {
/*  78 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  79 */           String query = "select CURVALUE from HostDataCollected where RESOURCENAME like '%" + (String)this.HostResourceCount.get(this.i) + "%' and CATEGORY like '%" + getCategory() + "%' and CollectionTime = " + collectionTime + " and ENTITY like '%" + this.entity + "%'";
/*  80 */           ResultSet set = null;
/*     */           try
/*     */           {
/*  83 */             set = AMConnectionPool.executeQueryStmt(query);
/*  84 */             if (set.next())
/*     */             {
/*  86 */               this.memuse = set.getLong(1);
/*  87 */               this.memfree = (100L - this.memuse);
/*  88 */               this.startValues[0][this.i] = new Long(0L);
/*  89 */               this.endValues[0][this.i] = new Long(this.memuse);
/*  90 */               this.startValues[1][this.i] = new Long(0L);
/*  91 */               this.endValues[1][this.i] = new Long(this.memfree);
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/* 104 */               if (set != null) {
/* 105 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 109 */               e.printStackTrace();
/*     */             }
/*  72 */             this.i += 1;
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
/*     */           }
/*     */           catch (Exception exp)
/*     */           {
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
/*  98 */             exp.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/* 104 */               if (set != null) {
/* 105 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 109 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 116 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 120 */       e.printStackTrace();
/*     */     }
/*     */     
/* 123 */     this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, this.categories, this.startValues, this.endValues);
/* 124 */     return this.ds;
/*     */   }
/*     */   
/*     */ 
/*     */   private final long getMaxCollectionTimeFrom(String query)
/*     */   {
/* 130 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 131 */     ResultSet set = null;
/* 132 */     collectionTime = -1L;
/*     */     try
/*     */     {
/* 135 */       set = AMConnectionPool.executeQueryStmt(query);
/* 136 */       if (set.next()) {}
/*     */       
/* 138 */       return set.getLong(1);
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 142 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 148 */         if (set != null) {
/* 149 */           set.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 152 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 167 */     return "CpuGraph";
/*     */   }
/*     */   
/*     */   public String getHaid()
/*     */   {
/* 172 */     return this.haid;
/*     */   }
/*     */   
/*     */   public void setHaid(String hid)
/*     */   {
/* 177 */     this.haid = hid;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 181 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 185 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getAppname()
/*     */   {
/* 190 */     return this.appname;
/*     */   }
/*     */   
/*     */   public void setAppname(String ent)
/*     */   {
/* 195 */     this.appname = ent;
/*     */   }
/*     */   
/*     */   public String getEntity()
/*     */   {
/* 200 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent)
/*     */   {
/* 205 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public String generateToolTip(CategoryDataset arg0, int series, int arg2)
/*     */   {
/* 210 */     String Name = null;
/* 211 */     DefaultIntervalCategoryDataset ds1 = (DefaultIntervalCategoryDataset)arg0;
/* 212 */     String id = "10000012";
/* 213 */     String query = "select RESOURCENAME from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + id + "";
/*     */     try
/*     */     {
/* 216 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 217 */       if (this.rs.next())
/*     */       {
/* 219 */         Name = this.rs.getString(1);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 225 */       e.printStackTrace();
/*     */     }
/* 227 */     return "Resource " + Name;
/*     */   }
/*     */   
/*     */ 
/*     */   public String generateLink(Object dataset, int series, Object category)
/*     */   {
/* 233 */     String id = (String)category;
/* 234 */     String query = "select AM_ManagedObject.Resourcename,AM_ManagedObject.ResourceID from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.childID=" + id + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ManagedObject.Type ='Node'";
/*     */     try
/*     */     {
/* 237 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 238 */       if (this.rs.next())
/*     */       {
/* 240 */         String Name = this.rs.getString(1);
/* 241 */         int resId = this.rs.getInt(2);
/* 242 */         id = "HostResource.do?name=" + Name + "&resourceid=" + resId + "&haid=" + this.haid + "#Memory Utilization";
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 247 */       e.printStackTrace();
/*     */     }
/*     */     
/* 250 */     return id;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\snapshot\bean\HostMemoryGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */