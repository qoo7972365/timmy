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
/*      */ import java.util.Properties;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
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
/*      */ public final class mopaction_005finitialscreen_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
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
/* 2182 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2183 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2213 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2238 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2249 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2251 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/* 2256 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/* 2257 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2268 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2271 */     JspWriter out = null;
/* 2272 */     Object page = this;
/* 2273 */     JspWriter _jspx_out = null;
/* 2274 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2278 */       response.setContentType("text/html;charset=UTF-8");
/* 2279 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2281 */       _jspx_page_context = pageContext;
/* 2282 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2283 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2284 */       session = pageContext.getSession();
/* 2285 */       out = pageContext.getOut();
/* 2286 */       _jspx_out = out;
/*      */       
/* 2288 */       out.write("\n\n\n\n\n");
/* 2289 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2291 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2292 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2294 */       out.write(10);
/* 2295 */       out.write(10);
/* 2296 */       out.write(10);
/* 2297 */       out.write("\n<script>\n\n\nfunction showDomains()\n{\n   var value=document.MBeanOperationActionForm.actionname.value;\n   if(trimAll(value)==\"\")\n    {\n    \twindow.alert('");
/* 2298 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/* 2299 */       out.write("');\n    \treturn false;\n    }\n    if(displayNameHasQuotes(trimAll(value),'");
/* 2300 */       out.print(FormatUtil.getString("am.webclient.newaction.alertsinglequote"));
/* 2301 */       out.write("'))\n    {\n   \treturn false;\n    }\n\n    if(isBlackListedCharactersPresent(value,'");
/* 2302 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/* 2303 */       out.write("')) {\n\treturn false;\n    }    \n    value=document.MBeanOperationActionForm.sendmail.value;\n    if(value==0){\n    \twindow.alert('");
/* 2304 */       out.print(FormatUtil.getString("am.webclient.mbop.action.createmail"));
/* 2305 */       out.write("');\n    \treturn false;\n    }\n    document.MBeanOperationActionForm.submit();\n}\n\nfunction cancel() {\n\n     if(typeof(document.MBeanOperationActionForm.redirectto)!=\"undefined\")\n     {\n     \tlocation.href=document.MBeanOperationActionForm.redirectto.value;\n     }\n     else\n     {\n     \tlocation.href=\"/adminAction.do?method=showActionProfiles\";\n     }\n}\nfunction callAction()\n{\n\t showDiv(\"takeaction\");\n}\nfunction removeAction()\n{\n   hideDiv(\"takeaction\");\n}\n function getAction()\n{\n  if(document.MBeanOperationActionForm.actionname.value=='')\n\t{\n   alert(\"");
/* 2306 */       out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2307 */       out.write("\");\n   document.MBeanOperationActionForm.actionname.focus();\n   return false;\n  }\n if(document.MBeanOperationActionForm.priority.value=='')\n {\n   alert(\"");
/* 2308 */       out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduleemail.text"));
/* 2309 */       out.write("\");\n   document.MBeanOperationActionForm.priority.focus();\n   return false;\n }\n else\n {\n  var a=document.MBeanOperationActionForm.priority.value;\n  var b=encodeURIComponent(document.MBeanOperationActionForm.actionname.value);\n  url=\"/adminAction.do?method=sendActionDetails&emailid=\"+a+\"&emailname=\"+b; //NO I18N\n\n  http.open(\"GET\",url,true);\n  http.onreadystatechange = getActionTypes;\n  http.send(null);\n }\n\n}\n\n\nfunction getActionTypes()\n{\n  if(http.readyState == 4)\n  {\n    var result = http.responseText;\n    hideDiv(\"takeaction\");\n    var id=result;\n    var stringtokens=id.split(\",\");\n    smessage=stringtokens[0];\n    sid=stringtokens[1];\n    hideDiv(\"actionmessage\");\n    if(sid==0){\n          showDiv(\"actionmessage\");\n          return;\n    }\n   if(smessage !='null' || smessage != '')\n   {\n           hideDiv(\"actionmessage\");\n          document.MBeanOperationActionForm.sendmail.options[document.MBeanOperationActionForm.sendmail.length] =new Option(smessage,sid,false,true);\n   }\n   else\n   {\n          showDiv(\"actionmessage\");\n");
/* 2310 */       out.write("          document.getElementById(\"actionmessage\").innerHTML=smessage;\n   }\n  }\n}\n</script>\n<link href=\"/images/");
/* 2311 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2313 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/*      */ 
/* 2316 */       String resourceID = request.getParameter("resourceid");
/* 2317 */       List datacollectableAgents = (List)request.getAttribute("datacollectableagents");
/* 2318 */       if (datacollectableAgents == null)
/*      */       {
/* 2320 */         datacollectableAgents = com.adventnet.appmanager.cam.CAMDBUtil.getDataCollectableAgentDetails(false);
/*      */       }
/* 2322 */       boolean nomonitorsPresent = (datacollectableAgents == null) || (datacollectableAgents.size() == 0);
/*      */       
/*      */ 
/* 2325 */       out.write(10);
/* 2326 */       out.write(10);
/*      */       
/* 2328 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2329 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2330 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/* 2332 */       _jspx_th_html_005fform_005f0.setAction("/MBeanOperationAction.do");
/*      */       
/* 2334 */       _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2335 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2336 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/* 2338 */           out.write(10);
/* 2339 */           out.write(10);
/* 2340 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/* 2342 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/* 2345 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/* 2349 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/* 2350 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2352 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/* 2353 */           out.write("\t  {\n\t\t\t");
/* 2354 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2356 */           out.write("\n\t\t  ");
/*      */           
/* 2358 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */           {
/* 2360 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/* 2364 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/* 2366 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/* 2367 */           if (EnterpriseUtil.isManagedServer())
/*      */           {
/* 2369 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/* 2373 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/* 2375 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/* 2376 */           if (EnterpriseUtil.isManagedServer())
/*      */           {
/* 2378 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/* 2382 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/* 2384 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/* 2385 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2386 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/* 2388 */           else if (EnterpriseUtil.isManagedServer()) {
/* 2389 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/* 2391 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/* 2393 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/* 2394 */           if (EnterpriseUtil.isManagedServer()) {
/* 2395 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/* 2397 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/* 2399 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/* 2401 */           String action_haid = request.getParameter("haid");
/* 2402 */           String returnpath = "";
/*      */           
/* 2404 */           if (request.getParameter("returnpath") != null)
/*      */           {
/* 2406 */             returnpath = "&returnpath=" + URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/* 2410 */           out.write(10);
/* 2411 */           out.write(10);
/*      */           
/* 2413 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2414 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2415 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2417 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/* 2418 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2419 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2420 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2421 */               out = _jspx_page_context.pushBody();
/* 2422 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2423 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2426 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/* 2427 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2428 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2431 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2432 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2435 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2436 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/* 2439 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2440 */           out.write(10);
/*      */           
/* 2442 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2443 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2444 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2446 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/* 2447 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2448 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2449 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2450 */               out = _jspx_page_context.pushBody();
/* 2451 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2452 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2455 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/* 2456 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2457 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2460 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2461 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2464 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2465 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/* 2468 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2469 */           out.write(10);
/*      */           
/* 2471 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2472 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2473 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2475 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/* 2476 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2477 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2478 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2479 */               out = _jspx_page_context.pushBody();
/* 2480 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2481 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2484 */               out.print(EnterpriseUtil.isAdminServer());
/* 2485 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2486 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2489 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2490 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2493 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2494 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/* 2497 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2498 */           out.write(10);
/*      */           
/* 2500 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2501 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2502 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2504 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/* 2505 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2506 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2507 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2508 */               out = _jspx_page_context.pushBody();
/* 2509 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2510 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2513 */               out.print(EnterpriseUtil.isProfEdition());
/* 2514 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2515 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2518 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2519 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2522 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2523 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/* 2526 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2527 */           out.write(10);
/*      */           
/* 2529 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2530 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 2531 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2533 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/* 2534 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 2535 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/* 2536 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2537 */               out = _jspx_page_context.pushBody();
/* 2538 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 2539 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2542 */               out.print(EnterpriseUtil.isCloudEdition());
/* 2543 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 2544 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2547 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2548 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2551 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 2552 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/* 2555 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2556 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/* 2557 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/* 2558 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/* 2560 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2561 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2562 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 2563 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2564 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/* 2566 */               out.write(10);
/* 2567 */               out.write(9);
/*      */               
/* 2569 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2570 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2571 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/* 2573 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/* 2574 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2575 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/* 2577 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2578 */                   out.print(action_haid);
/* 2579 */                   out.print(returnpath);
/* 2580 */                   out.write(34);
/* 2581 */                   out.write(62);
/* 2582 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2583 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/* 2584 */                   out.print(action_haid);
/* 2585 */                   out.print(returnpath);
/* 2586 */                   out.write(34);
/* 2587 */                   out.write(62);
/* 2588 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2589 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/* 2590 */                   out.print(action_haid);
/* 2591 */                   out.print(returnpath);
/* 2592 */                   out.write(34);
/* 2593 */                   out.write(62);
/* 2594 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2595 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/* 2596 */                   out.print(action_haid);
/* 2597 */                   out.print(returnpath);
/* 2598 */                   out.write(34);
/* 2599 */                   out.write(62);
/* 2600 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/* 2601 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/* 2603 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2604 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2605 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/* 2606 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2607 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 2609 */                       out.write("\n\t\t\t");
/*      */                       
/* 2611 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2612 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2613 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2615 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/* 2616 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2617 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 2619 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 2621 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2622 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2623 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 2624 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2625 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/* 2627 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/* 2629 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2630 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2631 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/* 2633 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/* 2634 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2635 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/* 2637 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2638 */                                   out.print(action_haid);
/* 2639 */                                   out.print(returnpath);
/* 2640 */                                   out.write(34);
/* 2641 */                                   out.write(62);
/* 2642 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2643 */                                   out.write("</option>\n\t\t\t\t\t");
/* 2644 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2645 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2649 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2650 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/* 2653 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2654 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/* 2656 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2657 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2658 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/* 2659 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2660 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 2662 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 2663 */                                   out.print(action_haid);
/* 2664 */                                   out.print(returnpath);
/* 2665 */                                   out.write(34);
/* 2666 */                                   out.write(62);
/* 2667 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 2668 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 2669 */                                   out.print(action_haid);
/* 2670 */                                   out.print(returnpath);
/* 2671 */                                   out.write(34);
/* 2672 */                                   out.write(62);
/* 2673 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 2674 */                                   out.write("</option>\n\t\t\t\t\t");
/* 2675 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2676 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2680 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2681 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 2684 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2685 */                               out.write("\n\t\t\t\t");
/* 2686 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2687 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2691 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2692 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/* 2695 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2696 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/* 2697 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2698 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2699 */                             out.print(action_haid);
/* 2700 */                             out.print(returnpath);
/* 2701 */                             out.write(34);
/* 2702 */                             out.write(62);
/* 2703 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2704 */                             out.write("</option> ");
/*      */                           }
/* 2706 */                           out.write("\n\t\t\t");
/* 2707 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2708 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2712 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2713 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 2716 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2717 */                       out.write("\n\t\t\t");
/*      */                       
/* 2719 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2720 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2721 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2722 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2723 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 2725 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2726 */                           out.print(action_haid);
/* 2727 */                           out.print(returnpath);
/* 2728 */                           out.write(34);
/* 2729 */                           out.write(62);
/* 2730 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2731 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/* 2733 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2734 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2735 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/* 2737 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/* 2738 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2739 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 2741 */                               out.write("\n\t\t\t\t\t");
/* 2742 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2743 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/* 2744 */                                 out.print(action_haid);
/* 2745 */                                 out.print(returnpath);
/* 2746 */                                 out.write(34);
/* 2747 */                                 out.write(62);
/* 2748 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2749 */                                 out.write("</option> ");
/*      */                               }
/* 2751 */                               out.write("\n\t\t   \t\t");
/* 2752 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2753 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2757 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2758 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/* 2761 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2762 */                           out.write("\n\t\t\t");
/* 2763 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2764 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2768 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2769 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 2772 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2773 */                       out.write(10);
/* 2774 */                       out.write(9);
/* 2775 */                       out.write(9);
/* 2776 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2777 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2781 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2782 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 2785 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2786 */                   out.write(10);
/* 2787 */                   out.write(9);
/* 2788 */                   out.write(9);
/*      */                   
/* 2790 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2791 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2792 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/* 2794 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/* 2795 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2796 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/* 2798 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 2799 */                       out.print(action_haid);
/* 2800 */                       out.print(returnpath);
/* 2801 */                       out.write(34);
/* 2802 */                       out.write(62);
/* 2803 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 2804 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 2805 */                       out.print(action_haid);
/* 2806 */                       out.print(returnpath);
/* 2807 */                       out.write(34);
/* 2808 */                       out.write(62);
/* 2809 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 2810 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/* 2811 */                       out.print(action_haid);
/* 2812 */                       out.print(returnpath);
/* 2813 */                       out.write(34);
/* 2814 */                       out.write(62);
/* 2815 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2816 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/* 2818 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2819 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2820 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/* 2822 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/* 2823 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2824 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2826 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 2827 */                           out.print(action_haid);
/* 2828 */                           out.print(returnpath);
/* 2829 */                           out.write(34);
/* 2830 */                           out.write(62);
/* 2831 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 2832 */                           out.write("</option>\n\t   \t\t");
/* 2833 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2834 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2838 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2839 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2842 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2843 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/* 2844 */                       out.print(action_haid);
/* 2845 */                       out.print(returnpath);
/* 2846 */                       out.write(34);
/* 2847 */                       out.write(62);
/* 2848 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/* 2849 */                       out.write("</option>\n   \t\t");
/* 2850 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2851 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2855 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2856 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/* 2859 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2860 */                   out.write(10);
/* 2861 */                   out.write(9);
/* 2862 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2863 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2867 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2868 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/* 2871 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2872 */               out.write(10);
/* 2873 */               out.write(9);
/*      */               
/* 2875 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2876 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2877 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/* 2878 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2879 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/* 2881 */                   out.write(10);
/*      */                   
/* 2883 */                   String redirectTo = null;
/* 2884 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/* 2886 */                     redirectTo = URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/* 2890 */                     redirectTo = URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/* 2893 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/* 2894 */                   out.print(action_haid);
/* 2895 */                   out.write("&global=true");
/* 2896 */                   out.print(returnpath);
/* 2897 */                   out.write(34);
/* 2898 */                   out.write(62);
/* 2899 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2900 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/* 2901 */                   out.print(action_haid);
/* 2902 */                   out.write("&global=true");
/* 2903 */                   out.print(returnpath);
/* 2904 */                   out.write(34);
/* 2905 */                   out.write(62);
/* 2906 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2907 */                   out.write("</option>\n\t");
/*      */                   
/* 2909 */                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2910 */                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2911 */                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/* 2913 */                   _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 2914 */                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2915 */                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                     for (;;) {
/* 2917 */                       out.write(32);
/* 2918 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/* 2919 */                       out.print(action_haid);
/* 2920 */                       out.write("&global=true");
/* 2921 */                       out.print(returnpath);
/* 2922 */                       out.write(34);
/* 2923 */                       out.write(62);
/* 2924 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2925 */                       out.write("</option>\n\t");
/* 2926 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2927 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2931 */                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2932 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                   }
/*      */                   
/* 2935 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2936 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/* 2937 */                   out.print(action_haid);
/* 2938 */                   out.write("&global=true");
/* 2939 */                   out.print(returnpath);
/* 2940 */                   out.write(34);
/* 2941 */                   out.write(62);
/* 2942 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/* 2943 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/* 2944 */                   out.print(action_haid);
/* 2945 */                   out.write("&global=true");
/* 2946 */                   out.print(returnpath);
/* 2947 */                   out.write(34);
/* 2948 */                   out.write(62);
/* 2949 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/* 2950 */                   out.write("</option>\n\t");
/* 2951 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 2952 */                     out.write(32);
/* 2953 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/* 2954 */                     out.print(action_haid);
/* 2955 */                     out.print(returnpath);
/* 2956 */                     out.write(34);
/* 2957 */                     out.write(62);
/* 2958 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/* 2959 */                     out.write("</option>\n\t");
/*      */                   }
/* 2961 */                   out.write(10);
/* 2962 */                   out.write(9);
/* 2963 */                   out.write(9);
/* 2964 */                   out.write(10);
/* 2965 */                   out.write(9);
/* 2966 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                   {
/* 2968 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/* 2969 */                     out.print(action_haid);
/* 2970 */                     out.write("&redirectTo=");
/* 2971 */                     out.print(redirectTo);
/* 2972 */                     out.write(34);
/* 2973 */                     out.write(62);
/* 2974 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2975 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/* 2978 */                   out.write("\n\t\n\t");
/* 2979 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/* 2980 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/* 2981 */                     out.print(action_haid);
/* 2982 */                     out.write("&global=true");
/* 2983 */                     out.print(returnpath);
/* 2984 */                     out.write("&ext=true\">");
/* 2985 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/* 2986 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/* 2987 */                     out.print(action_haid);
/* 2988 */                     out.print(returnpath);
/* 2989 */                     out.write("&ext=true&global=true\">");
/* 2990 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 2991 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/* 2992 */                     out.print(action_haid);
/* 2993 */                     out.write("&global=true");
/* 2994 */                     out.print(returnpath);
/* 2995 */                     out.write("&ext=true\">");
/* 2996 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 2997 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/* 2998 */                     out.print(action_haid);
/* 2999 */                     out.print(returnpath);
/* 3000 */                     out.write("&ext=true&global=true\">");
/* 3001 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/* 3002 */                     out.write("</option>\n\t ");
/* 3003 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3004 */                     out.write(32);
/* 3005 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/* 3006 */                     out.print(action_haid);
/* 3007 */                     out.write("&global=true");
/* 3008 */                     out.print(returnpath);
/* 3009 */                     out.write(34);
/* 3010 */                     out.write(62);
/* 3011 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 3012 */                     out.write("</option>\n\t");
/*      */                   }
/* 3014 */                   out.write(10);
/* 3015 */                   out.write(9);
/* 3016 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/* 3017 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 3018 */                     out.print(action_haid);
/* 3019 */                     out.print(returnpath);
/* 3020 */                     out.write(34);
/* 3021 */                     out.write(62);
/* 3022 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 3023 */                     out.write("</option>\t\n\t");
/*      */                   }
/* 3025 */                   out.write(10);
/* 3026 */                   out.write(9);
/* 3027 */                   out.write(32);
/* 3028 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3029 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 3030 */                     out.print(action_haid);
/* 3031 */                     out.print(returnpath);
/* 3032 */                     out.write(34);
/* 3033 */                     out.write(62);
/* 3034 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 3035 */                     out.write("</option>\n\t");
/*      */                   }
/* 3037 */                   out.write(10);
/* 3038 */                   out.write(9);
/* 3039 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3040 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3044 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3045 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/* 3048 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3049 */               out.write(10);
/* 3050 */               out.write(9);
/* 3051 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3052 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3056 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3057 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/* 3060 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3061 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/* 3063 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3064 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3065 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 3067 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/* 3068 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3069 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/* 3071 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 3072 */               out.write("<!--$Id$-->\n\n\n\n");
/* 3073 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/* 3075 */               out.write("\n      \n\n");
/*      */               
/* 3077 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3078 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3079 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 3081 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 3082 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3083 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 3085 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 3086 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3088 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/* 3090 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3091 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3092 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3094 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 3095 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3096 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 3098 */                       out.write("\n    \t");
/*      */                       
/* 3100 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3101 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3102 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 3104 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 3105 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3106 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/* 3107 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3108 */                           out = _jspx_page_context.pushBody();
/* 3109 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 3110 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3113 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3114 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3115 */                           out.write(" </b></font>\n    \t");
/* 3116 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 3117 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3120 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3121 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3124 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3125 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 3128 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 3129 */                       out.write("\n    ");
/* 3130 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3131 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3135 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3136 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 3139 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3140 */                   out.write("\n    ");
/*      */                   
/* 3142 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3143 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3144 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3146 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 3147 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3148 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 3150 */                       out.write("\n    \t");
/*      */                       
/* 3152 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3153 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3154 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 3156 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 3157 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3158 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 3159 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3160 */                           out = _jspx_page_context.pushBody();
/* 3161 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 3162 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3165 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 3166 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 3167 */                           out.write("</font>  \t");
/* 3168 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 3169 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3172 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3173 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3176 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3177 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 3180 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 3181 */                       out.write("\n    ");
/* 3182 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3183 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3187 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3188 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 3191 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3192 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 3193 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3195 */                   out.write("</td>  \n   \n");
/*      */                   
/* 3197 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3198 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3199 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 3200 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3201 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 3203 */                       out.write("\n    ");
/*      */                       
/* 3205 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3206 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3207 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 3209 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 3210 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3211 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 3213 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 3215 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3216 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 3217 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 3219 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 3220 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 3221 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 3222 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3223 */                               out = _jspx_page_context.pushBody();
/* 3224 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 3225 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3228 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 3229 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3230 */                               out.write(" </b></font>\n    \t");
/* 3231 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 3232 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3235 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 3236 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3239 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 3240 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 3243 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 3244 */                           out.write("\n   ");
/* 3245 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3246 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3250 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3251 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 3254 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3255 */                       out.write("\n   ");
/*      */                       
/* 3257 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3258 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3259 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 3260 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3261 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 3263 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 3265 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3266 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 3267 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 3269 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 3270 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 3271 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 3272 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3273 */                               out = _jspx_page_context.pushBody();
/* 3274 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 3275 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3278 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3279 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 3280 */                               out.write(" </font>\n    \t");
/* 3281 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 3282 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3285 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3286 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3289 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 3290 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 3293 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 3294 */                           out.write("\n\t\n\t\t\n   ");
/* 3295 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3296 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3300 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3301 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 3304 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3305 */                       out.write(10);
/* 3306 */                       out.write(32);
/* 3307 */                       out.write(32);
/* 3308 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3309 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3313 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3314 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 3317 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3318 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 3319 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3321 */                   out.write("\n    \t");
/* 3322 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3324 */                   out.write("\n    \t\n    \t");
/* 3325 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3327 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 3329 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3330 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3331 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 3332 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3333 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 3335 */                       out.write("\n       ");
/*      */                       
/* 3337 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3338 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3339 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 3341 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 3342 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3343 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 3345 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 3347 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3348 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 3349 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 3351 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 3352 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 3353 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 3354 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3355 */                               out = _jspx_page_context.pushBody();
/* 3356 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 3357 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3360 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 3361 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3362 */                               out.write(" </b></font>\n   \t    \t");
/* 3363 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 3364 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3367 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 3368 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3371 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 3372 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 3375 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 3376 */                           out.write("\n       ");
/* 3377 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3378 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3382 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3383 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 3386 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3387 */                       out.write("\n        ");
/*      */                       
/* 3389 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3390 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3391 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 3392 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3393 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 3395 */                           out.write("  \n   \t    \t");
/*      */                           
/* 3397 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3398 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 3399 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 3401 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 3402 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 3403 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 3404 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3405 */                               out = _jspx_page_context.pushBody();
/* 3406 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 3407 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3410 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3411 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 3412 */                               out.write(" </font>\n   \t    \t");
/* 3413 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 3414 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3417 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 3418 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3421 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 3422 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 3425 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 3426 */                           out.write("\n   \t");
/* 3427 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3428 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3432 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3433 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 3436 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3437 */                       out.write(10);
/* 3438 */                       out.write(32);
/* 3439 */                       out.write(32);
/* 3440 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3441 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3445 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3446 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 3449 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3450 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 3451 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3453 */                   out.write("\n       ");
/* 3454 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3456 */                   out.write("\n       ");
/* 3457 */                   out.write("\n       \t");
/* 3458 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3460 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 3462 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3463 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3464 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3466 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 3467 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3468 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 3470 */                       out.write(10);
/* 3471 */                       out.write(9);
/*      */                       
/* 3473 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3474 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 3475 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 3477 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 3478 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 3479 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 3480 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3481 */                           out = _jspx_page_context.pushBody();
/* 3482 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 3483 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3486 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 3487 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3488 */                           out.write(" </b></font>\n    \t");
/* 3489 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 3490 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3493 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 3494 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3497 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 3498 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 3501 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 3502 */                       out.write(10);
/* 3503 */                       out.write(9);
/* 3504 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3505 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3509 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3510 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 3513 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3514 */                   out.write(10);
/* 3515 */                   out.write(9);
/*      */                   
/* 3517 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3518 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3519 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 3521 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 3522 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3523 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 3525 */                       out.write(10);
/* 3526 */                       out.write(9);
/*      */                       
/* 3528 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3529 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 3530 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 3532 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 3533 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 3534 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 3535 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3536 */                           out = _jspx_page_context.pushBody();
/* 3537 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 3538 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3541 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 3542 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 3543 */                           out.write(" </font>\n    \t");
/* 3544 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 3545 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3548 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 3549 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3552 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 3553 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 3556 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 3557 */                       out.write("\n\t\n\t");
/* 3558 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3559 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3563 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3564 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 3567 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3568 */                   out.write(10);
/* 3569 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3571 */                   out.write("   \n ");
/* 3572 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3574 */                   out.write(10);
/* 3575 */                   out.write(32);
/* 3576 */                   out.write(10);
/* 3577 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 3579 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 3580 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 3581 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3582 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3586 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3587 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 3590 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3591 */               out.write(10);
/* 3592 */               out.write(10);
/* 3593 */               if (request.getAttribute("EmailForm") == null) {
/* 3594 */                 out.write(10);
/*      */                 
/* 3596 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3597 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 3598 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 3600 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 3602 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 3603 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 3604 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 3605 */                   String msg = null;
/* 3606 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3607 */                     out = _jspx_page_context.pushBody();
/* 3608 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 3609 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 3611 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 3613 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\"><bean:write name=\"msg\"  filter=\"false\"  /></td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 3614 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 3615 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 3616 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3619 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 3620 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3623 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 3624 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 3627 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 3629 */               out.write(32);
/*      */               
/* 3631 */               MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 3632 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 3633 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 3635 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 3636 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 3637 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 3639 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 3641 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 3642 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 3643 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 3645 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 3647 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 3648 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 3649 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 3650 */                     String msg = null;
/* 3651 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3652 */                       out = _jspx_page_context.pushBody();
/* 3653 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 3654 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 3656 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 3658 */                       out.write("\n\t  <bean:write name=\"msg\" filter=\"false\" /><br>\n\t  ");
/* 3659 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 3660 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 3661 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3664 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 3665 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3668 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 3669 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 3672 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 3673 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 3674 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 3675 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3679 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 3680 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 3683 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 3684 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 3685 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 3686 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3687 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3691 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3692 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 3695 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3696 */           out.write(10);
/* 3697 */           out.write("\n<input type=\"hidden\" name=\"method\" value=\"showDomains\">\n");
/* 3698 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3700 */           out.write(10);
/* 3701 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3703 */           out.write(10);
/* 3704 */           if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3706 */           out.write(10);
/* 3707 */           if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3709 */           out.write(10);
/* 3710 */           if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3712 */           out.write(10);
/* 3713 */           if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3715 */           out.write(10);
/* 3716 */           if (_jspx_meth_am_005fhiddenparam_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3718 */           out.write(10);
/* 3719 */           if (_jspx_meth_am_005fhiddenparam_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3721 */           out.write(10);
/*      */           
/* 3723 */           boolean isPopup = request.getParameter("popup") != null;
/*      */           
/* 3725 */           out.write("\n<div id=\"actionmessage\" style=\"display:none\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n\t<td  class=\"msg-status-tp-left-corn\"></td>\n\t<td  class=\"msg-status-top-mid-bg\"></td>\n\t<td  class=\"msg-status-tp-right-corn\"></td>\n</tr>\n<tr> \n\t<td class=\"msg-status-left-bg\">&nbsp;</td>\n\t<td width=\"98%\" class=\"msg-table-width\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\">\n\t<tr>\n\t<td width=\"2%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_failure.gif\" alt=\"icon\" height=\"20\" width=\"20\"></td>\n\t<td width=\"98%\" class=\"msg-table-width\">&nbsp;");
/* 3726 */           out.print(FormatUtil.getString("am.webclient.mbop.nomailconfigure.text"));
/* 3727 */           out.write("</td>\n\t</tr>\n\t</table>\n\t</td>\n\t<td class=\"msg-status-right-bg\"></td>\n</tr>\n<tr>\n\t<td class=\"msg-status-btm-left-corn\">&nbsp;</td>\n\t<td class=\"msg-status-btm-mid-bg\">&nbsp;</td>\n\t<td class=\"msg-status-btm-right-corn\" >&nbsp;</td>\n</tr>\n</table>\n</div>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n    <tr>\n    <td width=\"2%\" class=\"tableheading-monitor-config \"><img src=\"/images/bean.png\" class=\"tableheading-add-icon\"></td>\n\n      <td width=\"98%\" height=\"31\" class=\"tableheading-monitor-config\" >\n        ");
/* 3728 */           out.print(FormatUtil.getString("am.webclient.newaction.mbeanstep1"));
/* 3729 */           out.write("</td>\n    </tr>\n  </table>\n<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\" width=\"99%\">\n   ");
/*      */           
/* 3731 */           String discoveryLink = "/adminAction.do?method=reloadHostDiscoveryForm&type=JMX1.2-MX4J-RMI:1099&port=1099&redirectto=" + URLEncoder.encode(new StringBuilder().append("/MBeanOperationAction.do?method=showInitialScreen&haid=").append(request.getParameter("haid")).toString());
/* 3732 */           if (!nomonitorsPresent)
/*      */           {
/*      */ 
/* 3735 */             out.write("\n   <tr>\n       <td width=\"25%\" class=\"bodytext label-align\">&nbsp;");
/* 3736 */             out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 3737 */             out.write("</td>\n       <td colspan=\"2\" class=\"bodytext\"> ");
/* 3738 */             if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 3740 */             out.write("</td>\n  </tr>\n  <tr>\n    <td width=\"25%\" height=\"37\" class=\"label-align\"><SPAN class='bodytext'>&nbsp;");
/* 3741 */             out.print(FormatUtil.getString("am.webclient.newaction.mbeanmonitor"));
/* 3742 */             out.write("</SPAN></td>\n    <td width=\"25%\" align=\"left\" valign=\"middle\" class=\"bodytext\">\n      <select name=\"actionresourceid\" class=\"formtext default\">\n        ");
/*      */             
/* 3744 */             IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3745 */             _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3746 */             _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 3748 */             _jspx_th_logic_005fiterate_005f0.setName("datacollectableagents");
/*      */             
/* 3750 */             _jspx_th_logic_005fiterate_005f0.setId("agent");
/*      */             
/* 3752 */             _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */             
/* 3754 */             _jspx_th_logic_005fiterate_005f0.setType("java.util.List");
/* 3755 */             int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3756 */             if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3757 */               List agent = null;
/* 3758 */               Integer j = null;
/* 3759 */               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3760 */                 out = _jspx_page_context.pushBody();
/* 3761 */                 _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3762 */                 _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */               }
/* 3764 */               agent = (List)_jspx_page_context.findAttribute("agent");
/* 3765 */               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */               for (;;) {
/* 3767 */                 out.write("\n        ");
/*      */                 
/* 3769 */                 String selected = "";
/* 3770 */                 if ((resourceID != null) && (resourceID.equals(agent.get(0))))
/*      */                 {
/* 3772 */                   selected = "selected";
/*      */                 }
/*      */                 
/* 3775 */                 out.write("\n        <option value=\"");
/* 3776 */                 out.print(agent.get(0));
/* 3777 */                 out.write(34);
/* 3778 */                 out.write(32);
/* 3779 */                 out.print(selected);
/* 3780 */                 out.write(62);
/* 3781 */                 out.print(getTrimmedText((String)agent.get(1), 45));
/* 3782 */                 out.write("</option>\n        ");
/* 3783 */                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3784 */                 agent = (List)_jspx_page_context.findAttribute("agent");
/* 3785 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 3786 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 3789 */               if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3790 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 3793 */             if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3794 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */             }
/*      */             
/* 3797 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3798 */             out.write("\n        </select>\n        <td width=\"50%\" align=\"left\"><span class=\"bodytext\">&nbsp;&nbsp;");
/* 3799 */             out.print(FormatUtil.getString("am.webclient.newaction.mbeanaddmonitor", new String[] { discoveryLink }));
/* 3800 */             out.write(" </span></tr>\n        <tr>\n        \t<td class=\"bodytext label-align\" width=\"25%\">");
/* 3801 */             out.print(FormatUtil.getString("am.webclient.mbop.notifyaction.text"));
/* 3802 */             out.write("</td>\n        \t<td class=\"bodytext\" width=\"25%\" align=\"left\" valign=\"middle\" >\n\t\t");
/* 3803 */             if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 3805 */             out.write("\n\t\t<td class=\"bodytext\" width=\"50%\" >&nbsp;&nbsp;<a href='javascript:callAction()' class='staticlinks'>");
/* 3806 */             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverynewaction.text"));
/* 3807 */             out.write(" </a></td>\n\t       </td>\n\t</tr>\n\t<tr>\n    \t<td class=\"bodytext\" width=\"25%\"></td>\n    \t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n    \t   <div id='takeaction' style=\"display:none;\">\n    \t      <table width='99%' cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n              \t<tr>\n                \t<td class='bodytext'> ");
/* 3808 */             out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 3809 */             out.write(32);
/* 3810 */             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 3812 */             out.write(" <input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:getAction();\" value=\"");
/* 3813 */             out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 3814 */             out.write("\"> <input name=\"cancel1\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3815 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3816 */             out.write("\" onclick='javascript:removeAction()'></TD>\n    \t      </table>\n    \t   </div>\n    \t</td>\n\t</tr>\n\t");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3822 */             out.write("\n\t<tr>\n        \t<td colspan=\"3\" class=\"bodytext\">&nbsp;&nbsp;");
/* 3823 */             out.print(FormatUtil.getString("am.webclient.newaction.mbeannojmxmonitor", new String[] { discoveryLink }));
/* 3824 */             out.write("</td>\n        </tr>\n\t");
/*      */           }
/*      */           
/*      */ 
/* 3828 */           out.write("\n\t<tr>\n\t    <td class=\"tablebottom\" width=\"25%\" style=\"height:30px; color:#ff0000; font-size:11px;\">* ");
/* 3829 */           out.print(FormatUtil.getString("am.webclient.newaction.trapfieldsnote"));
/* 3830 */           out.write("</td>\n\t    <td class=\"tablebottom\" width=\"75%\" colspan='2'>\n\t    ");
/*      */           
/* 3832 */           if (!nomonitorsPresent)
/*      */           {
/*      */ 
/* 3835 */             out.write("\n\t        <input name=\"button\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:showDomains();\" value=\"");
/* 3836 */             out.print(FormatUtil.getString("am.webclient.newaction.mbeanshowdomains"));
/* 3837 */             out.write("\"/>\n\t        &nbsp;&nbsp;\n\t        ");
/*      */           }
/*      */           
/*      */ 
/* 3841 */           out.write("\n\t        ");
/*      */           
/* 3843 */           if (isPopup)
/*      */           {
/*      */ 
/* 3846 */             out.write("\n\t\t<input type=\"button\" value=\"");
/* 3847 */             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 3848 */             out.write("\" class='buttons btn_link' onClick=\"javascript:window.parent.close()\" />\n\t\t");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 3854 */             out.write("\n\t\t<input type=\"button\" value=\"");
/* 3855 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3856 */             out.write("\" class='buttons btn_link' onClick=\"javascript:cancel();\" />\n\t\t");
/*      */           }
/*      */           
/*      */ 
/* 3860 */           out.write("\n\t </td>\n  </tr>\n</table>\n</td>\n <td width=\"30%\" valign=\"top\">\n        \t");
/* 3861 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.actions.quicknote.mbeanoperationinitial.help")), request.getCharacterEncoding()), out, false);
/* 3862 */           out.write("\n\t\t</td>\n</tr>\n</table>\n\n");
/* 3863 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3864 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3868 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3869 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 3872 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3873 */         out.write(10);
/*      */       }
/* 3875 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3876 */         out = _jspx_out;
/* 3877 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3878 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3879 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3882 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3888 */     PageContext pageContext = _jspx_page_context;
/* 3889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3891 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3892 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3893 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3895 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3897 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3898 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3899 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3900 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3901 */       return true;
/*      */     }
/* 3903 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3909 */     PageContext pageContext = _jspx_page_context;
/* 3910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3912 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3913 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3914 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3916 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 3917 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3918 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3920 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 3921 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3922 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3926 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3927 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3928 */       return true;
/*      */     }
/* 3930 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3936 */     PageContext pageContext = _jspx_page_context;
/* 3937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3939 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3940 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3941 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3943 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 3944 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3945 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3947 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 3948 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3953 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3954 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3955 */       return true;
/*      */     }
/* 3957 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3963 */     PageContext pageContext = _jspx_page_context;
/* 3964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3966 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3967 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3968 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3970 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3971 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3973 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3974 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3976 */           out.write(" \n      ");
/* 3977 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3978 */             return true;
/* 3979 */           out.write(32);
/* 3980 */           out.write(10);
/* 3981 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3982 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3986 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3987 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3990 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 3991 */         out = _jspx_page_context.popBody(); }
/* 3992 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3994 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3995 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 4002 */     PageContext pageContext = _jspx_page_context;
/* 4003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4005 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4006 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 4007 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 4009 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 4011 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 4012 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 4013 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 4014 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4015 */       return true;
/*      */     }
/* 4017 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 4018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4023 */     PageContext pageContext = _jspx_page_context;
/* 4024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4026 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 4027 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 4028 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4030 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 4032 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 4034 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 4035 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 4036 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 4037 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4038 */       return true;
/*      */     }
/* 4040 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 4041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4046 */     PageContext pageContext = _jspx_page_context;
/* 4047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4049 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4050 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4051 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4053 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 4055 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 4056 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4057 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4058 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4059 */       return true;
/*      */     }
/* 4061 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4067 */     PageContext pageContext = _jspx_page_context;
/* 4068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4070 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4071 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4072 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4074 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 4075 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4076 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4078 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 4079 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 4080 */           return true;
/* 4081 */         out.write("&wiz=true\">\n    ");
/* 4082 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4083 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4087 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4088 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4089 */       return true;
/*      */     }
/* 4091 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4097 */     PageContext pageContext = _jspx_page_context;
/* 4098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4100 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4101 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4102 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4104 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 4105 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4106 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4107 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4108 */       return true;
/*      */     }
/* 4110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4116 */     PageContext pageContext = _jspx_page_context;
/* 4117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4119 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4120 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4121 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4123 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 4125 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 4126 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4127 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4128 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4129 */       return true;
/*      */     }
/* 4131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4137 */     PageContext pageContext = _jspx_page_context;
/* 4138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4140 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4141 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4142 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4144 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 4145 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4146 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4148 */         out.write("\n    \t</a>\n    \t");
/* 4149 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4150 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4154 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4155 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4156 */       return true;
/*      */     }
/* 4158 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4164 */     PageContext pageContext = _jspx_page_context;
/* 4165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4167 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4168 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4169 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4171 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 4172 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4173 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4175 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 4176 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4177 */           return true;
/* 4178 */         out.write("&wiz=true\">\n       ");
/* 4179 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4180 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4184 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4185 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4186 */       return true;
/*      */     }
/* 4188 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4194 */     PageContext pageContext = _jspx_page_context;
/* 4195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4197 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4198 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4199 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4201 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 4202 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4203 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4204 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4205 */       return true;
/*      */     }
/* 4207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4213 */     PageContext pageContext = _jspx_page_context;
/* 4214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4216 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4217 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4218 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4220 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 4222 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 4223 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4224 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4225 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4226 */       return true;
/*      */     }
/* 4228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4229 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4234 */     PageContext pageContext = _jspx_page_context;
/* 4235 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4237 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4238 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4239 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4241 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 4242 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4243 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 4245 */         out.write("\n       \t</a>\n       \t");
/* 4246 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4247 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4251 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4252 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4253 */       return true;
/*      */     }
/* 4255 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4261 */     PageContext pageContext = _jspx_page_context;
/* 4262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4264 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4265 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4266 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4268 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4269 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4270 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4272 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 4273 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4274 */           return true;
/* 4275 */         out.write("&wiz=true\">\n ");
/* 4276 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4277 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4281 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4282 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4283 */       return true;
/*      */     }
/* 4285 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4291 */     PageContext pageContext = _jspx_page_context;
/* 4292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4294 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4295 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4296 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4298 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 4299 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4300 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4301 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4302 */       return true;
/*      */     }
/* 4304 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4310 */     PageContext pageContext = _jspx_page_context;
/* 4311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4313 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 4314 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4315 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4317 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 4319 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 4320 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4321 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4323 */       return true;
/*      */     }
/* 4325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4331 */     PageContext pageContext = _jspx_page_context;
/* 4332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4334 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4335 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4336 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4338 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 4339 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4340 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 4342 */         out.write("\t    \n    </a>\n ");
/* 4343 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4344 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4348 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4349 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4350 */       return true;
/*      */     }
/* 4352 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4358 */     PageContext pageContext = _jspx_page_context;
/* 4359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4361 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4362 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 4363 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4365 */     _jspx_th_am_005fhiddenparam_005f0.setName("redirectto");
/* 4366 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 4367 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 4368 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4369 */       return true;
/*      */     }
/* 4371 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 4372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4377 */     PageContext pageContext = _jspx_page_context;
/* 4378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4380 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4381 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 4382 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4384 */     _jspx_th_am_005fhiddenparam_005f1.setName("returnpath");
/* 4385 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 4386 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 4387 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4388 */       return true;
/*      */     }
/* 4390 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 4391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4396 */     PageContext pageContext = _jspx_page_context;
/* 4397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4399 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4400 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 4401 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4403 */     _jspx_th_am_005fhiddenparam_005f2.setName("popup");
/* 4404 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 4405 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 4406 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4407 */       return true;
/*      */     }
/* 4409 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 4410 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4415 */     PageContext pageContext = _jspx_page_context;
/* 4416 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4418 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4419 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 4420 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4422 */     _jspx_th_am_005fhiddenparam_005f3.setName("resourceid");
/* 4423 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 4424 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 4425 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4426 */       return true;
/*      */     }
/* 4428 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 4429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4434 */     PageContext pageContext = _jspx_page_context;
/* 4435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4437 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4438 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 4439 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4441 */     _jspx_th_am_005fhiddenparam_005f4.setName("attributeid");
/* 4442 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 4443 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 4444 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4445 */       return true;
/*      */     }
/* 4447 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 4448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4453 */     PageContext pageContext = _jspx_page_context;
/* 4454 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4456 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4457 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 4458 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4460 */     _jspx_th_am_005fhiddenparam_005f5.setName("severity");
/* 4461 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 4462 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 4463 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4464 */       return true;
/*      */     }
/* 4466 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 4467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4472 */     PageContext pageContext = _jspx_page_context;
/* 4473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4475 */     HiddenParam _jspx_th_am_005fhiddenparam_005f6 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4476 */     _jspx_th_am_005fhiddenparam_005f6.setPageContext(_jspx_page_context);
/* 4477 */     _jspx_th_am_005fhiddenparam_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4479 */     _jspx_th_am_005fhiddenparam_005f6.setName("wiz");
/* 4480 */     int _jspx_eval_am_005fhiddenparam_005f6 = _jspx_th_am_005fhiddenparam_005f6.doStartTag();
/* 4481 */     if (_jspx_th_am_005fhiddenparam_005f6.doEndTag() == 5) {
/* 4482 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4483 */       return true;
/*      */     }
/* 4485 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 4486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4491 */     PageContext pageContext = _jspx_page_context;
/* 4492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4494 */     HiddenParam _jspx_th_am_005fhiddenparam_005f7 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 4495 */     _jspx_th_am_005fhiddenparam_005f7.setPageContext(_jspx_page_context);
/* 4496 */     _jspx_th_am_005fhiddenparam_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4498 */     _jspx_th_am_005fhiddenparam_005f7.setName("global");
/* 4499 */     int _jspx_eval_am_005fhiddenparam_005f7 = _jspx_th_am_005fhiddenparam_005f7.doStartTag();
/* 4500 */     if (_jspx_th_am_005fhiddenparam_005f7.doEndTag() == 5) {
/* 4501 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4502 */       return true;
/*      */     }
/* 4504 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 4505 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4510 */     PageContext pageContext = _jspx_page_context;
/* 4511 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4513 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 4514 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4515 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4517 */     _jspx_th_html_005ftext_005f0.setProperty("actionname");
/*      */     
/* 4519 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 4521 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 4523 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 4524 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4525 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4526 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4527 */       return true;
/*      */     }
/* 4529 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4535 */     PageContext pageContext = _jspx_page_context;
/* 4536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4538 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 4539 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4540 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4542 */     _jspx_th_html_005fselect_005f0.setProperty("sendmail");
/*      */     
/* 4544 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext default");
/* 4545 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4546 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4547 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4548 */         out = _jspx_page_context.pushBody();
/* 4549 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4550 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4553 */         out.write("\n\t\t\t");
/* 4554 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 4555 */           return true;
/* 4556 */         out.write(10);
/* 4557 */         out.write(9);
/* 4558 */         out.write(9);
/* 4559 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4560 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4563 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4564 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4567 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4568 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 4569 */       return true;
/*      */     }
/* 4571 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 4572 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4577 */     PageContext pageContext = _jspx_page_context;
/* 4578 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4580 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4581 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 4582 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 4584 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("maillist");
/* 4585 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 4586 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 4587 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4588 */       return true;
/*      */     }
/* 4590 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 4591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4596 */     PageContext pageContext = _jspx_page_context;
/* 4597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4599 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 4600 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4601 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4603 */     _jspx_th_html_005ftext_005f1.setProperty("priority");
/*      */     
/* 4605 */     _jspx_th_html_005ftext_005f1.setSize("30");
/*      */     
/* 4607 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/* 4609 */     _jspx_th_html_005ftext_005f1.setMaxlength("50");
/* 4610 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4611 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4612 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4613 */       return true;
/*      */     }
/* 4615 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4616 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mopaction_005finitialscreen_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */