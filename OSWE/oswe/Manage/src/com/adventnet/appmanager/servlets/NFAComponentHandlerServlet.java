/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import HTTPClient.HTTPConnection;
/*     */ import HTTPClient.HTTPResponse;
/*     */ import HTTPClient.URI;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.extprod.TrafficDevicesSynchronizer;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.ExtProdUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.net.ConnectException;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NFAComponentHandlerServlet
/*     */   extends HttpServlet
/*     */ {
/*  41 */   TrafficDevicesSynchronizer tds = null;
/*     */   
/*  43 */   public void service(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException { String reqType = rq.getParameter("reqType");
/*  44 */     Element rootElement = null;
/*  45 */     if ("complete_re_sync".equalsIgnoreCase(reqType))
/*     */     {
/*  47 */       rootElement = getCompleteReSyncRootelement();
/*     */     }
/*     */     else
/*     */     {
/*  51 */       InputStream ins = rq.getInputStream();
/*  52 */       rootElement = getRootElement(ins);
/*     */     }
/*  54 */     handleNetflowRouters(rootElement);
/*  55 */     handleNetflowInterfaces(rootElement);
/*     */   }
/*     */   
/*     */   private Element getCompleteReSyncRootelement() {
/*  59 */     Element toReturn = null;
/*  60 */     AMLog.debug("TrafficDevicesSynchronizer started...");
/*     */     try
/*     */     {
/*  63 */       Properties productInfo = ExtProdUtil.getExtProductInfoUsingMasId(Integer.toString(EnterpriseUtil.getManagedServerIndex()), "NetflowAnalyzer");
/*  64 */       AMLog.debug("productInfo - " + productInfo);
/*     */       
/*     */ 
/*  67 */       int prodId = Integer.parseInt((String)productInfo.get("ID"));
/*  68 */       String urlToContact = (String)productInfo.get("URLTOCONTACT");
/*     */       
/*  70 */       int responseCode = -1;
/*  71 */       boolean xmlcheck = false;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/*  76 */         URI uri = new URI(urlToContact);
/*  77 */         HTTPConnection con = new HTTPConnection(uri);
/*  78 */         HTTPResponse rsp = con.Post(urlToContact);
/*  79 */         responseCode = rsp.getStatusCode();
/*  80 */         AMLog.debug("TrafficDevicesSynchronizer responseCode  - " + responseCode);
/*  81 */         if (responseCode == 404)
/*     */         {
/*  83 */           AMLog.debug("The requested URL is not found on the remote server. The URL is " + urlToContact);
/*  84 */           String tempStr = new String(rsp.getData());
/*  85 */           AMLog.debug("The response from the Remote product is ===>>> " + tempStr);
/*  86 */           throw new NullPointerException(FormatUtil.getString("am.webclient.addon.servernotfound.message", new String[] { urlToContact, "NetflowAnalyzer" }));
/*     */         }
/*  88 */         byte[] rawdata = rsp.getData();
/*  89 */         if (rawdata == null)
/*     */         {
/*  91 */           throw new NullPointerException(FormatUtil.getString("am.webclient.addon.userpwd.message", new String[] { "NetflowAnalyzer" }));
/*     */         }
/*     */         
/*     */ 
/*  95 */         String resp = new String(rsp.getData());
/*  96 */         if (resp.indexOf("<?xml version=") != -1)
/*     */         {
/*  98 */           xmlcheck = true;
/*     */         }
/*     */         
/* 101 */         if (xmlcheck)
/*     */         {
/* 103 */           ByteArrayInputStream bis = new ByteArrayInputStream(rawdata);
/* 104 */           DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 105 */           DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 106 */           toReturn = docBuilder.parse(bis).getDocumentElement();
/* 107 */           bis.close();
/*     */           
/* 109 */           if (toReturn != null)
/*     */           {
/*     */             try
/*     */             {
/* 113 */               String query = "delete from NetflowRouters where PRODUCTID=" + prodId;
/* 114 */               AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query);
/* 115 */               EnterpriseUtil.addUpdateQueryToFile(query);
/* 116 */               query = "delete from NetflowInterfaces where PRODUCTID=" + prodId;
/* 117 */               AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query);
/* 118 */               EnterpriseUtil.addUpdateQueryToFile(query);
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 122 */               ex.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 128 */           AMLog.debug("The response from the Remote product is ::: " + new String(rsp.getData()));
/*     */         }
/*     */       }
/*     */       catch (ConnectException ce)
/*     */       {
/* 133 */         AMLog.debug("INTEGRATION : Connection refused as Server is Not Running");
/* 134 */         ce.printStackTrace();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 138 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 143 */       ex.printStackTrace();
/*     */     }
/* 145 */     return toReturn;
/*     */   }
/*     */   
/*     */   private void handleNetflowInterfaces(Element rootElement) {
/* 149 */     NodeList interfaces = rootElement.getElementsByTagName("INTERFACE");
/* 150 */     if ((interfaces != null) && (interfaces.getLength() > 0))
/*     */     {
/* 152 */       for (int i = 0; i < interfaces.getLength(); i++)
/*     */       {
/* 154 */         Element interfaceelement = (Element)interfaces.item(i);
/* 155 */         handleNetflowInterface(interfaceelement);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleNetflowRouters(Element rootElement) {
/* 161 */     NodeList routers = rootElement.getElementsByTagName("ROUTER");
/* 162 */     if ((routers != null) && (routers.getLength() > 0))
/*     */     {
/* 164 */       for (int i = 0; i < routers.getLength(); i++)
/*     */       {
/* 166 */         Element routerElement = (Element)routers.item(i);
/* 167 */         handleNetflowRouter(routerElement);
/* 168 */         handleNetflowInterfaces(routerElement);
/*     */       } }
/*     */   }
/*     */   
/*     */   public static Element getRootElement(InputStream inStream) {
/*     */     try {
/* 174 */       InputStreamReader isr = new InputStreamReader(inStream, "UTF-8");
/* 175 */       LineNumberReader lnr = new LineNumberReader(isr);
/* 176 */       InputSource isrc = new InputSource(lnr);
/*     */       
/* 178 */       DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 179 */       DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 180 */       Document doc = docBuilder.parse(isrc);
/* 181 */       return doc.getDocumentElement();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 185 */       e.printStackTrace();
/*     */     }
/* 187 */     return null;
/*     */   }
/*     */   
/* 190 */   private static void handleNetflowInterface(Element interfaceelement) { String state = null;
/* 191 */     String operation = interfaceelement.getAttribute("OPERATION");
/* 192 */     String intfId = interfaceelement.getAttribute("ID");
/* 193 */     Properties productInfo = ExtProdUtil.getExtProductInfoUsingMasId(Integer.toString(EnterpriseUtil.getManagedServerIndex()), "NetflowAnalyzer");
/*     */     
/* 195 */     int prodId = Integer.parseInt((String)productInfo.get("ID"));
/* 196 */     deleteNFAInterface(intfId, prodId);
/* 197 */     if (!"INTERFACE_REMOVED".equals(operation))
/*     */     {
/* 199 */       addInterface(interfaceelement, prodId);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void addInterface(Element interfaceelement, int prodId)
/*     */   {
/* 206 */     String intfId = interfaceelement.getAttribute("ID");
/* 207 */     String intf_name = handleSpecialChars(interfaceelement.getAttribute("NAME"));
/*     */     
/* 209 */     String intf_index = interfaceelement.getAttribute("Index");
/* 210 */     String routerId = interfaceelement.getAttribute("RouterID");
/* 211 */     String intf_state = interfaceelement.getAttribute("STATE");
/*     */     try
/*     */     {
/* 214 */       String query = "insert into NetflowInterfaces values( '" + intfId + "' , '" + intf_name + "', '" + intf_index + "', '" + intf_state + "', '" + routerId + "', " + prodId + " );";
/* 215 */       AMConnectionPool.executeUpdateStmt(query);
/* 216 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*     */     } catch (SQLException e) {
/* 218 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String handleSpecialChars(String str)
/*     */   {
/* 226 */     if (str != null)
/*     */     {
/* 228 */       if (str.indexOf("\"") != -1)
/*     */       {
/* 230 */         str = URLEncoder.encode(str);
/*     */       }
/* 232 */       if (str.indexOf("'") != -1)
/*     */       {
/* 234 */         str = EnterpriseUtil.modStringForSingleQuote(str);
/*     */       }
/*     */     }
/* 237 */     return str;
/*     */   }
/*     */   
/*     */   private static void deleteNFAInterface(String intfId, int prodId) {
/*     */     try {
/* 242 */       String query = "delete from NetflowInterfaces where INTF_ID = '" + intfId + "' and PRODUCTID='" + prodId + "';";
/* 243 */       AMConnectionPool.executeUpdateStmt(query);
/* 244 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*     */     } catch (SQLException e) {
/* 246 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void handleNetflowRouter(Element routerElement) {
/* 251 */     Properties productInfo = ExtProdUtil.getExtProductInfoUsingMasId(Integer.toString(EnterpriseUtil.getManagedServerIndex()), "NetflowAnalyzer");
/* 252 */     int prodId = Integer.parseInt((String)productInfo.get("ID"));
/*     */     
/* 254 */     String Id = routerElement.getAttribute("ID");
/* 255 */     String rout_name = routerElement.getAttribute("NAME");
/* 256 */     String rout_Ip = routerElement.getAttribute("ROUTERIP");
/* 257 */     int routid = Integer.parseInt(Id);
/*     */     try {
/* 259 */       String query = "delete from NetflowRouters where ROUTERID = " + routid + " ;";
/* 260 */       AMConnectionPool.executeUpdateStmt(query);
/* 261 */       EnterpriseUtil.addUpdateQueryToFile(query);
/* 262 */       query = "insert into NetflowRouters values( " + routid + " ,'" + rout_Ip + "' ,'" + rout_name + "' ," + prodId + " );";
/* 263 */       AMConnectionPool.executeUpdateStmt(query);
/* 264 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*     */     }
/*     */     catch (SQLException e) {
/* 267 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\NFAComponentHandlerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */