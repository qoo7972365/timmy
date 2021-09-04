/*     */ package com.adventnet.appmanager.server.tomcat.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TomcatResponseBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  20 */   private String resourcename = "default";
/*     */   private AMConnectionPool pool;
/*     */   Properties prop;
/*  23 */   DefaultPieDataset ds = null;
/*     */   private ResultSet rs;
/*     */   private int resourceid;
/*     */   private String version;
/*     */   
/*     */   public void setresourceID(int id)
/*     */   {
/*  30 */     this.resourceid = id;
/*     */   }
/*     */   
/*     */   public int getresourceID() {
/*  34 */     return this.resourceid;
/*     */   }
/*     */   
/*     */   public void setVersion(String ver)
/*     */   {
/*  39 */     this.version = ver;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  43 */     return this.version;
/*     */   }
/*     */   
/*  46 */   public TomcatResponseBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  51 */     this.ds = new DefaultPieDataset();
/*  52 */     String query = "select max(COLLECTIONTIME) from AM_TOMCATRESPONSESTATS  where  AM_TOMCATRESPONSESTATS.RESOURCEID=" + getresourceID() + "";
/*     */     try {
/*  54 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  55 */       long time = 0L;
/*  56 */       if (this.rs.next()) {
/*  57 */         time = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  62 */       this.rs.close();
/*  63 */       String query1 = "select * from AM_TOMCATRESPONSESTATS where COLLECTIONTIME=" + time + " and RESOURCEID=" + getresourceID() + "";
/*  64 */       this.rs = AMConnectionPool.executeQueryStmt(query1);
/*  65 */       Properties p = new Properties();
/*  66 */       if (this.rs.next()) {
/*  67 */         if ((getVersion().equals("5")) || (getVersion().equals("6")) || (getVersion().equals("7")))
/*     */         {
/*  69 */           p.put("1XX", new Long(this.rs.getLong("RESPONSE2XX") + this.rs.getLong("RESPONSE4XX")));
/*  70 */           long error = 0L;
/*     */           try
/*     */           {
/*  73 */             error = this.rs.getLong("RESPONSE4XX") * 100L / (this.rs.getLong("RESPONSE2XX") + this.rs.getLong("RESPONSE4XX"));
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*  77 */             error = 0L;
/*     */           }
/*  79 */           p.put("Error%", new Long(error));
/*     */         }
/*     */         else
/*     */         {
/*  83 */           p.put("1XX", new Long(this.rs.getLong("RESPONSE1XX")));
/*     */         }
/*  85 */         p.put("2XX", new Long(this.rs.getLong("RESPONSE2XX")));
/*  86 */         p.put("3XX", new Long(this.rs.getLong("RESPONSE3XX")));
/*  87 */         p.put("4XX", new Long(this.rs.getLong("RESPONSE4XX")));
/*  88 */         p.put("5XX", new Long(this.rs.getLong("RESPONSE5XX")));
/*     */         
/*  90 */         if ((getVersion().equals("5")) || (getVersion().equals("6")) || (getVersion().equals("7")))
/*     */         {
/*  92 */           this.ds.setValue(FormatUtil.getString("Successful Requests"), ((Long)p.get("2XX")).longValue());
/*  93 */           this.ds.setValue(FormatUtil.getString("Errors"), ((Long)p.get("4XX")).longValue());
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*  98 */         else if ((((Long)p.get("1XX")).longValue() != 0L) || (((Long)p.get("2XX")).longValue() != 0L) || (((Long)p.get("3XX")).longValue() != 0L) || (((Long)p.get("4XX")).longValue() != 0L) || (((Long)p.get("5XX")).longValue() != 0L)) {
/*  99 */           this.ds.setValue("1XX", ((Long)p.get("1XX")).longValue());
/* 100 */           this.ds.setValue("2XX", ((Long)p.get("2XX")).longValue());
/* 101 */           this.ds.setValue("3XX", ((Long)p.get("3XX")).longValue());
/* 102 */           this.ds.setValue("4XX", ((Long)p.get("4XX")).longValue());
/* 103 */           this.ds.setValue("5XX", ((Long)p.get("5XX")).longValue());
/*     */         }
/*     */         else {
/* 106 */           this.ds = null;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */       setProperties(p);
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
/* 130 */       return this.ds;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 117 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 123 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 142 */     return "tomcatgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 147 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 151 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public Properties getProperties() {
/* 155 */     if (this.prop == null) {
/* 156 */       return new Properties();
/*     */     }
/* 158 */     return this.prop;
/*     */   }
/*     */   
/* 161 */   public void setProperties(Properties p) { this.prop = p; }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\tomcat\bean\TomcatResponseBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */