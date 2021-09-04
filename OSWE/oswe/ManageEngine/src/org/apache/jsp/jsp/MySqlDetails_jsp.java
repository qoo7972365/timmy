/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.mysql.bean.MySqlGraphs;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class MySqlDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   65 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   68 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   69 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   70 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   77 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   82 */     ArrayList list = null;
/*   83 */     StringBuffer sbf = new StringBuffer();
/*   84 */     ManagedApplication mo = new ManagedApplication();
/*   85 */     if (distinct)
/*      */     {
/*   87 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   91 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   94 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   96 */       ArrayList row = (ArrayList)list.get(i);
/*   97 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   98 */       if (distinct) {
/*   99 */         sbf.append(row.get(0));
/*      */       } else
/*  101 */         sbf.append(row.get(1));
/*  102 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  105 */     return sbf.toString(); }
/*      */   
/*  107 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  110 */     if (severity == null)
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  114 */     if (severity.equals("5"))
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  118 */     if (severity.equals("1"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  125 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  132 */     if (severity == null)
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("1"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  140 */     if (severity.equals("4"))
/*      */     {
/*  142 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  144 */     if (severity.equals("5"))
/*      */     {
/*  146 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  151 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  157 */     if (severity == null)
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  161 */     if (severity.equals("5"))
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  165 */     if (severity.equals("1"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  171 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  177 */     if (severity == null)
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  181 */     if (severity.equals("1"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  185 */     if (severity.equals("4"))
/*      */     {
/*  187 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  189 */     if (severity.equals("5"))
/*      */     {
/*  191 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  195 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  201 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  207 */     if (severity == 5)
/*      */     {
/*  209 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  211 */     if (severity == 1)
/*      */     {
/*  213 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  218 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  224 */     if (severity == null)
/*      */     {
/*  226 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  228 */     if (severity.equals("5"))
/*      */     {
/*  230 */       if (isAvailability) {
/*  231 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  234 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  237 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  239 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  241 */     if (severity.equals("1"))
/*      */     {
/*  243 */       if (isAvailability) {
/*  244 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  247 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  254 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  261 */     if (severity == null)
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("5"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("4"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("1"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  280 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  286 */     if (severity == null)
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("5"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  294 */     if (severity.equals("4"))
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  298 */     if (severity.equals("1"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  305 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  312 */     if (severity == null)
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  316 */     if (severity.equals("5"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  320 */     if (severity.equals("4"))
/*      */     {
/*  322 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  324 */     if (severity.equals("1"))
/*      */     {
/*  326 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  331 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  339 */     StringBuffer out = new StringBuffer();
/*  340 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  341 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  342 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  343 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  344 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  345 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  346 */     out.append("</tr>");
/*  347 */     out.append("</form></table>");
/*  348 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  355 */     if (val == null)
/*      */     {
/*  357 */       return "-";
/*      */     }
/*      */     
/*  360 */     String ret = FormatUtil.formatNumber(val);
/*  361 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  362 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  365 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  369 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  377 */     StringBuffer out = new StringBuffer();
/*  378 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  379 */     out.append("<tr>");
/*  380 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  382 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  384 */     out.append("</tr>");
/*  385 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  389 */       if (j % 2 == 0)
/*      */       {
/*  391 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  395 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  398 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  400 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  403 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  407 */       out.append("</tr>");
/*      */     }
/*  409 */     out.append("</table>");
/*  410 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  413 */     out.append("</tr>");
/*  414 */     out.append("</table>");
/*  415 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  421 */     StringBuffer out = new StringBuffer();
/*  422 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  423 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  424 */     out.append("<tr>");
/*  425 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  426 */     out.append("<tr>");
/*  427 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  428 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  429 */     out.append("</tr>");
/*  430 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  433 */       out.append("<tr>");
/*  434 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  435 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  436 */       out.append("</tr>");
/*      */     }
/*      */     
/*  439 */     out.append("</table>");
/*  440 */     out.append("</table>");
/*  441 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  446 */     if (severity.equals("0"))
/*      */     {
/*  448 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  452 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  459 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  472 */     StringBuffer out = new StringBuffer();
/*  473 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  474 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  476 */       out.append("<tr>");
/*  477 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  478 */       out.append("</tr>");
/*      */       
/*      */ 
/*  481 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  483 */         String borderclass = "";
/*      */         
/*      */ 
/*  486 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  488 */         out.append("<tr>");
/*      */         
/*  490 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  491 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  492 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  498 */     out.append("</table><br>");
/*  499 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  500 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  502 */       List sLinks = secondLevelOfLinks[0];
/*  503 */       List sText = secondLevelOfLinks[1];
/*  504 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  507 */         out.append("<tr>");
/*  508 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  509 */         out.append("</tr>");
/*  510 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  512 */           String borderclass = "";
/*      */           
/*      */ 
/*  515 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  517 */           out.append("<tr>");
/*      */           
/*  519 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  520 */           if (sLinks.get(i).toString().length() == 0) {
/*  521 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  524 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  526 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  530 */     out.append("</table>");
/*  531 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  538 */     StringBuffer out = new StringBuffer();
/*  539 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  540 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  542 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  544 */         out.append("<tr>");
/*  545 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  546 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  550 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  552 */           String borderclass = "";
/*      */           
/*      */ 
/*  555 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  557 */           out.append("<tr>");
/*      */           
/*  559 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  560 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  561 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  564 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  567 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  572 */     out.append("</table><br>");
/*  573 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  574 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  576 */       List sLinks = secondLevelOfLinks[0];
/*  577 */       List sText = secondLevelOfLinks[1];
/*  578 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  581 */         out.append("<tr>");
/*  582 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  583 */         out.append("</tr>");
/*  584 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  586 */           String borderclass = "";
/*      */           
/*      */ 
/*  589 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  591 */           out.append("<tr>");
/*      */           
/*  593 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  594 */           if (sLinks.get(i).toString().length() == 0) {
/*  595 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  598 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  600 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  604 */     out.append("</table>");
/*  605 */     return out.toString();
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
/*  618 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  627 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  630 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  633 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  636 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  639 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  647 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  652 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  657 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  662 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  667 */     if (val != null)
/*      */     {
/*  669 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  673 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  678 */     if (val == null) {
/*  679 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  683 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  688 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  694 */     if (val != null)
/*      */     {
/*  696 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  700 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  706 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  711 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  715 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  720 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  725 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  730 */     String hostaddress = "";
/*  731 */     String ip = request.getHeader("x-forwarded-for");
/*  732 */     if (ip == null)
/*  733 */       ip = request.getRemoteAddr();
/*  734 */     InetAddress add = null;
/*  735 */     if (ip.equals("127.0.0.1")) {
/*  736 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  740 */       add = InetAddress.getByName(ip);
/*      */     }
/*  742 */     hostaddress = add.getHostName();
/*  743 */     if (hostaddress.indexOf('.') != -1) {
/*  744 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  745 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  749 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  754 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  760 */     if (severity == null)
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  764 */     if (severity.equals("5"))
/*      */     {
/*  766 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  768 */     if (severity.equals("1"))
/*      */     {
/*  770 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  775 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  780 */     ResultSet set = null;
/*  781 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  782 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  784 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  785 */       if (set.next()) { String str1;
/*  786 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  787 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  790 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  795 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  798 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  800 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  804 */     StringBuffer rca = new StringBuffer();
/*  805 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  806 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  809 */     int rcalength = key.length();
/*  810 */     String split = "6. ";
/*  811 */     int splitPresent = key.indexOf(split);
/*  812 */     String div1 = "";String div2 = "";
/*  813 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  815 */       if (rcalength > 180) {
/*  816 */         rca.append("<span class=\"rca-critical-text\">");
/*  817 */         getRCATrimmedText(key, rca);
/*  818 */         rca.append("</span>");
/*      */       } else {
/*  820 */         rca.append("<span class=\"rca-critical-text\">");
/*  821 */         rca.append(key);
/*  822 */         rca.append("</span>");
/*      */       }
/*  824 */       return rca.toString();
/*      */     }
/*  826 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  827 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  828 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  829 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  830 */     getRCATrimmedText(div1, rca);
/*  831 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  834 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  835 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  836 */     getRCATrimmedText(div2, rca);
/*  837 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  839 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  844 */     String[] st = msg.split("<br>");
/*  845 */     for (int i = 0; i < st.length; i++) {
/*  846 */       String s = st[i];
/*  847 */       if (s.length() > 180) {
/*  848 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  850 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  854 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  855 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  857 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  861 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  862 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  863 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  866 */       if (key == null) {
/*  867 */         return ret;
/*      */       }
/*      */       
/*  870 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  871 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  874 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  875 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  876 */       set = AMConnectionPool.executeQueryStmt(query);
/*  877 */       if (set.next())
/*      */       {
/*  879 */         String helpLink = set.getString("LINK");
/*  880 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  883 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  889 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  908 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  899 */         if (set != null) {
/*  900 */           AMConnectionPool.closeStatement(set);
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
/*  914 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  915 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  917 */       String entityStr = (String)keys.nextElement();
/*  918 */       String mmessage = temp.getProperty(entityStr);
/*  919 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  920 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  922 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  928 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  929 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  931 */       String entityStr = (String)keys.nextElement();
/*  932 */       String mmessage = temp.getProperty(entityStr);
/*  933 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  934 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  936 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  941 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  951 */     String des = new String();
/*  952 */     while (str.indexOf(find) != -1) {
/*  953 */       des = des + str.substring(0, str.indexOf(find));
/*  954 */       des = des + replace;
/*  955 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  957 */     des = des + str;
/*  958 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  965 */       if (alert == null)
/*      */       {
/*  967 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  969 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  971 */         return "&nbsp;";
/*      */       }
/*      */       
/*  974 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  976 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  979 */       int rcalength = test.length();
/*  980 */       if (rcalength < 300)
/*      */       {
/*  982 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  986 */       StringBuffer out = new StringBuffer();
/*  987 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  988 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  989 */       out.append("</div>");
/*  990 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  991 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  992 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  997 */       ex.printStackTrace();
/*      */     }
/*  999 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1005 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1010 */     ArrayList attribIDs = new ArrayList();
/* 1011 */     ArrayList resIDs = new ArrayList();
/* 1012 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1014 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1016 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1018 */       String resourceid = "";
/* 1019 */       String resourceType = "";
/* 1020 */       if (type == 2) {
/* 1021 */         resourceid = (String)row.get(0);
/* 1022 */         resourceType = (String)row.get(3);
/*      */       }
/* 1024 */       else if (type == 3) {
/* 1025 */         resourceid = (String)row.get(0);
/* 1026 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1029 */         resourceid = (String)row.get(6);
/* 1030 */         resourceType = (String)row.get(7);
/*      */       }
/* 1032 */       resIDs.add(resourceid);
/* 1033 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1034 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1036 */       String healthentity = null;
/* 1037 */       String availentity = null;
/* 1038 */       if (healthid != null) {
/* 1039 */         healthentity = resourceid + "_" + healthid;
/* 1040 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1043 */       if (availid != null) {
/* 1044 */         availentity = resourceid + "_" + availid;
/* 1045 */         entitylist.add(availentity);
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
/* 1059 */     Properties alert = getStatus(entitylist);
/* 1060 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1065 */     int size = monitorList.size();
/*      */     
/* 1067 */     String[] severity = new String[size];
/*      */     
/* 1069 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1071 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1072 */       String resourceName1 = (String)row1.get(7);
/* 1073 */       String resourceid1 = (String)row1.get(6);
/* 1074 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1075 */       if (severity[j] == null)
/*      */       {
/* 1077 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1081 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1083 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1085 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1088 */         if (sev > 0) {
/* 1089 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1090 */           monitorList.set(k, monitorList.get(j));
/* 1091 */           monitorList.set(j, t);
/* 1092 */           String temp = severity[k];
/* 1093 */           severity[k] = severity[j];
/* 1094 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1100 */     int z = 0;
/* 1101 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1104 */       int i = 0;
/* 1105 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1108 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1112 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1116 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1118 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1121 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1125 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1128 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1129 */       String resourceName1 = (String)row1.get(7);
/* 1130 */       String resourceid1 = (String)row1.get(6);
/* 1131 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1132 */       if (hseverity[j] == null)
/*      */       {
/* 1134 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1139 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1141 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1144 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1147 */         if (hsev > 0) {
/* 1148 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1149 */           monitorList.set(k, monitorList.get(j));
/* 1150 */           monitorList.set(j, t);
/* 1151 */           String temp1 = hseverity[k];
/* 1152 */           hseverity[k] = hseverity[j];
/* 1153 */           hseverity[j] = temp1;
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
/* 1165 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1166 */     boolean forInventory = false;
/* 1167 */     String trdisplay = "none";
/* 1168 */     String plusstyle = "inline";
/* 1169 */     String minusstyle = "none";
/* 1170 */     String haidTopLevel = "";
/* 1171 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1173 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1175 */         haidTopLevel = request.getParameter("haid");
/* 1176 */         forInventory = true;
/* 1177 */         trdisplay = "table-row;";
/* 1178 */         plusstyle = "none";
/* 1179 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1186 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1189 */     ArrayList listtoreturn = new ArrayList();
/* 1190 */     StringBuffer toreturn = new StringBuffer();
/* 1191 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1192 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1193 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1195 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1197 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1198 */       String childresid = (String)singlerow.get(0);
/* 1199 */       String childresname = (String)singlerow.get(1);
/* 1200 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1201 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1202 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1203 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1204 */       String unmanagestatus = (String)singlerow.get(5);
/* 1205 */       String actionstatus = (String)singlerow.get(6);
/* 1206 */       String linkclass = "monitorgp-links";
/* 1207 */       String titleforres = childresname;
/* 1208 */       String titilechildresname = childresname;
/* 1209 */       String childimg = "/images/trcont.png";
/* 1210 */       String flag = "enable";
/* 1211 */       String dcstarted = (String)singlerow.get(8);
/* 1212 */       String configMonitor = "";
/* 1213 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1214 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1216 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1218 */       if (singlerow.get(7) != null)
/*      */       {
/* 1220 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1222 */       String haiGroupType = "0";
/* 1223 */       if ("HAI".equals(childtype))
/*      */       {
/* 1225 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1227 */       childimg = "/images/trend.png";
/* 1228 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1229 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1230 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1232 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1234 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1236 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1237 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1240 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1242 */         linkclass = "disabledtext";
/* 1243 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1245 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1246 */       String availmouseover = "";
/* 1247 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1249 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1251 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1252 */       String healthmouseover = "";
/* 1253 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1255 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1258 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1259 */       int spacing = 0;
/* 1260 */       if (level >= 1)
/*      */       {
/* 1262 */         spacing = 40 * level;
/*      */       }
/* 1264 */       if (childtype.equals("HAI"))
/*      */       {
/* 1266 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1267 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1268 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1270 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1271 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1272 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1273 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1274 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1275 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1276 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1277 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1278 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1279 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1280 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1282 */         if (!forInventory)
/*      */         {
/* 1284 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1287 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1289 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1291 */           actions = editlink + actions;
/*      */         }
/* 1293 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1295 */           actions = actions + associatelink;
/*      */         }
/* 1297 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1298 */         String arrowimg = "";
/* 1299 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/* 1304 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1306 */         if (isIt360)
/*      */         {
/* 1308 */           actionimg = "";
/* 1309 */           actions = "";
/* 1310 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1311 */           checkbox = "";
/*      */         }
/*      */         
/* 1314 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1316 */           actions = "";
/*      */         }
/* 1318 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1320 */           checkbox = "";
/*      */         }
/*      */         
/* 1323 */         String resourcelink = "";
/*      */         
/* 1325 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1327 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1331 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1334 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1337 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1339 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1340 */         if (!isIt360)
/*      */         {
/* 1342 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1349 */         toreturn.append("</tr>");
/* 1350 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1352 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1353 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1357 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1358 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1361 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1365 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1367 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1368 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1369 */             toreturn.append(assocMessage);
/* 1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1372 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1373 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1379 */         String resourcelink = null;
/* 1380 */         boolean hideEditLink = false;
/* 1381 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1383 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1384 */           hideEditLink = true;
/* 1385 */           if (isIt360)
/*      */           {
/* 1387 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1391 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1393 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1395 */           hideEditLink = true;
/* 1396 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1397 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1402 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1405 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1406 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1407 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1408 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1409 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1410 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1411 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1412 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1413 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1414 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1415 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1416 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1417 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1419 */         if (hideEditLink)
/*      */         {
/* 1421 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1423 */         if (!forInventory)
/*      */         {
/* 1425 */           removefromgroup = "";
/*      */         }
/* 1427 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1428 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1429 */           actions = actions + configcustomfields;
/*      */         }
/* 1431 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1433 */           actions = editlink + actions;
/*      */         }
/* 1435 */         String managedLink = "";
/* 1436 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1438 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1439 */           actions = "";
/* 1440 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1441 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1444 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1446 */           checkbox = "";
/*      */         }
/*      */         
/* 1449 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1451 */           actions = "";
/*      */         }
/* 1453 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1456 */         if (isIt360)
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1464 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1465 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1466 */         if (!isIt360)
/*      */         {
/* 1468 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1472 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1474 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1477 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1484 */       StringBuilder toreturn = new StringBuilder();
/* 1485 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1486 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1487 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1488 */       String title = "";
/* 1489 */       message = EnterpriseUtil.decodeString(message);
/* 1490 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1491 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1492 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1494 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1496 */       else if ("5".equals(severity))
/*      */       {
/* 1498 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1502 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1504 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1505 */       toreturn.append(v);
/*      */       
/* 1507 */       toreturn.append(link);
/* 1508 */       if (severity == null)
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("5"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       else if (severity.equals("4"))
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       else if (severity.equals("1"))
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1527 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1529 */       toreturn.append("</a>");
/* 1530 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1534 */       ex.printStackTrace();
/*      */     }
/* 1536 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1543 */       StringBuilder toreturn = new StringBuilder();
/* 1544 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1545 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1546 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1547 */       if (message == null)
/*      */       {
/* 1549 */         message = "";
/*      */       }
/*      */       
/* 1552 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1553 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1555 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1556 */       toreturn.append(v);
/*      */       
/* 1558 */       toreturn.append(link);
/*      */       
/* 1560 */       if (severity == null)
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1564 */       else if (severity.equals("5"))
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       else if (severity.equals("1"))
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1575 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1577 */       toreturn.append("</a>");
/* 1578 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1584 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1587 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1588 */     if (invokeActions != null) {
/* 1589 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1590 */       while (iterator.hasNext()) {
/* 1591 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1592 */         if (actionmap.containsKey(actionid)) {
/* 1593 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1598 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1602 */     String actionLink = "";
/* 1603 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1604 */     String query = "";
/* 1605 */     ResultSet rs = null;
/* 1606 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1607 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1608 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1609 */       actionLink = "method=" + methodName;
/*      */     }
/* 1611 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1612 */       actionLink = methodName;
/*      */     }
/* 1614 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1615 */     Iterator itr = methodarglist.iterator();
/* 1616 */     boolean isfirstparam = true;
/* 1617 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1618 */     while (itr.hasNext()) {
/* 1619 */       HashMap argmap = (HashMap)itr.next();
/* 1620 */       String argtype = (String)argmap.get("TYPE");
/* 1621 */       String argname = (String)argmap.get("IDENTITY");
/* 1622 */       String paramname = (String)argmap.get("PARAMETER");
/* 1623 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1624 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1625 */         isfirstparam = false;
/* 1626 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1628 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1632 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1636 */         actionLink = actionLink + "&";
/*      */       }
/* 1638 */       String paramValue = null;
/* 1639 */       String tempargname = argname;
/* 1640 */       if (commonValues.getProperty(tempargname) != null) {
/* 1641 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1644 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1645 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1646 */           if (dbType.equals("mysql")) {
/* 1647 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1650 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1652 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1654 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1655 */             if (rs.next()) {
/* 1656 */               paramValue = rs.getString("VALUE");
/* 1657 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1661 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1665 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1668 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1673 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1674 */           paramValue = rowId;
/*      */         }
/* 1676 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1677 */           paramValue = managedObjectName;
/*      */         }
/* 1679 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1680 */           paramValue = resID;
/*      */         }
/* 1682 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1683 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1686 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1688 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1689 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1690 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1692 */     return actionLink;
/*      */   }
/*      */   
/* 1695 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1696 */     String dependentAttribute = null;
/* 1697 */     String align = "left";
/*      */     
/* 1699 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1700 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1701 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1702 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1703 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1704 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1705 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1706 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1707 */       align = "center";
/*      */     }
/*      */     
/* 1710 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1711 */     String actualdata = "";
/*      */     
/* 1713 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1714 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1715 */         actualdata = availValue;
/*      */       }
/* 1717 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1718 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1722 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1723 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1726 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1732 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1733 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1734 */       toreturn.append("<table>");
/* 1735 */       toreturn.append("<tr>");
/* 1736 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1737 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1738 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1739 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1740 */         String toolTip = "";
/* 1741 */         String hideClass = "";
/* 1742 */         String textStyle = "";
/* 1743 */         boolean isreferenced = true;
/* 1744 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1745 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1746 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1747 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1749 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1750 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1751 */           while (valueList.hasMoreTokens()) {
/* 1752 */             String dependentVal = valueList.nextToken();
/* 1753 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1754 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1755 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1757 */               toolTip = "";
/* 1758 */               hideClass = "";
/* 1759 */               isreferenced = false;
/* 1760 */               textStyle = "disabledtext";
/* 1761 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1765 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1766 */           toolTip = "";
/* 1767 */           hideClass = "";
/* 1768 */           isreferenced = false;
/* 1769 */           textStyle = "disabledtext";
/* 1770 */           if (dependentImageMap != null) {
/* 1771 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1772 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1775 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1779 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1780 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1781 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1782 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1783 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1784 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1786 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1787 */           if (isreferenced) {
/* 1788 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1792 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1793 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1794 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1795 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1796 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1797 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1799 */           toreturn.append("</span>");
/* 1800 */           toreturn.append("</a>");
/* 1801 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1804 */       toreturn.append("</tr>");
/* 1805 */       toreturn.append("</table>");
/* 1806 */       toreturn.append("</td>");
/*      */     } else {
/* 1808 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1811 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1815 */     String colTime = null;
/* 1816 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1817 */     if ((rows != null) && (rows.size() > 0)) {
/* 1818 */       Iterator<String> itr = rows.iterator();
/* 1819 */       String maxColQuery = "";
/* 1820 */       for (;;) { if (itr.hasNext()) {
/* 1821 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1822 */           ResultSet maxCol = null;
/*      */           try {
/* 1824 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1825 */             while (maxCol.next()) {
/* 1826 */               if (colTime == null) {
/* 1827 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1830 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1839 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1841 */               if (maxCol != null)
/* 1842 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1844 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1839 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1841 */               if (maxCol != null)
/* 1842 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1844 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1849 */     return colTime;
/*      */   }
/*      */   
/* 1852 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1853 */     tablename = null;
/* 1854 */     ResultSet rsTable = null;
/* 1855 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1857 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1858 */       while (rsTable.next()) {
/* 1859 */         tablename = rsTable.getString("DATATABLE");
/* 1860 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1861 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1874 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1865 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1868 */         if (rsTable != null)
/* 1869 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1871 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1877 */     String argsList = "";
/* 1878 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1880 */       if (showArgsMap.get(row) != null) {
/* 1881 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1882 */         if (showArgslist != null) {
/* 1883 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1884 */             if (argsList.trim().equals("")) {
/* 1885 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1888 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1895 */       e.printStackTrace();
/* 1896 */       return "";
/*      */     }
/* 1898 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1903 */     String argsList = "";
/* 1904 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1907 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1909 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1910 */         if (hideArgsList != null)
/*      */         {
/* 1912 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1914 */             if (argsList.trim().equals(""))
/*      */             {
/* 1916 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1920 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1928 */       ex.printStackTrace();
/*      */     }
/* 1930 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1934 */     StringBuilder toreturn = new StringBuilder();
/* 1935 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1942 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1943 */       Iterator itr = tActionList.iterator();
/* 1944 */       while (itr.hasNext()) {
/* 1945 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1946 */         String confirmmsg = "";
/* 1947 */         String link = "";
/* 1948 */         String isJSP = "NO";
/* 1949 */         HashMap tactionMap = (HashMap)itr.next();
/* 1950 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1951 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1952 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1953 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1954 */           (actionmap.containsKey(actionId))) {
/* 1955 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1956 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1957 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1958 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1959 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1961 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1967 */           if (isTableAction) {
/* 1968 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1971 */             tableName = "Link";
/* 1972 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1973 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1974 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1975 */             toreturn.append("</a></td>");
/*      */           }
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1979 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1980 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1986 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1992 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1994 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1995 */       Properties prop = (Properties)node.getUserObject();
/* 1996 */       String mgID = prop.getProperty("label");
/* 1997 */       String mgName = prop.getProperty("value");
/* 1998 */       String isParent = prop.getProperty("isParent");
/* 1999 */       int mgIDint = Integer.parseInt(mgID);
/* 2000 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2002 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2004 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2005 */       if (node.getChildCount() > 0)
/*      */       {
/* 2007 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2009 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2011 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2013 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2017 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2022 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2024 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2026 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2028 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2032 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2035 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2036 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2038 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2042 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2044 */       if (node.getChildCount() > 0)
/*      */       {
/* 2046 */         builder.append("<UL>");
/* 2047 */         printMGTree(node, builder);
/* 2048 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2053 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2054 */     StringBuffer toReturn = new StringBuffer();
/* 2055 */     String table = "-";
/*      */     try {
/* 2057 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2058 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2059 */       float total = 0.0F;
/* 2060 */       while (it.hasNext()) {
/* 2061 */         String attName = (String)it.next();
/* 2062 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2063 */         boolean roundOffData = false;
/* 2064 */         if ((data != null) && (!data.equals(""))) {
/* 2065 */           if (data.indexOf(",") != -1) {
/* 2066 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2069 */             float value = Float.parseFloat(data);
/* 2070 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2073 */             total += value;
/* 2074 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2077 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2082 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2083 */       while (attVsWidthList.hasNext()) {
/* 2084 */         String attName = (String)attVsWidthList.next();
/* 2085 */         String data = (String)attVsWidthProps.get(attName);
/* 2086 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2087 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2088 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2089 */         String className = (String)graphDetails.get("ClassName");
/* 2090 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2091 */         if (percentage < 1.0F)
/*      */         {
/* 2093 */           data = percentage + "";
/*      */         }
/* 2095 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2097 */       if (toReturn.length() > 0) {
/* 2098 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2102 */       e.printStackTrace();
/*      */     }
/* 2104 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2110 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2111 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2112 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2113 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2114 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2115 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2116 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2117 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2118 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2121 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2122 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2123 */       splitvalues[0] = multiplecondition.toString();
/* 2124 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2127 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2132 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2133 */     if (thresholdType != 3) {
/* 2134 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2135 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2136 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2137 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2138 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2139 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2141 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2142 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2143 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2144 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2145 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2146 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2148 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2149 */     if (updateSelected != null) {
/* 2150 */       updateSelected[0] = "selected";
/*      */     }
/* 2152 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2157 */       StringBuffer toreturn = new StringBuffer("");
/* 2158 */       if (commaSeparatedMsgId != null) {
/* 2159 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2160 */         int count = 0;
/* 2161 */         while (msgids.hasMoreTokens()) {
/* 2162 */           String id = msgids.nextToken();
/* 2163 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2164 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2165 */           count++;
/* 2166 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2167 */             if (toreturn.length() == 0) {
/* 2168 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2170 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2171 */             if (!image.trim().equals("")) {
/* 2172 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2174 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2175 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2178 */         if (toreturn.length() > 0) {
/* 2179 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2183 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2186 */       e.printStackTrace(); }
/* 2187 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2193 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2199 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2200 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/MySqlLeftPage.jsp", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2226 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2246 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2251 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2253 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2254 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2264 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2271 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2274 */     JspWriter out = null;
/* 2275 */     Object page = this;
/* 2276 */     JspWriter _jspx_out = null;
/* 2277 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2281 */       response.setContentType("text/html;charset=UTF-8");
/* 2282 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2284 */       _jspx_page_context = pageContext;
/* 2285 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2286 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2287 */       session = pageContext.getSession();
/* 2288 */       out = pageContext.getOut();
/* 2289 */       _jspx_out = out;
/*      */       
/* 2291 */       out.write("<!DOCTYPE html>\n");
/* 2292 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/* 2293 */       request.setAttribute("HelpKey", "Monitors MySQL Details");
/* 2294 */       out.write(10);
/* 2295 */       GetWLSGraph wlsGraph = null;
/* 2296 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2297 */       if (wlsGraph == null) {
/* 2298 */         wlsGraph = new GetWLSGraph();
/* 2299 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2301 */       out.write(10);
/* 2302 */       MySqlGraphs mysqlgraph = null;
/* 2303 */       mysqlgraph = (MySqlGraphs)_jspx_page_context.getAttribute("mysqlgraph", 1);
/* 2304 */       if (mysqlgraph == null) {
/* 2305 */         mysqlgraph = new MySqlGraphs();
/* 2306 */         _jspx_page_context.setAttribute("mysqlgraph", mysqlgraph, 1);
/*      */       }
/* 2308 */       out.write(10);
/* 2309 */       PerformanceBean perfgraph = null;
/* 2310 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2311 */       if (perfgraph == null) {
/* 2312 */         perfgraph = new PerformanceBean();
/* 2313 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2315 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2317 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2318 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2319 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2321 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2322 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2323 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2325 */           out.write(10);
/* 2326 */           out.write(9);
/* 2327 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2329 */           out.write(10);
/* 2330 */           out.write(9);
/* 2331 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2333 */           out.write(10);
/* 2334 */           out.write(9);
/* 2335 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2337 */           out.write(10);
/* 2338 */           out.write(10);
/* 2339 */           out.write(9);
/*      */           
/* 2341 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2342 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2343 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2345 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2347 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2348 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2349 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2350 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2351 */               out = _jspx_page_context.pushBody();
/* 2352 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2353 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2356 */               out.write(" <SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/MySQL.js\"></SCRIPT> ");
/* 2357 */               out.write("\n\t\t\n\t\t<script>\n\n \n</script>\n\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t<tr>\n\t\t\t\t");
/*      */               
/* 2359 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2361 */               String haid = request.getParameter("haid");
/* 2362 */               String haName = null;
/* 2363 */               if (haid != null) {
/* 2364 */                 haName = (String)ht.get(haid);
/*      */               }
/*      */               
/* 2367 */               out.write("\n\t\t\t\t");
/*      */               
/* 2369 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2370 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2371 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2373 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid) && param.haid!='null'}");
/* 2374 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2375 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2377 */                   out.write("\n\t\t\t\t\t<td class=\"bcsign breadcrumb_btm_space\" valign=\"top\">");
/* 2378 */                   out.print(BreadcrumbUtil.getHomePage(request));
/* 2379 */                   out.write("\n\t\t\t\t\t&gt; ");
/* 2380 */                   out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2381 */                   out.write("\n\t\t\t\t\t&gt; <span class=\"bcactive\">");
/* 2382 */                   out.print(request.getAttribute("monitorname"));
/* 2383 */                   out.write("\n\t\t\t\t\t</span></td>\n\t\t\t\t");
/* 2384 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2385 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2389 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2390 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2393 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2394 */               out.write("\n\t\t\t\t");
/*      */               
/* 2396 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2397 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2398 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2400 */               _jspx_th_c_005fif_005f3.setTest("${(empty param.haid || (!empty invalidhaid) || param.haid=='null')}");
/* 2401 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2402 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2404 */                   out.write("\n\t\t\t\t\t<td class=\"bcsign breadcrumb_btm_space\" valign=\"top\">");
/* 2405 */                   out.print(BreadcrumbUtil.getMonitorsPage());
/* 2406 */                   out.write("\n\t\t\t\t\t&gt; ");
/* 2407 */                   out.print(BreadcrumbUtil.getMonitorResourceTypes("MYSQL-DB-server"));
/* 2408 */                   out.write("\n\t\t\t\t\t&gt; <span class=\"bcactive\">");
/* 2409 */                   out.print(request.getAttribute("monitorname"));
/* 2410 */                   out.write("\n\t\t\t\t\t</span></td>\n\t\t\t\t");
/* 2411 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2412 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2416 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2417 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2420 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2421 */               out.write("\n\t\t\t</tr>\n\t\t</table>\n\n\t\t");
/*      */               
/* 2423 */               String resourceid = request.getParameter("resourceid");
/* 2424 */               String name = (String)request.getAttribute("name");
/* 2425 */               String appname = (String)request.getAttribute("appName");
/* 2426 */               String datatypestr = request.getParameter("datatype");
/*      */               
/* 2428 */               int datatype = 1;
/* 2429 */               if (datatypestr != null) {
/* 2430 */                 datatype = Integer.parseInt(datatypestr);
/*      */               }
/*      */               
/* 2433 */               out.write("\n\n\t\t");
/*      */               
/* 2435 */               String selected = "";
/* 2436 */               if (datatype == 1) {
/* 2437 */                 selected = "am.javaruntime.tab.overview";
/*      */               }
/* 2439 */               else if (datatype == 2) {
/* 2440 */                 selected = "am.javaruntime.tab.configuration";
/*      */               }
/*      */               
/* 2443 */               String editpage = request.getParameter("editPage");
/* 2444 */               String display = "none";
/* 2445 */               if ((editpage != null) && (editpage.equals("true"))) {
/* 2446 */                 display = "block";
/*      */               }
/*      */               
/* 2449 */               out.write("\n\n\n\t\t");
/* 2450 */               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.javaruntime.tab.overview,am.javaruntime.tab.configuration", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.javaruntime.tab.overview,am.javaruntime.tab.configuration", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getOverviewData,getConfigurationData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 2451 */               out.write("\n\n\t\t<br>\n\t\t<div id=\"edit\" style=\"DISPLAY: none\">");
/* 2452 */               JspRuntimeLibrary.include(request, response, "/jsp/MySqlConfig.jsp?reconfigure=true&configured=true", out, false);
/* 2453 */               out.write(" <br>\n\t\t</div>\n\n\t\t<table border=\"0\" width=\"100%\">\n\t\t\t<tr>\n\t\t\t\t<td>\n\t\t\t\t<div id=\"data\">\n\t\t\t\t");
/*      */               
/* 2455 */               if (datatype == 1)
/*      */               {
/* 2457 */                 out.write(32);
/* 2458 */                 JspRuntimeLibrary.include(request, response, "/jsp/MySqlOverview.jsp" + ("/jsp/MySqlOverview.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2459 */                 out.write(32);
/*      */ 
/*      */               }
/* 2462 */               else if (datatype == 2)
/*      */               {
/* 2464 */                 out.write(32);
/* 2465 */                 JspRuntimeLibrary.include(request, response, "/jsp/MySqlConfiguration.jsp" + ("/jsp/MySqlConfiguration.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2466 */                 out.write(32);
/*      */               }
/*      */               
/*      */ 
/* 2470 */               out.write("\n\t\t\t\t</div>\n\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t");
/* 2471 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2472 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2475 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2476 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2479 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2480 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 2483 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2484 */           out.write(32);
/* 2485 */           out.write("\n\t\n\n\t");
/* 2486 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2488 */           out.write(10);
/* 2489 */           out.write(9);
/*      */           
/* 2491 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2492 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 2493 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2495 */           _jspx_th_tiles_005fput_005f5.setName("ServerLeftArea");
/*      */           
/* 2497 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 2498 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 2499 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 2500 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 2501 */               out = _jspx_page_context.pushBody();
/* 2502 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 2503 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2506 */               out.write(10);
/* 2507 */               out.write(9);
/* 2508 */               out.write(9);
/* 2509 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2510 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2512 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2513 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2514 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2516 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2518 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2520 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2522 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2523 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2524 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2525 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2528 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2529 */               String available = null;
/* 2530 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2531 */               out.write(10);
/*      */               
/* 2533 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2534 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2535 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2537 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2539 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2541 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2543 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2544 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2545 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2546 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2549 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2550 */               String unavailable = null;
/* 2551 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2552 */               out.write(10);
/*      */               
/* 2554 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2555 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2556 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2558 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2560 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2562 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2564 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2565 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2566 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2567 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2570 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2571 */               String unmanaged = null;
/* 2572 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2573 */               out.write(10);
/*      */               
/* 2575 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2576 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2577 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2579 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2581 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2583 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2585 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2586 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2587 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2588 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2591 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2592 */               String scheduled = null;
/* 2593 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2594 */               out.write(10);
/*      */               
/* 2596 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2597 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2598 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2600 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2602 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2604 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2606 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2607 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2608 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2609 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2612 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2613 */               String critical = null;
/* 2614 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2615 */               out.write(10);
/*      */               
/* 2617 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2618 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2619 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2621 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2623 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2625 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2627 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2628 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2629 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2630 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2633 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2634 */               String clear = null;
/* 2635 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2636 */               out.write(10);
/*      */               
/* 2638 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2639 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2640 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 2642 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2644 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2646 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2648 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2649 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2650 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2651 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2654 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2655 */               String warning = null;
/* 2656 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2657 */               out.write(10);
/* 2658 */               out.write(10);
/*      */               
/* 2660 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2661 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2663 */               out.write(10);
/* 2664 */               out.write(10);
/* 2665 */               out.write(10);
/* 2666 */               out.write("\n\n\n<script>\n");
/*      */               
/* 2668 */               if (request.getParameter("editPage") != null)
/*      */               {
/* 2670 */                 out.write("\n toggleDiv('edit');\n");
/*      */               }
/*      */               
/* 2673 */               out.write("\n</script>\n\n\n");
/*      */               
/* 2675 */               String haid = null;
/* 2676 */               String appname = null;
/* 2677 */               String network = null;
/* 2678 */               haid = (String)request.getAttribute("haid");
/* 2679 */               appname = (String)request.getAttribute("appName");
/* 2680 */               String resourcename = (String)request.getAttribute("name");
/* 2681 */               String resourceid = request.getParameter("resourceid");
/* 2682 */               String configured = (String)request.getAttribute("configured");
/* 2683 */               String displayname = null;
/* 2684 */               if (request.getParameter("configure") != null)
/*      */               {
/* 2686 */                 displayname = (String)request.getAttribute("displayname");
/* 2687 */                 if (displayname == null)
/*      */                 {
/* 2689 */                   displayname = request.getParameter("resourcename");
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/* 2694 */                 displayname = request.getParameter("resourcename");
/*      */               }
/* 2696 */               ArrayList attribIDs = new ArrayList();
/* 2697 */               ArrayList resIDs = new ArrayList();
/* 2698 */               attribIDs.add("115");
/* 2699 */               attribIDs.add("116");
/* 2700 */               resIDs.add(resourceid);
/* 2701 */               Properties alert = getStatus(resIDs, attribIDs);
/* 2702 */               String healthStatus = alert.getProperty(resourceid + "#" + "116");
/* 2703 */               String avaStatus = alert.getProperty(resourceid + "#" + "115");
/* 2704 */               if (configured.equals("false"))
/*      */               {
/* 2706 */                 out.write("\n<br>\n");
/* 2707 */                 out.write("\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td class=\"leftlinksheading\"><img\n\t\t\tsrc=\"/images/");
/* 2708 */                 if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 2710 */                 out.write("/img_quicknote.gif\"\n\t\t\twidth=\"140\" height=\"30\"></td>\n\t</tr>\n\t<tr>\n\t\t<td class=\"quicknote\">");
/* 2711 */                 out.print(FormatUtil.getString("am.webclient.mysqlleftpage.quicknote.text"));
/* 2712 */                 out.write("\n\t\t</td>\n\t</tr>\n</table>\n\n");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 2717 */                 out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" class=\"leftlinksheading\">");
/* 2718 */                 out.print(FormatUtil.getString("am.webclient.mysql.servertype"));
/* 2719 */                 out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\">");
/*      */                 
/* 2721 */                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2722 */                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2723 */                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/* 2724 */                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2725 */                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                   for (;;) {
/* 2727 */                     out.write("\n\t\t\t");
/*      */                     
/* 2729 */                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2730 */                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2731 */                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                     
/* 2733 */                     _jspx_th_c_005fwhen_005f0.setTest("${param.all!='true'}");
/* 2734 */                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2735 */                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                       for (;;) {
/* 2737 */                         out.write("\n\t\t\t\t");
/* 2738 */                         out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2739 */                         out.write("\n\t\t\t");
/* 2740 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2741 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2745 */                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2746 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                     }
/*      */                     
/* 2749 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2750 */                     out.write("\n\t\t\t");
/*      */                     
/* 2752 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2753 */                     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2754 */                     _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2755 */                     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2756 */                     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                       for (;;) {
/* 2758 */                         out.write("\n\t\t\t\t<a\n\t\t\t\t\thref=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2759 */                         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                           return;
/* 2761 */                         out.write("&haid=");
/* 2762 */                         out.print(haid);
/* 2763 */                         out.write("\"\n\t\t\t\t\tclass=\"new-left-links\">");
/* 2764 */                         out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2765 */                         out.write("</a>\n\t\t\t");
/* 2766 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2767 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2771 */                     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2772 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                     }
/*      */                     
/* 2775 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2776 */                     out.write(10);
/* 2777 */                     out.write(9);
/* 2778 */                     out.write(9);
/* 2779 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2780 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2784 */                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2785 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                 }
/*      */                 
/* 2788 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2789 */                 out.write("</td>\n\t</tr>\n\t");
/*      */                 
/* 2791 */                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2792 */                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2793 */                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 2795 */                 _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO}");
/* 2796 */                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2797 */                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                   for (;;) {
/* 2799 */                     out.write(10);
/* 2800 */                     out.write(9);
/* 2801 */                     out.write(9);
/*      */                     
/* 2803 */                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2804 */                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2805 */                     _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                     
/* 2807 */                     _jspx_th_c_005fif_005f5.setTest("${showdata=='2'}");
/* 2808 */                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2809 */                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                       for (;;) {
/* 2811 */                         out.write("\n\t\t\t<tr>\n\t\t\t\t<td width=\"87%\" class=\"leftlinkstd\">");
/*      */                         
/* 2813 */                         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2814 */                         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2815 */                         _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f5);
/* 2816 */                         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2817 */                         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                           for (;;) {
/* 2819 */                             out.write("\n\t\t\t\t\t");
/*      */                             
/* 2821 */                             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2822 */                             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2823 */                             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                             
/* 2825 */                             _jspx_th_c_005fwhen_005f1.setTest("${! empty param.configured && details !='Availability' }");
/* 2826 */                             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2827 */                             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                               for (;;) {
/* 2829 */                                 out.write("\n\t\t\t\t\t\t<a\n\t\t\t\t\t\t\thref=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2830 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                                   return;
/* 2832 */                                 out.write("&haid=");
/* 2833 */                                 out.print(haid);
/* 2834 */                                 out.write("&alert=true\"\n\t\t\t\t\t\t\tclass=\"new-left-links\">");
/* 2835 */                                 out.print(ALERTCONFIG_TEXT);
/* 2836 */                                 out.write("</a>\n\t\t\t\t\t");
/* 2837 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2838 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2842 */                             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2843 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                             }
/*      */                             
/* 2846 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2847 */                             out.write("\n\t\t\t\t\t");
/*      */                             
/* 2849 */                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2850 */                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2851 */                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                             
/* 2853 */                             _jspx_th_c_005fwhen_005f2.setTest("${(param.all=='true')}");
/* 2854 */                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2855 */                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                               for (;;) {
/* 2857 */                                 out.write("\n\t\t\t\t\t\t");
/* 2858 */                                 out.print(ALERTCONFIG_TEXT);
/* 2859 */                                 out.write("\n\t\t\t\t\t");
/* 2860 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2861 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2865 */                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2866 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                             }
/*      */                             
/* 2869 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2870 */                             out.write("\n\t\t\t\t\t");
/*      */                             
/* 2872 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2873 */                             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2874 */                             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2875 */                             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2876 */                             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                               for (;;) {
/* 2878 */                                 out.write("\n\t\t\t\t\t\t<a\n\t\t\t\t\t\t\thref=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2879 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                   return;
/* 2881 */                                 out.write("\"\n\t\t\t\t\t\t\tclass=\"new-left-links\">");
/* 2882 */                                 out.print(ALERTCONFIG_TEXT);
/* 2883 */                                 out.write("</a>\n\t\t\t\t\t");
/* 2884 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2885 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2889 */                             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2890 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                             }
/*      */                             
/* 2893 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2894 */                             out.write("\n\t\t\t\t");
/* 2895 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2896 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2900 */                         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2901 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                         }
/*      */                         
/* 2904 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2905 */                         out.write("</td>\n\t\t\t</tr>\n\t\t");
/* 2906 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2907 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2911 */                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2912 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                     }
/*      */                     
/* 2915 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2916 */                     out.write(10);
/* 2917 */                     out.write(9);
/* 2918 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2919 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2923 */                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2924 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                 }
/*      */                 
/* 2927 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2928 */                 out.write(10);
/* 2929 */                 out.write(9);
/*      */                 
/* 2931 */                 if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(request.getParameter("resourceid")) < EnterpriseUtil.RANGE) && (!request.isUserInRole("OPERATOR")))
/*      */                 {
/*      */ 
/* 2934 */                   out.write("\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\"><a\n\t\t\thref=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">");
/* 2935 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2936 */                   out.write("</a>\n\t\t</td>\n\t</tr>\n\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2941 */                   out.write(10);
/* 2942 */                   out.write(9);
/*      */                   
/* 2944 */                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2945 */                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2946 */                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 2948 */                   _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2949 */                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2950 */                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                     for (;;) {
/* 2952 */                       out.write("\n\t\t<tr>\n\t\t\t<td class=\"leftlinkstd\"><a target=\"mas_window\"\n\t\t\t\thref=\"/showresource.do?resourceid=");
/* 2953 */                       out.print(request.getParameter("resourceid"));
/* 2954 */                       out.write("&type=");
/* 2955 */                       out.print(request.getParameter("type"));
/* 2956 */                       out.write("&moname=");
/* 2957 */                       out.print(request.getParameter("moname"));
/* 2958 */                       out.write("&method=showdetails&resourcename=");
/* 2959 */                       out.print(request.getParameter("resourcename"));
/* 2960 */                       out.write("&aam_jump=true&editPage=true\"\n\t\t\t\tclass=\"new-left-links\"> ");
/* 2961 */                       out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2962 */                       out.write("</a>\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 2963 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2964 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2968 */                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2969 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                   }
/*      */                   
/* 2972 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2973 */                   out.write(10);
/* 2974 */                   out.write(9);
/*      */                 }
/* 2976 */                 out.write(10);
/* 2977 */                 out.write(9);
/*      */                 
/* 2979 */                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2980 */                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2981 */                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 2983 */                 _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 2984 */                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2985 */                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                   for (;;) {
/* 2987 */                     out.write("\n\t\t<tr>\n\t\t\t<td width=\"87%\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"/manageConfMons.do?haid=");
/* 2988 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                       return;
/* 2990 */                     out.write("&method=editPreConfMonitor&resourceid=");
/* 2991 */                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                       return;
/* 2993 */                     out.write("&type=MYSQLDB:3306&resourcename=");
/* 2994 */                     out.print(request.getParameter("moname"));
/* 2995 */                     out.write("&displayName=");
/* 2996 */                     out.print(request.getParameter("resourcename"));
/* 2997 */                     out.write("&\" class=\"new-left-links\">");
/* 2998 */                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2999 */                     out.write("</a>\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 3000 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3001 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3005 */                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3006 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                 }
/*      */                 
/* 3009 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3010 */                 out.write("\n\t<script language=\"JavaScript\">\n function confirmDelete()\n  {\n   ");
/* 3011 */                 if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3013 */                 out.write(10);
/* 3014 */                 out.write(9);
/*      */                 
/* 3016 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3017 */                 _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3018 */                 _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3020 */                 _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3021 */                 int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3022 */                 if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                   for (;;) {
/* 3024 */                     out.write("\n  var s = confirm('");
/* 3025 */                     out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 3026 */                     out.write("')\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=MYSQL-DB-server&select=");
/* 3027 */                     out.print(resourceid);
/* 3028 */                     out.write("\";\n \t ");
/* 3029 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3030 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3034 */                 if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3035 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                 }
/*      */                 
/* 3038 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3039 */                 out.write("\n }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 3040 */                 out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 3041 */                 out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 3042 */                 if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3044 */                 out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 3045 */                 if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3047 */                 out.write("\n\t\t  var show_msg=\"false\";\n      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 3048 */                 out.print(request.getParameter("resourceid"));
/* 3049 */                 out.write("; //No i18n\n      $.ajax({\n        type:'POST', //No i18n\n        url:url,\n        async:false,\n        success: function(data)\n        {\n          show_msg=data\n        }\n      });\n      if(show_msg.indexOf(\"true\")>-1)\n      {\n          alert(\"");
/* 3050 */                 out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 3051 */                 out.write("\");\n\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3052 */                 if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3054 */                 out.write("\";\n       }\n       else { \n\t\t    var s = confirm(\"");
/* 3055 */                 out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 3056 */                 out.write("\");\n    \t\tif (s){\n  \t   \t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3057 */                 if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3059 */                 out.write("\"; //No I18N\n  \t\t\t\t\t}\n  \t\t\t }  \n\t }\n  </script>\n\t");
/*      */                 
/* 3061 */                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3062 */                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3063 */                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3065 */                 _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO}");
/* 3066 */                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3067 */                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                   for (;;) {
/* 3069 */                     out.write("\n\t\t<tr>\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmDelete();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 3070 */                     out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3071 */                     out.write("</A></td>\n\t\t</tr>\n\t");
/* 3072 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3073 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3077 */                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3078 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                 }
/*      */                 
/* 3081 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3082 */                 out.write(10);
/* 3083 */                 out.write(10);
/* 3084 */                 out.write(9);
/*      */                 
/* 3086 */                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3087 */                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3088 */                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3090 */                 _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO}");
/* 3091 */                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3092 */                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                   for (;;) {
/* 3094 */                     out.write("\n\t\t<tr>\n\t\t\t");
/*      */                     
/* 3096 */                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3097 */                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3098 */                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f8);
/*      */                     
/* 3100 */                     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3101 */                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3102 */                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                       for (;;) {
/* 3104 */                         out.write("\n\t\t\t\t<td class=\"leftlinkstd\"><a href=\"javascript:alertUser()\"\n\t\t\t\t\tclass=\"new-left-links\">");
/* 3105 */                         out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescripts"));
/* 3106 */                         out.write("\n\t\t\t\t</a></td>\n\t\t\t");
/* 3107 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3108 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3112 */                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3113 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                     }
/*      */                     
/* 3116 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3117 */                     out.write("\n\t\t\t");
/*      */                     
/* 3119 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3120 */                     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3121 */                     _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                     
/* 3123 */                     _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3124 */                     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3125 */                     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                       for (;;) {
/* 3127 */                         out.write("\n\t\t\t\t<td class=\"leftlinkstd\"><a\n\t\t\t\t\thref='/showresource.do?type=Script Monitor&method=getAssociateMonitors&hostid=");
/* 3128 */                         out.print(resourceid);
/* 3129 */                         out.write("'\n\t\t\t\t\tclass=\"new-left-links\">");
/* 3130 */                         out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescripts"));
/* 3131 */                         out.write("\n\t\t\t\t</a></td>\n\t\t\t");
/* 3132 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3133 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3137 */                     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3138 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                     }
/*      */                     
/* 3141 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3142 */                     out.write("\n\t\t</tr>\n\t");
/* 3143 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3144 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3148 */                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3149 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                 }
/*      */                 
/* 3152 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3153 */                 out.write("\n\n\n\n\t");
/*      */                 
/* 3155 */                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3156 */                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3157 */                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3159 */                 _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3160 */                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3161 */                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                   for (;;) {
/* 3163 */                     out.write("\n\t\t<tr>\n\t\t\t");
/*      */                     
/* 3165 */                     if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                     {
/*      */ 
/* 3168 */                       out.write("\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmManage();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 3169 */                       out.print(FormatUtil.getString("Manage"));
/* 3170 */                       out.write("</A></td>\n\t\t\t");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 3176 */                       out.write("\n\t\t\t<td class=\"leftlinkstd\"><A href=\"javascript:confirmUnManage();\"\n\t\t\t\tclass=\"new-left-links\">");
/* 3177 */                       out.print(FormatUtil.getString("UnManage"));
/* 3178 */                       out.write("</A></td>\n\t\t\t");
/*      */                     }
/*      */                     
/*      */ 
/* 3182 */                     out.write("\n\t\t</tr>\n\t");
/* 3183 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3184 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3188 */                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3189 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                 }
/*      */                 
/* 3192 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3193 */                 out.write(10);
/* 3194 */                 out.write(9);
/*      */                 
/* 3196 */                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3197 */                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3198 */                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3200 */                 _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 3201 */                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3202 */                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                   for (;;) {
/* 3204 */                     out.write("\n\t\t<tr>\n\t\t\t<td colspan=\"2\" class=\"leftlinkstd\">");
/* 3205 */                     out.print(FaultUtil.getAlertTemplateURL(resourceid, displayname, "MYSQL-DB-server", "MySQL Database Server"));
/* 3206 */                     out.write("\n\t\t\t</td>\n\t\t</tr>\n\t");
/* 3207 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3208 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3212 */                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3213 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                 }
/*      */                 
/* 3216 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3217 */                 out.write(10);
/* 3218 */                 out.write(9);
/*      */                 
/* 3220 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3221 */                 _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3222 */                 _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3224 */                 _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3225 */                 int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3226 */                 if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                   for (;;) {
/* 3228 */                     out.write(10);
/* 3229 */                     out.write(9);
/* 3230 */                     out.write(9);
/*      */                     
/* 3232 */                     String resourceid_poll = request.getParameter("resourceid");
/* 3233 */                     String resourcetype_poll = request.getParameter("type");
/*      */                     
/* 3235 */                     out.write("\n\t\t<tr>\n\t\t\t<td width=\"49%\" height=\"21\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3236 */                     out.print(resourceid_poll);
/* 3237 */                     out.write("&resourcetype=");
/* 3238 */                     out.print(resourcetype_poll);
/* 3239 */                     out.write("\"\n\t\t\t\tclass=\"new-left-links\"> ");
/* 3240 */                     out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3241 */                     out.write("</a></td>\n\t\t</tr>\n\n\t");
/* 3242 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3243 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3247 */                 if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3248 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                 }
/*      */                 
/* 3251 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3252 */                 out.write(10);
/* 3253 */                 out.write(9);
/*      */                 
/* 3255 */                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3256 */                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3257 */                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3259 */                 _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3260 */                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3261 */                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                   for (;;) {
/* 3263 */                     out.write("\n\t\t<tr>\n\t\t\t<td width=\"49%\" height=\"21\" class=\"leftlinkstd\"><a\n\t\t\t\thref=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3264 */                     out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3265 */                     out.write("</a></td>\n\t\t\t</td>\n\t");
/* 3266 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3267 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3271 */                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3272 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                 }
/*      */                 
/* 3275 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3276 */                 out.write(10);
/* 3277 */                 out.write(9);
/* 3278 */                 out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                 
/* 3280 */                 if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                 {
/* 3282 */                   Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3283 */                   String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                   
/* 3285 */                   String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3286 */                   String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3287 */                   if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                   {
/* 3289 */                     if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                     {
/*      */ 
/* 3292 */                       out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3293 */                       out.print(ciInfoUrl);
/* 3294 */                       out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3295 */                       out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3296 */                       out.write("</a></td>");
/* 3297 */                       out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3298 */                       out.print(ciRLUrl);
/* 3299 */                       out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3300 */                       out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3301 */                       out.write("</a></td>");
/* 3302 */                       out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                     }
/* 3306 */                     else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                     {
/*      */ 
/* 3309 */                       out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3310 */                       out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3311 */                       out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3312 */                       out.print(ciInfoUrl);
/* 3313 */                       out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3314 */                       out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3315 */                       out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3316 */                       out.print(ciRLUrl);
/* 3317 */                       out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3318 */                       out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3319 */                       out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/* 3324 */                 out.write("\n \n \n\n");
/* 3325 */                 out.write("\n</table>\n<br>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" class=\"leftlinksheading\">");
/* 3326 */                 out.print(FormatUtil.getString("am.webclient.mysql.processlist.show"));
/* 3327 */                 out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"87%\" class=\"leftlinkstd\"><a class=\"new-left-links\"\n\t\t\thref=\"javascript:MM_openBrWindow('/MySql.do?method=triggerprocesslist&resourceid=");
/* 3328 */                 out.print(resourceid);
/* 3329 */                 out.write("','MySql_Process_List','scrollbars=yes,resizable=yes')\">");
/* 3330 */                 out.print(FormatUtil.getString("am.webclient.mysql.processlist.view"));
/* 3331 */                 out.write("</a>\n\t\t</td>\n\t</tr>\n</table>\n<br>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\tclass=\"leftmnutables\">\n\t<tr>\n\t\t<td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3332 */                 out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3333 */                 out.write("</td>\n\t</tr>\n\t<tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n\t\t<td width=\"49%\"><a\thref=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3334 */                 out.print(request.getParameter("resourceid"));
/* 3335 */                 out.write("&attributeid=116')\"\tclass=\"new-left-links\"> ");
/* 3336 */                 out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3337 */                 out.write(" </a></td>\n\t\t<td width=\"51%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3338 */                 out.print(request.getParameter("resourceid"));
/* 3339 */                 out.write("&attributeid=116')\"\t>");
/* 3340 */                 out.print(getSeverityImageForHealth(healthStatus));
/* 3341 */                 out.write("</a></td>\n\t</tr>\n\t<tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n\t\t<td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3342 */                 out.print(request.getParameter("resourceid"));
/* 3343 */                 out.write("&attributeid=115')\" class=\"new-left-links\">");
/* 3344 */                 out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3345 */                 out.write("</a></td>\n\t\t<td width=\"51%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3346 */                 out.print(request.getParameter("resourceid"));
/* 3347 */                 out.write("&attributeid=115')\">");
/* 3348 */                 out.print(getSeverityImageForAvailability(avaStatus));
/* 3349 */                 out.write("</a></td>\n\t</tr>\n</table>\n\n\n");
/*      */                 
/* 3351 */                 ArrayList menupos = new ArrayList(5);
/* 3352 */                 if (request.isUserInRole("OPERATOR")) {
/* 3353 */                   menupos.add("179");
/* 3354 */                   menupos.add("200");
/* 3355 */                   menupos.add("222");
/* 3356 */                   menupos.add("242");
/* 3357 */                   menupos.add("158");
/*      */                 } else {
/* 3359 */                   menupos.add("361");
/*      */                 }
/* 3361 */                 pageContext.setAttribute("menupos", menupos);
/*      */                 
/* 3363 */                 out.write(10);
/* 3364 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 3368 */               out.write("\n\n<br>\n");
/* 3369 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 3373 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3374 */               if (EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 3376 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 3380 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3381 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3382 */                 String loginName = request.getUserPrincipal().getName();
/* 3383 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 3385 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 3387 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 3390 */               String monitorType = request.getParameter("type");
/* 3391 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3392 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 3393 */               if (showAssociatedBSG)
/*      */               {
/* 3395 */                 Hashtable associatedmgs = new Hashtable();
/* 3396 */                 String resId = request.getParameter("resourceid");
/* 3397 */                 request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3398 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 3400 */                   mon = false;
/*      */                 }
/*      */                 
/* 3403 */                 if (!mon)
/*      */                 {
/* 3405 */                   out.write(10);
/* 3406 */                   out.write(10);
/*      */                   
/* 3408 */                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3409 */                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3410 */                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3412 */                   _jspx_th_c_005fif_005f11.setTest("${!empty associatedmgs}");
/* 3413 */                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3414 */                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                     for (;;) {
/* 3416 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3417 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3418 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 3420 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3421 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3422 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f11);
/*      */                       
/* 3424 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 3426 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 3428 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3429 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 3431 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3432 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 3434 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3435 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
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
/* 3493 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3437 */                             out.write("&method=showApplication\" title=\"");
/* 3438 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
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
/* 3493 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3440 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 3441 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
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
/* 3493 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3443 */                             out.write("\n    \t");
/* 3444 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3445 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 3447 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3448 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3449 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 3451 */                             _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3452 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3453 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 3455 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3456 */                                 if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
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
/*      */ 
/* 3493 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3458 */                                 out.write(39);
/* 3459 */                                 out.write(44);
/* 3460 */                                 out.write(39);
/* 3461 */                                 out.print(resId);
/* 3462 */                                 out.write(39);
/* 3463 */                                 out.write(44);
/* 3464 */                                 out.write(39);
/* 3465 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3466 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3467 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3468 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3469 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3470 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3474 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3475 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
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
/* 3493 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3478 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3479 */                             out.write("</td>\n        </tr>\n\t");
/* 3480 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3481 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3485 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3493 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3489 */                           int tmp8347_8346 = 0; int[] tmp8347_8344 = _jspx_push_body_count_c_005fforEach_005f0; int tmp8349_8348 = tmp8347_8344[tmp8347_8346];tmp8347_8344[tmp8347_8346] = (tmp8349_8348 - 1); if (tmp8349_8348 <= 0) break;
/* 3490 */                           out = _jspx_page_context.popBody(); }
/* 3491 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3493 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3494 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 3496 */                       out.write("\n      </table>\n ");
/* 3497 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3498 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3502 */                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3503 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                   }
/*      */                   
/* 3506 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3507 */                   out.write(10);
/* 3508 */                   out.write(32);
/*      */                   
/* 3510 */                   IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3511 */                   _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3512 */                   _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3514 */                   _jspx_th_c_005fif_005f12.setTest("${empty associatedmgs}");
/* 3515 */                   int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3516 */                   if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                     for (;;) {
/* 3518 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3519 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3520 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3521 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3522 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3523 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3524 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3528 */                   if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3529 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                   }
/*      */                   
/* 3532 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3533 */                   out.write(10);
/* 3534 */                   out.write(32);
/* 3535 */                   out.write(10);
/*      */ 
/*      */                 }
/* 3538 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 3542 */                   out.write(10);
/*      */                   
/* 3544 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3545 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3546 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3548 */                   _jspx_th_c_005fif_005f13.setTest("${!empty associatedmgs}");
/* 3549 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3550 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 3552 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3553 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                         return;
/* 3555 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 3557 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3558 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3559 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 3561 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 3563 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 3565 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3566 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 3568 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3569 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 3571 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3572 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
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
/* 3633 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3574 */                             out.write("&method=showApplication\" title=\"");
/* 3575 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
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
/* 3633 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3577 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 3578 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
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
/* 3633 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3580 */                             out.write("\n    \t");
/* 3581 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3582 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 3584 */                             PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3585 */                             _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3586 */                             _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 3588 */                             _jspx_th_logic_005fpresent_005f6.setRole("ADMIN");
/* 3589 */                             int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3590 */                             if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                               for (;;) {
/* 3592 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3593 */                                 if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fpresent_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3633 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3595 */                                 out.write(39);
/* 3596 */                                 out.write(44);
/* 3597 */                                 out.write(39);
/* 3598 */                                 out.print(resId);
/* 3599 */                                 out.write(39);
/* 3600 */                                 out.write(44);
/* 3601 */                                 out.write(39);
/* 3602 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3603 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3604 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3605 */                                 out.write("\"  title=\"");
/* 3606 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                 {
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
/* 3633 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3608 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3609 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3610 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3614 */                             if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3615 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
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
/* 3633 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3618 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3619 */                             out.write("\n\n\t\t \t");
/* 3620 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3621 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3625 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3633 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3629 */                           int tmp9373_9372 = 0; int[] tmp9373_9370 = _jspx_push_body_count_c_005fforEach_005f1; int tmp9375_9374 = tmp9373_9370[tmp9373_9372];tmp9373_9370[tmp9373_9372] = (tmp9375_9374 - 1); if (tmp9375_9374 <= 0) break;
/* 3630 */                           out = _jspx_page_context.popBody(); }
/* 3631 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3633 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3634 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 3636 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3637 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3638 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3642 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3643 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 3646 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3647 */                   out.write(10);
/* 3648 */                   out.write(32);
/* 3649 */                   if (_jspx_meth_c_005fif_005f14(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 3651 */                   out.write(32);
/* 3652 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 3656 */               else if (mon)
/*      */               {
/*      */ 
/* 3659 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3660 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3662 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3666 */               out.write(9);
/* 3667 */               out.write(9);
/* 3668 */               out.write(10);
/* 3669 */               out.write(10);
/* 3670 */               out.write(9);
/* 3671 */               out.write(9);
/* 3672 */               response.setContentType("text/html;charset=UTF-8");
/* 3673 */               out.write(10);
/* 3674 */               out.write(9);
/* 3675 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 3676 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3679 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3680 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3683 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3684 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 3687 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 3688 */           out.write(10);
/* 3689 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3690 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3694 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3695 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else
/* 3698 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */     } catch (Throwable t) {
/* 3700 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3701 */         out = _jspx_out;
/* 3702 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3703 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3704 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3707 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3713 */     PageContext pageContext = _jspx_page_context;
/* 3714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3716 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3717 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3718 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3720 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3722 */     _jspx_th_tiles_005fput_005f0.setValue("MySQL - Snapshot");
/* 3723 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3724 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3725 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3726 */       return true;
/*      */     }
/* 3728 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3729 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3734 */     PageContext pageContext = _jspx_page_context;
/* 3735 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3737 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3738 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3739 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3741 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3742 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3743 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3745 */         out.write(10);
/* 3746 */         out.write(9);
/* 3747 */         out.write(9);
/* 3748 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3749 */           return true;
/* 3750 */         out.write(32);
/* 3751 */         out.write(10);
/* 3752 */         out.write(9);
/* 3753 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3754 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3758 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3759 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3760 */       return true;
/*      */     }
/* 3762 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3768 */     PageContext pageContext = _jspx_page_context;
/* 3769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3771 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3772 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3773 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3775 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3777 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3778 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3779 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3780 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3781 */       return true;
/*      */     }
/* 3783 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3784 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3789 */     PageContext pageContext = _jspx_page_context;
/* 3790 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3792 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3793 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3794 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3796 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3797 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3798 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3800 */         out.write(10);
/* 3801 */         out.write(9);
/* 3802 */         out.write(9);
/* 3803 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3804 */           return true;
/* 3805 */         out.write(32);
/* 3806 */         out.write(10);
/* 3807 */         out.write(9);
/* 3808 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3809 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3813 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3814 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3815 */       return true;
/*      */     }
/* 3817 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3823 */     PageContext pageContext = _jspx_page_context;
/* 3824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3826 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3827 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3828 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3830 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3832 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3833 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3834 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3835 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3836 */       return true;
/*      */     }
/* 3838 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3839 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3844 */     PageContext pageContext = _jspx_page_context;
/* 3845 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3847 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3848 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3849 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3851 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3853 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3854 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3855 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3856 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3857 */       return true;
/*      */     }
/* 3859 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3865 */     PageContext pageContext = _jspx_page_context;
/* 3866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3868 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3869 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3870 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 3872 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3874 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3875 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3876 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3877 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3878 */       return true;
/*      */     }
/* 3880 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3881 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3886 */     PageContext pageContext = _jspx_page_context;
/* 3887 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3889 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3890 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3891 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3893 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3894 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3895 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3896 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3897 */       return true;
/*      */     }
/* 3899 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3905 */     PageContext pageContext = _jspx_page_context;
/* 3906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3908 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3909 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3910 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 3912 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3913 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3914 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3915 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3916 */       return true;
/*      */     }
/* 3918 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3919 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3924 */     PageContext pageContext = _jspx_page_context;
/* 3925 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3927 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3928 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3929 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3931 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3932 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3933 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3934 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3935 */       return true;
/*      */     }
/* 3937 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3943 */     PageContext pageContext = _jspx_page_context;
/* 3944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3946 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3947 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3948 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3950 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 3951 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3952 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3953 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3954 */       return true;
/*      */     }
/* 3956 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3962 */     PageContext pageContext = _jspx_page_context;
/* 3963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3965 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3966 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3967 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3969 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3970 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3971 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3972 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3973 */       return true;
/*      */     }
/* 3975 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3981 */     PageContext pageContext = _jspx_page_context;
/* 3982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3984 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3985 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3986 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 3988 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3989 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3990 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3992 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3993 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3994 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3998 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4012 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4015 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4016 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4017 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4018 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4019 */       return true;
/*      */     }
/* 4021 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4027 */     PageContext pageContext = _jspx_page_context;
/* 4028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4030 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4031 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4032 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4034 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4035 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4036 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 4038 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 4039 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4044 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4045 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4046 */       return true;
/*      */     }
/* 4048 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4054 */     PageContext pageContext = _jspx_page_context;
/* 4055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4057 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4058 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4059 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4061 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 4062 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4063 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4064 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4065 */       return true;
/*      */     }
/* 4067 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4073 */     PageContext pageContext = _jspx_page_context;
/* 4074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4076 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4077 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4078 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4080 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4081 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4082 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4083 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4084 */       return true;
/*      */     }
/* 4086 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4087 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4092 */     PageContext pageContext = _jspx_page_context;
/* 4093 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4095 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4096 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4097 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4099 */     _jspx_th_c_005fout_005f9.setValue("${ha.key}");
/* 4100 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4101 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4102 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4103 */       return true;
/*      */     }
/* 4105 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4111 */     PageContext pageContext = _jspx_page_context;
/* 4112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4114 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4115 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4116 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4118 */     _jspx_th_c_005fout_005f10.setValue("${ha.value}");
/* 4119 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4120 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4121 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4122 */       return true;
/*      */     }
/* 4124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4130 */     PageContext pageContext = _jspx_page_context;
/* 4131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4133 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4134 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4135 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4137 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 4138 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4139 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4140 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4141 */         out = _jspx_page_context.pushBody();
/* 4142 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4143 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4144 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4147 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4148 */           return true;
/* 4149 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4153 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4154 */         out = _jspx_page_context.popBody();
/* 4155 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4158 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4159 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4160 */       return true;
/*      */     }
/* 4162 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4168 */     PageContext pageContext = _jspx_page_context;
/* 4169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4171 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4172 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4173 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4175 */     _jspx_th_c_005fout_005f11.setValue("${ha.value}");
/* 4176 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4177 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4178 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4179 */       return true;
/*      */     }
/* 4181 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4187 */     PageContext pageContext = _jspx_page_context;
/* 4188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4190 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4191 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4192 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4194 */     _jspx_th_c_005fout_005f12.setValue("${ha.key}");
/* 4195 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4196 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4197 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4198 */       return true;
/*      */     }
/* 4200 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4206 */     PageContext pageContext = _jspx_page_context;
/* 4207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4209 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4210 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4211 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 4213 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4214 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4215 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4216 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4217 */       return true;
/*      */     }
/* 4219 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4225 */     PageContext pageContext = _jspx_page_context;
/* 4226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4228 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4229 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4230 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4232 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 4233 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4234 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4235 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4236 */       return true;
/*      */     }
/* 4238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4244 */     PageContext pageContext = _jspx_page_context;
/* 4245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4247 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4248 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4249 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4251 */     _jspx_th_c_005fout_005f14.setValue("${ha.value}");
/* 4252 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4253 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4254 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4255 */       return true;
/*      */     }
/* 4257 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4263 */     PageContext pageContext = _jspx_page_context;
/* 4264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4266 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4267 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4268 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4270 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 4271 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4272 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4273 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4274 */         out = _jspx_page_context.pushBody();
/* 4275 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4276 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4277 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4280 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4281 */           return true;
/* 4282 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4286 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4287 */         out = _jspx_page_context.popBody();
/* 4288 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4291 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4292 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4293 */       return true;
/*      */     }
/* 4295 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4296 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4301 */     PageContext pageContext = _jspx_page_context;
/* 4302 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4304 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4305 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4306 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4308 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 4309 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4310 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4311 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4312 */       return true;
/*      */     }
/* 4314 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4320 */     PageContext pageContext = _jspx_page_context;
/* 4321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4323 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4324 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4325 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 4327 */     _jspx_th_c_005fout_005f16.setValue("${ha.key}");
/* 4328 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4329 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4330 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4331 */       return true;
/*      */     }
/* 4333 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4339 */     PageContext pageContext = _jspx_page_context;
/* 4340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4342 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4343 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4344 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 4346 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 4347 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4348 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4349 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4350 */       return true;
/*      */     }
/* 4352 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4358 */     PageContext pageContext = _jspx_page_context;
/* 4359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4361 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4362 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4363 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4365 */     _jspx_th_c_005fif_005f14.setTest("${empty associatedmgs}");
/* 4366 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4367 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 4369 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4370 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 4371 */           return true;
/* 4372 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 4373 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f14, _jspx_page_context))
/* 4374 */           return true;
/* 4375 */         out.write("</td>\n\t ");
/* 4376 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4377 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4381 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4382 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4383 */       return true;
/*      */     }
/* 4385 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4391 */     PageContext pageContext = _jspx_page_context;
/* 4392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4394 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4395 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4396 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4398 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4399 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4400 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4401 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4402 */       return true;
/*      */     }
/* 4404 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4410 */     PageContext pageContext = _jspx_page_context;
/* 4411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4413 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4414 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4415 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4417 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 4418 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4419 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4420 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4421 */       return true;
/*      */     }
/* 4423 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4429 */     PageContext pageContext = _jspx_page_context;
/* 4430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4432 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4433 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4434 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4436 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4437 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4438 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4439 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4440 */       return true;
/*      */     }
/* 4442 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4443 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MySqlDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */