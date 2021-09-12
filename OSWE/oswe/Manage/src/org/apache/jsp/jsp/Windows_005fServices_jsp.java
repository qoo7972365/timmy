/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class Windows_005fServices_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   60 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   63 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   64 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   65 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   72 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   77 */     ArrayList list = null;
/*   78 */     StringBuffer sbf = new StringBuffer();
/*   79 */     ManagedApplication mo = new ManagedApplication();
/*   80 */     if (distinct)
/*      */     {
/*   82 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   86 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   89 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   91 */       ArrayList row = (ArrayList)list.get(i);
/*   92 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   93 */       if (distinct) {
/*   94 */         sbf.append(row.get(0));
/*      */       } else
/*   96 */         sbf.append(row.get(1));
/*   97 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  100 */     return sbf.toString(); }
/*      */   
/*  102 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  105 */     if (severity == null)
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("5"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  113 */     if (severity.equals("1"))
/*      */     {
/*  115 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  120 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  127 */     if (severity == null)
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("1"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("4"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  139 */     if (severity.equals("5"))
/*      */     {
/*  141 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  146 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  152 */     if (severity == null)
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  156 */     if (severity.equals("5"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  160 */     if (severity.equals("1"))
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  166 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  172 */     if (severity == null)
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  176 */     if (severity.equals("1"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  180 */     if (severity.equals("4"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  184 */     if (severity.equals("5"))
/*      */     {
/*  186 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  190 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  196 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  202 */     if (severity == 5)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  206 */     if (severity == 1)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  213 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  219 */     if (severity == null)
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  223 */     if (severity.equals("5"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  232 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  234 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  236 */     if (severity.equals("1"))
/*      */     {
/*  238 */       if (isAvailability) {
/*  239 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  242 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  249 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  256 */     if (severity == null)
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("5"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("4"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("1"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  275 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  281 */     if (severity == null)
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("5"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("4"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  293 */     if (severity.equals("1"))
/*      */     {
/*  295 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  300 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  307 */     if (severity == null)
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  311 */     if (severity.equals("5"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  315 */     if (severity.equals("4"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  319 */     if (severity.equals("1"))
/*      */     {
/*  321 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  326 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  334 */     StringBuffer out = new StringBuffer();
/*  335 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  336 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  337 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  338 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  339 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  340 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  341 */     out.append("</tr>");
/*  342 */     out.append("</form></table>");
/*  343 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  350 */     if (val == null)
/*      */     {
/*  352 */       return "-";
/*      */     }
/*      */     
/*  355 */     String ret = FormatUtil.formatNumber(val);
/*  356 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  357 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  360 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  364 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  372 */     StringBuffer out = new StringBuffer();
/*  373 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  374 */     out.append("<tr>");
/*  375 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  377 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  379 */     out.append("</tr>");
/*  380 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  384 */       if (j % 2 == 0)
/*      */       {
/*  386 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  390 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  393 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  395 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  398 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  402 */       out.append("</tr>");
/*      */     }
/*  404 */     out.append("</table>");
/*  405 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  408 */     out.append("</tr>");
/*  409 */     out.append("</table>");
/*  410 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  416 */     StringBuffer out = new StringBuffer();
/*  417 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  418 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  421 */     out.append("<tr>");
/*  422 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  423 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  424 */     out.append("</tr>");
/*  425 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  428 */       out.append("<tr>");
/*  429 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  430 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  431 */       out.append("</tr>");
/*      */     }
/*      */     
/*  434 */     out.append("</table>");
/*  435 */     out.append("</table>");
/*  436 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  441 */     if (severity.equals("0"))
/*      */     {
/*  443 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  447 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  454 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session)
/*      */   {
/*  467 */     StringBuffer out = new StringBuffer();
/*  468 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  469 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  471 */       out.append("<tr>");
/*  472 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  473 */       out.append("</tr>");
/*      */       
/*      */ 
/*  476 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  478 */         String borderclass = "";
/*      */         
/*      */ 
/*  481 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  483 */         out.append("<tr>");
/*      */         
/*  485 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  486 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  487 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  493 */     out.append("</table><br>");
/*  494 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  495 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  497 */       List sLinks = secondLevelOfLinks[0];
/*  498 */       List sText = secondLevelOfLinks[1];
/*  499 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  502 */         out.append("<tr>");
/*  503 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  504 */         out.append("</tr>");
/*  505 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  507 */           String borderclass = "";
/*      */           
/*      */ 
/*  510 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  512 */           out.append("<tr>");
/*      */           
/*  514 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  515 */           if (sLinks.get(i).toString().length() == 0) {
/*  516 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  519 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  521 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  525 */     out.append("</table>");
/*  526 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  533 */     StringBuffer out = new StringBuffer();
/*  534 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  535 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  537 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  539 */         out.append("<tr>");
/*  540 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  541 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  545 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  547 */           String borderclass = "";
/*      */           
/*      */ 
/*  550 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  552 */           out.append("<tr>");
/*      */           
/*  554 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  555 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  556 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  559 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  562 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  567 */     out.append("</table><br>");
/*  568 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  569 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  571 */       List sLinks = secondLevelOfLinks[0];
/*  572 */       List sText = secondLevelOfLinks[1];
/*  573 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  576 */         out.append("<tr>");
/*  577 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  578 */         out.append("</tr>");
/*  579 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  581 */           String borderclass = "";
/*      */           
/*      */ 
/*  584 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  586 */           out.append("<tr>");
/*      */           
/*  588 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  589 */           if (sLinks.get(i).toString().length() == 0) {
/*  590 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  593 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  595 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  599 */     out.append("</table>");
/*  600 */     return out.toString();
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
/*      */   private String getSeverityClass(int status)
/*      */   {
/*  613 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  625 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  631 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  634 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  642 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  647 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  652 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  657 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  662 */     if (val != null)
/*      */     {
/*  664 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  668 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  673 */     if (val == null) {
/*  674 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  678 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  683 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  689 */     if (val != null)
/*      */     {
/*  691 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  695 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  701 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  710 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  715 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  720 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  725 */     String hostaddress = "";
/*  726 */     String ip = request.getHeader("x-forwarded-for");
/*  727 */     if (ip == null)
/*  728 */       ip = request.getRemoteAddr();
/*  729 */     InetAddress add = null;
/*  730 */     if (ip.equals("127.0.0.1")) {
/*  731 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  735 */       add = InetAddress.getByName(ip);
/*      */     }
/*  737 */     hostaddress = add.getHostName();
/*  738 */     if (hostaddress.indexOf('.') != -1) {
/*  739 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  740 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  744 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  749 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  755 */     if (severity == null)
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("5"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  763 */     if (severity.equals("1"))
/*      */     {
/*  765 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  770 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  775 */     ResultSet set = null;
/*  776 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  777 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  779 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  780 */       if (set.next()) { String str1;
/*  781 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  782 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  785 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  790 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  793 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  795 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  799 */     StringBuffer rca = new StringBuffer();
/*  800 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  801 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  804 */     int rcalength = key.length();
/*  805 */     String split = "6. ";
/*  806 */     int splitPresent = key.indexOf(split);
/*  807 */     String div1 = "";String div2 = "";
/*  808 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  810 */       if (rcalength > 180) {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         getRCATrimmedText(key, rca);
/*  813 */         rca.append("</span>");
/*      */       } else {
/*  815 */         rca.append("<span class=\"rca-critical-text\">");
/*  816 */         rca.append(key);
/*  817 */         rca.append("</span>");
/*      */       }
/*  819 */       return rca.toString();
/*      */     }
/*  821 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  822 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  823 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  824 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div1, rca);
/*  826 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  829 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  830 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  831 */     getRCATrimmedText(div2, rca);
/*  832 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  834 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  839 */     String[] st = msg.split("<br>");
/*  840 */     for (int i = 0; i < st.length; i++) {
/*  841 */       String s = st[i];
/*  842 */       if (s.length() > 180) {
/*  843 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  845 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  849 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  850 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  852 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  856 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  857 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  858 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  861 */       if (key == null) {
/*  862 */         return ret;
/*      */       }
/*      */       
/*  865 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  866 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  869 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  870 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  871 */       set = AMConnectionPool.executeQueryStmt(query);
/*  872 */       if (set.next())
/*      */       {
/*  874 */         String helpLink = set.getString("LINK");
/*  875 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  878 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  884 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*      */ 
/*      */ 
/*      */ 
/*  903 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  894 */         if (set != null) {
/*  895 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getStatus(List entitylist)
/*      */   {
/*  909 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  910 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  912 */       String entityStr = (String)keys.nextElement();
/*  913 */       String mmessage = temp.getProperty(entityStr);
/*  914 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  915 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  917 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  923 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  924 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  926 */       String entityStr = (String)keys.nextElement();
/*  927 */       String mmessage = temp.getProperty(entityStr);
/*  928 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  929 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  931 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  936 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  946 */     String des = new String();
/*  947 */     while (str.indexOf(find) != -1) {
/*  948 */       des = des + str.substring(0, str.indexOf(find));
/*  949 */       des = des + replace;
/*  950 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  952 */     des = des + str;
/*  953 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  960 */       if (alert == null)
/*      */       {
/*  962 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  964 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  966 */         return "&nbsp;";
/*      */       }
/*      */       
/*  969 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  971 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  974 */       int rcalength = test.length();
/*  975 */       if (rcalength < 300)
/*      */       {
/*  977 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  981 */       StringBuffer out = new StringBuffer();
/*  982 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  983 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  984 */       out.append("</div>");
/*  985 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  986 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  987 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  992 */       ex.printStackTrace();
/*      */     }
/*  994 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1000 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1005 */     ArrayList attribIDs = new ArrayList();
/* 1006 */     ArrayList resIDs = new ArrayList();
/* 1007 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1009 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1011 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1013 */       String resourceid = "";
/* 1014 */       String resourceType = "";
/* 1015 */       if (type == 2) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = (String)row.get(3);
/*      */       }
/* 1019 */       else if (type == 3) {
/* 1020 */         resourceid = (String)row.get(0);
/* 1021 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1024 */         resourceid = (String)row.get(6);
/* 1025 */         resourceType = (String)row.get(7);
/*      */       }
/* 1027 */       resIDs.add(resourceid);
/* 1028 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1029 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1031 */       String healthentity = null;
/* 1032 */       String availentity = null;
/* 1033 */       if (healthid != null) {
/* 1034 */         healthentity = resourceid + "_" + healthid;
/* 1035 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1038 */       if (availid != null) {
/* 1039 */         availentity = resourceid + "_" + availid;
/* 1040 */         entitylist.add(availentity);
/*      */       }
/*      */     }
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
/* 1054 */     Properties alert = getStatus(entitylist);
/* 1055 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1060 */     int size = monitorList.size();
/*      */     
/* 1062 */     String[] severity = new String[size];
/*      */     
/* 1064 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1066 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1067 */       String resourceName1 = (String)row1.get(7);
/* 1068 */       String resourceid1 = (String)row1.get(6);
/* 1069 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1070 */       if (severity[j] == null)
/*      */       {
/* 1072 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1076 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1078 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1080 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1083 */         if (sev > 0) {
/* 1084 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1085 */           monitorList.set(k, monitorList.get(j));
/* 1086 */           monitorList.set(j, t);
/* 1087 */           String temp = severity[k];
/* 1088 */           severity[k] = severity[j];
/* 1089 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1095 */     int z = 0;
/* 1096 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1099 */       int i = 0;
/* 1100 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1103 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1107 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1111 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1113 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1116 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1120 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1123 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1124 */       String resourceName1 = (String)row1.get(7);
/* 1125 */       String resourceid1 = (String)row1.get(6);
/* 1126 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1127 */       if (hseverity[j] == null)
/*      */       {
/* 1129 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1134 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1136 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1139 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1142 */         if (hsev > 0) {
/* 1143 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1144 */           monitorList.set(k, monitorList.get(j));
/* 1145 */           monitorList.set(j, t);
/* 1146 */           String temp1 = hseverity[k];
/* 1147 */           hseverity[k] = hseverity[j];
/* 1148 */           hseverity[j] = temp1;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*      */   {
/* 1160 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1161 */     boolean forInventory = false;
/* 1162 */     String trdisplay = "none";
/* 1163 */     String plusstyle = "inline";
/* 1164 */     String minusstyle = "none";
/* 1165 */     String haidTopLevel = "";
/* 1166 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1168 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1170 */         haidTopLevel = request.getParameter("haid");
/* 1171 */         forInventory = true;
/* 1172 */         trdisplay = "table-row;";
/* 1173 */         plusstyle = "none";
/* 1174 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1181 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1184 */     ArrayList listtoreturn = new ArrayList();
/* 1185 */     StringBuffer toreturn = new StringBuffer();
/* 1186 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1187 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1188 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1190 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1192 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1193 */       String childresid = (String)singlerow.get(0);
/* 1194 */       String childresname = (String)singlerow.get(1);
/* 1195 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1196 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1197 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1198 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1199 */       String unmanagestatus = (String)singlerow.get(5);
/* 1200 */       String actionstatus = (String)singlerow.get(6);
/* 1201 */       String linkclass = "monitorgp-links";
/* 1202 */       String titleforres = childresname;
/* 1203 */       String titilechildresname = childresname;
/* 1204 */       String childimg = "/images/trcont.png";
/* 1205 */       String flag = "enable";
/* 1206 */       String dcstarted = (String)singlerow.get(8);
/* 1207 */       String configMonitor = "";
/* 1208 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1209 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1211 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1213 */       if (singlerow.get(7) != null)
/*      */       {
/* 1215 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1217 */       String haiGroupType = "0";
/* 1218 */       if ("HAI".equals(childtype))
/*      */       {
/* 1220 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1222 */       childimg = "/images/trend.png";
/* 1223 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1224 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1225 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1227 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1229 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1231 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1232 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1235 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1237 */         linkclass = "disabledtext";
/* 1238 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1240 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String availmouseover = "";
/* 1242 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1244 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1246 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1247 */       String healthmouseover = "";
/* 1248 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1250 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1253 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1254 */       int spacing = 0;
/* 1255 */       if (level >= 1)
/*      */       {
/* 1257 */         spacing = 40 * level;
/*      */       }
/* 1259 */       if (childtype.equals("HAI"))
/*      */       {
/* 1261 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1262 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1263 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1265 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1266 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1267 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1268 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1269 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1270 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1271 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1272 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1273 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1274 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1275 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1277 */         if (!forInventory)
/*      */         {
/* 1279 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1282 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = editlink + actions;
/*      */         }
/* 1288 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1290 */           actions = actions + associatelink;
/*      */         }
/* 1292 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1293 */         String arrowimg = "";
/* 1294 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1296 */           actions = "";
/* 1297 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1298 */           checkbox = "";
/* 1299 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1301 */         if (isIt360)
/*      */         {
/* 1303 */           actionimg = "";
/* 1304 */           actions = "";
/* 1305 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1306 */           checkbox = "";
/*      */         }
/*      */         
/* 1309 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1311 */           actions = "";
/*      */         }
/* 1313 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1315 */           checkbox = "";
/*      */         }
/*      */         
/* 1318 */         String resourcelink = "";
/*      */         
/* 1320 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1329 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1334 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1335 */         if (!isIt360)
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1344 */         toreturn.append("</tr>");
/* 1345 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1347 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1348 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1352 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1353 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1356 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1360 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1362 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1364 */             toreturn.append(assocMessage);
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1368 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1374 */         String resourcelink = null;
/* 1375 */         boolean hideEditLink = false;
/* 1376 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1378 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1379 */           hideEditLink = true;
/* 1380 */           if (isIt360)
/*      */           {
/* 1382 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1386 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1388 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1390 */           hideEditLink = true;
/* 1391 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1392 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1397 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1400 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1401 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1402 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1403 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1404 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1405 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1406 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1407 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1408 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1409 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1410 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1411 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1412 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1414 */         if (hideEditLink)
/*      */         {
/* 1416 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1418 */         if (!forInventory)
/*      */         {
/* 1420 */           removefromgroup = "";
/*      */         }
/* 1422 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1423 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1424 */           actions = actions + configcustomfields;
/*      */         }
/* 1426 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1428 */           actions = editlink + actions;
/*      */         }
/* 1430 */         String managedLink = "";
/* 1431 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1433 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1434 */           actions = "";
/* 1435 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1436 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1439 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1441 */           checkbox = "";
/*      */         }
/*      */         
/* 1444 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1446 */           actions = "";
/*      */         }
/* 1448 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1451 */         if (isIt360)
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1460 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1461 */         if (!isIt360)
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1467 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1469 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1472 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1479 */       StringBuilder toreturn = new StringBuilder();
/* 1480 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1481 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1482 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1483 */       String title = "";
/* 1484 */       message = EnterpriseUtil.decodeString(message);
/* 1485 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1486 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1487 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1489 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1491 */       else if ("5".equals(severity))
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1497 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1499 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1500 */       toreturn.append(v);
/*      */       
/* 1502 */       toreturn.append(link);
/* 1503 */       if (severity == null)
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("5"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("4"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       else if (severity.equals("1"))
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1524 */       toreturn.append("</a>");
/* 1525 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1529 */       ex.printStackTrace();
/*      */     }
/* 1531 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1538 */       StringBuilder toreturn = new StringBuilder();
/* 1539 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1540 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1541 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1542 */       if (message == null)
/*      */       {
/* 1544 */         message = "";
/*      */       }
/*      */       
/* 1547 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1548 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1550 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1551 */       toreturn.append(v);
/*      */       
/* 1553 */       toreturn.append(link);
/*      */       
/* 1555 */       if (severity == null)
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("5"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       else if (severity.equals("1"))
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1572 */       toreturn.append("</a>");
/* 1573 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1579 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1582 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1583 */     if (invokeActions != null) {
/* 1584 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1585 */       while (iterator.hasNext()) {
/* 1586 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1587 */         if (actionmap.containsKey(actionid)) {
/* 1588 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1593 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1597 */     String actionLink = "";
/* 1598 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1599 */     String query = "";
/* 1600 */     ResultSet rs = null;
/* 1601 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1602 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1603 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1604 */       actionLink = "method=" + methodName;
/*      */     }
/* 1606 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */       actionLink = methodName;
/*      */     }
/* 1609 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1610 */     Iterator itr = methodarglist.iterator();
/* 1611 */     boolean isfirstparam = true;
/* 1612 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1613 */     while (itr.hasNext()) {
/* 1614 */       HashMap argmap = (HashMap)itr.next();
/* 1615 */       String argtype = (String)argmap.get("TYPE");
/* 1616 */       String argname = (String)argmap.get("IDENTITY");
/* 1617 */       String paramname = (String)argmap.get("PARAMETER");
/* 1618 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1619 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1620 */         isfirstparam = false;
/* 1621 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1623 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1627 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1631 */         actionLink = actionLink + "&";
/*      */       }
/* 1633 */       String paramValue = null;
/* 1634 */       String tempargname = argname;
/* 1635 */       if (commonValues.getProperty(tempargname) != null) {
/* 1636 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1639 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1640 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1641 */           if (dbType.equals("mysql")) {
/* 1642 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1645 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1647 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1649 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1650 */             if (rs.next()) {
/* 1651 */               paramValue = rs.getString("VALUE");
/* 1652 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1656 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1660 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1663 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1668 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1669 */           paramValue = rowId;
/*      */         }
/* 1671 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1672 */           paramValue = managedObjectName;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1675 */           paramValue = resID;
/*      */         }
/* 1677 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1678 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1681 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1683 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1684 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1685 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1687 */     return actionLink;
/*      */   }
/*      */   
/* 1690 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1691 */     String dependentAttribute = null;
/* 1692 */     String align = "left";
/*      */     
/* 1694 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1695 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1696 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1697 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1698 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1699 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1700 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1701 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1702 */       align = "center";
/*      */     }
/*      */     
/* 1705 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1706 */     String actualdata = "";
/*      */     
/* 1708 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1709 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1710 */         actualdata = availValue;
/*      */       }
/* 1712 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1713 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1717 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1718 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1721 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1727 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1728 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1729 */       toreturn.append("<table>");
/* 1730 */       toreturn.append("<tr>");
/* 1731 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1732 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1733 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1734 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1735 */         String toolTip = "";
/* 1736 */         String hideClass = "";
/* 1737 */         String textStyle = "";
/* 1738 */         boolean isreferenced = true;
/* 1739 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1740 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1741 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1742 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1744 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1745 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1746 */           while (valueList.hasMoreTokens()) {
/* 1747 */             String dependentVal = valueList.nextToken();
/* 1748 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1749 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1750 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1752 */               toolTip = "";
/* 1753 */               hideClass = "";
/* 1754 */               isreferenced = false;
/* 1755 */               textStyle = "disabledtext";
/* 1756 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1760 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1761 */           toolTip = "";
/* 1762 */           hideClass = "";
/* 1763 */           isreferenced = false;
/* 1764 */           textStyle = "disabledtext";
/* 1765 */           if (dependentImageMap != null) {
/* 1766 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1767 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1770 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1774 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1775 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1776 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1777 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1778 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1779 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1781 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1782 */           if (isreferenced) {
/* 1783 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1787 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1788 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1789 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1790 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1791 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1792 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1794 */           toreturn.append("</span>");
/* 1795 */           toreturn.append("</a>");
/* 1796 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1799 */       toreturn.append("</tr>");
/* 1800 */       toreturn.append("</table>");
/* 1801 */       toreturn.append("</td>");
/*      */     } else {
/* 1803 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1806 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1810 */     String colTime = null;
/* 1811 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1812 */     if ((rows != null) && (rows.size() > 0)) {
/* 1813 */       Iterator<String> itr = rows.iterator();
/* 1814 */       String maxColQuery = "";
/* 1815 */       for (;;) { if (itr.hasNext()) {
/* 1816 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1817 */           ResultSet maxCol = null;
/*      */           try {
/* 1819 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1820 */             while (maxCol.next()) {
/* 1821 */               if (colTime == null) {
/* 1822 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1825 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1834 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1834 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1836 */               if (maxCol != null)
/* 1837 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1839 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1844 */     return colTime;
/*      */   }
/*      */   
/* 1847 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1848 */     tablename = null;
/* 1849 */     ResultSet rsTable = null;
/* 1850 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1852 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1853 */       while (rsTable.next()) {
/* 1854 */         tablename = rsTable.getString("DATATABLE");
/* 1855 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1856 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1869 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1860 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1863 */         if (rsTable != null)
/* 1864 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1866 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1872 */     String argsList = "";
/* 1873 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1875 */       if (showArgsMap.get(row) != null) {
/* 1876 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1877 */         if (showArgslist != null) {
/* 1878 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1879 */             if (argsList.trim().equals("")) {
/* 1880 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1883 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1890 */       e.printStackTrace();
/* 1891 */       return "";
/*      */     }
/* 1893 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1898 */     String argsList = "";
/* 1899 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1902 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1904 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1905 */         if (hideArgsList != null)
/*      */         {
/* 1907 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1909 */             if (argsList.trim().equals(""))
/*      */             {
/* 1911 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1915 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1923 */       ex.printStackTrace();
/*      */     }
/* 1925 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1929 */     StringBuilder toreturn = new StringBuilder();
/* 1930 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1937 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1938 */       Iterator itr = tActionList.iterator();
/* 1939 */       while (itr.hasNext()) {
/* 1940 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1941 */         String confirmmsg = "";
/* 1942 */         String link = "";
/* 1943 */         String isJSP = "NO";
/* 1944 */         HashMap tactionMap = (HashMap)itr.next();
/* 1945 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1946 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1947 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1948 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1949 */           (actionmap.containsKey(actionId))) {
/* 1950 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1951 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1952 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1953 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1954 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1956 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1962 */           if (isTableAction) {
/* 1963 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1966 */             tableName = "Link";
/* 1967 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1968 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1969 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1970 */             toreturn.append("</a></td>");
/*      */           }
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1975 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1981 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1987 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1989 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1990 */       Properties prop = (Properties)node.getUserObject();
/* 1991 */       String mgID = prop.getProperty("label");
/* 1992 */       String mgName = prop.getProperty("value");
/* 1993 */       String isParent = prop.getProperty("isParent");
/* 1994 */       int mgIDint = Integer.parseInt(mgID);
/* 1995 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1997 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1999 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2000 */       if (node.getChildCount() > 0)
/*      */       {
/* 2002 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2004 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2006 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2012 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2019 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2021 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2027 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2030 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2031 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2033 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2037 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2039 */       if (node.getChildCount() > 0)
/*      */       {
/* 2041 */         builder.append("<UL>");
/* 2042 */         printMGTree(node, builder);
/* 2043 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2048 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2049 */     StringBuffer toReturn = new StringBuffer();
/* 2050 */     String table = "-";
/*      */     try {
/* 2052 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2053 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2054 */       float total = 0.0F;
/* 2055 */       while (it.hasNext()) {
/* 2056 */         String attName = (String)it.next();
/* 2057 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2058 */         boolean roundOffData = false;
/* 2059 */         if ((data != null) && (!data.equals(""))) {
/* 2060 */           if (data.indexOf(",") != -1) {
/* 2061 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2064 */             float value = Float.parseFloat(data);
/* 2065 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2068 */             total += value;
/* 2069 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2072 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2077 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2078 */       while (attVsWidthList.hasNext()) {
/* 2079 */         String attName = (String)attVsWidthList.next();
/* 2080 */         String data = (String)attVsWidthProps.get(attName);
/* 2081 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2082 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2083 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2084 */         String className = (String)graphDetails.get("ClassName");
/* 2085 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2086 */         if (percentage < 1.0F)
/*      */         {
/* 2088 */           data = percentage + "";
/*      */         }
/* 2090 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2092 */       if (toReturn.length() > 0) {
/* 2093 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2097 */       e.printStackTrace();
/*      */     }
/* 2099 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2105 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2106 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2107 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2108 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2109 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2110 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2111 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2112 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2113 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2116 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2117 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2118 */       splitvalues[0] = multiplecondition.toString();
/* 2119 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2122 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2127 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2128 */     if (thresholdType != 3) {
/* 2129 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2130 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2131 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2132 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2133 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2134 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2136 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2137 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2138 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2139 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2140 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2141 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2143 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2144 */     if (updateSelected != null) {
/* 2145 */       updateSelected[0] = "selected";
/*      */     }
/* 2147 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2152 */       StringBuffer toreturn = new StringBuffer("");
/* 2153 */       if (commaSeparatedMsgId != null) {
/* 2154 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2155 */         int count = 0;
/* 2156 */         while (msgids.hasMoreTokens()) {
/* 2157 */           String id = msgids.nextToken();
/* 2158 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2159 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2160 */           count++;
/* 2161 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2162 */             if (toreturn.length() == 0) {
/* 2163 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2165 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2166 */             if (!image.trim().equals("")) {
/* 2167 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2169 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2170 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2173 */         if (toreturn.length() > 0) {
/* 2174 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2178 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2181 */       e.printStackTrace(); }
/* 2182 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2194 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2195 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2220 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2236 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2239 */     JspWriter out = null;
/* 2240 */     Object page = this;
/* 2241 */     JspWriter _jspx_out = null;
/* 2242 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2246 */       response.setContentType("text/html;charset=UTF-8");
/* 2247 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2249 */       _jspx_page_context = pageContext;
/* 2250 */       ServletContext application = pageContext.getServletContext();
/* 2251 */       ServletConfig config = pageContext.getServletConfig();
/* 2252 */       session = pageContext.getSession();
/* 2253 */       out = pageContext.getOut();
/* 2254 */       _jspx_out = out;
/*      */       
/* 2256 */       out.write("\n\n\n\n\n\n\n\n");
/* 2257 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2259 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2260 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2261 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2270 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2271 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2272 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2275 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2276 */         String available = null;
/* 2277 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2278 */         out.write(10);
/*      */         
/* 2280 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2281 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2282 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2291 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2292 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2293 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2296 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2297 */           String unavailable = null;
/* 2298 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2299 */           out.write(10);
/*      */           
/* 2301 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2302 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2303 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2312 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2313 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2314 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2317 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2318 */             String unmanaged = null;
/* 2319 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2320 */             out.write(10);
/*      */             
/* 2322 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2323 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2324 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2333 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2334 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2335 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2338 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2339 */               String scheduled = null;
/* 2340 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2341 */               out.write(10);
/*      */               
/* 2343 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2344 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2345 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2354 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2355 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2356 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2359 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2360 */                 String critical = null;
/* 2361 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2362 */                 out.write(10);
/*      */                 
/* 2364 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2365 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2366 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2375 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2376 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2377 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2380 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2381 */                   String clear = null;
/* 2382 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2383 */                   out.write(10);
/*      */                   
/* 2385 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2386 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2387 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2396 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2397 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2398 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2401 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2402 */                     String warning = null;
/* 2403 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2404 */                     out.write(10);
/* 2405 */                     out.write(10);
/*      */                     
/* 2407 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2408 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2410 */                     out.write(10);
/* 2411 */                     out.write(10);
/* 2412 */                     out.write(10);
/* 2413 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<html>\n<head>\n<style>\n.graytableborder{\n    background-color: #FFFEE2;\n    border: 1px solid #CCCCCC;\n       font-family: Arial, Helvetica, sans-serif;\n        font-size: 11px;\n}\n.graytblheading{\n      font-family: Arial, Helvetica, sans-serif;\n        font-size: 11px;\n        font-weight: bold;\n    background-color: #EBEBEB;\n    border-bottom-width: 1px;\n    border-bottom-style: solid;\n    border-bottom-color: #DFDFDF;\n}\n.messagerows{\n    background-color: FEFDE3;\n    font-family: Arial, Helvetica, sans-serif;\n    font-size: 11px;\n    color: #000000;\n}\n</style>\n\n</head>\n\n");
/*      */                     
/* 2415 */                     Properties alert = null;
/* 2416 */                     ArrayList unManagedList = (ArrayList)request.getAttribute("UnManagedChildList");
/* 2417 */                     ArrayList attribIDs = new ArrayList();
/* 2418 */                     ArrayList resIDs = new ArrayList();
/* 2419 */                     String haid = (String)request.getAttribute("haid");
/* 2420 */                     String appname = request.getParameter("appName");
/* 2421 */                     String resourcename = request.getParameter("name");
/* 2422 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2423 */                     String redirect = "/HostResource.do?name=" + resourcename + "&haid=" + request.getParameter("haid") + "&appName=" + request.getParameter("appName") + "&resourceid=" + resourceid + "";
/* 2424 */                     String encodeurl = URLEncoder.encode(redirect);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2429 */                     String resType = (String)request.getAttribute("hostResType");
/* 2430 */                     String hostname = (String)request.getAttribute("hostResName");
/* 2431 */                     if (hostname == null)
/*      */                     {
/* 2433 */                       hostname = resourcename;
/*      */                     }
/* 2435 */                     resIDs.add(request.getParameter("resourceid"));
/*      */                     
/* 2437 */                     attribIDs.add("731");
/*      */                     
/*      */ 
/* 2440 */                     ArrayList servicelists = (ArrayList)request.getAttribute("windowsservices");
/* 2441 */                     if (servicelists != null)
/*      */                     {
/* 2443 */                       for (int i = 0; i < servicelists.size(); i++)
/*      */                       {
/* 2445 */                         ArrayList servicelist = (ArrayList)servicelists.get(i);
/* 2446 */                         resIDs.add((String)servicelist.get(0));
/*      */                       }
/*      */                     }
/* 2449 */                     alert = getStatus(resIDs, attribIDs);
/*      */                     
/*      */ 
/* 2452 */                     out.write("\n                 ");
/*      */                     
/* 2454 */                     if (request.getAttribute("resultofaction") != null)
/*      */                     {
/* 2456 */                       Hashtable resultofaction = (Hashtable)request.getAttribute("resultofaction");
/* 2457 */                       Enumeration resaction = resultofaction.keys();
/* 2458 */                       int sizeofservices = resultofaction.size();
/*      */                       
/* 2460 */                       out.write("\n\t \t<br>\n\t \t<div id=\"serviceactionmessage\" style=\"Display:block\">\n\t \t<table class=\"graytableborder\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" width=\"99%\">\n\n\t \t  <tr>\n\t \t\t   <td width=\"45%\"  height=\"20\" class=\"graytblheading\">");
/* 2461 */                       out.print(FormatUtil.getString("am.monitortab.tableview.service.text"));
/* 2462 */                       out.write("</td>\n\t \t\t   <td width=\"45%\" height=\"20\"  class=\"graytblheading\">");
/* 2463 */                       out.print(FormatUtil.getString("am.webclient.hostresource.services.result.text"));
/* 2464 */                       out.write("</td>\n\t \t\t   <td width=\"10%\" align=\"right\" height=\"20\" class=\"graytblheading\"><a href=\"javascript:hidemessage()\"><img src=\"/images/cross.gif\" title=\"Hide\" border=\"0\"></a></td>\n\t \t </tr>\n\t \t    ");
/*      */                       
/* 2466 */                       while (resaction.hasMoreElements())
/*      */                       {
/* 2468 */                         String servicesel = (String)resaction.nextElement();
/* 2469 */                         String result = (String)resultofaction.get(servicesel);
/* 2470 */                         String bgclass1 = "class=\"whitegrayborder\"";
/*      */                         
/* 2472 */                         if (sizeofservices % 2 == 0)
/*      */                         {
/* 2474 */                           bgclass1 = "class=\"yellowgrayborder\"";
/*      */                         }
/*      */                         else
/*      */                         {
/* 2478 */                           bgclass1 = "class=\"whitegrayborder\"";
/*      */                         }
/* 2480 */                         sizeofservices--;
/*      */                         
/* 2482 */                         out.write("\n\t \t    <tr>\n\t \t    <td  \"class=messagerows\" height=\"20\">");
/* 2483 */                         out.print(servicesel);
/* 2484 */                         out.write("</td>\n\t \t    <td  \"class=messagerows\" height=\"20\">");
/* 2485 */                         out.print(FormatUtil.getString(result));
/* 2486 */                         out.write("</td>\n\t \t   </tr>\n\t \t   ");
/*      */                       }
/*      */                       
/* 2489 */                       out.write("</table> </div>\n\n\t \t   ");
/*      */                     }
/*      */                     
/*      */ 
/* 2493 */                     out.write("     <br>\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t      <tr>\n\t         <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 2494 */                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.servicedetail"));
/* 2495 */                     out.write("</td>\n\t         ");
/*      */                     
/* 2497 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2498 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2499 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2501 */                     _jspx_th_c_005fif_005f0.setTest("${!empty ADMIN || !empty DEMO }");
/* 2502 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2503 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2505 */                         out.write("\n\t\t ");
/*      */                         
/* 2507 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2508 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2509 */                         _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2511 */                         _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2512 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2513 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2515 */                             out.write("\n\t\t <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:alertUser()\" class=\"bodytextboldwhiteun\">\n\t\t ");
/* 2516 */                             out.print(FormatUtil.getString("am.webclient.hostResource.servers.addservice"));
/* 2517 */                             out.write("</a> </td>\n\t\t ");
/* 2518 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2519 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2523 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2524 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                         }
/*      */                         
/* 2527 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2528 */                         out.write("\n\t\t ");
/*      */                         
/* 2530 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2531 */                         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2532 */                         _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2534 */                         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2535 */                         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2536 */                         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2538 */                             out.write("\n\t\t <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:void(0)\" class=\"bodytextboldwhiteun\" onClick=\"fnOpenNewWindow('../HostResource.do?getServiceList=true&resourceid=");
/* 2539 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/*      */                               return;
/* 2541 */                             out.write("&resType=");
/* 2542 */                             out.print(resType);
/* 2543 */                             out.write("')\">");
/* 2544 */                             out.print(FormatUtil.getString("am.webclient.hostResource.servers.addservice"));
/* 2545 */                             out.write("</a>&nbsp;</td>\n\t\t ");
/* 2546 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2547 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2551 */                         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2552 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                         }
/*      */                         
/* 2555 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2556 */                         out.write("\n\t         ");
/* 2557 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2558 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2562 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2563 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2566 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2567 */                       out.write("\n\t      </tr>\n\t     </table>\n\t     ");
/*      */                       
/* 2569 */                       Hashtable globals = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 2570 */                       String allowServices = (String)globals.get("allowOperatorServices");
/* 2571 */                       String allowServicesForAdminRoleUser = (String)globals.get("allowAdminWindowsServices");
/* 2572 */                       String allowManage = (String)globals.get("allowOperatorManage");
/* 2573 */                       boolean show = false;
/* 2574 */                       boolean showService = false;
/* 2575 */                       if ((allowServicesForAdminRoleUser != null) && (allowServicesForAdminRoleUser.equalsIgnoreCase("true")))
/*      */                       {
/* 2577 */                         showService = true;
/*      */                       }
/* 2579 */                       boolean allowManageAction = true;
/*      */                       
/* 2581 */                       out.write(10);
/*      */                       
/* 2583 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2584 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2585 */                       _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                       
/* 2587 */                       _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 2588 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2589 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2591 */                           out.write(10);
/* 2592 */                           out.write(9);
/*      */                           
/* 2594 */                           if (allowServices.equals("false"))
/*      */                           {
/* 2596 */                             show = false;
/*      */                           }
/* 2598 */                           else if (allowServices.equals("true"))
/*      */                           {
/* 2600 */                             show = true;
/*      */                           }
/* 2602 */                           if ((allowManage != null) && (allowManage.equalsIgnoreCase("true")))
/*      */                           {
/* 2604 */                             allowManageAction = true;
/*      */                           }
/*      */                           else
/*      */                           {
/* 2608 */                             allowManageAction = false;
/*      */                           }
/*      */                           
/* 2611 */                           out.write(10);
/* 2612 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2613 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2617 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2618 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                       }
/*      */                       else {
/* 2621 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2622 */                         out.write("\n\t\n\t ");
/*      */                         
/* 2624 */                         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2625 */                         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2626 */                         _jspx_th_c_005fif_005f1.setParent(null);
/*      */                         
/* 2628 */                         _jspx_th_c_005fif_005f1.setTest("${ !empty windowsservices}");
/* 2629 */                         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2630 */                         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                           for (;;) {
/* 2632 */                             out.write("\n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t ");
/*      */                             
/* 2634 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2635 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2636 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                             
/* 2638 */                             _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO }");
/* 2639 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2640 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2642 */                                 out.write("\n\t \t<tr>\n");
/*      */                                 
/* 2644 */                                 if (showService)
/*      */                                 {
/* 2646 */                                   out.write("\n\t         <td colspan=\"2\" align=\"left\" class=\"columnheadingdelete\">\n\t\t         <a href=\"javascript:actiononServices('Delete');\" class=\"staticlinks\">");
/* 2647 */                                   out.print(FormatUtil.getString("webclient.fault.alarm.operations.delete"));
/* 2648 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t         <a href=\"javascript:actiononServices('Restart');\" class=\"staticlinks\">");
/* 2649 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restart"));
/* 2650 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t         <a href=\"javascript:actiononServices('Start');\" class=\"staticlinks\">");
/* 2651 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.start"));
/* 2652 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t <a href=\"javascript:actiononServices('Stop');\" class=\"staticlinks\">");
/* 2653 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stop"));
/* 2654 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t <a href=\"javascript:manageMonitors(true, 'service');\" class=\"staticlinks\">");
/* 2655 */                                   out.print(FormatUtil.getString("Manage"));
/* 2656 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t <a href=\"javascript:manageMonitors(false, 'service');\" class=\"staticlinks\">");
/* 2657 */                                   out.print(FormatUtil.getString("UnManage"));
/* 2658 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t <a href=\"javascript:manageMonitors(false,'service',true);\" class=\"staticlinks\">");
/* 2659 */                                   out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 2660 */                                   out.write("</a>\n\t\t </td>\n\t         <td colspan=\"4\" align=\"left\" class=\"columnheadingdelete\"><div id=\"actionstatus\" >&nbsp;</div></td>\n");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2665 */                                   out.write("\n\t         <td colspan=\"2\" align=\"left\" class=\"columnheadingdelete\">\n\t\t         <a href=\"javascript:actiononServices('Delete');\" class=\"staticlinks\">");
/* 2666 */                                   out.print(FormatUtil.getString("webclient.fault.alarm.operations.delete"));
/* 2667 */                                   out.write("</a> &nbsp;&nbsp;&nbsp;</td>\n\t         <td colspan=\"4\" align=\"left\" class=\"columnheadingdelete\"><div id=\"actionstatus\" >&nbsp;</div></td>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2671 */                                 out.write("\t      \n\t\t</tr>\n\t ");
/* 2672 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2673 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2677 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2678 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2681 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2682 */                             out.write(10);
/* 2683 */                             out.write(9);
/* 2684 */                             out.write(32);
/*      */                             
/* 2686 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2687 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2688 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                             
/* 2690 */                             _jspx_th_logic_005fpresent_005f2.setRole("OPERATOR");
/* 2691 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2692 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/* 2694 */                                 out.write("\n\t <tr>\n\t\t ");
/*      */                                 
/* 2696 */                                 if ((show) || (allowManageAction))
/*      */                                 {
/*      */ 
/* 2699 */                                   out.write("\n\t\t <td colspan=\"2\" align=\"left\" class=\"columnheadingdelete\">\n\t\t");
/* 2700 */                                   if (show) {
/* 2701 */                                     out.write("\n\t\t\t\t<a href=\"javascript:actiononServices('Restart');\" class=\"staticlinks\">");
/* 2702 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restart"));
/* 2703 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;<a href=\"javascript:actiononServices('Start');\" class=\"staticlinks\">");
/* 2704 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.start"));
/* 2705 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;<a href=\"javascript:actiononServices('Stop');\" class=\"staticlinks\">");
/* 2706 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stop"));
/* 2707 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t");
/*      */                                   }
/*      */                                   
/* 2710 */                                   if (allowManageAction)
/*      */                                   {
/*      */ 
/* 2713 */                                     out.write("\n\t\t\t<a href=\"javascript:manageMonitors(true, 'service');\" class=\"staticlinks\">");
/* 2714 */                                     out.print(FormatUtil.getString("Manage"));
/* 2715 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t<a href=\"javascript:manageMonitors(false, 'service');\" class=\"staticlinks\">");
/* 2716 */                                     out.print(FormatUtil.getString("UnManage"));
/* 2717 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t<a href=\"javascript:manageMonitors(false,'service',true);\" class=\"staticlinks\">");
/* 2718 */                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 2719 */                                     out.write("</a>\n\t\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2723 */                                   out.write("\n\t\t\t</td>\n\t\t\t<td colspan=\"4\" align=\"left\" class=\"columnheadingdelete\"><div id=\"actionstatus\" >&nbsp;</div></td>\n\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2727 */                                 out.write("\n\t\t</tr>\n\t");
/* 2728 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2729 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2733 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2734 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/* 2737 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2738 */                             out.write("\n\t   <tr>\n\t   \t  <td width=\"100%\" colspan=\"6\">\n\t   \t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"hostServicesDetails");
/* 2739 */                             out.print(resourceid);
/* 2740 */                             out.write("\" class=\"\">\n\t   \t   \t<tr>\n\t      \t\t<td width=2%  height=\"28\" class=\"columnheading\"><input type=\"checkbox\" name=\"headercheckbox\" onClick=\"javascript:selectallservices(this)\"></td>\n\t      \t\t<td width=\"40%\" height=\"28\"  class=\"columnheading\">");
/* 2741 */                             out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 2742 */                             out.write("</td>\n\t      \t\t<td width=\"20%\" height=\"28\"  class=\"columnheading\">");
/* 2743 */                             out.print(FormatUtil.getString("am.webclient.hostResource.servers.servicename"));
/* 2744 */                             out.write("</td>\n\t      \t\t<td width=\"15%\" height=\"28\"  class=\"columnheading\"  align=\"center\">");
/* 2745 */                             out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2746 */                             out.write("</td>\n\t      \t\t<td width=\"25%\" height=\"28\"  class=\"columnheading\"  align=\"center\">");
/* 2747 */                             out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 2748 */                             out.write("</td>\n\t   \t    </tr>\n\n\t   <input type=\"hidden\" name=\"haid\" value=\"");
/* 2749 */                             out.print(haid);
/* 2750 */                             out.write("\"/>\n\t   <input type=\"hidden\" name=\"enabled\" value=\"NA\"/>\n\t   <input type=\"hidden\" name=\"managedservice\" value=\"NA\"/>\n\t   <input type=\"hidden\" name=\"unmanageAndResetService\" value=\"NA\"/>\n\t   <input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2751 */                             out.print(resourceid);
/* 2752 */                             out.write("\"/>\n\t   <input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t   <input type=\"hidden\" name=\"appName\" value=\"");
/* 2753 */                             out.print(appname);
/* 2754 */                             out.write("\"/>\n\t   <input type=\"hidden\" name=\"name\" value=\"");
/* 2755 */                             out.print(resourcename);
/* 2756 */                             out.write("\"/>\n\t   <input type=\"hidden\" name=\"actiononServices\">\n\n\t ");
/*      */                             
/* 2758 */                             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2759 */                             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2760 */                             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                             
/* 2762 */                             _jspx_th_logic_005fiterate_005f0.setName("windowsservices");
/*      */                             
/* 2764 */                             _jspx_th_logic_005fiterate_005f0.setId("srow");
/*      */                             
/* 2766 */                             _jspx_th_logic_005fiterate_005f0.setIndexId("s");
/*      */                             
/* 2768 */                             _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2769 */                             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2770 */                             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2771 */                               ArrayList srow = null;
/* 2772 */                               Integer s = null;
/* 2773 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2774 */                                 out = _jspx_page_context.pushBody();
/* 2775 */                                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2776 */                                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                               }
/* 2778 */                               srow = (ArrayList)_jspx_page_context.findAttribute("srow");
/* 2779 */                               s = (Integer)_jspx_page_context.findAttribute("s");
/*      */                               for (;;) {
/* 2781 */                                 out.write(10);
/* 2782 */                                 out.write(9);
/* 2783 */                                 out.write(32);
/*      */                                 
/* 2785 */                                 String serviceid = (String)srow.get(0);
/* 2786 */                                 String DisplayName = (String)srow.get(2);
/* 2787 */                                 String SName = (String)srow.get(1);
/* 2788 */                                 String bgclass = "class=\"whitegrayborder\"";
/*      */                                 
/* 2790 */                                 if (s.intValue() % 2 == 0)
/*      */                                 {
/* 2792 */                                   bgclass = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2796 */                                   bgclass = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 2798 */                                 String disableText = "";
/* 2799 */                                 if ((unManagedList != null) && (unManagedList.contains(serviceid)))
/*      */                                 {
/* 2801 */                                   disableText = "disabledtext";
/*      */                                 }
/*      */                                 
/* 2804 */                                 out.write("\n\t    <tr>\n\t\t<td  ");
/* 2805 */                                 out.print(bgclass);
/* 2806 */                                 out.write("><input type=\"checkbox\" name=\"services\" value=\"");
/* 2807 */                                 out.print(serviceid);
/* 2808 */                                 out.write("\"></td>\n\t\t<td  ");
/* 2809 */                                 out.print(bgclass);
/* 2810 */                                 out.write("><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2811 */                                 out.print(serviceid);
/* 2812 */                                 out.write("&period=0&resourcename=");
/* 2813 */                                 out.print(DisplayName);
/* 2814 */                                 out.write("')\" class=\"resourcename ");
/* 2815 */                                 out.print(disableText);
/* 2816 */                                 out.write(34);
/* 2817 */                                 out.write(62);
/* 2818 */                                 out.print(DisplayName);
/* 2819 */                                 out.write(" </a> ");
/* 2820 */                                 if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.underMaintenance(serviceid)) {
/* 2821 */                                   out.write("<img src=\"/images/down-time-icon.gif\">");
/*      */                                 }
/* 2823 */                                 out.write(" </td>\n\t\t<td  ");
/* 2824 */                                 out.print(bgclass);
/* 2825 */                                 out.write(" title=\"");
/* 2826 */                                 out.print(SName);
/* 2827 */                                 out.write("\" ><span class=\"");
/* 2828 */                                 out.print(disableText);
/* 2829 */                                 out.write(34);
/* 2830 */                                 out.write(62);
/* 2831 */                                 out.print(getTrimmedText(SName, 17));
/* 2832 */                                 out.write("</span></td>\n\t\t<td  ");
/* 2833 */                                 out.print(bgclass);
/* 2834 */                                 out.write(" align=\"center\" ><span style=\"display: none;\">");
/* 2835 */                                 out.print(alert.getProperty(serviceid + "#" + "731"));
/* 2836 */                                 out.write("</span><a href=\"javascript:void(0)\"  onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2837 */                                 out.print(serviceid);
/* 2838 */                                 out.write("&attributeid=731')\">");
/* 2839 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(serviceid + "#" + "731")));
/* 2840 */                                 out.write("</a></td>\n\t\t<td  ");
/* 2841 */                                 out.print(bgclass);
/* 2842 */                                 out.write("  align=\"center\">\n\t\t\t");
/*      */                                 
/* 2844 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2845 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2846 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                 
/* 2848 */                                 _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO }");
/* 2849 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2850 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2852 */                                     out.write("\n\t\t\t\t<a href=\"/HostResource.do?actiononServices=Edit&serviceid=");
/* 2853 */                                     out.print(serviceid);
/* 2854 */                                     out.write("&resourceid=");
/* 2855 */                                     out.print(resourceid);
/* 2856 */                                     out.write("&displayname=");
/* 2857 */                                     out.print(DisplayName);
/* 2858 */                                     out.write("&servicename=");
/* 2859 */                                     out.print(SName);
/* 2860 */                                     out.write("&name=");
/* 2861 */                                     out.print(resourcename);
/* 2862 */                                     out.write("&haid=");
/* 2863 */                                     out.print(haid);
/* 2864 */                                     out.write("&appName=");
/* 2865 */                                     out.print(appname);
/* 2866 */                                     out.write("\"><img src=\"/images/icon_edit.gif\" title=\"");
/* 2867 */                                     out.print(FormatUtil.getString("am.webclient.hostresourceconfig.editservice"));
/* 2868 */                                     out.write("\"  border=\"0\"></a>\n\t\t \t");
/* 2869 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2870 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2874 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2875 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2878 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2879 */                                 out.write("&nbsp;&nbsp;&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2880 */                                 out.print(serviceid);
/* 2881 */                                 out.write("&attributeIDs=731&attributeToSelect=731&redirectto=");
/* 2882 */                                 out.print(encodeurl);
/* 2883 */                                 out.write("'> <img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2884 */                                 out.print(ALERTCONFIG_TEXT);
/* 2885 */                                 out.write("\" border=\"0\" ></a></td>\n\n\t        ");
/* 2886 */                                 if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                   return;
/* 2888 */                                 out.write("\n\t    </tr>\n\t \t");
/* 2889 */                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2890 */                                 srow = (ArrayList)_jspx_page_context.findAttribute("srow");
/* 2891 */                                 s = (Integer)_jspx_page_context.findAttribute("s");
/* 2892 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2895 */                               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2896 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2899 */                             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2900 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                             }
/*      */                             
/* 2903 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2904 */                             out.write("\n\t \t</table>\n\t \t </td>\n\t   </tr>\n\t  </table>\n\t ");
/* 2905 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2906 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2910 */                         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2911 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                         }
/*      */                         else {
/* 2914 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2915 */                           out.write(10);
/* 2916 */                           out.write(9);
/* 2917 */                           out.write(32);
/*      */                           
/* 2919 */                           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2920 */                           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2921 */                           _jspx_th_c_005fif_005f4.setParent(null);
/*      */                           
/* 2923 */                           _jspx_th_c_005fif_005f4.setTest("${ empty windowsservices}");
/* 2924 */                           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2925 */                           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                             for (;;) {
/* 2927 */                               out.write("\n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t     <tr>\n\t\t\t<td class=\"bodytext\" align=\"center\" height=\"26\">");
/* 2928 */                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.noservice"));
/* 2929 */                               out.write("\n\t\t\t ");
/*      */                               
/* 2931 */                               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2932 */                               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2933 */                               _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                               
/* 2935 */                               _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO }");
/* 2936 */                               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2937 */                               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                 for (;;) {
/* 2939 */                                   out.write("\n\t\t\t\t ");
/*      */                                   
/* 2941 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2942 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2943 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f5);
/*      */                                   
/* 2945 */                                   _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 2946 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2947 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 2949 */                                       out.write("<a href=\"javascript:alertUser()\" class=\"staticlinks\">\n\t\t\t\t ");
/* 2950 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.addservice.second"));
/* 2951 */                                       out.write("</a> ");
/* 2952 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2953 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2957 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2958 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 2961 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2962 */                                   out.write("\n\t\t\t\t ");
/*      */                                   
/* 2964 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2965 */                                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2966 */                                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f5);
/*      */                                   
/* 2968 */                                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2969 */                                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2970 */                                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                     for (;;) {
/* 2972 */                                       out.write("<a href=\"javascript:void(0)\" class=\"staticlinks\" onClick=\"fnOpenNewWindow('../HostResource.do?getServiceList=true&resourceid=");
/* 2973 */                                       if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                         return;
/* 2975 */                                       out.write("&resType=");
/* 2976 */                                       out.print(resType);
/* 2977 */                                       out.write("')\">");
/* 2978 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.addservice.second"));
/* 2979 */                                       out.write("</a>\n\t\t\t\t ");
/* 2980 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2981 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2985 */                                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2986 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                   }
/*      */                                   
/* 2989 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2990 */                                   out.write("\n\t\t\t ");
/* 2991 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2992 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2996 */                               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2997 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                               }
/*      */                               
/* 3000 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3001 */                               out.write("\n\t     </tr>\n\t  </table>\n\t ");
/* 3002 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3003 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3007 */                           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3008 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */                           }
/*      */                           else {
/* 3011 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3012 */                             out.write("\n\t  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t <tr>\n\t\t  ");
/* 3013 */                             if (request.getAttribute("windowsservices") != null) {
/* 3014 */                               out.write("\n\t\t\t");
/*      */                               
/* 3016 */                               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3017 */                               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3018 */                               _jspx_th_c_005fif_005f6.setParent(null);
/*      */                               
/* 3020 */                               _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO }");
/* 3021 */                               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3022 */                               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                 for (;;) {
/* 3024 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 3026 */                                   if (showService)
/*      */                                   {
/* 3028 */                                     out.write("\n\t\t\t\t<td colspan=\"14\" align=\"left\" class=\"columnheadingdelete\">\n\t\t\t\t\t<a href=\"javascript:actiononServices('Delete');\" class=\"staticlinks\">");
/* 3029 */                                     out.print(FormatUtil.getString("webclient.fault.alarm.operations.delete"));
/* 3030 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:actiononServices('Restart');\" class=\"staticlinks\">");
/* 3031 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restart"));
/* 3032 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:actiononServices('Start');\" class=\"staticlinks\">");
/* 3033 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.start"));
/* 3034 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:actiononServices('Stop');\" class=\"staticlinks\">");
/* 3035 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stop"));
/* 3036 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:manageMonitors(true, 'service');\" class=\"staticlinks\">");
/* 3037 */                                     out.print(FormatUtil.getString("Manage"));
/* 3038 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:manageMonitors(false, 'service');\" class=\"staticlinks\">");
/* 3039 */                                     out.print(FormatUtil.getString("UnManage"));
/* 3040 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t\t<a href=\"javascript:manageMonitors(false, 'service',true);\" class=\"staticlinks\">");
/* 3041 */                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 3042 */                                     out.write("</a>\n\t\t\t\t</td>\n\t\t\t\n\t\t\t");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3046 */                                     out.write("\n\t\t\t\t<td colspan=\"14\" align=\"left\" class=\"columnheadingdelete\">\n\t\t\t\t\t<a href=\"javascript:actiononServices('Delete');\" class=\"staticlinks\">");
/* 3047 */                                     out.print(FormatUtil.getString("webclient.fault.alarm.operations.delete"));
/* 3048 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t\t</td>\n\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3052 */                                   out.write("\n\t\t\t\t<td height=\"15\" align=\"right\" class=\"columnheadingdelete\"><a href=\"#top1\" class=\"staticlinks\">");
/* 3053 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 3054 */                                   out.write("</a>&nbsp;</td>\n\t\t\t");
/* 3055 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3056 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3060 */                               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3061 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                               }
/*      */                               
/* 3064 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3065 */                               out.write("\n\t\t\t");
/*      */                               
/* 3067 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3068 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3069 */                               _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                               
/* 3071 */                               _jspx_th_logic_005fpresent_005f5.setRole("OPERATOR");
/* 3072 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3073 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/* 3075 */                                   out.write("\n\t\t\t<td colspan=\"14\" align=\"left\" class=\"columnheadingdelete\">\n\t\t\t");
/* 3076 */                                   if (show) {
/* 3077 */                                     out.write("\n\t\t\t<a href=\"javascript:actiononServices('Restart');\" class=\"staticlinks\">");
/* 3078 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.restart"));
/* 3079 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;<a href=\"javascript:actiononServices('Start');\" class=\"staticlinks\">");
/* 3080 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.start"));
/* 3081 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;<a href=\"javascript:actiononServices('Stop');\" class=\"staticlinks\">");
/* 3082 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.service.stop"));
/* 3083 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t");
/*      */                                   }
/*      */                                   
/* 3086 */                                   if (allowManageAction)
/*      */                                   {
/*      */ 
/* 3089 */                                     out.write("\n\t\t\t<a href=\"javascript:manageMonitors(true, 'service');\" class=\"staticlinks\">");
/* 3090 */                                     out.print(FormatUtil.getString("Manage"));
/* 3091 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t<a href=\"javascript:manageMonitors(false, 'service');\" class=\"staticlinks\">");
/* 3092 */                                     out.print(FormatUtil.getString("UnManage"));
/* 3093 */                                     out.write("</a> &nbsp;&nbsp;&nbsp;\n\t\t\t<a href=\"javascript:manageMonitors(false,'service',true);\" class=\"staticlinks\">");
/* 3094 */                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 3095 */                                     out.write("</a>\n\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3099 */                                   out.write("\n\t\t\t</td>\n\t\t\t<td colspan=\"15\" align=\"left\" class=\"columnheadingdelete\"><div id=\"actionstatus\" >&nbsp;</div></td>\n\t\t");
/* 3100 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3101 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3105 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3106 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                               }
/*      */                               
/* 3109 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3110 */                               out.write(10);
/* 3111 */                               out.write(9);
/* 3112 */                               out.write(9);
/*      */                             }
/* 3114 */                             out.write("\n\n\t          ");
/* 3115 */                             if (request.getAttribute("windowsservices") == null) {
/* 3116 */                               out.write("\n\t          <td height=\"15\" align=\"right\" class=\"tablebottom\"><a href=\"#top1\" class=\"staticlinks\">");
/* 3117 */                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 3118 */                               out.write("</a>&nbsp;</td>\n\t          ");
/*      */                             }
/* 3120 */                             out.write("\n\t     </tr>\n\t  </table>\n</html>\n<script language=\"javascript\">\n\tSORTTABLENAME = 'hostServicesDetails'+");
/* 3121 */                             out.print(resourceid);
/* 3122 */                             out.write("; //No I18N\n\tvar numberOfColumnsToBeSorted = 3;\n\tsortables_init(numberOfColumnsToBeSorted,true,false,true);\n</script>\n");
/*      */                           }
/* 3124 */                         } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3125 */         out = _jspx_out;
/* 3126 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3127 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3128 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3131 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3137 */     PageContext pageContext = _jspx_page_context;
/* 3138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3140 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3141 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3142 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 3144 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3145 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3146 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3147 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3148 */       return true;
/*      */     }
/* 3150 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3156 */     PageContext pageContext = _jspx_page_context;
/* 3157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3159 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3160 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3161 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3163 */     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3164 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3165 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3167 */         out.write("\n\t\t\n                ");
/* 3168 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3169 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3173 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3174 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3175 */       return true;
/*      */     }
/* 3177 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3183 */     PageContext pageContext = _jspx_page_context;
/* 3184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3186 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3187 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3188 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 3190 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3191 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3192 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3193 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3194 */       return true;
/*      */     }
/* 3196 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3197 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Windows_005fServices_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */