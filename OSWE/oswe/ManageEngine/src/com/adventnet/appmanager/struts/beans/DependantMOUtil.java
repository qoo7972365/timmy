/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DependantMOUtil
/*     */ {
/*     */   public static Vector getDependantResourceIDS(String resourceid)
/*     */   {
/*  21 */     return getDependantResourceIDS(resourceid, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public static Vector getDependantResourceIDS(String resourceid, boolean removeHaid)
/*     */   {
/*  27 */     Vector v = new Vector();
/*     */     try
/*     */     {
/*  30 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  31 */       if (!removeHaid)
/*     */       {
/*  33 */         v.add(resourceid);
/*     */       }
/*  35 */       Vector tempvector = new Vector();
/*  36 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select CHILDID from AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + "");
/*  37 */       while (rs.next())
/*     */       {
/*  39 */         v.add(String.valueOf(rs.getInt("CHILDID")));
/*  40 */         tempvector.add(String.valueOf(rs.getInt("CHILDID")));
/*     */       }
/*  42 */       rs.close();
/*     */       
/*     */ 
/*     */ 
/*  46 */       return v;
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
/*     */     }
/*     */     catch (Exception e)
/*     */     {
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
/*  78 */       e.printStackTrace();
/*     */     }
/*  80 */     return v;
/*     */   }
/*     */   
/*     */   public static Vector getDependantResourceIDS(Vector v) {
/*     */     try {
/*  85 */       ArrayList haids = new ArrayList(v);
/*  86 */       ArrayList alldata = ReportUtilities.getStructuredDataForMonitorGroup(haids);
/*     */       
/*  88 */       v = new Vector();
/*  89 */       for (int i = 0; i < alldata.size(); i++) {
/*  90 */         ArrayList listval = (ArrayList)alldata.get(i);
/*  91 */         if (listval.size() > 0) {
/*  92 */           for (int s = 0; s < listval.size(); s++) {
/*  93 */             ArrayList insidearray = (ArrayList)listval.get(s);
/*  94 */             String rid = insidearray.get(0).toString();
/*  95 */             v.add(rid);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 101 */       ex.printStackTrace();
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */     return v;
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getCondition(String column, Vector v)
/*     */   {
/* 156 */     StringBuffer condition = new StringBuffer(column + " in (");
/*     */     
/* 158 */     if (v == null)
/*     */     {
/* 160 */       return column + " in (-1) ";
/*     */     }
/* 162 */     if ((v != null) && (v.size() == 0))
/*     */     {
/* 164 */       return column + " in (-1) ";
/*     */     }
/*     */     
/*     */ 
/* 168 */     for (int i = 0; i < v.size(); i++)
/*     */     {
/* 170 */       if (i + 1 == v.size())
/*     */       {
/*     */ 
/* 173 */         condition.append(v.get(i) + ")");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 179 */         condition.append(v.get(i) + ",");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 184 */     return condition.toString();
/*     */   }
/*     */   
/*     */   public static Vector getDependantResourceIDSforOperator(String resourceid, boolean removeHaid, String userName)
/*     */   {
/* 189 */     Vector v = new Vector();
/*     */     try
/*     */     {
/* 192 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 193 */       if (!removeHaid)
/*     */       {
/* 195 */         v.add(resourceid);
/*     */       }
/* 197 */       Vector tempvector = new Vector();
/* 198 */       ResultSet rs = AMConnectionPool.executeQueryStmt("select CHILDID from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION_OWNERS,AM_HOLISTICAPPLICATION,AM_UserPasswordTable where AM_UserPasswordTable.USERNAME='" + userName + "' AND AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID AND (AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_PARENTCHILDMAPPER.PARENTID OR AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_PARENTCHILDMAPPER.CHILDID) AND AM_PARENTCHILDMAPPER.PARENTID=" + resourceid);
/* 199 */       while (rs.next())
/*     */       {
/* 201 */         v.add(String.valueOf(rs.getInt("CHILDID")));
/* 202 */         tempvector.add(String.valueOf(rs.getInt("CHILDID")));
/*     */       }
/* 204 */       rs.close();
/*     */ 
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 209 */       exc.printStackTrace();
/*     */     }
/* 211 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\DependantMOUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */