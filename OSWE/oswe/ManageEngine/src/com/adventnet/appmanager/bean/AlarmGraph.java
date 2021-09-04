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
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlarmGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  20 */   private AMConnectionPool pool = null;
/*     */   private ResultSet rs;
/*     */   private DefaultPieDataset ds;
/*  23 */   private int total = 0;
/*  24 */   private String username = "";
/*     */   
/*  26 */   public AlarmGraph() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  31 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String v)
/*     */   {
/*  36 */     this.username = v;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException {
/*  40 */     this.ds = new DefaultPieDataset();
/*  41 */     for (int i = 1; i <= 5; i++)
/*     */     {
/*  43 */       if ((i != 2) && (i != 3))
/*     */       {
/*     */ 
/*     */ 
/*  47 */         String query = "select count(*) from Alert where SEVERITY = " + i + " and groupname ='AppManager'";
/*     */         
/*  49 */         if ((this.username != null) && (!this.username.equals("")))
/*     */         {
/*  51 */           String qry1 = "";
/*  52 */           String qry2 = "";
/*  53 */           StringBuffer resid = new StringBuffer();
/*  54 */           qry1 = "select RESOURCEID from AM_UserPasswordTable,AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION where AM_UserPasswordTable.username='" + this.username + "' and AM_UserPasswordTable.userid=AM_HOLISTICAPPLICATION_OWNERS.ownerid and AM_HOLISTICAPPLICATION_OWNERS.haid=AM_PARENTCHILDMAPPER.parentid and AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and  AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID";
/*  55 */           qry2 = "select RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + this.username + "' order by RESOURCEID";
/*  56 */           ResultSet rs1 = null;
/*  57 */           ResultSet rs2 = null;
/*     */           try {
/*  59 */             rs1 = AMConnectionPool.executeQueryStmt(qry1);
/*  60 */             while (rs1.next())
/*     */             {
/*  62 */               resid.append(rs1.getString("RESOURCEID") + ", ");
/*     */             }
/*  64 */             rs2 = AMConnectionPool.executeQueryStmt(qry2);
/*  65 */             while (rs2.next())
/*     */             {
/*  67 */               resid.append(rs2.getString("RESOURCEID") + ", ");
/*     */             }
/*  69 */             rs1.close();
/*  70 */             rs2.close();
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/*  74 */             ex.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/*  78 */             AMConnectionPool.closeStatement(rs1);
/*  79 */             AMConnectionPool.closeStatement(rs2);
/*     */           }
/*  81 */           resid.deleteCharAt(resid.lastIndexOf(","));
/*  82 */           query = "select count(*) from Alert where SEVERITY = " + i + " and groupname='AppManager' and source in (" + resid + ")";
/*     */         }
/*     */         try {
/*  85 */           this.rs = AMConnectionPool.executeQueryStmt(query);
/*  86 */           if (this.rs.next())
/*     */           {
/*  88 */             this.ds.setValue(getAlert(i), this.rs.getLong(1));
/*     */           }
/*     */           
/*     */ 
/*  92 */           this.rs.close();
/*     */         }
/*     */         catch (Exception e) {
/*  95 */           e.printStackTrace();
/*     */         }
/*     */       } }
/*  98 */     return this.ds;
/*     */   }
/*     */   
/* 101 */   public boolean hasExpired(Map params, Date since) { return true; }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 105 */     return "availabilitygraph";
/*     */   }
/*     */   
/*     */   public String generateLink(Object dataset, Object category) {
/* 109 */     String severity = (String)category;
/* 110 */     if (severity.equals("Critical")) {
/* 111 */       return "javascript:open('/fault/AlarmView.do?displayName=Critical Alerts&viewId=Alerts&severity=1')";
/*     */     }
/* 113 */     if (severity.equals("Major")) {
/* 114 */       return "javascript:open('/fault/AlarmView.do?displayName=Minor Alerts&viewId=Alerts&severity=2')";
/*     */     }
/* 116 */     if (severity.equals("Minor")) {
/* 117 */       return "javascript:open('/fault/AlarmView.do?displayName=Major Alerts&viewId=Alerts&severity=3')";
/*     */     }
/* 119 */     if (severity.equals("Warning")) {
/* 120 */       return "javascript:open('/fault/AlarmView.do?displayName=Warning Alerts&viewId=Alerts&severity=4')";
/*     */     }
/*     */     
/* 123 */     return "javascript:open('/fault/AlarmView.do?displayName=Clear Alerts&viewId=Alerts&severity=5')";
/*     */   }
/*     */   
/*     */   public String getAlert(int i) {
/* 127 */     if (i == 1)
/* 128 */       return FormatUtil.getString("Critical");
/* 129 */     if (i == 2)
/* 130 */       return "Major";
/* 131 */     if (i == 3)
/* 132 */       return "Minor";
/* 133 */     if (i == 4) {
/* 134 */       return FormatUtil.getString("Warning");
/*     */     }
/* 136 */     return FormatUtil.getString("Clear");
/*     */   }
/*     */   
/* 139 */   public int getTotalCount() { String query = "select count(*) from Alert";
/*     */     try {
/* 141 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 142 */       if (this.rs.next())
/*     */       {
/* 144 */         this.total = this.rs.getInt(1);
/*     */       }
/* 146 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 149 */       e.printStackTrace();
/*     */     }
/* 151 */     return this.total;
/*     */   }
/*     */   
/*     */   public String generateToolTip(PieDataset dataset, Comparable section, int index)
/*     */   {
/* 156 */     return section + " : " + dataset.getValue(section);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\AlarmGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */