/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class AlarmLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  812 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  813 */     getRCATrimmedText(div1, rca);
/*  814 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  817 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  818 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  853 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  854 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  857 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  858 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  859 */       set = AMConnectionPool.executeQueryStmt(query);
/*  860 */       if (set.next())
/*      */       {
/*  862 */         String helpLink = set.getString("LINK");
/*  863 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
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
/*  897 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*  911 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*  971 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1287 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1985 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2040 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
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
/* 2182 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2183 */   static { _jspx_dependants.put("/jsp/includes/AlertLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/includes/ApplicationLinks.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2218 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2230 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2238 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2241 */     JspWriter out = null;
/* 2242 */     Object page = this;
/* 2243 */     JspWriter _jspx_out = null;
/* 2244 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2248 */       response.setContentType("text/html;charset=UTF-8");
/* 2249 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2251 */       _jspx_page_context = pageContext;
/* 2252 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2253 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2254 */       session = pageContext.getSession();
/* 2255 */       out = pageContext.getOut();
/* 2256 */       _jspx_out = out;
/*      */       
/* 2258 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n");
/* 2259 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2261 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2262 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2264 */       out.write(10);
/* 2265 */       out.write(10);
/* 2266 */       out.write(10);
/* 2267 */       out.write(10);
/* 2268 */       out.write(32);
/* 2269 */       out.write(32);
/* 2270 */       out.write("\n\n\n\n");
/*      */       
/* 2272 */       String critical = "0";
/* 2273 */       String warning = "0";
/* 2274 */       String clear = "0";
/* 2275 */       int criticalper = 0;
/* 2276 */       int clearper = 0;
/* 2277 */       int warningper = 0;
/* 2278 */       int total = 0;
/*      */       try
/*      */       {
/* 2281 */         if (request.getAttribute("alertdetails") != null) {
/* 2282 */           Hashtable ht = (Hashtable)request.getAttribute("alertdetails");
/*      */           
/*      */ 
/* 2285 */           critical = (String)ht.get("Critical");
/* 2286 */           warning = (String)ht.get("Warning");
/* 2287 */           clear = (String)ht.get("Clear");
/* 2288 */           total = Integer.parseInt(critical) + Integer.parseInt(warning) + Integer.parseInt(clear);
/* 2289 */           if (total > 0)
/*      */           {
/* 2291 */             criticalper = Integer.parseInt(critical) * 100 / total;
/* 2292 */             clearper = Integer.parseInt(clear) * 100 / total;
/* 2293 */             warningper = Integer.parseInt(warning) * 100 / total;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception alertcount)
/*      */       {
/* 2299 */         alertcount.printStackTrace();
/*      */       }
/*      */       
/* 2302 */       out.write(10);
/*      */       
/* 2304 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2305 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2306 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/* 2308 */       _jspx_th_c_005fif_005f0.setTest("${!empty isAlertsPage}");
/* 2309 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2310 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/* 2312 */           out.write(10);
/* 2313 */           out.write(10);
/* 2314 */           out.write(32);
/*      */           
/* 2316 */           if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */           {
/*      */ 
/* 2319 */             out.write("\n\n\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n    \t<tr>\n\n    \t\t<td  class=\"leftlinksheading \" colspan=\"3\" height=\"20\">");
/* 2320 */             out.print(FormatUtil.getString("Total Alarms"));
/* 2321 */             out.write(32);
/* 2322 */             out.write(40);
/* 2323 */             out.print(total);
/* 2324 */             out.write(")</td>\n\n    \t</tr>\n    <tr>\n\n    \t<td>\n    \t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  width=\"100%\" style=\"background-color:#ffffff; font-size:11px; font-family:Arial, Helvetica, sans-serif;\" >\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n    \t\t\t<tr><td width=\"1%\"></td>\n    \t\t\t\t<td width=\"28%\">&nbsp;");
/* 2325 */             out.print(FormatUtil.getString("Critical"));
/* 2326 */             out.write("</td>\n    \t\t\t\t<td  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" href=\"javascript:fnCallLink('Critical',");
/* 2327 */             out.print(critical);
/* 2328 */             out.write(")\" title=\"Critical : ");
/* 2329 */             out.print(critical);
/* 2330 */             out.write(32);
/* 2331 */             out.write(34);
/* 2332 */             out.write(62);
/* 2333 */             out.print(critical);
/* 2334 */             out.write("</a></td>\n    \t\t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t         <tbody><tr>\n\n\t\t\t\t          <td  align=\"center\" title=\"Critical : ");
/* 2335 */             out.print(critical);
/* 2336 */             out.write(" \" onclick=\"javascript:fnCallLink('Critical',");
/* 2337 */             out.print(critical);
/* 2338 */             out.write(")\" style=\"background-color: #ff1616; cursor:pointer; background-position: 18px 50%;\" width=");
/* 2339 */             out.print(criticalper);
/* 2340 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td  align=\"center\" onclick=\"javascript:fnCallLink('Critical',");
/* 2341 */             out.print(critical);
/* 2342 */             out.write(")\"  title=\"Critical : ");
/* 2343 */             out.print(critical);
/* 2344 */             out.write(" \" style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 2345 */             out.print(100 - criticalper);
/* 2346 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t        </tr>\n       </tbody></table></td>\n    \t\t\t</tr>\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n\t\t\t    \t\t\t<tr>\n\t\t\t    \t\t\t<td width=\"1%\"></td>\n\t\t\t    \t\t\t<td width=\"10%\">&nbsp;");
/* 2347 */             out.print(FormatUtil.getString("Warning"));
/* 2348 */             out.write("</td>\n\t\t\t    \t\t\t<td width=\"10%\"  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" title=\"Warning : ");
/* 2349 */             out.print(warning);
/* 2350 */             out.write(" \" href=\"javascript:fnCallLink('Warning',");
/* 2351 */             out.print(warning);
/* 2352 */             out.write(41);
/* 2353 */             out.write(34);
/* 2354 */             out.write(62);
/* 2355 */             out.print(warning);
/* 2356 */             out.write("</a></td>\n\t\t\t    \t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t\t\t<tbody><tr>\n\n\t\t\t\t\t\t<td align=\"center\" onclick=\"javascript:fnCallLink('Warning',");
/* 2357 */             out.print(warning);
/* 2358 */             out.write(")\" title=\"Warning : ");
/* 2359 */             out.print(warning);
/* 2360 */             out.write(" \" class=\"barGraphBg\" style=\"background-color: #ea940e; cursor:pointer; background-position: 42px 50%;\" width=");
/* 2361 */             out.print(warningper);
/* 2362 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t<td align=\"center\" title=\"Warning : ");
/* 2363 */             out.print(warning);
/* 2364 */             out.write(" \" onclick=\"javascript:fnCallLink('Warning',");
/* 2365 */             out.print(warning);
/* 2366 */             out.write(")\"  style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 2367 */             out.print(100 - warningper);
/* 2368 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t\t\t\t        </tr>\n       </tbody></table></td>\n    \t\t\t</tr>\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n\t\t\t    \t\t\t<tr><td width=\"1%\"></td>\n\t\t\t    \t\t\t\t<td>&nbsp;");
/* 2369 */             out.print(FormatUtil.getString("Clear"));
/* 2370 */             out.write("</td>\n\t\t\t    \t\t\t\t<td  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" title=\"Clear : ");
/* 2371 */             out.print(clear);
/* 2372 */             out.write(" \" href=\"javascript:fnCallLink('Clear',");
/* 2373 */             out.print(clear);
/* 2374 */             out.write(41);
/* 2375 */             out.write(34);
/* 2376 */             out.write(62);
/* 2377 */             out.print(clear);
/* 2378 */             out.write("</a> </td>\n\t\t\t    \t\t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t\t\t\t         <tbody><tr>\n\n\t\t\t\t\t\t\t          <td align=\"center\" class=\"barGraphBg\" title=\"Clear : ");
/* 2379 */             out.print(clear);
/* 2380 */             out.write(" \" onclick=\"javascript:fnCallLink('Clear',");
/* 2381 */             out.print(clear);
/* 2382 */             out.write(")\" style=\"background-color: #38da43; cursor:pointer; background-position: 42px 50%;\" width=");
/* 2383 */             out.print(clearper);
/* 2384 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t\t          <td align=\"center\" title=\"Clear : ");
/* 2385 */             out.print(clear);
/* 2386 */             out.write(" \" onclick=\"javascript:fnCallLink('Clear',");
/* 2387 */             out.print(clear);
/* 2388 */             out.write(")\"   style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 2389 */             out.print(100 - clearper);
/* 2390 */             out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t\t\t\t        </tr>\n\n\n\n\n\n       </tbody></table></td>\n    \t\t\t</tr>\n\n\n\n    \t\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n    \t\t</table>\n    \t</td>\n    \t<td class=\"status-right-bg\"></td>\n    </tr>\n\n</table>\n\n\n\n ");
/*      */           }
/*      */           
/*      */ 
/* 2394 */           out.write(10);
/* 2395 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2396 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2400 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2401 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 2404 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2405 */         out.write("\n\n\n\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr>\n    <td height=\"21\" class=\"leftlinksheading\">");
/* 2406 */         out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 2407 */         out.write("</td>\n  </tr>\n\n\t    <tr>\n    <td class=\"leftlinkstd\">\n");
/*      */         
/* 2409 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2410 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2411 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2412 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2413 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/* 2415 */             out.write(10);
/*      */             
/* 2417 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2418 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2419 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/* 2421 */             _jspx_th_c_005fwhen_005f0.setTest("${(param.viewId !='Alerts')}");
/* 2422 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2423 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/* 2425 */                 out.write(10);
/*      */                 
/* 2427 */                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                 {
/* 2429 */                   out.write("\n <a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts\" class=\"new-left-links\">");
/* 2430 */                   out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 2431 */                   out.write("</a>\n");
/*      */                 } else {
/* 2433 */                   out.write("\n <a href=\"/fault/AMAlarmView.do?displayName=All Alerts&monitor=MSSQL-DB-server&viewId=Alerts\" class=\"new-left-links\">");
/* 2434 */                   out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 2435 */                   out.write("</a>\n");
/*      */                 }
/*      */                 
/*      */ 
/* 2439 */                 out.write(10);
/* 2440 */                 out.write(10);
/* 2441 */                 out.write(32);
/* 2442 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2443 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2447 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2448 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/* 2451 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2452 */             out.write(10);
/* 2453 */             out.write(32);
/*      */             
/* 2455 */             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2456 */             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2457 */             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2458 */             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2459 */             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */               for (;;) {
/* 2461 */                 out.write(10);
/* 2462 */                 out.write(32);
/* 2463 */                 out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 2464 */                 out.write(10);
/* 2465 */                 out.write(32);
/* 2466 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2467 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2471 */             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2472 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */             }
/*      */             
/* 2475 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2476 */             out.write(10);
/* 2477 */             out.write(32);
/* 2478 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2479 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2483 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2484 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/* 2487 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2488 */           out.write("\n\n</td>\n        </tr>\n\n        <tr>\n    <td class=\"leftlinkstd\">\n\n    ");
/*      */           
/* 2490 */           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2491 */           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2492 */           _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2493 */           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2494 */           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */             for (;;) {
/* 2496 */               out.write("\n    ");
/*      */               
/* 2498 */               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2499 */               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2500 */               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */               
/* 2502 */               _jspx_th_c_005fwhen_005f1.setTest("${param.viewId !='Alerts.2'}");
/* 2503 */               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2504 */               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                 for (;;) {
/* 2506 */                   out.write(10);
/*      */                   
/* 2508 */                   if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                   {
/* 2510 */                     out.write("\n\t   <a href=\"/fault/AlarmView.do?displayName=Last one hour Alerts&viewId=Alerts.2\" class=\"new-left-links\">");
/* 2511 */                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2512 */                     out.write("</a>\n");
/*      */                   } else {
/* 2514 */                     out.write("\n   \t   <a href=\"/fault/AMAlarmView.do?displayName=Last one hour Alerts&monitor=MSSQL-DB-server&viewId=Alerts.2\" class=\"new-left-links\">");
/* 2515 */                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2516 */                     out.write("</a>\n");
/*      */                   }
/* 2518 */                   out.write(10);
/* 2519 */                   out.write(9);
/* 2520 */                   out.write(32);
/* 2521 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2522 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2526 */               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2527 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */               }
/*      */               
/* 2530 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2531 */               out.write("\n     ");
/*      */               
/* 2533 */               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2534 */               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2535 */               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2536 */               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2537 */               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                 for (;;) {
/* 2539 */                   out.write("\n    \t");
/* 2540 */                   out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 2541 */                   out.write("\n     ");
/* 2542 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2543 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2547 */               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2548 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */               }
/*      */               
/* 2551 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2552 */               out.write(10);
/* 2553 */               out.write(32);
/* 2554 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2555 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2559 */           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2560 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */           }
/*      */           else {
/* 2563 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2564 */             out.write("\n\n\n    </td>\n        </tr>\n<tr>\n\n\n    <td class=\"leftlinkstd\">\n    ");
/*      */             
/* 2566 */             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2567 */             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2568 */             _jspx_th_c_005fchoose_005f2.setParent(null);
/* 2569 */             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2570 */             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */               for (;;) {
/* 2572 */                 out.write("\n    ");
/*      */                 
/* 2574 */                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2575 */                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2576 */                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                 
/* 2578 */                 _jspx_th_c_005fwhen_005f2.setTest("${param.viewId !='Alerts.4'}");
/* 2579 */                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2580 */                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                   for (;;) {
/* 2582 */                     out.write(10);
/* 2583 */                     out.write(10);
/*      */                     
/* 2585 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/* 2587 */                       out.write("\n             <a href=\"/fault/AlarmView.do?displayName=Last one day Alerts&viewId=Alerts.4\" class=\"new-left-links\">\n    ");
/* 2588 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 2589 */                       out.write("</a>\n");
/*      */                     } else {
/* 2591 */                       out.write("\n             <a href=\"/fault/AMAlarmView.do?displayName=Last one day Alerts&monitor=MSSQL-DB-server&viewId=Alerts.4\" class=\"new-left-links\">\n    ");
/* 2592 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 2593 */                       out.write("</a>\n");
/*      */                     }
/* 2595 */                     out.write("\n\n     ");
/* 2596 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2597 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2601 */                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2602 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                 }
/*      */                 
/* 2605 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2606 */                 out.write("\n     ");
/*      */                 
/* 2608 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2609 */                 _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2610 */                 _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2611 */                 int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2612 */                 if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                   for (;;) {
/* 2614 */                     out.write("\n    \t ");
/* 2615 */                     out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 2616 */                     out.write("\n     ");
/* 2617 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2618 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2622 */                 if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2623 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                 }
/*      */                 
/* 2626 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2627 */                 out.write(10);
/* 2628 */                 out.write(32);
/* 2629 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2630 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2634 */             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2635 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*      */             }
/*      */             else {
/* 2638 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2639 */               out.write("\n\n</td>\n        </tr>\n</table>\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 2640 */               out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.trapheading.text"));
/* 2641 */               out.write("</td>\n</tr>\n<tr>\n\t");
/*      */               
/* 2643 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2644 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2645 */               _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */               
/* 2647 */               _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 2648 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2649 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2651 */                   out.write("\n\t<td class=\"leftlinkstd\">\n\t");
/*      */                   
/* 2653 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2654 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2655 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/* 2656 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2657 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 2659 */                       out.write(10);
/* 2660 */                       out.write(9);
/*      */                       
/* 2662 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2663 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2664 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 2666 */                       _jspx_th_c_005fwhen_005f3.setTest("${param.monitor !='Trap'}");
/* 2667 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2668 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 2670 */                           out.write("\n\t<a href=\"/fault/AMAlarmView.do?displayName=Traps%20Received&monitor=Trap\" class=\"new-left-links\">");
/* 2671 */                           out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 2672 */                           out.write("</a>\n\t");
/* 2673 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2674 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2678 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2679 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 2682 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2683 */                       out.write(10);
/* 2684 */                       out.write(9);
/*      */                       
/* 2686 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2687 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2688 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2689 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2690 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 2692 */                           out.write(10);
/* 2693 */                           out.write(9);
/* 2694 */                           out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 2695 */                           out.write(10);
/* 2696 */                           out.write(9);
/* 2697 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2698 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2702 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2703 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 2706 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2707 */                       out.write(10);
/* 2708 */                       out.write(9);
/* 2709 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2710 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2714 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2715 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 2718 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2719 */                   out.write("\n\t</td>\n\t");
/* 2720 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2721 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2725 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2726 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */               }
/*      */               else {
/* 2729 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2730 */                 out.write(10);
/* 2731 */                 out.write(9);
/*      */                 
/* 2733 */                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2734 */                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2735 */                 _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                 
/* 2737 */                 _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 2738 */                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2739 */                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                   for (;;) {
/* 2741 */                     out.write("\n\n\t<td class=\"leftlinkstd\">\n\n<a href=\"#\" class=\"disabledlink\">\t");
/* 2742 */                     out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 2743 */                     out.write("</a>\n</td>\n");
/* 2744 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2745 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2749 */                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2750 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                 }
/*      */                 else {
/* 2753 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2754 */                   out.write("\n</tr>\n<tr>\n\t");
/*      */                   
/* 2756 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2757 */                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2758 */                   _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                   
/* 2760 */                   _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 2761 */                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2762 */                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 2764 */                       out.write("\n\n\t<td class=\"leftlinkstd\">\n\t");
/*      */                       
/* 2766 */                       ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2767 */                       _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2768 */                       _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_logic_005fnotPresent_005f1);
/* 2769 */                       int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2770 */                       if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                         for (;;) {
/* 2772 */                           out.write(10);
/* 2773 */                           out.write(9);
/*      */                           
/* 2775 */                           WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2776 */                           _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2777 */                           _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                           
/* 2779 */                           _jspx_th_c_005fwhen_005f4.setTest("${param.viewId !='Trap'}");
/* 2780 */                           int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2781 */                           if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                             for (;;) {
/* 2783 */                               out.write("\n\t<a href=\"/fault/AMAlarmView.do?viewId=Trap\" class=\"new-left-links\">");
/* 2784 */                               out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 2785 */                               out.write("</a>\n\t");
/* 2786 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2787 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2791 */                           if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2792 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                           }
/*      */                           
/* 2795 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2796 */                           out.write(10);
/* 2797 */                           out.write(9);
/*      */                           
/* 2799 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2800 */                           _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2801 */                           _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2802 */                           int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2803 */                           if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                             for (;;) {
/* 2805 */                               out.write(10);
/* 2806 */                               out.write(9);
/* 2807 */                               out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 2808 */                               out.write(10);
/* 2809 */                               out.write(9);
/* 2810 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2811 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2815 */                           if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2816 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                           }
/*      */                           
/* 2819 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2820 */                           out.write(10);
/* 2821 */                           out.write(9);
/* 2822 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2823 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2827 */                       if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2828 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                       }
/*      */                       
/* 2831 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2832 */                       out.write("\n\t</td>\n\t");
/* 2833 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2834 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2838 */                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2839 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                   }
/*      */                   else {
/* 2842 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2843 */                     out.write("\n        ");
/*      */                     
/* 2845 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2846 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2847 */                     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                     
/* 2849 */                     _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 2850 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2851 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/* 2853 */                         out.write("\n\n        <td class=\"leftlinkstd\">\n\n\t<a href=\"#\" class=\"disabledlink\">        ");
/* 2854 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 2855 */                         out.write("</a>\n</td>\n");
/* 2856 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2857 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2861 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2862 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                     }
/*      */                     else {
/* 2865 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2866 */                       out.write("\n\n</tr>\n</table>\n\n ");
/*      */                       
/* 2868 */                       if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                       {
/*      */ 
/* 2871 */                         out.write("\n\n       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 2872 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 2873 */                         out.write("</td>\n</tr>\n<tr>\n\t<td class=\"leftlinkstd\">\n\t");
/*      */                         
/* 2875 */                         ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2876 */                         _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2877 */                         _jspx_th_c_005fchoose_005f5.setParent(null);
/* 2878 */                         int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2879 */                         if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                           for (;;) {
/* 2881 */                             out.write(10);
/* 2882 */                             out.write(9);
/*      */                             
/* 2884 */                             WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2885 */                             _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2886 */                             _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                             
/* 2888 */                             _jspx_th_c_005fwhen_005f5.setTest("${param.monitor !='JMXNotification'}");
/* 2889 */                             int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2890 */                             if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                               for (;;) {
/* 2892 */                                 out.write("\n\t<a href=\"/fault/AMAlarmView.do?displayName=Alerts due to JMX Notifications&monitor=JMXNotification\" class=\"new-left-links\">");
/* 2893 */                                 out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 2894 */                                 out.write("</a>\n\t");
/* 2895 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2896 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2900 */                             if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2901 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                             }
/*      */                             
/* 2904 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2905 */                             out.write(10);
/* 2906 */                             out.write(9);
/*      */                             
/* 2908 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2909 */                             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2910 */                             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 2911 */                             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2912 */                             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                               for (;;) {
/* 2914 */                                 out.write(10);
/* 2915 */                                 out.write(9);
/* 2916 */                                 out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 2917 */                                 out.write(10);
/* 2918 */                                 out.write(9);
/* 2919 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2920 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2924 */                             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2925 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                             }
/*      */                             
/* 2928 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2929 */                             out.write(10);
/* 2930 */                             out.write(9);
/* 2931 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2932 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2936 */                         if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2937 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                         }
/*      */                         
/* 2940 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2941 */                         out.write("\n\t</td>\n</tr>\n</table>\n ");
/*      */                       }
/*      */                       
/*      */ 
/* 2945 */                       out.write(10);
/* 2946 */                       out.write(10);
/* 2947 */                       out.write(32);
/*      */                       
/* 2949 */                       if (!EnterpriseUtil.isAdminServer())
/*      */                       {
/* 2951 */                         out.write(10);
/* 2952 */                         out.write(32);
/* 2953 */                         out.write(32);
/*      */                         
/* 2955 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2956 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2957 */                         _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                         
/* 2959 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2960 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2961 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2963 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 2964 */                             out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configure.text"));
/* 2965 */                             out.write("</td>\n</tr>\n<tr>\n\t<td class=\"leftlinkstd\">\n\t\t<a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\" class=\"new-left-links\">");
/* 2966 */                             out.print(FormatUtil.getString("am.webclient.header.title.configurealert.text"));
/* 2967 */                             out.write("</a>\n\t</td>\n</tr>\n</table>\n ");
/* 2968 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2969 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2973 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2974 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                         }
/*      */                         
/* 2977 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2978 */                         out.write(10);
/*      */                       }
/* 2980 */                       out.write(10);
/* 2981 */                       out.write("\n\n\n\n");
/*      */                       
/* 2983 */                       ArrayList menupos = new ArrayList(5);
/* 2984 */                       menupos.add("500");
/* 2985 */                       pageContext.setAttribute("menupos", menupos);
/*      */                       
/* 2987 */                       out.write(10);
/*      */                       
/* 2989 */                       ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2990 */                       _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2991 */                       _jspx_th_c_005fchoose_005f6.setParent(null);
/* 2992 */                       int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2993 */                       if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                         for (;;) {
/* 2995 */                           out.write(10);
/*      */                           
/* 2997 */                           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2998 */                           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2999 */                           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                           
/* 3001 */                           _jspx_th_c_005fwhen_005f6.setTest("${!empty param.haid && param.haid!=null && param.haid!='-' && param.haid!='All'}");
/* 3002 */                           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3003 */                           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                             for (;;) {
/* 3005 */                               out.write(10);
/* 3006 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                               
/* 3008 */                               int subGroupLevels1 = com.adventnet.appmanager.util.Constants.getSubGroupLevels();
/*      */                               
/* 3010 */                               out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                               
/* 3012 */                               String requestpathnew = "/images/mm_menu3.jsp";
/* 3013 */                               String category_2 = String.valueOf(com.adventnet.appmanager.util.Constants.isMinimizedversion());
/* 3014 */                               pageContext.setAttribute("category_2", category_2);
/*      */                               
/* 3016 */                               out.write(10);
/* 3017 */                               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpathnew, out, false);
/* 3018 */                               out.write("\n</script>\n  <SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n      ");
/*      */                               
/* 3020 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3021 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3022 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                               
/* 3024 */                               _jspx_th_c_005fif_005f1.setTest("${!empty param.haid }");
/* 3025 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3026 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 3028 */                                   out.write(" \n      \t  ");
/* 3029 */                                   if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                     return;
/* 3031 */                                   out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr> \n          <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3032 */                                   out.print(FormatUtil.getString("am.webclient.applicationlinks.monitorgrouplinks.text"));
/* 3033 */                                   out.write("</td>\n        </tr>\n\t\t        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" > ");
/* 3034 */                                   if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                     return;
/* 3036 */                                   out.write(32);
/* 3037 */                                   if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                     return;
/* 3039 */                                   out.write(" </td>\n        </tr>\n\n\t");
/*      */                                   
/* 3041 */                                   PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3042 */                                   _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3043 */                                   _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3045 */                                   _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3046 */                                   int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3047 */                                   if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                     for (;;) {
/* 3049 */                                       out.write(" \t \n\t<tr> \t \n\t");
/*      */                                       
/* 3051 */                                       Vector ParentMos1 = (Vector)request.getAttribute("ParentMos");
/* 3052 */                                       if ((ParentMos1 != null) && (ParentMos1.size() < subGroupLevels1)) {
/* 3053 */                                         out.write(" \t \n\t\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t\t<a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 3054 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 3055 */                                         out.write("</a> \t \n\t\t\t</td> \t \n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3059 */                                         out.write(" \t \n\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t<a href=\"#\"   class=\"disabledtext\">");
/* 3060 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 3061 */                                         out.write("</a> \t \n\t\t</td> \t \n\t\t");
/*      */                                       }
/* 3063 */                                       out.write(" \t \n\t\t</tr> \t \n\t\t");
/* 3064 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3065 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3069 */                                   if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3070 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                   }
/*      */                                   
/* 3073 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3074 */                                   out.write("\n\n       ");
/*      */                                   
/* 3076 */                                   PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3077 */                                   _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3078 */                                   _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3080 */                                   _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3081 */                                   int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3082 */                                   if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 3084 */                                       out.write("\n\t   <tr>\n\t    ");
/*      */                                       
/* 3086 */                                       Vector ParentMos2 = (Vector)request.getAttribute("ParentMos");
/* 3087 */                                       int mgResourceId = Integer.parseInt(request.getParameter("haid"));
/* 3088 */                                       if ((ParentMos2 != null) && (ParentMos2.size() < subGroupLevels1) && (mgResourceId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 3089 */                                         out.write("\n\t\t  <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t    <a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 3090 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 3091 */                                         out.write("</a>\n\t\t  </td>\n\t\t  ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3095 */                                         out.write("\n\t\t   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t     <a href=\"#\"   class=\"disabledtext\">");
/* 3096 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 3097 */                                         out.write("</a>\n\t\t   </td>\n\t\t   ");
/*      */                                       }
/* 3099 */                                       out.write("\n\t   </tr>\n\t   ");
/* 3100 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3101 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3105 */                                   if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3106 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 3109 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3110 */                                   out.write("\n\n        <tr> \n\t\t               ");
/*      */                                   
/* 3112 */                                   PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3113 */                                   _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3114 */                                   _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3116 */                                   _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3117 */                                   int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3118 */                                   if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                     for (;;) {
/* 3120 */                                       out.write("\n\t\t                 <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > ");
/*      */                                       
/* 3122 */                                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3123 */                                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3124 */                                       _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                                       
/* 3126 */                                       _jspx_th_c_005fif_005f5.setTest("${param.method!='getHAProfiles'}");
/* 3127 */                                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3128 */                                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                         for (;;) {
/* 3130 */                                           out.write(" \n\t\t                   <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 3131 */                                           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                             return;
/* 3133 */                                           out.write("\" \n\t\t                   class=\"new-left-links\">");
/* 3134 */                                           out.print(ALERTCONFIG_TEXT);
/* 3135 */                                           out.write("</a>");
/* 3136 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3137 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3141 */                                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3142 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                       }
/*      */                                       
/* 3145 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3146 */                                       out.write(32);
/*      */                                       
/* 3148 */                                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3149 */                                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3150 */                                       _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fpresent_005f5);
/*      */                                       
/* 3152 */                                       _jspx_th_c_005fif_005f6.setTest("${param.method=='getHAProfiles'}");
/* 3153 */                                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3154 */                                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                         for (;;) {
/* 3156 */                                           out.write(" \n\t\t                   ");
/* 3157 */                                           out.print(ALERTCONFIG_TEXT);
/* 3158 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3159 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3163 */                                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3164 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                       }
/*      */                                       
/* 3167 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3168 */                                       out.write("</td>\n\t\t                   ");
/* 3169 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3170 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3174 */                                   if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3175 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                   }
/*      */                                   
/* 3178 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3179 */                                   out.write("\t\n\t\t       \n\t\t                   ");
/*      */                                   
/* 3181 */                                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3182 */                                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3183 */                                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3185 */                                   _jspx_th_logic_005fpresent_005f6.setRole("OPERATOR");
/* 3186 */                                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3187 */                                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3189 */                                       out.write("\n\t\t                   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \n\t\t                   ");
/*      */                                       
/* 3191 */                                       AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3192 */                                       _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3193 */                                       _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                                       
/* 3195 */                                       _jspx_th_am_005fadminlink_005f0.setHref(ALERTCONFIG_TEXT);
/*      */                                       
/* 3197 */                                       _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 3198 */                                       int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3199 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3200 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3201 */                                           out = _jspx_page_context.pushBody();
/* 3202 */                                           _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3203 */                                           _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3206 */                                           out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 3207 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3208 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3211 */                                         if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3212 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3215 */                                       if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3216 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                       }
/*      */                                       
/* 3219 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3220 */                                       out.write("</td>\n\t\t                ");
/* 3221 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3222 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3226 */                                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3227 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3230 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3231 */                                   out.write("\t\t                   \n       </tr>\n        \n\t\t");
/*      */                                   
/* 3233 */                                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3234 */                                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3235 */                                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3237 */                                   _jspx_th_c_005fif_005f7.setTest("${category_2!='true'}");
/* 3238 */                                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3239 */                                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                     for (;;) {
/* 3241 */                                       out.write("\n          <tr>   ");
/*      */                                       
/* 3243 */                                       PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3244 */                                       _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3245 */                                       _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f7);
/*      */                                       
/* 3247 */                                       _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 3248 */                                       int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3249 */                                       if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                         for (;;) {
/* 3251 */                                           out.write("\n          ");
/* 3252 */                                           if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3253 */                                             out.write("\n          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"  > \n          ");
/*      */                                           } else {
/* 3255 */                                             out.write("\n\t          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"> \n\t          ");
/*      */                                           }
/* 3257 */                                           out.write("\n\t          ");
/*      */                                           
/* 3259 */                                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3260 */                                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3261 */                                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                           
/* 3263 */                                           _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN}");
/* 3264 */                                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3265 */                                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                             for (;;) {
/* 3267 */                                               out.write("\n\t          ");
/*      */                                               
/* 3269 */                                               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3270 */                                               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3271 */                                               _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f8);
/*      */                                               
/* 3273 */                                               _jspx_th_c_005fif_005f9.setTest("${param.method!='getMonitorForm' }");
/* 3274 */                                               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3275 */                                               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                                 for (;;) {
/* 3277 */                                                   out.write(" \n\t            <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 3278 */                                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                                     return;
/* 3280 */                                                   out.write("\" \n\t            class=\"new-left-links\" >");
/* 3281 */                                                   out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 3282 */                                                   out.write(32);
/* 3283 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3284 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3288 */                                               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3289 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                               }
/*      */                                               
/* 3292 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3293 */                                               out.write(" \n\t            ");
/*      */                                               
/* 3295 */                                               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3296 */                                               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3297 */                                               _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fif_005f8);
/*      */                                               
/* 3299 */                                               _jspx_th_c_005fif_005f10.setTest("${param.method=='getMonitorForm' || (empty ADMIN)}");
/* 3300 */                                               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3301 */                                               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                                 for (;;) {
/* 3303 */                                                   out.write(" \n\t            ");
/* 3304 */                                                   out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 3305 */                                                   out.write(32);
/* 3306 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3307 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3311 */                                               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3312 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                               }
/*      */                                               
/* 3315 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3316 */                                               out.write("\n\t            ");
/* 3317 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3318 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3322 */                                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3323 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                           }
/*      */                                           
/* 3326 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3327 */                                           out.write("\n\t            ");
/*      */                                           
/* 3329 */                                           IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3330 */                                           _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3331 */                                           _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                           
/* 3333 */                                           _jspx_th_c_005fif_005f11.setTest("${empty ADMIN}");
/* 3334 */                                           int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3335 */                                           if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                             for (;;) {
/* 3337 */                                               out.write("\n\t            ");
/*      */                                               
/* 3339 */                                               AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3340 */                                               _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3341 */                                               _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fif_005f11);
/*      */                                               
/* 3343 */                                               _jspx_th_am_005fadminlink_005f1.setHref("/jsp/CreateApplication.jsp");
/*      */                                               
/* 3345 */                                               _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3346 */                                               int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3347 */                                               if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3348 */                                                 if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3349 */                                                   out = _jspx_page_context.pushBody();
/* 3350 */                                                   _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3351 */                                                   _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3354 */                                                   out.write("\n\t            \t");
/* 3355 */                                                   out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 3356 */                                                   out.write("\n\t            ");
/* 3357 */                                                   int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3358 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3361 */                                                 if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3362 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3365 */                                               if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3366 */                                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                               }
/*      */                                               
/* 3369 */                                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3370 */                                               out.write("  \t\n\t            ");
/* 3371 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3372 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3376 */                                           if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3377 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                           }
/*      */                                           
/* 3380 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3381 */                                           out.write("   \n\t            </td>\n\t             ");
/* 3382 */                                           if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3383 */                                             out.write("\n\t             <td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n          \n          ");
/*      */                                           } else {
/* 3385 */                                             out.write("\n\t\t\t\t<td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n\t\t\t\t  ");
/*      */                                           }
/* 3387 */                                           out.write("\n          ");
/* 3388 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3389 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3393 */                                       if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3394 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                       }
/*      */                                       
/* 3397 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3398 */                                       out.write("\n        </tr>\n        ");
/* 3399 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3400 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3404 */                                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3405 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                   }
/*      */                                   
/* 3408 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3409 */                                   out.write("\n\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"/fault/AMAlarmView.do?displayName=Alerts&haid=");
/* 3410 */                                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                     return;
/* 3412 */                                   out.write("\" \n            class=\"new-left-links\">");
/* 3413 */                                   out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 3414 */                                   out.write(" </td>\n        </tr>\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"javascript:fnOpenNewWindow('/showReports.do?period=0&actionMethod=generateHAAvailabilityReport&haid=");
/* 3415 */                                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                     return;
/* 3417 */                                   out.write("')\" \n            class=\"new-left-links\"> ");
/* 3418 */                                   out.print(FormatUtil.getString("am.webclient.applicationlinks.applicationreport.text"));
/* 3419 */                                   out.write("</td>\n        </tr>\n        \n        <tr> \n        ");
/*      */                                   
/* 3421 */                                   String editlink = "/showapplication.do?method=editApplication&haid=" + request.getParameter("haid");
/*      */                                   
/* 3423 */                                   out.write(10);
/* 3424 */                                   out.write(9);
/* 3425 */                                   out.write(9);
/*      */                                   
/* 3427 */                                   PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3428 */                                   _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3429 */                                   _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3431 */                                   _jspx_th_logic_005fpresent_005f8.setRole("ENTERPRISEADMIN");
/* 3432 */                                   int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3433 */                                   if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                     for (;;) {
/* 3435 */                                       out.write("\n\t\t\t<td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"");
/* 3436 */                                       out.print(editlink);
/* 3437 */                                       out.write("\"\n\t\t\tClass=\"new-left-links\">");
/* 3438 */                                       out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3439 */                                       out.write("</a></td>\n\t\t");
/* 3440 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3441 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3445 */                                   if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3446 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                   }
/*      */                                   
/* 3449 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3450 */                                   out.write("\t\n\t\t");
/*      */                                   
/* 3452 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3453 */                                   _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3454 */                                   _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                                   
/* 3456 */                                   _jspx_th_logic_005fnotPresent_005f2.setRole("ENTERPRISEADMIN");
/* 3457 */                                   int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3458 */                                   if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                     for (;;) {
/* 3460 */                                       out.write("\n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >");
/*      */                                       
/* 3462 */                                       AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3463 */                                       _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3464 */                                       _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                       
/* 3466 */                                       _jspx_th_am_005fadminlink_005f2.setHref(editlink);
/*      */                                       
/* 3468 */                                       _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3469 */                                       int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3470 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3471 */                                         if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3472 */                                           out = _jspx_page_context.pushBody();
/* 3473 */                                           _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3474 */                                           _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3477 */                                           out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 3478 */                                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3479 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3482 */                                         if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3483 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3486 */                                       if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3487 */                                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                       }
/*      */                                       
/* 3490 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3491 */                                       out.write("</td>");
/* 3492 */                                       out.write(" \n\t\t");
/* 3493 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3494 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3498 */                                   if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3499 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                   }
/*      */                                   
/* 3502 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3503 */                                   out.write("\t\n        </tr>\n        \n        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t");
/*      */                                   
/* 3505 */                                   int mgResId = Integer.parseInt(request.getParameter("haid"));
/* 3506 */                                   if ((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                                   {
/*      */ 
/* 3509 */                                     out.write("\n\t\t\t\t<a href=\"javascript:applndelete()\" class=\"new-left-links\">");
/* 3510 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3511 */                                     out.write("</a>\n\t\t\t");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3516 */                                     out.write(" \n\t\t");
/*      */                                     
/* 3518 */                                     AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3519 */                                     _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 3520 */                                     _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                                     
/* 3522 */                                     _jspx_th_am_005fadminlink_005f3.setHref("javascript:applndelete()");
/*      */                                     
/* 3524 */                                     _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 3525 */                                     int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 3526 */                                     if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 3527 */                                       if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3528 */                                         out = _jspx_page_context.pushBody();
/* 3529 */                                         _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 3530 */                                         _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3533 */                                         out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3534 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 3535 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3538 */                                       if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 3539 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3542 */                                     if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 3543 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                                     }
/*      */                                     
/* 3546 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 3547 */                                     out.write(10);
/* 3548 */                                     out.write(9);
/* 3549 */                                     out.write(9);
/*      */                                   }
/* 3551 */                                   out.write("\n\t\t\t</td>\n        </tr>\n        \n\t\t\t");
/*      */                                   
/*      */ 
/* 3554 */                                   if (((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) || (!EnterpriseUtil.isAdminServer()))
/*      */                                   {
/*      */ 
/* 3557 */                                     out.write("\n\t        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t\t<a href=\"javascript:refreshstatus()\" class=\"new-left-links\">");
/* 3558 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.refresh.status.text"));
/* 3559 */                                     out.write("</a>&nbsp;<img  src=\"../images/icon_refresh.gif\" align=\"absmiddle\" border=\"0\"/>\n\t\t\t</td>\n\t\t\t </tr>\n\t\t\t");
/*      */                                   }
/* 3561 */                                   out.write("\n\t\t\n\t\t\t\n       \n\n        \n\n            </table>\n     ");
/* 3562 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3563 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3567 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3568 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 3571 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3572 */                               out.write("\n     \n<script>\n\nfunction applndelete()\n{\n");
/*      */                               
/* 3574 */                               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3575 */                               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3576 */                               _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                               
/* 3578 */                               _jspx_th_c_005fif_005f12.setTest("${MGtype=='1' }");
/* 3579 */                               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3580 */                               if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                 for (;;) {
/* 3582 */                                   out.write(" \n\tif(confirm('");
/* 3583 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletesub-group.text"));
/* 3584 */                                   out.write("')) {\n\tlocation.href = \"/manageApplications.do?method=delete&select=");
/* 3585 */                                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                                     return;
/* 3587 */                                   out.write("\";\n\t}\n");
/* 3588 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3589 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3593 */                               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3594 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                               }
/*      */                               
/* 3597 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3598 */                               out.write("\n        \n");
/*      */                               
/* 3600 */                               IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3601 */                               _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3602 */                               _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                               
/* 3604 */                               _jspx_th_c_005fif_005f13.setTest("${MGtype!='1' }");
/* 3605 */                               int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3606 */                               if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                 for (;;) {
/* 3608 */                                   out.write("         \n\tif(confirm('");
/* 3609 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletemg.text"));
/* 3610 */                                   out.write("'))\n\t{\n\t location.href = \"/manageApplications.do?method=delete&select=");
/* 3611 */                                   if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f13, _jspx_page_context))
/*      */                                     return;
/* 3613 */                                   out.write("\";\n\t}\n");
/* 3614 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3615 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3619 */                               if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3620 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                               }
/*      */                               
/* 3623 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3624 */                               out.write("\n} \nfunction refreshstatus()\n{\t\n\t location.href = \"/manageApplications.do?method=refreshNow&haid=");
/* 3625 */                               if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f6, _jspx_page_context))
/*      */                                 return;
/* 3627 */                               out.write("\";\n}\n</script>\n");
/* 3628 */                               out.write(10);
/* 3629 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3630 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3634 */                           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3635 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                           }
/*      */                           
/* 3638 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3639 */                           out.write(10);
/* 3640 */                           if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*      */                             return;
/* 3642 */                           out.write(10);
/* 3643 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3644 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3648 */                       if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3649 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*      */                       }
/*      */                       else {
/* 3652 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3653 */                         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3654 */                         out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3655 */                         out.write("</td>\n    <td width=\"2\" class=\"leftlinksheading\"><img src=\"/images/");
/* 3656 */                         if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                           return;
/* 3658 */                         out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr valign=\"middle\">\n    <td colspan=\"2\" height=\"64\" class=\"quicknote\"> ");
/* 3659 */                         out.print(FormatUtil.getString("am.webclient.alerttab.summaryquicknote.text"));
/* 3660 */                         out.write("\n\n    </td>\n  </tr>\n</table>\n");
/*      */                       }
/* 3662 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3663 */         out = _jspx_out;
/* 3664 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3665 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3666 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3669 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3675 */     PageContext pageContext = _jspx_page_context;
/* 3676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3678 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3679 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3680 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3682 */     _jspx_th_c_005fif_005f2.setTest("${param.method!='showApplication' && param.method!='showSnapshot' && param.method!='getHAProfiles' && param.method!='associateMonitors' && param.method!='getMonitorForm'}");
/* 3683 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3684 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3686 */         out.write(" \n\t  \t<br>\n\t  ");
/* 3687 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3688 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3692 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3693 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3694 */       return true;
/*      */     }
/* 3696 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3702 */     PageContext pageContext = _jspx_page_context;
/* 3703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3705 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3706 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3707 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3709 */     _jspx_th_c_005fif_005f3.setTest("${param.method!='showApplication'}");
/* 3710 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3711 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3713 */         out.write("  \n           <a href=\"/showapplication.do?haid=");
/* 3714 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3715 */           return true;
/* 3716 */         out.write("&method=showApplication\" \n            class=\"new-left-links\"> ");
/* 3717 */         if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3718 */           return true;
/* 3719 */         out.write("</a>");
/* 3720 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3721 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3725 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3726 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3727 */       return true;
/*      */     }
/* 3729 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3735 */     PageContext pageContext = _jspx_page_context;
/* 3736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3738 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3739 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3740 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3742 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3743 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3744 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3745 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3746 */       return true;
/*      */     }
/* 3748 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3754 */     PageContext pageContext = _jspx_page_context;
/* 3755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3757 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3758 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3759 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3761 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 3762 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3763 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3764 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3765 */         out = _jspx_page_context.pushBody();
/* 3766 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3767 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3770 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context))
/* 3771 */           return true;
/* 3772 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3773 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3776 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3777 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3780 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3781 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3782 */       return true;
/*      */     }
/* 3784 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3790 */     PageContext pageContext = _jspx_page_context;
/* 3791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3793 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3794 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3795 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 3797 */     _jspx_th_c_005fout_005f1.setValue("${applicationScope.applications[param.haid]}");
/* 3798 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3799 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3800 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3801 */       return true;
/*      */     }
/* 3803 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3809 */     PageContext pageContext = _jspx_page_context;
/* 3810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3812 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3813 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3814 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3816 */     _jspx_th_c_005fif_005f4.setTest("${param.method=='showApplication'}");
/* 3817 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3818 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3820 */         out.write(" \n          ");
/* 3821 */         if (_jspx_meth_am_005fTruncate_005f1(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3822 */           return true;
/* 3823 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3824 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3828 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3829 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3830 */       return true;
/*      */     }
/* 3832 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f1(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3838 */     PageContext pageContext = _jspx_page_context;
/* 3839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3841 */     Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3842 */     _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 3843 */     _jspx_th_am_005fTruncate_005f1.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3845 */     _jspx_th_am_005fTruncate_005f1.setLength(25);
/* 3846 */     int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 3847 */     if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 3848 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3849 */         out = _jspx_page_context.pushBody();
/* 3850 */         _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 3851 */         _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3854 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_am_005fTruncate_005f1, _jspx_page_context))
/* 3855 */           return true;
/* 3856 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 3857 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3860 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3861 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3864 */     if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 3865 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 3866 */       return true;
/*      */     }
/* 3868 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 3869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_am_005fTruncate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3874 */     PageContext pageContext = _jspx_page_context;
/* 3875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3877 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3878 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3879 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_am_005fTruncate_005f1);
/*      */     
/* 3881 */     _jspx_th_c_005fout_005f2.setValue("${applicationScope.applications[param.haid]}");
/* 3882 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3883 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3897 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3900 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3901 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3902 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3903 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3904 */       return true;
/*      */     }
/* 3906 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3912 */     PageContext pageContext = _jspx_page_context;
/* 3913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3915 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3916 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3917 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 3919 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 3920 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3921 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3923 */       return true;
/*      */     }
/* 3925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3931 */     PageContext pageContext = _jspx_page_context;
/* 3932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3934 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3935 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3936 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3938 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 3939 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3940 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3941 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3942 */       return true;
/*      */     }
/* 3944 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3950 */     PageContext pageContext = _jspx_page_context;
/* 3951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3953 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3954 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3955 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3957 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 3958 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3959 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3961 */       return true;
/*      */     }
/* 3963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3969 */     PageContext pageContext = _jspx_page_context;
/* 3970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3972 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3973 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3974 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 3976 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 3977 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3978 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3979 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3980 */       return true;
/*      */     }
/* 3982 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3988 */     PageContext pageContext = _jspx_page_context;
/* 3989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3991 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3992 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3993 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 3995 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 3996 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3997 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3999 */       return true;
/*      */     }
/* 4001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4007 */     PageContext pageContext = _jspx_page_context;
/* 4008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4010 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4011 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4012 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 4014 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 4015 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4016 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4018 */       return true;
/*      */     }
/* 4020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4026 */     PageContext pageContext = _jspx_page_context;
/* 4027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4029 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4030 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 4031 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 4032 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 4033 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 4035 */         out.write("\n<br>\n");
/* 4036 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 4037 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4041 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 4042 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4043 */       return true;
/*      */     }
/* 4045 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 4046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4051 */     PageContext pageContext = _jspx_page_context;
/* 4052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4054 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4055 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4056 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 4058 */     _jspx_th_c_005fout_005f10.setValue("${selectedskin}");
/*      */     
/* 4060 */     _jspx_th_c_005fout_005f10.setDefault("${initParam.defaultSkin}");
/* 4061 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4062 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4063 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4064 */       return true;
/*      */     }
/* 4066 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4067 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AlarmLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */