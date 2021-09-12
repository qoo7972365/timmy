/*      */ package com.adventnet.appmanager.filter;
/*      */ 
/*      */ import HTTPClient.HTTPConnection;
/*      */ import HTTPClient.NVPair;
/*      */ import HTTPClient.URI;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.servlet.Filter;
/*      */ import javax.servlet.FilterChain;
/*      */ import javax.servlet.FilterConfig;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.ServletOutputStream;
/*      */ import javax.servlet.ServletRequest;
/*      */ import javax.servlet.ServletResponse;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.httpclient.HttpClient;
/*      */ import org.apache.commons.httpclient.methods.PostMethod;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ public class ExtProdIntegFilter
/*      */   implements Filter
/*      */ {
/*   57 */   private FilterConfig config = null;
/*   58 */   private ServletContext moServletContext = null;
/*   59 */   private boolean needFilter = false;
/*   60 */   private static boolean isReqFromCentral = false;
/*   61 */   private static boolean toIncludeHeader = false;
/*   62 */   private static boolean isIT360 = com.adventnet.appmanager.util.Constants.isIt360;
/*      */   
/*      */   public void init(FilterConfig config) throws ServletException
/*      */   {
/*   66 */     this.config = config;
/*   67 */     this.moServletContext = config.getServletContext();
/*   68 */     AMLog.debug("ExtProdIntegFilter : Filter to take effect ?  " + this.needFilter);
/*      */   }
/*      */   
/*      */   public void destroy()
/*      */   {
/*   73 */     this.config = null;
/*      */   }
/*      */   
/*      */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*      */     throws IOException, ServletException
/*      */   {
/*   79 */     ResultSet set = null;
/*   80 */     ResultSet set1 = null;
/*      */     try
/*      */     {
/*   83 */       if (!(request instanceof HttpServletRequest))
/*      */       {
/*   85 */         chain.doFilter(request, response);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*   90 */         HttpServletRequest hreq = (HttpServletRequest)request;
/*      */         
/*      */ 
/*   93 */         String extProdName = hreq.getParameter("extProdName");
/*   94 */         String masId = hreq.getParameter("EXTDEVICEMASID");
/*   95 */         String fromCentral = hreq.getParameter("fromCentral");
/*   96 */         String uri = hreq.getRequestURI();
/*      */         
/*      */ 
/*   99 */         String isInternalRequest = request.getParameter("isInternalRequest");
/*  100 */         if ((uri.indexOf("/Widgets/") != -1) && ((isInternalRequest == null) || ((isInternalRequest != null) && (!isInternalRequest.equals("true"))))) {
/*      */           return;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  106 */         if ((uri.indexOf("/devices/ProcessRequest.do") != -1) && (request.getParameter("_apmHost") != null) && (masId == null) && (extProdName == null)) {
/*      */           try
/*      */           {
/*  109 */             String masName = hreq.getParameter("_apmHost");
/*  110 */             masId = EnterpriseUtil.getMASStartRange(masName) / EnterpriseUtil.RANGE + "";
/*  111 */             extProdName = "OpManager";
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  115 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*  118 */         String includeHeader = hreq.getParameter("includeHeader");
/*  119 */         String tileName = hreq.getParameter("tileName");
/*  120 */         String forOpStorCache = hreq.getParameter("forOpStorCache");
/*      */         
/*  122 */         String isTablet = (String)hreq.getSession().getAttribute("isTablet");
/*      */         
/*  124 */         isReqFromCentral = (fromCentral != null) && (fromCentral.trim().equals("true"));
/*  125 */         toIncludeHeader = (includeHeader != null) && (includeHeader.trim().equals("true"));
/*      */         
/*      */ 
/*  128 */         if ((extProdName == null) || ((tileName != null) && (tileName.trim().equals("Tablet.InnerPages"))))
/*      */         {
/*  130 */           chain.doFilter(request, response);
/*      */ 
/*      */ 
/*      */         }
/*  134 */         else if (("true".equals(forOpStorCache)) && (EnterpriseUtil.isManagedServer()))
/*      */         {
/*  136 */           ServletOutputStream out = response.getOutputStream();
/*  137 */           String file = hreq.getParameter("file");
/*  138 */           AMConnectionPool pool = AMConnectionPool.getInstance();
/*  139 */           String selectQuery = "select PROTOCOL,HOST,PORT,USERNAME," + DBQueryUtil.decode(com.adventnet.appmanager.util.Constants.EXTPRODENCODEKEY, "PASSWORD") + " as PASSWORD from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + extProdName + "' and MASID='" + masId + "'";
/*  140 */           if (isIT360)
/*      */           {
/*      */ 
/*      */ 
/*  144 */             selectQuery = "select PROTOCOL,HOST,PORT,USERNAME from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + extProdName + "' and MASID='" + masId + "'";
/*      */           }
/*  146 */           String host = null;
/*  147 */           String port = null;
/*  148 */           String protocol = "http";
/*  149 */           String userName = "admin";
/*  150 */           String password = "admin";
/*  151 */           if (masId != null) {
/*  152 */             set1 = AMConnectionPool.executeQueryStmt(selectQuery);
/*  153 */             if (set1.next())
/*      */             {
/*  155 */               host = set1.getString("HOST");
/*  156 */               port = set1.getString("PORT");
/*  157 */               protocol = set1.getString("PROTOCOL");
/*  158 */               userName = set1.getString("USERNAME");
/*  159 */               if (!isIT360)
/*      */               {
/*  161 */                 password = set1.getString("PASSWORD");
/*      */               }
/*      */             }
/*      */           }
/*  165 */           String urlToFetchOpStorData = protocol + "://" + host + ":" + port + file + "?PRINTER_FRIENDLY=true";
/*      */           
/*  167 */           String nmshome = System.getProperty("webnms.rootdir");
/*  168 */           Properties formProps = new Properties();
/*  169 */           formProps.setProperty("extProdName", extProdName);
/*  170 */           formProps.setProperty("nmshome", nmshome);
/*      */           
/*  172 */           com.manageengine.appmanager.comm.HTTPResponse ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchOpStorData, "Post", formProps, null, 0);
/*  173 */           int respCode1 = ress.getStatusCode();
/*  174 */           if (respCode1 == 200)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  182 */             byte[] data = ress.getData();
/*  183 */             if (data != null) {
/*  184 */               out.write(data);
/*      */             }
/*      */           }
/*  187 */           out.flush();
/*  188 */           chain.doFilter(request, response);
/*      */         }
/*      */         else
/*      */         {
/*  192 */           String extProdProtocol = "";
/*  193 */           String extProdHost = "";
/*  194 */           String extProdPort = "";
/*  195 */           if ((masId != null) && (EnterpriseUtil.isAdminServer()))
/*      */           {
/*  197 */             Properties prodInfo = ExtProdUtil.getExtProductInfoUsingMasId(masId, extProdName);
/*  198 */             if (prodInfo != null)
/*      */             {
/*  200 */               extProdProtocol = prodInfo.getProperty("protocol");
/*  201 */               extProdHost = prodInfo.getProperty("host");
/*  202 */               extProdPort = prodInfo.getProperty("port");
/*      */             }
/*      */           }
/*  205 */           StringBuffer sb = new StringBuffer();
/*  206 */           for (Iterator<String> keyIter = request.getParameterMap().keySet().iterator(); keyIter.hasNext();)
/*      */           {
/*  208 */             String paramKey = (String)keyIter.next();
/*  209 */             String paramValue = request.getParameter(paramKey);
/*  210 */             if (paramValue != null)
/*      */             {
/*  212 */               if (!Pattern.matches("[a-zA-Z0-9]*", paramValue))
/*      */               {
/*  214 */                 paramValue = URLEncoder.encode(paramValue);
/*      */               }
/*  216 */               sb.append(paramKey + "=" + paramValue + "&");
/*      */             }
/*      */           }
/*  219 */           String additionalParams = sb.toString();
/*      */           
/*      */ 
/*  222 */           if ((("OpManager".equalsIgnoreCase(extProdName)) || ("OpStor".equalsIgnoreCase(extProdName))) && (uri.indexOf("/fault/AlarmActions.do") != -1))
/*      */           {
/*  224 */             if (additionalParams.indexOf("printerFriendly") != -1)
/*      */             {
/*  226 */               additionalParams = additionalParams.replaceAll("&printerFriendly=true", "");
/*      */             }
/*  228 */             if (additionalParams.indexOf("PRINTER_FRIENDLY") == -1)
/*      */             {
/*  230 */               additionalParams = additionalParams + "&PRINTER_FRIENDLY=true";
/*      */             }
/*      */           }
/*  233 */           else if ((("OpManager".equalsIgnoreCase(extProdName)) || ("OpStor".equalsIgnoreCase(extProdName))) && (additionalParams.indexOf("printerFriendly") == -1))
/*      */           {
/*      */ 
/*      */ 
/*  237 */             if ((!com.adventnet.appmanager.util.Constants.isIt360) || (EnterpriseUtil.isAdminServer()) || (uri.indexOf("/devices/objectdetails.do") == -1))
/*      */             {
/*  239 */               additionalParams = additionalParams + "&printerFriendly=true";
/*      */             }
/*      */           }
/*  242 */           else if (("OpStor".equalsIgnoreCase(extProdName)) && (additionalParams.indexOf("PRINTER_FRIENDLY") == -1) && (!isIT360))
/*      */           {
/*  244 */             additionalParams = additionalParams + "&PRINTER_FRIENDLY=true";
/*      */           }
/*      */           
/*  247 */           if ((isTablet != null) && (isTablet.trim().equals("true")))
/*      */           {
/*  249 */             additionalParams = additionalParams + "&PRINTER_FRIENDLY=true";
/*      */           }
/*      */           
/*  252 */           String url = uri + "?" + additionalParams;
/*      */           
/*  254 */           AMLog.debug("ExtProdIntegFilter : url to request  " + url);
/*      */           
/*  256 */           HttpServletResponse hresponse = (HttpServletResponse)response;
/*  257 */           hresponse.setContentType("text/html; charset=UTF-8");
/*      */           
/*  259 */           if (isIT360)
/*      */           {
/*  261 */             if ((!EnterpriseUtil.isAdminServer()) && (!isReqFromCentral) && (uri.indexOf("generateWidget.do") == -1))
/*      */             {
/*  263 */               String oldtab = "1";
/*  264 */               if (extProdName.equals("OpStor"))
/*      */               {
/*  266 */                 oldtab = "9";
/*      */               }
/*  268 */               RequestDispatcher rd = request.getRequestDispatcher("/showTile.do?TileName=IT360.IFrame&oldtab=" + oldtab + "&url=" + url + "&service=" + extProdName);
/*  269 */               rd.include(hreq, hresponse); return;
/*      */             }
/*      */           }
/*      */           
/*  273 */           AMConnectionPool pool = AMConnectionPool.getInstance();
/*  274 */           String selectQuery = "select PROTOCOL,HOST,PORT,USERNAME," + DBQueryUtil.decode(com.adventnet.appmanager.util.Constants.EXTPRODENCODEKEY, "PASSWORD") + " as PASSWORD,APIKEY from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + extProdName + "' and MASID='" + masId + "'";
/*  275 */           if (isIT360)
/*      */           {
/*      */ 
/*      */ 
/*  279 */             selectQuery = "select PROTOCOL,HOST,PORT,USERNAME from AM_INTEGRATEDPRODUCTS where PRODUCT_NAME='" + extProdName + "' and MASID='" + masId + "'";
/*      */           }
/*  281 */           String host = null;
/*  282 */           String port = null;
/*  283 */           String protocol = "http";
/*  284 */           String userName = "admin";
/*  285 */           String password = "admin";
/*  286 */           String apiKey = null;
/*      */           
/*  288 */           set = AMConnectionPool.executeQueryStmt(selectQuery);
/*  289 */           if (set.next())
/*      */           {
/*  291 */             host = set.getString("HOST");
/*  292 */             port = set.getString("PORT");
/*  293 */             protocol = set.getString("PROTOCOL");
/*  294 */             userName = set.getString("USERNAME");
/*  295 */             if (!isIT360)
/*      */             {
/*  297 */               password = set.getString("PASSWORD");
/*      */             }
/*  299 */             apiKey = set.getString("APIKEY");
/*      */           }
/*  301 */           if (uri.indexOf("/devices/objectdetails.do") != -1)
/*      */           {
/*  303 */             String urlToContact = protocol + "://" + host + ":" + port;
/*  304 */             String resName = hreq.getParameter("name");
/*      */             
/*  306 */             if (apiKey == null)
/*      */             {
/*  308 */               apiKey = getAPIKeyFromOPM(urlToContact, userName, password, hreq, hresponse);
/*      */             }
/*  310 */             if ((apiKey != null) && (!apiKey.equals("")))
/*      */             {
/*  312 */               apiKey = validateAndGenerateApiKey(urlToContact, apiKey, userName, password, hreq, hresponse);
/*  313 */               if (apiKey == null) {
/*      */                 return;
/*      */               }
/*      */               
/*  317 */               hreq.setAttribute("opmApiKey", apiKey);
/*      */               
/*  319 */               RequestDispatcher dispatcher = hreq.getServletContext().getRequestDispatcher("/showresource.do?method=showNWDSnapshot&resName=" + resName);
/*  320 */               dispatcher.forward(hreq, hresponse); return;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  326 */           boolean status = false;
/*      */           
/*  328 */           if ((EnterpriseUtil.isAdminServer()) && ((extProdName.equals("OpManager")) || (extProdName.equals("NetflowAnalyzer")) || (extProdName.equalsIgnoreCase("OpStor"))))
/*      */           {
/*      */ 
/*  331 */             ResultSet rs = null;
/*      */             try
/*      */             {
/*  334 */               AMConnectionPool pool1 = AMConnectionPool.getInstance();
/*  335 */               long range = com.adventnet.appmanager.server.framework.comm.Constants.RANGE * Long.parseLong(masId);
/*  336 */               String selectMasQuery = "select  HOST, PORT , SSLPORT from AM_MAS_SERVER where SERVERSTATUS=1 and ALLOTED_GLOBAL_RANGE=" + range;
/*      */               
/*  338 */               rs = AMConnectionPool.executeQueryStmt(selectMasQuery);
/*  339 */               if (rs.next())
/*      */               {
/*      */ 
/*  342 */                 host = rs.getString("HOST");
/*  343 */                 port = rs.getString("SSLPORT");
/*  344 */                 protocol = "https";
/*      */               }
/*      */               
/*  347 */               status = proxyRequest(url, host, port, protocol, extProdName, userName, password, hresponse, hreq, masId);
/*      */             }
/*      */             catch (Exception ex1)
/*      */             {
/*  351 */               ex1.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*  355 */               if (rs != null)
/*      */               {
/*      */                 try
/*      */                 {
/*  359 */                   rs.close();
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/*  363 */                   e.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  372 */           status = proxyRequest(url, host, port, protocol, extProdName, userName, password, hresponse, hreq, masId);
/*      */           
/*      */ 
/*      */ 
/*  376 */           if ((!status) && (isIT360))
/*      */           {
/*  378 */             PrintWriter out = hresponse.getWriter();
/*  379 */             String tempUrl = protocol + "://" + host + ":" + port;
/*  380 */             String moduleName = com.adventnet.appmanager.util.Constants.productToServiceName.getProperty(extProdName);
/*  381 */             AMLog.debug("ExtProdIntegFilter : Could not connect probe at " + tempUrl + " May be down or request got timed out");
/*  382 */             out.println(FormatUtil.getString("it360.probe.module.down.message", new String[] { moduleName, tempUrl }));
/*      */           }
/*      */         }
/*      */       }
/*      */       return;
/*  387 */     } catch (Exception e) { e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  391 */       if (set != null)
/*      */       {
/*      */         try
/*      */         {
/*  395 */           set.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  399 */           e.printStackTrace();
/*      */         }
/*      */       }
/*  402 */       if (set1 != null)
/*      */       {
/*      */         try
/*      */         {
/*  406 */           set1.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  410 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean proxyRequest(String url, String host, String port, String protocol, String prodName, String userName, String password, HttpServletResponse hresponse, HttpServletRequest hreq, String masId)
/*      */   {
/*  418 */     ArrayList list = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  423 */       if ((hreq.getQueryString().indexOf("getRespAs=XML") != -1) || (hreq.getQueryString().indexOf("_apmHost") != -1))
/*      */       {
/*      */         try
/*      */         {
/*  427 */           getProbePointProductDataAsXML(protocol, host, port, url, hreq, hresponse, masId, prodName);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  431 */           AMLog.debug("Issue in getting XML content from probe product ::: " + ex.getMessage());
/*  432 */           ex.printStackTrace();
/*  433 */           return false;
/*      */         }
/*  435 */         return true;
/*      */       }
/*      */       
/*  438 */       String urlName = protocol + "://" + host + ":" + port + url;
/*  439 */       if (isIT360)
/*      */       {
/*  441 */         if (EnterpriseUtil.isAdminServer)
/*      */         {
/*  443 */           urlName = urlName + "&fromCentral=true";
/*      */           try
/*      */           {
/*  446 */             File f = new File(System.getProperty("webnms.rootdir") + File.separator + "ancillary" + File.separator + "en" + File.separator + "html" + File.separator + "flashUI_en_US.properties");
/*  447 */             if ((EnterpriseUtil.isAdminServer()) && (!f.exists()))
/*      */             {
/*  449 */               copyFileFromProbeToCentral(protocol + "://" + host + ":" + port + "/opmancillaryfiles/en/html/flashUI_en_US.properties", "/ancillary/en/html", "flashUI_en_US.properties");
/*  450 */               copyFileFromProbeToCentral(protocol + "://" + host + ":" + port + "/opmancillaryfiles/en/html/flashUI_en_US.properties", "/ancillary/cn/html", "flashUI_zh_CN.properties");
/*  451 */               copyFileFromProbeToCentral(protocol + "://" + host + ":" + port + "/opmancillaryfiles/en/html/flashUI_en_US.properties", "/ancillary/fr/html", "flashUI_fr_FR.properties");
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  456 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  459 */         return proxyRequestForIT360(protocol, urlName, host, port, hresponse, hreq, userName, password, prodName, masId);
/*      */       }
/*      */       
/*  462 */       URI uri1 = new URI(getHostPort(urlName));
/*  463 */       HTTPConnection con = new HTTPConnection(uri1);
/*  464 */       String tempString = "AppManager_Integration";
/*  465 */       con.setContext(tempString);
/*      */       
/*  467 */       NVPair nvpairP = new NVPair("Pragma", "no-cache");
/*  468 */       NVPair[] headersP = { nvpairP };
/*  469 */       HTTPClient.HTTPResponse rsp1 = null;
/*  470 */       if (("OpStor".equals(prodName)) && (System.getProperty("locale").equalsIgnoreCase("zh_CN"))) {
/*  471 */         NVPair nvpairLocale1 = new NVPair("Accept-Language", "zh-CN,zh;q=0.8");
/*  472 */         NVPair[] reqheaders = { nvpairP, nvpairLocale1 };
/*  473 */         rsp1 = con.Post(getURI(urlName), headersP, reqheaders);
/*      */       } else {
/*  475 */         rsp1 = con.Post(getURI(urlName), headersP);
/*      */       }
/*      */       
/*  478 */       String contentType = rsp1.getHeader("Content-Type").toLowerCase();
/*      */       
/*  480 */       if (contentType.contains("application")) {
/*  481 */         String fileName = contentType.contains("xls") ? "Report.xls" : contentType.contains("pdf") ? "Report.pdf" : "";
/*  482 */         String extprodFiles = isIT360 ? "extprodfiles/" + masId + "/" : "";
/*  483 */         String disposition = rsp1.getHeader("Content-Disposition");
/*  484 */         if ((disposition != null) && (disposition.indexOf("filename=") > 0)) {
/*  485 */           fileName = disposition.substring(disposition.indexOf("filename=") + 9, disposition.length());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  490 */         String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/*  491 */         byte[] data = rsp1.getData();
/*      */         
/*  493 */         if (EnterpriseUtil.isManagedServer)
/*      */         {
/*  495 */           File f = new File("." + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + File.separator + fileName);
/*  496 */           f.createNewFile();
/*  497 */           FileOutputStream fo = null;
/*      */           try
/*      */           {
/*  500 */             fo = new FileOutputStream(f);
/*  501 */             fo.write(data);
/*      */           }
/*      */           catch (Exception ex) {
/*  504 */             ex.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*  508 */             if (fo != null)
/*      */             {
/*  510 */               fo.close();
/*      */             }
/*      */           }
/*      */         }
/*  514 */         if (EnterpriseUtil.isAdminServer()) {
/*  515 */           urlName = protocol + "://" + host + ":" + port + "/" + extProductDir + "/" + fileName;
/*      */           
/*      */ 
/*  518 */           URI uri2 = new URI(getHostPort(urlName));
/*  519 */           HTTPConnection con2 = new HTTPConnection(uri2);
/*  520 */           con2.setContext(tempString);
/*      */           
/*  522 */           HTTPClient.HTTPResponse rsp2 = con2.Post(getURI(urlName), headersP);
/*  523 */           if (rsp2.getStatusCode() == 200) {
/*  524 */             data = rsp2.getData();
/*      */           }
/*      */         }
/*      */         
/*  528 */         ByteArrayInputStream bis = null;
/*  529 */         BufferedOutputStream bos = null;
/*      */         try
/*      */         {
/*  532 */           hresponse.reset();
/*  533 */           hresponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
/*  534 */           hresponse.setHeader("Pragma", "public");
/*  535 */           hresponse.setHeader("Content-Type", contentType);
/*  536 */           hresponse.setDateHeader("Expires", 0L);
/*  537 */           hresponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
/*  538 */           if (("OpStor".equals(prodName)) && (System.getProperty("locale").equalsIgnoreCase("zh_CN"))) {
/*  539 */             hresponse.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
/*      */           }
/*  541 */           bis = new ByteArrayInputStream(data);
/*  542 */           bos = new BufferedOutputStream(hresponse.getOutputStream(), 10240);
/*  543 */           byte[] input = new byte['⠀'];
/*      */           int length;
/*  545 */           while ((length = bis.read(input)) > 0)
/*      */           {
/*  547 */             bos.write(input, 0, length);
/*      */           }
/*  549 */           bos.flush();
/*      */         }
/*      */         catch (Exception exe)
/*      */         {
/*  553 */           exe.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  557 */           if (bis != null)
/*      */           {
/*  559 */             bis.close();
/*      */           }
/*  561 */           if (bos != null)
/*      */           {
/*  563 */             bos.close();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  571 */       if (url.indexOf("/devices/RealTimeMonitors.do") != -1)
/*      */       {
/*  573 */         String paramMeth = hreq.getParameter("methodCall");
/*  574 */         if ((paramMeth != null) && (paramMeth.indexOf("drawChart") != -1))
/*      */         {
/*  576 */           byte[] b = rsp1.getData();
/*  577 */           String fileName = new String(b).trim();
/*      */           
/*  579 */           if (fileName.indexOf("opmanager-") != -1)
/*      */           {
/*  581 */             String fileReqUrl = protocol + "://" + host + ":" + port + "/temp/" + fileName;
/*  582 */             URI subURL = new URI(fileReqUrl);
/*      */             
/*  584 */             HTTPConnection conSub = new HTTPConnection(subURL);
/*  585 */             conSub.setContext(tempString);
/*      */             
/*  587 */             NVPair nvpairS1 = new NVPair("Pragma", "no-cache");
/*  588 */             NVPair[] headersS1 = { nvpairS1 };
/*  589 */             HTTPClient.HTTPResponse fileRes = conSub.Post(getURI(fileReqUrl), headersS1);
/*      */             
/*  591 */             String nmshome = System.getProperty("webnms.rootdir");
/*      */             
/*  593 */             File tempDir = new File(nmshome + File.separator + "temp" + File.separator);
/*  594 */             if (!tempDir.exists())
/*      */             {
/*  596 */               tempDir.mkdirs();
/*      */             }
/*  598 */             File tempfile = new File(nmshome + File.separator + "temp" + File.separator + fileName);
/*  599 */             int code = fileRes.getStatusCode();
/*  600 */             if (code >= 300)
/*      */             {
/*  602 */               AMLog.debug("Resource not found on opmanager: " + fileReqUrl);
/*      */             }
/*  604 */             else if (code == 200)
/*      */             {
/*  606 */               tempfile.createNewFile();
/*  607 */               byte[] data = fileRes.getData();
/*      */               
/*  609 */               FileOutputStream fo = null;
/*      */               try
/*      */               {
/*  612 */                 fo = new FileOutputStream(tempfile);
/*  613 */                 fo.write(data);
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  617 */                 ex.printStackTrace();
/*      */               }
/*      */               finally
/*      */               {
/*  621 */                 if (fo != null)
/*      */                 {
/*  623 */                   fo.close();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  632 */       BufferedReader br = new BufferedReader(new InputStreamReader(rsp1.getInputStream()));
/*      */       
/*  634 */       String line = null;
/*      */       
/*  636 */       String loginFormToCheck = null;
/*  637 */       String sessionTimeOut = null;
/*  638 */       String loginCheck = null;
/*      */       
/*  640 */       if (prodName.equals("OpManager"))
/*      */       {
/*  642 */         loginFormToCheck = "window.location.href =\"/LoginPage.do";
/*  643 */         loginCheck = "<form name=\"loginForm\" METHOD=post action=\"/jsp/Login.do";
/*  644 */         sessionTimeOut = "Your session had been timed out for security reasons";
/*      */       }
/*  646 */       else if (prodName.equals("OpStor"))
/*      */       {
/*  648 */         loginFormToCheck = "<form name=\"loginForm\" method=post action=\"/jsp/Login.do";
/*  649 */         sessionTimeOut = "Your session had been timed out for security reasons";
/*      */       }
/*      */       
/*  652 */       while ((line = br.readLine()) != null)
/*      */       {
/*  654 */         list.add(line);
/*      */         
/*  656 */         if (((loginFormToCheck != null) && (line.indexOf(loginFormToCheck) != -1)) || ((sessionTimeOut != null) && (line.indexOf(sessionTimeOut) != -1)) || ((loginCheck != null) && (line.indexOf(loginCheck) != -1)))
/*      */         {
/*  658 */           String newUrl = protocol + "://" + host + ":" + port + "/jsp/Login.do?userName=" + userName + "&password=" + password + "&clienttype=html";
/*  659 */           String requestUrl = null;
/*  660 */           if (prodName.equals("OpStor"))
/*      */           {
/*  662 */             requestUrl = protocol + "://" + host + ":" + port + "/jsp/Login.do?ru=" + URLEncoder.encode(url);
/*      */           }
/*      */           else
/*      */           {
/*  666 */             requestUrl = protocol + "://" + host + ":" + port + "/jsp/Login.do";
/*      */           }
/*  668 */           URI uri = new URI(getHostPort(requestUrl));
/*  669 */           HTTPConnection con1 = new HTTPConnection(uri);
/*  670 */           con1.setContext(tempString);
/*  671 */           String domainName = userName.contains("\\") ? userName.substring(0, userName.indexOf("\\")) : "";
/*  672 */           String user = userName.contains("\\") ? userName.substring(userName.indexOf("\\") + 1) : userName;
/*  673 */           NVPair nvpair2 = new NVPair("userName", user);
/*  674 */           NVPair nvpair3 = new NVPair("password", password);
/*  675 */           NVPair nvpair4 = new NVPair("clienttype", "html");
/*  676 */           NVPair[] formelements = new NVPair[4];
/*  677 */           formelements[0] = nvpair2;
/*  678 */           formelements[1] = nvpair3;
/*  679 */           formelements[2] = nvpair4;
/*  680 */           formelements[3] = new NVPair("domainName", domainName);
/*  681 */           NVPair nvpair1 = new NVPair("Pragma", "no-cache");
/*  682 */           NVPair nvpairLocale = new NVPair("Accept-Language", "zh-CN,zh;q=0.8");
/*  683 */           NVPair[] headers = { ("OpStor".equals(prodName)) && (System.getProperty("locale").equalsIgnoreCase("zh_CN")) ? new NVPair[] { nvpair1, nvpairLocale } : nvpair1 };
/*  684 */           HTTPClient.HTTPResponse rsp = con1.Post(getURI(requestUrl), formelements, headers);
/*  685 */           BufferedReader br1 = new BufferedReader(new InputStreamReader(rsp.getInputStream()));
/*      */           
/*  687 */           String line1 = null;
/*  688 */           ArrayList list1 = new ArrayList();
/*  689 */           while ((line1 = br1.readLine()) != null)
/*      */           {
/*  691 */             list1.add(line1);
/*  692 */             if (line1.indexOf("Invalid username and/or password") != -1)
/*      */             {
/*  694 */               writeErrorMessage(prodName, hresponse, "User Credential Wrong");
/*  695 */               return false;
/*      */             }
/*      */           }
/*  698 */           br1.close();
/*  699 */           clientOutput(protocol, host, port, hresponse, hreq, list1, prodName, masId, null);
/*  700 */           return true;
/*      */         }
/*      */       }
/*  703 */       br.close();
/*      */       
/*  705 */       clientOutput(protocol, host, port, hresponse, hreq, list, prodName, masId, null);
/*  706 */       return true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  710 */       e.printStackTrace();
/*  711 */       if ((e.getMessage() != null) && (e.getMessage().indexOf("Connection refused") != -1))
/*      */       {
/*      */ 
/*  714 */         writeErrorMessage(prodName, hresponse, "Server Down");
/*      */ 
/*      */ 
/*      */       }
/*  718 */       else if ((list != null) && (list.size() > 0))
/*      */       {
/*      */         try
/*      */         {
/*  722 */           clientOutput(protocol, host, port, hresponse, hreq, list, prodName, masId, null);
/*      */         }
/*      */         catch (Exception e1)
/*      */         {
/*  726 */           e1.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/*  731 */         writeErrorMessage(prodName, hresponse, e.getMessage());
/*      */       }
/*      */     }
/*  734 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void writeErrorMessage(String prodName, HttpServletResponse hresponse, String errorMessage)
/*      */   {
/*      */     try
/*      */     {
/*  742 */       PrintWriter out = hresponse.getWriter();
/*  743 */       out.println("<table width=\"99%\" border=\"0\" cellspacing=\"0\" class=\"messagebox\" cellpadding=\"0\"><tr>");
/*  744 */       out.println("<td height=\"46\" valign=\"top\" class=\"tdindent\">");
/*  745 */       out.println("<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\"><tr>");
/*  746 */       out.println("<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>");
/*  747 */       out.println("<td width=\"95%\" class=\"message\">");
/*  748 */       if (errorMessage.equals("Server Down"))
/*      */       {
/*  750 */         out.println("Could not connect to " + prodName + ". Kindly check whether it is running.");
/*      */       }
/*  752 */       else if (errorMessage.equals("User Credential Wrong"))
/*      */       {
/*  754 */         out.println("UserName or password might have changed in " + prodName + ". Kindly update.<a class=\"bodytext\" href=\"/extDeviceAction.do?method=editExtDevices&prodName=" + prodName + "\">Edit</a>");
/*      */       }
/*  756 */       else if (errorMessage != null)
/*      */       {
/*  758 */         out.println(errorMessage);
/*      */       }
/*      */       else
/*      */       {
/*  762 */         out.println("Some problem occured while connecting to the remote Product.");
/*      */       }
/*  764 */       out.println("</td></tr></table>");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  768 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void clientOutput(String protocol, String host, String port, HttpServletResponse hresponse, HttpServletRequest hreq, ArrayList list, String prodName, String masId, String contextPath) throws Exception {
/*  773 */     hresponse.setContentType("text/html; charset=UTF-8");
/*  774 */     PrintWriter out = hresponse.getWriter();
/*      */     
/*  776 */     String httpStr = protocol + "://" + host + ":" + port;
/*      */     
/*  778 */     String line = null;
/*  779 */     String stringToReplace = null;
/*  780 */     String replaceStr = null;
/*  781 */     String chartStr = null;
/*  782 */     String replaceChartStr = null;
/*  783 */     String cssStr = null;
/*  784 */     String replaceCssStr = null;
/*  785 */     String dotDo = null;
/*  786 */     String replaceDotDo = null;
/*  787 */     String opStorCharStr = null;
/*  788 */     String replaceOpStorCharStr = null;
/*  789 */     String inventoryImage = null;
/*  790 */     String replaceInventoryImage = null;
/*  791 */     String reportsImage = null;
/*  792 */     String replaceReportsImage = null;
/*  793 */     String faultImage = null;
/*  794 */     String replaceFaultImage = null;
/*  795 */     String adminImage = null;
/*  796 */     String replaceAdminImage = null;
/*  797 */     String commonImage = null;
/*  798 */     String replaceCommonImage = null;
/*  799 */     String commonJS = null;
/*  800 */     String replaceCommonJS = null;
/*  801 */     String devicesJS = null;
/*  802 */     String replaceDevicesJS = null;
/*  803 */     String mapJS = null;
/*  804 */     String replaceMapJS = null;
/*  805 */     String mapImage = null;
/*  806 */     String replaceMapImage = null;
/*  807 */     String mapFlash = null;
/*  808 */     String replaceMapFlash = null;
/*  809 */     String it360JS = null;
/*  810 */     String replaceIt360JS = null;
/*      */     
/*  812 */     String extprodFiles = "";
/*  813 */     if (isIT360)
/*      */     {
/*  815 */       extprodFiles = "extprodfiles/" + masId + "/";
/*      */     }
/*      */     
/*  818 */     if (prodName.equals("OpManager"))
/*      */     {
/*  820 */       stringToReplace = "/webclient/devices/images/";
/*  821 */       replaceStr = httpStr + stringToReplace;
/*  822 */       chartStr = "/servlet/OpManagerDisplayChart?filename=opmanager-";
/*  823 */       replaceChartStr = httpStr + "/temp/opmanager-";
/*  824 */       cssStr = "/webclient/common/css/";
/*  825 */       replaceCssStr = httpStr + cssStr;
/*  826 */       commonImage = "/webclient/common/images/";
/*  827 */       replaceCommonImage = httpStr + commonImage;
/*  828 */       dotDo = ".do?";
/*  829 */       replaceDotDo = ".do?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*  830 */       commonJS = "/webclient/common/js/";
/*  831 */       replaceCommonJS = httpStr + commonJS;
/*  832 */       devicesJS = "/webclient/devices/js/";
/*  833 */       replaceDevicesJS = httpStr + devicesJS;
/*  834 */       mapJS = "/webclient/map/js/";
/*  835 */       replaceMapJS = httpStr + mapJS;
/*  836 */       mapFlash = "/webclient/map/flash/";
/*  837 */       replaceMapFlash = httpStr + mapFlash;
/*  838 */       mapImage = "/webclient/map/images/";
/*  839 */       replaceMapImage = httpStr + mapImage;
/*  840 */       it360JS = "/webclient/it360/js/";
/*  841 */       replaceIt360JS = httpStr + it360JS;
/*      */     }
/*  843 */     if (prodName.equals("OpStor"))
/*      */     {
/*  845 */       stringToReplace = "/webclient/common/images/";
/*  846 */       replaceStr = httpStr + stringToReplace;
/*  847 */       reportsImage = "/webclient/reports/images/";
/*  848 */       replaceReportsImage = httpStr + reportsImage;
/*  849 */       opStorCharStr = "webclient/temp/jfreechart";
/*  850 */       replaceOpStorCharStr = httpStr + "/" + opStorCharStr;
/*  851 */       cssStr = "/webclient/common/css/";
/*  852 */       replaceCssStr = httpStr + cssStr;
/*  853 */       dotDo = ".do?";
/*  854 */       replaceDotDo = ".do?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*  855 */       inventoryImage = "/webclient/inventory/images/";
/*  856 */       replaceInventoryImage = httpStr + inventoryImage;
/*  857 */       faultImage = "/webclient/fault/images/";
/*  858 */       replaceFaultImage = httpStr + faultImage;
/*  859 */       adminImage = "/webclient/admin/images/";
/*  860 */       replaceAdminImage = httpStr + adminImage;
/*      */     }
/*  862 */     if (prodName.equals("NetflowAnalyzer"))
/*      */     {
/*  864 */       dotDo = ".jsp?";
/*  865 */       replaceDotDo = ".jsp?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*  866 */       chartStr = "/netflow/servlet/DisplayChart?filename=";
/*      */     }
/*      */     
/*  869 */     AMLog.debug("EnterpriseUtil.getUseProxyForMASImage():" + EnterpriseUtil.getUseProxyForMASImage());
/*      */     
/*      */ 
/*  872 */     String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/*  873 */     File prodDir = new File("." + File.separator + findAndReplaceAll(extProductDir, "/", File.separator));
/*  874 */     if (!prodDir.exists())
/*      */     {
/*  876 */       if (isIT360)
/*      */       {
/*  878 */         prodDir.mkdirs();
/*      */       }
/*      */       else
/*      */       {
/*  882 */         prodDir.mkdir();
/*      */       }
/*      */     }
/*      */     
/*  886 */     int k = 0;
/*  887 */     if (list != null)
/*      */     {
/*  889 */       while (k < list.size())
/*      */       {
/*  891 */         line = (String)list.get(k);
/*  892 */         k++;
/*  893 */         if ((isIT360) && ((prodName.equals("OpManager")) || (prodName.equals("OpStor"))) && (EnterpriseUtil.isManagedServer()))
/*      */         {
/*  895 */           out.println(line);
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*  900 */         else if (((prodName.equals("OpManager")) || (prodName.equals("NetflowAnalyzer")) || (prodName.equalsIgnoreCase("OpStor"))) && (EnterpriseUtil.getUseProxyForMASImage()))
/*      */         {
/*  902 */           line = processLineForMatches(line, httpStr, masId, prodName, contextPath);
/*  903 */           if ((!isIT360) && ("OpStor".equals(prodName))) {
/*  904 */             if ((opStorCharStr != null) && (line.indexOf(opStorCharStr) != -1))
/*      */             {
/*  906 */               line = findAndReplaceAll(line, opStorCharStr, replaceOpStorCharStr);
/*      */             }
/*  908 */             if ((inventoryImage != null) && (line.indexOf(inventoryImage) != -1))
/*      */             {
/*  910 */               line = findAndReplaceAll(line, inventoryImage, replaceInventoryImage);
/*      */             }
/*      */           }
/*  913 */           out.println(line);
/*      */         }
/*      */         else {
/*  916 */           if ((!isIT360) || (!prodName.equals("OpManager")))
/*      */           {
/*  918 */             if ((stringToReplace != null) && (line.indexOf(stringToReplace) != -1))
/*      */             {
/*  920 */               line = findAndReplaceAll(line, stringToReplace, replaceStr);
/*      */             }
/*  922 */             if ((chartStr != null) && (line.indexOf(chartStr) != -1))
/*      */             {
/*  924 */               int startIndex = line.indexOf("filename=opmanager-");
/*  925 */               int endIndex = line.indexOf(".png");
/*  926 */               String subString = line.substring(startIndex + 19, endIndex);
/*  927 */               String tempStrToReplace = chartStr + subString + ".png";
/*  928 */               String tempReplaceStr = replaceChartStr + subString + ".png";
/*  929 */               if (prodName.equals("NetflowAnalyzer"))
/*      */               {
/*  931 */                 startIndex = line.indexOf("filename=");
/*  932 */                 subString = line.substring(startIndex + 9, endIndex);
/*      */               }
/*  934 */               line = findAndReplaceAll(line, tempStrToReplace, tempReplaceStr);
/*      */             }
/*  936 */             if ((commonJS != null) && (line.indexOf(commonJS) != -1))
/*      */             {
/*  938 */               line = findAndReplaceAll(line, commonJS, replaceCommonJS);
/*      */             }
/*  940 */             if ((devicesJS != null) && (line.indexOf(devicesJS) != -1))
/*      */             {
/*  942 */               line = findAndReplaceAll(line, devicesJS, replaceDevicesJS);
/*      */             }
/*  944 */             if ((mapJS != null) && (line.indexOf(mapJS) != -1))
/*      */             {
/*  946 */               line = findAndReplaceAll(line, mapJS, replaceMapJS);
/*      */             }
/*  948 */             if ((mapImage != null) && (line.indexOf(mapImage) != -1))
/*      */             {
/*  950 */               line = findAndReplaceAll(line, mapImage, replaceMapImage);
/*      */             }
/*  952 */             if ((mapFlash != null) && (line.indexOf(mapFlash) != -1))
/*      */             {
/*  954 */               line = findAndReplaceAll(line, mapFlash, replaceMapFlash);
/*      */             }
/*  956 */             if ((it360JS != null) && (line.indexOf(it360JS) != -1))
/*      */             {
/*  958 */               line = findAndReplaceAll(line, it360JS, replaceIt360JS);
/*      */             }
/*  960 */             if ((opStorCharStr != null) && (line.indexOf(opStorCharStr) != -1))
/*      */             {
/*  962 */               line = findAndReplaceAll(line, opStorCharStr, replaceOpStorCharStr);
/*      */             }
/*  964 */             if ((inventoryImage != null) && (line.indexOf(inventoryImage) != -1))
/*      */             {
/*  966 */               line = findAndReplaceAll(line, inventoryImage, replaceInventoryImage);
/*      */             }
/*  968 */             if ((cssStr != null) && (line.indexOf(cssStr) != -1))
/*      */             {
/*  970 */               line = findAndReplaceAll(line, cssStr, replaceCssStr);
/*      */             }
/*  972 */             if ((commonImage != null) && (line.indexOf(commonImage) != -1))
/*      */             {
/*  974 */               line = findAndReplaceAll(line, commonImage, replaceCommonImage);
/*      */             }
/*  976 */             if ((reportsImage != null) && (line.indexOf(reportsImage) != -1))
/*      */             {
/*  978 */               line = findAndReplaceAll(line, reportsImage, replaceReportsImage);
/*      */             }
/*  980 */             if ((faultImage != null) && (line.indexOf(faultImage) != -1))
/*      */             {
/*  982 */               line = findAndReplaceAll(line, faultImage, replaceFaultImage);
/*      */             }
/*  984 */             if ((adminImage != null) && (line.indexOf(adminImage) != -1))
/*      */             {
/*  986 */               line = findAndReplaceAll(line, adminImage, replaceAdminImage);
/*      */             }
/*  988 */             if (line.indexOf("openWindow") != -1)
/*      */             {
/*  990 */               line = findAndReplaceAll(line, "openWindow", "window.open");
/*      */             }
/*  992 */             if (line.indexOf(dotDo) != -1)
/*      */             {
/*      */ 
/*  995 */               if (line.indexOf("extProdName") == -1)
/*      */               {
/*  997 */                 line = findAndReplaceAll(line, dotDo, replaceDotDo);
/*      */               }
/*      */               
/*      */ 
/*      */             }
/* 1002 */             else if ((line.indexOf(".do") != -1) || (line.indexOf(".jsp") != -1))
/*      */             {
/* 1004 */               String dummy = line.toLowerCase();
/* 1005 */               StringBuffer des = new StringBuffer("");
/* 1006 */               if ((dummy.indexOf("action=") != -1) && (dummy.indexOf("form") != -1) && (dummy.indexOf("extProdName") == -1))
/*      */               {
/* 1008 */                 des.append("  ");
/* 1009 */                 des.append("<input type='hidden' name='printerFriendly' value='true' />");
/* 1010 */                 des.append("<input type='hidden' name='EXTDEVICEMASID' value='" + masId + "' />");
/* 1011 */                 des.append("<input type='hidden' name='extProdName' value='" + prodName + "' />");
/* 1012 */                 if (prodName.equals("NetflowAnalyzer"))
/*      */                 {
/* 1014 */                   des.append("\n");
/* 1015 */                   des.append("<script>function MM_goToURL(){var i, args=MM_goToURL.arguments; document.MM_returnValue = false;for (i=0; i<(args.length-1); i+=2) eval(\"self.location='\"+args[i+1]+\"'\");}</script>");
/*      */                 }
/*      */               }
/* 1018 */               line = line + des.toString();
/*      */             }
/*      */             
/* 1021 */             if (line.indexOf(".do") != -1)
/*      */             {
/* 1023 */               AMLog.debug("### DotDo Line===>" + line);
/*      */             }
/*      */           }
/* 1026 */           out.println(line);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1032 */     if (isIT360)
/*      */     {
/* 1034 */       String newline = "<script type=\"text/javascript\"> function  callResize() { var height = document.body.scrollHeight; parent.resizeIframe(height); } window.onload =callResize; </script>";
/* 1035 */       out.println(newline);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void writeNetworkDeviceResourceIntoAppmanager(String line, String find, String opmanagerurl, String masId, String prodName)
/*      */   {
/* 1041 */     String tostore = null;
/* 1042 */     boolean toprint = false;
/* 1043 */     boolean isStoreFile = false;
/* 1044 */     boolean islineWithCssFile = false;
/* 1045 */     String toqueryfromOpmanager = null;
/* 1046 */     boolean checkservlet = false;
/* 1047 */     boolean isJSwithDotDO = false;
/* 1048 */     String replaceDotDo = ".do?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/* 1049 */     String replaceDotDo1 = replaceDotDo;
/* 1050 */     String dotDo = ".do?";
/* 1051 */     String dotDo1 = dotDo;
/* 1052 */     String stringToReplace1 = "/webclient/devices/images/";
/* 1053 */     String it360StringToReplace1 = "/opmfiles/devices/images/";
/*      */     
/* 1055 */     String modifiedDotPng = "/temp/\"+response";
/*      */     
/* 1057 */     String stringToReplace = "/webclient/devices/images/";
/*      */     
/* 1059 */     String chartStr = "/servlet/OpManagerDisplayChart?filename=";
/* 1060 */     if ((!isIT360) && (EnterpriseUtil.isAdminServer()))
/*      */     {
/* 1062 */       chartStr = "/servlet/app_cached/";
/*      */     }
/*      */     
/* 1065 */     String cssStr = "/webclient/common/css/";
/* 1066 */     String it360CssStr = "/opmfiles/common/css/";
/* 1067 */     String commonImage = "/webclient/common/images/";
/* 1068 */     String commonJS = "/webclient/common/js/";
/* 1069 */     String it360CommonJS = "/opmfiles/common/js/";
/* 1070 */     String devicesJS = "/webclient/devices/js/";
/* 1071 */     String it360DevicesJS = "/opmfiles/devices/js/";
/* 1072 */     String mapJS = "/webclient/map/js/";
/* 1073 */     String it360MapJS = "/opmfiles/map/js/";
/* 1074 */     String mapImage = "/webclient/map/images/";
/* 1075 */     String it360MapImage = "/opmfiles/map/images/";
/* 1076 */     String mapFlash = "/webclient/map/flash/";
/* 1077 */     String it360MapFlash = "/opmfiles/map/flash/";
/* 1078 */     String opmAdminImages = "/webclient/admin/images/";
/* 1079 */     String replaceopmAdminImages = "/opmfiles/admin/images/";
/* 1080 */     String opmCommonImages = "/webclient/common/images/";
/* 1081 */     String replaceopmCommonImages = "/opmfiles/common/images/";
/* 1082 */     String it360JS = "/webclient/it360/js/";
/* 1083 */     String replaceIt360JS = "/opmfiles/it360/js/";
/* 1084 */     String nfaImage = "../images/";
/* 1085 */     String nbaImage = "/nba/images/";
/* 1086 */     String nfaScript = "../script/";
/* 1087 */     String nbaScript = "/nba/script/";
/* 1088 */     String nfaCss = "../css/";
/* 1089 */     String nfaDoubleDotCss = "../../css/";
/* 1090 */     String nfaDoubleDotImage = "../../images/";
/* 1091 */     String nbaCss = "/nba/css/";
/* 1092 */     String tempImages = "/temp/opmanager-";
/* 1093 */     String it360TempImages = "/opmtempfiles/";
/* 1094 */     String opStorCss = "/webclient/it360/css/";
/* 1095 */     String opStorImages = "/webclient/it360/images/";
/* 1096 */     String opStorInvImages = "/webclient/inventory/images/";
/* 1097 */     String it360OpStorCss = "/webclient/it360/css/";
/* 1098 */     String opStorReportsJS = "/webclient/reports/js/";
/* 1099 */     String opStorReportsImages = "/webclient/reports/images/";
/* 1100 */     String opStorAdminImages = "/webclient/admin/images/";
/* 1101 */     String it360OpStorReportsJS = "/webclient/reports/js/";
/* 1102 */     String opStorTempImages = "webclient/temp/";
/* 1103 */     String opStorReplaceTempImages = "/webclient/temp/";
/* 1104 */     String opStorSessions = "webclient/sessions/";
/* 1105 */     String opStorReplaceSessions = "/webclient/sessions/";
/*      */     try
/*      */     {
/* 1108 */       if (prodName.equals("NetflowAnalyzer"))
/*      */       {
/* 1110 */         stringToReplace = "/netflow/images/";
/* 1111 */         commonJS = "/netflow/script/";
/* 1112 */         cssStr = "/netflow/css/";
/*      */         
/* 1114 */         replaceDotDo = ".jsp?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/* 1115 */         dotDo = ".jsp?";
/* 1116 */         chartStr = "/netflow/servlet/DisplayChart?filename=";
/* 1117 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 1119 */           chartStr = "/servlet/app_cached/";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1124 */         if (line.indexOf(nbaImage) != -1)
/*      */         {
/* 1126 */           stringToReplace = nbaImage;
/*      */         }
/* 1128 */         else if (line.indexOf(nbaScript) != -1)
/*      */         {
/* 1130 */           commonJS = nbaScript;
/*      */         }
/* 1132 */         else if (line.indexOf(nbaCss) != -1)
/*      */         {
/* 1134 */           cssStr = nbaCss;
/*      */         }
/*      */       }
/* 1137 */       else if (prodName.equals("OpStor"))
/*      */       {
/* 1139 */         it360CssStr = "/webclient/common/css/";
/* 1140 */         it360CommonJS = "/webclient/common/js/";
/* 1141 */         stringToReplace = "../images/";
/*      */       }
/* 1143 */       if (line.indexOf(tempImages) != -1)
/*      */       {
/* 1145 */         isStoreFile = true;
/* 1146 */         int startIndexForResource = line.indexOf(tempImages);
/* 1147 */         int endIndexForResource = -1;
/* 1148 */         if (line.indexOf("png") != -1)
/*      */         {
/* 1150 */           endIndexForResource = line.indexOf("png", startIndexForResource + tempImages.length());
/* 1151 */           endIndexForResource += 3;
/*      */         }
/* 1153 */         else if (line.indexOf("jpg") != -1)
/*      */         {
/* 1155 */           endIndexForResource = line.indexOf("jpg", startIndexForResource + tempImages.length());
/* 1156 */           endIndexForResource += 3;
/*      */         }
/* 1158 */         else if (line.indexOf("gif") != -1)
/*      */         {
/* 1160 */           endIndexForResource = line.indexOf("gif", startIndexForResource + tempImages.length());
/* 1161 */           endIndexForResource += 3;
/*      */         }
/* 1163 */         tostore = tempImages + line.substring(startIndexForResource + tempImages.length(), endIndexForResource);
/* 1164 */         toqueryfromOpmanager = tostore;
/* 1165 */         if (isIT360)
/*      */         {
/* 1167 */           toqueryfromOpmanager = it360TempImages + line.substring(startIndexForResource + tempImages.length() - 10, endIndexForResource);
/*      */         }
/*      */       }
/* 1170 */       if (line.indexOf(nfaDoubleDotImage) != -1)
/*      */       {
/* 1172 */         line = line.replace(nfaDoubleDotImage, stringToReplace);
/*      */       }
/* 1174 */       if (line.indexOf(nfaImage) != -1)
/*      */       {
/* 1176 */         line = line.replace(nfaImage, stringToReplace);
/*      */       }
/* 1178 */       if (line.indexOf(nfaScript) != -1)
/*      */       {
/* 1180 */         line = line.replace(nfaScript, commonJS);
/*      */       }
/* 1182 */       if (line.indexOf(nfaDoubleDotCss) != -1)
/*      */       {
/* 1184 */         line = line.replace(nfaDoubleDotCss, cssStr);
/*      */       }
/* 1186 */       if (line.indexOf(nfaCss) != -1)
/*      */       {
/* 1188 */         line = line.replace(nfaCss, cssStr);
/*      */       }
/* 1190 */       if (line.indexOf(opStorTempImages) != -1)
/*      */       {
/* 1192 */         line = line.replace(opStorTempImages, opStorReplaceTempImages);
/*      */       }
/* 1194 */       if (line.indexOf(opStorSessions) != -1)
/*      */       {
/* 1196 */         line = line.replace(opStorSessions, opStorReplaceSessions);
/*      */       }
/* 1198 */       if (((!isIT360) || ((!prodName.equals("OpManager")) && (!prodName.equals("OpStor")))) && (line.indexOf(stringToReplace) != -1))
/*      */       {
/*      */ 
/* 1201 */         int startIndexForResource = line.indexOf(stringToReplace);
/* 1202 */         int endIndexForResource = -1;
/* 1203 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1205 */           endIndexForResource = line.indexOf(".png", startIndexForResource + stringToReplace.length());
/* 1206 */           endIndexForResource += 4;
/*      */         }
/* 1208 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1210 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + stringToReplace.length());
/* 1211 */           endIndexForResource += 4;
/*      */         }
/* 1213 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1215 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + stringToReplace.length());
/* 1216 */           endIndexForResource += 4;
/*      */         }
/* 1218 */         if (endIndexForResource != -1)
/*      */         {
/* 1220 */           isStoreFile = true;
/* 1221 */           tostore = stringToReplace + line.substring(startIndexForResource + stringToReplace.length(), endIndexForResource);
/* 1222 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1225 */       if ((chartStr != null) && (line.indexOf(chartStr) != -1))
/*      */       {
/*      */ 
/* 1228 */         int startIndexForResource = line.indexOf(chartStr);
/* 1229 */         int endIndexForResource = -1;
/* 1230 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1232 */           endIndexForResource = line.indexOf(".png", startIndexForResource + chartStr.length());
/* 1233 */           endIndexForResource += 4;
/*      */         }
/* 1235 */         if (endIndexForResource != -1)
/*      */         {
/* 1237 */           checkservlet = true;
/* 1238 */           isStoreFile = true;
/* 1239 */           tostore = "/servlet/app_cached/" + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/* 1240 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/* 1242 */             toqueryfromOpmanager = tostore;
/* 1243 */             if ((isIT360) && (prodName.equals("OpManager")))
/*      */             {
/* 1245 */               toqueryfromOpmanager = "/opmtempfiles/" + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1250 */             toqueryfromOpmanager = chartStr + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1255 */       if ((modifiedDotPng != null) && (line.indexOf(modifiedDotPng) != -1))
/*      */       {
/*      */ 
/*      */ 
/* 1259 */         int startIndexForResource = line.indexOf(modifiedDotPng);
/* 1260 */         int endIndexForResource = -1;
/* 1261 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1263 */           endIndexForResource = line.indexOf(".png", startIndexForResource + modifiedDotPng.length());
/* 1264 */           endIndexForResource += 4;
/*      */         }
/* 1266 */         if (endIndexForResource != -1)
/*      */         {
/* 1268 */           isStoreFile = true;
/* 1269 */           tostore = line.substring(startIndexForResource + modifiedDotPng.length(), endIndexForResource);
/* 1270 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/* 1272 */             toqueryfromOpmanager = tostore;
/*      */           }
/*      */           else
/*      */           {
/* 1276 */             toqueryfromOpmanager = modifiedDotPng + line.substring(startIndexForResource + modifiedDotPng.length(), endIndexForResource);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1281 */       if (line.indexOf(cssStr) != -1)
/*      */       {
/* 1283 */         int startIndexForResource = line.indexOf(cssStr);
/* 1284 */         int endIndexForResource = -1;
/* 1285 */         if (line.indexOf(".css") != -1)
/*      */         {
/* 1287 */           islineWithCssFile = true;
/* 1288 */           endIndexForResource = line.indexOf(".css", startIndexForResource + cssStr.length());
/* 1289 */           endIndexForResource += 4;
/*      */         }
/* 1291 */         if (endIndexForResource != -1)
/*      */         {
/* 1293 */           isStoreFile = true;
/* 1294 */           tostore = cssStr + line.substring(startIndexForResource + cssStr.length(), endIndexForResource);
/* 1295 */           toqueryfromOpmanager = tostore;
/* 1296 */           if ((isIT360) && ((prodName.equals("OpManager")) || (prodName.equals("OpStor"))))
/*      */           {
/* 1298 */             toqueryfromOpmanager = it360CssStr + line.substring(startIndexForResource + cssStr.length(), endIndexForResource);
/*      */           }
/*      */         }
/*      */       }
/* 1302 */       if (line.indexOf(opStorCss) != -1)
/*      */       {
/* 1304 */         int startIndexForResource = line.indexOf(opStorCss);
/* 1305 */         int endIndexForResource = -1;
/* 1306 */         if (line.indexOf(".css") != -1)
/*      */         {
/* 1308 */           islineWithCssFile = true;
/* 1309 */           endIndexForResource = line.indexOf(".css", startIndexForResource + opStorCss.length());
/* 1310 */           endIndexForResource += 4;
/*      */         }
/* 1312 */         if (endIndexForResource != -1)
/*      */         {
/* 1314 */           isStoreFile = true;
/* 1315 */           tostore = opStorCss + line.substring(startIndexForResource + opStorCss.length(), endIndexForResource);
/* 1316 */           toqueryfromOpmanager = tostore;
/* 1317 */           if ((com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */           {
/* 1319 */             toqueryfromOpmanager = it360OpStorCss + line.substring(startIndexForResource + opStorCss.length(), endIndexForResource);
/*      */           }
/*      */         }
/*      */       }
/* 1323 */       if ((!isIT360) && (line.indexOf(commonImage) != -1))
/*      */       {
/* 1325 */         int startIndexForResource = line.indexOf(commonImage);
/* 1326 */         int endIndexForResource = -1;
/* 1327 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1329 */           endIndexForResource = line.indexOf(".png", startIndexForResource + commonImage.length());
/* 1330 */           endIndexForResource += 4;
/*      */         }
/* 1332 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1334 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + commonImage.length());
/* 1335 */           endIndexForResource += 4;
/*      */         }
/* 1337 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1339 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + commonImage.length());
/* 1340 */           endIndexForResource += 4;
/*      */         }
/* 1342 */         if (endIndexForResource != -1)
/*      */         {
/* 1344 */           isStoreFile = true;
/* 1345 */           tostore = commonImage + line.substring(startIndexForResource + commonImage.length(), endIndexForResource);
/* 1346 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1349 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(commonImage) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1351 */         int startIndexForResource = line.indexOf(commonImage);
/* 1352 */         int endIndexForResource = -1;
/* 1353 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1355 */           endIndexForResource = line.indexOf(".png", startIndexForResource + commonImage.length());
/* 1356 */           endIndexForResource += 4;
/*      */         }
/* 1358 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1360 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + commonImage.length());
/* 1361 */           endIndexForResource += 4;
/*      */         }
/* 1363 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1365 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + commonImage.length());
/* 1366 */           endIndexForResource += 4;
/*      */         }
/* 1368 */         if (endIndexForResource != -1)
/*      */         {
/* 1370 */           isStoreFile = true;
/* 1371 */           tostore = commonImage + line.substring(startIndexForResource + commonImage.length(), endIndexForResource);
/* 1372 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1375 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorReplaceSessions) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1377 */         int startIndexForResource = line.indexOf(opStorReplaceSessions);
/* 1378 */         int endIndexForResource = -1;
/* 1379 */         if (line.indexOf(".csv") != -1)
/*      */         {
/* 1381 */           endIndexForResource = line.indexOf(".csv", startIndexForResource + opStorReplaceSessions.length());
/* 1382 */           endIndexForResource += 4;
/*      */         }
/* 1384 */         else if (line.indexOf(".pdf") != -1)
/*      */         {
/* 1386 */           endIndexForResource = line.indexOf(".pdf", startIndexForResource + opStorReplaceSessions.length());
/* 1387 */           endIndexForResource += 4;
/*      */         }
/* 1389 */         else if (line.indexOf(".xls") != -1)
/*      */         {
/* 1391 */           endIndexForResource = line.indexOf(".xls", startIndexForResource + opStorReplaceSessions.length());
/* 1392 */           endIndexForResource += 4;
/*      */         }
/* 1394 */         if (endIndexForResource != -1)
/*      */         {
/* 1396 */           isStoreFile = true;
/* 1397 */           tostore = opStorReplaceSessions + line.substring(startIndexForResource + opStorReplaceSessions.length(), endIndexForResource);
/* 1398 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1401 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorInvImages) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1403 */         int startIndexForResource = line.indexOf(opStorInvImages);
/* 1404 */         int endIndexForResource = -1;
/* 1405 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1407 */           endIndexForResource = line.indexOf(".png", startIndexForResource + opStorInvImages.length());
/* 1408 */           endIndexForResource += 4;
/*      */         }
/* 1410 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1412 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + opStorInvImages.length());
/* 1413 */           endIndexForResource += 4;
/*      */         }
/* 1415 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1417 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + opStorInvImages.length());
/* 1418 */           endIndexForResource += 4;
/*      */         }
/* 1420 */         if (endIndexForResource != -1)
/*      */         {
/* 1422 */           isStoreFile = true;
/* 1423 */           tostore = opStorInvImages + line.substring(startIndexForResource + opStorInvImages.length(), endIndexForResource);
/* 1424 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1427 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorAdminImages) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1429 */         int startIndexForResource = line.indexOf(opStorAdminImages);
/* 1430 */         int endIndexForResource = -1;
/* 1431 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1433 */           endIndexForResource = line.indexOf(".png", startIndexForResource + opStorAdminImages.length());
/* 1434 */           endIndexForResource += 4;
/*      */         }
/* 1436 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1438 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + opStorAdminImages.length());
/* 1439 */           endIndexForResource += 4;
/*      */         }
/* 1441 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1443 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + opStorAdminImages.length());
/* 1444 */           endIndexForResource += 4;
/*      */         }
/* 1446 */         if (endIndexForResource != -1)
/*      */         {
/* 1448 */           isStoreFile = true;
/* 1449 */           tostore = opStorAdminImages + line.substring(startIndexForResource + opStorAdminImages.length(), endIndexForResource);
/* 1450 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1453 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorReportsImages) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1455 */         int startIndexForResource = line.indexOf(opStorReportsImages);
/* 1456 */         int endIndexForResource = -1;
/* 1457 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1459 */           endIndexForResource = line.indexOf(".png", startIndexForResource + opStorReportsImages.length());
/* 1460 */           endIndexForResource += 4;
/*      */         }
/* 1462 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1464 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + opStorReportsImages.length());
/* 1465 */           endIndexForResource += 4;
/*      */         }
/* 1467 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1469 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + opStorReportsImages.length());
/* 1470 */           endIndexForResource += 4;
/*      */         }
/* 1472 */         if (endIndexForResource != -1)
/*      */         {
/* 1474 */           isStoreFile = true;
/* 1475 */           tostore = opStorReportsImages + line.substring(startIndexForResource + opStorReportsImages.length(), endIndexForResource);
/* 1476 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1479 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorImages) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1481 */         int startIndexForResource = line.indexOf(opStorImages);
/* 1482 */         int endIndexForResource = -1;
/* 1483 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1485 */           endIndexForResource = line.indexOf(".png", startIndexForResource + opStorImages.length());
/* 1486 */           endIndexForResource += 4;
/*      */         }
/* 1488 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1490 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + opStorImages.length());
/* 1491 */           endIndexForResource += 4;
/*      */         }
/* 1493 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1495 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + opStorImages.length());
/* 1496 */           endIndexForResource += 4;
/*      */         }
/* 1498 */         if (endIndexForResource != -1)
/*      */         {
/* 1500 */           isStoreFile = true;
/* 1501 */           tostore = opStorImages + line.substring(startIndexForResource + opStorImages.length(), endIndexForResource);
/* 1502 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1505 */       if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf(opStorReplaceTempImages) != -1) && (prodName.equals("OpStor")))
/*      */       {
/* 1507 */         int startIndexForResource = line.indexOf(opStorReplaceTempImages);
/* 1508 */         int endIndexForResource = -1;
/* 1509 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1511 */           endIndexForResource = line.indexOf(".png", startIndexForResource + opStorReplaceTempImages.length());
/* 1512 */           endIndexForResource += 4;
/*      */         }
/* 1514 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1516 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + opStorReplaceTempImages.length());
/* 1517 */           endIndexForResource += 4;
/*      */         }
/* 1519 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1521 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + opStorReplaceTempImages.length());
/* 1522 */           endIndexForResource += 4;
/*      */         }
/* 1524 */         if (endIndexForResource != -1)
/*      */         {
/* 1526 */           isStoreFile = true;
/* 1527 */           tostore = opStorReplaceTempImages + line.substring(startIndexForResource + opStorReplaceTempImages.length(), endIndexForResource);
/* 1528 */           toqueryfromOpmanager = tostore;
/*      */         }
/*      */       }
/* 1531 */       if (line.indexOf(commonJS) != -1)
/*      */       {
/*      */ 
/* 1534 */         int startIndexForResource = line.indexOf(commonJS);
/* 1535 */         int endIndexForResource = -1;
/* 1536 */         if (line.indexOf(".js") != -1)
/*      */         {
/* 1538 */           endIndexForResource = line.indexOf(".js", startIndexForResource + commonJS.length());
/* 1539 */           if (endIndexForResource != -1)
/*      */           {
/* 1541 */             isStoreFile = true;
/* 1542 */             endIndexForResource += 3;
/* 1543 */             isJSwithDotDO = true;
/*      */             
/*      */             try
/*      */             {
/* 1547 */               tostore = commonJS + line.substring(startIndexForResource + commonJS.length(), endIndexForResource);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1551 */               e.printStackTrace();
/*      */             }
/* 1553 */             toqueryfromOpmanager = tostore;
/*      */             try
/*      */             {
/* 1556 */               if ((isIT360) && ((prodName.equals("OpManager")) || (prodName.equals("OpStor"))))
/*      */               {
/* 1558 */                 toqueryfromOpmanager = it360CommonJS + line.substring(startIndexForResource + commonJS.length(), endIndexForResource);
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1563 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1568 */       if (line.indexOf(opStorReportsJS) != -1)
/*      */       {
/* 1570 */         int startIndexForResource = line.indexOf(opStorReportsJS);
/* 1571 */         int endIndexForResource = -1;
/* 1572 */         if (line.indexOf(".js") != -1)
/*      */         {
/* 1574 */           endIndexForResource = line.indexOf(".js", startIndexForResource + opStorReportsJS.length());
/* 1575 */           if (endIndexForResource != -1)
/*      */           {
/* 1577 */             isStoreFile = true;
/* 1578 */             endIndexForResource += 3;
/* 1579 */             isJSwithDotDO = true;
/*      */             
/*      */             try
/*      */             {
/* 1583 */               tostore = opStorReportsJS + line.substring(startIndexForResource + opStorReportsJS.length(), endIndexForResource);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1587 */               e.printStackTrace();
/*      */             }
/* 1589 */             toqueryfromOpmanager = tostore;
/*      */             try
/*      */             {
/* 1592 */               if ((isIT360) && ((prodName.equals("OpManager")) || (prodName.equals("OpStor"))))
/*      */               {
/* 1594 */                 toqueryfromOpmanager = it360OpStorReportsJS + line.substring(startIndexForResource + opStorReportsJS.length(), endIndexForResource);
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1599 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 1604 */       if (line.indexOf(devicesJS) != -1)
/*      */       {
/*      */ 
/* 1607 */         int startIndexForResource = line.indexOf(devicesJS);
/* 1608 */         int endIndexForResource = -1;
/* 1609 */         if (line.indexOf(".js") != -1)
/*      */         {
/*      */ 
/* 1612 */           endIndexForResource = line.indexOf(".js", startIndexForResource + devicesJS.length());
/* 1613 */           if (endIndexForResource != -1)
/*      */           {
/* 1615 */             isStoreFile = true;
/* 1616 */             endIndexForResource += 3;
/* 1617 */             isJSwithDotDO = true;
/*      */             
/* 1619 */             tostore = devicesJS + line.substring(startIndexForResource + devicesJS.length(), endIndexForResource);
/* 1620 */             toqueryfromOpmanager = tostore;
/* 1621 */             if (isIT360)
/*      */             {
/* 1623 */               toqueryfromOpmanager = it360DevicesJS + line.substring(startIndexForResource + devicesJS.length(), endIndexForResource);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1629 */       if (line.indexOf(mapJS) != -1)
/*      */       {
/*      */ 
/* 1632 */         int startIndexForResource = line.indexOf(mapJS);
/* 1633 */         int endIndexForResource = -1;
/* 1634 */         if (line.indexOf(".js") != -1)
/*      */         {
/*      */ 
/* 1637 */           endIndexForResource = line.indexOf(".js", startIndexForResource + mapJS.length());
/* 1638 */           if (endIndexForResource != -1)
/*      */           {
/* 1640 */             isStoreFile = true;
/* 1641 */             endIndexForResource += 3;
/* 1642 */             isJSwithDotDO = true;
/*      */             
/* 1644 */             tostore = mapJS + line.substring(startIndexForResource + mapJS.length(), endIndexForResource);
/* 1645 */             toqueryfromOpmanager = tostore;
/* 1646 */             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */             {
/* 1648 */               toqueryfromOpmanager = it360MapJS + line.substring(startIndexForResource + mapJS.length(), endIndexForResource);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1654 */       if (line.indexOf(mapImage) != -1)
/*      */       {
/*      */ 
/* 1657 */         int startIndexForResource = line.indexOf(mapImage);
/* 1658 */         int endIndexForResource = -1;
/* 1659 */         if (line.indexOf(".png") != -1)
/*      */         {
/* 1661 */           endIndexForResource = line.indexOf(".png", startIndexForResource + mapImage.length());
/* 1662 */           endIndexForResource += 4;
/*      */         }
/* 1664 */         else if (line.indexOf(".jpg") != -1)
/*      */         {
/* 1666 */           endIndexForResource = line.indexOf(".jpg", startIndexForResource + mapImage.length());
/* 1667 */           endIndexForResource += 4;
/*      */         }
/* 1669 */         else if (line.indexOf(".gif") != -1)
/*      */         {
/* 1671 */           endIndexForResource = line.indexOf(".gif", startIndexForResource + mapImage.length());
/* 1672 */           endIndexForResource += 4;
/*      */         }
/* 1674 */         if (endIndexForResource != -1)
/*      */         {
/* 1676 */           isStoreFile = true;
/* 1677 */           tostore = mapImage + line.substring(startIndexForResource + mapImage.length(), endIndexForResource);
/* 1678 */           toqueryfromOpmanager = tostore;
/* 1679 */           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */           {
/* 1681 */             toqueryfromOpmanager = it360MapImage + line.substring(startIndexForResource + mapImage.length(), endIndexForResource);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1686 */       if (line.indexOf(mapFlash) != -1)
/*      */       {
/* 1688 */         int startIndexForResource = line.indexOf(mapFlash);
/*      */         
/* 1690 */         int endIndexForResource = -1;
/* 1691 */         if (line.indexOf(".swf") != -1)
/*      */         {
/* 1693 */           endIndexForResource = line.indexOf(".swf", startIndexForResource + mapFlash.length());
/*      */         }
/* 1695 */         else if ((line.indexOf(".swf") == -1) && (line.indexOf("',//") != -1))
/*      */         {
/* 1697 */           endIndexForResource = line.indexOf("',//", startIndexForResource + mapFlash.length());
/*      */         }
/* 1699 */         if (endIndexForResource != -1)
/*      */         {
/* 1701 */           isStoreFile = true;
/* 1702 */           tostore = mapFlash + line.substring(startIndexForResource + mapFlash.length(), endIndexForResource) + ".swf";
/* 1703 */           toqueryfromOpmanager = tostore;
/* 1704 */           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */           {
/* 1706 */             toqueryfromOpmanager = it360MapFlash + line.substring(startIndexForResource + mapFlash.length(), endIndexForResource) + ".swf";
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1711 */       if (line.indexOf(it360JS) != -1)
/*      */       {
/*      */ 
/* 1714 */         int startIndexForResource = line.indexOf(it360JS);
/* 1715 */         int endIndexForResource = -1;
/* 1716 */         if (line.indexOf(".js") != -1)
/*      */         {
/*      */ 
/* 1719 */           endIndexForResource = line.indexOf(".js", startIndexForResource + it360JS.length());
/* 1720 */           if (endIndexForResource != -1)
/*      */           {
/* 1722 */             isStoreFile = true;
/* 1723 */             endIndexForResource += 3;
/* 1724 */             isJSwithDotDO = true;
/*      */             
/* 1726 */             tostore = it360JS + line.substring(startIndexForResource + it360JS.length(), endIndexForResource);
/* 1727 */             toqueryfromOpmanager = tostore;
/* 1728 */             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */             {
/* 1730 */               toqueryfromOpmanager = replaceIt360JS + line.substring(startIndexForResource + it360JS.length(), endIndexForResource);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1736 */       if ((!isStoreFile) || (tostore == null))
/*      */       {
/* 1738 */         AMLog.debug("isStoreFile-------------------->" + line);
/* 1739 */         return;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1744 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1747 */     File f = null;
/* 1748 */     FileOutputStream out = null;
/*      */     
/* 1750 */     com.manageengine.appmanager.comm.HTTPResponse ress = null;
/*      */     
/*      */ 
/* 1753 */     if (tostore != null) {
/*      */       try
/*      */       {
/*      */         long end1;
/* 1757 */         if (tostore != null)
/*      */         {
/* 1759 */           String nmshome = System.getProperty("webnms.rootdir");
/*      */           
/*      */ 
/* 1762 */           String[] directories = tostore.split("/");
/* 1763 */           String extprodFiles = "";
/* 1764 */           if (isIT360)
/*      */           {
/* 1766 */             extprodFiles = "extprodfiles/" + masId + "/";
/*      */           }
/* 1768 */           String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/* 1769 */           String dirStructure = "." + File.separator + findAndReplaceAll(extProductDir, "/", File.separator);
/* 1770 */           for (int i = 1; i < directories.length - 1; i++)
/*      */           {
/* 1772 */             dirStructure = dirStructure + File.separator + directories[i];
/* 1773 */             f = new File(dirStructure);
/* 1774 */             if (!f.exists())
/*      */             {
/* 1776 */               f.mkdir();
/*      */             }
/*      */           }
/* 1779 */           tostore = FormatUtil.findReplace(tostore, "/", File.separator);
/* 1780 */           String url = opmanagerurl + toqueryfromOpmanager;
/* 1781 */           if (((!isIT360) || ((!prodName.equals("OpManager")) && (!prodName.equals("OpStor")))) && (EnterpriseUtil.isAdminServer()))
/*      */           {
/* 1783 */             url = opmanagerurl + "/" + extProductDir + "/" + toqueryfromOpmanager;
/*      */           }
/* 1785 */           f = new File(nmshome + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + tostore);
/* 1786 */           if (!f.exists())
/*      */           {
/* 1788 */             long start1 = System.currentTimeMillis();
/*      */             
/*      */ 
/* 1791 */             Properties formProps = new Properties();
/* 1792 */             formProps.setProperty("probeId", masId);
/*      */             
/* 1794 */             if (prodName.equals("OpStor"))
/*      */             {
/* 1796 */               url = url + "?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&forOpStorCache=true&file=" + toqueryfromOpmanager;
/*      */             }
/* 1798 */             ress = EnterpriseUtil.sendAsynchRequestToProbe(url, "Post", formProps, null, 0);
/* 1799 */             int code = ress.getStatusCode();
/*      */             
/*      */ 
/* 1802 */             if (code >= 300)
/*      */             {
/* 1804 */               AMLog.debug("Resource not found on opmanager:" + url); return;
/*      */             }
/*      */             
/* 1807 */             if (islineWithCssFile)
/*      */             {
/*      */ 
/*      */ 
/* 1811 */               String wholedata = ress.getText();
/*      */               
/*      */ 
/* 1814 */               if (wholedata != null)
/*      */               {
/* 1816 */                 StringBuilder cssdata = new StringBuilder();
/* 1817 */                 String[] linearray = wholedata.split("\n");
/* 1818 */                 for (int i = 0; i < linearray.length; i++)
/*      */                 {
/*      */ 
/* 1821 */                   String lineToBeParsed = linearray[i];
/* 1822 */                   if ((lineToBeParsed.indexOf("../images") != -1) && (prodName.equals("OpStor")) && ((toqueryfromOpmanager.indexOf("/webclient/common/") != -1) || (toqueryfromOpmanager.indexOf("/webclient/admin/") != -1) || (toqueryfromOpmanager.indexOf("/webclient/common/") != -1) || (toqueryfromOpmanager.indexOf("/webclient/inventory/") != -1) || (toqueryfromOpmanager.indexOf("/webclient/it360/") != -1) || (toqueryfromOpmanager.indexOf("/webclient/reports/") != -1)))
/*      */                   {
/*      */ 
/*      */ 
/* 1826 */                     int index = toqueryfromOpmanager.indexOf('/', 11);
/* 1827 */                     lineToBeParsed = findAndReplaceAll(lineToBeParsed, "../images", toqueryfromOpmanager.substring(0, index) + "/images");
/*      */                   }
/*      */                   
/* 1830 */                   if ((lineToBeParsed.indexOf("../images") != -1) && (prodName.equals("NetflowAnalyzer")))
/*      */                   {
/* 1832 */                     if (cssStr.indexOf("/nba/css/") != -1)
/*      */                     {
/* 1834 */                       lineToBeParsed = findAndReplaceAll(lineToBeParsed, "../images/", nbaImage);
/*      */                     }
/* 1836 */                     else if (cssStr.indexOf("/netflow/css/") != -1)
/*      */                     {
/* 1838 */                       lineToBeParsed = findAndReplaceAll(lineToBeParsed, "../images/", "/netflow/images/");
/*      */                     }
/*      */                   }
/* 1841 */                   if ((lineToBeParsed.indexOf(opmCommonImages) != -1) && (prodName.equals("OpManager")))
/*      */                   {
/* 1843 */                     lineToBeParsed = findAndReplaceAll(lineToBeParsed, opmCommonImages, replaceopmCommonImages);
/*      */                   }
/* 1845 */                   if ((lineToBeParsed.indexOf(opmAdminImages) != -1) && (prodName.equals("OpManager")))
/*      */                   {
/* 1847 */                     lineToBeParsed = findAndReplaceAll(lineToBeParsed, opmAdminImages, replaceopmAdminImages);
/*      */                   }
/* 1849 */                   cssdata.append(processLineForMatches(lineToBeParsed, opmanagerurl, masId, prodName, null));
/* 1850 */                   cssdata.append("\n");
/*      */                 }
/* 1852 */                 byte[] cssdatainBytes = cssdata.toString().getBytes();
/* 1853 */                 f.createNewFile();
/*      */                 
/* 1855 */                 out = new FileOutputStream(f);
/* 1856 */                 out.write(cssdatainBytes);
/*      */               }
/*      */               else
/*      */               {
/* 1860 */                 AMLog.debug("[PROXY-REQUEST] Unable to get the css data for the request : " + url);
/*      */               }
/*      */               
/*      */             }
/* 1864 */             else if (isJSwithDotDO)
/*      */             {
/*      */ 
/*      */ 
/* 1868 */               byte[] data = ress.getData();
/*      */               
/* 1870 */               File f1 = null;
/* 1871 */               FileOutputStream out1 = null;
/*      */               
/* 1873 */               com.manageengine.appmanager.comm.HTTPResponse ress1 = null;
/*      */               
/*      */ 
/* 1876 */               if (data != null)
/*      */               {
/* 1878 */                 String wholedata = new String(data);
/* 1879 */                 StringBuilder jsdata = new StringBuilder();
/* 1880 */                 String[] linearray = wholedata.split("\n");
/* 1881 */                 String jsline = null;
/* 1882 */                 String temp1 = "";
/* 1883 */                 String filename = "";
/* 1884 */                 String findStr = "";
/* 1885 */                 String extn = "";
/* 1886 */                 for (int i = 0; i < linearray.length; i++)
/*      */                 {
/* 1888 */                   temp1 = "";
/* 1889 */                   filename = "";
/* 1890 */                   findStr = "";
/* 1891 */                   jsline = linearray[i];
/* 1892 */                   String srcStr = "";
/* 1893 */                   if (jsline.indexOf(stringToReplace1) != -1)
/*      */                   {
/* 1895 */                     jsline = findAndReplaceAll(jsline, stringToReplace1, it360StringToReplace1);
/*      */                   }
/* 1897 */                   if (jsline.indexOf(dotDo) != -1)
/*      */                   {
/* 1899 */                     jsline = findAndReplaceAll(jsline, dotDo, replaceDotDo);
/*      */                   }
/* 1901 */                   if (jsline.indexOf(dotDo1) != -1)
/*      */                   {
/* 1903 */                     jsline = findAndReplaceAll(jsline, dotDo1, replaceDotDo1);
/*      */                   }
/* 1905 */                   if ((jsline.indexOf(opmCommonImages) != -1) && (prodName.equals("OpManager")))
/*      */                   {
/* 1907 */                     jsline = findAndReplaceAll(jsline, opmCommonImages, replaceopmCommonImages);
/*      */                   }
/* 1909 */                   if ((prodName.equals("OpStor")) && (jsline.indexOf(".jsp?") != -1))
/*      */                   {
/* 1911 */                     jsline = findAndReplaceAll(jsline, ".jsp?", ".jsp?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&");
/*      */                   }
/* 1913 */                   if (prodName.equals("NetflowAnalyzer"))
/*      */                   {
/* 1915 */                     if (jsline.indexOf("../images/") != -1)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1922 */                       if (commonJS.indexOf("/nba/script") != -1)
/*      */                       {
/* 1924 */                         jsline = findAndReplaceAll(jsline, "../images/", nbaImage);
/* 1925 */                         findStr = nbaImage;
/*      */                       }
/* 1927 */                       else if (commonJS.indexOf("/netflow/script") != -1)
/*      */                       {
/* 1929 */                         jsline = findAndReplaceAll(jsline, "../images/", "/netflow/images/");
/* 1930 */                         findStr = "/netflow/images/";
/*      */                       }
/*      */                       
/*      */                     }
/* 1934 */                     else if ((jsline.indexOf("/netflow/images/") != -1) && (EnterpriseUtil.isAdminServer()))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/* 1939 */                       findStr = "/netflow/images/";
/*      */                     }
/* 1941 */                     else if (jsline.indexOf(nbaImage) != -1)
/*      */                     {
/* 1943 */                       findStr = nbaImage;
/*      */                     }
/*      */                     
/* 1946 */                     srcStr = jsline;
/* 1947 */                     if (!findStr.equals(""))
/*      */                     {
/* 1949 */                       StringBuffer dess = new StringBuffer();
/* 1950 */                       if (srcStr.indexOf(findStr) != -1)
/*      */                       {
/* 1952 */                         dess.append(srcStr.substring(0, srcStr.indexOf(findStr)));
/* 1953 */                         if (srcStr.indexOf(".png") != -1)
/*      */                         {
/* 1955 */                           extn = ".png";
/*      */                         }
/* 1957 */                         else if (srcStr.indexOf(".gif") != -1)
/*      */                         {
/* 1959 */                           extn = ".gif";
/*      */                         }
/* 1961 */                         else if (srcStr.indexOf(".jpg") != -1)
/*      */                         {
/* 1963 */                           extn = ".jpg";
/*      */                         }
/* 1965 */                         filename = srcStr.substring(srcStr.indexOf(findStr) + findStr.length(), srcStr.indexOf(extn) + 4);
/*      */                       }
/* 1967 */                       if (EnterpriseUtil.isAdminServer())
/*      */                       {
/* 1969 */                         temp1 = "/" + extProductDir + findStr;
/* 1970 */                         dess.append(temp1);
/*      */                       }
/*      */                       else
/*      */                       {
/* 1974 */                         temp1 = findStr;
/* 1975 */                         dess.append(temp1);
/*      */                       }
/* 1977 */                       String url3 = opmanagerurl + temp1 + filename;
/*      */                       
/* 1979 */                       f1 = new File(nmshome + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + findAndReplaceAll(findStr, "/", File.separator) + filename);
/* 1980 */                       File destDir = new File(nmshome + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + findAndReplaceAll(findStr, "/", File.separator));
/* 1981 */                       if (!destDir.exists())
/*      */                       {
/* 1983 */                         destDir.mkdirs();
/*      */                       }
/* 1985 */                       if (!f1.exists())
/*      */                       {
/*      */ 
/*      */ 
/*      */                         try
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/* 1994 */                           ress1 = EnterpriseUtil.sendAsynchRequestToProbe(url3, "Post", formProps, null, 0);
/* 1995 */                           if (ress1.getStatusCode() > 300)
/*      */                           {
/* 1997 */                             AMLog.debug("Resource not found in " + prodName);
/*      */                           }
/* 1999 */                           else if (ress1.getStatusCode() == 200)
/*      */                           {
/*      */ 
/* 2002 */                             byte[] data2 = ress1.getData();
/*      */                             
/*      */ 
/* 2005 */                             if (data2 != null)
/*      */                             {
/* 2007 */                               f1.createNewFile();
/* 2008 */                               out1 = new FileOutputStream(f1);
/* 2009 */                               out1.write(data2);
/*      */                             }
/*      */                             else
/*      */                             {
/* 2013 */                               AMLog.audit("[PROXY-REQUEST] Unable to get the data for the request : " + url3);
/*      */                             }
/*      */                           }
/* 2016 */                           if (ress1 != null)
/*      */                           {
/* 2018 */                             ress1.releaseConnection();
/*      */                           }
/*      */                         }
/*      */                         catch (FileNotFoundException fnf)
/*      */                         {
/* 2023 */                           AMLog.fatal("Could not retrieve image.  file path:" + f1.getPath() + " " + fnf);
/*      */                         }
/*      */                         catch (Exception e)
/*      */                         {
/* 2027 */                           if (ress1 != null) {
/* 2028 */                             ress1.releaseConnection();
/*      */                           }
/*      */                           
/* 2031 */                           AMLog.debug("Exception: " + e + " file:" + f1.getPath());
/*      */                         }
/*      */                         finally
/*      */                         {
/* 2035 */                           if (out1 != null)
/*      */                           {
/* 2037 */                             out1.close();
/*      */                           }
/* 2039 */                           if (ress1 != null) {
/* 2040 */                             ress1.releaseConnection();
/*      */                           }
/*      */                         }
/*      */                       }
/* 2044 */                       srcStr = srcStr.substring(srcStr.indexOf(findStr) + find.length());
/* 2045 */                       dess.append(srcStr);
/* 2046 */                       jsline = dess.toString();
/*      */                     }
/*      */                   }
/*      */                   
/* 2050 */                   jsdata.append(jsline);
/* 2051 */                   jsdata.append("\n");
/*      */                 }
/* 2053 */                 byte[] cssdatainBytes = jsdata.toString().getBytes();
/* 2054 */                 f.createNewFile();
/*      */                 
/* 2056 */                 out = new FileOutputStream(f);
/* 2057 */                 out.write(cssdatainBytes);
/*      */               }
/*      */               else
/*      */               {
/* 2061 */                 AMLog.debug("[PROXY-REQUEST] Unable to get the js data for the request : " + url);
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 2066 */               long start = System.currentTimeMillis();
/*      */               
/*      */ 
/* 2069 */               byte[] data = ress.getData();
/*      */               
/*      */ 
/* 2072 */               if (data != null)
/*      */               {
/* 2074 */                 long end = System.currentTimeMillis();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2080 */                 f.createNewFile();
/* 2081 */                 out = new FileOutputStream(f);
/* 2082 */                 out.write(data);
/*      */               }
/*      */               else
/*      */               {
/* 2086 */                 AMLog.debug("[PROXY-REQUEST] Unable to get the data for the request : " + url);
/*      */               }
/*      */             }
/*      */             
/* 2090 */             ress.releaseConnection();
/*      */             
/* 2092 */             end1 = System.currentTimeMillis();
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2100 */         if (ress != null) {
/* 2101 */           ress.releaseConnection();
/*      */         }
/*      */         
/*      */ 
/* 2105 */         e.printStackTrace();
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/*      */ 
/* 2111 */         if (ress != null) {
/* 2112 */           ress.releaseConnection();
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 2118 */           if (out != null)
/*      */           {
/* 2120 */             out.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 2125 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String processLineForMatches(String line, String opmanurl, String masId, String prodName, String contextPath)
/*      */   {
/* 2133 */     String stringToReplace = "/webclient/devices/images/";
/* 2134 */     String it360StringToReplace = "/opmfiles/devices/images/";
/*      */     
/* 2136 */     String chartStr = "/servlet/OpManagerDisplayChart?filename=";
/* 2137 */     if ((!isIT360) && (EnterpriseUtil.isAdminServer()))
/*      */     {
/* 2139 */       chartStr = "/servlet/app_cached/";
/*      */     }
/* 2141 */     String cssStr = "/webclient/common/css/";
/*      */     
/* 2143 */     String commonImage = "webclient/common/images/";
/* 2144 */     String replaceCommonImage = "opmfiles/common/images/";
/*      */     
/*      */ 
/* 2147 */     String opStorCss = "/webclient/it360/css/";
/* 2148 */     String opStorReportsJS = "/webclient/reports/js/";
/* 2149 */     String opStorImages = "/webclient/it360/images/";
/* 2150 */     String opStorInvImages = "/webclient/inventory/images/";
/* 2151 */     String opStorReportsImages = "/webclient/reports/images/";
/* 2152 */     String opStorAdminImages = "/webclient/admin/images/";
/* 2153 */     String opStorTempImages = "webclient/temp/";
/* 2154 */     String opStorSessions = "webclient/sessions/";
/*      */     
/* 2156 */     String tempImages = "/temp/opmanager-";
/*      */     
/* 2158 */     String dotDo = ".do?";
/* 2159 */     String replaceDotDo = ".do?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*      */     
/*      */ 
/*      */ 
/* 2163 */     String dotPng = "/temp/\"+response+\"?realTimeGraph=true";
/* 2164 */     String replaceDotPng = "/temp/\"+response+\"?realTimeGraph=true&printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*      */     
/* 2166 */     String modifiedDotPng = "/temp/\"+response";
/* 2167 */     String replacemodifiedDotPng = "/temp/\"+response+\"?printerFriendly=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&\"";
/*      */     
/* 2169 */     String commonJS = "/webclient/common/js/";
/* 2170 */     String devicesJS = "/webclient/devices/js/";
/* 2171 */     String mapJS = "/webclient/map/js/";
/* 2172 */     String mapFlash = "/webclient/map/flash/";
/* 2173 */     String mapImage = "/webclient/map/images/";
/* 2174 */     String it360JS = "/webclient/it360/js/";
/* 2175 */     String nfaImage = "../images/";
/* 2176 */     String nbaImage = "/nba/images/";
/* 2177 */     String nbaScript = "/nba/script/";
/* 2178 */     String nbaCss = "/nba/css/";
/* 2179 */     String nfaScript = "../script/";
/* 2180 */     String nfaCss = "../css/";
/* 2181 */     String nfaDoubleDotCss = "../../css/";
/* 2182 */     String nfaDoubleDotImage = "../../images/";
/*      */     
/* 2184 */     String dotJsp = ".jsp?";
/* 2185 */     String replaceDotJsp = ".jsp?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*      */     
/*      */ 
/* 2188 */     String wanHelpHtml = "WANMonitor_help.html";
/* 2189 */     String replaceWanHelpHtml = "WANMonitor_help.html?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*      */     
/* 2191 */     String voipHelpHtml = "VoIPMonitor_help.html";
/* 2192 */     String replacevoipHelpHtml = "VoIPMonitor_help.html?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/*      */     
/*      */ 
/* 2195 */     String nfaServlet = "Servlet?";
/* 2196 */     String nfaReplaceServlet = "Servlet?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName + "&";
/* 2197 */     String opmAdminImage = "/webclient/admin/images";
/* 2198 */     String replaceopmAdminImage = "/opmfiles/admin/images";
/* 2199 */     String opmCommonImage = "/webclient/common/images";
/* 2200 */     String replaceopmCommonImage = "/opmfiles/common/images";
/* 2201 */     String opmFaultImages = "/webclient/fault/images/";
/* 2202 */     String replaceopmFaultImages = "/opmfiles/fault/images/";
/* 2203 */     String nfaDotJsp1 = ".jsp\"";
/* 2204 */     String nfaDotJsp2 = ".jsp'";
/* 2205 */     String nfaDotDo1 = ".do\"";
/* 2206 */     String nfaDotDo2 = ".do'";
/* 2207 */     String idCol = "id='col1'";
/* 2208 */     String replaceIdCol = "id='col1' class='gridColumn'";
/* 2209 */     String emberUrl = "/apiclient";
/* 2210 */     String replaceEmberUrl = opmanurl + "/apiclient";
/* 2211 */     if (prodName.equals("NetflowAnalyzer"))
/*      */     {
/* 2213 */       if ((contextPath != null) && (!contextPath.equals("")))
/*      */       {
/*      */ 
/* 2216 */         stringToReplace = contextPath + "images/";
/* 2217 */         commonJS = contextPath + "script/";
/* 2218 */         cssStr = contextPath + "css/";
/*      */       }
/*      */       else
/*      */       {
/* 2222 */         stringToReplace = "/netflow/images/";
/* 2223 */         commonJS = "/netflow/script/";
/* 2224 */         cssStr = "/netflow/css/";
/*      */       }
/* 2226 */       if ((line.indexOf(".jsp") != -1) || (line.indexOf(".do") != -1))
/*      */       {
/* 2228 */         line = replaceString(line, contextPath);
/*      */       }
/* 2230 */       chartStr = "/netflow/servlet/DisplayChart?filename=";
/* 2231 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/* 2233 */         chartStr = "/servlet/app_cached/";
/*      */       }
/* 2235 */       if (line.indexOf(nfaServlet) != -1)
/*      */       {
/* 2237 */         if (line.indexOf(nfaReplaceServlet) == -1)
/*      */         {
/* 2239 */           line = findAndReplaceAll(line, nfaServlet, nfaReplaceServlet);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2245 */       if (line.indexOf(nbaImage) != -1)
/*      */       {
/* 2247 */         line = findAndCopyAll(line, nbaImage, opmanurl, masId, prodName);
/*      */       }
/* 2249 */       else if (line.indexOf(nbaScript) != -1)
/*      */       {
/* 2251 */         line = findAndCopyAll(line, nbaScript, opmanurl, masId, prodName);
/*      */       }
/* 2253 */       else if (line.indexOf(nbaCss) != -1)
/*      */       {
/* 2255 */         line = findAndCopyAll(line, nbaCss, opmanurl, masId, prodName);
/*      */       }
/*      */     }
/*      */     
/* 2259 */     if ((prodName != null) && (prodName.equals("OpStor")))
/*      */     {
/* 2261 */       commonImage = "/webclient/common/images/";
/* 2262 */       stringToReplace = "../images/";
/* 2263 */       it360StringToReplace = "../images/";
/*      */     }
/* 2265 */     if ((prodName != null) && (prodName.equals("OpManager")) && (line.indexOf(emberUrl) != -1)) {
/* 2266 */       line = findAndReplaceAll(line, emberUrl, replaceEmberUrl);
/*      */     }
/* 2268 */     if (line.indexOf(nfaDoubleDotImage) != -1)
/*      */     {
/* 2270 */       line = line.replace(nfaDoubleDotImage, stringToReplace);
/*      */     }
/* 2272 */     if (line.indexOf(nfaImage) != -1)
/*      */     {
/* 2274 */       line = line.replace(nfaImage, stringToReplace);
/*      */     }
/* 2276 */     if (line.indexOf(nfaScript) != -1)
/*      */     {
/* 2278 */       line = line.replace(nfaScript, commonJS);
/*      */     }
/* 2280 */     if (line.indexOf(nfaDoubleDotCss) != -1)
/*      */     {
/* 2282 */       line = line.replace(nfaDoubleDotCss, cssStr);
/*      */     }
/* 2284 */     if (line.indexOf(nfaCss) != -1)
/*      */     {
/* 2286 */       line = line.replace(nfaCss, cssStr);
/*      */     }
/* 2288 */     if (line.indexOf(nfaDotJsp1) != -1)
/*      */     {
/* 2290 */       line = line.replace(nfaDotJsp1, dotJsp + "\"");
/*      */     }
/* 2292 */     if (line.indexOf(nfaDotJsp2) != -1)
/*      */     {
/* 2294 */       line = line.replace(nfaDotJsp2, dotJsp + "'");
/*      */     }
/* 2296 */     if (line.indexOf(nfaDotDo1) != -1)
/*      */     {
/* 2298 */       line = line.replace(nfaDotDo1, dotDo + "\"");
/*      */     }
/* 2300 */     if (line.indexOf(nfaDotDo2) != -1)
/*      */     {
/* 2302 */       line = line.replace(nfaDotDo2, dotDo + "'");
/*      */     }
/* 2304 */     if ((stringToReplace != null) && (line.indexOf(stringToReplace) != -1))
/*      */     {
/* 2306 */       if ((isIT360) && (prodName.equals("OpManager")))
/*      */       {
/* 2308 */         line = findAndReplaceAll(line, stringToReplace, it360StringToReplace);
/*      */       }
/* 2310 */       else if (prodName.equals("OpStor"))
/*      */       {
/* 2312 */         line = findAndCopyAll(line, stringToReplace, opmanurl, masId, prodName);
/*      */       }
/*      */       else
/*      */       {
/* 2316 */         line = findAndCopyAll(line, stringToReplace, opmanurl, masId, prodName);
/*      */       }
/*      */     }
/* 2319 */     if ((chartStr != null) && (line.indexOf(chartStr) != -1))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2326 */       line = findAndCopyAll(line, chartStr, opmanurl, masId, prodName);
/*      */     }
/* 2328 */     if ((cssStr != null) && (line.indexOf(cssStr) != -1))
/*      */     {
/* 2330 */       line = findAndCopyAll(line, cssStr, opmanurl, masId, prodName);
/*      */     }
/* 2332 */     if ((opStorCss != null) && (line.indexOf(opStorCss) != -1))
/*      */     {
/* 2334 */       line = findAndCopyAll(line, opStorCss, opmanurl, masId, prodName);
/*      */     }
/* 2336 */     if ((opStorSessions != null) && (line.indexOf(opStorSessions) != -1))
/*      */     {
/* 2338 */       line = findAndCopyAll(line, opStorSessions, opmanurl, masId, prodName);
/*      */     }
/* 2340 */     if ((opStorReportsJS != null) && (line.indexOf(opStorReportsJS) != -1))
/*      */     {
/* 2342 */       line = findAndCopyAll(line, opStorReportsJS, opmanurl, masId, prodName);
/*      */     }
/* 2344 */     if ((tempImages != null) && (line.indexOf(tempImages) != -1))
/*      */     {
/* 2346 */       line = findAndCopyAll(line, tempImages, opmanurl, masId, prodName);
/*      */     }
/* 2348 */     if ((commonImage != null) && (line.indexOf(commonImage) != -1))
/*      */     {
/* 2350 */       if (isIT360)
/*      */       {
/* 2352 */         if ((prodName != null) && (prodName.equals("OpStor")))
/*      */         {
/* 2354 */           line = findAndCopyAll(line, commonImage, opmanurl, masId, prodName);
/*      */         }
/*      */         else
/*      */         {
/* 2358 */           line = findAndReplaceAll(line, commonImage, replaceCommonImage);
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 2363 */         line = findAndCopyAll(line, commonImage, opmanurl, masId, prodName);
/*      */       }
/*      */     }
/* 2366 */     if ((opStorInvImages != null) && (line.indexOf(opStorInvImages) != -1) && (com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */     {
/* 2368 */       line = findAndCopyAll(line, opStorInvImages, opmanurl, masId, prodName);
/*      */     }
/* 2370 */     if ((opStorImages != null) && (line.indexOf(opStorImages) != -1) && (com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */     {
/* 2372 */       line = findAndCopyAll(line, opStorImages, opmanurl, masId, prodName);
/*      */     }
/* 2374 */     if ((opStorTempImages != null) && (line.indexOf(opStorTempImages) != -1) && (com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */     {
/* 2376 */       line = findAndCopyAll(line, opStorTempImages, opmanurl, masId, prodName);
/*      */     }
/* 2378 */     if ((opStorAdminImages != null) && (line.indexOf(opStorAdminImages) != -1) && (com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */     {
/* 2380 */       line = findAndCopyAll(line, opStorAdminImages, opmanurl, masId, prodName);
/*      */     }
/* 2382 */     if ((opStorReportsImages != null) && (line.indexOf(opStorReportsImages) != -1) && (com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpStor")))
/*      */     {
/* 2384 */       line = findAndCopyAll(line, opStorReportsImages, opmanurl, masId, prodName);
/*      */     }
/* 2386 */     if ((commonJS != null) && (line.indexOf(commonJS) != -1))
/*      */     {
/* 2388 */       line = findAndCopyAll(line, commonJS, opmanurl, masId, prodName);
/*      */     }
/* 2390 */     if ((devicesJS != null) && (line.indexOf(devicesJS) != -1))
/*      */     {
/* 2392 */       line = findAndCopyAll(line, devicesJS, opmanurl, masId, prodName);
/*      */     }
/* 2394 */     if ((mapJS != null) && (line.indexOf(mapJS) != -1))
/*      */     {
/* 2396 */       line = findAndCopyAll(line, mapJS, opmanurl, masId, prodName);
/*      */     }
/* 2398 */     if ((mapImage != null) && (line.indexOf(mapImage) != -1))
/*      */     {
/* 2400 */       line = findAndCopyAll(line, mapImage, opmanurl, masId, prodName);
/*      */     }
/* 2402 */     if ((mapFlash != null) && (line.indexOf(mapFlash) != -1))
/*      */     {
/* 2404 */       line = findAndCopyAll(line, mapFlash, opmanurl, masId, prodName);
/*      */     }
/* 2406 */     if ((it360JS != null) && (line.indexOf(it360JS) != -1))
/*      */     {
/* 2408 */       line = findAndCopyAll(line, it360JS, opmanurl, masId, prodName);
/*      */     }
/*      */     
/* 2411 */     if ((line.indexOf(dotDo) != -1) && (line.indexOf(replaceDotDo) == -1))
/*      */     {
/*      */ 
/* 2414 */       line = findAndReplaceAll(line, dotDo, replaceDotDo);
/*      */     }
/* 2416 */     if ((line.indexOf(URLEncoder.encode(dotDo)) != -1) && (line.indexOf(URLEncoder.encode(replaceDotDo)) == -1))
/*      */     {
/* 2418 */       line = findAndReplaceAll(line, URLEncoder.encode(dotDo), URLEncoder.encode(replaceDotDo));
/*      */     }
/* 2420 */     if ((line.indexOf(dotPng) != -1) && (line.indexOf(replaceDotPng) == -1))
/*      */     {
/* 2422 */       line = findAndReplaceAll(line, dotPng, replaceDotPng);
/*      */     }
/* 2424 */     if ((line.indexOf(modifiedDotPng) != -1) && (line.indexOf(replacemodifiedDotPng) == -1) && (line.indexOf(dotPng) == -1))
/*      */     {
/*      */ 
/* 2427 */       line = findAndReplaceAll(line, modifiedDotPng, replacemodifiedDotPng);
/*      */     }
/*      */     
/* 2430 */     if ((line.indexOf(dotJsp) != -1) && (line.indexOf(replaceDotJsp) == -1) && (!line.contains("netflow/jspui/consoleDash.jsp")))
/*      */     {
/*      */ 
/* 2433 */       line = findAndReplaceAll(line, dotJsp, replaceDotJsp);
/*      */     }
/* 2435 */     if ((com.adventnet.appmanager.util.Constants.isIt360) && (prodName.equals("OpManager")))
/*      */     {
/* 2437 */       if ((line.indexOf(opmAdminImage) != -1) && (line.indexOf(replaceopmAdminImage) == -1))
/*      */       {
/* 2439 */         line = findAndReplaceAll(line, opmAdminImage, replaceopmAdminImage);
/*      */       }
/* 2441 */       if ((line.indexOf(opmCommonImage) != -1) && (line.indexOf(replaceopmCommonImage) == -1))
/*      */       {
/* 2443 */         line = findAndReplaceAll(line, opmCommonImage, replaceopmCommonImage);
/*      */       }
/* 2445 */       if ((line.indexOf(opmFaultImages) != -1) && (line.indexOf(replaceopmFaultImages) == -1))
/*      */       {
/* 2447 */         line = findAndReplaceAll(line, opmFaultImages, replaceopmFaultImages);
/*      */       }
/*      */     }
/*      */     
/* 2451 */     if ((line.indexOf(wanHelpHtml) != -1) && (line.indexOf(replaceWanHelpHtml) == -1))
/*      */     {
/*      */ 
/* 2454 */       line = findAndReplaceAll(line, wanHelpHtml, replaceWanHelpHtml);
/*      */     }
/* 2456 */     else if ((line.indexOf(voipHelpHtml) != -1) && (line.indexOf(replacevoipHelpHtml) == -1))
/*      */     {
/*      */ 
/* 2459 */       line = findAndReplaceAll(line, voipHelpHtml, replacevoipHelpHtml);
/*      */ 
/*      */ 
/*      */     }
/* 2463 */     else if ((line.indexOf(".do") != -1) || (line.indexOf(".jsp") != -1))
/*      */     {
/* 2465 */       String dummy = line.toLowerCase();
/* 2466 */       StringBuffer des = new StringBuffer("");
/* 2467 */       if ((dummy.indexOf("action=") != -1) && (dummy.indexOf("form") != -1) && (dummy.indexOf("extProdName") == -1))
/*      */       {
/* 2469 */         des.append("  ");
/* 2470 */         des.append("<input type='hidden' name='printerFriendly' value='true' />");
/* 2471 */         des.append("<input type='hidden' name='EXTDEVICEMASID' value='" + masId + "' />");
/* 2472 */         des.append("<input type='hidden' name='extProdName' value='" + prodName + "' />");
/*      */         
/* 2474 */         if (prodName.equals("NetflowAnalyzer"))
/*      */         {
/* 2476 */           des.append("\n");
/* 2477 */           des.append("<script>function MM_goToURL(){var i, args=MM_goToURL.arguments; document.MM_returnValue = false;for (i=0; i<(args.length-1); i+=2) eval(\"self.location='\"+args[i+1]+\"'\");}</script>");
/*      */         }
/*      */       }
/*      */       
/* 2481 */       if ((line.contains("netflow/jspui/consoleDash.jsp")) && (EnterpriseUtil.isAdminServer)) {
/* 2482 */         AMLog.info("Replacing 3rd level Traffic iframe link: " + line);
/* 2483 */         String replaceDotJspNFA = ".jsp?PRINTER_FRIENDLY=true&EXTDEVICEMASID=" + masId + "&extProdName=NetflowAnalyzer&";
/* 2484 */         Properties extProps = ExtProdUtil.getExtProductInfoUsingMasId(masId, "NetflowAnalyzer");
/* 2485 */         line = findAndReplaceAll(line, String.valueOf(extProps.get("host")), com.adventnet.appmanager.util.Constants.getAppHostName());
/* 2486 */         line = findAndReplaceAll(line, String.valueOf(extProps.get("port")), AMAutomaticPortChanger.getwebserverport());
/* 2487 */         line = findAndReplaceAll(line, dotJsp, replaceDotJspNFA);
/* 2488 */         AMLog.info("Replaced 3rd level Traffic iframe link: " + line);
/*      */       }
/* 2490 */       line = line + des.toString();
/*      */     }
/*      */     
/* 2493 */     if (line.indexOf(".do") != -1)
/*      */     {
/* 2495 */       AMLog.info("@@@ DotDo===>" + line);
/*      */     }
/* 2497 */     if (line.indexOf(idCol) != -1)
/*      */     {
/*      */ 
/* 2500 */       line = line.replace(idCol, replaceIdCol);
/*      */     }
/* 2502 */     return line;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String findAndCopyAll(String line, String find, String opmanurl, String masId, String prodName)
/*      */   {
/* 2509 */     String chartStr = "/servlet/OpManagerDisplayChart?filename=";
/* 2510 */     String tempImages = "/temp/opmanager-";
/* 2511 */     if (prodName.equals("NetflowAnalyzer"))
/*      */     {
/* 2513 */       chartStr = "/netflow/servlet/DisplayChart?filename=";
/*      */     }
/* 2515 */     StringBuffer des = new StringBuffer("");
/* 2516 */     String str = line;
/* 2517 */     String extprodFiles = "";
/* 2518 */     String temp1 = "";
/* 2519 */     if (isIT360)
/*      */     {
/* 2521 */       extprodFiles = "extprodfiles/" + masId + "/";
/*      */     }
/* 2523 */     String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/* 2524 */     while (str.indexOf(find) != -1)
/*      */     {
/* 2526 */       String slash = "/";
/* 2527 */       des.append(str.substring(0, str.indexOf(find)));
/* 2528 */       if (des.toString().endsWith("/")) {
/* 2529 */         slash = "";
/*      */       }
/* 2531 */       if (chartStr.equals(find))
/*      */       {
/* 2533 */         temp1 = slash + extProductDir + "/servlet/app_cached/";
/* 2534 */         des.append(temp1);
/*      */       }
/* 2536 */       else if (tempImages.equals(find))
/*      */       {
/* 2538 */         temp1 = slash + extProductDir + tempImages;
/* 2539 */         des.append(temp1);
/*      */ 
/*      */ 
/*      */       }
/* 2543 */       else if (((!isIT360) || ((!prodName.equals("OpManager")) && (!prodName.equals("OpStor")))) && (EnterpriseUtil.isAdminServer()))
/*      */       {
/* 2545 */         des.append(find);
/*      */       }
/*      */       else
/*      */       {
/* 2549 */         des.append(slash + extProductDir + "/" + find);
/*      */       }
/*      */       
/* 2552 */       writeNetworkDeviceResourceIntoAppmanager(str, find, opmanurl, masId, prodName);
/* 2553 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/* 2555 */     des.append(str);
/* 2556 */     return des.toString();
/*      */   }
/*      */   
/*      */   public static String findAndReplaceAll(String str, String find, String replace)
/*      */   {
/* 2561 */     StringBuffer des = new StringBuffer("");
/* 2562 */     while (str.indexOf(find) != -1)
/*      */     {
/* 2564 */       des.append(str.substring(0, str.indexOf(find)));
/* 2565 */       des.append(replace);
/* 2566 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/* 2568 */     des.append(str);
/* 2569 */     return des.toString();
/*      */   }
/*      */   
/*      */   private static String getHostPort(String url)
/*      */   {
/* 2574 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/* 2575 */     String http = tokens.nextToken();
/* 2576 */     String hostport = tokens.nextToken();
/* 2577 */     return http + "//" + hostport + "/";
/*      */   }
/*      */   
/*      */   private static String getURI(String url)
/*      */   {
/* 2582 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/* 2583 */     StringBuffer uri = new StringBuffer();
/* 2584 */     int i = 0;
/* 2585 */     for (i = 0; tokens.hasMoreTokens(); i++)
/*      */     {
/* 2587 */       if (i < 2)
/*      */       {
/* 2589 */         tokens.nextToken();
/*      */       }
/*      */       else {
/* 2592 */         uri.append("/");
/* 2593 */         uri.append(tokens.nextToken());
/*      */       } }
/* 2595 */     if (i == 0)
/*      */     {
/* 2597 */       uri = new StringBuffer("/");
/*      */     }
/* 2599 */     return uri.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean proxyRequestForIT360(String protocol, String url, String host, String port, HttpServletResponse hresponse, HttpServletRequest hreq, String userName, String password, String prodName, String masId)
/*      */   {
/* 2610 */     String urlToFetchProbeTicket = ("true".equals(System.getProperty("server.secure")) ? "https://" : "http://") + host + new StringBuilder().append(":").append(port).toString();
/*      */     
/*      */ 
/* 2613 */     PostMethod methodTogetProbeData = null;
/* 2614 */     boolean isSuccess = false;
/* 2615 */     String urlToFetchProbeData = url;
/*      */     
/*      */ 
/* 2618 */     com.manageengine.appmanager.comm.HTTPResponse ress = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 2623 */       if (EnterpriseUtil.isAdminServer)
/*      */       {
/* 2625 */         String consUser = "loginuser";
/* 2626 */         String consPass = "U2FuZ2VldGhN";
/*      */         
/* 2628 */         if ((urlToFetchProbeData.indexOf("/admin/ScheduleReports.do") != -1) || (urlToFetchProbeData.indexOf("/admin/ScheduleMgmt.do") != -1))
/*      */         {
/* 2630 */           consUser = "admin";
/* 2631 */           consPass = "admin";
/*      */         }
/* 2633 */         String ticket = EnterpriseUtil.getTicketFromProbe(host, port, consUser, consPass);
/* 2634 */         String temp = "";
/* 2635 */         int leftOutQueryStringIndex; if (urlToFetchProbeData.indexOf("&ticket") != -1)
/*      */         {
/* 2637 */           int ticketStartIndex = urlToFetchProbeData.indexOf("&ticket");
/* 2638 */           String temp1 = urlToFetchProbeData.substring(ticketStartIndex + 1);
/* 2639 */           if ((temp1 != null) && (!temp1.trim().equals("")))
/*      */           {
/* 2641 */             leftOutQueryStringIndex = temp1.indexOf("&");
/* 2642 */             temp = temp1.substring(leftOutQueryStringIndex);
/*      */           }
/* 2644 */           urlToFetchProbeData = urlToFetchProbeData.substring(0, ticketStartIndex) + temp;
/*      */         }
/* 2646 */         urlToFetchProbeData = urlToFetchProbeData + "&ticket=" + ticket;
/*      */         
/*      */ 
/* 2649 */         if (ticket == null)
/*      */         {
/* 2651 */           PrintWriter out = hresponse.getWriter();
/* 2652 */           String tempUrl = protocol + "://" + host + ":" + port;
/* 2653 */           AMLog.debug("ExtProdIntegFilter : Could not connect probe at " + tempUrl + " May be down or request got timed out");
/* 2654 */           out.println(FormatUtil.getString("it360.probe.server.down.message", new String[] { tempUrl }));
/* 2655 */           return 1;
/*      */         }
/*      */       }
/* 2658 */       AMLog.debug("ExtProdIntegFilter : urlToFetchProbeData : " + urlToFetchProbeData);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2664 */       if (urlToFetchProbeData.indexOf("/temp/opmanager-") != -1)
/*      */       {
/* 2666 */         String nmshome = System.getProperty("webnms.rootdir");
/* 2667 */         String extprodFiles = "";
/* 2668 */         if (isIT360)
/*      */         {
/* 2670 */           extprodFiles = "extprodfiles/" + masId + "/";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2675 */         int startIndex = urlToFetchProbeData.indexOf("temp");
/* 2676 */         int endIndex = urlToFetchProbeData.indexOf("png") + 3;
/* 2677 */         urlToFetchProbeData = urlToFetchProbeData.substring(startIndex - 1, endIndex);
/*      */         
/* 2679 */         String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/* 2680 */         String dirStructure = "." + File.separator + findAndReplaceAll(extProductDir, "/", File.separator);
/* 2681 */         String[] directories = urlToFetchProbeData.split("/");
/* 2682 */         for (int i = 1; i < directories.length - 1; i++)
/*      */         {
/* 2684 */           dirStructure = dirStructure + File.separator + directories[i];
/* 2685 */           File f = new File(dirStructure);
/* 2686 */           if (!f.exists())
/*      */           {
/* 2688 */             f.mkdir();
/*      */           }
/*      */         }
/* 2691 */         urlToFetchProbeData = FormatUtil.findReplace(urlToFetchProbeData, "/", File.separator);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2697 */         String fileName = urlToFetchProbeData.substring(urlToFetchProbeData.indexOf("temp") + "temp".length() + 1);
/* 2698 */         String url1 = protocol + "://" + host + ":" + port + "/opmtempfiles/" + fileName;
/* 2699 */         File f = new File(nmshome + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + "/temp/" + fileName);
/* 2700 */         if (!f.exists())
/*      */         {
/*      */ 
/* 2703 */           ress = EnterpriseUtil.sendAsynchRequestToProbe(url1.trim(), "Get", null, null, 0);
/* 2704 */           int code = ress.getStatusCode();
/*      */           
/*      */ 
/*      */ 
/* 2708 */           if (code >= 300)
/*      */           {
/* 2710 */             AMLog.debug("Resource not found on opmanager: " + url);
/*      */           }
/* 2712 */           f.createNewFile();
/*      */           
/*      */ 
/* 2715 */           byte[] data = ress.getData();
/*      */           
/*      */ 
/* 2718 */           FileOutputStream fo = null;
/*      */           try
/*      */           {
/* 2721 */             fo = new FileOutputStream(f);
/* 2722 */             fo.write(data);
/*      */             
/*      */ 
/*      */ 
/* 2726 */             hresponse.sendRedirect("/extprodfiles/" + masId + "/opmanagerfiles/temp/" + fileName);
/*      */           }
/*      */           catch (Exception ex) {
/* 2729 */             ex.printStackTrace();
/*      */           }
/*      */           finally {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2748 */       ArrayList<String> responseSrtingList = null;
/*      */       
/* 2750 */       String nmshome = System.getProperty("webnms.rootdir");
/* 2751 */       Properties formProps = new Properties();
/* 2752 */       formProps.setProperty("extProdName", prodName);
/* 2753 */       formProps.setProperty("nmshome", nmshome);
/* 2754 */       Properties headerProps = new Properties();
/* 2755 */       if ((prodName.equals("OpStor")) && (urlToFetchProbeData.indexOf("home.do") != -1)) {
/* 2756 */         headerProps.setProperty("ChangePageSizeRequest", "true");
/* 2757 */         headerProps.setProperty("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate, proxy-revalidate");
/* 2758 */         headerProps.setProperty("Pragma", "No-Cache");
/* 2759 */         headerProps.setProperty("If-Modified-Since", new Date().toString());
/* 2760 */         headerProps.setProperty("Expires", "-1");
/* 2761 */         ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchProbeData, "Post", formProps, headerProps, 0);
/*      */       }
/*      */       else
/*      */       {
/* 2765 */         ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchProbeData, "Post", formProps, null, 0);
/*      */       }
/* 2767 */       int respCode1 = ress.getStatusCode();
/*      */       
/*      */ 
/*      */ 
/* 2771 */       if (respCode1 == 200)
/*      */       {
/*      */ 
/*      */ 
/* 2775 */         if ((urlToFetchProbeData.indexOf("viewAs=PDF") != -1) || (urlToFetchProbeData.indexOf("viewAs=XLS") != -1) || (urlToFetchProbeData.indexOf("pdf=true") != -1))
/*      */         {
/* 2777 */           String fileName = "Report.pdf";
/* 2778 */           String contentType = "application/pdf";
/* 2779 */           String extprodFiles = "";
/* 2780 */           if (isIT360)
/*      */           {
/* 2782 */             extprodFiles = "extprodfiles/" + masId + "/";
/*      */           }
/* 2784 */           String extProductDir = (extprodFiles + prodName + "files").toLowerCase();
/* 2785 */           if (urlToFetchProbeData.indexOf("viewAs=XLS") != -1)
/*      */           {
/* 2787 */             fileName = "Report.xls";
/* 2788 */             contentType = "application/vnd.ms-excel";
/*      */           }
/* 2790 */           if (EnterpriseUtil.isManagedServer)
/*      */           {
/*      */ 
/*      */ 
/* 2794 */             byte[] data = ress.getData();
/*      */             
/*      */ 
/* 2797 */             File f = new File("." + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + File.separator + fileName);
/* 2798 */             f.createNewFile();
/* 2799 */             FileOutputStream fo = null;
/*      */             try
/*      */             {
/* 2802 */               fo = new FileOutputStream(f);
/* 2803 */               fo.write(data);
/*      */             }
/*      */             catch (Exception ex) {
/* 2806 */               ex.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/* 2810 */               if (fo != null)
/*      */               {
/* 2812 */                 fo.close();
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2818 */             String url1 = protocol + "://" + host + ":" + port + "/" + extProductDir + "/" + fileName;
/* 2819 */             AMLog.debug("ExtProdIntegFilter : The report url is : " + url1);
/*      */             
/*      */ 
/* 2822 */             com.manageengine.appmanager.comm.HTTPResponse ress1 = null;
/*      */             
/*      */ 
/*      */ 
/* 2826 */             ByteArrayInputStream bis = null;
/* 2827 */             BufferedOutputStream bos = null;
/*      */             
/*      */             try
/*      */             {
/* 2831 */               ress1 = EnterpriseUtil.sendAsynchRequestToProbe(url1.trim(), "Post", null, null, 0);
/* 2832 */               int respCode2 = ress1.getStatusCode();
/* 2833 */               byte[] data = ress1.getData();
/* 2834 */               ress1.releaseConnection();
/*      */               
/* 2836 */               hresponse.reset();
/* 2837 */               hresponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
/* 2838 */               hresponse.setHeader("Pragma", "public");
/* 2839 */               hresponse.setHeader("Content-Type", contentType);
/* 2840 */               hresponse.setDateHeader("Expires", 0L);
/* 2841 */               hresponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);
/* 2842 */               bis = new ByteArrayInputStream(data);
/* 2843 */               bos = new BufferedOutputStream(hresponse.getOutputStream(), 10240);
/* 2844 */               byte[] input = new byte['⠀'];
/*      */               int length;
/* 2846 */               while ((length = bis.read(input)) > 0)
/*      */               {
/* 2848 */                 bos.write(input, 0, length);
/*      */               }
/* 2850 */               bos.flush();
/*      */             }
/*      */             catch (Exception exe)
/*      */             {
/* 2854 */               exe.printStackTrace();
/*      */ 
/*      */             }
/*      */             finally
/*      */             {
/* 2859 */               if (ress1 != null) {
/* 2860 */                 ress1.releaseConnection();
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*      */ 
/* 2877 */           String contextPath = "";
/* 2878 */           if (prodName.equals("NetflowAnalyzer"))
/*      */           {
/* 2880 */             if (urlToFetchProbeData.indexOf("/netflow/") != -1)
/*      */             {
/* 2882 */               contextPath = "/netflow/";
/*      */             }
/* 2884 */             else if (urlToFetchProbeData.indexOf("/nba/") != -1)
/*      */             {
/* 2886 */               contextPath = "/nba/";
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2891 */           BufferedReader br1 = new BufferedReader(new InputStreamReader(ress.getInputStream()));
/*      */           
/*      */ 
/* 2894 */           String line1 = null;
/* 2895 */           responseSrtingList = new ArrayList();
/* 2896 */           while ((line1 = br1.readLine()) != null)
/*      */           {
/* 2898 */             responseSrtingList.add(line1);
/*      */           }
/* 2900 */           clientOutput(protocol, host, port, hresponse, hreq, responseSrtingList, prodName, masId, contextPath);
/*      */         }
/* 2902 */         isSuccess = true;
/*      */       }
/*      */       else {
/* 2905 */         EnterpriseUtil.probeUserTickets.remove(urlToFetchProbeTicket);
/*      */       }
/*      */       
/* 2908 */       AMLog.debug("ExtProdIntegFilter : The return code is : " + respCode1);
/*      */       
/* 2910 */       if (ress != null) {
/* 2911 */         ress.releaseConnection();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 2919 */       EnterpriseUtil.probeUserTickets.remove(urlToFetchProbeTicket);
/* 2920 */       if (ress != null) {
/* 2921 */         ress.releaseConnection();
/*      */       }
/*      */       
/* 2924 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2929 */       if (ress != null) {
/* 2930 */         ress.releaseConnection();
/*      */       }
/*      */       
/*      */ 
/* 2934 */       if (methodTogetProbeData != null)
/*      */       {
/* 2936 */         methodTogetProbeData.releaseConnection();
/*      */       }
/*      */     }
/* 2939 */     return isSuccess;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String replaceString(String line, String contextPath)
/*      */   {
/* 2948 */     String result = matchAndReplaceString(line, "\"", contextPath);
/* 2949 */     result = matchAndReplaceString(result, "'", contextPath);
/* 2950 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String matchAndReplaceString(String line, String pattern, String contextPath)
/*      */   {
/* 2962 */     String result = line;
/* 2963 */     String temp2 = "";
/* 2964 */     String matchStr = ".jsp";
/* 2965 */     boolean matches = false;
/* 2966 */     if ((line.indexOf(".jsp") != -1) || (line.indexOf(".do") != -1))
/*      */     {
/* 2968 */       matches = true;
/* 2969 */       if (line.indexOf(".do") != -1)
/*      */       {
/* 2971 */         matchStr = ".do";
/*      */       }
/*      */     }
/* 2974 */     if (matches)
/*      */     {
/* 2976 */       String tempLine = line.substring(0, line.indexOf(matchStr));
/* 2977 */       if (tempLine.indexOf(pattern) != -1)
/*      */       {
/* 2979 */         temp2 = tempLine.substring(tempLine.lastIndexOf(pattern) + 1);
/*      */       }
/* 2981 */       if (!temp2.trim().equals("")) {
/*      */         boolean donothing;
/* 2983 */         if (temp2.startsWith(contextPath + "jspui"))
/*      */         {
/* 2985 */           donothing = true;
/*      */         }
/* 2987 */         else if (temp2.startsWith("/jspui"))
/*      */         {
/* 2989 */           result = result.replace(temp2, contextPath + temp2);
/*      */         }
/* 2991 */         else if (Pattern.matches("[a-zA-Z0-9]*", temp2))
/*      */         {
/* 2993 */           temp2 = temp2 + matchStr;
/*      */           
/* 2995 */           result = result.replace(temp2, contextPath + "jspui/" + temp2);
/*      */         }
/*      */       }
/*      */     }
/* 2999 */     return result;
/*      */   }
/*      */   
/*      */   public static void getProbePointProductDataAsXML(String protocol, String host, String port, String urlToPost, HttpServletRequest hreq, HttpServletResponse hresponse, String masId, String prodName) throws Exception
/*      */   {
/* 3004 */     String url1 = protocol + "://" + host + ":" + port + urlToPost;
/* 3005 */     String probeTick = "";
/* 3006 */     if ((EnterpriseUtil.isAdminServer()) && (url1.indexOf("ticket") == -1))
/*      */     {
/* 3008 */       String conuser = "loginuser";
/* 3009 */       String conpass = "U2FuZ2VldGhN";
/*      */       try
/*      */       {
/* 3012 */         probeTick = EnterpriseUtil.getTicket(host, port, conuser, conpass, true);
/* 3013 */         url1 = url1 + "&ticket=" + probeTick + "&fromCentral=true";
/*      */         
/* 3015 */         if ((hreq.getParameter("_apmHost") != null) && (!"".equals(hreq.getParameter("_apmHost"))))
/*      */         {
/* 3017 */           url1 = url1 + "&EXTDEVICEMASID=" + masId + "&extProdName=" + prodName;
/*      */         }
/* 3019 */         String siteIds = CustomerManagementAPI.getSiteId(hreq);
/* 3020 */         if (siteIds == null)
/*      */         {
/* 3022 */           String custId = CustomerManagementAPI.getCustomerId(hreq);
/* 3023 */           if (custId == null)
/*      */           {
/* 3025 */             Properties custProp = EnterpriseUtil.getCustProp(hreq);
/* 3026 */             if ((custProp != null) && (custProp.size() > 0))
/*      */             {
/* 3028 */               String custName = custProp.keys().nextElement().toString();
/* 3029 */               custId = custProp.getProperty(custName);
/*      */             }
/*      */           }
/* 3032 */           String lName = EnterpriseUtil.getLoggedInUserName(hreq);
/* 3033 */           if (lName != null)
/*      */           {
/*      */ 
/* 3036 */             Properties siteProps = new Properties();
/*      */             
/* 3038 */             if ((!siteProps.isEmpty()) && (siteProps.containsKey("allSites")))
/*      */             {
/* 3040 */               siteIds = siteProps.getProperty("allSites");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3045 */             siteIds = "";
/*      */           }
/*      */         }
/* 3048 */         url1 = url1 + "&siteIds=" + siteIds;
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3053 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 3056 */     AMLog.debug("Net Url is : " + url1);
/* 3057 */     HttpClient client = new HttpClient();
/* 3058 */     PostMethod method = new PostMethod(url1);
/* 3059 */     method.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
/* 3060 */     BufferedReader br = null;
/* 3061 */     InputStreamReader in_rd = null;
/* 3062 */     PrintWriter out = null;
/* 3063 */     InputStream inst = null;
/*      */     try
/*      */     {
/* 3066 */       int statusCode = client.executeMethod(method);
/* 3067 */       inst = method.getResponseBodyAsStream();
/* 3068 */       in_rd = new InputStreamReader(inst);
/* 3069 */       br = new BufferedReader(in_rd);
/* 3070 */       hresponse.setContentType("text/xml; charset=UTF-8");
/* 3071 */       out = hresponse.getWriter();
/*      */       
/* 3073 */       String line = null;
/* 3074 */       StringBuffer buf = new StringBuffer();
/* 3075 */       while ((line = br.readLine()) != null)
/*      */       {
/* 3077 */         buf.append(line);
/*      */       }
/* 3079 */       out.println(buf.toString());
/* 3080 */       out.flush();
/* 3081 */       AMLog.debug("successfully written!");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3085 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3089 */       if (in_rd != null) in_rd.close();
/* 3090 */       if (inst != null) inst.close();
/* 3091 */       if (br != null) br.close();
/* 3092 */       if (out != null) { out.close();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyFileFromProbeToCentral(String fileToQueryFromProbe, String pathToStoreAtCentral, String fileName)
/*      */   {
/*      */     try
/*      */     {
/* 3104 */       String nmshome = System.getProperty("webnms.rootdir");
/* 3105 */       String filepathToStoreAtCentral = findAndReplaceAll(pathToStoreAtCentral, "/", File.separator);
/*      */       
/* 3107 */       File f = new File(nmshome + filepathToStoreAtCentral + File.separator + fileName);
/* 3108 */       if (f.exists())
/*      */       {
/* 3110 */         return;
/*      */       }
/*      */       
/*      */ 
/* 3114 */       String[] directories = pathToStoreAtCentral.split("/");
/* 3115 */       String dirStructure = "." + File.separator;
/* 3116 */       for (int i = 0; i < directories.length; i++)
/*      */       {
/* 3118 */         dirStructure = dirStructure + File.separator + directories[i];
/* 3119 */         File dir = new File(dirStructure);
/* 3120 */         if (!dir.exists())
/*      */         {
/* 3122 */           dir.mkdir();
/*      */         }
/*      */       }
/* 3125 */       Properties formProps = new Properties();
/* 3126 */       formProps.setProperty("probeId", "1");
/* 3127 */       com.manageengine.appmanager.comm.HTTPResponse ress = EnterpriseUtil.sendAsynchRequestToProbe(fileToQueryFromProbe, "Post", null, null, 0);
/* 3128 */       int code = ress.getStatusCode();
/*      */       
/* 3130 */       if (code >= 300)
/*      */       {
/* 3132 */         AMLog.debug("Resource not found on probe installation directory: " + fileToQueryFromProbe);
/*      */       }
/* 3134 */       f.createNewFile();
/* 3135 */       byte[] data = ress.getData();
/* 3136 */       FileOutputStream fo = null;
/*      */       try
/*      */       {
/* 3139 */         fo = new FileOutputStream(f);
/* 3140 */         fo.write(data);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3144 */         ex.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3148 */         if (ress != null)
/*      */         {
/* 3150 */           ress.releaseConnection();
/*      */         }
/* 3152 */         if (fo != null)
/*      */         {
/* 3154 */           fo.close();
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3161 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getAPIKeyFromOPM(String urlToContact, String username, String password, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3175 */     String apiKey = "";
/*      */     try
/*      */     {
/* 3178 */       String url = urlToContact + "/mobileNativeLogin?userName=" + username + "&password=" + password;
/* 3179 */       URI uri = new URI(url);
/* 3180 */       HTTPConnection con = new HTTPConnection(uri);
/* 3181 */       NVPair nvpairP = new NVPair("Pragma", "no-cache");
/* 3182 */       NVPair nvpair4 = new NVPair("clienttype", "html");
/* 3183 */       NVPair[] headersP = { nvpairP, nvpair4 };
/* 3184 */       HTTPClient.HTTPResponse rsp1 = con.Post(getURI(url), headersP);
/*      */       
/* 3186 */       byte[] b = rsp1.getData();
/* 3187 */       String s = new String(b);
/*      */       
/* 3189 */       JSONObject json1 = null;
/*      */       
/* 3191 */       if (s.indexOf("error") == -1)
/*      */       {
/* 3193 */         json1 = new JSONObject(s);
/* 3194 */         JSONObject ipauth = json1.getJSONObject("IphoneAuth");
/* 3195 */         JSONObject details = ipauth.getJSONObject("Details");
/* 3196 */         apiKey = (String)details.get("apiKey");
/*      */         
/* 3198 */         String updateApiKey = "update AM_INTEGRATEDPRODUCTS set APIKEY='" + apiKey + "' where PRODUCT_NAME='OpManager'";
/* 3199 */         AMConnectionPool.executeUpdateStmt(updateApiKey);
/* 3200 */         if (EnterpriseUtil.isManagedServer())
/*      */         {
/* 3202 */           EnterpriseUtil.addUpdateQueryToFile(updateApiKey);
/*      */         }
/*      */         
/*      */       }
/* 3206 */       else if ((s.indexOf("error") != -1) && (s.indexOf("Authentication failed") != -1))
/*      */       {
/* 3208 */         apiKey = getAPIKeyFromOPM(urlToContact, username, password, request, response);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (JSONException e)
/*      */     {
/* 3214 */       e.printStackTrace();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3218 */       e.printStackTrace();
/* 3219 */       if ((e.getMessage() != null) && (e.getMessage().indexOf("Connection refused") != -1))
/*      */       {
/*      */ 
/* 3222 */         writeErrorMessage("OpManager", response, "Server Down");
/*      */       }
/*      */     }
/*      */     
/* 3226 */     return apiKey;
/*      */   }
/*      */   
/*      */   private static String validateAndGenerateApiKey(String urlToContact, String apiKey, String username, String password, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3231 */     String apiKeyNew = apiKey;
/*      */     try {
/* 3233 */       String url = urlToContact + "/api/json/admin/listUsers?apiKey=" + apiKey;
/* 3234 */       URI uri = new URI(url);
/* 3235 */       HTTPConnection con = new HTTPConnection(uri);
/* 3236 */       NVPair nvpairP = new NVPair("Pragma", "no-cache");
/* 3237 */       NVPair[] headersP = { nvpairP };
/* 3238 */       HTTPClient.HTTPResponse rsp1 = con.Post(getURI(url), headersP);
/*      */       
/* 3240 */       byte[] b = rsp1.getData();
/* 3241 */       String s = new String(b);
/*      */       
/*      */ 
/* 3244 */       if ((s.indexOf("error") != -1) && (s.indexOf("Authentication failed") != -1))
/*      */       {
/* 3246 */         apiKeyNew = getAPIKeyFromOPM(urlToContact, username, password, request, response);
/*      */       }
/*      */     } catch (Exception e) {
/* 3249 */       e.printStackTrace();
/* 3250 */       if ((e.getMessage() != null) && (e.getMessage().indexOf("Connection refused") != -1))
/*      */       {
/* 3252 */         writeErrorMessage("OpManager", response, "Server Down");
/* 3253 */         return null;
/*      */       }
/*      */     }
/* 3256 */     return apiKeyNew;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\ExtProdIntegFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */