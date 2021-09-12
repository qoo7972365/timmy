/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class wtatrace_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*      */ {
/*   25 */   String tableheaderfg = "#ffffff";
/*   26 */   String tableheaderbg = "#778899";
/*   27 */   String[] headernames = { "Method Name", "Type", "Status", "Time(ms)", "SQL" };
/*   28 */   String[] methnames = { "getMethodIdentifier", "getType", "getStatus", "getExclusiveTime", "getException", "getProperties" };
/*   29 */   int[] widths = { 50, 10, 10, 10, 20 };
/*   30 */   String firstrowbg = "#ffffff";
/*   31 */   String secondrowbg = "#f2f2f2";
/*   32 */   String errorrowbg = "#fff4f4";
/*   33 */   String resourceid; String tid; String view = null;
/*      */   
/*      */ 
/*      */   public void drawTable(HashMap stats, HashMap commonProp, HashMap traceinfo, JspWriter out, int level, String parentid, String compType, long maxTime, boolean isLast)
/*      */     throws Exception
/*      */   {
/*   39 */     if ((compType.equals("all")) || (compType.equals(stats.get("type"))) || ((compType.equals("Servlet")) && (stats.get("type").equals("JSP"))) || ((compType.equals("Java")) && (stats.get("type").equals("Struts"))))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*   44 */       drawRow(stats, commonProp, out, level, parentid, false, compType, maxTime, isLast);
/*      */     }
/*      */     
/*   47 */     ArrayList children = (ArrayList)stats.get("children");
/*   48 */     if (children.size() == 0) {
/*   49 */       return;
/*      */     }
/*      */     
/*   52 */     int y = level + 1;
/*      */     
/*   54 */     int j = 0; for (int size = children.size(); j < size; j++) {
/*   55 */       String cid = (String)children.get(j);
/*   56 */       HashMap child = (HashMap)traceinfo.get(cid);
/*      */       boolean last;
/*   58 */       boolean last; if (j < size - 1) {
/*   59 */         last = false;
/*      */       }
/*      */       else {
/*   62 */         last = true;
/*      */       }
/*   64 */       drawTable(child, commonProp, traceinfo, out, y, parentid + "-" + (j + 1), compType, maxTime, last);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawRow(HashMap ts, HashMap commonProp, JspWriter out, int level, String id, boolean hasLinks, String compType, long maxTime, boolean isLast)
/*      */     throws Exception
/*      */   {
/*   74 */     int status = Integer.parseInt((String)ts.get("status"));
/*   75 */     if (status == 2) {
/*   76 */       if (!id.equals("-1")) {
/*   77 */         out.print("<tr bgcolor=\"" + this.errorrowbg + "\" id=\"" + id + "\" ");
/*      */       }
/*      */     }
/*   80 */     else if (!id.equals("-1")) {
/*   81 */       out.print("<tr id=\"" + id + "\" ");
/*      */     }
/*      */     
/*   84 */     ArrayList children = (ArrayList)ts.get("children");
/*   85 */     boolean isLeaf = children.size() == 0;
/*   86 */     if (isLeaf)
/*      */     {
/*   88 */       out.println("isLeaf=\"true\">");
/*      */     }
/*      */     else {
/*   91 */       out.println("isLeaf=\"false\">");
/*      */     }
/*      */     
/*      */ 
/*   95 */     int colspan = 1;
/*   96 */     if (compType.equals("all")) { colspan = 2;
/*      */     }
/*   98 */     int padding = 0;
/*   99 */     if (compType.equals("all")) {
/*  100 */       padding = level * 24;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  105 */     String tdclass = "class=\"leftlinkstd\" ";
/*  106 */     if (compType.equals("all")) {
/*  107 */       tdclass = "";
/*      */     }
/*  109 */     if (!compType.equals("all"))
/*      */     {
/*  111 */       out.println("<td " + tdclass + " width=\"1%\"><img src=\"/images/icon_backtotreeview.gif\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"invoke('" + this.resourceid + "','" + this.tid + "','all',0,'" + id + "','" + this.view + "')\"></td>");
/*  112 */       out.println("<td " + tdclass + " colspan=" + colspan + " nowrap height=\"10\">");
/*      */     }
/*      */     else {
/*  115 */       out.println("<td style=\"padding-left:" + padding + "px\" colspan=" + colspan + " nowrap height=\"10\">");
/*      */     }
/*  117 */     out.println("<a name=\"" + id + "\"></a>");
/*      */     
/*  119 */     if ((!isLeaf) || (level == 0))
/*      */     {
/*  121 */       if (compType.equals("all"))
/*      */       {
/*  123 */         if (level != 0)
/*      */         {
/*      */ 
/*  126 */           out.print("<img align=\"middle\" src=\"/images/trend.png\" border=\"0\" >");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  132 */         out.print("<a onmouseover=\"this.style.cursor='pointer'\" onclick=\"toggleRows(this)\">");
/*  133 */         out.print("<img align=\"middle\" id=\"img-" + id + "\" src=\"/images/trminus.png\" border=\"0\">");
/*  134 */         out.print("</a>");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  139 */     else if ((level != 0) && (compType.equals("all")))
/*      */     {
/*  141 */       if (isLast) {
/*  142 */         out.print("<img align=\"middle\" src=\"/images/trend.png\" border=\"0\" >");
/*      */       }
/*      */       else {
/*  145 */         out.print("<img align=\"middle\" src=\"/images/trcont.png\" border=\"0\" >");
/*      */       }
/*  147 */       out.print("<img align=\"middle\" src=\"/images/spacer1.gif\" border=\"0\">");
/*      */     }
/*      */     
/*  150 */     if (compType.equals("all")) {
/*  151 */       String img = getImageForType((String)ts.get("type"));
/*  152 */       out.println("<img align=\"middle\" src=\"" + img + "\" border=\"0\">");
/*      */     }
/*  154 */     out.print("<span class=\"bodytext\">");
/*  155 */     if (!compType.equals("JDBC"))
/*      */     {
/*  157 */       if (compType.equals("all"))
/*      */       {
/*      */ 
/*  160 */         if (maxTime != 0L) {
/*  161 */           long inctime = Long.parseLong((String)ts.get("inclusiveTime"));
/*  162 */           long per = inctime * 100L / maxTime;
/*  163 */           if (per > 50L) {
/*  164 */             out.println("<div style=\"display:inline;color:red\">");
/*      */           }
/*      */           else {
/*  167 */             out.println("<div style=\"display:inline;color:green\">");
/*      */           }
/*  169 */           out.println(per + "%&nbsp;");
/*      */         }
/*      */         else {
/*  172 */           out.println("<div style=\"display:inline;color:green\">");
/*  173 */           out.println("100%&nbsp;");
/*      */         }
/*  175 */         out.println("</div>");
/*      */       }
/*  177 */       out.println(getMethodName(commonProp, ts, level, compType));
/*      */     }
/*      */     else {
/*  180 */       String args = getArgRepresentation(ts);
/*  181 */       if (args == null) {
/*  182 */         out.println(getMethodName(commonProp, ts, level, compType));
/*      */       }
/*      */       else {
/*  185 */         out.println(addBreakAt(args, 100));
/*      */       }
/*      */     }
/*  188 */     out.print("</span>");
/*  189 */     out.println("</td>");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  195 */     out.println("<td " + tdclass + "height=\"10\">");
/*  196 */     out.print("<span class=\"bodytext\">");
/*  197 */     out.print(ts.get("type"));
/*  198 */     out.println("</span>");
/*  199 */     out.println("</td>");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  204 */     String statusStr = com.adventnet.appmanager.wta.agent.TransactionStats.STATUS_STRING[status];
/*  205 */     statusStr = FormatUtil.getString(statusStr);
/*  206 */     out.println("<td " + tdclass + "height=\"10\">");
/*  207 */     out.print("<span class=\"bodytext\">");
/*  208 */     out.print(statusStr);
/*  209 */     out.println("</span>");
/*  210 */     if (statusStr.equals("ERROR"))
/*      */     {
/*  212 */       String exception = (String)ts.get("exception");
/*      */       
/*  214 */       exception = StrUtil.findReplace(exception, "'", "\\'");
/*      */       
/*  216 */       exception = StrUtil.findReplace(exception, "\"", "&quot;");
/*      */       
/*  218 */       exception = StrUtil.findReplace(exception, "\n", "<br>");
/*  219 */       out.println("<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/cross.gif\" onclick=\"ddrivetip(this,event,'" + exception + "')\">");
/*      */     }
/*  221 */     out.println("</td>");
/*      */     
/*      */ 
/*      */ 
/*  225 */     out.println("<td " + tdclass + "height=\"10\">");
/*  226 */     out.print("<span class=\"bodytext\">");
/*  227 */     out.print(ts.get("exclusiveTime"));
/*  228 */     out.println("</span>");
/*  229 */     out.println("</td>");
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
/*  243 */     if (compType.equals("all")) {
/*  244 */       out.println("<td height=\"10\" nowrap>");
/*  245 */       out.print("<span class=\"bodytext\">");
/*  246 */       String args = getArgRepresentation(ts);
/*  247 */       if (args == null) {
/*  248 */         out.println("&nbsp;");
/*  249 */         out.println("</span>");
/*      */       }
/*      */       else
/*      */       {
/*  253 */         String toshow = "";
/*  254 */         if (args.length() > 30) {
/*  255 */           toshow = args.substring(0, 30);
/*  256 */           out.print(toshow);
/*      */         }
/*      */         else {
/*  259 */           toshow = args;
/*  260 */           out.println(args);
/*      */         }
/*  262 */         out.println("</span>");
/*  263 */         if (!toshow.equals(args))
/*      */         {
/*      */ 
/*  266 */           String res = addBreakAt(args, 50);
/*  267 */           res = StrUtil.findReplace(res, "'", "\\'");
/*  268 */           res = StrUtil.findReplace(res, "\"", "&quot;");
/*      */           
/*  270 */           out.println("<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_more.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>" + res + "</span>')\"");
/*      */         }
/*      */       }
/*      */       
/*  274 */       out.println("</td>");
/*      */     }
/*      */     
/*      */ 
/*  278 */     out.println("</tr>");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getImageForType(String type)
/*      */   {
/*  284 */     String toret = "/images/mib_node.gif";
/*  285 */     if (type.equals("EJB")) {
/*  286 */       toret = "/images/icon_ejb_wta.gif";
/*      */     }
/*  288 */     else if ((type.equals("Servlet")) || (type.equals("JSP"))) {
/*  289 */       toret = "/images/icon_servlet_wta.gif";
/*      */     }
/*  291 */     else if (type.equals("JDBC")) {
/*  292 */       toret = "/images/icon_jdbc_wta.gif";
/*      */     }
/*  294 */     else if ((type.equals("Java")) || (type.equals("Struts"))) {
/*  295 */       toret = "/images/icon_note_added.gif";
/*      */     }
/*  297 */     return toret;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getArgRepresentation(HashMap ts)
/*      */   {
/*  303 */     ArrayList args = (ArrayList)ts.get("args");
/*  304 */     if (args != null) {
/*  305 */       StringBuffer argval = new StringBuffer();
/*  306 */       int i = 0; for (int size = args.size(); i < size; i++)
/*      */       {
/*  308 */         HashMap prop = (HashMap)args.get(i);
/*  309 */         String value = (String)prop.get("value");
/*  310 */         if (value != null) {
/*  311 */           argval.append(value);
/*  312 */           argval.append(",");
/*      */         }
/*      */       }
/*  315 */       String argStr = argval.toString();
/*  316 */       if (argStr.endsWith(",")) {
/*  317 */         argStr = argStr.substring(0, argStr.lastIndexOf(","));
/*      */         
/*  319 */         return argStr;
/*      */       }
/*      */     }
/*  322 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getMethodName(HashMap comProp, HashMap ts, int level, String compType)
/*      */   {
/*  328 */     String uri = (String)comProp.get("url");
/*  329 */     String type = (String)ts.get("type");
/*  330 */     if (((type.equals("JSP")) || (type.equals("Servlet"))) && 
/*  331 */       (level == 0) && (compType.equals("all"))) {
/*  332 */       return uri;
/*      */     }
/*      */     
/*      */ 
/*  336 */     String packageName = (String)ts.get("package");
/*  337 */     String className = (String)ts.get("class");
/*  338 */     String methName = (String)ts.get("method");
/*      */     
/*  340 */     return className + "." + methName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String addBreakAt(String str, int len)
/*      */   {
/*  347 */     if (len == 0) return str;
/*  348 */     String temp = str;
/*  349 */     StringBuffer ret = new StringBuffer("");
/*      */     
/*  351 */     while (temp.length() > len)
/*      */     {
/*  353 */       ret.append(temp.substring(0, len));
/*  354 */       ret.append("<br>");
/*  355 */       temp = temp.substring(len);
/*      */     }
/*  357 */     ret.append(temp);
/*      */     
/*  359 */     return ret.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   private String getTabClass(int tab, int index)
/*      */   {
/*  365 */     if (tab == index)
/*      */     {
/*  367 */       return "tabselected";
/*      */     }
/*      */     
/*      */ 
/*  371 */     return "tabnormal";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getBeginImage(int tabtoselect, boolean showintrotab)
/*      */   {
/*  378 */     if (((!showintrotab) && (tabtoselect == 0)) || ((showintrotab) && (tabtoselect == 5))) {
/*  379 */       return "mnu_hightlight_begin.gif";
/*      */     }
/*  381 */     return "mnu_normal_begin.gif";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getTDBackGroud(int tabtoselect, int currentTab)
/*      */   {
/*  388 */     if (currentTab == tabtoselect)
/*      */     {
/*  390 */       return "bg_mnu_highlight.gif";
/*      */     }
/*      */     
/*      */ 
/*  394 */     return "bg_mnu_normal.gif";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getTabImage(int tabtoselect, int currentTab)
/*      */   {
/*  402 */     int dif = currentTab - tabtoselect;
/*  403 */     switch (dif)
/*      */     {
/*      */     case -1: 
/*  406 */       return "mnu_normalend_hightlightbeg.gif";
/*      */     case 0: 
/*  408 */       return "mnu_hightlightend_normalbeg.gif";
/*      */     case 1: 
/*  410 */       return "mnu_normalbeg_end.gif";
/*      */     }
/*  412 */     return "mnu_normalbeg_end.gif";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getEndImage(int tabtoselect)
/*      */   {
/*  420 */     if (tabtoselect == 4) {
/*  421 */       return "mnu_highlight_end.gif";
/*      */     }
/*  423 */     return "mnu_normal_end.gif";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  428 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*  440 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*  444 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  445 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  446 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  447 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  451 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  452 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  459 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  462 */     JspWriter out = null;
/*  463 */     Object page = this;
/*  464 */     JspWriter _jspx_out = null;
/*  465 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  469 */       response.setContentType("text/html;charset=UTF-8");
/*  470 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  472 */       _jspx_page_context = pageContext;
/*  473 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  474 */       ServletConfig config = pageContext.getServletConfig();
/*  475 */       session = pageContext.getSession();
/*  476 */       out = pageContext.getOut();
/*  477 */       _jspx_out = out;
/*      */       
/*  479 */       out.write("<!--$Id$-->\n<!--html-->\n    \n\t<!--head>\n\t<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=is0-8859-1\">\n\t<meta NAME=\"Author\" Content=\"JMX Agent\">\n\t<title>\n\t\tWeb Transaction Analyzer\n\t</title>\n    <link href=\"/images/");
/*  480 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n    <link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n    <script type=\"text/javascript\" src=\"/webclient/common/js/sorttable.js\"></script>\n    <script type=\"text/javascript\">\n\n\n\n</script>\n\t</head-->\n\n\n\n\n\n\n");
/*  481 */       out.write(10);
/*      */       
/*  483 */       this.resourceid = request.getParameter("resourceid");
/*  484 */       this.tid = request.getParameter("tid");
/*      */       
/*  486 */       String compType = request.getParameter("comptype");
/*  487 */       if (compType == null)
/*      */       {
/*  489 */         compType = "all";
/*      */       }
/*  491 */       this.view = request.getParameter("view");
/*  492 */       if (this.view == null) { this.view = "";
/*      */       }
/*  494 */       out.write("\n\t<!--body onload=\"expandAllRows('");
/*  495 */       out.write("')\" bgcolor=\"#FFFFFF\" marginheight=0 topmargin=0 marginwidth=5 leftmargin=5 link=\"#0000FF\"  vlink=\"#0000FF\"  alink=\"#0000FF\"-->\n    <!--center><h1>Web Transaction Analyzer</h1></center-->\n\n    ");
/*  496 */       out.write("\n    ");
/*  497 */       out.write("\n    ");
/*  498 */       out.write("\n    ");
/*  499 */       out.write("\n    ");
/*  500 */       out.write("\n    ");
/*  501 */       out.write("\n    ");
/*  502 */       out.write("\n    ");
/*  503 */       out.write("\n    ");
/*  504 */       out.write("\n\n\n    ");
/*      */       
/*  506 */       String tabToSelect = request.getParameter("tabtoselect");
/*  507 */       if (tabToSelect == null)
/*      */       {
/*  509 */         tabToSelect = "0";
/*      */       }
/*      */       
/*  512 */       int index = 0;
/*  513 */       index = Integer.parseInt(tabToSelect);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  519 */       HashMap commonProp = (HashMap)request.getAttribute("commonProp");
/*  520 */       HashMap traceinfo = (HashMap)request.getAttribute("traceinfo");
/*  521 */       String firstins = (String)request.getAttribute("firstMethodInstance");
/*  522 */       HashMap first = (HashMap)traceinfo.get(firstins);
/*  523 */       HashMap compCount = (HashMap)request.getAttribute("compCount");
/*      */       
/*  525 */       String id = "1";
/*  526 */       if (!compType.equals("all"))
/*      */       {
/*  528 */         id = "comptable";
/*      */       }
/*      */       else {
/*  531 */         id = "treetable";
/*      */       }
/*      */       
/*      */ 
/*  535 */       out.write("\n<table width=\"100%\">\n<tr>\n<td style=\"text-align:right\">\n");
/*      */       
/*  537 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  538 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  539 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  541 */       _jspx_th_c_005fif_005f0.setTest("${empty(param.view)}");
/*  542 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  543 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  545 */           out.write(10);
/*      */           
/*  547 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  548 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  549 */           _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  551 */           _jspx_th_c_005fif_005f1.setTest("${empty(param.tabtoselect)||param.tabtoselect==0}");
/*  552 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  553 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */             for (;;) {
/*  555 */               out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  556 */               out.print(this.resourceid);
/*  557 */               out.write(39);
/*  558 */               out.write(44);
/*  559 */               out.write(39);
/*  560 */               out.print(this.tid);
/*  561 */               out.write("','all',0,null,'all')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  562 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  563 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  567 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  568 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */           }
/*      */           
/*  571 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  572 */           out.write(10);
/*      */           
/*  574 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  575 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  576 */           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  578 */           _jspx_th_c_005fif_005f2.setTest("${param.tabtoselect==1}");
/*  579 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  580 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */             for (;;) {
/*  582 */               out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  583 */               out.print(this.resourceid);
/*  584 */               out.write(39);
/*  585 */               out.write(44);
/*  586 */               out.write(39);
/*  587 */               out.print(this.tid);
/*  588 */               out.write("','Servlet',1,null,'all')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  589 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  590 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  594 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  595 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */           }
/*      */           
/*  598 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  599 */           out.write(10);
/*      */           
/*  601 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  602 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  603 */           _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  605 */           _jspx_th_c_005fif_005f3.setTest("${param.tabtoselect==2}");
/*  606 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  607 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */             for (;;) {
/*  609 */               out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  610 */               out.print(this.resourceid);
/*  611 */               out.write(39);
/*  612 */               out.write(44);
/*  613 */               out.write(39);
/*  614 */               out.print(this.tid);
/*  615 */               out.write("','EJB',2,null,'all')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  616 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  617 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  621 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  622 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */           }
/*      */           
/*  625 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  626 */           out.write(10);
/*      */           
/*  628 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  629 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  630 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  632 */           _jspx_th_c_005fif_005f4.setTest("${param.tabtoselect==3}");
/*  633 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  634 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */             for (;;) {
/*  636 */               out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  637 */               out.print(this.resourceid);
/*  638 */               out.write(39);
/*  639 */               out.write(44);
/*  640 */               out.write(39);
/*  641 */               out.print(this.tid);
/*  642 */               out.write("','Java',3,null,'all')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  643 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  644 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  648 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  649 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */           }
/*      */           
/*  652 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  653 */           out.write(10);
/*      */           
/*  655 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  656 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  657 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/*  659 */           _jspx_th_c_005fif_005f5.setTest("${param.tabtoselect==4}");
/*  660 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  661 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/*  663 */               out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  664 */               out.print(this.resourceid);
/*  665 */               out.write(39);
/*  666 */               out.write(44);
/*  667 */               out.write(39);
/*  668 */               out.print(this.tid);
/*  669 */               out.write("','JDBC',4,null,'all')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  670 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  671 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  675 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  676 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/*  679 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  680 */           out.write(10);
/*  681 */           out.write(32);
/*  682 */           out.print(FormatUtil.getString("am.webclient.wta.showallmethods.link"));
/*  683 */           out.write("</a>\n ");
/*  684 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  685 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  689 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  690 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  693 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  694 */         out.write(10);
/*  695 */         out.write(32);
/*      */         
/*  697 */         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  698 */         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  699 */         _jspx_th_c_005fif_005f6.setParent(null);
/*      */         
/*  701 */         _jspx_th_c_005fif_005f6.setTest("${!empty(param.view) && param.view == 'all'}");
/*  702 */         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  703 */         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */           for (;;) {
/*  705 */             out.write(10);
/*  706 */             out.write(32);
/*      */             
/*  708 */             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  709 */             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  710 */             _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */             
/*  712 */             _jspx_th_c_005fif_005f7.setTest("${param.tabtoselect==0}");
/*  713 */             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  714 */             if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */               for (;;) {
/*  716 */                 out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  717 */                 out.print(this.resourceid);
/*  718 */                 out.write(39);
/*  719 */                 out.write(44);
/*  720 */                 out.write(39);
/*  721 */                 out.print(this.tid);
/*  722 */                 out.write("','all',0,null,'')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  723 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/*  724 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  728 */             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/*  729 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */             }
/*      */             
/*  732 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*  733 */             out.write(10);
/*      */             
/*  735 */             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  736 */             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  737 */             _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */             
/*  739 */             _jspx_th_c_005fif_005f8.setTest("${param.tabtoselect==1}");
/*  740 */             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  741 */             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */               for (;;) {
/*  743 */                 out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  744 */                 out.print(this.resourceid);
/*  745 */                 out.write(39);
/*  746 */                 out.write(44);
/*  747 */                 out.write(39);
/*  748 */                 out.print(this.tid);
/*  749 */                 out.write("','Servlet',1,null,'')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  750 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  751 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  755 */             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  756 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */             }
/*      */             
/*  759 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  760 */             out.write(10);
/*      */             
/*  762 */             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  763 */             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  764 */             _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f6);
/*      */             
/*  766 */             _jspx_th_c_005fif_005f9.setTest("${param.tabtoselect==2}");
/*  767 */             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  768 */             if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */               for (;;) {
/*  770 */                 out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  771 */                 out.print(this.resourceid);
/*  772 */                 out.write(39);
/*  773 */                 out.write(44);
/*  774 */                 out.write(39);
/*  775 */                 out.print(this.tid);
/*  776 */                 out.write("','EJB',2,null,'')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  777 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  778 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  782 */             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  783 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */             }
/*      */             
/*  786 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  787 */             out.write(10);
/*      */             
/*  789 */             IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  790 */             _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/*  791 */             _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fif_005f6);
/*      */             
/*  793 */             _jspx_th_c_005fif_005f10.setTest("${param.tabtoselect==3}");
/*  794 */             int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/*  795 */             if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */               for (;;) {
/*  797 */                 out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  798 */                 out.print(this.resourceid);
/*  799 */                 out.write(39);
/*  800 */                 out.write(44);
/*  801 */                 out.write(39);
/*  802 */                 out.print(this.tid);
/*  803 */                 out.write("','Java',3,null,'')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  804 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/*  805 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  809 */             if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/*  810 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */             }
/*      */             
/*  813 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*  814 */             out.write(10);
/*      */             
/*  816 */             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  817 */             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/*  818 */             _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fif_005f6);
/*      */             
/*  820 */             _jspx_th_c_005fif_005f11.setTest("${param.tabtoselect==4}");
/*  821 */             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/*  822 */             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */               for (;;) {
/*  824 */                 out.write("\n<a class=\"staticlinks\" href=\"javascript:invoke('");
/*  825 */                 out.print(this.resourceid);
/*  826 */                 out.write(39);
/*  827 */                 out.write(44);
/*  828 */                 out.write(39);
/*  829 */                 out.print(this.tid);
/*  830 */                 out.write("','JDBC',4,null,'')\" onmouseover=\"this.style.cursor='pointer'\">\n");
/*  831 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/*  832 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  836 */             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/*  837 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */             }
/*      */             
/*  840 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*  841 */             out.write(10);
/*  842 */             out.write(32);
/*  843 */             out.print(FormatUtil.getString("am.webclient.wta.message"));
/*  844 */             out.write("</a>\n ");
/*  845 */             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/*  846 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  850 */         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/*  851 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*      */         }
/*      */         else {
/*  854 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*  855 */           out.write("\n </td>\n</tr>\n</table>\n\n<table class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\"\nwidth=\"100%\">\n<tbody>\n<tr>\n<td>\n\n<table style=\"width: 100%; text-align: left;\" border=\"0\"\ncellpadding=\"2\" cellspacing=\"0\">\n<tbody>\n<tr>\n<td class=\"leftlinksheading\">");
/*  856 */           out.print(FormatUtil.getString("am.webclient.wta.table.header"));
/*  857 */           out.write(32);
/*  858 */           out.println((String)commonProp.get("url"));
/*  859 */           out.write("</td>\n\n</tr>\n</tbody>\n</table>\n");
/*  860 */           int width = 91;
/*  861 */           out.write("\n<table style=\"text-align: left;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n<tbody>\n<tr>\n<td class=\"");
/*  862 */           out.print(getTabClass(index, 0));
/*  863 */           out.write("\"><a class=\"staticlinks\" href=\"javascript:invoke('");
/*  864 */           out.print(this.resourceid);
/*  865 */           out.write(39);
/*  866 */           out.write(44);
/*  867 */           out.write(39);
/*  868 */           out.print(this.tid);
/*  869 */           out.write("','all',0,null,'");
/*  870 */           out.print(this.view);
/*  871 */           out.write("')\" >");
/*  872 */           out.print(FormatUtil.getString("Tree"));
/*  873 */           out.write(32);
/*  874 */           out.write(40);
/*  875 */           if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */             return;
/*  877 */           out.write(")</a></td>\n");
/*      */           
/*  879 */           IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  880 */           _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/*  881 */           _jspx_th_c_005fif_005f12.setParent(null);
/*      */           
/*  883 */           _jspx_th_c_005fif_005f12.setTest("${compCount.Web > 0}");
/*  884 */           int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/*  885 */           if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */             for (;;) {
/*  887 */               out.write("\n<td class=\"tabseparator\"><br></td>\n<td class=\"");
/*  888 */               out.print(getTabClass(index, 1));
/*  889 */               out.write("\"><a class=\"staticlinks\" href=\"javascript:invoke('");
/*  890 */               out.print(this.resourceid);
/*  891 */               out.write(39);
/*  892 */               out.write(44);
/*  893 */               out.write(39);
/*  894 */               out.print(this.tid);
/*  895 */               out.write("','Servlet',1,null,'");
/*  896 */               out.print(this.view);
/*  897 */               out.write("')\" onmouseover=\"this.style.cursor='pointer'\">");
/*  898 */               out.print(FormatUtil.getString("Web"));
/*  899 */               out.write(32);
/*  900 */               out.write(40);
/*  901 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                 return;
/*  903 */               out.write(")</a></td>\n");
/*  904 */               width -= 9;
/*  905 */               out.write(10);
/*  906 */               int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/*  907 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  911 */           if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/*  912 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */           }
/*      */           else {
/*  915 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*  916 */             out.write(10);
/*      */             
/*  918 */             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  919 */             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  920 */             _jspx_th_c_005fif_005f13.setParent(null);
/*      */             
/*  922 */             _jspx_th_c_005fif_005f13.setTest("${compCount.EJB > 0}");
/*  923 */             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  924 */             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */               for (;;) {
/*  926 */                 out.write("\n<td class=\"tabseparator\"><br></td>\n<td class=\"");
/*  927 */                 out.print(getTabClass(index, 2));
/*  928 */                 out.write("\"><a class=\"staticlinks\" href=\"javascript:invoke('");
/*  929 */                 out.print(this.resourceid);
/*  930 */                 out.write(39);
/*  931 */                 out.write(44);
/*  932 */                 out.write(39);
/*  933 */                 out.print(this.tid);
/*  934 */                 out.write("','EJB',2,null,'");
/*  935 */                 out.print(this.view);
/*  936 */                 out.write("')\" onmouseover=\"this.style.cursor='pointer'\">");
/*  937 */                 out.print(FormatUtil.getString("EJB"));
/*  938 */                 out.write(40);
/*  939 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                   return;
/*  941 */                 out.write(")</a></td>\n");
/*  942 */                 width -= 9;
/*  943 */                 out.write(10);
/*  944 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/*  945 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  949 */             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/*  950 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*      */             }
/*      */             else {
/*  953 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  954 */               out.write(10);
/*      */               
/*  956 */               IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  957 */               _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  958 */               _jspx_th_c_005fif_005f14.setParent(null);
/*      */               
/*  960 */               _jspx_th_c_005fif_005f14.setTest("${compCount.Java > 0}");
/*  961 */               int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  962 */               if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                 for (;;) {
/*  964 */                   out.write("\n<td class=\"tabseparator\"><br></td>\n<td class=\"");
/*  965 */                   out.print(getTabClass(index, 3));
/*  966 */                   out.write("\"><a class=\"staticlinks\" href=\"javascript:invoke('");
/*  967 */                   out.print(this.resourceid);
/*  968 */                   out.write(39);
/*  969 */                   out.write(44);
/*  970 */                   out.write(39);
/*  971 */                   out.print(this.tid);
/*  972 */                   out.write("','Java',3,null,'");
/*  973 */                   out.print(this.view);
/*  974 */                   out.write("')\" onmouseover=\"this.style.cursor='pointer'\">");
/*  975 */                   out.print(FormatUtil.getString("Java"));
/*  976 */                   out.write(40);
/*  977 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                     return;
/*  979 */                   out.write(")</a></td>\n");
/*  980 */                   width -= 9;
/*  981 */                   out.write(10);
/*  982 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  983 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  987 */               if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  988 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*      */               }
/*      */               else {
/*  991 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  992 */                 out.write(10);
/*      */                 
/*  994 */                 IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  995 */                 _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/*  996 */                 _jspx_th_c_005fif_005f15.setParent(null);
/*      */                 
/*  998 */                 _jspx_th_c_005fif_005f15.setTest("${compCount.JDBC > 0}");
/*  999 */                 int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1000 */                 if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                   for (;;) {
/* 1002 */                     out.write("\n<td class=\"tabseparator\"><br></td>\n<td class=\"");
/* 1003 */                     out.print(getTabClass(index, 4));
/* 1004 */                     out.write("\"><a class=\"staticlinks\" href=\"javascript:invoke('");
/* 1005 */                     out.print(this.resourceid);
/* 1006 */                     out.write(39);
/* 1007 */                     out.write(44);
/* 1008 */                     out.write(39);
/* 1009 */                     out.print(this.tid);
/* 1010 */                     out.write("','JDBC',4,null,'");
/* 1011 */                     out.print(this.view);
/* 1012 */                     out.write("')\" onmouseover=\"this.style.cursor='pointer'\">");
/* 1013 */                     out.print(FormatUtil.getString("SQL"));
/* 1014 */                     out.write(40);
/* 1015 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                       return;
/* 1017 */                     out.write(")</a></td>\n");
/* 1018 */                     width -= 9;
/* 1019 */                     out.write(10);
/* 1020 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1021 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1025 */                 if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1026 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*      */                 }
/*      */                 else {
/* 1029 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1030 */                   out.write("\n<td class=\"tabseparator\"><br></td>\n<td class=\"tabremains\" width=\"");
/* 1031 */                   out.print(width);
/* 1032 */                   out.write("%\"><br></td>\n</tr>\n</tbody>\n</table>\n\n          ");
/*      */                   
/*      */ 
/* 1035 */                   int cellpadding = 0;
/* 1036 */                   if (!compType.equals("all")) {
/* 1037 */                     cellpadding = 2;
/*      */                   }
/* 1039 */                   out.println("<table id=\"" + id + "\" cellpadding=\"" + cellpadding + "\" cellspacing=\"0\" align=\"center\" border=\"0\" width=\"100%\" >\n<tbody>");
/* 1040 */                   out.println("<tr>");
/* 1041 */                   if (compType.equals("all")) {
/* 1042 */                     out.println("<td width=\"30px\" class=\"tableHeader\"><img id=\"toggle\" onclick=\"expandAllRows('" + compType + "')\" onmouseover=\"this.style.cursor='pointer'\" src=\"/images/img_collapse_all.gif\" border=\"0\" align=\"middle\"></td>");
/*      */                   }
/* 1044 */                   for (int i = 0; i < this.headernames.length; i++)
/* 1045 */                     if ((!compType.equals("JDBC")) || (!this.headernames[i].equals("SQL")))
/*      */                     {
/*      */ 
/*      */ 
/* 1049 */                       if ((compType.equals("all")) || (!this.headernames[i].equals("SQL")))
/*      */                       {
/*      */ 
/*      */ 
/* 1053 */                         int colspan = 1;
/* 1054 */                         if ((!compType.equals("all")) && (this.headernames[i].equals("Method Name"))) {
/* 1055 */                           colspan = 2;
/*      */                         }
/* 1057 */                         out.println("<td colspan=\"" + colspan + "\" width=\"" + this.widths[i] + "%\" align=\"left\" class=\"tableHeader\">");
/* 1058 */                         out.println("<span class=\"bodytext\" style=\"font-weight: bold;\">");
/* 1059 */                         if ((compType.equals("JDBC")) && (this.headernames[i].equals("Method Name")))
/*      */                         {
/* 1061 */                           out.println("SQL");
/*      */                         }
/*      */                         else {
/* 1064 */                           out.println(FormatUtil.getString(this.headernames[i]));
/*      */                         }
/* 1066 */                         out.println("<br>\n</span></td>");
/*      */                       } }
/* 1068 */                   out.println("</tr>");
/*      */                   
/* 1070 */                   if (compType.equals("all"))
/*      */                   {
/*      */ 
/* 1073 */                     drawRow(first, commonProp, out, 0, "1", false, compType, Long.parseLong((String)first.get("inclusiveTime")), false);
/*      */                     
/* 1075 */                     drawTable(first, commonProp, traceinfo, out, 1, "1-1", compType, Long.parseLong((String)first.get("inclusiveTime")), false);
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/* 1080 */                     drawTable(first, commonProp, traceinfo, out, 0, "1", compType, Long.parseLong((String)first.get("inclusiveTime")), false);
/*      */                   }
/* 1082 */                   out.println("</tbody>\n</table>");
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1091 */                   out.write("\n          </td></tr></table>\n\n       ");
/* 1092 */                   out.write("\n    ");
/* 1093 */                   out.write("\n\n    ");
/* 1094 */                   out.write("\n\n    ");
/* 1095 */                   out.write("\n\n    ");
/* 1096 */                   out.write("\n\n    ");
/* 1097 */                   out.write("\n    ");
/* 1098 */                   out.write("\n    ");
/* 1099 */                   out.write("\n    ");
/* 1100 */                   out.write("\n    ");
/* 1101 */                   out.write("\n    ");
/* 1102 */                   out.write("\n    ");
/* 1103 */                   out.write("\n\t<!--/body>\n\n</html-->\n");
/*      */                 }
/* 1105 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1106 */         out = _jspx_out;
/* 1107 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1108 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1109 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1112 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1118 */     PageContext pageContext = _jspx_page_context;
/* 1119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1121 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1122 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1123 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1125 */     _jspx_th_c_005fout_005f0.setValue("${compCount.Total}");
/* 1126 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1127 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1128 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1129 */       return true;
/*      */     }
/* 1131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1137 */     PageContext pageContext = _jspx_page_context;
/* 1138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1140 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1141 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1142 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 1144 */     _jspx_th_c_005fout_005f1.setValue("${compCount.Web}");
/* 1145 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1146 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1147 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1148 */       return true;
/*      */     }
/* 1150 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1156 */     PageContext pageContext = _jspx_page_context;
/* 1157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1159 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1160 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1161 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 1163 */     _jspx_th_c_005fout_005f2.setValue("${compCount.EJB}");
/* 1164 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1165 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1166 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1167 */       return true;
/*      */     }
/* 1169 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1170 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1175 */     PageContext pageContext = _jspx_page_context;
/* 1176 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1178 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1179 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1180 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 1182 */     _jspx_th_c_005fout_005f3.setValue("${compCount.Java}");
/* 1183 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1184 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1185 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1186 */       return true;
/*      */     }
/* 1188 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1194 */     PageContext pageContext = _jspx_page_context;
/* 1195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1197 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1198 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1199 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 1201 */     _jspx_th_c_005fout_005f4.setValue("${compCount.JDBC}");
/* 1202 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1203 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1204 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1205 */       return true;
/*      */     }
/* 1207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1208 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wtatrace_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */