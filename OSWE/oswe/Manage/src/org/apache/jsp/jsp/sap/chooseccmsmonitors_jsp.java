/*      */ package org.apache.jsp.jsp.sap;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class chooseccmsmonitors_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   43 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   46 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   47 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   48 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   55 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   60 */     ArrayList list = null;
/*   61 */     StringBuffer sbf = new StringBuffer();
/*   62 */     ManagedApplication mo = new ManagedApplication();
/*   63 */     if (distinct)
/*      */     {
/*   65 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   69 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   72 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   74 */       ArrayList row = (ArrayList)list.get(i);
/*   75 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   76 */       if (distinct) {
/*   77 */         sbf.append(row.get(0));
/*      */       } else
/*   79 */         sbf.append(row.get(1));
/*   80 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   83 */     return sbf.toString(); }
/*      */   
/*   85 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   88 */     if (severity == null)
/*      */     {
/*   90 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   92 */     if (severity.equals("5"))
/*      */     {
/*   94 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   96 */     if (severity.equals("1"))
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  103 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  110 */     if (severity == null)
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  114 */     if (severity.equals("1"))
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  118 */     if (severity.equals("4"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("5"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  129 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  135 */     if (severity == null)
/*      */     {
/*  137 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  139 */     if (severity.equals("5"))
/*      */     {
/*  141 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  143 */     if (severity.equals("1"))
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  149 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  155 */     if (severity == null)
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  159 */     if (severity.equals("1"))
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  163 */     if (severity.equals("4"))
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  167 */     if (severity.equals("5"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  173 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  179 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  185 */     if (severity == 5)
/*      */     {
/*  187 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  189 */     if (severity == 1)
/*      */     {
/*  191 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  196 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  202 */     if (severity == null)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  206 */     if (severity.equals("5"))
/*      */     {
/*  208 */       if (isAvailability) {
/*  209 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  212 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  215 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  219 */     if (severity.equals("1"))
/*      */     {
/*  221 */       if (isAvailability) {
/*  222 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  225 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  232 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  239 */     if (severity == null)
/*      */     {
/*  241 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  243 */     if (severity.equals("5"))
/*      */     {
/*  245 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  247 */     if (severity.equals("4"))
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("1"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  258 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  264 */     if (severity == null)
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  268 */     if (severity.equals("5"))
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("4"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("1"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  283 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  290 */     if (severity == null)
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  294 */     if (severity.equals("5"))
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  298 */     if (severity.equals("4"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  302 */     if (severity.equals("1"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  309 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  317 */     StringBuffer out = new StringBuffer();
/*  318 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  319 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  320 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  321 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  322 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  323 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  324 */     out.append("</tr>");
/*  325 */     out.append("</form></table>");
/*  326 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  333 */     if (val == null)
/*      */     {
/*  335 */       return "-";
/*      */     }
/*      */     
/*  338 */     String ret = FormatUtil.formatNumber(val);
/*  339 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  340 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  343 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  347 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  355 */     StringBuffer out = new StringBuffer();
/*  356 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  357 */     out.append("<tr>");
/*  358 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  360 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  362 */     out.append("</tr>");
/*  363 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  367 */       if (j % 2 == 0)
/*      */       {
/*  369 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  373 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  376 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  378 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  381 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  385 */       out.append("</tr>");
/*      */     }
/*  387 */     out.append("</table>");
/*  388 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  389 */     out.append("<tr>");
/*  390 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  391 */     out.append("</tr>");
/*  392 */     out.append("</table>");
/*  393 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  399 */     StringBuffer out = new StringBuffer();
/*  400 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  401 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  402 */     out.append("<tr>");
/*  403 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  404 */     out.append("<tr>");
/*  405 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  406 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  407 */     out.append("</tr>");
/*  408 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  411 */       out.append("<tr>");
/*  412 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  413 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  414 */       out.append("</tr>");
/*      */     }
/*      */     
/*  417 */     out.append("</table>");
/*  418 */     out.append("</table>");
/*  419 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  424 */     if (severity.equals("0"))
/*      */     {
/*  426 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  430 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  437 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  450 */     StringBuffer out = new StringBuffer();
/*  451 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  452 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  454 */       out.append("<tr>");
/*  455 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  456 */       out.append("</tr>");
/*      */       
/*      */ 
/*  459 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  461 */         String borderclass = "";
/*      */         
/*      */ 
/*  464 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  466 */         out.append("<tr>");
/*      */         
/*  468 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  469 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  470 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  476 */     out.append("</table><br>");
/*  477 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  478 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  480 */       List sLinks = secondLevelOfLinks[0];
/*  481 */       List sText = secondLevelOfLinks[1];
/*  482 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  485 */         out.append("<tr>");
/*  486 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  487 */         out.append("</tr>");
/*  488 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  490 */           String borderclass = "";
/*      */           
/*      */ 
/*  493 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  495 */           out.append("<tr>");
/*      */           
/*  497 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  498 */           if (sLinks.get(i).toString().length() == 0) {
/*  499 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  502 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  504 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  508 */     out.append("</table>");
/*  509 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  516 */     StringBuffer out = new StringBuffer();
/*  517 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  518 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  520 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  522 */         out.append("<tr>");
/*  523 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  524 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  528 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  530 */           String borderclass = "";
/*      */           
/*      */ 
/*  533 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  535 */           out.append("<tr>");
/*      */           
/*  537 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  538 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  539 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  542 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  545 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  550 */     out.append("</table><br>");
/*  551 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  552 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  554 */       List sLinks = secondLevelOfLinks[0];
/*  555 */       List sText = secondLevelOfLinks[1];
/*  556 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  559 */         out.append("<tr>");
/*  560 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  561 */         out.append("</tr>");
/*  562 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  564 */           String borderclass = "";
/*      */           
/*      */ 
/*  567 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  569 */           out.append("<tr>");
/*      */           
/*  571 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  572 */           if (sLinks.get(i).toString().length() == 0) {
/*  573 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  576 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  578 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  582 */     out.append("</table>");
/*  583 */     return out.toString();
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
/*  596 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  599 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  602 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  611 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  614 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  617 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  625 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  630 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  635 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  640 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  645 */     if (val != null)
/*      */     {
/*  647 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  651 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  656 */     if (val == null) {
/*  657 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  661 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  666 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  672 */     if (val != null)
/*      */     {
/*  674 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  678 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  684 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  689 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  693 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  698 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  703 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  708 */     String hostaddress = "";
/*  709 */     String ip = request.getHeader("x-forwarded-for");
/*  710 */     if (ip == null)
/*  711 */       ip = request.getRemoteAddr();
/*  712 */     InetAddress add = null;
/*  713 */     if (ip.equals("127.0.0.1")) {
/*  714 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  718 */       add = InetAddress.getByName(ip);
/*      */     }
/*  720 */     hostaddress = add.getHostName();
/*  721 */     if (hostaddress.indexOf('.') != -1) {
/*  722 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  723 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  727 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  732 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  738 */     if (severity == null)
/*      */     {
/*  740 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  742 */     if (severity.equals("5"))
/*      */     {
/*  744 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  746 */     if (severity.equals("1"))
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  753 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  758 */     ResultSet set = null;
/*  759 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  760 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  762 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  763 */       if (set.next()) { String str1;
/*  764 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  765 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  768 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  773 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  776 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  778 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  782 */     StringBuffer rca = new StringBuffer();
/*  783 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  784 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  787 */     int rcalength = key.length();
/*  788 */     String split = "6. ";
/*  789 */     int splitPresent = key.indexOf(split);
/*  790 */     String div1 = "";String div2 = "";
/*  791 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  793 */       if (rcalength > 180) {
/*  794 */         rca.append("<span class=\"rca-critical-text\">");
/*  795 */         getRCATrimmedText(key, rca);
/*  796 */         rca.append("</span>");
/*      */       } else {
/*  798 */         rca.append("<span class=\"rca-critical-text\">");
/*  799 */         rca.append(key);
/*  800 */         rca.append("</span>");
/*      */       }
/*  802 */       return rca.toString();
/*      */     }
/*  804 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  805 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  806 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  807 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  808 */     getRCATrimmedText(div1, rca);
/*  809 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  812 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  813 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  814 */     getRCATrimmedText(div2, rca);
/*  815 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  817 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  822 */     String[] st = msg.split("<br>");
/*  823 */     for (int i = 0; i < st.length; i++) {
/*  824 */       String s = st[i];
/*  825 */       if (s.length() > 180) {
/*  826 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  828 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  832 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  833 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  835 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  839 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  840 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  841 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  844 */       if (key == null) {
/*  845 */         return ret;
/*      */       }
/*      */       
/*  848 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  849 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  852 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  853 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  854 */       set = AMConnectionPool.executeQueryStmt(query);
/*  855 */       if (set.next())
/*      */       {
/*  857 */         String helpLink = set.getString("LINK");
/*  858 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  861 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  867 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  886 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  877 */         if (set != null) {
/*  878 */           AMConnectionPool.closeStatement(set);
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
/*  892 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  893 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  895 */       String entityStr = (String)keys.nextElement();
/*  896 */       String mmessage = temp.getProperty(entityStr);
/*  897 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  898 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  900 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  906 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   private void debug(String debugMessage)
/*      */   {
/*  919 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  929 */     String des = new String();
/*  930 */     while (str.indexOf(find) != -1) {
/*  931 */       des = des + str.substring(0, str.indexOf(find));
/*  932 */       des = des + replace;
/*  933 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  935 */     des = des + str;
/*  936 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  943 */       if (alert == null)
/*      */       {
/*  945 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  947 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  949 */         return "&nbsp;";
/*      */       }
/*      */       
/*  952 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  954 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  957 */       int rcalength = test.length();
/*  958 */       if (rcalength < 300)
/*      */       {
/*  960 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  964 */       StringBuffer out = new StringBuffer();
/*  965 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  966 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  967 */       out.append("</div>");
/*  968 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  969 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  970 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  975 */       ex.printStackTrace();
/*      */     }
/*  977 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  983 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  988 */     ArrayList attribIDs = new ArrayList();
/*  989 */     ArrayList resIDs = new ArrayList();
/*  990 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  992 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  994 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  996 */       String resourceid = "";
/*  997 */       String resourceType = "";
/*  998 */       if (type == 2) {
/*  999 */         resourceid = (String)row.get(0);
/* 1000 */         resourceType = (String)row.get(3);
/*      */       }
/* 1002 */       else if (type == 3) {
/* 1003 */         resourceid = (String)row.get(0);
/* 1004 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1007 */         resourceid = (String)row.get(6);
/* 1008 */         resourceType = (String)row.get(7);
/*      */       }
/* 1010 */       resIDs.add(resourceid);
/* 1011 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1012 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1014 */       String healthentity = null;
/* 1015 */       String availentity = null;
/* 1016 */       if (healthid != null) {
/* 1017 */         healthentity = resourceid + "_" + healthid;
/* 1018 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1021 */       if (availid != null) {
/* 1022 */         availentity = resourceid + "_" + availid;
/* 1023 */         entitylist.add(availentity);
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
/* 1037 */     Properties alert = getStatus(entitylist);
/* 1038 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1043 */     int size = monitorList.size();
/*      */     
/* 1045 */     String[] severity = new String[size];
/*      */     
/* 1047 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1049 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1050 */       String resourceName1 = (String)row1.get(7);
/* 1051 */       String resourceid1 = (String)row1.get(6);
/* 1052 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1053 */       if (severity[j] == null)
/*      */       {
/* 1055 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1059 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1061 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1063 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1066 */         if (sev > 0) {
/* 1067 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1068 */           monitorList.set(k, monitorList.get(j));
/* 1069 */           monitorList.set(j, t);
/* 1070 */           String temp = severity[k];
/* 1071 */           severity[k] = severity[j];
/* 1072 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1078 */     int z = 0;
/* 1079 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1082 */       int i = 0;
/* 1083 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1086 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1090 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1094 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1096 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1099 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1103 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1106 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1107 */       String resourceName1 = (String)row1.get(7);
/* 1108 */       String resourceid1 = (String)row1.get(6);
/* 1109 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1110 */       if (hseverity[j] == null)
/*      */       {
/* 1112 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1117 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1119 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1122 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1125 */         if (hsev > 0) {
/* 1126 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1127 */           monitorList.set(k, monitorList.get(j));
/* 1128 */           monitorList.set(j, t);
/* 1129 */           String temp1 = hseverity[k];
/* 1130 */           hseverity[k] = hseverity[j];
/* 1131 */           hseverity[j] = temp1;
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
/* 1143 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1144 */     boolean forInventory = false;
/* 1145 */     String trdisplay = "none";
/* 1146 */     String plusstyle = "inline";
/* 1147 */     String minusstyle = "none";
/* 1148 */     String haidTopLevel = "";
/* 1149 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1151 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1153 */         haidTopLevel = request.getParameter("haid");
/* 1154 */         forInventory = true;
/* 1155 */         trdisplay = "table-row;";
/* 1156 */         plusstyle = "none";
/* 1157 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1164 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1167 */     ArrayList listtoreturn = new ArrayList();
/* 1168 */     StringBuffer toreturn = new StringBuffer();
/* 1169 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1170 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1171 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1173 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1175 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1176 */       String childresid = (String)singlerow.get(0);
/* 1177 */       String childresname = (String)singlerow.get(1);
/* 1178 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1179 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1180 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1181 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1182 */       String unmanagestatus = (String)singlerow.get(5);
/* 1183 */       String actionstatus = (String)singlerow.get(6);
/* 1184 */       String linkclass = "monitorgp-links";
/* 1185 */       String titleforres = childresname;
/* 1186 */       String titilechildresname = childresname;
/* 1187 */       String childimg = "/images/trcont.png";
/* 1188 */       String flag = "enable";
/* 1189 */       String dcstarted = (String)singlerow.get(8);
/* 1190 */       String configMonitor = "";
/* 1191 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1192 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1194 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1196 */       if (singlerow.get(7) != null)
/*      */       {
/* 1198 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1200 */       String haiGroupType = "0";
/* 1201 */       if ("HAI".equals(childtype))
/*      */       {
/* 1203 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1205 */       childimg = "/images/trend.png";
/* 1206 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1207 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1208 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1210 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1212 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1214 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1215 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1218 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1220 */         linkclass = "disabledtext";
/* 1221 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1223 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1224 */       String availmouseover = "";
/* 1225 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1227 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1229 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1230 */       String healthmouseover = "";
/* 1231 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1233 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1236 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1237 */       int spacing = 0;
/* 1238 */       if (level >= 1)
/*      */       {
/* 1240 */         spacing = 40 * level;
/*      */       }
/* 1242 */       if (childtype.equals("HAI"))
/*      */       {
/* 1244 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1245 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1246 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1248 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1249 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1250 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1251 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1252 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1253 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1254 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1255 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1256 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1257 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1258 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1260 */         if (!forInventory)
/*      */         {
/* 1262 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1265 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1267 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1269 */           actions = editlink + actions;
/*      */         }
/* 1271 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1273 */           actions = actions + associatelink;
/*      */         }
/* 1275 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1276 */         String arrowimg = "";
/* 1277 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1279 */           actions = "";
/* 1280 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1281 */           checkbox = "";
/* 1282 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1284 */         if (isIt360)
/*      */         {
/* 1286 */           actionimg = "";
/* 1287 */           actions = "";
/* 1288 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1289 */           checkbox = "";
/*      */         }
/*      */         
/* 1292 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1294 */           actions = "";
/*      */         }
/* 1296 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1298 */           checkbox = "";
/*      */         }
/*      */         
/* 1301 */         String resourcelink = "";
/*      */         
/* 1303 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1305 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1309 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1312 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1313 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1314 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1318 */         if (!isIt360)
/*      */         {
/* 1320 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1324 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1327 */         toreturn.append("</tr>");
/* 1328 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1330 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1331 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1336 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1339 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1343 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1345 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1346 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1347 */             toreturn.append(assocMessage);
/* 1348 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1349 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1351 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1357 */         String resourcelink = null;
/* 1358 */         boolean hideEditLink = false;
/* 1359 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1361 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1362 */           hideEditLink = true;
/* 1363 */           if (isIt360)
/*      */           {
/* 1365 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1369 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1371 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1373 */           hideEditLink = true;
/* 1374 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1375 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1380 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1383 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1384 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1385 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1386 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1387 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1388 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1389 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1390 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1391 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1392 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1393 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1394 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1395 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1397 */         if (hideEditLink)
/*      */         {
/* 1399 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1401 */         if (!forInventory)
/*      */         {
/* 1403 */           removefromgroup = "";
/*      */         }
/* 1405 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1406 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1407 */           actions = actions + configcustomfields;
/*      */         }
/* 1409 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1411 */           actions = editlink + actions;
/*      */         }
/* 1413 */         String managedLink = "";
/* 1414 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1416 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1417 */           actions = "";
/* 1418 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1419 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1422 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1424 */           checkbox = "";
/*      */         }
/*      */         
/* 1427 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1429 */           actions = "";
/*      */         }
/* 1431 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1432 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1433 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1434 */         if (isIt360)
/*      */         {
/* 1436 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1440 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1444 */         if (!isIt360)
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1452 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1455 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1462 */       StringBuilder toreturn = new StringBuilder();
/* 1463 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1464 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1465 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1466 */       String title = "";
/* 1467 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1468 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1469 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1470 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1472 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1474 */       else if ("5".equals(severity))
/*      */       {
/* 1476 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1480 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1482 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1483 */       toreturn.append(v);
/*      */       
/* 1485 */       toreturn.append(link);
/* 1486 */       if (severity == null)
/*      */       {
/* 1488 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1490 */       else if (severity.equals("5"))
/*      */       {
/* 1492 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1494 */       else if (severity.equals("4"))
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("1"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       toreturn.append("</a>");
/* 1508 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1512 */       ex.printStackTrace();
/*      */     }
/* 1514 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1521 */       StringBuilder toreturn = new StringBuilder();
/* 1522 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1523 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1524 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1525 */       if (message == null)
/*      */       {
/* 1527 */         message = "";
/*      */       }
/*      */       
/* 1530 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1531 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1533 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1534 */       toreturn.append(v);
/*      */       
/* 1536 */       toreturn.append(link);
/*      */       
/* 1538 */       if (severity == null)
/*      */       {
/* 1540 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1542 */       else if (severity.equals("5"))
/*      */       {
/* 1544 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1546 */       else if (severity.equals("1"))
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       toreturn.append("</a>");
/* 1556 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1562 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1565 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1566 */     if (invokeActions != null) {
/* 1567 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1568 */       while (iterator.hasNext()) {
/* 1569 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1570 */         if (actionmap.containsKey(actionid)) {
/* 1571 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1576 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1580 */     String actionLink = "";
/* 1581 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1582 */     String query = "";
/* 1583 */     ResultSet rs = null;
/* 1584 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1585 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1586 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1587 */       actionLink = "method=" + methodName;
/*      */     }
/* 1589 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1590 */       actionLink = methodName;
/*      */     }
/* 1592 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1593 */     Iterator itr = methodarglist.iterator();
/* 1594 */     boolean isfirstparam = true;
/* 1595 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1596 */     while (itr.hasNext()) {
/* 1597 */       HashMap argmap = (HashMap)itr.next();
/* 1598 */       String argtype = (String)argmap.get("TYPE");
/* 1599 */       String argname = (String)argmap.get("IDENTITY");
/* 1600 */       String paramname = (String)argmap.get("PARAMETER");
/* 1601 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1602 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1603 */         isfirstparam = false;
/* 1604 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1606 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1610 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1614 */         actionLink = actionLink + "&";
/*      */       }
/* 1616 */       String paramValue = null;
/* 1617 */       String tempargname = argname;
/* 1618 */       if (commonValues.getProperty(tempargname) != null) {
/* 1619 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1622 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1623 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1624 */           if (dbType.equals("mysql")) {
/* 1625 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1628 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1630 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1632 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1633 */             if (rs.next()) {
/* 1634 */               paramValue = rs.getString("VALUE");
/* 1635 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1639 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1643 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1646 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1651 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1652 */           paramValue = rowId;
/*      */         }
/* 1654 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1655 */           paramValue = managedObjectName;
/*      */         }
/* 1657 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1658 */           paramValue = resID;
/*      */         }
/* 1660 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1661 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1664 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1666 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1667 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1668 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1670 */     return actionLink;
/*      */   }
/*      */   
/* 1673 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1674 */     String dependentAttribute = null;
/* 1675 */     String align = "left";
/*      */     
/* 1677 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1678 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1679 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1680 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1681 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1682 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1683 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1684 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1685 */       align = "center";
/*      */     }
/*      */     
/* 1688 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1689 */     String actualdata = "";
/*      */     
/* 1691 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1692 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1693 */         actualdata = availValue;
/*      */       }
/* 1695 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1696 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1700 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1701 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1704 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1710 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1711 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1712 */       toreturn.append("<table>");
/* 1713 */       toreturn.append("<tr>");
/* 1714 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1715 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1716 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1717 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1718 */         String toolTip = "";
/* 1719 */         String hideClass = "";
/* 1720 */         String textStyle = "";
/* 1721 */         boolean isreferenced = true;
/* 1722 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1723 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1724 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1725 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1727 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1728 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1729 */           while (valueList.hasMoreTokens()) {
/* 1730 */             String dependentVal = valueList.nextToken();
/* 1731 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1732 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1733 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1735 */               toolTip = "";
/* 1736 */               hideClass = "";
/* 1737 */               isreferenced = false;
/* 1738 */               textStyle = "disabledtext";
/* 1739 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1743 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1744 */           toolTip = "";
/* 1745 */           hideClass = "";
/* 1746 */           isreferenced = false;
/* 1747 */           textStyle = "disabledtext";
/* 1748 */           if (dependentImageMap != null) {
/* 1749 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1750 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1753 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1757 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1758 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1759 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1760 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1761 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1762 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1764 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1765 */           if (isreferenced) {
/* 1766 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1770 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1771 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1772 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1773 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1774 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1775 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1777 */           toreturn.append("</span>");
/* 1778 */           toreturn.append("</a>");
/* 1779 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1782 */       toreturn.append("</tr>");
/* 1783 */       toreturn.append("</table>");
/* 1784 */       toreturn.append("</td>");
/*      */     } else {
/* 1786 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1789 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1793 */     String colTime = null;
/* 1794 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1795 */     if ((rows != null) && (rows.size() > 0)) {
/* 1796 */       Iterator<String> itr = rows.iterator();
/* 1797 */       String maxColQuery = "";
/* 1798 */       for (;;) { if (itr.hasNext()) {
/* 1799 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1800 */           ResultSet maxCol = null;
/*      */           try {
/* 1802 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1803 */             while (maxCol.next()) {
/* 1804 */               if (colTime == null) {
/* 1805 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1808 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1817 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1819 */               if (maxCol != null)
/* 1820 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1822 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1817 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1819 */               if (maxCol != null)
/* 1820 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1822 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1827 */     return colTime;
/*      */   }
/*      */   
/* 1830 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1831 */     tablename = null;
/* 1832 */     ResultSet rsTable = null;
/* 1833 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1835 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1836 */       while (rsTable.next()) {
/* 1837 */         tablename = rsTable.getString("DATATABLE");
/* 1838 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1839 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1852 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1843 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1846 */         if (rsTable != null)
/* 1847 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1849 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1855 */     String argsList = "";
/* 1856 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1858 */       if (showArgsMap.get(row) != null) {
/* 1859 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1860 */         if (showArgslist != null) {
/* 1861 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1862 */             if (argsList.trim().equals("")) {
/* 1863 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1866 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1873 */       e.printStackTrace();
/* 1874 */       return "";
/*      */     }
/* 1876 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1881 */     String argsList = "";
/* 1882 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1885 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1887 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1888 */         if (hideArgsList != null)
/*      */         {
/* 1890 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1892 */             if (argsList.trim().equals(""))
/*      */             {
/* 1894 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1898 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1906 */       ex.printStackTrace();
/*      */     }
/* 1908 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1912 */     StringBuilder toreturn = new StringBuilder();
/* 1913 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1920 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1921 */       Iterator itr = tActionList.iterator();
/* 1922 */       while (itr.hasNext()) {
/* 1923 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1924 */         String confirmmsg = "";
/* 1925 */         String link = "";
/* 1926 */         String isJSP = "NO";
/* 1927 */         HashMap tactionMap = (HashMap)itr.next();
/* 1928 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1929 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1930 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1931 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1932 */           (actionmap.containsKey(actionId))) {
/* 1933 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1934 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1935 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1936 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1937 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1939 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1945 */           if (isTableAction) {
/* 1946 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1949 */             tableName = "Link";
/* 1950 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1951 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1952 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1953 */             toreturn.append("</a></td>");
/*      */           }
/* 1955 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1956 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1964 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1970 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1972 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1973 */       Properties prop = (Properties)node.getUserObject();
/* 1974 */       String mgID = prop.getProperty("label");
/* 1975 */       String mgName = prop.getProperty("value");
/* 1976 */       String isParent = prop.getProperty("isParent");
/* 1977 */       int mgIDint = Integer.parseInt(mgID);
/* 1978 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1980 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1982 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1983 */       if (node.getChildCount() > 0)
/*      */       {
/* 1985 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1987 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1989 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1991 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1995 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2000 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2002 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2004 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2006 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2010 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2013 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2014 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2016 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2020 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2022 */       if (node.getChildCount() > 0)
/*      */       {
/* 2024 */         builder.append("<UL>");
/* 2025 */         printMGTree(node, builder);
/* 2026 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2031 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2032 */     StringBuffer toReturn = new StringBuffer();
/* 2033 */     String table = "-";
/*      */     try {
/* 2035 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2036 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2037 */       float total = 0.0F;
/* 2038 */       while (it.hasNext()) {
/* 2039 */         String attName = (String)it.next();
/* 2040 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2041 */         boolean roundOffData = false;
/* 2042 */         if ((data != null) && (!data.equals(""))) {
/* 2043 */           if (data.indexOf(",") != -1) {
/* 2044 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2047 */             float value = Float.parseFloat(data);
/* 2048 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2051 */             total += value;
/* 2052 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2055 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2060 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2061 */       while (attVsWidthList.hasNext()) {
/* 2062 */         String attName = (String)attVsWidthList.next();
/* 2063 */         String data = (String)attVsWidthProps.get(attName);
/* 2064 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2065 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2066 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2067 */         String className = (String)graphDetails.get("ClassName");
/* 2068 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2069 */         if (percentage < 1.0F)
/*      */         {
/* 2071 */           data = percentage + "";
/*      */         }
/* 2073 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2075 */       if (toReturn.length() > 0) {
/* 2076 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2080 */       e.printStackTrace();
/*      */     }
/* 2082 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2088 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2089 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2090 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2091 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2092 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2093 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2094 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2095 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2096 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2099 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2100 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2101 */       splitvalues[0] = multiplecondition.toString();
/* 2102 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2105 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2110 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2111 */     if (thresholdType != 3) {
/* 2112 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2113 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2114 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2115 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2116 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2117 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2119 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2120 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2121 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2122 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2123 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2124 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2126 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2127 */     if (updateSelected != null) {
/* 2128 */       updateSelected[0] = "selected";
/*      */     }
/* 2130 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2135 */       StringBuffer toreturn = new StringBuffer("");
/* 2136 */       if (commaSeparatedMsgId != null) {
/* 2137 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2138 */         int count = 0;
/* 2139 */         while (msgids.hasMoreTokens()) {
/* 2140 */           String id = msgids.nextToken();
/* 2141 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2142 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2143 */           count++;
/* 2144 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2145 */             if (toreturn.length() == 0) {
/* 2146 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2148 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2149 */             if (!image.trim().equals("")) {
/* 2150 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2152 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2153 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2156 */         if (toreturn.length() > 0) {
/* 2157 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2161 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2164 */       e.printStackTrace(); }
/* 2165 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2171 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2177 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2178 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2196 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2200 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2211 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2216 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2222 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2231 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2234 */     JspWriter out = null;
/* 2235 */     Object page = this;
/* 2236 */     JspWriter _jspx_out = null;
/* 2237 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2241 */       response.setContentType("text/html;charset=UTF-8");
/* 2242 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2244 */       _jspx_page_context = pageContext;
/* 2245 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2246 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2247 */       session = pageContext.getSession();
/* 2248 */       out = pageContext.getOut();
/* 2249 */       _jspx_out = out;
/*      */       
/* 2251 */       out.write("<!DOCTYPE html>\n");
/* 2252 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$ -->\n\n");
/*      */       
/* 2254 */       request.setAttribute("HelpKey", "Monitors SAP CCMS Server Details");
/*      */       
/* 2256 */       out.write("\n\n\n\n\n\n");
/* 2257 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2259 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2260 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2262 */       out.write(10);
/* 2263 */       out.write(10);
/* 2264 */       out.write(10);
/* 2265 */       out.write(10);
/* 2266 */       out.write(10);
/*      */       
/* 2268 */       String resourceID = request.getParameter("resourceid");
/* 2269 */       String haid = request.getParameter("haid");
/* 2270 */       String screenID = request.getParameter("screenid");
/* 2271 */       String displayName = BreadcrumbUtil.getDisplayName(request);
/* 2272 */       String quickNote = FormatUtil.getString("am.webclient.cam.choosedomain.quicknote");
/* 2273 */       String topDesc = FormatUtil.getString("a.webclient.cam.choosedomain.quicknote");
/* 2274 */       request.setAttribute("quicknote", quickNote);
/*      */       
/* 2276 */       out.write(10);
/* 2277 */       out.write(10);
/*      */       
/* 2279 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2280 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2281 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2283 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2284 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2285 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2287 */           out.write(10);
/*      */           
/* 2289 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2290 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2291 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2293 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2295 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.ccms.choosemonitorset"));
/* 2296 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2297 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2298 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2301 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2302 */           out.write(10);
/* 2303 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2305 */           out.write(10);
/* 2306 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2308 */           out.write(10);
/*      */           
/* 2310 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2311 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2312 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2314 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2316 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2317 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2318 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2319 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2320 */               out = _jspx_page_context.pushBody();
/* 2321 */               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2322 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2325 */               out.write("\n\n<script>\nvar http1;\nfunction submitAttribs()\n{\n    if(document.getElementsByName(\"monitorelementscheckbox\").length==0)\n    {\n    \talert(\"");
/* 2326 */               out.print(FormatUtil.getString("am.webclient.ccms.ccmsalert"));
/* 2327 */               out.write("\");\n    \treturn;\n    }\n    document.AMActionForm.submit();\n}\nfunction showMonitorTreeElements(monitorset,monitors)\n{\n\tURL='/sap.do?method=showCCMSMonitorTreeElements&resourceid=");
/* 2328 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2330 */               out.write("&screenid=");
/* 2331 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2333 */               out.write("&set='+monitorset+'&monitor='+monitors;\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = new Function('if(http1.readyState == 4 && http1.status == 200){document.getElementById(\"'+monitorset+'_'+monitors+'\").innerHTML =\"&nbsp;\",document.getElementById(\"'+monitorset+'_'+monitors+'\").innerHTML = http1.responseText;}');\n\thttp1.open(\"GET\", URL, true);\n\thttp1.send(null);\n}\nfunction selectMonitorElements(monitor,monitorkey)\n{\n\tvar ele = document.getElementsByName(\"monitorelementscheckbox\");\n\tfor(i=0; i<ele.length; i++)\n\t{\n\t\tvar mon = ele[i].value;\n\t\tif(mon.indexOf(\"||\"+monitorkey+\"||\")!=-1)\n\t\t{\n\t\t\tif(monitor.checked==true)\n\t\t\t\tele[i].checked=true;\n\t\t\telse\n\t\t\t\tele[i].checked=false;\n\t\t}\n\t}\n}\n</script>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    ");
/*      */               
/* 2335 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2336 */               String aid = request.getParameter("haid");
/* 2337 */               String haName = null;
/* 2338 */               if (aid != null)
/*      */               {
/* 2340 */                 haName = (String)ht.get(aid);
/*      */               }
/*      */               
/* 2343 */               out.write("\n    ");
/*      */               
/* 2345 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2346 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2347 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 2348 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2349 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2351 */                   out.write("\n      ");
/*      */                   
/* 2353 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2354 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2355 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2357 */                   _jspx_th_c_005fwhen_005f0.setTest("${!empty param.haid && param.haid!=null && param.haid!='null'}");
/* 2358 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2359 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2361 */                       out.write("\n        <td class=\"bcsign\" height=\"22\" valign=\"top\"> ");
/* 2362 */                       out.print(BreadcrumbUtil.getHomePage(request));
/* 2363 */                       out.write(" &gt; ");
/* 2364 */                       out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2365 */                       out.write(" &gt; ");
/* 2366 */                       out.print(BreadcrumbUtil.getServerDetailsPage(resourceID, displayName));
/* 2367 */                       out.write(" &gt; <span class=\"bcactive\"> ");
/* 2368 */                       out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2369 */                       out.write("</span></td>\n      ");
/* 2370 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2371 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2375 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2376 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 2379 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2380 */                   out.write("\n      ");
/*      */                   
/* 2382 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2383 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2384 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2385 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2386 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2388 */                       out.write("\n        <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2389 */                       out.print(BreadcrumbUtil.getMonitorsPage());
/* 2390 */                       out.write(" &gt; ");
/* 2391 */                       out.print(BreadcrumbUtil.getMonitorResourceTypes("SAP-CCMS"));
/* 2392 */                       out.write(" &gt; ");
/* 2393 */                       out.print(BreadcrumbUtil.getServerDetailsPage(resourceID, displayName));
/* 2394 */                       out.write(" &gt; <span class=\"bcactive\"> ");
/* 2395 */                       out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2396 */                       out.write("</span> </td>\n      ");
/* 2397 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2398 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2402 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2403 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 2406 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2407 */                   out.write("\n    ");
/* 2408 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2409 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2413 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2414 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 2417 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2418 */               out.write("\n  </tr>\n  <tr>\n    <td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n  </tr>\n</table>\n\n");
/*      */               
/* 2420 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2421 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2422 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2424 */               _jspx_th_html_005fform_005f0.setAction("/sap");
/*      */               
/* 2426 */               _jspx_th_html_005fform_005f0.setMethod("POST");
/* 2427 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2428 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2430 */                   out.write(10);
/*      */                   
/* 2432 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2433 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2434 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2436 */                   _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && param.haid!=null}");
/* 2437 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2438 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/* 2440 */                       out.write("\n  <input type=\"hidden\" name=\"haid\" value=\"");
/* 2441 */                       out.print(haid);
/* 2442 */                       out.write("\"/>\n");
/* 2443 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2444 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2448 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2449 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/* 2452 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2453 */                   out.write("\n<input type=\"hidden\" name=\"screenid\" value=\"");
/* 2454 */                   out.print(screenID);
/* 2455 */                   out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2456 */                   out.print(resourceID);
/* 2457 */                   out.write("\"/>\n<input type=hidden name=\"method\" value=\"createCustomCCMSMonitors\"/>\n<br>\n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\" width=\"99%\" bgcolor='FFFFFF'>\n  <tr>\n    <td height=\"32\" colspan=\"2\" class=\"tableheadingbborder\"> ");
/* 2458 */                   out.print(FormatUtil.getString("am.webclient.ccms.chooseattributes"));
/* 2459 */                   out.write(" * </td>\n  </tr>\n  <tr>\n    <td colspan=\"2\">&nbsp;</td>\n  </tr>\n  ");
/* 2460 */                   if (_jspx_meth_c_005fforEach_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2462 */                   out.write("\n  <tr>\n    <td colspan=\"2\">&nbsp;</td>\n  </tr>\n  <tr>\n    <td height=\"30\" colspan=\"2\" class=\"tablebottom\" align=\"center\">\n      <input name=\"submitbutton\" type=\"button\" onClick=\"javascript:submitAttribs();\" class='buttons' value=\"");
/* 2463 */                   out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 2464 */                   out.write("\" />&nbsp;&nbsp;\n      <input type=\"submit\" value=\"");
/* 2465 */                   out.print(FormatUtil.getString("am.webclient.camscreen.back.button"));
/* 2466 */                   out.write("\" class='buttons' onClick=\"history.back()\" />\n    </td>\n  </tr>\n</table>\n");
/* 2467 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2468 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2472 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2473 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2476 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2477 */               out.write(10);
/* 2478 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2479 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2482 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2483 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2486 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2487 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 2490 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2491 */           out.write(10);
/* 2492 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2494 */           out.write(10);
/* 2495 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2496 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2500 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2501 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 2504 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2505 */         out.write(10);
/*      */       }
/* 2507 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2508 */         out = _jspx_out;
/* 2509 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2510 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2511 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2514 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2520 */     PageContext pageContext = _jspx_page_context;
/* 2521 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2523 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2524 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2525 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2527 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2529 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2530 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2531 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2532 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2533 */       return true;
/*      */     }
/* 2535 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2541 */     PageContext pageContext = _jspx_page_context;
/* 2542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2544 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2545 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2546 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2548 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 2550 */     _jspx_th_tiles_005fput_005f2.setType("string");
/* 2551 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2552 */     if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2553 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2554 */         out = _jspx_page_context.pushBody();
/* 2555 */         _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2556 */         _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2559 */         out.write("&nbsp;");
/* 2560 */         int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2561 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2564 */       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2565 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2568 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2569 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2570 */       return true;
/*      */     }
/* 2572 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2578 */     PageContext pageContext = _jspx_page_context;
/* 2579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2581 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2582 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2583 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2585 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 2586 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2587 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2588 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2589 */       return true;
/*      */     }
/* 2591 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2597 */     PageContext pageContext = _jspx_page_context;
/* 2598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2600 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2601 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2602 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2604 */     _jspx_th_c_005fout_005f1.setValue("${param.screenid}");
/* 2605 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2606 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2607 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2608 */       return true;
/*      */     }
/* 2610 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2616 */     PageContext pageContext = _jspx_page_context;
/* 2617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2619 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2620 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2621 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2623 */     _jspx_th_c_005fforEach_005f0.setItems("${monitors}");
/*      */     
/* 2625 */     _jspx_th_c_005fforEach_005f0.setVar("mon");
/*      */     
/* 2627 */     _jspx_th_c_005fforEach_005f0.setVarStatus("msid");
/* 2628 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 2630 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2631 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 2633 */           out.write("\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" width=\"20\" height=\"15\"></td>\n\t\t<td><a href=\"javascript:void(0);\" style=\"font-family: Arial, Helvetica, sans-serif; font-weight: normal; font-size: 11px; color: #000000; text-decoration: none;\" onclick=\"javascript:showMonitorTreeElements('");
/* 2634 */           boolean bool; if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2635 */             return true;
/* 2636 */           out.write(39);
/* 2637 */           out.write(44);
/* 2638 */           out.write(39);
/* 2639 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2640 */             return true;
/* 2641 */           out.write("'),toggleDivInline('");
/* 2642 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2643 */             return true;
/* 2644 */           out.write(95);
/* 2645 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2646 */             return true;
/* 2647 */           out.write("$arrowShow");
/* 2648 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2649 */             return true;
/* 2650 */           out.write(95);
/* 2651 */           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2652 */             return true;
/* 2653 */           out.write("$arrowHide");
/* 2654 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2655 */             return true;
/* 2656 */           out.write(95);
/* 2657 */           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2658 */             return true;
/* 2659 */           out.write("')\"><div id='arrowShow");
/* 2660 */           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2661 */             return true;
/* 2662 */           out.write(95);
/* 2663 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2664 */             return true;
/* 2665 */           out.write("' style=\"display: inline;\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id='arrowHide");
/* 2666 */           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2667 */             return true;
/* 2668 */           out.write(95);
/* 2669 */           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2670 */             return true;
/* 2671 */           out.write("' style=\"display: none;\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div>");
/* 2672 */           if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2673 */             return true;
/* 2674 */           out.write("</a></td>\n\t</tr>\n\t<tr>\n\t\t<td width=\"5%\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"15\"></td>\n\t\t<td width=\"95%\"><div id=\"");
/* 2675 */           if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2676 */             return true;
/* 2677 */           out.write(95);
/* 2678 */           if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2679 */             return true;
/* 2680 */           out.write("\" style=\"DISPLAY: none\"><img border=\"0\" src=\"/images/icon_cogwheel.gif\"></div></td>\n\t</tr>\n  ");
/* 2681 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2682 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2686 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 2687 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2690 */         int tmp768_767 = 0; int[] tmp768_765 = _jspx_push_body_count_c_005fforEach_005f0; int tmp770_769 = tmp768_765[tmp768_767];tmp768_765[tmp768_767] = (tmp770_769 - 1); if (tmp770_769 <= 0) break;
/* 2691 */         out = _jspx_page_context.popBody(); }
/* 2692 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2694 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2695 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 2697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2702 */     PageContext pageContext = _jspx_page_context;
/* 2703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2705 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2706 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2707 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2709 */     _jspx_th_c_005fout_005f2.setValue("${monitorset}");
/* 2710 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2711 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2712 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2713 */       return true;
/*      */     }
/* 2715 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2721 */     PageContext pageContext = _jspx_page_context;
/* 2722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2724 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2725 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2726 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2728 */     _jspx_th_c_005fout_005f3.setValue("${mon}");
/* 2729 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2730 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2731 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2732 */       return true;
/*      */     }
/* 2734 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2740 */     PageContext pageContext = _jspx_page_context;
/* 2741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2743 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2744 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2745 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2747 */     _jspx_th_c_005fout_005f4.setValue("${monitorset}");
/* 2748 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2749 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2750 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2751 */       return true;
/*      */     }
/* 2753 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2759 */     PageContext pageContext = _jspx_page_context;
/* 2760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2762 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2763 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2764 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2766 */     _jspx_th_c_005fout_005f5.setValue("${mon}");
/* 2767 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2768 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2770 */       return true;
/*      */     }
/* 2772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2778 */     PageContext pageContext = _jspx_page_context;
/* 2779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2781 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2782 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2783 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2785 */     _jspx_th_c_005fout_005f6.setValue("${monitorset}");
/* 2786 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2787 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2788 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2789 */       return true;
/*      */     }
/* 2791 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2797 */     PageContext pageContext = _jspx_page_context;
/* 2798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2800 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2801 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2802 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2804 */     _jspx_th_c_005fout_005f7.setValue("${mon}");
/* 2805 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2806 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2808 */       return true;
/*      */     }
/* 2810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2816 */     PageContext pageContext = _jspx_page_context;
/* 2817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2819 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2820 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2821 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2823 */     _jspx_th_c_005fout_005f8.setValue("${monitorset}");
/* 2824 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2825 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2827 */       return true;
/*      */     }
/* 2829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2835 */     PageContext pageContext = _jspx_page_context;
/* 2836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2838 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2839 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2840 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2842 */     _jspx_th_c_005fout_005f9.setValue("${mon}");
/* 2843 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2844 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2845 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2846 */       return true;
/*      */     }
/* 2848 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2854 */     PageContext pageContext = _jspx_page_context;
/* 2855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2857 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2858 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2859 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2861 */     _jspx_th_c_005fout_005f10.setValue("${monitorset}");
/* 2862 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2863 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2864 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2865 */       return true;
/*      */     }
/* 2867 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2873 */     PageContext pageContext = _jspx_page_context;
/* 2874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2876 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2877 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2878 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2880 */     _jspx_th_c_005fout_005f11.setValue("${mon}");
/* 2881 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2882 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2883 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2884 */       return true;
/*      */     }
/* 2886 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2892 */     PageContext pageContext = _jspx_page_context;
/* 2893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2895 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2896 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2897 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2899 */     _jspx_th_c_005fout_005f12.setValue("${monitorset}");
/* 2900 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2901 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2902 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2903 */       return true;
/*      */     }
/* 2905 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2911 */     PageContext pageContext = _jspx_page_context;
/* 2912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2914 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2915 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2916 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2918 */     _jspx_th_c_005fout_005f13.setValue("${mon}");
/* 2919 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2920 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2921 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2922 */       return true;
/*      */     }
/* 2924 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2930 */     PageContext pageContext = _jspx_page_context;
/* 2931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2933 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2934 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2935 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2937 */     _jspx_th_c_005fout_005f14.setValue("${mon}");
/* 2938 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2939 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2940 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2941 */       return true;
/*      */     }
/* 2943 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2949 */     PageContext pageContext = _jspx_page_context;
/* 2950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2952 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2953 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 2954 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2956 */     _jspx_th_c_005fout_005f15.setValue("${monitorset}");
/* 2957 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 2958 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 2959 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2960 */       return true;
/*      */     }
/* 2962 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2968 */     PageContext pageContext = _jspx_page_context;
/* 2969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2971 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2972 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 2973 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2975 */     _jspx_th_c_005fout_005f16.setValue("${mon}");
/* 2976 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 2977 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2991 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2994 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 2996 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 2997 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2998 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2999 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3000 */       return true;
/*      */     }
/* 3002 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3003 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sap\chooseccmsmonitors_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */