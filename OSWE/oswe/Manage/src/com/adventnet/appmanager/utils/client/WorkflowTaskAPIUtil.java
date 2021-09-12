/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.client.views.IconViewUtils;
/*     */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.IPV6Util;
/*     */ import com.adventnet.appmanager.util.WinServiceActionUtil;
/*     */ import com.adventnet.nms.topodb.ManagedObject;
/*     */ import com.adventnet.nms.topodb.TopoAPI;
/*     */ import com.adventnet.nms.topodb.TopoObject;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONObject;
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
/*     */ public class WorkflowTaskAPIUtil
/*     */ {
/*     */   public static String executeWorkflowTask(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  39 */     String outputString = "";
/*  40 */     String action = request.getParameter("ACTION");
/*  41 */     if (action == null)
/*     */     {
/*  43 */       return outputString;
/*     */     }
/*  45 */     if (action.equals("GET_DEVICE_DETAILS"))
/*     */     {
/*  47 */       JSONObject deviceInfo = new JSONObject();
/*  48 */       String moID = request.getParameter("MOID");
/*  49 */       String deviceName = request.getParameter("DEVICENAME");
/*  50 */       String resourceType = DBUtil.getTypeName(Integer.parseInt(moID));
/*     */       
/*  52 */       deviceInfo.put("agentname", deviceName);
/*  53 */       deviceInfo.put("type", resourceType);
/*     */       
/*  55 */       String dnsName = IconViewUtils.getQualifiedHostName(deviceName);
/*  56 */       if (dnsName != null)
/*     */       {
/*  58 */         deviceInfo.put("dnsname", IconViewUtils.getQualifiedHostName(deviceName));
/*     */       }
/*     */       
/*  61 */       String vendorname = EnterpriseUtil.getVendorName(moID);
/*  62 */       deviceInfo.put("vendorname", vendorname);
/*     */       
/*     */       try
/*     */       {
/*  66 */         ManagedObject obj = null;
/*  67 */         TopoAPI tApi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*  68 */         if ((tApi != null) && ((obj = tApi.getByName(deviceName)) != null))
/*     */         {
/*  70 */           String ipAddress = ((TopoObject)obj).getIpAddress();
/*  71 */           if (ipAddress != null)
/*     */           {
/*  73 */             deviceInfo.put("ipaddress", ipAddress);
/*  74 */             boolean isIPV6Address = IPV6Util.isIPV6Address(ipAddress);
/*  75 */             deviceInfo.put("isipv6", "" + isIPV6Address);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  81 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  86 */       Map<String, String[]> hostDetails = WinServiceActionUtil.getHostDetails(moID, resourceType);
/*  87 */       String[] hostInfo = null;
/*  88 */       if (hostDetails != null)
/*     */       {
/*  90 */         hostInfo = (String[])hostDetails.get(moID);
/*  91 */         if (dnsName == null)
/*     */         {
/*  93 */           deviceInfo.put("dnsname", hostInfo[0]);
/*     */         }
/*  95 */         deviceInfo.put("username", hostInfo[2]);
/*  96 */         deviceInfo.put("password", hostInfo[3]);
/*     */       }
/*     */       
/*     */ 
/* 100 */       int attrIntVal = 0;
/* 101 */       String attribAvail = AMAttributesCache.getAvailabilityId(resourceType);
/* 102 */       List<String> attributes = Arrays.asList(new String[] { attribAvail });
/* 103 */       ArrayList<Hashtable<?, ?>> attrDetails = GetCurrentDataUtil.getAttributeDetails(attributes, moID, resourceType);
/* 104 */       if ((attrDetails != null) && (attrDetails.size() > 0)) {
/* 105 */         Object attrVal = ((Hashtable)attrDetails.get(0)).get("Value");
/* 106 */         if (attrVal != null)
/*     */         {
/* 108 */           attrIntVal = Integer.parseInt(attrVal.toString());
/* 109 */           if (attrIntVal == 5)
/*     */           {
/* 111 */             attrIntVal = 1;
/*     */           }
/*     */           else
/*     */           {
/* 115 */             attrIntVal = 0;
/*     */           }
/*     */         }
/*     */       }
/* 119 */       deviceInfo.put("state", "" + attrIntVal);
/*     */       
/* 121 */       outputString = deviceInfo.toString();
/*     */     }
/* 123 */     return outputString;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\WorkflowTaskAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */