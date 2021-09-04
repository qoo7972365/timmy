/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.dotnet.struts.DotNetGraphs;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
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
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class DotNetDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  919 */     AMLog.debug("JSP : " + debugMessage);
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
/* 1374 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1419 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1467 */       message = EnterpriseUtil.decodeString(message);
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
/*      */           catch (SQLException e) {
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
/* 1817 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1819 */               if (maxCol != null)
/* 1820 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1822 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1817 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1978 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2191 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2195 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2196 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2197 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2198 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2199 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2201 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2205 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2206 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2207 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
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
/* 2236 */       out.write("<!--$Id$-->\n");
/* 2237 */       DotNetGraphs dotnetgraph = null;
/* 2238 */       dotnetgraph = (DotNetGraphs)_jspx_page_context.getAttribute("dotnetgraph", 2);
/* 2239 */       if (dotnetgraph == null) {
/* 2240 */         dotnetgraph = new DotNetGraphs();
/* 2241 */         _jspx_page_context.setAttribute("dotnetgraph", dotnetgraph, 2);
/*      */       }
/* 2243 */       out.write(10);
/* 2244 */       out.write(10);
/* 2245 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2247 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2248 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2250 */       out.write(10);
/* 2251 */       out.write(10);
/* 2252 */       out.write(10);
/* 2253 */       out.write("\n\n\n \n");
/*      */       
/* 2255 */       String resourceid = (String)request.getAttribute("resourceid");
/* 2256 */       dotnetgraph.setresourceid(Integer.parseInt(resourceid));
/* 2257 */       dotnetgraph.updateproduceDataset();
/* 2258 */       dotnetgraph.setCategory("thread");
/* 2259 */       String haid = (String)request.getAttribute("haid");
/* 2260 */       String appname = (String)request.getAttribute("appName");
/* 2261 */       String name = (String)request.getAttribute("name");
/* 2262 */       Properties alert = (Properties)request.getAttribute("alert");
/* 2263 */       Properties data = (Properties)request.getAttribute("performance");
/* 2264 */       String encodeurl = (String)request.getAttribute("encodeurl");
/*      */       
/* 2266 */       out.write("\n  \n <br>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"31\" class=\"tableheadingbborder\">");
/* 2267 */       out.print(FormatUtil.getString("am.webclient.dotnet.threads"));
/* 2268 */       out.write("</td>\n    <td height=\"31\" class=\"tableheadingbborder\">");
/* 2269 */       out.print(FormatUtil.getString("am.webclient.dotnet.memory"));
/* 2270 */       out.write("</td>\n  </tr>\n  <tr> \n    <td width=\"40%\" height=\"31\" valign=\"top\" > <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n        <tr> \n          <td width=\"145%\" height=\"200\" class=\"rbborder\" valign=\"top\"><br> ");
/*      */       
/* 2272 */       TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2273 */       _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 2274 */       _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */       
/* 2276 */       _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("dotnetgraph");
/*      */       
/* 2278 */       _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */       
/* 2280 */       _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */       
/* 2282 */       _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */       
/* 2284 */       _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */       
/* 2286 */       _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.threads"));
/* 2287 */       int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 2288 */       if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 2289 */         if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2290 */           out = _jspx_page_context.pushBody();
/* 2291 */           _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 2292 */           _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */         }
/*      */         for (;;) {
/* 2295 */           out.write(" \n            ");
/* 2296 */           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 2297 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/* 2300 */         if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 2301 */           out = _jspx_page_context.popBody();
/*      */         }
/*      */       }
/* 2304 */       if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 2305 */         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */       }
/*      */       else {
/* 2308 */         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 2309 */         out.write(" <br></td>\n        </tr>\n        <tr> \n          <td  ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"rborder\">\n              <tr> \n                <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2310 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */           return;
/* 2312 */         out.write("</span></td>\n                <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2313 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */           return;
/* 2315 */         out.write("</span></td>\n                <td class=\"columnheadingb\" >&nbsp;</td>\n                <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2316 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */           return;
/* 2318 */         out.write("</span>&nbsp;</td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborder\" >");
/* 2319 */         out.print(FormatUtil.getString("am.webclient.dotnet.phythread"));
/* 2320 */         out.write("</td>\n                <td class=\"whitegrayborder\" > ");
/* 2321 */         out.print(formatNumberForDotNet(data.getProperty("PHYSICALTHREADS")));
/* 2322 */         out.write("\t\n                </td>\n                <td class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2323 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/* 2325 */         out.write("&attributeid=3304&period=-7')\"> \n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2326 */         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2327 */         out.write("\"></td>\n                <td class=\"whitegrayborder\" ><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2328 */         out.print(resourceid);
/* 2329 */         out.write("&attributeid=3304')\">");
/* 2330 */         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3304")));
/* 2331 */         out.write("</a></td>\n              </tr>\n              <tr> \n                <td class=\"yellowgrayborder\" >");
/* 2332 */         out.print(FormatUtil.getString("am.webclient.dotnet.logicalthread"));
/* 2333 */         out.write("</td>\n                <td class=\"yellowgrayborder\" >");
/* 2334 */         out.print(formatNumberForDotNet(data.getProperty("LOGICALTHREADS")));
/* 2335 */         out.write(" \n                </td>\n                <td class=\"yellowgrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2336 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */           return;
/* 2338 */         out.write("&attributeid=3303&period=-7')\"> \n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2339 */         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2340 */         out.write("\"></td>\n                <td class=\"yellowgrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2341 */         out.print(resourceid);
/* 2342 */         out.write("&attributeid=3303')\">");
/* 2343 */         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3303")));
/* 2344 */         out.write("</a></td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborder\" colspan=\"4\" align=\"right\"> <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2345 */         out.print(resourceid);
/* 2346 */         out.write("&attributeIDs=3304,3303&attributeToSelect=3303&redirectto=");
/* 2347 */         out.print(encodeurl);
/* 2348 */         out.write("'class=\"staticlinks\">");
/* 2349 */         out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2350 */         out.write("</a> \n                  &nbsp; </td>\n              </tr>\n            </table></td>\n        </tr>\n      </table></td>\n    ");
/*      */         
/* 2352 */         dotnetgraph.setCategory("mem");
/*      */         
/* 2354 */         out.write("\n    <td width=\"40%\" height=\"31\" align=\"right\" valign=\"top\" class=\"align-left\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n        <tr valign=\"top\"> \n          <td width=\"100%\" height=\"189\" colspan=\"4\" class=\"bottomborder\"><br>");
/*      */         
/* 2356 */         TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2357 */         _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 2358 */         _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */         
/* 2360 */         _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("dotnetgraph");
/*      */         
/* 2362 */         _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */         
/* 2364 */         _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */         
/* 2366 */         _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */         
/* 2368 */         _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */         
/* 2370 */         _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.heapsize"));
/* 2371 */         int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 2372 */         if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 2373 */           if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 2374 */             out = _jspx_page_context.pushBody();
/* 2375 */             _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 2376 */             _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */           }
/*      */           for (;;) {
/* 2379 */             out.write(" \n            ");
/* 2380 */             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 2381 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/* 2384 */           if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 2385 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/* 2388 */         if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 2389 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/*      */         }
/*      */         else {
/* 2392 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 2393 */           out.write("</td>\n        </tr>\n        <tr > \n          <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2394 */           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */             return;
/* 2396 */           out.write("</span></td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2397 */           if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */             return;
/* 2399 */           out.write("</span></td>\n          <td class=\"columnheadingb\" >&nbsp;</td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2400 */           if (_jspx_meth_fmt_005fmessage_005f5(_jspx_page_context))
/*      */             return;
/* 2402 */           out.write("</span>&nbsp;</td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2403 */           out.print(FormatUtil.getString("Heap Size"));
/* 2404 */           out.write("</td>\n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2405 */           out.print(formatNumberForDotNet(data.getProperty("CURRENTMEMORY")));
/* 2406 */           out.write(" \n          </td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2407 */           if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */             return;
/* 2409 */           out.write("&attributeid=3305&period=-7')\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2410 */           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2411 */           out.write("\"></a></td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2412 */           out.print(resourceid);
/* 2413 */           out.write("&attributeid=3305')\">");
/* 2414 */           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3305")));
/* 2415 */           out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"yellowgrayborder\" >");
/* 2416 */           out.print(FormatUtil.getString("am.webclient.dotnet.timeingc"));
/* 2417 */           out.write("</td>\n          <td width=\"100%\" class=\"yellowgrayborder\" >");
/* 2418 */           out.print(formatNumberForDotNet(data.getProperty("GC")));
/* 2419 */           out.write(" \n          </td>\n          <td width=\"100%\" class=\"yellowgrayborder\" ><img src=\"../images/spacer.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"></td>\n          <td width=\"100%\" class=\"yellowgrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2420 */           out.print(resourceid);
/* 2421 */           out.write("&attributeid=3305')\">");
/* 2422 */           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3313")));
/* 2423 */           out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" colspan=\"4\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2424 */           out.print(resourceid);
/* 2425 */           out.write("&attributeIDs=3305,3313&attributeToSelect=3305&redirectto=");
/* 2426 */           out.print(encodeurl);
/* 2427 */           out.write("'class=\"staticlinks\">");
/* 2428 */           out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2429 */           out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<br>\t\n");
/*      */           
/* 2431 */           dotnetgraph.setCategory("locks");
/*      */           
/* 2433 */           out.write("\n \n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"31\" class=\"tableheadingbborder\" >");
/* 2434 */           out.print(FormatUtil.getString("am.webclient.dotnet.locks"));
/* 2435 */           out.write("</td>\n    <td height=\"31\" class=\"tableheadingbborder\">");
/* 2436 */           out.print(FormatUtil.getString("am.webclient.dotnet.exception"));
/* 2437 */           out.write("</td>\n  </tr>\n  <tr> \n    <td width=\"50%\" height=\"31\" valign=\"top\" class=\"rborder\"> \n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr> \n          <td width=\"100%\" class=\"bottomborder\" height=\"200\" colspan=\"4\"> ");
/*      */           
/* 2439 */           TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2440 */           _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 2441 */           _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */           
/* 2443 */           _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("dotnetgraph");
/*      */           
/* 2445 */           _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */           
/* 2447 */           _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */           
/* 2449 */           _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */           
/* 2451 */           _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */           
/* 2453 */           _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.quelength"));
/* 2454 */           int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 2455 */           if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 2456 */             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 2457 */               out = _jspx_page_context.pushBody();
/* 2458 */               _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 2459 */               _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2462 */               out.write(" \n            ");
/* 2463 */               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 2464 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2467 */             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 2468 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2471 */           if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 2472 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/*      */           }
/*      */           else {
/* 2475 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 2476 */             out.write(" </td>\n        </tr>\n        <tr > \n          <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2477 */             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */               return;
/* 2479 */             out.write("</span></td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2480 */             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_page_context))
/*      */               return;
/* 2482 */             out.write("</span></td>\n          <td class=\"columnheadingb\" >&nbsp;</td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2483 */             if (_jspx_meth_fmt_005fmessage_005f8(_jspx_page_context))
/*      */               return;
/* 2485 */             out.write("</span>&nbsp;</td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2486 */             out.print(FormatUtil.getString("am.webclient.dotnet.quelength"));
/* 2487 */             out.write("</td>\n          <td width=\"100%\" class=\"whitegrayborder\" > ");
/* 2488 */             out.print(formatNumberForDotNet(data.getProperty("QUEUELENGTH")));
/* 2489 */             out.write("\t\n          </td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2490 */             if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */               return;
/* 2492 */             out.write("&attributeid=3306&period=-7')\"> \n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2493 */             out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2494 */             out.write("\"></a></td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2495 */             out.print(resourceid);
/* 2496 */             out.write("&attributeid=3306')\">");
/* 2497 */             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3306")));
/* 2498 */             out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"yellowgrayborder\" >");
/* 2499 */             out.print(FormatUtil.getString("am.webclient.dotnet.connectionpermin"));
/* 2500 */             out.write("</td>\n          <td width=\"100%\" class=\"yellowgrayborder\" >");
/* 2501 */             out.print(formatNumberForDotNet(data.getProperty("CONTENTIONPERMIN")));
/* 2502 */             out.write(" \n          </td>\n          <td width=\"100%\" class=\"yellowgrayborder\" >&nbsp;</td>\n          <td width=\"100%\" class=\"yellowgrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2503 */             out.print(resourceid);
/* 2504 */             out.write("&attributeid=3307')\">");
/* 2505 */             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3307")));
/* 2506 */             out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" colspan=\"4\" align=\"right\"> \n            <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2507 */             out.print(resourceid);
/* 2508 */             out.write("&attributeIDs=3306,3307&attributeToSelect=3306&redirectto=");
/* 2509 */             out.print(encodeurl);
/* 2510 */             out.write("'class=\"staticlinks\">");
/* 2511 */             out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2512 */             out.write("</a> \n            &nbsp; </td>\n        </tr>\n      </table>\n    </td>\n    ");
/*      */             
/* 2514 */             dotnetgraph.setCategory("excep");
/* 2515 */             System.out.println("Data in Dotnet is " + data);
/*      */             
/* 2517 */             out.write("\n    <td width=\"50%\" height=\"31\" align=\"right\" valign=\"top\" class=\"align-left\"> \n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n        <tr> \n          <td width=\"100%\" class=\"bottomborder\" height=\"200\" colspan=\"4\"> ");
/*      */             
/* 2519 */             TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2520 */             _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 2521 */             _jspx_th_awolf_005ftimechart_005f3.setParent(null);
/*      */             
/* 2523 */             _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("dotnetgraph");
/*      */             
/* 2525 */             _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */             
/* 2527 */             _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */             
/* 2529 */             _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */             
/* 2531 */             _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */             
/* 2533 */             _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.exceptionspermin"));
/* 2534 */             int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 2535 */             if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 2536 */               if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 2537 */                 out = _jspx_page_context.pushBody();
/* 2538 */                 _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 2539 */                 _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2542 */                 out.write(" \n            ");
/* 2543 */                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 2544 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2547 */               if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 2548 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2551 */             if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 2552 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/*      */             }
/*      */             else {
/* 2555 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 2556 */               out.write(" </td>\n        </tr>\n        <tr> \n          <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2557 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_page_context))
/*      */                 return;
/* 2559 */               out.write("</span></td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2560 */               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_page_context))
/*      */                 return;
/* 2562 */               out.write("</span></td>\n          <td class=\"columnheadingb\" >&nbsp;</td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2563 */               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_page_context))
/*      */                 return;
/* 2565 */               out.write("</span>&nbsp;</td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2566 */               out.print(FormatUtil.getString("am.webclient.dotnet.exceptionspermin"));
/* 2567 */               out.write("</td>\n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2568 */               out.print(formatNumberForDotNet(data.getProperty("EXCEPTIONSPERMIN")));
/* 2569 */               out.write(" \n          </td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><img src=\"../images/spacer.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"></td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2570 */               out.print(resourceid);
/* 2571 */               out.write("&attributeid=3308')\">");
/* 2572 */               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3308")));
/* 2573 */               out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" colspan=\"4\" class=\"yellowgrayborder\" >&nbsp;</td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" colspan=\"4\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2574 */               out.print(resourceid);
/* 2575 */               out.write("&attributeIDs=3308&attributeToSelect=3308&redirectto=");
/* 2576 */               out.print(encodeurl);
/* 2577 */               out.write("'class=\"staticlinks\">");
/* 2578 */               out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2579 */               out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<br>\n\n\t\n\n");
/*      */               
/* 2581 */               dotnetgraph.setCategory("jit");
/*      */               
/* 2583 */               out.write("\n \n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"31\" class=\"tableheadingbborder\">");
/* 2584 */               out.print(FormatUtil.getString("am.webclient.dotnet.jit"));
/* 2585 */               out.write("</td>\n    <td height=\"31\" class=\"tableheadingbborder\">");
/* 2586 */               out.print(FormatUtil.getString("am.webclient.dotnet.security"));
/* 2587 */               out.write("</td>\n  </tr>\n  <tr> \n    <td width=\"40%\" height=\"31\" valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n        <tr> \n          <td width=\"100%\" class=\"bottomborder\" height=\"200\" colspan=\"4\"> ");
/*      */               
/* 2589 */               TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2590 */               _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 2591 */               _jspx_th_awolf_005ftimechart_005f4.setParent(null);
/*      */               
/* 2593 */               _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("dotnetgraph");
/*      */               
/* 2595 */               _jspx_th_awolf_005ftimechart_005f4.setWidth("300");
/*      */               
/* 2597 */               _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */               
/* 2599 */               _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */               
/* 2601 */               _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */               
/* 2603 */               _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.pertimeinjit"));
/* 2604 */               int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 2605 */               if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 2606 */                 if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 2607 */                   out = _jspx_page_context.pushBody();
/* 2608 */                   _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 2609 */                   _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2612 */                   out.write(" \n            ");
/* 2613 */                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 2614 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2617 */                 if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 2618 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2621 */               if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 2622 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/*      */               }
/*      */               else {
/* 2625 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 2626 */                 out.write(" </td>\n        </tr>\n        <tr > \n          <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2627 */                 if (_jspx_meth_fmt_005fmessage_005f12(_jspx_page_context))
/*      */                   return;
/* 2629 */                 out.write("</span></td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2630 */                 if (_jspx_meth_fmt_005fmessage_005f13(_jspx_page_context))
/*      */                   return;
/* 2632 */                 out.write("</span></td>\n          <td class=\"columnheadingb\" >&nbsp;</td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2633 */                 if (_jspx_meth_fmt_005fmessage_005f14(_jspx_page_context))
/*      */                   return;
/* 2635 */                 out.write("</span>&nbsp;</td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"whitegrayborder\" >");
/* 2636 */                 out.print(FormatUtil.getString("am.webclient.dotnet.pertimeinjit"));
/* 2637 */                 out.write("</td>\n          <td width=\"100%\" class=\"whitegrayborder\" > ");
/* 2638 */                 out.print(formatNumberForDotNet(data.getProperty("JIT")));
/* 2639 */                 out.write("\t\n          </td>\n          <td width=\"100%\" class=\"whitegrayborder\" >&nbsp;</td>\n          <td width=\"100%\" class=\"whitegrayborder\" ><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2640 */                 out.print(resourceid);
/* 2641 */                 out.write("&attributeid=3314')\">");
/* 2642 */                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3314")));
/* 2643 */                 out.write("</a></td>\n        </tr>\n        <tr> \n          <td width=\"100%\" class=\"yellowgrayborder\" colspan=\"4\" align=\"right\"> \n            <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2644 */                 out.print(resourceid);
/* 2645 */                 out.write("&attributeIDs=3314&attributeToSelect=3314&redirectto=");
/* 2646 */                 out.print(encodeurl);
/* 2647 */                 out.write("'class=\"staticlinks\">");
/* 2648 */                 out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2649 */                 out.write("</a>&nbsp; \n          </td>\n        </tr>\n      </table></td>\n    ");
/*      */                 
/* 2651 */                 dotnetgraph.setCategory("security");
/* 2652 */                 System.out.println("Data in Dotnet is " + data);
/*      */                 
/* 2654 */                 out.write("\n    <td width=\"40%\" height=\"31\" align=\"right\" valign=\"top\" class=\"align-left\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        <tr> \n          <td width=\"100%\" class=\"bottomborder\" height=\"200\" colspan=\"4\"> ");
/*      */                 
/* 2656 */                 TimeChart _jspx_th_awolf_005ftimechart_005f5 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 2657 */                 _jspx_th_awolf_005ftimechart_005f5.setPageContext(_jspx_page_context);
/* 2658 */                 _jspx_th_awolf_005ftimechart_005f5.setParent(null);
/*      */                 
/* 2660 */                 _jspx_th_awolf_005ftimechart_005f5.setDataSetProducer("dotnetgraph");
/*      */                 
/* 2662 */                 _jspx_th_awolf_005ftimechart_005f5.setWidth("300");
/*      */                 
/* 2664 */                 _jspx_th_awolf_005ftimechart_005f5.setHeight("170");
/*      */                 
/* 2666 */                 _jspx_th_awolf_005ftimechart_005f5.setLegend("true");
/*      */                 
/* 2668 */                 _jspx_th_awolf_005ftimechart_005f5.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                 
/* 2670 */                 _jspx_th_awolf_005ftimechart_005f5.setYaxisLabel(FormatUtil.getString("am.webclient.dotnet.runcheck"));
/* 2671 */                 int _jspx_eval_awolf_005ftimechart_005f5 = _jspx_th_awolf_005ftimechart_005f5.doStartTag();
/* 2672 */                 if (_jspx_eval_awolf_005ftimechart_005f5 != 0) {
/* 2673 */                   if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 2674 */                     out = _jspx_page_context.pushBody();
/* 2675 */                     _jspx_th_awolf_005ftimechart_005f5.setBodyContent((BodyContent)out);
/* 2676 */                     _jspx_th_awolf_005ftimechart_005f5.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2679 */                     out.write(" \n            ");
/* 2680 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f5.doAfterBody();
/* 2681 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2684 */                   if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 2685 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2688 */                 if (_jspx_th_awolf_005ftimechart_005f5.doEndTag() == 5) {
/* 2689 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/*      */                 }
/*      */                 else {
/* 2692 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/* 2693 */                   out.write(" </td>\n        </tr>\n        <tr > \n          <td height=\"20\" class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2694 */                   if (_jspx_meth_fmt_005fmessage_005f15(_jspx_page_context))
/*      */                     return;
/* 2696 */                   out.write("</span></td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2697 */                   if (_jspx_meth_fmt_005fmessage_005f16(_jspx_page_context))
/*      */                     return;
/* 2699 */                   out.write("</span></td>\n          <td class=\"columnheadingb\" >&nbsp;</td>\n          <td class=\"columnheadingb\" ><span class=\"bodytextbold\">");
/* 2700 */                   if (_jspx_meth_fmt_005fmessage_005f17(_jspx_page_context))
/*      */                     return;
/* 2702 */                   out.write("</span>&nbsp;</td>\n        </tr>\n        <tr> \n          <td class=\"whitegrayborder\" >");
/* 2703 */                   out.print(FormatUtil.getString("am.webclient.dotnet.runcheckpermin"));
/* 2704 */                   out.write("</td>\n          <td  class=\"whitegrayborder\" >");
/* 2705 */                   out.print(formatNumberForDotNet(data.getProperty("RUNTIMECHECKSPERMIN")));
/* 2706 */                   out.write(" \n          </td>\n          <td  class=\"whitegrayborder\" >&nbsp;</td>\n          <td  class=\"whitegrayborder\" ><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2707 */                   out.print(resourceid);
/* 2708 */                   out.write("&attributeid=3315')\">");
/* 2709 */                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3315")));
/* 2710 */                   out.write("</a></td>\n        </tr>\n        <tr> \n          <td  class=\"yellowgrayborder\" colspan=\"4\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2711 */                   out.print(resourceid);
/* 2712 */                   out.write("&attributeIDs=3315&attributeToSelect=3315&redirectto=");
/* 2713 */                   out.print(encodeurl);
/* 2714 */                   out.write("'class=\"staticlinks\">");
/* 2715 */                   out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2716 */                   out.write("</a>&nbsp;</td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<br>\t\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr >\n  <td height=\"26\"  class=\"tableheadingtrans\">");
/* 2717 */                   out.print(FormatUtil.getString("am.webclient.dotnet.appdetails"));
/* 2718 */                   out.write("</td>\n  </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"39%\" height=\"28\"  class=\"columnheading\">");
/* 2719 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.name"));
/* 2720 */                   out.write("</td>\n  <td width=\"21%\" height=\"28\"  class=\"columnheading\">");
/* 2721 */                   out.print(FormatUtil.getString("am.webclient.dotnet.activesession"));
/* 2722 */                   out.write("</td>\n  <td width=\"19%\" height=\"28\"  class=\"columnheading\">");
/* 2723 */                   out.print(FormatUtil.getString("am.webclient.dotnet.requestpermin"));
/* 2724 */                   out.write("</td>\n  <td width=\"6%\" align=\"center\" height=\"28\"  class=\"columnheading\">");
/* 2725 */                   out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2726 */                   out.write("</td>\n  <td height=\"28\" width=\"15%\"   class=\"columnheading\">");
/* 2727 */                   out.print(FormatUtil.getString("am.webclient.dotnet.confalert"));
/* 2728 */                   out.write("</td>\n  </tr>\n  ");
/*      */                   
/* 2730 */                   EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2731 */                   _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2732 */                   _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                   
/* 2734 */                   _jspx_th_logic_005fempty_005f0.setName("dbdetails");
/* 2735 */                   int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2736 */                   if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                     for (;;) {
/* 2738 */                       out.write("\n  <tr>\n  <td height=\"28\" colspan=\"5\" align=\"center\" class=\"whitegrayborder\">");
/* 2739 */                       out.print(FormatUtil.getString("am.webclient.dotnet.noappavailable"));
/* 2740 */                       out.write("</td>\n  </tr>\n  ");
/* 2741 */                       int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2742 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2746 */                   if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2747 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                   }
/*      */                   else {
/* 2750 */                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2751 */                     out.write(10);
/* 2752 */                     out.write(32);
/* 2753 */                     out.write(32);
/*      */                     
/* 2755 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2756 */                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2757 */                     _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                     
/* 2759 */                     _jspx_th_logic_005fnotEmpty_005f0.setName("dbdetails");
/* 2760 */                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2761 */                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                       for (;;) {
/* 2763 */                         out.write(10);
/* 2764 */                         out.write(32);
/* 2765 */                         out.write(32);
/*      */                         
/* 2767 */                         String bgcolor = "whitegrayborder";
/* 2768 */                         Hashtable dbinfo = (Hashtable)request.getAttribute("dbdetails");
/* 2769 */                         int l = 0;
/* 2770 */                         for (Enumeration e = dbinfo.keys(); e.hasMoreElements();)
/*      */                         {
/* 2772 */                           String dbname = (String)e.nextElement();
/* 2773 */                           Properties dbprop = (Properties)dbinfo.get(dbname);
/* 2774 */                           if (l % 2 == 0)
/*      */                           {
/* 2776 */                             bgcolor = "whitegrayborder";
/*      */                           }
/*      */                           else {
/* 2779 */                             bgcolor = "yellowgrayborder";
/*      */                           }
/* 2781 */                           l++;
/*      */                           
/* 2783 */                           out.write("\n  <tr>\n  <td class=\"");
/* 2784 */                           out.print(bgcolor);
/* 2785 */                           out.write("\"><a href=\"/DotNet.do?name=");
/* 2786 */                           out.print(name);
/* 2787 */                           out.write("&haid=");
/* 2788 */                           out.print(haid);
/* 2789 */                           out.write("&appName=");
/* 2790 */                           out.print(appname);
/* 2791 */                           out.write("&resourceid=");
/* 2792 */                           out.print(resourceid);
/* 2793 */                           out.write("&details=DB&dbid=");
/* 2794 */                           out.print(dbprop.getProperty("DBID"));
/* 2795 */                           out.write("&dbname=");
/* 2796 */                           out.print(dbname);
/* 2797 */                           out.write("\" class=\"ResourceName\">");
/* 2798 */                           out.print(dbname);
/* 2799 */                           out.write("</a></td>\n  <td class=\"");
/* 2800 */                           out.print(bgcolor);
/* 2801 */                           out.write("\" align=\"center\"> ");
/* 2802 */                           out.print(dbprop.getProperty("ACTIVESESSIONS"));
/* 2803 */                           out.write(" &nbsp;</td>\n  <td class=\"");
/* 2804 */                           out.print(bgcolor);
/* 2805 */                           out.write("\" align=\"center\"> ");
/* 2806 */                           out.print(dbprop.getProperty("REQUESTSPERMIN"));
/* 2807 */                           out.write(" &nbsp;</td>\n  <td class=\"");
/* 2808 */                           out.print(bgcolor);
/* 2809 */                           out.write("\" align=\"left\"><center>\n  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2810 */                           out.print(dbprop.getProperty("DBID"));
/* 2811 */                           out.write("&attributeid=3321')\">");
/* 2812 */                           out.print(getSeverityImageForHealth(alert.getProperty(dbprop.getProperty("DBID") + "#" + "3321")));
/* 2813 */                           out.write("</a>\n  </center></td>\n  <td width=\"15%\" align=\"center\" class=\"");
/* 2814 */                           out.print(bgcolor);
/* 2815 */                           out.write("\"><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2816 */                           out.print(dbprop.getProperty("DBID"));
/* 2817 */                           out.write("&attributeIDs=3325,3321&attributeToSelect=3325&redirectto=");
/* 2818 */                           out.print(encodeurl);
/* 2819 */                           out.write("'>\n  <img src=\"/images/icon_associateaction.gif\" title=\"Alert Configuration\" border=\"0\" ></a></td>\n  </tr>\n  ");
/*      */                         }
/*      */                         
/*      */ 
/* 2823 */                         out.write(10);
/* 2824 */                         out.write(32);
/* 2825 */                         out.write(32);
/* 2826 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2827 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2831 */                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2832 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                     }
/*      */                     else {
/* 2835 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2836 */                       out.write("\n  </table>\n");
/*      */                     }
/* 2838 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2839 */         out = _jspx_out;
/* 2840 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2841 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2842 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2845 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2851 */     PageContext pageContext = _jspx_page_context;
/* 2852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2854 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2855 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2856 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 2857 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2858 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2859 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2860 */         out = _jspx_page_context.pushBody();
/* 2861 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2862 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2865 */         out.write("table.heading.attribute");
/* 2866 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2867 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2870 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2871 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2874 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2875 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2876 */       return true;
/*      */     }
/* 2878 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2884 */     PageContext pageContext = _jspx_page_context;
/* 2885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2887 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2888 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2889 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/* 2890 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2891 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2892 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2893 */         out = _jspx_page_context.pushBody();
/* 2894 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2895 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2898 */         out.write("table.heading.value");
/* 2899 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2900 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2903 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2904 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2907 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2908 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2909 */       return true;
/*      */     }
/* 2911 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2917 */     PageContext pageContext = _jspx_page_context;
/* 2918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2920 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2921 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2922 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/* 2923 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2924 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 2925 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2926 */         out = _jspx_page_context.pushBody();
/* 2927 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 2928 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2931 */         out.write("table.heading.status");
/* 2932 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 2933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2936 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2937 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2940 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2941 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2942 */       return true;
/*      */     }
/* 2944 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2950 */     PageContext pageContext = _jspx_page_context;
/* 2951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2953 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2954 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2955 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2957 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 2958 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2959 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2961 */       return true;
/*      */     }
/* 2963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2969 */     PageContext pageContext = _jspx_page_context;
/* 2970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2972 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2973 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2974 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2976 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 2977 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2978 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2979 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2980 */       return true;
/*      */     }
/* 2982 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2988 */     PageContext pageContext = _jspx_page_context;
/* 2989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2991 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2992 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2993 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/* 2994 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2995 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 2996 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 2997 */         out = _jspx_page_context.pushBody();
/* 2998 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 2999 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3002 */         out.write("table.heading.attribute");
/* 3003 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 3004 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3007 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3008 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3011 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3012 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3013 */       return true;
/*      */     }
/* 3015 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3016 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3021 */     PageContext pageContext = _jspx_page_context;
/* 3022 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3024 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3025 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3026 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/* 3027 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3028 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3029 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3030 */         out = _jspx_page_context.pushBody();
/* 3031 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3032 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3035 */         out.write("table.heading.value");
/* 3036 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3037 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3040 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3041 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3044 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3045 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3046 */       return true;
/*      */     }
/* 3048 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3054 */     PageContext pageContext = _jspx_page_context;
/* 3055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3057 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3058 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3059 */     _jspx_th_fmt_005fmessage_005f5.setParent(null);
/* 3060 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3061 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3062 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3063 */         out = _jspx_page_context.pushBody();
/* 3064 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3065 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3068 */         out.write("table.heading.status");
/* 3069 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3070 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3073 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3074 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3077 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3078 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3079 */       return true;
/*      */     }
/* 3081 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3087 */     PageContext pageContext = _jspx_page_context;
/* 3088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3090 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3091 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3092 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 3094 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3095 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3096 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3097 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3098 */       return true;
/*      */     }
/* 3100 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3106 */     PageContext pageContext = _jspx_page_context;
/* 3107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3109 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3110 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3111 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/* 3112 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3113 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3114 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3115 */         out = _jspx_page_context.pushBody();
/* 3116 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3117 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3120 */         out.write("table.heading.attribute");
/* 3121 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3122 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3125 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3126 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3129 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3130 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3131 */       return true;
/*      */     }
/* 3133 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3139 */     PageContext pageContext = _jspx_page_context;
/* 3140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3142 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3143 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3144 */     _jspx_th_fmt_005fmessage_005f7.setParent(null);
/* 3145 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3146 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3147 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3148 */         out = _jspx_page_context.pushBody();
/* 3149 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3150 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3153 */         out.write("table.heading.value");
/* 3154 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3158 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3159 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3162 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3163 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3164 */       return true;
/*      */     }
/* 3166 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3172 */     PageContext pageContext = _jspx_page_context;
/* 3173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3175 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3176 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3177 */     _jspx_th_fmt_005fmessage_005f8.setParent(null);
/* 3178 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3179 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3180 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3181 */         out = _jspx_page_context.pushBody();
/* 3182 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3183 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3186 */         out.write("table.heading.status");
/* 3187 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3188 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3191 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3192 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3195 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3196 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3197 */       return true;
/*      */     }
/* 3199 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3205 */     PageContext pageContext = _jspx_page_context;
/* 3206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3208 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3209 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3210 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 3212 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3213 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3214 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3215 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3216 */       return true;
/*      */     }
/* 3218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3224 */     PageContext pageContext = _jspx_page_context;
/* 3225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3227 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3228 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3229 */     _jspx_th_fmt_005fmessage_005f9.setParent(null);
/* 3230 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3231 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3232 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3233 */         out = _jspx_page_context.pushBody();
/* 3234 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3235 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3238 */         out.write("table.heading.attribute");
/* 3239 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3240 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3243 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3244 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3247 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3248 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3249 */       return true;
/*      */     }
/* 3251 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3257 */     PageContext pageContext = _jspx_page_context;
/* 3258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3260 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3261 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3262 */     _jspx_th_fmt_005fmessage_005f10.setParent(null);
/* 3263 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3264 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3265 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3266 */         out = _jspx_page_context.pushBody();
/* 3267 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3268 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3271 */         out.write("table.heading.value");
/* 3272 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3273 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3276 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3277 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3280 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3281 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3282 */       return true;
/*      */     }
/* 3284 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3285 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3290 */     PageContext pageContext = _jspx_page_context;
/* 3291 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3293 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3294 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3295 */     _jspx_th_fmt_005fmessage_005f11.setParent(null);
/* 3296 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3297 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 3298 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3299 */         out = _jspx_page_context.pushBody();
/* 3300 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 3301 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3304 */         out.write("table.heading.status");
/* 3305 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 3306 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3309 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3310 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3313 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3314 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3315 */       return true;
/*      */     }
/* 3317 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3323 */     PageContext pageContext = _jspx_page_context;
/* 3324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3326 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3327 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3328 */     _jspx_th_fmt_005fmessage_005f12.setParent(null);
/* 3329 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3330 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 3331 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3332 */         out = _jspx_page_context.pushBody();
/* 3333 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 3334 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3337 */         out.write("table.heading.attribute");
/* 3338 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 3339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3342 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3343 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3346 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3347 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3348 */       return true;
/*      */     }
/* 3350 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3356 */     PageContext pageContext = _jspx_page_context;
/* 3357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3359 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3360 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3361 */     _jspx_th_fmt_005fmessage_005f13.setParent(null);
/* 3362 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3363 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 3364 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3365 */         out = _jspx_page_context.pushBody();
/* 3366 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 3367 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3370 */         out.write("table.heading.value");
/* 3371 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 3372 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3375 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3376 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3379 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3380 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3381 */       return true;
/*      */     }
/* 3383 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3389 */     PageContext pageContext = _jspx_page_context;
/* 3390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3392 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3393 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3394 */     _jspx_th_fmt_005fmessage_005f14.setParent(null);
/* 3395 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3396 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 3397 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3398 */         out = _jspx_page_context.pushBody();
/* 3399 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 3400 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3403 */         out.write("table.heading.status");
/* 3404 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 3405 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3408 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3409 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3412 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3413 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3414 */       return true;
/*      */     }
/* 3416 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3422 */     PageContext pageContext = _jspx_page_context;
/* 3423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3425 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3426 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 3427 */     _jspx_th_fmt_005fmessage_005f15.setParent(null);
/* 3428 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 3429 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 3430 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3431 */         out = _jspx_page_context.pushBody();
/* 3432 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 3433 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3436 */         out.write("table.heading.attribute");
/* 3437 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 3438 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3441 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3442 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3445 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 3446 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3447 */       return true;
/*      */     }
/* 3449 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3450 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3455 */     PageContext pageContext = _jspx_page_context;
/* 3456 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3458 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3459 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 3460 */     _jspx_th_fmt_005fmessage_005f16.setParent(null);
/* 3461 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 3462 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 3463 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3464 */         out = _jspx_page_context.pushBody();
/* 3465 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 3466 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3469 */         out.write("table.heading.value");
/* 3470 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 3471 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3474 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3475 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3478 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 3479 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3480 */       return true;
/*      */     }
/* 3482 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3488 */     PageContext pageContext = _jspx_page_context;
/* 3489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3491 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3492 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 3493 */     _jspx_th_fmt_005fmessage_005f17.setParent(null);
/* 3494 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 3495 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 3496 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3497 */         out = _jspx_page_context.pushBody();
/* 3498 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 3499 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3502 */         out.write("table.heading.status");
/* 3503 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 3504 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3507 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3508 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3511 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 3512 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3513 */       return true;
/*      */     }
/* 3515 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3516 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DotNetDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */