/*      */ package jsp;
/*      */ 
/*      */ import com.adventnet.management.log.LogUser;
/*      */ import com.adventnet.nms.authentication.UserConfigAPI;
/*      */ import com.adventnet.nms.commonbe.BEServerContext;
/*      */ import com.adventnet.nms.commonfe.GenericFEAPIImpl;
/*      */ import com.adventnet.nms.db.util.DBXmlUpdate;
/*      */ import com.adventnet.nms.db.util.TreeAPI;
/*      */ import com.adventnet.nms.jsp.JspUtility;
/*      */ import com.adventnet.nms.jsp.SessionListener;
/*      */ import com.adventnet.nms.startnms.NmsMainFE;
/*      */ import com.adventnet.nms.store.relational.RelationalAPI;
/*      */ import com.adventnet.nms.topodb.TopoAPI;
/*      */ import com.adventnet.nms.util.ClientParameters;
/*      */ import com.adventnet.nms.util.GenericUtility;
/*      */ import com.adventnet.nms.util.NmsLogMgr;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.adventnet.nms.util.PureServerUtilsFE;
/*      */ import com.adventnet.nms.util.RunProcessInterface;
/*      */ import com.adventnet.security.AuthUtil;
/*      */ import com.adventnet.security.authentication.AuthenticationException;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Writer;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.Connection;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletRequest;
/*      */ import javax.servlet.ServletResponse;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ public class index extends org.apache.jasper.runtime.HttpJspBase
/*      */ {
/*   53 */   String nodesToOpen = null;
/*      */   
/*   55 */   String htmlui_frames = "false";
/*   56 */   private String nmsArcJar = null;
/*   57 */   private String userPassword = null;
/*      */   
/*      */   private String getRealmString(String paramString)
/*      */   {
/*   61 */     paramString = paramString.substring(1, paramString.length() - 1);
/*   62 */     StringBuffer localStringBuffer = new StringBuffer();
/*   63 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", true);
/*   64 */     while (localStringTokenizer.hasMoreTokens()) {
/*   65 */       localStringBuffer.append(localStringTokenizer.nextToken().trim());
/*      */     }
/*   67 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*   70 */   Hashtable paramList = new Hashtable(50);
/*      */   
/*      */   public void readClientParameters() {
/*   73 */     readClientParameters(null);
/*      */   }
/*      */   
/*      */   public void readClientParameters(String paramString)
/*      */   {
/*   78 */     this.paramList.clear();
/*   79 */     String str1 = NmsMainFE.proto;
/*   80 */     ClientParameters localClientParameters = new ClientParameters();
/*   81 */     localClientParameters.readClientParameters(this.paramList);
/*   82 */     Object localObject1; if (paramString != null)
/*      */     {
/*   84 */       localObject1 = new ClientParameters(paramString);
/*   85 */       localObject2 = new Hashtable(20);
/*   86 */       ((ClientParameters)localObject1).readClientParameters((Hashtable)localObject2);
/*   87 */       this.paramList.putAll((java.util.Map)localObject2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */     this.nmsArcJar = ((String)this.paramList.get("cache_archive"));
/*   96 */     if (this.nmsArcJar != null)
/*      */     {
/*   98 */       localObject1 = (String)this.paramList.get("ARCHIVE");
/*   99 */       localObject1 = ((String)localObject1).substring(this.nmsArcJar.length() + 1);
/*  100 */       this.paramList.put("ARCHIVE", localObject1);
/*      */     }
/*      */     
/*  103 */     this.nodesToOpen = ((String)this.paramList.get("INIT_PANEL"));
/*      */     
/*  105 */     if (this.paramList.get("SHOW_SINGLE_PAGE_IN_FRAMES") != null)
/*  106 */       this.htmlui_frames = ((String)this.paramList.get("SHOW_SINGLE_PAGE_IN_FRAMES"));
/*  107 */     if (this.nodesToOpen == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  113 */       this.nodesToOpen = "ipnet.netmap";
/*      */       try
/*      */       {
/*  116 */         localObject1 = GenericUtility.getTopoAPI();
/*  117 */         this.nodesToOpen = (((TopoAPI)localObject1).getLocalNetworkAddrs() + ".netmap");
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*  121 */         System.err.println("Exception while obtaining the LocalNetworkAddr from the Server.");
/*  122 */         this.nodesToOpen = "ipnet.netmap";
/*      */       }
/*  124 */       this.paramList.put("INIT_PANEL", this.nodesToOpen);
/*      */     }
/*  126 */     this.paramList.put("CLIENT_SERVER", str1);
/*  127 */     String str2 = PureServerUtilsFE.getClientTransportClassName();
/*  128 */     this.paramList.put("CLIENT_CLASS_NAME", str2);
/*  129 */     this.paramList.put("KEEPALIVE_WINDOW_SIZE", new Integer(NmsUtil.keepalive_window_size).toString());
/*  130 */     Object localObject2 = TimeZone.getDefault().getID();
/*      */     
/*  132 */     BEServerContext localBEServerContext = PureServerUtilsFE.getBEServerContext();
/*  133 */     Properties localProperties = localBEServerContext.getProperties();
/*  134 */     String str3 = (String)localProperties.get("TimeZoneID");
/*      */     
/*  136 */     this.paramList.put("BE_TIMEZONE_ID", str3);
/*  137 */     this.paramList.put("FE_TIMEZONE_ID", localObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getNodeToOpen(String paramString, Element paramElement)
/*      */     throws IOException
/*      */   {
/*  145 */     String str1 = null;
/*  146 */     if (paramElement == null)
/*      */     {
/*  148 */       return null;
/*      */     }
/*  150 */     String str2 = "";
/*  151 */     if (paramElement.getAttribute("URL") != null) {
/*  152 */       str2 = paramElement.getAttribute("URL");
/*      */     }
/*  154 */     if (str2.equals(paramString))
/*      */     {
/*  156 */       return paramElement.getAttribute("NODEID");
/*      */     }
/*  158 */     NodeList localNodeList = paramElement.getChildNodes();
/*  159 */     if (localNodeList == null)
/*      */     {
/*  161 */       return null;
/*      */     }
/*  163 */     int i = localNodeList.getLength();
/*  164 */     for (int j = 0; j < i; j++)
/*      */     {
/*  166 */       Element localElement = (Element)localNodeList.item(j);
/*  167 */       if (localElement != null)
/*      */       {
/*      */ 
/*      */ 
/*  171 */         str1 = getNodeToOpen(paramString, localElement);
/*  172 */         if (str1 != null)
/*  173 */           return str1;
/*      */       } }
/*  175 */     return str1;
/*      */   }
/*      */   
/*      */   public Element searchForElement(String paramString, Element paramElement, JspWriter paramJspWriter) throws IOException
/*      */   {
/*  180 */     String str = paramElement.getAttribute("ID");
/*  181 */     if (paramElement == null)
/*      */     {
/*  183 */       return null;
/*      */     }
/*  185 */     if (str.equals(paramString))
/*      */     {
/*  187 */       return paramElement;
/*      */     }
/*  189 */     NodeList localNodeList = paramElement.getChildNodes();
/*  190 */     if (localNodeList == null)
/*      */     {
/*  192 */       return null;
/*      */     }
/*  194 */     int i = localNodeList.getLength();
/*  195 */     for (int j = 0; j < i; j++)
/*      */     {
/*  197 */       Element localElement1 = null;
/*  198 */       Element localElement2 = (Element)localNodeList.item(j);
/*  199 */       if (localElement2 != null)
/*      */       {
/*      */ 
/*      */ 
/*  203 */         localElement1 = searchForElement(paramString, localElement2, paramJspWriter);
/*  204 */         if (localElement1 != null)
/*  205 */           return localElement1;
/*      */       } }
/*  207 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Element getLeafNode(Element paramElement)
/*      */   {
/*  217 */     Object localObject = null;
/*  218 */     NodeList localNodeList1 = paramElement.getChildNodes();
/*  219 */     int i = localNodeList1.getLength();
/*  220 */     for (int j = 0; j < i; j++)
/*      */     {
/*  222 */       Element localElement1 = (Element)localNodeList1.item(j);
/*  223 */       if (localElement1.getNodeType() == 1) {
/*  224 */         NodeList localNodeList2 = localElement1.getChildNodes();
/*  225 */         int k = localNodeList2.getLength();
/*      */         
/*      */ 
/*  228 */         Element localElement2 = null;
/*  229 */         for (int m = 0; m < k; m++)
/*      */         {
/*  231 */           Node localNode = localNodeList2.item(m);
/*  232 */           if (localNode.getNodeType() == 1)
/*      */           {
/*  234 */             localElement2 = (Element)localNode;
/*  235 */             break;
/*      */           }
/*      */         }
/*  238 */         if (localElement2 == null) {
/*  239 */           return localElement1;
/*      */         }
/*  241 */         return getLeafNode(localElement1);
/*      */       } }
/*  243 */     return null;
/*      */   }
/*      */   
/*      */   private String getReqString(Element paramElement)
/*      */   {
/*  248 */     if (paramElement == null)
/*      */     {
/*  250 */       return "../jsp/ShowImage.jsp?imageName=../images/screen.png";
/*      */     }
/*  252 */     String str1 = paramElement.getAttribute("ID");
/*  253 */     if (str1 != null)
/*      */     {
/*  255 */       str1 = URLEncoder.encode(str1);
/*      */     }
/*  257 */     String str2 = paramElement.getAttribute("TREE-NAME");
/*  258 */     String str3 = paramElement.getAttribute("MENU-FILE-NAME");
/*  259 */     StringBuffer localStringBuffer = new StringBuffer(paramElement.getAttribute("URL"));
/*  260 */     if ((localStringBuffer == null) || (localStringBuffer.length() == 0))
/*      */     {
/*  262 */       localStringBuffer = new StringBuffer();
/*  263 */       localStringBuffer.append("jsp/ShowImage.jsp?imageName=../images/screen.png");
/*      */     }
/*  265 */     if (localStringBuffer.toString().indexOf(":") == -1)
/*      */     {
/*  267 */       localStringBuffer.insert(0, "../");
/*  268 */       if (localStringBuffer.toString().indexOf("?") == -1)
/*      */       {
/*  270 */         localStringBuffer.append("?");
/*      */       }
/*      */       else
/*      */       {
/*  274 */         localStringBuffer.append("&");
/*      */       }
/*  276 */       localStringBuffer.append("type=").append(str1);
/*  277 */       localStringBuffer.append("&displayName=").append(URLEncoder.encode(str2));
/*  278 */       localStringBuffer.append("&menuFileName=").append(str3);
/*      */     }
/*  280 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */   private boolean isSAServerRunning()
/*      */   {
/*  285 */     return com.adventnet.nms.fe.sas.NmsSAServerFE.isSAServerRunning();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  295 */   private static boolean _jspx_inited = false;
/*      */   
/*      */   public final void _jspx_init()
/*      */     throws org.apache.jasper.runtime.JspException
/*      */   {}
/*      */   
/*      */   public void _jspService(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws IOException, javax.servlet.ServletException
/*      */   {
/*  303 */     JspFactory localJspFactory = null;
/*  304 */     PageContext localPageContext = null;
/*  305 */     HttpSession localHttpSession = null;
/*  306 */     ServletContext localServletContext = null;
/*  307 */     javax.servlet.ServletConfig localServletConfig = null;
/*  308 */     JspWriter localJspWriter = null;
/*  309 */     index localindex = this;
/*  310 */     Object localObject1 = null;
/*      */     try
/*      */     {
/*  313 */       if (!_jspx_inited) {
/*  314 */         synchronized (this) {
/*  315 */           if (!_jspx_inited) {
/*  316 */             _jspx_init();
/*  317 */             _jspx_inited = true;
/*      */           }
/*      */         }
/*      */       }
/*  321 */       localJspFactory = JspFactory.getDefaultFactory();
/*  322 */       paramHttpServletResponse.setContentType("text/html;ISO-8859-1");
/*  323 */       localPageContext = localJspFactory.getPageContext(this, paramHttpServletRequest, paramHttpServletResponse, "", true, 8192, true);
/*      */       
/*      */ 
/*  326 */       localServletContext = localPageContext.getServletContext();
/*  327 */       localServletConfig = localPageContext.getServletConfig();
/*  328 */       localHttpSession = localPageContext.getSession();
/*  329 */       localJspWriter = localPageContext.getOut();
/*      */       
/*      */ 
/*  332 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  336 */       localJspWriter.write("\n\n");
/*      */       
/*      */ 
/*      */ 
/*  340 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  344 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  348 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  352 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  356 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  360 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  364 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  368 */       localJspWriter.write("\n\n");
/*      */       
/*      */ 
/*      */ 
/*  372 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  376 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  380 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  384 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  388 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  392 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  396 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  400 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  404 */       localJspWriter.write(" \n\n");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  410 */       ??? = (String)localHttpSession.getAttribute("Language");
/*  411 */       String str1 = (String)localHttpSession.getAttribute("Country");
/*  412 */       String str2 = "text/html;charset=" + JspUtility.getContentType((String)???, str1);
/*  413 */       paramHttpServletResponse.setContentType(str2);
/*      */       
/*  415 */       int i = 0;
/*  416 */       int j = 0;
/*  417 */       String str3 = null;
/*  418 */       String str4 = "/jsp/Login.jsp?";
/*  419 */       String str5 = paramHttpServletRequest.getRequestURI();
/*  420 */       String str6 = null;
/*  421 */       String str7 = paramHttpServletRequest.getMethod();
/*      */       
/*      */ 
/*  424 */       if (str7.equalsIgnoreCase("GET"))
/*      */       {
/*  426 */         str6 = paramHttpServletRequest.getQueryString();
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*  431 */       else if (str7.equalsIgnoreCase("POST")) {
/*  432 */         int k = 0;
/*  433 */         if (paramHttpServletRequest.getParameter("buttonCount") != null)
/*      */         {
/*  435 */           k = 1;
/*      */         }
/*      */         
/*      */ 
/*  439 */         localObject4 = paramHttpServletRequest.getParameterNames();
/*  440 */         localObject5 = new StringBuffer();
/*  441 */         while (((Enumeration)localObject4).hasMoreElements()) {
/*  442 */           localObject6 = (String)((Enumeration)localObject4).nextElement();
/*  443 */           if ((!((String)localObject6).equals("userName")) && (!((String)localObject6).equals("password")) && (!((String)localObject6).equals("login")) && (!((String)localObject6).equals("requestfrom")) && (!((String)localObject6).equals("javaui")) && ((k != 0) || ((k == 0) && (!((String)localObject6).equals("htmlui"))))) {
/*  444 */             localObject7 = paramHttpServletRequest.getParameter((String)localObject6);
/*  445 */             if (localObject7 != null)
/*      */             {
/*  447 */               localObject7 = URLEncoder.encode((String)localObject7);
/*      */             }
/*  449 */             localObject5 = ((StringBuffer)localObject5).append((String)localObject6).append("=").append((String)localObject7).append("&");
/*      */           }
/*      */         }
/*  452 */         str6 = ((StringBuffer)localObject5).toString();
/*      */       }
/*      */       
/*  455 */       String str8 = paramHttpServletRequest.getParameter("requestfrom");
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
/*  466 */       if ((localHttpSession.getAttribute("userName") == null) || ((str8 != null) && (str8.equals("loginPage"))))
/*      */       {
/*      */ 
/*  469 */         localObject4 = paramHttpServletRequest.getParameter("userName");
/*  470 */         if ((localObject4 == null) || (((String)localObject4).equals("null")) || (((String)localObject4).trim().equals(""))) {
/*  471 */           if (str8 == null) {
/*  472 */             str4 = str4 + "message=" + URLEncoder.encode(NmsUtil.GetString("Please authenticate yourself")) + "&buttonCount=singleButton&uri=" + str5;
/*      */           } else {
/*  474 */             str4 = str4 + "message=" + URLEncoder.encode(NmsUtil.GetString("Please enter user name"));
/*      */           }
/*  476 */           if (str6 != null) {
/*  477 */             str4 = str4 + "&" + str6;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  483 */           localJspWriter.write("\n\t\t<script>\n\t\twindow.open(\"");
/*      */           
/*      */ 
/*      */ 
/*  487 */           localJspWriter.print(str4);
/*      */           
/*      */ 
/*  490 */           localJspWriter.write("\",\"_top\");\n\t\t</script>\n\t\t");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  495 */           return;
/*      */         }
/*  497 */         localObject5 = paramHttpServletRequest.getParameter("password");
/*  498 */         if ((localObject5 == null) || (((String)localObject5).equals("null")) || (((String)localObject5).trim().equals(""))) {
/*  499 */           if (str8 == null) {
/*  500 */             str4 = str4 + "message=" + URLEncoder.encode(NmsUtil.GetString("Please authenticate yourself")) + "&buttonCount=singleButton&uri=" + str5;
/*      */           } else {
/*  502 */             str4 = str4 + "message=" + URLEncoder.encode(NmsUtil.GetString("Please enter password"));
/*      */           }
/*  504 */           if (str6 != null) {
/*  505 */             str4 = str4 + "&" + str6;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  510 */           localJspWriter.write("\n\t\t<script>\n\t\twindow.open(\"");
/*      */           
/*      */ 
/*      */ 
/*  514 */           localJspWriter.print(str4);
/*      */           
/*      */ 
/*  517 */           localJspWriter.write("\",\"_top\");\n\t\t</script>\n\t\t");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  522 */           return;
/*      */         }
/*  524 */         localObject6 = new String("<HTML><HEAD><TITLE>Please try again...</TITLE><LINK HREF=../template/nmshtmlui.css rel=stylesheet></HEAD><BODY><I><B><FONT id='leftcap'>" + NmsUtil.GetString("AdventNet") + " " + NmsUtil.GetString("WebNMS Version x.x") + "</FONT></B></I><P>&nbsp;</P><P>&nbsp;</P><HR><P>&nbsp;</P>&nbsp;<P></P>&nbsp;<P></P>&nbsp;<P></P><FONT class='redFont'><CENTER>The server is not yet initialized. It will take a while to do so. Please try again...</CENTER></FONT></BODY></HTML>");
/*  525 */         if (!NmsUtil.webNMSModulesStarted) {
/*  526 */           localJspWriter.println((String)localObject6);
/*  527 */           localJspWriter.flush();
/*  528 */           localJspWriter.close();
/*  529 */           return;
/*      */         }
/*  531 */         for (localObject7 = NmsUtil.runProcessModules.keys(); ((Enumeration)localObject7).hasMoreElements();) {
/*  532 */           String str9 = (String)((Enumeration)localObject7).nextElement();
/*  533 */           localObject8 = (RunProcessInterface)NmsUtil.runProcessModules.get(str9);
/*  534 */           if (!((RunProcessInterface)localObject8).isInitialized()) {
/*  535 */             localJspWriter.println((String)localObject6);
/*  536 */             System.err.println(str9 + NmsUtil.GetString(" module not initialized"));
/*  537 */             localJspWriter.flush();
/*  538 */             localJspWriter.close();
/*  539 */             return;
/*      */           }
/*      */         }
/*      */         
/*  543 */         if (!NmsMainFE.isStarted)
/*      */         {
/*  545 */           System.err.println(NmsUtil.GetString("FATAL : Front End Server is not yet started"));
/*      */           
/*      */ 
/*      */ 
/*  549 */           localJspWriter.write("\n\t\t\t<HTML>\n\t\t\t<TITLE>\n                             ");
/*      */           
/*      */ 
/*      */ 
/*  553 */           localJspWriter.print(NmsUtil.GetString("Front End Server is not Started/Initialized"));
/*      */           
/*      */ 
/*  556 */           localJspWriter.write("\n\t\t\t</TITLE>\n\t\t\t<LINK HREF=../template/nmshtmlui.css rel=stylesheet>\n\t\t\t<BODY>\n\t\t\t<I><B>\n\t\t\t<FONT id=leftcap>");
/*      */           
/*      */ 
/*      */ 
/*  560 */           localJspWriter.print(NmsUtil.GetString("AdventNet"));
/*      */           
/*      */ 
/*  563 */           localJspWriter.write(" ");
/*      */           
/*      */ 
/*      */ 
/*  567 */           localJspWriter.print(NmsUtil.GetString("WebNMS Version x.x"));
/*      */           
/*      */ 
/*  570 */           localJspWriter.write("</FONT>\n\t\t\t</B></I>\n\t\t\t<hr>\n\t\t\t<br><br><br><br><br><br>\n\t\t\t<center><font class=redFont>");
/*      */           
/*      */ 
/*      */ 
/*  574 */           localJspWriter.print(NmsUtil.GetString("Front End Server is not yet started or initialized !!."));
/*      */           
/*      */ 
/*  577 */           localJspWriter.write("</font>\n\t\t\t</BODY>\n\t\t\t</HTML>\n\t\t");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  582 */           return;
/*      */         }
/*      */         
/*  585 */         boolean bool1 = false;
/*      */         
/*      */ 
/*      */ 
/*  589 */         localObject8 = paramHttpServletRequest.getRemoteHost();
/*      */         
/*      */ 
/*      */ 
/*  593 */         if ((localObject8 == null) || (((String)localObject8).trim().equals("")))
/*      */         {
/*      */ 
/*  596 */           localObject8 = paramHttpServletRequest.getRemoteAddr();
/*      */         }
/*      */         
/*  599 */         localObject9 = new Properties();
/*      */         
/*  601 */         ((Properties)localObject9).setProperty("hostname", (String)localObject8);
/*      */         Object localObject11;
/*      */         try {
/*  604 */           int m = 0;
/*  605 */           String str12 = NmsUtil.getParameter("MAX_CLIENT_SESSIONS");
/*  606 */           if (str12 != null)
/*      */           {
/*      */             try
/*      */             {
/*  610 */               m = Integer.parseInt(str12);
/*      */             }
/*      */             catch (Exception localException2)
/*      */             {
/*  614 */               m = 0;
/*      */             }
/*      */           }
/*  617 */           localObject11 = GenericFEAPIImpl.getAPI().getActualUsers();
/*      */           
/*  619 */           if ((m > 0) && (((List)localObject11).size() >= m))
/*      */           {
/*  621 */             str4 = str4 + "message=" + URLEncoder.encode(NmsUtil.GetString("Maximum Client Session exceeds, unable to create new session"));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  626 */             localJspWriter.write("\n                     <script>\n                     window.open(\"");
/*      */             
/*      */ 
/*      */ 
/*  630 */             localJspWriter.print(str4);
/*      */             
/*      */ 
/*  633 */             localJspWriter.write("\",\"_top\");\n                     </script>\n                     ");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  638 */             return;
/*      */           }
/*      */           
/*      */ 
/*  642 */           bool1 = PureServerUtilsFE.isPasswordCorrect((String)localObject4, (String)localObject5, (Properties)localObject9);
/*      */         }
/*      */         catch (AuthenticationException localAuthenticationException)
/*      */         {
/*  646 */           int n = localAuthenticationException.getExceptionType();
/*  647 */           if (n == 0)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  652 */             bool1 = true;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  657 */             i = 1;
/*      */           }
/*  659 */           else if (n == 1)
/*      */           {
/*  661 */             str3 = NmsUtil.GetString("The User Account has EXPIRED. Please contact the Administrator.");
/*      */           }
/*  663 */           else if (n == 2)
/*      */           {
/*  665 */             str3 = NmsUtil.GetString("Maximum Login attempts reached. The User Account has been DISABLED. Please contact the Administrator.");
/*      */           }
/*  667 */           else if (n == 3)
/*      */           {
/*  669 */             str3 = "The User Account has been DISABLED. Please contact the Administrator.";
/*      */           }
/*  671 */           else if (n != 4)
/*      */           {
/*      */ 
/*  674 */             if (n == 5)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  679 */               bool1 = true;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  684 */               j = 1;
/*      */             }
/*  686 */             else if (n == -1)
/*      */             {
/*  688 */               str3 = localAuthenticationException.getMessage();
/*      */             } }
/*  690 */           if ((str3 == null) || (str3.trim().equals("")) || (str3.trim().equals("null")))
/*      */           {
/*  692 */             str3 = NmsUtil.GetString("Login Failed. Please contact the Administrator");
/*      */           }
/*  694 */           if ((i == 0) && (j == 0))
/*      */           {
/*  696 */             str4 = str4 + NmsUtil.GetString("message=") + URLEncoder.encode(str3);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  702 */             localJspWriter.write("\n\t\t\t<script>\n\t\t\twindow.open(\"");
/*      */             
/*      */ 
/*      */ 
/*  706 */             localJspWriter.print(str4);
/*      */             
/*      */ 
/*  709 */             localJspWriter.write("\",\"_top\");\n\t\t\t</script>\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  714 */             return;
/*      */           }
/*      */         }
/*      */         
/*  718 */         if (bool1) {
/*  719 */           localHttpSession.setAttribute("userName", localObject4);
/*  720 */           localHttpSession.setAttribute("password", localObject5);
/*      */           
/*  722 */           localHttpSession.setMaxInactiveInterval(1800);
/*      */           
/*      */ 
/*  725 */           String str11 = paramHttpServletRequest.getRemoteAddr();
/*  726 */           NmsLogMgr.MISCUSER.log(NmsUtil.GetString("User ") + (String)localObject4 + NmsUtil.GetString(" logged into the Browser Client at ") + JspUtility.gettime() + NmsUtil.GetString("from ") + str11, 1);
/*      */           
/*      */ 
/*  729 */           if (!NmsMainFE.singleJVM)
/*      */           {
/*      */             try
/*      */             {
/*      */ 
/*  734 */               NmsMainFE.downloadClientFilesFromBE((String)localObject4);
/*      */             }
/*      */             catch (Exception localException1)
/*      */             {
/*  738 */               NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception in downloading client related files from the BackEnd Server..."), localException1);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  743 */           localObject10 = null;
/*      */           try
/*      */           {
/*  746 */             localObject10 = NmsUtil.relapi.getConnection();
/*  747 */             localObject11 = new DBXmlUpdate((Connection)localObject10);
/*  748 */             boolean bool2 = ((DBXmlUpdate)localObject11).updateDB((String)localObject4, "Tree.xml");
/*  749 */             NmsLogMgr.MISCUSER.log(NmsUtil.GetString("Populated database for User ") + (String)localObject4 + " " + String.valueOf(bool2) + " ", 1);
/*      */ 
/*      */           }
/*      */           catch (Exception localException3)
/*      */           {
/*  754 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception in Populating the database for the user..."), localException3);
/*  755 */             localJspWriter.println("<br><br><center>" + localException3.getMessage() + "</center>");
/*  756 */             return;
/*      */           }
/*      */           
/*      */ 
/*  760 */           if (paramHttpServletRequest.getParameter("htmlui") != null)
/*      */           {
/*  762 */             GenericFEAPIImpl.getAPI().addActiveUser((String)localObject4);
/*      */           }
/*      */           
/*  765 */           localObject12 = new Properties();
/*  766 */           ((Properties)localObject12).setProperty("userName", (String)localObject4);
/*  767 */           ((Properties)localObject12).setProperty("hostname", (String)localObject8);
/*  768 */           localObject13 = new SessionListener((Properties)localObject12);
/*  769 */           localHttpSession.setAttribute("sessionListener", localObject13);
/*      */           
/*      */ 
/*      */ 
/*  773 */           if (i != 0) {
/*  774 */             str4 = "/jsp/PasswordExpired.jsp";
/*  775 */             if (str6 != null) {
/*  776 */               str6 = str6.replace(' ', '+');
/*  777 */               str4 = str4 + "?htmlui=&" + str6;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  782 */             localJspWriter.write("\n\t\t\t\t<script>\n\t\t\t\twindow.open(\"");
/*      */             
/*      */ 
/*      */ 
/*  786 */             localJspWriter.print(str4);
/*      */             
/*      */ 
/*  789 */             localJspWriter.write("\",\"_top\");\n\t\t\t\t</script>\n\t\t\t");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  795 */           if (j != 0) {
/*  796 */             str4 = "/jsp/PasswordExpired.jsp";
/*  797 */             if (str6 != null) {
/*  798 */               str6 = str6.replace(' ', '+');
/*  799 */               str4 = str4 + "?htmlui=&firstLogin=true&" + str6;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  804 */             localJspWriter.write("\n\t\t\t\t<script>\n\t\t\t\twindow.open(\"");
/*      */             
/*      */ 
/*      */ 
/*  808 */             localJspWriter.print(str4);
/*      */             
/*      */ 
/*  811 */             localJspWriter.write("\",\"_top\");\n\t\t\t\t</script>\n\t\t\t");
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  819 */           str4 = str4 + NmsUtil.GetString("message=") + URLEncoder.encode(NmsUtil.GetString("Login Failed. Please verify your user name and password."));
/*  820 */           if (str8 == null) {
/*  821 */             str4 = str4 + "&buttonCount=singleButton&uri=" + str5;
/*      */           }
/*  823 */           if (str6 != null) {
/*  824 */             str4 = str4 + "&" + str6;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  829 */           localJspWriter.write("\n\t\t<script>\n\t\twindow.open(\"");
/*      */           
/*      */ 
/*      */ 
/*  833 */           localJspWriter.print(str4);
/*      */           
/*      */ 
/*  836 */           localJspWriter.write("\",\"_top\");\n\t\t</script>\n\t\t");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  841 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  846 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  850 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  854 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  858 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  862 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  866 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  870 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  874 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  878 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  882 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  886 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  890 */       localJspWriter.write("                \n");
/*      */       
/*      */ 
/*      */ 
/*  894 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  898 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  902 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  906 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  910 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  914 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  918 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  922 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  926 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  930 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  934 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  938 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  942 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/*  946 */       localJspWriter.write("\n\t");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  951 */       Object localObject4 = "?";
/*      */       
/*  953 */       paramHttpServletResponse.setStatus(200);
/*  954 */       Object localObject5 = new Hashtable(50);
/*  955 */       Object localObject6 = paramHttpServletRequest.getParameterNames();
/*  956 */       while (((Enumeration)localObject6).hasMoreElements()) {
/*  957 */         localObject7 = (String)((Enumeration)localObject6).nextElement();
/*  958 */         str10 = paramHttpServletRequest.getParameter((String)localObject7);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  963 */         if ((!((String)localObject7).equals("userName")) && (!((String)localObject7).equals("password")))
/*      */         {
/*  965 */           localObject4 = (String)localObject4 + (String)localObject7 + "=" + URLEncoder.encode(str10) + "&";
/*      */         }
/*      */         
/*  968 */         if (localObject7 == null) localObject7 = "-";
/*  969 */         if ((str10 != null) && (str10.indexOf("(") != -1)) {
/*  970 */           localObject8 = new StringTokenizer(str10, "(");
/*  971 */           str10 = ((StringTokenizer)localObject8).nextToken();
/*      */         }
/*  973 */         ((Hashtable)localObject5).put(localObject7, str10);
/*      */       }
/*      */       
/*  976 */       if (localHttpSession.getAttribute("Country") == null) {
/*  977 */         if (((Hashtable)localObject5).get("Country") == null) ((Hashtable)localObject5).put("Country", "null");
/*  978 */         localHttpSession.setAttribute("Country", ((String)((Hashtable)localObject5).get("Country")).trim());
/*      */       }
/*      */       
/*      */ 
/*  982 */       if (localHttpSession.getAttribute("Language") == null) {
/*  983 */         if (((Hashtable)localObject5).get("Language") == null) ((Hashtable)localObject5).put("Language", "null");
/*  984 */         localHttpSession.setAttribute("Language", ((String)((Hashtable)localObject5).get("Language")).trim());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  991 */       Object localObject7 = paramHttpServletResponse.getWriter();
/*  992 */       String str10 = paramHttpServletRequest.getRemoteHost();
/*  993 */       Object localObject8 = paramHttpServletRequest.getPathTranslated();
/*  994 */       Object localObject9 = (String)localHttpSession.getAttribute("userName");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1029 */       Object localObject10 = (UserConfigAPI)NmsUtil.getAPI("UserConfigAPI");
/* 1030 */       Object localObject12 = null;
/* 1031 */       if (localObject10 != null)
/*      */       {
/* 1033 */         localObject13 = ((UserConfigAPI)localObject10).getAllGroupNames((String)localObject9);
/* 1034 */         if (localObject13 != null)
/*      */         {
/* 1036 */           localObject12 = getRealmString(((Vector)localObject13).toString().trim());
/*      */         }
/*      */       }
/* 1039 */       readClientParameters((String)localObject9);
/*      */       
/*      */ 
/* 1042 */       this.nodesToOpen = (paramHttpServletRequest.getParameter("nodeToOpen") == null ? this.nodesToOpen : paramHttpServletRequest.getParameter("nodeToOpen"));
/* 1043 */       if (localHttpSession.getAttribute("increments") == null) {
/* 1044 */         localHttpSession.setAttribute("increments", GenericUtility.populateIncrementsVector((String)localObject9));
/*      */       }
/* 1046 */       NmsUtil.current_keys.addElement(paramHttpServletRequest.getRemoteAddr());
/*      */       
/*      */ 
/* 1049 */       localJspWriter.write("\n<HTML><HEAD>\n<meta http-equiv=\"Content-Type\" content=\"");
/*      */       
/*      */ 
/*      */ 
/* 1053 */       localJspWriter.print(str2);
/*      */       
/*      */ 
/* 1056 */       localJspWriter.write("\">\n<TITLE>");
/*      */       
/*      */ 
/*      */ 
/* 1060 */       localJspWriter.print(NmsUtil.GetString("AdventNet") + " " + NmsUtil.GetString("WebNMS Version x.x"));
/*      */       
/*      */ 
/* 1063 */       localJspWriter.write("</TITLE></HEAD>\n");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1068 */       Object localObject13 = paramHttpServletRequest.getRequestedSessionId();
/* 1069 */       Object localObject14; if ((localObject13 != null) && (paramHttpServletRequest.getParameter("requestfrom") == null) && (paramHttpServletRequest.getParameter("login") == null) && ((((Hashtable)localObject5).containsKey("htmlui")) || (((Hashtable)localObject5).containsKey("fromAppClient"))))
/*      */       {
/* 1071 */         localObject14 = new Cookie("JSESSIONID", (String)localObject13);
/* 1072 */         ((Cookie)localObject14).setPath("/");
/* 1073 */         paramHttpServletResponse.addCookie((Cookie)localObject14); }
/*      */       Object localObject15;
/*      */       Object localObject18;
/* 1076 */       Object localObject16; if (((Hashtable)localObject5).containsKey("fromAppClient"))
/*      */       {
/*      */ 
/* 1079 */         if (this.htmlui_frames.equalsIgnoreCase("true"))
/*      */         {
/* 1081 */           localObject14 = paramHttpServletRequest.getParameter("fromAppClient");
/* 1082 */           if (((String)localObject14).indexOf("../") != -1) {
/* 1083 */             localObject14 = ((String)localObject14).substring(((String)localObject14).indexOf("../") + 3);
/* 1084 */           } else if (((String)localObject14).indexOf("/jsp") != -1)
/* 1085 */             localObject14 = ((String)localObject14).substring(((String)localObject14).indexOf("/jsp") + 1);
/* 1086 */           localObject15 = null;
/*      */           try
/*      */           {
/* 1089 */             Connection localConnection = NmsUtil.relapi.getConnection();
/* 1090 */             localObject18 = new TreeAPI(localConnection);
/* 1091 */             localObject15 = ((TreeAPI)localObject18).getNodeFromDB((String)localObject9, null);
/* 1092 */             if (getNodeToOpen((String)localObject14, (Element)localObject15) != null) {
/* 1093 */               this.nodesToOpen = getNodeToOpen((String)localObject14, (Element)localObject15);
/*      */             }
/*      */           } catch (Exception localException5) {
/* 1096 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception in getting Node from TreeAPI") + localException5.getMessage(), localException5);
/*      */           }
/* 1098 */           localObject16 = "../jsp/ShowImage.jsp?imageName=../images/screen.png";
/* 1099 */           if (getNodeToOpen((String)localObject14, (Element)localObject15) == null)
/*      */           {
/* 1101 */             this.nodesToOpen = "Maps";
/* 1102 */             localObject16 = "/" + (String)localObject14;
/*      */           }
/* 1104 */           else if (localObject15 != null)
/*      */           {
/* 1106 */             localObject18 = searchForElement(this.nodesToOpen, (Element)localObject15, localJspWriter);
/* 1107 */             if (localObject18 == null)
/*      */             {
/* 1109 */               localObject18 = getLeafNode((Element)localObject15);
/*      */             }
/* 1111 */             localObject16 = getReqString((Element)localObject18);
/* 1112 */             localServletContext.setAttribute("elementFor" + (String)localObject9, localObject15);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1117 */           localJspWriter.write("\n\t\t\t<SCRIPT>\n\t\t\twindow.focus();\n\t\t</SCRIPT>\n\t\t\t<FRAMESET COLS=\"220,*\" >\n\t\t\t<FRAMESET ROWS=\"*,100\" >\n\t\t\t<FRAME NAME=\"tree\" SRC=\"/jsp/NmsTree.jsp?fromIndexPage=");
/*      */           
/*      */ 
/*      */ 
/* 1121 */           localJspWriter.print(URLEncoder.encode(this.nodesToOpen));
/*      */           
/*      */ 
/* 1124 */           localJspWriter.write("&completeRefresh=\" SCROLLING=\"AUTO\" MARGINWIDTH=\"1\" MARGINHEIGHT=\"6\">\n\t\t\t<FRAME NAME=\"status\" SRC=\"/jsp/Status.jsp\" MARGINWIDTH=\"0\" MARGINHEIGHT=\"5\" TOPMARGIN=\"5\" SCROLLING=\"AUTO\">\n\t\t\t</FRAMESET>\n\t\t\t<FRAME NAME=\"center\" SRC=\"");
/*      */           
/*      */ 
/*      */ 
/* 1128 */           localJspWriter.print((String)localObject16);
/*      */           
/*      */ 
/* 1131 */           localJspWriter.write("\" SCROLLING=\"AUTO\" >\n\t\t\t</FRAMESET>\n\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1142 */           localJspWriter.write("\n\t\t\t<FRAMESET ROWS=\"100%,*\" BORDER=\"0\" FRAMEBORDER=\"0\" FRAMEWIDTH=\"0\" FRAMEHEIGHT=\"0\" FRAMEMARGIN=\"0\" NORESIZE SCROLLING=\"NO\">\n\t\t\t<FRAME NAME=\"usersFile\" SRC='");
/*      */           
/*      */ 
/*      */ 
/* 1146 */           localJspWriter.print((String)((Hashtable)localObject5).get("fromAppClient"));
/*      */           
/*      */ 
/* 1149 */           localJspWriter.write("?");
/*      */           
/*      */ 
/*      */ 
/* 1153 */           localJspWriter.print(str6);
/*      */           
/*      */ 
/* 1156 */           localJspWriter.write("' NORESIZE SCROLLING=\"AUTO\">\n\t\t\t<FRAME NAME=\"dummy\" SRC=\"../html/dummy.html\" NORESIZE SCROLLING=\"NO\">\n\t\t\t</FRAMESET>\n\t\t\t");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         Object localObject17;
/*      */         Object localObject19;
/* 1163 */         if (((Hashtable)localObject5).containsKey("htmlui"))
/*      */         {
/* 1165 */           localObject14 = null;
/*      */           
/* 1167 */           localObject15 = (String)((Hashtable)localObject5).get("uri");
/*      */           try
/*      */           {
/* 1170 */             localObject16 = NmsUtil.relapi.getConnection();
/* 1171 */             localObject18 = new TreeAPI((Connection)localObject16);
/* 1172 */             localObject14 = ((TreeAPI)localObject18).getNodeFromDB((String)localObject9, null);
/*      */ 
/*      */           }
/*      */           catch (Exception localException6)
/*      */           {
/* 1177 */             NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception in getting Node from TreeAPI") + localException6.getMessage(), localException6);
/*      */           }
/* 1179 */           localObject17 = "../jsp/ShowImage.jsp?imageName=../images/screen.png";
/* 1180 */           if (localObject14 != null)
/*      */           {
/* 1182 */             localObject18 = searchForElement(this.nodesToOpen, (Element)localObject14, localJspWriter);
/* 1183 */             if (localObject18 == null)
/*      */             {
/* 1185 */               localObject18 = getLeafNode((Element)localObject14);
/*      */             }
/* 1187 */             if ((localObject15 != null) && (this.htmlui_frames.equalsIgnoreCase("true")))
/*      */             {
/*      */ 
/*      */ 
/* 1191 */               localObject19 = ((String)localObject15).substring(((String)localObject15).indexOf("/") + 1);
/* 1192 */               this.nodesToOpen = getNodeToOpen((String)localObject19, (Element)localObject14);
/* 1193 */               if (this.nodesToOpen == null)
/* 1194 */                 this.nodesToOpen = "Maps";
/* 1195 */               if ((!((String)localObject15).equals("/jsp/ShowImage.jsp")) && (!((String)localObject15).equals("/jsp/NmsTree.jsp")) && (!((String)localObject15).equals("/jsp/Status.jsp")) && (!((String)localObject15).equals("/jsp/FrameSet.jsp"))) {
/* 1196 */                 localObject17 = localObject15;
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 1201 */               localObject17 = getReqString((Element)localObject18);
/*      */             }
/* 1203 */             localServletContext.setAttribute("elementFor" + (String)localObject9, localObject14);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1208 */           localJspWriter.write("\n\t\t<SCRIPT>\n\t\twindow.focus();\n\t</SCRIPT>\n\t\t<FRAMESET COLS=\"220,*\" >\n\t\t<FRAMESET ROWS=\"*,100\" >\n\t\t<FRAME NAME=\"tree\" SRC=\"/jsp/NmsTree.jsp?fromIndexPage=");
/*      */           
/*      */ 
/*      */ 
/* 1212 */           localJspWriter.print(URLEncoder.encode(this.nodesToOpen));
/*      */           
/*      */ 
/* 1215 */           localJspWriter.write("&completeRefresh=\" SCROLLING=\"AUTO\" MARGINWIDTH=\"1\" MARGINHEIGHT=\"6\">\n\t\t<FRAME NAME=\"status\" SRC=\"/jsp/Status.jsp\" MARGINWIDTH=\"0\" MARGINHEIGHT=\"5\" TOPMARGIN=\"5\" SCROLLING=\"AUTO\">\n\t\t</FRAMESET>\n\t\t<FRAME NAME=\"center\" SRC=\"");
/*      */           
/*      */ 
/*      */ 
/* 1219 */           localJspWriter.print((String)localObject17);
/*      */           
/*      */ 
/* 1222 */           localJspWriter.write("\" SCROLLING=\"AUTO\" >\n\t\t</FRAMESET>\n\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 1228 */         else if (((Hashtable)localObject5).containsKey("javaui")) {
/*      */           String str13;
/* 1230 */           if (((Hashtable)localObject5).containsKey("showapplet"))
/*      */           {
/*      */ 
/*      */ 
/* 1234 */             localJspWriter.write("\n\t\t\t<OBJECT classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\"\n\t\t\tWIDTH=\"1\" HEIGHT=\"1\" ALIGN=\"baseline\"\n\t\t\tcodebase=\"http://java.sun.com/products/plugin/1.2/jinstall-12-win32.cab#Version=1,1,0,0\">\n\n\t\t\t<!-- for applet caching -->\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1239 */             if (this.nmsArcJar != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1244 */               localJspWriter.write("\n                        <!-- Commented by Balan on 21/03/03 for Issue Reported by Hettaras NetWork Both Archive and \n\t\t\t\t     NMSArchive is loaded in ARHIVE Tag\n\t\t\t\t\t<PARAM NAME=\"cache_archive\" VALUE= \"");
/*      */               
/*      */ 
/*      */ 
/* 1248 */               localJspWriter.print(this.nmsArcJar);
/*      */               
/*      */ 
/* 1251 */               localJspWriter.write("\" >\n                         -->\n\t\t\t\t\t<PARAM NAME=\"cache_option\" VALUE=\"Plugin\">\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1260 */             localJspWriter.write("\n\n\t\t\t<PARAM NAME=\"jsessionid\" VALUE=\"");
/*      */             
/*      */ 
/*      */ 
/* 1264 */             localJspWriter.print(localHttpSession.getId());
/*      */             
/*      */ 
/* 1267 */             localJspWriter.write("\">\n\t\t\t<PARAM NAME=\"code\" VALUE=\"com.adventnet.nms.startclient.NmsMainApplet.class\">\n\t\t\t<PARAM NAME=\"type\" VALUE=\"application/x-java-applet;version=1.2\">\n\t\t\t<PARAM name=MAP_FILE value=ipnet.netmap>\n\t\t\t<PARAM name=SHOW_BUTTONS value=false>\n\t\t\t<PARAM name=PASSWORD_KEY value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1271 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1274 */             localJspWriter.write("\">\n\t\t\t<PARAM name=HANDLE value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1278 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1281 */             localJspWriter.write("\">\n\t\t\t<PARAM name=USE_MAS value=true>\n\t\t\t<PARAM name=USER_NAME value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1285 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1288 */             localJspWriter.write("\">\n\t\t\t<PARAM name=LANGUAGE value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1292 */             localJspWriter.print((String)((Hashtable)localObject5).get("Language"));
/*      */             
/*      */ 
/* 1295 */             localJspWriter.write("\">\n\t\t\t<PARAM name=COUNTRY value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1299 */             localJspWriter.print((String)((Hashtable)localObject5).get("Country"));
/*      */             
/*      */ 
/* 1302 */             localJspWriter.write("\">  \n\t\t\t<PARAM name=NMS_FE_SECONDARY_PORT value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1306 */             localJspWriter.print(PureServerUtilsFE.nms_fe_secondary_port);
/*      */             
/*      */ 
/* 1309 */             localJspWriter.write("\">\n\t\t\t<PARAM name=NMS_FE_SECONDARY_PORT_DIR value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1313 */             localJspWriter.print(PureServerUtilsFE.nms_fe_secondary_port_dir);
/*      */             
/*      */ 
/* 1316 */             localJspWriter.write("\">\n\n");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1321 */             if (this.paramList.get("EXPOSE_PASSWORD") != null)
/*      */             {
/* 1323 */               localObject14 = this.paramList.get("EXPOSE_PASSWORD").toString();
/* 1324 */               if ("true".equals(localObject14))
/*      */               {
/* 1326 */                 localObject15 = "RtoPUX";
/* 1327 */                 localObject17 = (String)localHttpSession.getAttribute("password");
/* 1328 */                 localObject18 = AuthUtil.encryptString((String)localObject17, (String)localObject15);
/* 1329 */                 this.userPassword = URLEncoder.encode((String)localObject18);
/*      */                 
/*      */ 
/* 1332 */                 localJspWriter.write("\n                    <PARAM name=PASSWORD value=\"");
/*      */                 
/*      */ 
/*      */ 
/* 1336 */                 localJspWriter.print(this.userPassword);
/*      */                 
/*      */ 
/* 1339 */                 localJspWriter.write("\">\n");
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1347 */             localJspWriter.write("\n\n\n\t\t\t\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1352 */             if (!isSAServerRunning())
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1357 */               localJspWriter.write("\n\t\t\t\t\t<PARAM name=\"TRANSPORT_PROVIDER\" value=\"com.adventnet.nms.client.sas.SASClientTransporter\" >\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1366 */             localJspWriter.write("\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1371 */             if (localObject12 != null)
/*      */             {
/*      */ 
/*      */ 
/* 1375 */               localJspWriter.write("\n\t\t\t\t\t<PARAM name=REALMS value=\"");
/*      */               
/*      */ 
/*      */ 
/* 1379 */               localJspWriter.print((String)localObject12);
/*      */               
/*      */ 
/* 1382 */               localJspWriter.write("\" >\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1392 */             localObject14 = PureServerUtilsFE.be_host;
/* 1393 */             if (((String)localObject14).equals("localhost"))
/*      */             {
/*      */               try
/*      */               {
/* 1397 */                 localObject14 = InetAddress.getLocalHost().getHostName();
/*      */               }
/*      */               catch (Exception localException4) {}
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1404 */             str13 = PureServerUtilsFE.be_host;
/* 1405 */             if (str13.equals("localhost"))
/*      */             {
/*      */               try
/*      */               {
/* 1409 */                 str13 = InetAddress.getLocalHost().getHostAddress();
/*      */ 
/*      */ 
/*      */               }
/*      */               catch (Exception localException7) {}
/*      */             }
/*      */             else
/*      */             {
/* 1417 */               str13 = InetAddress.getByName(str13).getHostAddress();
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1423 */             localJspWriter.write("\n\t\t\t<PARAM name=BE_HOST_NAME value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1427 */             localJspWriter.print((String)localObject14);
/*      */             
/*      */ 
/* 1430 */             localJspWriter.write("\">\n                  <PARAM name=BE_HOST_ADDRESS value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1434 */             localJspWriter.print(str13);
/*      */             
/*      */ 
/* 1437 */             localJspWriter.write("\">\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1442 */             int i1 = NmsUtil.getRegistryPort();
/*      */             
/* 1444 */             if (i1 == -1)
/*      */             {
/* 1446 */               i1 = 1099;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1451 */             localJspWriter.write("\n\t\t\t<PARAM name=RMI_REG_PORT value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1455 */             localJspWriter.print(i1);
/*      */             
/*      */ 
/* 1458 */             localJspWriter.write("\">\n\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1463 */             localObject18 = Calendar.getInstance();
/* 1464 */             ((Calendar)localObject18).set(1998, 0, 1, 0, 0, 0);
/* 1465 */             localObject19 = ((Calendar)localObject18).getTime();
/* 1466 */             String str14 = String.valueOf(TimeZone.getDefault().getRawOffset());
/*      */             
/*      */ 
/*      */ 
/* 1470 */             localJspWriter.write("\n\t\t\t<PARAM name=jan1_98 value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1474 */             localJspWriter.print(((Date)localObject19).getTime());
/*      */             
/*      */ 
/* 1477 */             localJspWriter.write("\">\n\t\t\t<PARAM name=TIME_ZONE value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1481 */             localJspWriter.print(str14);
/*      */             
/*      */ 
/* 1484 */             localJspWriter.write("\">\n\t\t\t<PARAM name=CHECK value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1488 */             localJspWriter.print(paramHttpServletRequest.getHeader("Authorization"));
/*      */             
/*      */ 
/* 1491 */             localJspWriter.write("\">\n\t\t\t<PARAM name=osname value=\"");
/*      */             
/*      */ 
/*      */ 
/* 1495 */             localJspWriter.print(System.getProperty("os.name"));
/*      */             
/*      */ 
/* 1498 */             localJspWriter.write("\">\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1503 */             for (Enumeration localEnumeration = this.paramList.keys(); localEnumeration.hasMoreElements();) {
/* 1504 */               localObject20 = (String)localEnumeration.nextElement();
/*      */               
/*      */ 
/*      */ 
/* 1508 */               localJspWriter.write("\n\t\t\t\t\t<PARAM name=\"");
/*      */               
/*      */ 
/*      */ 
/* 1512 */               localJspWriter.print((String)localObject20);
/*      */               
/*      */ 
/* 1515 */               localJspWriter.write("\" value='");
/*      */               
/*      */ 
/*      */ 
/* 1519 */               localJspWriter.print((String)this.paramList.get(localObject20));
/*      */               
/*      */ 
/* 1522 */               localJspWriter.write("'>\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1531 */             localJspWriter.write("\n\t\t\t<COMMENT>\n\t\t\t<EMBED type=\"application/x-java-applet\"\n\t\t\twidth=\"1\" height=\"1\"\n\t\t\tcode=\"com.adventnet.nms.startclient.NmsMainApplet.class\"\n\t\t\tMAP_FILE=\"ipnet.netmap\"\n\t\t\tSHOW_BUTTONS=\"false\"\n\t\t\tPASSWORD_KEY='");
/*      */             
/*      */ 
/*      */ 
/* 1535 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1538 */             localJspWriter.write("'\n\t\t\tHANDLE='");
/*      */             
/*      */ 
/*      */ 
/* 1542 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1545 */             localJspWriter.write("'\n\t\t\tUSE_MAS=true\n\t\t\tUSER_NAME='");
/*      */             
/*      */ 
/*      */ 
/* 1549 */             localJspWriter.print((String)localObject9);
/*      */             
/*      */ 
/* 1552 */             localJspWriter.write("'\n\t\t\tjan1_98='");
/*      */             
/*      */ 
/*      */ 
/* 1556 */             localJspWriter.print(((Date)localObject19).getTime());
/*      */             
/*      */ 
/* 1559 */             localJspWriter.write("'\n\t\t\tLANGUAGE='");
/*      */             
/*      */ 
/*      */ 
/* 1563 */             localJspWriter.print(((Hashtable)localObject5).get("Language"));
/*      */             
/*      */ 
/* 1566 */             localJspWriter.write("'\n\t\t\tCOUNTRY='");
/*      */             
/*      */ 
/*      */ 
/* 1570 */             localJspWriter.print(((Hashtable)localObject5).get("Country"));
/*      */             
/*      */ 
/* 1573 */             localJspWriter.write("'\n\t\t\tNMS_FE_SECONDARY_PORT='");
/*      */             
/*      */ 
/*      */ 
/* 1577 */             localJspWriter.print(PureServerUtilsFE.nms_fe_secondary_port);
/*      */             
/*      */ 
/* 1580 */             localJspWriter.write("'\n\t\t\tNMS_FE_SECONDARY_PORT_DIR='");
/*      */             
/*      */ 
/*      */ 
/* 1584 */             localJspWriter.print(PureServerUtilsFE.nms_fe_secondary_port_dir);
/*      */             
/*      */ 
/* 1587 */             localJspWriter.write("'\n\t\t\tPASSWORD='");
/*      */             
/*      */ 
/*      */ 
/* 1591 */             localJspWriter.print(this.userPassword);
/*      */             
/*      */ 
/* 1594 */             localJspWriter.write("'\n\t\t\t\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1599 */             if (!isSAServerRunning())
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1604 */               localJspWriter.write("\n\t\t\t\t\tTRANSPORT_PROVIDER=\"com.adventnet.nms.client.sas.SASClientTransporter\"\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1613 */             localJspWriter.write("\n\n\n\t\t\tBE_HOST_NAME='");
/*      */             
/*      */ 
/*      */ 
/* 1617 */             localJspWriter.print((String)localObject14);
/*      */             
/*      */ 
/* 1620 */             localJspWriter.write("'\n                  BE_HOST_ADDRESS='");
/*      */             
/*      */ 
/*      */ 
/* 1624 */             localJspWriter.print(str13);
/*      */             
/*      */ 
/* 1627 */             localJspWriter.write("'  \n\t\t\tRMI_REG_PORT='");
/*      */             
/*      */ 
/*      */ 
/* 1631 */             localJspWriter.print(i1);
/*      */             
/*      */ 
/* 1634 */             localJspWriter.write("'\n\n\t\t\tTIME_ZONE='");
/*      */             
/*      */ 
/*      */ 
/* 1638 */             localJspWriter.print(str14);
/*      */             
/*      */ 
/* 1641 */             localJspWriter.write("'\n\t\t\tCHECK='");
/*      */             
/*      */ 
/*      */ 
/* 1645 */             localJspWriter.print(paramHttpServletRequest.getHeader("Authorization"));
/*      */             
/*      */ 
/* 1648 */             localJspWriter.write("'\n\t\t\tosname='");
/*      */             
/*      */ 
/*      */ 
/* 1652 */             localJspWriter.print(System.getProperty("os.name"));
/*      */             
/*      */ 
/* 1655 */             localJspWriter.write("'\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1660 */             if (localObject12 != null)
/*      */             {
/*      */ 
/*      */ 
/* 1664 */               localJspWriter.write("\n\t\t\t\t\tREALMS='");
/*      */               
/*      */ 
/*      */ 
/* 1668 */               localJspWriter.print((String)localObject12);
/*      */               
/*      */ 
/* 1671 */               localJspWriter.write("'\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1677 */             for (Object localObject20 = this.paramList.keys(); ((Enumeration)localObject20).hasMoreElements();) {
/* 1678 */               String str15 = (String)((Enumeration)localObject20).nextElement();
/*      */               
/*      */ 
/*      */ 
/* 1682 */               localJspWriter.write("\n\t\t\t\t");
/*      */               
/*      */ 
/*      */ 
/* 1686 */               localJspWriter.print(str15.replace('"', ' '));
/*      */               
/*      */ 
/* 1689 */               localJspWriter.write("='");
/*      */               
/*      */ 
/*      */ 
/* 1693 */               localJspWriter.print(this.paramList.get(str15));
/*      */               
/*      */ 
/* 1696 */               localJspWriter.write("'\n\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1705 */             localJspWriter.write("\n\t\t\tjsessionid='");
/*      */             
/*      */ 
/*      */ 
/* 1709 */             localJspWriter.print(localHttpSession.getId());
/*      */             
/*      */ 
/* 1712 */             localJspWriter.write("'\n\t\t\t>\n\n\t\t\t<!-- for applet caching -->\n\t\t\t");
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1717 */             if (this.nmsArcJar != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 1722 */               localJspWriter.write("\n                            <!-- Commented by Balan on 21/03/03 for Issue Reported by Hettaras NetWork Both Archive and \n\t\t\t\t     NMSArchive is loaded in ARHIVE Tag\n\t\t\t\t\t <PARAM NAME=\"cache_archive\" VALUE= \"");
/*      */               
/*      */ 
/*      */ 
/* 1726 */               localJspWriter.print(this.nmsArcJar);
/*      */               
/*      */ 
/* 1729 */               localJspWriter.write("\" >\n                              --> \n\t\t\t\t\t<PARAM NAME=\"cache_option\" VALUE=\"Plugin\">\n\t\t\t\t\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1738 */             localJspWriter.write("\n\n\n\n\t\t\t</EMBED>\n\t\t\t</COMMENT>\n\t\t\t</OBJECT>\n\t\t\t</BODY>\n\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1748 */             localObject14 = (String)localHttpSession.getAttribute("Country");
/* 1749 */             localObject14 = GenericUtility.replace((String)localObject14, " ", "+");
/* 1750 */             localObject14 = GenericUtility.replace((String)localObject14, "(", "%28");
/* 1751 */             localObject14 = GenericUtility.replace((String)localObject14, ")", "%29");
/*      */             
/* 1753 */             str13 = (String)localHttpSession.getAttribute("Language");
/* 1754 */             str13 = GenericUtility.replace(str13, " ", "+");
/* 1755 */             str13 = GenericUtility.replace(str13, "(", "%28");
/* 1756 */             str13 = GenericUtility.replace(str13, ")", "%29");
/*      */             
/*      */ 
/*      */ 
/* 1760 */             localJspWriter.write("\t\t\n\t\t\t<FRAMESET ROWS=\"100%,*\" BORDER=\"0\" FRAMEBORDER=\"NO\">\n\t\t\t<FRAME NAME=\"Main Page\" FRAMEBORDER=\"NO\" MARGINHEIGHT=\"0\" MARGINWIDTH=\"0\" SRC=\"../html/splash.html\">\n\t\t\t<FRAME NAME=\"contents\" FRAMEBORDER=\"NO\" MARGINHEIGHT=\"0\" MARGINWIDTH=\"0\" SRC=\"index.jsp?javaui=&showapplet=&Country=");
/*      */             
/*      */ 
/*      */ 
/* 1764 */             localJspWriter.print((String)localObject14);
/*      */             
/*      */ 
/* 1767 */             localJspWriter.write("&Language=");
/*      */             
/*      */ 
/*      */ 
/* 1771 */             localJspWriter.print(str13);
/*      */             
/*      */ 
/* 1774 */             localJspWriter.write("\" SCROLLING=\"NO\" NORESIZE >\n\t\t\t</FRAMESET>\n\t\t\t");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1782 */       localJspWriter.flush();
/*      */       
/*      */ 
/* 1785 */       localJspWriter.write("\n");
/*      */       
/*      */ 
/*      */ 
/* 1789 */       localJspWriter.write("\n\n\n");
/*      */ 
/*      */     }
/*      */     catch (Throwable localThrowable)
/*      */     {
/* 1794 */       if ((localJspWriter != null) && (localJspWriter.getBufferSize() != 0))
/* 1795 */         localJspWriter.clearBuffer();
/* 1796 */       if (localPageContext != null) localPageContext.handlePageException(localThrowable);
/*      */     } finally {
/* 1798 */       if (localJspFactory != null) localJspFactory.releasePageContext(localPageContext);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\jsp\index.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */