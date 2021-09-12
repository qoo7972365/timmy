/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ 
/*      */ public final class TemplateServerList_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2196 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2200 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2211 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2215 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2222 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2224 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
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
/* 2251 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/* 2252 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2254 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2255 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2256 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2258 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2260 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2262 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2264 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2265 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2266 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2267 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2270 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2271 */         String available = null;
/* 2272 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2273 */         out.write(10);
/*      */         
/* 2275 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2276 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2277 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2279 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2281 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2283 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2285 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2286 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2287 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2288 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2291 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2292 */           String unavailable = null;
/* 2293 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2294 */           out.write(10);
/*      */           
/* 2296 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2297 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2298 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2300 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2302 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2304 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2306 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2307 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2308 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2309 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2312 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2313 */             String unmanaged = null;
/* 2314 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2315 */             out.write(10);
/*      */             
/* 2317 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2318 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2319 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2321 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2323 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2325 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2327 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2328 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2329 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2330 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2333 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2334 */               String scheduled = null;
/* 2335 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2336 */               out.write(10);
/*      */               
/* 2338 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2339 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2340 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2342 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2344 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2346 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2348 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2349 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2350 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2351 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2354 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2355 */                 String critical = null;
/* 2356 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2357 */                 out.write(10);
/*      */                 
/* 2359 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2360 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2361 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2363 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2365 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2367 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2369 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2370 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2371 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2372 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2375 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2376 */                   String clear = null;
/* 2377 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2378 */                   out.write(10);
/*      */                   
/* 2380 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2381 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2382 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2384 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2386 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2388 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2390 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2391 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2392 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2393 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2396 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2397 */                     String warning = null;
/* 2398 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2399 */                     out.write(10);
/* 2400 */                     out.write(10);
/*      */                     
/* 2402 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2403 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2405 */                     out.write(10);
/* 2406 */                     out.write(10);
/* 2407 */                     out.write(10);
/* 2408 */                     out.write(10);
/*      */                     
/*      */                     try
/*      */                     {
/* 2412 */                       out.write(10);
/* 2413 */                       out.write(10);
/*      */                       
/* 2415 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2416 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2417 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2418 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2419 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;)
/*      */                         {
/* 2422 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2423 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2424 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2426 */                           _jspx_th_c_005fwhen_005f0.setTest("${not empty serverdetaillist}");
/* 2427 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2428 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2430 */                               out.write("\n\n<table width=\"100%\" cellpadding=\"2\" cellspacing=\"2\" border=\"0\" class=\"glance-table-bg\">\n\n                <tr>\n                  <td class=\"bodytext\" width=\"14%\" align=\"left\"  ");
/* 2431 */                               if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2432 */                                 out.write("style=\"display:none\"");
/*      */                               }
/* 2434 */                               out.write(62);
/* 2435 */                               out.print(FormatUtil.getString("am.webclient.processtemplate.selectmontype"));
/* 2436 */                               out.write("</td>\n                  <td class=\"bodytext\" width=\"28%\" align=\"left\"  ");
/* 2437 */                               if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2438 */                                 out.write("style=\"display:none\"");
/*      */                               }
/* 2440 */                               out.write(">\n\t\t\t\t\t<select id=\"servertypelist\" name=\"servertypelist\" style=\"background-color:#FDFEF2;width:200px\"  onchange=\"javascript:getServerList();\">\n\t\t\t\t\t  \t");
/* 2441 */                               if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2443 */                               out.write("\t\n\t\t\t  \t\t </select>\t\n                  </td>\n                  <td width=\"12%\" align=\"left\" class=\"bodytext\">");
/* 2444 */                               out.print(FormatUtil.getString("am.webclient.processtemplate.selectmonitor"));
/* 2445 */                               out.write("</td>\n                  <td width=\"33%\" align=\"left\" class=\"bodytext\">\n\t\t\t\t\t  ");
/* 2446 */                               if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2448 */                               out.write("             \n                </td>\n\t\t\t\t<td width=\"12%\" align=\"center\">\n\t\t\t\t<a href='javascript:getProcessList()' class='staticlinks'>");
/* 2449 */                               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                 return;
/* 2451 */                               out.write("</a> ");
/* 2452 */                               out.write("\n\t\t\t\t</td>\n                </tr >\n</table>\n");
/* 2453 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2454 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2458 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2459 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 2462 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2463 */                           if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                             return;
/* 2465 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2466 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2470 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2471 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 2474 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2475 */                       out.write(10);
/*      */                     } catch (Exception exc) {
/* 2477 */                       exc.printStackTrace();
/*      */                     }
/* 2479 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n<script type=\"text/javascript\">\n$('document').ready(function(){\n\t$('#servertypelist').chosen({ // NO I18N\n\t\twidth: \"200px\", // NO I18N\n\t\tno_results_text: '");
/* 2480 */                     out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/* 2481 */                     out.write("',\n\t\tdisable_search_threshold: 5\n\t});\n\t\n\t$('select[name=\"serverlist\"]').chosen({ // NO I18N\n\t\twidth: \"250px\", // NO I18N\n\t\tno_results_text: '");
/* 2482 */                     out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/* 2483 */                     out.write("',\n\t\tdisable_search_threshold: 5\n\t});\n})\ngetServerList();\n</script>\n\n");
/*      */                   }
/* 2485 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2486 */         out = _jspx_out;
/* 2487 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2488 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2489 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2492 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2498 */     PageContext pageContext = _jspx_page_context;
/* 2499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2501 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2502 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2503 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2505 */     _jspx_th_c_005fforEach_005f0.setItems("${requestScope.serverdetaillist}");
/*      */     
/* 2507 */     _jspx_th_c_005fforEach_005f0.setVar("serverrow");
/*      */     
/* 2509 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowcount");
/* 2510 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 2512 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2513 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 2515 */           out.write("\n\t\t\t\t\t\t\t");
/* 2516 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2517 */             return true;
/* 2518 */           out.write("\n\t\t\t  \t\t \t");
/* 2519 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2520 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2524 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 2525 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2528 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f0; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 2529 */         out = _jspx_page_context.popBody(); }
/* 2530 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2532 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2533 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 2535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2540 */     PageContext pageContext = _jspx_page_context;
/* 2541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2543 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2544 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2545 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 2546 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2547 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 2549 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 2550 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2551 */           return true;
/* 2552 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 2553 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2554 */           return true;
/* 2555 */         out.write("\n\t\t\t\t\t\t\t");
/* 2556 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2557 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2561 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2562 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2563 */       return true;
/*      */     }
/* 2565 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2571 */     PageContext pageContext = _jspx_page_context;
/* 2572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2574 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2575 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2576 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 2578 */     _jspx_th_c_005fwhen_005f1.setTest("${param.optionSelected==serverrow.key}");
/* 2579 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2580 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 2582 */         out.write("\n\t\t\t\t\t\t\t\t<option value='");
/* 2583 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2584 */           return true;
/* 2585 */         out.write("' selected='selected'> ");
/* 2586 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2587 */           return true;
/* 2588 */         out.write(" </option>\n\t\t\t\t\t\t\t\t");
/* 2589 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2590 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2594 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2595 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2596 */       return true;
/*      */     }
/* 2598 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2604 */     PageContext pageContext = _jspx_page_context;
/* 2605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2607 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2608 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2609 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2611 */     _jspx_th_c_005fout_005f0.setValue("${rowcount.count}");
/* 2612 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2613 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2614 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2615 */       return true;
/*      */     }
/* 2617 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2623 */     PageContext pageContext = _jspx_page_context;
/* 2624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2626 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2627 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2628 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 2630 */     _jspx_th_c_005fout_005f1.setValue("${servertypei18nkey[serverrow.key]}");
/* 2631 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2632 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2634 */       return true;
/*      */     }
/* 2636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2642 */     PageContext pageContext = _jspx_page_context;
/* 2643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2645 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2646 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2647 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 2648 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2649 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2651 */         out.write("\n\t\t\t\t\t\t\t\t<option value='");
/* 2652 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2653 */           return true;
/* 2654 */         out.write("' > ");
/* 2655 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2656 */           return true;
/* 2657 */         out.write(" </option>\n\t\t\t\t\t\t\t\t");
/* 2658 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2659 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2663 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2664 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2665 */       return true;
/*      */     }
/* 2667 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2673 */     PageContext pageContext = _jspx_page_context;
/* 2674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2676 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2677 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2678 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2680 */     _jspx_th_c_005fout_005f2.setValue("${rowcount.count}");
/* 2681 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2682 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2684 */       return true;
/*      */     }
/* 2686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2692 */     PageContext pageContext = _jspx_page_context;
/* 2693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2695 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2696 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2697 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2699 */     _jspx_th_c_005fout_005f3.setValue("${servertypei18nkey[serverrow.key]}");
/* 2700 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2701 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2703 */       return true;
/*      */     }
/* 2705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2711 */     PageContext pageContext = _jspx_page_context;
/* 2712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2714 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2715 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 2716 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 2718 */     _jspx_th_c_005fforEach_005f1.setItems("${requestScope.serverdetaillist}");
/*      */     
/* 2720 */     _jspx_th_c_005fforEach_005f1.setVar("servertypemap");
/*      */     
/* 2722 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowcount");
/* 2723 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 2725 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 2726 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 2728 */           out.write("\n\t\t\t\t\t   <div id=");
/* 2729 */           boolean bool; if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2730 */             return true;
/* 2731 */           out.write(" style=\"display:block\">\n\t\t\t\t\t\t<select  id='");
/* 2732 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2733 */             return true;
/* 2734 */           out.write("' name=\"serverlist\" style=\"background-color:#FDFEF2;width:250px\" >\n\t\t\t\t\t\t");
/* 2735 */           if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2736 */             return true;
/* 2737 */           out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t\t</div>\n\t\t\t  \t\t ");
/* 2738 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 2739 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2743 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 2744 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2747 */         int tmp281_280 = 0; int[] tmp281_278 = _jspx_push_body_count_c_005fforEach_005f1; int tmp283_282 = tmp281_278[tmp281_280];tmp281_278[tmp281_280] = (tmp283_282 - 1); if (tmp283_282 <= 0) break;
/* 2748 */         out = _jspx_page_context.popBody(); }
/* 2749 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 2751 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 2752 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 2754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2759 */     PageContext pageContext = _jspx_page_context;
/* 2760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2762 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2763 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2764 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2766 */     _jspx_th_c_005fout_005f4.setValue("${rowcount.count}");
/* 2767 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2768 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2769 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2770 */       return true;
/*      */     }
/* 2772 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2773 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2778 */     PageContext pageContext = _jspx_page_context;
/* 2779 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2781 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2782 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2783 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2785 */     _jspx_th_c_005fout_005f5.setValue("${rowcount.count}_select");
/* 2786 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2787 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2788 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2789 */       return true;
/*      */     }
/* 2791 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2797 */     PageContext pageContext = _jspx_page_context;
/* 2798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2800 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2801 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2802 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/* 2803 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2804 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 2806 */         out.write("\n\t\t\t\t\t\t\t");
/* 2807 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2808 */           return true;
/* 2809 */         out.write("\n\t\t\t\t\t\t\t");
/* 2810 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2811 */           return true;
/* 2812 */         out.write("\n\t\t\t\t\t\t");
/* 2813 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2814 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2818 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2819 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2820 */       return true;
/*      */     }
/* 2822 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2828 */     PageContext pageContext = _jspx_page_context;
/* 2829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2831 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2832 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2833 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2835 */     _jspx_th_c_005fwhen_005f2.setTest("${ isAdminServer eq true }");
/* 2836 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2837 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 2839 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 2840 */         if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2841 */           return true;
/* 2842 */         out.write("\n\t\t\t\t\t\t\t");
/* 2843 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2844 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2848 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2849 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2850 */       return true;
/*      */     }
/* 2852 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2853 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2858 */     PageContext pageContext = _jspx_page_context;
/* 2859 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2861 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2862 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 2863 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 2865 */     _jspx_th_c_005fforEach_005f2.setItems("${servertypemap.value}");
/*      */     
/* 2867 */     _jspx_th_c_005fforEach_005f2.setVar("masdispnamemap");
/* 2868 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 2870 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 2871 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 2873 */           out.write("\n\t\t\t\t\t\t\t\t\t<optgroup label='");
/* 2874 */           boolean bool; if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2875 */             return true;
/* 2876 */           out.write("'>\t\n\t\t\t\t\t\t\t\t\t\t");
/* 2877 */           if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2878 */             return true;
/* 2879 */           out.write("\n\t\t\t\t\t\t\t\t\t </optgroup>\n\t\t\t\t\t\t\t\t");
/* 2880 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 2881 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2885 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 2886 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2889 */         int tmp234_233 = 0; int[] tmp234_231 = _jspx_push_body_count_c_005fforEach_005f2; int tmp236_235 = tmp234_231[tmp234_233];tmp234_231[tmp234_233] = (tmp236_235 - 1); if (tmp236_235 <= 0) break;
/* 2890 */         out = _jspx_page_context.popBody(); }
/* 2891 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 2893 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 2894 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 2896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2901 */     PageContext pageContext = _jspx_page_context;
/* 2902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2904 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2905 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2906 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2908 */     _jspx_th_c_005fout_005f6.setValue("${masdispnamemap.key}");
/* 2909 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2910 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2912 */       return true;
/*      */     }
/* 2914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2920 */     PageContext pageContext = _jspx_page_context;
/* 2921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2923 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2924 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 2925 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2927 */     _jspx_th_c_005fforEach_005f3.setItems("${masdispnamemap.value}");
/*      */     
/* 2929 */     _jspx_th_c_005fforEach_005f3.setVar("servernameid");
/* 2930 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 2932 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 2933 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 2935 */           out.write("\n\t\t\t\t\t\t\t\t\t\t<option value='");
/* 2936 */           boolean bool; if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 2937 */             return true;
/* 2938 */           out.write(39);
/* 2939 */           out.write(62);
/* 2940 */           out.write(32);
/* 2941 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 2942 */             return true;
/* 2943 */           out.write(" </option>\t\t\n\t\t\t\t\t\t\t\t\t\t");
/* 2944 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 2945 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2949 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 2950 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2953 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f3; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 2954 */         out = _jspx_page_context.popBody(); }
/* 2955 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 2957 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 2958 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 2960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 2965 */     PageContext pageContext = _jspx_page_context;
/* 2966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2968 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2969 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2970 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 2972 */     _jspx_th_c_005fout_005f7.setValue("${servernameid.key}");
/* 2973 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2974 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2975 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2976 */       return true;
/*      */     }
/* 2978 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2979 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 2984 */     PageContext pageContext = _jspx_page_context;
/* 2985 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2987 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2988 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2989 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 2991 */     _jspx_th_c_005fout_005f8.setValue("${servernameid.value}");
/* 2992 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2993 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2994 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2995 */       return true;
/*      */     }
/* 2997 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3003 */     PageContext pageContext = _jspx_page_context;
/* 3004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3006 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3007 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3008 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3009 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3010 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3012 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 3013 */         if (_jspx_meth_c_005fforEach_005f4(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 3014 */           return true;
/* 3015 */         out.write("\n\t\t\t\t\t\t\t");
/* 3016 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3017 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3021 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3022 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3023 */       return true;
/*      */     }
/* 3025 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3026 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f4(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 3031 */     PageContext pageContext = _jspx_page_context;
/* 3032 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3034 */     ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 3035 */     _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 3036 */     _jspx_th_c_005fforEach_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3038 */     _jspx_th_c_005fforEach_005f4.setItems("${servertypemap.value}");
/*      */     
/* 3040 */     _jspx_th_c_005fforEach_005f4.setVar("servernameid");
/* 3041 */     int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */     try {
/* 3043 */       int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 3044 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */         for (;;) {
/* 3046 */           out.write("\n\t\t\t\t\t\t\t\t\t<option value='");
/* 3047 */           boolean bool; if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3048 */             return true;
/* 3049 */           out.write(39);
/* 3050 */           out.write(62);
/* 3051 */           out.write(32);
/* 3052 */           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3053 */             return true;
/* 3054 */           out.write(" </option>\t\t\n\t\t\t\t\t\t\t\t");
/* 3055 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 3056 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3060 */       if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/* 3061 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3064 */         int tmp247_246 = 0; int[] tmp247_244 = _jspx_push_body_count_c_005fforEach_005f4; int tmp249_248 = tmp247_244[tmp247_246];tmp247_244[tmp247_246] = (tmp249_248 - 1); if (tmp249_248 <= 0) break;
/* 3065 */         out = _jspx_page_context.popBody(); }
/* 3066 */       _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */     } finally {
/* 3068 */       _jspx_th_c_005fforEach_005f4.doFinally();
/* 3069 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */     }
/* 3071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3076 */     PageContext pageContext = _jspx_page_context;
/* 3077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3079 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3080 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3081 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3083 */     _jspx_th_c_005fout_005f9.setValue("${servernameid.key}");
/* 3084 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3085 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3086 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3087 */       return true;
/*      */     }
/* 3089 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3095 */     PageContext pageContext = _jspx_page_context;
/* 3096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3098 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3099 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3100 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3102 */     _jspx_th_c_005fout_005f10.setValue("${servernameid.value}");
/* 3103 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3104 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3105 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3106 */       return true;
/*      */     }
/* 3108 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3114 */     PageContext pageContext = _jspx_page_context;
/* 3115 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3117 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 3118 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3119 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3121 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.processtemplate.showprocess");
/* 3122 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3123 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3124 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3125 */         out = _jspx_page_context.pushBody();
/* 3126 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3127 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3130 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 3131 */           return true;
/* 3132 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3133 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3136 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3137 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3140 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3141 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3142 */       return true;
/*      */     }
/* 3144 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3150 */     PageContext pageContext = _jspx_page_context;
/* 3151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3153 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 3154 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3155 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 3156 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 3157 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 3158 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 3159 */         out = _jspx_page_context.pushBody();
/* 3160 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3161 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3164 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 3165 */           return true;
/* 3166 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 3167 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3170 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 3171 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3174 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 3175 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 3176 */       return true;
/*      */     }
/* 3178 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 3179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3184 */     PageContext pageContext = _jspx_page_context;
/* 3185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3187 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3188 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3189 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 3191 */     _jspx_th_c_005fout_005f11.setValue("${paramtemplatetypestr}");
/* 3192 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3193 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3194 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3195 */       return true;
/*      */     }
/* 3197 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3203 */     PageContext pageContext = _jspx_page_context;
/* 3204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3206 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3207 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3208 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3209 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3210 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3212 */         out.write("\n<table width=\"80%\" cellpadding=\"5\" cellspacing=\"5\" border=\"0\">\n                <tr>\n                  <td class=\"bodytext\" width=\"20%\" align=\"center\">");
/* 3213 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/* 3214 */           return true;
/* 3215 */         out.write("</td> ");
/* 3216 */         out.write("\n\t\t</tr>\n</table>\n");
/* 3217 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3218 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3222 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3223 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3224 */       return true;
/*      */     }
/* 3226 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3227 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3232 */     PageContext pageContext = _jspx_page_context;
/* 3233 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3235 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3236 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3237 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 3239 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.template.noservermsg");
/* 3240 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3241 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3242 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3243 */       return true;
/*      */     }
/* 3245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3246 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TemplateServerList_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */