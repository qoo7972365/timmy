/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.HAAlertGraph;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.SkipPageException;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.JspSourceDependent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ 
/*      */ public final class TopNValues_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   57 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   60 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   61 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   62 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   69 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   74 */     ArrayList list = null;
/*   75 */     StringBuffer sbf = new StringBuffer();
/*   76 */     ManagedApplication mo = new ManagedApplication();
/*   77 */     if (distinct)
/*      */     {
/*   79 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   83 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   86 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   88 */       ArrayList row = (ArrayList)list.get(i);
/*   89 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   90 */       if (distinct) {
/*   91 */         sbf.append(row.get(0));
/*      */       } else
/*   93 */         sbf.append(row.get(1));
/*   94 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   97 */     return sbf.toString(); }
/*      */   
/*   99 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  102 */     if (severity == null)
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("5"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  110 */     if (severity.equals("1"))
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  117 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  124 */     if (severity == null)
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("1"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("4"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("5"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  143 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  149 */     if (severity == null)
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  153 */     if (severity.equals("5"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  157 */     if (severity.equals("1"))
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  163 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  169 */     if (severity == null)
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  173 */     if (severity.equals("1"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  177 */     if (severity.equals("4"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  181 */     if (severity.equals("5"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  187 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  193 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  199 */     if (severity == 5)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  203 */     if (severity == 1)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  210 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  216 */     if (severity == null)
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  220 */     if (severity.equals("5"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  229 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  231 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  233 */     if (severity.equals("1"))
/*      */     {
/*  235 */       if (isAvailability) {
/*  236 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  239 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  246 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  253 */     if (severity == null)
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("5"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("4"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("1"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  272 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  278 */     if (severity == null)
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("5"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("4"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("1"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  297 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  304 */     if (severity == null)
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  308 */     if (severity.equals("5"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  312 */     if (severity.equals("4"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  316 */     if (severity.equals("1"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  323 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  331 */     StringBuffer out = new StringBuffer();
/*  332 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  333 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  334 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  335 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  336 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  337 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  338 */     out.append("</tr>");
/*  339 */     out.append("</form></table>");
/*  340 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  347 */     if (val == null)
/*      */     {
/*  349 */       return "-";
/*      */     }
/*      */     
/*  352 */     String ret = FormatUtil.formatNumber(val);
/*  353 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  354 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  357 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  361 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  369 */     StringBuffer out = new StringBuffer();
/*  370 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  371 */     out.append("<tr>");
/*  372 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  374 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  376 */     out.append("</tr>");
/*  377 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  381 */       if (j % 2 == 0)
/*      */       {
/*  383 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  387 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  390 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  392 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  395 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  399 */       out.append("</tr>");
/*      */     }
/*  401 */     out.append("</table>");
/*  402 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  405 */     out.append("</tr>");
/*  406 */     out.append("</table>");
/*  407 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  413 */     StringBuffer out = new StringBuffer();
/*  414 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  415 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  420 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  421 */     out.append("</tr>");
/*  422 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  425 */       out.append("<tr>");
/*  426 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  427 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  428 */       out.append("</tr>");
/*      */     }
/*      */     
/*  431 */     out.append("</table>");
/*  432 */     out.append("</table>");
/*  433 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  438 */     if (severity.equals("0"))
/*      */     {
/*  440 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  444 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  451 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  464 */     StringBuffer out = new StringBuffer();
/*  465 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  466 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  468 */       out.append("<tr>");
/*  469 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  470 */       out.append("</tr>");
/*      */       
/*      */ 
/*  473 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  475 */         String borderclass = "";
/*      */         
/*      */ 
/*  478 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  480 */         out.append("<tr>");
/*      */         
/*  482 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  483 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  484 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  490 */     out.append("</table><br>");
/*  491 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  492 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  494 */       List sLinks = secondLevelOfLinks[0];
/*  495 */       List sText = secondLevelOfLinks[1];
/*  496 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  499 */         out.append("<tr>");
/*  500 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  501 */         out.append("</tr>");
/*  502 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  504 */           String borderclass = "";
/*      */           
/*      */ 
/*  507 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  509 */           out.append("<tr>");
/*      */           
/*  511 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  512 */           if (sLinks.get(i).toString().length() == 0) {
/*  513 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  516 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  518 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  522 */     out.append("</table>");
/*  523 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  530 */     StringBuffer out = new StringBuffer();
/*  531 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  532 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  534 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  536 */         out.append("<tr>");
/*  537 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  538 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  542 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  544 */           String borderclass = "";
/*      */           
/*      */ 
/*  547 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  549 */           out.append("<tr>");
/*      */           
/*  551 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  552 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  553 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  556 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  559 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  564 */     out.append("</table><br>");
/*  565 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  566 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  568 */       List sLinks = secondLevelOfLinks[0];
/*  569 */       List sText = secondLevelOfLinks[1];
/*  570 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  573 */         out.append("<tr>");
/*  574 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  575 */         out.append("</tr>");
/*  576 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  578 */           String borderclass = "";
/*      */           
/*      */ 
/*  581 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  583 */           out.append("<tr>");
/*      */           
/*  585 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  586 */           if (sLinks.get(i).toString().length() == 0) {
/*  587 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  590 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  592 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  596 */     out.append("</table>");
/*  597 */     return out.toString();
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
/*  610 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  631 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  639 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  644 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  649 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  654 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  659 */     if (val != null)
/*      */     {
/*  661 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  665 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  670 */     if (val == null) {
/*  671 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  675 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  680 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  686 */     if (val != null)
/*      */     {
/*  688 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  692 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  698 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  707 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  717 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  722 */     String hostaddress = "";
/*  723 */     String ip = request.getHeader("x-forwarded-for");
/*  724 */     if (ip == null)
/*  725 */       ip = request.getRemoteAddr();
/*  726 */     InetAddress add = null;
/*  727 */     if (ip.equals("127.0.0.1")) {
/*  728 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  732 */       add = InetAddress.getByName(ip);
/*      */     }
/*  734 */     hostaddress = add.getHostName();
/*  735 */     if (hostaddress.indexOf('.') != -1) {
/*  736 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  737 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  741 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  746 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  752 */     if (severity == null)
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("5"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  760 */     if (severity.equals("1"))
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  767 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  772 */     ResultSet set = null;
/*  773 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  774 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  776 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  777 */       if (set.next()) { String str1;
/*  778 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  779 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  782 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  787 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  790 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  792 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  796 */     StringBuffer rca = new StringBuffer();
/*  797 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  798 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  801 */     int rcalength = key.length();
/*  802 */     String split = "6. ";
/*  803 */     int splitPresent = key.indexOf(split);
/*  804 */     String div1 = "";String div2 = "";
/*  805 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  807 */       if (rcalength > 180) {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         getRCATrimmedText(key, rca);
/*  810 */         rca.append("</span>");
/*      */       } else {
/*  812 */         rca.append("<span class=\"rca-critical-text\">");
/*  813 */         rca.append(key);
/*  814 */         rca.append("</span>");
/*      */       }
/*  816 */       return rca.toString();
/*      */     }
/*  818 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  819 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  820 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  821 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div1, rca);
/*  823 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  826 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  827 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div2, rca);
/*  829 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  831 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  836 */     String[] st = msg.split("<br>");
/*  837 */     for (int i = 0; i < st.length; i++) {
/*  838 */       String s = st[i];
/*  839 */       if (s.length() > 180) {
/*  840 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  842 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  846 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  847 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  849 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  853 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  854 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  855 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  858 */       if (key == null) {
/*  859 */         return ret;
/*      */       }
/*      */       
/*  862 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  863 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  866 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  867 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  868 */       set = AMConnectionPool.executeQueryStmt(query);
/*  869 */       if (set.next())
/*      */       {
/*  871 */         String helpLink = set.getString("LINK");
/*  872 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  875 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  881 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  900 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  891 */         if (set != null) {
/*  892 */           AMConnectionPool.closeStatement(set);
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
/*  906 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  907 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  909 */       String entityStr = (String)keys.nextElement();
/*  910 */       String mmessage = temp.getProperty(entityStr);
/*  911 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  912 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  914 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  920 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  921 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  923 */       String entityStr = (String)keys.nextElement();
/*  924 */       String mmessage = temp.getProperty(entityStr);
/*  925 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  926 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  928 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  933 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  943 */     String des = new String();
/*  944 */     while (str.indexOf(find) != -1) {
/*  945 */       des = des + str.substring(0, str.indexOf(find));
/*  946 */       des = des + replace;
/*  947 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  949 */     des = des + str;
/*  950 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  957 */       if (alert == null)
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  961 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  963 */         return "&nbsp;";
/*      */       }
/*      */       
/*  966 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  968 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  971 */       int rcalength = test.length();
/*  972 */       if (rcalength < 300)
/*      */       {
/*  974 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  978 */       StringBuffer out = new StringBuffer();
/*  979 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  980 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  981 */       out.append("</div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  984 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  989 */       ex.printStackTrace();
/*      */     }
/*  991 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  997 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1002 */     ArrayList attribIDs = new ArrayList();
/* 1003 */     ArrayList resIDs = new ArrayList();
/* 1004 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1006 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1008 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1010 */       String resourceid = "";
/* 1011 */       String resourceType = "";
/* 1012 */       if (type == 2) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = (String)row.get(3);
/*      */       }
/* 1016 */       else if (type == 3) {
/* 1017 */         resourceid = (String)row.get(0);
/* 1018 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1021 */         resourceid = (String)row.get(6);
/* 1022 */         resourceType = (String)row.get(7);
/*      */       }
/* 1024 */       resIDs.add(resourceid);
/* 1025 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1026 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1028 */       String healthentity = null;
/* 1029 */       String availentity = null;
/* 1030 */       if (healthid != null) {
/* 1031 */         healthentity = resourceid + "_" + healthid;
/* 1032 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1035 */       if (availid != null) {
/* 1036 */         availentity = resourceid + "_" + availid;
/* 1037 */         entitylist.add(availentity);
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
/* 1051 */     Properties alert = getStatus(entitylist);
/* 1052 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1057 */     int size = monitorList.size();
/*      */     
/* 1059 */     String[] severity = new String[size];
/*      */     
/* 1061 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1063 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1064 */       String resourceName1 = (String)row1.get(7);
/* 1065 */       String resourceid1 = (String)row1.get(6);
/* 1066 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1067 */       if (severity[j] == null)
/*      */       {
/* 1069 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1073 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1075 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1077 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1080 */         if (sev > 0) {
/* 1081 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1082 */           monitorList.set(k, monitorList.get(j));
/* 1083 */           monitorList.set(j, t);
/* 1084 */           String temp = severity[k];
/* 1085 */           severity[k] = severity[j];
/* 1086 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1092 */     int z = 0;
/* 1093 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1096 */       int i = 0;
/* 1097 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1100 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1104 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1108 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1110 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1113 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1117 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1120 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1121 */       String resourceName1 = (String)row1.get(7);
/* 1122 */       String resourceid1 = (String)row1.get(6);
/* 1123 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1124 */       if (hseverity[j] == null)
/*      */       {
/* 1126 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1131 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1133 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1136 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1139 */         if (hsev > 0) {
/* 1140 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1141 */           monitorList.set(k, monitorList.get(j));
/* 1142 */           monitorList.set(j, t);
/* 1143 */           String temp1 = hseverity[k];
/* 1144 */           hseverity[k] = hseverity[j];
/* 1145 */           hseverity[j] = temp1;
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
/* 1157 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1158 */     boolean forInventory = false;
/* 1159 */     String trdisplay = "none";
/* 1160 */     String plusstyle = "inline";
/* 1161 */     String minusstyle = "none";
/* 1162 */     String haidTopLevel = "";
/* 1163 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1165 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1167 */         haidTopLevel = request.getParameter("haid");
/* 1168 */         forInventory = true;
/* 1169 */         trdisplay = "table-row;";
/* 1170 */         plusstyle = "none";
/* 1171 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1178 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1181 */     ArrayList listtoreturn = new ArrayList();
/* 1182 */     StringBuffer toreturn = new StringBuffer();
/* 1183 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1184 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1185 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1187 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1189 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1190 */       String childresid = (String)singlerow.get(0);
/* 1191 */       String childresname = (String)singlerow.get(1);
/* 1192 */       childresname = ExtProdUtil.decodeString(childresname);
/* 1193 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1194 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1195 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1196 */       String unmanagestatus = (String)singlerow.get(5);
/* 1197 */       String actionstatus = (String)singlerow.get(6);
/* 1198 */       String linkclass = "monitorgp-links";
/* 1199 */       String titleforres = childresname;
/* 1200 */       String titilechildresname = childresname;
/* 1201 */       String childimg = "/images/trcont.png";
/* 1202 */       String flag = "enable";
/* 1203 */       String dcstarted = (String)singlerow.get(8);
/* 1204 */       String configMonitor = "";
/* 1205 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1206 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1208 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1210 */       if (singlerow.get(7) != null)
/*      */       {
/* 1212 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1214 */       String haiGroupType = "0";
/* 1215 */       if ("HAI".equals(childtype))
/*      */       {
/* 1217 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1219 */       childimg = "/images/trend.png";
/* 1220 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1221 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1222 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1224 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1226 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1228 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1229 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1232 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1234 */         linkclass = "disabledtext";
/* 1235 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1237 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String availmouseover = "";
/* 1239 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1241 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1243 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1244 */       String healthmouseover = "";
/* 1245 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1247 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1250 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1251 */       int spacing = 0;
/* 1252 */       if (level >= 1)
/*      */       {
/* 1254 */         spacing = 40 * level;
/*      */       }
/* 1256 */       if (childtype.equals("HAI"))
/*      */       {
/* 1258 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1259 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1260 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1262 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1263 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1264 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1265 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1266 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1267 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1268 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1269 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1270 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1271 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1272 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1274 */         if (!forInventory)
/*      */         {
/* 1276 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1279 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = editlink + actions;
/*      */         }
/* 1285 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1287 */           actions = actions + associatelink;
/*      */         }
/* 1289 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1290 */         String arrowimg = "";
/* 1291 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/* 1296 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1298 */         if (isIt360)
/*      */         {
/* 1300 */           actionimg = "";
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1308 */           actions = "";
/*      */         }
/* 1310 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1312 */           checkbox = "";
/*      */         }
/*      */         
/* 1315 */         String resourcelink = "";
/*      */         
/* 1317 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1326 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1332 */         if (!isIt360)
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1341 */         toreturn.append("</tr>");
/* 1342 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1344 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1345 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1350 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1353 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1357 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1359 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1361 */             toreturn.append(assocMessage);
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1371 */         String resourcelink = null;
/* 1372 */         boolean hideEditLink = false;
/* 1373 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1375 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1376 */           hideEditLink = true;
/* 1377 */           if (isIt360)
/*      */           {
/* 1379 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1383 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1385 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1387 */           hideEditLink = true;
/* 1388 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1389 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1394 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1397 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1398 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1399 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1400 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1401 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1402 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1403 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1404 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1405 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1406 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1407 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1408 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1409 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1411 */         if (hideEditLink)
/*      */         {
/* 1413 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1415 */         if (!forInventory)
/*      */         {
/* 1417 */           removefromgroup = "";
/*      */         }
/* 1419 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1420 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1421 */           actions = actions + configcustomfields;
/*      */         }
/* 1423 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1425 */           actions = editlink + actions;
/*      */         }
/* 1427 */         String managedLink = "";
/* 1428 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1430 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1431 */           actions = "";
/* 1432 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1433 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1436 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1438 */           checkbox = "";
/*      */         }
/*      */         
/* 1441 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1443 */           actions = "";
/*      */         }
/* 1445 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1448 */         if (isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1458 */         if (!isIt360)
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1464 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1466 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1469 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1476 */       StringBuilder toreturn = new StringBuilder();
/* 1477 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1478 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1479 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1480 */       String title = "";
/* 1481 */       message = EnterpriseUtil.decodeString(message);
/* 1482 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1483 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1484 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1486 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1488 */       else if ("5".equals(severity))
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1494 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1496 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1497 */       toreturn.append(v);
/*      */       
/* 1499 */       toreturn.append(link);
/* 1500 */       if (severity == null)
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("5"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("4"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("1"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1521 */       toreturn.append("</a>");
/* 1522 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1526 */       ex.printStackTrace();
/*      */     }
/* 1528 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1535 */       StringBuilder toreturn = new StringBuilder();
/* 1536 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1537 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1538 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1539 */       if (message == null)
/*      */       {
/* 1541 */         message = "";
/*      */       }
/*      */       
/* 1544 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1545 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1547 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1548 */       toreturn.append(v);
/*      */       
/* 1550 */       toreturn.append(link);
/*      */       
/* 1552 */       if (severity == null)
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("5"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       else if (severity.equals("1"))
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1569 */       toreturn.append("</a>");
/* 1570 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1576 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1579 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1580 */     if (invokeActions != null) {
/* 1581 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1582 */       while (iterator.hasNext()) {
/* 1583 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1584 */         if (actionmap.containsKey(actionid)) {
/* 1585 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1590 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1594 */     String actionLink = "";
/* 1595 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1596 */     String query = "";
/* 1597 */     ResultSet rs = null;
/* 1598 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1599 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1600 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1601 */       actionLink = "method=" + methodName;
/*      */     }
/* 1603 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */       actionLink = methodName;
/*      */     }
/* 1606 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1607 */     Iterator itr = methodarglist.iterator();
/* 1608 */     boolean isfirstparam = true;
/* 1609 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1610 */     while (itr.hasNext()) {
/* 1611 */       HashMap argmap = (HashMap)itr.next();
/* 1612 */       String argtype = (String)argmap.get("TYPE");
/* 1613 */       String argname = (String)argmap.get("IDENTITY");
/* 1614 */       String paramname = (String)argmap.get("PARAMETER");
/* 1615 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1616 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1617 */         isfirstparam = false;
/* 1618 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1620 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1624 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1628 */         actionLink = actionLink + "&";
/*      */       }
/* 1630 */       String paramValue = null;
/* 1631 */       String tempargname = argname;
/* 1632 */       if (commonValues.getProperty(tempargname) != null) {
/* 1633 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1636 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1637 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1638 */           if (dbType.equals("mysql")) {
/* 1639 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1642 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1644 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1646 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1647 */             if (rs.next()) {
/* 1648 */               paramValue = rs.getString("VALUE");
/* 1649 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1653 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1657 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1660 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1665 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1666 */           paramValue = rowId;
/*      */         }
/* 1668 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1669 */           paramValue = managedObjectName;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1672 */           paramValue = resID;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1675 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1678 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1680 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1681 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1682 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1684 */     return actionLink;
/*      */   }
/*      */   
/* 1687 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1688 */     String dependentAttribute = null;
/* 1689 */     String align = "left";
/*      */     
/* 1691 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1692 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1693 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1694 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1695 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1696 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1697 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1698 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1699 */       align = "center";
/*      */     }
/*      */     
/* 1702 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1703 */     String actualdata = "";
/*      */     
/* 1705 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1706 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1707 */         actualdata = availValue;
/*      */       }
/* 1709 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1710 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1714 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1715 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1718 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1724 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1725 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1726 */       toreturn.append("<table>");
/* 1727 */       toreturn.append("<tr>");
/* 1728 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1729 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1730 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1731 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1732 */         String toolTip = "";
/* 1733 */         String hideClass = "";
/* 1734 */         String textStyle = "";
/* 1735 */         boolean isreferenced = true;
/* 1736 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1737 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1738 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1739 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1741 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1742 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1743 */           while (valueList.hasMoreTokens()) {
/* 1744 */             String dependentVal = valueList.nextToken();
/* 1745 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1746 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1747 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1749 */               toolTip = "";
/* 1750 */               hideClass = "";
/* 1751 */               isreferenced = false;
/* 1752 */               textStyle = "disabledtext";
/* 1753 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1757 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1758 */           toolTip = "";
/* 1759 */           hideClass = "";
/* 1760 */           isreferenced = false;
/* 1761 */           textStyle = "disabledtext";
/* 1762 */           if (dependentImageMap != null) {
/* 1763 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1764 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1767 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1771 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1772 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1773 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1774 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1775 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1776 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1778 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1779 */           if (isreferenced) {
/* 1780 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1784 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1785 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1786 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1787 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1788 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1789 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1791 */           toreturn.append("</span>");
/* 1792 */           toreturn.append("</a>");
/* 1793 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1796 */       toreturn.append("</tr>");
/* 1797 */       toreturn.append("</table>");
/* 1798 */       toreturn.append("</td>");
/*      */     } else {
/* 1800 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1803 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1807 */     String colTime = null;
/* 1808 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1809 */     if ((rows != null) && (rows.size() > 0)) {
/* 1810 */       Iterator<String> itr = rows.iterator();
/* 1811 */       String maxColQuery = "";
/* 1812 */       for (;;) { if (itr.hasNext()) {
/* 1813 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1814 */           ResultSet maxCol = null;
/*      */           try {
/* 1816 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1817 */             while (maxCol.next()) {
/* 1818 */               if (colTime == null) {
/* 1819 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1822 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1831 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1841 */     return colTime;
/*      */   }
/*      */   
/* 1844 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1845 */     tablename = null;
/* 1846 */     ResultSet rsTable = null;
/* 1847 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1849 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1850 */       while (rsTable.next()) {
/* 1851 */         tablename = rsTable.getString("DATATABLE");
/* 1852 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1853 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1866 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1857 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1860 */         if (rsTable != null)
/* 1861 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1863 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1869 */     String argsList = "";
/* 1870 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1872 */       if (showArgsMap.get(row) != null) {
/* 1873 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1874 */         if (showArgslist != null) {
/* 1875 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1876 */             if (argsList.trim().equals("")) {
/* 1877 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1880 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1887 */       e.printStackTrace();
/* 1888 */       return "";
/*      */     }
/* 1890 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1895 */     String argsList = "";
/* 1896 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1899 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1901 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1902 */         if (hideArgsList != null)
/*      */         {
/* 1904 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1906 */             if (argsList.trim().equals(""))
/*      */             {
/* 1908 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1912 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1920 */       ex.printStackTrace();
/*      */     }
/* 1922 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1926 */     StringBuilder toreturn = new StringBuilder();
/* 1927 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1934 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1935 */       Iterator itr = tActionList.iterator();
/* 1936 */       while (itr.hasNext()) {
/* 1937 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1938 */         String confirmmsg = "";
/* 1939 */         String link = "";
/* 1940 */         String isJSP = "NO";
/* 1941 */         HashMap tactionMap = (HashMap)itr.next();
/* 1942 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1943 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1944 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1945 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1946 */           (actionmap.containsKey(actionId))) {
/* 1947 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1948 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1949 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1950 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1951 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1953 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1959 */           if (isTableAction) {
/* 1960 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1963 */             tableName = "Link";
/* 1964 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1965 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1966 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1967 */             toreturn.append("</a></td>");
/*      */           }
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1978 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1984 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1986 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1987 */       Properties prop = (Properties)node.getUserObject();
/* 1988 */       String mgID = prop.getProperty("label");
/* 1989 */       String mgName = prop.getProperty("value");
/* 1990 */       String isParent = prop.getProperty("isParent");
/* 1991 */       int mgIDint = Integer.parseInt(mgID);
/* 1992 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1994 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1996 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1997 */       if (node.getChildCount() > 0)
/*      */       {
/* 1999 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2001 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2003 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2009 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2016 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2018 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2027 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2028 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2030 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2034 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2036 */       if (node.getChildCount() > 0)
/*      */       {
/* 2038 */         builder.append("<UL>");
/* 2039 */         printMGTree(node, builder);
/* 2040 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2045 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2046 */     StringBuffer toReturn = new StringBuffer();
/* 2047 */     String table = "-";
/*      */     try {
/* 2049 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2050 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2051 */       float total = 0.0F;
/* 2052 */       while (it.hasNext()) {
/* 2053 */         String attName = (String)it.next();
/* 2054 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2055 */         boolean roundOffData = false;
/* 2056 */         if ((data != null) && (!data.equals(""))) {
/* 2057 */           if (data.indexOf(",") != -1) {
/* 2058 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2061 */             float value = Float.parseFloat(data);
/* 2062 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2065 */             total += value;
/* 2066 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2069 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2074 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2075 */       while (attVsWidthList.hasNext()) {
/* 2076 */         String attName = (String)attVsWidthList.next();
/* 2077 */         String data = (String)attVsWidthProps.get(attName);
/* 2078 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2079 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2080 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2081 */         String className = (String)graphDetails.get("ClassName");
/* 2082 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2083 */         if (percentage < 1.0F)
/*      */         {
/* 2085 */           data = percentage + "";
/*      */         }
/* 2087 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2089 */       if (toReturn.length() > 0) {
/* 2090 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2094 */       e.printStackTrace();
/*      */     }
/* 2096 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2102 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2103 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2104 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2105 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2106 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2107 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2108 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2109 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2110 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2113 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2114 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2115 */       splitvalues[0] = multiplecondition.toString();
/* 2116 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2119 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2124 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2125 */     if (thresholdType != 3) {
/* 2126 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2127 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2128 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2129 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2130 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2131 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2133 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2134 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2135 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2136 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2137 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2138 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2140 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2141 */     if (updateSelected != null) {
/* 2142 */       updateSelected[0] = "selected";
/*      */     }
/* 2144 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2149 */       StringBuffer toreturn = new StringBuffer("");
/* 2150 */       if (commaSeparatedMsgId != null) {
/* 2151 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2152 */         int count = 0;
/* 2153 */         while (msgids.hasMoreTokens()) {
/* 2154 */           String id = msgids.nextToken();
/* 2155 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2156 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2157 */           count++;
/* 2158 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2159 */             if (toreturn.length() == 0) {
/* 2160 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2162 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2163 */             if (!image.trim().equals("")) {
/* 2164 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2166 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2167 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2170 */         if (toreturn.length() > 0) {
/* 2171 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2175 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2178 */       e.printStackTrace(); }
/* 2179 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2185 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2191 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2192 */   static { _jspx_dependants.put("/jsp/includes/applications_init.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2202 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2206 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2208 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2212 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2219 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2222 */     JspWriter out = null;
/* 2223 */     Object page = this;
/* 2224 */     JspWriter _jspx_out = null;
/* 2225 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2229 */       response.setContentType("text/html;charset=UTF-8");
/* 2230 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2232 */       _jspx_page_context = pageContext;
/* 2233 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2234 */       ServletConfig config = pageContext.getServletConfig();
/* 2235 */       session = pageContext.getSession();
/* 2236 */       out = pageContext.getOut();
/* 2237 */       _jspx_out = out;
/*      */       
/* 2239 */       out.write(10);
/* 2240 */       out.write(10);
/* 2241 */       out.write("<!--$Id$-->\n\n");
/* 2242 */       Hashtable availabilitykeys = null;
/* 2243 */       synchronized (application) {
/* 2244 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2245 */         if (availabilitykeys == null) {
/* 2246 */           availabilitykeys = new Hashtable();
/* 2247 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2250 */       out.write(10);
/* 2251 */       Hashtable healthkeys = null;
/* 2252 */       synchronized (application) {
/* 2253 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2254 */         if (healthkeys == null) {
/* 2255 */           healthkeys = new Hashtable();
/* 2256 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2259 */       out.write(10);
/*      */       
/* 2261 */       request.setAttribute("HelpKey", "Home Page");
/*      */       
/*      */ 
/* 2264 */       out.write("\n\n\n\n\n\n\n\n\n  \n\n\n\n\n\n\n\n");
/* 2265 */       ManagedApplication mo = null;
/* 2266 */       synchronized (application) {
/* 2267 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2268 */         if (mo == null) {
/* 2269 */           mo = new ManagedApplication();
/* 2270 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2273 */       out.write(10);
/* 2274 */       HAAlertGraph graph = null;
/* 2275 */       graph = (HAAlertGraph)_jspx_page_context.getAttribute("graph", 1);
/* 2276 */       if (graph == null) {
/* 2277 */         graph = new HAAlertGraph();
/* 2278 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/* 2280 */       out.write(10);
/* 2281 */       out.write(10);
/* 2282 */       out.write(10);
/* 2283 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2285 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2296 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2297 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2298 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2301 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2302 */         String available = null;
/* 2303 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2304 */         out.write(10);
/*      */         
/* 2306 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2317 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2318 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2319 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2322 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2323 */           String unavailable = null;
/* 2324 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2325 */           out.write(10);
/*      */           
/* 2327 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2338 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2339 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2340 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2343 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2344 */             String unmanaged = null;
/* 2345 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2346 */             out.write(10);
/*      */             
/* 2348 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2359 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2360 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2361 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2364 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2365 */               String scheduled = null;
/* 2366 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2367 */               out.write(10);
/*      */               
/* 2369 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2380 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2381 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2382 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2385 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2386 */                 String critical = null;
/* 2387 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2388 */                 out.write(10);
/*      */                 
/* 2390 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2401 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2402 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2403 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2406 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2407 */                   String clear = null;
/* 2408 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2409 */                   out.write(10);
/*      */                   
/* 2411 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2422 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2423 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2424 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2427 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2428 */                     String warning = null;
/* 2429 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2430 */                     out.write(10);
/* 2431 */                     out.write(10);
/*      */                     
/* 2433 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2434 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/* 2438 */                     out.write(10);
/* 2439 */                     out.write(10);
/*      */                     
/* 2441 */                     ArrayList attribIDs = new ArrayList(2);
/* 2442 */                     attribIDs.add("17");
/* 2443 */                     attribIDs.add("18");
/*      */                     
/* 2445 */                     attribIDs.add("7000");
/* 2446 */                     attribIDs.add("7001");
/* 2447 */                     attribIDs.add("7010");
/* 2448 */                     attribIDs.add("7011");
/* 2449 */                     attribIDs.add("7020");
/* 2450 */                     attribIDs.add("7021");
/* 2451 */                     attribIDs.add("7030");
/* 2452 */                     attribIDs.add("7031");
/* 2453 */                     attribIDs.add("7040");
/* 2454 */                     attribIDs.add("7041");
/* 2455 */                     attribIDs.add("7060");
/* 2456 */                     attribIDs.add("7061");
/* 2457 */                     attribIDs.add("7050");
/* 2458 */                     attribIDs.add("7051");
/* 2459 */                     attribIDs.add("3201");
/* 2460 */                     attribIDs.add("3202");
/* 2461 */                     attribIDs.add("7070");
/* 2462 */                     attribIDs.add("7071");
/* 2463 */                     attribIDs.add(String.valueOf(700));
/* 2464 */                     attribIDs.add(String.valueOf(701));
/* 2465 */                     attribIDs.add(String.valueOf(800));
/* 2466 */                     attribIDs.add(String.valueOf(801));
/* 2467 */                     attribIDs.add(String.valueOf(1000));
/* 2468 */                     attribIDs.add(String.valueOf(1001));
/* 2469 */                     attribIDs.add(String.valueOf(1100));
/* 2470 */                     attribIDs.add(String.valueOf(1101));
/* 2471 */                     attribIDs.add(String.valueOf(1200));
/* 2472 */                     attribIDs.add(String.valueOf(1201));
/* 2473 */                     attribIDs.add(String.valueOf(1300));
/* 2474 */                     attribIDs.add(String.valueOf(1301));
/* 2475 */                     attribIDs.add(String.valueOf(1650));
/* 2476 */                     attribIDs.add(String.valueOf(1651));
/* 2477 */                     attribIDs.add(String.valueOf(1350));
/* 2478 */                     attribIDs.add(String.valueOf(1351));
/* 2479 */                     attribIDs.add(String.valueOf(1450));
/* 2480 */                     attribIDs.add(String.valueOf(1451));
/* 2481 */                     attribIDs.add(String.valueOf(1950));
/* 2482 */                     attribIDs.add(String.valueOf(1951));
/* 2483 */                     attribIDs.add(String.valueOf(1930));
/* 2484 */                     attribIDs.add(String.valueOf(1931));
/* 2485 */                     attribIDs.add(String.valueOf(1470));
/* 2486 */                     attribIDs.add(String.valueOf(1471));
/*      */                     
/* 2488 */                     attribIDs.add(String.valueOf(1370));
/* 2489 */                     attribIDs.add(String.valueOf(1371));
/* 2490 */                     attribIDs.add(String.valueOf(1378));
/* 2491 */                     attribIDs.add(String.valueOf(1390));
/* 2492 */                     attribIDs.add(String.valueOf(1391));
/* 2493 */                     attribIDs.add(String.valueOf(1395));
/*      */                     
/* 2495 */                     attribIDs.add(String.valueOf(3700));
/* 2496 */                     attribIDs.add(String.valueOf(3701));
/* 2497 */                     attribIDs.add(String.valueOf(7052));
/* 2498 */                     attribIDs.add(String.valueOf(7053));
/*      */                     
/* 2500 */                     ArrayList resIDs = new ArrayList();
/* 2501 */                     ArrayList dashboardentity = new ArrayList();
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2506 */                     ArrayList resourceTypeIDs = new ArrayList();
/* 2507 */                     StringBuffer queryBuff = new StringBuffer();
/*      */                     
/* 2509 */                     ResultSet rs = null;
/* 2510 */                     AMConnectionPool dbConnection = AMConnectionPool.getInstance();
/* 2511 */                     for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                     {
/* 2513 */                       queryBuff.delete(0, queryBuff.length());
/* 2514 */                       queryBuff.append("select AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject join AM_ATTRIBUTES on AM_ManagedObject.TYPE= AM_ATTRIBUTES.RESOURCETYPE where AM_ATTRIBUTES.RESOURCETYPE='");
/* 2515 */                       queryBuff.append(com.adventnet.appmanager.util.Constants.categoryLink[i]);
/* 2516 */                       queryBuff.append("'");
/* 2517 */                       queryBuff.append(" order by ATTRIBUTEID");
/* 2518 */                       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 2519 */                       if (rs != null)
/*      */                       {
/* 2521 */                         if (rs.next())
/*      */                         {
/* 2523 */                           String resTypeID = rs.getString(1);
/* 2524 */                           resIDs.add("" + resTypeID);
/* 2525 */                           dashboardentity.add(resTypeID + "_" + rs.getString(2));
/*      */                           
/* 2527 */                           resourceTypeIDs.add(com.adventnet.appmanager.util.Constants.categoryLink[i]);
/* 2528 */                           resourceTypeIDs.add(resTypeID);
/* 2529 */                           resourceTypeIDs.add(rs.getString(2));
/*      */                           
/* 2531 */                           rs.next();
/* 2532 */                           dashboardentity.add(resTypeID + "_" + rs.getString(2));
/*      */                           
/* 2534 */                           resourceTypeIDs.add(rs.getString(2));
/*      */                         } }
/*      */                     }
/* 2537 */                     if (rs != null)
/*      */                     {
/* 2539 */                       AMConnectionPool.closeStatement(rs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2590 */                     String cachehealth = null;
/* 2591 */                     String cacheavail = null;
/*      */                     
/*      */ 
/* 2594 */                     String categorytype1 = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 2595 */                     pageContext.setAttribute("categorytype1", categorytype1);
/*      */                     
/* 2597 */                     out.write("\n\n\n\n");
/* 2598 */                     out.write("\n\n\n\n\n\n\n");
/*      */                     
/* 2600 */                     String haid = request.getParameter("mgId");
/* 2601 */                     String attributeID = request.getParameter("attributeId");
/* 2602 */                     String startTime = request.getParameter("startTime");
/* 2603 */                     String endTime = request.getParameter("endTime");
/* 2604 */                     String type = request.getParameter("type");
/* 2605 */                     String chartType = request.getParameter("chartType");
/* 2606 */                     Hashtable output = ReportUtil.getTopNValues(attributeID, haid, type);
/*      */                     
/*      */ 
/* 2609 */                     int outPutSize = output.size();
/* 2610 */                     if (outPutSize > 5)
/*      */                     {
/* 2612 */                       outPutSize = 5;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2617 */                     out.write(10);
/* 2618 */                     out.write(10);
/* 2619 */                     if (chartType.equals("Helical"))
/*      */                     {
/* 2621 */                       out.println("<table border=0 borderspacing=0 align=left cellpadding=0 cellspacing=10 width='100%'><tr>");
/* 2622 */                       if (outPutSize > 0)
/*      */                       {
/* 2624 */                         for (int i = 0; i < outPutSize; i++)
/*      */                         {
/* 2626 */                           ArrayList tempList = (ArrayList)output.get(Integer.valueOf(i + 1));
/* 2627 */                           Integer maxValue = new Integer(Integer.parseInt(tempList.get(4).toString()));
/*      */                           
/* 2629 */                           out.write("\n\n\t<td width=\"20%\" bgcolor='#F5F5F5'>\n\t\t<table CELLSPACING=\"5\" background=\"black\" cellpadding=\"5\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"40%\">\n\t\t\t\t\t<table>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t\t\t");
/* 2630 */                           JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(maxValue), request.getCharacterEncoding()), out, true);
/* 2631 */                           out.write(" \n\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\n\n\t\t\t\t<td valign=\"bottom\" align=\"left\" width=\"60%\">\n\t\t\t\t\t<table>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2632 */                           out.print(FormatUtil.getString("am.webclient.wli.min.text"));
/* 2633 */                           out.write("</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">:</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2634 */                           if (((String)tempList.get(3)).equals("-1")) {
/* 2635 */                             out.write(78);
/* 2636 */                             out.write(65);
/* 2637 */                             out.write(32);
/*      */                           } else {
/* 2639 */                             out.write(32);
/* 2640 */                             out.print(tempList.get(3));
/* 2641 */                             out.write(32);
/*      */                           }
/* 2643 */                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2644 */                           out.print(FormatUtil.getString("am.mobile.maximum.txt"));
/* 2645 */                           out.write("</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">:</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2646 */                           if (((String)tempList.get(2)).equals("-1")) {
/* 2647 */                             out.write(78);
/* 2648 */                             out.write(65);
/* 2649 */                             out.write(32);
/*      */                           } else {
/* 2651 */                             out.write(32);
/* 2652 */                             out.print(tempList.get(2));
/* 2653 */                             out.write(32);
/*      */                           }
/* 2655 */                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2656 */                           out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 2657 */                           out.write("</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">:</td>\n\t\t\t\t\t\t\t<td class=\"bodytext\">");
/* 2658 */                           out.print(tempList.get(4));
/* 2659 */                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t");
/*      */                           
/* 2661 */                           String resID = (String)tempList.get(0);
/* 2662 */                           String devType = DBUtil.getTypeName(Integer.parseInt(resID));
/* 2663 */                           String resUrl = "/showresource.do?resourceid=" + resID + "&method=showResourceForResourceID";
/* 2664 */                           if ((com.adventnet.appmanager.util.Constants.isIt360) && (devType != null) && (devType.equals("HAI")))
/*      */                           {
/* 2666 */                             resUrl = "/showapplication.do?method=showApplication&haid=" + resID;
/*      */                           }
/*      */                           
/* 2669 */                           out.write("\n\t\t\t\t\t\t\t<td colspan=\"3\" title=\"");
/* 2670 */                           out.print(tempList.get(1));
/* 2671 */                           out.write("\" align='center'> <a href=\"");
/* 2672 */                           out.print(resUrl);
/* 2673 */                           out.write("\" class=\"staticlinks\">");
/* 2674 */                           out.print(FormatUtil.getTrimmedText(tempList.get(1).toString(), 20));
/* 2675 */                           out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t");
/*      */                         }
/*      */                         
/* 2678 */                         if (outPutSize < 5)
/*      */                         {
/* 2680 */                           for (int i = outPutSize; i < 5; i++)
/*      */                           {
/*      */ 
/* 2683 */                             out.write(" <td width=\"20%\" bgcolor='#F5F5F5' align='center'><img src='/images/nodata.gif'> </td> ");
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/* 2691 */                         out.write("\n<td class=\"emptyTableMsg\">");
/* 2692 */                         out.print(FormatUtil.getString("No_Data_Available"));
/* 2693 */                         out.write("</td>\n");
/*      */                       }
/* 2695 */                       out.println("</tr></table>");
/*      */                     }
/*      */                     else {
/* 2698 */                       out.write("\n\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" >\n\t\t<tr>\n\t\t\t<td class=\"columnheading\">");
/* 2699 */                       out.print(FormatUtil.getString("Name"));
/* 2700 */                       out.write("</td>\n\t\t\t<td align='center' class=\"columnheading\">");
/* 2701 */                       out.print(FormatUtil.getString("am.webclient.wli.min.text"));
/* 2702 */                       out.write("</td>\n\t\t\t<td align='center' class=\"columnheading\">");
/* 2703 */                       out.print(FormatUtil.getString("am.mobile.maximum.txt"));
/* 2704 */                       out.write("</td>\n\t\t\t<td align='center' class=\"columnheading\">");
/* 2705 */                       out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 2706 */                       out.write("</td>\n\t\t\t\n\t\t</tr>\n\t\t\t\n");
/*      */                       
/* 2708 */                       if (outPutSize > 0)
/*      */                       {
/* 2710 */                         for (int i = 0; i < outPutSize; i++)
/*      */                         {
/* 2712 */                           ArrayList tempList = (ArrayList)output.get(Integer.valueOf(i + 1));
/* 2713 */                           String bgclass = "whitegrayborder";
/* 2714 */                           if (i % 2 != 0)
/*      */                           {
/* 2716 */                             bgclass = "yellowgrayborder";
/*      */                           }
/*      */                           
/* 2719 */                           String resID = (String)tempList.get(0);
/* 2720 */                           String devType = DBUtil.getTypeName(Integer.parseInt(resID));
/* 2721 */                           String resUrl = "/showresource.do?resourceid=" + resID + "&method=showResourceForResourceID";
/* 2722 */                           if ((com.adventnet.appmanager.util.Constants.isIt360) && (devType != null) && (devType.equals("HAI")))
/*      */                           {
/* 2724 */                             resUrl = "/showapplication.do?method=showApplication&haid=" + resID;
/*      */                           }
/*      */                           
/* 2727 */                           out.write("\n\t\t<tr>\n\t\t\t<td class=\"");
/* 2728 */                           out.print(bgclass);
/* 2729 */                           out.write("\" title=\"");
/* 2730 */                           out.print(tempList.get(1));
/* 2731 */                           out.write("\" > <a href=\"");
/* 2732 */                           out.print(resUrl);
/* 2733 */                           out.write("\" class=\"staticlinks\">");
/* 2734 */                           out.print(FormatUtil.getTrimmedText(tempList.get(1).toString(), 25));
/* 2735 */                           out.write("</a></td>\n\t\t\t<td class=\"");
/* 2736 */                           out.print(bgclass);
/* 2737 */                           out.write("\" align='center'> ");
/* 2738 */                           out.print(tempList.get(3));
/* 2739 */                           out.write(" </td>\n\t\t\t<td class=\"");
/* 2740 */                           out.print(bgclass);
/* 2741 */                           out.write("\" align='center'> ");
/* 2742 */                           out.print(tempList.get(2));
/* 2743 */                           out.write(" </td>\n\t\t\t<td class=\"");
/* 2744 */                           out.print(bgclass);
/* 2745 */                           out.write("\" align='center'> ");
/* 2746 */                           out.print(tempList.get(4));
/* 2747 */                           out.write(" </td>\n\t\t</tr>\n\t");
/*      */                         }
/*      */                       }
/*      */                       else
/*      */                       {
/* 2752 */                         out.write("\n\t<tr class=\"emptyTableMsg\"><td colspan=\"4\">");
/* 2753 */                         out.print(FormatUtil.getString("No_Data_Available"));
/* 2754 */                         out.write("</td></tr> ");
/*      */                       }
/* 2756 */                       out.write("\n</table>\n");
/*      */                     }
/* 2758 */                     out.write(10);
/*      */                   }
/* 2760 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof SkipPageException)) {
/* 2761 */         out = _jspx_out;
/* 2762 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2763 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2764 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2767 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TopNValues_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */