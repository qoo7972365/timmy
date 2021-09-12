/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ 
/*      */ public final class ShowApplicationDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2176 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2183 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/includes/MgTables.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2195 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2199 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2203 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2216 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2219 */     JspWriter out = null;
/* 2220 */     Object page = this;
/* 2221 */     JspWriter _jspx_out = null;
/* 2222 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2226 */       response.setContentType("text/html;charset=UTF-8");
/* 2227 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2229 */       _jspx_page_context = pageContext;
/* 2230 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2231 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2232 */       session = pageContext.getSession();
/* 2233 */       out = pageContext.getOut();
/* 2234 */       _jspx_out = out;
/*      */       
/* 2236 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n              \n\n\n\n");
/* 2237 */       Hashtable availabilitymap = null;
/* 2238 */       availabilitymap = (Hashtable)_jspx_page_context.getAttribute("availabilitymap", 2);
/* 2239 */       if (availabilitymap == null) {
/* 2240 */         availabilitymap = new Hashtable();
/* 2241 */         _jspx_page_context.setAttribute("availabilitymap", availabilitymap, 2);
/*      */       }
/* 2243 */       out.write(10);
/* 2244 */       Hashtable alertcounts = null;
/* 2245 */       alertcounts = (Hashtable)_jspx_page_context.getAttribute("alertcounts", 2);
/* 2246 */       if (alertcounts == null) {
/* 2247 */         alertcounts = new Hashtable();
/* 2248 */         _jspx_page_context.setAttribute("alertcounts", alertcounts, 2);
/*      */       }
/* 2250 */       out.write(10);
/* 2251 */       GetWLSGraph wlsGraph = null;
/* 2252 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2253 */       if (wlsGraph == null) {
/* 2254 */         wlsGraph = new GetWLSGraph();
/* 2255 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2257 */       out.write(10);
/* 2258 */       out.write(10);
/* 2259 */       Hashtable availabilitykeys = null;
/* 2260 */       synchronized (application) {
/* 2261 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2262 */         if (availabilitykeys == null) {
/* 2263 */           availabilitykeys = new Hashtable();
/* 2264 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2267 */       out.write(10);
/* 2268 */       Hashtable healthkeys = null;
/* 2269 */       synchronized (application) {
/* 2270 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2271 */         if (healthkeys == null) {
/* 2272 */           healthkeys = new Hashtable();
/* 2273 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2276 */       out.write(10);
/* 2277 */       out.write(10);
/* 2278 */       out.write(10);
/* 2279 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2281 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2292 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2293 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2294 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2297 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2298 */         String available = null;
/* 2299 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2300 */         out.write(10);
/*      */         
/* 2302 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2313 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2314 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2315 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2318 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2319 */           String unavailable = null;
/* 2320 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2321 */           out.write(10);
/*      */           
/* 2323 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2334 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2335 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2336 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2339 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2340 */             String unmanaged = null;
/* 2341 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2342 */             out.write(10);
/*      */             
/* 2344 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2355 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2356 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2357 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2360 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2361 */               String scheduled = null;
/* 2362 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2363 */               out.write(10);
/*      */               
/* 2365 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2376 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2377 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2378 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2381 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2382 */                 String critical = null;
/* 2383 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2384 */                 out.write(10);
/*      */                 
/* 2386 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2397 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2398 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2399 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2402 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2403 */                   String clear = null;
/* 2404 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2405 */                   out.write(10);
/*      */                   
/* 2407 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2418 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2419 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2420 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2423 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2424 */                     String warning = null;
/* 2425 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/*      */                     
/* 2429 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2430 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2432 */                     out.write(10);
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/* 2435 */                     out.write("\n\n        ");
/*      */                     
/* 2437 */                     boolean bol = false;
/* 2438 */                     if (request.getAttribute("ser_list") != null)
/*      */                     {
/* 2440 */                       bol = true;
/* 2441 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("ser_list"));
/*      */                       
/* 2443 */                       out.write("\n        ");
/* 2444 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 2448 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 2449 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 2451 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 2453 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 2456 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 2458 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 2460 */                           while (keys.hasMoreElements())
/*      */                           {
/* 2462 */                             String type = (String)keys.nextElement();
/* 2463 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2469 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 2470 */                               ArrayList attids = (ArrayList)al.get(0);
/* 2471 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 2472 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 2474 */                                 float width = 60.0F / (attids.size() - 2);
/* 2475 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 2476 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 2479 */                                 Enumeration res_id = ht_type.keys();
/* 2480 */                                 ArrayList resids1 = new ArrayList();
/* 2481 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 2483 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 2485 */                                   String elt = (String)res_id.nextElement();
/* 2486 */                                   resids1.add(elt);
/* 2487 */                                   Properties p = (Properties)ht_type.get(elt);
/* 2488 */                                   Enumeration prop = p.keys();
/* 2489 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 2491 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 2494 */                                 int colspan1 = attids.size();
/* 2495 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 2497 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 2498 */                                 out.print(colspan1);
/* 2499 */                                 out.write(34);
/* 2500 */                                 out.write(62);
/* 2501 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 2502 */                                 out.write("</td>\n\n");
/*      */                                 
/* 2504 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2505 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2506 */                                 _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                                 
/* 2508 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,OPERATOR");
/* 2509 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2510 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2512 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 2513 */                                     out.print(type);
/* 2514 */                                     out.write("&haid=");
/* 2515 */                                     out.print(request.getParameter("haid"));
/* 2516 */                                     out.write("')\" class=\"staticlinks1\">");
/* 2517 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 2518 */                                     out.write("</a></td>\n");
/* 2519 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2520 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2524 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2525 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2528 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2529 */                                 out.write(10);
/* 2530 */                                 out.write(10);
/* 2531 */                                 if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */                                   return;
/* 2533 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 2534 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 2535 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2536 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 2537 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2538 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 2539 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 2541 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 2544 */                                   out.write("\n                            <td width='");
/* 2545 */                                   out.print(width);
/* 2546 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 2547 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 2548 */                                   out.write("&nbsp;");
/* 2549 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 2550 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2554 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 2556 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 2558 */                                 String class1 = "yellowgrayborder";
/* 2559 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 2561 */                                   String ele = (String)resids.nextElement();
/* 2562 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 2564 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2568 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 2570 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 2572 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 2573 */                                   out.print(class1);
/* 2574 */                                   out.write("\" title=\"");
/* 2575 */                                   out.print(res_ids.getProperty(ele));
/* 2576 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2577 */                                   out.print(ele);
/* 2578 */                                   out.write(34);
/* 2579 */                                   out.write(62);
/* 2580 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 2581 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 2582 */                                   out.print(class1);
/* 2583 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2584 */                                   out.print(ele);
/* 2585 */                                   out.write("&attributeid=");
/* 2586 */                                   out.print(attids.get(0));
/* 2587 */                                   out.write("')\" class=\"staticlinks\">");
/* 2588 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 2589 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 2590 */                                   out.print(class1);
/* 2591 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2592 */                                   out.print(ele);
/* 2593 */                                   out.write("&attributeid=");
/* 2594 */                                   out.print(attids.get(1));
/* 2595 */                                   out.write("')\" class=\"staticlinks\">");
/* 2596 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 2597 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 2599 */                                   if (attids.size() > 2)
/*      */                                   {
/* 2601 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 2603 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 2606 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 2607 */                                         out.print(class1);
/* 2608 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 2610 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 2611 */                                         out.print(class1);
/* 2612 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2613 */                                         out.print(data_props.get((String)attids.get(i)));
/* 2614 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 2619 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2623 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 2628 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 2634 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 2637 */                       out.write(10);
/* 2638 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 2641 */                     if (request.getAttribute("url_list") != null)
/*      */                     {
/* 2643 */                       bol = true;
/* 2644 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("url_list"));
/*      */                       
/* 2646 */                       out.write("\n        ");
/* 2647 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 2651 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 2652 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 2654 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 2656 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 2659 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 2661 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 2663 */                           while (keys.hasMoreElements())
/*      */                           {
/* 2665 */                             String type = (String)keys.nextElement();
/* 2666 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2672 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 2673 */                               ArrayList attids = (ArrayList)al.get(0);
/* 2674 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 2675 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 2677 */                                 float width = 60.0F / (attids.size() - 2);
/* 2678 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 2679 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 2682 */                                 Enumeration res_id = ht_type.keys();
/* 2683 */                                 ArrayList resids1 = new ArrayList();
/* 2684 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 2686 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 2688 */                                   String elt = (String)res_id.nextElement();
/* 2689 */                                   resids1.add(elt);
/* 2690 */                                   Properties p = (Properties)ht_type.get(elt);
/* 2691 */                                   Enumeration prop = p.keys();
/* 2692 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 2694 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 2697 */                                 int colspan1 = attids.size();
/* 2698 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 2700 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 2701 */                                 out.print(colspan1);
/* 2702 */                                 out.write(34);
/* 2703 */                                 out.write(62);
/* 2704 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 2705 */                                 out.write("</td>\n\n");
/*      */                                 
/* 2707 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2708 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2709 */                                 _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                                 
/* 2711 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,OPERATOR");
/* 2712 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2713 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 2715 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 2716 */                                     out.print(type);
/* 2717 */                                     out.write("&haid=");
/* 2718 */                                     out.print(request.getParameter("haid"));
/* 2719 */                                     out.write("')\" class=\"staticlinks1\">");
/* 2720 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 2721 */                                     out.write("</a></td>\n");
/* 2722 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2723 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2727 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2728 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 2731 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2732 */                                 out.write(10);
/* 2733 */                                 out.write(10);
/* 2734 */                                 if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_page_context))
/*      */                                   return;
/* 2736 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 2737 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 2738 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2739 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 2740 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2741 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 2742 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 2744 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 2747 */                                   out.write("\n                            <td width='");
/* 2748 */                                   out.print(width);
/* 2749 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 2750 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 2751 */                                   out.write("&nbsp;");
/* 2752 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 2753 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2757 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 2759 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 2761 */                                 String class1 = "yellowgrayborder";
/* 2762 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 2764 */                                   String ele = (String)resids.nextElement();
/* 2765 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 2767 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2771 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 2773 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 2775 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 2776 */                                   out.print(class1);
/* 2777 */                                   out.write("\" title=\"");
/* 2778 */                                   out.print(res_ids.getProperty(ele));
/* 2779 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2780 */                                   out.print(ele);
/* 2781 */                                   out.write(34);
/* 2782 */                                   out.write(62);
/* 2783 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 2784 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 2785 */                                   out.print(class1);
/* 2786 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2787 */                                   out.print(ele);
/* 2788 */                                   out.write("&attributeid=");
/* 2789 */                                   out.print(attids.get(0));
/* 2790 */                                   out.write("')\" class=\"staticlinks\">");
/* 2791 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 2792 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 2793 */                                   out.print(class1);
/* 2794 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2795 */                                   out.print(ele);
/* 2796 */                                   out.write("&attributeid=");
/* 2797 */                                   out.print(attids.get(1));
/* 2798 */                                   out.write("')\" class=\"staticlinks\">");
/* 2799 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 2800 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 2802 */                                   if (attids.size() > 2)
/*      */                                   {
/* 2804 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 2806 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 2809 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 2810 */                                         out.print(class1);
/* 2811 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 2813 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 2814 */                                         out.print(class1);
/* 2815 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2816 */                                         out.print(data_props.get((String)attids.get(i)));
/* 2817 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 2822 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2826 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 2831 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 2837 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 2840 */                       out.write(10);
/* 2841 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 2844 */                     if (request.getAttribute("erp_list") != null)
/*      */                     {
/* 2846 */                       bol = true;
/* 2847 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("erp_list"));
/*      */                       
/* 2849 */                       out.write("\n        ");
/* 2850 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 2854 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 2855 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 2857 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 2859 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 2862 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 2864 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 2866 */                           while (keys.hasMoreElements())
/*      */                           {
/* 2868 */                             String type = (String)keys.nextElement();
/* 2869 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2875 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 2876 */                               ArrayList attids = (ArrayList)al.get(0);
/* 2877 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 2878 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 2880 */                                 float width = 60.0F / (attids.size() - 2);
/* 2881 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 2882 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 2885 */                                 Enumeration res_id = ht_type.keys();
/* 2886 */                                 ArrayList resids1 = new ArrayList();
/* 2887 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 2889 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 2891 */                                   String elt = (String)res_id.nextElement();
/* 2892 */                                   resids1.add(elt);
/* 2893 */                                   Properties p = (Properties)ht_type.get(elt);
/* 2894 */                                   Enumeration prop = p.keys();
/* 2895 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 2897 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 2900 */                                 int colspan1 = attids.size();
/* 2901 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 2903 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 2904 */                                 out.print(colspan1);
/* 2905 */                                 out.write(34);
/* 2906 */                                 out.write(62);
/* 2907 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 2908 */                                 out.write("</td>\n\n");
/*      */                                 
/* 2910 */                                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2911 */                                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2912 */                                 _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                                 
/* 2914 */                                 _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,OPERATOR");
/* 2915 */                                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2916 */                                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                   for (;;) {
/* 2918 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 2919 */                                     out.print(type);
/* 2920 */                                     out.write("&haid=");
/* 2921 */                                     out.print(request.getParameter("haid"));
/* 2922 */                                     out.write("')\" class=\"staticlinks1\">");
/* 2923 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 2924 */                                     out.write("</a></td>\n");
/* 2925 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2926 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2930 */                                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2931 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                 }
/*      */                                 
/* 2934 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2935 */                                 out.write(10);
/* 2936 */                                 out.write(10);
/* 2937 */                                 if (_jspx_meth_logic_005fnotPresent_005f2(_jspx_page_context))
/*      */                                   return;
/* 2939 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 2940 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 2941 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2942 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 2943 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 2944 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 2945 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 2947 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 2950 */                                   out.write("\n                            <td width='");
/* 2951 */                                   out.print(width);
/* 2952 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 2953 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 2954 */                                   out.write("&nbsp;");
/* 2955 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 2956 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2960 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 2962 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 2964 */                                 String class1 = "yellowgrayborder";
/* 2965 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 2967 */                                   String ele = (String)resids.nextElement();
/* 2968 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 2970 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2974 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 2976 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 2978 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 2979 */                                   out.print(class1);
/* 2980 */                                   out.write("\" title=\"");
/* 2981 */                                   out.print(res_ids.getProperty(ele));
/* 2982 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2983 */                                   out.print(ele);
/* 2984 */                                   out.write(34);
/* 2985 */                                   out.write(62);
/* 2986 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 2987 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 2988 */                                   out.print(class1);
/* 2989 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2990 */                                   out.print(ele);
/* 2991 */                                   out.write("&attributeid=");
/* 2992 */                                   out.print(attids.get(0));
/* 2993 */                                   out.write("')\" class=\"staticlinks\">");
/* 2994 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 2995 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 2996 */                                   out.print(class1);
/* 2997 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2998 */                                   out.print(ele);
/* 2999 */                                   out.write("&attributeid=");
/* 3000 */                                   out.print(attids.get(1));
/* 3001 */                                   out.write("')\" class=\"staticlinks\">");
/* 3002 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 3003 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 3005 */                                   if (attids.size() > 2)
/*      */                                   {
/* 3007 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 3009 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 3012 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 3013 */                                         out.print(class1);
/* 3014 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 3016 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 3017 */                                         out.print(class1);
/* 3018 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3019 */                                         out.print(data_props.get((String)attids.get(i)));
/* 3020 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3025 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3029 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 3034 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 3040 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 3043 */                       out.write(10);
/* 3044 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 3047 */                     if (request.getAttribute("dbs_list") != null)
/*      */                     {
/* 3049 */                       bol = true;
/* 3050 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("dbs_list"));
/*      */                       
/* 3052 */                       out.write("\n        ");
/* 3053 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 3057 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 3058 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 3060 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 3062 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 3065 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 3067 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 3069 */                           while (keys.hasMoreElements())
/*      */                           {
/* 3071 */                             String type = (String)keys.nextElement();
/* 3072 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3078 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 3079 */                               ArrayList attids = (ArrayList)al.get(0);
/* 3080 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 3081 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 3083 */                                 float width = 60.0F / (attids.size() - 2);
/* 3084 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 3085 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 3088 */                                 Enumeration res_id = ht_type.keys();
/* 3089 */                                 ArrayList resids1 = new ArrayList();
/* 3090 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 3092 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 3094 */                                   String elt = (String)res_id.nextElement();
/* 3095 */                                   resids1.add(elt);
/* 3096 */                                   Properties p = (Properties)ht_type.get(elt);
/* 3097 */                                   Enumeration prop = p.keys();
/* 3098 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 3100 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 3103 */                                 int colspan1 = attids.size();
/* 3104 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 3106 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 3107 */                                 out.print(colspan1);
/* 3108 */                                 out.write(34);
/* 3109 */                                 out.write(62);
/* 3110 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 3111 */                                 out.write("</td>\n\n");
/*      */                                 
/* 3113 */                                 PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3114 */                                 _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3115 */                                 _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                                 
/* 3117 */                                 _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,OPERATOR");
/* 3118 */                                 int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3119 */                                 if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                   for (;;) {
/* 3121 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 3122 */                                     out.print(type);
/* 3123 */                                     out.write("&haid=");
/* 3124 */                                     out.print(request.getParameter("haid"));
/* 3125 */                                     out.write("')\" class=\"staticlinks1\">");
/* 3126 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 3127 */                                     out.write("</a></td>\n");
/* 3128 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3129 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3133 */                                 if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3134 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                 }
/*      */                                 
/* 3137 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3138 */                                 out.write(10);
/* 3139 */                                 out.write(10);
/* 3140 */                                 if (_jspx_meth_logic_005fnotPresent_005f3(_jspx_page_context))
/*      */                                   return;
/* 3142 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 3143 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 3144 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3145 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 3146 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3147 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 3148 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 3150 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 3153 */                                   out.write("\n                            <td width='");
/* 3154 */                                   out.print(width);
/* 3155 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 3156 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 3157 */                                   out.write("&nbsp;");
/* 3158 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 3159 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3163 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 3165 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 3167 */                                 String class1 = "yellowgrayborder";
/* 3168 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 3170 */                                   String ele = (String)resids.nextElement();
/* 3171 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 3173 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3177 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 3179 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 3181 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 3182 */                                   out.print(class1);
/* 3183 */                                   out.write("\" title=\"");
/* 3184 */                                   out.print(res_ids.getProperty(ele));
/* 3185 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3186 */                                   out.print(ele);
/* 3187 */                                   out.write(34);
/* 3188 */                                   out.write(62);
/* 3189 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 3190 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 3191 */                                   out.print(class1);
/* 3192 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3193 */                                   out.print(ele);
/* 3194 */                                   out.write("&attributeid=");
/* 3195 */                                   out.print(attids.get(0));
/* 3196 */                                   out.write("')\" class=\"staticlinks\">");
/* 3197 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 3198 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 3199 */                                   out.print(class1);
/* 3200 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3201 */                                   out.print(ele);
/* 3202 */                                   out.write("&attributeid=");
/* 3203 */                                   out.print(attids.get(1));
/* 3204 */                                   out.write("')\" class=\"staticlinks\">");
/* 3205 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 3206 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 3208 */                                   if (attids.size() > 2)
/*      */                                   {
/* 3210 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 3212 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 3215 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 3216 */                                         out.print(class1);
/* 3217 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 3219 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 3220 */                                         out.print(class1);
/* 3221 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3222 */                                         out.print(data_props.get((String)attids.get(i)));
/* 3223 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3228 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3232 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 3237 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 3243 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 3246 */                       out.write(10);
/* 3247 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 3250 */                     if (request.getAttribute("tm_list") != null)
/*      */                     {
/* 3252 */                       bol = true;
/* 3253 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("tm_list"));
/*      */                       
/* 3255 */                       out.write("\n        ");
/* 3256 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 3260 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 3261 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 3263 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 3265 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 3268 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 3270 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 3272 */                           while (keys.hasMoreElements())
/*      */                           {
/* 3274 */                             String type = (String)keys.nextElement();
/* 3275 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3281 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 3282 */                               ArrayList attids = (ArrayList)al.get(0);
/* 3283 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 3284 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 3286 */                                 float width = 60.0F / (attids.size() - 2);
/* 3287 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 3288 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 3291 */                                 Enumeration res_id = ht_type.keys();
/* 3292 */                                 ArrayList resids1 = new ArrayList();
/* 3293 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 3295 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 3297 */                                   String elt = (String)res_id.nextElement();
/* 3298 */                                   resids1.add(elt);
/* 3299 */                                   Properties p = (Properties)ht_type.get(elt);
/* 3300 */                                   Enumeration prop = p.keys();
/* 3301 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 3303 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 3306 */                                 int colspan1 = attids.size();
/* 3307 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 3309 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 3310 */                                 out.print(colspan1);
/* 3311 */                                 out.write(34);
/* 3312 */                                 out.write(62);
/* 3313 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 3314 */                                 out.write("</td>\n\n");
/*      */                                 
/* 3316 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3317 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3318 */                                 _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                                 
/* 3320 */                                 _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,OPERATOR");
/* 3321 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3322 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3324 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 3325 */                                     out.print(type);
/* 3326 */                                     out.write("&haid=");
/* 3327 */                                     out.print(request.getParameter("haid"));
/* 3328 */                                     out.write("')\" class=\"staticlinks1\">");
/* 3329 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 3330 */                                     out.write("</a></td>\n");
/* 3331 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3332 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3336 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3337 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                 }
/*      */                                 
/* 3340 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3341 */                                 out.write(10);
/* 3342 */                                 out.write(10);
/* 3343 */                                 if (_jspx_meth_logic_005fnotPresent_005f4(_jspx_page_context))
/*      */                                   return;
/* 3345 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 3346 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 3347 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3348 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 3349 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3350 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 3351 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 3353 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 3356 */                                   out.write("\n                            <td width='");
/* 3357 */                                   out.print(width);
/* 3358 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 3359 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 3360 */                                   out.write("&nbsp;");
/* 3361 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 3362 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3366 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 3368 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 3370 */                                 String class1 = "yellowgrayborder";
/* 3371 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 3373 */                                   String ele = (String)resids.nextElement();
/* 3374 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 3376 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3380 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 3382 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 3384 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 3385 */                                   out.print(class1);
/* 3386 */                                   out.write("\" title=\"");
/* 3387 */                                   out.print(res_ids.getProperty(ele));
/* 3388 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3389 */                                   out.print(ele);
/* 3390 */                                   out.write(34);
/* 3391 */                                   out.write(62);
/* 3392 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 3393 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 3394 */                                   out.print(class1);
/* 3395 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3396 */                                   out.print(ele);
/* 3397 */                                   out.write("&attributeid=");
/* 3398 */                                   out.print(attids.get(0));
/* 3399 */                                   out.write("')\" class=\"staticlinks\">");
/* 3400 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 3401 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 3402 */                                   out.print(class1);
/* 3403 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3404 */                                   out.print(ele);
/* 3405 */                                   out.write("&attributeid=");
/* 3406 */                                   out.print(attids.get(1));
/* 3407 */                                   out.write("')\" class=\"staticlinks\">");
/* 3408 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 3409 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 3411 */                                   if (attids.size() > 2)
/*      */                                   {
/* 3413 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 3415 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 3418 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 3419 */                                         out.print(class1);
/* 3420 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 3422 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 3423 */                                         out.print(class1);
/* 3424 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3425 */                                         out.print(data_props.get((String)attids.get(i)));
/* 3426 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3431 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3435 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 3440 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 3446 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 3449 */                       out.write(10);
/* 3450 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 3453 */                     if (request.getAttribute("cam_list") != null)
/*      */                     {
/* 3455 */                       bol = true;
/* 3456 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("cam_list"));
/*      */                       
/* 3458 */                       out.write("\n        ");
/* 3459 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 3463 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 3464 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 3466 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 3468 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 3471 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 3473 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 3475 */                           while (keys.hasMoreElements())
/*      */                           {
/* 3477 */                             String type = (String)keys.nextElement();
/* 3478 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3484 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 3485 */                               ArrayList attids = (ArrayList)al.get(0);
/* 3486 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 3487 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 3489 */                                 float width = 60.0F / (attids.size() - 2);
/* 3490 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 3491 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 3494 */                                 Enumeration res_id = ht_type.keys();
/* 3495 */                                 ArrayList resids1 = new ArrayList();
/* 3496 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 3498 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 3500 */                                   String elt = (String)res_id.nextElement();
/* 3501 */                                   resids1.add(elt);
/* 3502 */                                   Properties p = (Properties)ht_type.get(elt);
/* 3503 */                                   Enumeration prop = p.keys();
/* 3504 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 3506 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 3509 */                                 int colspan1 = attids.size();
/* 3510 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 3512 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 3513 */                                 out.print(colspan1);
/* 3514 */                                 out.write(34);
/* 3515 */                                 out.write(62);
/* 3516 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 3517 */                                 out.write("</td>\n\n");
/*      */                                 
/* 3519 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3520 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3521 */                                 _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                                 
/* 3523 */                                 _jspx_th_logic_005fpresent_005f5.setRole("ADMIN,OPERATOR");
/* 3524 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3525 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3527 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 3528 */                                     out.print(type);
/* 3529 */                                     out.write("&haid=");
/* 3530 */                                     out.print(request.getParameter("haid"));
/* 3531 */                                     out.write("')\" class=\"staticlinks1\">");
/* 3532 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 3533 */                                     out.write("</a></td>\n");
/* 3534 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3535 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3539 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3540 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                 }
/*      */                                 
/* 3543 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3544 */                                 out.write(10);
/* 3545 */                                 out.write(10);
/* 3546 */                                 if (_jspx_meth_logic_005fnotPresent_005f5(_jspx_page_context))
/*      */                                   return;
/* 3548 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 3549 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 3550 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3551 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 3552 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3553 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 3554 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 3556 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 3559 */                                   out.write("\n                            <td width='");
/* 3560 */                                   out.print(width);
/* 3561 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 3562 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 3563 */                                   out.write("&nbsp;");
/* 3564 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 3565 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3569 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 3571 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 3573 */                                 String class1 = "yellowgrayborder";
/* 3574 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 3576 */                                   String ele = (String)resids.nextElement();
/* 3577 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 3579 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3583 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 3585 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 3587 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 3588 */                                   out.print(class1);
/* 3589 */                                   out.write("\" title=\"");
/* 3590 */                                   out.print(res_ids.getProperty(ele));
/* 3591 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3592 */                                   out.print(ele);
/* 3593 */                                   out.write(34);
/* 3594 */                                   out.write(62);
/* 3595 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 3596 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 3597 */                                   out.print(class1);
/* 3598 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3599 */                                   out.print(ele);
/* 3600 */                                   out.write("&attributeid=");
/* 3601 */                                   out.print(attids.get(0));
/* 3602 */                                   out.write("')\" class=\"staticlinks\">");
/* 3603 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 3604 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 3605 */                                   out.print(class1);
/* 3606 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3607 */                                   out.print(ele);
/* 3608 */                                   out.write("&attributeid=");
/* 3609 */                                   out.print(attids.get(1));
/* 3610 */                                   out.write("')\" class=\"staticlinks\">");
/* 3611 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 3612 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 3614 */                                   if (attids.size() > 2)
/*      */                                   {
/* 3616 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 3618 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 3621 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 3622 */                                         out.print(class1);
/* 3623 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 3625 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 3626 */                                         out.print(class1);
/* 3627 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3628 */                                         out.print(data_props.get((String)attids.get(i)));
/* 3629 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3634 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3638 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 3643 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 3649 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 3652 */                       out.write(10);
/* 3653 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 3656 */                     if (request.getAttribute("ms_list") != null)
/*      */                     {
/* 3658 */                       bol = true;
/* 3659 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("ms_list"));
/*      */                       
/* 3661 */                       out.write("\n        ");
/* 3662 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 3666 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 3667 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 3669 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 3671 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 3674 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 3676 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 3678 */                           while (keys.hasMoreElements())
/*      */                           {
/* 3680 */                             String type = (String)keys.nextElement();
/* 3681 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3687 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 3688 */                               ArrayList attids = (ArrayList)al.get(0);
/* 3689 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 3690 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 3692 */                                 float width = 60.0F / (attids.size() - 2);
/* 3693 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 3694 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 3697 */                                 Enumeration res_id = ht_type.keys();
/* 3698 */                                 ArrayList resids1 = new ArrayList();
/* 3699 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 3701 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 3703 */                                   String elt = (String)res_id.nextElement();
/* 3704 */                                   resids1.add(elt);
/* 3705 */                                   Properties p = (Properties)ht_type.get(elt);
/* 3706 */                                   Enumeration prop = p.keys();
/* 3707 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 3709 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 3712 */                                 int colspan1 = attids.size();
/* 3713 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 3715 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 3716 */                                 out.print(colspan1);
/* 3717 */                                 out.write(34);
/* 3718 */                                 out.write(62);
/* 3719 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 3720 */                                 out.write("</td>\n\n");
/*      */                                 
/* 3722 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3723 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3724 */                                 _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */                                 
/* 3726 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN,OPERATOR");
/* 3727 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3728 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 3730 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 3731 */                                     out.print(type);
/* 3732 */                                     out.write("&haid=");
/* 3733 */                                     out.print(request.getParameter("haid"));
/* 3734 */                                     out.write("')\" class=\"staticlinks1\">");
/* 3735 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 3736 */                                     out.write("</a></td>\n");
/* 3737 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3738 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3742 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3743 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 3746 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3747 */                                 out.write(10);
/* 3748 */                                 out.write(10);
/* 3749 */                                 if (_jspx_meth_logic_005fnotPresent_005f6(_jspx_page_context))
/*      */                                   return;
/* 3751 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 3752 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 3753 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3754 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 3755 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3756 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 3757 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 3759 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 3762 */                                   out.write("\n                            <td width='");
/* 3763 */                                   out.print(width);
/* 3764 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 3765 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 3766 */                                   out.write("&nbsp;");
/* 3767 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 3768 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3772 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 3774 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 3776 */                                 String class1 = "yellowgrayborder";
/* 3777 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 3779 */                                   String ele = (String)resids.nextElement();
/* 3780 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 3782 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3786 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 3788 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 3790 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 3791 */                                   out.print(class1);
/* 3792 */                                   out.write("\" title=\"");
/* 3793 */                                   out.print(res_ids.getProperty(ele));
/* 3794 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3795 */                                   out.print(ele);
/* 3796 */                                   out.write(34);
/* 3797 */                                   out.write(62);
/* 3798 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 3799 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 3800 */                                   out.print(class1);
/* 3801 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3802 */                                   out.print(ele);
/* 3803 */                                   out.write("&attributeid=");
/* 3804 */                                   out.print(attids.get(0));
/* 3805 */                                   out.write("')\" class=\"staticlinks\">");
/* 3806 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 3807 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 3808 */                                   out.print(class1);
/* 3809 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3810 */                                   out.print(ele);
/* 3811 */                                   out.write("&attributeid=");
/* 3812 */                                   out.print(attids.get(1));
/* 3813 */                                   out.write("')\" class=\"staticlinks\">");
/* 3814 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 3815 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 3817 */                                   if (attids.size() > 2)
/*      */                                   {
/* 3819 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 3821 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 3824 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 3825 */                                         out.print(class1);
/* 3826 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 3828 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 3829 */                                         out.print(class1);
/* 3830 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 3831 */                                         out.print(data_props.get((String)attids.get(i)));
/* 3832 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3837 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3841 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 3846 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 3852 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 3855 */                       out.write(10);
/* 3856 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 3859 */                     if (request.getAttribute("app_list") != null)
/*      */                     {
/* 3861 */                       bol = true;
/* 3862 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("app_list"));
/*      */                       
/* 3864 */                       out.write("\n        ");
/* 3865 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 3869 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 3870 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 3872 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 3874 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 3877 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 3879 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 3881 */                           while (keys.hasMoreElements())
/*      */                           {
/* 3883 */                             String type = (String)keys.nextElement();
/* 3884 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3890 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 3891 */                               ArrayList attids = (ArrayList)al.get(0);
/* 3892 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 3893 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 3895 */                                 float width = 60.0F / (attids.size() - 2);
/* 3896 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 3897 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 3900 */                                 Enumeration res_id = ht_type.keys();
/* 3901 */                                 ArrayList resids1 = new ArrayList();
/* 3902 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 3904 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 3906 */                                   String elt = (String)res_id.nextElement();
/* 3907 */                                   resids1.add(elt);
/* 3908 */                                   Properties p = (Properties)ht_type.get(elt);
/* 3909 */                                   Enumeration prop = p.keys();
/* 3910 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 3912 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 3915 */                                 int colspan1 = attids.size();
/* 3916 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 3918 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 3919 */                                 out.print(colspan1);
/* 3920 */                                 out.write(34);
/* 3921 */                                 out.write(62);
/* 3922 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 3923 */                                 out.write("</td>\n\n");
/*      */                                 
/* 3925 */                                 PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3926 */                                 _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3927 */                                 _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */                                 
/* 3929 */                                 _jspx_th_logic_005fpresent_005f7.setRole("ADMIN,OPERATOR");
/* 3930 */                                 int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3931 */                                 if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                   for (;;) {
/* 3933 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 3934 */                                     out.print(type);
/* 3935 */                                     out.write("&haid=");
/* 3936 */                                     out.print(request.getParameter("haid"));
/* 3937 */                                     out.write("')\" class=\"staticlinks1\">");
/* 3938 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 3939 */                                     out.write("</a></td>\n");
/* 3940 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3941 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3945 */                                 if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3946 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                 }
/*      */                                 
/* 3949 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3950 */                                 out.write(10);
/* 3951 */                                 out.write(10);
/* 3952 */                                 if (_jspx_meth_logic_005fnotPresent_005f7(_jspx_page_context))
/*      */                                   return;
/* 3954 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 3955 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 3956 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3957 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 3958 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 3959 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 3960 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 3962 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 3965 */                                   out.write("\n                            <td width='");
/* 3966 */                                   out.print(width);
/* 3967 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 3968 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 3969 */                                   out.write("&nbsp;");
/* 3970 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 3971 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3975 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 3977 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 3979 */                                 String class1 = "yellowgrayborder";
/* 3980 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 3982 */                                   String ele = (String)resids.nextElement();
/* 3983 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 3985 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3989 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 3991 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 3993 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 3994 */                                   out.print(class1);
/* 3995 */                                   out.write("\" title=\"");
/* 3996 */                                   out.print(res_ids.getProperty(ele));
/* 3997 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3998 */                                   out.print(ele);
/* 3999 */                                   out.write(34);
/* 4000 */                                   out.write(62);
/* 4001 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 4002 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 4003 */                                   out.print(class1);
/* 4004 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4005 */                                   out.print(ele);
/* 4006 */                                   out.write("&attributeid=");
/* 4007 */                                   out.print(attids.get(0));
/* 4008 */                                   out.write("')\" class=\"staticlinks\">");
/* 4009 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 4010 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 4011 */                                   out.print(class1);
/* 4012 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4013 */                                   out.print(ele);
/* 4014 */                                   out.write("&attributeid=");
/* 4015 */                                   out.print(attids.get(1));
/* 4016 */                                   out.write("')\" class=\"staticlinks\">");
/* 4017 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 4018 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 4020 */                                   if (attids.size() > 2)
/*      */                                   {
/* 4022 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 4024 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 4027 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 4028 */                                         out.print(class1);
/* 4029 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 4031 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 4032 */                                         out.print(class1);
/* 4033 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4034 */                                         out.print(data_props.get((String)attids.get(i)));
/* 4035 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 4040 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4044 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 4049 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 4055 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 4058 */                       out.write(10);
/* 4059 */                       out.write(10);
/* 4060 */                       out.write(9);
/*      */                     }
/*      */                     
/* 4063 */                     if (request.getAttribute("mom_list") != null)
/*      */                     {
/*      */ 
/* 4066 */                       bol = true;
/* 4067 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("mom_list"));
/*      */                       
/* 4069 */                       out.write(10);
/* 4070 */                       out.write(9);
/* 4071 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 4075 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 4076 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 4078 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 4080 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 4083 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 4085 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 4087 */                           while (keys.hasMoreElements())
/*      */                           {
/* 4089 */                             String type = (String)keys.nextElement();
/* 4090 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4096 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 4097 */                               ArrayList attids = (ArrayList)al.get(0);
/* 4098 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 4099 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 4101 */                                 float width = 60.0F / (attids.size() - 2);
/* 4102 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 4103 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 4106 */                                 Enumeration res_id = ht_type.keys();
/* 4107 */                                 ArrayList resids1 = new ArrayList();
/* 4108 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 4110 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 4112 */                                   String elt = (String)res_id.nextElement();
/* 4113 */                                   resids1.add(elt);
/* 4114 */                                   Properties p = (Properties)ht_type.get(elt);
/* 4115 */                                   Enumeration prop = p.keys();
/* 4116 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 4118 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 4121 */                                 int colspan1 = attids.size();
/* 4122 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 4124 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 4125 */                                 out.print(colspan1);
/* 4126 */                                 out.write(34);
/* 4127 */                                 out.write(62);
/* 4128 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 4129 */                                 out.write("</td>\n\n");
/*      */                                 
/* 4131 */                                 PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4132 */                                 _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4133 */                                 _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                                 
/* 4135 */                                 _jspx_th_logic_005fpresent_005f8.setRole("ADMIN,OPERATOR");
/* 4136 */                                 int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4137 */                                 if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                   for (;;) {
/* 4139 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 4140 */                                     out.print(type);
/* 4141 */                                     out.write("&haid=");
/* 4142 */                                     out.print(request.getParameter("haid"));
/* 4143 */                                     out.write("')\" class=\"staticlinks1\">");
/* 4144 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 4145 */                                     out.write("</a></td>\n");
/* 4146 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4147 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4151 */                                 if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4152 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                 }
/*      */                                 
/* 4155 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4156 */                                 out.write(10);
/* 4157 */                                 out.write(10);
/* 4158 */                                 if (_jspx_meth_logic_005fnotPresent_005f8(_jspx_page_context))
/*      */                                   return;
/* 4160 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 4161 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 4162 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 4163 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 4164 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 4165 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 4166 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 4168 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 4171 */                                   out.write("\n                            <td width='");
/* 4172 */                                   out.print(width);
/* 4173 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 4174 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 4175 */                                   out.write("&nbsp;");
/* 4176 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 4177 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4181 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 4183 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 4185 */                                 String class1 = "yellowgrayborder";
/* 4186 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 4188 */                                   String ele = (String)resids.nextElement();
/* 4189 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 4191 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4195 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 4197 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 4199 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 4200 */                                   out.print(class1);
/* 4201 */                                   out.write("\" title=\"");
/* 4202 */                                   out.print(res_ids.getProperty(ele));
/* 4203 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4204 */                                   out.print(ele);
/* 4205 */                                   out.write(34);
/* 4206 */                                   out.write(62);
/* 4207 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 4208 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 4209 */                                   out.print(class1);
/* 4210 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4211 */                                   out.print(ele);
/* 4212 */                                   out.write("&attributeid=");
/* 4213 */                                   out.print(attids.get(0));
/* 4214 */                                   out.write("')\" class=\"staticlinks\">");
/* 4215 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 4216 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 4217 */                                   out.print(class1);
/* 4218 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4219 */                                   out.print(ele);
/* 4220 */                                   out.write("&attributeid=");
/* 4221 */                                   out.print(attids.get(1));
/* 4222 */                                   out.write("')\" class=\"staticlinks\">");
/* 4223 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 4224 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 4226 */                                   if (attids.size() > 2)
/*      */                                   {
/* 4228 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 4230 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 4233 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 4234 */                                         out.print(class1);
/* 4235 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 4237 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 4238 */                                         out.print(class1);
/* 4239 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4240 */                                         out.print(data_props.get((String)attids.get(i)));
/* 4241 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 4246 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4250 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 4255 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 4261 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 4264 */                       out.write(10);
/* 4265 */                       out.write(10);
/* 4266 */                       out.write(9);
/*      */                     }
/*      */                     
/* 4269 */                     if (request.getAttribute("vir_list") != null)
/*      */                     {
/*      */ 
/* 4272 */                       bol = true;
/* 4273 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("vir_list"));
/*      */                       
/* 4275 */                       out.write(10);
/* 4276 */                       out.write(9);
/* 4277 */                       out.write("<!--$Id$-->    \n\n\n\n        ");
/*      */                       
/*      */                       try
/*      */                       {
/* 4281 */                         Properties res_dis_mapper = (Properties)request.getAttribute("res_dis_mapper");
/* 4282 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/* 4284 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 4286 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/*      */ 
/* 4289 */                           out.write("\n        <table width=\"99%\" >\n            ");
/*      */                           
/* 4291 */                           Enumeration keys = ser_list.keys();
/*      */                           
/* 4293 */                           while (keys.hasMoreElements())
/*      */                           {
/* 4295 */                             String type = (String)keys.nextElement();
/* 4296 */                             if (!type.equals("am_resids"))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4302 */                               ArrayList al = (ArrayList)configdetails.get(type);
/* 4303 */                               ArrayList attids = (ArrayList)al.get(0);
/* 4304 */                               ArrayList props_att = (ArrayList)al.get(1);
/* 4305 */                               if ((props_att != null) && (props_att.size() > 0))
/*      */                               {
/* 4307 */                                 float width = 60.0F / (attids.size() - 2);
/* 4308 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 4309 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/*      */                                 
/*      */ 
/* 4312 */                                 Enumeration res_id = ht_type.keys();
/* 4313 */                                 ArrayList resids1 = new ArrayList();
/* 4314 */                                 ArrayList attids1 = new ArrayList();
/*      */                                 
/* 4316 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 4318 */                                   String elt = (String)res_id.nextElement();
/* 4319 */                                   resids1.add(elt);
/* 4320 */                                   Properties p = (Properties)ht_type.get(elt);
/* 4321 */                                   Enumeration prop = p.keys();
/* 4322 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 4324 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/* 4327 */                                 int colspan1 = attids.size();
/* 4328 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 4330 */                                 out.write("\n            <tr>\n                <td>\n                    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 4331 */                                 out.print(colspan1);
/* 4332 */                                 out.write(34);
/* 4333 */                                 out.write(62);
/* 4334 */                                 out.print(FormatUtil.getString(res_dis_mapper.getProperty(type)));
/* 4335 */                                 out.write("</td>\n\n");
/*      */                                 
/* 4337 */                                 PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4338 */                                 _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 4339 */                                 _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                                 
/* 4341 */                                 _jspx_th_logic_005fpresent_005f9.setRole("ADMIN,OPERATOR");
/* 4342 */                                 int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 4343 */                                 if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                   for (;;) {
/* 4345 */                                     out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=");
/* 4346 */                                     out.print(type);
/* 4347 */                                     out.write("&haid=");
/* 4348 */                                     out.print(request.getParameter("haid"));
/* 4349 */                                     out.write("')\" class=\"staticlinks1\">");
/* 4350 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 4351 */                                     out.write("</a></td>\n");
/* 4352 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 4353 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4357 */                                 if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 4358 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                 }
/*      */                                 
/* 4361 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4362 */                                 out.write(10);
/* 4363 */                                 out.write(10);
/* 4364 */                                 if (_jspx_meth_logic_005fnotPresent_005f9(_jspx_page_context))
/*      */                                   return;
/* 4366 */                                 out.write("\n\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 4367 */                                 out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 4368 */                                 out.write("</h1></td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 4369 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(0)).getProperty("DISPLAYNAME")));
/* 4370 */                                 out.write("&nbsp;</td>\n                            <td width=\"10%\" align=\"center\" class=\"columnheading\">");
/* 4371 */                                 out.print(FormatUtil.getString(((Properties)props_att.get(1)).getProperty("DISPLAYNAME")));
/* 4372 */                                 out.write("&nbsp;</td>\n                            ");
/*      */                                 
/* 4374 */                                 for (int i = 2; i < attids.size(); i++)
/*      */                                 {
/*      */ 
/* 4377 */                                   out.write("\n                            <td width='");
/* 4378 */                                   out.print(width);
/* 4379 */                                   out.write("%' align=\"left\" class=\"columnheading\">");
/* 4380 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("DISPLAYNAME")));
/* 4381 */                                   out.write("&nbsp;");
/* 4382 */                                   out.print(FormatUtil.getString(((Properties)props_att.get(i)).getProperty("UNITS")));
/* 4383 */                                   out.write("</td>\n                            ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4387 */                                 out.write("\n                        </tr>\n                        ");
/*      */                                 
/* 4389 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/* 4391 */                                 String class1 = "yellowgrayborder";
/* 4392 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 4394 */                                   String ele = (String)resids.nextElement();
/* 4395 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 4397 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4401 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 4403 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/* 4405 */                                   out.write("\n                               <tr>\n                                   <td width=\"15%\" align=\"left\" class=\"");
/* 4406 */                                   out.print(class1);
/* 4407 */                                   out.write("\" title=\"");
/* 4408 */                                   out.print(res_ids.getProperty(ele));
/* 4409 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4410 */                                   out.print(ele);
/* 4411 */                                   out.write(34);
/* 4412 */                                   out.write(62);
/* 4413 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 4414 */                                   out.write("</a></td>\n                                   <td width=\"15%\" align=\"center\" class=\"");
/* 4415 */                                   out.print(class1);
/* 4416 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4417 */                                   out.print(ele);
/* 4418 */                                   out.write("&attributeid=");
/* 4419 */                                   out.print(attids.get(0));
/* 4420 */                                   out.write("')\" class=\"staticlinks\">");
/* 4421 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 4422 */                                   out.write("</a></td>\n                                   <td width=\"15%\"  align=\"center\" class=\"");
/* 4423 */                                   out.print(class1);
/* 4424 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4425 */                                   out.print(ele);
/* 4426 */                                   out.write("&attributeid=");
/* 4427 */                                   out.print(attids.get(1));
/* 4428 */                                   out.write("')\" class=\"staticlinks\">");
/* 4429 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 4430 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 4432 */                                   if (attids.size() > 2)
/*      */                                   {
/* 4434 */                                     for (int i = 2; i < attids.size(); i++)
/*      */                                     {
/* 4436 */                                       if (data_props.get((String)attids.get(i)) == null)
/*      */                                       {
/*      */ 
/* 4439 */                                         out.write("\n\t\t\t\t\t\t\t\t\t   <td  width=20%\" align=\"left\" class=\"");
/* 4440 */                                         out.print(class1);
/* 4441 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-</td>\n\t\t\t\t\t\t\t\t\t   ");
/*      */                                       } else {
/* 4443 */                                         out.write("\n                                   <td  width=20%\" align=\"left\" class=\"");
/* 4444 */                                         out.print(class1);
/* 4445 */                                         out.write("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 4446 */                                         out.print(data_props.get((String)attids.get(i)));
/* 4447 */                                         out.write("</td>\n                                   \n                                 \n                                   ");
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 4452 */                                   out.write("\n                                   \n                                   \n                                   \n                                   \n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4456 */                                 out.write("\n                    </table><br>\n                </td>\n               \n            </tr>\n             ");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           
/* 4461 */                           out.write("\n        \n        ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 4467 */                         exc.printStackTrace();
/*      */                       }
/*      */                       
/* 4470 */                       out.write(10);
/* 4471 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 4474 */                     if (request.getAttribute("sys_list") != null)
/*      */                     {
/* 4476 */                       bol = true;
/* 4477 */                       pageContext.setAttribute("ser_list", (Hashtable)request.getAttribute("sys_list"));
/*      */                       try
/*      */                       {
/* 4480 */                         Hashtable server_props = (Hashtable)request.getAttribute("server_props");
/* 4481 */                         ArrayList systems_list = (ArrayList)request.getAttribute("systems_list");
/* 4482 */                         Properties host_img_mapper = (Properties)request.getAttribute("host_img_mapper");
/* 4483 */                         ArrayList lin_att = (ArrayList)request.getAttribute("lin_att");
/* 4484 */                         ArrayList lin_att_id = (ArrayList)lin_att.get(0);
/* 4485 */                         ArrayList lin_att_name = (ArrayList)lin_att.get(1);
/* 4486 */                         ArrayList lin_att_disp_name = (ArrayList)lin_att.get(2);
/* 4487 */                         ArrayList lin_att_units = (ArrayList)lin_att.get(3);
/* 4488 */                         float width = 60.0F / (lin_att_id.size() - 2);
/* 4489 */                         int colspan1 = lin_att_id.size();
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4521 */                         if ((pageContext.getAttribute("ser_list") != null) && (((Hashtable)pageContext.getAttribute("ser_list")).size() > 0))
/*      */                         {
/*      */ 
/* 4524 */                           Hashtable configdetails = (Hashtable)request.getAttribute("configdetails");
/*      */                           
/* 4526 */                           Hashtable ser_list = (Hashtable)pageContext.getAttribute("ser_list");
/*      */                           
/* 4528 */                           out.write("\n        \n        <br>\n        <br>\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n                        <tr>\n                            <td class=\"tableheading\" colspan=\"");
/* 4529 */                           out.print(colspan1);
/* 4530 */                           out.write(34);
/* 4531 */                           out.write(62);
/* 4532 */                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.system"));
/* 4533 */                           out.write("</td>\n\n");
/*      */                           
/* 4535 */                           PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4536 */                           _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4537 */                           _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */                           
/* 4539 */                           _jspx_th_logic_005fpresent_005f10.setRole("ADMIN,OPERATOR");
/* 4540 */                           int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4541 */                           if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                             for (;;) {
/* 4543 */                               out.write("\n                            <td class=\"tableheading\" align=\"right\"><a href=\"javascript:updateView('/jsp/EditMGView.jsp?type=Linux&haid=");
/* 4544 */                               out.print(request.getParameter("haid"));
/* 4545 */                               out.write("')\" class=\"staticlinks1\">");
/* 4546 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.editview"));
/* 4547 */                               out.write("</a></td>\n\n");
/* 4548 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4549 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4553 */                           if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4554 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                           }
/*      */                           
/* 4557 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4558 */                           out.write(10);
/* 4559 */                           out.write(10);
/* 4560 */                           if (_jspx_meth_logic_005fnotPresent_005f10(_jspx_page_context))
/*      */                             return;
/* 4562 */                           out.write("\n                        </tr>\n                        <tr>\n                            <td width=\"20%\" class=\"columnheading\">");
/* 4563 */                           out.print(FormatUtil.getString("webclient.monitorgroupusdetails.Name"));
/* 4564 */                           out.write("</td>\n                            <td width='10%' align=\"center\" class=\"columnheading\">");
/* 4565 */                           out.print(FormatUtil.getString((String)lin_att_disp_name.get(0)));
/* 4566 */                           out.write("</td>\n                            <td width='10%' align=\"center\" class=\"columnheading\">");
/* 4567 */                           out.print(FormatUtil.getString((String)lin_att_disp_name.get(1)));
/* 4568 */                           out.write("</td>\n                            ");
/*      */                           
/* 4570 */                           for (int i = 2; i < lin_att_id.size(); i++)
/*      */                           {
/*      */ 
/* 4573 */                             out.write("\n                            <td width='");
/* 4574 */                             out.print(width);
/* 4575 */                             out.write("%' align=\"center\" class=\"columnheading\">");
/* 4576 */                             out.print(FormatUtil.getString((String)lin_att_disp_name.get(i)));
/* 4577 */                             out.write("&nbsp;");
/* 4578 */                             out.print(lin_att_units.get(i));
/* 4579 */                             out.write("</td>\n                            ");
/*      */                           }
/*      */                           
/*      */ 
/* 4583 */                           out.write("\n                        </tr>\n            \n            ");
/*      */                           
/* 4585 */                           Enumeration keys = ser_list.keys();
/* 4586 */                           String class1 = "yellowgrayborder";
/* 4587 */                           while (keys.hasMoreElements())
/*      */                           {
/*      */                             try
/*      */                             {
/* 4591 */                               String type = (String)keys.nextElement();
/* 4592 */                               if (!type.equals("am_resids"))
/*      */                               {
/*      */ 
/*      */ 
/* 4596 */                                 Properties names_id_mapper = (Properties)server_props.get(type);
/*      */                                 
/* 4598 */                                 ArrayList al = (ArrayList)configdetails.get(type);
/*      */                                 
/* 4600 */                                 ArrayList attids = (ArrayList)al.get(0);
/* 4601 */                                 ArrayList props_att = (ArrayList)al.get(1);
/*      */                                 
/* 4603 */                                 Hashtable ht_type = (Hashtable)ser_list.get(type);
/* 4604 */                                 Properties res_ids = (Properties)ser_list.get("am_resids");
/* 4605 */                                 Enumeration res_id = ht_type.keys();
/* 4606 */                                 ArrayList resids1 = new ArrayList();
/* 4607 */                                 ArrayList attids1 = new ArrayList();
/* 4608 */                                 while (res_id.hasMoreElements())
/*      */                                 {
/* 4610 */                                   String elt = (String)res_id.nextElement();
/* 4611 */                                   resids1.add(elt);
/* 4612 */                                   Properties p = (Properties)ht_type.get(elt);
/* 4613 */                                   Enumeration prop = p.keys();
/* 4614 */                                   while (prop.hasMoreElements())
/*      */                                   {
/* 4616 */                                     attids1.add(prop.nextElement());
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 4620 */                                 Properties alert = getStatus(resids1, attids1);
/*      */                                 
/* 4622 */                                 Enumeration resids = ht_type.keys();
/*      */                                 
/*      */ 
/* 4625 */                                 while (resids.hasMoreElements())
/*      */                                 {
/* 4627 */                                   String ele = (String)resids.nextElement();
/* 4628 */                                   if (class1.equals("yellowgrayborder"))
/*      */                                   {
/* 4630 */                                     class1 = "whitegrayborder";
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4634 */                                     class1 = "yellowgrayborder";
/*      */                                   }
/* 4636 */                                   Properties data_props = (Properties)ht_type.get(ele);
/*      */                                   
/*      */ 
/* 4639 */                                   out.write("\n                               <tr>\n                                   <td width=\"20%\" align=\"left\" class=\"");
/* 4640 */                                   out.print(class1);
/* 4641 */                                   out.write("\" title=\"");
/* 4642 */                                   out.print(res_ids.getProperty(ele));
/* 4643 */                                   out.write("\"><a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4644 */                                   out.print(ele);
/* 4645 */                                   out.write("\"><img src=\"");
/* 4646 */                                   out.print(host_img_mapper.getProperty(type));
/* 4647 */                                   out.write("\" title=\"");
/* 4648 */                                   out.print(type);
/* 4649 */                                   out.write("\" border=\"0\">");
/* 4650 */                                   out.print(getTrimmedText(res_ids.getProperty(ele), 25));
/* 4651 */                                   out.write("</a></td>\n                                   <td width=\"10%\" align=\"center\" class=\"");
/* 4652 */                                   out.print(class1);
/* 4653 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4654 */                                   out.print(ele);
/* 4655 */                                   out.write("&attributeid=");
/* 4656 */                                   out.print(attids.get(0));
/* 4657 */                                   out.write("')\" class=\"staticlinks\">");
/* 4658 */                                   out.print(getSeverityImageForAvailability(alert.getProperty(ele + "#" + attids.get(0))));
/* 4659 */                                   out.write("</a></td>\n                                   <td width=\"10%\" align=\"center\" class=\"");
/* 4660 */                                   out.print(class1);
/* 4661 */                                   out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4662 */                                   out.print(ele);
/* 4663 */                                   out.write("&attributeid=");
/* 4664 */                                   out.print(attids.get(1));
/* 4665 */                                   out.write("')\" class=\"staticlinks\">");
/* 4666 */                                   out.print(getSeverityImageForHealth(alert.getProperty(ele + "#" + attids.get(1))));
/* 4667 */                                   out.write("</a></td>\n                                   ");
/*      */                                   
/* 4669 */                                   if (lin_att_id.size() > 2)
/*      */                                   {
/* 4671 */                                     for (int i = 2; i < lin_att_id.size(); i++)
/*      */                                     {
/* 4673 */                                       String att = (String)lin_att_name.get(i);
/* 4674 */                                       String id = names_id_mapper.getProperty(att);
/* 4675 */                                       String val = "-";
/* 4676 */                                       if ((id != null) && (data_props.get(id) != null))
/*      */                                       {
/* 4678 */                                         val = (String)data_props.get(id);
/*      */                                       }
/*      */                                       
/* 4681 */                                       out.write("\n                                   <td  width='");
/* 4682 */                                       out.print(width);
/* 4683 */                                       out.write("%' align=\"center\" class=\"");
/* 4684 */                                       out.print(class1);
/* 4685 */                                       out.write(34);
/* 4686 */                                       out.write(62);
/* 4687 */                                       out.print(val);
/* 4688 */                                       out.write("</td>\n                                   ");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 4693 */                                   out.write("\n                               </tr>\n                               ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4697 */                                 out.write("\n             ");
/*      */                               }
/*      */                             }
/*      */                             catch (Exception exc) {}
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/* 4706 */                           out.write("\n        </table>\n        <br>\n        ");
/*      */                         }
/*      */                       }
/*      */                       catch (Exception exc) {}
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4716 */                       out.write("\n        ");
/*      */                     }
/*      */                     
/* 4719 */                     if (!bol)
/*      */                     {
/*      */ 
/* 4722 */                       out.write("\n        <br>\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n          <tr> \n            <td width=\"72%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4723 */                       out.print(FormatUtil.getString("am.webclient.monitorgroup.details"));
/* 4724 */                       out.write("</td>\n          </tr>\n          <tr>\n          <td width=\"72%\" height=\"26\" class=\"bodytext\">&nbsp;");
/* 4725 */                       out.print(FormatUtil.getString("am.webclient.monitorgroup.nomonitor"));
/* 4726 */                       out.write(" \n          </td>\n          </tr>\n        </table>\n   ");
/*      */                     }
/*      */                     
/*      */ 
/* 4730 */                     out.write(10);
/*      */                   }
/* 4732 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4733 */         out = _jspx_out;
/* 4734 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4735 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4736 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4739 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4745 */     PageContext pageContext = _jspx_page_context;
/* 4746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4748 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4749 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 4750 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 4752 */     _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN,OPERATOR");
/* 4753 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 4754 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 4756 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4757 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4758 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4762 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4763 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4764 */       return true;
/*      */     }
/* 4766 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4772 */     PageContext pageContext = _jspx_page_context;
/* 4773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4775 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4776 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 4777 */     _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */     
/* 4779 */     _jspx_th_logic_005fnotPresent_005f1.setRole("ADMIN,OPERATOR");
/* 4780 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 4781 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 4783 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4784 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 4785 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4789 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 4790 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4791 */       return true;
/*      */     }
/* 4793 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 4794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4799 */     PageContext pageContext = _jspx_page_context;
/* 4800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4802 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4803 */     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 4804 */     _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */     
/* 4806 */     _jspx_th_logic_005fnotPresent_005f2.setRole("ADMIN,OPERATOR");
/* 4807 */     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 4808 */     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */       for (;;) {
/* 4810 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4811 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 4812 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4816 */     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 4817 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4818 */       return true;
/*      */     }
/* 4820 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4826 */     PageContext pageContext = _jspx_page_context;
/* 4827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4829 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4830 */     _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 4831 */     _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */     
/* 4833 */     _jspx_th_logic_005fnotPresent_005f3.setRole("ADMIN,OPERATOR");
/* 4834 */     int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 4835 */     if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */       for (;;) {
/* 4837 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4838 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 4839 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4843 */     if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 4844 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 4845 */       return true;
/*      */     }
/* 4847 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 4848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4853 */     PageContext pageContext = _jspx_page_context;
/* 4854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4856 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4857 */     _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 4858 */     _jspx_th_logic_005fnotPresent_005f4.setParent(null);
/*      */     
/* 4860 */     _jspx_th_logic_005fnotPresent_005f4.setRole("ADMIN,OPERATOR");
/* 4861 */     int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 4862 */     if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */       for (;;) {
/* 4864 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4865 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 4866 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4870 */     if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 4871 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 4872 */       return true;
/*      */     }
/* 4874 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 4875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4880 */     PageContext pageContext = _jspx_page_context;
/* 4881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4883 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4884 */     _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 4885 */     _jspx_th_logic_005fnotPresent_005f5.setParent(null);
/*      */     
/* 4887 */     _jspx_th_logic_005fnotPresent_005f5.setRole("ADMIN,OPERATOR");
/* 4888 */     int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 4889 */     if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */       for (;;) {
/* 4891 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4892 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 4893 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4897 */     if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 4898 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 4899 */       return true;
/*      */     }
/* 4901 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 4902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4907 */     PageContext pageContext = _jspx_page_context;
/* 4908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4910 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4911 */     _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 4912 */     _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */     
/* 4914 */     _jspx_th_logic_005fnotPresent_005f6.setRole("ADMIN,OPERATOR");
/* 4915 */     int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 4916 */     if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */       for (;;) {
/* 4918 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4919 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 4920 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4924 */     if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 4925 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4926 */       return true;
/*      */     }
/* 4928 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4934 */     PageContext pageContext = _jspx_page_context;
/* 4935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4937 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4938 */     _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 4939 */     _jspx_th_logic_005fnotPresent_005f7.setParent(null);
/*      */     
/* 4941 */     _jspx_th_logic_005fnotPresent_005f7.setRole("ADMIN,OPERATOR");
/* 4942 */     int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 4943 */     if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */       for (;;) {
/* 4945 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4946 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 4947 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4951 */     if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 4952 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4953 */       return true;
/*      */     }
/* 4955 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4961 */     PageContext pageContext = _jspx_page_context;
/* 4962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4964 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4965 */     _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 4966 */     _jspx_th_logic_005fnotPresent_005f8.setParent(null);
/*      */     
/* 4968 */     _jspx_th_logic_005fnotPresent_005f8.setRole("ADMIN,OPERATOR");
/* 4969 */     int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 4970 */     if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */       for (;;) {
/* 4972 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 4973 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 4974 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4978 */     if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 4979 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 4980 */       return true;
/*      */     }
/* 4982 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 4983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4988 */     PageContext pageContext = _jspx_page_context;
/* 4989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4991 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4992 */     _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 4993 */     _jspx_th_logic_005fnotPresent_005f9.setParent(null);
/*      */     
/* 4995 */     _jspx_th_logic_005fnotPresent_005f9.setRole("ADMIN,OPERATOR");
/* 4996 */     int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 4997 */     if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */       for (;;) {
/* 4999 */         out.write("\n<td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 5000 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 5001 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5005 */     if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 5006 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 5007 */       return true;
/*      */     }
/* 5009 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 5010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5015 */     PageContext pageContext = _jspx_page_context;
/* 5016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5018 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f10 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 5019 */     _jspx_th_logic_005fnotPresent_005f10.setPageContext(_jspx_page_context);
/* 5020 */     _jspx_th_logic_005fnotPresent_005f10.setParent(null);
/*      */     
/* 5022 */     _jspx_th_logic_005fnotPresent_005f10.setRole("ADMIN,OPERATOR");
/* 5023 */     int _jspx_eval_logic_005fnotPresent_005f10 = _jspx_th_logic_005fnotPresent_005f10.doStartTag();
/* 5024 */     if (_jspx_eval_logic_005fnotPresent_005f10 != 0) {
/*      */       for (;;) {
/* 5026 */         out.write("\n\n <td class=\"tableheading\" align=\"right\">&nbsp;</td>\n\n");
/* 5027 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f10.doAfterBody();
/* 5028 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5032 */     if (_jspx_th_logic_005fnotPresent_005f10.doEndTag() == 5) {
/* 5033 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10);
/* 5034 */       return true;
/*      */     }
/* 5036 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f10);
/* 5037 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowApplicationDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */