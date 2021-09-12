/*     */ package com.manageengine.appmanager.amazon.client.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMBatchStmtExecutor;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.util.AppManagerUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.amazonaws.AmazonClientException;
/*     */ import com.amazonaws.AmazonServiceException;
/*     */ import com.amazonaws.auth.AWSCredentials;
/*     */ import com.amazonaws.auth.BasicAWSCredentials;
/*     */ import com.amazonaws.services.ec2.AmazonEC2;
/*     */ import com.amazonaws.services.ec2.model.InstanceMonitoring;
/*     */ import com.amazonaws.services.ec2.model.Monitoring;
/*     */ import com.manageengine.appmanager.amazon.comm.EC2Requests;
/*     */ import com.manageengine.appmanager.amazon.comm.ServicesRequests;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AmazonActions
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward manageCloudWatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  48 */     ActionMessages messages = new ActionMessages();
/*  49 */     String setMessage = "";
/*  50 */     if ((request.getParameter("rowIds") != null) || (request.getParameter("resourceId") != null))
/*     */     {
/*  52 */       String instanceResorceID = null;
/*  53 */       StringTokenizer nameAfterComma = null;
/*  54 */       String toPerform = "";
/*  55 */       ArrayList<String> ids = new ArrayList();
/*     */       
/*  57 */       if (request.getParameter("rowIds") != null)
/*     */       {
/*  59 */         String ec2ResourceIds = request.getParameter("rowIds");
/*  60 */         nameAfterComma = new StringTokenizer(ec2ResourceIds, ",");
/*  61 */         int count = nameAfterComma.countTokens();
/*  62 */         if (count >= 1)
/*     */         {
/*  64 */           while (nameAfterComma.hasMoreTokens())
/*     */           {
/*  66 */             instanceResorceID = nameAfterComma.nextToken();
/*  67 */             ids.add(instanceResorceID);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  73 */         instanceResorceID = request.getParameter("resourceId");
/*  74 */         ids.add(instanceResorceID);
/*     */       }
/*  76 */       if ((request.getParameter("rowIds") != null) || (request.getParameter("from") != null))
/*     */       {
/*  78 */         String instanceId = "CENTOS1";
/*  79 */         String ec2InstanceTypeId = "";
/*  80 */         ResultSet rsForCollectionTime = null;
/*     */         
/*     */         try
/*     */         {
/*  84 */           if (ids.size() > 0)
/*     */           {
/*  86 */             String setValue = "";
/*  87 */             instanceResorceID = (String)ids.get(0);
/*  88 */             String amazonResourceIdQuery = "select PARENTID from AM_PARENTCHILDMAPPER where CHILDID=" + instanceResorceID;
/*  89 */             String amazonResourceId = "";
/*  90 */             ResultSet amazonIdRs = null;
/*     */             try
/*     */             {
/*  93 */               amazonIdRs = AMConnectionPool.executeQueryStmt(amazonResourceIdQuery);
/*  94 */               if (amazonIdRs.next())
/*     */               {
/*  96 */                 amazonResourceId = amazonIdRs.getString("PARENTID");
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 101 */               e.printStackTrace();
/*     */             }
/*     */             finally {}
/*     */             
/*     */ 
/*     */ 
/* 107 */             ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/* 108 */             HashMap<String, String> instanceMap = null;
/* 109 */             AMLog.debug("instance ids in manageCloudWatch are : " + ids);
/* 110 */             for (int count = 0; count < ids.size(); count++)
/*     */             {
/* 112 */               instanceResorceID = (String)ids.get(count);
/* 113 */               instanceMap = new HashMap();
/* 114 */               AppManagerUtil.getInstanceRegionMap(instanceResorceID, ec2InstanceTypeId, instanceMap);
/* 115 */               String region = (String)instanceMap.get("REGION");
/* 116 */               instanceId = (String)instanceMap.get("INSTANCEID");
/*     */               
/* 118 */               AWSCredentials credentials = getCredentialsForResourceId(amazonResourceId);
/* 119 */               AmazonEC2 ec2Service = ServicesRequests.getEc2Service(credentials);
/* 120 */               if (request.getParameter("To") != null)
/*     */               {
/* 122 */                 toPerform = request.getParameter("To");
/*     */                 
/* 124 */                 if (toPerform.equalsIgnoreCase("enable"))
/*     */                 {
/* 126 */                   setValue = "enabled";
/* 127 */                   setMessage = FormatUtil.getString("am.webclient.amazon.cloudwatch.enabled.text", new String[] { String.valueOf(ids.size()) });
/* 128 */                   List<InstanceMonitoring> instanceMonitoringDetails = EC2Requests.enableMonitoring(ec2Service, region, instanceId);
/*     */                   
/* 130 */                   if ((request.getParameter("from") != null) && (ids.size() == 1))
/*     */                   {
/* 132 */                     String from = request.getParameter("from");
/* 133 */                     if (from.equalsIgnoreCase("EC2Instance"))
/*     */                     {
/* 135 */                       for (InstanceMonitoring instanceMonitoring : instanceMonitoringDetails)
/*     */                       {
/* 137 */                         if (instanceMonitoring.getMonitoring().getState().toString().equalsIgnoreCase("enabled"))
/*     */                         {
/*     */ 
/* 140 */                           rsForCollectionTime = AMConnectionPool.executeQueryStmt(getCollectionTimeQuery(instanceResorceID));
/* 141 */                           String collectionTime = "";
/* 142 */                           while (rsForCollectionTime.next())
/*     */                           {
/* 144 */                             collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*     */                           }
/*     */                           
/*     */ 
/* 148 */                           updateConfigurationInfo(instanceResorceID, "9829", setValue, collectionTime);
/* 149 */                           String updateErrorMsgQuery = "Update AM_MONITOR_ERRORS set Error_type = '2' , Error_Message = '" + FormatUtil.getString("am.webclient.amazon.enablecloudwatch.success") + "' where RESOURCEID=" + instanceResorceID;
/* 150 */                           AMConnectionPool.executeUpdateStmt(updateErrorMsgQuery);
/*     */                           
/* 152 */                           String returnPath = "/showresource.do?resourceid=" + instanceResorceID + "&method=showResourceForResourceID";
/* 153 */                           return new ActionForward(returnPath);
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                   else
/*     */                   {
/* 160 */                     String updateErrorMsgQuery = "Update AM_MONITOR_ERRORS set Error_type = '2' , Error_Message = '" + FormatUtil.getString("am.webclient.amazon.enablecloudwatch.success") + "' where RESOURCEID=" + instanceResorceID;
/* 161 */                     AMConnectionPool.executeUpdateStmt(updateErrorMsgQuery);
/*     */                   }
/*     */                 }
/* 164 */                 else if (toPerform.equalsIgnoreCase("disable"))
/*     */                 {
/* 166 */                   setValue = "disabled";
/* 167 */                   setMessage = FormatUtil.getString("am.webclient.amazon.cloudwatch.disabled.text", new String[] { String.valueOf(ids.size()) });
/*     */                   
/* 169 */                   EC2Requests.disableMonitoring(ec2Service, region, instanceId);
/*     */                 }
/*     */                 
/*     */ 
/* 173 */                 rsForCollectionTime = AMConnectionPool.executeQueryStmt(getCollectionTimeQuery(instanceResorceID));
/* 174 */                 String collectionTime = "";
/* 175 */                 while (rsForCollectionTime.next())
/*     */                 {
/* 177 */                   collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*     */                 }
/*     */                 
/*     */ 
/* 181 */                 AMConnectionPool.executeUpdateStmt(getUpdateQuery(instanceResorceID, "9829", setValue, collectionTime));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/* 188 */           e.printStackTrace();
/*     */         }
/*     */         catch (AmazonServiceException e)
/*     */         {
/* 192 */           e.printStackTrace();
/*     */         }
/*     */         catch (AmazonClientException e)
/*     */         {
/* 196 */           e.printStackTrace();
/*     */         }
/*     */         catch (IllegalArgumentException e)
/*     */         {
/* 200 */           e.printStackTrace();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 204 */           e.printStackTrace();
/*     */         }
/*     */         catch (Throwable e)
/*     */         {
/* 208 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 212 */           AMConnectionPool.closeStatement(rsForCollectionTime);
/*     */         }
/*     */         
/* 215 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/* 216 */         saveMessages(request, messages);
/* 217 */         request.setAttribute("SUCCESS", "true");
/* 218 */         return mapping.findForward("success");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 223 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("<br>Unable to enable CloudWatch.<br>The CloudWatch is already enabled for the selected Instances"));
/* 224 */       saveMessages(request, messages);
/* 225 */       request.setAttribute("SUCCESS", "false");
/* 226 */       return mapping.findForward("failed");
/*     */     }
/* 228 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward assignOSNames(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 235 */     ActionMessages messages = new ActionMessages();
/* 236 */     String resids = request.getParameter("rowIds");
/* 237 */     ArrayList<String> resourceids = new ArrayList();
/*     */     
/*     */ 
/* 240 */     ResultSet rsForCollectionTime = null;
/* 241 */     StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 242 */     if (resids == null)
/*     */     {
/* 244 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.amazon.editosname.notselected")));
/* 245 */       saveMessages(request, messages);
/* 246 */       request.setAttribute("SUCCESS", "false");
/* 247 */       return mapping.findForward("failed");
/*     */     }
/*     */     
/*     */ 
/* 251 */     int count = 0;
/* 252 */     while (nameAfterComma.hasMoreTokens())
/*     */     {
/* 254 */       String resid = nameAfterComma.nextToken();
/* 255 */       resourceids.add(resid);
/*     */     }
/*     */     try
/*     */     {
/* 259 */       String ec2InstanceTypeId = DBUtil.getBaseId("EC2Instance");
/*     */       
/* 261 */       ArrayList<String> ps = new ArrayList();
/*     */       
/* 263 */       for (int i = 0; i < resourceids.size(); i++)
/*     */       {
/* 265 */         String newname = request.getParameter((String)resourceids.get(i));
/* 266 */         if ((newname != null) && (resourceids.get(i) != null))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 272 */           count++;
/* 273 */           String query = "update AM_Amazon_EC2InstanceOSMAPPING set INSTANCEOS = '" + newname + "' where RESOURCEID = " + (String)resourceids.get(i) + "";
/* 274 */           ps.add(query);
/*     */           
/* 276 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*     */           
/*     */ 
/*     */ 
/* 280 */           rsForCollectionTime = AMConnectionPool.executeQueryStmt(getCollectionTimeQuery((String)resourceids.get(i)));
/* 281 */           String collectionTime = "";
/* 282 */           while (rsForCollectionTime.next())
/*     */           {
/* 284 */             collectionTime = rsForCollectionTime.getString("COLLECTIONTIME");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 289 */           updateConfigurationInfo((String)resourceids.get(i), "9822", newname, collectionTime);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 296 */       AMBatchStmtExecutor.executeBatch(ps);
/*     */ 
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/*     */ 
/* 302 */       exception.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 306 */       AMConnectionPool.closeStatement(rsForCollectionTime);
/*     */     }
/* 308 */     if (count != 0)
/*     */     {
/* 310 */       String Count = "";
/* 311 */       Count = Count + count;
/* 312 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.amazon.editosname.edited", new String[] { Count })));
/* 313 */       saveMessages(request, messages);
/* 314 */       request.setAttribute("SUCCESS", "true");
/* 315 */       return mapping.findForward("success");
/*     */     }
/*     */     
/*     */ 
/* 319 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("<br>Unable to edit OS name: The OS name is already present"));
/* 320 */     saveMessages(request, messages);
/* 321 */     request.setAttribute("SUCCESS", "false");
/* 322 */     return mapping.findForward("failed");
/*     */   }
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
/*     */   private AWSCredentials getCredentialsForResourceId(String amazonResourceId)
/*     */   {
/* 353 */     ResultSet rs = null;
/* 354 */     String amazonTypeId = DBUtil.getBaseId("Amazon");
/* 355 */     String keysQuery = "select *," + DBQueryUtil.decode("PASSWORD") + " as secretKey from AM_ARGS_" + amazonTypeId + " where resourceId = '" + amazonResourceId + "'";
/* 356 */     if (DBQueryUtil.isPgsql())
/*     */     {
/* 358 */       keysQuery = "select *," + DBQueryUtil.decode("\"Password\"") + " as secretKey from AM_ARGS_" + amazonTypeId + " where resourceId = '" + amazonResourceId + "'";
/*     */     }
/* 360 */     String accessKey = "";
/* 361 */     String secretKey = "";
/*     */     try
/*     */     {
/* 364 */       rs = AMConnectionPool.executeQueryStmt(keysQuery);
/* 365 */       if (rs.next())
/*     */       {
/* 367 */         accessKey = rs.getString("accessKey");
/* 368 */         secretKey = rs.getString("secretKey");
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 375 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 379 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 381 */     return new BasicAWSCredentials(accessKey, secretKey);
/*     */   }
/*     */   
/*     */   private static String getCollectionTimeQuery(String instanceResorceID)
/*     */   {
/* 386 */     StringBuffer collectionTimeQuery = new StringBuffer("select max(COLLECTIONTIME) as COLLECTIONTIME from ");
/* 387 */     ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/* 388 */     collectionTimeQuery.append((String)tables.get(0));
/* 389 */     collectionTimeQuery.append(" where RESOURCEID = ").append(instanceResorceID);
/* 390 */     return collectionTimeQuery.toString();
/*     */   }
/*     */   
/*     */   private static String getUpdateQuery(String instanceResorceID, String attID, String setValue, String collectionTime)
/*     */   {
/* 395 */     StringBuffer updateQuery = new StringBuffer("update ");
/* 396 */     ArrayList<String> tables = ConfMonitorUtil.getInstance().getCurrentTable("EC2Instance", null);
/* 397 */     updateQuery.append((String)tables.get(0));
/* 398 */     ConfMonitorConfiguration.getInstance().getAttributeName("EC2Instance", attID);
/* 399 */     updateQuery.append(" set ").append(ConfMonitorConfiguration.getInstance().getAttributeName("EC2Instance", attID));
/* 400 */     updateQuery.append(" '").append(setValue).append("' where RESOURCEID = ").append(instanceResorceID);
/* 401 */     updateQuery.append(" and COLLECTIONTIME = '").append(collectionTime).append("'");
/* 402 */     return updateQuery.toString();
/*     */   }
/*     */   
/*     */   private static void updateConfigurationInfo(String instanceResorceID, String attID, String setValue, String collectionTime)
/*     */   {
/* 407 */     HashMap configMap = new HashMap();
/* 408 */     configMap.put(attID, setValue);
/* 409 */     NewMonitorUtil.insertConfigurationDetails(configMap, Integer.parseInt(instanceResorceID), Long.valueOf(collectionTime).longValue(), false);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\amazon\client\actions\AmazonActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */