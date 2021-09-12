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
/*     */ public class HostCpuGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*     */   private AMConnectionPool pool;
/*  28 */   long cpuuse = 0L;
/*  29 */   long cpufree = 0L;
/*  30 */   private String category = "default";
/*     */   private ResultSet rs;
/*     */   private int i;
/*  33 */   private String appname = null;
/*     */   private String haid;
/*  35 */   private TimeSeries ts = null;
/*  36 */   private Vector HostResourceCount = new Vector();
/*  37 */   private Vector HostResourceId = new Vector();
/*  38 */   final String[] seriesNames = { "Cpu Used", "Cpu Free" };
/*     */   DefaultIntervalCategoryDataset ds;
/*  40 */   String[] categories = null;
/*  41 */   Long[][] startValues = (Long[][])null;
/*  42 */   Long[][] endValues = (Long[][])null;
/*     */   
/*     */   public HostCpuGraph()
/*     */   {
/*  46 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  51 */     String mainQuery = "select AM_ManagedObject.ResourceName, AM_ManagedObject.ResourceId from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=" + this.haid + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ManagedObject.Type ='Node'";
/*     */     try
/*     */     {
/*  54 */       this.rs = AMConnectionPool.executeQueryStmt(mainQuery);
/*  55 */       while (this.rs.next())
/*     */       {
/*     */ 
/*  58 */         String hostname = this.rs.getString(1);
/*  59 */         this.HostResourceCount.add(hostname);
/*  60 */         String hostid = this.rs.getString(2);
/*  61 */         this.HostResourceId.add(hostid);
/*     */       }
/*     */       
/*     */ 
/*  65 */       this.categories = new String[this.HostResourceCount.size()];
/*  66 */       this.startValues = new Long[this.seriesNames.length][this.categories.length];
/*  67 */       this.endValues = new Long[this.seriesNames.length][this.categories.length];
/*  68 */       for (this.i = 0; this.i < this.HostResourceCount.size();)
/*     */       {
/*  70 */         this.categories[this.i] = ((String)this.HostResourceId.get(this.i));
/*  71 */         long collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from HostDataCollected where RESOURCENAME like '%" + (String)this.HostResourceCount.get(this.i) + "%'");
/*  72 */         if (collectionTime != -1L)
/*     */         {
/*  74 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  75 */           String query = "select CURVALUE from HostDataCollected where RESOURCENAME like '%" + (String)this.HostResourceCount.get(this.i) + "%' and CATEGORY like '%" + getCategory() + "%' and CollectionTime = " + collectionTime + "";
/*  76 */           ResultSet set = null;
/*     */           try
/*     */           {
/*  79 */             set = AMConnectionPool.executeQueryStmt(query);
/*  80 */             if (set.next())
/*     */             {
/*  82 */               this.cpuuse = set.getLong(1);
/*  83 */               this.cpufree = (100L - this.cpuuse);
/*  84 */               this.startValues[0][this.i] = new Long(0L);
/*  85 */               this.endValues[0][this.i] = new Long(this.cpuuse);
/*  86 */               this.startValues[1][this.i] = new Long(0L);
/*  87 */               this.endValues[1][this.i] = new Long(this.cpufree);
/*     */             }
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
/*  99 */               if (set != null) {
/* 100 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 104 */               e.printStackTrace();
/*     */             }
/*  68 */             this.i += 1;
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
/*  93 */             exp.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/*  99 */               if (set != null) {
/* 100 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 104 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 111 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 115 */       e.printStackTrace();
/*     */     }
/*     */     
/* 118 */     this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, this.categories, this.startValues, this.endValues);
/* 119 */     return this.ds;
/*     */   }
/*     */   
/*     */ 
/*     */   private final long getMaxCollectionTimeFrom(String query)
/*     */   {
/* 125 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 126 */     ResultSet set = null;
/* 127 */     collectionTime = -1L;
/*     */     try
/*     */     {
/* 130 */       set = AMConnectionPool.executeQueryStmt(query);
/* 131 */       if (set.next()) {}
/*     */       
/* 133 */       return set.getLong(1);
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 137 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 143 */         if (set != null) {
/* 144 */           set.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 147 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 157 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 162 */     return "CpuGraph";
/*     */   }
/*     */   
/*     */   public String getHaid()
/*     */   {
/* 167 */     return this.haid;
/*     */   }
/*     */   
/*     */   public void setHaid(String hid)
/*     */   {
/* 172 */     this.haid = hid;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 176 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 180 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getAppname()
/*     */   {
/* 185 */     return this.appname;
/*     */   }
/*     */   
/*     */   public void setAppname(String ent)
/*     */   {
/* 190 */     this.appname = ent;
/*     */   }
/*     */   
/*     */ 
/*     */   public String generateToolTip(CategoryDataset arg0, int series, int arg2)
/*     */   {
/* 196 */     String Name = null;
/* 197 */     DefaultIntervalCategoryDataset ds1 = (DefaultIntervalCategoryDataset)arg0;
/* 198 */     String id = "10000012";
/* 199 */     String query = "select RESOURCENAME from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + id + "";
/*     */     try
/*     */     {
/* 202 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 203 */       if (this.rs.next())
/*     */       {
/* 205 */         Name = this.rs.getString(1);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 211 */       e.printStackTrace();
/*     */     }
/* 213 */     return "Resource " + Name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String generateLink(Object dataset, int series, Object category)
/*     */   {
/* 221 */     String id = (String)category;
/*     */     
/* 223 */     String query = "select AM_ManagedObject.Resourcename,AM_ManagedObject.ResourceID from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.childID=" + id + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and ManagedObject.Type ='Node'";
/*     */     try
/*     */     {
/* 226 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 227 */       if (this.rs.next())
/*     */       {
/* 229 */         String Name = this.rs.getString(1);
/* 230 */         int resId = this.rs.getInt(2);
/* 231 */         id = "HostResource.do?name=" + Name + "&haid=" + this.haid + "&resourceid=" + resId + "#Cpu Utilization";
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 236 */       e.printStackTrace();
/*     */     }
/*     */     
/* 239 */     return id;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\snapshot\bean\HostCpuGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */