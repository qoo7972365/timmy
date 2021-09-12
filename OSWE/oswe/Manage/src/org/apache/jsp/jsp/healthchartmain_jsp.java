/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.ChildMOHandler;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
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
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.el.ExpressionFactory;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspApplicationContext;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.HttpJspBase;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.tomcat.InstanceManager;
/*      */ 
/*      */ public final class healthchartmain_jsp extends HttpJspBase implements JspSourceDependent
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
/*  361 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
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
/*  683 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  855 */       return new Date(ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1033 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1034 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/* 1200 */       childresname = ExtProdUtil.decodeString(childresname);
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
/* 1304 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1645 */           String dbType = DBQueryUtil.getDBType();
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
/* 2002 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2057 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/* 2111 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
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
/* 2199 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2200 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   
/*      */   private ExpressionFactory _el_expressionfactory;
/*      */   private InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2215 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2226 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2229 */     JspWriter out = null;
/* 2230 */     Object page = this;
/* 2231 */     JspWriter _jspx_out = null;
/* 2232 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2236 */       response.setContentType("text/html;charset=UTF-8");
/* 2237 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2239 */       _jspx_page_context = pageContext;
/* 2240 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2241 */       ServletConfig config = pageContext.getServletConfig();
/* 2242 */       session = pageContext.getSession();
/* 2243 */       out = pageContext.getOut();
/* 2244 */       _jspx_out = out;
/*      */       
/* 2246 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2247 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2249 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2250 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2252 */       out.write(10);
/* 2253 */       out.write(10);
/* 2254 */       out.write(10);
/* 2255 */       out.write("\n\n<Script>\nfunction Onimg(id,color)\n{\n  if(color == 1)\n  {\n  document.getElementById(id).src=\"/images/red-bar-ro.gif\";\n  }\n  if(color == 4)\n  {\n  document.getElementById(id).src=\"/images/orange-bar-ro.gif\";\n  }\n  if(color == 5)\n  {\n  document.getElementById(id).src=\"/images/green-bar-ro.gif\";\n  }\n}\nfunction Offimg(id,color)\n{\n  if(color == 1)\n  {\n  document.getElementById(id).src=\"/images/red-bar.gif\";\n  }\n  if(color == 4)\n  {\n  document.getElementById(id).src=\"/images/orange-bar.gif\";\n  }\n  if(color == 5)\n  {\n  document.getElementById(id).src=\"/images/green-bar.gif\";\n  }\n}\n\n</script>\n<input type=\"hidden\" id=\"oldtab\" name=\"oldtab\" value='");
/* 2256 */       out.print(request.getParameter("oldtab"));
/* 2257 */       out.write("'/>\n");
/*      */       
/* 2259 */       boolean isConfMonitor = (request.getAttribute("isConfMonitor") != null) && (((String)request.getAttribute("isConfMonitor")).equals("true"));
/* 2260 */       boolean isServerAdded = (request.getAttribute("serverName") != null) && (!((String)request.getAttribute("serverName")).trim().equals(""));
/* 2261 */       String tdheight = (isConfMonitor) && (!isServerAdded) ? "50px" : "30px";
/* 2262 */       Hashtable hash = (Hashtable)request.getAttribute("health_report");
/* 2263 */       HashMap extDeviceMap = null;
/* 2264 */       if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */       {
/* 2266 */         extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */       }
/*      */       
/*      */ 
/* 2270 */       out.write("\n<table width=\"100%\" cellspacing=\"0\" border=\"0\" cellpadding=\"1\" class=\"lrbborder conf-mon-table-no-border\">\n\n");
/*      */       
/*      */ 
/* 2273 */       int period = Integer.parseInt(request.getParameter("period"));
/*      */       
/* 2275 */       long endtime = ((Date)request.getAttribute("endtime")).getTime();
/* 2276 */       long starttime = 0L;
/* 2277 */       int p = 0;
/* 2278 */       int diff = -1;
/*      */       
/* 2280 */       if (period == 1)
/*      */       {
/* 2282 */         p = 24;
/* 2283 */         diff = 3600000;
/* 2284 */         starttime = endtime - 86400000L;
/*      */       }
/* 2286 */       else if (period == 4)
/*      */       {
/* 2288 */         p = 6;
/* 2289 */         diff = 3600000;
/* 2290 */         starttime = endtime - 21600000L;
/*      */       }
/* 2292 */       else if (period == 2)
/*      */       {
/* 2294 */         p = 30;
/* 2295 */         diff = 86400000;
/* 2296 */         endtime -= 3600000L;
/* 2297 */         starttime = endtime - 2592000000L;
/*      */       }
/*      */       
/*      */ 
/* 2301 */       out.write("\n<tr >\n");
/* 2302 */       if (!isConfMonitor) {
/* 2303 */         out.write("\n\n<td width=\"120px\" align=\"center\" valign=\"middle\" class=\"columnheading\">\n\n<table width=\"120px\" cellpadding=\"0\" cellspacing=\"0\"  align=\"left\">\n        <tr>\n        <td  width=\"30\" style=\"font-size:11px; font-family:Arial, Helvetica, sans-serif;\" >");
/* 2304 */         out.print(FormatUtil.getString("Name"));
/* 2305 */         out.write("</td>\n    <td width=\"12\"><img src=\"/images/img_dboard_heading1.gif\" /></td>\n        <td style=\"font-size:11px; font-family:Arial, Helvetica, sans-serif;\"  width=\"60\" align=\"right\">");
/* 2306 */         out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.performance.date"));
/* 2307 */         out.write("&nbsp;</td>\n        <td width=\"5\" align=\"right\"><img src=\"/images/img_dboard_heading2.gif\" /></td>\n        </tr>\n</table>\n\n\n</td>\n");
/*      */       } else {
/* 2309 */         out.write("\n<td width=\"5%\" class=\"columnheading\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td>\n");
/*      */       }
/* 2311 */       out.write(10);
/*      */       
/* 2313 */       long start = starttime;
/* 2314 */       for (int i = 0; i < p; i++)
/*      */       {
/*      */ 
/* 2317 */         out.write("\n      <td align=\"left\"  class=\"columnheading\" >\n          ");
/*      */         
/* 2319 */         start += diff;
/* 2320 */         if ((p == 24) || (p == 6))
/*      */         {
/* 2322 */           Time current = new Time(start);
/* 2323 */           current.setTime(start);
/* 2324 */           out.println(current.toString().substring(0, 2));
/*      */         }
/*      */         else
/*      */         {
/* 2328 */           Date current = new Date(start);
/* 2329 */           current.setTime(start);
/* 2330 */           out.println(current.toString().substring(8, 10));
/*      */         }
/*      */         
/* 2333 */         out.write("\n      </td>\n");
/*      */       }
/*      */       
/*      */ 
/* 2337 */       out.write("\n</tr>\n ");
/*      */       
/* 2339 */       int n = 0;
/*      */       
/* 2341 */       ArrayList<String> hashTableKeys = new ArrayList(hash.keySet());
/* 2342 */       Map<String, String> sortedList = new TreeMap();
/* 2343 */       boolean isOpManagerDemoDone = false;
/* 2344 */       boolean isOpStorDemoDone = false;
/* 2345 */       for (int keysize = 0; keysize < hashTableKeys.size(); keysize++)
/*      */       {
/* 2347 */         String combinedValue = (String)hashTableKeys.get(keysize);
/* 2348 */         String monitorType = combinedValue.substring(0, combinedValue.indexOf("$"));
/* 2349 */         String monitorResid = combinedValue.substring(monitorType.length() + 1, combinedValue.indexOf("#"));
/* 2350 */         String monitorDisplayName = combinedValue.substring(combinedValue.indexOf("#") + 1);
/* 2351 */         sortedList.put(monitorDisplayName + "$" + monitorResid + "#" + monitorType, combinedValue);
/*      */       }
/*      */       
/* 2354 */       for (Iterator<String> e = sortedList.keySet().iterator(); e.hasNext();)
/*      */       {
/* 2356 */         int rowCount = 0;
/* 2357 */         String bgClass = "whitegrayrightalign";
/* 2358 */         if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */         {
/* 2360 */           if (n % 2 == 0)
/*      */           {
/* 2362 */             bgClass = "whitegrayborder";
/*      */           }
/*      */           else
/*      */           {
/* 2366 */             bgClass = "yellowgrayborder";
/*      */           }
/*      */         }
/* 2369 */         if (isConfMonitor) {
/* 2370 */           bgClass = " ";
/*      */         }
/* 2372 */         String keyValue = (String)e.next();
/* 2373 */         String temp = (String)sortedList.get(keyValue);
/* 2374 */         Hashtable mhash = (Hashtable)hash.get(temp);
/* 2375 */         String monType = temp.substring(0, temp.indexOf("$"));
/* 2376 */         if (((!isOpStorDemoDone) || (monType.toUpperCase().indexOf("OPSTOR") == -1)) && ((!isOpManagerDemoDone) || (monType.toUpperCase().indexOf("OPMANAGER") == -1)))
/*      */         {
/*      */ 
/*      */ 
/* 2380 */           out.write("\n\t<tr onmouseout=\"this.className='availTableHeader'\" onmouseover=\"this.className='availTableHeaderHover'\" class=\"availTableHeader\">\n");
/*      */           
/*      */ 
/*      */ 
/* 2384 */           int tem = temp.indexOf("#");
/* 2385 */           String resid = temp.substring(monType.length() + 1, tem);
/* 2386 */           int residint = Integer.parseInt(resid);
/* 2387 */           String dispname = temp.substring(tem + 1);
/* 2388 */           dispname = EnterpriseUtil.decodeString(dispname);
/* 2389 */           String toolTipTitle = dispname;
/* 2390 */           HashMap resNameMap = (HashMap)request.getAttribute("resourceMap");
/* 2391 */           if ((EnterpriseUtil.isAdminServer()) && (residint > EnterpriseUtil.RANGE))
/*      */           {
/* 2393 */             if (resNameMap != null)
/*      */             {
/* 2395 */               String[] arr = (String[])resNameMap.get(resid);
/* 2396 */               if (arr != null)
/*      */               {
/* 2398 */                 dispname = arr[1];
/* 2399 */                 toolTipTitle = arr[0];
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 2404 */               toolTipTitle = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/* 2405 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/* 2406 */               dispname = FormatUtil.getTrimmedText(dispname, 27);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 2411 */           else if (resNameMap != null)
/*      */           {
/* 2413 */             String[] arr = (String[])resNameMap.get(resid);
/* 2414 */             if (arr != null)
/*      */             {
/* 2416 */               dispname = arr[1];
/* 2417 */               toolTipTitle = arr[0];
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2422 */             dispname = FormatUtil.getTrimmedText(dispname, 27);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2427 */           out.write(10);
/* 2428 */           if (!isConfMonitor) {
/* 2429 */             out.write("\n\t\t<td width=\"19%\"  style=\"height:27px;\"   title=\"");
/* 2430 */             out.print(toolTipTitle);
/* 2431 */             out.write("\" class=\"");
/* 2432 */             out.print(bgClass);
/* 2433 */             out.write("\" nowrap>\n\t\t\t");
/*      */             
/* 2435 */             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2436 */             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2437 */             _jspx_th_c_005fif_005f0.setParent(null);
/*      */             
/* 2439 */             _jspx_th_c_005fif_005f0.setTest("${param.group=='HAI'}");
/* 2440 */             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2441 */             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */               for (;;) {
/* 2443 */                 out.write("\n\t\t\t<span class=\"availablity-arrow\"> &raquo;</span><a href=\"/showapplication.do?haid=");
/* 2444 */                 out.print(resid);
/* 2445 */                 out.write("&method=showApplication\" >\n\t\t\t");
/* 2446 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2447 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2451 */             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2452 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */             }
/*      */             
/* 2455 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2456 */             out.write("\n\t\t\t");
/*      */             
/* 2458 */             if (monType.equals("HAI"))
/*      */             {
/*      */ 
/* 2461 */               out.write("\n\t\t\t\t<a href=\"/showapplication.do?haid=");
/* 2462 */               out.print(resid);
/* 2463 */               out.write("&method=showApplication\" class=\"staticlinks\">\n\t\t   ");
/*      */             }
/* 2465 */             else if (monType.contains("Site24x7"))
/*      */             {
/*      */ 
/* 2468 */               out.write("\n\t\t\t<a href=\"javascript:MM_openBrWindow('/extDeviceAction.do?method=site24x7Reports&resourceid=");
/* 2469 */               out.print(resid);
/* 2470 */               out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"staticlinks\">\n\t\t\t");
/*      */             }
/* 2472 */             else if (DBUtil.isUnManaged(resid))
/*      */             {
/* 2474 */               out.write("\n\n\t\t\t<a href=\"/showresource.do?resourceid=");
/* 2475 */               out.print(resid);
/* 2476 */               out.write("&method=showResourceForResourceID\" class=\"disabledtext-u\">\n\t\t\t");
/* 2477 */             } else if (ChildMOHandler.isChildMonitorTypeSupportedForMG(monType)) {
/* 2478 */               out.write("\n  \t\t\t<a href=\"javascript:MM_openBrWindow('/showapplication.do?method=showChildApplicationDetail&resId=");
/* 2479 */               out.print(resid);
/* 2480 */               out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"staticlinks\">\n\t\t\t");
/*      */             } else {
/* 2482 */               out.write("\n\t\t\t<a href=\"/showresource.do?resourceid=");
/* 2483 */               out.print(resid);
/* 2484 */               out.write("&method=showResourceForResourceID\" class=\"staticlinks\">\n\t\t\t");
/*      */             }
/* 2486 */             out.write("\n\n\t\t\t");
/* 2487 */             if ((extDeviceMap != null) && (extDeviceMap.get(resid) != null))
/*      */             {
/* 2489 */               if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */               {
/* 2491 */                 String link = (String)extDeviceMap.get(resid);
/* 2492 */                 if (EnterpriseUtil.isAdminServer)
/*      */                 {
/* 2494 */                   link = URLEncoder.encode(link, "UTF-8");
/* 2495 */                   link = "/showTile.do?TileName=IT360.IFrameInvokeUrl&oldtab=1&produrl=" + link;
/*      */                 }
/*      */                 
/* 2498 */                 out.write("\n\t  \t\t\t\t<a href=\"");
/* 2499 */                 out.print(link);
/* 2500 */                 out.write("\" class=\"staticlinks\">\n\t  \t\t\t");
/*      */ 
/*      */ 
/*      */               }
/* 2504 */               else if ((request.isUserInRole("DEMO")) && (request.getAttribute("CategoryType") != null) && (((String)request.getAttribute("CategoryType")).equals("NWD")) && (monType.toUpperCase().indexOf("OPMANAGER") != -1)) {
/* 2505 */                 isOpManagerDemoDone = true;
/*      */                 
/* 2507 */                 out.write("\n\t  \t\t\t\t\t\t<a href=\"http://demo.appmanager.com/networkdevices/networkdevices.htm\" class=\"staticlinks\">\n\t  \t\t\t\t");
/* 2508 */               } else if ((request.isUserInRole("DEMO")) && (request.getAttribute("CategoryType") != null) && (((String)request.getAttribute("CategoryType")).equals("SAN")) && (monType.toUpperCase().indexOf("OPSTOR") != -1)) {
/* 2509 */                 isOpStorDemoDone = true;
/*      */                 
/* 2511 */                 out.write("\n\t  \t\t\t\t\t<a href=\"http://demo.appmanager.com/networkdevices/opstor.htm\" class=\"staticlinks\">\n\t  \t\t\t\t");
/*      */               } else {
/* 2513 */                 out.write("\n\t  \t\t\t\n\t  \t\t\t\t<a href=\"javascript:MM_openBrWindow('");
/* 2514 */                 out.print(extDeviceMap.get(resid));
/* 2515 */                 out.write("','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"staticlinks\">\n\t  \t\t\t");
/*      */               }
/* 2517 */               out.write("\n\t\t\t");
/*      */             }
/* 2519 */             out.write("\n\n\t\t\t");
/* 2520 */             out.print(dispname);
/* 2521 */             out.write("\n\t\t\t</a>\n\t     </td>\n\t    ");
/*      */           } else {
/* 2523 */             out.write("\n\t     <td width=\"5%\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td>\n\t    ");
/*      */           }
/* 2525 */           out.write("\n \t\t");
/*      */           
/* 2527 */           long stime = starttime;
/* 2528 */           for (int i = 0; i < p; i++)
/*      */           {
/* 2530 */             stime += diff;
/* 2531 */             long stime1 = stime;
/* 2532 */             Date d = new Date(stime);
/* 2533 */             Enumeration dayEnum; if (p == 30) {
/* 2534 */               for (dayEnum = mhash.keys(); dayEnum.hasMoreElements();) {
/* 2535 */                 long inValues = Long.parseLong("" + dayEnum.nextElement());
/* 2536 */                 if ((inValues <= stime) && (inValues > stime - diff)) {
/* 2537 */                   stime1 = inValues;
/* 2538 */                   break;
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/* 2543 */             out.write("\n     \t<td  width=\"18\" height=\"");
/* 2544 */             out.print(tdheight);
/* 2545 */             out.write("\" align=\"left\" valign=\"middle\" class=\"");
/* 2546 */             out.print(bgClass);
/* 2547 */             out.write("\" title=\"");
/* 2548 */             out.print(d);
/* 2549 */             out.write("\">\n\t\t\t");
/*      */             
/* 2551 */             if (mhash.get(Long.valueOf(stime1)) != null)
/*      */             {
/* 2553 */               Hashtable thash = (Hashtable)mhash.get(Long.valueOf(stime1));
/* 2554 */               if (!((String)thash.get("critic")).equals("0"))
/*      */               {
/*      */ 
/* 2557 */                 out.write("\n\t        \t<span id=\"");
/* 2558 */                 out.print(stime);
/* 2559 */                 out.write(36);
/* 2560 */                 out.print(temp.substring(0, tem));
/* 2561 */                 out.write("\" onclick=\"eventPop(this.id, ");
/* 2562 */                 out.print(p);
/* 2563 */                 out.write(")\" style=\"cursor:pointer\">\n\t            \t<img border=\"0\" width=\"15px\" height=\"8px\" src=\"/images/red-bar.gif\" hspace=\"1\" id=\"");
/* 2564 */                 out.print(n);
/* 2565 */                 out.write(36);
/* 2566 */                 out.print(i);
/* 2567 */                 out.write("\" onmouseover=\"Onimg(this.id,1)\" onmouseout=\"Offimg(this.id,1)\">\n\t            </span>\n\t \t\t\t");
/*      */ 
/*      */               }
/* 2570 */               else if (!((String)thash.get("warn")).equals("0"))
/*      */               {
/* 2572 */                 out.write("\n\t\t\t\t\t<span id=\"");
/* 2573 */                 out.print(stime);
/* 2574 */                 out.write(36);
/* 2575 */                 out.print(temp.substring(0, tem));
/* 2576 */                 out.write("\" onclick=\"eventPop(this.id, ");
/* 2577 */                 out.print(p);
/* 2578 */                 out.write(")\" style=\"cursor:pointer\">\n\t                \t<img border=\"0\" width=\"15px\" height=\"8px\" src=\"/images/orange-bar.gif\" hspace=\"1\" id=\"");
/* 2579 */                 out.print(n);
/* 2580 */                 out.write(36);
/* 2581 */                 out.print(i);
/* 2582 */                 out.write("\" onmouseover=\"Onimg(this.id,4)\" onmouseout=\"Offimg(this.id,4)\" >\n\t                </span>\n\t \t\t\t");
/*      */ 
/*      */               }
/* 2585 */               else if (!((String)thash.get("clear")).equals("0"))
/*      */               {
/*      */ 
/* 2588 */                 out.write("\n\t\t            <span id=\"");
/* 2589 */                 out.print(stime);
/* 2590 */                 out.write(36);
/* 2591 */                 out.print(temp.substring(0, tem));
/* 2592 */                 out.write("\" onclick=\"eventPop(this.id, ");
/* 2593 */                 out.print(p);
/* 2594 */                 out.write(")\" style=\"cursor:pointer\">\n\t\t            \t<img border=\"0\" width=\"15px\" height=\"8px\" src=\"/images/green-bar.gif\" hspace=\"1\" id=\"");
/* 2595 */                 out.print(n);
/* 2596 */                 out.write(36);
/* 2597 */                 out.print(i);
/* 2598 */                 out.write("\" onmouseover=\"Onimg(this.id,5)\" onmouseout=\"Offimg(this.id,5)\" >\n\t\t            </span>\n\t\t\t\t");
/*      */               } else {
/* 2600 */                 out.write("\n\t            <img border=\"0\" width=\"15px\" height=\"8px\" src=\"/images/gray-bar.gif\" hspace=\"1\" >\n\t\t\t\t");
/*      */               }
/*      */               
/*      */             }
/*      */             else
/*      */             {
/* 2606 */               out.write("\n\t   \t\t\t<img border=\"0\" width=\"15px\" height=\"8px\" src=\"/images/gray-bar.gif\" hspace=\"1\" >\n\t   \t\t");
/*      */             }
/* 2608 */             out.write("\n\t\t</td>\n");
/* 2609 */             rowCount++; }
/* 2610 */           out.write("\n\t</tr>\n\n");
/*      */           
/* 2612 */           n++;
/*      */         } }
/* 2614 */       if (n == 0)
/*      */       {
/* 2616 */         out.write("\n\t\t<td  height=\"30px\" align=\"center\" valign=\"middle\" class=\"bodytextbold\" nowrap=\"nowrap\">\n\t\t&nbsp;\n\t\t\t");
/* 2617 */         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text"));
/* 2618 */         out.write("\n\t\t</td>\n\t");
/*      */       }
/*      */       
/*      */ 
/* 2622 */       out.write("\n\n</table>\n");
/*      */     } catch (Throwable t) {
/* 2624 */       if (!(t instanceof SkipPageException)) {
/* 2625 */         out = _jspx_out;
/* 2626 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2627 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2628 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2631 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\healthchartmain_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */