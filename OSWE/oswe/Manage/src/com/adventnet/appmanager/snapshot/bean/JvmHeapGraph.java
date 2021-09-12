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
/*     */ public class JvmHeapGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  28 */   private String resID = null;
/*  29 */   private String appname = null;
/*  30 */   private String haid = null;
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet free;
/*     */   private ResultSet used;
/*     */   private int i;
/*  36 */   final String[] seriesNames = { "Heap Used", "Heap Free" };
/*     */   DefaultIntervalCategoryDataset ds;
/*     */   
/*     */   public JvmHeapGraph() {
/*  40 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  45 */     long maxtime = 0L;
/*  46 */     long heapfree = 0L;
/*  47 */     long heapused = 0L;
/*  48 */     long totalspace = 0L;
/*  49 */     Vector resourcecount = new Vector();
/*  50 */     String query = "select AM_ManagedObject.ResourceId from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=" + this.haid + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and (ManagedObject.Type ='WEBLOGIC-server' or ManagedObject.Type ='JBOSS-server' or ManagedObject.Type ='Tomcat-server')";
/*     */     try {
/*  52 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  56 */       e.printStackTrace();
/*     */     }
/*     */     
/*  59 */     String[] categories = null;
/*  60 */     Long[][] startValues = (Long[][])null;
/*  61 */     Long[][] endValues = (Long[][])null;
/*     */     try
/*     */     {
/*  64 */       while (this.rs.next())
/*     */       {
/*     */ 
/*  67 */         this.resID = this.rs.getString(1);
/*  68 */         resourcecount.add(this.resID);
/*     */       }
/*     */       
/*     */ 
/*  72 */       categories = new String[resourcecount.size()];
/*  73 */       startValues = new Long[this.seriesNames.length][categories.length];
/*  74 */       endValues = new Long[this.seriesNames.length][categories.length];
/*  75 */       for (this.i = 0; this.i < resourcecount.size();)
/*     */       {
/*  77 */         categories[this.i] = ((String)resourcecount.get(this.i));
/*  78 */         long collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JVMData where ID=" + resourcecount.get(this.i) + "");
/*  79 */         if (collectionTime != -1L)
/*     */         {
/*  81 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  82 */           query = "select * from AM_JVMData where COLLECTIONTIME=" + collectionTime + " and ID=" + resourcecount.get(this.i) + "";
/*  83 */           ResultSet set = null;
/*  84 */           long heapFree = -1L;
/*  85 */           long heapUsed = -1L;
/*     */           try
/*     */           {
/*  88 */             set = AMConnectionPool.executeQueryStmt(query);
/*  89 */             if (set.next())
/*     */             {
/*  91 */               heapUsed = set.getLong(2);
/*  92 */               heapFree = set.getLong(3);
/*  93 */               long totalSize = heapUsed + heapFree;
/*  94 */               long used = heapUsed * 100L / totalSize;
/*  95 */               long free = heapFree * 100L / totalSize;
/*  96 */               startValues[0][this.i] = new Long(0L);
/*  97 */               endValues[0][this.i] = new Long(used);
/*  98 */               startValues[1][this.i] = new Long(0L);
/*  99 */               endValues[1][this.i] = new Long(free);
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
/* 112 */               if (set != null) {
/* 113 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 117 */               e.printStackTrace();
/*     */             }
/*  75 */             this.i += 1;
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
/*     */ 
/*     */ 
/* 106 */             exp.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/* 112 */               if (set != null) {
/* 113 */                 set.close();
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 117 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 125 */       this.rs.close();
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */ 
/* 131 */       e.printStackTrace();
/*     */     }
/*     */     
/* 134 */     this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, categories, startValues, endValues);
/* 135 */     return this.ds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 142 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 147 */     return "tablespacegraph";
/*     */   }
/*     */   
/*     */   private final long getMaxCollectionTimeFrom(String query)
/*     */   {
/* 152 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 153 */     ResultSet set = null;
/* 154 */     collectionTime = -1L;
/*     */     try
/*     */     {
/* 157 */       set = AMConnectionPool.executeQueryStmt(query);
/* 158 */       if (set.next()) {}
/*     */       
/* 160 */       return set.getLong(1);
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 164 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 170 */         if (set != null) {
/* 171 */           set.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 174 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getAppname()
/*     */   {
/* 182 */     return this.appname;
/*     */   }
/*     */   
/*     */   public void setAppname(String ent)
/*     */   {
/* 187 */     this.appname = ent;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getHaid()
/*     */   {
/* 193 */     return this.haid;
/*     */   }
/*     */   
/*     */   public void setHaid(String hid)
/*     */   {
/* 198 */     this.haid = hid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String generateLink(Object dataset, int series, Object category)
/*     */   {
/* 205 */     String id = (String)category;
/*     */     
/* 207 */     String query = "select AM_ManagedObject.Type,AM_ManagedObject.Resourcename from ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.childID=" + id + " and ManagedObject.name=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and (ManagedObject.Type ='WEBLOGIC-server' or ManagedObject.Type ='JBOSS-server' or ManagedObject.Type ='Tomcat-server')";
/*     */     try
/*     */     {
/* 210 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 211 */       if (this.rs.next())
/*     */       {
/* 213 */         String Type = this.rs.getString(1);
/* 214 */         String Name = this.rs.getString(2);
/* 215 */         if (Type.equals("WEBLOGIC-server"))
/*     */         {
/* 217 */           id = "showresource.do?resourceid=" + id + "&resourcename=" + Name + "&haid=" + this.haid + "&type=WEBLOGIC-server&method=showdetails&name=" + this.appname + "";
/*     */         }
/*     */         
/* 220 */         if (Type.equals("JBOSS-server"))
/*     */         {
/* 222 */           id = "showresource.do?resourceid=" + id + "&resourcename=" + Name + "&haid=" + this.haid + "&type=JBOSS-server&method=showdetails&name=" + this.appname + "#JVM";
/*     */         }
/* 224 */         if (Type.equals("Tomcat-server"))
/*     */         {
/* 226 */           id = "showresource.do?resourceid=" + id + "&moname=" + Name + "&haid=" + this.haid + "&type=Tomcat-server&method=showdetails&name=" + this.appname + "";
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 232 */       e.printStackTrace();
/*     */     }
/*     */     
/* 235 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */   public String generateToolTip(CategoryDataset arg0, int series, int arg2)
/*     */   {
/* 241 */     String Name = null;
/* 242 */     DefaultIntervalCategoryDataset ds1 = (DefaultIntervalCategoryDataset)arg0;
/* 243 */     String id = "1212112";
/* 244 */     String query = "select RESOURCENAME from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + id + "";
/*     */     try
/*     */     {
/* 247 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 248 */       if (this.rs.next())
/*     */       {
/* 250 */         Name = this.rs.getString(1);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 256 */       e.printStackTrace();
/*     */     }
/* 258 */     return "Resource " + Name;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\snapshot\bean\JvmHeapGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */