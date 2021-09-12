/*      */ package org.apache.jsp.jsp;
/*      */ 
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
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
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
/*      */ public final class ShowCredentialTypeValues_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   62 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/*  712 */     java.net.InetAddress add = null;
/*  713 */     if (ip.equals("127.0.0.1")) {
/*  714 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  718 */       add = java.net.InetAddress.getByName(ip);
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
/* 2177 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2178 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2179 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2200 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2204 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2218 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2224 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/* 2227 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2241 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2244 */     JspWriter out = null;
/* 2245 */     Object page = this;
/* 2246 */     JspWriter _jspx_out = null;
/* 2247 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2251 */       response.setContentType("text/html;charset=UTF-8");
/* 2252 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2254 */       _jspx_page_context = pageContext;
/* 2255 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2256 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2257 */       session = pageContext.getSession();
/* 2258 */       out = pageContext.getOut();
/* 2259 */       _jspx_out = out;
/*      */       
/* 2261 */       out.write("<!DOCTYPE html>\n");
/* 2262 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2263 */       out.write("\n\n\n\n\n");
/* 2264 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2265 */       out.write(10);
/* 2266 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2268 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2269 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2271 */       out.write(10);
/* 2272 */       out.write(10);
/* 2273 */       out.write(10);
/* 2274 */       out.write("\n\n\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2275 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2277 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<!--<style>\n.form input{\nborder: 1px solid #ddd;\noutline: none;\nborder-radius: 4px;\n-webkit-transition: all 0.30s ease-in-out;\n-moz-transition: all 0.30s ease-in-out;\n-ms-transition: all 0.30s ease-in-out;\n-o-transition: all 0.30s ease-in-out;\ntransition: all 0.30s ease-in-out;\n}\n\n.form input:focus {\nborder-color: #83b4db;\nbox-shadow: 0 0 10px #8fbfe6;\n-moz-box-shadow: 0 0 10px #8fbfe6;\n-webkit-box-shadow: 0 0 10px #8fbfe6;\n}\n\n\n</style>-->\n<script>\n$(document).ready(function(){\n    $('.chzn-select').chosen();\n});\n\nfunction onLoad()\n{\n    if($('select[name=type]').val() == 'SNMP v3')\n    {\n        showSecurityLevelProps();\n    }\n    if($('select[name=type]').val() == 'Tomcat-server')\n    {\n        showAsPerTomcatVersion();\n    }\n    if($('select[name=type]').val() == 'SSH')\n    {\n        toggleRowsAsPerArgs('description,passphrase','password','sshPKAuth'); //No I18N\n    }\n    ");
/* 2278 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/* 2280 */       out.write("\n    \n}\n\nfunction onChangeToExecute(version)\n{\n    var tomcatVersion = ($('select[name=version]').val()); //No I18N\n    if(tomcatVersion == 3 || tomcatVersion == 4)\n        {\n        var toHideArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        hideArgs(toHideArgs);\n        $(\"#message3\").show(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n        }\n    if(tomcatVersion > 4)\n        {\n        toShowArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        showArgs(toShowArgs);\n        $(\"#message5\").show(\"slow\"); //No I18N\n        $(\"#message3\").hide(\"slow\"); //No I18N\n        }\n    if(tomcatVersion == 0)\n    {\n        var toHideArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        hideArgs(toHideArgs);\n        $(\"#message3\").hide(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n    }\n}\n\n      \nfunction showAsPerTomcatVersion()\n{\n    var tomcatVersion = ($('select[name=version]').val()); //No I18N\n    if(tomcatVersion == 3 || tomcatVersion == 4)\n        {\n");
/* 2281 */       out.write("        var toHideArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        hideArgs(toHideArgs);\n        $(\"#message3\").show(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n        }\n    if(tomcatVersion > 4)\n        {\n        toShowArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        showArgs(toShowArgs);\n        $(\"#message5\").show(\"slow\"); //No I18N\n        $(\"#message3\").hide(\"slow\"); //No I18N\n        }\n    if(tomcatVersion == 0)\n    {\n        var toHideArgs = 'username,password,tomcatmanagerurl'; //No I18N\n        hideArgs(toHideArgs);\n        $(\"#message3\").hide(\"slow\"); //No I18N\n        $(\"#message5\").hide(\"slow\"); //No I18N\n}\n}\n\nfunction toggleRowsAsPerArgs(argsToShow,argsToHide,checkBoxName)\n{\n    if(($('input[name='+checkBoxName+']').is(':checked')))\n    {\n        showArgs(argsToShow);\n        hideArgs(argsToHide);\n    }\n    else\n    {\n        showArgs(argsToHide);\n        hideArgs(argsToShow);\n    }\n\n}\n\nfunction showArgs(argsToPerform)\n{    \n    var argsInArray = argsToPerform.split(\",\");\n");
/* 2282 */       out.write("    for(var i=0;i < argsInArray.length;i++)\n    {\n        $(\"#\"+$.trim(argsInArray[i])).show(\"fast\"); ");
/* 2283 */       out.write("\n    }\n}\n\nfunction hideArgs(argsToPerform)\n{    \n    var argsInArray = argsToPerform.split(\",\");    \n    for(var i=0;i <= argsInArray.length;i++)\n    {        \n        $(\"#\"+$.trim(argsInArray[i])).hide(\"fast\"); ");
/* 2284 */       out.write("\n    }\n}\n\nfunction showSecurityLevelProps()\n \t {\n \t     if($('select[name=snmpSecurityLevel]').val() == 'NOAUTHNOPRIV')\n \t         {\n \t             $(\"#snmpAuthPassword\").hide(\"fast\"); //No I18N\n \t             $(\"#snmpAuthProtocol\").hide(\"fast\"); //No I18N\n                     $(\"#snmpPrivPassword\").hide(\"fast\"); //No I18N\n \t         }\n \t         if($('select[name=snmpSecurityLevel]').val() == 'AUTHNOPRIV')\n \t         {\n \t            $(\"#snmpPrivPassword\").hide(\"fast\"); //No I18N\n \t             $(\"#snmpAuthPassword\").show(\"slow\"); //No I18N\n                     $(\"#snmpAuthProtocol\").show(\"slow\"); //No I18N\n \t         }\n \t         if($('select[name=snmpSecurityLevel]').val() == 'AUTHPRIV')\n \t         {\n \t             $(\"#snmpAuthPassword\").show(\"slow\"); //No I18N\n \t             $(\"#snmpAuthProtocol\").show(\"slow\"); //No I18N\n                     $(\"#snmpPrivPassword\").show(\"slow\"); //No I18N\n \t         }\n \t      //alert($('select[name=snmpSecurityLevel]').val());\n \t }\n\n\nfunction showByTypeSelected(value)\n{        \n");
/* 2285 */       out.write("   $('input[name=save]').val('Save');\n   var fromDiscovery=$('input[name=fromDiscovery]').val();\n    var dataString=\"&method=showCredentialByTypeName&type=\"+value+\"&fromDiscovery=\"+fromDiscovery; //No I18N\n    var url=\"/credentialManager.do?method=showCredentialByTypeName&type=\"+value+\"&fromDiscovery=\"+fromDiscovery; //No I18N\n   // $(\"#mydiv\").load(url);\n    $.ajax({\n            type:\"POST\", //No I18N\n            url:\"/credentialManager.do\", //No I18N\n            data:dataString,\n            success:function(response) {\n\n                var html= $('div.newDiv',response).html();\n                $(\"div.newDiv\").html(html);\n               \n            }\n                   \n            \n    });\n\n}\n\nfunction cancelModify(idString,encValue,credentialID,displayValue)\n{    \n    var toReplaceInput = \"<input type=hidden name=\\'hidden_\"+idString+\"\\' value=\\\"encoded\\\"\\><input class=\\\"formtext\\\" readonly type=\\\"password\\\" value=\"+encValue+\" name=\\\"\"+idString+\"\\\">\";    \n    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue;text-decoration:underline;\\\" onclick=\\\"modifyPassword(\\'\"+idString+\"\\',\\'\"+credentialID+\"\\',\\'\"+encValue+\"\\',\\'\"+displayValue+\"\\')\\\">");
/* 2286 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */         return;
/* 2288 */       out.write("&nbsp;\"+displayValue+\"</a>\";\n    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n    //$('input[type=password]').attr('disabled', true); //No I18N\n}\n\nfunction modifyPassword(idString,credentialID,encValue,displayValue)\n{    \n    cacheid = (new Date()).getTime();    \n    var dataString = \"&method=changeToReadableFormat&credentialID=\"+credentialID+\"&rowID=\"+idString+\"&cacheid=\"+cacheid; //No I18N\n    $.ajax(\n    {\n                type:\"POST\", //No I18N\n                url:\"/credentialManager.do\", //No I18N\n                data:dataString,\n                success: function(response)\n                {                                        \n                    var toReplaceInput = \"<input type=hidden name=\\'hidden_\"+idString+\"\\' value=\\\"decoded\\\"\\><input id=\\\"toCheck\\\" class=\\\"formtext\\\" type=\\\"password\\\" value=\\\"\\\" name=\\\"\"+idString+\"\\\">\";\n                    var toReplaceModifyPart = \"<a href=\\\"javascript:void(0)\\\" style=\\\"color:blue\\\" onclick=\\\"cancelModify(\\'\"+idString+\"\\',\\'\"+encValue+\"\\',\\'\"+credentialID+\"\\','\"+displayValue+\"')\\\">");
/* 2289 */       if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */         return;
/* 2291 */       out.write("</a>\";\n                    $(\"#tdSpan_\"+idString).html(toReplaceInput);\n                    $(\"#modifySpan_\"+idString).html(toReplaceModifyPart);\n                    $(\"#toCheck\").focus();\n                    \n                }\n    });\n\n}\n$(document).ready(function()\n{    \n    if($('input[name=save]').val().trim() == 'Update')\n    {\n        //$('select[name=type]').attr('disabled', true); //No I18N\n        $('input[name=credentialName]').attr('disabled', true); //No I18N\n        //$('input[type=password]').attr('disabled', true); //No I18N\n    }\n   \n});\n\nfunction checkCredName() {\n\n$(\"#display\").hide();\n    $('input[name=credentialName]').focus(function(e) //No I18N\n    {        \n        $(\"#display\").hide();\n    });\n\n        cacheid = (new Date()).getTime();\n        var credName = $('input[name=credentialName]').val(); //No I18N        \n        dataString = \"&method=credentialNameExistsCheck&enteredName=\"+credName+\"&cacheid=\"+cacheid; //No I18N\n            $.ajax(\n            {\n                type:\"POST\", //No I18N\n");
/* 2292 */       out.write("                url:\"/credentialManager.do\", //No I18N\n                data:dataString,\n                success: function(response)\n                {\n                    if(response.trim() == 'false')\n                    {\n                        $(\"#display\").text(\"\"); //No I18N\n                        $(\"#display\").css(\"background-color\",\"white\"); //No I18N\n                        $(\"input[name=save]\").attr(\"disabled\", false); //No I18N\n                        $(\"#display\").hide();\n\t\t\t\t\t\treturn 1;\n                    }\n                    else\n                    {\n                      var stringToShow = \"");
/* 2293 */       out.print(FormatUtil.getString("am.webclient.credential.credentialNameExists"));
/* 2294 */       out.write("\";\n                      $(\"#display\").html(stringToShow);      // Set response into particular div ID .. //No I18N\n                      $(\"#display\").css(\"color\",\"red\"); //No I18N\n                      $(\"#display\").show();\n                      \n                      //document.getElementById(\"credentialNameInput\").focus();\n                      $(\"input[name=save]\").attr(\"disabled\", true); //No I18N\n\t\t\t\t\t  return -1;\n                    }\n                }\n    });\n\n\n}\n\n\nfunction validateAndSave(val1)\n{\n\t");
/* 2295 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/* 2297 */       out.write("\n    var fromDiscovery=$('input[name=fromDiscovery]').val();\n    var credentialName=$('input[name=credentialName]').val().trim();\n    var credentialID='-1';\n    var type=$('select[name=type]').val();\n   // var dataString=\"&method=saveCredentials&fromDiscovery=\"+fromDiscovery+\"&type=\"+type;\n   if(val1 == 'Update')\n\t{\n\tcredentialID=$('input[name=credentialID]').val().trim();\n\t}\n\telse {\n   if(checkCredName() == -1)\n   {\n   return;\n   }\n    if($('input[name=credentialName]').val().trim() == \"\")\n    {        \n        alert(\"");
/* 2298 */       out.print(FormatUtil.getString("am.webclient.credential.credentialNameEmpty"));
/* 2299 */       out.write("\");\n        return;\n    }    \n\t}\n    $.ajax(\n    {\n        type: \"POST\",//No I18N\n        url: \"/credentialManager.do?method=saveCredentials&credentialID=\"+credentialID+\"&fromDiscovery=\"+fromDiscovery+\"&type=\"+type, //No I18N\n        data: $('form').serialize(),\n        success: function(response) {\n    if(fromDiscovery != \"true\")\n             {\n           \n             checkOnLoad();\n            }\n            else\n            {\n\t\t\t\tvar result=$.parseJSON(response);\n\t\t\t\tcredentialID=result.credentialID;\n                onSaveDialog(credentialID,credentialName);\n               }\n            }\n\n\n    });\n    \n}   \n\nfunction checkSomething()\n{\n  window.opener.location.reload();\n  window.close();      \n}\n\nfunction checkOnLoad()\n{    \n    window.opener.document.location='/credentialManager.do?method=showCredentialManager'; //No I18N\n    window.opener.focus();\n    setTimeout(function(){checkSomething();}, 0);\n}\n\nfunction closeFunction()\n{\n\n     var fromDiscovery=$('input[name=fromDiscovery]').val();\n      if(fromDiscovery != \"true\")\n");
/* 2300 */       out.write("      {\n        window.close();    \n      }\n      else\n      {\n        closeDialog();\n      }\n    \n}\n</script>\n</head>\n\n<body>\n");
/*      */       
/* 2302 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 2303 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2304 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/* 2306 */       _jspx_th_html_005fform_005f0.setAction("/credentialManager");
/* 2307 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2308 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/* 2310 */           out.write("\n<input id=\"test\" type=\"hidden\" name=\"method\" value=\"showCredentialByTypeName\">\n<table id=\"heading\" class=\"darkheaderbg\" height=\"55\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td>\n<span class=\"headingboldwhite\">\n\t  ");
/* 2311 */           out.print(FormatUtil.getString("webclient.credentialManager.addEditCredential"));
/* 2312 */           out.write(32);
/* 2313 */           out.write("\n </span>\n</td>\n</tr>\n</table>\n\n    <table width=\"100%\" align=\"center\" id=\"credentialDetails\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\n\n    <tr>\n        <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 2314 */           out.print(FormatUtil.getString("am.webclient.credentialManager.credentialType"));
/* 2315 */           out.write("</td> ");
/* 2316 */           out.write("\n        <td width=\"50%\" class=\"whitegrayrightalign credential-popup\" ><span>\n\n");
/*      */           
/* 2318 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2319 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2320 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2322 */           _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */           
/* 2324 */           _jspx_th_html_005fselect_005f0.setStyleClass("chzn-select formtext normal");
/*      */           
/* 2326 */           _jspx_th_html_005fselect_005f0.setOnchange("showByTypeSelected(this.options[this.selectedIndex].value)");
/*      */           
/* 2328 */           _jspx_th_html_005fselect_005f0.setStyle("background-color: rgb(253, 254, 242); font-size: 13px; height: 22px;");
/* 2329 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2330 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2331 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2332 */               out = _jspx_page_context.pushBody();
/* 2333 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2334 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2337 */               out.write("\n        <optgroup label=");
/* 2338 */               out.print(FormatUtil.getString("Servers"));
/* 2339 */               out.write(">\n                                        ");
/*      */               
/* 2341 */               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2342 */               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2343 */               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2345 */               _jspx_th_html_005foption_005f0.setValue("TELNET");
/* 2346 */               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2347 */               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2348 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2349 */                   out = _jspx_page_context.pushBody();
/* 2350 */                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2351 */                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2354 */                   out.print(FormatUtil.getString("Telnet"));
/* 2355 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2356 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2359 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2360 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2363 */               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2364 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */               }
/*      */               
/* 2367 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2368 */               out.write(32);
/* 2369 */               out.write("\n                                        ");
/*      */               
/* 2371 */               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2372 */               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2373 */               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2375 */               _jspx_th_html_005foption_005f1.setValue("SNMP v1v2");
/* 2376 */               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2377 */               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2378 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2379 */                   out = _jspx_page_context.pushBody();
/* 2380 */                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2381 */                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2384 */                   out.print(FormatUtil.getString("SNMP v1v2"));
/* 2385 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2386 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2389 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2390 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2393 */               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2394 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */               }
/*      */               
/* 2397 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2398 */               out.write(32);
/* 2399 */               out.write("\n                                        ");
/*      */               
/* 2401 */               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2402 */               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2403 */               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2405 */               _jspx_th_html_005foption_005f2.setValue("SNMP v3");
/* 2406 */               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2407 */               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2408 */                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2409 */                   out = _jspx_page_context.pushBody();
/* 2410 */                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2411 */                   _jspx_th_html_005foption_005f2.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2414 */                   out.print(FormatUtil.getString("SNMP v3"));
/* 2415 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2416 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2419 */                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2420 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2423 */               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2424 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */               }
/*      */               
/* 2427 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2428 */               out.write(32);
/* 2429 */               out.write("\n                                        ");
/*      */               
/* 2431 */               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2432 */               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2433 */               _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2435 */               _jspx_th_html_005foption_005f3.setValue("SSH");
/* 2436 */               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2437 */               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2438 */                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2439 */                   out = _jspx_page_context.pushBody();
/* 2440 */                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2441 */                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2444 */                   out.print(FormatUtil.getString("SSH"));
/* 2445 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2446 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2449 */                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2450 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2453 */               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2454 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */               }
/*      */               
/* 2457 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2458 */               out.write("\n                                        ");
/*      */               
/* 2460 */               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2461 */               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2462 */               _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2464 */               _jspx_th_c_005fif_005f1.setTest("${showWMIMonitors == \"true\"}");
/* 2465 */               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2466 */               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                 for (;;) {
/* 2468 */                   out.write("\n                                        ");
/*      */                   
/* 2470 */                   OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2471 */                   _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2472 */                   _jspx_th_html_005foption_005f4.setParent(_jspx_th_c_005fif_005f1);
/*      */                   
/* 2474 */                   _jspx_th_html_005foption_005f4.setValue("WMI");
/* 2475 */                   int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2476 */                   if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2477 */                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2478 */                       out = _jspx_page_context.pushBody();
/* 2479 */                       _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2480 */                       _jspx_th_html_005foption_005f4.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2483 */                       out.print(FormatUtil.getString("WMI"));
/* 2484 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2485 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2488 */                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2489 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2492 */                   if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2493 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                   }
/*      */                   
/* 2496 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2497 */                   out.write(32);
/* 2498 */                   out.write("\n                                        </optgroup>\n                                        ");
/* 2499 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2500 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2504 */               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2505 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */               }
/*      */               
/* 2508 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2509 */               out.write("\n        <optgroup label=");
/* 2510 */               out.print(FormatUtil.getString("Application Servers"));
/* 2511 */               out.write(62);
/* 2512 */               out.write(32);
/* 2513 */               out.write("\n        \t\t\t ");
/*      */               
/* 2515 */               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2516 */               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2517 */               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2519 */               _jspx_th_html_005foption_005f5.setValue("APACHE:80");
/* 2520 */               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2521 */               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2522 */                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2523 */                   out = _jspx_page_context.pushBody();
/* 2524 */                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2525 */                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2528 */                   out.print(FormatUtil.getString("Apache Server"));
/* 2529 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2530 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2533 */                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2534 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2537 */               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2538 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */               }
/*      */               
/* 2541 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2542 */               out.write(32);
/* 2543 */               out.write("                                        \n\t\t\t\t \t");
/*      */               
/* 2545 */               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2546 */               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2547 */               _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2549 */               _jspx_th_html_005foption_005f6.setValue("GlassFish");
/* 2550 */               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2551 */               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2552 */                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2553 */                   out = _jspx_page_context.pushBody();
/* 2554 */                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 2555 */                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2558 */                   out.print(FormatUtil.getString("GlassFish"));
/* 2559 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2560 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2563 */                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2564 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2567 */               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2568 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */               }
/*      */               
/* 2571 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2572 */               out.write(32);
/* 2573 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 2575 */               OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2576 */               _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2577 */               _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2579 */               _jspx_th_html_005foption_005f7.setValue("JBOSS-server");
/* 2580 */               int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2581 */               if (_jspx_eval_html_005foption_005f7 != 0) {
/* 2582 */                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2583 */                   out = _jspx_page_context.pushBody();
/* 2584 */                   _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 2585 */                   _jspx_th_html_005foption_005f7.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2588 */                   out.print(FormatUtil.getString("JBoss Server"));
/* 2589 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 2590 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2593 */                 if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2594 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2597 */               if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2598 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */               }
/*      */               
/* 2601 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 2602 */               out.write(32);
/* 2603 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 2605 */               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2606 */               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2607 */               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2609 */               _jspx_th_html_005foption_005f8.setValue("Jetty Server");
/* 2610 */               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2611 */               if (_jspx_eval_html_005foption_005f8 != 0) {
/* 2612 */                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2613 */                   out = _jspx_page_context.pushBody();
/* 2614 */                   _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 2615 */                   _jspx_th_html_005foption_005f8.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2618 */                   out.print(FormatUtil.getString("Jetty Server"));
/* 2619 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 2620 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2623 */                 if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2624 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2627 */               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2628 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */               }
/*      */               
/* 2631 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 2632 */               out.write(32);
/* 2633 */               out.write("\n                                        ");
/*      */               
/* 2635 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2636 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2637 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2639 */               _jspx_th_c_005fif_005f2.setTest("${showWMIMonitors == \"true\"}");
/* 2640 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2641 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2643 */                   out.write("\n\t\t\t\t \t");
/*      */                   
/* 2645 */                   OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2646 */                   _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2647 */                   _jspx_th_html_005foption_005f9.setParent(_jspx_th_c_005fif_005f2);
/*      */                   
/* 2649 */                   _jspx_th_html_005foption_005f9.setValue(".Net:9080");
/* 2650 */                   int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2651 */                   if (_jspx_eval_html_005foption_005f9 != 0) {
/* 2652 */                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2653 */                       out = _jspx_page_context.pushBody();
/* 2654 */                       _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 2655 */                       _jspx_th_html_005foption_005f9.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2658 */                       out.print(FormatUtil.getString("Microsoft .NET"));
/* 2659 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 2660 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2663 */                     if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2664 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2667 */                   if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2668 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                   }
/*      */                   
/* 2671 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 2672 */                   out.write(32);
/* 2673 */                   out.write("\n                                        ");
/* 2674 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2675 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2679 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2680 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2683 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2684 */               out.write("\n                    ");
/*      */               
/* 2686 */               OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2687 */               _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2688 */               _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2690 */               _jspx_th_html_005foption_005f10.setValue("LyncServer");
/* 2691 */               int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2692 */               if (_jspx_eval_html_005foption_005f10 != 0) {
/* 2693 */                 if (_jspx_eval_html_005foption_005f10 != 1) {
/* 2694 */                   out = _jspx_page_context.pushBody();
/* 2695 */                   _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 2696 */                   _jspx_th_html_005foption_005f10.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2699 */                   out.print(FormatUtil.getString("Microsoft Lync Server"));
/* 2700 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 2701 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2704 */                 if (_jspx_eval_html_005foption_005f10 != 1) {
/* 2705 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2708 */               if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2709 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */               }
/*      */               
/* 2712 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 2713 */               out.write(32);
/* 2714 */               out.write("\n                    ");
/*      */               
/* 2716 */               OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2717 */               _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 2718 */               _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2720 */               _jspx_th_html_005foption_005f11.setValue("Kafka");
/* 2721 */               int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 2722 */               if (_jspx_eval_html_005foption_005f11 != 0) {
/* 2723 */                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 2724 */                   out = _jspx_page_context.pushBody();
/* 2725 */                   _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 2726 */                   _jspx_th_html_005foption_005f11.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2729 */                   out.print(FormatUtil.getString("Apache Kafka"));
/* 2730 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 2731 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2734 */                 if (_jspx_eval_html_005foption_005f11 != 1) {
/* 2735 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2738 */               if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 2739 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */               }
/*      */               
/* 2742 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 2743 */               out.write(32);
/* 2744 */               out.write("\n                   \t");
/*      */               
/* 2746 */               OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2747 */               _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 2748 */               _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2750 */               _jspx_th_html_005foption_005f12.setValue("Resin");
/* 2751 */               int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 2752 */               if (_jspx_eval_html_005foption_005f12 != 0) {
/* 2753 */                 if (_jspx_eval_html_005foption_005f12 != 1) {
/* 2754 */                   out = _jspx_page_context.pushBody();
/* 2755 */                   _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 2756 */                   _jspx_th_html_005foption_005f12.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2759 */                   out.print(FormatUtil.getString("Resin"));
/* 2760 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 2761 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2764 */                 if (_jspx_eval_html_005foption_005f12 != 1) {
/* 2765 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2768 */               if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 2769 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */               }
/*      */               
/* 2772 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 2773 */               out.write(32);
/* 2774 */               out.write("\n                   \t");
/*      */               
/* 2776 */               OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2777 */               _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 2778 */               _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2780 */               _jspx_th_html_005foption_005f13.setValue("RESTAPIMonitor");
/* 2781 */               int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 2782 */               if (_jspx_eval_html_005foption_005f13 != 0) {
/* 2783 */                 if (_jspx_eval_html_005foption_005f13 != 1) {
/* 2784 */                   out = _jspx_page_context.pushBody();
/* 2785 */                   _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 2786 */                   _jspx_th_html_005foption_005f13.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2789 */                   out.print(FormatUtil.getString("REST API"));
/* 2790 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 2791 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2794 */                 if (_jspx_eval_html_005foption_005f13 != 1) {
/* 2795 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2798 */               if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 2799 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */               }
/*      */               
/* 2802 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 2803 */               out.write(32);
/* 2804 */               out.write("\n        \t\t\t");
/*      */               
/* 2806 */               OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2807 */               _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 2808 */               _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2810 */               _jspx_th_html_005foption_005f14.setValue("Tomcat-server");
/* 2811 */               int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 2812 */               if (_jspx_eval_html_005foption_005f14 != 0) {
/* 2813 */                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 2814 */                   out = _jspx_page_context.pushBody();
/* 2815 */                   _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 2816 */                   _jspx_th_html_005foption_005f14.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2819 */                   out.print(FormatUtil.getString("Tomcat Server"));
/* 2820 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 2821 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2824 */                 if (_jspx_eval_html_005foption_005f14 != 1) {
/* 2825 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2828 */               if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 2829 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */               }
/*      */               
/* 2832 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 2833 */               out.write(32);
/* 2834 */               out.write("                   \n\n\t\t\t\t \t");
/*      */               
/* 2836 */               OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2837 */               _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 2838 */               _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2840 */               _jspx_th_html_005foption_005f15.setValue("VMware vFabric tc Server");
/* 2841 */               int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 2842 */               if (_jspx_eval_html_005foption_005f15 != 0) {
/* 2843 */                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 2844 */                   out = _jspx_page_context.pushBody();
/* 2845 */                   _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 2846 */                   _jspx_th_html_005foption_005f15.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2849 */                   out.print(FormatUtil.getString("VMware vFabric tc Server"));
/* 2850 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 2851 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2854 */                 if (_jspx_eval_html_005foption_005f15 != 1) {
/* 2855 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2858 */               if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 2859 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */               }
/*      */               
/* 2862 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 2863 */               out.write(32);
/* 2864 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 2866 */               OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2867 */               _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 2868 */               _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2870 */               _jspx_th_html_005foption_005f16.setValue("WEBLOGIC:7001");
/* 2871 */               int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 2872 */               if (_jspx_eval_html_005foption_005f16 != 0) {
/* 2873 */                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 2874 */                   out = _jspx_page_context.pushBody();
/* 2875 */                   _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 2876 */                   _jspx_th_html_005foption_005f16.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2879 */                   out.print(FormatUtil.getString("WebLogic Server"));
/* 2880 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 2881 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2884 */                 if (_jspx_eval_html_005foption_005f16 != 1) {
/* 2885 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2888 */               if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 2889 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */               }
/*      */               
/* 2892 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 2893 */               out.write(32);
/* 2894 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 2896 */               OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2897 */               _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 2898 */               _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2900 */               _jspx_th_html_005foption_005f17.setValue("WebSphere-server");
/* 2901 */               int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 2902 */               if (_jspx_eval_html_005foption_005f17 != 0) {
/* 2903 */                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 2904 */                   out = _jspx_page_context.pushBody();
/* 2905 */                   _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 2906 */                   _jspx_th_html_005foption_005f17.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2909 */                   out.print(FormatUtil.getString("WebSphere Server"));
/* 2910 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 2911 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2914 */                 if (_jspx_eval_html_005foption_005f17 != 1) {
/* 2915 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2918 */               if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 2919 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */               }
/*      */               
/* 2922 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 2923 */               out.write(32);
/* 2924 */               out.write("\n                                        </optgroup>\n      <optgroup label=");
/* 2925 */               out.print(FormatUtil.getString("Java/Transactions"));
/* 2926 */               out.write(62);
/* 2927 */               out.write(32);
/* 2928 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 2930 */               OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2931 */               _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 2932 */               _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2934 */               _jspx_th_html_005foption_005f18.setValue("JDK1.5:1099");
/* 2935 */               int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 2936 */               if (_jspx_eval_html_005foption_005f18 != 0) {
/* 2937 */                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 2938 */                   out = _jspx_page_context.pushBody();
/* 2939 */                   _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 2940 */                   _jspx_th_html_005foption_005f18.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2943 */                   out.print(FormatUtil.getString("Java Runtime"));
/* 2944 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 2945 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2948 */                 if (_jspx_eval_html_005foption_005f18 != 1) {
/* 2949 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2952 */               if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 2953 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */               }
/*      */               
/* 2956 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 2957 */               out.write(" </optgroup>");
/* 2958 */               out.write("\n      <optgroup label=");
/* 2959 */               out.print(FormatUtil.getString("Database Servers"));
/* 2960 */               out.write(62);
/* 2961 */               out.write(32);
/* 2962 */               out.write("\n\t\t\t\t");
/*      */               
/* 2964 */               OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2965 */               _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 2966 */               _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2968 */               _jspx_th_html_005foption_005f19.setValue("Cassandra");
/* 2969 */               int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 2970 */               if (_jspx_eval_html_005foption_005f19 != 0) {
/* 2971 */                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 2972 */                   out = _jspx_page_context.pushBody();
/* 2973 */                   _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 2974 */                   _jspx_th_html_005foption_005f19.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2977 */                   out.print(FormatUtil.getString("Cassandra"));
/* 2978 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 2979 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2982 */                 if (_jspx_eval_html_005foption_005f19 != 1) {
/* 2983 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2986 */               if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 2987 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */               }
/*      */               
/* 2990 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 2991 */               out.write(32);
/* 2992 */               out.write("\n\t\t\t\t");
/*      */               
/* 2994 */               OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2995 */               _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 2996 */               _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 2998 */               _jspx_th_html_005foption_005f20.setValue("Couchbase");
/* 2999 */               int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3000 */               if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3001 */                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3002 */                   out = _jspx_page_context.pushBody();
/* 3003 */                   _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3004 */                   _jspx_th_html_005foption_005f20.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3007 */                   out.print(FormatUtil.getString("Couchbase"));
/* 3008 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3009 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3012 */                 if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3013 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3016 */               if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3017 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */               }
/*      */               
/* 3020 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3021 */               out.write(32);
/* 3022 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3024 */               OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3025 */               _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3026 */               _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3028 */               _jspx_th_html_005foption_005f21.setValue("DB2:50000");
/* 3029 */               int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3030 */               if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3031 */                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3032 */                   out = _jspx_page_context.pushBody();
/* 3033 */                   _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3034 */                   _jspx_th_html_005foption_005f21.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3037 */                   out.print(FormatUtil.getString("DB2"));
/* 3038 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3039 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3042 */                 if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3043 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3046 */               if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3047 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */               }
/*      */               
/* 3050 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3051 */               out.write(32);
/* 3052 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3054 */               OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3055 */               _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3056 */               _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3058 */               _jspx_th_html_005foption_005f22.setValue("HBase");
/* 3059 */               int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3060 */               if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3061 */                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3062 */                   out = _jspx_page_context.pushBody();
/* 3063 */                   _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3064 */                   _jspx_th_html_005foption_005f22.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3067 */                   out.print(FormatUtil.getString("HBase"));
/* 3068 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3069 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3072 */                 if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3073 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3076 */               if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3077 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */               }
/*      */               
/* 3080 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3081 */               out.write(32);
/* 3082 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3084 */               OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3085 */               _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3086 */               _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3088 */               _jspx_th_html_005foption_005f23.setValue("Informix");
/* 3089 */               int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3090 */               if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3091 */                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3092 */                   out = _jspx_page_context.pushBody();
/* 3093 */                   _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3094 */                   _jspx_th_html_005foption_005f23.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3097 */                   out.print(FormatUtil.getString("Informix"));
/* 3098 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3099 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3102 */                 if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3103 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3106 */               if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3107 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */               }
/*      */               
/* 3110 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3111 */               out.write(32);
/* 3112 */               out.write("\n\t\t\t\t \t ");
/*      */               
/* 3114 */               OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3115 */               _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3116 */               _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3118 */               _jspx_th_html_005foption_005f24.setValue("MongoDB");
/* 3119 */               int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3120 */               if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3121 */                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3122 */                   out = _jspx_page_context.pushBody();
/* 3123 */                   _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3124 */                   _jspx_th_html_005foption_005f24.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3127 */                   out.print(FormatUtil.getString("MongoDB"));
/* 3128 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3129 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3132 */                 if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3133 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3136 */               if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3137 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */               }
/*      */               
/* 3140 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3141 */               out.write(32);
/* 3142 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3144 */               OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3145 */               _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3146 */               _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3148 */               _jspx_th_html_005foption_005f25.setValue("MSSQLDB:1433");
/* 3149 */               int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3150 */               if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3151 */                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3152 */                   out = _jspx_page_context.pushBody();
/* 3153 */                   _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3154 */                   _jspx_th_html_005foption_005f25.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3157 */                   out.print(FormatUtil.getString("MS SQL"));
/* 3158 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3159 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3162 */                 if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3163 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3166 */               if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3167 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */               }
/*      */               
/* 3170 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3171 */               out.write(32);
/* 3172 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3174 */               OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3175 */               _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3176 */               _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3178 */               _jspx_th_html_005foption_005f26.setValue("MYSQLDB:3306");
/* 3179 */               int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3180 */               if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3181 */                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3182 */                   out = _jspx_page_context.pushBody();
/* 3183 */                   _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3184 */                   _jspx_th_html_005foption_005f26.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3187 */                   out.print(FormatUtil.getString("MySQL"));
/* 3188 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3189 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3192 */                 if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3193 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3196 */               if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3197 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */               }
/*      */               
/* 3200 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3201 */               out.write(32);
/* 3202 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3204 */               OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3205 */               _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3206 */               _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3208 */               _jspx_th_html_005foption_005f27.setValue("OracleNoSQL");
/* 3209 */               int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3210 */               if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3211 */                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3212 */                   out = _jspx_page_context.pushBody();
/* 3213 */                   _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3214 */                   _jspx_th_html_005foption_005f27.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3217 */                   out.print(FormatUtil.getString("Oracle NoSQL"));
/* 3218 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3219 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3222 */                 if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3223 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3226 */               if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3227 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */               }
/*      */               
/* 3230 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3231 */               out.write(32);
/* 3232 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3234 */               OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3235 */               _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3236 */               _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3238 */               _jspx_th_html_005foption_005f28.setValue("ORACLE-DB-server");
/* 3239 */               int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3240 */               if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3241 */                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3242 */                   out = _jspx_page_context.pushBody();
/* 3243 */                   _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3244 */                   _jspx_th_html_005foption_005f28.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3247 */                   out.print(FormatUtil.getString("Oracle"));
/* 3248 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3249 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3252 */                 if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3253 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3256 */               if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3257 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */               }
/*      */               
/* 3260 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3261 */               out.write(32);
/* 3262 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3264 */               OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3265 */               _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 3266 */               _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3268 */               _jspx_th_html_005foption_005f29.setValue("PostgreSQL");
/* 3269 */               int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 3270 */               if (_jspx_eval_html_005foption_005f29 != 0) {
/* 3271 */                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3272 */                   out = _jspx_page_context.pushBody();
/* 3273 */                   _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 3274 */                   _jspx_th_html_005foption_005f29.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3277 */                   out.print(FormatUtil.getString("PostgreSQL"));
/* 3278 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 3279 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3282 */                 if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3283 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3286 */               if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 3287 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */               }
/*      */               
/* 3290 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 3291 */               out.write(32);
/* 3292 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3294 */               OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3295 */               _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 3296 */               _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3298 */               _jspx_th_html_005foption_005f30.setValue("SapHana");
/* 3299 */               int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 3300 */               if (_jspx_eval_html_005foption_005f30 != 0) {
/* 3301 */                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3302 */                   out = _jspx_page_context.pushBody();
/* 3303 */                   _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 3304 */                   _jspx_th_html_005foption_005f30.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3307 */                   out.print(FormatUtil.getString("SAP HANA"));
/* 3308 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 3309 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3312 */                 if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3313 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3316 */               if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 3317 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */               }
/*      */               
/* 3320 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 3321 */               out.write(32);
/* 3322 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3324 */               OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3325 */               _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 3326 */               _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3328 */               _jspx_th_html_005foption_005f31.setValue("SYBASEDB:5000");
/* 3329 */               int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 3330 */               if (_jspx_eval_html_005foption_005f31 != 0) {
/* 3331 */                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3332 */                   out = _jspx_page_context.pushBody();
/* 3333 */                   _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 3334 */                   _jspx_th_html_005foption_005f31.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3337 */                   out.print(FormatUtil.getString("Sybase"));
/* 3338 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 3339 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3342 */                 if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3343 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3346 */               if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 3347 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */               }
/*      */               
/* 3350 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 3351 */               out.write(32);
/* 3352 */               out.write("\n                                        </optgroup>\n<optgroup label=");
/* 3353 */               out.print(FormatUtil.getString("Middleware/Portal"));
/* 3354 */               out.write(62);
/* 3355 */               out.write(32);
/* 3356 */               out.write("\n                                        ");
/*      */               
/* 3358 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3359 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3360 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3362 */               _jspx_th_c_005fif_005f3.setTest("${showWMIMonitors == \"true\"}");
/* 3363 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3364 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 3366 */                   out.write("\n\t\t\t\t \t");
/*      */                   
/* 3368 */                   OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3369 */                   _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 3370 */                   _jspx_th_html_005foption_005f32.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 3372 */                   _jspx_th_html_005foption_005f32.setValue("Microsoft MQ");
/* 3373 */                   int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 3374 */                   if (_jspx_eval_html_005foption_005f32 != 0) {
/* 3375 */                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3376 */                       out = _jspx_page_context.pushBody();
/* 3377 */                       _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 3378 */                       _jspx_th_html_005foption_005f32.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3381 */                       out.print(FormatUtil.getString("Microsoft MQ (MSMQ)"));
/* 3382 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 3383 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3386 */                     if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3387 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3390 */                   if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 3391 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                   }
/*      */                   
/* 3394 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 3395 */                   out.write(32);
/* 3396 */                   out.write("\n\t\t\t\t \t");
/*      */                   
/* 3398 */                   OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3399 */                   _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 3400 */                   _jspx_th_html_005foption_005f33.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 3402 */                   _jspx_th_html_005foption_005f33.setValue("OfficeSharePointServer");
/* 3403 */                   int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 3404 */                   if (_jspx_eval_html_005foption_005f33 != 0) {
/* 3405 */                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3406 */                       out = _jspx_page_context.pushBody();
/* 3407 */                       _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 3408 */                       _jspx_th_html_005foption_005f33.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3411 */                       out.print(FormatUtil.getString("MS Office SharePoint"));
/* 3412 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 3413 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3416 */                     if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3417 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3420 */                   if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 3421 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                   }
/*      */                   
/* 3424 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 3425 */                   out.write(32);
/* 3426 */                   out.write("\n\t\t\t\t \t");
/*      */                   
/* 3428 */                   OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3429 */                   _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 3430 */                   _jspx_th_html_005foption_005f34.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 3432 */                   _jspx_th_html_005foption_005f34.setValue("BizTalkServer");
/* 3433 */                   int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 3434 */                   if (_jspx_eval_html_005foption_005f34 != 0) {
/* 3435 */                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3436 */                       out = _jspx_page_context.pushBody();
/* 3437 */                       _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 3438 */                       _jspx_th_html_005foption_005f34.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3441 */                       out.print(FormatUtil.getString("Microsoft BizTalk Server"));
/* 3442 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 3443 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3446 */                     if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3447 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3450 */                   if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 3451 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                   }
/*      */                   
/* 3454 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 3455 */                   out.write(32);
/* 3456 */                   out.write("\n                                         ");
/* 3457 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3458 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3462 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3463 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 3466 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3467 */               out.write("\n\t\t\t\t \t");
/*      */               
/* 3469 */               OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3470 */               _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 3471 */               _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3473 */               _jspx_th_html_005foption_005f35.setValue("RabbitMQ");
/* 3474 */               int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 3475 */               if (_jspx_eval_html_005foption_005f35 != 0) {
/* 3476 */                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3477 */                   out = _jspx_page_context.pushBody();
/* 3478 */                   _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 3479 */                   _jspx_th_html_005foption_005f35.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3482 */                   out.print(FormatUtil.getString("RabbitMQ"));
/* 3483 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 3484 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3487 */                 if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3488 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3491 */               if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 3492 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */               }
/*      */               
/* 3495 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 3496 */               out.write(32);
/* 3497 */               out.write("\n                                        </optgroup>\n\t\t<optgroup label=");
/* 3498 */               out.print(FormatUtil.getString("ERP"));
/* 3499 */               out.write(62);
/* 3500 */               out.write(32);
/* 3501 */               out.write("\n\t\t\t\t\t\t");
/*      */               
/* 3503 */               OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3504 */               _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 3505 */               _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3507 */               _jspx_th_html_005foption_005f36.setValue("OracleEBS");
/* 3508 */               int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 3509 */               if (_jspx_eval_html_005foption_005f36 != 0) {
/* 3510 */                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3511 */                   out = _jspx_page_context.pushBody();
/* 3512 */                   _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 3513 */                   _jspx_th_html_005foption_005f36.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3516 */                   out.print(FormatUtil.getString("OracleEBS"));
/* 3517 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 3518 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3521 */                 if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3522 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3525 */               if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 3526 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */               }
/*      */               
/* 3529 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 3530 */               out.write(32);
/* 3531 */               out.write("\n\t\t\t\t\t\t");
/*      */               
/* 3533 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3534 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3535 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3537 */               _jspx_th_c_005fif_005f4.setTest("${showWMIMonitors == \"true\"}");
/* 3538 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3539 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 3541 */                   out.write("\n\t\t\t\t\t\t\t");
/*      */                   
/* 3543 */                   OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3544 */                   _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 3545 */                   _jspx_th_html_005foption_005f37.setParent(_jspx_th_c_005fif_005f4);
/*      */                   
/* 3547 */                   _jspx_th_html_005foption_005f37.setValue("Microsoft Dynamics CRM");
/* 3548 */                   int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 3549 */                   if (_jspx_eval_html_005foption_005f37 != 0) {
/* 3550 */                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3551 */                       out = _jspx_page_context.pushBody();
/* 3552 */                       _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 3553 */                       _jspx_th_html_005foption_005f37.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3556 */                       out.print(FormatUtil.getString("Microsoft Dynamics CRM"));
/* 3557 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 3558 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3561 */                     if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3562 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3565 */                   if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 3566 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                   }
/*      */                   
/* 3569 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 3570 */                   out.write(32);
/* 3571 */                   out.write("\n\t\t\t\t\t\t");
/* 3572 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3573 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3577 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3578 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 3581 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3582 */               out.write("\n\t\t</optgroup> \n\t\t<optgroup label=");
/* 3583 */               out.print(FormatUtil.getString("Cloud Apps"));
/* 3584 */               out.write(62);
/* 3585 */               out.write(32);
/* 3586 */               out.write("\n                ");
/*      */               
/* 3588 */               OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3589 */               _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 3590 */               _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3592 */               _jspx_th_html_005foption_005f38.setValue("OpenStack");
/* 3593 */               int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 3594 */               if (_jspx_eval_html_005foption_005f38 != 0) {
/* 3595 */                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3596 */                   out = _jspx_page_context.pushBody();
/* 3597 */                   _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 3598 */                   _jspx_th_html_005foption_005f38.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3601 */                   out.print(FormatUtil.getString("OpenStack"));
/* 3602 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 3603 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3606 */                 if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3607 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3610 */               if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 3611 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */               }
/*      */               
/* 3614 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 3615 */               out.write(32);
/* 3616 */               out.write("\n        </optgroup>\n       <optgroup label=");
/* 3617 */               out.print(FormatUtil.getString("Mail Servers"));
/* 3618 */               out.write(62);
/* 3619 */               out.write(32);
/* 3620 */               out.write("\n                                        ");
/*      */               
/* 3622 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3623 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3624 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3626 */               _jspx_th_c_005fif_005f5.setTest("${isWindows == \"true\"}");
/* 3627 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3628 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 3630 */                   out.write("\n                                        ");
/*      */                   
/* 3632 */                   OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3633 */                   _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 3634 */                   _jspx_th_html_005foption_005f39.setParent(_jspx_th_c_005fif_005f5);
/*      */                   
/* 3636 */                   _jspx_th_html_005foption_005f39.setValue("Exchange-server");
/* 3637 */                   int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 3638 */                   if (_jspx_eval_html_005foption_005f39 != 0) {
/* 3639 */                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3640 */                       out = _jspx_page_context.pushBody();
/* 3641 */                       _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 3642 */                       _jspx_th_html_005foption_005f39.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 3645 */                       out.print(FormatUtil.getString("Exchange Server"));
/* 3646 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 3647 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 3650 */                     if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3651 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 3654 */                   if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 3655 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                   }
/*      */                   
/* 3658 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 3659 */                   out.write(32);
/* 3660 */                   out.write("\n                                         ");
/* 3661 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3662 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3666 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3667 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 3670 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3671 */               out.write("\n                                        </optgroup>\n       <optgroup label=");
/* 3672 */               out.print(FormatUtil.getString("Web Servers/Services"));
/* 3673 */               out.write(62);
/* 3674 */               out.write(32);
/* 3675 */               out.write("\n                                        ");
/*      */               
/* 3677 */               OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3678 */               _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 3679 */               _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3681 */               _jspx_th_html_005foption_005f40.setValue("ApacheSolr");
/* 3682 */               int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 3683 */               if (_jspx_eval_html_005foption_005f40 != 0) {
/* 3684 */                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 3685 */                   out = _jspx_page_context.pushBody();
/* 3686 */                   _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 3687 */                   _jspx_th_html_005foption_005f40.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3690 */                   out.print(FormatUtil.getString("Apache Solr"));
/* 3691 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 3692 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3695 */                 if (_jspx_eval_html_005foption_005f40 != 1) {
/* 3696 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3699 */               if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 3700 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */               }
/*      */               
/* 3703 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 3704 */               out.write(32);
/* 3705 */               out.write("\n\t   </optgroup>\n\t   <optgroup label=");
/* 3706 */               out.print(FormatUtil.getString("Services"));
/* 3707 */               out.write(62);
/* 3708 */               out.write("\n                                        ");
/*      */               
/* 3710 */               OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3711 */               _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 3712 */               _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3714 */               _jspx_th_html_005foption_005f41.setValue("Ceph Storage");
/* 3715 */               int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 3716 */               if (_jspx_eval_html_005foption_005f41 != 0) {
/* 3717 */                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 3718 */                   out = _jspx_page_context.pushBody();
/* 3719 */                   _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 3720 */                   _jspx_th_html_005foption_005f41.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3723 */                   out.print(FormatUtil.getString("Ceph Storage"));
/* 3724 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 3725 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3728 */                 if (_jspx_eval_html_005foption_005f41 != 1) {
/* 3729 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3732 */               if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 3733 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */               }
/*      */               
/* 3736 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 3737 */               out.write(32);
/* 3738 */               out.write("\n                                   \t\t");
/*      */               
/* 3740 */               OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3741 */               _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 3742 */               _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3744 */               _jspx_th_html_005foption_005f42.setValue("Coherence");
/* 3745 */               int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 3746 */               if (_jspx_eval_html_005foption_005f42 != 0) {
/* 3747 */                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 3748 */                   out = _jspx_page_context.pushBody();
/* 3749 */                   _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 3750 */                   _jspx_th_html_005foption_005f42.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3753 */                   out.print(FormatUtil.getString("Coherence"));
/* 3754 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 3755 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3758 */                 if (_jspx_eval_html_005foption_005f42 != 1) {
/* 3759 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3762 */               if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 3763 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */               }
/*      */               
/* 3766 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 3767 */               out.write(32);
/* 3768 */               out.write("\n                                        </optgroup>\n       <optgroup label=");
/* 3769 */               out.print(FormatUtil.getString("Virtualization"));
/* 3770 */               out.write(62);
/* 3771 */               out.write(32);
/* 3772 */               out.write("\n\t\t\t\t");
/*      */               
/* 3774 */               OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3775 */               _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 3776 */               _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3778 */               _jspx_th_html_005foption_005f43.setValue("Docker");
/* 3779 */               int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 3780 */               if (_jspx_eval_html_005foption_005f43 != 0) {
/* 3781 */                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 3782 */                   out = _jspx_page_context.pushBody();
/* 3783 */                   _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 3784 */                   _jspx_th_html_005foption_005f43.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3787 */                   out.print(FormatUtil.getString("Docker"));
/* 3788 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 3789 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3792 */                 if (_jspx_eval_html_005foption_005f43 != 1) {
/* 3793 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3796 */               if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 3797 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */               }
/*      */               
/* 3800 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 3801 */               out.write(32);
/* 3802 */               out.write("\n\t\t\t\t");
/*      */               
/* 3804 */               OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3805 */               _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 3806 */               _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 3808 */               _jspx_th_html_005foption_005f44.setValue("XenApp");
/* 3809 */               int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 3810 */               if (_jspx_eval_html_005foption_005f44 != 0) {
/* 3811 */                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 3812 */                   out = _jspx_page_context.pushBody();
/* 3813 */                   _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 3814 */                   _jspx_th_html_005foption_005f44.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3817 */                   out.print(FormatUtil.getString("XenApp"));
/* 3818 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 3819 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3822 */                 if (_jspx_eval_html_005foption_005f44 != 1) {
/* 3823 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3826 */               if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 3827 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */               }
/*      */               
/* 3830 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 3831 */               out.write(32);
/* 3832 */               out.write("\n\t   </optgroup>\n    ");
/* 3833 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3834 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3837 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3838 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3841 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3842 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/* 3845 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3846 */           out.write("\n</span></td>\n    </tr>\n    <tr>\n    <td colspan=\"2\">\n    \n    <div id=\"newDiv\" class=\"newDiv\">\n    <table width=\"100%\" align=\"center\" id=\"credentialDetailsInner\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n     <tr id=\"credentialName\">\n       \n        <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\">");
/* 3847 */           out.print(FormatUtil.getString("am.webclient.credentialManager.credentialName"));
/* 3848 */           out.write(" \n        <span class=\"mandatory\">&nbsp;*</span>\n        </td> ");
/* 3849 */           out.write("\n        <td width=\"50%\" class=\"whitegrayrightalign credential-popup\" ><input type=\"text\" id=\"credentialNameInput\" name=\"credentialName\" onblur=\"checkCredName()\" class=\"formtext normal\" value=\"");
/* 3850 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3852 */           out.write("\">\n        <div id=\"display\" style=\"font-size:10px\"></div>\n        </td>\n    </tr>\n\n");
/* 3853 */           if (_jspx_meth_c_005fforEach_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3855 */           out.write("\n</table>\n<input type=\"hidden\" id=\"status\" value=\"false\" name=\"status\">\n</div>\n\n</td>\n</tr>\n<tr id=\"saveButton\" > \n<td class=\"tablebottom\"></td>\n<td class=\"tablebottom credential-popup\">\n<input id=\"fromDiscovery\" name=\"fromDiscovery\" type=\"hidden\" value='");
/* 3856 */           if (_jspx_meth_c_005fout_005f58(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3858 */           out.write("'>\n\n<input id=\"save\" name=\"save\" class=\"buttons btn_highlt\" value=");
/* 3859 */           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 3861 */           out.write(" onClick=\"validateAndSave(this.value)\" align=\"right\" type=\"button\">\n&nbsp;&nbsp;\n<input id=\"cancel\" name=\"cancel\" class=\"buttons btn_highlt\" value=");
/* 3862 */           out.print(FormatUtil.getString("Cancel"));
/* 3863 */           out.write(" onClick=\"closeFunction()\" align=\"right\" type=\"button\">\n</td>\n</tr>\n\n\n    </table>\n");
/* 3864 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3865 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3869 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3870 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 3873 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3874 */         out.write("\n</body>\n\n<script>\nonLoad();\n");
/* 3875 */         if (_jspx_meth_c_005fif_005f15(_jspx_page_context))
/*      */           return;
/* 3877 */         out.write("\n</script>\n");
/*      */       }
/* 3879 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3880 */         out = _jspx_out;
/* 3881 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3882 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3883 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3886 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3892 */     PageContext pageContext = _jspx_page_context;
/* 3893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3895 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3896 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3897 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3899 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3901 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3902 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3903 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3904 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3905 */       return true;
/*      */     }
/* 3907 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3913 */     PageContext pageContext = _jspx_page_context;
/* 3914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3916 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3917 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3918 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3920 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.saved}");
/* 3921 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3922 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3924 */         out.write("\n        checkOnLoad();\n\n    ");
/* 3925 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3926 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3930 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3931 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3932 */       return true;
/*      */     }
/* 3934 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3940 */     PageContext pageContext = _jspx_page_context;
/* 3941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3943 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3944 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3945 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 3947 */     _jspx_th_fmt_005fmessage_005f0.setKey("Modify");
/* 3948 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3949 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3950 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3951 */       return true;
/*      */     }
/* 3953 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3959 */     PageContext pageContext = _jspx_page_context;
/* 3960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3962 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3963 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3964 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 3966 */     _jspx_th_fmt_005fmessage_005f1.setKey("Cancel");
/* 3967 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3968 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3969 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3970 */       return true;
/*      */     }
/* 3972 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3978 */     PageContext pageContext = _jspx_page_context;
/* 3979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3981 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3982 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3983 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3985 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3986 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3987 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3989 */         out.write("\n\t\talert(\"We have disabled this feature for the Online Demo. This feature is available when you download and install the software for evaluation or in production.\");//No I18N\n\t\treturn false;\n\t");
/* 3990 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3991 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3995 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3996 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3997 */       return true;
/*      */     }
/* 3999 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4005 */     PageContext pageContext = _jspx_page_context;
/* 4006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4008 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4009 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4010 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4012 */     _jspx_th_c_005fout_005f1.setValue("${credentialName}");
/* 4013 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4014 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4015 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4016 */       return true;
/*      */     }
/* 4018 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4024 */     PageContext pageContext = _jspx_page_context;
/* 4025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4027 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4028 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4029 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4031 */     _jspx_th_c_005fforEach_005f0.setVar("prop");
/*      */     
/* 4033 */     _jspx_th_c_005fforEach_005f0.setItems("${credentialValuesInList}");
/*      */     
/* 4035 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4036 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 4038 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4039 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 4041 */           out.write("\n\n    \n    ");
/* 4042 */           boolean bool; if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4043 */             return true;
/* 4044 */           out.write("\n    <input type=\"hidden\" name=\"credentialID\" value=\"");
/* 4045 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4046 */             return true;
/* 4047 */           out.write("\">\n    <tr id=\"space\"><td colspan=2 height=\"5\"></td></tr>\n    ");
/* 4048 */           if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4049 */             return true;
/* 4050 */           out.write("\n\n    ");
/* 4051 */           if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4052 */             return true;
/* 4053 */           out.write("    \n\n    ");
/* 4054 */           if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4055 */             return true;
/* 4056 */           out.write("\n\n    ");
/* 4057 */           if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4058 */             return true;
/* 4059 */           out.write("\n\n    ");
/* 4060 */           if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4061 */             return true;
/* 4062 */           out.write("\n        \n    ");
/* 4063 */           if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4064 */             return true;
/* 4065 */           out.write(10);
/* 4066 */           out.write(10);
/* 4067 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4068 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4072 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 4073 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4076 */         int tmp487_486 = 0; int[] tmp487_484 = _jspx_push_body_count_c_005fforEach_005f0; int tmp489_488 = tmp487_484[tmp487_486];tmp487_484[tmp487_486] = (tmp489_488 - 1); if (tmp489_488 <= 0) break;
/* 4077 */         out = _jspx_page_context.popBody(); }
/* 4078 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 4080 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4081 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 4083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4088 */     PageContext pageContext = _jspx_page_context;
/* 4089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4091 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4092 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4093 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4095 */     _jspx_th_c_005fset_005f0.setVar("typeSet");
/* 4096 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4097 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4098 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4099 */         out = _jspx_page_context.pushBody();
/* 4100 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4101 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4102 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4105 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4106 */           return true;
/* 4107 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4108 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4111 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4112 */         out = _jspx_page_context.popBody();
/* 4113 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4116 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4117 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4118 */       return true;
/*      */     }
/* 4120 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4126 */     PageContext pageContext = _jspx_page_context;
/* 4127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4129 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4130 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4131 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4133 */     _jspx_th_c_005fout_005f2.setValue("${prop.rowType}");
/* 4134 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4135 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4136 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4137 */       return true;
/*      */     }
/* 4139 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4145 */     PageContext pageContext = _jspx_page_context;
/* 4146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4148 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4149 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4150 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4152 */     _jspx_th_c_005fout_005f3.setValue("${credentialID}");
/* 4153 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4154 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4155 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4156 */       return true;
/*      */     }
/* 4158 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4164 */     PageContext pageContext = _jspx_page_context;
/* 4165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4167 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4168 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 4169 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4171 */     _jspx_th_c_005fif_005f6.setTest("${typeSet == \"Text\" }");
/* 4172 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 4173 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 4175 */         out.write("\n    <tr id=\"");
/* 4176 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4177 */           return true;
/* 4178 */         out.write("\" style=\"");
/* 4179 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4180 */           return true;
/* 4181 */         out.write("\">\n    \n            <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 4182 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4183 */           return true;
/* 4184 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\" ><input class=\"formtext normal\" type=");
/* 4185 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4186 */           return true;
/* 4187 */         out.write(" value=\"");
/* 4188 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4189 */           return true;
/* 4190 */         out.write("\" name=\"");
/* 4191 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4192 */           return true;
/* 4193 */         out.write("\"></td>\n    </tr>\n    ");
/* 4194 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 4195 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4199 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 4200 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4201 */       return true;
/*      */     }
/* 4203 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 4204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4209 */     PageContext pageContext = _jspx_page_context;
/* 4210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4212 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4213 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4214 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4216 */     _jspx_th_c_005fout_005f4.setValue("${prop.rowID}");
/* 4217 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4218 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4219 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4220 */       return true;
/*      */     }
/* 4222 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4228 */     PageContext pageContext = _jspx_page_context;
/* 4229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4231 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4232 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4233 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4235 */     _jspx_th_c_005fout_005f5.setValue("${prop.display}");
/* 4236 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4237 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4238 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4239 */       return true;
/*      */     }
/* 4241 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4242 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4247 */     PageContext pageContext = _jspx_page_context;
/* 4248 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4250 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4251 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4252 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4254 */     _jspx_th_c_005fout_005f6.setValue("${prop.rowDisplayName}");
/* 4255 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4256 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4257 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4258 */       return true;
/*      */     }
/* 4260 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4261 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4266 */     PageContext pageContext = _jspx_page_context;
/* 4267 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4269 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4270 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4271 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4273 */     _jspx_th_c_005fout_005f7.setValue("${prop.rowType}");
/* 4274 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4275 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4276 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4277 */       return true;
/*      */     }
/* 4279 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4280 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4285 */     PageContext pageContext = _jspx_page_context;
/* 4286 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4288 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4289 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4290 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4292 */     _jspx_th_c_005fout_005f8.setValue("${prop.rowValue}");
/* 4293 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4294 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4295 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4296 */       return true;
/*      */     }
/* 4298 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4299 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4304 */     PageContext pageContext = _jspx_page_context;
/* 4305 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4307 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4308 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4309 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4311 */     _jspx_th_c_005fout_005f9.setValue("${prop.rowName}");
/* 4312 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4313 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4314 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4315 */       return true;
/*      */     }
/* 4317 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4323 */     PageContext pageContext = _jspx_page_context;
/* 4324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4326 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4327 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 4328 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4330 */     _jspx_th_c_005fif_005f7.setTest("${typeSet == \"password\" }");
/* 4331 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 4332 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 4334 */         out.write("\n\n            <tr id=\"");
/* 4335 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4336 */           return true;
/* 4337 */         out.write("\" style=\"");
/* 4338 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4339 */           return true;
/* 4340 */         out.write("\">\n            \n            <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 4341 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4342 */           return true;
/* 4343 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\">\n            <span id=\"tdSpan_");
/* 4344 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4345 */           return true;
/* 4346 */         out.write("\">\n            \n            <input class=\"formtext normal\" ");
/* 4347 */         if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4348 */           return true;
/* 4349 */         out.write(" type=");
/* 4350 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4351 */           return true;
/* 4352 */         out.write(" value=\"");
/* 4353 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4354 */           return true;
/* 4355 */         out.write("\" name=\"");
/* 4356 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4357 */           return true;
/* 4358 */         out.write("\">&nbsp;  ");
/* 4359 */         out.write("\n            </span>\n            ");
/* 4360 */         if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4361 */           return true;
/* 4362 */         out.write("\n            ");
/* 4363 */         if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4364 */           return true;
/* 4365 */         out.write("\n            </td>\n            </tr>                       \n    ");
/* 4366 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 4367 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4371 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 4372 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4373 */       return true;
/*      */     }
/* 4375 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 4376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4381 */     PageContext pageContext = _jspx_page_context;
/* 4382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4384 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4385 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4386 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4388 */     _jspx_th_c_005fout_005f10.setValue("${prop.rowID}");
/* 4389 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4390 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4391 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4392 */       return true;
/*      */     }
/* 4394 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4400 */     PageContext pageContext = _jspx_page_context;
/* 4401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4403 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4404 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4405 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4407 */     _jspx_th_c_005fout_005f11.setValue("${prop.display}");
/* 4408 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4409 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4410 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4411 */       return true;
/*      */     }
/* 4413 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4419 */     PageContext pageContext = _jspx_page_context;
/* 4420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4422 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4423 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4424 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4426 */     _jspx_th_c_005fout_005f12.setValue("${prop.rowDisplayName}");
/* 4427 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4428 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4429 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4430 */       return true;
/*      */     }
/* 4432 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4438 */     PageContext pageContext = _jspx_page_context;
/* 4439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4441 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4442 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4443 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4445 */     _jspx_th_c_005fout_005f13.setValue("${prop.rowID}");
/* 4446 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4447 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4448 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4449 */       return true;
/*      */     }
/* 4451 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4457 */     PageContext pageContext = _jspx_page_context;
/* 4458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4460 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4461 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 4462 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4464 */     _jspx_th_c_005fif_005f8.setTest("${buttonToPerform=='Update'}");
/* 4465 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 4466 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 4468 */         out.write("readonly");
/* 4469 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 4470 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4474 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 4475 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4476 */       return true;
/*      */     }
/* 4478 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 4479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4484 */     PageContext pageContext = _jspx_page_context;
/* 4485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4487 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4488 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4489 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4491 */     _jspx_th_c_005fout_005f14.setValue("${prop.rowType}");
/* 4492 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4493 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4494 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4495 */       return true;
/*      */     }
/* 4497 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4498 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4503 */     PageContext pageContext = _jspx_page_context;
/* 4504 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4506 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4507 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4508 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4510 */     _jspx_th_c_005fout_005f15.setValue("${prop.rowValue}");
/* 4511 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4512 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4513 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4514 */       return true;
/*      */     }
/* 4516 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4522 */     PageContext pageContext = _jspx_page_context;
/* 4523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4525 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4526 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4527 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4529 */     _jspx_th_c_005fout_005f16.setValue("${prop.rowName}");
/* 4530 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4531 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4532 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4533 */       return true;
/*      */     }
/* 4535 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4541 */     PageContext pageContext = _jspx_page_context;
/* 4542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4544 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4545 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4546 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4548 */     _jspx_th_c_005fif_005f9.setTest("${buttonToPerform=='Update'}");
/* 4549 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4550 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4552 */         out.write("\n            <input type=\"hidden\" name=\"hidden_");
/* 4553 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4554 */           return true;
/* 4555 */         out.write("\" value=\"encoded\"/>  <!--No I18N-->\n            <span id=\"modifySpan_");
/* 4556 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4557 */           return true;
/* 4558 */         out.write("\">\n            <a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline;\" onclick=\"modifyPassword('");
/* 4559 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4560 */           return true;
/* 4561 */         out.write(39);
/* 4562 */         out.write(44);
/* 4563 */         out.write(39);
/* 4564 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4565 */           return true;
/* 4566 */         out.write(39);
/* 4567 */         out.write(44);
/* 4568 */         out.write(39);
/* 4569 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4570 */           return true;
/* 4571 */         out.write(39);
/* 4572 */         out.write(44);
/* 4573 */         out.write(39);
/* 4574 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4575 */           return true;
/* 4576 */         out.write("')\">");
/* 4577 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4578 */           return true;
/* 4579 */         out.write("&nbsp; ");
/* 4580 */         if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f9, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4581 */           return true;
/* 4582 */         out.write("</a>\n            </span>\n            ");
/* 4583 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4584 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4588 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4589 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4590 */       return true;
/*      */     }
/* 4592 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4593 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4598 */     PageContext pageContext = _jspx_page_context;
/* 4599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4601 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4602 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4603 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4605 */     _jspx_th_c_005fout_005f17.setValue("${prop.rowID}");
/* 4606 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4607 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4608 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4609 */       return true;
/*      */     }
/* 4611 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4617 */     PageContext pageContext = _jspx_page_context;
/* 4618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4620 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4621 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4622 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4624 */     _jspx_th_c_005fout_005f18.setValue("${prop.rowID}");
/* 4625 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4626 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4627 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4628 */       return true;
/*      */     }
/* 4630 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4636 */     PageContext pageContext = _jspx_page_context;
/* 4637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4639 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4640 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4641 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4643 */     _jspx_th_c_005fout_005f19.setValue("${prop.rowID}");
/* 4644 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4645 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4646 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4647 */       return true;
/*      */     }
/* 4649 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4650 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4655 */     PageContext pageContext = _jspx_page_context;
/* 4656 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4658 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4659 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4660 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4662 */     _jspx_th_c_005fout_005f20.setValue("${credentialID}");
/* 4663 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4664 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4665 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4666 */       return true;
/*      */     }
/* 4668 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4669 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4674 */     PageContext pageContext = _jspx_page_context;
/* 4675 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4677 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4678 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4679 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4681 */     _jspx_th_c_005fout_005f21.setValue("${prop.rowValue}");
/* 4682 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4683 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4684 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4685 */       return true;
/*      */     }
/* 4687 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4693 */     PageContext pageContext = _jspx_page_context;
/* 4694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4696 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4697 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4698 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4700 */     _jspx_th_c_005fout_005f22.setValue("${prop.rowDisplayName}");
/* 4701 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4702 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4704 */       return true;
/*      */     }
/* 4706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4712 */     PageContext pageContext = _jspx_page_context;
/* 4713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4715 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4716 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4717 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4719 */     _jspx_th_fmt_005fmessage_005f2.setKey("Modify");
/* 4720 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4721 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4722 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4723 */       return true;
/*      */     }
/* 4725 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4731 */     PageContext pageContext = _jspx_page_context;
/* 4732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4734 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4735 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 4736 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4738 */     _jspx_th_c_005fout_005f23.setValue("${prop.rowDisplayName}");
/* 4739 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 4740 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 4741 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4742 */       return true;
/*      */     }
/* 4744 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4750 */     PageContext pageContext = _jspx_page_context;
/* 4751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4753 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4754 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4755 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 4757 */     _jspx_th_c_005fif_005f10.setTest("${buttonToPerform !='Update'}");
/* 4758 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4759 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4761 */         out.write("\n                <input type=\"hidden\" name=\"hidden_");
/* 4762 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4763 */           return true;
/* 4764 */         out.write("\" value=\"decoded\"/>  <!--No I18N-->\n            ");
/* 4765 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4770 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4771 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4772 */       return true;
/*      */     }
/* 4774 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4780 */     PageContext pageContext = _jspx_page_context;
/* 4781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4783 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4784 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 4785 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4787 */     _jspx_th_c_005fout_005f24.setValue("${prop.rowID}");
/* 4788 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 4789 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 4790 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4791 */       return true;
/*      */     }
/* 4793 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4799 */     PageContext pageContext = _jspx_page_context;
/* 4800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4802 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4803 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4804 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4806 */     _jspx_th_c_005fif_005f11.setTest("${typeSet == \"checkbox\" }");
/* 4807 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4808 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4810 */         out.write("\n           <tr id=\"");
/* 4811 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4812 */           return true;
/* 4813 */         out.write("\" style=\"");
/* 4814 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4815 */           return true;
/* 4816 */         out.write("\">\n           \n            <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 4817 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4818 */           return true;
/* 4819 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\"><input name=\"");
/* 4820 */         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4821 */           return true;
/* 4822 */         out.write(34);
/* 4823 */         out.write(32);
/* 4824 */         out.write(32);
/* 4825 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4826 */           return true;
/* 4827 */         out.write(" onclick=\"toggleRowsAsPerArgs('");
/* 4828 */         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4829 */           return true;
/* 4830 */         out.write(39);
/* 4831 */         out.write(44);
/* 4832 */         out.write(39);
/* 4833 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4834 */           return true;
/* 4835 */         out.write(39);
/* 4836 */         out.write(44);
/* 4837 */         out.write(39);
/* 4838 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4839 */           return true;
/* 4840 */         out.write("')\" type=");
/* 4841 */         if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fif_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4842 */           return true;
/* 4843 */         out.write("></td>\n            </tr>                                   \n\n    ");
/* 4844 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4845 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4849 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4850 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4851 */       return true;
/*      */     }
/* 4853 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4859 */     PageContext pageContext = _jspx_page_context;
/* 4860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4862 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4863 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 4864 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4866 */     _jspx_th_c_005fout_005f25.setValue("${prop.rowID}");
/* 4867 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 4868 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 4869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4870 */       return true;
/*      */     }
/* 4872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4878 */     PageContext pageContext = _jspx_page_context;
/* 4879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4881 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4882 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 4883 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4885 */     _jspx_th_c_005fout_005f26.setValue("${prop.display}");
/* 4886 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 4887 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 4888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 4889 */       return true;
/*      */     }
/* 4891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 4892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4897 */     PageContext pageContext = _jspx_page_context;
/* 4898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4900 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4901 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 4902 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4904 */     _jspx_th_c_005fout_005f27.setValue("${prop.rowDisplayName}");
/* 4905 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 4906 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 4907 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 4908 */       return true;
/*      */     }
/* 4910 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 4911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4916 */     PageContext pageContext = _jspx_page_context;
/* 4917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4919 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4920 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 4921 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4923 */     _jspx_th_c_005fout_005f28.setValue("${prop.checkBoxName}");
/* 4924 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 4925 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 4926 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 4927 */       return true;
/*      */     }
/* 4929 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 4930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4935 */     PageContext pageContext = _jspx_page_context;
/* 4936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4938 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4939 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 4940 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4942 */     _jspx_th_c_005fout_005f29.setValue("${prop.checked}");
/* 4943 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 4944 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 4945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 4946 */       return true;
/*      */     }
/* 4948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 4949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4954 */     PageContext pageContext = _jspx_page_context;
/* 4955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4957 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4958 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 4959 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4961 */     _jspx_th_c_005fout_005f30.setValue("${prop.argsToShow}");
/* 4962 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 4963 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 4964 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 4965 */       return true;
/*      */     }
/* 4967 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 4968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4973 */     PageContext pageContext = _jspx_page_context;
/* 4974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4976 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4977 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 4978 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4980 */     _jspx_th_c_005fout_005f31.setValue("${prop.argsToHide}");
/* 4981 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 4982 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 4983 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 4984 */       return true;
/*      */     }
/* 4986 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 4987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4992 */     PageContext pageContext = _jspx_page_context;
/* 4993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4995 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4996 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 4997 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4999 */     _jspx_th_c_005fout_005f32.setValue("${prop.checkBoxName}");
/* 5000 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5001 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5002 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5003 */       return true;
/*      */     }
/* 5005 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5011 */     PageContext pageContext = _jspx_page_context;
/* 5012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5014 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5015 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5016 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 5018 */     _jspx_th_c_005fout_005f33.setValue("${prop.rowType}");
/* 5019 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5020 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5021 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5022 */       return true;
/*      */     }
/* 5024 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5030 */     PageContext pageContext = _jspx_page_context;
/* 5031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5033 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5034 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 5035 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5037 */     _jspx_th_c_005fif_005f12.setTest("${typeSet == \"radio\" }");
/* 5038 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 5039 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 5041 */         out.write("\n            <tr id=\"");
/* 5042 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fif_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5043 */           return true;
/* 5044 */         out.write("\" style=\"");
/* 5045 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fif_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5046 */           return true;
/* 5047 */         out.write("\">\n            \n            <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 5048 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fif_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5049 */           return true;
/* 5050 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\">\n            ");
/* 5051 */         if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5052 */           return true;
/* 5053 */         out.write("\n            </td>\n            </tr>\n    ");
/* 5054 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 5055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5059 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 5060 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5061 */       return true;
/*      */     }
/* 5063 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 5064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5069 */     PageContext pageContext = _jspx_page_context;
/* 5070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5072 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5073 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5074 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5076 */     _jspx_th_c_005fout_005f34.setValue("${prop.rowID}");
/* 5077 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5078 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5079 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5080 */       return true;
/*      */     }
/* 5082 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5088 */     PageContext pageContext = _jspx_page_context;
/* 5089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5091 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5092 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5093 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5095 */     _jspx_th_c_005fout_005f35.setValue("${prop.display}");
/* 5096 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5097 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5098 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5099 */       return true;
/*      */     }
/* 5101 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5102 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5107 */     PageContext pageContext = _jspx_page_context;
/* 5108 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5110 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5111 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 5112 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5114 */     _jspx_th_c_005fout_005f36.setValue("${prop.rowDisplayName}");
/* 5115 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 5116 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 5117 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5118 */       return true;
/*      */     }
/* 5120 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 5121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5126 */     PageContext pageContext = _jspx_page_context;
/* 5127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5129 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5130 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5131 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 5133 */     _jspx_th_c_005fforEach_005f1.setVar("radioValueProperties");
/*      */     
/* 5135 */     _jspx_th_c_005fforEach_005f1.setItems("${prop.radioValueProperties}");
/*      */     
/* 5137 */     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 5138 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 5140 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5141 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 5143 */           out.write("\n                 <input id=\"");
/* 5144 */           boolean bool; if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5145 */             return true;
/* 5146 */           out.write("\" value= \"");
/* 5147 */           if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5148 */             return true;
/* 5149 */           out.write(34);
/* 5150 */           out.write(32);
/* 5151 */           if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5152 */             return true;
/* 5153 */           out.write(" name=\"");
/* 5154 */           if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5155 */             return true;
/* 5156 */           out.write("\" type=\"radio\" ><span class=\"bodytext\">");
/* 5157 */           if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5158 */             return true;
/* 5159 */           out.write("</span> &nbsp;\n             ");
/* 5160 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5161 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5165 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 5166 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5169 */         int tmp368_367 = 0; int[] tmp368_365 = _jspx_push_body_count_c_005fforEach_005f1; int tmp370_369 = tmp368_365[tmp368_367];tmp368_365[tmp368_367] = (tmp370_369 - 1); if (tmp370_369 <= 0) break;
/* 5170 */         out = _jspx_page_context.popBody(); }
/* 5171 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 5173 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 5174 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 5176 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5181 */     PageContext pageContext = _jspx_page_context;
/* 5182 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5184 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5185 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 5186 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5188 */     _jspx_th_c_005fout_005f37.setValue("${radioValueProperties.radioID}");
/* 5189 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 5190 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 5191 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5192 */       return true;
/*      */     }
/* 5194 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 5195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5200 */     PageContext pageContext = _jspx_page_context;
/* 5201 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5203 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5204 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 5205 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5207 */     _jspx_th_c_005fout_005f38.setValue("${radioValueProperties.radioValue}");
/* 5208 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 5209 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 5210 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5211 */       return true;
/*      */     }
/* 5213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 5214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5219 */     PageContext pageContext = _jspx_page_context;
/* 5220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5222 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5223 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 5224 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5226 */     _jspx_th_c_005fout_005f39.setValue("${radioValueProperties.checked}");
/* 5227 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 5228 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 5229 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5230 */       return true;
/*      */     }
/* 5232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 5233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5238 */     PageContext pageContext = _jspx_page_context;
/* 5239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5241 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5242 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 5243 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5245 */     _jspx_th_c_005fout_005f40.setValue("${radioValueProperties.radioName}");
/* 5246 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 5247 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 5248 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 5249 */       return true;
/*      */     }
/* 5251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 5252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5257 */     PageContext pageContext = _jspx_page_context;
/* 5258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5260 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5261 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 5262 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5264 */     _jspx_th_c_005fout_005f41.setValue("${radioValueProperties.radioDisplayValue}");
/* 5265 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 5266 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 5267 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 5268 */       return true;
/*      */     }
/* 5270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 5271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5276 */     PageContext pageContext = _jspx_page_context;
/* 5277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5279 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5280 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 5281 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5283 */     _jspx_th_c_005fif_005f13.setTest("${typeSet == \"dropDown\" }");
/* 5284 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 5285 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 5287 */         out.write("\n            <tr id=\"");
/* 5288 */         if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5289 */           return true;
/* 5290 */         out.write("\" style=\"");
/* 5291 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5292 */           return true;
/* 5293 */         out.write("\">\n            <td class=\"bodytext whitegrayrightalign label-align\" width=\"30%\"> ");
/* 5294 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5295 */           return true;
/* 5296 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\">\n                <select name=\"");
/* 5297 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5298 */           return true;
/* 5299 */         out.write("\" id=\"");
/* 5300 */         if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5301 */           return true;
/* 5302 */         out.write("\" class=\"formtext normal\" onChange=\"");
/* 5303 */         if (_jspx_meth_c_005fout_005f47(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5304 */           return true;
/* 5305 */         out.write("\" >\n                    ");
/* 5306 */         if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5307 */           return true;
/* 5308 */         out.write("\n                </select>\n            </td>\n            </tr>\n    ");
/* 5309 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 5310 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5314 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 5315 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5316 */       return true;
/*      */     }
/* 5318 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5324 */     PageContext pageContext = _jspx_page_context;
/* 5325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5327 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5328 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 5329 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5331 */     _jspx_th_c_005fout_005f42.setValue("${prop.rowID}");
/* 5332 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 5333 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 5334 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 5335 */       return true;
/*      */     }
/* 5337 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 5338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5343 */     PageContext pageContext = _jspx_page_context;
/* 5344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5346 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5347 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 5348 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5350 */     _jspx_th_c_005fout_005f43.setValue("${prop.display}");
/* 5351 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 5352 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 5353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 5354 */       return true;
/*      */     }
/* 5356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 5357 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5362 */     PageContext pageContext = _jspx_page_context;
/* 5363 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5365 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5366 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 5367 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5369 */     _jspx_th_c_005fout_005f44.setValue("${prop.rowName}");
/* 5370 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 5371 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 5372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 5373 */       return true;
/*      */     }
/* 5375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 5376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5381 */     PageContext pageContext = _jspx_page_context;
/* 5382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5384 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5385 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 5386 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5388 */     _jspx_th_c_005fout_005f45.setValue("${prop.dropDownName}");
/* 5389 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 5390 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 5391 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 5392 */       return true;
/*      */     }
/* 5394 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 5395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5400 */     PageContext pageContext = _jspx_page_context;
/* 5401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5403 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5404 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 5405 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5407 */     _jspx_th_c_005fout_005f46.setValue("${prop.dropDownID}");
/* 5408 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 5409 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 5410 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 5411 */       return true;
/*      */     }
/* 5413 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 5414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f47(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5419 */     PageContext pageContext = _jspx_page_context;
/* 5420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5422 */     OutTag _jspx_th_c_005fout_005f47 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5423 */     _jspx_th_c_005fout_005f47.setPageContext(_jspx_page_context);
/* 5424 */     _jspx_th_c_005fout_005f47.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5426 */     _jspx_th_c_005fout_005f47.setValue("${prop.onChangeMethod}");
/* 5427 */     int _jspx_eval_c_005fout_005f47 = _jspx_th_c_005fout_005f47.doStartTag();
/* 5428 */     if (_jspx_th_c_005fout_005f47.doEndTag() == 5) {
/* 5429 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 5430 */       return true;
/*      */     }
/* 5432 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f47);
/* 5433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5438 */     PageContext pageContext = _jspx_page_context;
/* 5439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5441 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5442 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 5443 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 5445 */     _jspx_th_c_005fforEach_005f2.setVar("dropDownValue");
/*      */     
/* 5447 */     _jspx_th_c_005fforEach_005f2.setItems("${prop.dropDownValuesAndProperties}");
/*      */     
/* 5449 */     _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 5450 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 5452 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 5453 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 5455 */           out.write("\n                        ");
/* 5456 */           if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5457 */             return true;
/* 5458 */           out.write("\n                    ");
/* 5459 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 5460 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5464 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 5465 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5468 */         int tmp202_201 = 0; int[] tmp202_199 = _jspx_push_body_count_c_005fforEach_005f2; int tmp204_203 = tmp202_199[tmp202_201];tmp202_199[tmp202_201] = (tmp204_203 - 1); if (tmp204_203 <= 0) break;
/* 5469 */         out = _jspx_page_context.popBody(); }
/* 5470 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 5472 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 5473 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 5475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5480 */     PageContext pageContext = _jspx_page_context;
/* 5481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5483 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5484 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 5485 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/* 5486 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 5487 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 5489 */         out.write("\n                        ");
/* 5490 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5491 */           return true;
/* 5492 */         out.write("\n                        ");
/* 5493 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5494 */           return true;
/* 5495 */         out.write("\n                        ");
/* 5496 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5501 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5502 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5503 */       return true;
/*      */     }
/* 5505 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5511 */     PageContext pageContext = _jspx_page_context;
/* 5512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5514 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5515 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5516 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5518 */     _jspx_th_c_005fwhen_005f0.setTest("${dropDownValue.Selected == 'selected'}");
/* 5519 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5520 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5522 */         out.write("\n                            <option value=\"");
/* 5523 */         if (_jspx_meth_c_005fout_005f48(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5524 */           return true;
/* 5525 */         out.write("\" selected=\"selected\" >");
/* 5526 */         if (_jspx_meth_c_005fout_005f49(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5527 */           return true;
/* 5528 */         out.write("</option>\n                        ");
/* 5529 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5530 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5534 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5535 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5536 */       return true;
/*      */     }
/* 5538 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f48(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5544 */     PageContext pageContext = _jspx_page_context;
/* 5545 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5547 */     OutTag _jspx_th_c_005fout_005f48 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5548 */     _jspx_th_c_005fout_005f48.setPageContext(_jspx_page_context);
/* 5549 */     _jspx_th_c_005fout_005f48.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5551 */     _jspx_th_c_005fout_005f48.setValue("${dropDownValue.dropDownValue}");
/* 5552 */     int _jspx_eval_c_005fout_005f48 = _jspx_th_c_005fout_005f48.doStartTag();
/* 5553 */     if (_jspx_th_c_005fout_005f48.doEndTag() == 5) {
/* 5554 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 5555 */       return true;
/*      */     }
/* 5557 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f48);
/* 5558 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f49(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5563 */     PageContext pageContext = _jspx_page_context;
/* 5564 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5566 */     OutTag _jspx_th_c_005fout_005f49 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5567 */     _jspx_th_c_005fout_005f49.setPageContext(_jspx_page_context);
/* 5568 */     _jspx_th_c_005fout_005f49.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5570 */     _jspx_th_c_005fout_005f49.setValue("${dropDownValue.dropDownDisplayName}");
/* 5571 */     int _jspx_eval_c_005fout_005f49 = _jspx_th_c_005fout_005f49.doStartTag();
/* 5572 */     if (_jspx_th_c_005fout_005f49.doEndTag() == 5) {
/* 5573 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 5574 */       return true;
/*      */     }
/* 5576 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f49);
/* 5577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5582 */     PageContext pageContext = _jspx_page_context;
/* 5583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5585 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5586 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5587 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5588 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5589 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5591 */         out.write("\n                            <option value=\"");
/* 5592 */         if (_jspx_meth_c_005fout_005f50(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5593 */           return true;
/* 5594 */         out.write(34);
/* 5595 */         out.write(62);
/* 5596 */         if (_jspx_meth_c_005fout_005f51(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5597 */           return true;
/* 5598 */         out.write("</option>\n                        ");
/* 5599 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5600 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5604 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5605 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5606 */       return true;
/*      */     }
/* 5608 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f50(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5614 */     PageContext pageContext = _jspx_page_context;
/* 5615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5617 */     OutTag _jspx_th_c_005fout_005f50 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5618 */     _jspx_th_c_005fout_005f50.setPageContext(_jspx_page_context);
/* 5619 */     _jspx_th_c_005fout_005f50.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5621 */     _jspx_th_c_005fout_005f50.setValue("${dropDownValue.dropDownValue}");
/* 5622 */     int _jspx_eval_c_005fout_005f50 = _jspx_th_c_005fout_005f50.doStartTag();
/* 5623 */     if (_jspx_th_c_005fout_005f50.doEndTag() == 5) {
/* 5624 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 5625 */       return true;
/*      */     }
/* 5627 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f50);
/* 5628 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f51(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5633 */     PageContext pageContext = _jspx_page_context;
/* 5634 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5636 */     OutTag _jspx_th_c_005fout_005f51 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5637 */     _jspx_th_c_005fout_005f51.setPageContext(_jspx_page_context);
/* 5638 */     _jspx_th_c_005fout_005f51.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5640 */     _jspx_th_c_005fout_005f51.setValue("${dropDownValue.dropDownDisplayName}");
/* 5641 */     int _jspx_eval_c_005fout_005f51 = _jspx_th_c_005fout_005f51.doStartTag();
/* 5642 */     if (_jspx_th_c_005fout_005f51.doEndTag() == 5) {
/* 5643 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 5644 */       return true;
/*      */     }
/* 5646 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f51);
/* 5647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5652 */     PageContext pageContext = _jspx_page_context;
/* 5653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5655 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5656 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 5657 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5659 */     _jspx_th_c_005fif_005f14.setTest("${typeSet == \"textarea\"}");
/* 5660 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 5661 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */       for (;;) {
/* 5663 */         out.write("\n            \n            <tr id=\"");
/* 5664 */         if (_jspx_meth_c_005fout_005f52(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5665 */           return true;
/* 5666 */         out.write("\" style=\"");
/* 5667 */         if (_jspx_meth_c_005fout_005f53(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5668 */           return true;
/* 5669 */         out.write("\">\n            <td class=\"bodytext whitegrayrightalign credential-popup label-align\" width=\"30%\"> ");
/* 5670 */         if (_jspx_meth_c_005fout_005f54(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5671 */           return true;
/* 5672 */         out.write("</td>\n            <td width=\"50%\" class=\"whitegrayrightalign credential-popup\"><textarea style=\"width: 95%;\" class=\"formtextarea\" id=\"");
/* 5673 */         if (_jspx_meth_c_005fout_005f55(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5674 */           return true;
/* 5675 */         out.write("\" name=\"");
/* 5676 */         if (_jspx_meth_c_005fout_005f56(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5677 */           return true;
/* 5678 */         out.write(34);
/* 5679 */         out.write(62);
/* 5680 */         out.write(32);
/* 5681 */         if (_jspx_meth_c_005fout_005f57(_jspx_th_c_005fif_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5682 */           return true;
/* 5683 */         out.write("</textarea></td>\n            </tr>\n    ");
/* 5684 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 5685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5689 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 5690 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5691 */       return true;
/*      */     }
/* 5693 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 5694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f52(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5699 */     PageContext pageContext = _jspx_page_context;
/* 5700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5702 */     OutTag _jspx_th_c_005fout_005f52 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5703 */     _jspx_th_c_005fout_005f52.setPageContext(_jspx_page_context);
/* 5704 */     _jspx_th_c_005fout_005f52.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5706 */     _jspx_th_c_005fout_005f52.setValue("${prop.rowID}");
/* 5707 */     int _jspx_eval_c_005fout_005f52 = _jspx_th_c_005fout_005f52.doStartTag();
/* 5708 */     if (_jspx_th_c_005fout_005f52.doEndTag() == 5) {
/* 5709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 5710 */       return true;
/*      */     }
/* 5712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f52);
/* 5713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f53(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5718 */     PageContext pageContext = _jspx_page_context;
/* 5719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5721 */     OutTag _jspx_th_c_005fout_005f53 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5722 */     _jspx_th_c_005fout_005f53.setPageContext(_jspx_page_context);
/* 5723 */     _jspx_th_c_005fout_005f53.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5725 */     _jspx_th_c_005fout_005f53.setValue("${prop.display}");
/* 5726 */     int _jspx_eval_c_005fout_005f53 = _jspx_th_c_005fout_005f53.doStartTag();
/* 5727 */     if (_jspx_th_c_005fout_005f53.doEndTag() == 5) {
/* 5728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 5729 */       return true;
/*      */     }
/* 5731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f53);
/* 5732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f54(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5737 */     PageContext pageContext = _jspx_page_context;
/* 5738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5740 */     OutTag _jspx_th_c_005fout_005f54 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5741 */     _jspx_th_c_005fout_005f54.setPageContext(_jspx_page_context);
/* 5742 */     _jspx_th_c_005fout_005f54.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5744 */     _jspx_th_c_005fout_005f54.setValue("${prop.rowDisplayName}");
/* 5745 */     int _jspx_eval_c_005fout_005f54 = _jspx_th_c_005fout_005f54.doStartTag();
/* 5746 */     if (_jspx_th_c_005fout_005f54.doEndTag() == 5) {
/* 5747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 5748 */       return true;
/*      */     }
/* 5750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f54);
/* 5751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f55(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5756 */     PageContext pageContext = _jspx_page_context;
/* 5757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5759 */     OutTag _jspx_th_c_005fout_005f55 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5760 */     _jspx_th_c_005fout_005f55.setPageContext(_jspx_page_context);
/* 5761 */     _jspx_th_c_005fout_005f55.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5763 */     _jspx_th_c_005fout_005f55.setValue("${prop.textAreaID}");
/* 5764 */     int _jspx_eval_c_005fout_005f55 = _jspx_th_c_005fout_005f55.doStartTag();
/* 5765 */     if (_jspx_th_c_005fout_005f55.doEndTag() == 5) {
/* 5766 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 5767 */       return true;
/*      */     }
/* 5769 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f55);
/* 5770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f56(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5775 */     PageContext pageContext = _jspx_page_context;
/* 5776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5778 */     OutTag _jspx_th_c_005fout_005f56 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5779 */     _jspx_th_c_005fout_005f56.setPageContext(_jspx_page_context);
/* 5780 */     _jspx_th_c_005fout_005f56.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5782 */     _jspx_th_c_005fout_005f56.setValue("${prop.rowName}");
/* 5783 */     int _jspx_eval_c_005fout_005f56 = _jspx_th_c_005fout_005f56.doStartTag();
/* 5784 */     if (_jspx_th_c_005fout_005f56.doEndTag() == 5) {
/* 5785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 5786 */       return true;
/*      */     }
/* 5788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f56);
/* 5789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f57(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5794 */     PageContext pageContext = _jspx_page_context;
/* 5795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5797 */     OutTag _jspx_th_c_005fout_005f57 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5798 */     _jspx_th_c_005fout_005f57.setPageContext(_jspx_page_context);
/* 5799 */     _jspx_th_c_005fout_005f57.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 5801 */     _jspx_th_c_005fout_005f57.setValue("${prop.rowValue}");
/* 5802 */     int _jspx_eval_c_005fout_005f57 = _jspx_th_c_005fout_005f57.doStartTag();
/* 5803 */     if (_jspx_th_c_005fout_005f57.doEndTag() == 5) {
/* 5804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 5805 */       return true;
/*      */     }
/* 5807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f57);
/* 5808 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f58(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5813 */     PageContext pageContext = _jspx_page_context;
/* 5814 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5816 */     OutTag _jspx_th_c_005fout_005f58 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5817 */     _jspx_th_c_005fout_005f58.setPageContext(_jspx_page_context);
/* 5818 */     _jspx_th_c_005fout_005f58.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5820 */     _jspx_th_c_005fout_005f58.setValue("${fromDiscovery}");
/* 5821 */     int _jspx_eval_c_005fout_005f58 = _jspx_th_c_005fout_005f58.doStartTag();
/* 5822 */     if (_jspx_th_c_005fout_005f58.doEndTag() == 5) {
/* 5823 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 5824 */       return true;
/*      */     }
/* 5826 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f58);
/* 5827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5832 */     PageContext pageContext = _jspx_page_context;
/* 5833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5835 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 5836 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5837 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5839 */     _jspx_th_fmt_005fmessage_005f3.setKey("${buttonToPerform}");
/* 5840 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5841 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5842 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5843 */       return true;
/*      */     }
/* 5845 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5851 */     PageContext pageContext = _jspx_page_context;
/* 5852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5854 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5855 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 5856 */     _jspx_th_c_005fif_005f15.setParent(null);
/*      */     
/* 5858 */     _jspx_th_c_005fif_005f15.setTest("${buttonToPerform == \"Update\"}");
/* 5859 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 5860 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 5862 */         out.write("\n$('select[name=type]').attr('disabled', true); //No I18N\n");
/* 5863 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 5864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5868 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 5869 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5870 */       return true;
/*      */     }
/* 5872 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 5873 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowCredentialTypeValues_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */