/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
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
/*     */ public class HAAlertGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  38 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParam() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*     */     try
/*     */     {
/*  54 */       ArrayList haList = new ArrayList();
/*     */       
/*  56 */       String haidQuery = "SELECT RESOURCEID,RESOURCENAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0";
/*  57 */       ArrayList resList = this.mo.getRows(haidQuery);
/*  58 */       int resSize = resList.size();
/*  59 */       if (resSize == 0)
/*     */       {
/*  61 */         return null;
/*     */       }
/*     */       
/*  64 */       boolean criticalAlertsPresent = false;
/*  65 */       for (int i = 0; i < resSize; i++) {
/*  66 */         Properties prop = new Properties();
/*  67 */         ArrayList temp = (ArrayList)resList.get(i);
/*  68 */         String rid = (String)temp.get(0);
/*  69 */         String haName = (String)temp.get(1);
/*  70 */         Vector resourceids = DependantMOUtil.getDependantResourceIDS(rid);
/*     */         
/*     */ 
/*     */ 
/*  74 */         resourceids.remove(rid);
/*  75 */         String sevQuery = "select count(*) from Alert where GROUPNAME='AppManager' and " + DependantMOUtil.getCondition("SOURCE", resourceids) + " and SEVERITY =1";
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */         ArrayList alertCountList = this.mo.getRows(sevQuery);
/*  82 */         int tempSize = alertCountList.size();
/*  83 */         for (int j = 0; j < tempSize; j++) {
/*  84 */           ArrayList temp1 = (ArrayList)alertCountList.get(j);
/*  85 */           String sevCount = (String)temp1.get(0);
/*  86 */           Integer in = new Integer(sevCount);
/*  87 */           if (in.intValue() > 0)
/*     */           {
/*  89 */             if (!criticalAlertsPresent)
/*     */             {
/*  91 */               criticalAlertsPresent = true;
/*     */             }
/*     */           }
/*  94 */           prop.put("Critical", in);
/*     */         }
/*     */         
/*  97 */         prop.put("HAID", new Integer(rid));
/*  98 */         prop.setProperty("HANAME", haName);
/*     */         
/* 100 */         if (!prop.containsKey("Critical")) {
/* 101 */           prop.put("Critical", new Integer(0));
/*     */         }
/* 103 */         haList.add(prop);
/*     */       }
/* 105 */       if (!criticalAlertsPresent)
/*     */       {
/* 107 */         return null;
/*     */       }
/* 109 */       Properties[] propArr = (Properties[])haList.toArray(new Properties[0]);
/*     */       
/* 111 */       Arrays.sort(propArr, new ComparatorImpl());
/*     */       
/* 113 */       int categoryLength = 5;
/* 114 */       if (propArr.length < categoryLength)
/*     */       {
/* 116 */         categoryLength = propArr.length;
/*     */       }
/*     */       
/* 119 */       String[] categories = new String[categoryLength];
/*     */       
/*     */ 
/* 122 */       DefaultCategoryDataset result = new DefaultCategoryDataset();
/*     */       
/* 124 */       for (int i = 0; i < categories.length; i++) {
/* 125 */         Properties tempProp = propArr[i];
/* 126 */         categories[i] = tempProp.getProperty("HANAME");
/*     */         
/* 128 */         result.addValue((Integer)tempProp.get("Critical"), "", categories[i]);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 133 */       return result;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 137 */       e.printStackTrace();
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 149 */     return "DataProducer";
/*     */   }
/*     */   
/*     */   private final void closeResultSet(ResultSet set)
/*     */   {
/* 154 */     if (set != null)
/*     */     {
/*     */       try
/*     */       {
/* 158 */         set.close();
/*     */       }
/*     */       catch (Exception ex) {
/* 161 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   class ComparatorImpl
/*     */     implements Comparator
/*     */   {
/*     */     public ComparatorImpl() {}
/*     */     
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/* 174 */       Properties prop1 = (Properties)o1;
/* 175 */       Properties prop2 = (Properties)o2;
/*     */       
/* 177 */       int val1 = ((Integer)prop1.get("Critical")).intValue();
/* 178 */       int val2 = ((Integer)prop2.get("Critical")).intValue();
/*     */       
/* 180 */       if (val1 == val2)
/*     */       {
/* 182 */         return 0;
/*     */       }
/* 184 */       if (val1 > val2)
/*     */       {
/* 186 */         return -1;
/*     */       }
/*     */       
/*     */ 
/* 190 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 196 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\HAAlertGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */