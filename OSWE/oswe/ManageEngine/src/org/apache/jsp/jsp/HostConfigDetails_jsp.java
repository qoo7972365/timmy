/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.hostresources.util.HostConfInfoGeneric;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
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
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ 
/*      */ public final class HostConfigDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   48 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   51 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   52 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   53 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   60 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   65 */     ArrayList list = null;
/*   66 */     StringBuffer sbf = new StringBuffer();
/*   67 */     ManagedApplication mo = new ManagedApplication();
/*   68 */     if (distinct)
/*      */     {
/*   70 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   74 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   77 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   79 */       ArrayList row = (ArrayList)list.get(i);
/*   80 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   81 */       if (distinct) {
/*   82 */         sbf.append(row.get(0));
/*      */       } else
/*   84 */         sbf.append(row.get(1));
/*   85 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   88 */     return sbf.toString(); }
/*      */   
/*   90 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   93 */     if (severity == null)
/*      */     {
/*   95 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   97 */     if (severity.equals("5"))
/*      */     {
/*   99 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  101 */     if (severity.equals("1"))
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  108 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  115 */     if (severity == null)
/*      */     {
/*  117 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  119 */     if (severity.equals("1"))
/*      */     {
/*  121 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  123 */     if (severity.equals("4"))
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("5"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  134 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  140 */     if (severity == null)
/*      */     {
/*  142 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  144 */     if (severity.equals("5"))
/*      */     {
/*  146 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  148 */     if (severity.equals("1"))
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  154 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  160 */     if (severity == null)
/*      */     {
/*  162 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  164 */     if (severity.equals("1"))
/*      */     {
/*  166 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  168 */     if (severity.equals("4"))
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  172 */     if (severity.equals("5"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  178 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  184 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  190 */     if (severity == 5)
/*      */     {
/*  192 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  194 */     if (severity == 1)
/*      */     {
/*  196 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  201 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  207 */     if (severity == null)
/*      */     {
/*  209 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  211 */     if (severity.equals("5"))
/*      */     {
/*  213 */       if (isAvailability) {
/*  214 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  217 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  220 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  222 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  224 */     if (severity.equals("1"))
/*      */     {
/*  226 */       if (isAvailability) {
/*  227 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  230 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  237 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  244 */     if (severity == null)
/*      */     {
/*  246 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  248 */     if (severity.equals("5"))
/*      */     {
/*  250 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  252 */     if (severity.equals("4"))
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("1"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  263 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  269 */     if (severity == null)
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("5"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("4"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("1"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  288 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  295 */     if (severity == null)
/*      */     {
/*  297 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  299 */     if (severity.equals("5"))
/*      */     {
/*  301 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  303 */     if (severity.equals("4"))
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  307 */     if (severity.equals("1"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  314 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  322 */     StringBuffer out = new StringBuffer();
/*  323 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  324 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  325 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  326 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  327 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  328 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  329 */     out.append("</tr>");
/*  330 */     out.append("</form></table>");
/*  331 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  338 */     if (val == null)
/*      */     {
/*  340 */       return "-";
/*      */     }
/*      */     
/*  343 */     String ret = FormatUtil.formatNumber(val);
/*  344 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  345 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  348 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  352 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  360 */     StringBuffer out = new StringBuffer();
/*  361 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  362 */     out.append("<tr>");
/*  363 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  365 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  367 */     out.append("</tr>");
/*  368 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  372 */       if (j % 2 == 0)
/*      */       {
/*  374 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  378 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  381 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  383 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  386 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  390 */       out.append("</tr>");
/*      */     }
/*  392 */     out.append("</table>");
/*  393 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  394 */     out.append("<tr>");
/*  395 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  396 */     out.append("</tr>");
/*  397 */     out.append("</table>");
/*  398 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  404 */     StringBuffer out = new StringBuffer();
/*  405 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  406 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  407 */     out.append("<tr>");
/*  408 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  409 */     out.append("<tr>");
/*  410 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  411 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  412 */     out.append("</tr>");
/*  413 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  416 */       out.append("<tr>");
/*  417 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  418 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  419 */       out.append("</tr>");
/*      */     }
/*      */     
/*  422 */     out.append("</table>");
/*  423 */     out.append("</table>");
/*  424 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  429 */     if (severity.equals("0"))
/*      */     {
/*  431 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  435 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  442 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  455 */     StringBuffer out = new StringBuffer();
/*  456 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  457 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  459 */       out.append("<tr>");
/*  460 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  461 */       out.append("</tr>");
/*      */       
/*      */ 
/*  464 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  466 */         String borderclass = "";
/*      */         
/*      */ 
/*  469 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  471 */         out.append("<tr>");
/*      */         
/*  473 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  474 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  475 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  481 */     out.append("</table><br>");
/*  482 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  483 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  485 */       List sLinks = secondLevelOfLinks[0];
/*  486 */       List sText = secondLevelOfLinks[1];
/*  487 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  490 */         out.append("<tr>");
/*  491 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  492 */         out.append("</tr>");
/*  493 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  495 */           String borderclass = "";
/*      */           
/*      */ 
/*  498 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  500 */           out.append("<tr>");
/*      */           
/*  502 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  503 */           if (sLinks.get(i).toString().length() == 0) {
/*  504 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  507 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  509 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  513 */     out.append("</table>");
/*  514 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  521 */     StringBuffer out = new StringBuffer();
/*  522 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  523 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  525 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  527 */         out.append("<tr>");
/*  528 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  529 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  533 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  535 */           String borderclass = "";
/*      */           
/*      */ 
/*  538 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  540 */           out.append("<tr>");
/*      */           
/*  542 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  543 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  544 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  547 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  550 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  555 */     out.append("</table><br>");
/*  556 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  557 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  559 */       List sLinks = secondLevelOfLinks[0];
/*  560 */       List sText = secondLevelOfLinks[1];
/*  561 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  564 */         out.append("<tr>");
/*  565 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  566 */         out.append("</tr>");
/*  567 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  569 */           String borderclass = "";
/*      */           
/*      */ 
/*  572 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  574 */           out.append("<tr>");
/*      */           
/*  576 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  577 */           if (sLinks.get(i).toString().length() == 0) {
/*  578 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  581 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  583 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  587 */     out.append("</table>");
/*  588 */     return out.toString();
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
/*  601 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  616 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  619 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  622 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  630 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  635 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  640 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  645 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  650 */     if (val != null)
/*      */     {
/*  652 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  656 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  661 */     if (val == null) {
/*  662 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  666 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  671 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  677 */     if (val != null)
/*      */     {
/*  679 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  683 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  689 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  694 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  698 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  703 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  708 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  713 */     String hostaddress = "";
/*  714 */     String ip = request.getHeader("x-forwarded-for");
/*  715 */     if (ip == null)
/*  716 */       ip = request.getRemoteAddr();
/*  717 */     InetAddress add = null;
/*  718 */     if (ip.equals("127.0.0.1")) {
/*  719 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  723 */       add = InetAddress.getByName(ip);
/*      */     }
/*  725 */     hostaddress = add.getHostName();
/*  726 */     if (hostaddress.indexOf('.') != -1) {
/*  727 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  728 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  732 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  737 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  743 */     if (severity == null)
/*      */     {
/*  745 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  747 */     if (severity.equals("5"))
/*      */     {
/*  749 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  751 */     if (severity.equals("1"))
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  758 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  763 */     ResultSet set = null;
/*  764 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  765 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  767 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  768 */       if (set.next()) { String str1;
/*  769 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  770 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  773 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  778 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  781 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  783 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  787 */     StringBuffer rca = new StringBuffer();
/*  788 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  789 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  792 */     int rcalength = key.length();
/*  793 */     String split = "6. ";
/*  794 */     int splitPresent = key.indexOf(split);
/*  795 */     String div1 = "";String div2 = "";
/*  796 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  798 */       if (rcalength > 180) {
/*  799 */         rca.append("<span class=\"rca-critical-text\">");
/*  800 */         getRCATrimmedText(key, rca);
/*  801 */         rca.append("</span>");
/*      */       } else {
/*  803 */         rca.append("<span class=\"rca-critical-text\">");
/*  804 */         rca.append(key);
/*  805 */         rca.append("</span>");
/*      */       }
/*  807 */       return rca.toString();
/*      */     }
/*  809 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  810 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  811 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  812 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  813 */     getRCATrimmedText(div1, rca);
/*  814 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  817 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  818 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div2, rca);
/*  820 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  822 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  827 */     String[] st = msg.split("<br>");
/*  828 */     for (int i = 0; i < st.length; i++) {
/*  829 */       String s = st[i];
/*  830 */       if (s.length() > 180) {
/*  831 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  833 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  837 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  838 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  840 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  844 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  845 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  846 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  849 */       if (key == null) {
/*  850 */         return ret;
/*      */       }
/*      */       
/*  853 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  854 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  857 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  858 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  859 */       set = AMConnectionPool.executeQueryStmt(query);
/*  860 */       if (set.next())
/*      */       {
/*  862 */         String helpLink = set.getString("LINK");
/*  863 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  866 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  872 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  891 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  882 */         if (set != null) {
/*  883 */           AMConnectionPool.closeStatement(set);
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
/*  897 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  898 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  900 */       String entityStr = (String)keys.nextElement();
/*  901 */       String mmessage = temp.getProperty(entityStr);
/*  902 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  903 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  905 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  911 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  912 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  914 */       String entityStr = (String)keys.nextElement();
/*  915 */       String mmessage = temp.getProperty(entityStr);
/*  916 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  917 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  919 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  924 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  934 */     String des = new String();
/*  935 */     while (str.indexOf(find) != -1) {
/*  936 */       des = des + str.substring(0, str.indexOf(find));
/*  937 */       des = des + replace;
/*  938 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  940 */     des = des + str;
/*  941 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  948 */       if (alert == null)
/*      */       {
/*  950 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  952 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  954 */         return "&nbsp;";
/*      */       }
/*      */       
/*  957 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  962 */       int rcalength = test.length();
/*  963 */       if (rcalength < 300)
/*      */       {
/*  965 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  969 */       StringBuffer out = new StringBuffer();
/*  970 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  971 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  972 */       out.append("</div>");
/*  973 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  974 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  975 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  980 */       ex.printStackTrace();
/*      */     }
/*  982 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  988 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  993 */     ArrayList attribIDs = new ArrayList();
/*  994 */     ArrayList resIDs = new ArrayList();
/*  995 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  997 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  999 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1001 */       String resourceid = "";
/* 1002 */       String resourceType = "";
/* 1003 */       if (type == 2) {
/* 1004 */         resourceid = (String)row.get(0);
/* 1005 */         resourceType = (String)row.get(3);
/*      */       }
/* 1007 */       else if (type == 3) {
/* 1008 */         resourceid = (String)row.get(0);
/* 1009 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1012 */         resourceid = (String)row.get(6);
/* 1013 */         resourceType = (String)row.get(7);
/*      */       }
/* 1015 */       resIDs.add(resourceid);
/* 1016 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1017 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1019 */       String healthentity = null;
/* 1020 */       String availentity = null;
/* 1021 */       if (healthid != null) {
/* 1022 */         healthentity = resourceid + "_" + healthid;
/* 1023 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1026 */       if (availid != null) {
/* 1027 */         availentity = resourceid + "_" + availid;
/* 1028 */         entitylist.add(availentity);
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
/* 1042 */     Properties alert = getStatus(entitylist);
/* 1043 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1048 */     int size = monitorList.size();
/*      */     
/* 1050 */     String[] severity = new String[size];
/*      */     
/* 1052 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1054 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1055 */       String resourceName1 = (String)row1.get(7);
/* 1056 */       String resourceid1 = (String)row1.get(6);
/* 1057 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1058 */       if (severity[j] == null)
/*      */       {
/* 1060 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1064 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1066 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1068 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1071 */         if (sev > 0) {
/* 1072 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1073 */           monitorList.set(k, monitorList.get(j));
/* 1074 */           monitorList.set(j, t);
/* 1075 */           String temp = severity[k];
/* 1076 */           severity[k] = severity[j];
/* 1077 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1083 */     int z = 0;
/* 1084 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1087 */       int i = 0;
/* 1088 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1091 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1095 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1099 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1101 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1104 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1108 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1111 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1112 */       String resourceName1 = (String)row1.get(7);
/* 1113 */       String resourceid1 = (String)row1.get(6);
/* 1114 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1115 */       if (hseverity[j] == null)
/*      */       {
/* 1117 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1122 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1124 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1127 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1130 */         if (hsev > 0) {
/* 1131 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1132 */           monitorList.set(k, monitorList.get(j));
/* 1133 */           monitorList.set(j, t);
/* 1134 */           String temp1 = hseverity[k];
/* 1135 */           hseverity[k] = hseverity[j];
/* 1136 */           hseverity[j] = temp1;
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
/* 1148 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1149 */     boolean forInventory = false;
/* 1150 */     String trdisplay = "none";
/* 1151 */     String plusstyle = "inline";
/* 1152 */     String minusstyle = "none";
/* 1153 */     String haidTopLevel = "";
/* 1154 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1156 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1158 */         haidTopLevel = request.getParameter("haid");
/* 1159 */         forInventory = true;
/* 1160 */         trdisplay = "table-row;";
/* 1161 */         plusstyle = "none";
/* 1162 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1169 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1172 */     ArrayList listtoreturn = new ArrayList();
/* 1173 */     StringBuffer toreturn = new StringBuffer();
/* 1174 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1175 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1176 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1178 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1180 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1181 */       String childresid = (String)singlerow.get(0);
/* 1182 */       String childresname = (String)singlerow.get(1);
/* 1183 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1184 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1185 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1186 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1187 */       String unmanagestatus = (String)singlerow.get(5);
/* 1188 */       String actionstatus = (String)singlerow.get(6);
/* 1189 */       String linkclass = "monitorgp-links";
/* 1190 */       String titleforres = childresname;
/* 1191 */       String titilechildresname = childresname;
/* 1192 */       String childimg = "/images/trcont.png";
/* 1193 */       String flag = "enable";
/* 1194 */       String dcstarted = (String)singlerow.get(8);
/* 1195 */       String configMonitor = "";
/* 1196 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1197 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1199 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1201 */       if (singlerow.get(7) != null)
/*      */       {
/* 1203 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1205 */       String haiGroupType = "0";
/* 1206 */       if ("HAI".equals(childtype))
/*      */       {
/* 1208 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1210 */       childimg = "/images/trend.png";
/* 1211 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1212 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1213 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1215 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1217 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1219 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1220 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1223 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1225 */         linkclass = "disabledtext";
/* 1226 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1228 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1229 */       String availmouseover = "";
/* 1230 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1232 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1234 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String healthmouseover = "";
/* 1236 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1238 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1241 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1242 */       int spacing = 0;
/* 1243 */       if (level >= 1)
/*      */       {
/* 1245 */         spacing = 40 * level;
/*      */       }
/* 1247 */       if (childtype.equals("HAI"))
/*      */       {
/* 1249 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1250 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1251 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1253 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1254 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1255 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1256 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1257 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1258 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1259 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1260 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1261 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1262 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1263 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1265 */         if (!forInventory)
/*      */         {
/* 1267 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1270 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1272 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1274 */           actions = editlink + actions;
/*      */         }
/* 1276 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1278 */           actions = actions + associatelink;
/*      */         }
/* 1280 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1281 */         String arrowimg = "";
/* 1282 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1284 */           actions = "";
/* 1285 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1286 */           checkbox = "";
/* 1287 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1289 */         if (isIt360)
/*      */         {
/* 1291 */           actionimg = "";
/* 1292 */           actions = "";
/* 1293 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1294 */           checkbox = "";
/*      */         }
/*      */         
/* 1297 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1299 */           actions = "";
/*      */         }
/* 1301 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         String resourcelink = "";
/*      */         
/* 1308 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1310 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1314 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1317 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1323 */         if (!isIt360)
/*      */         {
/* 1325 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1329 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1332 */         toreturn.append("</tr>");
/* 1333 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1335 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1336 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1340 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1341 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1344 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1348 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1350 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1352 */             toreturn.append(assocMessage);
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1362 */         String resourcelink = null;
/* 1363 */         boolean hideEditLink = false;
/* 1364 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1366 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1367 */           hideEditLink = true;
/* 1368 */           if (isIt360)
/*      */           {
/* 1370 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1374 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1376 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1378 */           hideEditLink = true;
/* 1379 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1380 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1385 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1388 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1389 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1390 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1391 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1392 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1393 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1394 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1395 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1396 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1397 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1398 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1399 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1400 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1402 */         if (hideEditLink)
/*      */         {
/* 1404 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1406 */         if (!forInventory)
/*      */         {
/* 1408 */           removefromgroup = "";
/*      */         }
/* 1410 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1411 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1412 */           actions = actions + configcustomfields;
/*      */         }
/* 1414 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1416 */           actions = editlink + actions;
/*      */         }
/* 1418 */         String managedLink = "";
/* 1419 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1421 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1422 */           actions = "";
/* 1423 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1424 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1427 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1429 */           checkbox = "";
/*      */         }
/*      */         
/* 1432 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1434 */           actions = "";
/*      */         }
/* 1436 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1437 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1438 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1439 */         if (isIt360)
/*      */         {
/* 1441 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1445 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1449 */         if (!isIt360)
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1455 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1457 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1460 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1467 */       StringBuilder toreturn = new StringBuilder();
/* 1468 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1469 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1470 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1471 */       String title = "";
/* 1472 */       message = EnterpriseUtil.decodeString(message);
/* 1473 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1474 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1475 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1477 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1479 */       else if ("5".equals(severity))
/*      */       {
/* 1481 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1485 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1487 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1488 */       toreturn.append(v);
/*      */       
/* 1490 */       toreturn.append(link);
/* 1491 */       if (severity == null)
/*      */       {
/* 1493 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1495 */       else if (severity.equals("5"))
/*      */       {
/* 1497 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1499 */       else if (severity.equals("4"))
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("1"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       toreturn.append("</a>");
/* 1513 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1517 */       ex.printStackTrace();
/*      */     }
/* 1519 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1526 */       StringBuilder toreturn = new StringBuilder();
/* 1527 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1528 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1529 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1530 */       if (message == null)
/*      */       {
/* 1532 */         message = "";
/*      */       }
/*      */       
/* 1535 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1536 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1538 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1539 */       toreturn.append(v);
/*      */       
/* 1541 */       toreturn.append(link);
/*      */       
/* 1543 */       if (severity == null)
/*      */       {
/* 1545 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1547 */       else if (severity.equals("5"))
/*      */       {
/* 1549 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1551 */       else if (severity.equals("1"))
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       toreturn.append("</a>");
/* 1561 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1567 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1570 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1571 */     if (invokeActions != null) {
/* 1572 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1573 */       while (iterator.hasNext()) {
/* 1574 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1575 */         if (actionmap.containsKey(actionid)) {
/* 1576 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1581 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1585 */     String actionLink = "";
/* 1586 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1587 */     String query = "";
/* 1588 */     ResultSet rs = null;
/* 1589 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1590 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1591 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1592 */       actionLink = "method=" + methodName;
/*      */     }
/* 1594 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1595 */       actionLink = methodName;
/*      */     }
/* 1597 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1598 */     Iterator itr = methodarglist.iterator();
/* 1599 */     boolean isfirstparam = true;
/* 1600 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1601 */     while (itr.hasNext()) {
/* 1602 */       HashMap argmap = (HashMap)itr.next();
/* 1603 */       String argtype = (String)argmap.get("TYPE");
/* 1604 */       String argname = (String)argmap.get("IDENTITY");
/* 1605 */       String paramname = (String)argmap.get("PARAMETER");
/* 1606 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1607 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1608 */         isfirstparam = false;
/* 1609 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1611 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1615 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1619 */         actionLink = actionLink + "&";
/*      */       }
/* 1621 */       String paramValue = null;
/* 1622 */       String tempargname = argname;
/* 1623 */       if (commonValues.getProperty(tempargname) != null) {
/* 1624 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1627 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1628 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1629 */           if (dbType.equals("mysql")) {
/* 1630 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1633 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1635 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1637 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1638 */             if (rs.next()) {
/* 1639 */               paramValue = rs.getString("VALUE");
/* 1640 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1644 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1648 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1651 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1656 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1657 */           paramValue = rowId;
/*      */         }
/* 1659 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1660 */           paramValue = managedObjectName;
/*      */         }
/* 1662 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1663 */           paramValue = resID;
/*      */         }
/* 1665 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1666 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1669 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1671 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1672 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1673 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1675 */     return actionLink;
/*      */   }
/*      */   
/* 1678 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1679 */     String dependentAttribute = null;
/* 1680 */     String align = "left";
/*      */     
/* 1682 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1683 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1684 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1685 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1686 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1687 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1688 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1689 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1690 */       align = "center";
/*      */     }
/*      */     
/* 1693 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1694 */     String actualdata = "";
/*      */     
/* 1696 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1697 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1698 */         actualdata = availValue;
/*      */       }
/* 1700 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1701 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1705 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1706 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1709 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1715 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1716 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1717 */       toreturn.append("<table>");
/* 1718 */       toreturn.append("<tr>");
/* 1719 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1720 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1721 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1722 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1723 */         String toolTip = "";
/* 1724 */         String hideClass = "";
/* 1725 */         String textStyle = "";
/* 1726 */         boolean isreferenced = true;
/* 1727 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1728 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1729 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1730 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1732 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1733 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1734 */           while (valueList.hasMoreTokens()) {
/* 1735 */             String dependentVal = valueList.nextToken();
/* 1736 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1737 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1738 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1740 */               toolTip = "";
/* 1741 */               hideClass = "";
/* 1742 */               isreferenced = false;
/* 1743 */               textStyle = "disabledtext";
/* 1744 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1748 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1749 */           toolTip = "";
/* 1750 */           hideClass = "";
/* 1751 */           isreferenced = false;
/* 1752 */           textStyle = "disabledtext";
/* 1753 */           if (dependentImageMap != null) {
/* 1754 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1755 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1758 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1762 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1763 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1764 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1765 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1766 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1767 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1769 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1770 */           if (isreferenced) {
/* 1771 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1775 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1776 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1777 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1778 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1779 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1780 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1782 */           toreturn.append("</span>");
/* 1783 */           toreturn.append("</a>");
/* 1784 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1787 */       toreturn.append("</tr>");
/* 1788 */       toreturn.append("</table>");
/* 1789 */       toreturn.append("</td>");
/*      */     } else {
/* 1791 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1794 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1798 */     String colTime = null;
/* 1799 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1800 */     if ((rows != null) && (rows.size() > 0)) {
/* 1801 */       Iterator<String> itr = rows.iterator();
/* 1802 */       String maxColQuery = "";
/* 1803 */       for (;;) { if (itr.hasNext()) {
/* 1804 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1805 */           ResultSet maxCol = null;
/*      */           try {
/* 1807 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1808 */             while (maxCol.next()) {
/* 1809 */               if (colTime == null) {
/* 1810 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1813 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1822 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1824 */               if (maxCol != null)
/* 1825 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1827 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1822 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1824 */               if (maxCol != null)
/* 1825 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1827 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1832 */     return colTime;
/*      */   }
/*      */   
/* 1835 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1836 */     tablename = null;
/* 1837 */     ResultSet rsTable = null;
/* 1838 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1840 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1841 */       while (rsTable.next()) {
/* 1842 */         tablename = rsTable.getString("DATATABLE");
/* 1843 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1844 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1857 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1848 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1851 */         if (rsTable != null)
/* 1852 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1854 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1860 */     String argsList = "";
/* 1861 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1863 */       if (showArgsMap.get(row) != null) {
/* 1864 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1865 */         if (showArgslist != null) {
/* 1866 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1867 */             if (argsList.trim().equals("")) {
/* 1868 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1871 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1878 */       e.printStackTrace();
/* 1879 */       return "";
/*      */     }
/* 1881 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1886 */     String argsList = "";
/* 1887 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1890 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1892 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1893 */         if (hideArgsList != null)
/*      */         {
/* 1895 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1897 */             if (argsList.trim().equals(""))
/*      */             {
/* 1899 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1903 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1911 */       ex.printStackTrace();
/*      */     }
/* 1913 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1917 */     StringBuilder toreturn = new StringBuilder();
/* 1918 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1925 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1926 */       Iterator itr = tActionList.iterator();
/* 1927 */       while (itr.hasNext()) {
/* 1928 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1929 */         String confirmmsg = "";
/* 1930 */         String link = "";
/* 1931 */         String isJSP = "NO";
/* 1932 */         HashMap tactionMap = (HashMap)itr.next();
/* 1933 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1934 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1935 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1936 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1937 */           (actionmap.containsKey(actionId))) {
/* 1938 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1939 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1940 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1941 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1942 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1944 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1950 */           if (isTableAction) {
/* 1951 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1954 */             tableName = "Link";
/* 1955 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1956 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1957 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1958 */             toreturn.append("</a></td>");
/*      */           }
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1969 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1975 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1977 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1978 */       Properties prop = (Properties)node.getUserObject();
/* 1979 */       String mgID = prop.getProperty("label");
/* 1980 */       String mgName = prop.getProperty("value");
/* 1981 */       String isParent = prop.getProperty("isParent");
/* 1982 */       int mgIDint = Integer.parseInt(mgID);
/* 1983 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1985 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1987 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1988 */       if (node.getChildCount() > 0)
/*      */       {
/* 1990 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1992 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1994 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1996 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2000 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2005 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2007 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2009 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2011 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2015 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2018 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2019 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2021 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2025 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2027 */       if (node.getChildCount() > 0)
/*      */       {
/* 2029 */         builder.append("<UL>");
/* 2030 */         printMGTree(node, builder);
/* 2031 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2036 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2037 */     StringBuffer toReturn = new StringBuffer();
/* 2038 */     String table = "-";
/*      */     try {
/* 2040 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2041 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2042 */       float total = 0.0F;
/* 2043 */       while (it.hasNext()) {
/* 2044 */         String attName = (String)it.next();
/* 2045 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2046 */         boolean roundOffData = false;
/* 2047 */         if ((data != null) && (!data.equals(""))) {
/* 2048 */           if (data.indexOf(",") != -1) {
/* 2049 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2052 */             float value = Float.parseFloat(data);
/* 2053 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2056 */             total += value;
/* 2057 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2060 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2065 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2066 */       while (attVsWidthList.hasNext()) {
/* 2067 */         String attName = (String)attVsWidthList.next();
/* 2068 */         String data = (String)attVsWidthProps.get(attName);
/* 2069 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2070 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2071 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2072 */         String className = (String)graphDetails.get("ClassName");
/* 2073 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2074 */         if (percentage < 1.0F)
/*      */         {
/* 2076 */           data = percentage + "";
/*      */         }
/* 2078 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2080 */       if (toReturn.length() > 0) {
/* 2081 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2085 */       e.printStackTrace();
/*      */     }
/* 2087 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2093 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2094 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2095 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2096 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2097 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2098 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2099 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2100 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2101 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2104 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2105 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2106 */       splitvalues[0] = multiplecondition.toString();
/* 2107 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2110 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2115 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2116 */     if (thresholdType != 3) {
/* 2117 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2118 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2119 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2120 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2121 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2122 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2124 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2125 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2126 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2127 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2128 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2129 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2131 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2132 */     if (updateSelected != null) {
/* 2133 */       updateSelected[0] = "selected";
/*      */     }
/* 2135 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2140 */       StringBuffer toreturn = new StringBuffer("");
/* 2141 */       if (commaSeparatedMsgId != null) {
/* 2142 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2143 */         int count = 0;
/* 2144 */         while (msgids.hasMoreTokens()) {
/* 2145 */           String id = msgids.nextToken();
/* 2146 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2147 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2148 */           count++;
/* 2149 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2150 */             if (toreturn.length() == 0) {
/* 2151 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2153 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2154 */             if (!image.trim().equals("")) {
/* 2155 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2157 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2158 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2161 */         if (toreturn.length() > 0) {
/* 2162 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2166 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2169 */       e.printStackTrace(); }
/* 2170 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2177 */   String resourceType = "";
/*      */   
/*      */   public ArrayList getDisplayNameList(String[] attrbsList, HashMap infoscollec)
/*      */   {
/* 2181 */     ArrayList aListDispName = null;
/* 2182 */     for (int i = 0; i < attrbsList.length; i++) {
/* 2183 */       ArrayList al = (ArrayList)infoscollec.get(attrbsList[i]);
/* 2184 */       if ((al != null) && (al.size() != 0))
/*      */       {
/*      */ 
/* 2187 */         if (aListDispName == null) {
/* 2188 */           aListDispName = new ArrayList();
/*      */         }
/* 2190 */         String attrbDisp = (String)al.get(1);
/* 2191 */         if ((attrbsList[i].equals("CONF_PRNT_SERVER")) && (this.resourceType.indexOf("windows") == -1))
/*      */         {
/* 2193 */           attrbDisp = "am.webclient.common.device.name";
/*      */         }
/* 2195 */         String units = (String)al.get(3);
/* 2196 */         units = checkNull(units);
/* 2197 */         if (units.length() != 0) {
/* 2198 */           aListDispName.add(FormatUtil.getString(attrbDisp) + " (" + FormatUtil.getString(units) + ")");
/*      */         } else
/* 2200 */           aListDispName.add(FormatUtil.getString(attrbDisp));
/*      */       }
/*      */     }
/* 2203 */     return aListDispName;
/*      */   }
/*      */   
/*      */   public String checkNull(String units) {
/* 2207 */     if ((units == null) || (units.trim().length() == 0)) {
/* 2208 */       return "";
/*      */     }
/* 2210 */     if (units.toLowerCase().equals("null")) {
/* 2211 */       return "";
/*      */     }
/* 2213 */     return units;
/*      */   }
/*      */   
/*      */   public String customizeUptimeVal(String value) {
/* 2217 */     StringBuffer result = new StringBuffer();
/* 2218 */     StringTokenizer tokens = new StringTokenizer(value, ":");
/* 2219 */     if (tokens.countTokens() == 4) {
/* 2220 */       String tok = tokens.nextToken();
/* 2221 */       if (!tok.equals("0")) {
/* 2222 */         result.append(tok);
/* 2223 */         result.append(" ");
/* 2224 */         result.append(FormatUtil.getString("am.webclient.server.config.days"));
/* 2225 */         result.append(", ");
/*      */       }
/* 2227 */       tok = tokens.nextToken();
/* 2228 */       if (!tok.equals("0")) {
/* 2229 */         result.append(tok);
/* 2230 */         result.append(" ");
/* 2231 */         result.append(FormatUtil.getString("am.webclient.server.config.hours"));
/* 2232 */         result.append(", ");
/*      */       }
/* 2234 */       tok = tokens.nextToken();
/* 2235 */       if (!tok.equals("0")) {
/* 2236 */         result.append(tok);
/* 2237 */         result.append(" ");
/* 2238 */         result.append(FormatUtil.getString("am.webclient.server.config.minutes"));
/* 2239 */         result.append(", ");
/*      */       }
/* 2241 */       tok = tokens.nextToken();
/* 2242 */       if (!tok.equals("0")) {
/* 2243 */         result.append(tok);
/* 2244 */         result.append(" ");
/* 2245 */         result.append(FormatUtil.getString("am.webclient.server.config.seconds"));
/*      */       }
/* 2247 */       tok = result.toString().trim();
/* 2248 */       int idx = tok.lastIndexOf(",");
/* 2249 */       if (idx == tok.length() - 1) {
/* 2250 */         tok = tok.substring(0, tok.length() - 1);
/*      */       }
/* 2252 */       return tok;
/*      */     }
/* 2254 */     return "-";
/*      */   }
/*      */   
/* 2257 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2263 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2264 */   static { _jspx_dependants.put("/jsp/includes/ShowConfigDetails.jspf", Long.valueOf(1473429417000L));
/* 2265 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2275 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2279 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2282 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2286 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2294 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2297 */     JspWriter out = null;
/* 2298 */     Object page = this;
/* 2299 */     JspWriter _jspx_out = null;
/* 2300 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2304 */       response.setContentType("text/html;charset=UTF-8");
/* 2305 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2307 */       _jspx_page_context = pageContext;
/* 2308 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2309 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2310 */       session = pageContext.getSession();
/* 2311 */       out = pageContext.getOut();
/* 2312 */       _jspx_out = out;
/*      */       
/*      */ 
/* 2315 */       out.write("\n\n\n\n\n\n\n");
/* 2316 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2318 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2319 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2321 */       out.write(10);
/* 2322 */       out.write(10);
/* 2323 */       out.write(10);
/* 2324 */       out.write(10);
/* 2325 */       out.write(10);
/* 2326 */       out.write(10);
/* 2327 */       out.write(10);
/*      */       
/*      */ 
/* 2330 */       String resourceId = request.getParameter("resourceid");
/* 2331 */       String modeOfMon = request.getParameter("mode");
/* 2332 */       this.resourceType = request.getParameter("resourceType");
/* 2333 */       if (this.resourceType == null) {
/* 2334 */         this.resourceType = "";
/*      */       }
/* 2336 */       this.resourceType = this.resourceType.toLowerCase();
/* 2337 */       if ((modeOfMon != null) && ((modeOfMon.equalsIgnoreCase("TELNET")) || (modeOfMon.equalsIgnoreCase("SSH"))) && 
/* 2338 */         (!this.resourceType.equals("linux")))
/*      */       {
/* 2340 */         out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\">\n\t\t<tr>\n\t\t");
/*      */         
/* 2342 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2343 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2344 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */         
/* 2346 */         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2347 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2348 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */           for (;;) {
/* 2350 */             out.write("\n\t\t\t<td class=\"AlarmInnerBoxBg bodytext\" align=\"center\">");
/* 2351 */             out.print(FormatUtil.getString("am.webclient.server.config.notsupported.info.text.withmodechange", new String[] { request.getParameter("resourceType") }));
/* 2352 */             out.write("</td>\n\t\t");
/* 2353 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2354 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2358 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2359 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */         }
/*      */         else {
/* 2362 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2363 */           out.write(10);
/* 2364 */           out.write(9);
/* 2365 */           out.write(9);
/*      */           
/* 2367 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2368 */           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2369 */           _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */           
/* 2371 */           _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN");
/* 2372 */           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2373 */           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */             for (;;) {
/* 2375 */               out.write("\n\t\t\t<td class=\"AlarmInnerBoxBg bodytext\" align=\"center\">");
/* 2376 */               out.print(FormatUtil.getString("am.webclient.server.config.notsupported.info.text", new String[] { request.getParameter("resourceType") }));
/* 2377 */               out.write("</td>\n\t\t");
/* 2378 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2379 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2383 */           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2384 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */           }
/*      */           else {
/* 2387 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2388 */             out.write("\t\t\n\t\t</tr>\n\t</table>\n");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2394 */         HashMap childIds = (HashMap)request.getAttribute("CHILD_IDS");
/* 2395 */         HashMap configDetails = (HashMap)request.getAttribute("CONFIG_DETAILS");
/*      */         
/*      */ 
/* 2398 */         String displayType = "";
/* 2399 */         String title = "";
/* 2400 */         String[] attrbsList = null;
/*      */         
/* 2402 */         HashMap infos = null;
/* 2403 */         String chldId = "";
/* 2404 */         ArrayList ids = null;
/* 2405 */         ArrayList aListDispName = null;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2410 */         String[] doFormatNumber = { "10061", "10062", "10063", "10064", "10123", "10095", "10096" };
/* 2411 */         Arrays.sort(doFormatNumber);
/*      */         
/* 2413 */         out.write(10);
/* 2414 */         out.write(10);
/*      */         
/* 2416 */         displayType = "row";
/* 2417 */         title = "am.webclient.server.config.sysinfo";
/* 2418 */         attrbsList = HostConfInfoGeneric.getSupportedSystemInfos(this.resourceType, modeOfMon);
/* 2419 */         infos = (HashMap)configDetails.get(resourceId);
/*      */         
/*      */ 
/* 2422 */         if ((infos != null) && (infos.size() > 0)) {
/* 2423 */           ArrayList al = (ArrayList)infos.get("System_Uptime");
/* 2424 */           if ((al != null) && (al.size() == 4)) {
/* 2425 */             String uptime = (String)al.get(2);
/* 2426 */             uptime = customizeUptimeVal(uptime);
/* 2427 */             al.set(2, uptime);
/*      */           }
/*      */         }
/*      */         
/* 2431 */         out.write(10);
/* 2432 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 2434 */         if (displayType.equals("row"))
/*      */         {
/* 2436 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 2439 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2440 */             out.print(FormatUtil.getString(title));
/* 2441 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 2442 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2443 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 2445 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2451 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2452 */             out.print(FormatUtil.getString(title));
/* 2453 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 2455 */             boolean hasInfo = false;
/* 2456 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 2459 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 2461 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 2464 */                 String attrbId = (String)al.get(0);
/* 2465 */                 String attrbDisp = (String)al.get(1);
/* 2466 */                 String attrbValue = (String)al.get(2);
/* 2467 */                 String units = (String)al.get(3);
/* 2468 */                 units = checkNull(units);
/* 2469 */                 hasInfo = true;
/* 2470 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 2472 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 2474 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 2477 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2478 */                   out.print(resourceId);
/* 2479 */                   out.write("&attributeid=");
/* 2480 */                   out.print(attrbId);
/* 2481 */                   out.write("&restype=");
/* 2482 */                   out.print(this.resourceType);
/* 2483 */                   out.write("&mode=");
/* 2484 */                   out.print(modeOfMon);
/* 2485 */                   out.write("&period=-7',740,550)\">");
/* 2486 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2487 */                   out.write(32);
/* 2488 */                   out.write(40);
/* 2489 */                   out.print(FormatUtil.getString(units));
/* 2490 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2496 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2497 */                   out.print(resourceId);
/* 2498 */                   out.write("&attributeid=");
/* 2499 */                   out.print(attrbId);
/* 2500 */                   out.write("&restype=");
/* 2501 */                   out.print(this.resourceType);
/* 2502 */                   out.write("&mode=");
/* 2503 */                   out.print(modeOfMon);
/* 2504 */                   out.write("&period=-7',740,550)\">");
/* 2505 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2506 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2510 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 2512 */                 if (formatNumber)
/*      */                 {
/* 2514 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2515 */                   out.print(formatNumber(attrbValue));
/* 2516 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2520 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2521 */                   out.print(attrbValue);
/* 2522 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2526 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 2529 */             if (!hasInfo)
/*      */             {
/* 2531 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 2532 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2533 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 2535 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2540 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 2543 */         else if (displayType.equals("table"))
/*      */         {
/* 2545 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 2548 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2549 */             out.print(FormatUtil.getString(title));
/* 2550 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 2551 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2552 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 2554 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 2559 */             chldId = (String)ids.get(0);
/* 2560 */             infos = (HashMap)configDetails.get(chldId);
/* 2561 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 2562 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 2565 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 2566 */               out.print(aListDispName.size());
/* 2567 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2568 */               out.print(FormatUtil.getString(title));
/* 2569 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 2571 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 2574 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 2575 */                 out.print((String)aListDispName.get(i));
/* 2576 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 2580 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 2582 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 2584 */                 chldId = (String)ids.get(k);
/* 2585 */                 infos = (HashMap)configDetails.get(chldId);
/* 2586 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 2590 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 2592 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 2594 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 2595 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 2598 */                       String attrbId = (String)al.get(0);
/* 2599 */                       String attrbValue = (String)al.get(2);
/* 2600 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 2602 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 2604 */                       out.write("&attributeid=");
/*      */                       
/* 2606 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 2608 */                       if (formatNumber)
/*      */                       {
/* 2610 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 2611 */                         out.print(formatNumber(attrbValue));
/* 2612 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2616 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 2617 */                         out.print(attrbValue);
/* 2618 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 2622 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 2626 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 2630 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 2638 */           out.write(10);
/* 2639 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 2643 */         out.write(10);
/* 2644 */         out.write("\n<br>\n\n");
/*      */         
/* 2646 */         attrbsList = HostConfInfoGeneric.getSupportedOSInfos(this.resourceType, modeOfMon);
/* 2647 */         title = "am.webclient.server.config.osinfo";
/*      */         
/* 2649 */         out.write(10);
/* 2650 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 2652 */         if (displayType.equals("row"))
/*      */         {
/* 2654 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 2657 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2658 */             out.print(FormatUtil.getString(title));
/* 2659 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 2660 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2661 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 2663 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2669 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2670 */             out.print(FormatUtil.getString(title));
/* 2671 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 2673 */             boolean hasInfo = false;
/* 2674 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 2677 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 2679 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 2682 */                 String attrbId = (String)al.get(0);
/* 2683 */                 String attrbDisp = (String)al.get(1);
/* 2684 */                 String attrbValue = (String)al.get(2);
/* 2685 */                 String units = (String)al.get(3);
/* 2686 */                 units = checkNull(units);
/* 2687 */                 hasInfo = true;
/* 2688 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 2690 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 2692 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 2695 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2696 */                   out.print(resourceId);
/* 2697 */                   out.write("&attributeid=");
/* 2698 */                   out.print(attrbId);
/* 2699 */                   out.write("&restype=");
/* 2700 */                   out.print(this.resourceType);
/* 2701 */                   out.write("&mode=");
/* 2702 */                   out.print(modeOfMon);
/* 2703 */                   out.write("&period=-7',740,550)\">");
/* 2704 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2705 */                   out.write(32);
/* 2706 */                   out.write(40);
/* 2707 */                   out.print(FormatUtil.getString(units));
/* 2708 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2714 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2715 */                   out.print(resourceId);
/* 2716 */                   out.write("&attributeid=");
/* 2717 */                   out.print(attrbId);
/* 2718 */                   out.write("&restype=");
/* 2719 */                   out.print(this.resourceType);
/* 2720 */                   out.write("&mode=");
/* 2721 */                   out.print(modeOfMon);
/* 2722 */                   out.write("&period=-7',740,550)\">");
/* 2723 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2724 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2728 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 2730 */                 if (formatNumber)
/*      */                 {
/* 2732 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2733 */                   out.print(formatNumber(attrbValue));
/* 2734 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2738 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2739 */                   out.print(attrbValue);
/* 2740 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2744 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 2747 */             if (!hasInfo)
/*      */             {
/* 2749 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 2750 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2751 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 2753 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2758 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 2761 */         else if (displayType.equals("table"))
/*      */         {
/* 2763 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 2766 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2767 */             out.print(FormatUtil.getString(title));
/* 2768 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 2769 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2770 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 2772 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 2777 */             chldId = (String)ids.get(0);
/* 2778 */             infos = (HashMap)configDetails.get(chldId);
/* 2779 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 2780 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 2783 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 2784 */               out.print(aListDispName.size());
/* 2785 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2786 */               out.print(FormatUtil.getString(title));
/* 2787 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 2789 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 2792 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 2793 */                 out.print((String)aListDispName.get(i));
/* 2794 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 2798 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 2800 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 2802 */                 chldId = (String)ids.get(k);
/* 2803 */                 infos = (HashMap)configDetails.get(chldId);
/* 2804 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 2808 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 2810 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 2812 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 2813 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 2816 */                       String attrbId = (String)al.get(0);
/* 2817 */                       String attrbValue = (String)al.get(2);
/* 2818 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 2820 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 2822 */                       out.write("&attributeid=");
/*      */                       
/* 2824 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 2826 */                       if (formatNumber)
/*      */                       {
/* 2828 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 2829 */                         out.print(formatNumber(attrbValue));
/* 2830 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2834 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 2835 */                         out.print(attrbValue);
/* 2836 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 2840 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 2844 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 2848 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 2856 */           out.write(10);
/* 2857 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 2861 */         out.write(10);
/* 2862 */         out.write("\n<br>\n\n");
/*      */         
/* 2864 */         attrbsList = HostConfInfoGeneric.memAttrbs;
/* 2865 */         title = "am.webclient.server.config.meminfo";
/*      */         
/*      */ 
/* 2868 */         if ((infos != null) && (infos.size() > 0) && 
/* 2869 */           (this.resourceType.startsWith("windows"))) {
/* 2870 */           ArrayList al = (ArrayList)infos.get(attrbsList[2]);
/* 2871 */           if ((al != null) && (al.size() == 4)) {
/* 2872 */             al.set(1, "Total Virtual Memory");
/*      */           }
/* 2874 */           al = (ArrayList)infos.get(attrbsList[3]);
/* 2875 */           if ((al != null) && (al.size() == 4)) {
/* 2876 */             al.set(1, "Available Virtual Memory");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2881 */         out.write(10);
/* 2882 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 2884 */         if (displayType.equals("row"))
/*      */         {
/* 2886 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 2889 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2890 */             out.print(FormatUtil.getString(title));
/* 2891 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 2892 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2893 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 2895 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2901 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2902 */             out.print(FormatUtil.getString(title));
/* 2903 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 2905 */             boolean hasInfo = false;
/* 2906 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 2909 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 2911 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 2914 */                 String attrbId = (String)al.get(0);
/* 2915 */                 String attrbDisp = (String)al.get(1);
/* 2916 */                 String attrbValue = (String)al.get(2);
/* 2917 */                 String units = (String)al.get(3);
/* 2918 */                 units = checkNull(units);
/* 2919 */                 hasInfo = true;
/* 2920 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 2922 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 2924 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 2927 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2928 */                   out.print(resourceId);
/* 2929 */                   out.write("&attributeid=");
/* 2930 */                   out.print(attrbId);
/* 2931 */                   out.write("&restype=");
/* 2932 */                   out.print(this.resourceType);
/* 2933 */                   out.write("&mode=");
/* 2934 */                   out.print(modeOfMon);
/* 2935 */                   out.write("&period=-7',740,550)\">");
/* 2936 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2937 */                   out.write(32);
/* 2938 */                   out.write(40);
/* 2939 */                   out.print(FormatUtil.getString(units));
/* 2940 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2946 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 2947 */                   out.print(resourceId);
/* 2948 */                   out.write("&attributeid=");
/* 2949 */                   out.print(attrbId);
/* 2950 */                   out.write("&restype=");
/* 2951 */                   out.print(this.resourceType);
/* 2952 */                   out.write("&mode=");
/* 2953 */                   out.print(modeOfMon);
/* 2954 */                   out.write("&period=-7',740,550)\">");
/* 2955 */                   out.print(FormatUtil.getString(attrbDisp));
/* 2956 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2960 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 2962 */                 if (formatNumber)
/*      */                 {
/* 2964 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2965 */                   out.print(formatNumber(attrbValue));
/* 2966 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2970 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 2971 */                   out.print(attrbValue);
/* 2972 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2976 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 2979 */             if (!hasInfo)
/*      */             {
/* 2981 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 2982 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2983 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 2985 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2990 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 2993 */         else if (displayType.equals("table"))
/*      */         {
/* 2995 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 2998 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 2999 */             out.print(FormatUtil.getString(title));
/* 3000 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3001 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3002 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3004 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3009 */             chldId = (String)ids.get(0);
/* 3010 */             infos = (HashMap)configDetails.get(chldId);
/* 3011 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 3012 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 3015 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 3016 */               out.print(aListDispName.size());
/* 3017 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3018 */               out.print(FormatUtil.getString(title));
/* 3019 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 3021 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 3024 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 3025 */                 out.print((String)aListDispName.get(i));
/* 3026 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3030 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 3032 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 3034 */                 chldId = (String)ids.get(k);
/* 3035 */                 infos = (HashMap)configDetails.get(chldId);
/* 3036 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 3040 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 3042 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 3044 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 3045 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 3048 */                       String attrbId = (String)al.get(0);
/* 3049 */                       String attrbValue = (String)al.get(2);
/* 3050 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 3052 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 3054 */                       out.write("&attributeid=");
/*      */                       
/* 3056 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 3058 */                       if (formatNumber)
/*      */                       {
/* 3060 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3061 */                         out.print(formatNumber(attrbValue));
/* 3062 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3066 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3067 */                         out.print(attrbValue);
/* 3068 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 3072 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 3076 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 3080 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 3088 */           out.write(10);
/* 3089 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 3093 */         out.write(10);
/* 3094 */         out.write("\n<br>\n\n");
/*      */         
/* 3096 */         displayType = "table";
/* 3097 */         title = "am.webclient.server.config.cpuinfo";
/* 3098 */         attrbsList = HostConfInfoGeneric.getSupportedProcInfos(this.resourceType, modeOfMon);
/*      */         
/* 3100 */         ids = (ArrayList)childIds.get("HOST_CONF_PROC");
/*      */         
/* 3102 */         out.write(10);
/* 3103 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 3105 */         if (displayType.equals("row"))
/*      */         {
/* 3107 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 3110 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3111 */             out.print(FormatUtil.getString(title));
/* 3112 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3113 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3114 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3116 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3122 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3123 */             out.print(FormatUtil.getString(title));
/* 3124 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 3126 */             boolean hasInfo = false;
/* 3127 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 3130 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 3132 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 3135 */                 String attrbId = (String)al.get(0);
/* 3136 */                 String attrbDisp = (String)al.get(1);
/* 3137 */                 String attrbValue = (String)al.get(2);
/* 3138 */                 String units = (String)al.get(3);
/* 3139 */                 units = checkNull(units);
/* 3140 */                 hasInfo = true;
/* 3141 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 3143 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 3145 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 3148 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3149 */                   out.print(resourceId);
/* 3150 */                   out.write("&attributeid=");
/* 3151 */                   out.print(attrbId);
/* 3152 */                   out.write("&restype=");
/* 3153 */                   out.print(this.resourceType);
/* 3154 */                   out.write("&mode=");
/* 3155 */                   out.print(modeOfMon);
/* 3156 */                   out.write("&period=-7',740,550)\">");
/* 3157 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3158 */                   out.write(32);
/* 3159 */                   out.write(40);
/* 3160 */                   out.print(FormatUtil.getString(units));
/* 3161 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3167 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3168 */                   out.print(resourceId);
/* 3169 */                   out.write("&attributeid=");
/* 3170 */                   out.print(attrbId);
/* 3171 */                   out.write("&restype=");
/* 3172 */                   out.print(this.resourceType);
/* 3173 */                   out.write("&mode=");
/* 3174 */                   out.print(modeOfMon);
/* 3175 */                   out.write("&period=-7',740,550)\">");
/* 3176 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3177 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3181 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 3183 */                 if (formatNumber)
/*      */                 {
/* 3185 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3186 */                   out.print(formatNumber(attrbValue));
/* 3187 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 3191 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3192 */                   out.print(attrbValue);
/* 3193 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3197 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 3200 */             if (!hasInfo)
/*      */             {
/* 3202 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 3203 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3204 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 3206 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3211 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 3214 */         else if (displayType.equals("table"))
/*      */         {
/* 3216 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 3219 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3220 */             out.print(FormatUtil.getString(title));
/* 3221 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3222 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3223 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3225 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3230 */             chldId = (String)ids.get(0);
/* 3231 */             infos = (HashMap)configDetails.get(chldId);
/* 3232 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 3233 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 3236 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 3237 */               out.print(aListDispName.size());
/* 3238 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3239 */               out.print(FormatUtil.getString(title));
/* 3240 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 3242 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 3245 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 3246 */                 out.print((String)aListDispName.get(i));
/* 3247 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3251 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 3253 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 3255 */                 chldId = (String)ids.get(k);
/* 3256 */                 infos = (HashMap)configDetails.get(chldId);
/* 3257 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 3261 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 3263 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 3265 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 3266 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 3269 */                       String attrbId = (String)al.get(0);
/* 3270 */                       String attrbValue = (String)al.get(2);
/* 3271 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 3273 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 3275 */                       out.write("&attributeid=");
/*      */                       
/* 3277 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 3279 */                       if (formatNumber)
/*      */                       {
/* 3281 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3282 */                         out.print(formatNumber(attrbValue));
/* 3283 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3287 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3288 */                         out.print(attrbValue);
/* 3289 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 3293 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 3297 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 3301 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 3309 */           out.write(10);
/* 3310 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 3314 */         out.write(10);
/* 3315 */         out.write("\n<br>\n\n");
/*      */         
/* 3317 */         title = "am.webclient.server.config.netinfo";
/* 3318 */         attrbsList = HostConfInfoGeneric.getSupportedNetInterInfos(this.resourceType, modeOfMon);
/* 3319 */         ids = (ArrayList)childIds.get("HOST_CONF_NET");
/*      */         
/* 3321 */         out.write(10);
/* 3322 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 3324 */         if (displayType.equals("row"))
/*      */         {
/* 3326 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 3329 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3330 */             out.print(FormatUtil.getString(title));
/* 3331 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3332 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3333 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3335 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3341 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3342 */             out.print(FormatUtil.getString(title));
/* 3343 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 3345 */             boolean hasInfo = false;
/* 3346 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 3349 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 3351 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 3354 */                 String attrbId = (String)al.get(0);
/* 3355 */                 String attrbDisp = (String)al.get(1);
/* 3356 */                 String attrbValue = (String)al.get(2);
/* 3357 */                 String units = (String)al.get(3);
/* 3358 */                 units = checkNull(units);
/* 3359 */                 hasInfo = true;
/* 3360 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 3362 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 3364 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 3367 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3368 */                   out.print(resourceId);
/* 3369 */                   out.write("&attributeid=");
/* 3370 */                   out.print(attrbId);
/* 3371 */                   out.write("&restype=");
/* 3372 */                   out.print(this.resourceType);
/* 3373 */                   out.write("&mode=");
/* 3374 */                   out.print(modeOfMon);
/* 3375 */                   out.write("&period=-7',740,550)\">");
/* 3376 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3377 */                   out.write(32);
/* 3378 */                   out.write(40);
/* 3379 */                   out.print(FormatUtil.getString(units));
/* 3380 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3386 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3387 */                   out.print(resourceId);
/* 3388 */                   out.write("&attributeid=");
/* 3389 */                   out.print(attrbId);
/* 3390 */                   out.write("&restype=");
/* 3391 */                   out.print(this.resourceType);
/* 3392 */                   out.write("&mode=");
/* 3393 */                   out.print(modeOfMon);
/* 3394 */                   out.write("&period=-7',740,550)\">");
/* 3395 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3396 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3400 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 3402 */                 if (formatNumber)
/*      */                 {
/* 3404 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3405 */                   out.print(formatNumber(attrbValue));
/* 3406 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 3410 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3411 */                   out.print(attrbValue);
/* 3412 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3416 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 3419 */             if (!hasInfo)
/*      */             {
/* 3421 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 3422 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3423 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 3425 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3430 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 3433 */         else if (displayType.equals("table"))
/*      */         {
/* 3435 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 3438 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3439 */             out.print(FormatUtil.getString(title));
/* 3440 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3441 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3442 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3444 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3449 */             chldId = (String)ids.get(0);
/* 3450 */             infos = (HashMap)configDetails.get(chldId);
/* 3451 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 3452 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 3455 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 3456 */               out.print(aListDispName.size());
/* 3457 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3458 */               out.print(FormatUtil.getString(title));
/* 3459 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 3461 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 3464 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 3465 */                 out.print((String)aListDispName.get(i));
/* 3466 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3470 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 3472 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 3474 */                 chldId = (String)ids.get(k);
/* 3475 */                 infos = (HashMap)configDetails.get(chldId);
/* 3476 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 3480 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 3482 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 3484 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 3485 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 3488 */                       String attrbId = (String)al.get(0);
/* 3489 */                       String attrbValue = (String)al.get(2);
/* 3490 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 3492 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 3494 */                       out.write("&attributeid=");
/*      */                       
/* 3496 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 3498 */                       if (formatNumber)
/*      */                       {
/* 3500 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3501 */                         out.print(formatNumber(attrbValue));
/* 3502 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3506 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3507 */                         out.print(attrbValue);
/* 3508 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 3512 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 3516 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 3520 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 3528 */           out.write(10);
/* 3529 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 3533 */         out.write(10);
/* 3534 */         out.write("\n<br>\n\n");
/*      */         
/* 3536 */         title = "am.webclient.server.config.prntinfo";
/* 3537 */         attrbsList = HostConfInfoGeneric.getSupportedPrntInfos(this.resourceType, modeOfMon);
/* 3538 */         ids = (ArrayList)childIds.get("HOST_CONF_PRNT");
/*      */         
/* 3540 */         out.write(10);
/* 3541 */         out.write("<!--$Id$-->\n\n\n");
/*      */         
/* 3543 */         if (displayType.equals("row"))
/*      */         {
/* 3545 */           if ((infos == null) || (infos.size() == 0))
/*      */           {
/*      */ 
/* 3548 */             out.write("\t\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3549 */             out.print(FormatUtil.getString(title));
/* 3550 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3551 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3552 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3554 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3560 */             out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3561 */             out.print(FormatUtil.getString(title));
/* 3562 */             out.write("</td>\n\t\t</tr>\n");
/*      */             
/* 3564 */             boolean hasInfo = false;
/* 3565 */             for (int i = 0; i < attrbsList.length; i++)
/*      */             {
/*      */ 
/* 3568 */               ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/*      */               
/* 3570 */               if ((al != null) && (al.size() != 0))
/*      */               {
/*      */ 
/* 3573 */                 String attrbId = (String)al.get(0);
/* 3574 */                 String attrbDisp = (String)al.get(1);
/* 3575 */                 String attrbValue = (String)al.get(2);
/* 3576 */                 String units = (String)al.get(3);
/* 3577 */                 units = checkNull(units);
/* 3578 */                 hasInfo = true;
/* 3579 */                 boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                 
/* 3581 */                 out.write("\n\t\t<tr>\n\t\t\t<td width=\"40%\" class=\"monitorinfoodd\" style=\"height:25px\">\n");
/*      */                 
/* 3583 */                 if (units.length() != 0)
/*      */                 {
/*      */ 
/* 3586 */                   out.write("\t\t\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3587 */                   out.print(resourceId);
/* 3588 */                   out.write("&attributeid=");
/* 3589 */                   out.print(attrbId);
/* 3590 */                   out.write("&restype=");
/* 3591 */                   out.print(this.resourceType);
/* 3592 */                   out.write("&mode=");
/* 3593 */                   out.print(modeOfMon);
/* 3594 */                   out.write("&period=-7',740,550)\">");
/* 3595 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3596 */                   out.write(32);
/* 3597 */                   out.write(40);
/* 3598 */                   out.print(FormatUtil.getString(units));
/* 3599 */                   out.write(")</a></b>\n");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3605 */                   out.write("\n\t\t\t\t<b> <a class=\"configuration-links-active\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/* 3606 */                   out.print(resourceId);
/* 3607 */                   out.write("&attributeid=");
/* 3608 */                   out.print(attrbId);
/* 3609 */                   out.write("&restype=");
/* 3610 */                   out.print(this.resourceType);
/* 3611 */                   out.write("&mode=");
/* 3612 */                   out.print(modeOfMon);
/* 3613 */                   out.write("&period=-7',740,550)\">");
/* 3614 */                   out.print(FormatUtil.getString(attrbDisp));
/* 3615 */                   out.write("</a></b>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3619 */                 out.write("\n\t\t\t</td>\n");
/*      */                 
/* 3621 */                 if (formatNumber)
/*      */                 {
/* 3623 */                   out.write("\t\t\t\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3624 */                   out.print(formatNumber(attrbValue));
/* 3625 */                   out.write(" </td>\n");
/*      */                 }
/*      */                 else
/*      */                 {
/* 3629 */                   out.write("\n\t\t\t<td width=\"60%\" class=\"monitorinfoodd\">");
/* 3630 */                   out.print(attrbValue);
/* 3631 */                   out.write("</td>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 3635 */                 out.write("\t\t\t\n\t\t</tr>\n\n");
/*      */               }
/*      */             }
/* 3638 */             if (!hasInfo)
/*      */             {
/* 3640 */               out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\">");
/* 3641 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3642 */               out.write(".\n\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */               
/* 3644 */               out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\t\t\t\t\n");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3649 */           out.write("\n\t</table>\n");
/*      */ 
/*      */         }
/* 3652 */         else if (displayType.equals("table"))
/*      */         {
/* 3654 */           if ((ids == null) || (ids.size() == 0))
/*      */           {
/*      */ 
/* 3657 */             out.write("\n\t<table class=\"lrtbdarkborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"2\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3658 */             out.print(FormatUtil.getString(title));
/* 3659 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:30px\">");
/* 3660 */             out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3661 */             out.write(".\n\t\t\t\t<!--<a target=\"_blank\" href=\"http://www.manageengine.com/products/applications_manager/troubleshoot.html#m70\"><font class=\"staticlinks-red\">");
/*      */             
/* 3663 */             out.write("</font></a> -->\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3668 */             chldId = (String)ids.get(0);
/* 3669 */             infos = (HashMap)configDetails.get(chldId);
/* 3670 */             aListDispName = getDisplayNameList(attrbsList, infos);
/* 3671 */             if ((aListDispName != null) && (aListDispName.size() > 0))
/*      */             {
/*      */ 
/* 3674 */               out.write("\n\t<table class=\"lrbtborder\" width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td colspan=\"");
/* 3675 */               out.print(aListDispName.size());
/* 3676 */               out.write("\" width=\"100%\" class=\"tableheadingbborder\">");
/* 3677 */               out.print(FormatUtil.getString(title));
/* 3678 */               out.write("</td>\n\t\t</tr>\n\t\t<tr>\n");
/*      */               
/* 3680 */               for (int i = 0; i < aListDispName.size(); i++)
/*      */               {
/*      */ 
/* 3683 */                 out.write("\n\t\t\t<td class=\"columnheading\" style=\"height:25px\">");
/* 3684 */                 out.print((String)aListDispName.get(i));
/* 3685 */                 out.write("</td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3689 */               out.write("\n\t\t</tr>\n");
/*      */               
/* 3691 */               for (int k = 0; k < ids.size(); k++)
/*      */               {
/* 3693 */                 chldId = (String)ids.get(k);
/* 3694 */                 infos = (HashMap)configDetails.get(chldId);
/* 3695 */                 if ((infos != null) && (infos.size() != 0))
/*      */                 {
/*      */ 
/*      */ 
/* 3699 */                   out.write("\n\t\t<tr>\n");
/*      */                   
/* 3701 */                   for (int i = 0; i < attrbsList.length; i++)
/*      */                   {
/* 3703 */                     ArrayList al = (ArrayList)infos.get(attrbsList[i]);
/* 3704 */                     if ((al != null) && (al.size() != 0))
/*      */                     {
/*      */ 
/* 3707 */                       String attrbId = (String)al.get(0);
/* 3708 */                       String attrbValue = (String)al.get(2);
/* 3709 */                       boolean formatNumber = Arrays.binarySearch(doFormatNumber, attrbId) >= 0;
/*      */                       
/* 3711 */                       out.write("\n\t\t\t\t<!--<a class=\"dotteduline\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=");
/*      */                       
/* 3713 */                       out.write("&attributeid=");
/*      */                       
/* 3715 */                       out.write("&period=-7',740,550)\">-->\n");
/*      */                       
/* 3717 */                       if (formatNumber)
/*      */                       {
/* 3719 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3720 */                         out.print(formatNumber(attrbValue));
/* 3721 */                         out.write("</td>\n");
/*      */                       }
/*      */                       else
/*      */                       {
/* 3725 */                         out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" style=\"height:30px\">");
/* 3726 */                         out.print(attrbValue);
/* 3727 */                         out.write("</td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 3731 */                       out.write("\t\t\t\t\n\t\t\t\t<!--</a>-->\n");
/*      */                     }
/*      */                   }
/*      */                   
/* 3735 */                   out.write("\n\t\t</tr>\n");
/*      */                 }
/*      */               }
/*      */               
/* 3739 */               out.write("\n\t\t</table>\n");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 3747 */           out.write(10);
/* 3748 */           out.write(10);
/*      */         }
/*      */         
/*      */ 
/* 3752 */         out.write(10);
/* 3753 */         out.write(10);
/* 3754 */         out.write(10);
/*      */       }
/* 3756 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3757 */         out = _jspx_out;
/* 3758 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3759 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3760 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3763 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostConfigDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */