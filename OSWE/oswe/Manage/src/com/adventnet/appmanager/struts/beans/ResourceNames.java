/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class ResourceNames
/*     */ {
/*  13 */   private Hashtable table = new Hashtable();
/*  14 */   private Hashtable resourcetypes = new Hashtable();
/*  15 */   private Hashtable resourceDisplay = new Hashtable();
/*  16 */   private Hashtable images = null;
/*     */   
/*     */   public ResourceNames() {
/*  19 */     initialize(null);
/*     */   }
/*     */   
/*     */   public ResourceNames(String sourceList) {
/*  23 */     initialize(sourceList);
/*     */   }
/*     */   
/*     */   private void initialize(String sourceList)
/*     */   {
/*  28 */     String query = null;
/*     */     try {
/*  30 */       ArrayList rows = null;
/*  31 */       ManagedApplication mo = new ManagedApplication();
/*     */       
/*  33 */       if (sourceList != null) {
/*  34 */         query = "select RESOURCEID,DISPLAYNAME,type from AM_ManagedObject where resourceid in(" + sourceList + ")";
/*     */       } else {
/*  36 */         query = "select source,AM_ManagedObject.DISPLAYNAME,type from Alert , AM_ManagedObject where  resourceid=source";
/*     */       }
/*  38 */       rows = mo.getRows(query);
/*     */       
/*  40 */       HashSet hashSet = new HashSet(rows);
/*  41 */       rows = new ArrayList(hashSet);
/*     */       
/*  43 */       ArrayList rows2 = mo.getRows("select RESOURCETYPE,IMAGEPATH,DISPLAYNAME from AM_ManagedResourceType");
/*  44 */       this.table = new Hashtable(rows.size());
/*     */       
/*  46 */       for (int i = 0; i < rows.size(); i++)
/*     */       {
/*  48 */         ArrayList row = (ArrayList)rows.get(i);
/*  49 */         this.table.put(row.get(0), row.get(1));
/*  50 */         this.resourcetypes.put(row.get(0), row.get(2));
/*     */       }
/*     */       
/*     */ 
/*  54 */       this.images = new Hashtable(rows2.size());
/*  55 */       for (int i = 0; i < rows2.size(); i++)
/*     */       {
/*  57 */         ArrayList row = (ArrayList)rows2.get(i);
/*  58 */         if (row.get(1) != null)
/*     */         {
/*  60 */           this.images.put(row.get(0), row.get(1));
/*     */         }
/*     */         else
/*     */         {
/*  64 */           this.images.put(row.get(0), "/images/icon_monitors_unknown.gif");
/*     */         }
/*  66 */         this.resourceDisplay.put(row.get(0), row.get(2));
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*  72 */       ee.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getResourceName(String resourceid)
/*     */   {
/*  78 */     Object resourcename = this.table.get(resourceid);
/*  79 */     return (String)resourcename;
/*     */   }
/*     */   
/*     */   public String getResourceType(String resourceid) {
/*  83 */     Object type = this.resourcetypes.get(resourceid);
/*  84 */     return (String)type;
/*     */   }
/*     */   
/*     */   public String getResourceImage(String resourcetype)
/*     */   {
/*  89 */     Object type = this.images.get(resourcetype);
/*  90 */     return (String)type;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getGroupType(String resourceid)
/*     */   {
/*  96 */     String groupType = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*  97 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  98 */     ResultSet rs = null;
/*  99 */     String query = null;
/*     */     try
/*     */     {
/* 102 */       query = "select TYPE from AM_HOLISTICAPPLICATION where TYPE=1 and AM_HOLISTICAPPLICATION.HAID ='" + resourceid + "'";
/* 103 */       rs = AMConnectionPool.executeQueryStmt(query);
/*     */       
/* 105 */       if (rs.next())
/*     */       {
/* 107 */         groupType = FormatUtil.getString("am.webclient.reports.excel.subgroup.text");
/*     */       }
/* 109 */       rs.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 113 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 116 */     return groupType;
/*     */   }
/*     */   
/*     */   public String getResourceTypeDisplayName(String resourcetype) {
/* 120 */     Object disp = this.resourceDisplay.get(resourcetype);
/* 121 */     if (resourcetype.equals("file")) {
/* 122 */       resourcetype = "Files";
/* 123 */       return resourcetype;
/*     */     }
/* 125 */     if (resourcetype.equals("directory")) {
/* 126 */       resourcetype = "Directories";
/* 127 */       return resourcetype;
/*     */     }
/*     */     
/*     */ 
/* 131 */     return (String)disp;
/*     */   }
/*     */   
/*     */ 
/*     */   public Hashtable getImages()
/*     */   {
/* 137 */     return this.images;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\ResourceNames.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */