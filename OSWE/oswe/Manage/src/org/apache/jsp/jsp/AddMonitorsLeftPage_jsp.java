/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class AddMonitorsLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   45 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   48 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   49 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   50 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   57 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   62 */     ArrayList list = null;
/*   63 */     StringBuffer sbf = new StringBuffer();
/*   64 */     ManagedApplication mo = new ManagedApplication();
/*   65 */     if (distinct)
/*      */     {
/*   67 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   71 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   74 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   76 */       ArrayList row = (ArrayList)list.get(i);
/*   77 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   78 */       if (distinct) {
/*   79 */         sbf.append(row.get(0));
/*      */       } else
/*   81 */         sbf.append(row.get(1));
/*   82 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   85 */     return sbf.toString(); }
/*      */   
/*   87 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   90 */     if (severity == null)
/*      */     {
/*   92 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   94 */     if (severity.equals("5"))
/*      */     {
/*   96 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   98 */     if (severity.equals("1"))
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  105 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  112 */     if (severity == null)
/*      */     {
/*  114 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  116 */     if (severity.equals("1"))
/*      */     {
/*  118 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  120 */     if (severity.equals("4"))
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("5"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  131 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  137 */     if (severity == null)
/*      */     {
/*  139 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  141 */     if (severity.equals("5"))
/*      */     {
/*  143 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  145 */     if (severity.equals("1"))
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  151 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  157 */     if (severity == null)
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  161 */     if (severity.equals("1"))
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  165 */     if (severity.equals("4"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  169 */     if (severity.equals("5"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  175 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  181 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  187 */     if (severity == 5)
/*      */     {
/*  189 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  191 */     if (severity == 1)
/*      */     {
/*  193 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  198 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  204 */     if (severity == null)
/*      */     {
/*  206 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  208 */     if (severity.equals("5"))
/*      */     {
/*  210 */       if (isAvailability) {
/*  211 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  214 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  217 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  219 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  221 */     if (severity.equals("1"))
/*      */     {
/*  223 */       if (isAvailability) {
/*  224 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  227 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  234 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  241 */     if (severity == null)
/*      */     {
/*  243 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  245 */     if (severity.equals("5"))
/*      */     {
/*  247 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  249 */     if (severity.equals("4"))
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("1"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  260 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  266 */     if (severity == null)
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  270 */     if (severity.equals("5"))
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  274 */     if (severity.equals("4"))
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("1"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  285 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  292 */     if (severity == null)
/*      */     {
/*  294 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  296 */     if (severity.equals("5"))
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  300 */     if (severity.equals("4"))
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  304 */     if (severity.equals("1"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  311 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  319 */     StringBuffer out = new StringBuffer();
/*  320 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  321 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  322 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  323 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  324 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  325 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  326 */     out.append("</tr>");
/*  327 */     out.append("</form></table>");
/*  328 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  335 */     if (val == null)
/*      */     {
/*  337 */       return "-";
/*      */     }
/*      */     
/*  340 */     String ret = FormatUtil.formatNumber(val);
/*  341 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  342 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  345 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  349 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  357 */     StringBuffer out = new StringBuffer();
/*  358 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  359 */     out.append("<tr>");
/*  360 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  362 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  364 */     out.append("</tr>");
/*  365 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  369 */       if (j % 2 == 0)
/*      */       {
/*  371 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  375 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  378 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  380 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  383 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  387 */       out.append("</tr>");
/*      */     }
/*  389 */     out.append("</table>");
/*  390 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  391 */     out.append("<tr>");
/*  392 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  393 */     out.append("</tr>");
/*  394 */     out.append("</table>");
/*  395 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  401 */     StringBuffer out = new StringBuffer();
/*  402 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  403 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  404 */     out.append("<tr>");
/*  405 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  408 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  409 */     out.append("</tr>");
/*  410 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  413 */       out.append("<tr>");
/*  414 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  415 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  416 */       out.append("</tr>");
/*      */     }
/*      */     
/*  419 */     out.append("</table>");
/*  420 */     out.append("</table>");
/*  421 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  426 */     if (severity.equals("0"))
/*      */     {
/*  428 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  432 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  439 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  452 */     StringBuffer out = new StringBuffer();
/*  453 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  454 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  456 */       out.append("<tr>");
/*  457 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  458 */       out.append("</tr>");
/*      */       
/*      */ 
/*  461 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  463 */         String borderclass = "";
/*      */         
/*      */ 
/*  466 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  468 */         out.append("<tr>");
/*      */         
/*  470 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  471 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  472 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  478 */     out.append("</table><br>");
/*  479 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  480 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  482 */       List sLinks = secondLevelOfLinks[0];
/*  483 */       List sText = secondLevelOfLinks[1];
/*  484 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  487 */         out.append("<tr>");
/*  488 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  489 */         out.append("</tr>");
/*  490 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  492 */           String borderclass = "";
/*      */           
/*      */ 
/*  495 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  497 */           out.append("<tr>");
/*      */           
/*  499 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  500 */           if (sLinks.get(i).toString().length() == 0) {
/*  501 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  504 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  506 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  510 */     out.append("</table>");
/*  511 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  518 */     StringBuffer out = new StringBuffer();
/*  519 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  520 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  522 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  524 */         out.append("<tr>");
/*  525 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  526 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  530 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  532 */           String borderclass = "";
/*      */           
/*      */ 
/*  535 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  537 */           out.append("<tr>");
/*      */           
/*  539 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  540 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  541 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  544 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  547 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  552 */     out.append("</table><br>");
/*  553 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  554 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  556 */       List sLinks = secondLevelOfLinks[0];
/*  557 */       List sText = secondLevelOfLinks[1];
/*  558 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  561 */         out.append("<tr>");
/*  562 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  563 */         out.append("</tr>");
/*  564 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  566 */           String borderclass = "";
/*      */           
/*      */ 
/*  569 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  571 */           out.append("<tr>");
/*      */           
/*  573 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  574 */           if (sLinks.get(i).toString().length() == 0) {
/*  575 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  578 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  580 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  584 */     out.append("</table>");
/*  585 */     return out.toString();
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
/*  598 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  601 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  613 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  616 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  619 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  627 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  632 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  637 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  642 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  647 */     if (val != null)
/*      */     {
/*  649 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  653 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  658 */     if (val == null) {
/*  659 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  663 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  668 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  674 */     if (val != null)
/*      */     {
/*  676 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  680 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  686 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  691 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  695 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  700 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  705 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  710 */     String hostaddress = "";
/*  711 */     String ip = request.getHeader("x-forwarded-for");
/*  712 */     if (ip == null)
/*  713 */       ip = request.getRemoteAddr();
/*  714 */     InetAddress add = null;
/*  715 */     if (ip.equals("127.0.0.1")) {
/*  716 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  720 */       add = InetAddress.getByName(ip);
/*      */     }
/*  722 */     hostaddress = add.getHostName();
/*  723 */     if (hostaddress.indexOf('.') != -1) {
/*  724 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  725 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  729 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  734 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  740 */     if (severity == null)
/*      */     {
/*  742 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  744 */     if (severity.equals("5"))
/*      */     {
/*  746 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  748 */     if (severity.equals("1"))
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  755 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  760 */     ResultSet set = null;
/*  761 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  762 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  764 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  765 */       if (set.next()) { String str1;
/*  766 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  767 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  770 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  775 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  778 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  780 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  784 */     StringBuffer rca = new StringBuffer();
/*  785 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  786 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  789 */     int rcalength = key.length();
/*  790 */     String split = "6. ";
/*  791 */     int splitPresent = key.indexOf(split);
/*  792 */     String div1 = "";String div2 = "";
/*  793 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  795 */       if (rcalength > 180) {
/*  796 */         rca.append("<span class=\"rca-critical-text\">");
/*  797 */         getRCATrimmedText(key, rca);
/*  798 */         rca.append("</span>");
/*      */       } else {
/*  800 */         rca.append("<span class=\"rca-critical-text\">");
/*  801 */         rca.append(key);
/*  802 */         rca.append("</span>");
/*      */       }
/*  804 */       return rca.toString();
/*      */     }
/*  806 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  807 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  808 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  809 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  810 */     getRCATrimmedText(div1, rca);
/*  811 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  814 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  815 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  816 */     getRCATrimmedText(div2, rca);
/*  817 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  819 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  824 */     String[] st = msg.split("<br>");
/*  825 */     for (int i = 0; i < st.length; i++) {
/*  826 */       String s = st[i];
/*  827 */       if (s.length() > 180) {
/*  828 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  830 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  834 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  835 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  837 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  841 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  842 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  843 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  846 */       if (key == null) {
/*  847 */         return ret;
/*      */       }
/*      */       
/*  850 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  851 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  854 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  855 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  856 */       set = AMConnectionPool.executeQueryStmt(query);
/*  857 */       if (set.next())
/*      */       {
/*  859 */         String helpLink = set.getString("LINK");
/*  860 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  863 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  869 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  888 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  879 */         if (set != null) {
/*  880 */           AMConnectionPool.closeStatement(set);
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
/*  894 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  895 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  897 */       String entityStr = (String)keys.nextElement();
/*  898 */       String mmessage = temp.getProperty(entityStr);
/*  899 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  900 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  902 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  908 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  909 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  911 */       String entityStr = (String)keys.nextElement();
/*  912 */       String mmessage = temp.getProperty(entityStr);
/*  913 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  914 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  916 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  921 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  931 */     String des = new String();
/*  932 */     while (str.indexOf(find) != -1) {
/*  933 */       des = des + str.substring(0, str.indexOf(find));
/*  934 */       des = des + replace;
/*  935 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  937 */     des = des + str;
/*  938 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  945 */       if (alert == null)
/*      */       {
/*  947 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  949 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  951 */         return "&nbsp;";
/*      */       }
/*      */       
/*  954 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  959 */       int rcalength = test.length();
/*  960 */       if (rcalength < 300)
/*      */       {
/*  962 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  966 */       StringBuffer out = new StringBuffer();
/*  967 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  968 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  969 */       out.append("</div>");
/*  970 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  971 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  972 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  977 */       ex.printStackTrace();
/*      */     }
/*  979 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  985 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  990 */     ArrayList attribIDs = new ArrayList();
/*  991 */     ArrayList resIDs = new ArrayList();
/*  992 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  994 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  996 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  998 */       String resourceid = "";
/*  999 */       String resourceType = "";
/* 1000 */       if (type == 2) {
/* 1001 */         resourceid = (String)row.get(0);
/* 1002 */         resourceType = (String)row.get(3);
/*      */       }
/* 1004 */       else if (type == 3) {
/* 1005 */         resourceid = (String)row.get(0);
/* 1006 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1009 */         resourceid = (String)row.get(6);
/* 1010 */         resourceType = (String)row.get(7);
/*      */       }
/* 1012 */       resIDs.add(resourceid);
/* 1013 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1014 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1016 */       String healthentity = null;
/* 1017 */       String availentity = null;
/* 1018 */       if (healthid != null) {
/* 1019 */         healthentity = resourceid + "_" + healthid;
/* 1020 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1023 */       if (availid != null) {
/* 1024 */         availentity = resourceid + "_" + availid;
/* 1025 */         entitylist.add(availentity);
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
/* 1039 */     Properties alert = getStatus(entitylist);
/* 1040 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1045 */     int size = monitorList.size();
/*      */     
/* 1047 */     String[] severity = new String[size];
/*      */     
/* 1049 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1051 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1052 */       String resourceName1 = (String)row1.get(7);
/* 1053 */       String resourceid1 = (String)row1.get(6);
/* 1054 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1055 */       if (severity[j] == null)
/*      */       {
/* 1057 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1061 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1063 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1065 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1068 */         if (sev > 0) {
/* 1069 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1070 */           monitorList.set(k, monitorList.get(j));
/* 1071 */           monitorList.set(j, t);
/* 1072 */           String temp = severity[k];
/* 1073 */           severity[k] = severity[j];
/* 1074 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1080 */     int z = 0;
/* 1081 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1084 */       int i = 0;
/* 1085 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1088 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1092 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1096 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1098 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1101 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1105 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1108 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1109 */       String resourceName1 = (String)row1.get(7);
/* 1110 */       String resourceid1 = (String)row1.get(6);
/* 1111 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1112 */       if (hseverity[j] == null)
/*      */       {
/* 1114 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1119 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1121 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1124 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1127 */         if (hsev > 0) {
/* 1128 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1129 */           monitorList.set(k, monitorList.get(j));
/* 1130 */           monitorList.set(j, t);
/* 1131 */           String temp1 = hseverity[k];
/* 1132 */           hseverity[k] = hseverity[j];
/* 1133 */           hseverity[j] = temp1;
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
/* 1145 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1146 */     boolean forInventory = false;
/* 1147 */     String trdisplay = "none";
/* 1148 */     String plusstyle = "inline";
/* 1149 */     String minusstyle = "none";
/* 1150 */     String haidTopLevel = "";
/* 1151 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1153 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1155 */         haidTopLevel = request.getParameter("haid");
/* 1156 */         forInventory = true;
/* 1157 */         trdisplay = "table-row;";
/* 1158 */         plusstyle = "none";
/* 1159 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1166 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1169 */     ArrayList listtoreturn = new ArrayList();
/* 1170 */     StringBuffer toreturn = new StringBuffer();
/* 1171 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1172 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1173 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1175 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1177 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1178 */       String childresid = (String)singlerow.get(0);
/* 1179 */       String childresname = (String)singlerow.get(1);
/* 1180 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1181 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1182 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1183 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1184 */       String unmanagestatus = (String)singlerow.get(5);
/* 1185 */       String actionstatus = (String)singlerow.get(6);
/* 1186 */       String linkclass = "monitorgp-links";
/* 1187 */       String titleforres = childresname;
/* 1188 */       String titilechildresname = childresname;
/* 1189 */       String childimg = "/images/trcont.png";
/* 1190 */       String flag = "enable";
/* 1191 */       String dcstarted = (String)singlerow.get(8);
/* 1192 */       String configMonitor = "";
/* 1193 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1194 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1196 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1198 */       if (singlerow.get(7) != null)
/*      */       {
/* 1200 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1202 */       String haiGroupType = "0";
/* 1203 */       if ("HAI".equals(childtype))
/*      */       {
/* 1205 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1207 */       childimg = "/images/trend.png";
/* 1208 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1209 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1210 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1212 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1214 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1216 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1217 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1220 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1222 */         linkclass = "disabledtext";
/* 1223 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1225 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1226 */       String availmouseover = "";
/* 1227 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1229 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1231 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1232 */       String healthmouseover = "";
/* 1233 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1235 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1238 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1239 */       int spacing = 0;
/* 1240 */       if (level >= 1)
/*      */       {
/* 1242 */         spacing = 40 * level;
/*      */       }
/* 1244 */       if (childtype.equals("HAI"))
/*      */       {
/* 1246 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1247 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1248 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1250 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1251 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1252 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1253 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1254 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1255 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1256 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1257 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1258 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1259 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1260 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1262 */         if (!forInventory)
/*      */         {
/* 1264 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1267 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1269 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1271 */           actions = editlink + actions;
/*      */         }
/* 1273 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1275 */           actions = actions + associatelink;
/*      */         }
/* 1277 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1278 */         String arrowimg = "";
/* 1279 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1281 */           actions = "";
/* 1282 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1283 */           checkbox = "";
/* 1284 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1286 */         if (isIt360)
/*      */         {
/* 1288 */           actionimg = "";
/* 1289 */           actions = "";
/* 1290 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1291 */           checkbox = "";
/*      */         }
/*      */         
/* 1294 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1296 */           actions = "";
/*      */         }
/* 1298 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         String resourcelink = "";
/*      */         
/* 1305 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1307 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1311 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1314 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1320 */         if (!isIt360)
/*      */         {
/* 1322 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1329 */         toreturn.append("</tr>");
/* 1330 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1332 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1333 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1337 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1338 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1341 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1345 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1347 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1348 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1349 */             toreturn.append(assocMessage);
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1359 */         String resourcelink = null;
/* 1360 */         boolean hideEditLink = false;
/* 1361 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1363 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1364 */           hideEditLink = true;
/* 1365 */           if (isIt360)
/*      */           {
/* 1367 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1371 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1373 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1375 */           hideEditLink = true;
/* 1376 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1377 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1382 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1385 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1386 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1387 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1388 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1389 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1390 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1391 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1392 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1393 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1394 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1395 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1396 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1397 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1399 */         if (hideEditLink)
/*      */         {
/* 1401 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1403 */         if (!forInventory)
/*      */         {
/* 1405 */           removefromgroup = "";
/*      */         }
/* 1407 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1408 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1409 */           actions = actions + configcustomfields;
/*      */         }
/* 1411 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1413 */           actions = editlink + actions;
/*      */         }
/* 1415 */         String managedLink = "";
/* 1416 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1418 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1419 */           actions = "";
/* 1420 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1421 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1424 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1426 */           checkbox = "";
/*      */         }
/*      */         
/* 1429 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1431 */           actions = "";
/*      */         }
/* 1433 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1434 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1435 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1436 */         if (isIt360)
/*      */         {
/* 1438 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1442 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1446 */         if (!isIt360)
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1454 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1457 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1464 */       StringBuilder toreturn = new StringBuilder();
/* 1465 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1466 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1467 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1468 */       String title = "";
/* 1469 */       message = EnterpriseUtil.decodeString(message);
/* 1470 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1471 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1472 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1474 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1476 */       else if ("5".equals(severity))
/*      */       {
/* 1478 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1482 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1484 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1485 */       toreturn.append(v);
/*      */       
/* 1487 */       toreturn.append(link);
/* 1488 */       if (severity == null)
/*      */       {
/* 1490 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1492 */       else if (severity.equals("5"))
/*      */       {
/* 1494 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1496 */       else if (severity.equals("4"))
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("1"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       toreturn.append("</a>");
/* 1510 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1514 */       ex.printStackTrace();
/*      */     }
/* 1516 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1523 */       StringBuilder toreturn = new StringBuilder();
/* 1524 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1525 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1526 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1527 */       if (message == null)
/*      */       {
/* 1529 */         message = "";
/*      */       }
/*      */       
/* 1532 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1533 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1535 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1536 */       toreturn.append(v);
/*      */       
/* 1538 */       toreturn.append(link);
/*      */       
/* 1540 */       if (severity == null)
/*      */       {
/* 1542 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1544 */       else if (severity.equals("5"))
/*      */       {
/* 1546 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1548 */       else if (severity.equals("1"))
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       toreturn.append("</a>");
/* 1558 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1564 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1567 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1568 */     if (invokeActions != null) {
/* 1569 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1570 */       while (iterator.hasNext()) {
/* 1571 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1572 */         if (actionmap.containsKey(actionid)) {
/* 1573 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1578 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1582 */     String actionLink = "";
/* 1583 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1584 */     String query = "";
/* 1585 */     ResultSet rs = null;
/* 1586 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1587 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1588 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1589 */       actionLink = "method=" + methodName;
/*      */     }
/* 1591 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1592 */       actionLink = methodName;
/*      */     }
/* 1594 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1595 */     Iterator itr = methodarglist.iterator();
/* 1596 */     boolean isfirstparam = true;
/* 1597 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1598 */     while (itr.hasNext()) {
/* 1599 */       HashMap argmap = (HashMap)itr.next();
/* 1600 */       String argtype = (String)argmap.get("TYPE");
/* 1601 */       String argname = (String)argmap.get("IDENTITY");
/* 1602 */       String paramname = (String)argmap.get("PARAMETER");
/* 1603 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1604 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1605 */         isfirstparam = false;
/* 1606 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1608 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1612 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1616 */         actionLink = actionLink + "&";
/*      */       }
/* 1618 */       String paramValue = null;
/* 1619 */       String tempargname = argname;
/* 1620 */       if (commonValues.getProperty(tempargname) != null) {
/* 1621 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1624 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1625 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1626 */           if (dbType.equals("mysql")) {
/* 1627 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1630 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1632 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1634 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1635 */             if (rs.next()) {
/* 1636 */               paramValue = rs.getString("VALUE");
/* 1637 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1641 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1645 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1648 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1653 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1654 */           paramValue = rowId;
/*      */         }
/* 1656 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1657 */           paramValue = managedObjectName;
/*      */         }
/* 1659 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1660 */           paramValue = resID;
/*      */         }
/* 1662 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1663 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1666 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1668 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1669 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1670 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1672 */     return actionLink;
/*      */   }
/*      */   
/* 1675 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1676 */     String dependentAttribute = null;
/* 1677 */     String align = "left";
/*      */     
/* 1679 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1680 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1681 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1682 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1683 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1684 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1685 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1686 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1687 */       align = "center";
/*      */     }
/*      */     
/* 1690 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1691 */     String actualdata = "";
/*      */     
/* 1693 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1694 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1695 */         actualdata = availValue;
/*      */       }
/* 1697 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1698 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1702 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1703 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1706 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1712 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1713 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1714 */       toreturn.append("<table>");
/* 1715 */       toreturn.append("<tr>");
/* 1716 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1717 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1718 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1719 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1720 */         String toolTip = "";
/* 1721 */         String hideClass = "";
/* 1722 */         String textStyle = "";
/* 1723 */         boolean isreferenced = true;
/* 1724 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1725 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1726 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1727 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1729 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1730 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1731 */           while (valueList.hasMoreTokens()) {
/* 1732 */             String dependentVal = valueList.nextToken();
/* 1733 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1734 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1735 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1737 */               toolTip = "";
/* 1738 */               hideClass = "";
/* 1739 */               isreferenced = false;
/* 1740 */               textStyle = "disabledtext";
/* 1741 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1745 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1746 */           toolTip = "";
/* 1747 */           hideClass = "";
/* 1748 */           isreferenced = false;
/* 1749 */           textStyle = "disabledtext";
/* 1750 */           if (dependentImageMap != null) {
/* 1751 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1752 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1755 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1759 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1760 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1761 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1762 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1763 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1764 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1766 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1767 */           if (isreferenced) {
/* 1768 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1772 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1773 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1774 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1775 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1776 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1777 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1779 */           toreturn.append("</span>");
/* 1780 */           toreturn.append("</a>");
/* 1781 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1784 */       toreturn.append("</tr>");
/* 1785 */       toreturn.append("</table>");
/* 1786 */       toreturn.append("</td>");
/*      */     } else {
/* 1788 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1791 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1795 */     String colTime = null;
/* 1796 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1797 */     if ((rows != null) && (rows.size() > 0)) {
/* 1798 */       Iterator<String> itr = rows.iterator();
/* 1799 */       String maxColQuery = "";
/* 1800 */       for (;;) { if (itr.hasNext()) {
/* 1801 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1802 */           ResultSet maxCol = null;
/*      */           try {
/* 1804 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1805 */             while (maxCol.next()) {
/* 1806 */               if (colTime == null) {
/* 1807 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1810 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1819 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1821 */               if (maxCol != null)
/* 1822 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1824 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1819 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1821 */               if (maxCol != null)
/* 1822 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1824 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1829 */     return colTime;
/*      */   }
/*      */   
/* 1832 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1833 */     tablename = null;
/* 1834 */     ResultSet rsTable = null;
/* 1835 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1837 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1838 */       while (rsTable.next()) {
/* 1839 */         tablename = rsTable.getString("DATATABLE");
/* 1840 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1841 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1854 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1845 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1848 */         if (rsTable != null)
/* 1849 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1851 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1857 */     String argsList = "";
/* 1858 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1860 */       if (showArgsMap.get(row) != null) {
/* 1861 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1862 */         if (showArgslist != null) {
/* 1863 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1864 */             if (argsList.trim().equals("")) {
/* 1865 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1868 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1875 */       e.printStackTrace();
/* 1876 */       return "";
/*      */     }
/* 1878 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1883 */     String argsList = "";
/* 1884 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1887 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1889 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1890 */         if (hideArgsList != null)
/*      */         {
/* 1892 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1894 */             if (argsList.trim().equals(""))
/*      */             {
/* 1896 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1900 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1908 */       ex.printStackTrace();
/*      */     }
/* 1910 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1914 */     StringBuilder toreturn = new StringBuilder();
/* 1915 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1922 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1923 */       Iterator itr = tActionList.iterator();
/* 1924 */       while (itr.hasNext()) {
/* 1925 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1926 */         String confirmmsg = "";
/* 1927 */         String link = "";
/* 1928 */         String isJSP = "NO";
/* 1929 */         HashMap tactionMap = (HashMap)itr.next();
/* 1930 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1931 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1932 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1933 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1934 */           (actionmap.containsKey(actionId))) {
/* 1935 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1936 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1937 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1938 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1939 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1941 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1947 */           if (isTableAction) {
/* 1948 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1951 */             tableName = "Link";
/* 1952 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1953 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1954 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1955 */             toreturn.append("</a></td>");
/*      */           }
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1966 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1972 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1974 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1975 */       Properties prop = (Properties)node.getUserObject();
/* 1976 */       String mgID = prop.getProperty("label");
/* 1977 */       String mgName = prop.getProperty("value");
/* 1978 */       String isParent = prop.getProperty("isParent");
/* 1979 */       int mgIDint = Integer.parseInt(mgID);
/* 1980 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1982 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1984 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1985 */       if (node.getChildCount() > 0)
/*      */       {
/* 1987 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1989 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1991 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1993 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1997 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2002 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2004 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2006 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2008 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2012 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2015 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2016 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2018 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2022 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2024 */       if (node.getChildCount() > 0)
/*      */       {
/* 2026 */         builder.append("<UL>");
/* 2027 */         printMGTree(node, builder);
/* 2028 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2033 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2034 */     StringBuffer toReturn = new StringBuffer();
/* 2035 */     String table = "-";
/*      */     try {
/* 2037 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2038 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2039 */       float total = 0.0F;
/* 2040 */       while (it.hasNext()) {
/* 2041 */         String attName = (String)it.next();
/* 2042 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2043 */         boolean roundOffData = false;
/* 2044 */         if ((data != null) && (!data.equals(""))) {
/* 2045 */           if (data.indexOf(",") != -1) {
/* 2046 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2049 */             float value = Float.parseFloat(data);
/* 2050 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2053 */             total += value;
/* 2054 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2057 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2062 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2063 */       while (attVsWidthList.hasNext()) {
/* 2064 */         String attName = (String)attVsWidthList.next();
/* 2065 */         String data = (String)attVsWidthProps.get(attName);
/* 2066 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2067 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2068 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2069 */         String className = (String)graphDetails.get("ClassName");
/* 2070 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2071 */         if (percentage < 1.0F)
/*      */         {
/* 2073 */           data = percentage + "";
/*      */         }
/* 2075 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2077 */       if (toReturn.length() > 0) {
/* 2078 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2082 */       e.printStackTrace();
/*      */     }
/* 2084 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2090 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2091 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2092 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2093 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2094 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2095 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2096 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2097 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2098 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2101 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2102 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2103 */       splitvalues[0] = multiplecondition.toString();
/* 2104 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2107 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2112 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2113 */     if (thresholdType != 3) {
/* 2114 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2115 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2116 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2117 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2118 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2119 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2121 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2122 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2123 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2124 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2125 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2126 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2128 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2129 */     if (updateSelected != null) {
/* 2130 */       updateSelected[0] = "selected";
/*      */     }
/* 2132 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2137 */       StringBuffer toreturn = new StringBuffer("");
/* 2138 */       if (commaSeparatedMsgId != null) {
/* 2139 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2140 */         int count = 0;
/* 2141 */         while (msgids.hasMoreTokens()) {
/* 2142 */           String id = msgids.nextToken();
/* 2143 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2144 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2145 */           count++;
/* 2146 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2147 */             if (toreturn.length() == 0) {
/* 2148 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2150 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2151 */             if (!image.trim().equals("")) {
/* 2152 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2154 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2155 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2158 */         if (toreturn.length() > 0) {
/* 2159 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2163 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2166 */       e.printStackTrace(); }
/* 2167 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2173 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2179 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2180 */   static { _jspx_dependants.put("/jsp/includes/ApplicationLinks.jspf", Long.valueOf(1473429417000L));
/* 2181 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2197 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2201 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2203 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2204 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2205 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2210 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2217 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2219 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2220 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2228 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2231 */     JspWriter out = null;
/* 2232 */     Object page = this;
/* 2233 */     JspWriter _jspx_out = null;
/* 2234 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2238 */       response.setContentType("text/html;charset=UTF-8");
/* 2239 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2241 */       _jspx_page_context = pageContext;
/* 2242 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2243 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2244 */       session = pageContext.getSession();
/* 2245 */       out = pageContext.getOut();
/* 2246 */       _jspx_out = out;
/*      */       
/* 2248 */       out.write("<!--$Id$-->\n\n\n\n\n\n");
/* 2249 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2251 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2252 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2253 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2255 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2257 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2259 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2261 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2262 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2263 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2264 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2267 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2268 */         String available = null;
/* 2269 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2270 */         out.write(10);
/*      */         
/* 2272 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2273 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2274 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2276 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2278 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2280 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2282 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2283 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2284 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2285 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2288 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2289 */           String unavailable = null;
/* 2290 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2291 */           out.write(10);
/*      */           
/* 2293 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2294 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2295 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2297 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2299 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2301 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2303 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2304 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2305 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2306 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2309 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2310 */             String unmanaged = null;
/* 2311 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2312 */             out.write(10);
/*      */             
/* 2314 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2315 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2316 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2318 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2320 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2322 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2324 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2325 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2326 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2327 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2330 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2331 */               String scheduled = null;
/* 2332 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2333 */               out.write(10);
/*      */               
/* 2335 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2336 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2337 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2339 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2341 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2343 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2345 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2346 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2347 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2348 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2351 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2352 */                 String critical = null;
/* 2353 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2354 */                 out.write(10);
/*      */                 
/* 2356 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2357 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2358 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2360 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2362 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2364 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2366 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2367 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2368 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2369 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2372 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2373 */                   String clear = null;
/* 2374 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2375 */                   out.write(10);
/*      */                   
/* 2377 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2378 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2379 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2381 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2383 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2385 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2387 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2388 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2389 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2390 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2393 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2394 */                     String warning = null;
/* 2395 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2396 */                     out.write(10);
/* 2397 */                     out.write(10);
/*      */                     
/* 2399 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2400 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2402 */                     out.write(10);
/* 2403 */                     out.write(10);
/* 2404 */                     out.write(10);
/* 2405 */                     out.write(10);
/*      */                     
/*      */ 
/* 2408 */                     String resourceName = request.getParameter("type");
/* 2409 */                     String Name = "UrlMonitor";
/*      */                     String Msg;
/*      */                     String Msg;
/* 2412 */                     if (resourceName.equals(Name))
/*      */                     {
/* 2414 */                       Msg = FormatUtil.getString("am.webclient.addmonitors.url.message");
/*      */                     } else { String Msg;
/* 2416 */                       if (resourceName.equalsIgnoreCase("Script Monitor"))
/*      */                       {
/* 2418 */                         Msg = FormatUtil.getString("am.webclient.addmonitors.script.message");
/*      */                       }
/*      */                       else
/*      */                       {
/* 2422 */                         Msg = FormatUtil.getString("am.webclient.addmonitors.normal.message.text");
/*      */                       }
/*      */                     }
/* 2425 */                     String appname = request.getParameter("name");
/* 2426 */                     String haid = request.getParameter("haid");
/*      */                     
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/* 2430 */                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                     
/* 2432 */                     int subGroupLevels1 = com.adventnet.appmanager.util.Constants.getSubGroupLevels();
/*      */                     
/* 2434 */                     out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */                     
/* 2436 */                     String requestpathnew = "/images/mm_menu3.jsp";
/* 2437 */                     String category_2 = String.valueOf(com.adventnet.appmanager.util.Constants.isMinimizedversion());
/* 2438 */                     pageContext.setAttribute("category_2", category_2);
/*      */                     
/* 2440 */                     out.write(10);
/* 2441 */                     org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpathnew, out, false);
/* 2442 */                     out.write("\n</script>\n  <SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n      ");
/*      */                     
/* 2444 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2445 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2446 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2448 */                     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid }");
/* 2449 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2450 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2452 */                         out.write(" \n      \t  ");
/* 2453 */                         if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2455 */                         out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr> \n          <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2456 */                         out.print(FormatUtil.getString("am.webclient.applicationlinks.monitorgrouplinks.text"));
/* 2457 */                         out.write("</td>\n        </tr>\n\t\t        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" > ");
/* 2458 */                         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2460 */                         out.write(32);
/* 2461 */                         if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2463 */                         out.write(" </td>\n        </tr>\n\n\t");
/*      */                         
/* 2465 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2466 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2467 */                         _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2469 */                         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2470 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2471 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2473 */                             out.write(" \t \n\t<tr> \t \n\t");
/*      */                             
/* 2475 */                             Vector ParentMos1 = (Vector)request.getAttribute("ParentMos");
/* 2476 */                             if ((ParentMos1 != null) && (ParentMos1.size() < subGroupLevels1)) {
/* 2477 */                               out.write(" \t \n\t\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t\t<a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2478 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2479 */                               out.write("</a> \t \n\t\t\t</td> \t \n\t\t\t");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2483 */                               out.write(" \t \n\t<td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \t \n\t\t<a href=\"#\"   class=\"disabledtext\">");
/* 2484 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2485 */                               out.write("</a> \t \n\t\t</td> \t \n\t\t");
/*      */                             }
/* 2487 */                             out.write(" \t \n\t\t</tr> \t \n\t\t");
/* 2488 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2489 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2493 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2494 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                         }
/*      */                         
/* 2497 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2498 */                         out.write("\n\n       ");
/*      */                         
/* 2500 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2501 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2502 */                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2504 */                         _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2505 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2506 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 2508 */                             out.write("\n\t   <tr>\n\t    ");
/*      */                             
/* 2510 */                             Vector ParentMos2 = (Vector)request.getAttribute("ParentMos");
/* 2511 */                             int mgResourceId = Integer.parseInt(request.getParameter("haid"));
/* 2512 */                             if ((ParentMos2 != null) && (ParentMos2.size() < subGroupLevels1) && (mgResourceId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) {
/* 2513 */                               out.write("\n\t\t  <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t    <a href=\"#\"  onClick=\"toggleDiv('subgroup');\" class=\"new-left-links\">");
/* 2514 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2515 */                               out.write("</a>\n\t\t  </td>\n\t\t  ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2519 */                               out.write("\n\t\t   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" >\n\t\t     <a href=\"#\"   class=\"disabledtext\">");
/* 2520 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.new.subgroup.text"));
/* 2521 */                               out.write("</a>\n\t\t   </td>\n\t\t   ");
/*      */                             }
/* 2523 */                             out.write("\n\t   </tr>\n\t   ");
/* 2524 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2525 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2529 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2530 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                         }
/*      */                         
/* 2533 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2534 */                         out.write("\n\n        <tr> \n\t\t               ");
/*      */                         
/* 2536 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2537 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2538 */                         _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2540 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 2541 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2542 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2544 */                             out.write("\n\t\t                 <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > ");
/*      */                             
/* 2546 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2547 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2548 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                             
/* 2550 */                             _jspx_th_c_005fif_005f4.setTest("${param.method!='getHAProfiles'}");
/* 2551 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2552 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/* 2554 */                                 out.write(" \n\t\t                   <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2555 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                   return;
/* 2557 */                                 out.write("\" \n\t\t                   class=\"new-left-links\">");
/* 2558 */                                 out.print(ALERTCONFIG_TEXT);
/* 2559 */                                 out.write("</a>");
/* 2560 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2561 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2565 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2566 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/* 2569 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2570 */                             out.write(32);
/*      */                             
/* 2572 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2573 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2574 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                             
/* 2576 */                             _jspx_th_c_005fif_005f5.setTest("${param.method=='getHAProfiles'}");
/* 2577 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2578 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2580 */                                 out.write(" \n\t\t                   ");
/* 2581 */                                 out.print(ALERTCONFIG_TEXT);
/* 2582 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2583 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2587 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2588 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2591 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2592 */                             out.write("</td>\n\t\t                   ");
/* 2593 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2594 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2598 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2599 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                         }
/*      */                         
/* 2602 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2603 */                         out.write("\t\n\t\t       \n\t\t                   ");
/*      */                         
/* 2605 */                         PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2606 */                         _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2607 */                         _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2609 */                         _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/* 2610 */                         int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2611 */                         if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                           for (;;) {
/* 2613 */                             out.write("\n\t\t                   <td width=\"98%\" height=\"21\"  colspan=\"2\" class=\"leftlinkstd\" > \n\t\t                   ");
/*      */                             
/* 2615 */                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2616 */                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2617 */                             _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                             
/* 2619 */                             _jspx_th_am_005fadminlink_005f0.setHref(ALERTCONFIG_TEXT);
/*      */                             
/* 2621 */                             _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2622 */                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2623 */                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2624 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2625 */                                 out = _jspx_page_context.pushBody();
/* 2626 */                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2627 */                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2630 */                                 out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 2631 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2632 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2635 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2636 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2639 */                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2640 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                             }
/*      */                             
/* 2643 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2644 */                             out.write("</td>\n\t\t                ");
/* 2645 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2646 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2650 */                         if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2651 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                         }
/*      */                         
/* 2654 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2655 */                         out.write("\t\t                   \n       </tr>\n        \n\t\t");
/*      */                         
/* 2657 */                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2658 */                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2659 */                         _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2661 */                         _jspx_th_c_005fif_005f6.setTest("${category_2!='true'}");
/* 2662 */                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2663 */                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                           for (;;) {
/* 2665 */                             out.write("\n          <tr>   ");
/*      */                             
/* 2667 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2668 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2669 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f6);
/*      */                             
/* 2671 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2672 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2673 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 2675 */                                 out.write("\n          ");
/* 2676 */                                 if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2677 */                                   out.write("\n          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"  > \n          ");
/*      */                                 } else {
/* 2679 */                                   out.write("\n\t          <td width=\"98%\" height=\"21\"  class=\"leftlinkstd\"> \n\t          ");
/*      */                                 }
/* 2681 */                                 out.write("\n\t          ");
/*      */                                 
/* 2683 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2684 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2685 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                 
/* 2687 */                                 _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN}");
/* 2688 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2689 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 2691 */                                     out.write("\n\t          ");
/*      */                                     
/* 2693 */                                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2694 */                                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2695 */                                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 2697 */                                     _jspx_th_c_005fif_005f8.setTest("${param.method!='getMonitorForm' }");
/* 2698 */                                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2699 */                                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                       for (;;) {
/* 2701 */                                         out.write(" \n\t            <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 2702 */                                         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                           return;
/* 2704 */                                         out.write("\" \n\t            class=\"new-left-links\" >");
/* 2705 */                                         out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2706 */                                         out.write(32);
/* 2707 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2708 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2712 */                                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2713 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                     }
/*      */                                     
/* 2716 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2717 */                                     out.write(" \n\t            ");
/*      */                                     
/* 2719 */                                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2720 */                                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2721 */                                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                                     
/* 2723 */                                     _jspx_th_c_005fif_005f9.setTest("${param.method=='getMonitorForm' || (empty ADMIN)}");
/* 2724 */                                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2725 */                                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                       for (;;) {
/* 2727 */                                         out.write(" \n\t            ");
/* 2728 */                                         out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2729 */                                         out.write(32);
/* 2730 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2731 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2735 */                                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2736 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                     }
/*      */                                     
/* 2739 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2740 */                                     out.write("\n\t            ");
/* 2741 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2742 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2746 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2747 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 2750 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2751 */                                 out.write("\n\t            ");
/*      */                                 
/* 2753 */                                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2754 */                                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2755 */                                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                 
/* 2757 */                                 _jspx_th_c_005fif_005f10.setTest("${empty ADMIN}");
/* 2758 */                                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2759 */                                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                   for (;;) {
/* 2761 */                                     out.write("\n\t            ");
/*      */                                     
/* 2763 */                                     AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2764 */                                     _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2765 */                                     _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fif_005f10);
/*      */                                     
/* 2767 */                                     _jspx_th_am_005fadminlink_005f1.setHref("/jsp/CreateApplication.jsp");
/*      */                                     
/* 2769 */                                     _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2770 */                                     int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2771 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2772 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2773 */                                         out = _jspx_page_context.pushBody();
/* 2774 */                                         _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2775 */                                         _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2778 */                                         out.write("\n\t            \t");
/* 2779 */                                         out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 2780 */                                         out.write("\n\t            ");
/* 2781 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2782 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2785 */                                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2786 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2789 */                                     if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2790 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                     }
/*      */                                     
/* 2793 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2794 */                                     out.write("  \t\n\t            ");
/* 2795 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2796 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2800 */                                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2801 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                 }
/*      */                                 
/* 2804 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2805 */                                 out.write("   \n\t            </td>\n\t             ");
/* 2806 */                                 if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2807 */                                   out.write("\n\t             <td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n          \n          ");
/*      */                                 } else {
/* 2809 */                                   out.write("\n\t\t\t\t<td class=\"leftlinkstd\"  > \n\t\t\t\t<A class=\"new-left-links\" href=\"#\" ></a>   \n\t\t\t\t</td>\n\t\t\t\t  ");
/*      */                                 }
/* 2811 */                                 out.write("\n          ");
/* 2812 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2813 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2817 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2818 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                             }
/*      */                             
/* 2821 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2822 */                             out.write("\n        </tr>\n        ");
/* 2823 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2824 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2828 */                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2829 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                         }
/*      */                         
/* 2832 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2833 */                         out.write("\n\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"/fault/AMAlarmView.do?displayName=Alerts&haid=");
/* 2834 */                         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2836 */                         out.write("\" \n            class=\"new-left-links\">");
/* 2837 */                         out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 2838 */                         out.write(" </td>\n        </tr>\n        <tr> \n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"javascript:fnOpenNewWindow('/showReports.do?period=0&actionMethod=generateHAAvailabilityReport&haid=");
/* 2839 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2841 */                         out.write("')\" \n            class=\"new-left-links\"> ");
/* 2842 */                         out.print(FormatUtil.getString("am.webclient.applicationlinks.applicationreport.text"));
/* 2843 */                         out.write("</td>\n        </tr>\n        \n        <tr> \n        ");
/*      */                         
/* 2845 */                         String editlink = "/showapplication.do?method=editApplication&haid=" + request.getParameter("haid");
/*      */                         
/* 2847 */                         out.write(10);
/* 2848 */                         out.write(9);
/* 2849 */                         out.write(9);
/*      */                         
/* 2851 */                         PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2852 */                         _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2853 */                         _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2855 */                         _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 2856 */                         int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2857 */                         if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                           for (;;) {
/* 2859 */                             out.write("\n\t\t\t<td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" ><a href=\"");
/* 2860 */                             out.print(editlink);
/* 2861 */                             out.write("\"\n\t\t\tClass=\"new-left-links\">");
/* 2862 */                             out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 2863 */                             out.write("</a></td>\n\t\t");
/* 2864 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2865 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2869 */                         if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2870 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                         }
/*      */                         
/* 2873 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2874 */                         out.write("\t\n\t\t");
/*      */                         
/* 2876 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2877 */                         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2878 */                         _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                         
/* 2880 */                         _jspx_th_logic_005fnotPresent_005f0.setRole("ENTERPRISEADMIN");
/* 2881 */                         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2882 */                         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2884 */                             out.write("\n          <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >");
/*      */                             
/* 2886 */                             AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2887 */                             _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2888 */                             _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                             
/* 2890 */                             _jspx_th_am_005fadminlink_005f2.setHref(editlink);
/*      */                             
/* 2892 */                             _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2893 */                             int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2894 */                             if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2895 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2896 */                                 out = _jspx_page_context.pushBody();
/* 2897 */                                 _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2898 */                                 _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2901 */                                 out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 2902 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2903 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2906 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2907 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2910 */                             if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 2911 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                             }
/*      */                             
/* 2914 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 2915 */                             out.write("</td>");
/* 2916 */                             out.write(" \n\t\t");
/* 2917 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2918 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2922 */                         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2923 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                         }
/*      */                         
/* 2926 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2927 */                         out.write("\t\n        </tr>\n        \n        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t");
/*      */                         
/* 2929 */                         int mgResId = Integer.parseInt(request.getParameter("haid"));
/* 2930 */                         if ((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                         {
/*      */ 
/* 2933 */                           out.write("\n\t\t\t\t<a href=\"javascript:applndelete()\" class=\"new-left-links\">");
/* 2934 */                           out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2935 */                           out.write("</a>\n\t\t\t");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/* 2940 */                           out.write(" \n\t\t");
/*      */                           
/* 2942 */                           AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2943 */                           _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 2944 */                           _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                           
/* 2946 */                           _jspx_th_am_005fadminlink_005f3.setHref("javascript:applndelete()");
/*      */                           
/* 2948 */                           _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 2949 */                           int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 2950 */                           if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 2951 */                             if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 2952 */                               out = _jspx_page_context.pushBody();
/* 2953 */                               _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 2954 */                               _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2957 */                               out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2958 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 2959 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2962 */                             if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 2963 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2966 */                           if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 2967 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                           }
/*      */                           
/* 2970 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 2971 */                           out.write(10);
/* 2972 */                           out.write(9);
/* 2973 */                           out.write(9);
/*      */                         }
/* 2975 */                         out.write("\n\t\t\t</td>\n        </tr>\n        \n\t\t\t");
/*      */                         
/*      */ 
/* 2978 */                         if (((EnterpriseUtil.isAdminServer()) && (mgResId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE)) || (!EnterpriseUtil.isAdminServer()))
/*      */                         {
/*      */ 
/* 2981 */                           out.write("\n\t        <tr> \n            <td width=\"98%\" height=\"21\" colspan=\"2\" class=\"leftlinkstd\" >\n\t\t\t\t<a href=\"javascript:refreshstatus()\" class=\"new-left-links\">");
/* 2982 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.refresh.status.text"));
/* 2983 */                           out.write("</a>&nbsp;<img  src=\"../images/icon_refresh.gif\" align=\"absmiddle\" border=\"0\"/>\n\t\t\t</td>\n\t\t\t </tr>\n\t\t\t");
/*      */                         }
/* 2985 */                         out.write("\n\t\t\n\t\t\t\n       \n\n        \n\n            </table>\n     ");
/* 2986 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2987 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2991 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2992 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2995 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2996 */                       out.write("\n     \n<script>\n\nfunction applndelete()\n{\n");
/*      */                       
/* 2998 */                       IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2999 */                       _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3000 */                       _jspx_th_c_005fif_005f11.setParent(null);
/*      */                       
/* 3002 */                       _jspx_th_c_005fif_005f11.setTest("${MGtype=='1' }");
/* 3003 */                       int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3004 */                       if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                         for (;;) {
/* 3006 */                           out.write(" \n\tif(confirm('");
/* 3007 */                           out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletesub-group.text"));
/* 3008 */                           out.write("')) {\n\tlocation.href = \"/manageApplications.do?method=delete&select=");
/* 3009 */                           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                             return;
/* 3011 */                           out.write("\";\n\t}\n");
/* 3012 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3013 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3017 */                       if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3018 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*      */                       }
/*      */                       else {
/* 3021 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3022 */                         out.write("\n        \n");
/*      */                         
/* 3024 */                         IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3025 */                         _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3026 */                         _jspx_th_c_005fif_005f12.setParent(null);
/*      */                         
/* 3028 */                         _jspx_th_c_005fif_005f12.setTest("${MGtype!='1' }");
/* 3029 */                         int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3030 */                         if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                           for (;;) {
/* 3032 */                             out.write("         \n\tif(confirm('");
/* 3033 */                             out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertfordeletemg.text"));
/* 3034 */                             out.write("'))\n\t{\n\t location.href = \"/manageApplications.do?method=delete&select=");
/* 3035 */                             if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                               return;
/* 3037 */                             out.write("\";\n\t}\n");
/* 3038 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3039 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3043 */                         if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3044 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*      */                         }
/*      */                         else {
/* 3047 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3048 */                           out.write("\n} \nfunction refreshstatus()\n{\t\n\t location.href = \"/manageApplications.do?method=refreshNow&haid=");
/* 3049 */                           if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */                             return;
/* 3051 */                           out.write("\";\n}\n</script>\n");
/* 3052 */                           out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3053 */                           out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3054 */                           out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 3055 */                           if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                             return;
/* 3057 */                           out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr> \n    <!--<td class=\"quicknote\">Mouse over on the Monitor Type and select the type of server. <br> Choose the Monitor from the active list to be added to the Monitor Group. <br> If the Monitor is in the Inactive Monitors table, Initiate monitoring for the particular server by clicking on the resource name link.</td>-->\n\t<td class=\"quicknote\" colspan=\"2\">");
/* 3058 */                           out.print(Msg);
/* 3059 */                           out.write("</td>\n\t</tr>\n</table>\n");
/*      */                         }
/* 3061 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3062 */         out = _jspx_out;
/* 3063 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3064 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3065 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3068 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3074 */     PageContext pageContext = _jspx_page_context;
/* 3075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3077 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3078 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3079 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3081 */     _jspx_th_c_005fif_005f1.setTest("${param.method!='showApplication' && param.method!='showSnapshot' && param.method!='getHAProfiles' && param.method!='associateMonitors' && param.method!='getMonitorForm'}");
/* 3082 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3083 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3085 */         out.write(" \n\t  \t<br>\n\t  ");
/* 3086 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3087 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3091 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3092 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3093 */       return true;
/*      */     }
/* 3095 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3101 */     PageContext pageContext = _jspx_page_context;
/* 3102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3104 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3105 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3106 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3108 */     _jspx_th_c_005fif_005f2.setTest("${param.method!='showApplication'}");
/* 3109 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3110 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3112 */         out.write("  \n           <a href=\"/showapplication.do?haid=");
/* 3113 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3114 */           return true;
/* 3115 */         out.write("&method=showApplication\" \n            class=\"new-left-links\"> ");
/* 3116 */         if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3117 */           return true;
/* 3118 */         out.write("</a>");
/* 3119 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3120 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3124 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3125 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3126 */       return true;
/*      */     }
/* 3128 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3134 */     PageContext pageContext = _jspx_page_context;
/* 3135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3137 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3138 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3139 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3141 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3142 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3143 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3144 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3145 */       return true;
/*      */     }
/* 3147 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3153 */     PageContext pageContext = _jspx_page_context;
/* 3154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3156 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3157 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3158 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3160 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 3161 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3162 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3163 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3164 */         out = _jspx_page_context.pushBody();
/* 3165 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3166 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3169 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context))
/* 3170 */           return true;
/* 3171 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3172 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3175 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3176 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3179 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3180 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3181 */       return true;
/*      */     }
/* 3183 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3184 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3189 */     PageContext pageContext = _jspx_page_context;
/* 3190 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3192 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3193 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3194 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 3196 */     _jspx_th_c_005fout_005f1.setValue("${applicationScope.applications[param.haid]}");
/* 3197 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3198 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3200 */       return true;
/*      */     }
/* 3202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3208 */     PageContext pageContext = _jspx_page_context;
/* 3209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3211 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3212 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3213 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3215 */     _jspx_th_c_005fif_005f3.setTest("${param.method=='showApplication'}");
/* 3216 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3217 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3219 */         out.write(" \n          ");
/* 3220 */         if (_jspx_meth_am_005fTruncate_005f1(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3221 */           return true;
/* 3222 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3227 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3228 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3229 */       return true;
/*      */     }
/* 3231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f1(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3237 */     PageContext pageContext = _jspx_page_context;
/* 3238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3240 */     Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3241 */     _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 3242 */     _jspx_th_am_005fTruncate_005f1.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3244 */     _jspx_th_am_005fTruncate_005f1.setLength(25);
/* 3245 */     int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 3246 */     if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 3247 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3248 */         out = _jspx_page_context.pushBody();
/* 3249 */         _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 3250 */         _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3253 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_am_005fTruncate_005f1, _jspx_page_context))
/* 3254 */           return true;
/* 3255 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 3256 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3259 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 3260 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3263 */     if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 3264 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 3265 */       return true;
/*      */     }
/* 3267 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 3268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_am_005fTruncate_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3273 */     PageContext pageContext = _jspx_page_context;
/* 3274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3276 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3277 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3278 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_am_005fTruncate_005f1);
/*      */     
/* 3280 */     _jspx_th_c_005fout_005f2.setValue("${applicationScope.applications[param.haid]}");
/* 3281 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3282 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3283 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3284 */       return true;
/*      */     }
/* 3286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3292 */     PageContext pageContext = _jspx_page_context;
/* 3293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3295 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3296 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3297 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3299 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3300 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3301 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3303 */       return true;
/*      */     }
/* 3305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3311 */     PageContext pageContext = _jspx_page_context;
/* 3312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3314 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3315 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3316 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 3318 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 3319 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3320 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3322 */       return true;
/*      */     }
/* 3324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3330 */     PageContext pageContext = _jspx_page_context;
/* 3331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3333 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3334 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3335 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3337 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 3338 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3339 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3340 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3341 */       return true;
/*      */     }
/* 3343 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3349 */     PageContext pageContext = _jspx_page_context;
/* 3350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3352 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3353 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3354 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3356 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 3357 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3358 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3359 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3360 */       return true;
/*      */     }
/* 3362 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3368 */     PageContext pageContext = _jspx_page_context;
/* 3369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3371 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3372 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3373 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 3375 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 3376 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3377 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3378 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3379 */       return true;
/*      */     }
/* 3381 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3387 */     PageContext pageContext = _jspx_page_context;
/* 3388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3390 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3391 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3392 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 3394 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 3395 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3396 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3397 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3398 */       return true;
/*      */     }
/* 3400 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3406 */     PageContext pageContext = _jspx_page_context;
/* 3407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3409 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3410 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3411 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 3413 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 3414 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3415 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3416 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3417 */       return true;
/*      */     }
/* 3419 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3425 */     PageContext pageContext = _jspx_page_context;
/* 3426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3428 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3429 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3430 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 3432 */     _jspx_th_c_005fout_005f10.setValue("${selectedskin}");
/*      */     
/* 3434 */     _jspx_th_c_005fout_005f10.setDefault("${initParam.defaultSkin}");
/* 3435 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3436 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3437 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3438 */       return true;
/*      */     }
/* 3440 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3441 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AddMonitorsLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */