/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class IISWebsiteStats_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2176 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2183 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2205 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2209 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2224 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2228 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2233 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2235 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2236 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2237 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2238 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2239 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2248 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2251 */     JspWriter out = null;
/* 2252 */     Object page = this;
/* 2253 */     JspWriter _jspx_out = null;
/* 2254 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2258 */       response.setContentType("text/html;charset=UTF-8");
/* 2259 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2261 */       _jspx_page_context = pageContext;
/* 2262 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2263 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2264 */       session = pageContext.getSession();
/* 2265 */       out = pageContext.getOut();
/* 2266 */       _jspx_out = out;
/*      */       
/* 2268 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2270 */       request.setAttribute("HelpKey", "Monitors Service Details");
/*      */       
/* 2272 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2273 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2275 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2286 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2287 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2288 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2291 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2292 */         String available = null;
/* 2293 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2294 */         out.write(10);
/*      */         
/* 2296 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2307 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2308 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2309 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2312 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2313 */           String unavailable = null;
/* 2314 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2315 */           out.write(10);
/*      */           
/* 2317 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2328 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2329 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2330 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2333 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2334 */             String unmanaged = null;
/* 2335 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2336 */             out.write(10);
/*      */             
/* 2338 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2349 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2350 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2351 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2354 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2355 */               String scheduled = null;
/* 2356 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2357 */               out.write(10);
/*      */               
/* 2359 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2370 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2371 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2372 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2375 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2376 */                 String critical = null;
/* 2377 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2378 */                 out.write(10);
/*      */                 
/* 2380 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2391 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2392 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2393 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2396 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2397 */                   String clear = null;
/* 2398 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2399 */                   out.write(10);
/*      */                   
/* 2401 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2412 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2413 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2414 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2417 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2418 */                     String warning = null;
/* 2419 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2420 */                     out.write(10);
/* 2421 */                     out.write(10);
/*      */                     
/* 2423 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2424 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/* 2430 */                     out.write(10);
/* 2431 */                     GetWLSGraph wlsGraph = null;
/* 2432 */                     wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2433 */                     if (wlsGraph == null) {
/* 2434 */                       wlsGraph = new GetWLSGraph();
/* 2435 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2437 */                     out.write(10);
/* 2438 */                     Hashtable motypedisplaynames = null;
/* 2439 */                     synchronized (application) {
/* 2440 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2441 */                       if (motypedisplaynames == null) {
/* 2442 */                         motypedisplaynames = new Hashtable();
/* 2443 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2446 */                     out.write(10);
/*      */                     
/* 2448 */                     String haid = request.getParameter("haid");
/* 2449 */                     String resourceName = request.getParameter("resourcename");
/* 2450 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type")) + " Details";
/* 2451 */                     String websiteid = (String)request.getAttribute("websiteid");
/* 2452 */                     String websitename1 = (String)request.getAttribute("websitename");
/*      */                     
/* 2454 */                     String params = "";
/* 2455 */                     String paramsforwebsite = "";
/* 2456 */                     Enumeration enuma1 = request.getParameterNames();
/* 2457 */                     ArrayList attribIDs = new ArrayList();
/* 2458 */                     ArrayList resIDs = new ArrayList();
/* 2459 */                     String resID = request.getParameter("resourceid");
/* 2460 */                     if (websiteid != null)
/*      */                     {
/* 2462 */                       resIDs.add(websiteid);
/*      */                     }
/* 2464 */                     attribIDs.add("2003");
/* 2465 */                     attribIDs.add("2004");
/* 2466 */                     attribIDs.add("2005");
/* 2467 */                     attribIDs.add("2006");
/* 2468 */                     attribIDs.add("2007");
/* 2469 */                     attribIDs.add("2008");
/* 2470 */                     attribIDs.add("2009");
/* 2471 */                     attribIDs.add("2010");
/* 2472 */                     attribIDs.add("2011");
/* 2473 */                     attribIDs.add("2012");
/* 2474 */                     attribIDs.add("2013");
/* 2475 */                     attribIDs.add("2014");
/* 2476 */                     attribIDs.add("2015");
/* 2477 */                     Properties alert = new Properties();
/* 2478 */                     if (websiteid != null)
/*      */                     {
/* 2480 */                       alert = getStatus(resIDs, attribIDs);
/*      */                     }
/* 2482 */                     while (enuma1.hasMoreElements())
/*      */                     {
/* 2484 */                       String paramName = (String)enuma1.nextElement();
/* 2485 */                       if ((!paramName.equals("redirectto")) && (!paramName.equals("websiteidtoDelete")))
/*      */                       {
/*      */ 
/*      */ 
/* 2489 */                         if ((paramName.equals("websiteid")) || (paramName.equals("websitename")))
/*      */                         {
/* 2491 */                           paramsforwebsite = paramsforwebsite + "&";
/* 2492 */                           paramsforwebsite = paramsforwebsite + paramName + "=" + request.getParameter(paramName);
/*      */                         }
/*      */                         else {
/* 2495 */                           if (params.length() != 0)
/*      */                           {
/* 2497 */                             params = params + "&";
/* 2498 */                             paramsforwebsite = paramsforwebsite + "&";
/*      */                           }
/*      */                           
/* 2501 */                           params = params + paramName + "=" + request.getParameter(paramName);
/* 2502 */                           paramsforwebsite = paramsforwebsite + paramName + "=" + request.getParameter(paramName);
/*      */                         } } }
/* 2504 */                     String resourcetype = "IIS-Website";
/* 2505 */                     String IISmonitorpage = "/showresource.do?" + params;
/* 2506 */                     request.setAttribute("IISmonitorpage", IISmonitorpage);
/* 2507 */                     String IISWebsitename = "/showresource.do?" + paramsforwebsite;
/*      */                     
/* 2509 */                     out.write("\n<script>\nfunction deleteApplicationPool(haid)\n{\n\tvar url = \"");
/* 2510 */                     out.print(IISmonitorpage);
/* 2511 */                     out.write("&websiteidtoDelete=\"+haid;\n\tif(confirm(\"");
/* 2512 */                     out.print(FormatUtil.getString("am.webclient.appPool.delete.text"));
/* 2513 */                     out.write("\"))\n\t{\n\t\tdocument.location.href=url;\n\t}\n}\nfunction deletewebsite(url)\n{\nif(confirm(\"");
/* 2514 */                     out.print(FormatUtil.getString("am.webclient.iis.delete.website.text"));
/* 2515 */                     out.write("\"))\n{\ndocument.location.href=url;\n}\n}\n</script>\n");
/*      */                     
/* 2517 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2518 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2519 */                     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2520 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2521 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/* 2523 */                         out.write(10);
/*      */                         
/* 2525 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2526 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2527 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/* 2529 */                         _jspx_th_c_005fwhen_005f0.setTest("${websiteid != null}");
/* 2530 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2531 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/* 2533 */                             out.write(10);
/*      */                             
/* 2535 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2536 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2537 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2539 */                             _jspx_th_c_005fif_005f0.setTest("${DotNetAgentAvailable == 'true'}");
/* 2540 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2541 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2543 */                                 out.write(10);
/*      */                                 
/* 2545 */                                 request.setAttribute("hideLeftArea", "true");
/*      */                                 
/* 2547 */                                 out.write(10);
/* 2548 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2549 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2553 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2554 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2557 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2558 */                             out.write(10);
/*      */                             
/* 2560 */                             String bytessentpersec = "-";
/* 2561 */                             String bytesreceivedpersec = "-";
/* 2562 */                             String bytestransferedpersec = "-";
/* 2563 */                             String filessentpersec = "-";
/* 2564 */                             String filesreceivedpersec = "-";
/* 2565 */                             String filestransferredpersec = "-";
/* 2566 */                             String currentanonymoususers = "-";
/* 2567 */                             String anonymoususerspersec = "-";
/* 2568 */                             String currentnonanonymoususers = "-";
/* 2569 */                             String nonanonymoususerspersec = "-";
/* 2570 */                             String currentconnections = "-";
/* 2571 */                             String maxconnections = "-";
/* 2572 */                             boolean reportsenabled = false;
/* 2573 */                             AMConnectionPool pool = new AMConnectionPool();
/* 2574 */                             ResultSet rs1 = null;
/* 2575 */                             ResultSet rs2 = null;
/* 2576 */                             ResultSet rs3 = null;
/* 2577 */                             ResultSet rs4 = null;
/* 2578 */                             ResultSet rs5 = null;
/*      */                             try {
/* 2580 */                               String query1 = "SELECT max(COLLECTIONTIME) as collectiontime FROM AM_ManagedObjectData where RESID=" + resID;
/* 2581 */                               rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 2582 */                               if (rs1.next())
/*      */                               {
/* 2584 */                                 long col = rs1.getLong("collectiontime");
/* 2585 */                                 long onehourbefore = col - 3600000L;
/* 2586 */                                 String query2 = "select * from AM_IIS_TRAFFIC_DATA where RESOURCEID=" + websiteid + " and  AM_IIS_TRAFFIC_DATA.COLLECTIONTIME=" + col;
/* 2587 */                                 String query3 = "select * from AM_IIS_USERS_DATA where RESOURCEID=" + websiteid + " and AM_IIS_USERS_DATA.COLLECTIONTIME=" + col;
/* 2588 */                                 String query4 = "select max(CURRENTCONNECTIONS) as MAXCONNECTIONS from AM_IIS_USERS_DATA where RESOURCEID=" + websiteid + " and COLLECTIONTIME>=" + onehourbefore;
/* 2589 */                                 rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 2590 */                                 rs3 = AMConnectionPool.executeQueryStmt(query3);
/* 2591 */                                 rs4 = AMConnectionPool.executeQueryStmt(query4);
/* 2592 */                                 if (rs2.next())
/*      */                                 {
/* 2594 */                                   bytessentpersec = rs2.getString("BYTESSENTPERSEC").equals("-1") ? "-" : rs2.getString("BYTESSENTPERSEC");
/* 2595 */                                   bytesreceivedpersec = rs2.getString("BYTESRECEIVEDPERSEC").equals("-1") ? "-" : rs2.getString("BYTESRECEIVEDPERSEC");
/* 2596 */                                   bytestransferedpersec = rs2.getString("BYTESTOTALPERSEC").equals("-1") ? "-" : rs2.getString("BYTESTOTALPERSEC");
/* 2597 */                                   filessentpersec = rs2.getString("FILESSENTPERSEC").equals("-1") ? "-" : rs2.getString("FILESSENTPERSEC");
/* 2598 */                                   filesreceivedpersec = rs2.getString("FILESRECEIVEDPERSEC").equals("-1") ? "-" : rs2.getString("FILESRECEIVEDPERSEC");
/* 2599 */                                   filestransferredpersec = rs2.getString("FILESPERSEC").equals("-1") ? "-" : rs2.getString("FILESPERSEC");
/*      */                                 }
/*      */                                 
/* 2602 */                                 if (rs3.next())
/*      */                                 {
/* 2604 */                                   currentanonymoususers = rs3.getString("CURRENTANONYMOUSUSERS").equals("-1") ? "-" : rs3.getString("CURRENTANONYMOUSUSERS");
/* 2605 */                                   anonymoususerspersec = rs3.getString("ANONYMOUSUSERSPERSEC").equals("-1") ? "-" : rs3.getString("ANONYMOUSUSERSPERSEC");
/* 2606 */                                   currentnonanonymoususers = rs3.getString("CURRENTNONANONYMOUSUSERS").equals("-1") ? "-" : rs3.getString("CURRENTNONANONYMOUSUSERS");
/* 2607 */                                   nonanonymoususerspersec = rs3.getString("NONANONYMOUSUSERSPERSEC").equals("-1") ? "-" : rs3.getString("NONANONYMOUSUSERSPERSEC");
/* 2608 */                                   currentconnections = rs3.getString("CURRENTCONNECTIONS").equals("-1") ? "-" : rs3.getString("CURRENTCONNECTIONS");
/*      */                                 }
/*      */                                 
/* 2611 */                                 if (rs4.next())
/*      */                                 {
/* 2613 */                                   maxconnections = rs4.getString("MAXCONNECTIONS").equals("-1") ? "-" : rs4.getString("MAXCONNECTIONS");
/*      */                                 }
/*      */                               }
/* 2616 */                               rs5 = AMConnectionPool.executeQueryStmt("select TABLENAME from AM_ArchiverConfig where TABLENAME='AM_IIS_USERS_DATA'");
/* 2617 */                               if (rs5.next())
/*      */                               {
/* 2619 */                                 reportsenabled = true;
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                               try
/*      */                               {
/* 2629 */                                 if (rs1 != null) rs1.close();
/* 2630 */                                 if (rs2 != null) rs2.close();
/* 2631 */                                 if (rs3 != null) rs3.close();
/* 2632 */                                 if (rs4 != null) rs4.close();
/* 2633 */                                 if (rs5 != null) { rs5.close();
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception ex) {}
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/* 2641 */                               out.write(10);
/*      */                             }
/*      */                             catch (Exception ex)
/*      */                             {
/* 2624 */                               ex.printStackTrace();
/*      */                             }
/*      */                             finally
/*      */                             {
/*      */                               try {
/* 2629 */                                 if (rs1 != null) rs1.close();
/* 2630 */                                 if (rs2 != null) rs2.close();
/* 2631 */                                 if (rs3 != null) rs3.close();
/* 2632 */                                 if (rs4 != null) rs4.close();
/* 2633 */                                 if (rs5 != null) { rs5.close();
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception ex) {}
/*      */                             }
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2643 */                             InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2644 */                             _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2645 */                             _jspx_th_tiles_005finsert_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/* 2647 */                             _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2648 */                             int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2649 */                             if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                               for (;;) {
/* 2651 */                                 out.write(32);
/*      */                                 
/* 2653 */                                 PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2654 */                                 _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2655 */                                 _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                                 
/* 2657 */                                 _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                                 
/* 2659 */                                 _jspx_th_tiles_005fput_005f0.setValue(dispname);
/* 2660 */                                 int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2661 */                                 if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2662 */                                   this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                                 }
/*      */                                 
/* 2665 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2666 */                                 out.write(10);
/* 2667 */                                 if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2669 */                                 out.write(10);
/* 2670 */                                 if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2672 */                                 out.write(10);
/* 2673 */                                 out.write(10);
/* 2674 */                                 if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 2676 */                                 out.write(10);
/*      */                                 
/* 2678 */                                 PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2679 */                                 _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2680 */                                 _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                                 
/* 2682 */                                 _jspx_th_tiles_005fput_005f4.setName("UserArea");
/*      */                                 
/* 2684 */                                 _jspx_th_tiles_005fput_005f4.setType("string");
/* 2685 */                                 int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2686 */                                 if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2687 */                                   if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2688 */                                     out = _jspx_page_context.pushBody();
/* 2689 */                                     _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2690 */                                     _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2693 */                                     out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                                     
/* 2695 */                                     Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2696 */                                     String aid = request.getParameter("haid");
/* 2697 */                                     String haName = null;
/* 2698 */                                     if (aid != null)
/*      */                                     {
/* 2700 */                                       haName = (String)ht.get(aid);
/*      */                                     }
/*      */                                     
/* 2703 */                                     out.write(10);
/* 2704 */                                     out.write(9);
/*      */                                     
/* 2706 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2707 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2708 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 2710 */                                     _jspx_th_c_005fif_005f3.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2711 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2712 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/* 2714 */                                         out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2715 */                                         out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2716 */                                         out.write(" &gt; ");
/* 2717 */                                         out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2718 */                                         out.write(" &gt; <a href=\"");
/* 2719 */                                         out.print(IISmonitorpage);
/* 2720 */                                         out.write("\" class=>");
/* 2721 */                                         out.print(resourceName);
/* 2722 */                                         out.write(" </a> &gt; <span class=\"bcactive\"> <a href=\"");
/* 2723 */                                         out.print(IISWebsitename);
/* 2724 */                                         out.write("\" class=>");
/* 2725 */                                         out.print(websitename1);
/* 2726 */                                         out.write(" </span></td>\n\t");
/* 2727 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2728 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2732 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2733 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/* 2736 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2737 */                                     out.write(10);
/* 2738 */                                     out.write(9);
/*      */                                     
/* 2740 */                                     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2741 */                                     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2742 */                                     _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 2744 */                                     _jspx_th_c_005fif_005f4.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2745 */                                     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2746 */                                     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                       for (;;) {
/* 2748 */                                         out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2749 */                                         out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2750 */                                         out.write(" &gt; ");
/* 2751 */                                         out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(request.getParameter("type")));
/* 2752 */                                         out.write("  &gt; <a href=\"");
/* 2753 */                                         out.print(IISmonitorpage);
/* 2754 */                                         out.write("\" class=\"staticlinks\">");
/* 2755 */                                         out.print(resourceName);
/* 2756 */                                         out.write(" </a> &gt; <span class=\"bcactive\"> ");
/* 2757 */                                         out.print(websitename1);
/* 2758 */                                         out.write("</span></td>\n\t");
/* 2759 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2760 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2764 */                                     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2765 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                     }
/*      */                                     
/* 2768 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2769 */                                     out.write("\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n\n<div id=\"edit\" style=\"DISPLAY: none\">\n");
/* 2770 */                                     org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/ConfigureMailNWeb.jsp", out, false);
/* 2771 */                                     out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     <tr>\n       <td>&nbsp;</td>\n     </tr>\n   </table>\n\n</div>\n\n\n\n\n\n\n\n   <table border=\"0\" cellpadding=\"0\" cellspacing=\"2\" width=\"99%\">\n    <tr>\n     ");
/* 2772 */                                     wlsGraph.setParam(websiteid, "IIS_Connections");
/* 2773 */                                     out.write("\n     <td width=\"60%\" height=\"35\" >\n\n         <table class=\"lrtbdarkborder\" width=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n            <tr>\n\t           <td width=\"50%\" class=\"tableheading\" height=\"26\" colspan=\"2\">");
/* 2774 */                                     out.print(FormatUtil.getString("am.webclient.iis.connectionstats.text"));
/* 2775 */                                     out.write("</td>\n          </tr>\n            <tr>\n                  <td width=\"96%\" align=\"right\" height=\"25\">&nbsp;</td>\n           \t  <td width=\"4%\">&nbsp;</td>\n            </tr>\n            <tr>\n                  <td colspan=\"2\" class=\"bottomborder\"> ");
/*      */                                     
/* 2777 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 2778 */                                     _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2779 */                                     _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 2781 */                                     _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                     
/* 2783 */                                     _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                                     
/* 2785 */                                     _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                     
/* 2787 */                                     _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                     
/* 2789 */                                     _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 2791 */                                     _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.connections"));
/*      */                                     
/* 2793 */                                     _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 2794 */                                     int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2795 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2796 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2797 */                                         out = _jspx_page_context.pushBody();
/* 2798 */                                         _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2799 */                                         _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2802 */                                         out.write("\n                    ");
/* 2803 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2804 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2807 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2808 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2811 */                                     if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2812 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                     }
/*      */                                     
/* 2815 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2816 */                                     out.write("\n                  </td>\n            </tr>\n            <tr>\n                 <td colspan=\"2\">\n\t\t\t <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n\t\t\t    <tr>\n\t\t\t      <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2817 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 2819 */                                     out.write("</span></td>\n\t\t\t      <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2820 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 2822 */                                     out.write("</span></td>\n\t\t\t      <td class=\"columnheadingb\">&nbsp;</td>\n\t\t\t    </tr>\n\t\t\t    <tr>\n\t\t\t      <td width=\"48%\" class=\"whitegrayborder\">");
/* 2823 */                                     out.print(FormatUtil.getString("am.webclient.iis.currentconnections.text"));
/* 2824 */                                     out.write("</td>\n\t\t\t      <td width=\"24%\" class=\"whitegrayborder\">");
/* 2825 */                                     out.print(currentconnections);
/* 2826 */                                     out.write(32);
/* 2827 */                                     if (reportsenabled) {
/* 2828 */                                       out.write(" &nbsp;&nbsp;\n\t\t\t      <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2829 */                                       out.print(websiteid);
/* 2830 */                                       out.write("&attributeid=2009&period=-7')\">\n\t\t\t       <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"0\" border=\"0\"  title=\"");
/* 2831 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2832 */                                       out.write("\"></a>\n\t\t\t      ");
/*      */                                     }
/* 2834 */                                     out.write("</td>\n\t\t\t      <td width=\"28%\" class=\"whitegrayborder\">&nbsp; <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2835 */                                     out.print(websiteid);
/* 2836 */                                     out.write("&attributeid=2009')\">");
/* 2837 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2009")));
/* 2838 */                                     out.write(" </a></td>\n\t\t\t    </tr>\n\t\t\t    <tr>\n\t\t\t      <td width=\"48%\" class=\"yellowgrayborder\">");
/* 2839 */                                     out.print(FormatUtil.getString("am.webclient.iis.maxxonnections.text"));
/* 2840 */                                     out.write("</td>\n\t\t\t      <td width=\"24%\" class=\"yellowgrayborder\">");
/* 2841 */                                     out.print(maxconnections);
/* 2842 */                                     out.write("</td>\n\t\t\t      <td width=\"28%\" class=\"yellowgrayborder\"></td>\n\n\t\t\t    </tr>\n\t\t\t     <tr valign=\"top\"  align=\"right\">\n\t\t\t\t <td width=\"42%\" colspan=\"3\" class=\"whitegrayborder\" align=\"right\">&nbsp; <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2843 */                                     out.print(websiteid);
/* 2844 */                                     out.write("&attributeIDs=2009&attributeToSelect=2009&redirectto=");
/* 2845 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 2846 */                                     out.write("' class=\"staticlinks\">");
/* 2847 */                                     out.print(ALERTCONFIG_TEXT);
/* 2848 */                                     out.write("</a>&nbsp;&nbsp;&nbsp; </td>\n\t\t\t    </tr>\n\t\t\t  </table>\n                   </td>\n             </tr>\n          </table>\n       </td>\n       <td valign=\"top\">\n\t\t   <table  cellspacing=\"1\" width=\"100%\" cellpadding=\"0\" border=\"0\" class=\"lrtbdarkborder\">\n\n\t\t      <tr>\n\t\t             <td class=\"tableheading\" height=\"26\" colspan=\"3\">");
/* 2849 */                                     out.print(FormatUtil.getString("am.webclient.iis.availabilityof.text", new String[] { "\"" + websitename1 + "\"" }));
/* 2850 */                                     out.write("</td>\n                      </tr>\n\t\t       <tr>\n\t\t       <td></td>\n\t\t\t <td  width=\"4%\" align=\"right\" colspan=\"2\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2851 */                                     out.print(websiteid);
/* 2852 */                                     out.write("&period=1&resourcename=");
/* 2853 */                                     out.print(websitename1);
/* 2854 */                                     out.write("')\">\n\t\t\t   <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2855 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2856 */                                     out.write("\"></a>\n\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2857 */                                     out.print(websiteid);
/* 2858 */                                     out.write("&period=2&resourcename=");
/* 2859 */                                     out.print(websitename1);
/* 2860 */                                     out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2861 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2862 */                                     out.write("\"></a></td>\n\t\t       </tr>\n\t\t       ");
/*      */                                     
/* 2864 */                                     wlsGraph.setParam(websiteid, "AVAILABILITY");
/*      */                                     
/* 2866 */                                     out.write("\n\t\t       <tr align=\"center\">\n\t\t\t   <td height=\"28\" colspan=\"3\" class=\"whitegrayborder\"> ");
/* 2867 */                                     if (_jspx_meth_awolf_005fpiechart_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 2869 */                                     out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t <td width=\"39%\" height=\"25\" class=\"yellowgrayborder\"  title=\" ");
/* 2870 */                                     out.print(FormatUtil.getString("am.webclient.availabilityperf.curstatus.tooltip"));
/* 2871 */                                     out.write(34);
/* 2872 */                                     out.write(62);
/* 2873 */                                     out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 2874 */                                     out.write(" :</td>\n\t\t\t<td width=\"10%\" height=\"25\" align=\"left\" class=\"yellowgrayborder\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2875 */                                     out.print(websiteid);
/* 2876 */                                     out.write("&attributeid=2015&alertconfigurl=");
/* 2877 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + websiteid + "&attributeIDs=2014,2015&attributeToSelect=2015&redirectto=" + IISWebsitename));
/* 2878 */                                     out.write("')\">\n\t\t\t    ");
/* 2879 */                                     out.print(getSeverityImageForAvailability(alert.getProperty(websiteid + "#" + "2015")));
/* 2880 */                                     out.write("  </a>\n\t\t\t </td>\n\t\t\t<td width=\"51%\" align=\"right\" class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2881 */                                     out.print(websiteid);
/* 2882 */                                     out.write("&attributeIDs=2014,2015&attributeToSelect=2015&redirectto=");
/* 2883 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 2884 */                                     out.write("\" class=\"links\">");
/* 2885 */                                     out.print(ALERTCONFIG_TEXT);
/* 2886 */                                     out.write("</a>\n\n\t\t\t  </td>\n\t\t\t </tr>\n\n\t\t  </table>\n          </td>\n         </tr>\n         </table>\n\n\n\n\n\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     <tr>\n       <td>&nbsp;</td>\n     </tr>\n   </table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 2887 */                                     out.print(FormatUtil.getString("am.webclient.iis.bytestransferred.text"));
/* 2888 */                                     out.write("</td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 2889 */                                     out.print(FormatUtil.getString("am.webclient.iis.filestransefered.text"));
/* 2890 */                                     out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  ");
/*      */                                     
/* 2892 */                                     wlsGraph.setParam(websiteid, "IIS_BYTESSENTPERSECOND");
/*      */                                     
/* 2894 */                                     out.write("\n   <td width=\"50%\" height=\"35\" class=\"rbborder\">\n     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    \t<tr>\n           <td width=\"96%\" align=\"right\" height=\"25\">&nbsp;</td>\n    \t   <td width=\"4%\">&nbsp;</td>\n    \t</tr>\n    \t<tr>\n    \t<td colspan=\"2\"  class=\"bottomborder\">\n            ");
/*      */                                     
/* 2896 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 2897 */                                     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 2898 */                                     _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 2900 */                                     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("wlsGraph");
/*      */                                     
/* 2902 */                                     _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                     
/* 2904 */                                     _jspx_th_awolf_005ftimechart_005f1.setHeight("200");
/*      */                                     
/* 2906 */                                     _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                     
/* 2908 */                                     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 2910 */                                     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel("");
/*      */                                     
/* 2912 */                                     _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 2913 */                                     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 2914 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 2915 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 2916 */                                         out = _jspx_page_context.pushBody();
/* 2917 */                                         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 2918 */                                         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2921 */                                         out.write("\n            ");
/* 2922 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 2923 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2926 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 2927 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2930 */                                     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 2931 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                     }
/*      */                                     
/* 2934 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 2935 */                                     out.write("\n    \t</td>\n        </tr>\n        <tr>\n\t\t    <td valign=\"top\"  colspan=\"2\" > <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t        <tr>\n\t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2936 */                                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 2938 */                                     out.write("</span></td>\n\t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2939 */                                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 2941 */                                     out.write("</span></td>\n\t\t          <td class=\"columnheadingb\">&nbsp;</td>\n\t               </tr>\n\t               <tr>\n\t\t\t  <td width=\"48%\" class=\"whitegrayborder\">");
/* 2942 */                                     out.print(FormatUtil.getString("am.webclient.iis.bytessentpersec.text"));
/* 2943 */                                     out.write("</td>\n\t\t\t  <td width=\"24%\" class=\"whitegrayborder\">");
/* 2944 */                                     out.print(bytessentpersec);
/* 2945 */                                     out.write("</td>\n\t\t\t  <td width=\"28%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2946 */                                     out.print(websiteid);
/* 2947 */                                     out.write("&attributeid=2003')\">");
/* 2948 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2003")));
/* 2949 */                                     out.write(" </a> </td>\n                      </tr>\n                      <tr>\n\t\t\t  <td width=\"48%\" class=\"yellowgrayborder\">");
/* 2950 */                                     out.print(FormatUtil.getString("am.webclient.iis.bytesreceivedpersec.text"));
/* 2951 */                                     out.write("</td>\n\t\t\t  <td width=\"24%\" class=\"yellowgrayborder\">");
/* 2952 */                                     out.print(bytesreceivedpersec);
/* 2953 */                                     out.write("</td>\n\t\t\t  <td width=\"28%\" class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2954 */                                     out.print(websiteid);
/* 2955 */                                     out.write("&attributeid=2004')\">");
/* 2956 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2004")));
/* 2957 */                                     out.write(" </a>  </td>\n                      </tr>\n                      <tr>\n\t\t\t  <td width=\"48%\" class=\"whitegrayborder\">");
/* 2958 */                                     out.print(FormatUtil.getString("am.webclient.iis.bytestransferredpersec.text"));
/* 2959 */                                     out.write("</td>\n\t\t\t  <td width=\"24%\" class=\"whitegrayborder\">");
/* 2960 */                                     out.print(bytestransferedpersec);
/* 2961 */                                     out.write("</td>\n\t\t\t  <td width=\"28%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2962 */                                     out.print(websiteid);
/* 2963 */                                     out.write("&attributeid=2005')\">");
/* 2964 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2005")));
/* 2965 */                                     out.write(" </a>  </td>\n                      </tr>\n                      <tr>\n\t\t\t  <td width=\"48%\" class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t  <td width=\"28%\" class=\"yellowgrayborder\" colspan=\"2\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2966 */                                     out.print(websiteid);
/* 2967 */                                     out.write("&attributeIDs=2003,2004,2005&attributeToSelect=2003&redirectto=");
/* 2968 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 2969 */                                     out.write("' class=\"staticlinks\">");
/* 2970 */                                     out.print(ALERTCONFIG_TEXT);
/* 2971 */                                     out.write("</a></td>\n                      </tr>\n                      </table>\n\t             </td>\n\t        </tr>\n\n\n     </table>\n   </td>\n\n   ");
/*      */                                     
/* 2973 */                                     wlsGraph.setParam(websiteid, "IIS_FILESPERSECOND");
/*      */                                     
/* 2975 */                                     out.write("\n      <td width=\"50%\" height=\"35\" class=\"rbborder\">\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n       <tr>\n                 <td width=\"96%\" align=\"right\" height=\"25\">&nbsp;</td>\n          \t   <td width=\"4%\">&nbsp;</td>\n    \t</tr>\n       \t<tr>\n       \t<td colspan=\"2\"  class=\"bottomborder\">\n               ");
/*      */                                     
/* 2977 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 2978 */                                     _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 2979 */                                     _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 2981 */                                     _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("wlsGraph");
/*      */                                     
/* 2983 */                                     _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                     
/* 2985 */                                     _jspx_th_awolf_005ftimechart_005f2.setHeight("200");
/*      */                                     
/* 2987 */                                     _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                     
/* 2989 */                                     _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 2991 */                                     _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel("");
/*      */                                     
/* 2993 */                                     _jspx_th_awolf_005ftimechart_005f2.setDateFormat("HH:mm");
/* 2994 */                                     int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 2995 */                                     if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 2996 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 2997 */                                         out = _jspx_page_context.pushBody();
/* 2998 */                                         _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 2999 */                                         _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3002 */                                         out.write("\n               ");
/* 3003 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3004 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3007 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3008 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3011 */                                     if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3012 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                     }
/*      */                                     
/* 3015 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3016 */                                     out.write("\n       \t</td>\n           </tr>\n           <tr>\n   \t\t    <td valign=\"top\"  colspan=\"2\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n   \t\t        <tr>\n   \t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3017 */                                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3019 */                                     out.write("</span></td>\n   \t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3020 */                                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3022 */                                     out.write("</span></td>\n   \t\t          <td class=\"columnheadingb\">&nbsp;</td>\n   \t               </tr>\n   \t               <tr>\n   \t\t\t  <td width=\"48%\" class=\"whitegrayborder\">");
/* 3023 */                                     out.print(FormatUtil.getString("am.webclient.iis.filessentpersec.text"));
/* 3024 */                                     out.write("</td>\n   \t\t\t  <td width=\"24%\" class=\"whitegrayborder\">");
/* 3025 */                                     out.print(filessentpersec);
/* 3026 */                                     out.write("</td>\n   \t\t\t  <td width=\"28%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3027 */                                     out.print(websiteid);
/* 3028 */                                     out.write("&attributeid=2006')\">");
/* 3029 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2006")));
/* 3030 */                                     out.write(" </a>  </td>\n                         </tr>\n                         <tr>\n   \t\t\t  <td width=\"48%\" class=\"yellowgrayborder\">");
/* 3031 */                                     out.print(FormatUtil.getString("am.webclient.iis.filesreceivedpersec"));
/* 3032 */                                     out.write("</td>\n   \t\t\t  <td width=\"24%\" class=\"yellowgrayborder\">");
/* 3033 */                                     out.print(filesreceivedpersec);
/* 3034 */                                     out.write("</td>\n   \t\t\t  <td width=\"28%\" class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3035 */                                     out.print(websiteid);
/* 3036 */                                     out.write("&attributeid=2007')\">");
/* 3037 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2007")));
/* 3038 */                                     out.write(" </a>  </td>\n                         </tr>\n                         <tr>\n   \t\t\t  <td width=\"48%\" class=\"whitegrayborder\">");
/* 3039 */                                     out.print(FormatUtil.getString("am.webclient.iis.filestransferredpersec.text"));
/* 3040 */                                     out.write("</td>\n   \t\t\t  <td width=\"24%\" class=\"whitegrayborder\">");
/* 3041 */                                     out.print(filestransferredpersec);
/* 3042 */                                     out.write("</td>\n   \t\t\t  <td width=\"28%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3043 */                                     out.print(websiteid);
/* 3044 */                                     out.write("&attributeid=2008')\">");
/* 3045 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2008")));
/* 3046 */                                     out.write(" </a>  </td>\n                         </tr>\n                         <tr>\n   \t\t\t  <td width=\"38%\" class=\"yellowgrayborder\">&nbsp;</td>\n   \t\t\t  <td width=\"42%\" colspan=\"2\" class=\"yellowgrayborder\" align=\"right\">&nbsp;<img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3047 */                                     out.print(websiteid);
/* 3048 */                                     out.write("&attributeIDs=2006,2007,2008&attributeToSelect=2006&redirectto=");
/* 3049 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 3050 */                                     out.write("' class=\"staticlinks\">");
/* 3051 */                                     out.print(ALERTCONFIG_TEXT);
/* 3052 */                                     out.write("</a> </td>\n                         </tr>\n                         </table>\n   \t             </td>\n   \t        </tr>\n        </table>\n   </td>\n   </tr>\n   </table>\n   <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n     <tr>\n               <td width=\"72%\" height=\"31\" align=\"right\" class=\"tablebottom\"><a href=\"#top\" class=\"staticlinks\">");
/* 3053 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 3054 */                                     out.write("</a>&nbsp;&nbsp;</td>\n             </tr>\n           </table>\n\n   <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     <tr>\n       <td>&nbsp;</td>\n     </tr>\n   </table>\n\n\n <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheading\">");
/* 3055 */                                     out.print(FormatUtil.getString("am.webclient.iis.anonymoususers.text"));
/* 3056 */                                     out.write("</td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3057 */                                     out.print(FormatUtil.getString("am.webclient.iis.nonanonymoususers.text"));
/* 3058 */                                     out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n  ");
/*      */                                     
/* 3060 */                                     wlsGraph.setParam(websiteid, "IIS_AnonymousUsers");
/*      */                                     
/* 3062 */                                     out.write("\n   <td width=\"50%\" height=\"35\" class=\"rbborder\">\n     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    \t<tr>\n\t           <td width=\"96%\" align=\"right\" height=\"25\">&nbsp;</td>\n\t    \t   <td width=\"4%\">&nbsp;</td>\n    \t</tr>\n    \t<tr>\n    \t<td colspan=\"2\" class=\"bottomborder\">\n            ");
/*      */                                     
/* 3064 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3065 */                                     _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3066 */                                     _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 3068 */                                     _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("wlsGraph");
/*      */                                     
/* 3070 */                                     _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                                     
/* 3072 */                                     _jspx_th_awolf_005ftimechart_005f3.setHeight("200");
/*      */                                     
/* 3074 */                                     _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                     
/* 3076 */                                     _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3078 */                                     _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.iis.anonymoususerspersec.text"));
/*      */                                     
/* 3080 */                                     _jspx_th_awolf_005ftimechart_005f3.setDateFormat("HH:mm");
/* 3081 */                                     int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3082 */                                     if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3083 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3084 */                                         out = _jspx_page_context.pushBody();
/* 3085 */                                         _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3086 */                                         _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3089 */                                         out.write("\n            ");
/* 3090 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3091 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3094 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3095 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3098 */                                     if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3099 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                     }
/*      */                                     
/* 3102 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3103 */                                     out.write("\n    \t</td>\n        </tr>\n        <tr>\n\t\t    <td valign=\"top\"  colspan=\"2\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n\t\t        <tr>\n\t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3104 */                                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3106 */                                     out.write("</span></td>\n\t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3107 */                                     if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3109 */                                     out.write("</span></td>\n\t\t          <td class=\"columnheadingb\">&nbsp;</td>\n\t               </tr>\n\t               <tr>\n\t\t\t  <td width=\"48%\" class=\"whitegrayborder\">");
/* 3110 */                                     out.print(FormatUtil.getString("am.webclient.iis.currentanonymoususers.text"));
/* 3111 */                                     out.write("</td>\n\t\t\t  <td width=\"24%\" class=\"whitegrayborder\">");
/* 3112 */                                     out.print(currentanonymoususers);
/* 3113 */                                     if (reportsenabled) {
/* 3114 */                                       out.write(" &nbsp;&nbsp;\n\t\t\t      <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3115 */                                       out.print(websiteid);
/* 3116 */                                       out.write("&attributeid=2010&period=-7')\">\n\t\t\t       <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"0\" border=\"0\"  title=\"");
/* 3117 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3118 */                                       out.write("\"></a>\n\t\t\t      ");
/*      */                                     }
/* 3120 */                                     out.write("\n\t\t\t  <td width=\"28%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3121 */                                     out.print(websiteid);
/* 3122 */                                     out.write("&attributeid=2010')\">");
/* 3123 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2010")));
/* 3124 */                                     out.write(" </a>   </td>\n                      </tr>\n                      <tr>\n\t\t\t  <td width=\"48%\" class=\"yellowgrayborder\">");
/* 3125 */                                     out.print(FormatUtil.getString("am.webclient.iis.anonymoususerspersec.text"));
/* 3126 */                                     out.write("</td>\n\t\t\t  <td width=\"24%\" class=\"yellowgrayborder\">");
/* 3127 */                                     out.print(anonymoususerspersec);
/* 3128 */                                     if (reportsenabled) {
/* 3129 */                                       out.write(" &nbsp;&nbsp;\n\t\t\t      <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3130 */                                       out.print(websiteid);
/* 3131 */                                       out.write("&attributeid=2011&period=-7')\">\n\t\t\t       <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"0\" border=\"0\"  title=\"");
/* 3132 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3133 */                                       out.write("\"></a>\n\t\t\t      ");
/*      */                                     }
/* 3135 */                                     out.write("\n\t\t\t  <td width=\"28%\" class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3136 */                                     out.print(websiteid);
/* 3137 */                                     out.write("&attributeid=2011')\">");
/* 3138 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2011")));
/* 3139 */                                     out.write(" </a>   </td>\n                      </tr>\n                      <tr>\n\t\t\t  <td width=\"48%\" class=\"whitegrayborder\">&nbsp;</td>\n\t\t\t <td width=\"42%\" colspan=\"2\" class=\"whitegrayborder\" align=\"right\">&nbsp; <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3140 */                                     out.print(websiteid);
/* 3141 */                                     out.write("&attributeIDs=2010,2011&attributeToSelect=2010&redirectto=");
/* 3142 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 3143 */                                     out.write("' class=\"staticlinks\">");
/* 3144 */                                     out.print(ALERTCONFIG_TEXT);
/* 3145 */                                     out.write("</a> </td>\n                      </tr>\n                      </table>\n\t             </td>\n\t        </tr>\n\n\n     </table>\n   </td>\n\n      ");
/*      */                                     
/* 3147 */                                     wlsGraph.setParam(websiteid, "IIS_NonAnonymousUsers");
/*      */                                     
/* 3149 */                                     out.write("\n      <td width=\"50%\" height=\"35\" class=\"rbborder\">\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n       <tr>\n                  <td width=\"96%\" align=\"right\" height=\"25\">&nbsp;</td>\n           \t   <td width=\"4%\">&nbsp;</td>\n    \t</tr>\n       \t<tr>\n       \t<td colspan=\"2\" class=\"bottomborder\">\n               ");
/*      */                                     
/* 3151 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3152 */                                     _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3153 */                                     _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                                     
/* 3155 */                                     _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("wlsGraph");
/*      */                                     
/* 3157 */                                     _jspx_th_awolf_005ftimechart_005f4.setWidth("300");
/*      */                                     
/* 3159 */                                     _jspx_th_awolf_005ftimechart_005f4.setHeight("200");
/*      */                                     
/* 3161 */                                     _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                     
/* 3163 */                                     _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3165 */                                     _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.iis.nonanonymoususerspersec.text"));
/*      */                                     
/* 3167 */                                     _jspx_th_awolf_005ftimechart_005f4.setDateFormat("HH:mm");
/* 3168 */                                     int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3169 */                                     if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 3170 */                                       if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3171 */                                         out = _jspx_page_context.pushBody();
/* 3172 */                                         _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 3173 */                                         _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3176 */                                         out.write("\n               ");
/* 3177 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 3178 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3181 */                                       if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3182 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3185 */                                     if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3186 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                     }
/*      */                                     
/* 3189 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3190 */                                     out.write("\n       \t</td>\n           </tr>\n           <tr>\n   \t\t    <td valign=\"top\" colspan=\"3\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n   \t\t        <tr>\n   \t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3191 */                                     if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3193 */                                     out.write("</span></td>\n   \t\t          <td class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 3194 */                                     if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                                       return;
/* 3196 */                                     out.write("</span></td>\n   \t\t          <td class=\"columnheadingb\">&nbsp;</td>\n   \t               </tr>\n   \t               <tr>\n   \t\t\t  <td width=\"58%\" class=\"whitegrayborder\">");
/* 3197 */                                     out.print(FormatUtil.getString("am.webclient.iis.currentnonanonymoususers.text"));
/* 3198 */                                     out.write("</td>\n   \t\t\t  <td width=\"19%\" class=\"whitegrayborder\">");
/* 3199 */                                     out.print(currentnonanonymoususers);
/* 3200 */                                     out.write("</td>\n   \t\t\t  <td width=\"23%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3201 */                                     out.print(websiteid);
/* 3202 */                                     out.write("&attributeid=2012')\">");
/* 3203 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2012")));
/* 3204 */                                     out.write(" </a>    </td>\n                         </tr>\n                         <tr>\n   \t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3205 */                                     out.print(FormatUtil.getString("am.webclient.iis.nonanonymoususerspersec.text"));
/* 3206 */                                     out.write("</td>\n   \t\t\t  <td width=\"19%\" class=\"yellowgrayborder\">");
/* 3207 */                                     out.print(nonanonymoususerspersec);
/* 3208 */                                     out.write("</td>\n   \t\t\t  <td width=\"23%\" class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3209 */                                     out.print(websiteid);
/* 3210 */                                     out.write("&attributeid=2013')\">");
/* 3211 */                                     out.print(getSeverityImage(alert.getProperty(websiteid + "#" + "2013")));
/* 3212 */                                     out.write(" </a>    </td>\n                         </tr>\n                         <tr>\n   \t\t\t  <td width=\"58%\" class=\"whitegrayborder\">&nbsp;</td>\n   \t\t\t   <td width=\"42%\" colspan=\"2\" class=\"whitegrayborder\" align=\"right\">&nbsp; <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3213 */                                     out.print(websiteid);
/* 3214 */                                     out.write("&attributeIDs=2012,2013&attributeToSelect=2012&redirectto=");
/* 3215 */                                     out.print(URLEncoder.encode(IISWebsitename));
/* 3216 */                                     out.write("' class=\"staticlinks\">");
/* 3217 */                                     out.print(ALERTCONFIG_TEXT);
/* 3218 */                                     out.write("</a> </td>\n                         </tr>\n                         </table>\n   \t             </td>\n   \t        </tr>\n        </table>\n   </td>\n   </tr>\n   </table>\n   <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n     <tr>\n               <td width=\"72%\" height=\"31\" align=\"right\" class=\"tablebottom\"><a href=\"#top\" class=\"staticlinks\">");
/* 3219 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 3220 */                                     out.write("</a>&nbsp;&nbsp;</td>\n             </tr>\n           </table>\n\n\n\n\n\n\n\n");
/* 3221 */                                     int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3222 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3225 */                                   if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3226 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3229 */                                 if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3230 */                                   this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */                                 }
/*      */                                 
/* 3233 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3234 */                                 out.write(32);
/* 3235 */                                 if (_jspx_meth_tiles_005fput_005f5(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                                   return;
/* 3237 */                                 out.write(32);
/* 3238 */                                 int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3239 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3243 */                             if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3244 */                               this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                             }
/*      */                             
/* 3247 */                             this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3248 */                             out.write(10);
/* 3249 */                             out.write(10);
/* 3250 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3251 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3255 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3256 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/* 3259 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3260 */                         out.write(10);
/*      */                         
/* 3262 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3263 */                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3264 */                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3265 */                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3266 */                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                           for (;;) {
/* 3268 */                             out.write(10);
/* 3269 */                             if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.wmimonitors.remove")) {
/* 3270 */                               out.write("\n<script>\nfunction changeWebsiteStatus(action,websiteid,resid,type)\n{\nvar confirmMessage;\nif(action=='disable')\n{\n  if(type == 'website')\n  {\n\t  confirmMessage = \"");
/* 3271 */                               out.print(FormatUtil.getString("am.webclient.iis.disable.website.text"));
/* 3272 */                               out.write("\";\n  }\n  else\n  {\n\t  confirmMessage = \"");
/* 3273 */                               out.print(FormatUtil.getString("am.webclient.iis.disable.applicationpool.text"));
/* 3274 */                               out.write("\";\n  }\n  if(confirm(confirmMessage))\n  {\n     document.location.href=\"\\GlobalActions.do?method=changeWebsiteStatus&state=disable&websiteid=\"+websiteid+\"&resourceid=\"+resid;\n  }\n}\nif(action=='enable')\n{\n\tif(type == 'website')\n  \t{\n\t\tconfirmMessage = \"");
/* 3275 */                               out.print(FormatUtil.getString("am.webclient.iis.enable.website.text"));
/* 3276 */                               out.write("\";\n\t}\n\telse\n\t{\n\t\tconfirmMessage = \"");
/* 3277 */                               out.print(FormatUtil.getString("am.webclient.iis.enable.applicationpool.text"));
/* 3278 */                               out.write("\";\n\t}\n if(confirm(confirmMessage))\n  {\n     document.location.href=\"\\GlobalActions.do?method=changeWebsiteStatus&state=enable&websiteid=\"+websiteid+\"&resourceid=\"+resid;\n  }\n}\n\n\n}\n</script>\n\n");
/*      */                               
/* 3280 */                               HashMap systeminfo1 = (HashMap)request.getAttribute("systeminfo");
/* 3281 */                               long lastdc = systeminfo1.get("LASTDC") != null ? ((Long)systeminfo1.get("LASTDC")).longValue() : 0L;
/* 3282 */                               String oldquery4 = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.DISPLAYNAME,AM_IIS_TRAFFIC_DATA.BYTESTOTALPERSEC,AM_IIS_TRAFFIC_DATA.FILESRECEIVED from AM_PARENTCHILDMAPPER ,AM_ManagedObject left outer join AM_IIS_TRAFFIC_DATA on AM_IIS_TRAFFIC_DATA.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_IIS_TRAFFIC_DATA.COLLECTIONTIME=" + lastdc + "  where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resID;
/*      */                               
/*      */ 
/*      */ 
/* 3286 */                               String query4 = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.DISPLAYNAME,AM_IIS_TRAFFIC_DATA.BYTESTOTALPERSEC,AM_IIS_TRAFFIC_DATA.FILESRECEIVED,AM_ManagedObject.DCSTARTED from AM_ManagedObject  join AM_PARENTCHILDMAPPER  on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_IIS_TRAFFIC_DATA on AM_IIS_TRAFFIC_DATA.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_IIS_TRAFFIC_DATA.COLLECTIONTIME=" + lastdc + " where  AM_PARENTCHILDMAPPER.PARENTID=" + resID + " AND AM_ManagedObject.TYPE='IIS-Website'";
/*      */                               
/*      */ 
/* 3289 */                               FormatUtil.printQueryChange("IISWebsiteStats.jsp", oldquery4, query4);
/*      */                               
/* 3291 */                               ArrayList iiswebsites = new ManagedApplication().getRows(query4);
/* 3292 */                               ArrayList attribIDs1 = new ArrayList();
/* 3293 */                               ArrayList resIDs1 = new ArrayList();
/* 3294 */                               attribIDs1.add("2014");
/* 3295 */                               attribIDs1.add("2015");
/* 3296 */                               if ((iiswebsites != null) && (iiswebsites.size() > 0))
/*      */                               {
/* 3298 */                                 for (int i = 0; i < iiswebsites.size(); i++)
/*      */                                 {
/* 3300 */                                   ArrayList singlewebsite1 = (ArrayList)iiswebsites.get(i);
/* 3301 */                                   String websiteid1 = (String)singlewebsite1.get(0);
/* 3302 */                                   resIDs1.add(websiteid1);
/*      */                                 }
/*      */                               }
/* 3305 */                               Properties alert1 = getStatus(resIDs1, attribIDs1);
/* 3306 */                               String bgclass = "whitegrayborder";
/*      */                               
/*      */ 
/* 3309 */                               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td  height=\"31\" class=\"tableheading\">");
/* 3310 */                               out.print(FormatUtil.getString("am.webclient.iis.websitestats.text"));
/* 3311 */                               out.write("</td>\n      </tr>\n</table>\n");
/*      */                               
/* 3313 */                               String hostresourceid = null;
/* 3314 */                               String monitormode = null;
/*      */                               try
/*      */                               {
/* 3317 */                                 String oldQuery = "select InetService.TARGETNAME,test.RESOURCEID ,AM_HOSTINFO.MODE from AM_ManagedObject , AM_ATTRIBUTES left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST' left outer join AM_ManagedObject test on test.RESOURCENAME=InetService.TARGETNAME left outer join AM_HOSTINFO on AM_HOSTINFO.RESOURCEID=test.RESOURCEID where AM_ManagedObject.RESOURCEID=" + resID + " and AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE group by AM_ManagedObject.RESOURCENAME";
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 3322 */                                 String Query = "select InetService.TARGETNAME,test.RESOURCEID ,AM_HOSTINFO.MODE from AM_ManagedObject left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST' left outer join AM_ManagedObject test on test.RESOURCENAME=InetService.TARGETNAME left outer join AM_HOSTINFO on AM_HOSTINFO.RESOURCEID=test.RESOURCEID where AM_ManagedObject.RESOURCEID=" + resID;
/*      */                                 
/*      */ 
/* 3325 */                                 FormatUtil.printQueryChange("IISWebsiteStats.jsp", oldQuery, Query);
/*      */                                 
/* 3327 */                                 ArrayList hostdetails = new ManagedApplication().getRows(Query);
/* 3328 */                                 ArrayList hostsinglerow = (ArrayList)hostdetails.get(0);
/* 3329 */                                 String hostname = (String)hostsinglerow.get(0);
/* 3330 */                                 hostresourceid = (String)hostsinglerow.get(1);
/* 3331 */                                 request.setAttribute("hostresourceid", hostresourceid);
/* 3332 */                                 monitormode = (String)hostsinglerow.get(2);
/*      */                               }
/*      */                               catch (Exception ex)
/*      */                               {
/* 3336 */                                 ex.printStackTrace();
/*      */                               }
/* 3338 */                               if ((monitormode != null) && (!monitormode.equals("null")) && (monitormode.equalsIgnoreCase("WMI")))
/*      */                               {
/* 3340 */                                 if ((iiswebsites != null) && (iiswebsites.size() > 0))
/*      */                                 {
/*      */ 
/* 3343 */                                   out.write("\n<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n <tr>\n             <td width=\"25%\" class=\"columnheadingnotop\">");
/* 3344 */                                   out.print(FormatUtil.getString("am.webclient.iis.websitename.text"));
/* 3345 */                                   out.write("</td>\n             <td width=\"14%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3346 */                                   out.print(FormatUtil.getString("Availability"));
/* 3347 */                                   out.write("</td>\n             <td width=\"14%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3348 */                                   out.print(FormatUtil.getString("Health"));
/* 3349 */                                   out.write(" </td>\n             <td width=\"16%\" class=\"columnheadingnotop\">");
/* 3350 */                                   out.print(FormatUtil.getString("am.webclient.iis.bytestransferredpersec.text"));
/* 3351 */                                   out.write(" </td>\n             <td width=\"16%\" class=\"columnheadingnotop\">");
/* 3352 */                                   out.print(FormatUtil.getString("am.webclient.iis.filestransferredpersec.text"));
/* 3353 */                                   out.write(" </td>\n             ");
/*      */                                   
/* 3355 */                                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3356 */                                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3357 */                                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                   
/* 3359 */                                   _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 3360 */                                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3361 */                                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                     for (;;) {
/* 3363 */                                       out.write("\n             <td width=\"15%\" class=\"columnheadingnotop\">");
/* 3364 */                                       out.print(FormatUtil.getString("am.webclient.camscreen.actions.text"));
/* 3365 */                                       out.write(" </td>\n             ");
/* 3366 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3367 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3371 */                                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3372 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                   }
/*      */                                   
/* 3375 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3376 */                                   out.write("\n           </tr>\n           ");
/*      */                                   
/* 3378 */                                   for (int i = 0; i < iiswebsites.size(); i++)
/*      */                                   {
/* 3380 */                                     if (i % 2 == 0)
/*      */                                     {
/* 3382 */                                       bgclass = "whitegrayborder";
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3386 */                                       bgclass = "yellowgrayborder";
/*      */                                     }
/* 3388 */                                     ArrayList singlewebsite = (ArrayList)iiswebsites.get(i);
/* 3389 */                                     String websiteid1 = (String)singlewebsite.get(0);
/* 3390 */                                     String websitename = (String)singlewebsite.get(1);
/* 3391 */                                     String bytestrans = (singlewebsite.get(2) != null) && (!((String)singlewebsite.get(2)).equals("-1")) ? (String)singlewebsite.get(2) : "-";
/* 3392 */                                     String filestrans = (singlewebsite.get(3) != null) && (!((String)singlewebsite.get(3)).equals("-1")) ? (String)singlewebsite.get(3) : "-";
/* 3393 */                                     String dcstarted = (String)singlewebsite.get(4);
/* 3394 */                                     String websitestyleclass = "staticlinks";
/* 3395 */                                     String disablelink = "<a href=\"javascript:void(0)\" onClick=\"changeWebsiteStatus('disable','" + websiteid1 + "','" + resID + "','website')\" ><img src=\"/images/icon_tickmark.gif\" align=\"absmiddle\" title=\"" + FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text") + "\" id=\"1\" border=\"0\"></a>";
/* 3396 */                                     if (dcstarted.equals("7"))
/*      */                                     {
/* 3398 */                                       websitestyleclass = "disabledtext";
/* 3399 */                                       disablelink = "<a href=\"javascript:void(0)\" onClick=\"changeWebsiteStatus('enable','" + websiteid1 + "','" + resID + "','website')\" ><img src=\"/images/cross.gif\" align=\"absmiddle\" title=\"" + FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text") + "\" id=\"1\" border=\"0\">";
/*      */                                     }
/*      */                                     
/* 3402 */                                     out.write("\n           <tr>\n             <td class=\"");
/* 3403 */                                     out.print(bgclass);
/* 3404 */                                     out.write("\"><a href=\"");
/* 3405 */                                     out.print(IISmonitorpage + "&websiteid=" + websiteid1 + "&websitename=" + websitename);
/* 3406 */                                     out.write("\" class=\"");
/* 3407 */                                     out.print(websitestyleclass);
/* 3408 */                                     out.write(34);
/* 3409 */                                     out.write(62);
/* 3410 */                                     out.print(websitename);
/* 3411 */                                     out.write("</a></td>\n             <td class=\"");
/* 3412 */                                     out.print(bgclass);
/* 3413 */                                     out.write("\" align=\"center\"><a href=\"javascript:void(0)\"  onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3414 */                                     out.print(websiteid1);
/* 3415 */                                     out.write("&attributeid=2015')\">");
/* 3416 */                                     out.print(getSeverityImageForAvailability(alert1.getProperty(websiteid1 + "#" + "2015")));
/* 3417 */                                     out.write("</a></td>\n             <td class=\"");
/* 3418 */                                     out.print(bgclass);
/* 3419 */                                     out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3420 */                                     out.print(websiteid1);
/* 3421 */                                     out.write("&attributeid=2014')\">");
/* 3422 */                                     out.print(getSeverityImageForHealth(alert1.getProperty(websiteid1 + "#" + 2014)));
/* 3423 */                                     out.write("</a> </td>\n             <td class=\"");
/* 3424 */                                     out.print(bgclass);
/* 3425 */                                     out.write(34);
/* 3426 */                                     out.write(62);
/* 3427 */                                     out.print(bytestrans);
/* 3428 */                                     out.write("</td>\n             <td class=\"");
/* 3429 */                                     out.print(bgclass);
/* 3430 */                                     out.write(34);
/* 3431 */                                     out.write(62);
/* 3432 */                                     out.print(filestrans);
/* 3433 */                                     out.write("</td>\n             ");
/*      */                                     
/* 3435 */                                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3436 */                                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3437 */                                     _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                     
/* 3439 */                                     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 3440 */                                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3441 */                                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                       for (;;) {
/* 3443 */                                         out.write("\n             <td class=\"");
/* 3444 */                                         out.print(bgclass);
/* 3445 */                                         out.write("\">&nbsp; <a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3446 */                                         out.print(websiteid1);
/* 3447 */                                         out.write("&attributeIDs=2014,2015&attributeToSelect=2014&redirectto=");
/* 3448 */                                         out.print(URLEncoder.encode(IISmonitorpage));
/* 3449 */                                         out.write("'><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a>\n            &nbsp;");
/* 3450 */                                         out.print(disablelink);
/* 3451 */                                         out.write("\n             </td>\n             ");
/* 3452 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3453 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3457 */                                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3458 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                     }
/*      */                                     
/* 3461 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3462 */                                     out.write("\n           </tr>\n\t   ");
/*      */                                   }
/*      */                                   
/* 3465 */                                   out.write("\n\t   </table>\n\t     <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t     <tr>\n\t\t\t     <td class=\"tableheading\" align=\"right\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"MM_openBrWindow('../jsp/RemoveSites.jsp?resourceid=");
/* 3466 */                                   out.print(resID);
/* 3467 */                                   out.write("&type=IIS-Website','Personalize','width=800,height=350,screenX=200,screenY=250,scrollbars=yes')\">");
/* 3468 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.sitedeleted"));
/* 3469 */                                   out.write("</a></tr></td></table>\n\t   ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3474 */                                   out.write("\n           <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n           <tr>\n\t       <td width=\"50%\" height=\"39\" class=\"bodytext\" align=\"center\">");
/* 3475 */                                   out.print(FormatUtil.getString("am.webclient.iis.nowebsites.alert.text"));
/* 3476 */                                   out.write("</td>\n           </tr>\n       \t   </table>\n          ");
/*      */                                 }
/*      */                                 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3482 */                                 out.write("\n          <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t             <tr>\n\t  \t       <td width=\"50%\" height=\"39\" class=\"bodytext\" align=\"center\">");
/* 3483 */                                 out.print(FormatUtil.getString("am.webclient.iis.wmimode.alert.text"));
/* 3484 */                                 out.write("<a class=\"staticlinks\" href=\"/showresource.do?resourceid=");
/* 3485 */                                 out.print(hostresourceid);
/* 3486 */                                 out.write("&method=showResourceForResourceID&editPage=true\">");
/* 3487 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.clickhere"));
/* 3488 */                                 out.write("</a>&nbsp; ");
/* 3489 */                                 out.print(FormatUtil.getString("am.webclient.iis.click.edit.link", new String[] { "" }));
/* 3490 */                                 out.write("</td>\n\t       </tr>\n\t  </table>\n           ");
/*      */                               }
/*      */                               
/*      */ 
/* 3494 */                               out.write(10);
/* 3495 */                               out.write(10);
/* 3496 */                               out.write(10);
/*      */                             }
/* 3498 */                             out.write(10);
/* 3499 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3500 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3504 */                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3505 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                         }
/*      */                         
/* 3508 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3509 */                         out.write(10);
/* 3510 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3511 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3515 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3516 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                     }
/*      */                     else {
/* 3519 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3520 */                       out.write(10);
/*      */                     }
/* 3522 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3523 */         out = _jspx_out;
/* 3524 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3525 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3526 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3529 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3535 */     PageContext pageContext = _jspx_page_context;
/* 3536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3538 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3539 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3540 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3542 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3543 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3544 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3546 */         out.write(10);
/* 3547 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3548 */           return true;
/* 3549 */         out.write(10);
/* 3550 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3555 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3556 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3557 */       return true;
/*      */     }
/* 3559 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3565 */     PageContext pageContext = _jspx_page_context;
/* 3566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3568 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3569 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3570 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3572 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3574 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3575 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3576 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3577 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3578 */       return true;
/*      */     }
/* 3580 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3581 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3586 */     PageContext pageContext = _jspx_page_context;
/* 3587 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3589 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3590 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3591 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3593 */     _jspx_th_c_005fif_005f2.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3594 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3595 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3597 */         out.write(10);
/* 3598 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3599 */           return true;
/* 3600 */         out.write(10);
/* 3601 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3602 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3606 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3607 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3608 */       return true;
/*      */     }
/* 3610 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3616 */     PageContext pageContext = _jspx_page_context;
/* 3617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3619 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3620 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3621 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3623 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3625 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3626 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3627 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3628 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3629 */       return true;
/*      */     }
/* 3631 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3632 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3637 */     PageContext pageContext = _jspx_page_context;
/* 3638 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3640 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3641 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3642 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3644 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 3646 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/AvailabilityPerformanceLinks.jsp");
/* 3647 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3648 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3649 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3650 */       return true;
/*      */     }
/* 3652 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3653 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3658 */     PageContext pageContext = _jspx_page_context;
/* 3659 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3661 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3662 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3663 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3664 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3665 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3666 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3667 */         out = _jspx_page_context.pushBody();
/* 3668 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3669 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3672 */         out.write("table.heading.attribute");
/* 3673 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3674 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3677 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3678 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3681 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3682 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3683 */       return true;
/*      */     }
/* 3685 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3686 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3691 */     PageContext pageContext = _jspx_page_context;
/* 3692 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3694 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3695 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3696 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3697 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3698 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3699 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3700 */         out = _jspx_page_context.pushBody();
/* 3701 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3702 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3705 */         out.write("table.heading.value");
/* 3706 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3707 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3710 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3711 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3714 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3715 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3716 */       return true;
/*      */     }
/* 3718 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fpiechart_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3724 */     PageContext pageContext = _jspx_page_context;
/* 3725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3727 */     AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3728 */     _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3729 */     _jspx_th_awolf_005fpiechart_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 3731 */     _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */     
/* 3733 */     _jspx_th_awolf_005fpiechart_005f0.setWidth("240");
/*      */     
/* 3735 */     _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */     
/* 3737 */     _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */     
/* 3739 */     _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */     
/* 3741 */     _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */     
/* 3743 */     _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3744 */     int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3745 */     if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3746 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3747 */         out = _jspx_page_context.pushBody();
/* 3748 */         _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3749 */         _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3752 */         out.write("\n\t\t\t     ");
/* 3753 */         if (_jspx_meth_awolf_005fmap_005f0(_jspx_th_awolf_005fpiechart_005f0, _jspx_page_context))
/* 3754 */           return true;
/* 3755 */         out.write(32);
/* 3756 */         int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3760 */       if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3761 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3764 */     if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3765 */       this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3766 */       return true;
/*      */     }
/* 3768 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fmap_005f0(JspTag _jspx_th_awolf_005fpiechart_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3774 */     PageContext pageContext = _jspx_page_context;
/* 3775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3777 */     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3778 */     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3779 */     _jspx_th_awolf_005fmap_005f0.setParent((Tag)_jspx_th_awolf_005fpiechart_005f0);
/*      */     
/* 3781 */     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3782 */     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3783 */     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3784 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3785 */         out = _jspx_page_context.pushBody();
/* 3786 */         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3787 */         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3790 */         out.write(32);
/* 3791 */         if (_jspx_meth_awolf_005fparam_005f0(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3792 */           return true;
/* 3793 */         out.write(32);
/* 3794 */         if (_jspx_meth_awolf_005fparam_005f1(_jspx_th_awolf_005fmap_005f0, _jspx_page_context))
/* 3795 */           return true;
/* 3796 */         out.write("\n\t\t\t     ");
/* 3797 */         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3801 */       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3802 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3805 */     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3806 */       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3807 */       return true;
/*      */     }
/* 3809 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f0(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3815 */     PageContext pageContext = _jspx_page_context;
/* 3816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3818 */     AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3819 */     _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3820 */     _jspx_th_awolf_005fparam_005f0.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3822 */     _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */     
/* 3824 */     _jspx_th_awolf_005fparam_005f0.setValue("#29FF29");
/* 3825 */     int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3826 */     if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3827 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3828 */       return true;
/*      */     }
/* 3830 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fparam_005f1(JspTag _jspx_th_awolf_005fmap_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3836 */     PageContext pageContext = _jspx_page_context;
/* 3837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3839 */     AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3840 */     _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3841 */     _jspx_th_awolf_005fparam_005f1.setParent((Tag)_jspx_th_awolf_005fmap_005f0);
/*      */     
/* 3843 */     _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */     
/* 3845 */     _jspx_th_awolf_005fparam_005f1.setValue("#FF0000");
/* 3846 */     int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3847 */     if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3848 */       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3849 */       return true;
/*      */     }
/* 3851 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3857 */     PageContext pageContext = _jspx_page_context;
/* 3858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3860 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3861 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3862 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3863 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3864 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3865 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3866 */         out = _jspx_page_context.pushBody();
/* 3867 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3868 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3871 */         out.write("table.heading.attribute");
/* 3872 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3873 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3876 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3877 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3880 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3881 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3882 */       return true;
/*      */     }
/* 3884 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3885 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3890 */     PageContext pageContext = _jspx_page_context;
/* 3891 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3893 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3894 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3895 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3896 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3897 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 3898 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3899 */         out = _jspx_page_context.pushBody();
/* 3900 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 3901 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3904 */         out.write("table.heading.value");
/* 3905 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3906 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3909 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3910 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3913 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3914 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3915 */       return true;
/*      */     }
/* 3917 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3923 */     PageContext pageContext = _jspx_page_context;
/* 3924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3926 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3927 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3928 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3929 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3930 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3931 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3932 */         out = _jspx_page_context.pushBody();
/* 3933 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3934 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3937 */         out.write("table.heading.attribute");
/* 3938 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3939 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3942 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3943 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3946 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3947 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3948 */       return true;
/*      */     }
/* 3950 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3956 */     PageContext pageContext = _jspx_page_context;
/* 3957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3959 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3960 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3961 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3962 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3963 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3964 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3965 */         out = _jspx_page_context.pushBody();
/* 3966 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3967 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3970 */         out.write("table.heading.value");
/* 3971 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3975 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3976 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3979 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3980 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3981 */       return true;
/*      */     }
/* 3983 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3989 */     PageContext pageContext = _jspx_page_context;
/* 3990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3992 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3993 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3994 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 3995 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3996 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3997 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3998 */         out = _jspx_page_context.pushBody();
/* 3999 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 4000 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4003 */         out.write("table.heading.attribute");
/* 4004 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 4005 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4008 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 4009 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4012 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4013 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4014 */       return true;
/*      */     }
/* 4016 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4022 */     PageContext pageContext = _jspx_page_context;
/* 4023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4025 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4026 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4027 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 4028 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4029 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 4030 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4031 */         out = _jspx_page_context.pushBody();
/* 4032 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 4033 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4036 */         out.write("table.heading.value");
/* 4037 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 4038 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4041 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4042 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4045 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4046 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4047 */       return true;
/*      */     }
/* 4049 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4055 */     PageContext pageContext = _jspx_page_context;
/* 4056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4058 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4059 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 4060 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 4061 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 4062 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 4063 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4064 */         out = _jspx_page_context.pushBody();
/* 4065 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 4066 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4069 */         out.write("table.heading.attribute");
/* 4070 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 4071 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4074 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4075 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4078 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 4079 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4080 */       return true;
/*      */     }
/* 4082 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4088 */     PageContext pageContext = _jspx_page_context;
/* 4089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4091 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4092 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 4093 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/* 4094 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 4095 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 4096 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4097 */         out = _jspx_page_context.pushBody();
/* 4098 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 4099 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4102 */         out.write("table.heading.value");
/* 4103 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 4104 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4107 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4108 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4111 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 4112 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4113 */       return true;
/*      */     }
/* 4115 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f5(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4121 */     PageContext pageContext = _jspx_page_context;
/* 4122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4124 */     PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4125 */     _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 4126 */     _jspx_th_tiles_005fput_005f5.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4128 */     _jspx_th_tiles_005fput_005f5.setName("footer");
/*      */     
/* 4130 */     _jspx_th_tiles_005fput_005f5.setValue("/jsp/footer.jsp");
/* 4131 */     int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 4132 */     if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 4133 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 4134 */       return true;
/*      */     }
/* 4136 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f5);
/* 4137 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\IISWebsiteStats_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */