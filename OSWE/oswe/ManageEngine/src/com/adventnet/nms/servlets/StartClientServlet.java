/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.management.log.LogUser;
/*     */ import com.adventnet.nms.authentication.UserConfigAPI;
/*     */ import com.adventnet.nms.commonbe.BEServerContext;
/*     */ import com.adventnet.nms.db.util.DBXmlUpdate;
/*     */ import com.adventnet.nms.jsp.JspUtility;
/*     */ import com.adventnet.nms.startnms.NmsMainFE;
/*     */ import com.adventnet.nms.store.relational.RelationalAPI;
/*     */ import com.adventnet.nms.topodb.TopoAPI;
/*     */ import com.adventnet.nms.util.ClientParameters;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.util.PureServerUtilsFE;
/*     */ import com.adventnet.nms.util.RunProcessInterface;
/*     */ import com.adventnet.nms.webserver.HttpServlet;
/*     */ import com.adventnet.nms.webserver.ServletException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.Connection;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TimeZone;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.GenericServlet;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class StartClientServlet extends HttpServlet
/*     */ {
/*  41 */   String serverNotInitialized = new String("Sorry");
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
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  55 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*     */   {
/*  60 */     GenericUtility.KeyForCvcVSloginTime.put(paramHttpServletRequest.getSession(true).getId(), new Date());
/*  61 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/*  62 */     String str1 = paramHttpServletRequest.getParameter("userName");
/*  63 */     if (str1 == null) {
/*  64 */       str1 = paramHttpServletRequest.getHeader("userName");
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
/*  97 */     if ((!NmsUtil.webNMSModulesStarted) || (!NmsMainFE.isStarted))
/*     */     {
/*  99 */       localPrintWriter.println(this.serverNotInitialized);
/* 100 */       localPrintWriter.flush();
/* 101 */       localPrintWriter.close();
/* 102 */       return;
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
/* 125 */     for (Enumeration localEnumeration1 = NmsUtil.runProcessModules.keys(); localEnumeration1.hasMoreElements();)
/*     */     {
/* 127 */       String str2 = (String)localEnumeration1.nextElement();
/* 128 */       localObject1 = (RunProcessInterface)NmsUtil.runProcessModules.get(str2);
/* 129 */       if (!((RunProcessInterface)localObject1).isInitialized())
/*     */       {
/* 131 */         localPrintWriter.println(this.serverNotInitialized);
/* 132 */         localPrintWriter.flush();
/* 133 */         localPrintWriter.close();
/* 134 */         return;
/*     */       }
/*     */     }
/*     */     
/* 138 */     if (!NmsMainFE.singleJVM)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 143 */         NmsMainFE.downloadClientFilesFromBE(str1);
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/* 147 */         NmsLogMgr.MISCERR.fail("Exception in downloading client related files from the BackEnd Server...", localException1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 155 */       Connection localConnection = NmsUtil.relapi.getConnection();
/* 156 */       localObject1 = new DBXmlUpdate(localConnection);
/* 157 */       boolean bool = ((DBXmlUpdate)localObject1).updateDB(str1, "Tree.xml");
/* 158 */       NmsLogMgr.MISCUSER.log("Populated database for User " + str1 + " " + String.valueOf(bool) + " ", 1);
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/* 162 */       NmsLogMgr.MISCERR.fail("Exception in Populating the database for the user...", localException2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 169 */     UserConfigAPI localUserConfigAPI = (UserConfigAPI)NmsUtil.getAPI("UserConfigAPI");
/* 170 */     Object localObject1 = null;
/* 171 */     if (localUserConfigAPI != null)
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
/* 183 */       localObject2 = localUserConfigAPI.getRealm(str1);
/* 184 */       if (localObject2 != null)
/*     */       {
/* 186 */         localObject1 = getRealmString(((Vector)localObject2).toString().trim());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 191 */       localPrintWriter.println(this.serverNotInitialized);
/* 192 */       localPrintWriter.flush();
/* 193 */       localPrintWriter.close();
/* 194 */       return;
/*     */     }
/* 196 */     Object localObject2 = paramHttpServletRequest.getRemoteAddr();
/* 197 */     NmsUtil.current_keys.addElement(localObject2);
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
/* 210 */     String str3 = paramHttpServletRequest.getSession().getId();
/* 211 */     localPrintWriter.println("<PARAM name=jsessionid value=" + str3 + ">");
/*     */     
/* 213 */     localPrintWriter.println("<PARAM name=MAP_FILE value=ipnet.netmap>");
/* 214 */     localPrintWriter.println("<PARAM name=SHOW_BUTTONS value=false>");
/* 215 */     localPrintWriter.println("<PARAM name=PASSWORD_KEY value=" + str1 + ">");
/* 216 */     localPrintWriter.println("<PARAM name=HANDLE value=" + str1 + ">");
/* 217 */     localPrintWriter.println("<PARAM name=USE_MAS value=true>");
/* 218 */     localPrintWriter.println("<PARAM name=USER_NAME value=" + str1 + ">");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 223 */     localPrintWriter.println("<PARAM name=NMS_FE_SECONDARY_PORT value=\"" + PureServerUtilsFE.nms_fe_secondary_port + "\">");
/* 224 */     localPrintWriter.println("<PARAM name=NMS_FE_SECONDARY_PORT_DIR value=\"" + PureServerUtilsFE.nms_fe_secondary_port_dir + "\">");
/*     */     
/* 226 */     if (!com.adventnet.nms.fe.sas.NmsSAServerFE.isSAServerRunning())
/*     */     {
/* 228 */       localPrintWriter.println("<PARAM name=TRANSPORT_PROVIDER value=\"com.adventnet.nms.client.sas.SASClientTransporter\">");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 233 */     if (localObject1 != null)
/*     */     {
/* 235 */       localPrintWriter.println("<PARAM name=REALMS value=" + (String)localObject1 + ">");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 241 */     String str4 = PureServerUtilsFE.be_host;
/* 242 */     if (str4.equals("localhost"))
/*     */     {
/*     */       try
/*     */       {
/* 246 */         str4 = InetAddress.getLocalHost().getHostName();
/*     */       }
/*     */       catch (Exception localException3) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 253 */     String str5 = PureServerUtilsFE.be_host;
/* 254 */     if (str5.equals("localhost"))
/*     */     {
/*     */       try
/*     */       {
/* 258 */         str5 = InetAddress.getLocalHost().getHostAddress();
/*     */ 
/*     */ 
/*     */       }
/*     */       catch (Exception localException4) {}
/*     */     }
/*     */     else
/*     */     {
/* 266 */       str5 = InetAddress.getByName(str5).getHostAddress();
/*     */     }
/*     */     
/* 269 */     localPrintWriter.println("<PARAM name=BE_HOST_NAME value=\"" + str4 + "\">");
/*     */     
/*     */ 
/* 272 */     localPrintWriter.println("<PARAM name=BE_HOST_ADDRESS value=\"" + str5 + "\">");
/*     */     
/*     */ 
/* 275 */     int i = NmsUtil.getRegistryPort();
/*     */     
/* 277 */     if (i == -1)
/*     */     {
/* 279 */       i = 1099;
/*     */     }
/*     */     
/* 282 */     localPrintWriter.println("<PARAM name=RMI_REG_PORT value=\"" + i + "\">");
/*     */     
/* 284 */     localPrintWriter.println("<PARAM name=CLIENT_SERVER value=" + NmsMainFE.proto + ">");
/*     */     
/* 286 */     Calendar localCalendar = Calendar.getInstance();
/* 287 */     localCalendar.set(1998, 0, 1, 0, 0, 0);
/* 288 */     Date localDate = localCalendar.getTime();
/* 289 */     localPrintWriter.println("<PARAM name=jan1_98 value=" + localDate.getTime() + ">");
/*     */     
/*     */ 
/*     */ 
/* 293 */     readClientParameters(str1);
/*     */     
/*     */ 
/* 296 */     String str6 = TimeZone.getDefault().getID();
/* 297 */     String str7 = getBETimeZoneID();
/*     */     
/* 299 */     localPrintWriter.println("<PARAM name=BE_TIMEZONE_ID value=" + str7 + ">");
/* 300 */     localPrintWriter.println("<PARAM name=FE_TIMEZONE_ID value=" + str6 + ">");
/*     */     
/*     */ 
/*     */ 
/* 304 */     for (Enumeration localEnumeration2 = this.paramList.keys(); localEnumeration2.hasMoreElements();)
/*     */     {
/* 306 */       String str8 = (String)localEnumeration2.nextElement();
/* 307 */       localPrintWriter.println("<PARAM name=\"" + str8 + "\" value=\"" + (String)this.paramList.get(str8) + "\">");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 312 */     NmsLogMgr.MISCUSER.log("User " + str1 + "  logged into the Application Client at " + JspUtility.gettime() + " from " + (String)localObject2, 1);
/*     */     
/* 314 */     localPrintWriter.flush();
/* 315 */     localPrintWriter.close();
/*     */   }
/*     */   
/*     */   private String getRealmString(String paramString)
/*     */   {
/* 320 */     paramString = paramString.substring(1, paramString.length() - 1);
/* 321 */     StringBuffer localStringBuffer = new StringBuffer();
/* 322 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", true);
/* 323 */     while (localStringTokenizer.hasMoreTokens())
/*     */     {
/* 325 */       localStringBuffer.append(localStringTokenizer.nextToken().trim());
/*     */     }
/* 327 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/* 330 */   Hashtable paramList = new Hashtable(50);
/*     */   
/*     */ 
/*     */ 
/*     */   public void readClientParameters(String paramString)
/*     */   {
/* 336 */     this.paramList.clear();
/* 337 */     String str1 = NmsMainFE.proto;
/* 338 */     ClientParameters localClientParameters = new ClientParameters();
/* 339 */     localClientParameters.readClientParameters(this.paramList);
/*     */     Object localObject2;
/* 341 */     if (paramString != null)
/*     */     {
/* 343 */       localObject1 = new ClientParameters(paramString);
/* 344 */       localObject2 = new Hashtable(50);
/* 345 */       ((ClientParameters)localObject1).readClientParameters((Hashtable)localObject2);
/* 346 */       this.paramList.putAll((java.util.Map)localObject2);
/*     */     }
/*     */     
/* 349 */     Object localObject1 = (String)this.paramList.get("INIT_PANEL");
/*     */     
/* 351 */     if (localObject1 == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 357 */       localObject1 = "ipnet.netmap";
/*     */       try
/*     */       {
/* 360 */         localObject2 = GenericUtility.getTopoAPI();
/* 361 */         localObject1 = ((TopoAPI)localObject2).getLocalNetworkAddrs() + ".netmap";
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 365 */         System.err.println("Exception while obtaining the LocalNetworkAddr from the Server.");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 370 */         localException.printStackTrace();
/* 371 */         localObject1 = "ipnet.netmap";
/*     */       }
/*     */       
/* 374 */       this.paramList.put("INIT_PANEL", localObject1);
/*     */     }
/*     */     
/* 377 */     this.paramList.put("CLIENT_SERVER", str1);
/* 378 */     String str2 = PureServerUtilsFE.getClientTransportClassName();
/* 379 */     this.paramList.put("CLIENT_CLASS_NAME", str2);
/* 380 */     this.paramList.put("KEEPALIVE_WINDOW_SIZE", new Integer(NmsUtil.keepalive_window_size).toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void log(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 389 */       if (paramString == null) super.log("No detail."); else {
/* 390 */         super.log(paramString);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 394 */       System.err.println("Problems logging.  Ignoring exception: " + localException);
/* 395 */       localException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private String getBETimeZoneID()
/*     */   {
/* 401 */     BEServerContext localBEServerContext = PureServerUtilsFE.getBEServerContext();
/* 402 */     Properties localProperties = localBEServerContext.getProperties();
/* 403 */     String str = (String)localProperties.get("TimeZoneID");
/* 404 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\StartClientServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */