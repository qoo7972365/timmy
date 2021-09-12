/*     */ package com.adventnet.appmanager.fault.rcatree;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.AMPreparedStatementHandler;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.severity.SeverityInfo;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
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
/*     */ public class AMRCATreeCreator
/*     */ {
/*  36 */   DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
/*  37 */   DefaultTreeModel treeModel = new DefaultTreeModel(this.rootNode);
/*  38 */   int rootNodeRCAVal = -100;
/*  39 */   String firstNodeDescr = "";
/*  40 */   boolean firstNodeDescrAdded = false;
/*  41 */   long firstNodeTimeStamp = -1L;
/*  42 */   boolean firstNodeTimeStampAdded = false;
/*  43 */   String firstNodeResourceName = "";
/*  44 */   boolean firstNodeResourceNameAdded = false;
/*  45 */   String firstNodeAttributeDisplayNameWithUnit = "";
/*  46 */   boolean firstNodeAttributeDisplayNameWithUnitAdded = false;
/*     */   private int id_getChildIDForParentFromRCA;
/*     */   private int currentStatus;
/*     */   
/*     */   public AMRCATreeCreator()
/*     */   {
/*  52 */     HashMap<String, String> userData = new HashMap();
/*  53 */     userData.put("id", "" + this.rootNodeRCAVal);
/*  54 */     userData.put("actionlink", "#");
/*  55 */     userData.put("displayname", " (Rules are provided within brackets)");
/*  56 */     userData.put("imageicon", "/images/icon_rca.gif");
/*  57 */     userData.put("target", "#");
/*  58 */     this.rootNode.setUserObject(userData);
/*  59 */     String query = "SELECT AM_ManagedObject.DISPLAYNAME, AM_RCAMAPPER.CHILDRESOURCEID, AM_RCAMAPPER.CHILD_RESOURCEATTRIBUTEMAPPERID FROM AM_RCAMAPPER,AM_ManagedObject WHERE AM_ManagedObject.RESOURCEID=AM_RCAMAPPER.CHILDRESOURCEID AND PARENTRESOURCEID=? AND PARENT_RESOURCEATTRIBUTEMAPPERID=?";
/*  60 */     this.id_getChildIDForParentFromRCA = AMPreparedStatementHandler.getInstance().getPSId(query);
/*     */   }
/*     */   
/*     */   public DefaultTreeModel getTreeModel(int resourceID, int attributeID)
/*     */   {
/*  65 */     String displayName = DBUtil.getDisplaynameforResourceID(String.valueOf(resourceID));
/*  66 */     if ((displayName != null) && (!displayName.equals("")))
/*     */     {
/*  68 */       String parentEntity = resourceID + "_" + attributeID;
/*  69 */       HashMap<String, String> parentEventInfo = FaultUtil.getLatestEvent(parentEntity);
/*  70 */       this.firstNodeResourceName = displayName;
/*  71 */       if ((parentEventInfo == null) || (parentEventInfo.isEmpty()))
/*     */       {
/*  73 */         DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
/*  74 */         updateChildNodeWithUserObject(String.valueOf(resourceID), String.valueOf(attributeID), childNode, parentEventInfo, displayName, true);
/*  75 */         this.rootNode.add(childNode);
/*     */       }
/*     */       else
/*     */       {
/*  79 */         this.currentStatus = Integer.parseInt((String)parentEventInfo.get("SEVERITY"));
/*  80 */         populateTreeModel(resourceID, attributeID, this.rootNode, parentEventInfo, displayName, true);
/*     */       }
/*     */     }
/*  83 */     return this.treeModel;
/*     */   }
/*     */   
/*     */   public int getCurrentStatus()
/*     */   {
/*  88 */     return this.currentStatus;
/*     */   }
/*     */   
/*     */   public String getFirstNodeDescription()
/*     */   {
/*  93 */     return FormatUtil.replaceStringBySpecifiedString(this.firstNodeDescr, "<br>", "\n", 0);
/*     */   }
/*     */   
/*     */   public String getFirstNodeResourceName()
/*     */   {
/*  98 */     return this.firstNodeResourceName;
/*     */   }
/*     */   
/*     */   public String getFirstNodeAttributeDisplayNameWithUnit()
/*     */   {
/* 103 */     return this.firstNodeAttributeDisplayNameWithUnit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void populateTreeModel(int resourceID, int attributeID, DefaultMutableTreeNode parentNode, HashMap<String, String> parentEventInfo, String displayName, boolean isFirstNode)
/*     */   {
/* 113 */     int parentSeverity = Integer.parseInt((String)parentEventInfo.get("SEVERITY"));
/* 114 */     DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
/* 115 */     updateChildNodeWithUserObject(String.valueOf(resourceID), String.valueOf(attributeID), childNode, parentEventInfo, displayName, isFirstNode);
/* 116 */     parentNode.add(childNode);
/* 117 */     if ((attributeID != 18) && (attributeID != 17))
/*     */     {
/* 119 */       ResultSet rs = null;
/* 120 */       ArrayList<HashMap<String, Object>> queryoutput = new ArrayList();
/*     */       try
/*     */       {
/* 123 */         PreparedStatement stmt = AMPreparedStatementHandler.getInstance().getPreparedStatement(this.id_getChildIDForParentFromRCA);
/* 124 */         stmt.setInt(1, resourceID);
/* 125 */         stmt.setInt(2, attributeID);
/* 126 */         rs = stmt.executeQuery();
/* 127 */         while (rs.next())
/*     */         {
/* 129 */           HashMap<String, Object> map = new HashMap();
/* 130 */           String childDisplayName = rs.getString(1);
/* 131 */           int childResourceId = rs.getInt(2);
/* 132 */           int childAttributeId = rs.getInt(3);
/* 133 */           map.put("DISPLAYNAME", childDisplayName);
/* 134 */           map.put("RESOURCEID", Integer.valueOf(childResourceId));
/* 135 */           map.put("ATTRIBUTEID", Integer.valueOf(childAttributeId));
/* 136 */           queryoutput.add(map);
/*     */         }
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 141 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 145 */         AMConnectionPool.closeResultSet(rs);
/*     */       }
/* 147 */       int size = queryoutput.size();
/* 148 */       for (int i = 0; i < size; i++)
/*     */       {
/* 150 */         HashMap<String, Object> map = (HashMap)queryoutput.get(i);
/* 151 */         String childDisplayName = (String)map.get("DISPLAYNAME");
/* 152 */         int childResourceId = ((Integer)map.get("RESOURCEID")).intValue();
/* 153 */         int childAttributeId = ((Integer)map.get("ATTRIBUTEID")).intValue();
/* 154 */         String childEntity = String.valueOf(childResourceId + "_" + childAttributeId);
/* 155 */         HashMap<String, String> childEventInfo = FaultUtil.getLatestEvent(childEntity);
/* 156 */         if (childEventInfo != null)
/*     */         {
/* 158 */           int childSeverity = Integer.parseInt((String)childEventInfo.get("SEVERITY"));
/* 159 */           if (parentSeverity == childSeverity)
/*     */           {
/* 161 */             populateTreeModel(childResourceId, childAttributeId, childNode, childEventInfo, childDisplayName, false);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateChildNodeWithUserObject(String resourceIDArg, String attributeIDArg, DefaultMutableTreeNode nodeArg, HashMap<String, String> eventMap, String displayName, boolean isFirstNode)
/*     */   {
/* 170 */     HashMap<String, String> userData = new HashMap();
/* 171 */     String[] attrDetails = DBUtil.getAttributeDetails(attributeIDArg);
/* 172 */     int type = Integer.parseInt(attrDetails[0]);
/* 173 */     String attributeName = attrDetails[1];
/* 174 */     String attributeUnit = attrDetails[2];
/* 175 */     String actionlink = "";
/* 176 */     String imageicon = "";
/* 177 */     if ((eventMap == null) || (eventMap.isEmpty()))
/*     */     {
/* 179 */       String descr = "";
/* 180 */       if ((type == 0) || (type == 3))
/*     */       {
/* 182 */         descr = FormatUtil.getString("am.webclient.rcamessage.configurethreshold.text", new String[] { FaultUtil.ALERTCONFIG_TEXT });
/*     */       }
/* 184 */       else if (type == 1)
/*     */       {
/* 186 */         descr = FormatUtil.getString("am.webclient.rcamessage.checkdatacollection.text");
/*     */       }
/* 188 */       else if (type == 2)
/*     */       {
/* 190 */         descr = FormatUtil.getString("am.webclient.rcamessage.nodepencies.text");
/*     */       }
/* 192 */       actionlink = "javascript:showDescription('" + descr + "')";
/* 193 */       if (type == 1)
/*     */       {
/* 195 */         imageicon = "/images/icon_availability_unknown.gif";
/*     */       }
/*     */       else
/*     */       {
/* 199 */         imageicon = "/images/icon_unknown_status.gif";
/*     */       }
/* 201 */       if ((!this.firstNodeDescrAdded) && (isFirstNode))
/*     */       {
/* 203 */         this.firstNodeDescr = descr;
/* 204 */         this.firstNodeDescrAdded = true;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 209 */       int severity = Integer.parseInt((String)eventMap.get("SEVERITY"));
/* 210 */       String message = (String)eventMap.get("MESSAGE");
/* 211 */       long time = Long.parseLong((String)eventMap.get("TIME"));
/* 212 */       if (type == 1)
/*     */       {
/* 214 */         if (severity == 5)
/*     */         {
/* 216 */           imageicon = "/images/icon_availability_up.gif";
/*     */         }
/*     */         else
/*     */         {
/* 220 */           imageicon = "/images/icon_availability_down.gif";
/*     */         }
/*     */         
/*     */       }
/*     */       else {
/* 225 */         imageicon = "/images/icon_" + (type == 2 ? "health_" : "") + SeverityInfo.getInstance().getName(severity).toLowerCase() + ".gif";
/*     */       }
/* 227 */       actionlink = FormatUtil.findReplace(message, "'", "\\'");
/* 228 */       actionlink = replaceBr(actionlink);
/* 229 */       actionlink = "javascript:showDescription('" + actionlink + "')";
/* 230 */       if ((!this.firstNodeDescrAdded) && (isFirstNode))
/*     */       {
/* 232 */         this.firstNodeDescr = message;
/* 233 */         this.firstNodeDescrAdded = true;
/*     */       }
/* 235 */       if ((!this.firstNodeTimeStampAdded) && (isFirstNode))
/*     */       {
/* 237 */         this.firstNodeTimeStamp = time;
/* 238 */         this.firstNodeTimeStampAdded = true;
/*     */       }
/*     */     }
/* 241 */     if ((!this.firstNodeAttributeDisplayNameWithUnitAdded) && (isFirstNode))
/*     */     {
/* 243 */       this.firstNodeAttributeDisplayNameWithUnit = (attributeName + (attributeUnit.trim().equals("") ? "" : new StringBuilder().append(" (").append(attributeUnit.trim()).append(")").toString()));
/* 244 */       this.firstNodeAttributeDisplayNameWithUnitAdded = true;
/*     */     }
/* 246 */     String displayNameWithAttr = FaultUtil.getString("am.fault.rcatree.nodedisplay.format.text", new Object[] { attributeName, displayName });
/* 247 */     userData.put("id", resourceIDArg.concat(attributeIDArg));
/* 248 */     userData.put("displayname", displayNameWithAttr);
/* 249 */     userData.put("target", "#");
/* 250 */     userData.put("actionlink", actionlink);
/* 251 */     userData.put("imageicon", imageicon);
/* 252 */     nodeArg.setUserObject(userData);
/*     */   }
/*     */   
/*     */   public String getRCAnalysisTime()
/*     */   {
/* 257 */     if (this.firstNodeTimeStamp != -1L)
/*     */     {
/* 259 */       return FormatUtil.formatDT("" + this.firstNodeTimeStamp);
/*     */     }
/* 261 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private static String replaceBr(String withBr)
/*     */   {
/* 267 */     return FormatUtil.replaceStringBySpecifiedString(withBr, "<br>", "\\n", 0);
/*     */   }
/*     */   
/*     */   public void updateRuleDetails(StringBuffer buffer, String resourceID, String attributeID)
/*     */     throws SQLException
/*     */   {
/* 273 */     ResultSet rs = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */     String query = "select NAME from AM_ATTRIBUTETHRESHOLDMAPPER,AM_THRESHOLDCONFIG where AM_ATTRIBUTETHRESHOLDMAPPER.ID=" + resourceID + " AND AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=" + attributeID + " AND AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID=AM_THRESHOLDCONFIG.ID";
/*     */     try
/*     */     {
/* 282 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 283 */       if (rs.next())
/*     */       {
/* 285 */         buffer.append(FaultUtil.getString("am.fault.rcatree.dependsonthreshold.text", new Object[] { rs.getString(1) }));
/*     */       }
/*     */       else
/*     */       {
/* 289 */         query = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resourceID + " AND ATTRIBUTE=" + attributeID;
/* 290 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 291 */         if (rs.next())
/*     */         {
/* 293 */           int rcaValue = rs.getInt(1);
/* 294 */           if (rcaValue != this.rootNodeRCAVal)
/*     */           {
/* 296 */             if (rcaValue == -1)
/*     */             {
/* 298 */               buffer.append(FaultUtil.getString("am.fault.rcatree.allrule.text"));
/*     */             }
/* 300 */             else if (rcaValue == 1)
/*     */             {
/* 302 */               buffer.append(FaultUtil.getString("am.fault.rcatree.anyonerule.text"));
/*     */             }
/*     */             else
/*     */             {
/* 306 */               buffer.append(FaultUtil.getString("am.fault.rcatree.anyrule.text", new Object[] { "" + rcaValue }));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 314 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 318 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\fault\rcatree\AMRCATreeCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */