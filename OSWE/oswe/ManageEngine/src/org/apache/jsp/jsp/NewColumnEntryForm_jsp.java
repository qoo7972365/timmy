/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class NewColumnEntryForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  672 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  818 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  859 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  903 */     Properties temp = FaultUtil.getStatus(entitylist, false);
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
/*  917 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*  977 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1022 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/* 1293 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1634 */           String dbType = DBQueryUtil.getDBType();
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
/* 1991 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2199 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2203 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2206 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2218 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2221 */     JspWriter out = null;
/* 2222 */     Object page = this;
/* 2223 */     JspWriter _jspx_out = null;
/* 2224 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2228 */       response.setContentType("text/html;charset=UTF-8");
/* 2229 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2231 */       _jspx_page_context = pageContext;
/* 2232 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2233 */       ServletConfig config = pageContext.getServletConfig();
/* 2234 */       session = pageContext.getSession();
/* 2235 */       out = pageContext.getOut();
/* 2236 */       _jspx_out = out;
/*      */       
/* 2238 */       out.write(10);
/* 2239 */       out.write(10);
/* 2240 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2242 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2243 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2245 */       out.write(10);
/* 2246 */       out.write(10);
/* 2247 */       out.write(10);
/* 2248 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<script type=\"text/javascript\" src=\"/template/validation.js\"></script>\n\n\n<link href=\"/images/");
/* 2249 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2251 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<script>\n    var fieldcount=1;\n    var xmlhttp;\n    var selectResourceName;\n    var selectResourceIdr;\n    var attributeIDArray = new Array();\n    var attributeNameArray=new Array();\nfunction criteriaAdd(spanobj)\n{\n    // function to add new row of field columns and field type and value\n\n    fieldcount=fieldcount+1;\n    //alert(fieldcount);\n    var baseTable = document.getElementById('tbodycri');\n    var allRows = baseTable.getElementsByTagName('tr');\n    var trid = spanobj.parentNode.parentNode.getAttribute('id');\n    var commoncolArray = new Array();\n    var commoncolIdArray = new Array();\n    var commoncolDataArray = new Array();\n    var changecolNamelist = 'false';\n\n    // to get last row id\n    var newtrID = parseInt(trid.substr((trid.indexOf('_')+1),(trid.length)-1));\n    newtrID = newtrID + 1;\n\n    var root = document.getElementById('criteriaAdd1');\n    var row1 = root.getElementsByTagName('tr');\n");
/* 2252 */       out.write("\n\n    var newRow = row1[0].cloneNode(true);\n    newRow.id = \"row_\" + newtrID;////No I18N\n\n    var inputList = newRow.getElementsByTagName('input');\n\n    for(var i=0;i<inputList.length;i++)\n    {\n        inputList[i].setAttribute('name',inputList[i].getAttribute('name')+'_'+newtrID); //No I18N\n    \tinputList[i].setAttribute('id',inputList[i].getAttribute('id')+'_'+newtrID); //No I18N\n    }\n    var selectList = newRow.getElementsByTagName('select');\n    var idval;\n    for(var i=0;i<selectList.length;i++)\n    {\n\n\t    selectList[i].setAttribute('name',selectList[i].getAttribute('name')+'_'+newtrID); //No I18N\n\t    var id1 = selectList[i].getAttribute('id');\n\t    selectList[i].setAttribute('id',id1+'_'+newtrID); //No I18N\n    }\n    var imgList = newRow.getElementsByTagName('img');\n    for(var i=0;i<imgList.length;i++)\n    {\n    imgList[i].setAttribute('name',\"row_\" + newtrID); //No I18N\n    }\n    var spanEle = newRow.getElementsByTagName('span');\n\n     for(var i=0;i<spanEle.length;i++)\n    \t{\n    \t\tspanEle[i].setAttribute('name',spanEle[i].getAttribute('name')+'_'+newtrID); //No I18N\n");
/* 2253 */       out.write("    \t\tspanEle[i].setAttribute('id',spanEle[i].getAttribute('id')+'_'+newtrID); //No I18N\n\n     \t}\n\n    document.getElementById(spanobj.getAttribute('id')).style.display = \"none\";\n    document.getElementById('tbodycri').appendChild(newRow);\n\n\n        var hiddenID=\"attributeID_\"+newtrID; //No I18N\n        var hiddenElement = document.createElement(\"input\");\n        hiddenElement.setAttribute(\"type\", \"hidden\"); //No I18N\n        hiddenElement.setAttribute(\"name\", hiddenID); //No I18N\n        hiddenElement.setAttribute(\"id\", hiddenID); //No I18N\n        //hiddenElement.setAttribute(\"value\",\"hello\");\n        newRow.appendChild(hiddenElement);\n\n    \n    if(changecolNamelist == 'true')\n    {\n        document.getElementById(idval).length = 0;\n    \tselectoptn =document.createElement(\"OPTION\"); //No I18N\n    \tselectoptn.value = \"-- Select Column --\"; //No I18N\n    \tselectoptn.text = \"-- Select Column --\"; //No I18N\n    \tdocument.getElementById(idval).options.add(selectoptn);\n        \n    \tfor(var i=0;i<commoncolArray.length;i++)\n");
/* 2254 */       out.write("    \t{\n        \n        optnCriteria = document.createElement(\"OPTION\"); //No I18N\n        optnCriteria.text = commoncolArray[i]; //No I18N\n        optnCriteria.value = commoncolIdArray[i]; //No I18N\n        optnCriteria.name = commoncolDataArray[i]; //No I18N\n        optnCriteria.id = commoncolDataArray[i]; //No I18N\n        document.getElementById(idval).options.add(optnCriteria);\n    \t}\n        document.getElementById(idval).options.add(selectoptn);\n    }\n\n\n\n// for checking purpose\nsetValues()\n\n}\n\n// EDIT CODING STARTS HERE\n\nfunction editFormrestore()\n{       //count, resourcename, attributename, attributeid\n\n\n    var editCount = Number(document.getElementById(\"editCount\").value);\t//NO I18N\n    var editResourceName= document.getElementById(\"editResourceName\").value; //NO I18N\n    //alert('firsteditResourceName'+editResourceName);\n    var editAttributeName=document.getElementById(\"editAttributeName\").value; //NO I18N\n    var editAttributeID=document.getElementById(\"editAttributeID\").value; //NO I18N\n    var editResourceAttributeList=document.getElementById(\"resourceAttributeList\").value; //NO I18N\n");
/* 2255 */       out.write("\n\n    var editResourceNameArray=editResourceName.split(\",\");  //NO I18N\n    var editAttributeNameArray=editAttributeName.split(\",\"); //NO I18N\n    var editAttributeIDArray=editAttributeID.split(\",\");     //NO I18N\n    var editResourceAttributeListArray=editResourceAttributeList.split(\"@\"); //NO I18N\n\n\n        fieldcount=editCount;\n\n        var x=document.getElementById(\"resourceName_1\");   //NO I18N\n        x.options[x.selectedIndex].text=editResourceNameArray[0];   //NO I18N\n        x.options[x.selectedIndex].value=editResourceNameArray[0];       //NO I18N \n        document.getElementById(\"attributeID_1\").value=editAttributeIDArray[0];  //NO I18N\n\n        //var selAttributeName=\"criteriaType_\"+newtrID;\n        edit_fillAttributeOption(\"criteriaType_1\",editResourceAttributeListArray[0]);  //NO I18N\n\n        //var selAttributeName=\"criteriaType_\"+newtrID;\n        var x=document.getElementById(\"criteriaType_1\");  //NO I18N\n        x.options[x.selectedIndex].text=editAttributeNameArray[0];  //NO I18N\n        x.options[x.selectedIndex].value=editAttributeNameArray[0];  //NO I18N\n");
/* 2256 */       out.write("        \n        //var attributeListArray\n      if (editCount>1)\n      {\n          for(var i=1;i<editCount;i++)\n          {\n            rowIterate(i+1,editResourceNameArray[i],editAttributeNameArray[i],editAttributeIDArray[i],editResourceAttributeListArray[i]); //NO I18N\n          }\n      }\n\n\n        setValues(); //No I18N\n\n}\n\nfunction rowIterate(newtrID, resourceName, attributeName, attributeID, attributeList)//, attributeName,attributeID)\n{\n    var root = document.getElementById('criteriaAdd1'); //NO I18N\n    var row1 = root.getElementsByTagName('tr'); //NO I18N\n    var oldtrID=newtrID-1; //NO I18N\n    var newRow = row1[0].cloneNode(true); //NO I18N\n    //row1[0].clone\n    newRow.id = \"row_\" + newtrID; //NO I18N\n\n    var inputList = newRow.getElementsByTagName('input'); //NO I18N\n\n    for(var i=0;i<inputList.length;i++)\n    {\n        inputList[i].setAttribute('name',inputList[i].getAttribute('name')+'_'+newtrID); //NO I18N\n    \tinputList[i].setAttribute('id',inputList[i].getAttribute('id')+'_'+newtrID); //NO I18N\n");
/* 2257 */       out.write("    } \n    var selectList = newRow.getElementsByTagName('select'); //NO I18N\n    var idval; //NO I18N\n    for(var i=0;i<selectList.length;i++)\n    {\n\n\t    selectList[i].setAttribute('name',selectList[i].getAttribute('name')+'_'+newtrID); //NO I18N\n\t    var id1 = selectList[i].getAttribute('id'); //NO I18N\n\t    selectList[i].setAttribute('id',id1+'_'+newtrID);//NO I18N\n\n    }\n    \n    var imgList = newRow.getElementsByTagName('img'); //NO I18N\n    for(var i=0;i<imgList.length;i++)\n    {\n        imgList[i].setAttribute('name',\"row_\" + newtrID); //NO I18N\n    }\n    var spanEle = newRow.getElementsByTagName('span'); //NO I18N\n\n     for(var i=0;i<spanEle.length;i++)\n    \t{\n    \t\tspanEle[i].setAttribute('name',spanEle[i].getAttribute('name')+'_'+newtrID); //NO I18N\n    \t\tspanEle[i].setAttribute('id',spanEle[i].getAttribute('id')+'_'+newtrID); //NO I18N\n            //alert(i);\n            if (i==0)\n            {\n                //spanEle[i].setAttribute('text',resourceName);\n            }\n            else //NO I18N\n            {\n");
/* 2258 */       out.write("                //spanEle[i].setAttribute('text',attributeName);\n                //alert(spanEle[i].getAttribute('text'));\n            }\n     \t}\n\n    document.getElementById(\"span_\"+oldtrID).style.display = \"none\"; //NO I18N\n    document.getElementById('tbodycri').appendChild(newRow); //NO I18N\n\n\n        var hiddenID=\"attributeID_\"+newtrID;//NO I18N\n        var hiddenElement = document.createElement(\"input\"); //NO I18N\n        hiddenElement.setAttribute(\"type\", \"hidden\"); //NO I18N\n        hiddenElement.setAttribute(\"name\", hiddenID); //NO I18N\n        hiddenElement.setAttribute(\"id\", hiddenID); //NO I18N\n        //hiddenElement.setAttribute(\"value\",\"hello\");\n        newRow.appendChild(hiddenElement);\n\n\n        var selResourceName=\"resourceName_\"+newtrID; //NO I18N\n        //alert('ResourceName'+resourceName);\n        //document.getElementById(selResourceName).value=resourceName;\n        var x=document.getElementById(selResourceName);\n        x.options[x.selectedIndex].text=resourceName; //No I18N\n        x.options[x.selectedIndex].value=resourceName;\n");
/* 2259 */       out.write("\n        var selAttributeName=\"criteriaType_\"+newtrID; //NO I18N\n        edit_fillAttributeOption(selAttributeName,attributeList);\n        var x=document.getElementById(selAttributeName);\n        x.options[x.selectedIndex].text=attributeName;\n        x.options[x.selectedIndex].value=attributeName;\n\n\n        document.getElementById(hiddenID).value=attributeID;\n\n}\n\nfunction fillAttributeColumnAlone(id)\n{\n\n    var selAttributeName=document.getElementById(id).name;\n    var newtrID = selAttributeName.substr(13);\n    var selResourceName=\"resourceName_\"+newtrID;  //NO I18N\n    fillAttributeColumn(selResourceName);\n}\n\n//EDIT CODING ENDS HERE\n\n\nfunction deleteCriteria(rowobj)\n{\n\n//// function to delete the  row of field columns and field type and value\nvar baseTable = document.getElementById('criteriaAdd');\nvar allRows = baseTable.getElementsByTagName('tr');\nvar parentnode = rowobj.getAttribute('id');\n\n    if (allRows.length > 2)\n    {\n    // to show 'ADD' button in previous row\n        var lastnode = allRows[(allRows.length)-1].getAttribute('id');\n");
/* 2260 */       out.write("\n            if (parentnode == lastnode)\n            {\n                var nod = allRows[(allRows.length)-2];\n                var spannode = nod.getElementsByTagName(\"span\");\n                spannode[0].style.display = \"\"; //No I18N\n            }\n\n        // delete the row\n        var rowid = rowobj.getAttribute('id');\n        var element = document.getElementById(rowid);\n        element.parentNode.removeChild(element);\n\n        //to  rename the criteria field\n        renamefield(rowid)\n\n        // for checking purpose\n        setValues()\n    }\n\n}\n\nfunction renamefield(rowid)\n{\n\n    //function to rename the row od fields once we the delete\n    var idtemp;\n    var id;\n    var i;\n    var j;\n    var tempid;\n\n    var fieldrow;\n    var columnid;\n    var criteriaType;\n    var criteriavalue;\n    var span;\n\n    idtemp=rowid.toString()\n    //to retrieve the number from row_1\n    id=idtemp.substr(4);\n\n    // rename iterations starts here\n    var temp=(+id);\n\n    //alert(temp);\n    for(i=temp;i<fieldcount;i++)\n        {\n");
/* 2261 */       out.write("\n                j=i+1;\n\n                var row = document.getElementById(\"row_\"+j);\n                //row.setAttribute(\"name\",\"row_\"+i );\n                row.setAttribute(\"id\", \"row_\"+i); //No I18N\n\n                var resourcename = document.getElementById(\"resourceName_\"+j);\n                resourcename.setAttribute(\"name\",\"resourceName_\"+i); //No I18N\n                resourcename.setAttribute(\"id\", \"resourceName_\"+i); //No I18N\n\n                var criteriaType = document.getElementById(\"criteriaType_\"+j);\n                criteriaType.setAttribute(\"name\",\"criteriaType_\"+i); //No I18N\n                criteriaType.setAttribute(\"id\", \"criteriaType_\"+i);\n\n                var span = document.getElementById(\"span_\"+j);\n                span.setAttribute(\"name\",\"span_\"+i); //No I18N\n                span.setAttribute(\"id\", \"span_\"+i); //No I18N\n\n                var hiddenElement=document.getElementById(\"attributeID_\"+j)\n                hiddenElement.setAttribute(\"name\",\"attributeID_\"+i); //NO I18N\n                hiddenElement.setAttribute(\"id\", \"attributeID_\"+i); //NO I18N\n");
/* 2262 */       out.write("        }\n\n    fieldcount=fieldcount-1; //No I18N\n}\n\nfunction setValues()\n{\n        //set values for field count\n\n        document.getElementById(\"fieldcount\").setAttribute(\"value\", fieldcount);\n}\n\nfunction filterResourceSelectSubmit(resourceValue)\n        {\n            //function to submit the form once all the criteria is statisfied\n\n                 //if (test == true)\n                   //   {\n                            var strFilterEntryForm =document.getElementById(\"frmColumnEntryForm\");\n                            strFilterEntryForm.submit();\n                     // }\n        }\n\nfunction validiate()\n{\n    //function to vladiate the filterentryform\n    var strColumnName=document.getElementById(\"columnName\").value;\n    var strFilterColumn;\n    var strFilterType;\n    var strFilterValue;\n    var i;\n    var test=true;\n    var editOption=document.getElementById(\"editOption\").value;\n    if (strColumnName ==\"\")\n        {\n            // Please enter the ColumnName\n\n\n            alert(\"");
/* 2263 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */         return;
/* 2265 */       out.write("\");\n            document.getElementById(\"columnName\").focus();\n            test=false;\n            return test;\n\n        }\n\n\n\n    for(i=1;i<=fieldcount;i++)\n        {\n            strFilterColumn =document.getElementById(\"resourceName_\"+i).value; //No I18N\n            strFilterType=document.getElementById(\"criteriaType_\"+i).value;\n                //alert(\"this is here \");\n                if (strFilterColumn==\"-- Select Column --\")\n                    {   //alert(\"hello\");\n                        //Please select the column\n                        alert(\"");
/* 2266 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */         return;
/* 2268 */       out.write("\");\n                        document.getElementById(\"resourceName_\"+i).focus();\n                        test=false;\n                        return test;\n                        //break;\n                    }\n                if (strFilterType==\"-- Select Criteria --\")\n                    {\n                        //Please select the criteria\n                        alert(\"");
/* 2269 */       if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */         return;
/* 2271 */       out.write("\");\n                        document.getElementById(\"criteriaType_\"+i).focus();\n                        test=false;\n                        return test;\n                        //break;\n                    }\n            }\n\n\n         if (editOption!='true')\n            {\n                if (strColumnName!=\"\")\n                {\n                    test=checkColumnName();\n                    return test;\n                    \n                }\n            }\n           return test;\n}\n\nfunction windowClose()\n{\n    //function to closee the windows\n    window.close();\n}\n\nfunction checkColumnName()\n{\n    //Function to check for filtername to avoid the duplication entry\n    var value=document.getElementById(\"columnName\").value\n    var columnNames=document.getElementById(\"columnNames\").value;\n    var columnNamesArray=columnNames.split(\",\");\n    // to return if filtername text field is null\n    var test=true;\n\n    for(var i=0;i<columnNamesArray.length;i++)\n        { //No I18N\n            var existedName=columnNamesArray[i];\n");
/* 2272 */       out.write("            if(existedName==value)\n                {\n                    //The Filter name already exist\n                    alert(\"");
/* 2273 */       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */         return;
/* 2275 */       out.write("\"); //No I18N\n                    document.getElementById(\"columnName\").value=\"\";\n                    document.getElementById(\"columnName\").focus();\n                    test=false;\n                    //return;\n                }\n        }\n       return test;\n}\n\nfunction fillAttributeColumn(id)\n{\n    var value1=document.getElementById(id).value;\n    selectResourceName=document.getElementById(id).name;\n    selectResourceId=id;\n    //columnName_1\n    xmlhttp=getHTTPObject();\n    var url=\"AttibutesColumn.jsp\"; //No I18N\n    url=url+\"?resourceType=\"+value1; //No I18N\n    xmlhttp.onreadystatechange=stateChanged;\n    xmlhttp.open(\"GET\",url,true);\n    xmlhttp.send(null);\n}\n\nfunction stateChanged()\n{\nif (xmlhttp.readyState==4)\n  {\n  if (xmlhttp.status==200)\n  {\n      document.getElementById(\"attributeList\").value=xmlhttp.responseText.split(\",\");\n      createAttributeOption(selectResourceId);\n  }\n  }\n}\n\nfunction edit_fillAttributeOption(selAttributeName, attributeList)\n{\n  var selectAttributeType=document.getElementById(selAttributeName);\n");
/* 2276 */       out.write("\n  // to remove all the child from Dropdownlist\n\n\n  while (selectAttributeType.firstChild)  //NO I18N\n   {\n         selectAttributeType.removeChild(selectAttributeType.firstChild);\n   }\n\n\n   // first option in Dropdownlist\n  var anOption = document.createElement(\"OPTION\");  //NO I18N\n\n  anOption.value=\"-- Select Criteria --\";  //NO I18N\n  anOption.text=\"Select Attributes\";  //NO I18N\n  document.getElementById(selAttributeName).options.add(anOption); //NO I18N\n\n\n  //check to neglecting the null values\n  if (attributeList!=null & attributeList.length>0)\n  {\n  var attributeListArray=attributeList.split(\",\");  //NO I18N\n  var attributeListCount=attributeListArray.length;\n      attributeIDArray.length=0;\n      attributeNameArray.length=0;\n      for(var i=0;i<attributeListCount;i++)\n      {\n          var attribute=attributeListArray[i];  //NO I18N\n          var attributeArray=attribute.split(\"#\") //NO I18N\n          var attributeName=attributeArray[1];  //NO I18N\n\n          attributeIDArray[i]=attributeArray[0]; //NO I18N\n");
/* 2277 */       out.write("          attributeNameArray[i]=attributeArray[1]; //NO I18N\n\n          anOption = document.createElement(\"OPTION\"); //NO I18N\n          anOption.value=attributeArray[1]; //NO I18N\n          anOption.text=attributeArray[1]; //NO I18N\n          anOption.id=attributeArray[1]; //NO I18N\n          document.getElementById(selAttributeName).options.add(anOption); //NO I18N\n      }\n  }\n}\n\n\n\nfunction createAttributeOption(id)\n{\n    //alert(selectResourceId);\n  //to getting resource type Drop down list for getting the Attribute ID\n\n  selectResource=document.getElementById(id);\n  selectResourceName=document.getElementById(id).name;\n  // getting ID\n  var attributeId =selectResourceName.substr(13);\n  var SelectAttributeName=\"criteriaType_\"+attributeId; //No I18N\n\n  var selectAttributeType=document.getElementById(SelectAttributeName);\n\n  // to remove all the child from Dropdownlist\n  while (selectAttributeType.firstChild)\n   {\n         selectAttributeType.removeChild(selectAttributeType.firstChild);\n   }\n\n   // first option in Dropdownlist\n");
/* 2278 */       out.write("  var anOption = document.createElement(\"OPTION\");\n\n  anOption.value=\"-- Select Criteria --\";  //NO I18N\n  anOption.text=\"Select Attributes\";   //NO I18N\n  document.getElementById(SelectAttributeName).options.add(anOption); //NO I18N\n\n  var attributeList=document.getElementById(\"attributeList\").value;\n \n  //check to neglecting the null values\n  if (attributeList!=null & attributeList.length>0)\n  {\n  var attributeListArray=attributeList.split(\",\");\n  var attributeListCount=attributeListArray.length;\n      attributeIDArray.length=0;\n      attributeNameArray.length=0;\n       for(var i=0;i<attributeListCount;i++)\n      {\n          var attribute=attributeListArray[i];\n          var attributeArray=attribute.split(\"#\")\n          var attributeName=attributeArray[1];\n\n          attributeIDArray[i]=attributeArray[0]; //No I18N\n          attributeNameArray[i]=attributeArray[1]; //No I18N\n\n          anOption = document.createElement(\"OPTION\"); //No I18N\n\n          anOption.value=attributeArray[1];  //NO I18N\n          anOption.text=attributeArray[1]; //NO I18N\n");
/* 2279 */       out.write("          anOption.id=attributeArray[1]; //NO I18N\n          document.getElementById(SelectAttributeName).options.add(anOption); //NO I18N\n\n\n          //to append the option in ths DropDownList\n          \n      }\n  }\n}\n\nfunction getHTTPObject() {\n  var xmlhttp;\n  if (window.ActiveXObject){\n    try { //No I18N\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\"); //No I18N\n    } catch (e) { //No I18N\n      try { //No I18N\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\"); //No I18N\n      } catch (E) {\n        xmlhttp = false;\n      }\n    }\n}\n  else if (typeof XMLHttpRequest != 'undefined') {\n    try {\n      xmlhttp = new XMLHttpRequest();\n    } catch (e) {\n      xmlhttp = false;\n    }\n  }\n  return xmlhttp;\n}\n\n// to assign the attributeID as hidden value for each attributename selected\n\nfunction assignAttributeID(id)\n{\n\n  var selectAttribute=document.getElementById(id).name;\n  var selectAttributeName=document.getElementById(id).value;\n\n  var rowID =selectAttribute.substr(13);\n  var attribueID=\"attributeID_\"+rowID; //No I18N\n");
/* 2280 */       out.write("  var attrNameIndex=contains(attributeNameArray,selectAttributeName);\n  \n  //to get the get ID from the value\n  document.getElementById(attribueID).value=attributeIDArray[attrNameIndex];\n}\n\n//to get index value of attributenamearray\nfunction contains(array,string)\n{\n    \n    var arrayCount=array.length;\n    var index;\n    for (var i=0;i<arrayCount;i++)\n        {\n            if (array[i]==string)\n                {\n                    index=i;\n                }\n        }\n\n    return index;\n}\n\nfunction columnEntryFormSubmit()\n        {\n            //function to submit the form once all the criteria is statisfied\n\n                  var test=validiate()\n                  if (test == true)\n                      {\n                            var strFilterEntryForm =document.getElementById(\"frmColumnEntryForm\");\n                            document.getElementById(\"col\").value= document.getElementById(\"columnName\").value;\n                            strFilterEntryForm.submit();\n                      }\n        }\n\n\n</script>\n");
/* 2281 */       out.write("<!--Add New Filter -->\n\n\n\n    \n<body>\n\n<!--form action=\"/showFilterResource.do?method=addNewFilter\" method=\"post\"-->\n<form method=\"post\" id=\"frmColumnEntryForm\" >\n");
/*      */       
/*      */ 
/*      */ 
/* 2285 */       AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/* 2286 */       ManagedApplication mo1 = new ManagedApplication();
/* 2287 */       ManagedApplication mo = new ManagedApplication();
/* 2288 */       String viewName = request.getParameter("viewname");
/* 2289 */       int viewID = DBUtil.getViewID(viewName);
/* 2290 */       String fieldCount = request.getParameter("fieldcount");
/* 2291 */       String editOption = request.getParameter("editOption");
/* 2292 */       if (editOption == null)
/*      */       {
/* 2294 */         editOption = "false";
/*      */       }
/*      */       
/* 2297 */       String barTitle = "";
/* 2298 */       String windowTitle = "";
/* 2299 */       if ((editOption.equals("true") & !editOption.equals("")))
/*      */       {
/*      */ 
/* 2302 */         barTitle = FormatUtil.getString("am.webclient.alertviews.heading.addmoreattributes.title");
/* 2303 */         windowTitle = FormatUtil.getString("am.webclient.alertviews.heading.addmoreattributes.title");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2308 */         barTitle = FormatUtil.getString("am.webclient.alertviews.heading.addnewcolumn.title");
/* 2309 */         windowTitle = FormatUtil.getString("am.webclient.alertviews.heading.addnewcolumn.title");
/*      */       }
/*      */       
/*      */ 
/* 2313 */       String column = request.getParameter("column");
/*      */       
/* 2315 */       String columnName = request.getParameter("columnName");
/* 2316 */       System.out.println("columnName" + columnName);
/* 2317 */       System.out.println("editOption" + editOption);
/* 2318 */       int columnID = 0;
/*      */       
/*      */ 
/*      */ 
/* 2322 */       out.write("\n\n    \n    <input type=\"hidden\" name=\"attributeList\" id=\"attributeList\" value=\"\">\n    <input type=\"hidden\" name=\"editOption\" id =\"editOption\" value=\"");
/* 2323 */       out.print(editOption);
/* 2324 */       out.write("\">\n        <!-- column name in hiddeen-->\n    <input type=\"hidden\" name=\"col\" id =\"col\">\n    <!--input type=\"button\" name=\"edit\" value=\"edit\" onclick=\"restore()\"-->\n    <title>");
/* 2325 */       out.print(windowTitle);
/* 2326 */       out.write("</title>\n\n\n    \n\n");
/*      */       
/*      */ 
/*      */ 
/* 2330 */       String strColumnList1 = "";
/* 2331 */       String strColumnList = "";
/* 2332 */       ResultSet columnNameSet = null;
/*      */       try
/*      */       {
/* 2335 */         String columnNameQuery = "select COLUMNNAME from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where VIEWID=" + viewID + "";
/* 2336 */         columnNameSet = AMConnectionPool.executeQueryStmt(columnNameQuery);
/*      */         
/* 2338 */         while (columnNameSet.next())
/*      */         {
/* 2340 */           strColumnList1 = columnNameSet.getString("COLUMNNAME");
/* 2341 */           strColumnList = strColumnList + strColumnList1 + ",";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2348 */         e.printStackTrace();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2353 */         AMConnectionPool.closeStatement(columnNameSet); return; } finally { AMConnectionPool.closeStatement(columnNameSet);
/*      */       }
/*      */       
/* 2356 */       if ((strColumnList != null) && (!strColumnList.equals("")))
/*      */       {
/* 2358 */         strColumnList = strColumnList.substring(0, strColumnList.length() - 1);
/*      */       }
/*      */       
/* 2361 */       out.write("\n    <input type=\"hidden\" name=\"columnNames\" id=\"columnNames\" value=\"");
/* 2362 */       out.print(strColumnList);
/* 2363 */       out.write("\" >\n\n    ");
/*      */       
/*      */ 
/* 2366 */       columnName = request.getParameter("col");
/*      */       
/* 2368 */       if (columnName != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2373 */         if ((columnName != null) && (editOption.equals("true")) && (viewName != null))
/*      */         {
/* 2375 */           ResultSet columnIDSet = null;
/*      */           try
/*      */           {
/* 2378 */             String columnIDQuery = "select COLUMNID from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where COLUMNNAME='" + columnName + "' and VIEWID=" + viewID + "";
/* 2379 */             System.out.println("columnIDQuery" + columnIDQuery);
/* 2380 */             columnIDSet = AMConnectionPool.executeQueryStmt(columnIDQuery);
/*      */             
/* 2382 */             if (columnIDSet.next())
/*      */             {
/* 2384 */               columnID = columnIDSet.getInt("COLUMNID");
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 2391 */             e.printStackTrace();
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 2396 */             AMConnectionPool.closeStatement(columnIDSet); return; } finally { AMConnectionPool.closeStatement(columnIDSet);
/*      */           }
/*      */           
/*      */ 
/*      */           try
/*      */           {
/* 2402 */             String columnQuery = "delete from AM_STANDALONE_VIEWCOLUMN_DETAILS where COLUMNID=" + columnID + "";
/* 2403 */             mo.executeUpdateStmt(columnQuery);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 2407 */             e.printStackTrace(); return;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2416 */         if ((columnName != null) && (!editOption.equals("true")) && (viewName != null))
/*      */         {
/*      */ 
/* 2419 */           String viewNameQuery = "Select VIEWNAME from AM_STANDALONE_VIEWDETAILS where VIEWNAME='" + viewName + "'";
/* 2420 */           ArrayList viewNameList = mo.getRows(viewNameQuery);
/* 2421 */           int viewListSize = viewNameList.size();
/* 2422 */           int incrViewID = 0;
/* 2423 */           if (viewListSize == 0)
/*      */           {
/*      */ 
/*      */             try
/*      */             {
/*      */ 
/* 2429 */               incrViewID = DBQueryUtil.getIncrementedID("VIEWID", "AM_STANDALONE_VIEWDETAILS");
/* 2430 */               String viewNameInsertQuery = "insert into AM_STANDALONE_VIEWDETAILS values(" + incrViewID + ",'" + viewName + "')";
/* 2431 */               System.out.println("viewNameInsertQuery" + viewNameInsertQuery);
/* 2432 */               mo.executeUpdateStmt(viewNameInsertQuery);
/*      */ 
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2437 */               e.printStackTrace(); return;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2443 */           Statement stmt = null;
/* 2444 */           ResultSet rs = null;
/*      */           
/*      */           try
/*      */           {
/* 2448 */             AMConnectionPool.getInstance();stmt = AMConnectionPool.getConnection().createStatement();
/*      */             
/* 2450 */             int incrColumnID = DBQueryUtil.getIncrementedID("COLUMNID", "AM_STANDALONE_VIEWCOLUMN_ASSOCIATION");
/* 2451 */             System.out.println("incrColumnID" + incrColumnID);
/*      */             
/* 2453 */             String columnInsertQuery = "insert into AM_STANDALONE_VIEWCOLUMN_ASSOCIATION values(" + incrColumnID + ",'" + columnName + "'," + viewID + ")";
/* 2454 */             System.out.println("columnInsertQuery" + columnInsertQuery);
/* 2455 */             stmt.executeUpdate(columnInsertQuery);
/*      */             
/*      */ 
/* 2458 */             columnID = incrColumnID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2471 */             e.printStackTrace();
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 2476 */             AMConnectionPool.closeStatement(rs);
/* 2477 */             stmt.close(); return;
/*      */           }
/*      */           finally
/*      */           {
/* 2476 */             AMConnectionPool.closeStatement(rs);
/* 2477 */             stmt.close();
/*      */           }
/*      */         }
/* 2480 */         String tempResourceName = "";
/* 2481 */         String tempAttributeType = "";
/* 2482 */         String tempAttributeID = "";
/* 2483 */         String resourceNames = "";
/* 2484 */         String attributeType = "";
/* 2485 */         String attributeID = "";
/* 2486 */         String attrIDQuery = "";
/* 2487 */         ResultSet attrIDSet = null;
/* 2488 */         int count = Integer.parseInt(fieldCount);
/*      */         
/*      */ 
/* 2491 */         int attrID = 0;
/* 2492 */         for (int i = 1; i <= count; i++)
/*      */         {
/* 2494 */           tempResourceName = "resourceName_" + i;
/* 2495 */           tempAttributeType = "criteriaType_" + i;
/* 2496 */           tempAttributeID = "attributeID_" + i;
/*      */           
/* 2498 */           resourceNames = request.getParameter(tempResourceName);
/* 2499 */           attributeType = request.getParameter(tempAttributeType);
/* 2500 */           attributeID = request.getParameter(tempAttributeID);
/*      */           
/*      */ 
/*      */ 
/* 2504 */           if (resourceNames.equals("Script Monitor"))
/*      */           {
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 2513 */               attrIDQuery = "select ATTRIBUTEID from AM_ATTRIBUTES where ATTRIBUTEID>=2200 and ATTRIBUTEID<2300";
/* 2514 */               attrIDSet = AMConnectionPool.executeQueryStmt(attrIDQuery);
/*      */               
/* 2516 */               while (attrIDSet.next())
/*      */               {
/* 2518 */                 attrID = Integer.parseInt(attrIDSet.getString("ATTRIBUTEID").trim());
/*      */                 
/* 2520 */                 String query1 = "insert into AM_STANDALONE_VIEWCOLUMN_DETAILS values ('" + columnID + "','" + resourceNames + "', '" + attributeType + "'," + attrID + ")";
/* 2521 */                 mo1.executeUpdateStmt(query1);
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2526 */               e.printStackTrace();
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 2531 */               AMConnectionPool.closeStatement(attrIDSet); return; } finally { AMConnectionPool.closeStatement(attrIDSet);
/*      */             }
/*      */             
/*      */           }
/*      */           else
/*      */           {
/*      */             try
/*      */             {
/* 2539 */               attrID = Integer.parseInt(attributeID);
/* 2540 */               String query1 = "insert into AM_STANDALONE_VIEWCOLUMN_DETAILS values ('" + columnID + "','" + resourceNames + "', '" + attributeType + "'," + attrID + ")";
/* 2541 */               mo1.executeUpdateStmt(query1);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2545 */               e.printStackTrace(); return;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2551 */         out.write("\n    <script>\n        window.opener.location=\"/jsp/ConfigureViews.jsp?selectedTab=divColumnsTab&isCreateView=false&state=column&viewname=");
/* 2552 */         out.print(viewName);
/* 2553 */         out.write("\";\n        //am.webclient.alertviews.confirm.columnname.create.title\n        //alert(\"");
/* 2554 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */           return;
/* 2556 */         out.write("\"); //No I18N\n        \n        //window.opener.location.reload();\n        window.close();\n    </script>\n   ");
/*      */       }
/*      */       
/*      */ 
/* 2560 */       out.write("\n\n\n\n    <!--input type=\"hidden\" name=\"filtercolumn\">\n    <INPUT TYPE=\"hidden\" NAME=\"filtercriteria\">\n    <INPUT TYPE=\"hidden\" NAME=\"filtervalue\"-->\n    <INPUT TYPE=\"hidden\" NAME=\"fieldcount\" ID=\"fieldcount\" value=\"1\">\n    \n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n   <tr>\n       <td class=\"tableheadingbborder\" height=\"26\" width=\"100%\">\n           <!--Add New Filter-->\n           <!-- ");
/* 2561 */       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */         return;
/* 2563 */       out.write(" --> ");
/* 2564 */       out.write("\n                ");
/* 2565 */       out.print(barTitle);
/* 2566 */       out.write("\n         <td>\n   </tr>\n\t<tr>\n\t\t<td>\n    \t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n    \t\t\t\t<tr>\n                        <td>\n                \t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n                    \t\t\t<tr>\n                                        <!--Filter Name -->\n                                        <!-- Column Name -->\n                                    <td width=\"25%\" class=\"bodytext label-align\" ><b>");
/* 2567 */       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */         return;
/* 2569 */       out.write("</b></td> ");
/* 2570 */       out.write("\n                                    <td width=\"75%\">\n                                    ");
/*      */       
/* 2572 */       if ((editOption.equals("true")) && (!editOption.equals("")))
/*      */       {
/*      */ 
/* 2575 */         out.write("\n                                    <input type=\"text\" align=\"right\" name=\"columnName\" id=\"columnName\" class=\"formtext normal\" value=\"");
/* 2576 */         out.print(column);
/* 2577 */         out.write("\" disabled=\"true\" >\n                                    ");
/*      */       }
/*      */       else
/*      */       {
/* 2581 */         out.write("\n                                        <input type=\"text\" align=\"right\" name=\"columnName\" id=\"columnName\" class=\"formtext normal\" > <!--onblur=\"checkColumnName()\"-->\n                                    ");
/*      */       }
/*      */       
/* 2584 */       out.write("\n                                     \n                                    </td>\n                    \t\t\t</tr>\n\n                \t\t\t</table>\n                        </td>\n                    </tr>\n                <!--tr><td class=\"bodytext\" ><b>");
/* 2585 */       if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */         return;
/* 2587 */       out.write("</b></td></tr-->\n\t\t\t\t<tr>\n    \t\t\t\t\t<td>\n                            <!--colour changes done in commmonstyle.css -->\n     \t \t\t\t\t\t<fieldset id =\"f1\">\n                                <!-- Select Criteria -->\n                                <!--Select Resource & Attributes -->\n                              \t\n                                \n          \t\t\t\t\t\t<table width=\"100%\" border=\"0\" align=\"center\"  class=\"bodytext\" cellpadding=\"5\" cellspacing=\"0\" id=\"criteriaAdd\">\n            \t\t\t\t\t\t<tbody id = \"tbodycri\">\n              \t\t\t\t\t\t<tr>\n                \t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\" nowrap>\n                                        <span><b>");
/* 2588 */       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */         return;
/* 2590 */       out.write("</b></span>\n                                        </td>\n                                        <!--Columns -->\n                                        <!--Resources-->\n               \t \t\t\t\t\t\t<td width=\"30%\"><b>");
/* 2591 */       if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */         return;
/* 2593 */       out.write("</b></td>\n                                        <!--Criteria Type -->\n                                        <!--Attributes -->\n\n                \t\t\t\t\t\t<td width=\"30%\"> <b>");
/* 2594 */       if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*      */         return;
/* 2596 */       out.write("</b></td>\n                                        <!--Value-->\n                \t\t\t\t\t\t<!--td width=\"16%\"><b>");
/* 2597 */       if (_jspx_meth_fmt_005fmessage_005f11(_jspx_page_context))
/*      */         return;
/* 2599 */       out.write("</b> </td-->\n                \t\t\t\t\t\t<td width=\"15W%\"> </td>\n              \t\t\t\t\t\t</tr>\n              \t\t\t\t\t\t<tr id = \"row_1\" class=\"bgstrip\">\n                \t\t\t\t\t\t<td width=\"25%\" class=\"bodytext label-align\"><select name = \"andorList_1\" id =\"andorList_1\" style= \"display:none;\" />&nbsp;</td>\n                \t\t\t\t\t\t<td width=\"30%\">\n\n                \t\t\t\t\t\t\t<select name=\"resourceName_1\" id= \"resourceName_1\"  size=1  value=\"-- Select Column --\" class=\"formtext\" STYLE=\"width:160px;\" onChange=\"fillAttributeColumn(this.id)\">\n                                                <!--Select Column -->\n                                                <!--");
/* 2600 */       if (_jspx_meth_fmt_005fmessage_005f12(_jspx_page_context))
/*      */         return;
/* 2602 */       out.write(" -->\n                      \t\t\t\t\t\t\t<option value =\"-- Select Column --\">Select Resources</option>\n                                                ");
/*      */       
/*      */ 
/* 2605 */       ArrayList resourceList = mo.getRows("Select RESOURCETYPE from AM_ManagedResourceType where RESOURCETYPE not in ('HAI') order by RESOURCETYPE");
/*      */       
/* 2607 */       int resourceCount = resourceList.size();
/* 2608 */       for (int i = 0; i < resourceCount; i++)
/*      */       {
/* 2610 */         ArrayList tempView = (ArrayList)resourceList.get(i);
/* 2611 */         String tempView1 = (String)tempView.get(0);
/*      */         
/*      */ 
/* 2614 */         out.write("\n                                                            <option value=\"");
/* 2615 */         out.print(tempView1);
/* 2616 */         out.write(34);
/* 2617 */         out.write(62);
/* 2618 */         out.print(tempView1);
/* 2619 */         out.write("</option>\n                                                 ");
/*      */       }
/*      */       
/* 2622 */       out.write("\n\n                  \t\t\t\t\t\t\t</select>\n                                        </td>\n\n                \t\t\t\t\t\t<td width=\"30%\">\n                                            \n                                        <select name=\"criteriaType_1\" id = \"criteriaType_1\" size=1 value=\"Select Criteria\" class=\"formtext\" STYLE=\"width:160px;\" onChange=\"assignAttributeID(this.id)\" >\n                                            <!---- Select Criteria -- -->\n                                            <!--");
/* 2623 */       if (_jspx_meth_fmt_005fmessage_005f13(_jspx_page_context))
/*      */         return;
/* 2625 */       out.write(" -->");
/* 2626 */       out.write("\n                                            <option value =\"-- Select Criteria --\"><b>Select Attributes</b></option> ");
/* 2627 */       out.write("\n\n                                        </select>\n                                            <input type=\"hidden\" id=\"attributeID_1\" name=\"attributeID_1\">\n                \t\t\t\t\t\t</td>\n                \t\t\t\t\t\t<!--td width=\"16%\"> <input type=\"text\" class='smallbox' name = \"criteriavalue_1\" id =\"txtHint\" size=\"20\"value=\"\"> </td-->\n                \t\t\t\t\t\t<!--td> <span name = \"browsespan_1\" id = \"browsespan_1\"> <img src =\"/images/browse.png\" style=\"cursor: pointer;\" onclick=\"javascript:showBrowse(this.parentNode.parentNode.parentNode)\" title =\"Search Value\" /></span> <span name =\"datespan_1\" id =\"datespan_1\" style = \"display:none;\"><img style=\"cursor: pointer;\" src =\"/images/calendar.gif\" onclick=\"javascript:showDateWindow(this.parentNode.parentNode.parentNode);\" /></span> </td-->\n                                        <!--Add Criteria -->\n\n                \t\t\t\t\t\t<td width=\"15%\" nowrap> \n                                        <img src=\"/images/delete.png\" class=\"align-middle\" style=\"cursor: pointer;\" title = \"");
/* 2628 */       if (_jspx_meth_fmt_005fmessage_005f14(_jspx_page_context))
/*      */         return;
/* 2630 */       out.write("\" onClick=\"javascript:deleteCriteria(this.parentNode.parentNode); \"> &nbsp;\n\n                                        <span name=\"span_1\" id =\"span_1\" ><img src=\"/images/add-plus.png\" class=\"align-middle\" style=\"cursor: pointer;\" alt = \"Add Criteria\" title = \"");
/* 2631 */       if (_jspx_meth_fmt_005fmessage_005f15(_jspx_page_context))
/*      */         return;
/* 2633 */       out.write("\" onClick=\"criteriaAdd(this.parentNode);\" ></span>\n                                        </td>\n              \t\t\t\t\t\t</tr>\n\n            \t\t\t\t\t\t</tbody>\n          \t\t\t\t\t\t</table>\n          \t\t\t\t\t</fieldset>\n    \t\t\t\t\t</td>\n\t\t\t\t</tr>\n\n\t\t\t</table>\n\n\t\t\t<div style=\"display:none\">\n  \t\t\t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" id=\"criteriaAdd1\">\n    \t\t\t<tbody>\n      \t\t\t\t<tr class=\"bgstrip\" name=\"row\">\n        \t\t\t\t<td width=\"25%\" >&nbsp; </td>\n\t\t\t\t\t<td width=\"27%\">\n                        <!--Resource Type -->\n\t\t\t\t\t\t<select name=\"resourceName\" id = \"resourceName\" size=1 value=\"\" class=\"formtext\" STYLE=\"width:160px;\" onChange=\"fillAttributeColumn(this.id)\">\n                            <!---- Select Column -- -->\n                            <!-- ");
/* 2634 */       if (_jspx_meth_fmt_005fmessage_005f16(_jspx_page_context))
/*      */         return;
/* 2636 */       out.write("--> ");
/* 2637 */       out.write("\n            \t\t\t\t\t<option value=\"-- Select Column --\">Select Resources</option> ");
/* 2638 */       out.write("\n                                ");
/*      */       
/* 2640 */       for (int i = 0; i < resourceCount; i++)
/*      */       {
/* 2642 */         ArrayList tempView = (ArrayList)resourceList.get(i);
/* 2643 */         String tempView1 = (String)tempView.get(0);
/*      */         
/* 2645 */         out.write("\n                                 <option value=\"");
/* 2646 */         out.print(tempView1);
/* 2647 */         out.write(34);
/* 2648 */         out.write(62);
/* 2649 */         out.print(tempView1);
/* 2650 */         out.write("</option>\n                               ");
/*      */       }
/*      */       
/*      */ 
/* 2654 */       out.write("\n\n                      \t\t\t<!--option value=\"name\">Name</option>\n                      \t\t\t<option value=\"type\">Type</option-->\n                      \t\t\t<!--option value=\"availability\">Availability</option-->\n                      \t\t\t<!--option value=\"health\">Health</option-->\n\n\n          \t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"27%\">\n                        <!-- select attributes -->\n\t\t\t\t\t\t<select name=\"criteriaType\"  id =\"criteriaType\" size=1 value=\"Select Criteria\" class=\"formtext\" STYLE=\"width:160px;\"  onchange=\"assignAttributeID(this.id)\"> <!--onfocus=\"fillAttributeColumnAlone(this.id)\" -->\n                               <!---- Select Criteria -- -->\n                               <!--");
/* 2655 */       if (_jspx_meth_fmt_005fmessage_005f17(_jspx_page_context))
/*      */         return;
/* 2657 */       out.write("  --> ");
/* 2658 */       out.write("\n                               <option value = \"-- Select Criteria --\">Select Attributes</option> ");
/* 2659 */       out.write("\n        \t\t\t\t</select>\n                        \n\t\t\t\t\t</td>\n\n\t\t\t\t\t<!--td width=\"16%\"> <input type=\"text\" class=\"smallbox\" name='criteriavalue' id='criteriavalue' size=\"20\"> </td-->\n        \t\t\t\t<!--td> <span name = \"browsespan\" id = \"browsespan\"> <img src =\"/images/browse.png\" style=\"cursor: pointer;\" style=\"cursor: pointer;\" title=\"Search Value\" onclick=\"javascript:showBrowse(this.parentNode.parentNode.parentNode)\" /></span> <span name =\"datespan\" id =\"datespan\" style =\"display:none\"><img src =\"/images/calendar.gif\" style=\"cursor: pointer;\" onclick= \"javascript:showDateWindow(this.parentNode.parentNode.parentNode);\"/></span> </td-->\n        \t\t\t\t<td width=\"10%\" nowrap>\n                        <!--Delete Criteria -->\n\t\t\t\t\t\t<img src=\"/images/delete.png\" class=\"align-middle\" style=\"cursor: pointer;\" title = \"");
/* 2660 */       if (_jspx_meth_fmt_005fmessage_005f18(_jspx_page_context))
/*      */         return;
/* 2662 */       out.write("\" onClick=\"javascript:deleteCriteria(this.parentNode.parentNode); \"> &nbsp;\n                        <!--Add Criteria -->\n\t\t\t\t\t\t<span  name=\"span\" id=\"span\"><img  style=\"cursor: pointer;\" src=\"/images/add-plus.png\" class=\"align-middle\" title = \"");
/* 2663 */       if (_jspx_meth_fmt_005fmessage_005f19(_jspx_page_context))
/*      */         return;
/* 2665 */       out.write("\" onClick=\"criteriaAdd(this.parentNode);\" ></span>\n\n        \t\t\t\t</td>\n      \t\t\t\t</tr>\n    \t\t\t</tbody>\n  \t\t\t</table>\n\t\t\t</div>\n    <!--div id=\"attributedetail\" style=\"display:block;overflow:auto;width:400px;height:375px\" >\n\n\t  </div-->\n\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n        \t\t\t<tr>    <!--save -->\n                    \t\t<td width=\"25%\" class=\"tablebottom\"></td>\n                            <td class=\"tablebottom\">\n                \t\t\t<input type=\"button\" class=\"buttons btn_highlt\" name=\"sumbit\" value=\"");
/* 2666 */       if (_jspx_meth_fmt_005fmessage_005f20(_jspx_page_context))
/*      */         return;
/* 2668 */       out.write("\" onClick=\"columnEntryFormSubmit()\">\n                            <!--close-->\n                \t\t\t<input type=\"button\" class=\"buttons btn_link\" name=\"close\" value=\"");
/* 2669 */       if (_jspx_meth_fmt_005fmessage_005f21(_jspx_page_context))
/*      */         return;
/* 2671 */       out.write("\" onClick=\"windowClose()\">\n                            </td>\n\n    \t\t\t\t</tr>\n\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n    ");
/*      */       
/* 2673 */       String editColumnName = request.getParameter("column");
/* 2674 */       int editColumnID = 0;
/* 2675 */       int columnCount = 0;
/* 2676 */       String resourceNameStr = "";
/* 2677 */       String attributeNameStr = "";
/* 2678 */       String attributeIDStr = "";
/*      */       
/* 2680 */       String resourceNameStr1 = "";
/* 2681 */       String attributeNameStr1 = "";
/* 2682 */       String attributeIDStr1 = "";
/*      */       
/*      */ 
/* 2685 */       if ((editOption.equals("true")) && (!editOption.equals("")))
/*      */       {
/*      */ 
/* 2688 */         ResultSet editColumnIDSet = null;
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 2693 */           String editColumnIDQuery = "select COLUMNID from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where COLUMNNAME='" + editColumnName + "' and VIEWID=" + viewID + "";
/* 2694 */           editColumnIDSet = AMConnectionPool.executeQueryStmt(editColumnIDQuery);
/*      */           
/* 2696 */           if (editColumnIDSet.next())
/*      */           {
/* 2698 */             editColumnID = editColumnIDSet.getInt("COLUMNID");
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2705 */           e.printStackTrace();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2710 */           AMConnectionPool.closeStatement(editColumnIDSet); return; } finally { AMConnectionPool.closeStatement(editColumnIDSet);
/*      */         }
/*      */         
/* 2713 */         ArrayList columnList = mo.getRows("select RESOURCETYPE, ATTRIBUTE, ATTRIBUTEID  from AM_STANDALONE_VIEWCOLUMN_DETAILS  where COLUMNID=" + editColumnID + "");
/* 2714 */         columnCount = columnList.size();
/*      */         
/*      */ 
/* 2717 */         ResultSet scriptResourceSet = null;
/* 2718 */         String defaultAttrQuery = "";
/* 2719 */         String defaultAttrID = "";
/* 2720 */         String defaultAttrName = "";
/* 2721 */         String tempList1 = "";
/* 2722 */         String resourceAttributeList = "";
/* 2723 */         String resourceAttributeList1 = "";
/* 2724 */         ResultSet defaultAttrSet = null;
/*      */         
/* 2726 */         String resourceType = "";
/* 2727 */         String scriptResourceID = "";
/* 2728 */         String scriptResourceName = "";
/* 2729 */         String tempList = "";
/*      */         
/*      */ 
/*      */ 
/* 2733 */         for (int i = 0; i < columnCount; i++)
/*      */         {
/* 2735 */           ArrayList columnDetailList = (ArrayList)columnList.get(i);
/*      */           
/* 2737 */           resourceType = (String)columnDetailList.get(0);
/*      */           
/* 2739 */           if (resourceType != "")
/*      */           {
/* 2741 */             tempList = "";
/*      */             
/*      */ 
/* 2744 */             if (!resourceType.equals("Script Monitor"))
/*      */             {
/*      */               try
/*      */               {
/* 2748 */                 defaultAttrQuery = "select ATTRIBUTEID, DISPLAYNAME from AM_ATTRIBUTES where RESOURCETYPE='" + resourceType + "' and DISPLAYNAME in('Availability','Health');";
/* 2749 */                 defaultAttrSet = AMConnectionPool.executeQueryStmt(defaultAttrQuery);
/* 2750 */                 while (defaultAttrSet.next())
/*      */                 {
/* 2752 */                   defaultAttrID = defaultAttrSet.getString("ATTRIBUTEID");
/* 2753 */                   defaultAttrName = defaultAttrSet.getString("DISPLAYNAME");
/* 2754 */                   tempList = tempList + defaultAttrID + "#" + defaultAttrName + ",";
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 2759 */                 e.printStackTrace();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2764 */                 AMConnectionPool.closeStatement(defaultAttrSet); return; } finally { AMConnectionPool.closeStatement(defaultAttrSet);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/* 2769 */             if (resourceType.equals("Script Monitor"))
/*      */             {
/*      */ 
/* 2772 */               String scriptResourceQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where TYPE='Script Monitor' order by DISPLAYNAME";
/* 2773 */               scriptResourceSet = AMConnectionPool.executeQueryStmt(scriptResourceQuery);
/* 2774 */               while (scriptResourceSet.next())
/*      */               {
/* 2776 */                 scriptResourceID = scriptResourceSet.getString("RESOURCEID");
/* 2777 */                 scriptResourceName = scriptResourceSet.getString("DISPLAYNAME");
/* 2778 */                 tempList = tempList + scriptResourceID + "#" + scriptResourceName + ",";
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 2783 */               List attList = ReportUtil.getAttributesForResourcetype(resourceType);
/*      */               
/*      */ 
/* 2786 */               String healthIDQuery = "";
/* 2787 */               String healthID = "";
/* 2788 */               ResultSet healthIDSet = null;
/* 2789 */               healthIDQuery = "select ATTRIBUTEID from AM_ATTRIBUTES where RESOURCETYPE='" + resourceType + "' and ATTRIBUTE in ('Health')";
/* 2790 */               healthIDSet = AMConnectionPool.executeQueryStmt(healthIDQuery);
/*      */               
/* 2792 */               while (healthIDSet.next())
/*      */               {
/* 2794 */                 healthID = healthIDSet.getString("ATTRIBUTEID");
/*      */               }
/* 2796 */               ArrayList childAttrList = ReportUtil.getSecondLevelAttribute(healthID, resourceType, true);
/* 2797 */               int childCount = childAttrList.size();
/* 2798 */               for (int k = 0; k < childCount; k++)
/*      */               {
/* 2800 */                 String temp = (String)childAttrList.get(k);
/* 2801 */                 if (!attList.contains(temp))
/*      */                 {
/* 2803 */                   attList.add(temp);
/*      */                 }
/* 2805 */                 else if (attList.contains(temp))
/*      */                 {
/* 2807 */                   int index = attList.indexOf(temp);
/* 2808 */                   attList.remove(index);
/* 2809 */                   attList.add(temp);
/*      */                 }
/*      */               }
/*      */               
/* 2813 */               int attributeCount = attList.size();
/* 2814 */               for (int j = 0; j < attributeCount; j++)
/*      */               {
/* 2816 */                 String res = (String)attList.get(j);
/* 2817 */                 tempList = tempList + res + ",";
/*      */               }
/*      */             }
/* 2820 */             tempList = tempList.substring(0, tempList.length() - 1);
/* 2821 */             tempList1 = tempList.trim();
/*      */           }
/* 2823 */           resourceAttributeList = resourceAttributeList + tempList1 + "@";
/*      */           
/*      */ 
/* 2826 */           resourceNameStr1 = (String)columnDetailList.get(0);
/* 2827 */           resourceNameStr = resourceNameStr + resourceNameStr1 + ",";
/*      */           
/* 2829 */           attributeNameStr1 = (String)columnDetailList.get(1);
/* 2830 */           attributeNameStr = attributeNameStr + attributeNameStr1 + ",";
/*      */           
/* 2832 */           attributeIDStr1 = (String)columnDetailList.get(2);
/* 2833 */           attributeIDStr = attributeIDStr + attributeIDStr1 + ",";
/*      */         }
/*      */         
/* 2836 */         if ((resourceAttributeList != "") && (resourceNameStr != null))
/*      */         {
/* 2838 */           resourceAttributeList = resourceAttributeList.substring(0, resourceAttributeList.length() - 1);
/* 2839 */           resourceAttributeList1 = resourceAttributeList.trim();
/*      */         }
/*      */         
/* 2842 */         if ((resourceNameStr != "") && (resourceNameStr != null))
/*      */         {
/* 2844 */           resourceNameStr = resourceNameStr.substring(0, resourceNameStr.length() - 1);
/*      */         }
/* 2846 */         if ((attributeNameStr != "") && (attributeNameStr != null))
/*      */         {
/* 2848 */           attributeNameStr = attributeNameStr.substring(0, attributeNameStr.length() - 1);
/*      */         }
/*      */         
/* 2851 */         if ((attributeIDStr != "") && (attributeIDStr != null))
/*      */         {
/* 2853 */           attributeIDStr = attributeIDStr.substring(0, attributeIDStr.length() - 1);
/*      */         }
/*      */         
/* 2856 */         out.write("\n                    <INPUT TYPE=\"hidden\" NAME=\"editCount\" ID=\"editCount\" value=\"");
/* 2857 */         out.print(columnCount);
/* 2858 */         out.write("\">\n                    <INPUT TYPE=\"hidden\" NAME=\"editResourceName\" ID=\"editResourceName\" value=\"");
/* 2859 */         out.print(resourceNameStr);
/* 2860 */         out.write("\">\n                    <INPUT TYPE=\"hidden\" NAME=\"editAttributeName\" ID=\"editAttributeName\" value=\"");
/* 2861 */         out.print(attributeNameStr);
/* 2862 */         out.write("\">\n                    <INPUT TYPE=\"hidden\" NAME=\"editAttributeID\" ID=\"editAttributeID\" value=\"");
/* 2863 */         out.print(attributeIDStr);
/* 2864 */         out.write("\">\n                    <INPUT TYPE=\"hidden\" NAME=\"resourceAttributeList\" ID=\"resourceAttributeList\" value=\"");
/* 2865 */         out.print(resourceAttributeList1);
/* 2866 */         out.write("\">\n                    <script>\n                            editFormrestore();\n                    </script>\n                  ");
/*      */       }
/* 2868 */       out.write("\n\n</table>\n</form>\n</body>\n");
/*      */     } catch (Throwable t) {
/* 2870 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2871 */         out = _jspx_out;
/* 2872 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2873 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2874 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2877 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2883 */     PageContext pageContext = _jspx_page_context;
/* 2884 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2886 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2887 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2888 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2890 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2892 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2893 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2894 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2895 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2896 */       return true;
/*      */     }
/* 2898 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2904 */     PageContext pageContext = _jspx_page_context;
/* 2905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2907 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2908 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2909 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 2910 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2911 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2912 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2913 */         out = _jspx_page_context.pushBody();
/* 2914 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2915 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2918 */         out.write("am.webclient.alertviews.err.columnname.title");
/* 2919 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2920 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2923 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2924 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2927 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2928 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2929 */       return true;
/*      */     }
/* 2931 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2932 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2937 */     PageContext pageContext = _jspx_page_context;
/* 2938 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2940 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2941 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2942 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 2943 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2944 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2945 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2946 */         out = _jspx_page_context.pushBody();
/* 2947 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2948 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2951 */         out.write("am.webclient.alertviews.err.resourcetype.title");
/* 2952 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2953 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2956 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2957 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2960 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2961 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2962 */       return true;
/*      */     }
/* 2964 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2970 */     PageContext pageContext = _jspx_page_context;
/* 2971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2973 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2974 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2975 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 2976 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2977 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 2978 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2979 */         out = _jspx_page_context.pushBody();
/* 2980 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 2981 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2984 */         out.write("am.webclient.alertviews.err.attributes.title");
/* 2985 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 2986 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2989 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2990 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2993 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2994 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2995 */       return true;
/*      */     }
/* 2997 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3003 */     PageContext pageContext = _jspx_page_context;
/* 3004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3006 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3007 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3008 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 3009 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3010 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3011 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3012 */         out = _jspx_page_context.pushBody();
/* 3013 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3014 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3017 */         out.write("am.webclient.alertviews.err.columnduplicate.title");
/* 3018 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3019 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3022 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3023 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3026 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3027 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3028 */       return true;
/*      */     }
/* 3030 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3036 */     PageContext pageContext = _jspx_page_context;
/* 3037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3039 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3040 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3041 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3042 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3043 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3044 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3045 */         out = _jspx_page_context.pushBody();
/* 3046 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3047 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3050 */         out.write("am.webclient.alertviews.confirm.columnname.create.title");
/* 3051 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3052 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3055 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3056 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3059 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3060 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3061 */       return true;
/*      */     }
/* 3063 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3069 */     PageContext pageContext = _jspx_page_context;
/* 3070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3072 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3073 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3074 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 3075 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3076 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3077 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3078 */         out = _jspx_page_context.pushBody();
/* 3079 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3080 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3083 */         out.write("am.webclient.noc.filter.formname.text");
/* 3084 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3088 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3089 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3092 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3093 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3094 */       return true;
/*      */     }
/* 3096 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3102 */     PageContext pageContext = _jspx_page_context;
/* 3103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3105 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3106 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3107 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/* 3108 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3109 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3110 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3111 */         out = _jspx_page_context.pushBody();
/* 3112 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3113 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3116 */         out.write("am.webclient.alertviews.heading.columnname.title");
/* 3117 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3118 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3121 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3122 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3125 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3126 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3127 */       return true;
/*      */     }
/* 3129 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3130 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3135 */     PageContext pageContext = _jspx_page_context;
/* 3136 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3138 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3139 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3140 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/* 3141 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3142 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3143 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3144 */         out = _jspx_page_context.pushBody();
/* 3145 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3146 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3149 */         out.write("am.webclient.alertviews.heading.selectfieldset.title");
/* 3150 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3154 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3155 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3158 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3159 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3160 */       return true;
/*      */     }
/* 3162 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3168 */     PageContext pageContext = _jspx_page_context;
/* 3169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3171 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3172 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3173 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/* 3174 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3175 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3176 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3177 */         out = _jspx_page_context.pushBody();
/* 3178 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3179 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3182 */         out.write("am.webclient.alertviews.heading.selectfieldset.title");
/* 3183 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3184 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3187 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3188 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3191 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3192 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3193 */       return true;
/*      */     }
/* 3195 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3201 */     PageContext pageContext = _jspx_page_context;
/* 3202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3204 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3205 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3206 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/* 3207 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3208 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3209 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3210 */         out = _jspx_page_context.pushBody();
/* 3211 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3212 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3215 */         out.write("am.webclient.alertviews.heading.resourcetype.title");
/* 3216 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3220 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3221 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3224 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3225 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3226 */       return true;
/*      */     }
/* 3228 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3234 */     PageContext pageContext = _jspx_page_context;
/* 3235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3237 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3238 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3239 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/* 3240 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3241 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3242 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3243 */         out = _jspx_page_context.pushBody();
/* 3244 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3245 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3248 */         out.write("am.webclient.alertviews.heading.attributes.title");
/* 3249 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3250 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3253 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3254 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3257 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3258 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3259 */       return true;
/*      */     }
/* 3261 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3267 */     PageContext pageContext = _jspx_page_context;
/* 3268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3270 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3271 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3272 */     _jspx_th_fmt_005fmessage_005f11.setParent(null);
/* 3273 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3274 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 3275 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3276 */         out = _jspx_page_context.pushBody();
/* 3277 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 3278 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3281 */         out.write("am.webclient.noc.filter.value.text");
/* 3282 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 3283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3286 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3287 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3290 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3291 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3292 */       return true;
/*      */     }
/* 3294 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3300 */     PageContext pageContext = _jspx_page_context;
/* 3301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3303 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3304 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3305 */     _jspx_th_fmt_005fmessage_005f12.setParent(null);
/* 3306 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3307 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 3308 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3309 */         out = _jspx_page_context.pushBody();
/* 3310 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 3311 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3314 */         out.write("am.webclient.noc.filter.selectcolumn.text");
/* 3315 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 3316 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3319 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3320 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3323 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3324 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3325 */       return true;
/*      */     }
/* 3327 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3328 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3333 */     PageContext pageContext = _jspx_page_context;
/* 3334 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3336 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3337 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3338 */     _jspx_th_fmt_005fmessage_005f13.setParent(null);
/* 3339 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3340 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 3341 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3342 */         out = _jspx_page_context.pushBody();
/* 3343 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 3344 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3347 */         out.write("am.webclient.noc.filter.selectcriteriatype.text");
/* 3348 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 3349 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3352 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3353 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3356 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3357 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3358 */       return true;
/*      */     }
/* 3360 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3366 */     PageContext pageContext = _jspx_page_context;
/* 3367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3369 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3370 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3371 */     _jspx_th_fmt_005fmessage_005f14.setParent(null);
/* 3372 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3373 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 3374 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3375 */         out = _jspx_page_context.pushBody();
/* 3376 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 3377 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3380 */         out.write("am.webclient.alertviews.delete.title");
/* 3381 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 3382 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3385 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3386 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3389 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3390 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3391 */       return true;
/*      */     }
/* 3393 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3399 */     PageContext pageContext = _jspx_page_context;
/* 3400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3402 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3403 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 3404 */     _jspx_th_fmt_005fmessage_005f15.setParent(null);
/* 3405 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 3406 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 3407 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3408 */         out = _jspx_page_context.pushBody();
/* 3409 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 3410 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3413 */         out.write("am.webclient.alertviews.addnew.title");
/* 3414 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 3415 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3418 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3419 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3422 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 3423 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3424 */       return true;
/*      */     }
/* 3426 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3427 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3432 */     PageContext pageContext = _jspx_page_context;
/* 3433 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3435 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3436 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 3437 */     _jspx_th_fmt_005fmessage_005f16.setParent(null);
/* 3438 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 3439 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 3440 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3441 */         out = _jspx_page_context.pushBody();
/* 3442 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 3443 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3446 */         out.write("am.webclient.noc.filter.selectcolumn.text");
/* 3447 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 3448 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3451 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3452 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3455 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 3456 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3457 */       return true;
/*      */     }
/* 3459 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3465 */     PageContext pageContext = _jspx_page_context;
/* 3466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3468 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3469 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 3470 */     _jspx_th_fmt_005fmessage_005f17.setParent(null);
/* 3471 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 3472 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 3473 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3474 */         out = _jspx_page_context.pushBody();
/* 3475 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 3476 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3479 */         out.write("am.webclient.noc.filter.selectcriteriatype.text");
/* 3480 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 3481 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3484 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3485 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3488 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 3489 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3490 */       return true;
/*      */     }
/* 3492 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3493 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3498 */     PageContext pageContext = _jspx_page_context;
/* 3499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3501 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3502 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 3503 */     _jspx_th_fmt_005fmessage_005f18.setParent(null);
/* 3504 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 3505 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 3506 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 3507 */         out = _jspx_page_context.pushBody();
/* 3508 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 3509 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3512 */         out.write("am.webclient.alertviews.delete.title");
/* 3513 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 3514 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3517 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 3518 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3521 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 3522 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3523 */       return true;
/*      */     }
/* 3525 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3531 */     PageContext pageContext = _jspx_page_context;
/* 3532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3534 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3535 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 3536 */     _jspx_th_fmt_005fmessage_005f19.setParent(null);
/* 3537 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 3538 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 3539 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 3540 */         out = _jspx_page_context.pushBody();
/* 3541 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 3542 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3545 */         out.write("am.webclient.alertviews.addnew.title");
/* 3546 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 3547 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3550 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 3551 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3554 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 3555 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3556 */       return true;
/*      */     }
/* 3558 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3559 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3564 */     PageContext pageContext = _jspx_page_context;
/* 3565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3567 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3568 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 3569 */     _jspx_th_fmt_005fmessage_005f20.setParent(null);
/* 3570 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 3571 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 3572 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 3573 */         out = _jspx_page_context.pushBody();
/* 3574 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 3575 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3578 */         out.write("am.webclient.alertviews.button.save.name");
/* 3579 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 3580 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3583 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 3584 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3587 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 3588 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3589 */       return true;
/*      */     }
/* 3591 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3597 */     PageContext pageContext = _jspx_page_context;
/* 3598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3600 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3601 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 3602 */     _jspx_th_fmt_005fmessage_005f21.setParent(null);
/* 3603 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 3604 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 3605 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 3606 */         out = _jspx_page_context.pushBody();
/* 3607 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 3608 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3611 */         out.write("am.webclient.alertviews.button.close.name");
/* 3612 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 3613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3616 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 3617 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3620 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 3621 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3622 */       return true;
/*      */     }
/* 3624 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3625 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NewColumnEntryForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */