/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class allresources_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1379 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1424 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1472 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1983 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/* 2182 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2183 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/includes/monitors_setasdefault.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2212 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2216 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2237 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2241 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2244 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2245 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2255 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2257 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.release();
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2267 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2270 */     JspWriter out = null;
/* 2271 */     Object page = this;
/* 2272 */     JspWriter _jspx_out = null;
/* 2273 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2277 */       response.setContentType("text/html;charset=UTF-8");
/* 2278 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2280 */       _jspx_page_context = pageContext;
/* 2281 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2282 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2283 */       session = pageContext.getSession();
/* 2284 */       out = pageContext.getOut();
/* 2285 */       _jspx_out = out;
/*      */       
/* 2287 */       out.write("<!DOCTYPE html>\n");
/* 2288 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2290 */       request.setAttribute("HelpKey", "Monitors Page");
/*      */       
/* 2292 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2293 */       ManagedApplication mo = null;
/* 2294 */       synchronized (application) {
/* 2295 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2296 */         if (mo == null) {
/* 2297 */           mo = new ManagedApplication();
/* 2298 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/* 2301 */       out.write(10);
/* 2302 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2304 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2306 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2314 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2315 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2316 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2317 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2320 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2321 */         String available = null;
/* 2322 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2323 */         out.write(10);
/*      */         
/* 2325 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2327 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2335 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2336 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2337 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2338 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2341 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2342 */           String unavailable = null;
/* 2343 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2344 */           out.write(10);
/*      */           
/* 2346 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2348 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2356 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2357 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2358 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2359 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2362 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2363 */             String unmanaged = null;
/* 2364 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2365 */             out.write(10);
/*      */             
/* 2367 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2369 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2377 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2378 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2379 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2380 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2383 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2384 */               String scheduled = null;
/* 2385 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2386 */               out.write(10);
/*      */               
/* 2388 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2390 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2399 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2400 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2401 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2404 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2405 */                 String critical = null;
/* 2406 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2407 */                 out.write(10);
/*      */                 
/* 2409 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2411 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2419 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2420 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2421 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2422 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2425 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2426 */                   String clear = null;
/* 2427 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2428 */                   out.write(10);
/*      */                   
/* 2430 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2431 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2432 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2440 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2441 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2442 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2443 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2446 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2447 */                     String warning = null;
/* 2448 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2449 */                     out.write(10);
/* 2450 */                     out.write(10);
/*      */                     
/* 2452 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2453 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2455 */                     out.write(10);
/* 2456 */                     out.write(10);
/* 2457 */                     out.write(10);
/* 2458 */                     out.write(10);
/*      */                     
/* 2460 */                     String headerandTab = "/jsp/header.jsp?tabtoselect=1";
/* 2461 */                     String title = FormatUtil.getString("am.webclient.monitortab.title");
/* 2462 */                     String network = request.getParameter("selectedNetwork");
/* 2463 */                     String leftPage = "/jsp/MapsLeftPage.jsp?method=showResourceTypesAll&group=All&selectedNetwork=" + network;
/* 2464 */                     String toAppendLink = "";
/* 2465 */                     String monTypeAtt = "All";
/* 2466 */                     String showManage = request.getParameter("showmanage");
/* 2467 */                     if (request.getParameter("viewmontype") != null)
/*      */                     {
/* 2469 */                       monTypeAtt = request.getParameter("viewmontype");
/*      */                     }
/* 2471 */                     if (network != null)
/*      */                     {
/* 2473 */                       title = network + title;
/* 2474 */                       leftPage = leftPage + "&selectedNetwork=" + network;
/* 2475 */                       toAppendLink = "&selectedNetwork=" + network;
/*      */                     }
/*      */                     else
/*      */                     {
/* 2479 */                       title = title;
/*      */                     }
/* 2481 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                     {
/* 2483 */                       headerandTab = "/jsp/header.jsp?oldtab=6";
/* 2484 */                       leftPage = "/it360/jsp/admin/it360AdminLeftLinks.jsp";
/*      */                     }
/* 2486 */                     request.setAttribute("defaultview", "showResourceTypesAll");
/*      */                     
/* 2488 */                     ArrayList globalslist = mo.getRows("select VALUE from AM_GLOBALCONFIG where NAME='allowOperatorEdit' or NAME='allowOperatorManage' or NAME='allowOperatorUpdateIP'");
/* 2489 */                     String allowEdit = (String)((ArrayList)globalslist.get(0)).get(0);
/* 2490 */                     String allowManage = (String)((ArrayList)globalslist.get(1)).get(0);
/* 2491 */                     String allowUpdateIP = (String)((ArrayList)globalslist.get(2)).get(0);
/*      */                     
/* 2493 */                     pageContext.setAttribute("allowEdit", allowEdit);
/* 2494 */                     pageContext.setAttribute("allowUpdateIP", allowUpdateIP);
/* 2495 */                     pageContext.setAttribute("allowManage", allowManage);
/*      */                     
/* 2497 */                     out.write(10);
/* 2498 */                     if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                       return;
/* 2500 */                     out.write(10);
/*      */                     
/* 2502 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2503 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2504 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2506 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2507 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2508 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2510 */                         out.write(10);
/*      */                         
/* 2512 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2513 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2514 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2516 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2518 */                         _jspx_th_tiles_005fput_005f0.setValue(title);
/* 2519 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2520 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2521 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2524 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2525 */                         out.write(10);
/*      */                         
/* 2527 */                         PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2528 */                         _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2529 */                         _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2531 */                         _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */                         
/* 2533 */                         _jspx_th_tiles_005fput_005f1.setValue(headerandTab);
/* 2534 */                         int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2535 */                         if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2536 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */                         }
/*      */                         
/* 2539 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2540 */                         out.write(10);
/*      */                         
/* 2542 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2543 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2544 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2546 */                         _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                         
/* 2548 */                         _jspx_th_tiles_005fput_005f2.setValue(leftPage);
/* 2549 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2550 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2551 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 2554 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2555 */                         out.write(10);
/*      */                         
/* 2557 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2558 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2559 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2561 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2563 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2564 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2565 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2566 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2567 */                             out = _jspx_page_context.pushBody();
/* 2568 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2569 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2572 */                             out.write(10);
/*      */                             
/* 2574 */                             String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/* 2575 */                             String unmantype = "";
/* 2576 */                             if (!monTypeAtt.equals("All"))
/*      */                             {
/* 2578 */                               for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                               {
/* 2580 */                                 if (monTypeAtt.equals(com.adventnet.appmanager.util.Constants.categoryLink[i]))
/*      */                                 {
/* 2582 */                                   types = types + " and AM_ManagedResourceType.RESOURCEGROUP IN (" + "'" + monTypeAtt + "')";
/* 2583 */                                   unmantype = " and AM_ManagedResourceType.RESOURCEGROUP IN ('" + monTypeAtt + "')";
/* 2584 */                                   break;
/*      */                                 }
/*      */                               }
/* 2587 */                               if (!unmantype.contains("RESOURCEGROUP"))
/*      */                               {
/* 2589 */                                 types = types + " and AM_ManagedResourceType.SUBGROUP IN (" + "'" + monTypeAtt + "')";
/* 2590 */                                 unmantype = " and AM_ManagedResourceType.SUBGROUP IN ('" + monTypeAtt + "')";
/*      */                               }
/*      */                             }
/*      */                             
/* 2594 */                             StringBuffer resourceGroup = new StringBuffer();
/* 2595 */                             for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                             {
/* 2597 */                               if ((!com.adventnet.appmanager.util.Constants.categoryLink[i].equals("NWD")) || 
/* 2598 */                                 (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                               {
/*      */ 
/* 2601 */                                 resourceGroup.append("'");
/* 2602 */                                 resourceGroup.append(com.adventnet.appmanager.util.Constants.categoryLink[i]);
/* 2603 */                                 resourceGroup.append("'");
/* 2604 */                                 resourceGroup.append(",");
/*      */                               } }
/* 2606 */                             if (resourceGroup.length() > 0)
/*      */                             {
/* 2608 */                               resourceGroup = resourceGroup.deleteCharAt(resourceGroup.length() - 1);
/*      */                             }
/*      */                             
/* 2611 */                             List devicesList = null;
/* 2612 */                             List devicesList1 = null;
/*      */                             
/* 2614 */                             String query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP in(" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + " ";
/* 2615 */                             String query1 = "";
/* 2616 */                             if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                             {
/* 2618 */                               query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCEGROUP in (" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + "";
/*      */                             }
/* 2620 */                             else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                             {
/* 2622 */                               query1 = query;
/* 2623 */                               query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCEGROUP in (" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + "";
/*      */                             }
/* 2625 */                             if (network != null) {
/* 2626 */                               query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, InetService, IpAddress where AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP  in (" + resourceGroup + ") and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + network + "' ";
/* 2627 */                               String devicesQuery = "SELECT resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH  FROM AM_ManagedObject,IpAddress,AM_ManagedResourceType where IpAddress.PARENTNET='" + network + "' and IpAddress.PARENTNODE=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE in " + types;
/* 2628 */                               String devicesQuery1 = "";
/* 2629 */                               if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                               {
/* 2631 */                                 query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCEGROUP in(" + resourceGroup + ") and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + network + "' ";
/* 2632 */                                 devicesQuery = "SELECT resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH  FROM IpAddress,AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and IpAddress.PARENTNET='" + network + "' and IpAddress.PARENTNODE=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_UnManagedNodes.resid is null and AM_ManagedObject.TYPE in " + types;
/*      */                               }
/* 2634 */                               else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                               {
/* 2636 */                                 query1 = query;
/* 2637 */                                 devicesQuery1 = devicesQuery;
/* 2638 */                                 query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, InetService, IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCEGROUP  in (" + resourceGroup + ")  and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + network + "' ";
/* 2639 */                                 devicesQuery = "SELECT resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH  FROM AM_ManagedResourceType,IpAddress, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and IpAddress.PARENTNET='" + network + "' and IpAddress.PARENTNODE=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE in " + types;
/* 2640 */                                 devicesList1 = mo.getRows(devicesQuery1);
/*      */                               }
/* 2642 */                               devicesList = mo.getRows(devicesQuery);
/*      */                             }
/* 2644 */                             String resIds = "";
/* 2645 */                             List list = new ArrayList();
/* 2646 */                             List list1 = new ArrayList();
/* 2647 */                             if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*      */                             {
/* 2649 */                               query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") and AM_ManagedObject.TYPE in " + types;
/*      */                             }
/*      */                             
/* 2652 */                             out.write(10);
/*      */                             
/* 2654 */                             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2655 */                             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2656 */                             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 2657 */                             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2658 */                             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                               for (;;) {
/* 2660 */                                 out.write(10);
/*      */                                 
/* 2662 */                                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2663 */                                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2664 */                                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                 
/* 2666 */                                 _jspx_th_c_005fwhen_005f0.setTest("${ !empty OPERATOR }");
/* 2667 */                                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2668 */                                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                   for (;;) {
/* 2670 */                                     out.write(10);
/*      */                                     
/*      */ 
/* 2673 */                                     Vector resIds_vector = com.adventnet.appmanager.struts.beans.ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 2674 */                                     if (resIds_vector.size() != 0)
/*      */                                     {
/* 2676 */                                       for (int i = 0; i < resIds_vector.size(); i++)
/*      */                                       {
/* 2678 */                                         resIds = resIds + (String)resIds_vector.get(i) + ",";
/*      */                                       }
/* 2680 */                                       resIds = resIds.substring(0, resIds.length() - 1);
/* 2681 */                                       query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP in(" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.RESOURCEID in (" + resIds + ") ";
/* 2682 */                                       if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("true")))
/*      */                                       {
/* 2684 */                                         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_ManagedObject left join AM_UnManagedNodes on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_UnManagedNodes.resid is null and AM_ManagedResourceType.RESOURCEGROUP  in (" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.RESOURCEID in (" + resIds + ")";
/*      */                                       }
/* 2686 */                                       else if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                       {
/* 2688 */                                         query1 = query;
/* 2689 */                                         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE NOT IN ('HAI','Network') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedResourceType.RESOURCEGROUP  in (" + resourceGroup + ") and AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.RESOURCEID in (" + resIds + ")";
/* 2690 */                                         list1 = mo.getRows(query1);
/*      */                                       }
/* 2692 */                                       if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*      */                                       {
/* 2694 */                                         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") and AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.RESOURCEID in (" + resIds + ") ";
/*      */                                       }
/* 2696 */                                       list = mo.getRows(query);
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 2700 */                                       resIds = "-1";
/*      */                                     }
/*      */                                     
/* 2703 */                                     out.write(10);
/* 2704 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2705 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2709 */                                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2710 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                 }
/*      */                                 
/* 2713 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2714 */                                 out.write(10);
/*      */                                 
/* 2716 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2717 */                                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2718 */                                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2719 */                                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2720 */                                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                   for (;;) {
/* 2722 */                                     out.write(10);
/*      */                                     
/* 2724 */                                     if ((request.getParameter("showmanage") != null) && (request.getParameter("showmanage").equals("false")))
/*      */                                     {
/* 2726 */                                       list1 = mo.getRows(query1);
/*      */                                     }
/* 2728 */                                     list = mo.getRows(query);
/*      */                                     
/* 2730 */                                     out.write(10);
/* 2731 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2732 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2736 */                                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2737 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                 }
/*      */                                 
/* 2740 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2741 */                                 out.write(10);
/* 2742 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2743 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2747 */                             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2748 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                             }
/*      */                             
/* 2751 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2752 */                             out.write(10);
/*      */                             
/* 2754 */                             if (devicesList != null)
/*      */                             {
/* 2756 */                               for (int a = 0; a < devicesList.size(); a++)
/*      */                               {
/* 2758 */                                 list.add(devicesList.get(a));
/*      */                               }
/*      */                             }
/* 2761 */                             if (devicesList1 != null)
/*      */                             {
/* 2763 */                               for (int a = 0; a < devicesList1.size(); a++)
/*      */                               {
/* 2765 */                                 list1.add(devicesList1.get(a));
/*      */                               }
/*      */                             }
/* 2768 */                             request.setAttribute("resourcetable", list);
/* 2769 */                             request.setAttribute("resourcetableunmanagecount", list1);
/* 2770 */                             List unmanagednodes = new ArrayList();
/*      */                             
/* 2772 */                             out.write(10);
/*      */                             
/* 2774 */                             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2775 */                             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2776 */                             _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/* 2777 */                             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2778 */                             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                               for (;;) {
/* 2780 */                                 out.write(10);
/*      */                                 
/* 2782 */                                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2783 */                                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2784 */                                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                 
/* 2786 */                                 _jspx_th_c_005fwhen_005f1.setTest("${ !empty OPERATOR }");
/* 2787 */                                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2788 */                                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                   for (;;) {
/* 2790 */                                     out.write(10);
/*      */                                     
/* 2792 */                                     unmanagednodes = mo.getRows("select * from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE!='HAI' and  resid in (" + resIds + ") and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + unmantype + "");
/*      */                                     
/* 2794 */                                     out.write(10);
/* 2795 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2796 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2800 */                                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2801 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                 }
/*      */                                 
/* 2804 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2805 */                                 out.write(10);
/*      */                                 
/* 2807 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2808 */                                 _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2809 */                                 _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2810 */                                 int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2811 */                                 if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                   for (;;) {
/* 2813 */                                     out.write(10);
/*      */                                     
/* 2815 */                                     unmanagednodes = mo.getRows("select * from AM_ManagedResourceType, AM_UnManagedNodes left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_UnManagedNodes.resid where AM_ManagedObject.TYPE!='HAI' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + unmantype + "");
/*      */                                     
/* 2817 */                                     out.write(10);
/* 2818 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2819 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2823 */                                 if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2824 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                 }
/*      */                                 
/* 2827 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2828 */                                 out.write(10);
/* 2829 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2830 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2834 */                             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2835 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                             }
/*      */                             
/* 2838 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2839 */                             out.write(10);
/*      */                             
/* 2841 */                             int unmancount = 0;
/* 2842 */                             if ((unmanagednodes != null) && (unmanagednodes.size() > 0))
/*      */                             {
/* 2844 */                               unmancount = unmanagednodes.size();
/*      */                             }
/* 2846 */                             boolean resourceAvailable = list.size() > 0;
/* 2847 */                             if (resourceAvailable) {
/* 2848 */                               title = title + " <span class=bodytext>(" + FormatUtil.getString("am.monitortab.total.text") + " <b>" + list.size() + "</b> " + FormatUtil.getString("am.webclient.monitorstab.text") + ")</span>";
/*      */                             }
/*      */                             
/* 2851 */                             out.write(10);
/* 2852 */                             out.write(10);
/*      */                             
/* 2854 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2855 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2856 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2858 */                             _jspx_th_c_005fif_005f0.setTest("${param.search!='true'}");
/* 2859 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2860 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2862 */                                 out.write(10);
/* 2863 */                                 out.write("<!--$Id$-->\n\n<script>\nvar urlredirect = new Array();\nurlredirect[0] = '/showresource.do?method=showResourceTypesAll&group=All");
/* 2864 */                                 out.print(toAppendLink);
/* 2865 */                                 out.write("';\n</script>\n");
/*      */                                 
/* 2867 */                                 if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2868 */                                   out.write("\n  <script>\n     urlredirect[0]='showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server");
/* 2869 */                                   out.print(toAppendLink);
/* 2870 */                                   out.write("';\n </script>\n ");
/*      */                                 }
/*      */                                 
/* 2873 */                                 out.write("\n <script>\nurlredirect[1] = '/showresource.do?method=showResourceTypes");
/* 2874 */                                 out.print(toAppendLink);
/* 2875 */                                 out.write("&monitor_viewtype=categoryview';\nurlredirect[2] = '/showresource.do?method=showPlasmaView';\nurlredirect[3] = '/showresource.do?method=showMonitorGroupView';\nurlredirect[4] = '/showresource.do?method=showGMapView&group=All");
/* 2876 */                                 out.print(toAppendLink);
/* 2877 */                                 out.write("';\nurlredirect[5] = '/showresource.do?method=showIconsView");
/* 2878 */                                 out.print(toAppendLink);
/* 2879 */                                 out.write("';\nurlredirect[6] = '/showresource.do?method=showDetailsView");
/* 2880 */                                 out.print(toAppendLink);
/* 2881 */                                 out.write("';\n\n</script>\n\n\n\n\n");
/*      */                                 
/* 2883 */                                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2884 */                                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2885 */                                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2887 */                                 _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                                 
/* 2889 */                                 _jspx_th_html_005fform_005f0.setStyle("display :inline");
/* 2890 */                                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2891 */                                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                   for (;;) {
/* 2893 */                                     out.write(10);
/* 2894 */                                     if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 2896 */                                     out.write(10);
/* 2897 */                                     if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 2899 */                                     out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"62%\" height=\"35\" class=\"monitorsheading\">\n      <table width=\"100%\">\n      <tr>\n        <td  height=\"35\"   width=\"70%\" class=\"monitorsheading\"> ");
/* 2900 */                                     out.print(title);
/* 2901 */                                     out.write(" </td>\n        <td  height=\"35\" class=\"monitorsheading\"  style=\"padding-left:2px\">\n    ");
/* 2902 */                                     String CategoryViewtype = (String)request.getAttribute("categorytype");
/* 2903 */                                     if (CategoryViewtype == null) {
/* 2904 */                                       CategoryViewtype = "";
/*      */                                     }
/* 2906 */                                     String monitorviewtype = (String)request.getAttribute("monitor_viewtype");
/* 2907 */                                     if (monitorviewtype == null) {
/* 2908 */                                       monitorviewtype = "";
/*      */                                     }
/* 2910 */                                     if (monitorviewtype.startsWith("CategoryView")) {
/* 2911 */                                       if (CategoryViewtype.equals("added monitors"))
/*      */                                       {
/* 2913 */                                         out.write("          <a  href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\"  class=\"staticlinks\" onclick=\"javascript:setCookieval('showAddedMonitors');\">");
/* 2914 */                                         out.print(FormatUtil.getString("am.monitortab.category.AddedMonitors.text"));
/* 2915 */                                         out.write("</a>\n  ");
/*      */ 
/*      */                                       }
/* 2918 */                                       else if (CategoryViewtype.equals("all monitors"))
/*      */                                       {
/* 2920 */                                         out.write("            <a href=\"/showresource.do?method=showResourceTypes\"   class=\"staticlinks\" onclick=\"javascript:setCookieval('showAllMonitors');\">");
/* 2921 */                                         out.print(FormatUtil.getString("am.monitortab.category.AllMonitors.text"));
/* 2922 */                                         out.write("</a>\n\n  ");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 2927 */                                     out.write("\n        </td>\n      </tr>\n      </table>\n    </td>\n    ");
/*      */                                     
/* 2929 */                                     String tempStl = "center";
/* 2930 */                                     if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                     {
/* 2932 */                                       tempStl = "right";
/*      */                                       
/* 2934 */                                       out.write("\n      <td align=\"right\" width=\"30%\" class=\"bodytext\" style=\"white-space:nowrap;\">\n      ");
/* 2935 */                                       if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin()) {
/* 2936 */                                         out.write("\n      ");
/*      */                                         
/* 2938 */                                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2939 */                                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2940 */                                         _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 2942 */                                         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2943 */                                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2944 */                                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                           for (;;) {
/* 2946 */                                             out.write("\n        <span id=\"createNewMG\">");
/* 2947 */                                             out.print(FormatUtil.getString("am.monitortab.creategroup.text"));
/* 2948 */                                             out.write(" :  &nbsp;</span>\n        <select id=\"createMG\" onchange=\"javascript:changeMGURL(this)\" styleClass=\"formtext\" style=\"margin-right: 30px;display:none;\">\n          <option value=\"createNewMG\" selected>");
/* 2949 */                                             out.print(FormatUtil.getString("am.monitortab.selectgrouptype.text"));
/* 2950 */                                             out.write("</option>\n          <option value=\"1\">");
/* 2951 */                                             out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 2952 */                                             out.write("</option>\n          <option value=\"2\">");
/* 2953 */                                             out.print(FormatUtil.getString("am.webclient.mg.type.webappgroup"));
/* 2954 */                                             out.write("</option>\n          <option value=\"3\">");
/* 2955 */                                             out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/* 2956 */                                             out.write("</option>\n        </select>\n      ");
/* 2957 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2958 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2962 */                                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2963 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                         }
/*      */                                         
/* 2966 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2967 */                                         out.write("\n     ");
/*      */                                       }
/* 2969 */                                       out.write("\n\n      ");
/* 2970 */                                       out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 2971 */                                       out.write(" :  &nbsp;\n\n      ");
/*      */                                       
/* 2973 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2974 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2975 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 2977 */                                       _jspx_th_html_005fselect_005f0.setProperty("defaultmonitorsview");
/*      */                                       
/* 2979 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:changeUrl(this)");
/*      */                                       
/* 2981 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 2982 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2983 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2984 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2985 */                                           out = _jspx_page_context.pushBody();
/* 2986 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2987 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2990 */                                           out.write("\n        ");
/*      */                                           
/* 2992 */                                           OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2993 */                                           _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2994 */                                           _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                           
/* 2996 */                                           _jspx_th_html_005foption_005f0.setValue("showResourceTypesAll");
/* 2997 */                                           int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2998 */                                           if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2999 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3000 */                                               out = _jspx_page_context.pushBody();
/* 3001 */                                               _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3002 */                                               _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3005 */                                               out.print(FormatUtil.getString("am.monitortab.bulkconfiguration.text"));
/* 3006 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3007 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3010 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3011 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3014 */                                           if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3015 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                           }
/*      */                                           
/* 3018 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3019 */                                           out.write("\n        ");
/*      */                                           
/* 3021 */                                           OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3022 */                                           _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3023 */                                           _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                           
/* 3025 */                                           _jspx_th_html_005foption_005f1.setValue("showResourceTypes");
/* 3026 */                                           int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3027 */                                           if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3028 */                                             if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3029 */                                               out = _jspx_page_context.pushBody();
/* 3030 */                                               _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3031 */                                               _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3034 */                                               out.print(FormatUtil.getString("am.monitortab.category.text"));
/* 3035 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3036 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3039 */                                             if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3040 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3043 */                                           if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3044 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                           }
/*      */                                           
/* 3047 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3048 */                                           out.write("\n        ");
/*      */                                           
/* 3050 */                                           OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3051 */                                           _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3052 */                                           _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                           
/* 3054 */                                           _jspx_th_html_005foption_005f2.setValue("plasmaView");
/* 3055 */                                           int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3056 */                                           if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3057 */                                             if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3058 */                                               out = _jspx_page_context.pushBody();
/* 3059 */                                               _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3060 */                                               _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3063 */                                               out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 3064 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3065 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3068 */                                             if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3069 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3072 */                                           if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3073 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                           }
/*      */                                           
/* 3076 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3077 */                                           out.write("\n        ");
/*      */                                           
/* 3079 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3080 */                                           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3081 */                                           _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                           
/* 3083 */                                           _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 3084 */                                           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3085 */                                           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                             for (;;) {
/* 3087 */                                               out.write("\n        ");
/*      */                                               
/* 3089 */                                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3090 */                                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3091 */                                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                               
/* 3093 */                                               _jspx_th_html_005foption_005f3.setValue("showMonitorGroupView");
/* 3094 */                                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3095 */                                               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3096 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3097 */                                                   out = _jspx_page_context.pushBody();
/* 3098 */                                                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3099 */                                                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3102 */                                                   out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 3103 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3104 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3107 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3108 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3111 */                                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3112 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                               }
/*      */                                               
/* 3115 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3116 */                                               out.write("\n        ");
/*      */                                               
/* 3118 */                                               OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3119 */                                               _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3120 */                                               _jspx_th_html_005foption_005f4.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                               
/* 3122 */                                               _jspx_th_html_005foption_005f4.setValue("showGMapView");
/* 3123 */                                               int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3124 */                                               if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3125 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3126 */                                                   out = _jspx_page_context.pushBody();
/* 3127 */                                                   _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3128 */                                                   _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3131 */                                                   out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 3132 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3133 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3136 */                                                 if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3137 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3140 */                                               if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3141 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                               }
/*      */                                               
/* 3144 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3145 */                                               out.write("\n        ");
/* 3146 */                                               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3147 */                                                 out.write("\n        ");
/*      */                                                 
/* 3149 */                                                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3150 */                                                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3151 */                                                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                                 
/* 3153 */                                                 _jspx_th_html_005foption_005f5.setValue("showIconsView");
/* 3154 */                                                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3155 */                                                 if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3156 */                                                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3157 */                                                     out = _jspx_page_context.pushBody();
/* 3158 */                                                     _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3159 */                                                     _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3162 */                                                     out.print(FormatUtil.getString("am.monitortab.icons.text"));
/* 3163 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3164 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3167 */                                                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3168 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3171 */                                                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3172 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 3175 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3176 */                                                 out.write("\n        ");
/*      */                                                 
/* 3178 */                                                 OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3179 */                                                 _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3180 */                                                 _jspx_th_html_005foption_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                                 
/* 3182 */                                                 _jspx_th_html_005foption_005f6.setValue("showDetailsView");
/* 3183 */                                                 int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3184 */                                                 if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3185 */                                                   if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3186 */                                                     out = _jspx_page_context.pushBody();
/* 3187 */                                                     _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3188 */                                                     _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3191 */                                                     out.print(FormatUtil.getString("am.monitortab.table.text"));
/* 3192 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3193 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3196 */                                                   if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3197 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3200 */                                                 if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3201 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                                 }
/*      */                                                 
/* 3204 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3205 */                                                 out.write("\n        ");
/*      */                                               }
/* 3207 */                                               out.write("\n        ");
/*      */                                               
/* 3209 */                                               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3210 */                                               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3211 */                                               _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                               
/* 3213 */                                               _jspx_th_html_005foption_005f7.setValue("showFlashView");
/* 3214 */                                               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3215 */                                               if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3216 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3217 */                                                   out = _jspx_page_context.pushBody();
/* 3218 */                                                   _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3219 */                                                   _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3222 */                                                   out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 3223 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3224 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3227 */                                                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3228 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3231 */                                               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3232 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                               }
/*      */                                               
/* 3235 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3236 */                                               out.write("\n        ");
/* 3237 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3238 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3242 */                                           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3243 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                           }
/*      */                                           
/* 3246 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3247 */                                           out.write("\n      ");
/* 3248 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3249 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3252 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3253 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3256 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3257 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 3260 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3261 */                                       out.write("\n\n      ");
/* 3262 */                                       if (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()) {
/* 3263 */                                         out.write("\n      <span class=\"bodytext\">\n        ");
/*      */                                         
/* 3265 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3266 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3267 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                         
/* 3269 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 3270 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3271 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 3273 */                                             out.write("\n          ");
/*      */                                             
/* 3275 */                                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3276 */                                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3277 */                                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                             
/* 3279 */                                             _jspx_th_c_005fif_005f1.setTest("${globalconfig['defaultmonitorsview'] != requestScope.defaultview}");
/* 3280 */                                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3281 */                                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                               for (;;) {
/* 3283 */                                                 out.write("\n            <input type=hidden name=\"method\" value=\"setDefaultMonitorsView\">\n        <a href=\"javascript:setMonitorsViewDefault()\" class=\"new-monitordiv-link\">");
/* 3284 */                                                 out.print(FormatUtil.getString("am.monitortab.setasdefaultview.text"));
/* 3285 */                                                 out.write(" </a>\n          ");
/* 3286 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3287 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3291 */                                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3292 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                             }
/*      */                                             
/* 3295 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3296 */                                             out.write("\n        ");
/* 3297 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3298 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3302 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3303 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3306 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3307 */                                         out.write("\n      </span>\n      ");
/*      */                                       }
/* 3309 */                                       out.write("\n      </td>\n      ");
/*      */                                     }
/*      */                                     
/* 3312 */                                     out.write("\n      ");
/*      */                                     
/* 3314 */                                     String location = (String)pageContext.getAttribute("setdefaultlocation");
/* 3315 */                                     if ((location != null) && (location.equals("Googleview")) && (request.getAttribute("key_set") != null) && (request.getAttribute("key_set").equals("true")))
/*      */                                     {
/* 3317 */                                       out.write("\n      <td colspan=\"2\" align=\"");
/* 3318 */                                       out.print(tempStl);
/* 3319 */                                       out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 3320 */                                       out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" class=\"staticlinks\">");
/* 3321 */                                       out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 3322 */                                       out.write("</a>\n       </span>\n\t  </td> \n      ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3326 */                                     out.write("\n      ");
/*      */                                     
/* 3328 */                                     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3329 */                                     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3330 */                                     _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 3332 */                                     _jspx_th_c_005fif_005f2.setTest("${AMActionForm.showMapView == true}");
/* 3333 */                                     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3334 */                                     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                       for (;;) {
/* 3336 */                                         out.write("\n      <td colspan=\"2\" align=\"");
/* 3337 */                                         out.print(tempStl);
/* 3338 */                                         out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 3339 */                                         out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" id=\"savezoomlevel\" class=\"staticlinks\">");
/* 3340 */                                         out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 3341 */                                         out.write("</a>\n       </span>\n\t  </td> \n      ");
/* 3342 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3343 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3347 */                                     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3348 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                     }
/*      */                                     
/* 3351 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3352 */                                     out.write("\n  </tr>\n</table>\n");
/* 3353 */                                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3354 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3358 */                                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3359 */                                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                 }
/*      */                                 
/* 3362 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3363 */                                 out.write("\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\nvar defaultview = \"");
/* 3364 */                                 out.print(request.getAttribute("defaultview"));
/* 3365 */                                 out.write("\";\nif(defaultview == \"showMonitorGroupView\")//No I18N\n{\n  $('#createMG').show();//No I18N\n  $('#createNewMG').show();//No I18N\n}\nelse\n{\n  $('#createMG').hide();//No I18N\n  $('#createNewMG').hide();//No I18N\n}\n//Set cookie function\nfunction setCookie(name,value,expdays)\n{\n       var expdate=new Date();   //No I18N\n       expdate.setDate(expdate.getDate() + expdays);\n       var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString());  //No I18N\n       document.cookie=name + \"=\" + val;   //No I18N\n}\n\n// Get cookie function\nfunction getCookie(name)\n{\n       var i,x,y,arr=document.cookie.split(\";\");   //No I18N\n       y = null;\n       for (i=0;i<arr.length;i++)\n       {\n         x=arr[i].substr(0,arr[i].indexOf(\"=\"));   //No I18N\n         y=arr[i].substr(arr[i].indexOf(\"=\")+1);   //No I18N\n         x=x.replace(/^\\s+|\\s+$/g,\"\");   //No I18N\n         if (x==name)\n         {\n           return unescape(y);\n         }\n       }\n}\nfunction setCookieval(Category_type){\n  //alert(Category_type);\n  if(Category_type==\"showAddedMonitors\"){\n");
/* 3366 */                                 out.write("    setCookie('Category_type','showAddedMonitors');  //No I18N\n  }\n  else{\n    setCookie('Category_type','showAllMonitors');  //No I18N\n  }\n}\n\nfunction setMonitorsViewDefault() {\n");
/* 3367 */                                 if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 3369 */                                 out.write(10);
/* 3370 */                                 out.write(32);
/* 3371 */                                 out.write(32);
/* 3372 */                                 if (_jspx_meth_logic_005fnotPresent_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 3374 */                                 out.write("\n}\n\nfunction changeMGURL(a)\n{\n  var er = /^[0-9]+$/;\n  if(!er.test(a.value))\n  {\n    return;\n  }\n  location.href = '/admin/createapplication.do?method=createapp&grouptype='+a.value;\n}\n\nfunction changeUrl(a)\n{\n\t if(a.selectedIndex == 2 || a.selectedIndex == 7)\n\t {\n \t\tvar url = urlredirect[2];\n \t\tvar windowOpenOptions='scrollbars=1,resizable=1,width=900,height=650,left=50,screenX=50,screenY=25,top=25';\n \t\tvar name = \"PlasmaView\"; // NO I18N\n \t\t\n \t\tif(a.selectedIndex == 7)\n \t\t{\n \t\t\turl = '/GraphicalView.do?method=popUp&haid=0&isPopUp=true'; // NO I18N\n \t\t\twindowOpenOptions = 'scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25'; // NO I18N\n \t\t\tname = \"FlashView\"; // NO I18N\n \t\t}\n\t\twindow.open(url, name, windowOpenOptions);\n\n\t\tvar defaultview = \"");
/* 3375 */                                 out.print(request.getAttribute("defaultview"));
/* 3376 */                                 out.write("\";\n        \n        if(defaultview == \"showResourceTypesAll\")\n        {\n\t\t\ta.selectedIndex =0;\n        }\n        else if(defaultview == \"showResourceTypes\")\n        {\n            a.selectedIndex = 1;\n        }\n        else if(defaultview == \"showMonitorGroupView\")\n        {\n            a.selectedIndex = 3;\n        }\n        else if(defaultview == \"showGMapView\")\n        {\n\t\t\ta.selectedIndex = 4;\n        }\n        else if(defaultview == \"showIconsView\")\n        {\n            a.selectedIndex = 5;\n        }\n        else if(defaultview == \"showDetailsView\")\n        {\n            a.selectedIndex = 6;\n        }\n        return;\n\t}\n\tlocation.href=urlredirect[a.selectedIndex]; //NO I18N\n}\n</script>\n");
/* 3377 */                                 out.write(10);
/* 3378 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3379 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3383 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3384 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 3387 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3388 */                             out.write(10);
/* 3389 */                             out.write(32);
/*      */                             
/* 3391 */                             if (request.getParameter("mas_host") != null)
/*      */                             {
/* 3393 */                               out.write("\n\t<table class=\"messagebox\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" width=\"98%\">\n\t  <tbody><tr>\n\t\t<td align=\"center\" width=\"4%\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" height=\"23\" width=\"23\">\n\t\t</td>\n\t\t<td class=\"message\" height=\"34\" width=\"94%\">\n\t\t");
/*      */                               
/* 3395 */                               if (!request.getParameter("serverId").equals("-1"))
/*      */                               {
/* 3397 */                                 if (request.getParameter("serverId").equals("0"))
/*      */                                 {
/*      */ 
/* 3400 */                                   out.write("\n       ");
/* 3401 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.accessproblem.details"));
/* 3402 */                                   out.write("\n          ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3407 */                                   out.write("\n\t\t\t");
/* 3408 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.passwordWrong.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port"), request.getParameter("serverId") }));
/* 3409 */                                   out.write("\n\t\t ");
/*      */                                 }
/*      */                               } else {
/* 3412 */                                 out.write(10);
/* 3413 */                                 out.write(9);
/* 3414 */                                 out.write(9);
/* 3415 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.down.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port") }));
/* 3416 */                                 out.write(10);
/* 3417 */                                 out.write(9);
/* 3418 */                                 out.write(9);
/*      */                               }
/* 3420 */                               out.write("\n\t\t</tr>\n\t\t</tbody></table>\n\t\t<table>\n\t\t<tr>\n\t  <td class=\"monitorsheading\"><img src=\"../images/spacer.gif\" height=\"5\" width=\"99%\"></td>\n\t </tr>\n\t</table>\n");
/*      */                             }
/* 3422 */                             out.write("\n\t  \n      \n   <!--  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        <tr>\n    \t      <td width=\"99%\" height=\"24\">-->\n              ");
/* 3423 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "displayresources.jsp", out, false);
/* 3424 */                             out.write("\n       <!--  </td>\n\t  </tr>\n\t  </table> -->\n      \n           \n          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n      \t  <tr >\n           <td height=\"26\" class=\"bodytextbold\" align=\"left\">\n          <form action=\"/deleteMO.do?\" name=\"deletemonitor1\" method=\"post\" style=\"display:inline\">\n          <input type=\"hidden\" name=\"method\" value=\"deleteMO\"></input>\n          ");
/*      */                             
/* 3426 */                             if (list.size() > 10)
/*      */                             {
/* 3428 */                               out.write("\n\t\t  ");
/*      */                               
/* 3430 */                               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3431 */                               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3432 */                               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 3434 */                               _jspx_th_logic_005fpresent_005f2.setRole("DEMO,ADMIN");
/* 3435 */                               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3436 */                               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                 for (;;) {
/* 3438 */                                   out.write("&nbsp;\n                 <select onchange=\"javascript:invokeOperations1()\" class=\"formtext\" name=\"operation\">\n                     <option VALUE=\"selectactions\" selected=true>--------");
/* 3439 */                                   out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 3440 */                                   out.write("--------</option>\n                     <option value=\"delete\">");
/* 3441 */                                   out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3442 */                                   out.write("</option>\n                     <option value=\"editDisplayNames\">");
/* 3443 */                                   out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 3444 */                                   out.write("</option>\n\t\t     \t\t <option value=\"updateIP\">");
/* 3445 */                                   out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 3446 */                                   out.write("</option><!--No I18N-->\n                     <option value=\"manage\">");
/* 3447 */                                   out.print(FormatUtil.getString("Manage"));
/* 3448 */                                   out.write("</option>\n                     <option value=\"unManage\">");
/* 3449 */                                   out.print(FormatUtil.getString("UnManage"));
/* 3450 */                                   out.write("</option>\n                     <option value=\"updateAuthentication\">");
/* 3451 */                                   out.print(FormatUtil.getString("am.webclient.updateauthentication.text"));
/* 3452 */                                   out.write("</option>\n                     <option value=\"updatePoll\">");
/* 3453 */                                   out.print(FormatUtil.getString("am.webclient.updatepolling.text"));
/* 3454 */                                   out.write("</option>\n                     <option value=\"copyPaste\">");
/* 3455 */                                   out.print(FormatUtil.getString("am.webclient.copypaste.headtitle"));
/* 3456 */                                   out.write("</option>\n                 </select>\n            ");
/* 3457 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3458 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3462 */                               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3463 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                               }
/*      */                               
/* 3466 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3467 */                               out.write("\n            ");
/*      */                               
/* 3469 */                               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3470 */                               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3471 */                               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 3473 */                               _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 3474 */                               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3475 */                               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 3477 */                                   out.write("\n                ");
/*      */                                   
/* 3479 */                                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3480 */                                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3481 */                                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fpresent_005f3);
/* 3482 */                                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3483 */                                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                     for (;;) {
/* 3485 */                                       out.write("\n                    ");
/*      */                                       
/* 3487 */                                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3488 */                                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3489 */                                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                       
/* 3491 */                                       _jspx_th_c_005fwhen_005f2.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' }");
/* 3492 */                                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3493 */                                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                         for (;;) {
/* 3495 */                                           out.write(" &nbsp;\n                        <select onchange=\"javascript:invokeOperations1()\" class=\"formtext\" name=\"operation\">\n                            <option VALUE=\"selectactions\" selected=true>-----");
/* 3496 */                                           out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 3497 */                                           out.write("-----</option>\n                            ");
/*      */                                           
/* 3499 */                                           ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3500 */                                           _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3501 */                                           _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f2);
/* 3502 */                                           int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3503 */                                           if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                             for (;;) {
/* 3505 */                                               out.write("\n\t                   \t\t\t ");
/*      */                                               
/* 3507 */                                               WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3508 */                                               _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3509 */                                               _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                               
/* 3511 */                                               _jspx_th_c_005fwhen_005f3.setTest("${allowEdit=='true'}");
/* 3512 */                                               int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3513 */                                               if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                                 for (;;) {
/* 3515 */                                                   out.write("\n\t                            \t<option value=\"editDisplayNames\">");
/* 3516 */                                                   out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 3517 */                                                   out.write("</option>\n\t                             ");
/* 3518 */                                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3519 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3523 */                                               if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3524 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                               }
/*      */                                               
/* 3527 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3528 */                                               out.write("\n                \t\t\t");
/* 3529 */                                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3530 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3534 */                                           if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3535 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                           }
/*      */                                           
/* 3538 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3539 */                                           out.write("\n                \t\t\t");
/*      */                                           
/* 3541 */                                           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3542 */                                           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3543 */                                           _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f2);
/* 3544 */                                           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3545 */                                           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                             for (;;) {
/* 3547 */                                               out.write("\n\t                   \t\t\t ");
/*      */                                               
/* 3549 */                                               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3550 */                                               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3551 */                                               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                               
/* 3553 */                                               _jspx_th_c_005fwhen_005f4.setTest("${allowUpdateIP=='true'}");
/* 3554 */                                               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3555 */                                               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                                 for (;;) {
/* 3557 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t <option value=\"updateIP\">");
/* 3558 */                                                   out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 3559 */                                                   out.write("</option>\n\t                             ");
/* 3560 */                                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3561 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3565 */                                               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3566 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                               }
/*      */                                               
/* 3569 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3570 */                                               out.write("\n                \t\t\t");
/* 3571 */                                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3572 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3576 */                                           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3577 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                           }
/*      */                                           
/* 3580 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3581 */                                           out.write("\n                \t\t\t");
/*      */                                           
/* 3583 */                                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3584 */                                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 3585 */                                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fwhen_005f2);
/* 3586 */                                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 3587 */                                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                             for (;;) {
/* 3589 */                                               out.write("\n\t                   \t\t\t ");
/*      */                                               
/* 3591 */                                               WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3592 */                                               _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3593 */                                               _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                               
/* 3595 */                                               _jspx_th_c_005fwhen_005f5.setTest("${allowManage=='true'}");
/* 3596 */                                               int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3597 */                                               if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                                 for (;;) {
/* 3599 */                                                   out.write("\n\t                            \t<option value=\"manage\">");
/* 3600 */                                                   out.print(FormatUtil.getString("Manage"));
/* 3601 */                                                   out.write("</option>\n                            \t\t<option value=\"unManage\">");
/* 3602 */                                                   out.print(FormatUtil.getString("UnManage"));
/* 3603 */                                                   out.write("</option>\n\t                             ");
/* 3604 */                                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3605 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3609 */                                               if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3610 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                               }
/*      */                                               
/* 3613 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3614 */                                               out.write("\n                \t\t\t");
/* 3615 */                                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3616 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3620 */                                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3621 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                           }
/*      */                                           
/* 3624 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3625 */                                           out.write("\n                     \t   \n                        </select>\n                    ");
/* 3626 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3627 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3631 */                                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3632 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                       }
/*      */                                       
/* 3635 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3636 */                                       out.write("\n                ");
/* 3637 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3638 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3642 */                                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3643 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                   }
/*      */                                   
/* 3646 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3647 */                                   out.write("\n             ");
/* 3648 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3649 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3653 */                               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3654 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                               }
/*      */                               
/* 3657 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3658 */                               out.write("\n            \t&nbsp;\n            \t");
/*      */                               String selectedValue;
/*      */                               String selectedValue;
/* 3661 */                               if (request.getParameter("method").equals("showResourceTypesAll"))
/*      */                               {
/* 3663 */                                 selectedValue = request.getParameter("viewmontype");
/*      */                               } else { String selectedValue;
/* 3665 */                                 if ((request.getParameter("method").equals("showResourceTypes")) && (request.getParameter("group") != null))
/*      */                                 {
/* 3667 */                                   selectedValue = request.getParameter("group");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3671 */                                   selectedValue = request.getParameter("network");
/*      */                                 }
/*      */                               }
/* 3674 */                               out.write("\n\t\t        <!--");
/*      */                               
/* 3676 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3677 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3678 */                               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 3680 */                               _jspx_th_html_005fform_005f1.setAction("/showresource.do?method=showResourceTypesAll&group=All");
/*      */                               
/* 3682 */                               _jspx_th_html_005fform_005f1.setMethod("post");
/*      */                               
/* 3684 */                               _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/* 3685 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3686 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 3688 */                                   out.write(" -->\n\t\t            ");
/*      */                                   
/* 3690 */                                   NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3691 */                                   _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3692 */                                   _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f1);
/*      */                                   
/* 3694 */                                   _jspx_th_logic_005fnotEmpty_005f0.setName("listviewresourcetype");
/* 3695 */                                   int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3696 */                                   if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                     for (;;) {
/* 3698 */                                       out.write("\n\t\t            ");
/*      */                                       
/* 3700 */                                       SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 3701 */                                       _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3702 */                                       _jspx_th_html_005fselect_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                       
/* 3704 */                                       _jspx_th_html_005fselect_005f1.setProperty("type");
/*      */                                       
/* 3706 */                                       _jspx_th_html_005fselect_005f1.setValue(selectedValue);
/*      */                                       
/* 3708 */                                       _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadURLType(this.options[this.selectedIndex].value,this.form,this);");
/*      */                                       
/* 3710 */                                       _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/* 3711 */                                       int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3712 */                                       if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3713 */                                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3714 */                                           out = _jspx_page_context.pushBody();
/* 3715 */                                           _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3716 */                                           _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3719 */                                           out.write("\n\t\t            \t\n\t\t\t\t        ");
/*      */                                           
/* 3721 */                                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3722 */                                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3723 */                                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                           
/* 3725 */                                           _jspx_th_logic_005fiterate_005f0.setName("listviewresourcetype");
/*      */                                           
/* 3727 */                                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                           
/* 3729 */                                           _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                           
/* 3731 */                                           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3732 */                                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3733 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3734 */                                             ArrayList row = null;
/* 3735 */                                             Integer j = null;
/* 3736 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3737 */                                               out = _jspx_page_context.pushBody();
/* 3738 */                                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3739 */                                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                             }
/* 3741 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3742 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                             for (;;) {
/* 3744 */                                               out.write("\n\t\t\t\t        ");
/*      */                                               
/* 3746 */                                               String space = "";
/* 3747 */                                               boolean hasresourceGroup = false;
/* 3748 */                                               for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                                               {
/* 3750 */                                                 if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals((String)row.get(1)))
/*      */                                                 {
/* 3752 */                                                   hasresourceGroup = true;
/* 3753 */                                                   break; } }
/*      */                                               String selectedStyle;
/*      */                                               String selectedStyle;
/* 3756 */                                               if (((String)row.get(1) != null) && ((((String)row.get(1)).equals("All")) || (hasresourceGroup)))
/*      */                                               {
/* 3758 */                                                 selectedStyle = "bodytextbold";
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 3762 */                                                 selectedStyle = "bodytext";
/* 3763 */                                                 if (!((String)row.get(1)).equals("-")) { space = "&nbsp;&nbsp;&nbsp;";
/*      */                                                 }
/*      */                                               }
/* 3766 */                                               out.write("\n\t\t\t\t            ");
/*      */                                               
/* 3768 */                                               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass.get(OptionTag.class);
/* 3769 */                                               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3770 */                                               _jspx_th_html_005foption_005f8.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 3772 */                                               _jspx_th_html_005foption_005f8.setValue((String)row.get(1));
/*      */                                               
/* 3774 */                                               _jspx_th_html_005foption_005f8.setStyleClass(selectedStyle);
/* 3775 */                                               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3776 */                                               if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3777 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3778 */                                                   out = _jspx_page_context.pushBody();
/* 3779 */                                                   _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3780 */                                                   _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3783 */                                                   out.print(space);
/* 3784 */                                                   out.print(row.get(0));
/* 3785 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3786 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3789 */                                                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3790 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3793 */                                               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3794 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                               }
/*      */                                               
/* 3797 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyleClass.reuse(_jspx_th_html_005foption_005f8);
/* 3798 */                                               out.write("\n\t\t\t\t        ");
/* 3799 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3800 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3801 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 3802 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3805 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3806 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3809 */                                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3810 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                           }
/*      */                                           
/* 3813 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3814 */                                           out.write("\n\t\t\t\t      \n\t\t            ");
/* 3815 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3816 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3819 */                                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3820 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3823 */                                       if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3824 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                       }
/*      */                                       
/* 3827 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 3828 */                                       out.write("\n\t\t            ");
/* 3829 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3830 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3834 */                                   if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3835 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                   }
/*      */                                   
/* 3838 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3839 */                                   out.write("\t \n\t\t        <!--");
/* 3840 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3841 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3845 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3846 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 3849 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3850 */                               out.write("-->\n\t\t        </td>\n            </form>\n\t        </tr></table>\n\t     </table></td>\n\t      </tr></table>\n\t     </td>\n\t     </tr></table>\n\t\t</td>\n\t\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\t\t\t\t</tr>\n\t\t</table>\n ");
/*      */                             }
/*      */                             
/*      */ 
/* 3854 */                             out.write("\n          \n\n\t\t\t\n");
/* 3855 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3856 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3859 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3860 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3863 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3864 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 3867 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3868 */                         out.write(10);
/* 3869 */                         out.write(32);
/* 3870 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3872 */                         out.write(10);
/* 3873 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3874 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3878 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3879 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3882 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3883 */                       out.write(10);
/*      */                     }
/* 3885 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3886 */         out = _jspx_out;
/* 3887 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3888 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3889 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3892 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3898 */     PageContext pageContext = _jspx_page_context;
/* 3899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3901 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3902 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3903 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 3905 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 3907 */     _jspx_th_c_005fset_005f0.setProperty("defaultmonitorsview");
/*      */     
/* 3909 */     _jspx_th_c_005fset_005f0.setValue("showResourceTypesAll");
/* 3910 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3911 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3912 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3913 */       return true;
/*      */     }
/* 3915 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3916 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3921 */     PageContext pageContext = _jspx_page_context;
/* 3922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3924 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3925 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3926 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3928 */     _jspx_th_html_005fhidden_005f0.setProperty("zoomlevel");
/* 3929 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3930 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3944 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3947 */     _jspx_th_html_005fhidden_005f1.setProperty("zoomlocation");
/* 3948 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3949 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3950 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3951 */       return true;
/*      */     }
/* 3953 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3959 */     PageContext pageContext = _jspx_page_context;
/* 3960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3962 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3963 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3964 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3966 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3967 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3968 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3970 */         out.write("\n    alertUser();\n    return;\n  ");
/* 3971 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3976 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3977 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3978 */       return true;
/*      */     }
/* 3980 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3986 */     PageContext pageContext = _jspx_page_context;
/* 3987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3989 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3990 */     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3991 */     _jspx_th_logic_005fnotPresent_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3993 */     _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3994 */     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3995 */     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */       for (;;) {
/* 3997 */         out.write("\n  document.AMActionForm.submit();\n  ");
/* 3998 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3999 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4003 */     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 4004 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4005 */       return true;
/*      */     }
/* 4007 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 4008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4013 */     PageContext pageContext = _jspx_page_context;
/* 4014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4016 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4017 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4018 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4020 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4022 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4023 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4024 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4025 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4026 */       return true;
/*      */     }
/* 4028 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4029 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\allresources_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */