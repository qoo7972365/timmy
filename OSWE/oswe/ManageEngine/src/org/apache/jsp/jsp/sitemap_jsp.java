/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Map;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ 
/*      */ public final class sitemap_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   24 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   39 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   43 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   44 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   45 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   46 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   47 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   49 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   53 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   54 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.release();
/*   55 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   56 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   57 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*   64 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   67 */     JspWriter out = null;
/*   68 */     Object page = this;
/*   69 */     JspWriter _jspx_out = null;
/*   70 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   74 */       response.setContentType("text/html");
/*   75 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   77 */       _jspx_page_context = pageContext;
/*   78 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   79 */       ServletConfig config = pageContext.getServletConfig();
/*   80 */       session = pageContext.getSession();
/*   81 */       out = pageContext.getOut();
/*   82 */       _jspx_out = out;
/*      */       
/*   84 */       out.write("<!--$Id$-->\n\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n\n<script language=\"JavaScript1.2\">\n\n\nvar bIsOpenApplicationDiv=false;\n\nvar bIsOpenMonitorDiv=false;\n\nvar bIsOpenAlertDiv=true;\n\nvar bIsOpenAdminDiv=true;\n\n\n            var plusSign                    = \"../images/icon_plus.gif\";\n            var minusSign                   = \"../images/icon_minus.gif\";\n            var currentProfileId            = \"-1\";\n\n\n   // Inverts the state of the given expand menu name + the related div (if any).\n    function InvertDivState(name, divName){\n        var oDiv  = document.getElementById(name+\"Div\");\n        var oSpan = document.getElementById(name+\"Sign\");\n        var oTd   = document.getElementById(name+\"Td\");\n        eval(\"bIsOpen\" + name + \"Div = !bIsOpen\" + name + \"Div\");\n        var bIsOpen = eval(\"bIsOpen\" + name + \"Div\");\n        if(bIsOpen){\n          if(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"block\";\n");
/*   85 */       out.write("          oDiv.style.display = \"block\";\n          oSpan.src = minusSign;\n        }else {\n          if(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"none\";\n          oDiv.style.display = \"none\";\n          oSpan.src = plusSign;\n        }\n    }\n\n</script>\n<script language=\"JavaScript\" type=\"text/JavaScript\">\n\nfunction checkforwindows()\n{\n  ");
/*      */       
/*   87 */       String ostype = System.getProperty("os.name");
/*   88 */       if (ostype.indexOf("Windows") != -1)
/*      */       {
/*      */ 
/*   91 */         out.write("\n \t\treturn true;\n\t");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*   97 */         out.write("\n\t\talert(\"Event Log Monitoring is not supported in Linux installations.\");\n \t\treturn false;\n\t");
/*      */       }
/*      */       
/*      */ 
/*  101 */       out.write("\n  \n }\n</script>\n<style type=\"text/css\">\n<!--\n.lrbtborder {\n\tborder: 1px solid #BCBCBC;\n}\n-->\n</style>\n</head>\n");
/*      */       
/*  103 */       ManagedApplication mo = new ManagedApplication();
/*  104 */       String categorytype = Constants.getCategorytype();
/*  105 */       pageContext.setAttribute("categorytype", categorytype);
/*      */       
/*  107 */       out.write("\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td height=\"87%\" colspan=\"2\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n        <tr>\n          <td width=\"24%\" height=\"22\"> <span class=\"monitorsheading\">&nbsp;");
/*  108 */       out.print(FormatUtil.getString("am.webclient.footer.sitemaplink.text"));
/*  109 */       out.write("</span></td>\n        </tr>\n\t\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n      </table>\n      <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td width=\"72%\" height=\"31\" class=\"tableheading\" onClick=\"InvertDivState('Monitor');\">&nbsp;&nbsp;<img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" id=\"MonitorSign\">\n            <a href=\"/showresource.do?method=showResourceTypes\" class=\"staticlinks\">");
/*  110 */       out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  111 */       out.write("</a>\n          </td>\n        </tr>\n      </table>\n      <div id=\"MonitorDiv\"  style=\"DISPLAY: block\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"4\" class=\"lrbborder\">\n          <tr>\n            <td align=\"center\"> <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n            ");
/*      */       
/*  113 */       if (Constants.getCategorytype().equals("LAMP"))
/*      */       {
/*      */ 
/*  116 */         out.write("\n\t    <tr > \n                  <td width=\"29%\" height=\"29\"   class=\"yellowgrayborder\">&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" class=\"bodytextboldunderline\">");
/*  117 */         out.print(FormatUtil.getString("am.monitortab.applicationservers.text"));
/*  118 */         out.write("</a></b></td>\n                  <td width=\"18%\"   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td width=\"22%\"  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td width=\"31%\"  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n            </c:if>\n                <tr > \n                  <td height=\"23\" class=\"whitegrayborder\"><img src=\"../images/icon_monitors_tomcat.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=Tomcat-server&detailspage=true\" class=\"bodytext\">");
/*  119 */         out.print(FormatUtil.getString("am.webclient.monitortype.tomcatservers.text"));
/*  120 */         out.write("</A> </td>\n                </tr>\n                <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" class=\"bodytextboldunderline\">");
/*  121 */         out.print(FormatUtil.getString("am.monitortab.services.text"));
/*  122 */         out.write("</a> \n                    </b> </td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                \n                  <td height=\"22\"  class=\"bodytext\">&nbsp;&nbsp;<img src=\"../images/icon_monitors_mx4jjmx1_2.gif\" hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=JMX1.2-MX4J-RMI&detailspage=true\" class=\"staticlinks\"> ");
/*  123 */         out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/*  124 */         out.write("</a> </td>\n                  <td height=\"22\"  class=\"bodytext\"><img src=\"../images/icon_monitors_mailserver.gif\"  border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=MAIL-server&detailspage=true\" class=\"bodytext\">");
/*  125 */         out.print(FormatUtil.getString("am.webclient.sitemap.mail-server.text"));
/*  126 */         out.write("</A></td>\n                  <td height=\"22\" class=\"bodytext\"><img src=\"../images/icon_monitors_servicemonitoring.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=Port-Test&detailspage=true\" class=\"bodytext\">");
/*  127 */         out.print(FormatUtil.getString("am.webclient.sitemap.serviceportmonitoring.text"));
/*  128 */         out.write("</a></td>\n\n\t\t         <td class=\"bodytext\"><img src=\"../images/icon_monitors_adventnet.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=RMI&detailspage=true\" class=\"bodytext\">");
/*  129 */         out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/*  130 */         out.write("</a></td>\n                </tr>\n                <tr >\n\n\t\t\t\t  <td height=\"22\" class=\"bodytext\"><img src=\"/images/icon_monitors_snmp.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=TELNET&detailspage=true\" class=\"bodytext\">");
/*  131 */         out.print(FormatUtil.getString("Telnet"));
/*  132 */         out.write("</a></td>\n\t\t\t\t  \n                  <td height=\"22\"  class=\"bodytext\">&nbsp;&nbsp;&nbsp;<img src=\"/images/icon_monitors_snmp.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=SNMP&detailspage=true\" class=\"bodytext\">");
/*  133 */         out.print(FormatUtil.getString("am.reporttab.heading.snmp.text"));
/*  134 */         out.write("</a> </td>\n                  <td height=\"22\"  class=\"bodytext\">&nbsp;&nbsp;&nbsp;<img src=\"/images/icon_monitors_exchange.gif\" width=\"18\" height=\"18\" hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=Exchange-server&detailspage=true\" class=\"bodytext\">");
/*  135 */         out.print(FormatUtil.getString("Exchange Server"));
/*  136 */         out.write("</a> </td>\n                 <!-- <td height=\"22\"  class=\"bodytext\"><a href=\"/jsp/networkdetails.jsp?network=JBOSS-server&appname=null&haid=null\" class=\"bodytext\"><img src=\"../images/icon_monitors_webserver.gif\" hspace=\"5\" border=\"0\" align=\"absmiddle\"></a><a href=\"/showresource.do?method=showResourceTypes&network=WEB-server&detailspage=true\" class=\"bodytext\">Web Servers</a></td>-->\n\t\t\t\t</tr>\n\t\t\t\t<tr >\n                <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\" class=\"bodytextboldunderline\">");
/*  137 */         out.print(FormatUtil.getString("am.monitortab.webservices.text"));
/*  138 */         out.write("</a></b> </td>\n                    <td   class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t<td  class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t<td  class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n                  <td height=\"22\" class=\"bodytext\"><img src=\"../images/icon_monitors_apache.gif\" width=\"32\" height=\"9\" hspace=\"5\"><a href=\"/showresource.do?method=showResourceTypes&network=Apache-server&detailspage=true\" class=\"bodytext\">");
/*  139 */         out.print(FormatUtil.getString("Apache Server"));
/*  140 */         out.write(" </a></td>\n\t\t\t\t  <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;<img src=\"../images/icon_php.gif\"  hspace=\"5\"><a href=\"/showresource.do?method=showResourceTypes&network=PHP&detailspage=true\" class=\"bodytext\">");
/*  141 */         out.print(FormatUtil.getString("am.reporttab.heading.php.text"));
/*  142 */         out.write(" </a></td>\n\t\t\t\t  <td height=\"22\"  class=\"bodytext\"><img src=\"../images/icon_monitors_webserver.gif\" hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=WEB-server&detailspage=true\" class=\"bodytext\">");
/*  143 */         out.print(FormatUtil.getString("am.reporttab.heading.webserver.text"));
/*  144 */         out.write("</a></td>\n    \t\t\t <tr>\n\t\t\t   <td class=\"whitegrayborder\" width=\"10%\"><img src=\"/images/icon_monitors_urlmonitor.gif\" title=\"URL\" align=\"middle\" border=\"0\" hspace=\"5\"><span class=\"footer\"></span><a href=\"/showresource.do?method=showResourceTypes&network=UrlMonitor&detailspage=true\" class=\"bodytext\">");
/*  145 */         out.print(FormatUtil.getString("am.webclient.urlmonitor.type.text"));
/*  146 */         out.write("</a></td>\n\t\t\t   <td class=\"whitegrayborder\" width=\"10%\"><img src=\"/images/icon_monitors_urlmonitor.gif\" title=\"URL\" align=\"mi\n\t\t\t   ddle\" border=\"0\" hspace=\"5\"><span class=\"footer\"></span><a href=\"/showresource.do?method=showResourceTypes&network=UrlSeq&detailspage=true\" class=\"bodytext\">");
/*  147 */         out.print(FormatUtil.getString("am.webclient.urlseq.type.text"));
/*  148 */         out.write("</a></td>\n\n\t\t\t   <!--<td height=\"22\"  class=\"bodytext\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\" class=\"bodytextboldunderline\">URL Monitors</a></td>-->\n\t\t\t\t </tr>\n\t\t\t\t  </tr>\n\t\t\t\t  <tr>\n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp; <b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\" class=\"bodytextboldunderline\">");
/*  149 */         out.print(FormatUtil.getString("am.monitortab.servers.text"));
/*  150 */         out.write(" </a></b></td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                  <td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_linux.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=Linux&detailspage=true\" class=\"bodytext\">");
/*  151 */         out.print(FormatUtil.getString("Linux"));
/*  152 */         out.write("</A></td>\n                  <td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_freebsd.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=FreeBSD&detailspage=true\" class=\"bodytext\">");
/*  153 */         out.print(FormatUtil.getString("FreeBSD"));
/*  154 */         out.write("</A></td>\n                  \n                <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\" class=\"bodytextboldunderline\">");
/*  155 */         out.print(FormatUtil.getString("am.monitortab.databaseservers.text"));
/*  156 */         out.write("</a></b></span> </td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<img src=\"../images/icon_monitors_mysql.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=MYSQL-DB-server&detailspage=true\" class=\"bodytext\">");
/*  157 */         out.print(FormatUtil.getString("MySQL"));
/*  158 */         out.write("</A></td>\n\n                </tr>\n                <tr class=\"yellowgrayborder\"> \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<a class=\"bodytextboldunderline\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM\">");
/*  159 */         out.print(FormatUtil.getString("am.monitortab.custommonitors.text"));
/*  160 */         out.write("</a></td>\n                  <td  class=\"bodytext\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n            \n\t\t<tr> \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<a class=\"bodytextboldunderline\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=Script Monitor\">");
/*  161 */         out.print(FormatUtil.getString("am.monitortab.scriptmonitors.text"));
/*  162 */         out.write("</a></td>\n                  <td  class=\"bodytext\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n            ");
/*      */ 
/*      */       }
/*  165 */       else if (Constants.getCategorytype().equals("DATABASE"))
/*      */       {
/*      */ 
/*  168 */         out.write("\n                <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" class=\"bodytextboldunderline\">");
/*  169 */         out.print(FormatUtil.getString("am.monitortab.services.text"));
/*  170 */         out.write("</a> \n                    </b> </td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                  <td height=\"22\" class=\"bodytext\" width=\"20%\"><img src=\"../images/icon_monitors_servicemonitoring.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=Port-Test&detailspage=true\" class=\"bodytext\">");
/*  171 */         out.print(FormatUtil.getString("am.webclient.sitemap.serviceportmonitoring.text"));
/*  172 */         out.write("</a></td>\n\t  \n                  <td height=\"22\"  class=\"bodytext\">&nbsp;&nbsp;&nbsp;<img src=\"/images/icon_monitors_snmp.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><a href=\"/showresource.do?method=showResourceTypes&network=SNMP&detailspage=true\" class=\"bodytext\">");
/*  173 */         out.print(FormatUtil.getString("am.reporttab.heading.snmp.text"));
/*  174 */         out.write("</a> </td>\n\t\t\t\t</tr>\n\t\t\t\t<tr >\n                <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\" class=\"bodytextboldunderline\">");
/*  175 */         out.print(FormatUtil.getString("am.monitortab.webservices.text"));
/*  176 */         out.write("</a></b> </td>\n                    <td   class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t<td  class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t<td  class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n                  \n\t\t\t   <td class=\"whitegrayborder\" width=\"20%\"><img src=\"/images/icon_monitors_urlmonitor.gif\" title=\"URL\" align=\"middle\" border=\"0\" hspace=\"5\"><span class=\"footer\"></span><a href=\"/showresource.do?method=showResourceTypes&network=UrlMonitor&detailspage=true\" class=\"bodytext\">");
/*  177 */         out.print(FormatUtil.getString("am.webclient.urlmonitor.type.text"));
/*  178 */         out.write("</a></td>\n\t\t\t   <td class=\"whitegrayborder\" width=\"20%\"><img src=\"/images/icon_monitors_urlmonitor.gif\" title=\"URL\" align=\"mi\n\t\t\t   ddle\" border=\"0\" hspace=\"5\"><span class=\"footer\"></span><a href=\"/showresource.do?method=showResourceTypes&network=UrlSeq&detailspage=true\" class=\"bodytext\">");
/*  179 */         out.print(FormatUtil.getString("am.webclient.urlseq.type.text"));
/*  180 */         out.write("</a></td>\n\n\t\t\t   </tr>\n\t\t\t\t  <tr>\n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp; <b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\" class=\"bodytextboldunderline\">");
/*  181 */         out.print(FormatUtil.getString("am.monitortab.servers.text"));
/*  182 */         out.write(" </a></b></td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;&nbsp;<img src=\"../images/icon_monitors_solaris.gif\"  border=\"0\" align=\"absmiddle\">&nbsp;<A HREF=\"/showresource.do?method=showResourceTypes&network=Sun Solaris&detailspage=true\" class=\"bodytext\">");
/*  183 */         out.print(FormatUtil.getString("Sun Solaris"));
/*  184 */         out.write("</A></td>\n                  <td height=\"23\"  class=\"bodytext\"><img src=\"../images/icon_monitors_windows.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\">&nbsp;<A HREF=\"/showresource.do?method=showResourceTypes&network=Windows&detailspage=true\" class=\"bodytext\">");
/*  185 */         out.print(FormatUtil.getString("Windows"));
/*  186 */         out.write("</A></td>\n                  <td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_linux.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=Linux&detailspage=true\" class=\"bodytext\">");
/*  187 */         out.print(FormatUtil.getString("Linux"));
/*  188 */         out.write("</A></td>\n                 <td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_hpunix.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=HP-UX / Tru64&detailspage=true\" class=\"bodytext\">");
/*  189 */         out.print(FormatUtil.getString("HP-UX / Tru64"));
/*  190 */         out.write("</A></td>\n                  </tr><tr><td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_aix.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=Aix&detailspage=true\" class=\"bodytext\">");
/*  191 */         out.print(FormatUtil.getString("AIX"));
/*  192 */         out.write("</A></td>\n                  <td height=\"23\"  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_freebsd.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=FreeBSD&detailspage=true\" class=\"bodytext\">");
/*  193 */         out.print(FormatUtil.getString("FreeBSD"));
/*  194 */         out.write("</A></td>\n           \n                  <td  class=\"whitegrayborder\"><img src=\"../images/icon_monitors_unknown.gif\"  hspace=\"5\" border=\"0\" align=\"absbottom\"><A HREF=\"/showresource.do?method=showResourceTypes&network=UnKnown&detailspage=true\" class=\"bodytext\">");
/*  195 */         out.print(FormatUtil.getString("Unknown"));
/*  196 */         out.write("</A></td>\n                </tr><td></td>\n                <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\">&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\" class=\"bodytextboldunderline\">");
/*  197 */         out.print(FormatUtil.getString("am.monitortab.databaseservers.text"));
/*  198 */         out.write("</a></b></span> </td>\n                  <td   class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                  <td  class=\"yellowgrayborder\">&nbsp;</td>\n                </tr>\n                <tr > \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<img src=\"../images/icon_monitors_mysql.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=MYSQL-DB-server&detailspage=true\" class=\"bodytext\">");
/*  199 */         out.print(FormatUtil.getString("MySQL"));
/*  200 */         out.write("</A></td>\n                  <td  class=\"bodytext\"><img src=\"../images/icon_monitors_oracle.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=ORACLE-DB-server&detailspage=true\" class=\"bodytext\">");
/*  201 */         out.print(FormatUtil.getString("Oracle"));
/*  202 */         out.write("</A></td>\n                  <td><img src=\"../images/icon_monitors_mssql.gif\"  hspace=\"5\" border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=MSSQL-DB-server&detailspage=true\" class=\"bodytext\">");
/*  203 */         out.print(FormatUtil.getString("MS SQL"));
/*  204 */         out.write("</a> </td>\n                   <td><img src=\"../images/icon_monitors_db2.gif\"  border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=DB2-server&detailspage=true\" class=\"bodytext\">");
/*  205 */         out.print(FormatUtil.getString("DB2"));
/*  206 */         out.write("</a></td>\n                   <td><img src=\"../images/icon_monitors_sybase.gif\"  border=\"0\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=SYBASE-DB-server&detailspage=true\" class=\"bodytext\">");
/*  207 */         out.print(FormatUtil.getString("Sybase"));
/*  208 */         out.write("</a></td>\n                </tr>\n               <!--<tr class=\"yellowgrayborder\"> \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\" class=\"bodytextboldunderline\">URL \n                    Monitors</a></td>\n                  <td  class=\"bodytext\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>-->           \n            \n\t\t<tr> \n                  <td height=\"23\"  class=\"bodytext\">&nbsp;<a class=\"bodytextboldunderline\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=Script Monitor\">");
/*  209 */         out.print(FormatUtil.getString("am.monitortab.scriptmonitors.text"));
/*  210 */         out.write("</a></td>\n                  <td  class=\"bodytext\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                ");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  215 */         String query1 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='APP' and RESOURCETYPE in " + Constants.resourceTypes + "  and DISPLAYNAME NOT IN ('WebLogic Clusters') ORDER BY DISPLAYNAME";
/*      */         
/*  217 */         ArrayList rows1 = mo.getRows(query1);
/*  218 */         if (rows1.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  222 */           out.write("\n                <tr > \n                  <td  height=\"29\"   class=\"yellowgrayborder\" >&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" class=\"bodytextboldunderline\">");
/*  223 */           out.print(FormatUtil.getString("am.monitortab.applicationservers.text"));
/*  224 */           out.write("</a></b></td>\n                  \n                </tr>\n                <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  226 */           for (int i = 0; i < rows1.size(); i++)
/*      */           {
/*  228 */             ArrayList row1 = (ArrayList)rows1.get(i);
/*  229 */             String res1 = (String)row1.get(0);
/*  230 */             String image = (String)row1.get(3);
/*  231 */             String dname = (String)row1.get(1);
/*      */             
/*      */ 
/*  234 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  235 */             out.print(image);
/*  236 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  237 */             out.print(res1);
/*  238 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  239 */             out.print(FormatUtil.getString(dname));
/*  240 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  242 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  244 */         out.write("\n\t\t\t\t\n                \n                  ");
/*  245 */         String query2 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='TM' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  247 */         ArrayList rows2 = mo.getRows(query2);
/*  248 */         if (rows2.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  252 */           out.write("\n               <tr >\n\t\t\t\t<td height=\"27\" class=\"yellowgrayborder\" >&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=TM\" class=\"bodytextboldunderline\">");
/*  253 */           out.print(FormatUtil.getString("am.monitortab.transactionmonitors.text"));
/*  254 */           out.write("</a></b> </td>\n\t\t\t\t\n\t\t\t\t</tr>\n                <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  256 */           for (int i = 0; i < rows2.size(); i++)
/*      */           {
/*  258 */             ArrayList row2 = (ArrayList)rows2.get(i);
/*  259 */             String res2 = (String)row2.get(0);
/*  260 */             String image2 = (String)row2.get(3);
/*  261 */             String dname2 = (String)row2.get(1);
/*      */             
/*      */ 
/*  264 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder'  ><img src='../");
/*  265 */             out.print(image2);
/*  266 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  267 */             out.print(res2);
/*  268 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  269 */             out.print(FormatUtil.getString(dname2));
/*  270 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  272 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  274 */         out.write("\n\t\t\t\t\n\t\t");
/*  275 */         String query3 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='SER' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  277 */         ArrayList rows3 = mo.getRows(query3);
/*  278 */         if (rows3.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  282 */           out.write(" <tr >  \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" class=\"bodytextboldunderline\">");
/*  283 */           out.print(FormatUtil.getString("am.monitortab.services.text"));
/*  284 */           out.write("</a> \n                    </b> </td>\n                  \n                </tr>\n                  <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  286 */           for (int i = 0; i < rows3.size(); i++)
/*      */           {
/*  288 */             ArrayList row3 = (ArrayList)rows3.get(i);
/*  289 */             String res3 = (String)row3.get(0);
/*  290 */             String image3 = (String)row3.get(3);
/*  291 */             String dname3 = (String)row3.get(1);
/*      */             
/*      */ 
/*  294 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  295 */             out.print(image3);
/*  296 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  297 */             out.print(res3);
/*  298 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  299 */             out.print(FormatUtil.getString(dname3));
/*  300 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  302 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  304 */         out.write("\n                \n                \n            ");
/*  305 */         String query4 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='URL' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  307 */         ArrayList rows4 = mo.getRows(query4);
/*  308 */         if (rows4.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  312 */           out.write(" <tr >\n                <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\" class=\"bodytextboldunderline\">");
/*  313 */           out.print(FormatUtil.getString("am.monitortab.webservices.text"));
/*  314 */           out.write("</a></b> </td>\n                    \n\t\t\t\t\t</tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  316 */           for (int i = 0; i < rows4.size(); i++)
/*      */           {
/*  318 */             ArrayList row4 = (ArrayList)rows4.get(i);
/*  319 */             String res4 = (String)row4.get(0);
/*  320 */             String image4 = (String)row4.get(3);
/*  321 */             String dname4 = (String)row4.get(1);
/*      */             
/*      */ 
/*  324 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  325 */             out.print(image4);
/*  326 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  327 */             out.print(res4);
/*  328 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  329 */             out.print(FormatUtil.getString(dname4));
/*  330 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  332 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  334 */         out.write("\n                \n                \n                ");
/*  335 */         String query5 = "SELECT  RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='SYS' and RESOURCETYPE in " + Constants.resourceTypes + " and  RESOURCETYPE NOT IN('Node','WindowsNT','WindowsNT_Server','Windows95','SUN PC','Windows 2000','Windows 2003','Windows XP') GROUP BY SUBGROUP";
/*      */         
/*  337 */         ArrayList rows5 = mo.getRows(query5);
/*  338 */         if (rows5.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  342 */           out.write(" \t  <tr>\n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp; <b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\" class=\"bodytextboldunderline\">");
/*  343 */           out.print(FormatUtil.getString("am.monitortab.servers.text"));
/*  344 */           out.write(" </a></b></td>\n                  \n                </tr>\n                   <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  346 */           for (int i = 0; i < rows5.size(); i++)
/*      */           {
/*  348 */             ArrayList row5 = (ArrayList)rows5.get(i);
/*  349 */             String res5 = (String)row5.get(0);
/*  350 */             String image5 = (String)row5.get(3);
/*  351 */             String dname5 = (String)row5.get(1);
/*      */             
/*      */ 
/*  354 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  355 */             out.print(image5);
/*  356 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  357 */             out.print(res5);
/*  358 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  359 */             out.print(FormatUtil.getString(dname5));
/*  360 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  362 */           out.write(" <td height='23' class='whitegrayborder' ><img src='../images/icon_monitors_windows.gif' width=\"25\" height=\"18\" hspace=\"9\" align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=Windows&detailspage=true\" class=\"bodytext\">");
/*  363 */           out.print(FormatUtil.getString("Windows"));
/*  364 */           out.write("</a></td></tr></table></td></tr>");
/*      */         }
/*  366 */         out.write("\n                \n                \n            \t ");
/*  367 */         String query6 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='DBS' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  369 */         ArrayList rows6 = mo.getRows(query6);
/*  370 */         if (rows6.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  374 */           out.write(" \t   <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\" class=\"bodytextboldunderline\">");
/*  375 */           out.print(FormatUtil.getString("am.monitortab.databaseservers.text"));
/*  376 */           out.write("</a></b></span> </td>\n                 \n                </tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  378 */           for (int i = 0; i < rows6.size(); i++)
/*      */           {
/*  380 */             ArrayList row6 = (ArrayList)rows6.get(i);
/*  381 */             String res6 = (String)row6.get(0);
/*  382 */             String image6 = (String)row6.get(3);
/*  383 */             String dname6 = (String)row6.get(1);
/*      */             
/*      */ 
/*  386 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  387 */             out.print(image6);
/*  388 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  389 */             out.print(res6);
/*  390 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  391 */             out.print(FormatUtil.getString(dname6));
/*  392 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  394 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  396 */         out.write("\n                \t\n\t\t\t\t\n\t\t\t");
/*  397 */         String query7 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='MS' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  399 */         ArrayList rows7 = mo.getRows(query7);
/*  400 */         if (rows7.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  404 */           out.write(" \t   <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" class=\"bodytextboldunderline\">");
/*  405 */           out.print(FormatUtil.getString("Mail Servers"));
/*  406 */           out.write("</a></b></span> </td>\n                \n                </tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  408 */           for (int i = 0; i < rows7.size(); i++)
/*      */           {
/*  410 */             ArrayList row7 = (ArrayList)rows7.get(i);
/*  411 */             String res7 = (String)row7.get(0);
/*  412 */             String image7 = (String)row7.get(3);
/*  413 */             String dname7 = (String)row7.get(1);
/*      */             
/*      */ 
/*  416 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  417 */             out.print(image7);
/*  418 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  419 */             out.print(res7);
/*  420 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  421 */             out.print(FormatUtil.getString(dname7));
/*  422 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  424 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  426 */         out.write("\n\n\t\t\t");
/*  427 */         String query8 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='ERP' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  429 */         ArrayList rows8 = mo.getRows(query8);
/*  430 */         if (rows8.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  434 */           out.write(" \t   <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" class=\"bodytextboldunderline\">");
/*  435 */           out.print(FormatUtil.getString("ERP"));
/*  436 */           out.write("</a></b></span> </td>\n                \n                </tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  438 */           for (int i = 0; i < rows8.size(); i++)
/*      */           {
/*  440 */             ArrayList row8 = (ArrayList)rows8.get(i);
/*  441 */             String res8 = (String)row8.get(0);
/*  442 */             String image8 = (String)row8.get(3);
/*  443 */             String dname8 = (String)row8.get(1);
/*      */             
/*      */ 
/*  446 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  447 */             out.print(image8);
/*  448 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  449 */             out.print(res8);
/*  450 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  451 */             out.print(FormatUtil.getString(dname8));
/*  452 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  454 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  456 */         out.write("\n\n\t\t\t");
/*  457 */         String query9 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='MOM' and RESOURCETYPE in " + Constants.resourceTypes + " ORDER BY DISPLAYNAME";
/*      */         
/*  459 */         ArrayList rows9 = mo.getRows(query9);
/*  460 */         if (rows9.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  464 */           out.write(" \t   <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\" class=\"bodytextboldunderline\">");
/*  465 */           out.print(FormatUtil.getString("Middleware/Portal"));
/*  466 */           out.write("</a></b></span> </td>\n                \n                </tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  468 */           for (int i = 0; i < rows9.size(); i++)
/*      */           {
/*  470 */             ArrayList row9 = (ArrayList)rows9.get(i);
/*  471 */             String res9 = (String)row9.get(0);
/*  472 */             String image9 = (String)row9.get(3);
/*  473 */             String dname9 = (String)row9.get(1);
/*      */             
/*      */ 
/*  476 */             out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  477 */             out.print(image9);
/*  478 */             out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  479 */             out.print(res9);
/*  480 */             out.write("&detailspage=true\" class=\"bodytext\">");
/*  481 */             out.print(FormatUtil.getString(dname9));
/*  482 */             out.write("</a></td>\n          \n       \n                \n                ");
/*      */           }
/*  484 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  486 */         out.write("\n\n\t\t");
/*  487 */         String query10 = "SELECT min(RESOURCETYPE),min(DISPLAYNAME),SUBGROUP,min(IMAGEPATH) FROM AM_ManagedResourceType where RESOURCEGROUP='SYS' and RESOURCETYPE in " + Constants.resourceTypes + " GROUP BY SUBGROUP ORDER BY SUBGROUP";
/*      */         
/*  489 */         ArrayList rows10 = mo.getRows(query10);
/*  490 */         if (rows10.size() > 0)
/*      */         {
/*      */ 
/*      */ 
/*  494 */           out.write(" \t   <tr > \n                  <td height=\"27\"   class=\"yellowgrayborder\" >&nbsp;<span class=\"bodytext\"><b><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\" class=\"bodytextboldunderline\">");
/*  495 */           out.print(FormatUtil.getString("Servers"));
/*  496 */           out.write("</a></b></span> </td>\n                \n                </tr>\n                    <tr><td><table width='100%'><tr>\n                 ");
/*      */           
/*  498 */           for (int i = 0; i < rows10.size(); i++)
/*      */           {
/*      */ 
/*  501 */             ArrayList row10 = (ArrayList)rows10.get(i);
/*  502 */             String res10 = (String)row10.get(0);
/*  503 */             String image10 = (String)row10.get(3);
/*  504 */             String dname10 = (String)row10.get(1);
/*  505 */             if (!res10.toLowerCase().contains("node"))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  510 */               if (dname10.toLowerCase().contains("windows"))
/*      */               {
/*  512 */                 dname10 = "Windows";
/*  513 */                 res10 = "Windows";
/*      */               }
/*      */               
/*  516 */               out.write("\n                         \n                         <td height='23' class='whitegrayborder' ><img src='../");
/*  517 */               out.print(image10);
/*  518 */               out.write("'  align=\"absmiddle\"><A HREF=\"/showresource.do?method=showResourceTypes&network=");
/*  519 */               out.print(res10);
/*  520 */               out.write("&detailspage=true\" class=\"bodytext\">");
/*  521 */               out.print(FormatUtil.getString(dname10));
/*  522 */               out.write("</a></td>\n          \n       \n                \n                ");
/*      */             } }
/*  524 */           out.write("</tr></table></td></tr>");
/*      */         }
/*  526 */         out.write("\n                \t\t\t\n\t\t\t\n               \n               \n              \n                <tr class=\"yellowgrayborder\"> \n                  <td height=\"23\"  class=\"bodytext\" colspan='8'>&nbsp;<a class=\"bodytextboldunderline\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM\">");
/*  527 */         out.print(FormatUtil.getString("am.monitortab.custommonitors.text"));
/*  528 */         out.write("</a></td>\n                  \n                </tr>\n            \n\t\t<tr> \n                  <td height=\"23\"  class=\"bodytext\" colspan='8'>&nbsp;<a class=\"bodytextboldunderline\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=Script Monitor\">");
/*  529 */         out.print(FormatUtil.getString("am.monitortab.scriptmonitors.text"));
/*  530 */         out.write("</a></td>\n                 \n                </tr>\n                ");
/*      */       }
/*      */       
/*      */ 
/*  534 */       out.write("\n              </table></td>\n          </tr>\n        </table>\n      </div>\n      <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td>&nbsp;</td>\n        </tr>\n      </table>\n      <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr>\n          <td width=\"72%\" height=\"31\" class=\"tableheading\" onClick=\"InvertDivState('Alert');\">&nbsp;&nbsp;<img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" id=\"AlertSign\">\n            <a href=\"/fault/AlarmView.do?displayName=Alerts\" class=\"staticlinks\">");
/*  535 */       out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  536 */       out.write("</a>\n          </td>\n        </tr>\n      </table>\n      <div id=\"AlertDiv\"  style=\"DISPLAY: block\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n          <tr >\n            <td width=\"40%\" height=\"23\"  class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/fault/AlarmView.do?displayName=Last one hour Alerts&viewId=Alerts.2\" class=\"bodytext\">");
/*  537 */       out.print(FormatUtil.getString("am.webclient.sitemap.last1hralerts.text"));
/*  538 */       out.write("</a></td>\n            <td width=\"32%\" height=\"23\"  class=\"bodytext\"><a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts.1&header=All Alerts - Critical\" class=\"bodytext\">");
/*  539 */       out.print(FormatUtil.getString("am.webclient.sitemap.onlycritical.text"));
/*  540 */       out.write("</a></td>\n            <td width=\"28%\" height=\"23\"><a href=\"/fault/AlarmView.do?displayName=Last one day Alerts&viewId=Alerts.4\" class=\"bodytext\">");
/*  541 */       out.print(FormatUtil.getString("am.webclient.sitemap.last1dayalerts.text"));
/*  542 */       out.write("</a> </td>\n          </tr>\n        </table>\n      </div>\n      <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td>&nbsp;</td>\n        </tr>\n      </table>\n      <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr>\n          <td width=\"72%\" height=\"31\" class=\"tableheading\" onClick=\"InvertDivState('Admin');\">&nbsp;&nbsp;<img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" id=\"AdminSign\">\n           \n           ");
/*      */       
/*  544 */       AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  545 */       _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  546 */       _jspx_th_am_005fadminlink_005f0.setParent(null);
/*      */       
/*  548 */       _jspx_th_am_005fadminlink_005f0.setHref("/showTile.do?TileName=Tile.AdminConf");
/*      */       
/*  550 */       _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/*  551 */       int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  552 */       if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  553 */         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  554 */           out = _jspx_page_context.pushBody();
/*  555 */           _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/*  556 */           _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */         }
/*      */         for (;;) {
/*  559 */           out.print(FormatUtil.getString("am.webclient.admintab.text"));
/*  560 */           out.write(32);
/*  561 */           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  562 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*  565 */         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  566 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/*  569 */       if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  570 */         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*      */       }
/*      */       else {
/*  573 */         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  574 */         out.write(" </td>\n        </tr>\n      </table>\n      <div id=\"AdminDiv\"  style=\"DISPLAY: block\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">\n          <tr>\n            <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n                <tr >\n                  <td width=\"39%\" height=\"23\"  class=\"whitegrayborder\">");
/*      */         
/*  576 */         AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  577 */         _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  578 */         _jspx_th_am_005fadminlink_005f1.setParent(null);
/*      */         
/*  580 */         _jspx_th_am_005fadminlink_005f1.setHref("/admin/createapplicationwiz.do");
/*      */         
/*  582 */         _jspx_th_am_005fadminlink_005f1.setEnableClass("bodytext");
/*      */         
/*  584 */         _jspx_th_am_005fadminlink_005f1.setAccess(110);
/*  585 */         int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  586 */         if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  587 */           if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  588 */             out = _jspx_page_context.pushBody();
/*  589 */             _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/*  590 */             _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */           }
/*      */           for (;;) {
/*  593 */             out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  594 */             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  595 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  598 */           if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  599 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  602 */         if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  603 */           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f1);
/*      */         }
/*      */         else {
/*  606 */           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f1);
/*  607 */           out.write("</td>\n                  <td width=\"33%\" height=\"23\"  class=\"whitegrayborder\">");
/*      */           
/*  609 */           AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  610 */           _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/*  611 */           _jspx_th_am_005fadminlink_005f2.setParent(null);
/*      */           
/*  613 */           _jspx_th_am_005fadminlink_005f2.setHref("/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999");
/*      */           
/*  615 */           _jspx_th_am_005fadminlink_005f2.setEnableClass("bodytext");
/*      */           
/*  617 */           _jspx_th_am_005fadminlink_005f2.setAccess(110);
/*  618 */           int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/*  619 */           if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/*  620 */             if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  621 */               out = _jspx_page_context.pushBody();
/*  622 */               _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/*  623 */               _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  626 */               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  627 */               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/*  628 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  631 */             if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  632 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  635 */           if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/*  636 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f2);
/*      */           }
/*      */           else {
/*  639 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f2);
/*  640 */             out.write("</td>\n                  <td width=\"28%\" height=\"23\" class=\"whitegrayborder\"><span class=\"bodytext\">");
/*      */             
/*  642 */             AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  643 */             _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/*  644 */             _jspx_th_am_005fadminlink_005f3.setParent(null);
/*      */             
/*  646 */             _jspx_th_am_005fadminlink_005f3.setHref("/showTile.do?TileName=.ThresholdConf");
/*      */             
/*  648 */             _jspx_th_am_005fadminlink_005f3.setEnableClass("bodytext");
/*      */             
/*  650 */             _jspx_th_am_005fadminlink_005f3.setAccess(110);
/*  651 */             int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/*  652 */             if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/*  653 */               if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  654 */                 out = _jspx_page_context.pushBody();
/*  655 */                 _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/*  656 */                 _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  659 */                 out.print(FormatUtil.getString("am.webclient.toolbar.newthresholdlink.text"));
/*  660 */                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/*  661 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  664 */               if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  665 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  668 */             if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/*  669 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f3);
/*      */             }
/*      */             else {
/*  672 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f3);
/*  673 */               out.write("</span></td>\n                </tr>\n                <tr >\n                  <td height=\"23\"  class=\"yellowgrayborder\">");
/*      */               
/*  675 */               AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  676 */               _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/*  677 */               _jspx_th_am_005fadminlink_005f4.setParent(null);
/*      */               
/*  679 */               _jspx_th_am_005fadminlink_005f4.setHref("/showTile.do?TileName=.EmailActions");
/*      */               
/*  681 */               _jspx_th_am_005fadminlink_005f4.setEnableClass("bodytext");
/*      */               
/*  683 */               _jspx_th_am_005fadminlink_005f4.setAccess(110);
/*  684 */               int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/*  685 */               if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/*  686 */                 if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/*  687 */                   out = _jspx_page_context.pushBody();
/*  688 */                   _jspx_th_am_005fadminlink_005f4.setBodyContent((BodyContent)out);
/*  689 */                   _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  692 */                   out.print(FormatUtil.getString("am.webclient.configurealert.newaction"));
/*  693 */                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/*  694 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  697 */                 if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/*  698 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  701 */               if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/*  702 */                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f4);
/*      */               }
/*      */               else {
/*  705 */                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f4);
/*  706 */                 out.write(" </td>\n\t\t\t\t\t<td height=\"23\"  class=\"yellowgrayborder\">\n\t\t\t\t\t");
/*      */                 
/*  708 */                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  709 */                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  710 */                 _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                 
/*  712 */                 _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/*  713 */                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  714 */                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                   for (;;) {
/*  716 */                     out.write("\n\t\t\t\t\t<a href=\"javascript:alertUser()\" class=\"bodytext\">");
/*  717 */                     out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  718 */                     out.write("</a>\n\t\t\t\t\t");
/*  719 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  720 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  724 */                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  725 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                 }
/*      */                 else {
/*  728 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  729 */                   out.write("\n\t\t\t\t\t");
/*      */                   
/*  731 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  732 */                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  733 */                   _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                   
/*  735 */                   _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  736 */                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  737 */                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                     for (;;) {
/*  739 */                       out.write("\n\t\t\t\t  ");
/*      */                       
/*  741 */                       AdminLink _jspx_th_am_005fadminlink_005f5 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  742 */                       _jspx_th_am_005fadminlink_005f5.setPageContext(_jspx_page_context);
/*  743 */                       _jspx_th_am_005fadminlink_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                       
/*  745 */                       _jspx_th_am_005fadminlink_005f5.setHref("/adminAction.do?method=showNetworkDiscoveryForm");
/*      */                       
/*  747 */                       _jspx_th_am_005fadminlink_005f5.setEnableClass("bodytext");
/*  748 */                       int _jspx_eval_am_005fadminlink_005f5 = _jspx_th_am_005fadminlink_005f5.doStartTag();
/*  749 */                       if (_jspx_eval_am_005fadminlink_005f5 != 0) {
/*  750 */                         if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  751 */                           out = _jspx_page_context.pushBody();
/*  752 */                           _jspx_th_am_005fadminlink_005f5.setBodyContent((BodyContent)out);
/*  753 */                           _jspx_th_am_005fadminlink_005f5.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  756 */                           out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  757 */                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f5.doAfterBody();
/*  758 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  761 */                         if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  762 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  765 */                       if (_jspx_th_am_005fadminlink_005f5.doEndTag() == 5) {
/*  766 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f5); return;
/*      */                       }
/*      */                       
/*  769 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f5);
/*  770 */                       out.write("\n\t\t\t\t  ");
/*  771 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  772 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  776 */                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  777 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                   }
/*      */                   else {
/*  780 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  781 */                     out.write("\n\t\t\t\t  </td>\n\t\t\t\t  <td height=\"23\"  class=\"yellowgrayborder\">\n\t\t\t\t  ");
/*      */                     
/*  783 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  784 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  785 */                     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                     
/*  787 */                     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/*  788 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  789 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/*  791 */                         out.write("\n\t\t\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/*  792 */                         out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  793 */                         out.write("</a>\n\t\t\t\t  ");
/*  794 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  795 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  799 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  800 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                     }
/*      */                     else {
/*  803 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  804 */                       out.write("\n\t\t\t\t  ");
/*      */                       
/*  806 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  807 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  808 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                       
/*  810 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  811 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  812 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  814 */                           out.write("\n\t\t\t\t  ");
/*      */                           
/*  816 */                           AdminLink _jspx_th_am_005fadminlink_005f6 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  817 */                           _jspx_th_am_005fadminlink_005f6.setPageContext(_jspx_page_context);
/*  818 */                           _jspx_th_am_005fadminlink_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                           
/*  820 */                           _jspx_th_am_005fadminlink_005f6.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                           
/*  822 */                           _jspx_th_am_005fadminlink_005f6.setEnableClass("bodytext");
/*  823 */                           int _jspx_eval_am_005fadminlink_005f6 = _jspx_th_am_005fadminlink_005f6.doStartTag();
/*  824 */                           if (_jspx_eval_am_005fadminlink_005f6 != 0) {
/*  825 */                             if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/*  826 */                               out = _jspx_page_context.pushBody();
/*  827 */                               _jspx_th_am_005fadminlink_005f6.setBodyContent((BodyContent)out);
/*  828 */                               _jspx_th_am_005fadminlink_005f6.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  831 */                               out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  832 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f6.doAfterBody();
/*  833 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  836 */                             if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/*  837 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  840 */                           if (_jspx_th_am_005fadminlink_005f6.doEndTag() == 5) {
/*  841 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f6); return;
/*      */                           }
/*      */                           
/*  844 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f6);
/*  845 */                           out.write("\n\t\t\t\t  ");
/*  846 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  847 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  851 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  852 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                       }
/*      */                       else {
/*  855 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  856 */                         out.write("\n\t\t\t\t  </td>\n                </tr>\n                <tr >\n\t\t\t\t  <td height=\"23\"  class=\"whitegrayborder\">");
/*      */                         
/*  858 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  859 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  860 */                         _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                         
/*  862 */                         _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/*  863 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  864 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/*  866 */                             out.write("<a href=\"javascript:alertUser()\" class=\"bodytext\">");
/*  867 */                             out.print(FormatUtil.getString("am.webclient.admin.proxy.configureproxyserver.text"));
/*  868 */                             out.write("</a> ");
/*  869 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  870 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  874 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  875 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*      */                         }
/*      */                         else {
/*  878 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  879 */                           out.write(32);
/*      */                           
/*  881 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  882 */                           _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  883 */                           _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                           
/*  885 */                           _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/*  886 */                           int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  887 */                           if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                             for (;;) {
/*  889 */                               out.write(" \n                    ");
/*      */                               
/*  891 */                               AdminLink _jspx_th_am_005fadminlink_005f7 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  892 */                               _jspx_th_am_005fadminlink_005f7.setPageContext(_jspx_page_context);
/*  893 */                               _jspx_th_am_005fadminlink_005f7.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                               
/*  895 */                               _jspx_th_am_005fadminlink_005f7.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                               
/*  897 */                               _jspx_th_am_005fadminlink_005f7.setEnableClass("bodytext");
/*  898 */                               int _jspx_eval_am_005fadminlink_005f7 = _jspx_th_am_005fadminlink_005f7.doStartTag();
/*  899 */                               if (_jspx_eval_am_005fadminlink_005f7 != 0) {
/*  900 */                                 if (_jspx_eval_am_005fadminlink_005f7 != 1) {
/*  901 */                                   out = _jspx_page_context.pushBody();
/*  902 */                                   _jspx_th_am_005fadminlink_005f7.setBodyContent((BodyContent)out);
/*  903 */                                   _jspx_th_am_005fadminlink_005f7.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/*  906 */                                   out.print(FormatUtil.getString("am.webclient.admin.proxy.configureproxyserver.text"));
/*  907 */                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f7.doAfterBody();
/*  908 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*  911 */                                 if (_jspx_eval_am_005fadminlink_005f7 != 1) {
/*  912 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/*  915 */                               if (_jspx_th_am_005fadminlink_005f7.doEndTag() == 5) {
/*  916 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f7); return;
/*      */                               }
/*      */                               
/*  919 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f7);
/*  920 */                               out.write(32);
/*  921 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  922 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  926 */                           if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  927 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*      */                           }
/*      */                           else {
/*  930 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  931 */                             out.write(" </td>\n\t\t\t\t  <td height=\"23\"  class=\"whitegrayborder\">\n\t\t\t\t  ");
/*      */                             
/*  933 */                             PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  934 */                             _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  935 */                             _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                             
/*  937 */                             _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/*  938 */                             int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  939 */                             if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                               for (;;) {
/*  941 */                                 out.write("\n\t\t\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/*  942 */                                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  943 */                                 out.write("</a>\n\t\t\t\t  ");
/*  944 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  945 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  949 */                             if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  950 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                             }
/*      */                             else {
/*  953 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  954 */                               out.write("\n\t\t\t\t  ");
/*      */                               
/*  956 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  957 */                               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  958 */                               _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */                               
/*  960 */                               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/*  961 */                               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  962 */                               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                 for (;;) {
/*  964 */                                   out.write("\n\t\t\t\t  ");
/*      */                                   
/*  966 */                                   AdminLink _jspx_th_am_005fadminlink_005f8 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  967 */                                   _jspx_th_am_005fadminlink_005f8.setPageContext(_jspx_page_context);
/*  968 */                                   _jspx_th_am_005fadminlink_005f8.setParent(_jspx_th_logic_005fnotPresent_005f3);
/*      */                                   
/*  970 */                                   _jspx_th_am_005fadminlink_005f8.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                                   
/*  972 */                                   _jspx_th_am_005fadminlink_005f8.setEnableClass("bodytext");
/*  973 */                                   int _jspx_eval_am_005fadminlink_005f8 = _jspx_th_am_005fadminlink_005f8.doStartTag();
/*  974 */                                   if (_jspx_eval_am_005fadminlink_005f8 != 0) {
/*  975 */                                     if (_jspx_eval_am_005fadminlink_005f8 != 1) {
/*  976 */                                       out = _jspx_page_context.pushBody();
/*  977 */                                       _jspx_th_am_005fadminlink_005f8.setBodyContent((BodyContent)out);
/*  978 */                                       _jspx_th_am_005fadminlink_005f8.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  981 */                                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  982 */                                       out.write(32);
/*  983 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f8.doAfterBody();
/*  984 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  987 */                                     if (_jspx_eval_am_005fadminlink_005f8 != 1) {
/*  988 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  991 */                                   if (_jspx_th_am_005fadminlink_005f8.doEndTag() == 5) {
/*  992 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f8); return;
/*      */                                   }
/*      */                                   
/*  995 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f8);
/*  996 */                                   out.write("\n\t\t\t\t  ");
/*  997 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  998 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1002 */                               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 1003 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*      */                               }
/*      */                               else {
/* 1006 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 1007 */                                 out.write("\n\t\t\t\t  </td>\n\t\t\t\t  <td height=\"23\" class=\"whitegrayborder\">\n\t\t\t\t  ");
/*      */                                 
/* 1009 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1010 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 1011 */                                 _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                                 
/* 1013 */                                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 1014 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 1015 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 1017 */                                     out.write("\n\t\t\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1018 */                                     out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1019 */                                     out.write("</a>\n\t\t\t\t  ");
/* 1020 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 1021 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1025 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 1026 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                                 }
/*      */                                 else {
/* 1029 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 1030 */                                   out.write("\n\t\t\t\t  ");
/*      */                                   
/* 1032 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1033 */                                   _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 1034 */                                   _jspx_th_logic_005fnotPresent_005f4.setParent(null);
/*      */                                   
/* 1036 */                                   _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 1037 */                                   int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 1038 */                                   if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 1040 */                                       out.write("\n\t\t\t\t  ");
/*      */                                       
/* 1042 */                                       AdminLink _jspx_th_am_005fadminlink_005f9 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1043 */                                       _jspx_th_am_005fadminlink_005f9.setPageContext(_jspx_page_context);
/* 1044 */                                       _jspx_th_am_005fadminlink_005f9.setParent(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                       
/* 1046 */                                       _jspx_th_am_005fadminlink_005f9.setHref("/Upload.do");
/*      */                                       
/* 1048 */                                       _jspx_th_am_005fadminlink_005f9.setEnableClass("bodytext");
/* 1049 */                                       int _jspx_eval_am_005fadminlink_005f9 = _jspx_th_am_005fadminlink_005f9.doStartTag();
/* 1050 */                                       if (_jspx_eval_am_005fadminlink_005f9 != 0) {
/* 1051 */                                         if (_jspx_eval_am_005fadminlink_005f9 != 1) {
/* 1052 */                                           out = _jspx_page_context.pushBody();
/* 1053 */                                           _jspx_th_am_005fadminlink_005f9.setBodyContent((BodyContent)out);
/* 1054 */                                           _jspx_th_am_005fadminlink_005f9.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 1057 */                                           out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1058 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f9.doAfterBody();
/* 1059 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 1062 */                                         if (_jspx_eval_am_005fadminlink_005f9 != 1) {
/* 1063 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 1066 */                                       if (_jspx_th_am_005fadminlink_005f9.doEndTag() == 5) {
/* 1067 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f9); return;
/*      */                                       }
/*      */                                       
/* 1070 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f9);
/* 1071 */                                       out.write("\n\t\t\t\t  ");
/* 1072 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1073 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1077 */                                   if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1078 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/*      */                                   }
/*      */                                   else {
/* 1081 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1082 */                                     out.write("\n\t\t\t\t  </td>\n                </tr>\n                <tr >\n                  <td height=\"23\"  class=\"yellowgrayborder\"><a href=\"/Upload.do\" class=\"bodytext\"> \n                    </a><a href=\"/common/serverinfo.do\" class=\"bodytext\">");
/* 1083 */                                     out.print(FormatUtil.getString("am.webclient.support.productinfo"));
/* 1084 */                                     out.write("</a></td>\n                 <td height=\"23\"  class=\"yellowgrayborder\" >\n                    ");
/*      */                                     
/* 1086 */                                     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1087 */                                     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 1088 */                                     _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                                     
/* 1090 */                                     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 1091 */                                     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 1092 */                                     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                       for (;;) {
/* 1094 */                                         out.write("\n\t\t\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1095 */                                         out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1096 */                                         out.write("</a>\n\t\t\t\t  ");
/* 1097 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 1098 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 1102 */                                     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 1103 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                                     }
/*      */                                     else {
/* 1106 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 1107 */                                       out.write("\n\t\t\t\t  ");
/*      */                                       
/* 1109 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1110 */                                       _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 1111 */                                       _jspx_th_logic_005fnotPresent_005f5.setParent(null);
/*      */                                       
/* 1113 */                                       _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 1114 */                                       int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 1115 */                                       if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                         for (;;) {
/* 1117 */                                           out.write("\n\t\t\t\t  ");
/*      */                                           
/* 1119 */                                           AdminLink _jspx_th_am_005fadminlink_005f10 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1120 */                                           _jspx_th_am_005fadminlink_005f10.setPageContext(_jspx_page_context);
/* 1121 */                                           _jspx_th_am_005fadminlink_005f10.setParent(_jspx_th_logic_005fnotPresent_005f5);
/*      */                                           
/* 1123 */                                           _jspx_th_am_005fadminlink_005f10.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert");
/*      */                                           
/* 1125 */                                           _jspx_th_am_005fadminlink_005f10.setEnableClass("bodytext");
/* 1126 */                                           int _jspx_eval_am_005fadminlink_005f10 = _jspx_th_am_005fadminlink_005f10.doStartTag();
/* 1127 */                                           if (_jspx_eval_am_005fadminlink_005f10 != 0) {
/* 1128 */                                             if (_jspx_eval_am_005fadminlink_005f10 != 1) {
/* 1129 */                                               out = _jspx_page_context.pushBody();
/* 1130 */                                               _jspx_th_am_005fadminlink_005f10.setBodyContent((BodyContent)out);
/* 1131 */                                               _jspx_th_am_005fadminlink_005f10.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 1134 */                                               out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1135 */                                               out.write(32);
/* 1136 */                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f10.doAfterBody();
/* 1137 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 1140 */                                             if (_jspx_eval_am_005fadminlink_005f10 != 1) {
/* 1141 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 1144 */                                           if (_jspx_th_am_005fadminlink_005f10.doEndTag() == 5) {
/* 1145 */                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f10); return;
/*      */                                           }
/*      */                                           
/* 1148 */                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f10);
/* 1149 */                                           out.write("\n\t\t\t\t  ");
/* 1150 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 1151 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 1155 */                                       if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 1156 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/*      */                                       }
/*      */                                       else {
/* 1159 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 1160 */                                         out.write("\n\n                  </td>\n                   \n<td height=\"23\" class=\"yellowgrayborder\">\n");
/*      */                                         
/* 1162 */                                         PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1163 */                                         _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 1164 */                                         _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */                                         
/* 1166 */                                         _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 1167 */                                         int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 1168 */                                         if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                           for (;;) {
/* 1170 */                                             out.write("\n<a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1171 */                                             out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1172 */                                             out.write("</a>\n");
/* 1173 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 1174 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 1178 */                                         if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 1179 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                                         }
/*      */                                         else {
/* 1182 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 1183 */                                           out.write(10);
/*      */                                           
/* 1185 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1186 */                                           _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 1187 */                                           _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */                                           
/* 1189 */                                           _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 1190 */                                           int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 1191 */                                           if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                             for (;;) {
/* 1193 */                                               out.write("\n\t\t\t\t  ");
/*      */                                               
/* 1195 */                                               AdminLink _jspx_th_am_005fadminlink_005f11 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1196 */                                               _jspx_th_am_005fadminlink_005f11.setPageContext(_jspx_page_context);
/* 1197 */                                               _jspx_th_am_005fadminlink_005f11.setParent(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                               
/* 1199 */                                               _jspx_th_am_005fadminlink_005f11.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                               
/* 1201 */                                               _jspx_th_am_005fadminlink_005f11.setEnableClass("bodytext");
/* 1202 */                                               int _jspx_eval_am_005fadminlink_005f11 = _jspx_th_am_005fadminlink_005f11.doStartTag();
/* 1203 */                                               if (_jspx_eval_am_005fadminlink_005f11 != 0) {
/* 1204 */                                                 if (_jspx_eval_am_005fadminlink_005f11 != 1) {
/* 1205 */                                                   out = _jspx_page_context.pushBody();
/* 1206 */                                                   _jspx_th_am_005fadminlink_005f11.setBodyContent((BodyContent)out);
/* 1207 */                                                   _jspx_th_am_005fadminlink_005f11.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 1210 */                                                   out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1211 */                                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f11.doAfterBody();
/* 1212 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 1215 */                                                 if (_jspx_eval_am_005fadminlink_005f11 != 1) {
/* 1216 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 1219 */                                               if (_jspx_th_am_005fadminlink_005f11.doEndTag() == 5) {
/* 1220 */                                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f11); return;
/*      */                                               }
/*      */                                               
/* 1223 */                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f11);
/* 1224 */                                               out.write("\n\t\t\t\t\t");
/* 1225 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 1226 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 1230 */                                           if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 1231 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/*      */                                           }
/*      */                                           else {
/* 1234 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 1235 */                                             out.write("\n\t\t\t\t\t</td>\n                </tr>\n\t\t<tr>\n\t\t  <td height=\"23\"  class=\"whitegrayborder\">");
/*      */                                             
/* 1237 */                                             PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1238 */                                             _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 1239 */                                             _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */                                             
/* 1241 */                                             _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 1242 */                                             int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 1243 */                                             if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                               for (;;) {
/* 1245 */                                                 out.write("<a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1246 */                                                 out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1247 */                                                 out.write("</a> ");
/* 1248 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 1249 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 1253 */                                             if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 1254 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                                             }
/*      */                                             else {
/* 1257 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 1258 */                                               out.write(32);
/*      */                                               
/* 1260 */                                               NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1261 */                                               _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 1262 */                                               _jspx_th_logic_005fnotPresent_005f7.setParent(null);
/*      */                                               
/* 1264 */                                               _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 1265 */                                               int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 1266 */                                               if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                                 for (;;) {
/* 1268 */                                                   out.write(" \n\t\t\t");
/*      */                                                   
/* 1270 */                                                   AdminLink _jspx_th_am_005fadminlink_005f12 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1271 */                                                   _jspx_th_am_005fadminlink_005f12.setPageContext(_jspx_page_context);
/* 1272 */                                                   _jspx_th_am_005fadminlink_005f12.setParent(_jspx_th_logic_005fnotPresent_005f7);
/*      */                                                   
/* 1274 */                                                   _jspx_th_am_005fadminlink_005f12.setHref("/downTimeScheduler.do?method=maintenanceTaskListView");
/*      */                                                   
/* 1276 */                                                   _jspx_th_am_005fadminlink_005f12.setEnableClass("bodytext");
/* 1277 */                                                   int _jspx_eval_am_005fadminlink_005f12 = _jspx_th_am_005fadminlink_005f12.doStartTag();
/* 1278 */                                                   if (_jspx_eval_am_005fadminlink_005f12 != 0) {
/* 1279 */                                                     if (_jspx_eval_am_005fadminlink_005f12 != 1) {
/* 1280 */                                                       out = _jspx_page_context.pushBody();
/* 1281 */                                                       _jspx_th_am_005fadminlink_005f12.setBodyContent((BodyContent)out);
/* 1282 */                                                       _jspx_th_am_005fadminlink_005f12.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 1285 */                                                       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1286 */                                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f12.doAfterBody();
/* 1287 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 1290 */                                                     if (_jspx_eval_am_005fadminlink_005f12 != 1) {
/* 1291 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 1294 */                                                   if (_jspx_th_am_005fadminlink_005f12.doEndTag() == 5) {
/* 1295 */                                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f12); return;
/*      */                                                   }
/*      */                                                   
/* 1298 */                                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f12);
/* 1299 */                                                   out.write(32);
/* 1300 */                                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 1301 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 1305 */                                               if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 1306 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/*      */                                               }
/*      */                                               else {
/* 1309 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 1310 */                                                 out.write(" </td>");
/* 1311 */                                                 out.write("\n\t\t  <td height=\"23\"  class=\"whitegrayborder\">\n\t\t  ");
/*      */                                                 
/* 1313 */                                                 PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1314 */                                                 _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 1315 */                                                 _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                                                 
/* 1317 */                                                 _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 1318 */                                                 int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 1319 */                                                 if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                                   for (;;) {
/* 1321 */                                                     out.write("\n\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1322 */                                                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1323 */                                                     out.write("</a>\n\t\t  ");
/* 1324 */                                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 1325 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 1329 */                                                 if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 1330 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/*      */                                                 }
/*      */                                                 else {
/* 1333 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 1334 */                                                   out.write("\n\t\t  ");
/*      */                                                   
/* 1336 */                                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1337 */                                                   _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 1338 */                                                   _jspx_th_logic_005fnotPresent_005f8.setParent(null);
/*      */                                                   
/* 1340 */                                                   _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 1341 */                                                   int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 1342 */                                                   if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                                                     for (;;) {
/* 1344 */                                                       out.write("\n\t\t  ");
/*      */                                                       
/* 1346 */                                                       AdminLink _jspx_th_am_005fadminlink_005f13 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1347 */                                                       _jspx_th_am_005fadminlink_005f13.setPageContext(_jspx_page_context);
/* 1348 */                                                       _jspx_th_am_005fadminlink_005f13.setParent(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                                       
/* 1350 */                                                       _jspx_th_am_005fadminlink_005f13.setHref("/adminAction.do?method=listTrapListener");
/*      */                                                       
/* 1352 */                                                       _jspx_th_am_005fadminlink_005f13.setEnableClass("bodytext");
/* 1353 */                                                       int _jspx_eval_am_005fadminlink_005f13 = _jspx_th_am_005fadminlink_005f13.doStartTag();
/* 1354 */                                                       if (_jspx_eval_am_005fadminlink_005f13 != 0) {
/* 1355 */                                                         if (_jspx_eval_am_005fadminlink_005f13 != 1) {
/* 1356 */                                                           out = _jspx_page_context.pushBody();
/* 1357 */                                                           _jspx_th_am_005fadminlink_005f13.setBodyContent((BodyContent)out);
/* 1358 */                                                           _jspx_th_am_005fadminlink_005f13.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 1361 */                                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1362 */                                                           out.write(32);
/* 1363 */                                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f13.doAfterBody();
/* 1364 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 1367 */                                                         if (_jspx_eval_am_005fadminlink_005f13 != 1) {
/* 1368 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 1371 */                                                       if (_jspx_th_am_005fadminlink_005f13.doEndTag() == 5) {
/* 1372 */                                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f13); return;
/*      */                                                       }
/*      */                                                       
/* 1375 */                                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f13);
/* 1376 */                                                       out.write("\n\t\t  ");
/* 1377 */                                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 1378 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/*      */                                                   }
/* 1382 */                                                   if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 1383 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                                   }
/*      */                                                   else {
/* 1386 */                                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 1387 */                                                     out.write("\n\t\t  </td>\n\t\t  <td height=\"23\" class=\"whitegrayborder\">\n\t\t  ");
/*      */                                                     
/* 1389 */                                                     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1390 */                                                     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 1391 */                                                     _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                                                     
/* 1393 */                                                     _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 1394 */                                                     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 1395 */                                                     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                                       for (;;) {
/* 1397 */                                                         out.write("\n\t\t  <a href=\"javascript:alertUser()\" class=\"bodytext\">");
/* 1398 */                                                         out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1399 */                                                         out.write("</a>\n\t\t  ");
/* 1400 */                                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 1401 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 1405 */                                                     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 1406 */                                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/*      */                                                     }
/*      */                                                     else {
/* 1409 */                                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 1410 */                                                       out.write("\n\t\t  ");
/*      */                                                       
/* 1412 */                                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1413 */                                                       _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 1414 */                                                       _jspx_th_logic_005fnotPresent_005f9.setParent(null);
/*      */                                                       
/* 1416 */                                                       _jspx_th_logic_005fnotPresent_005f9.setRole("DEMO");
/* 1417 */                                                       int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 1418 */                                                       if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */                                                         for (;;) {
/* 1420 */                                                           out.write("\n\t\t  ");
/*      */                                                           
/* 1422 */                                                           AdminLink _jspx_th_am_005fadminlink_005f14 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1423 */                                                           _jspx_th_am_005fadminlink_005f14.setPageContext(_jspx_page_context);
/* 1424 */                                                           _jspx_th_am_005fadminlink_005f14.setParent(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                                           
/* 1426 */                                                           _jspx_th_am_005fadminlink_005f14.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                                                           
/* 1428 */                                                           _jspx_th_am_005fadminlink_005f14.setEnableClass("bodytext");
/* 1429 */                                                           int _jspx_eval_am_005fadminlink_005f14 = _jspx_th_am_005fadminlink_005f14.doStartTag();
/* 1430 */                                                           if (_jspx_eval_am_005fadminlink_005f14 != 0) {
/* 1431 */                                                             if (_jspx_eval_am_005fadminlink_005f14 != 1) {
/* 1432 */                                                               out = _jspx_page_context.pushBody();
/* 1433 */                                                               _jspx_th_am_005fadminlink_005f14.setBodyContent((BodyContent)out);
/* 1434 */                                                               _jspx_th_am_005fadminlink_005f14.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 1437 */                                                               out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1438 */                                                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f14.doAfterBody();
/* 1439 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 1442 */                                                             if (_jspx_eval_am_005fadminlink_005f14 != 1) {
/* 1443 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 1446 */                                                           if (_jspx_th_am_005fadminlink_005f14.doEndTag() == 5) {
/* 1447 */                                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f14); return;
/*      */                                                           }
/*      */                                                           
/* 1450 */                                                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f14);
/* 1451 */                                                           out.write("\n\t\t  ");
/* 1452 */                                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 1453 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/*      */                                                       }
/* 1457 */                                                       if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 1458 */                                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/*      */                                                       }
/*      */                                                       else {
/* 1461 */                                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 1462 */                                                         out.write("\n\t\t  </td>\n\t\t</tr>\n\t\t");
/*      */                                                         
/* 1464 */                                                         PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1465 */                                                         _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 1466 */                                                         _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */                                                         
/* 1468 */                                                         _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 1469 */                                                         int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 1470 */                                                         if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                                           for (;;) {
/* 1472 */                                                             out.write("\n\t\t<tr> \n\t\t       <td width=\"39%\" height=\"23\" class=\"yellowgrayborder\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance\" class=\"bodytext\">");
/* 1473 */                                                             out.print(FormatUtil.getString("am.webclient.admin.performancepoll.link"));
/* 1474 */                                                             out.write("</a></td>\n\t\t       <td width=\"33%\" height=\"23\" class=\"yellowgrayborder\">");
/*      */                                                             
/* 1476 */                                                             AdminLink _jspx_th_am_005fadminlink_005f15 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1477 */                                                             _jspx_th_am_005fadminlink_005f15.setPageContext(_jspx_page_context);
/* 1478 */                                                             _jspx_th_am_005fadminlink_005f15.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                                             
/* 1480 */                                                             _jspx_th_am_005fadminlink_005f15.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=gmapkey");
/*      */                                                             
/* 1482 */                                                             _jspx_th_am_005fadminlink_005f15.setEnableClass("staticlinks");
/* 1483 */                                                             int _jspx_eval_am_005fadminlink_005f15 = _jspx_th_am_005fadminlink_005f15.doStartTag();
/* 1484 */                                                             if (_jspx_eval_am_005fadminlink_005f15 != 0) {
/* 1485 */                                                               if (_jspx_eval_am_005fadminlink_005f15 != 1) {
/* 1486 */                                                                 out = _jspx_page_context.pushBody();
/* 1487 */                                                                 _jspx_th_am_005fadminlink_005f15.setBodyContent((BodyContent)out);
/* 1488 */                                                                 _jspx_th_am_005fadminlink_005f15.doInitBody();
/*      */                                                               }
/*      */                                                               for (;;) {
/* 1491 */                                                                 out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 1492 */                                                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f15.doAfterBody();
/* 1493 */                                                                 if (evalDoAfterBody != 2)
/*      */                                                                   break;
/*      */                                                               }
/* 1496 */                                                               if (_jspx_eval_am_005fadminlink_005f15 != 1) {
/* 1497 */                                                                 out = _jspx_page_context.popBody();
/*      */                                                               }
/*      */                                                             }
/* 1500 */                                                             if (_jspx_th_am_005fadminlink_005f15.doEndTag() == 5) {
/* 1501 */                                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f15); return;
/*      */                                                             }
/*      */                                                             
/* 1504 */                                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f15);
/* 1505 */                                                             out.write("</td>\n\t\t       <td width=\"28%\" height=\"23\" class=\"yellowgrayborder\">");
/*      */                                                             
/* 1507 */                                                             AdminLink _jspx_th_am_005fadminlink_005f16 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1508 */                                                             _jspx_th_am_005fadminlink_005f16.setPageContext(_jspx_page_context);
/* 1509 */                                                             _jspx_th_am_005fadminlink_005f16.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                                             
/* 1511 */                                                             _jspx_th_am_005fadminlink_005f16.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                                                             
/* 1513 */                                                             _jspx_th_am_005fadminlink_005f16.setEnableClass("bodytext");
/* 1514 */                                                             int _jspx_eval_am_005fadminlink_005f16 = _jspx_th_am_005fadminlink_005f16.doStartTag();
/* 1515 */                                                             if (_jspx_eval_am_005fadminlink_005f16 != 0) {
/* 1516 */                                                               if (_jspx_eval_am_005fadminlink_005f16 != 1) {
/* 1517 */                                                                 out = _jspx_page_context.pushBody();
/* 1518 */                                                                 _jspx_th_am_005fadminlink_005f16.setBodyContent((BodyContent)out);
/* 1519 */                                                                 _jspx_th_am_005fadminlink_005f16.doInitBody();
/*      */                                                               }
/*      */                                                               for (;;) {
/* 1522 */                                                                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1523 */                                                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f16.doAfterBody();
/* 1524 */                                                                 if (evalDoAfterBody != 2)
/*      */                                                                   break;
/*      */                                                               }
/* 1527 */                                                               if (_jspx_eval_am_005fadminlink_005f16 != 1) {
/* 1528 */                                                                 out = _jspx_page_context.popBody();
/*      */                                                               }
/*      */                                                             }
/* 1531 */                                                             if (_jspx_th_am_005fadminlink_005f16.doEndTag() == 5) {
/* 1532 */                                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f16); return;
/*      */                                                             }
/*      */                                                             
/* 1535 */                                                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f16);
/* 1536 */                                                             out.write("</td>\n                </tr>\n\t\t\t\t<tr>\n\t\t\t\t");
/*      */                                                             
/* 1538 */                                                             if (Constants.getCategorytype().equals("LAMP"))
/*      */                                                             {
/*      */ 
/* 1541 */                                                               out.write("\n\t\t\t<td width=\"28%\" height=\"23\" class=\"whitegrayborder\" colspan=\"3\">");
/*      */                                                               
/* 1543 */                                                               AdminLink _jspx_th_am_005fadminlink_005f17 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/* 1544 */                                                               _jspx_th_am_005fadminlink_005f17.setPageContext(_jspx_page_context);
/* 1545 */                                                               _jspx_th_am_005fadminlink_005f17.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                                               
/* 1547 */                                                               _jspx_th_am_005fadminlink_005f17.setHref("/adminAction.do?method=showSdeskConfiguration&admin=true");
/* 1548 */                                                               int _jspx_eval_am_005fadminlink_005f17 = _jspx_th_am_005fadminlink_005f17.doStartTag();
/* 1549 */                                                               if (_jspx_eval_am_005fadminlink_005f17 != 0) {
/* 1550 */                                                                 if (_jspx_eval_am_005fadminlink_005f17 != 1) {
/* 1551 */                                                                   out = _jspx_page_context.pushBody();
/* 1552 */                                                                   _jspx_th_am_005fadminlink_005f17.setBodyContent((BodyContent)out);
/* 1553 */                                                                   _jspx_th_am_005fadminlink_005f17.doInitBody();
/*      */                                                                 }
/*      */                                                                 for (;;) {
/* 1556 */                                                                   out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1557 */                                                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f17.doAfterBody();
/* 1558 */                                                                   if (evalDoAfterBody != 2)
/*      */                                                                     break;
/*      */                                                                 }
/* 1561 */                                                                 if (_jspx_eval_am_005fadminlink_005f17 != 1) {
/* 1562 */                                                                   out = _jspx_page_context.popBody();
/*      */                                                                 }
/*      */                                                               }
/* 1565 */                                                               if (_jspx_th_am_005fadminlink_005f17.doEndTag() == 5) {
/* 1566 */                                                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f17); return;
/*      */                                                               }
/*      */                                                               
/* 1569 */                                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f17);
/* 1570 */                                                               out.write("</td>\n                </tr>\n\t\t\t\t");
/*      */ 
/*      */                                                             }
/* 1573 */                                                             else if (Constants.getCategorytype().equals("DATABASE"))
/*      */                                                             {
/*      */ 
/* 1576 */                                                               out.write("\n\t\t\t<td width=\"39%\" height=\"23\" class=\"whitegrayborder\"><a href=\"/eventlogrules.do?method=view\" class=\"bodytext\" onClick=\"return checkforwindows()\"> \n                  ");
/* 1577 */                                                               out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.link"));
/* 1578 */                                                               out.write("</a></td>\n\t\t\t<td width=\"28%\" height=\"23\" class=\"whitegrayborder\" colspan=\"2\">");
/*      */                                                               
/* 1580 */                                                               AdminLink _jspx_th_am_005fadminlink_005f18 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/* 1581 */                                                               _jspx_th_am_005fadminlink_005f18.setPageContext(_jspx_page_context);
/* 1582 */                                                               _jspx_th_am_005fadminlink_005f18.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                                               
/* 1584 */                                                               _jspx_th_am_005fadminlink_005f18.setHref("/adminAction.do?method=showSdeskConfiguration&admin=true");
/* 1585 */                                                               int _jspx_eval_am_005fadminlink_005f18 = _jspx_th_am_005fadminlink_005f18.doStartTag();
/* 1586 */                                                               if (_jspx_eval_am_005fadminlink_005f18 != 0) {
/* 1587 */                                                                 if (_jspx_eval_am_005fadminlink_005f18 != 1) {
/* 1588 */                                                                   out = _jspx_page_context.pushBody();
/* 1589 */                                                                   _jspx_th_am_005fadminlink_005f18.setBodyContent((BodyContent)out);
/* 1590 */                                                                   _jspx_th_am_005fadminlink_005f18.doInitBody();
/*      */                                                                 }
/*      */                                                                 for (;;) {
/* 1593 */                                                                   out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1594 */                                                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f18.doAfterBody();
/* 1595 */                                                                   if (evalDoAfterBody != 2)
/*      */                                                                     break;
/*      */                                                                 }
/* 1598 */                                                                 if (_jspx_eval_am_005fadminlink_005f18 != 1) {
/* 1599 */                                                                   out = _jspx_page_context.popBody();
/*      */                                                                 }
/*      */                                                               }
/* 1602 */                                                               if (_jspx_th_am_005fadminlink_005f18.doEndTag() == 5) {
/* 1603 */                                                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f18); return;
/*      */                                                               }
/*      */                                                               
/* 1606 */                                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f18);
/* 1607 */                                                               out.write("</td>\n                </tr>\n\t\t\t\t");
/*      */ 
/*      */                                                             }
/*      */                                                             else
/*      */                                                             {
/*      */ 
/* 1613 */                                                               out.write("\n\t\t\t\t");
/* 1614 */                                                               if (!OEMUtil.isRemove("am.wmimonitors.remove")) {
/* 1615 */                                                                 out.write("\n\t\t\t<td width=\"39%\" height=\"23\" class=\"whitegrayborder\"><a href=\"/eventlogrules.do?method=view\" class=\"bodytext\" onClick=\"return checkforwindows()\"> \n                  ");
/* 1616 */                                                                 out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.link"));
/* 1617 */                                                                 out.write("</a></td>\n                  "); }
/* 1618 */                                                               if (!OEMUtil.isRemove("am.managerrole.remove")) {
/* 1619 */                                                                 out.write("\n\t\t\t<td width=\"33%\" height=\"23\" class=\"whitegrayborder\"><a href=\"/jsp/GettingStartedForManager.jsp\" class=\"bodytext\"> \n                  ");
/* 1620 */                                                                 out.print(FormatUtil.getString("am.webclient.admin.managerconsole.link"));
/* 1621 */                                                                 out.write("</a></td>\n                  "); }
/* 1622 */                                                               if (!OEMUtil.isRemove("am.addonproducts.remove")) {
/* 1623 */                                                                 out.write("\n\t\t\t<td width=\"28%\" height=\"23\" class=\"whitegrayborder\">");
/*      */                                                                 
/* 1625 */                                                                 AdminLink _jspx_th_am_005fadminlink_005f19 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.get(AdminLink.class);
/* 1626 */                                                                 _jspx_th_am_005fadminlink_005f19.setPageContext(_jspx_page_context);
/* 1627 */                                                                 _jspx_th_am_005fadminlink_005f19.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                                                 
/* 1629 */                                                                 _jspx_th_am_005fadminlink_005f19.setHref("/adminAction.do?method=showSdeskConfiguration&admin=true");
/* 1630 */                                                                 int _jspx_eval_am_005fadminlink_005f19 = _jspx_th_am_005fadminlink_005f19.doStartTag();
/* 1631 */                                                                 if (_jspx_eval_am_005fadminlink_005f19 != 0) {
/* 1632 */                                                                   if (_jspx_eval_am_005fadminlink_005f19 != 1) {
/* 1633 */                                                                     out = _jspx_page_context.pushBody();
/* 1634 */                                                                     _jspx_th_am_005fadminlink_005f19.setBodyContent((BodyContent)out);
/* 1635 */                                                                     _jspx_th_am_005fadminlink_005f19.doInitBody();
/*      */                                                                   }
/*      */                                                                   for (;;) {
/* 1638 */                                                                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1639 */                                                                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f19.doAfterBody();
/* 1640 */                                                                     if (evalDoAfterBody != 2)
/*      */                                                                       break;
/*      */                                                                   }
/* 1643 */                                                                   if (_jspx_eval_am_005fadminlink_005f19 != 1) {
/* 1644 */                                                                     out = _jspx_page_context.popBody();
/*      */                                                                   }
/*      */                                                                 }
/* 1647 */                                                                 if (_jspx_th_am_005fadminlink_005f19.doEndTag() == 5) {
/* 1648 */                                                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f19); return;
/*      */                                                                 }
/*      */                                                                 
/* 1651 */                                                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref.reuse(_jspx_th_am_005fadminlink_005f19);
/* 1652 */                                                                 out.write("</td>");
/*      */                                                               }
/* 1654 */                                                               out.write("\n                </tr>\n\t\t\t\t");
/*      */                                                             }
/*      */                                                             
/* 1657 */                                                             out.write(10);
/* 1658 */                                                             out.write(9);
/* 1659 */                                                             out.write(9);
/* 1660 */                                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 1661 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/*      */                                                         }
/* 1665 */                                                         if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 1666 */                                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                                         }
/*      */                                                         else {
/* 1669 */                                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 1670 */                                                           out.write("\n        </table></td>\n          </tr>\t\t\n        </table>\n      </div></td>\n  </tr>\n  <tr>\n    <td height=\"12%\" colspan=\"2\" align=\"center\" valign=\"middle\" class=\"arial10\">\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td align=\"center\" class=\"arial10\">&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n</body>\n</html>\n");
/*      */                                                         }
/* 1672 */                                                       } } } } } } } } } } } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1673 */         out = _jspx_out;
/* 1674 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1675 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1676 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1679 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sitemap_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */