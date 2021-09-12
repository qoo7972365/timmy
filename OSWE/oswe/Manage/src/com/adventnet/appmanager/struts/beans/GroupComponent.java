/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class GroupComponent
/*     */ {
/*  12 */   private static HashMap mointorGroupTypes = new HashMap();
/*  13 */   private static HashMap mointorGroupLabels = new HashMap();
/*  14 */   private static HashMap mgTypeMapping = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  20 */   static { intialize(); }
/*     */   
/*  22 */   private int subgroupType = 1;
/*     */   private String groupName;
/*     */   private int resourceid;
/*  25 */   private int noOfChilds = 0;
/*  26 */   private int contains = 0;
/*     */   
/*     */   public static HashMap getMointorGroupTypes() {
/*  29 */     return mointorGroupTypes;
/*     */   }
/*     */   
/*     */   public static void intialize() {
/*  33 */     ResultSet rs = null;
/*  34 */     ResultSet rs1 = null;
/*     */     try
/*     */     {
/*  37 */       AMConnectionPool con = new AMConnectionPool();
/*  38 */       if (Constants.sqlManager) {
/*  39 */         rs = AMConnectionPool.executeQueryStmt("select TYPEID,TYPE,DISPLAYNAME from  AM_MONITORGROUP_TYPES where TYPEID in (1,1004,1005)");
/*     */       } else {
/*  41 */         rs = AMConnectionPool.executeQueryStmt("select TYPEID,TYPE,DISPLAYNAME from  AM_MONITORGROUP_TYPES");
/*     */       }
/*  43 */       while (rs.next())
/*     */       {
/*  45 */         String type = rs.getString("TYPE");
/*  46 */         String typeid = rs.getString("TYPEID");
/*  47 */         String displayname = rs.getString("DISPLAYNAME");
/*  48 */         mointorGroupTypes.put(type, typeid);
/*  49 */         mointorGroupLabels.put(typeid, com.adventnet.appmanager.util.FormatUtil.getString(displayname));
/*     */       }
/*  51 */       if (Constants.sqlManager) {
/*  52 */         rs1 = AMConnectionPool.executeQueryStmt("select PARENTID,CHILDID from AM_MONITORGROUP_TYPE_MAPPING where PARENTID in (1,1004,1005) and CHILDID in (1,1004,1005) order by PARENTID,CHILDID");
/*     */       } else {
/*  54 */         rs1 = AMConnectionPool.executeQueryStmt("select PARENTID,CHILDID from   AM_MONITORGROUP_TYPE_MAPPING order by PARENTID,CHILDID");
/*     */       }
/*  56 */       HashMap<String, ArrayList> allMappings = new HashMap();
/*  57 */       while (rs1.next())
/*     */       {
/*  59 */         String parentid = rs1.getString("PARENTID");
/*  60 */         String childid = rs1.getString("CHILDID");
/*  61 */         ArrayList<String> maplist = null;
/*  62 */         if (allMappings.get(parentid) == null)
/*     */         {
/*  64 */           maplist = new ArrayList();
/*  65 */           allMappings.put(parentid, maplist);
/*     */         }
/*     */         else
/*     */         {
/*  69 */           maplist = (ArrayList)allMappings.get(parentid);
/*     */         }
/*  71 */         maplist.add(childid);
/*     */       }
/*  73 */       Set keyset = allMappings.keySet();
/*     */       
/*  75 */       for (Object key : keyset)
/*     */       {
/*  77 */         ArrayList<String> maplist = (ArrayList)allMappings.get(key);
/*  78 */         String[] chilarry = new String[maplist.size()];
/*  79 */         for (int i = 0; i < maplist.size(); i++)
/*     */         {
/*  81 */           chilarry[i] = ((String)maplist.get(i));
/*     */         }
/*  83 */         mgTypeMapping.put(key, chilarry);
/*     */       }
/*     */       return;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  89 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/*  94 */         if (rs != null)
/*     */         {
/*  96 */           rs.close();
/*     */         }
/*  98 */         if (rs1 != null)
/*     */         {
/* 100 */           rs1.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public static HashMap getMointorGroupLabels() {
/* 108 */     return mointorGroupLabels;
/*     */   }
/*     */   
/*     */   public static HashMap getMgTypeMapping() {
/* 112 */     return mgTypeMapping;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HashMap getSupportedTopLevelGroups()
/*     */   {
/* 122 */     HashMap temp = new HashMap();
/* 123 */     temp.put("monitorgroup", "1");
/* 124 */     if (Constants.sqlManager) {
/* 125 */       temp.put("databasegroup", "1004");
/*     */     } else {
/* 127 */       temp.put("webappgroup", "2");
/*     */     }
/*     */     
/* 130 */     return temp;
/*     */   }
/*     */   
/*     */   public static HashMap getResourceTypeToSelect(String type) {
/* 134 */     HashMap toreturn = Constants.getResourceTypeToSelect(type);
/* 135 */     return toreturn;
/*     */   }
/*     */   
/*     */   public static String getResourceTypeCondition(String type)
/*     */   {
/* 140 */     if (type.equals("1003"))
/*     */     {
/* 142 */       return " and  RESOURCEGROUP not in ('DBS') ";
/*     */     }
/* 144 */     if ((type.equals("1004")) || (type.equals("1005")))
/*     */     {
/* 146 */       return " and  RESOURCEGROUP not in ('APP') ";
/*     */     }
/* 148 */     if (type.equals("1006"))
/*     */     {
/* 150 */       return " and  RESOURCEGROUP not in ('APP','DBS') ";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 155 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean isServerToBeAssociated(String groupType)
/*     */   {
/* 161 */     if ((groupType.equals("1003")) || (groupType.equals("1004")) || (groupType.equals("1005")))
/*     */     {
/* 163 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   public static String getGroupType(String haid)
/*     */   {
/* 172 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 173 */     groupType = "1";
/* 174 */     ResultSet typers = null;
/*     */     try {
/* 176 */       typers = AMConnectionPool.executeQueryStmt("select GROUPTYPE from AM_HOLISTICAPPLICATION where HAID=" + haid);
/* 177 */       if (typers.next()) {}
/*     */       
/* 179 */       return typers.getString("GROUPTYPE");
/*     */ 
/*     */     }
/*     */     catch (Exception e) {}finally
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 187 */         if (typers != null)
/*     */         {
/* 189 */           typers.close();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] getSupportedChildGroupsfortopLevelGroup(String type)
/*     */   {
/* 203 */     if (type.equals("2"))
/*     */     {
/* 205 */       return new String[] { "1001", "1007", "1008", "1002", "1003", "1004", "1006" };
/*     */     }
/*     */     
/*     */ 
/* 209 */     return getSupportedChildGroups(type);
/*     */   }
/*     */   
/*     */ 
/*     */   public static String[] getSupportedChildGroups(String type)
/*     */   {
/* 215 */     if (mgTypeMapping.get(type) != null)
/*     */     {
/* 217 */       return (String[])mgTypeMapping.get(type);
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isCluster(String type)
/*     */   {
/* 224 */     if (mgTypeMapping.get(type) != null)
/*     */     {
/* 226 */       return false;
/*     */     }
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   public static String getDefaultClusterType(String componentType)
/*     */   {
/* 233 */     if (mgTypeMapping.get(componentType) != null)
/*     */     {
/* 235 */       if ("1004".equals(componentType))
/*     */       {
/* 237 */         return "1005";
/*     */       }
/* 239 */       String[] childList = (String[])mgTypeMapping.get(componentType);
/* 240 */       int i = 0; if (i < childList.length)
/*     */       {
/* 242 */         return childList[i];
/*     */       }
/*     */     }
/*     */     
/* 246 */     return "1";
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getSubGroupLabel(String subgrouptype)
/*     */   {
/* 252 */     if (mointorGroupLabels.get(subgrouptype) != null)
/*     */     {
/* 254 */       return (String)mointorGroupLabels.get(subgrouptype);
/*     */     }
/*     */     
/*     */ 
/* 258 */     return "Sub Group";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int getSubGroupTypeForLabel(String label)
/*     */   {
/* 265 */     if (mointorGroupTypes.get(label) != null)
/*     */     {
/* 267 */       return Integer.parseInt((String)mointorGroupTypes.get(label));
/*     */     }
/*     */     
/*     */ 
/* 271 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSubgroupType(int subgroupType)
/*     */   {
/* 277 */     this.subgroupType = subgroupType;
/*     */   }
/*     */   
/*     */   public int getSubgroupType() {
/* 281 */     return this.subgroupType;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupname) {
/* 285 */     this.groupName = this.groupName;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/* 289 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setResourceid(int resourceid) {
/* 293 */     this.resourceid = resourceid;
/*     */   }
/*     */   
/*     */   public int getResourceid() {
/* 297 */     return this.resourceid;
/*     */   }
/*     */   
/*     */   public void setNoOfChilds(int noOfChilds)
/*     */   {
/* 302 */     this.noOfChilds = noOfChilds;
/*     */   }
/*     */   
/*     */   public int getNoOfChilds() {
/* 306 */     return this.noOfChilds;
/*     */   }
/*     */   
/*     */   public void setContains(int contains)
/*     */   {
/* 311 */     this.contains = contains;
/*     */   }
/*     */   
/*     */   public int getContains() {
/* 315 */     return this.contains;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\GroupComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */