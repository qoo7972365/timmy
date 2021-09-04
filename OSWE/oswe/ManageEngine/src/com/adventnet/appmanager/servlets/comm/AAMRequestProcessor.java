/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommJob;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommunicationTaskManager;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.MASSyncUtil;
/*     */ import com.manageengine.it360.util.RebrandUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class AAMRequestProcessor extends HttpServlet
/*     */ {
/*  29 */   private ServletContext servletContext = null;
/*  30 */   private ServletConfig config = null;
/*     */   
/*     */   public void init(ServletConfig sConfig) throws ServletException {
/*  33 */     super.init(sConfig);
/*  34 */     this.servletContext = sConfig.getServletContext();
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  40 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  46 */     PrintWriter out = response.getWriter();
/*  47 */     String buildnumber = request.getParameter("bn");
/*  48 */     String edition = request.getParameter("Edition");
/*  49 */     String hostName = request.getParameter("host");
/*  50 */     String portNo = request.getParameter("port");
/*  51 */     if (!EnterpriseUtil.isCompatibleBuild(buildnumber))
/*     */     {
/*  53 */       AMLog.debug(" Probe Registration : Incompatible Build Number " + buildnumber + " received from hostname :" + hostName + ":" + portNo);
/*  54 */       out.println("errorcode=2007");
/*     */       
/*  56 */       return;
/*     */     }
/*  58 */     if ((edition != null) && (!EnterpriseUtil.getEditionType().equalsIgnoreCase(edition)))
/*     */     {
/*  60 */       AMLog.debug(" Probe Registration : Incompatible Server Edition " + edition + " received from hostname :" + hostName + ":" + portNo);
/*  61 */       out.println("errorcode=2012");
/*     */       
/*  63 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  68 */     String command = request.getParameter("command");
/*  69 */     if ("MAS_Compatible".equals(command))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */       if (!EnterpriseUtil.isManagedServer())
/*     */       {
/*  79 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/*  81 */           out.println("errorcode=2001");
/*  82 */           return;
/*     */         }
/*     */         
/*     */ 
/*  86 */         out.println("errorcode=2002");
/*  87 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */       if ((request.getParameter("serverID") != null) && (!request.getParameter("serverID").equals(EnterpriseUtil.getManagedServerIndex() + "")))
/*     */       {
/*  97 */         out.println("errorcode=2003");
/*  98 */         return; }
/*  99 */       if (request.getParameter("serverID") != null)
/*     */       {
/*     */ 
/* 102 */         int serverID = Integer.parseInt(request.getParameter("serverID"));
/*     */         
/* 104 */         serverID *= 10000000;
/*     */         
/*     */ 
/*     */ 
/* 108 */         AMConnectionPool pool = AMConnectionPool.getInstance();
/* 109 */         int moId = -1;
/*     */         try {
/* 111 */           String moQry = "select  max(RESOURCEID) from AM_ManagedObject";
/*     */           
/* 113 */           ResultSet rs = AMConnectionPool.executeQueryStmt(moQry);
/* 114 */           if (rs.next())
/*     */           {
/*     */ 
/* 117 */             moId = rs.getInt(1);
/*     */           }
/* 119 */           rs.close();
/*     */ 
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 124 */           ex.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/* 128 */         int diff = moId - serverID;
/*     */         
/*     */ 
/*     */ 
/* 132 */         if ((diff <= 0) || (diff >= 10000000))
/*     */         {
/*     */ 
/*     */ 
/* 136 */           out.println("errorcode=2010");
/* 137 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 147 */     long timestamp = 0L;
/*     */     try
/*     */     {
/* 150 */       timestamp = Long.parseLong(request.getParameter("time_stamp"));
/*     */     } catch (NumberFormatException e) {
/* 152 */       AMLog.debug("Enterprise : Request dropped as timestamp was improper. command " + command + " QueryString " + request.getQueryString());
/* 153 */       return;
/*     */     }
/*     */     
/* 156 */     Map qryStrMap = com.adventnet.appmanager.server.framework.comm.HClient.getQueryString(request);
/*     */     
/* 158 */     if ("Register_Me_MAS".equals(command))
/*     */     {
/* 160 */       String host = request.getParameter("host");
/* 161 */       int port = 9090;
/*     */       try
/*     */       {
/* 164 */         if (!EnterpriseUtil.isAdminServer())
/*     */         {
/* 166 */           if (EnterpriseUtil.isManagedServer())
/*     */           {
/* 168 */             out.println("errorcode=2005");
/* 169 */             return;
/*     */           }
/*     */           
/*     */ 
/* 173 */           out.println("errorcode=2004");
/* 174 */           return;
/*     */         }
/*     */         
/* 177 */         port = Integer.parseInt(request.getParameter("port"));
/* 178 */         Map mapInfo = CommDBUtil.addMAS(qryStrMap);
/* 179 */         if (mapInfo.get("serverIDExists") != null)
/*     */         {
/*     */ 
/* 182 */           AMLog.debug(" Probe Registration : Already Probe Server registered with central server. hostname :" + hostName + ":" + portNo);
/* 183 */           out.println("errorcode=1100");
/*     */           
/* 185 */           out.println("range=" + mapInfo.get("range"));
/* 186 */           return;
/*     */         }
/* 188 */         String range = null;
/*     */         try
/*     */         {
/* 191 */           range = mapInfo.get("range").toString();
/*     */         }
/*     */         catch (Exception e) {}
/*     */         
/*     */ 
/*     */ 
/* 197 */         if (range != null)
/*     */         {
/* 199 */           String mas_servername = (String)mapInfo.get("mas_servername");
/*     */           
/* 201 */           out.println("mas_servername=" + mas_servername);
/* 202 */           out.println("range=" + range);
/* 203 */           out.println("status=Registered Successfully");
/* 204 */           String admindbtype = System.getProperty("am.dbserver.type");
/* 205 */           out.println("admindbtype=" + admindbtype);
/* 206 */           AMLog.debug("Enterprise : MAS Registration request with Admin server SUCCESS. QueryString is ... " + request.getQueryString());
/* 207 */           String managerId = (String)mapInfo.get("resourceId");
/*     */           
/*     */           try
/*     */           {
/* 211 */             int id = Integer.parseInt(managerId);
/* 212 */             AMLog.debug("Enterprise : Comm Job thread started in AAMRequestProcessor for Manager ID " + id);
/* 213 */             new CommJob(id).run(true);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 217 */             AMLog.debug("Enterprise : Unable to start Comm Job thread in AAMRequestProcessor for " + managerId + ". Refer stack trace.");
/* 218 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */ 
/* 222 */           AMLog.debug("Sync rebrand data in  the newly registered probe ===" + managerId);
/* 223 */           if (EnterpriseUtil.isIt360MSPAdminServer())
/*     */           {
/* 225 */             RebrandUtil rbUtil = RebrandUtil.getInstance();
/* 226 */             Properties brandProps = RebrandUtil.brandProps;
/* 227 */             CommunicationTaskManager taskManager = new CommunicationTaskManager(false);
/*     */             
/* 229 */             String param = brandProps.getProperty("copyRight");
/* 230 */             param = param.replaceAll("\"", "&quot;");
/* 231 */             brandProps.setProperty("copyRight", param);
/* 232 */             brandProps.setProperty("subModule", "updateMSPRebrandInfo");
/* 233 */             brandProps.setProperty("appType", "UpdateMSPRebrandInfo");
/*     */             try
/*     */             {
/* 236 */               String isRebrandingCustomized = "".equals(DBUtil.getGlobalConfigValue("isRebrandingCustomized")) ? "false" : DBUtil.getGlobalConfigValue("isRebrandingCustomized");
/* 237 */               brandProps.setProperty("isRebrandingCustomized", isRebrandingCustomized);
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 241 */               ex.printStackTrace();
/*     */             }
/*     */             
/* 244 */             AMLog.debug("brandProps in central:" + brandProps);
/* 245 */             Vector probeList = new Vector();
/* 246 */             probeList.add(managerId);
/* 247 */             CommunicationTaskManager.createTask("3", "synchCustomer", brandProps, null, probeList);
/* 248 */             taskManager.start();
/*     */           }
/*     */         }
/*     */         try
/*     */         {
/* 253 */           HashMap<String, String> params = new HashMap();
/* 254 */           params.put("am.sso.enabled", Constants.ssoEnabled + "");
/* 255 */           params.put("apicallfrom", "admin");
/* 256 */           MASSyncUtil.addTasktoSync(params, "/AppManager/xml/ssoprops/update", Integer.parseInt(range) / EnterpriseUtil.RANGE + "", "POST", 9, 2);
/*     */         } catch (Exception sso) {
/* 258 */           sso.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 263 */         AMLog.debug("Enterprise : Unable to register MAS from " + host + " and " + port + " with Admin Server(Stack Trace has details). QueryString from MAS is ... " + request.getQueryString());
/* 264 */         e.printStackTrace();
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void destroy()
/*     */   {
/* 282 */     this.servletContext.removeAttribute("availabilitykeys");
/* 283 */     this.servletContext.removeAttribute("healthkeys");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\AAMRequestProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */