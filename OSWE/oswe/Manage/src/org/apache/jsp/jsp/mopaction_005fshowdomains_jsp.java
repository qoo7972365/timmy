/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class mopaction_005fshowdomains_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   67 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*  666 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  717 */     java.net.InetAddress add = null;
/*  718 */     if (ip.equals("127.0.0.1")) {
/*  719 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  723 */       add = java.net.InetAddress.getByName(ip);
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
/*  838 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  924 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1822 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1824 */               if (maxCol != null)
/* 1825 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1827 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1822 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 2183 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2242 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2247 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/* 2248 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2256 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2259 */     JspWriter out = null;
/* 2260 */     Object page = this;
/* 2261 */     JspWriter _jspx_out = null;
/* 2262 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2266 */       response.setContentType("text/html;charset=UTF-8");
/* 2267 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2269 */       _jspx_page_context = pageContext;
/* 2270 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2271 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2272 */       session = pageContext.getSession();
/* 2273 */       out = pageContext.getOut();
/* 2274 */       _jspx_out = out;
/*      */       
/* 2276 */       out.write("\n\n\n\n\n");
/* 2277 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2279 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2280 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2282 */       out.write(10);
/* 2283 */       out.write(10);
/* 2284 */       out.write(10);
/* 2285 */       out.write(10);
/*      */       
/* 2287 */       String hostName = (String)request.getAttribute("hostname");
/* 2288 */       String portNumber = (String)request.getAttribute("portnumber");
/* 2289 */       String resourceType = (String)request.getAttribute("resourcetype");
/* 2290 */       String resourceID = (String)request.getAttribute("actionresourceid");
/*      */       
/* 2292 */       out.write("\n<script>\nfunction cancel() {\n    \n     if(typeof(document.MBeanOperationActionForm.redirectto)!=\"undefined\")\n     {\n     \tlocation.href=document.MBeanOperationActionForm.redirectto.value;\n     }\n     else\n     {\n     \tlocation.href=\"/adminAction.do?method=showActionProfiles\";\n     }\n}\n</script>\n");
/*      */       
/* 2294 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2295 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2296 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/* 2298 */       _jspx_th_html_005fform_005f0.setAction("/MBeanOperationAction.do");
/*      */       
/* 2300 */       _jspx_th_html_005fform_005f0.setMethod("POST");
/* 2301 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2302 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/* 2304 */           out.write(32);
/* 2305 */           out.write(10);
/* 2306 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/* 2308 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/* 2311 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/* 2315 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/* 2316 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2318 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/* 2319 */           out.write("\t  {\n\t\t\t");
/* 2320 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2322 */           out.write("\n\t\t  ");
/*      */           
/* 2324 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */           {
/* 2326 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/* 2330 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/* 2332 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/* 2333 */           if (EnterpriseUtil.isManagedServer())
/*      */           {
/* 2335 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/* 2339 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/* 2341 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/* 2342 */           if (EnterpriseUtil.isManagedServer())
/*      */           {
/* 2344 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/* 2348 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/* 2350 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/* 2351 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2352 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/* 2354 */           else if (EnterpriseUtil.isManagedServer()) {
/* 2355 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/* 2357 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/* 2359 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/* 2360 */           if (EnterpriseUtil.isManagedServer()) {
/* 2361 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/* 2363 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/* 2365 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/* 2367 */           String action_haid = request.getParameter("haid");
/* 2368 */           String returnpath = "";
/*      */           
/* 2370 */           if (request.getParameter("returnpath") != null)
/*      */           {
/* 2372 */             returnpath = "&returnpath=" + URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/* 2376 */           out.write(10);
/* 2377 */           out.write(10);
/*      */           
/* 2379 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2380 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2381 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2383 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/* 2384 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2385 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2386 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2387 */               out = _jspx_page_context.pushBody();
/* 2388 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2389 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2392 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/* 2393 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2394 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2397 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2398 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2401 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2402 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/* 2405 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2406 */           out.write(10);
/*      */           
/* 2408 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2409 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2410 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2412 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/* 2413 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2414 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2415 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2416 */               out = _jspx_page_context.pushBody();
/* 2417 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2418 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2421 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/* 2422 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2423 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2426 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2427 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2430 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2431 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/* 2434 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2435 */           out.write(10);
/*      */           
/* 2437 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2438 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2439 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2441 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/* 2442 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2443 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2444 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2445 */               out = _jspx_page_context.pushBody();
/* 2446 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2447 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2450 */               out.print(EnterpriseUtil.isAdminServer());
/* 2451 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2452 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2455 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2456 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2459 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2460 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/* 2463 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2464 */           out.write(10);
/*      */           
/* 2466 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2467 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2468 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2470 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/* 2471 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2472 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2473 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2474 */               out = _jspx_page_context.pushBody();
/* 2475 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2476 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2479 */               out.print(EnterpriseUtil.isProfEdition());
/* 2480 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2481 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2484 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2485 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2488 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2489 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/* 2492 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2493 */           out.write(10);
/*      */           
/* 2495 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2496 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 2497 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2499 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/* 2500 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 2501 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/* 2502 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2503 */               out = _jspx_page_context.pushBody();
/* 2504 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 2505 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2508 */               out.print(EnterpriseUtil.isCloudEdition());
/* 2509 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 2510 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2513 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2514 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2517 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 2518 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/* 2521 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2522 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/* 2523 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/* 2524 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/* 2526 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2527 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2528 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 2529 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2530 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/* 2532 */               out.write(10);
/* 2533 */               out.write(9);
/*      */               
/* 2535 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2536 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2537 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/* 2539 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/* 2540 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2541 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/* 2543 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2544 */                   out.print(action_haid);
/* 2545 */                   out.print(returnpath);
/* 2546 */                   out.write(34);
/* 2547 */                   out.write(62);
/* 2548 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2549 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/* 2550 */                   out.print(action_haid);
/* 2551 */                   out.print(returnpath);
/* 2552 */                   out.write(34);
/* 2553 */                   out.write(62);
/* 2554 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2555 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/* 2556 */                   out.print(action_haid);
/* 2557 */                   out.print(returnpath);
/* 2558 */                   out.write(34);
/* 2559 */                   out.write(62);
/* 2560 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2561 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/* 2562 */                   out.print(action_haid);
/* 2563 */                   out.print(returnpath);
/* 2564 */                   out.write(34);
/* 2565 */                   out.write(62);
/* 2566 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/* 2567 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/* 2569 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2570 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2571 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/* 2572 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2573 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 2575 */                       out.write("\n\t\t\t");
/*      */                       
/* 2577 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2578 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2579 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2581 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/* 2582 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2583 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 2585 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 2587 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2588 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2589 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 2590 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2591 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/* 2593 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/* 2595 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2596 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2597 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/* 2599 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/* 2600 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2601 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/* 2603 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2604 */                                   out.print(action_haid);
/* 2605 */                                   out.print(returnpath);
/* 2606 */                                   out.write(34);
/* 2607 */                                   out.write(62);
/* 2608 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2609 */                                   out.write("</option>\n\t\t\t\t\t");
/* 2610 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2611 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2615 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2616 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/* 2619 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2620 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/* 2622 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2623 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2624 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/* 2625 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2626 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 2628 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 2629 */                                   out.print(action_haid);
/* 2630 */                                   out.print(returnpath);
/* 2631 */                                   out.write(34);
/* 2632 */                                   out.write(62);
/* 2633 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 2634 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 2635 */                                   out.print(action_haid);
/* 2636 */                                   out.print(returnpath);
/* 2637 */                                   out.write(34);
/* 2638 */                                   out.write(62);
/* 2639 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 2640 */                                   out.write("</option>\n\t\t\t\t\t");
/* 2641 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2642 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2646 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2647 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 2650 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2651 */                               out.write("\n\t\t\t\t");
/* 2652 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2653 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2657 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2658 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/* 2661 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2662 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/* 2663 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2664 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2665 */                             out.print(action_haid);
/* 2666 */                             out.print(returnpath);
/* 2667 */                             out.write(34);
/* 2668 */                             out.write(62);
/* 2669 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2670 */                             out.write("</option> ");
/*      */                           }
/* 2672 */                           out.write("\n\t\t\t");
/* 2673 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2674 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2678 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2679 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 2682 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2683 */                       out.write("\n\t\t\t");
/*      */                       
/* 2685 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2686 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2687 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2688 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2689 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 2691 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2692 */                           out.print(action_haid);
/* 2693 */                           out.print(returnpath);
/* 2694 */                           out.write(34);
/* 2695 */                           out.write(62);
/* 2696 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2697 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/* 2699 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2700 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2701 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/* 2703 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/* 2704 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2705 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 2707 */                               out.write("\n\t\t\t\t\t");
/* 2708 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2709 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2710 */                                 out.print(action_haid);
/* 2711 */                                 out.print(returnpath);
/* 2712 */                                 out.write(34);
/* 2713 */                                 out.write(62);
/* 2714 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2715 */                                 out.write("</option> ");
/*      */                               }
/* 2717 */                               out.write("\n\t\t   \t\t");
/* 2718 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2719 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2723 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2724 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/* 2727 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2728 */                           out.write("\n\t\t\t");
/* 2729 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2730 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2734 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2735 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 2738 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2739 */                       out.write(10);
/* 2740 */                       out.write(9);
/* 2741 */                       out.write(9);
/* 2742 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2743 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2747 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2748 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 2751 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2752 */                   out.write(10);
/* 2753 */                   out.write(9);
/* 2754 */                   out.write(9);
/*      */                   
/* 2756 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2757 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2758 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/* 2760 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/* 2761 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2762 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/* 2764 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 2765 */                       out.print(action_haid);
/* 2766 */                       out.print(returnpath);
/* 2767 */                       out.write(34);
/* 2768 */                       out.write(62);
/* 2769 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 2770 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 2771 */                       out.print(action_haid);
/* 2772 */                       out.print(returnpath);
/* 2773 */                       out.write(34);
/* 2774 */                       out.write(62);
/* 2775 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 2776 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/* 2777 */                       out.print(action_haid);
/* 2778 */                       out.print(returnpath);
/* 2779 */                       out.write(34);
/* 2780 */                       out.write(62);
/* 2781 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2782 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/* 2784 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2785 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2786 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/* 2788 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/* 2789 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2790 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2792 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 2793 */                           out.print(action_haid);
/* 2794 */                           out.print(returnpath);
/* 2795 */                           out.write(34);
/* 2796 */                           out.write(62);
/* 2797 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 2798 */                           out.write("</option>\n\t   \t\t");
/* 2799 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2800 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2804 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2805 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2808 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2809 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/* 2810 */                       out.print(action_haid);
/* 2811 */                       out.print(returnpath);
/* 2812 */                       out.write(34);
/* 2813 */                       out.write(62);
/* 2814 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/* 2815 */                       out.write("</option>\n   \t\t");
/* 2816 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2817 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2821 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2822 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/* 2825 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2826 */                   out.write(10);
/* 2827 */                   out.write(9);
/* 2828 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2829 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2833 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2834 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/* 2837 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2838 */               out.write(10);
/* 2839 */               out.write(9);
/*      */               
/* 2841 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2842 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2843 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/* 2844 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2845 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/* 2847 */                   out.write(10);
/*      */                   
/* 2849 */                   String redirectTo = null;
/* 2850 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/* 2852 */                     redirectTo = URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/* 2856 */                     redirectTo = URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/* 2859 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/* 2860 */                   out.print(action_haid);
/* 2861 */                   out.write("&global=true");
/* 2862 */                   out.print(returnpath);
/* 2863 */                   out.write(34);
/* 2864 */                   out.write(62);
/* 2865 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2866 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/* 2867 */                   out.print(action_haid);
/* 2868 */                   out.write("&global=true");
/* 2869 */                   out.print(returnpath);
/* 2870 */                   out.write(34);
/* 2871 */                   out.write(62);
/* 2872 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2873 */                   out.write("</option>\n\t");
/*      */                   
/* 2875 */                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2876 */                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2877 */                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/* 2879 */                   _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 2880 */                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2881 */                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                     for (;;) {
/* 2883 */                       out.write(32);
/* 2884 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/* 2885 */                       out.print(action_haid);
/* 2886 */                       out.write("&global=true");
/* 2887 */                       out.print(returnpath);
/* 2888 */                       out.write(34);
/* 2889 */                       out.write(62);
/* 2890 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2891 */                       out.write("</option>\n\t");
/* 2892 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2893 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2897 */                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2898 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                   }
/*      */                   
/* 2901 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2902 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/* 2903 */                   out.print(action_haid);
/* 2904 */                   out.write("&global=true");
/* 2905 */                   out.print(returnpath);
/* 2906 */                   out.write(34);
/* 2907 */                   out.write(62);
/* 2908 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/* 2909 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/* 2910 */                   out.print(action_haid);
/* 2911 */                   out.write("&global=true");
/* 2912 */                   out.print(returnpath);
/* 2913 */                   out.write(34);
/* 2914 */                   out.write(62);
/* 2915 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/* 2916 */                   out.write("</option>\n\t");
/* 2917 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 2918 */                     out.write(32);
/* 2919 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/* 2920 */                     out.print(action_haid);
/* 2921 */                     out.print(returnpath);
/* 2922 */                     out.write(34);
/* 2923 */                     out.write(62);
/* 2924 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2925 */                     out.write("</option>\n\t");
/*      */                   }
/* 2927 */                   out.write(10);
/* 2928 */                   out.write(9);
/* 2929 */                   out.write(9);
/* 2930 */                   out.write(10);
/* 2931 */                   out.write(9);
/* 2932 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                   {
/* 2934 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/* 2935 */                     out.print(action_haid);
/* 2936 */                     out.write("&redirectTo=");
/* 2937 */                     out.print(redirectTo);
/* 2938 */                     out.write(34);
/* 2939 */                     out.write(62);
/* 2940 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2941 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/* 2944 */                   out.write("\n\t\n\t");
/* 2945 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 2946 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 2947 */                     out.print(action_haid);
/* 2948 */                     out.write("&global=true");
/* 2949 */                     out.print(returnpath);
/* 2950 */                     out.write("&ext=true\">");
/* 2951 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 2952 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/* 2953 */                     out.print(action_haid);
/* 2954 */                     out.print(returnpath);
/* 2955 */                     out.write("&ext=true&global=true\">");
/* 2956 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2957 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 2958 */                     out.print(action_haid);
/* 2959 */                     out.write("&global=true");
/* 2960 */                     out.print(returnpath);
/* 2961 */                     out.write("&ext=true\">");
/* 2962 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 2963 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/* 2964 */                     out.print(action_haid);
/* 2965 */                     out.print(returnpath);
/* 2966 */                     out.write("&ext=true&global=true\">");
/* 2967 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2968 */                     out.write("</option>\n\t ");
/* 2969 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2970 */                     out.write(32);
/* 2971 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 2972 */                     out.print(action_haid);
/* 2973 */                     out.write("&global=true");
/* 2974 */                     out.print(returnpath);
/* 2975 */                     out.write(34);
/* 2976 */                     out.write(62);
/* 2977 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 2978 */                     out.write("</option>\n\t");
/*      */                   }
/* 2980 */                   out.write(10);
/* 2981 */                   out.write(9);
/* 2982 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/* 2983 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 2984 */                     out.print(action_haid);
/* 2985 */                     out.print(returnpath);
/* 2986 */                     out.write(34);
/* 2987 */                     out.write(62);
/* 2988 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 2989 */                     out.write("</option>\t\n\t");
/*      */                   }
/* 2991 */                   out.write(10);
/* 2992 */                   out.write(9);
/* 2993 */                   out.write(32);
/* 2994 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2995 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 2996 */                     out.print(action_haid);
/* 2997 */                     out.print(returnpath);
/* 2998 */                     out.write(34);
/* 2999 */                     out.write(62);
/* 3000 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 3001 */                     out.write("</option>\n\t");
/*      */                   }
/* 3003 */                   out.write(10);
/* 3004 */                   out.write(9);
/* 3005 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3006 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3010 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3011 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/* 3014 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3015 */               out.write(10);
/* 3016 */               out.write(9);
/* 3017 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3018 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3022 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3023 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/* 3026 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3027 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/* 3029 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3030 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3031 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 3033 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/* 3034 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3035 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/* 3037 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 3038 */               out.write("<!--$Id$-->\n\n\n\n");
/* 3039 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/* 3041 */               out.write("\n      \n\n");
/*      */               
/* 3043 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3044 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3045 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 3047 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 3048 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3049 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 3051 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 3052 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3054 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/* 3056 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3057 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3058 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3060 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 3061 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3062 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 3064 */                       out.write("\n    \t");
/*      */                       
/* 3066 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3067 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3068 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 3070 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 3071 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3072 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/* 3073 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3074 */                           out = _jspx_page_context.pushBody();
/* 3075 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 3076 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3079 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3080 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3081 */                           out.write(" </b></font>\n    \t");
/* 3082 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 3083 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3086 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3087 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3090 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3091 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 3094 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 3095 */                       out.write("\n    ");
/* 3096 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3097 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3101 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3102 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 3105 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3106 */                   out.write("\n    ");
/*      */                   
/* 3108 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3109 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3110 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3112 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 3113 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3114 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 3116 */                       out.write("\n    \t");
/*      */                       
/* 3118 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3119 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3120 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 3122 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 3123 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3124 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 3125 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3126 */                           out = _jspx_page_context.pushBody();
/* 3127 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 3128 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3131 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 3132 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3133 */                           out.write("</font>  \t");
/* 3134 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 3135 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3138 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3139 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3142 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3143 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 3146 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 3147 */                       out.write("\n    ");
/* 3148 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3149 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3153 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3154 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 3157 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3158 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 3159 */                   if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3161 */                   out.write("</td>  \n   \n");
/*      */                   
/* 3163 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3164 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3165 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 3166 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3167 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 3169 */                       out.write("\n    ");
/*      */                       
/* 3171 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3172 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3173 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 3175 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 3176 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3177 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 3179 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 3181 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3182 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 3183 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 3185 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 3186 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 3187 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 3188 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3189 */                               out = _jspx_page_context.pushBody();
/* 3190 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 3191 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3194 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3195 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3196 */                               out.write(" </b></font>\n    \t");
/* 3197 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 3198 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3201 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3202 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3205 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 3206 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 3209 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 3210 */                           out.write("\n   ");
/* 3211 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3212 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3216 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3217 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 3220 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3221 */                       out.write("\n   ");
/*      */                       
/* 3223 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3224 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3225 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 3226 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3227 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 3229 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 3231 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3232 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 3233 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 3235 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 3236 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 3237 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 3238 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3239 */                               out = _jspx_page_context.pushBody();
/* 3240 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 3241 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3244 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3245 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3246 */                               out.write(" </font>\n    \t");
/* 3247 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 3248 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3251 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3252 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3255 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 3256 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 3259 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 3260 */                           out.write("\n\t\n\t\t\n   ");
/* 3261 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3262 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3266 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3267 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 3270 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3271 */                       out.write(10);
/* 3272 */                       out.write(32);
/* 3273 */                       out.write(32);
/* 3274 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3275 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3279 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3280 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 3283 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3284 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 3285 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3287 */                   out.write("\n    \t");
/* 3288 */                   if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3290 */                   out.write("\n    \t\n    \t");
/* 3291 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3293 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 3295 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3296 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3297 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 3298 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3299 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 3301 */                       out.write("\n       ");
/*      */                       
/* 3303 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3304 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3305 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 3307 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 3308 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3309 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 3311 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 3313 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3314 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 3315 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 3317 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 3318 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 3319 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 3320 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3321 */                               out = _jspx_page_context.pushBody();
/* 3322 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 3323 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3326 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 3327 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3328 */                               out.write(" </b></font>\n   \t    \t");
/* 3329 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 3330 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3333 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3334 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3337 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 3338 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 3341 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 3342 */                           out.write("\n       ");
/* 3343 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3344 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3348 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3349 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 3352 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3353 */                       out.write("\n        ");
/*      */                       
/* 3355 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3356 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3357 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 3358 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3359 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 3361 */                           out.write("  \n   \t    \t");
/*      */                           
/* 3363 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3364 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 3365 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 3367 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 3368 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 3369 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 3370 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3371 */                               out = _jspx_page_context.pushBody();
/* 3372 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 3373 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3376 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3377 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3378 */                               out.write(" </font>\n   \t    \t");
/* 3379 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 3380 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3383 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3384 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3387 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 3388 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 3391 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 3392 */                           out.write("\n   \t");
/* 3393 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3394 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3398 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3399 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 3402 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3403 */                       out.write(10);
/* 3404 */                       out.write(32);
/* 3405 */                       out.write(32);
/* 3406 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3407 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3411 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3412 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 3415 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3416 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 3417 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3419 */                   out.write("\n       ");
/* 3420 */                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3422 */                   out.write("\n       ");
/* 3423 */                   out.write("\n       \t");
/* 3424 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3426 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 3428 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3429 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3430 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3432 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 3433 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3434 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 3436 */                       out.write(10);
/* 3437 */                       out.write(9);
/*      */                       
/* 3439 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3440 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 3441 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 3443 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 3444 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 3445 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 3446 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3447 */                           out = _jspx_page_context.pushBody();
/* 3448 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 3449 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3452 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 3453 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3454 */                           out.write(" </b></font>\n    \t");
/* 3455 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 3456 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3459 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3460 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3463 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 3464 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 3467 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 3468 */                       out.write(10);
/* 3469 */                       out.write(9);
/* 3470 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3471 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3475 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3476 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 3479 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3480 */                   out.write(10);
/* 3481 */                   out.write(9);
/*      */                   
/* 3483 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3484 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3485 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3487 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 3488 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3489 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 3491 */                       out.write(10);
/* 3492 */                       out.write(9);
/*      */                       
/* 3494 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3495 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 3496 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 3498 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 3499 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 3500 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 3501 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3502 */                           out = _jspx_page_context.pushBody();
/* 3503 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 3504 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3507 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3508 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3509 */                           out.write(" </font>\n    \t");
/* 3510 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 3511 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3514 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3515 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3518 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 3519 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 3522 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 3523 */                       out.write("\n\t\n\t");
/* 3524 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3525 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3529 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3530 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 3533 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3534 */                   out.write(10);
/* 3535 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3537 */                   out.write("   \n ");
/* 3538 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3540 */                   out.write(10);
/* 3541 */                   out.write(32);
/* 3542 */                   out.write(10);
/* 3543 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3545 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 3546 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 3547 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3548 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3552 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3553 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 3556 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3557 */               out.write(10);
/* 3558 */               out.write(10);
/* 3559 */               if (request.getAttribute("EmailForm") == null) {
/* 3560 */                 out.write(10);
/*      */                 
/* 3562 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3563 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 3564 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 3566 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 3568 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 3569 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 3570 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 3571 */                   String msg = null;
/* 3572 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3573 */                     out = _jspx_page_context.pushBody();
/* 3574 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 3575 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 3577 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 3579 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\"><bean:write name=\"msg\"  filter=\"false\"  /></td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 3580 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 3581 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 3582 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3585 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3586 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3589 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 3590 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 3593 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 3595 */               out.write(32);
/*      */               
/* 3597 */               MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 3598 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 3599 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 3601 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 3602 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 3603 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 3605 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 3607 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3608 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 3609 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 3611 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 3613 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 3614 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 3615 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 3616 */                     String msg = null;
/* 3617 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3618 */                       out = _jspx_page_context.pushBody();
/* 3619 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 3620 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 3622 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 3624 */                       out.write("\n\t  <bean:write name=\"msg\" filter=\"false\" /><br>\n\t  ");
/* 3625 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 3626 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 3627 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3630 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3631 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3634 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 3635 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 3638 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 3639 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 3640 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 3641 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3645 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 3646 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 3649 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 3650 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 3651 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 3652 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3653 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3657 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3658 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 3661 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3662 */           out.write(10);
/* 3663 */           out.write("\n<input type=\"hidden\" name=\"actionname\" value=\"");
/* 3664 */           out.print((String)request.getAttribute("actionname"));
/* 3665 */           out.write("\"/>\n<input type=\"hidden\" name=\"sendmail\" value=\"");
/* 3666 */           out.print(request.getAttribute("sendmail"));
/* 3667 */           out.write("\"/>\n<input type=\"hidden\" name=\"actionresourceid\" value=\"");
/* 3668 */           out.print(resourceID);
/* 3669 */           out.write("\"/>\n<input type=\"hidden\" name=\"hostname\" value=\"");
/* 3670 */           out.print(hostName);
/* 3671 */           out.write("\"/>\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3672 */           out.print(resourceType);
/* 3673 */           out.write("\"/>\n<input type=\"hidden\" name=\"portnumber\" value=\"");
/* 3674 */           out.print(portNumber);
/* 3675 */           out.write("\"/>\n<input type=hidden name=\"method\" value=\"showMBeans\"/>\n");
/* 3676 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3678 */           out.write(10);
/* 3679 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3681 */           out.write(10);
/* 3682 */           if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3684 */           out.write(10);
/* 3685 */           if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3687 */           out.write(10);
/* 3688 */           if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3690 */           out.write(10);
/* 3691 */           if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3693 */           out.write(10);
/* 3694 */           if (_jspx_meth_am_005fhiddenparam_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3696 */           out.write(10);
/* 3697 */           if (_jspx_meth_am_005fhiddenparam_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3699 */           out.write(10);
/*      */           
/* 3701 */           boolean isPopup = request.getParameter("popup") != null;
/*      */           
/* 3703 */           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp;\n        ");
/* 3704 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanstep2"));
/* 3705 */           out.write("</td>\n    </tr>\n  </table>\n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrborder\" width=\"99%\" bgcolor='FFFFFF'>\n        <tr>\n          <td width=\"33%\" height=\"27\" class=\"yellowgrayborder\"> <b>");
/* 3706 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanagent"));
/* 3707 */           out.write("</b> ");
/* 3708 */           out.print(getTrimmedText(hostName, 45));
/* 3709 */           out.write("</td>\n          <td width=\"33%\" class=\"yellowgrayborder\"> <b>");
/* 3710 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanport"));
/* 3711 */           out.write("</b> ");
/* 3712 */           out.print(portNumber);
/* 3713 */           out.write("</td>\n          <td width=\"33%\" class=\"yellowgrayborder\"><b>");
/* 3714 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeantype"));
/* 3715 */           out.write("</b> ");
/* 3716 */           out.print(resourceType);
/* 3717 */           out.write("</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\n   ");
/*      */           
/* 3719 */           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3720 */           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3721 */           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 3723 */           _jspx_th_logic_005fiterate_005f0.setName("domains");
/*      */           
/* 3725 */           _jspx_th_logic_005fiterate_005f0.setId("domain");
/*      */           
/* 3727 */           _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */           
/* 3729 */           _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 3730 */           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3731 */           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3732 */             String domain = null;
/* 3733 */             Integer j = null;
/* 3734 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3735 */               out = _jspx_page_context.pushBody();
/* 3736 */               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3737 */               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */             }
/* 3739 */             domain = (String)_jspx_page_context.findAttribute("domain");
/* 3740 */             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */             for (;;) {
/* 3742 */               out.write(" \n  <tr> \n    <td width=\"4%\"height='25' class=\"bodytext\" >&nbsp;</td>\n    <td class=\"bodytext\" width=\"1%\" ><input type=\"radio\" name=\"domain\" value=\"");
/* 3743 */               out.print(domain);
/* 3744 */               out.write("\"></td>\n    <td width=\"95%\" class=\"bodytext\" >");
/* 3745 */               out.print(domain);
/* 3746 */               out.write("</td>\n  </tr>\n  ");
/* 3747 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3748 */               domain = (String)_jspx_page_context.findAttribute("domain");
/* 3749 */               j = (Integer)_jspx_page_context.findAttribute("j");
/* 3750 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3753 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3754 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3757 */           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3758 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */           }
/*      */           
/* 3761 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3762 */           out.write(" \n  <tr> \n    <td height=\"36\"  >&nbsp;</td>\n    <td  ><input type=\"radio\" name=\"domain\" value=\"adventnet_meam_cam_domainfilter\"></td>\n    <td ><span class=\"bodytext\">");
/* 3763 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanfilter"));
/* 3764 */           out.write(" </span> <input type=\"text\" name=\"domainfilter\" value=\"\" class='formtext'/> \n    </td>\n  </tr>\n  <tr > \n    <td width=\"100%\" height=\"30\" colspan=\"3\"  class=\"tablebottom\" align=\"center\" ><input type=\"button\" value=\"");
/* 3765 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanback"));
/* 3766 */           out.write("\" class='buttons btn_back' onClick=\"javascript:history.back();\" />&nbsp;&nbsp;&nbsp;&nbsp;<input name=\"submit\" type=\"submit\" class='buttons btn_highlt' value=\"");
/* 3767 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanshowmbeans"));
/* 3768 */           out.write("\" /> \n      &nbsp;&nbsp; \n      \n              ");
/*      */           
/* 3770 */           if (isPopup)
/*      */           {
/*      */ 
/* 3773 */             out.write("\n      \t<input type=\"button\" value=\"");
/* 3774 */             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 3775 */             out.write("\" class='buttons btn_link' onClick=\"javascript:window.parent.close()\" />        \n      \t");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3781 */             out.write("\n      \t<input type=\"button\" value=\"");
/* 3782 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3783 */             out.write("\" class='buttons btn_link' onClick=\"javascript:cancel();\" />\n      \t");
/*      */           }
/*      */           
/*      */ 
/* 3787 */           out.write("\n    </td>\n  </tr>\n</table>\n</td>\n <td width=\"30%\" valign=\"top\">\n        \t");
/* 3788 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.actions.quicknote.mbeanoperation.help")), request.getCharacterEncoding()), out, false);
/* 3789 */           out.write("\n\t\t</td>\n</tr>\n</table>\n");
/* 3790 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3791 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3795 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3796 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else
/* 3799 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */     } catch (Throwable t) {
/* 3801 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3802 */         out = _jspx_out;
/* 3803 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3804 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3805 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3808 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3814 */     PageContext pageContext = _jspx_page_context;
/* 3815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3817 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3818 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3819 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3821 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 3822 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3823 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3825 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 3826 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3827 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3831 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3832 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3833 */       return true;
/*      */     }
/* 3835 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3841 */     PageContext pageContext = _jspx_page_context;
/* 3842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3844 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3845 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3846 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3848 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 3849 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3850 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3852 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 3853 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3854 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3858 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3859 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3860 */       return true;
/*      */     }
/* 3862 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3868 */     PageContext pageContext = _jspx_page_context;
/* 3869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3871 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3872 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3873 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3875 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3876 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3878 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3879 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3881 */           out.write(" \n      ");
/* 3882 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3883 */             return true;
/* 3884 */           out.write(32);
/* 3885 */           out.write(10);
/* 3886 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3887 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3891 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3892 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3895 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 3896 */         out = _jspx_page_context.popBody(); }
/* 3897 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3899 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3900 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3907 */     PageContext pageContext = _jspx_page_context;
/* 3908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3910 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3911 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3912 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3914 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3916 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3917 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3918 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3919 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3920 */       return true;
/*      */     }
/* 3922 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3928 */     PageContext pageContext = _jspx_page_context;
/* 3929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3931 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3932 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3933 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3935 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 3937 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 3939 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 3940 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3941 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3942 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3943 */       return true;
/*      */     }
/* 3945 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3951 */     PageContext pageContext = _jspx_page_context;
/* 3952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3954 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3955 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3956 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3958 */     _jspx_th_c_005fout_005f0.setValue("${wizimage}");
/*      */     
/* 3960 */     _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 3961 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3962 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3963 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3964 */       return true;
/*      */     }
/* 3966 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3972 */     PageContext pageContext = _jspx_page_context;
/* 3973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3975 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3976 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3977 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3979 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 3980 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3981 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3983 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 3984 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 3985 */           return true;
/* 3986 */         out.write("&wiz=true\">\n    ");
/* 3987 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3988 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3992 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3993 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3994 */       return true;
/*      */     }
/* 3996 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4002 */     PageContext pageContext = _jspx_page_context;
/* 4003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4005 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4006 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4007 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4009 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 4010 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4011 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4012 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4013 */       return true;
/*      */     }
/* 4015 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4021 */     PageContext pageContext = _jspx_page_context;
/* 4022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4024 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4025 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4026 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4028 */     _jspx_th_c_005fout_005f2.setValue("${wizimage}");
/*      */     
/* 4030 */     _jspx_th_c_005fout_005f2.setEscapeXml("false");
/* 4031 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4032 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4033 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4034 */       return true;
/*      */     }
/* 4036 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4037 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4042 */     PageContext pageContext = _jspx_page_context;
/* 4043 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4045 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4046 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4047 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4049 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 4050 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4051 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4053 */         out.write("\n    \t</a>\n    \t");
/* 4054 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4059 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4060 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4061 */       return true;
/*      */     }
/* 4063 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4069 */     PageContext pageContext = _jspx_page_context;
/* 4070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4072 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4073 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4074 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4076 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 4077 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4078 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4080 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 4081 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4082 */           return true;
/* 4083 */         out.write("&wiz=true\">\n       ");
/* 4084 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4089 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4090 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4091 */       return true;
/*      */     }
/* 4093 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4099 */     PageContext pageContext = _jspx_page_context;
/* 4100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4102 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4103 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4104 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4106 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 4107 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4108 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4109 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4110 */       return true;
/*      */     }
/* 4112 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4118 */     PageContext pageContext = _jspx_page_context;
/* 4119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4121 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4122 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4123 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4125 */     _jspx_th_c_005fout_005f4.setValue("${wizimage}");
/*      */     
/* 4127 */     _jspx_th_c_005fout_005f4.setEscapeXml("false");
/* 4128 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4129 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4130 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4131 */       return true;
/*      */     }
/* 4133 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4139 */     PageContext pageContext = _jspx_page_context;
/* 4140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4142 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4143 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4144 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4146 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 4147 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4148 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4150 */         out.write("\n       \t</a>\n       \t");
/* 4151 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4152 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4156 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4157 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4158 */       return true;
/*      */     }
/* 4160 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4166 */     PageContext pageContext = _jspx_page_context;
/* 4167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4169 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4170 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4171 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4173 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4174 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4175 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4177 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 4178 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4179 */           return true;
/* 4180 */         out.write("&wiz=true\">\n ");
/* 4181 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4182 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4186 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4187 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4188 */       return true;
/*      */     }
/* 4190 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4196 */     PageContext pageContext = _jspx_page_context;
/* 4197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4199 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4200 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4201 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4203 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4204 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4205 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4206 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4207 */       return true;
/*      */     }
/* 4209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4210 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4215 */     PageContext pageContext = _jspx_page_context;
/* 4216 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4218 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4219 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4220 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4222 */     _jspx_th_c_005fout_005f6.setValue("${wizimage}");
/*      */     
/* 4224 */     _jspx_th_c_005fout_005f6.setEscapeXml("false");
/* 4225 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4226 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4227 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4228 */       return true;
/*      */     }
/* 4230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4236 */     PageContext pageContext = _jspx_page_context;
/* 4237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4239 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4240 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4241 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4243 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4244 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4245 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 4247 */         out.write("\t    \n    </a>\n ");
/* 4248 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4249 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4253 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4254 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4255 */       return true;
/*      */     }
/* 4257 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4258 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4263 */     PageContext pageContext = _jspx_page_context;
/* 4264 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4266 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4267 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 4268 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4270 */     _jspx_th_am_005fhiddenparam_005f0.setName("popup");
/* 4271 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 4272 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 4273 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4274 */       return true;
/*      */     }
/* 4276 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4277 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4282 */     PageContext pageContext = _jspx_page_context;
/* 4283 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4285 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4286 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 4287 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4289 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectto");
/* 4290 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 4291 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 4292 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4293 */       return true;
/*      */     }
/* 4295 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4296 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4301 */     PageContext pageContext = _jspx_page_context;
/* 4302 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4304 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4305 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 4306 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4308 */     _jspx_th_am_005fhiddenparam_005f2.setName("returnpath");
/* 4309 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 4310 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 4311 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4312 */       return true;
/*      */     }
/* 4314 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4320 */     PageContext pageContext = _jspx_page_context;
/* 4321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4323 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4324 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 4325 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4327 */     _jspx_th_am_005fhiddenparam_005f3.setName("resourceid");
/* 4328 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 4329 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 4330 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4331 */       return true;
/*      */     }
/* 4333 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4339 */     PageContext pageContext = _jspx_page_context;
/* 4340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4342 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4343 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 4344 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4346 */     _jspx_th_am_005fhiddenparam_005f4.setName("attributeid");
/* 4347 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 4348 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 4349 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4350 */       return true;
/*      */     }
/* 4352 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4358 */     PageContext pageContext = _jspx_page_context;
/* 4359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4361 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4362 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 4363 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4365 */     _jspx_th_am_005fhiddenparam_005f5.setName("severity");
/* 4366 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 4367 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 4368 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4369 */       return true;
/*      */     }
/* 4371 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4377 */     PageContext pageContext = _jspx_page_context;
/* 4378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4380 */     HiddenParam _jspx_th_am_005fhiddenparam_005f6 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4381 */     _jspx_th_am_005fhiddenparam_005f6.setPageContext(_jspx_page_context);
/* 4382 */     _jspx_th_am_005fhiddenparam_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4384 */     _jspx_th_am_005fhiddenparam_005f6.setName("wiz");
/* 4385 */     int _jspx_eval_am_005fhiddenparam_005f6 = _jspx_th_am_005fhiddenparam_005f6.doStartTag();
/* 4386 */     if (_jspx_th_am_005fhiddenparam_005f6.doEndTag() == 5) {
/* 4387 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4388 */       return true;
/*      */     }
/* 4390 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4396 */     PageContext pageContext = _jspx_page_context;
/* 4397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4399 */     HiddenParam _jspx_th_am_005fhiddenparam_005f7 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4400 */     _jspx_th_am_005fhiddenparam_005f7.setPageContext(_jspx_page_context);
/* 4401 */     _jspx_th_am_005fhiddenparam_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4403 */     _jspx_th_am_005fhiddenparam_005f7.setName("global");
/* 4404 */     int _jspx_eval_am_005fhiddenparam_005f7 = _jspx_th_am_005fhiddenparam_005f7.doStartTag();
/* 4405 */     if (_jspx_th_am_005fhiddenparam_005f7.doEndTag() == 5) {
/* 4406 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4407 */       return true;
/*      */     }
/* 4409 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4410 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mopaction_005fshowdomains_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */