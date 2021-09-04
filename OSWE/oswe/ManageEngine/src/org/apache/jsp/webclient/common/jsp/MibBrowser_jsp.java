/*      */ package org.apache.jsp.webclient.common.jsp;
/*      */ 
/*      */ import com.adventnet.nms.jsp.MibBrowserMain;
/*      */ import com.adventnet.nms.util.ElementTree;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.adventnet.nms.webclient.mibbrowser.MibNodeConverter;
/*      */ import com.adventnet.snmp.mibs.LeafSyntax;
/*      */ import com.adventnet.snmp.mibs.MibModule;
/*      */ import com.adventnet.snmp.mibs.MibNode;
/*      */ import com.adventnet.snmp.mibs.MibTC;
/*      */ import com.adventnet.snmp.mibs.MibTrap;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import org.apache.crimson.tree.XmlDocument;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.tomcat.InstanceManager;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ public final class MibBrowser_jsp extends HttpJspBase implements JspSourceDependent
/*      */ {
/*   37 */   static String HELP_URL_KEY = "HTMLUI_MIBManager_Client";
/*      */   
/*      */   public String constNodesToOpen()
/*      */   {
/*   41 */     StringBuffer temp = new StringBuffer();
/*   42 */     if (this.treeState != null) {
/*   43 */       for (Enumeration vectorElement = this.treeState.elements(); vectorElement.hasMoreElements();)
/*      */       {
/*   45 */         temp.append((String)vectorElement.nextElement());
/*   46 */         temp.append(" ");
/*      */       }
/*   48 */       return temp.toString();
/*      */     }
/*   50 */     return new String();
/*      */   }
/*      */   
/*      */   public Vector treeInialize()
/*      */   {
/*   55 */     if (this.nodesToOpen != null)
/*      */     {
/*   57 */       StringTokenizer lt = new StringTokenizer(this.nodesToOpen);
/*   58 */       while (lt.hasMoreElements())
/*      */       {
/*   60 */         this.treeState.addElement(lt.nextToken());
/*      */       }
/*   62 */       return this.treeState; }
/*   63 */     return new Vector();
/*      */   }
/*      */   
/*      */ 
/*   67 */   Hashtable treeStatesOfUsers = new Hashtable();
/*      */   Vector treeState;
/*      */   Vector nodeList;
/*      */   String nodesToOpen;
/*      */   String nodeToOpen;
/*   72 */   String space = "<IMG ALIGN=\"top\" BORDER=0 WIDTH=20 HEIGHT=20 SRC=\"../images/space.png\" >";
/*      */   
/*      */   String ell;
/*      */   String ee;
/*      */   String hline;
/*      */   String vline;
/*      */   String plus;
/*      */   String minus;
/*   80 */   String url = "/MibBrowser.do";
/*   81 */   Properties data = null;
/*   82 */   Properties parameters = null;
/*   83 */   String child = null;
/*   84 */   String root = null;
/*   85 */   String childname = null;
/*   86 */   Vector childList = null;
/*   87 */   String host = null;
/*   88 */   String community = null;
/*   89 */   String wcommunity = null;
/*   90 */   String setvalue = null;
/*   91 */   String oid = null;
/*   92 */   String resultstr = null;
/*      */   String Open;
/*      */   String syntax;
/*      */   String status;
/*      */   String access;
/*      */   String index;
/*      */   String desc;
/*      */   String isTable;
/*      */   String selectedMib;
/*      */   String prevselectedMib;
/*      */   String userName;
/*      */   String targetVersion;
/*  104 */   Hashtable getLoadedMib = null;
/*      */   
/*      */ 
/*      */ 
/*  108 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */   private static Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  117 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  121 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  122 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspDestroy() {}
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  132 */     HttpSession session = null;
/*      */     
/*      */ 
/*  135 */     JspWriter out = null;
/*  136 */     Object page = this;
/*  137 */     JspWriter _jspx_out = null;
/*  138 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  142 */       response.setContentType("text/html;charset=UTF-8");
/*  143 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  145 */       _jspx_page_context = pageContext;
/*  146 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  147 */       ServletConfig config = pageContext.getServletConfig();
/*  148 */       session = pageContext.getSession();
/*  149 */       out = pageContext.getOut();
/*  150 */       _jspx_out = out;
/*      */       
/*  152 */       out.write("\n\n\n\n\n\n<html> \n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/webclient/common/js/windowFunctions.js\"></SCRIPT>\n<script> \nfunction Sbtbutton(name) \n{ \n\n\tdocument.mibForm.pressType.value=\"Button\"; \n\tdocument.mibForm.pressName.value=name; \n\tif ( (name==\"snmpget\") || (name==\"snmpgetnext\")|| (name==\"snmpgetbulk\")  || (name==\"snmptable\") || (name==\"snmpset\")) \n\t{ \n \tvar toid = document.mibForm.oid.value; \n\t\tif ( (toid != null) && (toid!=\"null\")&&(toid!=\"\")&& (toid.length > 0)) \n\t\t\tdocument.forms[\"mibForm\"].submit(); \n\t\telse \n                       alert(\"");
/*  153 */       out.print(NmsUtil.GetString("webclient.common.mibbrowser.oid.notselected.errormessage"));
/*  154 */       out.write("\"); \n\t} \n\telse \n\t{ \n\t\tdocument.forms[\"mibForm\"].submit(); \n\t} \n} \n</script>\n\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n\n");
/*  155 */       MibBrowserMain mibDetail = null;
/*  156 */       synchronized (session) {
/*  157 */         mibDetail = (MibBrowserMain)_jspx_page_context.getAttribute("mibDetail", 3);
/*  158 */         if (mibDetail == null) {
/*  159 */           mibDetail = new MibBrowserMain();
/*  160 */           _jspx_page_context.setAttribute("mibDetail", mibDetail, 3);
/*      */         }
/*      */       }
/*  163 */       out.write(32);
/*  164 */       out.write(10);
/*  165 */       out.write(10);
/*  166 */       String treeStyle = null;
/*  167 */       String oidtree = null;
/*  168 */       this.parameters = new Properties();
/*  169 */       this.syntax = "";
/*  170 */       this.access = "";
/*  171 */       this.status = "";
/*  172 */       this.index = "";
/*  173 */       this.desc = "";
/*  174 */       this.isTable = "false";
/*  175 */       MibNode mibnode = null;
/*      */       
/*      */ 
/*      */ 
/*  179 */       for (Enumeration parameterNames = request.getParameterNames(); parameterNames.hasMoreElements();)
/*      */       {
/*  181 */         String param = (String)parameterNames.nextElement();
/*  182 */         String value = request.getParameter(param);
/*  183 */         if (value == null) value = "";
/*  184 */         this.parameters.put(param, value);
/*      */       }
/*      */       
/*      */ 
/*  188 */       if ((this.parameters.getProperty("host") != null) && (this.parameters.getProperty("host").length() > 0)) {
/*  189 */         this.host = this.parameters.getProperty("host");
/*      */       } else {
/*  191 */         this.host = "localhost";
/*      */       }
/*  193 */       if ((this.parameters.getProperty("community") != null) && (this.parameters.getProperty("community").length() > 0)) {
/*  194 */         this.community = this.parameters.getProperty("community");
/*      */       } else {
/*  196 */         this.community = "public";
/*      */       }
/*  198 */       if ((this.parameters.getProperty("wcommunity") != null) && (this.parameters.getProperty("wcommunity").length() > 0)) {
/*  199 */         this.wcommunity = this.parameters.getProperty("wcommunity");
/*      */       } else {
/*  201 */         this.wcommunity = "";
/*      */       }
/*  203 */       if ((this.parameters.getProperty("setvalue") != null) && (this.parameters.getProperty("setvalue").length() > 0)) {
/*  204 */         this.setvalue = this.parameters.getProperty("setvalue");
/*      */       } else {
/*  206 */         this.setvalue = "";
/*      */       }
/*  208 */       if ((this.parameters.getProperty("resultstr") != null) && (this.parameters.getProperty("resultstr").length() > 0)) {
/*  209 */         this.resultstr = this.parameters.getProperty("resultstr");
/*      */       } else {
/*  211 */         this.resultstr = "";
/*      */       }
/*  213 */       if ((this.parameters.getProperty("oid") != null) && (this.parameters.getProperty("oid").length() > 0))
/*  214 */         this.oid = this.parameters.getProperty("oid"); else {
/*  215 */         this.oid = "";
/*      */       }
/*  217 */       if ((this.parameters.getProperty("syntax") != null) && (this.parameters.getProperty("syntax").length() > 0)) {
/*  218 */         this.syntax = this.parameters.getProperty("syntax");
/*      */       } else {
/*  220 */         this.syntax = "";
/*      */       }
/*  222 */       if ((this.parameters.getProperty("access") != null) && (this.parameters.getProperty("access").length() > 0)) {
/*  223 */         this.access = this.parameters.getProperty("access");
/*      */       } else {
/*  225 */         this.access = "";
/*      */       }
/*  227 */       if ((this.parameters.getProperty("status") != null) && (this.parameters.getProperty("status").length() > 0)) {
/*  228 */         this.status = this.parameters.getProperty("status");
/*      */       } else {
/*  230 */         this.status = "";
/*      */       }
/*  232 */       if ((this.parameters.getProperty("index") != null) && (this.parameters.getProperty("index").length() > 0)) {
/*  233 */         this.index = this.parameters.getProperty("index");
/*      */       } else {
/*  235 */         this.index = "";
/*      */       }
/*  237 */       if ((this.parameters.getProperty("desc") != null) && (this.parameters.getProperty("desc").length() > 0)) {
/*  238 */         this.desc = this.parameters.getProperty("desc");
/*      */       } else {
/*  240 */         this.desc = "";
/*      */       }
/*  242 */       oidtree = this.oid;
/*  243 */       this.userName = ((String)session.getAttribute("userName"));
/*      */       
/*      */ 
/*  246 */       out.write("\n\n\n \n<BODY MARGINHEIGHT=0 MARGINWIDTH=0 LEFTMARGIN=0 TOPMARGIN=0 >\n\n<img src=\"../images/space.png\" height=4>\n");
/*  247 */       Hashtable table = null;
/*  248 */       table = (Hashtable)_jspx_page_context.getAttribute("table", 2);
/*  249 */       if (table == null) {
/*  250 */         table = new Hashtable();
/*  251 */         _jspx_page_context.setAttribute("table", table, 2);
/*      */       }
/*  253 */       out.write(10);
/*  254 */       out.write(10);
/*  255 */       out.write(10);
/*  256 */       out.write(10);
/*      */       
/*  258 */       table.put("menuFileName", "mibmenu");
/*  259 */       request.setAttribute("HELP_URL_KEY", HELP_URL_KEY);
/*      */       
/*  261 */       out.write("\n\n<form name=\"mibForm\" action=\"/MibBrowser.do\" method=\"post\"> \n\n");
/*      */       
/*  263 */       String compiledFile = "false";
/*  264 */       String OverWrite = "false";
/*  265 */       if ((this.parameters.containsKey("CompiledFile")) && (this.parameters.getProperty("CompiledFile").equals("true")))
/*      */       {
/*  267 */         compiledFile = "true";
/*      */       }
/*  269 */       if ((this.parameters.containsKey("OverWrite")) && (this.parameters.getProperty("OverWrite").equals("true")))
/*      */       {
/*  271 */         OverWrite = "true";
/*      */       }
/*      */       
/*  274 */       if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && (this.parameters.getProperty("pressName").equals("unLoad")))
/*      */       {
/*  276 */         this.selectedMib = this.parameters.getProperty("selectedMib");
/*  277 */         this.parameters.put("unLoadMib", this.selectedMib);
/*  278 */         if ((this.selectedMib.indexOf("cmi") != -1) || (this.selectedMib.indexOf("cds") != -1) || (this.selectedMib.indexOf("txt") != -1))
/*      */         {
/*  280 */           int t = this.selectedMib.indexOf(".");
/*  281 */           String st = this.selectedMib;
/*  282 */           this.selectedMib = this.selectedMib.substring(0, t);
/*  283 */           this.parameters.put("selectedMib", this.selectedMib);
/*  284 */           this.parameters.put("unLoadMib", this.selectedMib);
/*      */         }
/*  286 */         this.resultstr = (mibDetail.unLoad(this.parameters) + "\n" + this.resultstr);
/*  287 */         this.selectedMib = mibDetail.getModuleName();
/*  288 */         this.parameters.put("selectedMib", this.selectedMib);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  294 */       this.selectedMib = this.parameters.getProperty("selectedMib");
/*      */       
/*      */ 
/*      */ 
/*  298 */       if (this.selectedMib == null)
/*      */       {
/*  300 */         this.selectedMib = ((String)request.getSession().getAttribute("selectedMib"));
/*      */       }
/*      */       
/*      */ 
/*  304 */       if ((this.selectedMib != null) && (this.selectedMib.length() > 0))
/*      */       {
/*      */ 
/*  307 */         this.prevselectedMib = this.parameters.getProperty("prevselectedMib");
/*      */         
/*  309 */         if (!this.selectedMib.equalsIgnoreCase(this.prevselectedMib))
/*      */         {
/*  311 */           if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && (this.parameters.getProperty("pressName").equals("setaddMib")))
/*      */           {
/*  313 */             this.parameters.put("nodesToOpen", "");
/*  314 */             this.syntax = "";
/*  315 */             this.access = "";
/*  316 */             this.status = "";
/*  317 */             this.index = "";
/*  318 */             this.desc = "";
/*  319 */             this.oid = "";
/*      */           }
/*  321 */           if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && (this.parameters.getProperty("pressName").equals("selectedMib")))
/*      */           {
/*  323 */             this.parameters.put("nodesToOpen", "");
/*      */             
/*  325 */             this.syntax = "";
/*  326 */             this.access = "";
/*  327 */             this.status = "";
/*  328 */             this.index = "";
/*  329 */             this.desc = "";
/*  330 */             this.oid = "";
/*      */           }
/*      */           
/*      */ 
/*  334 */           this.resultstr = (mibDetail.reloadMib(this.userName, this.selectedMib, compiledFile, OverWrite) + "\n" + this.resultstr);
/*  335 */           this.parameters.put("selectedMib", this.selectedMib);
/*  336 */           request.getSession().setAttribute("selectedMib", this.selectedMib);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  344 */         this.resultstr = (mibDetail.reloadMib(this.userName, "RFC1213-MIB", compiledFile, OverWrite) + this.resultstr);
/*  345 */         this.selectedMib = "RFC1213-MIB";
/*      */         
/*  347 */         this.parameters.put("selectedMib", "RFC1213-MIB");
/*      */       }
/*  349 */       if ((this.parameters.getProperty("pressType") != null) && (this.parameters.getProperty("pressType").length() > 0) && (this.parameters.getProperty("pressType").equals("Treelink")))
/*      */       {
/*      */ 
/*  352 */         mibnode = mibDetail.getNodeForString(this.parameters.getProperty("type"));
/*      */         
/*  354 */         if (mibnode != null)
/*      */         {
/*  356 */           this.oid = mibnode.getOIDString();
/*  357 */           oidtree = this.oid;
/*  358 */           this.status = mibnode.getStatus();
/*      */           
/*  360 */           if (this.status == null)
/*  361 */             this.status = "--";
/*  362 */           this.access = mibnode.printAccess();
/*  363 */           if (this.access == null)
/*  364 */             this.access = "--";
/*  365 */           Vector index1 = mibnode.getIndexNames();
/*  366 */           StringBuffer temp = new StringBuffer();
/*  367 */           if (index1 != null)
/*      */           {
/*  369 */             for (int i = 0; i < index1.size(); i++)
/*      */             {
/*  371 */               temp.append(String.valueOf(index1.elementAt(i)));
/*      */             }
/*      */           }
/*  374 */           this.index = temp.toString();
/*  375 */           if (this.index == null)
/*  376 */             this.index = "--";
/*  377 */           this.desc = mibnode.getDescription();
/*  378 */           if (this.desc == null)
/*  379 */             this.desc = "--";
/*  380 */           if (mibnode.getSyntax() != null) {
/*  381 */             this.syntax = mibnode.getSyntax().getDescription();
/*      */           }
/*      */           else {
/*  384 */             this.syntax = "--";
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  392 */           MibModule mm = mibDetail.getModule();
/*      */           
/*  394 */           MibTC mtc = mm.getMibTC(String.valueOf(this.parameters.getProperty("type")));
/*  395 */           if (mtc != null)
/*      */           {
/*  397 */             this.oid = mtc.getName();
/*  398 */             oidtree = this.oid;
/*  399 */             this.status = mtc.getStatus();
/*  400 */             if (this.status == null)
/*      */             {
/*  402 */               this.status = "--"; }
/*  403 */             if (mtc.getSyntax() != null) {
/*  404 */               this.syntax = mtc.getSyntax().getDescription();
/*      */             } else
/*  406 */               this.syntax = "--";
/*  407 */             this.desc = mtc.getTCDescription();
/*  408 */             if (this.desc == null)
/*  409 */               this.desc = "--";
/*  410 */             this.index = "--";
/*  411 */             this.access = "--";
/*      */           }
/*      */           else
/*      */           {
/*  415 */             MibTrap mtrap = mm.getMibTrap(String.valueOf(this.parameters.getProperty("type")));
/*  416 */             if (mtrap != null)
/*      */             {
/*  418 */               this.oid = mtrap.getName();
/*  419 */               oidtree = this.oid;
/*  420 */               this.desc = mtrap.toTagString();
/*  421 */               if (this.desc == null)
/*  422 */                 this.desc = "--";
/*  423 */               this.status = "--";
/*  424 */               this.syntax = "--";
/*  425 */               this.index = "--";
/*  426 */               this.access = "--";
/*      */ 
/*      */ 
/*      */             }
/*  430 */             else if ((this.parameters.getProperty("type").equals("TRAPS")) || (this.parameters.getProperty("type").equals("TEXTUALCONVENTIONS")))
/*      */             {
/*  432 */               this.oid = this.parameters.getProperty("type");
/*  433 */               oidtree = this.oid;
/*  434 */               this.desc = "--";
/*  435 */               this.status = "--";
/*  436 */               this.syntax = "--";
/*  437 */               this.index = "--";
/*  438 */               this.access = "--";
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  445 */       if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && ((this.parameters.getProperty("pressName").equals("Parameters")) || (this.parameters.getProperty("pressName").equals("setversion"))))
/*      */       {
/*  447 */         this.data = new Properties();
/*  448 */         this.data = mibDetail.getTargetDetail(this.userName);
/*  449 */         if (this.parameters.getProperty("pressName").equals("setversion"))
/*      */         {
/*      */ 
/*  452 */           this.data.put("SnmpVersion", this.parameters.get("SnmpVersion"));
/*      */         }
/*  454 */         String sel = " ";
/*  455 */         if (this.data != null)
/*      */         {
/*      */ 
/*  458 */           out.write("\n<p align=\"center\">\n    <TABLE BORDER=0 width=\"500\" align=\"center\" CELLPADDING=2 CELLSPACING=1 class=\"tableBorder\"> \n\t<tr>\n\t<td colspan=\"2\" class=\"header1Bg\" height=\"30\"><span class=\"header1\">");
/*  459 */           out.print(NmsUtil.GetString("Set Parameters"));
/*  460 */           out.write("</span></td>\n\t</tr>\n      <TR ID=high> <TD height=\"25\" class=\"propertyLeftBg\" width=\"200\"> <span class=\"text\"> ");
/*  461 */           out.print(NmsUtil.GetString("SnmpVersion"));
/*  462 */           out.write("</span></TD> \n\t<TD class=\"propertyBg\"><SELECT NAME=\"SnmpVersion\" class=\"formStyle\" onChange=\"Sbtbutton('setversion')\">\n\n");
/*      */           
/*  464 */           if ((this.data.getProperty("SnmpVersion") == null) || (this.data.getProperty("SnmpVersion") == "") || (this.data.getProperty("SnmpVersion").equals("Snmp_Version1")))
/*      */           {
/*  466 */             out.write("\n\n       <OPTION value=\"Snmp_Version1\" SELECTED >");
/*  467 */             out.print(NmsUtil.GetString("Snmp_Version1"));
/*  468 */             out.write("</OPTION>\n       <OPTION value=\"Snmp_Version2c\">");
/*  469 */             out.print(NmsUtil.GetString("Snmp_Version2c"));
/*  470 */             out.write("</OPTION>\n\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  476 */             out.write("\n\n      <OPTION value=\"Snmp_Version2c\" SELECTED >");
/*  477 */             out.print(NmsUtil.GetString("Snmp_Version2c"));
/*  478 */             out.write("</OPTION>\n      <OPTION value=\"Snmp_Version1\">");
/*  479 */             out.print(NmsUtil.GetString("Snmp_Version1"));
/*  480 */             out.write("</OPTION> \n\n");
/*      */           }
/*      */           
/*      */ 
/*  484 */           out.write("\n\t\n     </SELECT></TD>\n    </TR>\n\n    <TR ID=high>\n        <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\"> ");
/*  485 */           out.print(NmsUtil.GetString("SnmpPort"));
/*  486 */           out.write("</span></TD> \n\t<TD class=\"propertyBg\"><INPUT TYPE=TEXT SIZE=30 NAME=port VALUE=\"");
/*  487 */           out.print(this.data.getProperty("port"));
/*  488 */           out.write("\" class=\"formstyle\"></TD>\n    </TR>\n    <TR ID=high>\n        <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  489 */           out.print(NmsUtil.GetString("Timeout (Secs)"));
/*  490 */           out.write("</span></TD> \n\t<TD class=\"propertyBg\"><INPUT TYPE=TEXT SIZE=30 NAME=timeout VALUE=\"");
/*  491 */           out.print(this.data.getProperty("timeout"));
/*  492 */           out.write("\" class=\"formstyle\"></TD>\n    </TR> \n    <TR ID=high>\n         <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  493 */           out.print(NmsUtil.GetString("Retries"));
/*  494 */           out.write("</span></TD> \n\t <TD class=\"propertyBg\"><INPUT TYPE=TEXT SIZE=30 NAME=retries VALUE=\"");
/*  495 */           out.print(this.data.getProperty("retries"));
/*  496 */           out.write("\" class=\"formstyle\"></TD>\n    </TR> \n\n");
/*      */           
/*  498 */           if ((this.data.getProperty("SnmpVersion") == null) || (this.data.getProperty("SnmpVersion") == "") || (this.data.getProperty("SnmpVersion").equals("Snmp_Version1")))
/*      */           {
/*  500 */             out.write("\t\n    <TR ID=high>\n         <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  501 */             out.print(NmsUtil.GetString("Max-Repetetions (For GetBulk)"));
/*  502 */             out.write("</span></TD>\n\t <TD class=\"propertyBg\"><span class=\"text\">");
/*  503 */             out.print(this.data.getProperty("maxrep"));
/*  504 */             out.write("</text></TD>\n    </TR> \t\t\t\n    <TR ID=high>\n         <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  505 */             out.print(NmsUtil.GetString("Non Repeaters"));
/*  506 */             out.write("</span></TD>\n \t <TD class=\"propertyBg\"><span class=\"text\">");
/*  507 */             out.print(this.data.getProperty("nonrep"));
/*  508 */             out.write("</text></TD>\n    </TR> \n \n\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  514 */             out.write("\n\n    <TR ID=high>\n         <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  515 */             out.print(NmsUtil.GetString("Max-Repetetions (For GetBulk)"));
/*  516 */             out.write("</span></TD> \t\t\t\n         <TD class=\"propertyBg\"><INPUT TYPE=TEXT SIZE=30 NAME=maxrep VALUE=\"");
/*  517 */             out.print(this.data.getProperty("maxrep"));
/*  518 */             out.write("\" class=\"formstyle\"></TD>\n    </TR> \n    <TR ID=high>\n         <TD height=\"25\" class=\"propertyLeftBg\"> <span class=\"text\">");
/*  519 */             out.print(NmsUtil.GetString("NonRepeters"));
/*  520 */             out.write("</span></TD>\n         <TD class=\"propertyBg\"><INPUT TYPE=TEXT SIZE=30 NAME=nonrep VALUE=\"");
/*  521 */             out.print(this.data.getProperty("nonrep"));
/*  522 */             out.write("\" class=\"formstyle\"></TD>\n    </TR>\n");
/*      */           }
/*      */           
/*      */ 
/*  526 */           out.write("\n<tr>\n<td class=\"propertyLeftBg\">&nbsp;\n</td>\n<td class=\"propertyBg\"><INPUT TYPE=button NAME=\"setparameters\" VALUE=\"");
/*  527 */           out.print(NmsUtil.GetString(" OK "));
/*  528 */           out.write("\" onClick=\"Sbtbutton('setparameter')\"  class=\"button\">\n</td>\n</tr>\n\n\n</TABLE>\n</p>\n\n  <INPUT TYPE=\"hidden\" NAME=\"nodeToOpen\"  value=\"");
/*  529 */           out.print(this.parameters.getProperty("prevnodeToOpen"));
/*  530 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"nodesToOpen\"  value=\"");
/*  531 */           out.print(this.parameters.getProperty("nodesToOpen"));
/*  532 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"prevnodeToOpen\"  value=\"");
/*  533 */           out.print(this.nodeToOpen);
/*  534 */           out.write("\" > \n  <INPUT TYPE=\"hidden\" NAME=\"pressType\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"pressName\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"host\"  value=\"");
/*  535 */           out.print(this.parameters.getProperty("host"));
/*  536 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"community\"  value=\"");
/*  537 */           out.print(this.parameters.getProperty("community"));
/*  538 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"resultstr\"  value=\"");
/*  539 */           out.print(this.parameters.getProperty("resultstr"));
/*  540 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"wcommunity\"  value=\"");
/*  541 */           out.print(this.parameters.getProperty("wcommunity"));
/*  542 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"setvalue\"  value=\"");
/*  543 */           out.print(this.parameters.getProperty("setvalue"));
/*  544 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"oid\"  value=\"");
/*  545 */           out.print(this.parameters.getProperty("oid"));
/*  546 */           out.write("\">\n  <INPUT TYPE=\"hidden\" NAME=\"prevselectedMib\" value=\"");
/*  547 */           out.print(this.parameters.getProperty("selectedMib"));
/*  548 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"syntax\"  value=\"");
/*  549 */           out.print(this.parameters.getProperty("syntax"));
/*  550 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"access\"  value=\"");
/*  551 */           out.print(this.parameters.getProperty("access"));
/*  552 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"status\"  value=\"");
/*  553 */           out.print(this.parameters.getProperty("status"));
/*  554 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"index\"  value=\"");
/*  555 */           out.print(this.parameters.getProperty("index"));
/*  556 */           out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"desc\"  value=\"");
/*  557 */           out.print(this.parameters.getProperty("desc"));
/*  558 */           out.write("\"> \n</CENTER> \n</FORM> \n</BODY> \n\n");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  564 */           out.write(" \n   ");
/*  565 */           out.print(NmsUtil.GetString("Data not loaded"));
/*  566 */           out.write("</form></body> \n");
/*      */         }
/*      */         
/*      */       }
/*  570 */       else if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && (this.parameters.getProperty("pressName").equals("addmib")))
/*      */       {
/*  572 */         this.selectedMib = this.parameters.getProperty("selectedMib");
/*  573 */         if ((this.selectedMib == null) || (this.selectedMib.equals("null")) || (this.selectedMib.equals("")))
/*  574 */           this.selectedMib = "RFC1213-MIB";
/*  575 */         File mibdir = new File(NmsUtil.MIBDIR);
/*      */         
/*      */ 
/*  578 */         out.write("\n<p align=\"center\">\n\n   <TABLE BORDER=\"0\" width=\"500\" align=\"center\" CELLPADDING=\"2\" CELLSPACING=\"1\" class=\"tableBorder\">\n<tr>\n<td class=\"header1Bg\" height=\"30\" colspan=\"2\">\n\t<span class=\"header1\">");
/*  579 */         out.print(NmsUtil.GetString("Load Mib"));
/*  580 */         out.write("</span>\n</td>\n\n</tr>\n     <TR><TD class=\"propertyLeftBg\"\" ALIGN=\"center\" height=\"180\" width=\"200\">\n\t<SELECT NAME=\"selectedMib\" SIZE=10 class=\"formStyle\">\n\n");
/*      */         
/*  582 */         if (!mibdir.isDirectory())
/*      */         {
/*  584 */           out.println(java.text.MessageFormat.format(NmsUtil.GetString("{0} is not a directory"), new String[] { "" + NmsUtil.MIBDIR }));
/*      */         }
/*  586 */         String[] str = mibdir.list();
/*  587 */         for (int i = 0; i < str.length; i++)
/*      */         {
/*  589 */           if (this.selectedMib != null)
/*      */           {
/*  591 */             if (str[i].equalsIgnoreCase(this.selectedMib))
/*      */             {
/*      */ 
/*  594 */               out.write(" \n\n\t<OPTION SELECTED>");
/*  595 */               out.print(str[i]);
/*  596 */               out.write("</OPTION> \n\n");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*  602 */               out.write(" \n\n\t<OPTION>");
/*  603 */               out.print(str[i]);
/*  604 */               out.write("</OPTION> \n\n");
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  612 */             out.write(" \n\n       <OPTION SELECTED>");
/*  613 */             out.print(str[i]);
/*  614 */             out.write("</OPTION> \n\n");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  619 */         out.write(" \n\n      </SELECT></TD>\n      \n\t<td class=\"propertyBg\"><span class=\"text\">\n      \t\t<INPUT TYPE=\"CHECKBOX\" NAME =\"CompiledFile\" value=\"true\" >");
/*  620 */         out.print(NmsUtil.GetString("Load Mibs From Compiled Files"));
/*  621 */         out.write(" <br>\t\t\t                   \n      \n\t\t<INPUT TYPE=\"CHECKBOX\" NAME =\"OverWrite\" value=\"true\">");
/*  622 */         out.print(NmsUtil.GetString("OverWrite Existing Compiled Files"));
/*  623 */         out.write("</span>\n\t\t<br>\n\t\t<br>\n\t\t\n\t\t<INPUT TYPE=button NAME=setaddMib VALUE=\"");
/*  624 */         out.print(NmsUtil.GetString(" Load "));
/*  625 */         out.write("\" onClick=\"Sbtbutton('setaddMib')\"  class=\"button\"> \t\t\t\t\n\n\t<INPUT TYPE=button NAME=backaddMib VALUE=\"");
/*  626 */         out.print(NmsUtil.GetString("Cancel"));
/*  627 */         out.write("\" onClick=\"history.back()\" class=\"button\">\n\t</td>\n      </tr>\n\n </TABLE>\n</p>\t\n <BR> \t\t\n <INPUT TYPE=\"hidden\" NAME=\"nodeToOpen\"  value=\"");
/*  628 */         out.print(this.parameters.getProperty("prevnodeToOpen"));
/*  629 */         out.write("\"> \n <BR>\n <INPUT TYPE=\"hidden\" NAME=\"nodesToOpen\"  value=\"");
/*  630 */         out.print(this.parameters.getProperty("nodesToOpen"));
/*  631 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"prevnodeToOpen\"  value=\"");
/*  632 */         out.print(this.nodeToOpen);
/*  633 */         out.write("\" > \n <INPUT TYPE=\"hidden\" NAME=\"pressType\"  > \n <INPUT TYPE=\"hidden\" NAME=\"pressName\"  > \n <INPUT TYPE=\"hidden\" NAME=\"resultstr\"  value=\"");
/*  634 */         out.print(this.parameters.getProperty("resultstr"));
/*  635 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"host\"  value=\"");
/*  636 */         out.print(this.parameters.getProperty("host"));
/*  637 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"community\"  value=\"");
/*  638 */         out.print(this.parameters.getProperty("community"));
/*  639 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"wcommunity\"  value=\"");
/*  640 */         out.print(this.parameters.getProperty("wcommunity"));
/*  641 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"setvalue\"  value=\"");
/*  642 */         out.print(this.parameters.getProperty("setvalue"));
/*  643 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"oid\"  value=\"");
/*  644 */         out.print(this.parameters.getProperty("oid"));
/*  645 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"prevselectedMib\" value=\"");
/*  646 */         out.print(this.parameters.getProperty("selectedMib"));
/*  647 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"syntax\"  value=\"");
/*  648 */         out.print(this.parameters.getProperty("syntax"));
/*  649 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"access\"  value=\"");
/*  650 */         out.print(this.parameters.getProperty("access"));
/*  651 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"status\"  value=\"");
/*  652 */         out.print(this.parameters.getProperty("status"));
/*  653 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"index\"  value=\"");
/*  654 */         out.print(this.parameters.getProperty("index"));
/*  655 */         out.write("\"> \n <INPUT TYPE=\"hidden\" NAME=\"desc\"  value=\"");
/*  656 */         out.print(this.parameters.getProperty("desc"));
/*  657 */         out.write("\"> \n </CENTER>\n </FORM>\n </BODY>\n\n");
/*      */ 
/*      */ 
/*      */       }
/*  661 */       else if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button")) && (this.parameters.getProperty("pressName").equals("snmptable")))
/*      */       {
/*  663 */         String res = null;
/*  664 */         this.data = new Properties();
/*  665 */         this.data = mibDetail.snmpTable(this.parameters, this.userName);
/*  666 */         if (this.data != null)
/*      */         {
/*  668 */           if (this.data.getProperty("tablestr") != null) {
/*  669 */             res = this.data.getProperty("tablestr");
/*      */           }
/*      */         }
/*  672 */         this.data = null;
/*  673 */         if (res == null) {
/*  674 */           res = "Selected Node is not a Table Node";
/*      */         }
/*  676 */         out.write(" \n\n\n  <TABLE align=\"center\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\">\n\t\n\t<tr>\n\t\t<td height=\"30\" class=\"header2\">     ");
/*  677 */         out.print(NmsUtil.GetString("Table For"));
/*  678 */         out.write(32);
/*  679 */         out.print(this.parameters.getProperty("oid"));
/*  680 */         out.write("\n\n\t\t</td>\n\t</tr>\n    <TR>\n        <TD class=\"text\"> \n      \n\n");
/*      */         
/*  682 */         out.println(res);
/*      */         
/*  684 */         out.write("    \n\n      \n      </TD>\n    </TR>\n  </TABLE>\n\n\n  <INPUT TYPE=\"hidden\" NAME=\"nodeToOpen\"  value=\"");
/*  685 */         out.print(this.parameters.getProperty("prevnodeToOpen"));
/*  686 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"nodesToOpen\"  value=\"");
/*  687 */         out.print(this.parameters.getProperty("nodesToOpen"));
/*  688 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"prevnodeToOpen\"  value=\"");
/*  689 */         out.print(this.nodeToOpen);
/*  690 */         out.write("\" > \n  <INPUT TYPE=\"hidden\" NAME=\"pressType\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"pressName\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"resultstr\"  value=\"");
/*  691 */         out.print(this.parameters.getProperty("resultstr"));
/*  692 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"host\"  value=\"");
/*  693 */         out.print(this.parameters.getProperty("host"));
/*  694 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"community\"  value=\"");
/*  695 */         out.print(this.parameters.getProperty("community"));
/*  696 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"wcommunity\"  value=\"");
/*  697 */         out.print(this.parameters.getProperty("wcommunity"));
/*  698 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"setvalue\"  value=\"");
/*  699 */         out.print(this.parameters.getProperty("setvalue"));
/*  700 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"oid\"  value=\"");
/*  701 */         out.print(this.parameters.getProperty("oid"));
/*  702 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"selectedMib\" value=\"");
/*  703 */         out.print(this.parameters.getProperty("selectedMib"));
/*  704 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"syntax\"  value=\"");
/*  705 */         out.print(this.parameters.getProperty("syntax"));
/*  706 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"access\"  value=\"");
/*  707 */         out.print(this.parameters.getProperty("access"));
/*  708 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"status\"  value=\"");
/*  709 */         out.print(this.parameters.getProperty("status"));
/*  710 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"index\"  value=\"");
/*  711 */         out.print(this.parameters.getProperty("index"));
/*  712 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"desc\"  value=\"");
/*  713 */         out.print(this.parameters.getProperty("desc"));
/*  714 */         out.write("\"> \n</CENTER> \n</FORM> \n</BODY> \n\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  720 */         StringBuffer sbtemp = null;
/*  721 */         if ((this.parameters.containsKey("pressType")) && (this.parameters.getProperty("pressType").equals("Button"))) {
/*  722 */           this.data = new Properties();
/*      */           
/*  724 */           if (this.parameters.containsKey("pressName"))
/*      */           {
/*  726 */             if (this.parameters.getProperty("pressName").equalsIgnoreCase("clear"))
/*      */             {
/*  728 */               this.resultstr = " ";
/*  729 */               this.parameters.put("resultstr", this.resultstr);
/*      */ 
/*      */             }
/*  732 */             else if (this.parameters.getProperty("pressName").equalsIgnoreCase("setparameter"))
/*      */             {
/*  734 */               this.resultstr = mibDetail.setTargetDetail(this.userName, this.parameters);
/*      */ 
/*      */ 
/*      */             }
/*  738 */             else if (this.parameters.getProperty("pressName").equalsIgnoreCase("snmpget"))
/*      */             {
/*  740 */               this.data = mibDetail.snmpGet(this.parameters, this.userName);
/*      */ 
/*      */             }
/*  743 */             else if (this.parameters.getProperty("pressName").equalsIgnoreCase("snmpgetnext"))
/*      */             {
/*  745 */               this.data = mibDetail.snmpGetNext(this.parameters, this.userName);
/*  746 */               if ((this.data.getProperty("oid") != null) && (this.data.getProperty("oid").length() > 0))
/*  747 */                 this.oid = this.data.getProperty("oid");
/*  748 */               oidtree = this.oid;
/*      */               
/*      */ 
/*      */ 
/*  752 */               mibnode = mibDetail.getNodeForString(String.valueOf(this.data.getProperty("oid")));
/*  753 */               if (mibnode != null)
/*      */               {
/*  755 */                 String temp11 = mibnode.getNumberedOIDString();
/*      */                 
/*  757 */                 Vector childList = new Vector();
/*  758 */                 Vector nodestoopen = new Vector();
/*  759 */                 StringTokenizer st = new StringTokenizer(temp11, ".", false);
/*  760 */                 StringBuffer sb = new StringBuffer();
/*  761 */                 sbtemp = new StringBuffer(this.parameters.getProperty("nodesToOpen"));
/*  762 */                 StringTokenizer nsTO = new StringTokenizer(sbtemp.toString());
/*  763 */                 while (nsTO.hasMoreElements())
/*      */                 {
/*  765 */                   nodestoopen.addElement(nsTO.nextElement());
/*      */                 }
/*      */                 
/*  768 */                 String sbcheck = new String(this.parameters.getProperty("nodesToOpen"));
/*  769 */                 int counter = 0;
/*  770 */                 while (st.hasMoreElements())
/*      */                 {
/*  772 */                   sb.append(".");
/*      */                   
/*  774 */                   sb.append(st.nextElement());
/*      */                   
/*  776 */                   childList.addElement(sb.toString());
/*  777 */                   counter++;
/*      */                 }
/*  779 */                 int len = childList.size();
/*  780 */                 len -= 1;
/*  781 */                 childList.removeElement(childList.lastElement());
/*  782 */                 for (int i = 1; i < len; i++)
/*      */                 {
/*  784 */                   if (!nodestoopen.contains(String.valueOf(childList.elementAt(i))))
/*      */                   {
/*      */ 
/*  787 */                     sbtemp.append(" ");
/*  788 */                     sbtemp.append(String.valueOf(childList.elementAt(i)));
/*      */                   }
/*      */                 }
/*      */                 
/*  792 */                 this.parameters.put("nodesToOpen", sbtemp.toString());
/*      */                 
/*  794 */                 this.status = mibnode.getStatus();
/*  795 */                 if (this.status == null)
/*  796 */                   this.status = "--";
/*  797 */                 this.access = mibnode.printAccess();
/*  798 */                 if (this.access == null)
/*  799 */                   this.access = "--";
/*  800 */                 Vector index1 = mibnode.getIndexNames();
/*  801 */                 StringBuffer temp = new StringBuffer();
/*  802 */                 if (index1 != null)
/*      */                 {
/*  804 */                   for (int i = 0; i < index1.size(); i++)
/*      */                   {
/*  806 */                     temp.append(String.valueOf(index1.elementAt(i)));
/*      */                   }
/*      */                 }
/*  809 */                 this.index = temp.toString();
/*  810 */                 if (this.index == null)
/*  811 */                   this.index = "--";
/*  812 */                 this.desc = mibnode.getDescription();
/*      */                 
/*  814 */                 if (this.desc == null)
/*  815 */                   this.desc = "--";
/*  816 */                 if (mibnode.getSyntax() != null) {
/*  817 */                   this.syntax = mibnode.getSyntax().getDescription();
/*      */                 } else {
/*  819 */                   this.syntax = "--";
/*      */ 
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */             }
/*  829 */             else if (this.parameters.getProperty("pressName").equalsIgnoreCase("snmpgetbulk"))
/*      */             {
/*  831 */               this.data = mibDetail.snmpGetBulk(this.parameters, this.userName);
/*      */ 
/*      */ 
/*      */             }
/*  835 */             else if (this.parameters.getProperty("pressName").equalsIgnoreCase("snmpset"))
/*      */             {
/*  837 */               this.data = mibDetail.snmpSet(this.parameters, this.userName);
/*      */             }
/*      */             
/*      */ 
/*  841 */             if ((this.data != null) && (this.data.getProperty("resultstr") != null)) {
/*  842 */               this.resultstr = this.data.getProperty("resultstr");
/*      */             }
/*  844 */             if ((this.data != null) && (this.data.getProperty("oid") != null) && (this.data.getProperty("oid").length() > 0))
/*      */             {
/*  846 */               this.oid = this.data.getProperty("oid");
/*  847 */               oidtree = this.oid;
/*      */             }
/*      */             
/*      */ 
/*  851 */             this.data = null;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  856 */         this.treeState = new Vector();
/*  857 */         if (this.parameters.containsKey("treeStyle"))
/*  858 */           treeStyle = this.parameters.getProperty("treeStyle");
/*  859 */         if (treeStyle == null)
/*      */         {
/*      */ 
/*  862 */           treeStyle = "win";
/*      */         }
/*  864 */         this.nodeToOpen = this.parameters.getProperty("nodeToOpen");
/*  865 */         this.nodesToOpen = this.parameters.getProperty("nodesToOpen");
/*  866 */         if ((this.nodesToOpen == null) || (this.nodesToOpen.equals("null")))
/*  867 */           this.nodesToOpen = "";
/*  868 */         if ((this.nodeToOpen == null) || (this.nodeToOpen.equals("null")))
/*  869 */           this.nodeToOpen = "";
/*  870 */         this.treeState = treeInialize();
/*  871 */         MibNodeConverter mibNodeConverter = new MibNodeConverter();
/*      */         
/*  873 */         Properties treeImages = new Properties();
/*      */         
/*  875 */         treeImages.put(MibNodeConverter.DOT_IMAGE, "/webclient/common/images/mib_directory.gif");
/*  876 */         treeImages.put(MibNodeConverter.BACK_IMAGE, "/webclient/common/images/goback.png");
/*      */         
/*  878 */         treeImages.put(MibNodeConverter.FILE_IMAGE, "/webclient/common/images/mib_tree.gif");
/*  879 */         treeImages.put(MibNodeConverter.FOLDER_OPENED_IMAGE, "/webclient/common/images/mib_folder.gif");
/*  880 */         treeImages.put(MibNodeConverter.FOLDER_CLOSED_IMAGE, "/webclient/common/images/mib_cfolder.gif");
/*  881 */         treeImages.put(MibNodeConverter.EXT_INDEX_IMAGE, "/webclient/common/images/mib_extkey.gif");
/*  882 */         treeImages.put(MibNodeConverter.INDEX_IMAGE, "/webclient/common/images/mib_key.gif");
/*  883 */         treeImages.put(MibNodeConverter.LEAF_IMAGE, "/webclient/common/images/mib_node.gif");
/*  884 */         treeImages.put(MibNodeConverter.RESTRICTED_IMAGE, "/webclient/common/images/mib_Nonode.gif");
/*  885 */         treeImages.put(MibNodeConverter.TABLE_IMAGE, "/webclient/common/images/mib_table.gif");
/*  886 */         treeImages.put(MibNodeConverter.ENTRY_IMAGE, "/webclient/common/images/mib_entry.gif");
/*  887 */         treeImages.put(MibNodeConverter.TRAP_LIST_IMAGE, "/webclient/common/images/mib_trap.gif");
/*  888 */         treeImages.put(MibNodeConverter.TC_LIST_IMAGE, "/webclient/common/images/mib_textcon.gif");
/*      */         
/*  890 */         mibNodeConverter.setTreeImages(treeImages);
/*      */         
/*  892 */         XmlDocument xmldocument = null;
/*      */         
/*      */ 
/*  895 */         String root1 = null;
/*      */         MibModule Module;
/*      */         try
/*      */         {
/*  899 */           Module = mibDetail.getModule();
/*      */           
/*      */ 
/*  902 */           mn = mibDetail.getNode();
/*      */         }
/*      */         catch (Exception e) {
/*      */           MibNode mn;
/*  906 */           out.println(NmsUtil.GetString("<CENTER><B>Tree view cannot be constructed</B>"));
/*  907 */           out.println("<HR>");
/*  908 */           out.println(NmsUtil.GetString("Exception occured while getting MibNode from MibBean. </CENTER>"));
/*  909 */           com.adventnet.nms.util.NmsLogMgr.MISCERR.fail(NmsUtil.GetString("Exception caught in MibTreeDetail "), e); return;
/*      */         }
/*      */         
/*      */ 
/*  913 */         this.data = new Properties();
/*  914 */         this.targetVersion = "false";
/*  915 */         this.data = mibDetail.getTargetDetail(this.userName);
/*  916 */         if ((this.data.getProperty("SnmpVersion") != null) && (this.data.getProperty("SnmpVersion") != "") && (this.data.getProperty("SnmpVersion").equals("Snmp_Version2c"))) {
/*  917 */           this.targetVersion = "true";
/*      */         }
/*  919 */         this.isTable = mibDetail.isTableString(String.valueOf(this.oid));
/*      */         
/*      */ 
/*      */ 
/*  923 */         out.write(" \n\n  <INPUT TYPE=\"hidden\" NAME=\"treeStyle\" value=\"");
/*  924 */         out.print(treeStyle);
/*  925 */         out.write("\" > \n  <INPUT TYPE=\"hidden\" NAME=\"pressType\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"pressName\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"nodeToOpen\"  > \n  <INPUT TYPE=\"hidden\" NAME=\"prevnodeToOpen\"  value=\"");
/*  926 */         out.print(this.nodeToOpen);
/*  927 */         out.write("\" > \n  <INPUT TYPE=\"hidden\" NAME=\"prevselectedMib\" value=\"");
/*  928 */         out.print(this.selectedMib);
/*  929 */         out.write("\"> \n  <INPUT TYPE=\"HIDDEN\" NAME=\"unLoadMib\" value=\"");
/*  930 */         out.print(this.selectedMib);
/*  931 */         out.write("\"> \n  <INPUT TYPE=\"HIDDEN\" NAME=\"desc\" value=\"");
/*  932 */         out.print(this.desc);
/*  933 */         out.write("\"> \n  <INPUT TYPE=\"hidden\" NAME=\"type\" > \n  <INPUT TYPE=\"hidden\" NAME=\"displayName\" > \n<CENTER> \n\n<TABLE BORDER=\"0\" align=\"center\" WIDTH=\"98%\" class=\"tableBorder\">\n<tr>\n<td colspan=2 class=\"header1Bg\" height=\"30\"><span class=\"header1\">");
/*  934 */         out.print(NmsUtil.GetString("Mib Manager"));
/*  935 */         out.write("</span></td>\n</tr>\n\n  <TR>\n    <TD colspan=2> \n\n      <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0  WIDTH=100%> \n\t<TR height=\"25\"> \n\n \t    <TD ALIGN=left>\n\t\t\n\t\t<input type=\"button\" value=\"");
/*  936 */         out.print(NmsUtil.GetString("Load MIB"));
/*  937 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('addmib')\">\n\n\t\t<input type=\"button\" value=\"");
/*  938 */         out.print(NmsUtil.GetString("Unload MIB"));
/*  939 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('unLoad')\">\n\t\t\n\t\t<input type=\"button\" value=\"");
/*  940 */         out.print(NmsUtil.GetString("Parameters"));
/*  941 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('Parameters')\">\t\t\n\t\t\n\t\t<input type=\"button\" value=\"");
/*  942 */         out.print(NmsUtil.GetString(" Get "));
/*  943 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('snmpget')\">\n\t\t\n\t\t<input type=\"button\" value=\"");
/*  944 */         out.print(NmsUtil.GetString("Get Next"));
/*  945 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('snmpgetnext')\">\t\n\n");
/*  946 */         if (this.targetVersion.equals("true")) {
/*  947 */           out.write("\n\t\t\n\t\t<input type=\"button\" value=\"");
/*  948 */           out.print(NmsUtil.GetString("Get Bulk"));
/*  949 */           out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('snmpgetbulk')\">\t\t\n");
/*      */         } else {
/*  951 */           out.write("\n\t\t\n\t\t<input type=\"button\" disabled=\"true\" value=\"");
/*  952 */           out.print(NmsUtil.GetString("Get Bulk"));
/*  953 */           out.write("\" class=\"buttonDisabled\">\t\t\n");
/*      */         }
/*  955 */         out.write("\n\n\t\t<input type=\"button\" value=\"");
/*  956 */         out.print(NmsUtil.GetString(" Set "));
/*  957 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('snmpset')\">\t\n\n");
/*  958 */         if (this.isTable.equals("true")) {
/*  959 */           out.write("\n\n\t\t<input type=\"button\" value=\"");
/*  960 */           out.print(NmsUtil.GetString("Table"));
/*  961 */           out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('snmptable')\">\n\t\t\n");
/*      */         } else {
/*  963 */           out.write("\n\n\t\t<input type=\"button\" disabled=\"true\" value=\"");
/*  964 */           out.print(NmsUtil.GetString("Table"));
/*  965 */           out.write("\" class=\"buttonDisabled\">\n\n");
/*      */         }
/*  967 */         out.write("\t\t\n\n\t\t<input type=\"button\" value=\"");
/*  968 */         out.print(NmsUtil.GetString("Clear"));
/*  969 */         out.write("\" class=\"button\" onClick=\"javascript:Sbtbutton('clear')\">\n\n          </TR>\n        </TABLE>\n      </TD> \n   </TR>\n \n   <TR>\n      <TD COLSPAN=2> \n\t<TABLE BORDER=\"0\" WIDTH=\"100%\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#EEECEC\"> \n          <TR> \n\t     <TD width=\"30%\" ALIGN=\"LEFT\" VALIGN=\"TOP\" bgcolor=\"#FFFFFF\"> \n\n");
/*      */         
/*  971 */         out.println("<table  border=0 cellpadding=0 cellspacing=0 class=\"text\" >");
/*  972 */         out.println("<tr> <td nowrap><IMG BORDER=0 HEIGHT=20 align=TEXTTOP SRC=\"../images/origin.jpg\" >&nbsp;" + Module.getName() + "</td></tr>");
/*      */         
/*  974 */         String vline = "<IMG BORDER=0 WIDTH=20 HEIGHT=20 align=TEXTTOP SRC=\"../images/vline.png\" >";
/*  975 */         out.println("</table>");
/*  976 */         xmldocument = mibNodeConverter.formElement(Module, this.nodeToOpen, this.treeState);
/*      */         
/*  978 */         Element rootnode = xmldocument.getDocumentElement();
/*  979 */         NodeList children = rootnode.getChildNodes();
/*      */         
/*      */ 
/*  982 */         ElementTree elementTree = new ElementTree(treeStyle);
/*  983 */         this.getLoadedMib = new Hashtable();
/*  984 */         String sessionId = session.getId();
/*  985 */         this.treeState = new Vector();
/*  986 */         this.treeState = treeInialize();
/*      */         
/*      */ 
/*  989 */         out.write(" \n\n   <TABLE  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  cols=\"1\" class=\"text\"> \n\t\t\n");
/*      */         
/*      */ 
/*  992 */         if ((oidtree == null) || (oidtree.equals("null")) || (oidtree.equals("")) || (oidtree.length() < 0))
/*      */         {
/*  994 */           elementTree.exploreNode(rootnode, "", out, this.nodeToOpen, this.treeState, root1);
/*      */         }
/*      */         else
/*      */         {
/*  998 */           mibnode = mibDetail.getNodeForString(oidtree);
/*  999 */           if (mibnode != null)
/*      */           {
/* 1001 */             this.root = mibnode.getNumberedOIDString();
/*      */           }
/*      */           else
/*      */           {
/* 1005 */             this.root = oidtree;
/*      */           }
/* 1007 */           elementTree.exploreNode(rootnode, this.space, out, this.nodeToOpen, this.treeState, this.root);
/*      */         }
/*      */         
/* 1010 */         out.println("</table>");
/* 1011 */         this.nodesToOpen = constNodesToOpen();
/*      */         
/* 1013 */         this.treeState = null;
/* 1014 */         this.nodeList = null;
/*      */         
/*      */ 
/* 1017 */         out.write(" \n\n  <INPUT TYPE=\"hidden\" NAME=\"nodesToOpen\" value=\"");
/* 1018 */         out.print(this.nodesToOpen);
/* 1019 */         out.write("\" > \n  </TD> \n  <TD ALIGN=\"center\" VALIGN=\"TOP\" class=\"propertyBg\"> \n    <TABLE BORDER=\"0\" WIDTH=\"98%\" align=\"right\"> \n        <TR> \n \t <TD> \n\t   <TABLE BORDER=\"0\" WIDTH=\"100%\" CELLPADDING=\"1\"> \n\t      <TR> \n\n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n                   ");
/* 1020 */         out.print(NmsUtil.GetString("Host"));
/* 1021 */         out.write("\n                </TD> \n\n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP>\n                   <INPUT TYPE=\"TEXT\" SIZE=\"20\" NAME=\"host\" VALUE=\"");
/* 1022 */         out.print(this.host);
/* 1023 */         out.write("\" class=\"formstyle\">\n                </TD> \n\n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n                    ");
/* 1024 */         out.print(NmsUtil.GetString("Community"));
/* 1025 */         out.write("\n                </TD> \n\n\t\t<TD ALIGN=\"RIGHT\" VALIGN=\"TOP\" NOWRAP>\n                   <INPUT TYPE=\"PASSWORD\" SIZE=\"20\" NAME=\"community\" VALUE=\"");
/* 1026 */         out.print(this.community);
/* 1027 */         out.write("\" class=\"formstyle\">\n                </TD> \n \t     </TR> \n\n    \t     <TR> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n                   ");
/* 1028 */         out.print(NmsUtil.GetString("Set Value"));
/* 1029 */         out.write("\n                </TD> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP>\n                   <INPUT TYPE=\"TEXT\" SIZE=\"20\" NAME=\"setvalue\" VALUE=\"");
/* 1030 */         out.print(this.setvalue);
/* 1031 */         out.write("\" class=\"formstyle\">\n                </TD> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n                   ");
/* 1032 */         out.print(NmsUtil.GetString("Write Community"));
/* 1033 */         out.write("\n                </TD> \n\t\t<TD ALIGN=\"RIGHT\" VALIGN=\"TOP\" NOWRAP>\n                   <INPUT TYPE=\"TEXT\" SIZE=\"20\" NAME=\"wcommunity\" VALUE=\"");
/* 1034 */         out.print(this.wcommunity);
/* 1035 */         out.write("\" class=\"formstyle\">\n                </TD> \n\t     </TR> \n\n\t     <TR> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n                   ");
/* 1036 */         out.print(NmsUtil.GetString("Object ID"));
/* 1037 */         out.write("\n                </TD> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" COLSPAN=\"3\" NOWRAP>\n                   <INPUT TYPE=\"TEXT\" SIZE=\"50\" NAME=\"oid\" VALUE=\"");
/* 1038 */         out.print(this.oid);
/* 1039 */         out.write("\" class=\"formstyle\" style=width:450>\n                </TD> \n\t     </TR> \n\t     \n\t     <TR> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" NOWRAP class=\"text\">\n\t\t");
/* 1040 */         out.print(NmsUtil.GetString("Loaded Mibs"));
/* 1041 */         out.write("                </TD> \n\t\t<TD ALIGN=\"LEFT\" VALIGN=\"TOP\" COLSPAN=\"3\" NOWRAP>\n                   <SELECT NAME=\"selectedMib\" class=\"formStyle\"> \n");
/*      */         
/*      */ 
/* 1044 */         this.getLoadedMib = new Hashtable();
/* 1045 */         this.getLoadedMib = mibDetail.getLoadedMib();
/* 1046 */         this.selectedMib = this.parameters.getProperty("selectedMib");
/*      */         
/* 1048 */         if ((this.selectedMib == null) || (this.selectedMib.equals("null")) || (this.selectedMib.equals("")))
/*      */         {
/* 1050 */           this.selectedMib = "RFC1213-MIB";
/*      */         }
/* 1052 */         String value = " ";
/*      */         
/* 1054 */         if ((this.selectedMib.indexOf("cmi") != -1) || (this.selectedMib.indexOf("cds") != -1) || (this.selectedMib.indexOf("txt") != -1))
/*      */         {
/* 1056 */           int t = this.selectedMib.indexOf(".");
/* 1057 */           String st = this.selectedMib;
/*      */           
/* 1059 */           this.selectedMib = this.selectedMib.substring(0, t);
/* 1060 */           value = "VALUE=\"" + st + "\"";
/*      */         }
/*      */         
/* 1063 */         out.println("<br>");
/* 1064 */         out.println(NmsUtil.GetString("selectedmib.......................") + this.selectedMib);
/* 1065 */         out.println(NmsUtil.GetString(" Loaded Mib...") + this.getLoadedMib);
/*      */         Enumeration loadedMib;
/* 1067 */         if (this.getLoadedMib != null)
/*      */         {
/* 1069 */           for (loadedMib = this.getLoadedMib.elements(); loadedMib.hasMoreElements();)
/*      */           {
/* 1071 */             String element = String.valueOf(loadedMib.nextElement());
/* 1072 */             if (this.selectedMib != null)
/*      */             {
/* 1074 */               if (element.equalsIgnoreCase(this.selectedMib))
/*      */               {
/*      */ 
/*      */ 
/* 1078 */                 out.write(" \n\t\t   <OPTION ");
/* 1079 */                 out.print(value);
/* 1080 */                 out.write(" SELECTED>");
/* 1081 */                 out.print(element);
/* 1082 */                 out.write("</OPTION> \n                ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/*      */ 
/* 1088 */                 out.write(" \n\t\t   <OPTION  >");
/* 1089 */                 out.print(element);
/* 1090 */                 out.write("</OPTION> \n                 ");
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 1097 */               out.write("\n\t\t<OPTION  SELECTED>");
/* 1098 */               out.print(element);
/* 1099 */               out.write("</OPTION> \n            ");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1107 */           out.write("\n\t<OPTION> ");
/* 1108 */           out.print(NmsUtil.GetString("No Mib Selected"));
/* 1109 */           out.write("</OPTION>\n   ");
/*      */         }
/*      */         
/*      */ 
/* 1113 */         out.write(" \n\n\t\t</SELECT>\n\t\t              <INPUT TYPE=\"BUTTON\" NAME=\"browse\" VALUE=\"");
/* 1114 */         out.print(NmsUtil.GetString("select"));
/* 1115 */         out.write("\" onClick=Sbtbutton('selectedMib') class=\"button\">\n                </TD> \n\t     </TR>\n\t     <tr>\n\t     <td>\n\t     </td>\n\t     <td colspan=\"3\">\n\t     ");
/*      */         
/* 1117 */         if ((this.resultstr == null) || (this.resultstr.equals("null")) || (this.resultstr.equals(""))) {
/* 1118 */           this.resultstr = "";
/*      */         }
/* 1120 */         out.write(" \n\n\t             <TEXTAREA NAME=\"resultstr\" ROWS=\"12\" COLS=\"60\" class=\"formstyleMib\">");
/* 1121 */         out.print(this.resultstr);
/* 1122 */         out.write(" </TEXTAREA>\n\n\t     </td>\n\t     </tr>\n\t     <tr>\n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP class=\"text\">\n\t                        ");
/* 1123 */         out.print(NmsUtil.GetString("Syntax"));
/* 1124 */         out.write("\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP>\n\t                        <INPUT TYPE=\"TEXT\" SIZE=\"15\" NAME=\"syntax\" VALUE=\"");
/* 1125 */         out.print(this.syntax);
/* 1126 */         out.write("\" class=\"formstyle\">\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP class=\"text\">\n\t                        ");
/* 1127 */         out.print(NmsUtil.GetString("Status"));
/* 1128 */         out.write("\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP >\n\t                        <INPUT TYPE=\"TEXT\" SIZE=\"15\" NAME=\"status\" VALUE=\"");
/* 1129 */         out.print(this.status);
/* 1130 */         out.write("\" class=\"formstyle\">\n                </TD>\n\t     </tr>\n\t     <tr>\n\t     <TD ALIGN=\"LEFT\" NOWRAP WIDTH=\"10%\" class=\"text\">\n\t                        ");
/* 1131 */         out.print(NmsUtil.GetString("Access"));
/* 1132 */         out.write("\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP WIDTH=\"30%\">\n\t                        <INPUT TYPE=\"TEXT\" SIZE=\"15\" NAME=\"access\" VALUE=\"");
/* 1133 */         out.print(this.access);
/* 1134 */         out.write("\" class=\"formstyle\">\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP WIDTH=\"10%\" class=\"text\">\n\t                        ");
/* 1135 */         out.print(NmsUtil.GetString("Index"));
/* 1136 */         out.write("\n\t                     </TD> \n\t     \n\t     \t\t<TD ALIGN=\"LEFT\" NOWRAP WIDTH=\"20%\">\n\t                        <INPUT TYPE=\"TEXT\" SIZE=\"15\" NAME=\"index\" VALUE=\"");
/* 1137 */         out.print(this.index);
/* 1138 */         out.write("\" class=\"formstyle\">\n                </TD>\n\t     \n\t     </tr>\n\t     <tr>\n\t     <td colspan=\"4\" class=\"text\">");
/* 1139 */         out.print(NmsUtil.GetString("Mib Node Description:"));
/* 1140 */         out.write("\n\t     </td>\n\t     </tr>\n\t     <tr>\n\t     <td colspan=\"4\" class=\"text\">");
/* 1141 */         out.print(this.desc);
/* 1142 */         out.write("\n\t     </td>\n\t     </tr>\n\t     \n\t     \n\t </TABLE> \n\n\n</TD> \n</TR> \n</TABLE> \n</TD> \n</TR> \n</TABLE> \n</TD> \n</TR> \n</TABLE> \n</FORM> \n</body> \n\n");
/*      */       }
/*      */       
/*      */ 
/* 1146 */       out.write(32);
/* 1147 */       out.write(10);
/* 1148 */       out.write(32);
/* 1149 */       out.write(10);
/* 1150 */       out.write(32);
/* 1151 */       out.write(10);
/* 1152 */       out.write("\n</html> \n");
/*      */     } catch (Throwable t) {
/* 1154 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1155 */         out = _jspx_out;
/* 1156 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1157 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1158 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1161 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\MibBrowser_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */