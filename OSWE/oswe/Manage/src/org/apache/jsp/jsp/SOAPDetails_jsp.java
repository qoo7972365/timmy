/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.wsm.WSMGraph;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class SOAPDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2176 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2183 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/wsmbreadcrumb.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2242 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2243 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2248 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.release();
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
/* 2276 */       out.write("<!--$Id$ -->\n\n\n\n\n");
/* 2277 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2279 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2280 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2282 */       out.write(10);
/* 2283 */       out.write(10);
/* 2284 */       out.write(10);
/* 2285 */       out.write("\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2286 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2288 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2289 */       WSMGraph wsmGraph = null;
/* 2290 */       wsmGraph = (WSMGraph)_jspx_page_context.getAttribute("wsmGraph", 2);
/* 2291 */       if (wsmGraph == null) {
/* 2292 */         wsmGraph = new WSMGraph();
/* 2293 */         _jspx_page_context.setAttribute("wsmGraph", wsmGraph, 2);
/*      */       }
/* 2295 */       out.write(10);
/* 2296 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2297 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 2);
/* 2298 */       if (wlsGraph == null) {
/* 2299 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2300 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 2);
/*      */       }
/* 2302 */       out.write(10);
/* 2303 */       out.write(10);
/*      */       
/* 2305 */       HashMap soapInfo = (HashMap)request.getAttribute("SOAPInfo");
/* 2306 */       String operationId = request.getParameter("operationId");
/* 2307 */       String type = request.getParameter("type");
/* 2308 */       String soapReq = "";String soapRes = "";
/* 2309 */       String soapAction = (String)soapInfo.get("Action");
/* 2310 */       String name = (String)soapInfo.get("name");
/* 2311 */       String xslt = "";
/* 2312 */       HashMap xsltAttributes = null;
/* 2313 */       ArrayList attribIDs = new ArrayList();
/* 2314 */       ArrayList resIDs = new ArrayList();
/* 2315 */       Properties alert = null;
/* 2316 */       HashMap configProps = new HashMap();
/* 2317 */       soapReq = (String)soapInfo.get("Request");
/* 2318 */       xslt = (String)soapInfo.get("xsltInput");
/* 2319 */       pageContext.setAttribute("xslt", xslt);
/* 2320 */       soapRes = (String)soapInfo.get("Response");
/* 2321 */       if (!soapRes.contains("No data Available"))
/*      */       {
/* 2323 */         xsltAttributes = (HashMap)soapInfo.get("xsltAttributes");
/* 2324 */         pageContext.setAttribute("xsltAttributes", xsltAttributes);
/* 2325 */         ArrayList graphIds = (ArrayList)soapInfo.get("graphIds");
/* 2326 */         pageContext.setAttribute("graphIds", graphIds);
/* 2327 */         Object[] arr = xsltAttributes.keySet().toArray();
/* 2328 */         attribIDs.add("3504");
/* 2329 */         attribIDs.add("3505");
/* 2330 */         attribIDs.add("3506");
/* 2331 */         for (int i = 0; i < arr.length; i++)
/*      */         {
/* 2333 */           attribIDs.add(arr[i].toString());
/*      */         }
/* 2335 */         resIDs.add(operationId);
/* 2336 */         alert = getStatus(resIDs, attribIDs);
/* 2337 */         configProps = (HashMap)request.getAttribute("configProps");
/* 2338 */         pageContext.setAttribute("configProps", configProps);
/* 2339 */         pageContext.setAttribute("noResponse", Boolean.valueOf(false));
/*      */       }
/*      */       else
/*      */       {
/* 2343 */         pageContext.setAttribute("noResponse", Boolean.valueOf(true));
/*      */       }
/* 2345 */       String resid = (String)soapInfo.get("resourceId");
/* 2346 */       String encodeurl = URLEncoder.encode("/wsm.do?method=getSOAPInfo&operationId=" + operationId + "&type=response&resId=" + resid);
/* 2347 */       request.setAttribute("operationName", name);
/*      */       
/*      */ 
/*      */ 
/* 2351 */       out.write(10);
/* 2352 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t");
/*      */       
/* 2354 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2355 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2356 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/* 2358 */       _jspx_th_c_005fif_005f0.setTest("${param.method=='showdetails'}");
/* 2359 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2360 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/* 2362 */           out.write("\t\n       <td class=\"bcsign\" height=\"22\">");
/* 2363 */           out.print(BreadcrumbUtil.getMonitorsPage());
/* 2364 */           out.write(" &gt; ");
/* 2365 */           out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2366 */           out.write(" &gt; <span class=\"bcactive\">");
/* 2367 */           out.print(request.getAttribute("displayname"));
/* 2368 */           out.write(" </span></td>\n\t");
/* 2369 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2370 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2374 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2375 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 2378 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2379 */         out.write(10);
/* 2380 */         out.write(9);
/*      */         
/* 2382 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2383 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2384 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/* 2386 */         _jspx_th_c_005fif_005f1.setTest("${param.method=='manageoperations'}");
/* 2387 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2388 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/* 2390 */             out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 2391 */             out.print(BreadcrumbUtil.getMonitorsPage());
/* 2392 */             out.write(" &gt; ");
/* 2393 */             out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2394 */             out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2395 */             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */               return;
/* 2397 */             out.write(34);
/* 2398 */             out.write(62);
/* 2399 */             out.print(request.getAttribute("displayname"));
/* 2400 */             out.write("</a> &gt; <span class=\"bcactive\">");
/* 2401 */             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 2402 */             out.write("</span>\n      </td>\n\t");
/* 2403 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2404 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2408 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2409 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */         }
/*      */         else {
/* 2412 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2413 */           out.write(10);
/* 2414 */           out.write(10);
/* 2415 */           out.write(9);
/*      */           
/* 2417 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2418 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2419 */           _jspx_th_c_005fif_005f2.setParent(null);
/*      */           
/* 2421 */           _jspx_th_c_005fif_005f2.setTest("${param.method=='showoperations'}");
/* 2422 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2423 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */             for (;;) {
/* 2425 */               out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 2426 */               out.print(BreadcrumbUtil.getMonitorsPage());
/* 2427 */               out.write(" &gt; ");
/* 2428 */               out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2429 */               out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2430 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                 return;
/* 2432 */               out.write(34);
/* 2433 */               out.write(62);
/* 2434 */               out.print(request.getAttribute("displayname"));
/* 2435 */               out.write("</a> &gt; <span class=\"bcactive\">");
/* 2436 */               out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 2437 */               out.write("</span>\n      </td>\n\t");
/* 2438 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2439 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2443 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2444 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */           }
/*      */           else {
/* 2447 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2448 */             out.write(10);
/* 2449 */             out.write(9);
/*      */             
/* 2451 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2452 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2453 */             _jspx_th_c_005fif_005f3.setParent(null);
/*      */             
/* 2455 */             _jspx_th_c_005fif_005f3.setTest("${param.method=='getSOAPInfo'}");
/* 2456 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2457 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */               for (;;) {
/* 2459 */                 out.write("\n\t  <td class=\"bcsign\" height=\"22\">\n\t   \t");
/* 2460 */                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2461 */                 out.write(" &gt; ");
/* 2462 */                 out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 2463 */                 out.write(" &gt; <a class=\"bcinactive\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2464 */                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                   return;
/* 2466 */                 out.write(34);
/* 2467 */                 out.write(62);
/* 2468 */                 out.print(request.getAttribute("displayname"));
/* 2469 */                 out.write(" </a> &gt; <span class=\"bcactive\">");
/* 2470 */                 out.print(request.getAttribute("operationName"));
/* 2471 */                 out.write("</span>\n\t");
/* 2472 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2473 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2477 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2478 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */             }
/*      */             else {
/* 2481 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2482 */               out.write("\n</tr>\n\t<tr>\n\t\t<td  class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"2px\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\t\n");
/* 2483 */               out.write("\n\n<html>\n\t<head>\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/confMonitor.js\"></SCRIPT>\n\t<script type=\"text/javascript\">\n\t\twindow.onload = function()\n\t\t{\n\t\t\t");
/* 2484 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */                 return;
/* 2486 */               out.write("\n\t\t}\n\t\tfunction showReq()\n\t\t{\n\t\t\tdocument.getElementById(\"Request\").style.display=\"block\";\n\t\t\tdocument.getElementById(\"Response\").style.display=\"none\";\n\t\t\tchangeTabStyle(0,1);\n\t\t}\n\n\t\tfunction showRes()\n\t\t{\n\t\t\tdocument.getElementById(\"Request\").style.display=\"none\";\n\t\t\tdocument.getElementById(\"Response\").style.display=\"block\";\n\t\t\tchangeTabStyle(1,0);\n\t\t}\n\t\tfunction submitForm(formtosubmit)\n\t\t\t{\n\t\t");
/* 2487 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                 return;
/* 2489 */               out.write("\n\t\t\t\tvar soaprequest = document.getElementById(\"soapRequest\").value.trim();\n\t\t\t\tvar soapvalue;\n\t\t\t\tif(soaprequest == '' )\n\t\t\t\t{\t\n\t\t\t\t\n\t\t\t\t\talert('");
/* 2490 */               out.print(FormatUtil.getString("am.webclient.wsm.alert.soaprequest.text"));
/* 2491 */               out.write("');\n\t\t\t\t\treturn;\n\t\t\t\t}\t\t\n\t\t\t\t\t\n\t\t\t\n\t\t\t\tfor(var i=0;i<soaprequest.length;i++)\n\t\t\t\t{\n\t\t\t\t\n\t\t\t \tif(soaprequest.substring(i,i+3)==\">?<\")\n\t\t\t \t{\n\t\t\t \t\ti=soaprequest.length\n\t\t\t \t\tsoapvalue = false\n\t\t\t \t\talert('");
/* 2492 */               out.print(FormatUtil.getString("am.webclient.wsm.alert.entervalues.text"));
/* 2493 */               out.write("')\n\t\t\t \t\t\n\t\t\t\t}\n\t\t\t \telse\n\t\t\t \t{\n\t\t\t \t \tsoapvalue = true\n\t\t\t \t\n\t\t\t\t}\n\t\t\t\t}\n\t\t\t\tif(soapvalue)\n\t\t\t\t{\n\t\t\t\t\tif(formtosubmit == 1)\n\t\t\t\t\t{\n\t\t\t\t\t\tdocument.forms.requestForm.submit();\n\t\t\t\t\t}\n\t\t\t\t\telse if(formtosubmit==2)\n\t\t\t\t\t{\n\t\t\t\t\t\tvar dialogTitle = '");
/* 2494 */               out.print(FormatUtil.getString("am.webclient.wsm.soapdetails.soapresponse"));
/* 2495 */               out.write("'\n\t\t\t\t\t\tvar tag = $('<div></div>');\n\t\t\t\t\t\ttag.html('<table border=\"0\"  width=\"100%\" ><tr><td align=\"center\" height=\"300\"><img src=\"/images/loader.gif\" ></td></tr></table>').dialog({\n\t\t\t\t\t\t\t\tautoOpen: true,\tmodal: true, draggable: false, resizable: false,\n\t\t\t\t\t\t\t\tcloseText: 'Close',\tshow: 'fade', position:'center',  //No I18N\n\t\t\t\t\t\t\t\twidth: '650',\n\t\t\t\t\t\t\t\tminHeight : '350', \n\t\t\t\t\t\t\t\ttitle: dialogTitle,\n\t\t\t\t\t\t\t\topen: function()\n\t\t\t\t\t\t\t\t{\n\t\t\t\t\t\t\t\t\t$('.ui-dialog').css(\"padding\",\"0px\"); //No I18N\n\t\t\t\t\t\t\t\t\t$('.ui-dialog-titlebar').removeClass('ui-corner-all').addClass('ui-corner-top'); //No I18N\n\t\t\t\t\t\t\t\t},\n\t\t\t\t\t\t\t\tclose: function(event, ui) \n\t\t\t\t\t\t\t\t{\n\t\t\t\t\t\t\t\t\ttag.dialog('destroy').empty().hide(); //No I18N\n\t\t\t\t\t\t\t\t}\n\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t$.post(\"/wsm.do?method=testOperation&resourceid=\"+");
/* 2496 */               out.print(resid);
/* 2497 */               out.write(", $('#requestForm').serialize(),function(data){\t// No I18N\n\t\t\t\t\t\tJSONObject = eval( \"(\" + data + \")\" );\n\t\t\t\t\t\tif(JSONObject.xsltOutput!=\"\")\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\ttag.dialog({width:'1000'});\n\t\t\t\t\t\t\ttag.html('<table width=\"100%\" border=\"0\" class=\"bodytext\"><tr><td width=\"50%\"><table><tr><td class=\"tableheadingbborder\" width=\"20%\" valign=\"top\"><span class=\"bcactive\">SOAP Response : </span></td></tr><tr><td align=\"center\"><textarea rows=\"20\" cols=\"70\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\" style=\"resize: none; overflow-y: scroll;\">'+JSONObject.soapresponse+'</textarea></td></tr></table></td><td><table><tr><td class=\"tableheadingbborder\" width=\"20%\" valign=\"top\"><span class=\"bcactive\">XSLT Output : </span></td></tr><tr><td align=\"center\"><textarea style=\"resize: none; overflow-y: scroll;\" rows=\"20\" cols=\"70\" accept=\"xml\"  id=\"xsltOutput\" name=\"xsltOutput\">'+JSONObject.xsltOutput+'</textarea></td></tr></table></td></tr></table>') //No I18N\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\ttag.html('<table width=\"100%\" border=\"0\" class=\"bodytext\"><tr><td align=\"center\"><textarea style=\"border: none; resize: none\" rows=\"20\" cols=\"85\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\">'+JSONObject.soapresponse+'</textarea></td></tr></table>') \n");
/* 2498 */               out.write("\t\t\t\t\t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t}\t\n\t\t\t\t}\n\t\t\t}\n\tfunction enablereports()\n\t{\n\t\t");
/* 2499 */               if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                 return;
/* 2501 */               out.write("\n\t\tif(!checkforOneSelected(document.xsltAttrs,'xslt'))\n\t\t{\n\t\t\talert(\"");
/* 2502 */               out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectattribute.text"));
/* 2503 */               out.write("\");\n\t\t\treturn;\n\t\t}\n\t\tdocument.forms.xsltAttrs.method.value=\"enabledisablereports\"; //No I18N\n\t\tdocument.forms.xsltAttrs.enable.value=\"true\";\n\t\tdocument.forms.xsltAttrs.submit();\n\t}\n\n\tfunction disablereports()\n\t{\n\t\t");
/* 2504 */               if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                 return;
/* 2506 */               out.write("\n\t\tif(!checkforOneSelected(document.xsltAttrs,'xslt'))\n\t\t{\n\t\t\talert(\"");
/* 2507 */               out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectattribute.text"));
/* 2508 */               out.write("\");\n\t\t\treturn;\n\t\t}\n\t\tdocument.forms.xsltAttrs.method.value=\"enabledisablereports\"; //No I18N\n\t\tdocument.forms.xsltAttrs.enable.value=\"false\";\n\t\tdocument.forms.xsltAttrs.submit();\n\t}\n\n\t</script>\n\t");
/* 2509 */               out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2510 */               out.write("\n\t</head>\n\t<body>\n\t<div id=\"performgcr\" style=\"display:none;padding-bottom:6px;\">\n\t</div>\t\n\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n  \t\t\t<tbody>\n    \t\t\t<tr class=\"tabBtmLine\">\n     \t\t\t\t <td id=\"mytd\" nowrap=\"nowrap\">\n      \t\t\t\t\t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    \t\t\t\t\t<tbody>\n    \t\t\t\t\t\t<tr>\n        \t\t\t\t\t\t<td width=\"17\">&nbsp;&nbsp;&nbsp;&nbsp;</td>\n            \t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<table style=\"cursor: pointer;\" id=\"RequestTab\" title=\"SOAP Request Information\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t        \t\t\t\t\t\t<tbody>\n\t\t        \t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td id=\"Tab_0_left\" class=\"tbUnselected_Left\"><img src=\"/images/spacer.gif\" alt=\"spacer\" height=\"1\" width=\"1\"></td>\n\t\t\t\t\t\t\t\t\t\t\t<td id=\"Tab_0_middle\" style=\"padding-left:5px;padding-right:5px\" class=\"tbUnselected_Middle\" onclick=\"showReq()\"><span id=\"Tab_0_text\" class=\"bodytext\">");
/* 2511 */               out.print(FormatUtil.getString("am.webclient.wsm.soaprequest.text"));
/* 2512 */               out.write("</span></td>\n\t\t                \t\t\t\t\t<td id=\"Tab_0_right\" class=\"tbUnselected_Right\"><img src=\"/images/spacer.gif\" alt=\"spacer\" height=\"1\" width=\"1\"></td>\n\t\t            \t\t\t\t\t</tr>\n\t            \t\t\t\t\t</tbody>\n              \t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<table style=\"cursor: pointer;\" id=\"ResponseTab\" title=\"SOAP Response Information\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t            \t\t\t\t\t<tbody>\n\t\t            \t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td id=\"Tab_1_left\" class=\"tbSelected_Left\"><img src=\"/images/spacer.gif\" alt=\"spacer\" height=\"1\" width=\"1\"></td>\n\t\t\t\t\t\t\t\t\t\t\t<td id=\"Tab_1_middle\" style=\"padding-left:5px;padding-right:5px\" class=\"tbSelected_Middle\" onclick=\"showRes()\"><span id=\"Tab_1_text\" class=\"tabLinkActive\">");
/* 2513 */               out.print(FormatUtil.getString("am.webclient.wsm.soapresponse.text"));
/* 2514 */               out.write("</span></td>\n\t\t                \t\t\t\t\t<td id=\"Tab_1_right\" class=\"tbSelected_Right\"><img src=\"/images/spacer.gif\" alt=\"spacer\" height=\"1\" width=\"1\"></td>\n\t\t            \t\t\t\t\t</tr>\n\t            \t\t\t\t\t</tbody>\n              \t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr><td><br /><br /></td></tr>\n    \t\t\t\t</tbody>\n    \t\t\t\t</table>\n\t\t\t\t</td>\n            </tr>\n        </tbody>\n    </table>\n\t\n\n\t\t\t<table border=\"0\" align=\"center\" width=\"90%\">\n\t\t\t\t<tr><td  height=\"10\"></td></tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id=\"Request\">\n\t\t\t\t\t\t<form id=\"requestForm\" action=\"/wsm.do\" method=\"post\">\n\t\t\t\t\t\t<input type=\"hidden\" name=\"method\" value=\"updateRequest\">\n\t\t\t\t\t\t<input type=\"hidden\" name=\"resourceId\" value=");
/* 2515 */               out.print(resid);
/* 2516 */               out.write(">\n\t\t\t\t\t\t<input type=\"hidden\" name=\"operationId\" value=");
/* 2517 */               out.print(operationId);
/* 2518 */               out.write(">\n\t\t\t\t\t\t<table cellpadding=\"10\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<table>\n\t\t\t\t\t\t\t\t\t<tr height=\"2%\">\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" border=\"0\"  cellpadding=\"0\" width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:15px\" width=\"20%\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"bcactive\">");
/* 2519 */               out.print(FormatUtil.getString("am.webclient.wsm.operationname.text"));
/* 2520 */               out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"operationName\" id=\"operationName\" value=\"");
/* 2521 */               out.print(name);
/* 2522 */               out.write("\" style=\"width:300px\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td  height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr height=\"2%\">\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" border=\"0\"  cellpadding=\"0\" width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:15px\" width=\"20%\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"bcactive\">");
/* 2523 */               out.print(FormatUtil.getString("am.webclient.wsm.soapdetails.soapaction.text"));
/* 2524 */               out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"soapAction\" id=\"soapAction\" value=\"");
/* 2525 */               out.print(soapAction);
/* 2526 */               out.write("\" style=\"width:300px\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td  height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr height=\"2%\">\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\"  border=\"0\" cellpadding=\"0\" width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:15px\" width=\"20%\" valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"bcactive\">");
/* 2527 */               out.print(FormatUtil.getString("am.webclient.wsm.soaprequest.text"));
/* 2528 */               out.write("</span>&nbsp;<span class=\"mandatory\">*</span>&nbsp;\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<textarea rows=\"20\" cols=\"85\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\" style=\"overflow-y: scroll;\"> ");
/* 2529 */               out.print(soapReq);
/* 2530 */               out.write(" </textarea>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td  height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr height=\"2%\">\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\"  border=\"0\" cellpadding=\"0\" width=\"99%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:15px\" width=\"20%\" valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"bcactive\">");
/* 2531 */               out.print(FormatUtil.getString("am.webclient.wsm.xsltInput.text"));
/* 2532 */               out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<textarea rows=\"20\" cols=\"85\" accept=\"xml\"  id=\"XSLTInput\" name=\"XSLTInput\" style=\"overflow-y: scroll;\" >");
/* 2533 */               if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */                 return;
/* 2535 */               out.write("</textarea>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t");
/*      */               
/* 2537 */               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2538 */               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2539 */               _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */               
/* 2541 */               _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,DEMO");
/* 2542 */               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2543 */               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                 for (;;) {
/* 2545 */                   out.write("\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"center\"><input type=\"button\" class=\"buttons\" value=");
/* 2546 */                   out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2547 */                   out.write(" onClick=\"javascript:submitForm(1)\"><input type=\"button\" class=\"buttons\" value=\"");
/* 2548 */                   out.print(FormatUtil.getString("am.webclient.wsm.testoperation.text"));
/* 2549 */                   out.write("\" onClick=\"javascript:submitForm(2)\"/></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t");
/* 2550 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2551 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2555 */               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2556 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */               }
/*      */               else {
/* 2559 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2560 */                 out.write("\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td valign=\"top\" style=\"padding-top:60px\">\n\t\t\t\t\t\t\t\t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardHdrTopBg\">\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 2561 */                 out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 2562 */                 out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t\t\t\t\t\t\t\t\t\t    <tr>\n  \t\t\t\t\t\t\t\t\t\t      <td style=\"padding-top: 10px;\" class=\"boxedContent\">\n  \t\t\t\t\t\t\t\t\t\t      <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n    \t\t\t\t\t\t\t\t\t\t\t<tr>\n      \t\t\t\t\t\t\t\t\t\t    \t<td class=\"txtSpace\"><p>");
/* 2563 */                 out.print(FormatUtil.getString("am.webclient.wsm.helpcard.heading.text"));
/* 2564 */                 out.write("</p></td>\n    \t\t\t\t\t\t   \t\t\t \t\t</tr>\n      \t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t    \t\t\t    \t<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        \t\t\t\t\t\t\t\t\t       \t\t<tr>\n          \t\t\t\t\t\t\t\t\t        \t<td class=\"hCardInnerTopLeft\"/>\n          \t\t\t\t\t\t\t\t\t        \t<td class=\"hCardInnerTopBg\"/>\n          \t\t\t\t\t\t\t\t\t        \t<td class=\"hCardInnerTopRight\"/>\n        \t\t\t\t\t\t\t\t\t        \t</tr>\n        \t\t\t\t\t\t \t\t\t       \t<tr>\n          \t\t\t\t\t\t \t\t\t       \t\t<td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                  \t\t\t\t\t\t\t\t\t\t    <td class=\"hCardInnerBoxBg\">");
/* 2565 */                 out.print(FormatUtil.getString("am.webclient.wsm.helpcard.contents.text"));
/* 2566 */                 out.write("</td>\n            \t\t\t\t\t\t\t\t\t    \t    <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n          \t\t\t\t\t\t\t\t\t   \t    \t</tr>\n  \t\t\t\t\t\t        \t\t\t\t\t</table></td>\n  \t\t\t\t\t\t      \t\t\t  \t\t</tr>\n\t\t\t\t\t\t    \t\t\t\t   </table>\n     \t\t\t\t\t\t\t\t\t\t   </td>\n  \t\t\t\t\t\t\t\t\t\t\t</tr>\n  \t\t\t\t\t\t\t\t\t\t\t</table>\n  \t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t\t\t\t\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</form>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id=\"Response\">\n\t\t\t\t\t\t");
/*      */                 
/* 2568 */                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2569 */                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2570 */                 _jspx_th_c_005fchoose_005f1.setParent(null);
/* 2571 */                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2572 */                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                   for (;;) {
/* 2574 */                     out.write("\n\t\t\t\t\t\t");
/*      */                     
/* 2576 */                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2577 */                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2578 */                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                     
/* 2580 */                     _jspx_th_c_005fwhen_005f1.setTest("${noResponse}");
/* 2581 */                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2582 */                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                       for (;;) {
/* 2584 */                         out.write("\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n  \t\t\t\t\t\t\t\t<tbody><tr>\n\t\t\t\t\t\t\t\t\t<td width=\"6%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"23\" height=\"23\"> \n    \t\t\t\t\t\t\t\t</td>\n   \t\t\t\t\t\t\t\t\t <td width=\"94%\" height=\"34\" class=\"message\">");
/* 2585 */                         out.print(FormatUtil.getString("weblogic.datacollection.notstarted"));
/* 2586 */                         out.write(".</td>\n    \t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody></table>\n\t\t\t\t\t\t");
/* 2587 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2588 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2592 */                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2593 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                     }
/*      */                     
/* 2596 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2597 */                     out.write("\n\t\t\t\t\t\t");
/*      */                     
/* 2599 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2600 */                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2601 */                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2602 */                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2603 */                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                       for (;;) {
/* 2605 */                         out.write("\n\t\t\t\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" align=\"center\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"55%\" valign=\"top\">\n\t\t\t\t\t\t\t\t<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\">\n    \t\t\t\t\t\t\t\t<tr><td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2606 */                         out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2607 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n    \t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\" width=\"20%\">");
/* 2608 */                         out.print(FormatUtil.getString("am.webclient.wsm.host.text"));
/* 2609 */                         out.write("</td>\n    \t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\" width=\"80%\">");
/* 2610 */                         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2612 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2613 */                         out.print(FormatUtil.getString("am.webclient.wsm.port.text"));
/* 2614 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2615 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2617 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2618 */                         out.print(FormatUtil.getString("am.webclient.wsm.servicename.text"));
/* 2619 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2620 */                         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2622 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2623 */                         out.print(FormatUtil.getString("am.webclient.wsm.endpoint.text"));
/* 2624 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2625 */                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2627 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2628 */                         out.print(FormatUtil.getString("am.webclient.wsm.wsdlpath.text"));
/* 2629 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2630 */                         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2632 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2633 */                         out.print(FormatUtil.getString("am.webclient.wsm.soapname.text"));
/* 2634 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2635 */                         out.print(name);
/* 2636 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2637 */                         out.print(FormatUtil.getString("am.webclient.wsm.soapaction.text"));
/* 2638 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2639 */                         out.print(soapAction);
/* 2640 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2641 */                         out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2642 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                         
/* 2644 */                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2645 */                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2646 */                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/* 2647 */                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2648 */                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                           for (;;) {
/* 2650 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                             
/* 2652 */                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2653 */                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2654 */                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                             
/* 2656 */                             _jspx_th_c_005fwhen_005f2.setTest("${configProps.lastpoll != 0}");
/* 2657 */                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2658 */                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                               for (;;) {
/* 2660 */                                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2661 */                                 out.print(formatDT((String)configProps.get("lastpoll")));
/* 2662 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2663 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2664 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2668 */                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2669 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                             }
/*      */                             
/* 2672 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2673 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2674 */                             if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*      */                               return;
/* 2676 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 2677 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2678 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2682 */                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2683 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                         }
/*      */                         
/* 2686 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2687 */                         out.write("\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2688 */                         out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2689 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                         
/* 2691 */                         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2692 */                         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2693 */                         _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fotherwise_005f1);
/* 2694 */                         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2695 */                         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                           for (;;) {
/* 2697 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                             
/* 2699 */                             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2700 */                             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2701 */                             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                             
/* 2703 */                             _jspx_th_c_005fwhen_005f3.setTest("${configProps.nextpoll != 0}");
/* 2704 */                             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2705 */                             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                               for (;;) {
/* 2707 */                                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2708 */                                 out.print(formatDT((String)configProps.get("nextpoll")));
/* 2709 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2710 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2711 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2715 */                             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2716 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                             }
/*      */                             
/* 2719 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2720 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2721 */                             if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/*      */                               return;
/* 2723 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 2724 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2725 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2729 */                         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2730 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                         }
/*      */                         
/* 2733 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2734 */                         out.write("\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td  width=\"40%\" valign=\"top\" align=\"right\">\n\t\t\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" height=\"30%\">\n\t\t\t\t\t\t\t\t\t<tr><td class=\"tableheadingbborder\" align=\"center\">");
/* 2735 */                         out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2736 */                         out.write("</td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"right\">\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2737 */                         out.print(operationId);
/* 2738 */                         out.write("&period=1&resourcename=");
/* 2739 */                         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2741 */                         out.write("')\">\n\t\t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2742 */                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2743 */                         out.write("\"></a>\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2744 */                         out.print(operationId);
/* 2745 */                         out.write("&period=2&resourcename=");
/* 2746 */                         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2748 */                         out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2749 */                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2750 */                         out.write("\"></a>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t");
/* 2751 */                         wlsGraph.setParam(operationId, "AVAILABILITY");
/* 2752 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t<td style=\"height:170px\" align=\"center\">\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2753 */                         if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2755 */                         out.write(32);
/* 2756 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\" height=\"36\">\n\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 2757 */                         out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 2758 */                         out.write(" :<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2759 */                         out.print(operationId);
/* 2760 */                         out.write("&attributeid=3504&alertconfigurl=");
/* 2761 */                         out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + operationId + "&attributeIDs=3504&attributeToSelect=3504&redirectto=" + encodeurl));
/* 2762 */                         out.write("')\"></span>\n\t\t\t\t\t\t\t\t\t\t                ");
/* 2763 */                         out.print(getSeverityImageForAvailability(alert.getProperty(operationId + "#" + "3504")));
/* 2764 */                         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"right\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2765 */                         out.print(operationId);
/* 2766 */                         out.write("&attributeIDs=3504,3505&attributeToSelect=3504&redirectto=");
/* 2767 */                         out.print(encodeurl);
/* 2768 */                         out.write("' class=\"links\"><span class=\"bodytext\">");
/* 2769 */                         out.print(ALERTCONFIG_TEXT);
/* 2770 */                         out.write("</span></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<tr><td><br /></td></tr>\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n    \t\t\t\t\t</tr>\n    \t\t\t\t\t</table>\n\t\t\t\t\t\t<br />\t\n\t\t\t\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" align=\"center\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"55%\" valign=\"top\">\n\t\t\t\t\t\t\t\t<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\">\n    \t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\" width=\"20%\" valign=\"top\">\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"bcactive\">");
/* 2771 */                         out.print(FormatUtil.getString("am.webclient.wsm.soapresponse.text"));
/* 2772 */                         out.write("</span>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t\t\t\t\t\t<textarea rows=\"19\" cols=\"85\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\" style=\"overflow-y: scroll;\" readonly> ");
/* 2773 */                         out.print(soapRes);
/* 2774 */                         out.write(" </textarea>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<td width=\"40%\" valign=\"top\">\n\t\t\t\t\t\t\t\t<table width=\"99%\" class=\"lrtbdarkborder\" cellspacing=\"1\" cellpadding=\"1\" border=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\t\n\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"  class=\"tableheadingbborder\">\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2775 */                         out.print(FormatUtil.getString("am.webclient.wsm.operationexecutiontime.text"));
/* 2776 */                         out.write(32);
/* 2777 */                         out.write(45);
/* 2778 */                         out.write(32);
/* 2779 */                         out.print(wsmGraph.getMethodName(operationId));
/* 2780 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"95%\" align=\"right\">\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2781 */                         out.print(operationId);
/* 2782 */                         out.write("&attributeid=3506&period=-7&resourcename=");
/* 2783 */                         out.print(name);
/* 2784 */                         out.write("')\">\n\t\t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2785 */                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2786 */                         out.write("\" /></a>\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2787 */                         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2789 */                         out.write("&attributeid=3506&period=2&resourcename=");
/* 2790 */                         out.print(name);
/* 2791 */                         out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2792 */                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2793 */                         out.write("\"></a>\n\t\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2794 */                         wsmGraph.setParameter(resid, operationId, "methodexecution");
/* 2795 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                         
/* 2797 */                         TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2798 */                         _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2799 */                         _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                         
/* 2801 */                         _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wsmGraph");
/*      */                         
/* 2803 */                         _jspx_th_awolf_005ftimechart_005f0.setWidth("450");
/*      */                         
/* 2805 */                         _jspx_th_awolf_005ftimechart_005f0.setHeight("200");
/*      */                         
/* 2807 */                         _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                         
/* 2809 */                         _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                         
/* 2811 */                         _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.wsm.executiontime.text"));
/* 2812 */                         int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2813 */                         if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2814 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2815 */                             out = _jspx_page_context.pushBody();
/* 2816 */                             _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2817 */                             _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2820 */                             out.write(32);
/* 2821 */                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2822 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2825 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2826 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2829 */                         if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2830 */                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                         }
/*      */                         
/* 2833 */                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2834 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td><br /><br /></td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t\t\t\t\t<table width=\"80%\" border=\"0\" class=\"lrbtborder\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"columnheadingnotop\">");
/* 2835 */                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2837 */                         out.write("</td> ");
/* 2838 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"columnheadingnotop\" align=\"center\">");
/* 2839 */                         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2841 */                         out.write("</td> ");
/* 2842 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"columnheadingnotop\" align=\"center\">");
/* 2843 */                         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2845 */                         out.write("</td> ");
/* 2846 */                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t<tr class=\"whitegrayborder\">\n\t\t\t\t\t\t\t\t\t\t\t\t<td>");
/* 2847 */                         out.print(FormatUtil.getString("am.webclient.common.current.text"));
/* 2848 */                         out.write(32);
/* 2849 */                         out.print(FormatUtil.getString("am.webclient.wsm.operationexecutiontime.text"));
/* 2850 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td>");
/* 2851 */                         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                           return;
/* 2853 */                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2854 */                         out.print(operationId);
/* 2855 */                         out.write("&attributeid=3505&alertconfigurl=");
/* 2856 */                         out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + operationId + "&attributeIDs=3504,3505,3506&attributeToSelect=3506&redirectto=" + encodeurl));
/* 2857 */                         out.write("')\">");
/* 2858 */                         out.print(getSeverityImage(alert.getProperty(operationId + "#" + "3506")));
/* 2859 */                         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"3\" align=\"right\" class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\"> <span class=\"bodytext\"><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2860 */                         out.print(operationId);
/* 2861 */                         out.write("&attributeIDs=3504,3505,3506,3507&attributeToSelect=3506&redirectto=");
/* 2862 */                         out.print(encodeurl);
/* 2863 */                         out.write("\" class=\"staticlinks\">");
/* 2864 */                         out.print(ALERTCONFIG_TEXT);
/* 2865 */                         out.write("</a></span>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t    </td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td><br /><br /></td></tr>\n\t\t\t\t\t\t\t\t</table> \n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<tr><td><br /><br /></td></tr>\n\n\t\t\t\t\t");
/*      */                         
/* 2867 */                         IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2868 */                         _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2869 */                         _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                         
/* 2871 */                         _jspx_th_c_005fif_005f4.setTest("${!empty xsltAttributes}");
/* 2872 */                         int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2873 */                         if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                           for (;;) {
/* 2875 */                             out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t\t<form id=\"xsltTable\" name=\"xsltAttrs\" method=\"post\" action=\"/wsm.do\">\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"method\" value=\"deleteoperations\" />\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"enable\" value=\"false\" />\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"operationId\" value=\"");
/* 2876 */                             out.print(operationId);
/* 2877 */                             out.write("\" />\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"resourceId\" value=\"");
/* 2878 */                             out.print(resid);
/* 2879 */                             out.write("\" />\n\t\t\t\t\t\t\t<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbtborder\">\n        \t\t\t\t\t\t<tbody><tr>\n        \t\t\t\t\t\t\t");
/* 2880 */                             if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                               return;
/* 2882 */                             out.write("\n        \t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2883 */                             out.print(FormatUtil.getString("Attribute"));
/* 2884 */                             out.write("</span></td>  ");
/* 2885 */                             out.write("\n          \t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2886 */                             out.print(FormatUtil.getString("Value"));
/* 2887 */                             out.write("</span></td>  ");
/* 2888 */                             out.write("\n          \t\t\t\t\t\t\t<td class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 2889 */                             out.print(FormatUtil.getString("Threshold"));
/* 2890 */                             out.write("</span></td> ");
/* 2891 */                             out.write("\n          \t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2892 */                             out.print(FormatUtil.getString("am.webclient.rule.action"));
/* 2893 */                             out.write("</span></td>  ");
/* 2894 */                             out.write("\n          \t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 2895 */                             out.print(FormatUtil.getString("am.webclient.sap.bg.history"));
/* 2896 */                             out.write("</span></td>  ");
/* 2897 */                             out.write("\n        \t\t\t\t\t\t</tr> \n\t\t\t\t\t\t\t\t");
/*      */                             
/* 2899 */                             ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2900 */                             _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2901 */                             _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f4);
/*      */                             
/* 2903 */                             _jspx_th_c_005fforEach_005f0.setItems("${xsltAttributes}");
/*      */                             
/* 2905 */                             _jspx_th_c_005fforEach_005f0.setVar("xsltAttr");
/* 2906 */                             int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                             try {
/* 2908 */                               int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2909 */                               if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                 for (;;) {
/* 2911 */                                   out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2912 */                                   if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 2914 */                                   out.write("\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t");
/* 2915 */                                   if (_jspx_meth_logic_005fpresent_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
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
/*      */ 
/*      */ 
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 2917 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t<td width=\"47%\" height=\"25\" class=\"yellowgrayborderbr\">");
/* 2918 */                                   if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
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
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 2920 */                                   out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"25%\" height=\"25\" class=\"yellowgrayborderbr\">");
/* 2921 */                                   if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                   {
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
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 2923 */                                   out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td height=\"25\" class=\"yellowgrayborderbr\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2924 */                                   out.print(operationId);
/* 2925 */                                   out.write("&attributeid=");
/* 2926 */                                   out.print(pageContext.getAttribute("attrId").toString());
/* 2927 */                                   out.write("')\">");
/* 2928 */                                   out.print(getSeverityImage(alert.getProperty(operationId + "#" + pageContext.getAttribute("attrId").toString())));
/* 2929 */                                   out.write("</a></td>\n\t\t\t\t\t\t\t\t\t\t<td height=\"25\" class=\"yellowgrayborderbr\" align=\"center\">\n        \t  \t\t\t\t\t\t\t\t");
/*      */                                   
/* 2931 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2932 */                                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2933 */                                   _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                   
/* 2935 */                                   _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN");
/* 2936 */                                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2937 */                                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                     for (;;) {
/* 2939 */                                       out.write("\n        \t  \t\t\t\t\t\t\t\t<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2940 */                                       out.print(operationId);
/* 2941 */                                       out.write("&attributeIDs=");
/* 2942 */                                       out.print(pageContext.getAttribute("attrId").toString());
/* 2943 */                                       out.write("&attributeToSelect=");
/* 2944 */                                       out.print(pageContext.getAttribute("attrId").toString());
/* 2945 */                                       out.write("&redirectto=");
/* 2946 */                                       out.print(encodeurl);
/* 2947 */                                       out.write("\" class=\"staticlinks\"><img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2948 */                                       out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2949 */                                       out.write("\" border=\"0\" /></a>\n        \t  \t\t\t\t\t\t\t\t");
/* 2950 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2951 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2955 */                                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2956 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
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
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 2959 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2960 */                                   out.write("\n        \t  \t\t\t\t\t\t\t</td>\n        \t  \t\t\t\t\t\t\t<td height=\"30\" class=\"yellowgrayborderbr\" align=\"center\">\n        \t  \t\t\t\t\t\t\t\t");
/*      */                                   
/* 2962 */                                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2963 */                                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2964 */                                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fforEach_005f0);
/* 2965 */                                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2966 */                                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                     for (;;) {
/* 2968 */                                       out.write("\n        \t  \t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 2970 */                                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2971 */                                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2972 */                                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                       
/* 2974 */                                       _jspx_th_c_005fwhen_005f5.setTest("${xsltAttr.value.Reports==\"1\"}");
/* 2975 */                                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2976 */                                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                         for (;;) {
/* 2978 */                                           out.write("\n        \t  \t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2979 */                                           if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
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
/* 3066 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 2981 */                                           out.write("&attributeid=");
/* 2982 */                                           if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
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
/* 3066 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 2984 */                                           out.write("&period=-7&resourcename=");
/* 2985 */                                           out.print(name);
/* 2986 */                                           out.write("')\">\n        \t  \t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"15\" hspace=\"5\" vspace=\"2\" border=\"0\"  title=\"");
/* 2987 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2988 */                                           out.write("\"/>\n        \t  \t\t\t\t\t\t\t\t\t\t</a>\n        \t  \t\t\t\t\t\t\t\t\t");
/* 2989 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2990 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2994 */                                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2995 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
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
/* 3066 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 2998 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2999 */                                       out.write("\n        \t  \t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 3001 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3002 */                                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3003 */                                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 3004 */                                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3005 */                                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                         for (;;) {
/* 3007 */                                           out.write("\n        \t  \t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 3009 */                                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3010 */                                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3011 */                                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                                           
/* 3013 */                                           _jspx_th_c_005fif_005f5.setTest("${xsltAttr.value.Type==\"0\"}");
/* 3014 */                                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3015 */                                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                             for (;;) {
/* 3017 */                                               out.write("\n\t        \t  \t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7days_disabled.gif\" width=\"24\" height=\"15\" hspace=\"5\" vspace=\"2\" border=\"0\"  title=\"");
/* 3018 */                                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text.disabled"));
/* 3019 */                                               out.write("\"/>\n\t        \t  \t\t\t\t\t\t\t\t\t");
/* 3020 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3021 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3025 */                                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3026 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
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
/* 3066 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3029 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3030 */                                           out.write("\n\t        \t  \t\t\t\t\t\t\t\t");
/* 3031 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3032 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3036 */                                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3037 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
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
/* 3066 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/* 3040 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3041 */                                       out.write("\n        \t  \t\t\t\t\t\t\t\t");
/* 3042 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3043 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3047 */                                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3048 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
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
/* 3066 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                   }
/* 3051 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3052 */                                   out.write("\n        \t  \t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/* 3053 */                                   int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3054 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3058 */                               if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3066 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                               }
/*      */                             }
/*      */                             catch (Throwable _jspx_exception)
/*      */                             {
/*      */                               for (;;)
/*      */                               {
/* 3062 */                                 int tmp6136_6135 = 0; int[] tmp6136_6133 = _jspx_push_body_count_c_005fforEach_005f0; int tmp6138_6137 = tmp6136_6133[tmp6136_6135];tmp6136_6133[tmp6136_6135] = (tmp6138_6137 - 1); if (tmp6138_6137 <= 0) break;
/* 3063 */                                 out = _jspx_page_context.popBody(); }
/* 3064 */                               _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                             } finally {
/* 3066 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3067 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                             }
/* 3069 */                             out.write("\n\t\t\t\t\t\t\t\t");
/*      */                             
/* 3071 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3072 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3073 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f4);
/*      */                             
/* 3075 */                             _jspx_th_logic_005fnotPresent_005f1.setRole("ENTERPRISEADMIN");
/* 3076 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3077 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 3079 */                                 out.write("\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td align=\"left\" class=\"tablebottom\" colspan=\"6\"><a class=\"staticlinks\" href=\"javascript:enablereports()\">");
/* 3080 */                                 out.print(FormatUtil.getString("am.webclient.wsm.enablereports.text"));
/* 3081 */                                 out.write("</a>&nbsp;<span style=\"color:#000; height:12px;\">&iota;</span>&nbsp;<a class=\"staticlinks\" href=\"javascript:disablereports()\">");
/* 3082 */                                 out.print(FormatUtil.getString("am.webclient.wsm.disablereports.text"));
/* 3083 */                                 out.write("</a>&nbsp;</td>\t");
/* 3084 */                                 out.write("\t\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/* 3085 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3086 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3090 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3091 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 3094 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3095 */                             out.write("\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr><td><br /><br /></td></tr>\n\t\t\t\t\t\t");
/* 3096 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3097 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3101 */                         if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3102 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                         }
/*      */                         
/* 3105 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3106 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/* 3108 */                         ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 3109 */                         _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3110 */                         _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                         
/* 3112 */                         _jspx_th_c_005fforEach_005f1.setItems("${graphIds}");
/*      */                         
/* 3114 */                         _jspx_th_c_005fforEach_005f1.setVar("graphAttr");
/* 3115 */                         int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                         try {
/* 3117 */                           int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3118 */                           if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                             for (;;) {
/* 3120 */                               out.write("\n\t\t\t\t\t\t\t");
/* 3121 */                               if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                               {
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
/* 3165 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3166 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/* 3123 */                               out.write("\n\t\t\t\t\t\t\t");
/* 3124 */                               wsmGraph.setParameter(pageContext.getAttribute("graphAttr").toString(), operationId, "xsltAttrGraph");
/* 3125 */                               out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"center\" colspan=\"2\">\n\t\t\t\t\t\t\t\t\t<table class=\"lrtbdarkborder\" width=\"100%\" cellspacing=\"0\" align=\"center\">\n\t\t\t\t\t\t\t\t\t\t<tr>\t\n\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\"  class=\"tableheadingbborder\">\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3126 */                               out.print(FormatUtil.getString(pageContext.getAttribute("attrName").toString()));
/* 3127 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               
/* 3129 */                               TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.get(TimeChart.class);
/* 3130 */                               _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3131 */                               _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                               
/* 3133 */                               _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wsmGraph");
/*      */                               
/* 3135 */                               _jspx_th_awolf_005ftimechart_005f1.setWidth("550");
/*      */                               
/* 3137 */                               _jspx_th_awolf_005ftimechart_005f1.setHeight("175");
/*      */                               
/* 3139 */                               _jspx_th_awolf_005ftimechart_005f1.setLegend("false");
/*      */                               
/* 3141 */                               _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                               
/* 3143 */                               _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString(pageContext.getAttribute("attrName").toString()));
/* 3144 */                               int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3145 */                               if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3146 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1);
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
/* 3165 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 3166 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                               }
/* 3149 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3150 */                               out.write(32);
/* 3151 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table> \n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr><td><br /></td></tr>\n\t\t\t\t\t\t");
/* 3152 */                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3153 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3157 */                           if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3165 */                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3166 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                           }
/*      */                         }
/*      */                         catch (Throwable _jspx_exception)
/*      */                         {
/*      */                           for (;;)
/*      */                           {
/* 3161 */                             int tmp6841_6840 = 0; int[] tmp6841_6838 = _jspx_push_body_count_c_005fforEach_005f1; int tmp6843_6842 = tmp6841_6838[tmp6841_6840];tmp6841_6838[tmp6841_6840] = (tmp6843_6842 - 1); if (tmp6843_6842 <= 0) break;
/* 3162 */                             out = _jspx_page_context.popBody(); }
/* 3163 */                           _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                         } finally {
/* 3165 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3166 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                         }
/* 3168 */                         out.write("\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t");
/* 3169 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3170 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3174 */                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3175 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                     }
/*      */                     
/* 3178 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3179 */                     out.write("\n\t\t\t\t\t\t");
/* 3180 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3181 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3185 */                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3186 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */                 }
/*      */                 else {
/* 3189 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3190 */                   out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t</body>\n</html>\n\n");
/*      */                 }
/* 3192 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3193 */         out = _jspx_out;
/* 3194 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3195 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3196 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3199 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3205 */     PageContext pageContext = _jspx_page_context;
/* 3206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3208 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3209 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3210 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3212 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3214 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3215 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3216 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3218 */       return true;
/*      */     }
/* 3220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3226 */     PageContext pageContext = _jspx_page_context;
/* 3227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3229 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3230 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3231 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3233 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3234 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3235 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3236 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3237 */       return true;
/*      */     }
/* 3239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3245 */     PageContext pageContext = _jspx_page_context;
/* 3246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3248 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3249 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3250 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3252 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3253 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3254 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3256 */       return true;
/*      */     }
/* 3258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3264 */     PageContext pageContext = _jspx_page_context;
/* 3265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3267 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3268 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3269 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3271 */     _jspx_th_c_005fout_005f3.setValue("${param.resId}");
/* 3272 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3273 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3274 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3275 */       return true;
/*      */     }
/* 3277 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3283 */     PageContext pageContext = _jspx_page_context;
/* 3284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3286 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3287 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3288 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 3289 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3290 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3292 */         out.write("\n\t\t\t\t");
/* 3293 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3294 */           return true;
/* 3295 */         out.write("\n\t\t\t\t");
/* 3296 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3297 */           return true;
/* 3298 */         out.write("\n\t\t\t");
/* 3299 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3300 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3304 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3305 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3306 */       return true;
/*      */     }
/* 3308 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3309 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3314 */     PageContext pageContext = _jspx_page_context;
/* 3315 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3317 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3318 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3319 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3321 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='request'}");
/* 3322 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3323 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3325 */         out.write("\n\t\t\t\t\tshowReq();\n\t\t\t\t");
/* 3326 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3331 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3332 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3333 */       return true;
/*      */     }
/* 3335 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3336 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3341 */     PageContext pageContext = _jspx_page_context;
/* 3342 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3344 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3345 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3346 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3347 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3348 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3350 */         out.write("\n\t\t\t\t\tshowRes();\n\t\t\t\t");
/* 3351 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3352 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3356 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3357 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3358 */       return true;
/*      */     }
/* 3360 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3366 */     PageContext pageContext = _jspx_page_context;
/* 3367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3369 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3370 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3371 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3373 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3374 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3375 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3377 */         out.write("\n\t\t alertUser();\n\t\treturn ;\n\t\t");
/* 3378 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3383 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3384 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3385 */       return true;
/*      */     }
/* 3387 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3393 */     PageContext pageContext = _jspx_page_context;
/* 3394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3396 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3397 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3398 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3400 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3401 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3402 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3404 */         out.write("\n\t\t alertUser();\n\t\treturn ;\n\t\t");
/* 3405 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3410 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3411 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3412 */       return true;
/*      */     }
/* 3414 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3420 */     PageContext pageContext = _jspx_page_context;
/* 3421 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3423 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3424 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3425 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3427 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3428 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3429 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3431 */         out.write("\n\t\t alertUser();\n\t\treturn ;\n\t\t");
/* 3432 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3433 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3437 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3438 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3439 */       return true;
/*      */     }
/* 3441 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3447 */     PageContext pageContext = _jspx_page_context;
/* 3448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3450 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3451 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3452 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 3454 */     _jspx_th_c_005fout_005f4.setValue("${xslt}");
/* 3455 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3456 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3457 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3458 */       return true;
/*      */     }
/* 3460 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3466 */     PageContext pageContext = _jspx_page_context;
/* 3467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3469 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3470 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3471 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3473 */     _jspx_th_c_005fout_005f5.setValue("${configProps.host}");
/* 3474 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3475 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3476 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3477 */       return true;
/*      */     }
/* 3479 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3480 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3485 */     PageContext pageContext = _jspx_page_context;
/* 3486 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3488 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3489 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3490 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3492 */     _jspx_th_c_005fout_005f6.setValue("${configProps.port}");
/* 3493 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3494 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3495 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3496 */       return true;
/*      */     }
/* 3498 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3504 */     PageContext pageContext = _jspx_page_context;
/* 3505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3507 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3508 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3509 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3511 */     _jspx_th_c_005fout_005f7.setValue("${configProps.serviceName}");
/* 3512 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3513 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3514 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3515 */       return true;
/*      */     }
/* 3517 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3518 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3523 */     PageContext pageContext = _jspx_page_context;
/* 3524 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3526 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3527 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3528 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3530 */     _jspx_th_c_005fout_005f8.setValue("${configProps.endpoint}");
/* 3531 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3532 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3533 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3534 */       return true;
/*      */     }
/* 3536 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3542 */     PageContext pageContext = _jspx_page_context;
/* 3543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3545 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3546 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3547 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3549 */     _jspx_th_c_005fout_005f9.setValue("${configProps.wsdlpath}");
/* 3550 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3551 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3552 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3553 */       return true;
/*      */     }
/* 3555 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3561 */     PageContext pageContext = _jspx_page_context;
/* 3562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3564 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3565 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3566 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3567 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3568 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3570 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3571 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3572 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3576 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3577 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3578 */       return true;
/*      */     }
/* 3580 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3586 */     PageContext pageContext = _jspx_page_context;
/* 3587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3589 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3590 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3591 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3592 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3593 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3595 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3596 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3597 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3601 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3602 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3603 */       return true;
/*      */     }
/* 3605 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3611 */     PageContext pageContext = _jspx_page_context;
/* 3612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3614 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3615 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3616 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3618 */     _jspx_th_c_005fout_005f10.setValue("${param.operationName}");
/* 3619 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3620 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3621 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3622 */       return true;
/*      */     }
/* 3624 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3625 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3630 */     PageContext pageContext = _jspx_page_context;
/* 3631 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3633 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3634 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3635 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3637 */     _jspx_th_c_005fout_005f11.setValue("${param.operationName}");
/* 3638 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3639 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3640 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3641 */       return true;
/*      */     }
/* 3643 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3649 */     PageContext pageContext = _jspx_page_context;
/* 3650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3652 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3653 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3654 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3656 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 3658 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */     
/* 3660 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */     
/* 3662 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 3664 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 3666 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 3668 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3669 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3670 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3671 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3672 */         out = _jspx_page_context.pushBody();
/* 3673 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3674 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3677 */         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3678 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 3679 */           return true;
/* 3680 */         out.write(32);
/* 3681 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 3682 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3683 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3686 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3687 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3690 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3691 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3692 */       return true;
/*      */     }
/* 3694 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3695 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3700 */     PageContext pageContext = _jspx_page_context;
/* 3701 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3703 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3704 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3705 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 3707 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3708 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3709 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3710 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3711 */         out = _jspx_page_context.pushBody();
/* 3712 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3713 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3716 */         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3717 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3718 */           return true;
/* 3719 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 3720 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3721 */           return true;
/* 3722 */         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t");
/* 3723 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3724 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3727 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3728 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3731 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3732 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3733 */       return true;
/*      */     }
/* 3735 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3741 */     PageContext pageContext = _jspx_page_context;
/* 3742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3744 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3745 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3746 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3748 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 3750 */     _jspx_th_awolf_005fparam_005f0.setValue("#FF0000");
/* 3751 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3752 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3753 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3754 */       return true;
/*      */     }
/* 3756 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3762 */     PageContext pageContext = _jspx_page_context;
/* 3763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3765 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3766 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3767 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3769 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 3771 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 3772 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3773 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3774 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3775 */       return true;
/*      */     }
/* 3777 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3783 */     PageContext pageContext = _jspx_page_context;
/* 3784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3786 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3787 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3788 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3790 */     _jspx_th_c_005fout_005f12.setValue("${param.operationId}");
/* 3791 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3792 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3793 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3794 */       return true;
/*      */     }
/* 3796 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3797 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3802 */     PageContext pageContext = _jspx_page_context;
/* 3803 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3805 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3806 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3807 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/* 3808 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3809 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3810 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3811 */         out = _jspx_page_context.pushBody();
/* 3812 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3813 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3816 */         out.write("table.heading.attribute");
/* 3817 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3818 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3821 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3822 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3825 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3826 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3827 */       return true;
/*      */     }
/* 3829 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3835 */     PageContext pageContext = _jspx_page_context;
/* 3836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3838 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3839 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3840 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/* 3841 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3842 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3843 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3844 */         out = _jspx_page_context.pushBody();
/* 3845 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3846 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3849 */         out.write("table.heading.value");
/* 3850 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3851 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3854 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3855 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3858 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3859 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3860 */       return true;
/*      */     }
/* 3862 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3868 */     PageContext pageContext = _jspx_page_context;
/* 3869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3871 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3872 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3873 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/* 3874 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3875 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3876 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3877 */         out = _jspx_page_context.pushBody();
/* 3878 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3879 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3882 */         out.write("table.heading.status");
/* 3883 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3887 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3888 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3891 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3892 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3893 */       return true;
/*      */     }
/* 3895 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3901 */     PageContext pageContext = _jspx_page_context;
/* 3902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3904 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3905 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3906 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3908 */     _jspx_th_c_005fout_005f13.setValue("${param.executiontime}");
/* 3909 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3910 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3912 */       return true;
/*      */     }
/* 3914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3920 */     PageContext pageContext = _jspx_page_context;
/* 3921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3923 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3924 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3925 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3927 */     _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,DEMO");
/* 3928 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3929 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 3931 */         out.write("\n        \t\t\t\t\t\t\t<td class=\"columnheadingnotop\" width=\"5%\" align=\"center\"><input type=\"checkbox\" onclick=\"ToggleAll(this,document.xsltAttrs,'xslt')\"></td>\n        \t\t\t\t\t\t\t");
/* 3932 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3937 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3938 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3939 */       return true;
/*      */     }
/* 3941 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3947 */     PageContext pageContext = _jspx_page_context;
/* 3948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3950 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3951 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3952 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3954 */     _jspx_th_c_005fset_005f0.setVar("attrId");
/*      */     
/* 3956 */     _jspx_th_c_005fset_005f0.setValue("${xsltAttr.key}");
/* 3957 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3958 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3959 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3960 */       return true;
/*      */     }
/* 3962 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3968 */     PageContext pageContext = _jspx_page_context;
/* 3969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3971 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3972 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3973 */     _jspx_th_logic_005fpresent_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3975 */     _jspx_th_logic_005fpresent_005f5.setRole("ADMIN,DEMO");
/* 3976 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3977 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 3979 */         out.write("\n\t\t\t\t\t\t\t\t\t\t<td height=\"25\" width=\"5%\" align=\"center\" class=\"yellowgrayborderbr\">\n\t\t\t\t\t\t\t\t\t\t");
/* 3980 */         if (_jspx_meth_c_005fchoose_005f4(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3981 */           return true;
/* 3982 */         out.write("\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t");
/* 3983 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3988 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3989 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3990 */       return true;
/*      */     }
/* 3992 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3998 */     PageContext pageContext = _jspx_page_context;
/* 3999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4001 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4002 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 4003 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/* 4004 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 4005 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 4007 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 4008 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4009 */           return true;
/* 4010 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 4011 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4012 */           return true;
/* 4013 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 4014 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 4015 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4019 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 4020 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4021 */       return true;
/*      */     }
/* 4023 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 4024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4029 */     PageContext pageContext = _jspx_page_context;
/* 4030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4032 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4033 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 4034 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 4036 */     _jspx_th_c_005fwhen_005f4.setTest("${xsltAttr.value.Type==\"0\"}");
/* 4037 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 4038 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 4040 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"xslt\" value=\"");
/* 4041 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4042 */           return true;
/* 4043 */         out.write("\"/>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 4044 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 4045 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4049 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 4050 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4051 */       return true;
/*      */     }
/* 4053 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 4054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4059 */     PageContext pageContext = _jspx_page_context;
/* 4060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4062 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4063 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4064 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 4066 */     _jspx_th_c_005fout_005f14.setValue("${attrId}");
/* 4067 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4068 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4069 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4070 */       return true;
/*      */     }
/* 4072 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4073 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4078 */     PageContext pageContext = _jspx_page_context;
/* 4079 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4081 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4082 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 4083 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 4084 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 4085 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 4087 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"xslt\" value=\"");
/* 4088 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4089 */           return true;
/* 4090 */         out.write("\" disabled/>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 4091 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 4092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4096 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 4097 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4098 */       return true;
/*      */     }
/* 4100 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 4101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4106 */     PageContext pageContext = _jspx_page_context;
/* 4107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4109 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4110 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4111 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 4113 */     _jspx_th_c_005fout_005f15.setValue("${attrId}");
/* 4114 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4115 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4116 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4117 */       return true;
/*      */     }
/* 4119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4125 */     PageContext pageContext = _jspx_page_context;
/* 4126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4128 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4129 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4130 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4132 */     _jspx_th_c_005fout_005f16.setValue("${xsltAttr.value.Name}");
/* 4133 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4134 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4135 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4136 */       return true;
/*      */     }
/* 4138 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4144 */     PageContext pageContext = _jspx_page_context;
/* 4145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4147 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4148 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4149 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4151 */     _jspx_th_c_005fout_005f17.setValue("${xsltAttr.value.Value}");
/* 4152 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4153 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4154 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4155 */       return true;
/*      */     }
/* 4157 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4163 */     PageContext pageContext = _jspx_page_context;
/* 4164 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4166 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4167 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4168 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4170 */     _jspx_th_c_005fout_005f18.setValue("${param.operationId}");
/* 4171 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4172 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4173 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4174 */       return true;
/*      */     }
/* 4176 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4182 */     PageContext pageContext = _jspx_page_context;
/* 4183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4185 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4186 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4187 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 4189 */     _jspx_th_c_005fout_005f19.setValue("${attrId}");
/* 4190 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4191 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4192 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4193 */       return true;
/*      */     }
/* 4195 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4201 */     PageContext pageContext = _jspx_page_context;
/* 4202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4204 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4205 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4206 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4208 */     _jspx_th_c_005fset_005f1.setValue("${xsltAttributes[graphAttr].Name}");
/*      */     
/* 4210 */     _jspx_th_c_005fset_005f1.setVar("attrName");
/* 4211 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4212 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4213 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4214 */       return true;
/*      */     }
/* 4216 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4217 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SOAPDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */