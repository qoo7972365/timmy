/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.mssql.bean.MsSqlGraphs;
/*      */ import com.adventnet.appmanager.server.mysql.bean.MySqlGraphs;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class MSSQLDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   54 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   57 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   58 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   59 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   66 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   71 */     ArrayList list = null;
/*   72 */     StringBuffer sbf = new StringBuffer();
/*   73 */     ManagedApplication mo = new ManagedApplication();
/*   74 */     if (distinct)
/*      */     {
/*   76 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   80 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   83 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   85 */       ArrayList row = (ArrayList)list.get(i);
/*   86 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   87 */       if (distinct) {
/*   88 */         sbf.append(row.get(0));
/*      */       } else
/*   90 */         sbf.append(row.get(1));
/*   91 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   94 */     return sbf.toString(); }
/*      */   
/*   96 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   99 */     if (severity == null)
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("5"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("1"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  114 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  121 */     if (severity == null)
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("1"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("4"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("5"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  140 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  146 */     if (severity == null)
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  150 */     if (severity.equals("5"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  154 */     if (severity.equals("1"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  160 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  166 */     if (severity == null)
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  170 */     if (severity.equals("1"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  174 */     if (severity.equals("4"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  178 */     if (severity.equals("5"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  184 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  190 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  196 */     if (severity == 5)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  200 */     if (severity == 1)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  207 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  213 */     if (severity == null)
/*      */     {
/*  215 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  217 */     if (severity.equals("5"))
/*      */     {
/*  219 */       if (isAvailability) {
/*  220 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  223 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  226 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  228 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  230 */     if (severity.equals("1"))
/*      */     {
/*  232 */       if (isAvailability) {
/*  233 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  236 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  243 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  250 */     if (severity == null)
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("5"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("4"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("1"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  269 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  275 */     if (severity == null)
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("5"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("4"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("1"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  294 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  301 */     if (severity == null)
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  305 */     if (severity.equals("5"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  309 */     if (severity.equals("4"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  313 */     if (severity.equals("1"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  320 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  328 */     StringBuffer out = new StringBuffer();
/*  329 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  330 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  331 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  332 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  333 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  334 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  335 */     out.append("</tr>");
/*  336 */     out.append("</form></table>");
/*  337 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  344 */     if (val == null)
/*      */     {
/*  346 */       return "-";
/*      */     }
/*      */     
/*  349 */     String ret = FormatUtil.formatNumber(val);
/*  350 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  351 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  354 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  358 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  366 */     StringBuffer out = new StringBuffer();
/*  367 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  368 */     out.append("<tr>");
/*  369 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  371 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  373 */     out.append("</tr>");
/*  374 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  378 */       if (j % 2 == 0)
/*      */       {
/*  380 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  384 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  387 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  389 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  392 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  396 */       out.append("</tr>");
/*      */     }
/*  398 */     out.append("</table>");
/*  399 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  400 */     out.append("<tr>");
/*  401 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  402 */     out.append("</tr>");
/*  403 */     out.append("</table>");
/*  404 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  410 */     StringBuffer out = new StringBuffer();
/*  411 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  412 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  417 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  418 */     out.append("</tr>");
/*  419 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  422 */       out.append("<tr>");
/*  423 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  424 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  425 */       out.append("</tr>");
/*      */     }
/*      */     
/*  428 */     out.append("</table>");
/*  429 */     out.append("</table>");
/*  430 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  435 */     if (severity.equals("0"))
/*      */     {
/*  437 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  441 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  448 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  461 */     StringBuffer out = new StringBuffer();
/*  462 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  463 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  465 */       out.append("<tr>");
/*  466 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  467 */       out.append("</tr>");
/*      */       
/*      */ 
/*  470 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  472 */         String borderclass = "";
/*      */         
/*      */ 
/*  475 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  477 */         out.append("<tr>");
/*      */         
/*  479 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  480 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  481 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  487 */     out.append("</table><br>");
/*  488 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  489 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  491 */       List sLinks = secondLevelOfLinks[0];
/*  492 */       List sText = secondLevelOfLinks[1];
/*  493 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  496 */         out.append("<tr>");
/*  497 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  498 */         out.append("</tr>");
/*  499 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  501 */           String borderclass = "";
/*      */           
/*      */ 
/*  504 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  506 */           out.append("<tr>");
/*      */           
/*  508 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  509 */           if (sLinks.get(i).toString().length() == 0) {
/*  510 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  513 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  515 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  519 */     out.append("</table>");
/*  520 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  527 */     StringBuffer out = new StringBuffer();
/*  528 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  529 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  531 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  533 */         out.append("<tr>");
/*  534 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  535 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  539 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  541 */           String borderclass = "";
/*      */           
/*      */ 
/*  544 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  546 */           out.append("<tr>");
/*      */           
/*  548 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  549 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  550 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  553 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  556 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  561 */     out.append("</table><br>");
/*  562 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  563 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  565 */       List sLinks = secondLevelOfLinks[0];
/*  566 */       List sText = secondLevelOfLinks[1];
/*  567 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  570 */         out.append("<tr>");
/*  571 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  572 */         out.append("</tr>");
/*  573 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  575 */           String borderclass = "";
/*      */           
/*      */ 
/*  578 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  580 */           out.append("<tr>");
/*      */           
/*  582 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  583 */           if (sLinks.get(i).toString().length() == 0) {
/*  584 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  587 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  589 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  593 */     out.append("</table>");
/*  594 */     return out.toString();
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
/*  607 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  628 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  636 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  641 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  646 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  651 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  656 */     if (val != null)
/*      */     {
/*  658 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  662 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  667 */     if (val == null) {
/*  668 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  672 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  677 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  683 */     if (val != null)
/*      */     {
/*  685 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  689 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  695 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  719 */     String hostaddress = "";
/*  720 */     String ip = request.getHeader("x-forwarded-for");
/*  721 */     if (ip == null)
/*  722 */       ip = request.getRemoteAddr();
/*  723 */     InetAddress add = null;
/*  724 */     if (ip.equals("127.0.0.1")) {
/*  725 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  729 */       add = InetAddress.getByName(ip);
/*      */     }
/*  731 */     hostaddress = add.getHostName();
/*  732 */     if (hostaddress.indexOf('.') != -1) {
/*  733 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  734 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  738 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  743 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  749 */     if (severity == null)
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("5"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("1"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  764 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  769 */     ResultSet set = null;
/*  770 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  771 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  773 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  774 */       if (set.next()) { String str1;
/*  775 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  776 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  779 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  784 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  787 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  789 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  793 */     StringBuffer rca = new StringBuffer();
/*  794 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  795 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  798 */     int rcalength = key.length();
/*  799 */     String split = "6. ";
/*  800 */     int splitPresent = key.indexOf(split);
/*  801 */     String div1 = "";String div2 = "";
/*  802 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  804 */       if (rcalength > 180) {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         getRCATrimmedText(key, rca);
/*  807 */         rca.append("</span>");
/*      */       } else {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         rca.append(key);
/*  811 */         rca.append("</span>");
/*      */       }
/*  813 */       return rca.toString();
/*      */     }
/*  815 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  816 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  817 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  818 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div2, rca);
/*  826 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  828 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  833 */     String[] st = msg.split("<br>");
/*  834 */     for (int i = 0; i < st.length; i++) {
/*  835 */       String s = st[i];
/*  836 */       if (s.length() > 180) {
/*  837 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  839 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  843 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  844 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  846 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  850 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  851 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  852 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  855 */       if (key == null) {
/*  856 */         return ret;
/*      */       }
/*      */       
/*  859 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  872 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  878 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  897 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  888 */         if (set != null) {
/*  889 */           AMConnectionPool.closeStatement(set);
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
/*  903 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  904 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  906 */       String entityStr = (String)keys.nextElement();
/*  907 */       String mmessage = temp.getProperty(entityStr);
/*  908 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  909 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  911 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  917 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  930 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  940 */     String des = new String();
/*  941 */     while (str.indexOf(find) != -1) {
/*  942 */       des = des + str.substring(0, str.indexOf(find));
/*  943 */       des = des + replace;
/*  944 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  946 */     des = des + str;
/*  947 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  954 */       if (alert == null)
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  958 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  960 */         return "&nbsp;";
/*      */       }
/*      */       
/*  963 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  968 */       int rcalength = test.length();
/*  969 */       if (rcalength < 300)
/*      */       {
/*  971 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  975 */       StringBuffer out = new StringBuffer();
/*  976 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  977 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  978 */       out.append("</div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  981 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  986 */       ex.printStackTrace();
/*      */     }
/*  988 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  994 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  999 */     ArrayList attribIDs = new ArrayList();
/* 1000 */     ArrayList resIDs = new ArrayList();
/* 1001 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1003 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1005 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1007 */       String resourceid = "";
/* 1008 */       String resourceType = "";
/* 1009 */       if (type == 2) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = (String)row.get(3);
/*      */       }
/* 1013 */       else if (type == 3) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1018 */         resourceid = (String)row.get(6);
/* 1019 */         resourceType = (String)row.get(7);
/*      */       }
/* 1021 */       resIDs.add(resourceid);
/* 1022 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1025 */       String healthentity = null;
/* 1026 */       String availentity = null;
/* 1027 */       if (healthid != null) {
/* 1028 */         healthentity = resourceid + "_" + healthid;
/* 1029 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1032 */       if (availid != null) {
/* 1033 */         availentity = resourceid + "_" + availid;
/* 1034 */         entitylist.add(availentity);
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
/* 1048 */     Properties alert = getStatus(entitylist);
/* 1049 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1054 */     int size = monitorList.size();
/*      */     
/* 1056 */     String[] severity = new String[size];
/*      */     
/* 1058 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1060 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1061 */       String resourceName1 = (String)row1.get(7);
/* 1062 */       String resourceid1 = (String)row1.get(6);
/* 1063 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1064 */       if (severity[j] == null)
/*      */       {
/* 1066 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1070 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1072 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1074 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1077 */         if (sev > 0) {
/* 1078 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1079 */           monitorList.set(k, monitorList.get(j));
/* 1080 */           monitorList.set(j, t);
/* 1081 */           String temp = severity[k];
/* 1082 */           severity[k] = severity[j];
/* 1083 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1089 */     int z = 0;
/* 1090 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1093 */       int i = 0;
/* 1094 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1097 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1101 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1105 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1107 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1110 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1114 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1117 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1118 */       String resourceName1 = (String)row1.get(7);
/* 1119 */       String resourceid1 = (String)row1.get(6);
/* 1120 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1121 */       if (hseverity[j] == null)
/*      */       {
/* 1123 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1130 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1133 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1136 */         if (hsev > 0) {
/* 1137 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1138 */           monitorList.set(k, monitorList.get(j));
/* 1139 */           monitorList.set(j, t);
/* 1140 */           String temp1 = hseverity[k];
/* 1141 */           hseverity[k] = hseverity[j];
/* 1142 */           hseverity[j] = temp1;
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
/* 1154 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1155 */     boolean forInventory = false;
/* 1156 */     String trdisplay = "none";
/* 1157 */     String plusstyle = "inline";
/* 1158 */     String minusstyle = "none";
/* 1159 */     String haidTopLevel = "";
/* 1160 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1162 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1164 */         haidTopLevel = request.getParameter("haid");
/* 1165 */         forInventory = true;
/* 1166 */         trdisplay = "table-row;";
/* 1167 */         plusstyle = "none";
/* 1168 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1175 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1178 */     ArrayList listtoreturn = new ArrayList();
/* 1179 */     StringBuffer toreturn = new StringBuffer();
/* 1180 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1181 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1182 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1184 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1186 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1187 */       String childresid = (String)singlerow.get(0);
/* 1188 */       String childresname = (String)singlerow.get(1);
/* 1189 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1190 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1191 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1192 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1193 */       String unmanagestatus = (String)singlerow.get(5);
/* 1194 */       String actionstatus = (String)singlerow.get(6);
/* 1195 */       String linkclass = "monitorgp-links";
/* 1196 */       String titleforres = childresname;
/* 1197 */       String titilechildresname = childresname;
/* 1198 */       String childimg = "/images/trcont.png";
/* 1199 */       String flag = "enable";
/* 1200 */       String dcstarted = (String)singlerow.get(8);
/* 1201 */       String configMonitor = "";
/* 1202 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1203 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1205 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1207 */       if (singlerow.get(7) != null)
/*      */       {
/* 1209 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1211 */       String haiGroupType = "0";
/* 1212 */       if ("HAI".equals(childtype))
/*      */       {
/* 1214 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1216 */       childimg = "/images/trend.png";
/* 1217 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1218 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1219 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1221 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1223 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1225 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1226 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1229 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1231 */         linkclass = "disabledtext";
/* 1232 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1234 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String availmouseover = "";
/* 1236 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1238 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1240 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String healthmouseover = "";
/* 1242 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1244 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1247 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1248 */       int spacing = 0;
/* 1249 */       if (level >= 1)
/*      */       {
/* 1251 */         spacing = 40 * level;
/*      */       }
/* 1253 */       if (childtype.equals("HAI"))
/*      */       {
/* 1255 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1256 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1257 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1259 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1260 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1261 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1262 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1263 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1264 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1265 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1266 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1267 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1268 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1269 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1271 */         if (!forInventory)
/*      */         {
/* 1273 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1276 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = editlink + actions;
/*      */         }
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = actions + associatelink;
/*      */         }
/* 1286 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1287 */         String arrowimg = "";
/* 1288 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/* 1293 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1295 */         if (isIt360)
/*      */         {
/* 1297 */           actionimg = "";
/* 1298 */           actions = "";
/* 1299 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1305 */           actions = "";
/*      */         }
/* 1307 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1309 */           checkbox = "";
/*      */         }
/*      */         
/* 1312 */         String resourcelink = "";
/*      */         
/* 1314 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1323 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1329 */         if (!isIt360)
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1338 */         toreturn.append("</tr>");
/* 1339 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1341 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1342 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1347 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1350 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1354 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1356 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1358 */             toreturn.append(assocMessage);
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1368 */         String resourcelink = null;
/* 1369 */         boolean hideEditLink = false;
/* 1370 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1372 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1373 */           hideEditLink = true;
/* 1374 */           if (isIt360)
/*      */           {
/* 1376 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1380 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1382 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1384 */           hideEditLink = true;
/* 1385 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1386 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1391 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1394 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1395 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1396 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1397 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1398 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1399 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1400 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1401 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1402 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1403 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1404 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1405 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1406 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1408 */         if (hideEditLink)
/*      */         {
/* 1410 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1412 */         if (!forInventory)
/*      */         {
/* 1414 */           removefromgroup = "";
/*      */         }
/* 1416 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1417 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1418 */           actions = actions + configcustomfields;
/*      */         }
/* 1420 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1422 */           actions = editlink + actions;
/*      */         }
/* 1424 */         String managedLink = "";
/* 1425 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1427 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1428 */           actions = "";
/* 1429 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1430 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1433 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1435 */           checkbox = "";
/*      */         }
/*      */         
/* 1438 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1440 */           actions = "";
/*      */         }
/* 1442 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1445 */         if (isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1455 */         if (!isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1463 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1466 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       StringBuilder toreturn = new StringBuilder();
/* 1474 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1475 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1476 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1477 */       String title = "";
/* 1478 */       message = EnterpriseUtil.decodeString(message);
/* 1479 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1480 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1481 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1483 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1485 */       else if ("5".equals(severity))
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1493 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1494 */       toreturn.append(v);
/*      */       
/* 1496 */       toreturn.append(link);
/* 1497 */       if (severity == null)
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("5"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("4"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("1"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1518 */       toreturn.append("</a>");
/* 1519 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1523 */       ex.printStackTrace();
/*      */     }
/* 1525 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1532 */       StringBuilder toreturn = new StringBuilder();
/* 1533 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1534 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1535 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1536 */       if (message == null)
/*      */       {
/* 1538 */         message = "";
/*      */       }
/*      */       
/* 1541 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1542 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1544 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1545 */       toreturn.append(v);
/*      */       
/* 1547 */       toreturn.append(link);
/*      */       
/* 1549 */       if (severity == null)
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("5"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("1"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1566 */       toreturn.append("</a>");
/* 1567 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1573 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1576 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1577 */     if (invokeActions != null) {
/* 1578 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1579 */       while (iterator.hasNext()) {
/* 1580 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1581 */         if (actionmap.containsKey(actionid)) {
/* 1582 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1587 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1591 */     String actionLink = "";
/* 1592 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1593 */     String query = "";
/* 1594 */     ResultSet rs = null;
/* 1595 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1596 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1597 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1598 */       actionLink = "method=" + methodName;
/*      */     }
/* 1600 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1601 */       actionLink = methodName;
/*      */     }
/* 1603 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1604 */     Iterator itr = methodarglist.iterator();
/* 1605 */     boolean isfirstparam = true;
/* 1606 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1607 */     while (itr.hasNext()) {
/* 1608 */       HashMap argmap = (HashMap)itr.next();
/* 1609 */       String argtype = (String)argmap.get("TYPE");
/* 1610 */       String argname = (String)argmap.get("IDENTITY");
/* 1611 */       String paramname = (String)argmap.get("PARAMETER");
/* 1612 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1613 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1614 */         isfirstparam = false;
/* 1615 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1617 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1621 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1625 */         actionLink = actionLink + "&";
/*      */       }
/* 1627 */       String paramValue = null;
/* 1628 */       String tempargname = argname;
/* 1629 */       if (commonValues.getProperty(tempargname) != null) {
/* 1630 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1633 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1634 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1635 */           if (dbType.equals("mysql")) {
/* 1636 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1639 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1641 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1643 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1644 */             if (rs.next()) {
/* 1645 */               paramValue = rs.getString("VALUE");
/* 1646 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1650 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1654 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1657 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1662 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1663 */           paramValue = rowId;
/*      */         }
/* 1665 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1666 */           paramValue = managedObjectName;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1669 */           paramValue = resID;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1672 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1675 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1677 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1678 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1679 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1681 */     return actionLink;
/*      */   }
/*      */   
/* 1684 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1685 */     String dependentAttribute = null;
/* 1686 */     String align = "left";
/*      */     
/* 1688 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1689 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1690 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1691 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1692 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1693 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1694 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1695 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1696 */       align = "center";
/*      */     }
/*      */     
/* 1699 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1700 */     String actualdata = "";
/*      */     
/* 1702 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1703 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1704 */         actualdata = availValue;
/*      */       }
/* 1706 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1707 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1711 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1712 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1715 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1721 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1722 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1723 */       toreturn.append("<table>");
/* 1724 */       toreturn.append("<tr>");
/* 1725 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1726 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1727 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1728 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1729 */         String toolTip = "";
/* 1730 */         String hideClass = "";
/* 1731 */         String textStyle = "";
/* 1732 */         boolean isreferenced = true;
/* 1733 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1734 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1735 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1736 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1738 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1739 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1740 */           while (valueList.hasMoreTokens()) {
/* 1741 */             String dependentVal = valueList.nextToken();
/* 1742 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1743 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1744 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1746 */               toolTip = "";
/* 1747 */               hideClass = "";
/* 1748 */               isreferenced = false;
/* 1749 */               textStyle = "disabledtext";
/* 1750 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1754 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1755 */           toolTip = "";
/* 1756 */           hideClass = "";
/* 1757 */           isreferenced = false;
/* 1758 */           textStyle = "disabledtext";
/* 1759 */           if (dependentImageMap != null) {
/* 1760 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1761 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1764 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1769 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1770 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1771 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1772 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1773 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1775 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1776 */           if (isreferenced) {
/* 1777 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1781 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1782 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1783 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1784 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1785 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1786 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1788 */           toreturn.append("</span>");
/* 1789 */           toreturn.append("</a>");
/* 1790 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1793 */       toreturn.append("</tr>");
/* 1794 */       toreturn.append("</table>");
/* 1795 */       toreturn.append("</td>");
/*      */     } else {
/* 1797 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1800 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1804 */     String colTime = null;
/* 1805 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1806 */     if ((rows != null) && (rows.size() > 0)) {
/* 1807 */       Iterator<String> itr = rows.iterator();
/* 1808 */       String maxColQuery = "";
/* 1809 */       for (;;) { if (itr.hasNext()) {
/* 1810 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1811 */           ResultSet maxCol = null;
/*      */           try {
/* 1813 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1814 */             while (maxCol.next()) {
/* 1815 */               if (colTime == null) {
/* 1816 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1819 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1828 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1828 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1838 */     return colTime;
/*      */   }
/*      */   
/* 1841 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1842 */     tablename = null;
/* 1843 */     ResultSet rsTable = null;
/* 1844 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1846 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1847 */       while (rsTable.next()) {
/* 1848 */         tablename = rsTable.getString("DATATABLE");
/* 1849 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1850 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1863 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1854 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1857 */         if (rsTable != null)
/* 1858 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1860 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1866 */     String argsList = "";
/* 1867 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1869 */       if (showArgsMap.get(row) != null) {
/* 1870 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1871 */         if (showArgslist != null) {
/* 1872 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1873 */             if (argsList.trim().equals("")) {
/* 1874 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1877 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1884 */       e.printStackTrace();
/* 1885 */       return "";
/*      */     }
/* 1887 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1892 */     String argsList = "";
/* 1893 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1896 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1898 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1899 */         if (hideArgsList != null)
/*      */         {
/* 1901 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1903 */             if (argsList.trim().equals(""))
/*      */             {
/* 1905 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1909 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1917 */       ex.printStackTrace();
/*      */     }
/* 1919 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1923 */     StringBuilder toreturn = new StringBuilder();
/* 1924 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1931 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1932 */       Iterator itr = tActionList.iterator();
/* 1933 */       while (itr.hasNext()) {
/* 1934 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1935 */         String confirmmsg = "";
/* 1936 */         String link = "";
/* 1937 */         String isJSP = "NO";
/* 1938 */         HashMap tactionMap = (HashMap)itr.next();
/* 1939 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1940 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1941 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1942 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1943 */           (actionmap.containsKey(actionId))) {
/* 1944 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1945 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1946 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1947 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1948 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1950 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1956 */           if (isTableAction) {
/* 1957 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1960 */             tableName = "Link";
/* 1961 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1962 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1963 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1964 */             toreturn.append("</a></td>");
/*      */           }
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1975 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1981 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1983 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1984 */       Properties prop = (Properties)node.getUserObject();
/* 1985 */       String mgID = prop.getProperty("label");
/* 1986 */       String mgName = prop.getProperty("value");
/* 1987 */       String isParent = prop.getProperty("isParent");
/* 1988 */       int mgIDint = Integer.parseInt(mgID);
/* 1989 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1991 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1993 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1994 */       if (node.getChildCount() > 0)
/*      */       {
/* 1996 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1998 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2000 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2013 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2024 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2025 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2033 */       if (node.getChildCount() > 0)
/*      */       {
/* 2035 */         builder.append("<UL>");
/* 2036 */         printMGTree(node, builder);
/* 2037 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2042 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2043 */     StringBuffer toReturn = new StringBuffer();
/* 2044 */     String table = "-";
/*      */     try {
/* 2046 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2047 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2048 */       float total = 0.0F;
/* 2049 */       while (it.hasNext()) {
/* 2050 */         String attName = (String)it.next();
/* 2051 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2052 */         boolean roundOffData = false;
/* 2053 */         if ((data != null) && (!data.equals(""))) {
/* 2054 */           if (data.indexOf(",") != -1) {
/* 2055 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2058 */             float value = Float.parseFloat(data);
/* 2059 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2062 */             total += value;
/* 2063 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2066 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2071 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2072 */       while (attVsWidthList.hasNext()) {
/* 2073 */         String attName = (String)attVsWidthList.next();
/* 2074 */         String data = (String)attVsWidthProps.get(attName);
/* 2075 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2076 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2077 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2078 */         String className = (String)graphDetails.get("ClassName");
/* 2079 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2080 */         if (percentage < 1.0F)
/*      */         {
/* 2082 */           data = percentage + "";
/*      */         }
/* 2084 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2086 */       if (toReturn.length() > 0) {
/* 2087 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2091 */       e.printStackTrace();
/*      */     }
/* 2093 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2099 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2100 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2101 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2102 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2103 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2104 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2105 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2106 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2107 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2110 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2111 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2112 */       splitvalues[0] = multiplecondition.toString();
/* 2113 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2116 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2121 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2122 */     if (thresholdType != 3) {
/* 2123 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2124 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2125 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2126 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2127 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2128 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2130 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2131 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2132 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2133 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2134 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2135 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2137 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2138 */     if (updateSelected != null) {
/* 2139 */       updateSelected[0] = "selected";
/*      */     }
/* 2141 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2146 */       StringBuffer toreturn = new StringBuffer("");
/* 2147 */       if (commaSeparatedMsgId != null) {
/* 2148 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2149 */         int count = 0;
/* 2150 */         while (msgids.hasMoreTokens()) {
/* 2151 */           String id = msgids.nextToken();
/* 2152 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2153 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2154 */           count++;
/* 2155 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2156 */             if (toreturn.length() == 0) {
/* 2157 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2159 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2160 */             if (!image.trim().equals("")) {
/* 2161 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2163 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2164 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2167 */         if (toreturn.length() > 0) {
/* 2168 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2172 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2175 */       e.printStackTrace(); }
/* 2176 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2189 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2207 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2211 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2222 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2229 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2231 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2233 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2242 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2245 */     JspWriter out = null;
/* 2246 */     Object page = this;
/* 2247 */     JspWriter _jspx_out = null;
/* 2248 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2252 */       response.setContentType("text/html;charset=UTF-8");
/* 2253 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2255 */       _jspx_page_context = pageContext;
/* 2256 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2257 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2258 */       session = pageContext.getSession();
/* 2259 */       out = pageContext.getOut();
/* 2260 */       _jspx_out = out;
/*      */       
/* 2262 */       out.write("<!DOCTYPE html>\n");
/* 2263 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2265 */       request.setAttribute("HelpKey", "Monitors MSSQL Details");
/*      */       
/* 2267 */       out.write(10);
/* 2268 */       out.write(10);
/* 2269 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2270 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2271 */       if (wlsGraph == null) {
/* 2272 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2273 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2275 */       out.write(10);
/* 2276 */       MySqlGraphs mysqlgraph = null;
/* 2277 */       mysqlgraph = (MySqlGraphs)_jspx_page_context.getAttribute("mysqlgraph", 1);
/* 2278 */       if (mysqlgraph == null) {
/* 2279 */         mysqlgraph = new MySqlGraphs();
/* 2280 */         _jspx_page_context.setAttribute("mysqlgraph", mysqlgraph, 1);
/*      */       }
/* 2282 */       out.write(10);
/* 2283 */       MsSqlGraphs mssqlgraph = null;
/* 2284 */       mssqlgraph = (MsSqlGraphs)_jspx_page_context.getAttribute("mssqlgraph", 1);
/* 2285 */       if (mssqlgraph == null) {
/* 2286 */         mssqlgraph = new MsSqlGraphs();
/* 2287 */         _jspx_page_context.setAttribute("mssqlgraph", mssqlgraph, 1);
/*      */       }
/* 2289 */       out.write(10);
/* 2290 */       PerformanceBean perfgraph = null;
/* 2291 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2292 */       if (perfgraph == null) {
/* 2293 */         perfgraph = new PerformanceBean();
/* 2294 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2296 */       out.write("\n\n\n\n\n\n\n\n");
/* 2297 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2299 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2300 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2301 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2303 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2305 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2307 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2309 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2310 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2311 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2312 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2315 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2316 */         String available = null;
/* 2317 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2318 */         out.write(10);
/*      */         
/* 2320 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2321 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2322 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2324 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2326 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2328 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2330 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2331 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2332 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2333 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2336 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2337 */           String unavailable = null;
/* 2338 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2339 */           out.write(10);
/*      */           
/* 2341 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2342 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2343 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2345 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2347 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2349 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2351 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2352 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2353 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2354 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2357 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2358 */             String unmanaged = null;
/* 2359 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2360 */             out.write(10);
/*      */             
/* 2362 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2363 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2364 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2366 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2368 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2370 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2372 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2373 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2374 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2375 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2378 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2379 */               String scheduled = null;
/* 2380 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2381 */               out.write(10);
/*      */               
/* 2383 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2384 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2385 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2387 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2389 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2391 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2393 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2394 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2395 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2396 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2399 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2400 */                 String critical = null;
/* 2401 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2402 */                 out.write(10);
/*      */                 
/* 2404 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2405 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2406 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2408 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2410 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2412 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2414 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2415 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2416 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2417 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2420 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2421 */                   String clear = null;
/* 2422 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2423 */                   out.write(10);
/*      */                   
/* 2425 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2427 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2429 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2431 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2433 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2435 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2436 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2437 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2438 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2441 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2442 */                     String warning = null;
/* 2443 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2444 */                     out.write(10);
/* 2445 */                     out.write(10);
/*      */                     
/* 2447 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2448 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2450 */                     out.write(10);
/* 2451 */                     out.write(10);
/* 2452 */                     out.write(10);
/* 2453 */                     out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/mssql.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/customfield.js\"></script>\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<link href=\"../images/calendar-win2k-cold-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n<script>\nfunction myOnLoad()\n{\n\tSORTTABLENAME = 'databaseDetails';\n\tvar numberOfColumnsToBeSorted =2;\n\tvar ignoreCheckBox =true;\n\tvar ignoreDefaultSorting=false;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n\n}\n\nfunction changeAction(value){\n\tif(!checkforOneSelected(document.sqlJobsAction,\"sqljobscheckbox\"))\n\t{\n\t\talert('");
/* 2454 */                     out.print(FormatUtil.getString("am.webclient.mssql.jobs.select.text"));
/* 2455 */                     out.write("');\n\t\treturn;\n\t}\n\tif(value == 'enable'){\n\t\tif(confirm(\"");
/* 2456 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 2457 */                     out.write("\")){\n\t\t\tdocument.sqlJobsAction.submit();\n\t\t}\n\t}else if(value == 'disable'){\n\t\tif(confirm(\"");
/* 2458 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 2459 */                     out.write("\")){\n\t\t\tdocument.sqlJobsAction.submit();\n\t\t}\n\t}\n}\n\nfunction changeDBAction(value){\n\tif(!checkforOneSelected(document.sqlDatabaseAction,\"sqldatabasecheckbox\"))\n\t{\n\t\talert('");
/* 2460 */                     out.print(FormatUtil.getString("am.webclient.mssql.database.select.text"));
/* 2461 */                     out.write("');\n\t\treturn;\n\t}\n\tif(value == 'enable'){\n\t\tif(confirm(\"");
/* 2462 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 2463 */                     out.write("\")){\n\t\t\tdocument.sqlDatabaseAction.submit();\n\t\t}\n\t}else if(value == 'disable'){\n\t\tif(confirm(\"");
/* 2464 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 2465 */                     out.write("\")){\n\t\t\tdocument.sqlDatabaseAction.submit();\n\t\t}\n\t}\n}\n\nfunction fnSelectAll(e,value)\n{\n\t\tif(value == 'sqljobs'){\n    \t\tToggleAll(e,document.sqlJobsAction,'sqljobscheckbox')\t\t// NO I18N\n\t\t}else{\n\t\t\tToggleAll(e,document.sqlDatabaseAction,'sqldatabasecheckbox')\t\t// NO I18N\n\t\t}\n}\n\nwindow.onload=function(){\n    /*var datatype='");
/* 2466 */                     out.print(request.getParameter("datatype"));
/* 2467 */                     out.write("';\n    if(datatype==5){\n        var dbname='");
/* 2468 */                     out.print(request.getParameter("dbname"));
/* 2469 */                     out.write("';\n\t\t$('#sqldbm_helplink').html(\"<a href='/help/monitors/database-details.html'  target='newwindow' class='headerwhitelinks' ><img src='/images/Blue/helpIcon.png'/></a>\");\n\t\tfrom='database';  //No I18N\n\t}*/\n};\n\nfunction getPerforQueryData(resid,hrefname) {\n\tvar date = new Date();\n\tvar url = '/MSSqlDispatch.do?resourceid='+resid+'&hrefname='+hrefname+'&method=performanceDetails&noredirect=false&date='+date;//NO I18N\t\n\thttp.open(\"GET\",url,true);\n\thttp.onreadystatechange = handlePerformanceQueryDetails;\n\thttp.send(null);\n}\n\nfunction handlePerformanceQueryDetails() {\n\tif (http.readyState == 4 && http.status==200) {\n\t\tvar result = http.responseText;\n\t\tvar ele = document.getElementById(\"data\");\n\t\tif(ele)\n\t\t{\n\t\t\tele.innerHTML = result;\n\t\t\tif(document.getElementById(\"loadingg\"))\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"loadingg\").style.display = \"none\";\n    \t\t}\n\t\t}\n\t}\n}\n\nfunction invokePerfFilterByActions(resid,hrefname) {\n\tvar option1 = document.getElementById(\"FilterByExecTime\").value;\n\tif(option1=='4') {\n\t\tdocument.getElementById(\"CustomDate\").style.display=\"block\";\t\t\n");
/* 2470 */                     out.write("\t\t$(document).ready(function() {\n\t\t\t$(function() {\t\t\n\t\t\t\tCalendar.setup({\n\t\t\t\t\tinputField     :    \"FromDate\",     // id of the input field      //No I18N\n\t\t\t\t\tifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field  //No I18N\n\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t\tbutton         :    \"FromDate\",  // trigger for the calendar (button ID)   //No I18N\n\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\talign          :    \"Bl\",           // alignment (defaults to \"Bl\")  //No I18N\n\t\t\t\t\tsingleClick    :    true\n\t\t\t\t});\t\t\t\t\t\t\t \n\t\t\t});\t\t\t\n\t\t\t$(function() {\n\t\t\t\tCalendar.setup({\n\t\t\t\t\tinputField     :    \"ToDate\",     // id of the input field      //No I18N\n\t\t\t\t\tifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field  //No I18N\n\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t\tbutton         :    \"ToDate\",  // trigger for the calendar (button ID)   //No I18N\n\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\talign          :    \"Bl\",           // alignment (defaults to \"Bl\")  //No I18N\n\t\t\t\t\tsingleClick    :    true\n\t\t\t\t});\t\t\n\t\t\t});\t\t\t\n\t\t});\n");
/* 2471 */                     out.write("\t} else {\n\t\tdocument.getElementById(\"CustomDate\").style.display=\"none\";\t\n\t\tdocument.getElementById(\"FromDate\").value=\"\";\n\t\tdocument.getElementById(\"ToDate\").value=\"\";\n\t}\n}\n\nfunction invokeBKUPActions(resid){ \n\t");
/* 2472 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2474 */                     out.write(10);
/* 2475 */                     out.write(9);
/* 2476 */                     if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2478 */                     out.write(" \n}\n\nfunction expandAllLockForReplication(elm) {\n\t\t$cllps = $('#replicationouter').find('.collapse');  //No I18N\n       $tr = $('#replicationouter').find('.expand1 > a');   //No I18N\n       if ($(elm).text() == '");
/* 2479 */                     out.print(FormatUtil.getString("am.mssql.replication.viewsubscriptions.text"));
/* 2480 */                     out.write("') {\t \t\n           $(elm).text('");
/* 2481 */                     out.print(FormatUtil.getString("am.mssql.replication.hidesubscriptions.text"));
/* 2482 */                     out.write("').attr('class', 'open');   //No I18N\n           $tr.addClass('open');   //No I18N\n\t\t    $cllps.show();\n\t\t    $('.expand1').closest('tr').addClass('rawopen');  //No I18N\n       } else {\n           $(elm).text('");
/* 2483 */                     out.print(FormatUtil.getString("am.mssql.replication.viewsubscriptions.text"));
/* 2484 */                     out.write("').attr('class', '');  //No I18N\n           $tr.removeClass('open');  //No I18N\n\t\t    $cllps.hide();\n\t\t    $('.expand1').closest('tr').removeClass('rawopen');  //No I18N\n\t   }\n      return false;\n}\n</script>\n\n");
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
/* 2499 */                     String haid = null;
/* 2500 */                     String appname = null;
/* 2501 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2502 */                     mssqlgraph.setresid(Integer.parseInt(resourceid));
/* 2503 */                     haid = (String)request.getAttribute("haid");
/* 2504 */                     appname = (String)request.getAttribute("appName");
/* 2505 */                     String details = request.getParameter("details");
/* 2506 */                     String showdata = (String)request.getAttribute("showdata");
/* 2507 */                     if (details == null) {
/* 2508 */                       details = "Availability";
/*      */                     }
/* 2510 */                     String displayname = null;
/* 2511 */                     ArrayList attribIDs = new ArrayList();
/* 2512 */                     ArrayList resIDs = new ArrayList();
/* 2513 */                     for (int i = 3100; i <= 3151; i++)
/*      */                     {
/* 2515 */                       attribIDs.add("" + i);
/*      */                     }
/* 2517 */                     for (int i = 3158; i <= 3161; i++) {
/* 2518 */                       attribIDs.add("" + i);
/*      */                     }
/* 2520 */                     attribIDs.add("3164");
/* 2521 */                     resIDs.add(resourceid);
/* 2522 */                     DecimalFormat df = new DecimalFormat("#.##");
/* 2523 */                     if (request.getAttribute("displayname") == null) {
/* 2524 */                       displayname = request.getParameter("resourcename");
/*      */                     } else {
/* 2526 */                       displayname = (String)request.getAttribute("displayname");
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2537 */                     out.write(10);
/* 2538 */                     if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */                       return;
/* 2540 */                     out.write(10);
/*      */                     
/* 2542 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2543 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2544 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2546 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2547 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2548 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2550 */                         out.write(10);
/* 2551 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2553 */                         out.write(10);
/* 2554 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2556 */                         out.write(10);
/* 2557 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2559 */                         out.write(10);
/* 2560 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2562 */                         out.write(10);
/*      */                         
/* 2564 */                         PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2565 */                         _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2566 */                         _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2568 */                         _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                         
/* 2570 */                         _jspx_th_tiles_005fput_005f4.setType("string");
/* 2571 */                         int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2572 */                         if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2573 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2574 */                             out = _jspx_page_context.pushBody();
/* 2575 */                             _jspx_th_tiles_005fput_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2576 */                             _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2579 */                             out.write(10);
/*      */                             
/* 2581 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2582 */                             String aid = request.getParameter("haid");
/* 2583 */                             String haName = null;
/* 2584 */                             if (aid != null)
/*      */                             {
/* 2586 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2589 */                             out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n\t");
/*      */                             
/* 2591 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2592 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2593 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2595 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2596 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2597 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2599 */                                 out.write("\n      <td class=\"bcsign\"  height=\"35\" valign=\"center\"> ");
/* 2600 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2601 */                                 out.write(" &gt; ");
/* 2602 */                                 out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2603 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2604 */                                 out.print(displayname);
/* 2605 */                                 out.write(" </span></td>\n\t");
/* 2606 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2607 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2611 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2612 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2615 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2616 */                             out.write(10);
/* 2617 */                             out.write(9);
/*      */                             
/* 2619 */                             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2620 */                             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2621 */                             _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                             
/* 2623 */                             _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (!empty invalidhaid)}");
/* 2624 */                             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2625 */                             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                               for (;;) {
/* 2627 */                                 out.write("\n      <td class=\"bcsign\"  height=\"35\" valign=\"center\"> ");
/* 2628 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2629 */                                 out.write(" &gt; ");
/* 2630 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes("MSSQL-DB-server"));
/* 2631 */                                 out.write(" &gt; <span class=\"bcactive\"> ");
/* 2632 */                                 out.print(displayname);
/* 2633 */                                 out.write(" </span></td>\n\t");
/* 2634 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2635 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2639 */                             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2640 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                             }
/*      */                             
/* 2643 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2644 */                             out.write("\n    </tr>\n</table>\n");
/* 2645 */                             if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 2647 */                             out.write(10);
/* 2648 */                             if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                               return;
/* 2650 */                             out.write(10);
/* 2651 */                             out.write(10);
/*      */                             
/* 2653 */                             int datatype = 1;
/* 2654 */                             if (request.getAttribute("datatype") != null) {
/* 2655 */                               datatype = Integer.parseInt((String)request.getAttribute("datatype"));
/*      */                             }
/* 2657 */                             String selected = "";
/* 2658 */                             if (datatype == 1) {
/* 2659 */                               selected = "am.webclient.mssqlinnertabs.overview";
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/* 2664 */                             else if (datatype == 6) {
/* 2665 */                               selected = "am.webclient.mssqlinnertabs.backup";
/*      */                             }
/* 2667 */                             else if (datatype == 14) {
/* 2668 */                               selected = "am.webclient.mssqlinnertabs.replication";
/*      */                             }
/* 2670 */                             else if (datatype == 7) {
/* 2671 */                               selected = "am.webclient.mssqlinnertabs.performance";
/*      */                             }
/*      */                             
/* 2674 */                             out.write(10);
/* 2675 */                             JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.mssqlinnertabs.overview,am.webclient.mssqlinnertabs.performance,am.webclient.mssqlinnertabs.backup,am.webclient.mssqlinnertabs.replication", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.mssqlinnertabs.overview,am.webclient.mssqlinnertabs.performance,am.webclient.mssqlinnertabs.backup,am.webclient.mssqlinnertabs.replication", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getOverviewData,getPerformanceData,getBackupData,getReplicationData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 2676 */                             out.write("\n<br/>\n\n<table border=\"0\" width=\"100%\">\n<tr>\n<td>\n<div id=\"data\">\n<script language=\"javascript\">\n");
/*      */                             
/* 2678 */                             if (datatype == 1)
/*      */                             {
/* 2680 */                               out.write("\ngetOverviewData(\"");
/* 2681 */                               out.print(resourceid);
/* 2682 */                               out.write("\",'','','Availability','');\n");
/* 2683 */                             } else if (datatype == 6)
/*      */                             {
/* 2685 */                               out.write("\ngetBackupData(\"");
/* 2686 */                               out.print(resourceid);
/* 2687 */                               out.write("\");\n");
/*      */                             }
/* 2689 */                             else if (datatype == 14)
/*      */                             {
/* 2691 */                               out.write("\ngetReplicationData(\"");
/* 2692 */                               out.print(resourceid);
/* 2693 */                               out.write("\");\n");
/*      */                             }
/* 2695 */                             else if (datatype == 7)
/*      */                             {
/* 2697 */                               out.write("\ngetPerformanceData(\"");
/* 2698 */                               out.print(resourceid);
/* 2699 */                               out.write("\");\n");
/*      */                             }
/*      */                             
/*      */ 
/* 2703 */                             out.write("\n</script>\n</div>\n</td>\n</tr>\n</table>\n");
/* 2704 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 2705 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2708 */                           if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2709 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2712 */                         if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2713 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                         }
/*      */                         
/* 2716 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 2717 */                         out.write(10);
/* 2718 */                         if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2720 */                         out.write(10);
/* 2721 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2722 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2726 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2727 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else
/* 2730 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                   }
/* 2732 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2733 */         out = _jspx_out;
/* 2734 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2735 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2736 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2739 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2745 */     PageContext pageContext = _jspx_page_context;
/* 2746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2748 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2749 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2750 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2752 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2753 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2754 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2756 */         out.write("\n\talertUser();\n\t");
/* 2757 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2758 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2762 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2763 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2764 */       return true;
/*      */     }
/* 2766 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2772 */     PageContext pageContext = _jspx_page_context;
/* 2773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2775 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2776 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2777 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 2779 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2780 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2781 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2783 */         out.write("\n\t\n    var job_value = \"\";\n    var option=document.managebackup.operation.value; \n    var i = 0;\n     $('input:checkbox[name=selectdb]').each(function() \n     {    \n    \t if($(this).is(':checked')){ \n    \t\t if(i==0){\n    \t\t\t job_value = $(this).val();\n    \t\t }else{\n    \t\t\t job_value = job_value +\",\"+$(this).val();\t \n    \t\t }\n    \t\t i=i+1;\n    \t if(option=='disableMonitoring' || option=='disableMonitoringReset'){\n    \t\t $(\"#\"+$(this).val()).attr('class', 'disabledtext'); \t\t \n    \t }else{\n    \t\t $(\"#\"+$(this).val()).attr('class', 'whitegrayborder'); \t \n    \t }\n     }\n      });\n     if((job_value=='')) {\n \t\talert(\"");
/* 2784 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2785 */           return true;
/* 2786 */         out.write("\");\t//No I18N\n \t\treturn false;\n \t}\n\tjQuery.ajax({url:'/MSSqlDispatch.do?method=backupManagementActions&resourceid='+resid+'&ids='+job_value+'&operation='+option, cache: false, success:function(result){  //No I18N\n\t}});\n\tdocument.managebackup.reset();\n\t");
/* 2787 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2788 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2792 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2793 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2794 */       return true;
/*      */     }
/* 2796 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2802 */     PageContext pageContext = _jspx_page_context;
/* 2803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2805 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2806 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2807 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/*      */     
/* 2809 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.mssql.dbdetails.alert");
/* 2810 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2811 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 2825 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 2828 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2829 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2831 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2832 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2834 */           out.write(10);
/* 2835 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2836 */             return true;
/* 2837 */           out.write(10);
/* 2838 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2839 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2843 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2844 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2847 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 2848 */         out = _jspx_page_context.popBody(); }
/* 2849 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2851 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2852 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2859 */     PageContext pageContext = _jspx_page_context;
/* 2860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2862 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 2863 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2864 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2866 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2868 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2869 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2870 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2871 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2872 */       return true;
/*      */     }
/* 2874 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2880 */     PageContext pageContext = _jspx_page_context;
/* 2881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2883 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2884 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2885 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2887 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2889 */     _jspx_th_tiles_005fput_005f0.setValue("MS SQL Server Details");
/* 2890 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2891 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2892 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2893 */       return true;
/*      */     }
/* 2895 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2901 */     PageContext pageContext = _jspx_page_context;
/* 2902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2904 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2905 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2906 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2908 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2909 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2910 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2912 */         out.write(10);
/* 2913 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 2914 */           return true;
/* 2915 */         out.write(10);
/* 2916 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2917 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2921 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2922 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2923 */       return true;
/*      */     }
/* 2925 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2931 */     PageContext pageContext = _jspx_page_context;
/* 2932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2934 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2935 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2936 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2938 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2940 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 2941 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2942 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2943 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2944 */       return true;
/*      */     }
/* 2946 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2952 */     PageContext pageContext = _jspx_page_context;
/* 2953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2955 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2956 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2957 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2959 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2960 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2961 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2963 */         out.write(10);
/* 2964 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 2965 */           return true;
/* 2966 */         out.write(10);
/* 2967 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2968 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2972 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2973 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2974 */       return true;
/*      */     }
/* 2976 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2982 */     PageContext pageContext = _jspx_page_context;
/* 2983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2985 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2986 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2987 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2989 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 2991 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 2992 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2993 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2994 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2995 */       return true;
/*      */     }
/* 2997 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3003 */     PageContext pageContext = _jspx_page_context;
/* 3004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3006 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3007 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3008 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3010 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 3012 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/MSSQLLeftPage.jsp");
/* 3013 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3014 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3015 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3016 */       return true;
/*      */     }
/* 3018 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3024 */     PageContext pageContext = _jspx_page_context;
/* 3025 */     JspWriter out = _jspx_page_context.getOut();
/* 3026 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3027 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3029 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3030 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3031 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 3033 */     _jspx_th_c_005fif_005f4.setTest("${!empty showconfigdiv && (showconfigdiv == 'true')}");
/* 3034 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3035 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3037 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: block\">\n");
/* 3038 */         JspRuntimeLibrary.include(request, response, "/jsp/MSSQLConfig.jsp?reconfigure=true&configured=true", out, false);
/* 3039 */         out.write("\n</div>\n");
/* 3040 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3041 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3045 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3046 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3047 */       return true;
/*      */     }
/* 3049 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3055 */     PageContext pageContext = _jspx_page_context;
/* 3056 */     JspWriter out = _jspx_page_context.getOut();
/* 3057 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 3058 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/* 3060 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3061 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3062 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 3064 */     _jspx_th_c_005fif_005f5.setTest("${empty showconfigdiv}");
/* 3065 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3066 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3068 */         out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 3069 */         JspRuntimeLibrary.include(request, response, "/jsp/MSSQLConfig.jsp?reconfigure=true&configured=true", out, false);
/* 3070 */         out.write("\n</div>\n");
/* 3071 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3072 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3076 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3077 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3078 */       return true;
/*      */     }
/* 3080 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3086 */     PageContext pageContext = _jspx_page_context;
/* 3087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3089 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3090 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3091 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3093 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 3095 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 3096 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3097 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3098 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3099 */       return true;
/*      */     }
/* 3101 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 3102 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MSSQLDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */