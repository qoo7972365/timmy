/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class ConfigureViews_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  663 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  835 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  850 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  851 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  854 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  855 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  856 */       set = AMConnectionPool.executeQueryStmt(query);
/*  857 */       if (set.next())
/*      */       {
/*  859 */         String helpLink = set.getString("LINK");
/*  860 */         DBUtil.searchLinks.put(key, helpLink);
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
/* 2179 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2180 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2193 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2197 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2198 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2199 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2200 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2201 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2202 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2203 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2207 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2218 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2221 */     JspWriter out = null;
/* 2222 */     Object page = this;
/* 2223 */     JspWriter _jspx_out = null;
/* 2224 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2228 */       response.setContentType("text/html;charset=UTF-8");
/* 2229 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2231 */       _jspx_page_context = pageContext;
/* 2232 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2233 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2234 */       session = pageContext.getSession();
/* 2235 */       out = pageContext.getOut();
/* 2236 */       _jspx_out = out;
/*      */       
/* 2238 */       out.write("<!DOCTYPE html>\n");
/* 2239 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2240 */       out.write(10);
/* 2241 */       out.write(10);
/* 2242 */       out.write(10);
/* 2243 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2245 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2246 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2248 */       out.write(10);
/* 2249 */       out.write(10);
/* 2250 */       out.write(10);
/* 2251 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2253 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2254 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2255 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2257 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2258 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2259 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2261 */           out.write(10);
/* 2262 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2264 */           out.write(10);
/* 2265 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2267 */           out.write(10);
/*      */           
/* 2269 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2270 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2271 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2273 */           _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */           
/* 2275 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2276 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2277 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2278 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2279 */               out = _jspx_page_context.pushBody();
/* 2280 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2281 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2284 */               out.write("\n\n\n\n<script language=\"Javascript\" src=\"/template/appmanager.js\" type=\"text/javascript\"></script>\n<link href=\"/images/");
/* 2285 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2287 */               out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */               
/* 2289 */               String viewName = request.getParameter("viewname");
/* 2290 */               String isCreateView = request.getParameter("isCreateView");
/* 2291 */               String columnName = request.getParameter("columnName");
/* 2292 */               AMConnectionPool ME_cp = AMConnectionPool.getInstance();
/* 2293 */               ManagedApplication mo = new ManagedApplication();
/* 2294 */               String selectedTab = request.getParameter("selectedTab");
/* 2295 */               int viewID = DBUtil.getViewID(viewName);
/*      */               
/* 2297 */               out.write("\n<input type=\"hidden\" name=\"viewname\" value=\"");
/* 2298 */               out.print(viewName);
/* 2299 */               out.write("\" id=\"viewname\">\n<script>\nfunction displayTabDiv(divName)\n{\n\n if (divName == 'divMonitorGrouplist')\n {\n  document.getElementById('divMonitorGrouplist').style.display=\"block\";\n  document.getElementById('divColumnList').style.display=\"none\";\n  document.getElementById('divAddNewColLink').style.display=\"none\";\n }\n else if (divName == 'divColumnList')\n {\n  document.getElementById('divMonitorGrouplist').style.display=\"none\";\n  document.getElementById('divColumnList').style.display=\"block\";\n  document.getElementById('divAddNewColLink').style.display=\"block\";\n }\n}\n\nfunction displayViewOpDiv(state)\n{\n\n \n\n if (state == true)\n {\n \n  document.getElementById('divCreateOption').style.display=\"block\";\n  document.getElementById('divEditOption').style.display=\"none\";\n }\n else \n {\n \n  document.getElementById('divEditOption').style.display=\"block\";\n  document.getElementById('divCreateOption').style.display=\"none\";\n }\n}\n\nfunction checkAll()\n{\n\t\tfor (var i=0;i<document.f1.elements.length;i++)\n\t\t{\n\t\t\tvar e=document.f1.elements[i];\n");
/* 2300 */               out.write("\t\t\tif ((e.name != 'selectAllGroup') && (e.type=='checkbox'))\n\t\t\t{\n\t\t\t\te.checked=document.f1.selectAllGroup.checked;\n\t\t\t}\n\t\t}\n}\n\nfunction  expCheck(str)\n{\n    //var objRegExp  = /^[A-Za-z0-9_\\-\\.]/;\n    var objRegExp=/^[A-Za-z0-9_\\-\\.]/;\n\treturn objRegExp.test(str);\n}\n\nfunction validateViewName()\n{\n    var test=true;\n\tview_name = document.getElementById(\"newviewname\").value // No I18N\n\tif (view_name == \"NewViewName\")\n\t{\n        //Enter the view name\n\t\talert('");
/* 2301 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2303 */               out.write("');\n\t\tdocument.getElementById(\"newviewname\").focus();\n\t\tdocument.getElementById(\"newviewname\").select();\n\t\ttest=false;\n\t}\n    //var sam=isCharacter(view_name)\n    ///alert(sam);\n    if (view_name!=\"\")\n    {   \n        var test1=expCheck(view_name);\n\n        //alert(test1);\n        if (test1==false)\n        {\n        \n        //alert(sam1);\n        alert(\"PLease Enter the Alpha Numeric with [-,_,.]\");\n        document.getElementById(\"newviewname\").focus();\n        test=false;\n        }\n\n    }\n\tif (view_name == \"\")\n\t{\n         //Enter the view name\n\t\talert('");
/* 2304 */               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2306 */               out.write("');\n\t\tdocument.getElementById(\"newviewname\").focus();\n\t\ttest=false;\n\t}\n\n\treturn test;\n}\n\nfunction changeViewName()\n{\n    \n    var form=document.frmAvailableviews;\n    var viewName=document.getElementById(\"selectViewName\").value;\n    form.action=\"/jsp/ConfigureViews.jsp?selectedTab=divMonitorGroup&isCreateView=false&viewname=\"+viewName; // No I18N\n    //form.action=\"/jsp/ConfigureViews.jsp?\";\n    //alert(\"hello1\");\n     form.submit();\n}\n\nfunction createNewViewName()\n{\n    var form=document.frmAvailableviews;\n    form.action=\"/jsp/ConfigureViews.jsp?selectedTab=divMonitorGroup&isCreateView=true&viewname=NewViewName\"; // No I18N\n}\n\nfunction backAvail()\n{\n    window.location=\"/jsp/AllViews.jsp\";\n}\n\nfunction formSubmit()\n{\n    var isCheckView=document.getElementById(\"isCreateView\").value;\n    var status= validateViewName();\n    if (status == true)\n    {\n        var form=document.frmAvailableviews;\n        var viewName=document.getElementById(\"viewname\").value;\n        form.action=\"/jsp/UpdateResourceMonitorGroupList.jsp?isCreateView=\"+isCheckView+\"&viewname=\"+viewName; // No I18N\n");
/* 2307 */               out.write("        form.submit();\n    }\n}\n\nfunction deleteColumnName(columnName)\n{\n    //to get id and store it in hidden variable\n\n      var viewName=document.getElementById(\"viewname\").value;\n      document.getElementById(\"columnName\").value=columnName;\n\n      //Are Sure to Delete this Column\n\n      var r=confirm(\"");
/* 2308 */               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2310 */               out.write("\" +columnName );// No I18N\n      if (r==true)\n      {\n        var form =document.getElementById(\"frmAvailableviews\");\n        form.action=\"/jsp/ConfigureViews.jsp?selectedTab=divColumnsTab&deleteOperation=true&isCreateView=false&viewname=\"+viewName; // No I18N\n        form.submit();\n      }\n}\n\nfunction addAttribute(id)\n{\n       \n       var columnName=id;\n       var viewName=document.getElementById(\"viewname\").value;\n       url='/jsp/NewColumnEntryForm.jsp?viewname='+viewName+'&editOption=true&column='+columnName; // No I18N\n       var win=window.open(url,'','resizable=no,scrollbars=YES,width=700,height=250');\n       win.focus(); // No I18N\n}\n\nfunction showNewColumnForm()\n    {\n        //alert('hello');\n        var view1=document.getElementById(\"newviewname\").value;\n        var viewNameTextBoxcheck =validateViewName();\n\n        if (viewNameTextBoxcheck == true)\n        {\n                url='/jsp/NewColumnEntryForm.jsp?viewname='+view1; // No I18N\n                var win=window.open(url,'','resizable=no,scrollbars=YES,width=700,height=250');\n");
/* 2311 */               out.write("                win.focus(); // No I18N\n            //    }\n        }\n    }\nfunction clearTempMessage()\n{\n    var viewName=document.getElementById(\"newviewname\").value;\n    if (viewName == \"NewViewName\")\n    {\n        document.getElementById(\"newviewname\").value=\"\";\n    }\n}\nfunction CheckViewName()\n    {\n\n    var state=true;\n    var value=document.getElementById(\"newviewname\").value\n    var viewNames=document.getElementById(\"viewNames\").value;\n    var viewNamesArray=viewNames.split(\",\");\n    // to return if filtername text field is null\n    if (value==\"\")\n        {\n            return state;\n        }\n    for(var i=0;i<viewNamesArray.length;i++)\n        {\n            var existedName=viewNamesArray[i];\n            if(existedName==value)\n                {\n                    state=false; // No I18N\n                    //alert(\"");
/* 2312 */               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2314 */               out.write("\");\n                }\n        }\n        if (state==false)\n        {\n            document.getElementById(\"newviewname\").focus();\n        }\n        //important here\n        //return state;\n\n    }\n\n\n</script>\n\n\n\n\n<!--body onLoad -->\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 2318 */               String deleteOperation = request.getParameter("deleteOperation");
/* 2319 */               if (((request.getParameter("viewname") != null ? 1 : 0) & (request.getParameter("columnName") != null ? 1 : 0) & (deleteOperation != null ? 1 : 0)) != 0)
/*      */               {
/* 2321 */                 ResultSet columnIDSet = null;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 try
/*      */                 {
/* 2330 */                   String columnIDQuery = "select COLUMNID from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where COLUMNNAME='" + columnName + "' and VIEWID=" + viewID + "";
/* 2331 */                   columnIDSet = AMConnectionPool.executeQueryStmt(columnIDQuery);
/* 2332 */                   int columnID1 = 0;
/* 2333 */                   while (columnIDSet.next())
/*      */                   {
/* 2335 */                     columnID1 = columnIDSet.getInt("COLUMNID");
/*      */                   }
/*      */                   
/* 2338 */                   String delColumnAsso = "delete from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where COLUMNID=" + columnID1 + "";
/* 2339 */                   mo.executeUpdateStmt(delColumnAsso);
/*      */                   
/* 2341 */                   String delColumnDetail = "delete from AM_STANDALONE_VIEWCOLUMN_DETAILS where COLUMNID=" + columnID1 + "";
/* 2342 */                   mo.executeUpdateStmt(delColumnDetail);
/*      */ 
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/* 2347 */                   e.printStackTrace();
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2352 */                   AMConnectionPool.closeStatement(columnIDSet); return; } finally { AMConnectionPool.closeStatement(columnIDSet);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2357 */               out.write("\n\n\n<form name=\"frmAvailableviews\" id=\"frmAvailableviews\" method=\"post\">\n<input type=\"hidden\" name=\"selectedTab\" id=\"selectedTab\" value=\"divMonitorGroup\">\n<input type=\"hidden\" name=\"isCreateView\" id=\"isCreateView\" value=");
/* 2358 */               out.print(isCreateView);
/* 2359 */               out.write(">\n<input type=\"hidden\" name=\"columnName\" id=\"columnName\">\n");
/*      */               
/* 2361 */               String strViewList1 = "";
/* 2362 */               String strViewList = "";
/* 2363 */               ResultSet viewNameSet = null;
/*      */               try
/*      */               {
/* 2366 */                 String viewNameQuery = "select VIEWNAME from AM_STANDALONE_VIEWDETAILS";
/* 2367 */                 viewNameSet = AMConnectionPool.executeQueryStmt(viewNameQuery);
/*      */                 
/* 2369 */                 while (viewNameSet.next())
/*      */                 {
/* 2371 */                   strViewList1 = viewNameSet.getString("VIEWNAME");
/* 2372 */                   strViewList = strViewList + strViewList1 + ",";
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*      */ 
/* 2380 */                 e.printStackTrace();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2385 */                 AMConnectionPool.closeStatement(viewNameSet); return; } finally { AMConnectionPool.closeStatement(viewNameSet);
/*      */               }
/*      */               
/* 2388 */               if (((strViewList != null ? 1 : 0) & (!strViewList.equals("") ? 1 : 0)) != 0)
/*      */               {
/* 2390 */                 strViewList = strViewList.substring(0, strViewList.length() - 1);
/*      */               }
/* 2392 */               System.out.println("strViewList" + strViewList);
/*      */               
/*      */ 
/* 2395 */               out.write("\n    <input type=\"hidden\" name=\"viewNames\" id=\"viewNames\" value=\"");
/* 2396 */               out.print(strViewList);
/* 2397 */               out.write("\" >\n\n\n\n\n\n\n<!--for link -->\n\n\n    ");
/*      */               
/* 2399 */               String state = request.getParameter("state");
/* 2400 */               if ((state != null) && (!state.equals("")))
/*      */               {
/*      */ 
/* 2403 */                 out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n  <tr>\n    <td> <img src=\"../images/icon_message_success.gif\" align=\"absmiddle\"> &nbsp;<span class=\"bodytext\">\n   ");
/*      */                 
/* 2405 */                 if (state.equals("column"))
/*      */                 {
/*      */ 
/* 2408 */                   out.write("\n          ");
/* 2409 */                   out.print(FormatUtil.getString("am.webclient.alertviews.confirm.columnname.create.title"));
/* 2410 */                   out.write("\n   ");
/*      */ 
/*      */                 }
/* 2413 */                 else if (state.equals("viewedit"))
/*      */                 {
/*      */ 
/* 2416 */                   out.write("\n    ");
/* 2417 */                   out.print(FormatUtil.getString("am.webclient.alertviews.confirm.view.update.title"));
/* 2418 */                   out.write("\n   ");
/*      */ 
/*      */                 }
/* 2421 */                 else if (state.equals("viewcreate"))
/*      */                 {
/*      */ 
/* 2424 */                   out.write("\n    ");
/* 2425 */                   out.print(FormatUtil.getString("am.webclient.alertviews.confirm.view.create.title"));
/* 2426 */                   out.write("\n    ");
/*      */                 }
/*      */                 
/*      */ 
/* 2430 */                 out.write("\n    </span>\n    </td>\n  </tr>\n</table>\n    ");
/*      */               }
/*      */               
/*      */ 
/* 2434 */               out.write("\n\n<!--table align=\"center\" > <tr><td><br><br></td></tr></table-->\n<table width=\"99%\" class=\"lrtbdarkborder\" cellpadding=\"8\" cellspacing=\"0\"  border=\"0\" align=center>\n    <tr>\n    <td class=\"cellpadd-none\">\n    \t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" align=center>\n    <tr>\n\n        <!-- View Configurations-->\n        <td colspan=3 width=\"\" class=\"tableheadingbborder\" align=\"left\">");
/* 2435 */               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2437 */               out.write("</td> ");
/* 2438 */               out.write("\n    </tr>\n    </table>\n    </td>\n    </tr>\n<tr>\n<td>\n        <!--Modification starts here-->\n    <table width=\"100%\" id=\"monitorGroupTable\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" align=center>\n    <tr>\n    <td>\n    <div id=\"divEditOption\">\n        <table width=\"100%\">\n           <tbody>\n            <tr>\n               <td  width=\"10%\">\n                   <!---Select View Name -->\n                        ");
/* 2439 */               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2441 */               out.write("\n               </td>\n               <td width=\"90%\">\n               <select name=\"viewname\" onchange=\"changeViewName()\" id=\"selectViewName\">\n               <!--option value=\"Select View\">Select View</option-->\n    ");
/*      */               
/*      */ 
/* 2444 */               ArrayList viewname_list = mo.getRows("select distinct(VIEWNAME) from AM_STANDALONE_VIEWDETAILS order by VIEWID");
/* 2445 */               System.out.println("viewname_list" + viewname_list);
/* 2446 */               if ((viewname_list != null) && (viewname_list.size() > 0))
/*      */               {
/* 2448 */                 if ((isCreateView == null) || (!isCreateView.equals("true")))
/*      */                 {
/* 2450 */                   int viewNameListCount = viewname_list.size();
/* 2451 */                   System.out.println("viewNameListCount" + viewNameListCount);
/*      */                   
/* 2453 */                   for (int vi = 0; vi < viewNameListCount; vi++)
/*      */                   {
/* 2455 */                     ArrayList tempview = (ArrayList)viewname_list.get(vi);
/* 2456 */                     String tempview1 = (String)tempview.get(0);
/*      */                     
/* 2458 */                     if (tempview1.equals(viewName))
/*      */                     {
/* 2460 */                       System.out.println("tempviewif" + tempview1);
/*      */                       
/* 2462 */                       out.write("\n                        <option value=");
/* 2463 */                       out.print(tempview1);
/* 2464 */                       out.write(" selected>");
/* 2465 */                       out.print(tempview1);
/* 2466 */                       out.write("</option>\n                    ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 2471 */                       System.out.println("tempview1else" + tempview1);
/*      */                       
/* 2473 */                       out.write("\n                        <option value=");
/* 2474 */                       out.print(tempview1);
/* 2475 */                       out.write(62);
/* 2476 */                       out.print(tempview1);
/* 2477 */                       out.write("</option>\n                    ");
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2482 */                   out.write("\n\n                ");
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2487 */               out.write("\n            </select>\n            </td>\n          </tr>\n        </tbody>\n       </table>\n     </div>\n     </td>\n\n    <td>\n    <div id=\"divCreateOption\" style=\"display:none\">\n        <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\">\n            <tbody>\n            <tr>\n                ");
/*      */               
/* 2489 */               if (viewName.equals("NewViewName"))
/*      */               {
/* 2491 */                 ResultSet columnIDSet = null;
/*      */                 try
/*      */                 {
/* 2494 */                   int tempviewID = DBUtil.getViewID(viewName);
/*      */                   
/*      */ 
/* 2497 */                   int columnID1 = 0;
/* 2498 */                   String columnIDStr = "";
/*      */                   
/*      */ 
/* 2501 */                   String columnIDQuery = "select COLUMNID from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where VIEWID=" + tempviewID + "";
/* 2502 */                   columnIDSet = AMConnectionPool.executeQueryStmt(columnIDQuery);
/* 2503 */                   while (columnIDSet.next())
/*      */                   {
/* 2505 */                     columnID1 = columnIDSet.getInt("COLUMNID");
/* 2506 */                     columnIDStr = columnIDStr + columnID1 + ",";
/*      */                   }
/* 2508 */                   if ((columnIDStr != null) && (!columnIDStr.equals("")))
/*      */                   {
/* 2510 */                     columnIDStr = columnIDStr.substring(0, columnIDStr.length() - 1);
/*      */                   }
/*      */                   
/* 2513 */                   String delColumnDetail = "delete from AM_STANDALONE_VIEWCOLUMN_DETAILS where COLUMNID in (" + columnIDStr + ")";
/*      */                   
/* 2515 */                   mo.executeUpdateStmt(delColumnDetail);
/*      */                   
/*      */ 
/* 2518 */                   String delColumnAsso = "delete from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where COLUMNID in (" + columnIDStr + ")";
/*      */                   
/* 2520 */                   mo.executeUpdateStmt(delColumnAsso);
/*      */                   
/* 2522 */                   String delViewAsso = "delete from AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION where VIEWID=" + tempviewID + "";
/* 2523 */                   mo.executeUpdateStmt(delViewAsso);
/*      */                   
/* 2525 */                   String delViewDetail = "delete from AM_STANDALONE_VIEWDETAILS where VIEWID=" + tempviewID + "";
/* 2526 */                   mo.executeUpdateStmt(delViewDetail);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2540 */                   System.out.println("some error occured");
/* 2541 */                   e.printStackTrace();
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2546 */                   AMConnectionPool.closeStatement(columnIDSet); return; } finally { AMConnectionPool.closeStatement(columnIDSet);
/*      */                 }
/*      */               }
/*      */               
/* 2550 */               out.write("\n\n                    <td  width=\"10%\" class=\"bodytext\" nowrap=\"nowrap\">\n                        <!--Enter view name -->\n                        ");
/* 2551 */               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2553 */               out.write("\n                    </td>\n                    <td width=\"90%\">\n                                <input type=text name=\"newviewname\" id=\"newviewname\" class=\"formtext normal\" onblur=\"CheckViewName()\" value=\"");
/* 2554 */               out.print(viewName);
/* 2555 */               out.write("\" onClick=\"clearTempMessage()\"/>\n                    </td>\n            </tr>\n            </tbody>\n        </table>\n       </div>\n     </td>\n           ");
/*      */               
/* 2557 */               String viewOption = "";
/*      */               
/* 2559 */               if ((isCreateView != null) && (isCreateView.equals("true")))
/*      */               {
/*      */ 
/* 2562 */                 out.write("\n                <script> displayViewOpDiv(true);  </script>\n            ");
/*      */                 
/* 2564 */                 System.out.println("viewOption" + viewOption);
/*      */               }
/*      */               
/* 2567 */               out.write("\n    </tr>\n    \n    <!--tr>\n    <td colspan=3 width=\"5%\" class=\"tableheading\"></td>\n    </tr-->\n\n\n        <!--Modification ends here -->\n\n    <!--tr>\n        <td colspan=3 width=\"5%\" class=\"columnheading\"><center></center></td>\n    </tr-->\n    </table>\n    <br>\n    \t\t\t\n                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"padd-bottom\">\n                    <tr class=\"tabBtmLine\">\n                            <td nowrap=\"nowrap\">\n                            <table id=\"InnerTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n                                <tbody>\n                                    <tr>\n                                    <td width=\"17\">\t</td>\n                                    <div id=\"divMonitorGroup\">\n                                        <td>\n                                            <table id=\"Monitor GroupTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" title=\"Monitor Group\">\n                                                <tbody>\n                                                <tr>\n");
/* 2568 */               out.write("                                                    <td class=\"tbSelected_Left\">\n                                                        <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"/>\n                                                    </td>\n                                                    <td class=\"tbSelected_Middle\" style=\"padding-left: 5px; padding-right: 5px;\" onclick=\"SetTabStyle('Monitor Group','InnerTab');toggleDiv('divMonitorGroup');toggleDiv('divColumnsTab');('null','null','null');displayTabDiv('divMonitorGrouplist')\">\n                                                        <!--here is removing and adding the title with A_ title tab  tabs.jsp-->\n                                                        <!--Monitor Group -->\n                                                        <span id=\"A_Monitor GroupTab\" class=\"tabLink\">");
/* 2569 */               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2571 */               out.write("</span> ");
/* 2572 */               out.write("\n                                                    </td>\n                                                    <td class=\"tbSelected_Right\">\n                                                        <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"/>\n                                                    </td>\n                                                </tr>\n                                                </tbody>\n                                            </table>\n                                        </td>\n                                    </div>\n\n                                    <div id=\"divColumnsTab\">\n                                        <td>\n                                            <table width=\"100%\"id=\"ColumnsTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" title=\"Columns\">\n                                                <tbody>\n                                                <tr>\n                                                    <td class=\"tbUnselected_Left\">\n");
/* 2573 */               out.write("                                                        <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"/>\n                                                    </td>\n                                                    <td class=\"tbUnselected_Middle\" style=\"padding-left: 5px; padding-right: 5px;\" onclick=\"SetTabStyle('Columns','InnerTab');toggleDiv('divColumnsTab');toggleDiv('divMonitorGroup');('null','null','null');displayTabDiv('divColumnList');\">\n                                                        <!--here is removing and adding the title with A_ title tab  tabs.jsp-->\n                                                        <!--Columns -->\n                                                        <span id=\"A_ColumnsTab\" class=\"tabLink\">");
/* 2574 */               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2576 */               out.write("</span> ");
/* 2577 */               out.write("\n                                                    </td>\n                                                    <td class=\"tbUnselected_Right\">\n                                                        <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"/>\n                                                    </td>\n                                                </tr>\n                                                </tbody>\n                                            </table>\n                                        </td>\n                                    </div>\n\n                                    </tr>\n                                </tbody>\n                            </table>\n                            </td>\n                            <td  align=\"right\">\n                                <!--Div for add new column link -->\n                                <div id=\"divAddNewColLink\" name=\"divAddNewColLink\" style=\"display:none\" >\n                                    <table><tbody><tr>\n                                    <td>\n");
/* 2578 */               out.write("                                        <!-- add new column-->\n                                       <a title=\"");
/* 2579 */               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2581 */               out.write("\" href=\"javascript:void(0)\" onclick=\"showNewColumnForm()\"><span><u><b>");
/* 2582 */               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2584 */               out.write("</b></u></span></a> ");
/* 2585 */               out.write("\n                                    </td>\n                                    </tr></tbody></table>\n                                </div>\n                            </td>\n                      </tr>\n                    </table>\n\n                <table class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n                <tr><td>\n                <div id=\"divMonitorGrouplist\" name=\"divMonitorGrouplist\" style=\"display:block\">\n                  <!--div is strated here --->\n                <table  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n                <tr><td>\n                    <table width=\"100%\"  cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" align=center>\n                        <!--Select Monitor Groups -->\n                        <tr><td class=\"tableheadingbborder\" width=\"100\">");
/* 2586 */               if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2588 */               out.write("</td></tr> ");
/* 2589 */               out.write("\n                    </table>\n                    </td>\n                </tr>\n                <tr>\n                <td>\n                     <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n                     ");
/*      */               
/*      */ 
/* 2592 */               ArrayList list1 = mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0  order by RESOURCENAME");
/* 2593 */               String isChecked = "UNCHECKED";
/*      */               
/*      */ 
/* 2596 */               ResultSet viewMonitorsSet = null;
/* 2597 */               ArrayList viewMonitorsArray = new ArrayList();
/*      */               try
/*      */               {
/* 2600 */                 String viewMonitorsQuery = "select MONITORGROUPID from AM_STANDALONE_VIEW_MONITORGROUP_ASSOCIATION where VIEWID=" + viewID + "";
/* 2601 */                 viewMonitorsSet = AMConnectionPool.executeQueryStmt(viewMonitorsQuery);
/* 2602 */                 while (viewMonitorsSet.next())
/*      */                 {
/* 2604 */                   viewMonitorsArray.add(viewMonitorsSet.getString(1));
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 2609 */                 e.printStackTrace();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 2614 */                 AMConnectionPool.closeStatement(viewMonitorsSet); return; } finally { AMConnectionPool.closeStatement(viewMonitorsSet);
/*      */               }
/*      */               
/* 2617 */               System.out.println("viewMonitorsArray" + viewMonitorsArray);
/*      */               
/* 2619 */               int listCount = list1.size();
/* 2620 */               for (int i = 0; i < listCount; i++)
/*      */               {
/* 2622 */                 isChecked = "UNCHECKED";
/* 2623 */                 ArrayList tempView = (ArrayList)list1.get(i);
/* 2624 */                 String tempView1 = (String)tempView.get(0);
/* 2625 */                 String monitorID = (String)tempView.get(6);
/* 2626 */                 if (viewMonitorsArray.contains(monitorID))
/*      */                 {
/* 2628 */                   isChecked = "CHECKED";
/*      */                 }
/*      */                 
/* 2631 */                 out.write("\n\n                                                   <tr name=\"row_");
/* 2632 */                 out.print(i);
/* 2633 */                 out.write("\" id=\"row_");
/* 2634 */                 out.print(i);
/* 2635 */                 out.write("\" >\n                                                        <td width=\"2%\" class=\"whitegrayborder\"><input type=\"checkbox\" name=\"ch_index");
/* 2636 */                 out.print(i);
/* 2637 */                 out.write("\" value=\"");
/* 2638 */                 out.print(monitorID);
/* 2639 */                 out.write(34);
/* 2640 */                 out.write(32);
/* 2641 */                 out.print(isChecked);
/* 2642 */                 out.write("></input></td>\n                                                        <td width=\"98%\" class=\"whitegrayborder\">");
/* 2643 */                 out.print(tempView1);
/* 2644 */                 out.write("</td>\n                                                    </tr>\n\n                        ");
/*      */               }
/*      */               
/*      */ 
/* 2648 */               out.write("\n\n\n                     </table>\n                  </td>\n                  </tr>\n                </table>\n                <br>\n\n                <table align=\"center\">\n        \t\t\t<tr>\n                            <!--td class=\"tbUnselected_Left\"><td>\n                            <td class=\"tbUnselected_Middle\"> hello<td>\n                            <td class=\"tbUnselected_Right\"> <td-->\n\n                            <td>\n                            <input type=\"hidden\" name=\"monitorID\" value=\"");
/* 2649 */               out.print(list1);
/* 2650 */               out.write("\" id=\"monitorID\">\n                            <input type=hidden name=mo_list value=5> </input>\n                            <!--save -->\n                \t\t\t<input type=\"button\" class=\"buttons btn_highlt\" name=\"sumbit\" value=\"");
/* 2651 */               if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2653 */               out.write("\" onclick=\"formSubmit()\">\n                                <!--close -->\n                \t\t\t<input type=\"button\" class=\"buttons btn_link\" name=\"close\" value=\"");
/* 2654 */               if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2656 */               out.write("\" onclick=\"javascript:backAvail();\">\n                            </td>\n\n    \t\t\t\t</tr>\n                </table>\n\n                </div>\n\n                <div id=\"divColumnList\" name=\"divColumnList\" style=\"display:none\">\n                <table  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n                <tr>\n                    <td>\n                    <!--table width=\"100%\"  cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" align=center>\n                        <tr><td class=\"tableheading\" width=\"100\">Add Column Here</td></tr>\n                    </table-->\n                    </td>\n                </tr>\n                <tr>\n                    <td>\n                    <table width=\"100%\"  cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" align=center>\n                        <tr>\n                            <!--Column Name -->\n                            <td align=\"left\"  class=\"tableheadingbborder\" width=\"20%\" >");
/* 2657 */               if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2659 */               out.write(" </td>");
/* 2660 */               out.write("\n                            <!--Description -->\n                            <td align=\"center\" class=\"tableheadingbborder\" width=\"60%\" >");
/* 2661 */               if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2663 */               out.write("</td> ");
/* 2664 */               out.write("\n                            <!--Add More Attributes -->\n                            <td align=\"center\" class=\"tableheadingbborder\" width=\"10%\"  >");
/* 2665 */               if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2667 */               out.write("</td> ");
/* 2668 */               out.write("\n                            <!--Delete -->\n                            <td align=\"center\" class=\"tableheadingbborder\" width=\"10%\"  >");
/* 2669 */               if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2671 */               out.write("</td> ");
/* 2672 */               out.write("\n                        </tr>\n                    </table>\n\n                    </td>\n\n                </tr>\n                <!--for Data retrieve (column contains resource and attribute details) -->\n                <tr>\n                    <td>\n                    <table width=\"100%\"  cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" align=center>\n                     ");
/*      */               
/* 2674 */               ResultSet columnDetailSet = null;
/*      */               
/*      */ 
/* 2677 */               ArrayList columnList1 = mo.getRows("select COLUMNNAME, COLUMNID from AM_STANDALONE_VIEWCOLUMN_ASSOCIATION where VIEWID=" + viewID + " order by COLUMNID");
/* 2678 */               int columnListCount = columnList1.size();
/* 2679 */               for (int i = 0; i < columnListCount; i++)
/*      */               {
/* 2681 */                 ArrayList tempColumn = (ArrayList)columnList1.get(i);
/* 2682 */                 String tempColumn1 = (String)tempColumn.get(0);
/* 2683 */                 String tempColumnID = (String)tempColumn.get(1);
/* 2684 */                 int columnID = Integer.parseInt(tempColumnID);
/*      */                 
/* 2686 */                 out.write("\n\n                    <tr name=\"row_");
/* 2687 */                 out.print(i);
/* 2688 */                 out.write("\" id=\"row_");
/* 2689 */                 out.print(i);
/* 2690 */                 out.write("\">\n                            <td class=\"whitegrayborder cellpadd-tb-none\" width=\"20%\"  ><b>");
/* 2691 */                 out.print(tempColumn1);
/* 2692 */                 out.write("</b></td>\n                            <td width=\"60%\" align=\"left\" class=\"whitegrayborder cellpadd-tb-none\">\n                            <table width=\"100%\"  cellpadding=\"1\" cellspacing=\"0\"  align=center style=\"border-left: 1px solid #E8E8E8;\">\n\n\n                    ");
/* 2693 */                 String columnDetails = "";
/*      */                 try
/*      */                 {
/* 2696 */                   String columnDetailQuery = "select distinct RESOURCETYPE,ATTRIBUTE from AM_STANDALONE_VIEWCOLUMN_DETAILS where COLUMNID=" + columnID + "";
/* 2697 */                   columnDetailSet = AMConnectionPool.executeQueryStmt(columnDetailQuery);
/*      */                   
/* 2699 */                   while (columnDetailSet.next())
/*      */                   {
/* 2701 */                     String resourceName = columnDetailSet.getString("RESOURCETYPE");
/* 2702 */                     String attributeName = columnDetailSet.getString("ATTRIBUTE");
/*      */                     
/* 2704 */                     out.write("\n                            <tr><td class=\"whitegrayborder cellpadd-tb-none rborder\" width=\"35%\">");
/* 2705 */                     out.print(resourceName);
/* 2706 */                     out.write("</td><td class=\"whitegrayborder\">");
/* 2707 */                     out.print(attributeName);
/* 2708 */                     out.write("</td></tr>\n                     ");
/*      */                     
/* 2710 */                     columnDetails = columnDetails + "<br>" + resourceName + "'s --  " + attributeName;
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 catch (Exception e)
/*      */                 {
/* 2716 */                   e.printStackTrace();
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2721 */                   AMConnectionPool.closeStatement(columnDetailSet); return; } finally { AMConnectionPool.closeStatement(columnDetailSet);
/*      */                 }
/*      */                 
/* 2724 */                 out.write("\n                            \n                                \n                                \n                                ");
/*      */                 
/* 2726 */                 out.write("</table></td>\n                            <!--Add More Attributes -->\n                            <td align=\"center\" width=\"10%\" class=\"whitegrayborder lrborder\"> <a id=\"");
/* 2727 */                 out.print(tempColumn1);
/* 2728 */                 out.write("\" title=\"");
/* 2729 */                 if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                   return;
/* 2731 */                 out.write("\" onclick=\"javascript:addAttribute(this.id);\"><img border=\"0\" class=\"cur-pointer\" src=\"/images/icon_edit.gif\"/></a></td>\n                            <!--Delete -->\n                            <td align=\"center\" width=\"10%\" class=\"whitegrayborder cellpadd-tb-none\"><a id=\"");
/* 2732 */                 out.print(tempColumn1);
/* 2733 */                 out.write("\" title=\"");
/* 2734 */                 if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                   return;
/* 2736 */                 out.write("\" onclick=\"javascript:deleteColumnName(this.id);\"><img border=\"0\" class=\"cur-pointer\" src=\"/images/delete.png\"/></a></td>\n                    ");
/*      */               }
/*      */               
/*      */ 
/* 2740 */               out.write("\n\n\n                    </tr>\n\n                        <!--tr class=\"whitegrayborder\">\n                            <td class=\"whitegrayborder\" width=\"20%\">Column Name</td>\n                            <td class=\"whitegrayborder\" width=\"40%\">Description</td>\n                            <td class=\"30%\"></td>\n                        </tr-->\n                    </table>\n\n                    </td>\n\n                </tr>\n\n                </table>\n\n                <table align=\"center\">\n                    <tr>\n                            <td>\n                            <!-- close -->\n                            <input type=\"button\" class=\"buttons btn_link\" name=\"close\" value=\"");
/* 2741 */               if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2743 */               out.write("\" onclick=\"javascript:backAvail();\">\n                            </td>\n\n                    </tr>\n                </table>\n\n                </div>\n                </td></tr>\n\n\n                </table>\n\n        ");
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2750 */               if (selectedTab.equals("divMonitorGroup"))
/*      */               {
/*      */ 
/*      */ 
/* 2754 */                 out.write("\n            <script>SetTabStyle('Monitor Group','InnerTab');toggleDiv('divMonitorGroup');toggleDiv('divColumnsTab');('null','null','null');displayTabDiv('divMonitorGrouplist'); </script>\n            ");
/*      */ 
/*      */               }
/* 2757 */               else if (selectedTab.equals("divColumnsTab"))
/*      */               {
/*      */ 
/*      */ 
/* 2761 */                 out.write("\n            <script>SetTabStyle('Columns','InnerTab');toggleDiv('divColumnsTab');toggleDiv('divMonitorGroup');('null','null','null');displayTabDiv('divColumnList'); </script>\n            ");
/*      */               }
/*      */               
/*      */ 
/* 2765 */               out.write("\n\n\n</td> </tr>\n<tr>\n    <td>\n\n            <!--tr> ");
/* 2766 */               out.write("\n                <td>\n                <input type=\"hidden\" name=\"monitorID\" value=\"");
/* 2767 */               out.print(list1);
/* 2768 */               out.write("\" id=\"monitorID\">\n                <input type=hidden name=mo_list value=5> </input>\n                <!--save -->\n\n                <!--input type=\"button\" class=\"buttons\" name=\"sumbit\" value=\"");
/* 2769 */               if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2771 */               out.write("\" onclick=\"javascript:formSubmit()\"-->");
/* 2772 */               out.write("\n                    <!--close -->\n\n            <!--/tr>\n        </table-->\n\n    </td>\n</tr>\n</table>\n</form>\n\n\n\n");
/* 2773 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2774 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2777 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2778 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2781 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2782 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 2785 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2786 */           out.write(32);
/* 2787 */           out.write(10);
/* 2788 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2790 */           out.write(32);
/* 2791 */           out.write(10);
/* 2792 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2793 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2797 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2798 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 2801 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2802 */         out.write(32);
/* 2803 */         out.write(32);
/* 2804 */         out.write(10);
/*      */       }
/* 2806 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2807 */         out = _jspx_out;
/* 2808 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2809 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2810 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2813 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2819 */     PageContext pageContext = _jspx_page_context;
/* 2820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2822 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2823 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2824 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2826 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2828 */     _jspx_th_tiles_005fput_005f0.setValue("Views Configurations");
/* 2829 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2830 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2831 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2832 */       return true;
/*      */     }
/* 2834 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2840 */     PageContext pageContext = _jspx_page_context;
/* 2841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2843 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2844 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2845 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2847 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2849 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 2850 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2851 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2865 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 2868 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2870 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2871 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2872 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2873 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2874 */       return true;
/*      */     }
/* 2876 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2882 */     PageContext pageContext = _jspx_page_context;
/* 2883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2885 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2886 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2887 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 2888 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2889 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 2890 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2891 */         out = _jspx_page_context.pushBody();
/* 2892 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 2893 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2896 */         out.write("am.webclient.alertviews.err.enterviewname.title");
/* 2897 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 2898 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2901 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 2902 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2905 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2906 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2907 */       return true;
/*      */     }
/* 2909 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2915 */     PageContext pageContext = _jspx_page_context;
/* 2916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2918 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2919 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2920 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 2921 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2922 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2923 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2924 */         out = _jspx_page_context.pushBody();
/* 2925 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2926 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2929 */         out.write("am.webclient.alertviews.err.enterviewname.title");
/* 2930 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2931 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2934 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2935 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2938 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2939 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2940 */       return true;
/*      */     }
/* 2942 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2943 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2948 */     PageContext pageContext = _jspx_page_context;
/* 2949 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2951 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2952 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2953 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 2954 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2955 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 2956 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2957 */         out = _jspx_page_context.pushBody();
/* 2958 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 2959 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2962 */         out.write("am.webclient.alertviews.confirm.deletecolumn.title");
/* 2963 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 2964 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2967 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 2968 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2971 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2972 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2973 */       return true;
/*      */     }
/* 2975 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2981 */     PageContext pageContext = _jspx_page_context;
/* 2982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2984 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 2985 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2986 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 2987 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2988 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 2989 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 2990 */         out = _jspx_page_context.pushBody();
/* 2991 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 2992 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2995 */         out.write("am.webclient.alertviews.err.columnduplicate.title");
/* 2996 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 2997 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3000 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 3001 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3004 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3005 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3006 */       return true;
/*      */     }
/* 3008 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3009 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3014 */     PageContext pageContext = _jspx_page_context;
/* 3015 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3017 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3018 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3019 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3020 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3021 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 3022 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3023 */         out = _jspx_page_context.pushBody();
/* 3024 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 3025 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3028 */         out.write("am.webclient.alertviews.viewconfiguration.title");
/* 3029 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 3030 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3033 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 3034 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3037 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3038 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3039 */       return true;
/*      */     }
/* 3041 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3042 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3047 */     PageContext pageContext = _jspx_page_context;
/* 3048 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3050 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3051 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3052 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3053 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3054 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 3055 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3056 */         out = _jspx_page_context.pushBody();
/* 3057 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 3058 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3061 */         out.write("am.webclient.alertviews.selectviewname.title");
/* 3062 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 3063 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3066 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 3067 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3070 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3071 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3072 */       return true;
/*      */     }
/* 3074 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3080 */     PageContext pageContext = _jspx_page_context;
/* 3081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3083 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3084 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3085 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3086 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3087 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 3088 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3089 */         out = _jspx_page_context.pushBody();
/* 3090 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 3091 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3094 */         out.write("am.webclient.alertviews.enterviewname.title");
/* 3095 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 3096 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3099 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 3100 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3103 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3104 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3105 */       return true;
/*      */     }
/* 3107 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3113 */     PageContext pageContext = _jspx_page_context;
/* 3114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3116 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3117 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3118 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3119 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3120 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 3121 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3122 */         out = _jspx_page_context.pushBody();
/* 3123 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 3124 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3127 */         out.write("am.webclient.alertviews.monitorgroups.title");
/* 3128 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 3129 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3132 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 3133 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3136 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3137 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3138 */       return true;
/*      */     }
/* 3140 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3146 */     PageContext pageContext = _jspx_page_context;
/* 3147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3149 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3150 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3151 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3152 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3153 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 3154 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3155 */         out = _jspx_page_context.pushBody();
/* 3156 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 3157 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3160 */         out.write("am.webclient.alertviews.columns.title");
/* 3161 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 3162 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3165 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 3166 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3169 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3170 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3171 */       return true;
/*      */     }
/* 3173 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3179 */     PageContext pageContext = _jspx_page_context;
/* 3180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3182 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3183 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3184 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3185 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3186 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 3187 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3188 */         out = _jspx_page_context.pushBody();
/* 3189 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 3190 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3193 */         out.write("am.webclient.alertviews.heading.addnewcolumn.title");
/* 3194 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 3195 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3198 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 3199 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3202 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3203 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3204 */       return true;
/*      */     }
/* 3206 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3212 */     PageContext pageContext = _jspx_page_context;
/* 3213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3215 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3216 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3217 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3218 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3219 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 3220 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3221 */         out = _jspx_page_context.pushBody();
/* 3222 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 3223 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3226 */         out.write("am.webclient.alertviews.heading.addnewcolumn.title");
/* 3227 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 3228 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3231 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 3232 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3235 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3236 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3237 */       return true;
/*      */     }
/* 3239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3245 */     PageContext pageContext = _jspx_page_context;
/* 3246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3248 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3249 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3250 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3251 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3252 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 3253 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3254 */         out = _jspx_page_context.pushBody();
/* 3255 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 3256 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3259 */         out.write("am.webclient.alertviews.selectmonitorgroups.title");
/* 3260 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 3261 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3264 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 3265 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3268 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3269 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3270 */       return true;
/*      */     }
/* 3272 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3273 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3278 */     PageContext pageContext = _jspx_page_context;
/* 3279 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3281 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3282 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3283 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3284 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3285 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 3286 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3287 */         out = _jspx_page_context.pushBody();
/* 3288 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 3289 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3292 */         out.write("am.webclient.alertviews.button.save.name");
/* 3293 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 3294 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3297 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 3298 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3301 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3302 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3303 */       return true;
/*      */     }
/* 3305 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3311 */     PageContext pageContext = _jspx_page_context;
/* 3312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3314 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3315 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3316 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3317 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3318 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 3319 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3320 */         out = _jspx_page_context.pushBody();
/* 3321 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 3322 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3325 */         out.write("am.webclient.alertviews.button.close.name");
/* 3326 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 3327 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3330 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 3331 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3334 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3335 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3336 */       return true;
/*      */     }
/* 3338 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3344 */     PageContext pageContext = _jspx_page_context;
/* 3345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3347 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3348 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3349 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3350 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3351 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 3352 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3353 */         out = _jspx_page_context.pushBody();
/* 3354 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 3355 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3358 */         out.write("am.webclient.alertviews.columnname.title");
/* 3359 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 3360 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3363 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 3364 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3367 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3368 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3369 */       return true;
/*      */     }
/* 3371 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3372 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3377 */     PageContext pageContext = _jspx_page_context;
/* 3378 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3380 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3381 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 3382 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3383 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 3384 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 3385 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3386 */         out = _jspx_page_context.pushBody();
/* 3387 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 3388 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3391 */         out.write("am.webclient.alertviews.description.title");
/* 3392 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 3393 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3396 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 3397 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3400 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 3401 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3402 */       return true;
/*      */     }
/* 3404 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3410 */     PageContext pageContext = _jspx_page_context;
/* 3411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3413 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3414 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 3415 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3416 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 3417 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 3418 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3419 */         out = _jspx_page_context.pushBody();
/* 3420 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 3421 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3424 */         out.write("am.webclient.alertviews.addmoreattributes.title");
/* 3425 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 3426 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3429 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 3430 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3433 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 3434 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3435 */       return true;
/*      */     }
/* 3437 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3443 */     PageContext pageContext = _jspx_page_context;
/* 3444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3446 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3447 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 3448 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3449 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 3450 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 3451 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3452 */         out = _jspx_page_context.pushBody();
/* 3453 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 3454 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3457 */         out.write("am.webclient.alertviews.delete.title");
/* 3458 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 3459 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3462 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 3463 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3466 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3480 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3482 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 3483 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 3484 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 3485 */         out = _jspx_page_context.pushBody();
/* 3486 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 3487 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3490 */         out.write("am.webclient.alertviews.addmoreattributes.title");
/* 3491 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 3492 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3495 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 3496 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3499 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 3500 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3501 */       return true;
/*      */     }
/* 3503 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3509 */     PageContext pageContext = _jspx_page_context;
/* 3510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3512 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3513 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 3514 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3515 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 3516 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 3517 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 3518 */         out = _jspx_page_context.pushBody();
/* 3519 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 3520 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3523 */         out.write("am.webclient.alertviews.delete.title");
/* 3524 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 3525 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3528 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 3529 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3532 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 3533 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3534 */       return true;
/*      */     }
/* 3536 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3542 */     PageContext pageContext = _jspx_page_context;
/* 3543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3545 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3546 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 3547 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3548 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 3549 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 3550 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 3551 */         out = _jspx_page_context.pushBody();
/* 3552 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 3553 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3556 */         out.write("am.webclient.alertviews.button.close.name");
/* 3557 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 3558 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3561 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 3562 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3565 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 3566 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3567 */       return true;
/*      */     }
/* 3569 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3575 */     PageContext pageContext = _jspx_page_context;
/* 3576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3578 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3579 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 3580 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/* 3581 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 3582 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 3583 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 3584 */         out = _jspx_page_context.pushBody();
/* 3585 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 3586 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3589 */         out.write("am.webclient.alertviews.button.save.name");
/* 3590 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 3591 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3594 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 3595 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3598 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 3599 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3600 */       return true;
/*      */     }
/* 3602 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3608 */     PageContext pageContext = _jspx_page_context;
/* 3609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3611 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3612 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3613 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3615 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3617 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3618 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3619 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3620 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3621 */       return true;
/*      */     }
/* 3623 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3624 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ConfigureViews_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */