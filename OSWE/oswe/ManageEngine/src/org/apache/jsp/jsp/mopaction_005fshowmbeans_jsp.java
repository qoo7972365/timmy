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
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
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
/*      */ public final class mopaction_005fshowmbeans_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/* 2176 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2232 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2250 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/* 2251 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/* 2252 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2259 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2262 */     JspWriter out = null;
/* 2263 */     Object page = this;
/* 2264 */     JspWriter _jspx_out = null;
/* 2265 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2269 */       response.setContentType("text/html;charset=UTF-8");
/* 2270 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2272 */       _jspx_page_context = pageContext;
/* 2273 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2274 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2275 */       session = pageContext.getSession();
/* 2276 */       out = pageContext.getOut();
/* 2277 */       _jspx_out = out;
/*      */       
/* 2279 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/* 2280 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2282 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2288 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2290 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2293 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2294 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2295 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2298 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2299 */         String available = null;
/* 2300 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2301 */         out.write(10);
/*      */         
/* 2303 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2309 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2311 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2314 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2315 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2316 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2319 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2320 */           String unavailable = null;
/* 2321 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2322 */           out.write(10);
/*      */           
/* 2324 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2330 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2332 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2335 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2336 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2337 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2340 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2341 */             String unmanaged = null;
/* 2342 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2343 */             out.write(10);
/*      */             
/* 2345 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2351 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2353 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2356 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2357 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2358 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2361 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2362 */               String scheduled = null;
/* 2363 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2364 */               out.write(10);
/*      */               
/* 2366 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2372 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2374 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2377 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2378 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2379 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2382 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2383 */                 String critical = null;
/* 2384 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2385 */                 out.write(10);
/*      */                 
/* 2387 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2393 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2395 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2398 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2399 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2400 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2403 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2404 */                   String clear = null;
/* 2405 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2406 */                   out.write(10);
/*      */                   
/* 2408 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2414 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2416 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2419 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2420 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2421 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2424 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2425 */                     String warning = null;
/* 2426 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2427 */                     out.write(10);
/* 2428 */                     out.write(10);
/*      */                     
/* 2430 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2431 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/*      */                     
/*      */ 
/* 2439 */                     String hostName = (String)request.getAttribute("hostname");
/* 2440 */                     String portNumber = (String)request.getAttribute("portnumber");
/* 2441 */                     String resourceType = (String)request.getAttribute("resourcetype");
/* 2442 */                     String resourceID = (String)request.getAttribute("actionresourceid");
/* 2443 */                     Map mbeanInfoMap = (Map)((Map)request.getAttribute("mbeaninfo")).get("mbeaninfo");
/*      */                     
/* 2445 */                     out.write(10);
/* 2446 */                     out.write(10);
/*      */                     
/* 2448 */                     FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2449 */                     _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2450 */                     _jspx_th_html_005fform_005f0.setParent(null);
/*      */                     
/* 2452 */                     _jspx_th_html_005fform_005f0.setMethod("POST");
/*      */                     
/* 2454 */                     _jspx_th_html_005fform_005f0.setAction("/MBeanOperationAction");
/* 2455 */                     int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2456 */                     if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                       for (;;) {
/* 2458 */                         out.write(32);
/* 2459 */                         out.write(10);
/* 2460 */                         out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */                         
/* 2462 */                         if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */                         {
/*      */ 
/* 2465 */                           out.write("\n        myOnLoad1();\n        ");
/*      */                         }
/*      */                         
/*      */ 
/* 2469 */                         out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/* 2470 */                         if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2472 */                         out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/* 2473 */                         out.write("\t  {\n\t\t\t");
/* 2474 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 2476 */                         out.write("\n\t\t  ");
/*      */                         
/* 2478 */                         if ((com.adventnet.appmanager.util.Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */                         {
/* 2480 */                           out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2484 */                           out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */                         }
/* 2486 */                         out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/* 2487 */                         if (EnterpriseUtil.isManagedServer())
/*      */                         {
/* 2489 */                           out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2493 */                           out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */                         }
/* 2495 */                         out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/* 2496 */                         if (EnterpriseUtil.isManagedServer())
/*      */                         {
/* 2498 */                           out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2502 */                           out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */                         }
/* 2504 */                         out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/* 2505 */                         if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2506 */                           out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                         }
/* 2508 */                         else if (EnterpriseUtil.isManagedServer()) {
/* 2509 */                           out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */                         } else {
/* 2511 */                           out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */                         }
/* 2513 */                         out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/* 2514 */                         if (EnterpriseUtil.isManagedServer()) {
/* 2515 */                           out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                         } else {
/* 2517 */                           out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */                         }
/* 2519 */                         out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */                         
/* 2521 */                         String action_haid = request.getParameter("haid");
/* 2522 */                         String returnpath = "";
/*      */                         
/* 2524 */                         if (request.getParameter("returnpath") != null)
/*      */                         {
/* 2526 */                           returnpath = "&returnpath=" + URLEncoder.encode(request.getParameter("returnpath"));
/*      */                         }
/*      */                         
/*      */ 
/* 2530 */                         out.write(10);
/* 2531 */                         out.write(10);
/*      */                         
/* 2533 */                         SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2534 */                         _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2535 */                         _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2537 */                         _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/* 2538 */                         int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2539 */                         if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2540 */                           if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2541 */                             out = _jspx_page_context.pushBody();
/* 2542 */                             _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2543 */                             _jspx_th_c_005fset_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2546 */                             out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/* 2547 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2548 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2551 */                           if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2552 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2555 */                         if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2556 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                         }
/*      */                         
/* 2559 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2560 */                         out.write(10);
/*      */                         
/* 2562 */                         SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2563 */                         _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2564 */                         _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2566 */                         _jspx_th_c_005fset_005f1.setVar("isIt360");
/* 2567 */                         int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2568 */                         if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2569 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2570 */                             out = _jspx_page_context.pushBody();
/* 2571 */                             _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2572 */                             _jspx_th_c_005fset_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2575 */                             out.print(com.adventnet.appmanager.util.Constants.isIt360);
/* 2576 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2577 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2580 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2581 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2584 */                         if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2585 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                         }
/*      */                         
/* 2588 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2589 */                         out.write(10);
/*      */                         
/* 2591 */                         SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2592 */                         _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2593 */                         _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2595 */                         _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/* 2596 */                         int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2597 */                         if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2598 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2599 */                             out = _jspx_page_context.pushBody();
/* 2600 */                             _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2601 */                             _jspx_th_c_005fset_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2604 */                             out.print(EnterpriseUtil.isAdminServer());
/* 2605 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2606 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2609 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2610 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2613 */                         if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2614 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                         }
/*      */                         
/* 2617 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2618 */                         out.write(10);
/*      */                         
/* 2620 */                         SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2621 */                         _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2622 */                         _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2624 */                         _jspx_th_c_005fset_005f3.setVar("isProfServer");
/* 2625 */                         int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2626 */                         if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2627 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2628 */                             out = _jspx_page_context.pushBody();
/* 2629 */                             _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2630 */                             _jspx_th_c_005fset_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2633 */                             out.print(EnterpriseUtil.isProfEdition());
/* 2634 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2635 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2638 */                           if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2639 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2642 */                         if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2643 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                         }
/*      */                         
/* 2646 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2647 */                         out.write(10);
/*      */                         
/* 2649 */                         SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2650 */                         _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 2651 */                         _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 2653 */                         _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/* 2654 */                         int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 2655 */                         if (_jspx_eval_c_005fset_005f4 != 0) {
/* 2656 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2657 */                             out = _jspx_page_context.pushBody();
/* 2658 */                             _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 2659 */                             _jspx_th_c_005fset_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2662 */                             out.print(EnterpriseUtil.isCloudEdition());
/* 2663 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 2664 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2667 */                           if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2668 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2671 */                         if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 2672 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                         }
/*      */                         
/* 2675 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2676 */                         out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/* 2677 */                         out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/* 2678 */                         out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */                         
/* 2680 */                         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2681 */                         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2682 */                         _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 2683 */                         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2684 */                         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                           for (;;) {
/* 2686 */                             out.write(10);
/* 2687 */                             out.write(9);
/*      */                             
/* 2689 */                             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2690 */                             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2691 */                             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                             
/* 2693 */                             _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/* 2694 */                             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2695 */                             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                               for (;;) {
/* 2697 */                                 out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2698 */                                 out.print(action_haid);
/* 2699 */                                 out.print(returnpath);
/* 2700 */                                 out.write(34);
/* 2701 */                                 out.write(62);
/* 2702 */                                 out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2703 */                                 out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/* 2704 */                                 out.print(action_haid);
/* 2705 */                                 out.print(returnpath);
/* 2706 */                                 out.write(34);
/* 2707 */                                 out.write(62);
/* 2708 */                                 out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2709 */                                 out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/* 2710 */                                 out.print(action_haid);
/* 2711 */                                 out.print(returnpath);
/* 2712 */                                 out.write(34);
/* 2713 */                                 out.write(62);
/* 2714 */                                 out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2715 */                                 out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/* 2716 */                                 out.print(action_haid);
/* 2717 */                                 out.print(returnpath);
/* 2718 */                                 out.write(34);
/* 2719 */                                 out.write(62);
/* 2720 */                                 out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/* 2721 */                                 out.write("</option>\n\t\t\n\t\t");
/*      */                                 
/* 2723 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2724 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2725 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/* 2726 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2727 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;) {
/* 2729 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 2731 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2732 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2733 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 2735 */                                     _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/* 2736 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2737 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 2739 */                                         out.write("\n\t\t\t\t");
/*      */                                         
/* 2741 */                                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2742 */                                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2743 */                                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 2744 */                                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2745 */                                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                           for (;;) {
/* 2747 */                                             out.write("\n\t\t\t\t\t");
/*      */                                             
/* 2749 */                                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2750 */                                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2751 */                                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                             
/* 2753 */                                             _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/* 2754 */                                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2755 */                                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                               for (;;) {
/* 2757 */                                                 out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2758 */                                                 out.print(action_haid);
/* 2759 */                                                 out.print(returnpath);
/* 2760 */                                                 out.write(34);
/* 2761 */                                                 out.write(62);
/* 2762 */                                                 out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2763 */                                                 out.write("</option>\n\t\t\t\t\t");
/* 2764 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2765 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2769 */                                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2770 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                             }
/*      */                                             
/* 2773 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2774 */                                             out.write("\n\t\t\t\t\t");
/*      */                                             
/* 2776 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2777 */                                             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2778 */                                             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/* 2779 */                                             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2780 */                                             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                               for (;;) {
/* 2782 */                                                 out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 2783 */                                                 out.print(action_haid);
/* 2784 */                                                 out.print(returnpath);
/* 2785 */                                                 out.write(34);
/* 2786 */                                                 out.write(62);
/* 2787 */                                                 out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 2788 */                                                 out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 2789 */                                                 out.print(action_haid);
/* 2790 */                                                 out.print(returnpath);
/* 2791 */                                                 out.write(34);
/* 2792 */                                                 out.write(62);
/* 2793 */                                                 out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 2794 */                                                 out.write("</option>\n\t\t\t\t\t");
/* 2795 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2796 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2800 */                                             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2801 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                             }
/*      */                                             
/* 2804 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2805 */                                             out.write("\n\t\t\t\t");
/* 2806 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2807 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2811 */                                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2812 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                         }
/*      */                                         
/* 2815 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2816 */                                         out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/* 2817 */                                         if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2818 */                                           out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2819 */                                           out.print(action_haid);
/* 2820 */                                           out.print(returnpath);
/* 2821 */                                           out.write(34);
/* 2822 */                                           out.write(62);
/* 2823 */                                           out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2824 */                                           out.write("</option> ");
/*      */                                         }
/* 2826 */                                         out.write("\n\t\t\t");
/* 2827 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2828 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2832 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2833 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 2836 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2837 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 2839 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2840 */                                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2841 */                                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2842 */                                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2843 */                                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                       for (;;) {
/* 2845 */                                         out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2846 */                                         out.print(action_haid);
/* 2847 */                                         out.print(returnpath);
/* 2848 */                                         out.write(34);
/* 2849 */                                         out.write(62);
/* 2850 */                                         out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2851 */                                         out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                                         
/* 2853 */                                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2854 */                                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2855 */                                         _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                                         
/* 2857 */                                         _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/* 2858 */                                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2859 */                                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                           for (;;) {
/* 2861 */                                             out.write("\n\t\t\t\t\t");
/* 2862 */                                             if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2863 */                                               out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2864 */                                               out.print(action_haid);
/* 2865 */                                               out.print(returnpath);
/* 2866 */                                               out.write(34);
/* 2867 */                                               out.write(62);
/* 2868 */                                               out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2869 */                                               out.write("</option> ");
/*      */                                             }
/* 2871 */                                             out.write("\n\t\t   \t\t");
/* 2872 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2873 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2877 */                                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2878 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                         }
/*      */                                         
/* 2881 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2882 */                                         out.write("\n\t\t\t");
/* 2883 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2884 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2888 */                                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2889 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                     }
/*      */                                     
/* 2892 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2893 */                                     out.write(10);
/* 2894 */                                     out.write(9);
/* 2895 */                                     out.write(9);
/* 2896 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2897 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2901 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2902 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 2905 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2906 */                                 out.write(10);
/* 2907 */                                 out.write(9);
/* 2908 */                                 out.write(9);
/*      */                                 
/* 2910 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2911 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2912 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2914 */                                 _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/* 2915 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2916 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2918 */                                     out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 2919 */                                     out.print(action_haid);
/* 2920 */                                     out.print(returnpath);
/* 2921 */                                     out.write(34);
/* 2922 */                                     out.write(62);
/* 2923 */                                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 2924 */                                     out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 2925 */                                     out.print(action_haid);
/* 2926 */                                     out.print(returnpath);
/* 2927 */                                     out.write(34);
/* 2928 */                                     out.write(62);
/* 2929 */                                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 2930 */                                     out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/* 2931 */                                     out.print(action_haid);
/* 2932 */                                     out.print(returnpath);
/* 2933 */                                     out.write(34);
/* 2934 */                                     out.write(62);
/* 2935 */                                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2936 */                                     out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                                     
/* 2938 */                                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2939 */                                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2940 */                                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 2942 */                                     _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/* 2943 */                                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2944 */                                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                       for (;;) {
/* 2946 */                                         out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 2947 */                                         out.print(action_haid);
/* 2948 */                                         out.print(returnpath);
/* 2949 */                                         out.write(34);
/* 2950 */                                         out.write(62);
/* 2951 */                                         out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 2952 */                                         out.write("</option>\n\t   \t\t");
/* 2953 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2954 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2958 */                                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2959 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                     }
/*      */                                     
/* 2962 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2963 */                                     out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/* 2964 */                                     out.print(action_haid);
/* 2965 */                                     out.print(returnpath);
/* 2966 */                                     out.write(34);
/* 2967 */                                     out.write(62);
/* 2968 */                                     out.print(FormatUtil.getString("am.container.action.createnew"));
/* 2969 */                                     out.write("</option>\n   \t\t");
/* 2970 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2971 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2975 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2976 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2979 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2980 */                                 out.write(10);
/* 2981 */                                 out.write(9);
/* 2982 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2983 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2987 */                             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2988 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                             }
/*      */                             
/* 2991 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2992 */                             out.write(10);
/* 2993 */                             out.write(9);
/*      */                             
/* 2995 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2996 */                             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2997 */                             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/* 2998 */                             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2999 */                             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                               for (;;) {
/* 3001 */                                 out.write(10);
/*      */                                 
/* 3003 */                                 String redirectTo = null;
/* 3004 */                                 if (request.getParameter("redirectto") != null)
/*      */                                 {
/* 3006 */                                   redirectTo = URLEncoder.encode(request.getParameter("redirectto"));
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3010 */                                   redirectTo = URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                                 }
/*      */                                 
/* 3013 */                                 out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/* 3014 */                                 out.print(action_haid);
/* 3015 */                                 out.write("&global=true");
/* 3016 */                                 out.print(returnpath);
/* 3017 */                                 out.write(34);
/* 3018 */                                 out.write(62);
/* 3019 */                                 out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 3020 */                                 out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/* 3021 */                                 out.print(action_haid);
/* 3022 */                                 out.write("&global=true");
/* 3023 */                                 out.print(returnpath);
/* 3024 */                                 out.write(34);
/* 3025 */                                 out.write(62);
/* 3026 */                                 out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 3027 */                                 out.write("</option>\n\t");
/*      */                                 
/* 3029 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3030 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3031 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                                 
/* 3033 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 3034 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3035 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 3037 */                                     out.write(32);
/* 3038 */                                     out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/* 3039 */                                     out.print(action_haid);
/* 3040 */                                     out.write("&global=true");
/* 3041 */                                     out.print(returnpath);
/* 3042 */                                     out.write(34);
/* 3043 */                                     out.write(62);
/* 3044 */                                     out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 3045 */                                     out.write("</option>\n\t");
/* 3046 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3047 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3051 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3052 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 3055 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3056 */                                 out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/* 3057 */                                 out.print(action_haid);
/* 3058 */                                 out.write("&global=true");
/* 3059 */                                 out.print(returnpath);
/* 3060 */                                 out.write(34);
/* 3061 */                                 out.write(62);
/* 3062 */                                 out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/* 3063 */                                 out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/* 3064 */                                 out.print(action_haid);
/* 3065 */                                 out.write("&global=true");
/* 3066 */                                 out.print(returnpath);
/* 3067 */                                 out.write(34);
/* 3068 */                                 out.write(62);
/* 3069 */                                 out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/* 3070 */                                 out.write("</option>\n\t");
/* 3071 */                                 if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 3072 */                                   out.write(32);
/* 3073 */                                   out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/* 3074 */                                   out.print(action_haid);
/* 3075 */                                   out.print(returnpath);
/* 3076 */                                   out.write(34);
/* 3077 */                                   out.write(62);
/* 3078 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 3079 */                                   out.write("</option>\n\t");
/*      */                                 }
/* 3081 */                                 out.write(10);
/* 3082 */                                 out.write(9);
/* 3083 */                                 out.write(9);
/* 3084 */                                 out.write(10);
/* 3085 */                                 out.write(9);
/* 3086 */                                 if ((!com.adventnet.appmanager.util.Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                                 {
/* 3088 */                                   out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/* 3089 */                                   out.print(action_haid);
/* 3090 */                                   out.write("&redirectTo=");
/* 3091 */                                   out.print(redirectTo);
/* 3092 */                                   out.write(34);
/* 3093 */                                   out.write(62);
/* 3094 */                                   out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 3095 */                                   out.write("</option> ");
/*      */                                 }
/*      */                                 
/* 3098 */                                 out.write("\n\t\n\t");
/* 3099 */                                 if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 3100 */                                   out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 3101 */                                   out.print(action_haid);
/* 3102 */                                   out.write("&global=true");
/* 3103 */                                   out.print(returnpath);
/* 3104 */                                   out.write("&ext=true\">");
/* 3105 */                                   out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 3106 */                                   out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/* 3107 */                                   out.print(action_haid);
/* 3108 */                                   out.print(returnpath);
/* 3109 */                                   out.write("&ext=true&global=true\">");
/* 3110 */                                   out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 3111 */                                   out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 3112 */                                   out.print(action_haid);
/* 3113 */                                   out.write("&global=true");
/* 3114 */                                   out.print(returnpath);
/* 3115 */                                   out.write("&ext=true\">");
/* 3116 */                                   out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 3117 */                                   out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/* 3118 */                                   out.print(action_haid);
/* 3119 */                                   out.print(returnpath);
/* 3120 */                                   out.write("&ext=true&global=true\">");
/* 3121 */                                   out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 3122 */                                   out.write("</option>\n\t ");
/* 3123 */                                 } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3124 */                                   out.write(32);
/* 3125 */                                   out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 3126 */                                   out.print(action_haid);
/* 3127 */                                   out.write("&global=true");
/* 3128 */                                   out.print(returnpath);
/* 3129 */                                   out.write(34);
/* 3130 */                                   out.write(62);
/* 3131 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 3132 */                                   out.write("</option>\n\t");
/*      */                                 }
/* 3134 */                                 out.write(10);
/* 3135 */                                 out.write(9);
/* 3136 */                                 if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/* 3137 */                                   out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 3138 */                                   out.print(action_haid);
/* 3139 */                                   out.print(returnpath);
/* 3140 */                                   out.write(34);
/* 3141 */                                   out.write(62);
/* 3142 */                                   out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 3143 */                                   out.write("</option>\t\n\t");
/*      */                                 }
/* 3145 */                                 out.write(10);
/* 3146 */                                 out.write(9);
/* 3147 */                                 out.write(32);
/* 3148 */                                 if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3149 */                                   out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 3150 */                                   out.print(action_haid);
/* 3151 */                                   out.print(returnpath);
/* 3152 */                                   out.write(34);
/* 3153 */                                   out.write(62);
/* 3154 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 3155 */                                   out.write("</option>\n\t");
/*      */                                 }
/* 3157 */                                 out.write(10);
/* 3158 */                                 out.write(9);
/* 3159 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3160 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3164 */                             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3165 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                             }
/*      */                             
/* 3168 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3169 */                             out.write(10);
/* 3170 */                             out.write(9);
/* 3171 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3172 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3176 */                         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3177 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                         }
/*      */                         
/* 3180 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3181 */                         out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */                         
/* 3183 */                         IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3184 */                         _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3185 */                         _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 3187 */                         _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/* 3188 */                         int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3189 */                         if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                           for (;;) {
/* 3191 */                             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 3192 */                             out.write("<!--$Id$-->\n\n\n\n");
/* 3193 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                               return;
/* 3195 */                             out.write("\n      \n\n");
/*      */                             
/* 3197 */                             IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3198 */                             _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3199 */                             _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */                             
/* 3201 */                             _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 3202 */                             int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3203 */                             if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                               for (;;) {
/* 3205 */                                 out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 3206 */                                 if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3208 */                                 out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                                 
/* 3210 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3211 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3212 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                                 
/* 3214 */                                 _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 3215 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3216 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 3218 */                                     out.write("\n    \t");
/*      */                                     
/* 3220 */                                     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3221 */                                     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3222 */                                     _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 3224 */                                     _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 3225 */                                     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3226 */                                     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 3227 */                                       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3228 */                                         out = _jspx_page_context.pushBody();
/* 3229 */                                         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 3230 */                                         _jspx_th_c_005fset_005f6.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3233 */                                         out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3234 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3235 */                                         out.write(" </b></font>\n    \t");
/* 3236 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 3237 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3240 */                                       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3241 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3244 */                                     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3245 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                                     }
/*      */                                     
/* 3248 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 3249 */                                     out.write("\n    ");
/* 3250 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3251 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3255 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3256 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 3259 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3260 */                                 out.write("\n    ");
/*      */                                 
/* 3262 */                                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3263 */                                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3264 */                                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                                 
/* 3266 */                                 _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 3267 */                                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3268 */                                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                   for (;;) {
/* 3270 */                                     out.write("\n    \t");
/*      */                                     
/* 3272 */                                     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3273 */                                     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3274 */                                     _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                                     
/* 3276 */                                     _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 3277 */                                     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3278 */                                     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 3279 */                                       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3280 */                                         out = _jspx_page_context.pushBody();
/* 3281 */                                         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 3282 */                                         _jspx_th_c_005fset_005f7.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3285 */                                         out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 3286 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3287 */                                         out.write("</font>  \t");
/* 3288 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 3289 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3292 */                                       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3293 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3296 */                                     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3297 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                                     }
/*      */                                     
/* 3300 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 3301 */                                     out.write("\n    ");
/* 3302 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3303 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3307 */                                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3308 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                 }
/*      */                                 
/* 3311 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3312 */                                 out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 3313 */                                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3315 */                                 out.write("</td>  \n   \n");
/*      */                                 
/* 3317 */                                 ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3318 */                                 _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3319 */                                 _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 3320 */                                 int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3321 */                                 if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                   for (;;) {
/* 3323 */                                     out.write("\n    ");
/*      */                                     
/* 3325 */                                     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3326 */                                     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3327 */                                     _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                     
/* 3329 */                                     _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 3330 */                                     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3331 */                                     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                       for (;;) {
/* 3333 */                                         out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                                         
/* 3335 */                                         SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3336 */                                         _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 3337 */                                         _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                                         
/* 3339 */                                         _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 3340 */                                         int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 3341 */                                         if (_jspx_eval_c_005fset_005f8 != 0) {
/* 3342 */                                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3343 */                                             out = _jspx_page_context.pushBody();
/* 3344 */                                             _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 3345 */                                             _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3348 */                                             out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3349 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3350 */                                             out.write(" </b></font>\n    \t");
/* 3351 */                                             int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 3352 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3355 */                                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3356 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3359 */                                         if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 3360 */                                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                                         }
/*      */                                         
/* 3363 */                                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 3364 */                                         out.write("\n   ");
/* 3365 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3366 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3370 */                                     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3371 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                     }
/*      */                                     
/* 3374 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3375 */                                     out.write("\n   ");
/*      */                                     
/* 3377 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3378 */                                     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3379 */                                     _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 3380 */                                     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3381 */                                     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                       for (;;) {
/* 3383 */                                         out.write("  \n    \t\n\t\t");
/*      */                                         
/* 3385 */                                         SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3386 */                                         _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 3387 */                                         _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                                         
/* 3389 */                                         _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 3390 */                                         int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 3391 */                                         if (_jspx_eval_c_005fset_005f9 != 0) {
/* 3392 */                                           if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3393 */                                             out = _jspx_page_context.pushBody();
/* 3394 */                                             _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 3395 */                                             _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3398 */                                             out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3399 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3400 */                                             out.write(" </font>\n    \t");
/* 3401 */                                             int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 3402 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3405 */                                           if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3406 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3409 */                                         if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 3410 */                                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                                         }
/*      */                                         
/* 3413 */                                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 3414 */                                         out.write("\n\t\n\t\t\n   ");
/* 3415 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3416 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3420 */                                     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3421 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                     }
/*      */                                     
/* 3424 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3425 */                                     out.write(10);
/* 3426 */                                     out.write(32);
/* 3427 */                                     out.write(32);
/* 3428 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3429 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3433 */                                 if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3434 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                 }
/*      */                                 
/* 3437 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3438 */                                 out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 3439 */                                 if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3441 */                                 out.write("\n    \t");
/* 3442 */                                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3444 */                                 out.write("\n    \t\n    \t");
/* 3445 */                                 if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3447 */                                 out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                                 
/* 3449 */                                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3450 */                                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3451 */                                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 3452 */                                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3453 */                                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                   for (;;) {
/* 3455 */                                     out.write("\n       ");
/*      */                                     
/* 3457 */                                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3458 */                                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3459 */                                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                     
/* 3461 */                                     _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 3462 */                                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3463 */                                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                       for (;;) {
/* 3465 */                                         out.write("\n   \n   \t    \t");
/*      */                                         
/* 3467 */                                         SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3468 */                                         _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 3469 */                                         _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                         
/* 3471 */                                         _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 3472 */                                         int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 3473 */                                         if (_jspx_eval_c_005fset_005f10 != 0) {
/* 3474 */                                           if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3475 */                                             out = _jspx_page_context.pushBody();
/* 3476 */                                             _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 3477 */                                             _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3480 */                                             out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 3481 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3482 */                                             out.write(" </b></font>\n   \t    \t");
/* 3483 */                                             int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 3484 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3487 */                                           if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3488 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3491 */                                         if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 3492 */                                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                                         }
/*      */                                         
/* 3495 */                                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 3496 */                                         out.write("\n       ");
/* 3497 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3498 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3502 */                                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3503 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                     }
/*      */                                     
/* 3506 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3507 */                                     out.write("\n        ");
/*      */                                     
/* 3509 */                                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3510 */                                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3511 */                                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 3512 */                                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3513 */                                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                       for (;;) {
/* 3515 */                                         out.write("  \n   \t    \t");
/*      */                                         
/* 3517 */                                         SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3518 */                                         _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 3519 */                                         _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                                         
/* 3521 */                                         _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 3522 */                                         int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 3523 */                                         if (_jspx_eval_c_005fset_005f11 != 0) {
/* 3524 */                                           if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3525 */                                             out = _jspx_page_context.pushBody();
/* 3526 */                                             _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 3527 */                                             _jspx_th_c_005fset_005f11.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3530 */                                             out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3531 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3532 */                                             out.write(" </font>\n   \t    \t");
/* 3533 */                                             int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 3534 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3537 */                                           if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3538 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3541 */                                         if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 3542 */                                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                                         }
/*      */                                         
/* 3545 */                                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 3546 */                                         out.write("\n   \t");
/* 3547 */                                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3548 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3552 */                                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3553 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                     }
/*      */                                     
/* 3556 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3557 */                                     out.write(10);
/* 3558 */                                     out.write(32);
/* 3559 */                                     out.write(32);
/* 3560 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3561 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3565 */                                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3566 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                 }
/*      */                                 
/* 3569 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3570 */                                 out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 3571 */                                 if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3573 */                                 out.write("\n       ");
/* 3574 */                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3576 */                                 out.write("\n       ");
/* 3577 */                                 out.write("\n       \t");
/* 3578 */                                 if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3580 */                                 out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                                 
/* 3582 */                                 IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3583 */                                 _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3584 */                                 _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                                 
/* 3586 */                                 _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 3587 */                                 int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3588 */                                 if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                   for (;;) {
/* 3590 */                                     out.write(10);
/* 3591 */                                     out.write(9);
/*      */                                     
/* 3593 */                                     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3594 */                                     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 3595 */                                     _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                                     
/* 3597 */                                     _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 3598 */                                     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 3599 */                                     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 3600 */                                       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3601 */                                         out = _jspx_page_context.pushBody();
/* 3602 */                                         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 3603 */                                         _jspx_th_c_005fset_005f12.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3606 */                                         out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 3607 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3608 */                                         out.write(" </b></font>\n    \t");
/* 3609 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 3610 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3613 */                                       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3614 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3617 */                                     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 3618 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                                     }
/*      */                                     
/* 3621 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 3622 */                                     out.write(10);
/* 3623 */                                     out.write(9);
/* 3624 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3625 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3629 */                                 if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3630 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                 }
/*      */                                 
/* 3633 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3634 */                                 out.write(10);
/* 3635 */                                 out.write(9);
/*      */                                 
/* 3637 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3638 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3639 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                                 
/* 3641 */                                 _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 3642 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3643 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 3645 */                                     out.write(10);
/* 3646 */                                     out.write(9);
/*      */                                     
/* 3648 */                                     SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3649 */                                     _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 3650 */                                     _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                                     
/* 3652 */                                     _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 3653 */                                     int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 3654 */                                     if (_jspx_eval_c_005fset_005f13 != 0) {
/* 3655 */                                       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3656 */                                         out = _jspx_page_context.pushBody();
/* 3657 */                                         _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 3658 */                                         _jspx_th_c_005fset_005f13.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3661 */                                         out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3662 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3663 */                                         out.write(" </font>\n    \t");
/* 3664 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 3665 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3668 */                                       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3669 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3672 */                                     if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 3673 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                                     }
/*      */                                     
/* 3676 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 3677 */                                     out.write("\n\t\n\t");
/* 3678 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3679 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3683 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3684 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 3687 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3688 */                                 out.write(10);
/* 3689 */                                 if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3691 */                                 out.write("   \n ");
/* 3692 */                                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3694 */                                 out.write(10);
/* 3695 */                                 out.write(32);
/* 3696 */                                 out.write(10);
/* 3697 */                                 if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                   return;
/* 3699 */                                 out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 3700 */                                 out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 3701 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3702 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3706 */                             if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3707 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                             }
/*      */                             
/* 3710 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3711 */                             out.write(10);
/* 3712 */                             out.write(10);
/* 3713 */                             if (request.getAttribute("EmailForm") == null) {
/* 3714 */                               out.write(10);
/*      */                               
/* 3716 */                               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3717 */                               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 3718 */                               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                               
/* 3720 */                               _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                               
/* 3722 */                               _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 3723 */                               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 3724 */                               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 3725 */                                 String msg = null;
/* 3726 */                                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3727 */                                   out = _jspx_page_context.pushBody();
/* 3728 */                                   _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 3729 */                                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                                 }
/* 3731 */                                 msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                 for (;;) {
/* 3733 */                                   out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 3734 */                                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                     return;
/* 3736 */                                   out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 3737 */                                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 3738 */                                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 3739 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3742 */                                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3743 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3746 */                               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 3747 */                                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                               }
/*      */                               
/* 3750 */                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */                             }
/* 3752 */                             out.write(32);
/*      */                             
/* 3754 */                             MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 3755 */                             _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 3756 */                             _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                             
/* 3758 */                             _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 3759 */                             int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 3760 */                             if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 3762 */                                 out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                                 
/* 3764 */                                 MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3765 */                                 _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 3766 */                                 _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                                 
/* 3768 */                                 _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                                 
/* 3770 */                                 _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 3771 */                                 int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 3772 */                                 if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 3773 */                                   String msg = null;
/* 3774 */                                   if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3775 */                                     out = _jspx_page_context.pushBody();
/* 3776 */                                     _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 3777 */                                     _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                                   }
/* 3779 */                                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                                   for (;;) {
/* 3781 */                                     out.write("\n\t  ");
/* 3782 */                                     if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                       return;
/* 3784 */                                     out.write("<br>\n\t  ");
/* 3785 */                                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 3786 */                                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 3787 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3790 */                                   if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3791 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3794 */                                 if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 3795 */                                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                                 }
/*      */                                 
/* 3798 */                                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 3799 */                                 out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 3800 */                                 int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 3801 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3805 */                             if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 3806 */                               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                             }
/*      */                             
/* 3809 */                             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 3810 */                             out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 3811 */                             out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 3812 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3813 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3817 */                         if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3818 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                         }
/*      */                         
/* 3821 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3822 */                         out.write(10);
/* 3823 */                         out.write("\n  <script>\nfunction submitAttribs() {\n\n    var sel = false;\n    if(document.forms.length > 0) { \n        var frmEle = document.MBeanOperationActionForm.elements;\n        if(typeof(frmEle) != \"undefined\" && frmEle.length > 0 && typeof(frmEle[\"mbeanname\"]) != \"undefined\") { \n            var siz = frmEle[\"mbeanname\"].length;\n            if(typeof(siz0)==\"undefined\")\n            { \n                sel=frmEle[\"mbeanname\"].checked;\n            }\n            if(!sel)\n            {\n            \tfor(i=0;i<siz;i++) {\n            \t    if(frmEle[\"mbeanname\"][i].checked) {\n            \t        sel = true;\n            \t        break;\n            \t    }\n            \t} \n            }\n            if(sel) \n            {    \n                document.MBeanOperationActionForm.submit();\n            }\n            else\n            {\n                alert('");
/* 3824 */                         out.print(FormatUtil.getString("am.webclient.newaction.alertnombean"));
/* 3825 */                         out.write("');\n            }\n            \n        } else {\n                alert('");
/* 3826 */                         out.print(FormatUtil.getString("am.webclient.newaction.alertnombean"));
/* 3827 */                         out.write("');\n        }\n\n    } else {\n                alert('");
/* 3828 */                         out.print(FormatUtil.getString("am.webclient.newaction.alertnombean"));
/* 3829 */                         out.write("');\n    }\n\n    \n        \n}\nfunction cancel() {\n    \n     if(typeof(document.MBeanOperationActionForm.redirectto)!=\"undefined\")\n     {\n     \tlocation.href=document.MBeanOperationActionForm.redirectto.value;\n     }\n     else\n     {\n     \tlocation.href=\"/adminAction.do?method=showActionProfiles\";\n     }\n}\n\n\n</SCRIPT>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp;\n        ");
/* 3830 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeanstep3"));
/* 3831 */                         out.write("</td>\n    </tr>\n  </table>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n  \n  <input type=\"hidden\" name=\"method\" value=\"showUserInputOperationValuesGetterScreen\"/>\n  <input type=\"hidden\" name=\"actionname\" value=\"");
/* 3832 */                         out.print((String)request.getAttribute("actionname"));
/* 3833 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"sendmail\" value=\"");
/* 3834 */                         out.print(request.getAttribute("sendmail"));
/* 3835 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"actionresourceid\" value=\"");
/* 3836 */                         out.print(resourceID);
/* 3837 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"hostname\" value=\"");
/* 3838 */                         out.print(hostName);
/* 3839 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3840 */                         out.print(resourceType);
/* 3841 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"portnumber\" value=\"");
/* 3842 */                         out.print(portNumber);
/* 3843 */                         out.write("\"/>\n  <input type=\"hidden\" name=\"domain\" value=\"");
/* 3844 */                         out.print((String)request.getAttribute("domain"));
/* 3845 */                         out.write("\"/>\n  ");
/* 3846 */                         if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3848 */                         out.write(10);
/* 3849 */                         out.write(32);
/* 3850 */                         out.write(32);
/* 3851 */                         if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3853 */                         out.write(10);
/* 3854 */                         out.write(32);
/* 3855 */                         out.write(32);
/* 3856 */                         if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3858 */                         out.write(10);
/* 3859 */                         out.write(32);
/* 3860 */                         out.write(32);
/* 3861 */                         if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3863 */                         out.write(10);
/* 3864 */                         out.write(32);
/* 3865 */                         out.write(32);
/* 3866 */                         if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3868 */                         out.write(10);
/* 3869 */                         out.write(32);
/* 3870 */                         out.write(32);
/* 3871 */                         if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3873 */                         out.write(10);
/* 3874 */                         out.write(32);
/* 3875 */                         out.write(32);
/* 3876 */                         if (_jspx_meth_am_005fhiddenparam_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3878 */                         out.write(10);
/* 3879 */                         out.write(32);
/* 3880 */                         out.write(32);
/* 3881 */                         if (_jspx_meth_am_005fhiddenparam_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                           return;
/* 3883 */                         out.write(10);
/*      */                         
/* 3885 */                         boolean isPopup = request.getParameter("popup") != null;
/*      */                         
/* 3887 */                         out.write("\n\n  <tr> \n          <td width=\"33%\" class=\"columnheadingnotop\"> <b>");
/* 3888 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeanagent"));
/* 3889 */                         out.write("</b> ");
/* 3890 */                         out.print(getTrimmedText(hostName, 40));
/* 3891 */                         out.write("</td>\n          <td width=\"33%\" class=\"columnheadingnotop\"> <b>");
/* 3892 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeanport"));
/* 3893 */                         out.write("</b> ");
/* 3894 */                         out.print(portNumber);
/* 3895 */                         out.write("</td>\n          <td width=\"33%\" class=\"columnheadingnotop\"><b>");
/* 3896 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeantype"));
/* 3897 */                         out.write("</b> ");
/* 3898 */                         out.print(resourceType);
/* 3899 */                         out.write("</td>\n  </tr>\n\n");
/*      */                         
/* 3901 */                         Set keys = mbeanInfoMap.keySet();
/* 3902 */                         Iterator it = keys.iterator();
/* 3903 */                         Object key = null;
/* 3904 */                         String className = "whitegrayborder";
/* 3905 */                         int ctr = 1;
/* 3906 */                         while (it.hasNext())
/*      */                         {
/* 3908 */                           if (ctr % 2 == 0)
/*      */                           {
/* 3910 */                             className = "yellowgrayborder";
/*      */                           }
/*      */                           else
/*      */                           {
/* 3914 */                             className = "whitegrayborder";
/*      */                           }
/* 3916 */                           key = it.next();
/* 3917 */                           ctr++;
/*      */                           
/* 3919 */                           out.write("\n\t <tr class='");
/* 3920 */                           out.print(className);
/* 3921 */                           out.write("'> \n\t \t<td width=\"3%\" valign=\"top\" class=\"");
/* 3922 */                           out.print(className);
/* 3923 */                           out.write("\" title='");
/* 3924 */                           out.print(key.toString());
/* 3925 */                           out.write("' colspan=\"6\"> \n\t\t   <input type='radio' name='mbeanname' value='");
/* 3926 */                           out.print(key);
/* 3927 */                           out.write("'></input>   \n\t                &nbsp;<span class=\"bodytext\"> ");
/* 3928 */                           out.print(getTrimmedText(key.toString(), 115));
/* 3929 */                           out.write("&nbsp;&nbsp;</span></td> \n\t        \n   </tr>\n         ");
/*      */                         }
/*      */                         
/*      */ 
/* 3933 */                         out.write("\n    <tr> \n      <td height=\"29\" colspan=\"6\" align=\"center\" class=\"tablebottom\"><input type=\"button\" value=\"");
/* 3934 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeanback"));
/* 3935 */                         out.write("\" class='buttons btn_back' onClick=\"javascript:history.back();\" />&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"");
/* 3936 */                         out.print(FormatUtil.getString("am.webclient.newaction.mbeanshowoperations"));
/* 3937 */                         out.write("\" onClick=\"javascript:submitAttribs();\" class='buttons btn_highlt' />&nbsp;&nbsp;&nbsp;\n      \n              ");
/*      */                         
/* 3939 */                         if (isPopup)
/*      */                         {
/*      */ 
/* 3942 */                           out.write("\n      \t<input type=\"button\" value=\"");
/* 3943 */                           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 3944 */                           out.write("\" class='buttons btn_link' onClick=\"javascript:window.parent.close()\" />        \n      \t");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 3950 */                           out.write("\n      \t<input type=\"button\" value=\"");
/* 3951 */                           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3952 */                           out.write("\" class='buttons btn_link' onClick=\"javascript:cancel();\" />\n      \t");
/*      */                         }
/*      */                         
/*      */ 
/* 3956 */                         out.write("\n      </td>\n      \n    </tr>\n  </table>\n  </td>\n   <td width=\"30%\" valign=\"top\">\n        \t");
/* 3957 */                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.actions.quicknote.mbeanoperation.help")), request.getCharacterEncoding()), out, false);
/* 3958 */                         out.write("\n\t\t</td>\n  </tr>\n  </table>\n");
/* 3959 */                         int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3960 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3964 */                     if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3965 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */                     }
/*      */                     else {
/* 3968 */                       this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3969 */                       out.write(10);
/*      */                     }
/* 3971 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3972 */         out = _jspx_out;
/* 3973 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3974 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3975 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3978 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3984 */     PageContext pageContext = _jspx_page_context;
/* 3985 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3987 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3988 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3989 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3991 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 3992 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3993 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3995 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 3996 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3997 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4001 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4002 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4003 */       return true;
/*      */     }
/* 4005 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4011 */     PageContext pageContext = _jspx_page_context;
/* 4012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4014 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4015 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4016 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4018 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 4019 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4020 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4022 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 4023 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4024 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4028 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4029 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4030 */       return true;
/*      */     }
/* 4032 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4038 */     PageContext pageContext = _jspx_page_context;
/* 4039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4041 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4042 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 4043 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 4045 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 4046 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 4048 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 4049 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 4051 */           out.write(" \n      ");
/* 4052 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 4053 */             return true;
/* 4054 */           out.write(32);
/* 4055 */           out.write(10);
/* 4056 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 4057 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4061 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 4062 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4065 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 4066 */         out = _jspx_page_context.popBody(); }
/* 4067 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4069 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 4070 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 4072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4077 */     PageContext pageContext = _jspx_page_context;
/* 4078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4080 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4081 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4082 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4084 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4086 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4087 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4088 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4089 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4090 */       return true;
/*      */     }
/* 4092 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4098 */     PageContext pageContext = _jspx_page_context;
/* 4099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4101 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4102 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4103 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4105 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 4107 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 4109 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 4110 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4111 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4112 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4113 */       return true;
/*      */     }
/* 4115 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4121 */     PageContext pageContext = _jspx_page_context;
/* 4122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4124 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4125 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4126 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4128 */     _jspx_th_c_005fout_005f0.setValue("${wizimage}");
/*      */     
/* 4130 */     _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 4131 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4132 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4133 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4134 */       return true;
/*      */     }
/* 4136 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4142 */     PageContext pageContext = _jspx_page_context;
/* 4143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4145 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4146 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4147 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4149 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 4150 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4151 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4153 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 4154 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 4155 */           return true;
/* 4156 */         out.write("&wiz=true\">\n    ");
/* 4157 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4158 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4162 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4163 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4164 */       return true;
/*      */     }
/* 4166 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4172 */     PageContext pageContext = _jspx_page_context;
/* 4173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4175 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4176 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4177 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4179 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 4180 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4181 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4182 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4183 */       return true;
/*      */     }
/* 4185 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4191 */     PageContext pageContext = _jspx_page_context;
/* 4192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4194 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4195 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4196 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4198 */     _jspx_th_c_005fout_005f2.setValue("${wizimage}");
/*      */     
/* 4200 */     _jspx_th_c_005fout_005f2.setEscapeXml("false");
/* 4201 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4202 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4204 */       return true;
/*      */     }
/* 4206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4212 */     PageContext pageContext = _jspx_page_context;
/* 4213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4215 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4216 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4217 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4219 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 4220 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4221 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4223 */         out.write("\n    \t</a>\n    \t");
/* 4224 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4225 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4229 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4230 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4231 */       return true;
/*      */     }
/* 4233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4239 */     PageContext pageContext = _jspx_page_context;
/* 4240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4242 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4243 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4244 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4246 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 4247 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4248 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4250 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 4251 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4252 */           return true;
/* 4253 */         out.write("&wiz=true\">\n       ");
/* 4254 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4255 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4259 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4260 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4261 */       return true;
/*      */     }
/* 4263 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4269 */     PageContext pageContext = _jspx_page_context;
/* 4270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4272 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4273 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4274 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4276 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 4277 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4278 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4280 */       return true;
/*      */     }
/* 4282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4288 */     PageContext pageContext = _jspx_page_context;
/* 4289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4291 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4292 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4293 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4295 */     _jspx_th_c_005fout_005f4.setValue("${wizimage}");
/*      */     
/* 4297 */     _jspx_th_c_005fout_005f4.setEscapeXml("false");
/* 4298 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4299 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4300 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4301 */       return true;
/*      */     }
/* 4303 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4309 */     PageContext pageContext = _jspx_page_context;
/* 4310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4312 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4313 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4314 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4316 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 4317 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4318 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4320 */         out.write("\n       \t</a>\n       \t");
/* 4321 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4322 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4326 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4327 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4328 */       return true;
/*      */     }
/* 4330 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4336 */     PageContext pageContext = _jspx_page_context;
/* 4337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4339 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4340 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4341 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4343 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4344 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4345 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4347 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 4348 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4349 */           return true;
/* 4350 */         out.write("&wiz=true\">\n ");
/* 4351 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4352 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4356 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4357 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4358 */       return true;
/*      */     }
/* 4360 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4366 */     PageContext pageContext = _jspx_page_context;
/* 4367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4369 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4370 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4371 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4373 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 4374 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4375 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4376 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4377 */       return true;
/*      */     }
/* 4379 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4380 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4385 */     PageContext pageContext = _jspx_page_context;
/* 4386 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4388 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4389 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4390 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4392 */     _jspx_th_c_005fout_005f6.setValue("${wizimage}");
/*      */     
/* 4394 */     _jspx_th_c_005fout_005f6.setEscapeXml("false");
/* 4395 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4396 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4397 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4398 */       return true;
/*      */     }
/* 4400 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4406 */     PageContext pageContext = _jspx_page_context;
/* 4407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4409 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4410 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4411 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4413 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4414 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4415 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 4417 */         out.write("\t    \n    </a>\n ");
/* 4418 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4419 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4423 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4424 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4425 */       return true;
/*      */     }
/* 4427 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4433 */     PageContext pageContext = _jspx_page_context;
/* 4434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4436 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 4437 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 4438 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 4440 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 4442 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 4443 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 4444 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 4445 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4446 */       return true;
/*      */     }
/* 4448 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 4449 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4454 */     PageContext pageContext = _jspx_page_context;
/* 4455 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4457 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 4458 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 4459 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 4461 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 4463 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 4464 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 4465 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 4466 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 4467 */       return true;
/*      */     }
/* 4469 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 4470 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4475 */     PageContext pageContext = _jspx_page_context;
/* 4476 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4478 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4479 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 4480 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4482 */     _jspx_th_am_005fhiddenparam_005f0.setName("popup");
/* 4483 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 4484 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 4485 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4486 */       return true;
/*      */     }
/* 4488 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4494 */     PageContext pageContext = _jspx_page_context;
/* 4495 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4497 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4498 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 4499 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4501 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectto");
/* 4502 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 4503 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 4504 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4505 */       return true;
/*      */     }
/* 4507 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4508 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4513 */     PageContext pageContext = _jspx_page_context;
/* 4514 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4516 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4517 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 4518 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4520 */     _jspx_th_am_005fhiddenparam_005f2.setName("returnpath");
/* 4521 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 4522 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 4523 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4524 */       return true;
/*      */     }
/* 4526 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4532 */     PageContext pageContext = _jspx_page_context;
/* 4533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4535 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4536 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 4537 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4539 */     _jspx_th_am_005fhiddenparam_005f3.setName("resourceid");
/* 4540 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 4541 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 4542 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4543 */       return true;
/*      */     }
/* 4545 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4551 */     PageContext pageContext = _jspx_page_context;
/* 4552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4554 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4555 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 4556 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4558 */     _jspx_th_am_005fhiddenparam_005f4.setName("attributeid");
/* 4559 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 4560 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 4561 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4562 */       return true;
/*      */     }
/* 4564 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4570 */     PageContext pageContext = _jspx_page_context;
/* 4571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4573 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4574 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 4575 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4577 */     _jspx_th_am_005fhiddenparam_005f5.setName("severity");
/* 4578 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 4579 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 4580 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4581 */       return true;
/*      */     }
/* 4583 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4589 */     PageContext pageContext = _jspx_page_context;
/* 4590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4592 */     HiddenParam _jspx_th_am_005fhiddenparam_005f6 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4593 */     _jspx_th_am_005fhiddenparam_005f6.setPageContext(_jspx_page_context);
/* 4594 */     _jspx_th_am_005fhiddenparam_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4596 */     _jspx_th_am_005fhiddenparam_005f6.setName("wiz");
/* 4597 */     int _jspx_eval_am_005fhiddenparam_005f6 = _jspx_th_am_005fhiddenparam_005f6.doStartTag();
/* 4598 */     if (_jspx_th_am_005fhiddenparam_005f6.doEndTag() == 5) {
/* 4599 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4600 */       return true;
/*      */     }
/* 4602 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4608 */     PageContext pageContext = _jspx_page_context;
/* 4609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4611 */     HiddenParam _jspx_th_am_005fhiddenparam_005f7 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4612 */     _jspx_th_am_005fhiddenparam_005f7.setPageContext(_jspx_page_context);
/* 4613 */     _jspx_th_am_005fhiddenparam_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4615 */     _jspx_th_am_005fhiddenparam_005f7.setName("global");
/* 4616 */     int _jspx_eval_am_005fhiddenparam_005f7 = _jspx_th_am_005fhiddenparam_005f7.doStartTag();
/* 4617 */     if (_jspx_th_am_005fhiddenparam_005f7.doEndTag() == 5) {
/* 4618 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4619 */       return true;
/*      */     }
/* 4621 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4622 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mopaction_005fshowmbeans_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */