/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class NetworkView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   53 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   56 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   57 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   58 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   65 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   70 */     ArrayList list = null;
/*   71 */     StringBuffer sbf = new StringBuffer();
/*   72 */     ManagedApplication mo = new ManagedApplication();
/*   73 */     if (distinct)
/*      */     {
/*   75 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   79 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   82 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   84 */       ArrayList row = (ArrayList)list.get(i);
/*   85 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   86 */       if (distinct) {
/*   87 */         sbf.append(row.get(0));
/*      */       } else
/*   89 */         sbf.append(row.get(1));
/*   90 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   93 */     return sbf.toString(); }
/*      */   
/*   95 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   98 */     if (severity == null)
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  102 */     if (severity.equals("5"))
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("1"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  113 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  120 */     if (severity == null)
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("1"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("4"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("5"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  139 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  145 */     if (severity == null)
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  149 */     if (severity.equals("5"))
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  153 */     if (severity.equals("1"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  159 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  165 */     if (severity == null)
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  169 */     if (severity.equals("1"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  173 */     if (severity.equals("4"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  177 */     if (severity.equals("5"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  183 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  189 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  195 */     if (severity == 5)
/*      */     {
/*  197 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  199 */     if (severity == 1)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  206 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  212 */     if (severity == null)
/*      */     {
/*  214 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  216 */     if (severity.equals("5"))
/*      */     {
/*  218 */       if (isAvailability) {
/*  219 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  222 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  225 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  227 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  229 */     if (severity.equals("1"))
/*      */     {
/*  231 */       if (isAvailability) {
/*  232 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  235 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  242 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  249 */     if (severity == null)
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("5"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("4"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("1"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  268 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  274 */     if (severity == null)
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("5"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("4"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("1"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  293 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  300 */     if (severity == null)
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  304 */     if (severity.equals("5"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  308 */     if (severity.equals("4"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  312 */     if (severity.equals("1"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  319 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  327 */     StringBuffer out = new StringBuffer();
/*  328 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  329 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  330 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  331 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  332 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  333 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  334 */     out.append("</tr>");
/*  335 */     out.append("</form></table>");
/*  336 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  343 */     if (val == null)
/*      */     {
/*  345 */       return "-";
/*      */     }
/*      */     
/*  348 */     String ret = FormatUtil.formatNumber(val);
/*  349 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  350 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  353 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  357 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  365 */     StringBuffer out = new StringBuffer();
/*  366 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  367 */     out.append("<tr>");
/*  368 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  370 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  372 */     out.append("</tr>");
/*  373 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  377 */       if (j % 2 == 0)
/*      */       {
/*  379 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  383 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  386 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  388 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  391 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  395 */       out.append("</tr>");
/*      */     }
/*  397 */     out.append("</table>");
/*  398 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  399 */     out.append("<tr>");
/*  400 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  401 */     out.append("</tr>");
/*  402 */     out.append("</table>");
/*  403 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  409 */     StringBuffer out = new StringBuffer();
/*  410 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  411 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  412 */     out.append("<tr>");
/*  413 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  416 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  417 */     out.append("</tr>");
/*  418 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  421 */       out.append("<tr>");
/*  422 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  423 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  424 */       out.append("</tr>");
/*      */     }
/*      */     
/*  427 */     out.append("</table>");
/*  428 */     out.append("</table>");
/*  429 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  434 */     if (severity.equals("0"))
/*      */     {
/*  436 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  440 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  447 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  460 */     StringBuffer out = new StringBuffer();
/*  461 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  462 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  464 */       out.append("<tr>");
/*  465 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  466 */       out.append("</tr>");
/*      */       
/*      */ 
/*  469 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  471 */         String borderclass = "";
/*      */         
/*      */ 
/*  474 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  476 */         out.append("<tr>");
/*      */         
/*  478 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  479 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  480 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  486 */     out.append("</table><br>");
/*  487 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  488 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  490 */       List sLinks = secondLevelOfLinks[0];
/*  491 */       List sText = secondLevelOfLinks[1];
/*  492 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  495 */         out.append("<tr>");
/*  496 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  497 */         out.append("</tr>");
/*  498 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  500 */           String borderclass = "";
/*      */           
/*      */ 
/*  503 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  505 */           out.append("<tr>");
/*      */           
/*  507 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  508 */           if (sLinks.get(i).toString().length() == 0) {
/*  509 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  512 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  514 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  518 */     out.append("</table>");
/*  519 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  526 */     StringBuffer out = new StringBuffer();
/*  527 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  528 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  530 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  532 */         out.append("<tr>");
/*  533 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  534 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  538 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  540 */           String borderclass = "";
/*      */           
/*      */ 
/*  543 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  545 */           out.append("<tr>");
/*      */           
/*  547 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  548 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  549 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  552 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  555 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  560 */     out.append("</table><br>");
/*  561 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  562 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  564 */       List sLinks = secondLevelOfLinks[0];
/*  565 */       List sText = secondLevelOfLinks[1];
/*  566 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  569 */         out.append("<tr>");
/*  570 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  571 */         out.append("</tr>");
/*  572 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  574 */           String borderclass = "";
/*      */           
/*      */ 
/*  577 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  579 */           out.append("<tr>");
/*      */           
/*  581 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  582 */           if (sLinks.get(i).toString().length() == 0) {
/*  583 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  586 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  588 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  592 */     out.append("</table>");
/*  593 */     return out.toString();
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
/*  606 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  621 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  627 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  635 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  640 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  645 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  650 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  655 */     if (val != null)
/*      */     {
/*  657 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  661 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  666 */     if (val == null) {
/*  667 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  671 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  676 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  682 */     if (val != null)
/*      */     {
/*  684 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  688 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  694 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  699 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  708 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  713 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  718 */     String hostaddress = "";
/*  719 */     String ip = request.getHeader("x-forwarded-for");
/*  720 */     if (ip == null)
/*  721 */       ip = request.getRemoteAddr();
/*  722 */     InetAddress add = null;
/*  723 */     if (ip.equals("127.0.0.1")) {
/*  724 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  728 */       add = InetAddress.getByName(ip);
/*      */     }
/*  730 */     hostaddress = add.getHostName();
/*  731 */     if (hostaddress.indexOf('.') != -1) {
/*  732 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  733 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  737 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  742 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  748 */     if (severity == null)
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  752 */     if (severity.equals("5"))
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("1"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  763 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  768 */     ResultSet set = null;
/*  769 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  770 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  772 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  773 */       if (set.next()) { String str1;
/*  774 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  775 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  778 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  783 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  786 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  788 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  792 */     StringBuffer rca = new StringBuffer();
/*  793 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  794 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  797 */     int rcalength = key.length();
/*  798 */     String split = "6. ";
/*  799 */     int splitPresent = key.indexOf(split);
/*  800 */     String div1 = "";String div2 = "";
/*  801 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  803 */       if (rcalength > 180) {
/*  804 */         rca.append("<span class=\"rca-critical-text\">");
/*  805 */         getRCATrimmedText(key, rca);
/*  806 */         rca.append("</span>");
/*      */       } else {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         rca.append(key);
/*  810 */         rca.append("</span>");
/*      */       }
/*  812 */       return rca.toString();
/*      */     }
/*  814 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  815 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  816 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  817 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div1, rca);
/*  819 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  822 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  823 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  824 */     getRCATrimmedText(div2, rca);
/*  825 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  827 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  832 */     String[] st = msg.split("<br>");
/*  833 */     for (int i = 0; i < st.length; i++) {
/*  834 */       String s = st[i];
/*  835 */       if (s.length() > 180) {
/*  836 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  838 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  842 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  843 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  845 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  849 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  850 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  851 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  854 */       if (key == null) {
/*  855 */         return ret;
/*      */       }
/*      */       
/*  858 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  859 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  862 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  863 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  864 */       set = AMConnectionPool.executeQueryStmt(query);
/*  865 */       if (set.next())
/*      */       {
/*  867 */         String helpLink = set.getString("LINK");
/*  868 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  871 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  877 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  896 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  887 */         if (set != null) {
/*  888 */           AMConnectionPool.closeStatement(set);
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
/*  902 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  903 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  905 */       String entityStr = (String)keys.nextElement();
/*  906 */       String mmessage = temp.getProperty(entityStr);
/*  907 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  908 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  910 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  916 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  917 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  919 */       String entityStr = (String)keys.nextElement();
/*  920 */       String mmessage = temp.getProperty(entityStr);
/*  921 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  922 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  924 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  929 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  939 */     String des = new String();
/*  940 */     while (str.indexOf(find) != -1) {
/*  941 */       des = des + str.substring(0, str.indexOf(find));
/*  942 */       des = des + replace;
/*  943 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  945 */     des = des + str;
/*  946 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  953 */       if (alert == null)
/*      */       {
/*  955 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  957 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  959 */         return "&nbsp;";
/*      */       }
/*      */       
/*  962 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  964 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  967 */       int rcalength = test.length();
/*  968 */       if (rcalength < 300)
/*      */       {
/*  970 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  974 */       StringBuffer out = new StringBuffer();
/*  975 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  976 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  977 */       out.append("</div>");
/*  978 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  980 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  985 */       ex.printStackTrace();
/*      */     }
/*  987 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  993 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  998 */     ArrayList attribIDs = new ArrayList();
/*  999 */     ArrayList resIDs = new ArrayList();
/* 1000 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1002 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1004 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1006 */       String resourceid = "";
/* 1007 */       String resourceType = "";
/* 1008 */       if (type == 2) {
/* 1009 */         resourceid = (String)row.get(0);
/* 1010 */         resourceType = (String)row.get(3);
/*      */       }
/* 1012 */       else if (type == 3) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1017 */         resourceid = (String)row.get(6);
/* 1018 */         resourceType = (String)row.get(7);
/*      */       }
/* 1020 */       resIDs.add(resourceid);
/* 1021 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1022 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1024 */       String healthentity = null;
/* 1025 */       String availentity = null;
/* 1026 */       if (healthid != null) {
/* 1027 */         healthentity = resourceid + "_" + healthid;
/* 1028 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1031 */       if (availid != null) {
/* 1032 */         availentity = resourceid + "_" + availid;
/* 1033 */         entitylist.add(availentity);
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
/* 1047 */     Properties alert = getStatus(entitylist);
/* 1048 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1053 */     int size = monitorList.size();
/*      */     
/* 1055 */     String[] severity = new String[size];
/*      */     
/* 1057 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1059 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1060 */       String resourceName1 = (String)row1.get(7);
/* 1061 */       String resourceid1 = (String)row1.get(6);
/* 1062 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1063 */       if (severity[j] == null)
/*      */       {
/* 1065 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1069 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1071 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1073 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1076 */         if (sev > 0) {
/* 1077 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1078 */           monitorList.set(k, monitorList.get(j));
/* 1079 */           monitorList.set(j, t);
/* 1080 */           String temp = severity[k];
/* 1081 */           severity[k] = severity[j];
/* 1082 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1088 */     int z = 0;
/* 1089 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1092 */       int i = 0;
/* 1093 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1096 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1100 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1104 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1106 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1109 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1113 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1116 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1117 */       String resourceName1 = (String)row1.get(7);
/* 1118 */       String resourceid1 = (String)row1.get(6);
/* 1119 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1120 */       if (hseverity[j] == null)
/*      */       {
/* 1122 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1127 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1129 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1132 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1135 */         if (hsev > 0) {
/* 1136 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1137 */           monitorList.set(k, monitorList.get(j));
/* 1138 */           monitorList.set(j, t);
/* 1139 */           String temp1 = hseverity[k];
/* 1140 */           hseverity[k] = hseverity[j];
/* 1141 */           hseverity[j] = temp1;
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
/* 1153 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1154 */     boolean forInventory = false;
/* 1155 */     String trdisplay = "none";
/* 1156 */     String plusstyle = "inline";
/* 1157 */     String minusstyle = "none";
/* 1158 */     String haidTopLevel = "";
/* 1159 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1161 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1163 */         haidTopLevel = request.getParameter("haid");
/* 1164 */         forInventory = true;
/* 1165 */         trdisplay = "table-row;";
/* 1166 */         plusstyle = "none";
/* 1167 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1174 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1177 */     ArrayList listtoreturn = new ArrayList();
/* 1178 */     StringBuffer toreturn = new StringBuffer();
/* 1179 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1180 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1181 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1183 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1185 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1186 */       String childresid = (String)singlerow.get(0);
/* 1187 */       String childresname = (String)singlerow.get(1);
/* 1188 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1189 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1190 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1191 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1192 */       String unmanagestatus = (String)singlerow.get(5);
/* 1193 */       String actionstatus = (String)singlerow.get(6);
/* 1194 */       String linkclass = "monitorgp-links";
/* 1195 */       String titleforres = childresname;
/* 1196 */       String titilechildresname = childresname;
/* 1197 */       String childimg = "/images/trcont.png";
/* 1198 */       String flag = "enable";
/* 1199 */       String dcstarted = (String)singlerow.get(8);
/* 1200 */       String configMonitor = "";
/* 1201 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1202 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1204 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1206 */       if (singlerow.get(7) != null)
/*      */       {
/* 1208 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1210 */       String haiGroupType = "0";
/* 1211 */       if ("HAI".equals(childtype))
/*      */       {
/* 1213 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1215 */       childimg = "/images/trend.png";
/* 1216 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1217 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1218 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1220 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1222 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1224 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1225 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1228 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1230 */         linkclass = "disabledtext";
/* 1231 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1233 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String availmouseover = "";
/* 1235 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1237 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1239 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1240 */       String healthmouseover = "";
/* 1241 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1243 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1246 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1247 */       int spacing = 0;
/* 1248 */       if (level >= 1)
/*      */       {
/* 1250 */         spacing = 40 * level;
/*      */       }
/* 1252 */       if (childtype.equals("HAI"))
/*      */       {
/* 1254 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1255 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1256 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1258 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1259 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1260 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1261 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1262 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1263 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1264 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1265 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1266 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1267 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1268 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1270 */         if (!forInventory)
/*      */         {
/* 1272 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1275 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1277 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1279 */           actions = editlink + actions;
/*      */         }
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = actions + associatelink;
/*      */         }
/* 1285 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1286 */         String arrowimg = "";
/* 1287 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1289 */           actions = "";
/* 1290 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1291 */           checkbox = "";
/* 1292 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1294 */         if (isIt360)
/*      */         {
/* 1296 */           actionimg = "";
/* 1297 */           actions = "";
/* 1298 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1299 */           checkbox = "";
/*      */         }
/*      */         
/* 1302 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1304 */           actions = "";
/*      */         }
/* 1306 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1308 */           checkbox = "";
/*      */         }
/*      */         
/* 1311 */         String resourcelink = "";
/*      */         
/* 1313 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1315 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1322 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1328 */         if (!isIt360)
/*      */         {
/* 1330 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1337 */         toreturn.append("</tr>");
/* 1338 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1340 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1341 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1346 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1349 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1353 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1355 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1357 */             toreturn.append(assocMessage);
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1367 */         String resourcelink = null;
/* 1368 */         boolean hideEditLink = false;
/* 1369 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1371 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1372 */           hideEditLink = true;
/* 1373 */           if (isIt360)
/*      */           {
/* 1375 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1379 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1381 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1383 */           hideEditLink = true;
/* 1384 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1385 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1390 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1393 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1394 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1395 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1396 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1397 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1398 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1399 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1400 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1401 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1402 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1403 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1404 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1405 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1407 */         if (hideEditLink)
/*      */         {
/* 1409 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1411 */         if (!forInventory)
/*      */         {
/* 1413 */           removefromgroup = "";
/*      */         }
/* 1415 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1416 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1417 */           actions = actions + configcustomfields;
/*      */         }
/* 1419 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1421 */           actions = editlink + actions;
/*      */         }
/* 1423 */         String managedLink = "";
/* 1424 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1426 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1427 */           actions = "";
/* 1428 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1429 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1432 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1434 */           checkbox = "";
/*      */         }
/*      */         
/* 1437 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1439 */           actions = "";
/*      */         }
/* 1441 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1444 */         if (isIt360)
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1454 */         if (!isIt360)
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1462 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1465 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1472 */       StringBuilder toreturn = new StringBuilder();
/* 1473 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1474 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1475 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1476 */       String title = "";
/* 1477 */       message = EnterpriseUtil.decodeString(message);
/* 1478 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1479 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1480 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1482 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1484 */       else if ("5".equals(severity))
/*      */       {
/* 1486 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1492 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1493 */       toreturn.append(v);
/*      */       
/* 1495 */       toreturn.append(link);
/* 1496 */       if (severity == null)
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("5"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("4"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("1"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1517 */       toreturn.append("</a>");
/* 1518 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1522 */       ex.printStackTrace();
/*      */     }
/* 1524 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1531 */       StringBuilder toreturn = new StringBuilder();
/* 1532 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1533 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1534 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1535 */       if (message == null)
/*      */       {
/* 1537 */         message = "";
/*      */       }
/*      */       
/* 1540 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1541 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1543 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1544 */       toreturn.append(v);
/*      */       
/* 1546 */       toreturn.append(link);
/*      */       
/* 1548 */       if (severity == null)
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1552 */       else if (severity.equals("5"))
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("1"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1565 */       toreturn.append("</a>");
/* 1566 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1572 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1575 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1576 */     if (invokeActions != null) {
/* 1577 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1578 */       while (iterator.hasNext()) {
/* 1579 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1580 */         if (actionmap.containsKey(actionid)) {
/* 1581 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1586 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1590 */     String actionLink = "";
/* 1591 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1592 */     String query = "";
/* 1593 */     ResultSet rs = null;
/* 1594 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1595 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1596 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1597 */       actionLink = "method=" + methodName;
/*      */     }
/* 1599 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1600 */       actionLink = methodName;
/*      */     }
/* 1602 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1603 */     Iterator itr = methodarglist.iterator();
/* 1604 */     boolean isfirstparam = true;
/* 1605 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1606 */     while (itr.hasNext()) {
/* 1607 */       HashMap argmap = (HashMap)itr.next();
/* 1608 */       String argtype = (String)argmap.get("TYPE");
/* 1609 */       String argname = (String)argmap.get("IDENTITY");
/* 1610 */       String paramname = (String)argmap.get("PARAMETER");
/* 1611 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1612 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1613 */         isfirstparam = false;
/* 1614 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1616 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1620 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1624 */         actionLink = actionLink + "&";
/*      */       }
/* 1626 */       String paramValue = null;
/* 1627 */       String tempargname = argname;
/* 1628 */       if (commonValues.getProperty(tempargname) != null) {
/* 1629 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1632 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1633 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1634 */           if (dbType.equals("mysql")) {
/* 1635 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1638 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1640 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1642 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1643 */             if (rs.next()) {
/* 1644 */               paramValue = rs.getString("VALUE");
/* 1645 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1649 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1653 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1656 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1661 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1662 */           paramValue = rowId;
/*      */         }
/* 1664 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1665 */           paramValue = managedObjectName;
/*      */         }
/* 1667 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1668 */           paramValue = resID;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1671 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1674 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1676 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1677 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1678 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1680 */     return actionLink;
/*      */   }
/*      */   
/* 1683 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1684 */     String dependentAttribute = null;
/* 1685 */     String align = "left";
/*      */     
/* 1687 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1688 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1689 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1690 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1691 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1692 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1693 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1694 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1695 */       align = "center";
/*      */     }
/*      */     
/* 1698 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1699 */     String actualdata = "";
/*      */     
/* 1701 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1702 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1703 */         actualdata = availValue;
/*      */       }
/* 1705 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1706 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1710 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1711 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1714 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1720 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1721 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1722 */       toreturn.append("<table>");
/* 1723 */       toreturn.append("<tr>");
/* 1724 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1725 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1726 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1727 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1728 */         String toolTip = "";
/* 1729 */         String hideClass = "";
/* 1730 */         String textStyle = "";
/* 1731 */         boolean isreferenced = true;
/* 1732 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1733 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1734 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1735 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1737 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1738 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1739 */           while (valueList.hasMoreTokens()) {
/* 1740 */             String dependentVal = valueList.nextToken();
/* 1741 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1742 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1743 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1745 */               toolTip = "";
/* 1746 */               hideClass = "";
/* 1747 */               isreferenced = false;
/* 1748 */               textStyle = "disabledtext";
/* 1749 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1753 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1754 */           toolTip = "";
/* 1755 */           hideClass = "";
/* 1756 */           isreferenced = false;
/* 1757 */           textStyle = "disabledtext";
/* 1758 */           if (dependentImageMap != null) {
/* 1759 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1760 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1763 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1767 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1768 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1769 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1770 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1771 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1772 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1774 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1775 */           if (isreferenced) {
/* 1776 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1780 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1781 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1782 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1783 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1784 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1785 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1787 */           toreturn.append("</span>");
/* 1788 */           toreturn.append("</a>");
/* 1789 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1792 */       toreturn.append("</tr>");
/* 1793 */       toreturn.append("</table>");
/* 1794 */       toreturn.append("</td>");
/*      */     } else {
/* 1796 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1799 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1803 */     String colTime = null;
/* 1804 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1805 */     if ((rows != null) && (rows.size() > 0)) {
/* 1806 */       Iterator<String> itr = rows.iterator();
/* 1807 */       String maxColQuery = "";
/* 1808 */       for (;;) { if (itr.hasNext()) {
/* 1809 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1810 */           ResultSet maxCol = null;
/*      */           try {
/* 1812 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1813 */             while (maxCol.next()) {
/* 1814 */               if (colTime == null) {
/* 1815 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1818 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1827 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1827 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1837 */     return colTime;
/*      */   }
/*      */   
/* 1840 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1841 */     tablename = null;
/* 1842 */     ResultSet rsTable = null;
/* 1843 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1845 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1846 */       while (rsTable.next()) {
/* 1847 */         tablename = rsTable.getString("DATATABLE");
/* 1848 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1849 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1862 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1853 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1856 */         if (rsTable != null)
/* 1857 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1859 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1865 */     String argsList = "";
/* 1866 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1868 */       if (showArgsMap.get(row) != null) {
/* 1869 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1870 */         if (showArgslist != null) {
/* 1871 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1872 */             if (argsList.trim().equals("")) {
/* 1873 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1876 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1883 */       e.printStackTrace();
/* 1884 */       return "";
/*      */     }
/* 1886 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1891 */     String argsList = "";
/* 1892 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1895 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1897 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1898 */         if (hideArgsList != null)
/*      */         {
/* 1900 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1902 */             if (argsList.trim().equals(""))
/*      */             {
/* 1904 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1908 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1916 */       ex.printStackTrace();
/*      */     }
/* 1918 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1922 */     StringBuilder toreturn = new StringBuilder();
/* 1923 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1930 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1931 */       Iterator itr = tActionList.iterator();
/* 1932 */       while (itr.hasNext()) {
/* 1933 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1934 */         String confirmmsg = "";
/* 1935 */         String link = "";
/* 1936 */         String isJSP = "NO";
/* 1937 */         HashMap tactionMap = (HashMap)itr.next();
/* 1938 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1939 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1940 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1941 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1942 */           (actionmap.containsKey(actionId))) {
/* 1943 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1944 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1945 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1946 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1947 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1949 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1955 */           if (isTableAction) {
/* 1956 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1959 */             tableName = "Link";
/* 1960 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1961 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1962 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1963 */             toreturn.append("</a></td>");
/*      */           }
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1974 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1980 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1982 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1983 */       Properties prop = (Properties)node.getUserObject();
/* 1984 */       String mgID = prop.getProperty("label");
/* 1985 */       String mgName = prop.getProperty("value");
/* 1986 */       String isParent = prop.getProperty("isParent");
/* 1987 */       int mgIDint = Integer.parseInt(mgID);
/* 1988 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1990 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1992 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1993 */       if (node.getChildCount() > 0)
/*      */       {
/* 1995 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1997 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1999 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2001 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2010 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2012 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2016 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2023 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2024 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2026 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2030 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2032 */       if (node.getChildCount() > 0)
/*      */       {
/* 2034 */         builder.append("<UL>");
/* 2035 */         printMGTree(node, builder);
/* 2036 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2041 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2042 */     StringBuffer toReturn = new StringBuffer();
/* 2043 */     String table = "-";
/*      */     try {
/* 2045 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2046 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2047 */       float total = 0.0F;
/* 2048 */       while (it.hasNext()) {
/* 2049 */         String attName = (String)it.next();
/* 2050 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2051 */         boolean roundOffData = false;
/* 2052 */         if ((data != null) && (!data.equals(""))) {
/* 2053 */           if (data.indexOf(",") != -1) {
/* 2054 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2057 */             float value = Float.parseFloat(data);
/* 2058 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2061 */             total += value;
/* 2062 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2065 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2070 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2071 */       while (attVsWidthList.hasNext()) {
/* 2072 */         String attName = (String)attVsWidthList.next();
/* 2073 */         String data = (String)attVsWidthProps.get(attName);
/* 2074 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2075 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2076 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2077 */         String className = (String)graphDetails.get("ClassName");
/* 2078 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2079 */         if (percentage < 1.0F)
/*      */         {
/* 2081 */           data = percentage + "";
/*      */         }
/* 2083 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2085 */       if (toReturn.length() > 0) {
/* 2086 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2090 */       e.printStackTrace();
/*      */     }
/* 2092 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2098 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2099 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2100 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2101 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2102 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2103 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2104 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2105 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2106 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2109 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2110 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2111 */       splitvalues[0] = multiplecondition.toString();
/* 2112 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2115 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2120 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2121 */     if (thresholdType != 3) {
/* 2122 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2123 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2124 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2125 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2126 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2127 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2129 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2130 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2131 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2132 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2133 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2134 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2136 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2137 */     if (updateSelected != null) {
/* 2138 */       updateSelected[0] = "selected";
/*      */     }
/* 2140 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2145 */       StringBuffer toreturn = new StringBuffer("");
/* 2146 */       if (commaSeparatedMsgId != null) {
/* 2147 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2148 */         int count = 0;
/* 2149 */         while (msgids.hasMoreTokens()) {
/* 2150 */           String id = msgids.nextToken();
/* 2151 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2152 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2153 */           count++;
/* 2154 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2155 */             if (toreturn.length() == 0) {
/* 2156 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2158 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2159 */             if (!image.trim().equals("")) {
/* 2160 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2162 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2163 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2166 */         if (toreturn.length() > 0) {
/* 2167 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2171 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2174 */       e.printStackTrace(); }
/* 2175 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   String[] supportedTypes = com.adventnet.appmanager.client.views.ViewsCreator.supportedTypes;
/* 2183 */   String[] supportedTypesDisplayName = com.adventnet.appmanager.client.views.ViewsCreator.supportedTypesDisplayName;
/* 2184 */   int positionOfServiceName = 0;
/* 2185 */   int positionOfServiceType = 1;
/* 2186 */   int positionOfServiceAvailabilityImg = 2;
/* 2187 */   int positionOfResourceName = 3;
/* 2188 */   int positionOfResourceTypeImg = 4;
/* 2189 */   int positionOfResourceAvailabilityImg = 5;
/* 2190 */   int positionOfResourceID = 6;
/* 2191 */   int positionOfServiceID = 7;
/* 2192 */   int positionOfResourceAvaiToootip = 8;
/* 2193 */   int positionOfServiceAvaiToootip = 9;
/* 2194 */   int positionOfResourceType = 10;
/*      */   
/*      */ 
/*      */   public String getCellTagsForService(List serviceNames, int index)
/*      */   {
/* 2199 */     String[] oneEleData = null;
/*      */     try
/*      */     {
/* 2202 */       oneEleData = (String[])serviceNames.get(index);
/* 2203 */       return "<td height=\"15\" valign=\"top\" title=\"" + oneEleData[this.positionOfServiceName] + "\"><img title=\"" + oneEleData[this.positionOfServiceAvaiToootip] + "\" src=\"../images/" + oneEleData[this.positionOfServiceAvailabilityImg] + "\" width=\"8\" height=\"8\" hspace=\"5\"><a href=\"/showresource.do?resourceid=" + oneEleData[this.positionOfServiceID] + "&method=showResourceForResourceID&view=icons&viewType=showIconsView\" class=\"arial9\">" + oneEleData[this.positionOfServiceType] + "</a></td>";
/*      */     }
/*      */     catch (IndexOutOfBoundsException io) {}
/*      */     
/* 2207 */     return "<td height=\"15\"> </td>";
/*      */   }
/*      */   
/*      */ 
/* 2211 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2217 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2218 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2219 */     _jspx_dependants.put("/jsp/includes/monitors_setasdefault.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2240 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2258 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2262 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2263 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2264 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2265 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/* 2267 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname.release();
/* 2269 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2270 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2271 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2272 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2273 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2281 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2284 */     JspWriter out = null;
/* 2285 */     Object page = this;
/* 2286 */     JspWriter _jspx_out = null;
/* 2287 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2291 */       response.setContentType("text/html;charset=UTF-8");
/* 2292 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2294 */       _jspx_page_context = pageContext;
/* 2295 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2296 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2297 */       session = pageContext.getSession();
/* 2298 */       out = pageContext.getOut();
/* 2299 */       _jspx_out = out;
/*      */       
/* 2301 */       out.write("<!DOCTYPE html>\n");
/* 2302 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2303 */       out.write("\n<html>\n<head>\n\n\n\n\n\n\n\n");
/* 2304 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2306 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2307 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2308 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2310 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2312 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2314 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2316 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2317 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2318 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2319 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2322 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2323 */         String available = null;
/* 2324 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2325 */         out.write(10);
/*      */         
/* 2327 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2328 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2329 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2331 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2333 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2335 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2337 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2338 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2339 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2340 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2343 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2344 */           String unavailable = null;
/* 2345 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2346 */           out.write(10);
/*      */           
/* 2348 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2349 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2350 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2352 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2354 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2356 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2358 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2359 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2360 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2361 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2364 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2365 */             String unmanaged = null;
/* 2366 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2367 */             out.write(10);
/*      */             
/* 2369 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2370 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2371 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2373 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2375 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2377 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2379 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2380 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2381 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2382 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2385 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2386 */               String scheduled = null;
/* 2387 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2388 */               out.write(10);
/*      */               
/* 2390 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2391 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2400 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2401 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2402 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2403 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2406 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2407 */                 String critical = null;
/* 2408 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2409 */                 out.write(10);
/*      */                 
/* 2411 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2412 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2413 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2415 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2417 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2419 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2421 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2422 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2423 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2424 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2427 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2428 */                   String clear = null;
/* 2429 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2430 */                   out.write(10);
/*      */                   
/* 2432 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2433 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2434 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2436 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2438 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2440 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2442 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2443 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2444 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2445 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2448 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2449 */                     String warning = null;
/* 2450 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2451 */                     out.write(10);
/* 2452 */                     out.write(10);
/*      */                     
/* 2454 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2455 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2457 */                     out.write(10);
/* 2458 */                     out.write(10);
/* 2459 */                     out.write(10);
/* 2460 */                     out.write(10);
/* 2461 */                     out.write(10);
/*      */                     
/* 2463 */                     request.setAttribute("HelpKey", "Monitors Page");
/*      */                     
/* 2465 */                     out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n</head>\n\n\n\n<script>\n\nfunction deleteSystem(resid)\n{\n\n\t");
/* 2466 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2468 */                     out.write(10);
/* 2469 */                     out.write(9);
/*      */                     
/* 2471 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2472 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2473 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2475 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2476 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2477 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2479 */                         out.write(" \n\tif(confirm(\"");
/* 2480 */                         out.print(FormatUtil.getString("am.monitortab.iconview.jsalert.text"));
/* 2481 */                         out.write("\"))\n\t{\n            window.location.href =\"/deleteMO.do?fromiconview=true&method=deleteMO&type=SYS&select=\" + resid;\n\t}\n\t");
/* 2482 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2483 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2487 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2488 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2491 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2492 */                       out.write("\n}\n</script>\n\n\n");
/*      */                       
/* 2494 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2495 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2496 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2498 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2499 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2500 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2502 */                           out.write(10);
/* 2503 */                           out.write(10);
/*      */                           
/* 2505 */                           String actionPath1 = "/showresource.do?method=showIconsView";
/* 2506 */                           String oldtab = request.getParameter("oldtab");
/*      */                           
/* 2508 */                           String tileName = request.getParameter("quickLinks");
/* 2509 */                           String type = request.getParameter("type");
/* 2510 */                           List dataModel = (List)request.getAttribute("mapmodel");
/* 2511 */                           boolean systemsPresent = (dataModel != null) && (dataModel.size() > 0);
/* 2512 */                           String network = request.getParameter("selectedNetwork");
/* 2513 */                           String leftPage = "/jsp/MapsLeftPage.jsp?method=showIconsView";
/* 2514 */                           String title = " " + FormatUtil.getString("am.webclient.monitor.networkview.title");
/*      */                           
/* 2516 */                           String actionPath = actionPath1 + "&type=" + type + "&quicklinks=" + tileName;
/*      */                           
/* 2518 */                           if (oldtab != null)
/*      */                           {
/* 2520 */                             actionPath = actionPath + "&oldtab=" + oldtab;
/*      */                           }
/*      */                           
/* 2523 */                           String title1 = title;
/* 2524 */                           String toAppendLink = "";
/* 2525 */                           if (network != null)
/*      */                           {
/* 2527 */                             title = network + title;
/* 2528 */                             title1 = title;
/* 2529 */                             toAppendLink = "&selectedNetwork=" + network;
/* 2530 */                             leftPage = leftPage + toAppendLink;
/*      */                           }
/*      */                           else
/*      */                           {
/* 2534 */                             title = title;
/* 2535 */                             title1 = title;
/*      */                           }
/* 2537 */                           if (systemsPresent)
/*      */                           {
/* 2539 */                             title = title + " <span class=bodytext>(" + FormatUtil.getString("am.monitortab.total.text") + " <b>" + dataModel.size() + "</b> " + FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers") + ")</span>";
/*      */                           }
/*      */                           
/* 2542 */                           request.setAttribute("defaultview", "showIconsView");
/*      */                           
/* 2544 */                           String query = null;
/* 2545 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/* 2547 */                             Vector resIdsVec = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.filterResourceIds(request);
/* 2548 */                             System.out.println("resIdsVec in categoryview:" + resIdsVec);
/* 2549 */                             String condition = com.adventnet.appmanager.reporting.ReportUtilities.getCondition("MO.RESOURCEID", resIdsVec);
/*      */                             
/* 2551 */                             if ((request.isUserInRole("OPERATOR")) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */                             {
/* 2553 */                               if ((type != null) && (type.equals("APPS")))
/*      */                               {
/* 2555 */                                 query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + com.adventnet.appmanager.util.Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' and " + condition;
/*      */                               }
/* 2557 */                               else if ((type != null) && (type.equals("SYS")))
/*      */                               {
/* 2559 */                                 query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + com.adventnet.appmanager.util.Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' and mo.TYPE not in ('VMWare ESX/ESXi','Hyper-V-Server') and " + condition;
/*      */                               }
/*      */                               else
/*      */                               {
/* 2563 */                                 query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt, Externaldevicedetails edd where mo.TYPE=mrt.RESOURCETYPE and mo.RESOURCENAME=edd.NAME and mrt.SUBGROUP like 'OpManager%' and mrt.SUBGROUP not like 'OpManager-Interface%' and " + condition;
/*      */                               }
/*      */                               
/*      */ 
/*      */                             }
/* 2568 */                             else if ((type != null) && (type.equals("APPS")))
/*      */                             {
/* 2570 */                               query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + com.adventnet.appmanager.util.Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' ";
/*      */                             }
/* 2572 */                             else if ((type != null) && (type.equals("SYS")))
/*      */                             {
/* 2574 */                               query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + com.adventnet.appmanager.util.Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' and mo.TYPE not in ('VMWare ESX/ESXi','Hyper-V-Server')";
/*      */                             }
/*      */                             else
/*      */                             {
/* 2578 */                               query = "select count(*) from AM_ManagedObject mo,AM_ManagedResourceType mrt, Externaldevicedetails edd where mo.TYPE=mrt.RESOURCETYPE and mo.RESOURCENAME=edd.NAME and mrt.SUBGROUP like 'OpManager%' and mrt.SUBGROUP not like 'OpManager-Interface%' ";
/*      */                             }
/*      */                             
/* 2581 */                             System.out.println("query in NetworkView  : " + query);
/*      */                           }
/*      */                           
/*      */ 
/* 2585 */                           ResultSet rss = null;
/* 2586 */                           int noOfObjects = 0;
/*      */                           
/* 2588 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/*      */                             try
/*      */                             {
/* 2592 */                               rss = AMConnectionPool.executeQueryStmt(query);
/*      */                               
/* 2594 */                               if (rss.next())
/*      */                               {
/* 2596 */                                 noOfObjects += Integer.parseInt(rss.getString(1));
/*      */                               }
/*      */                             }
/*      */                             catch (Exception e)
/*      */                             {
/* 2601 */                               e.printStackTrace();
/*      */                             }
/*      */                             finally
/*      */                             {
/* 2605 */                               AMConnectionPool.closeStatement(rss);
/*      */                             }
/*      */                           }
/*      */                           
/*      */ 
/* 2610 */                           out.write(10);
/* 2611 */                           out.write(10);
/* 2612 */                           if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2614 */                           out.write("     \n");
/*      */                           
/* 2616 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2617 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2618 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2620 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                           
/* 2622 */                           _jspx_th_tiles_005fput_005f0.setValue(title1);
/* 2623 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2624 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2625 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                           }
/*      */                           
/* 2628 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2629 */                           out.write(32);
/* 2630 */                           out.write(10);
/* 2631 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2633 */                           out.write(32);
/* 2634 */                           out.write(10);
/*      */                           
/* 2636 */                           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname.get(PutTag.class);
/* 2637 */                           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2638 */                           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2640 */                           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                           
/* 2642 */                           _jspx_th_tiles_005fput_005f2.setValue(leftPage);
/* 2643 */                           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2644 */                           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2645 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2646 */                               out = _jspx_page_context.pushBody();
/* 2647 */                               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2648 */                               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2651 */                               out.write(32);
/* 2652 */                               out.write(10);
/* 2653 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2654 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2657 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2658 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2661 */                           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2662 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                           }
/*      */                           
/* 2665 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2666 */                           out.write(32);
/* 2667 */                           out.write(10);
/*      */                           
/* 2669 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2670 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2671 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2673 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2675 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2676 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2677 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2678 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2679 */                               out = _jspx_page_context.pushBody();
/* 2680 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2681 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2684 */                               out.write(32);
/* 2685 */                               out.write(10);
/* 2686 */                               out.write("<!--$Id$-->\n\n<script>\nvar urlredirect = new Array();\nurlredirect[0] = '/showresource.do?method=showResourceTypesAll&group=All");
/* 2687 */                               out.print(toAppendLink);
/* 2688 */                               out.write("';\n</script>\n");
/*      */                               
/* 2690 */                               if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2691 */                                 out.write("\n  <script>\n     urlredirect[0]='showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server");
/* 2692 */                                 out.print(toAppendLink);
/* 2693 */                                 out.write("';\n </script>\n ");
/*      */                               }
/*      */                               
/* 2696 */                               out.write("\n <script>\nurlredirect[1] = '/showresource.do?method=showResourceTypes");
/* 2697 */                               out.print(toAppendLink);
/* 2698 */                               out.write("&monitor_viewtype=categoryview';\nurlredirect[2] = '/showresource.do?method=showPlasmaView';\nurlredirect[3] = '/showresource.do?method=showMonitorGroupView';\nurlredirect[4] = '/showresource.do?method=showGMapView&group=All");
/* 2699 */                               out.print(toAppendLink);
/* 2700 */                               out.write("';\nurlredirect[5] = '/showresource.do?method=showIconsView");
/* 2701 */                               out.print(toAppendLink);
/* 2702 */                               out.write("';\nurlredirect[6] = '/showresource.do?method=showDetailsView");
/* 2703 */                               out.print(toAppendLink);
/* 2704 */                               out.write("';\n\n</script>\n\n\n\n\n");
/*      */                               
/* 2706 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2707 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2708 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2710 */                               _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */                               
/* 2712 */                               _jspx_th_html_005fform_005f0.setStyle("display :inline");
/* 2713 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2714 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2716 */                                   out.write(10);
/* 2717 */                                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2719 */                                   out.write(10);
/* 2720 */                                   if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2722 */                                   out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"62%\" height=\"35\" class=\"monitorsheading\">\n      <table width=\"100%\">\n      <tr>\n        <td  height=\"35\"   width=\"70%\" class=\"monitorsheading\"> ");
/* 2723 */                                   out.print(title);
/* 2724 */                                   out.write(" </td>\n        <td  height=\"35\" class=\"monitorsheading\"  style=\"padding-left:2px\">\n    ");
/* 2725 */                                   String CategoryViewtype = (String)request.getAttribute("categorytype");
/* 2726 */                                   if (CategoryViewtype == null) {
/* 2727 */                                     CategoryViewtype = "";
/*      */                                   }
/* 2729 */                                   String monitorviewtype = (String)request.getAttribute("monitor_viewtype");
/* 2730 */                                   if (monitorviewtype == null) {
/* 2731 */                                     monitorviewtype = "";
/*      */                                   }
/* 2733 */                                   if (monitorviewtype.startsWith("CategoryView")) {
/* 2734 */                                     if (CategoryViewtype.equals("added monitors"))
/*      */                                     {
/* 2736 */                                       out.write("          <a  href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\"  class=\"staticlinks\" onclick=\"javascript:setCookieval('showAddedMonitors');\">");
/* 2737 */                                       out.print(FormatUtil.getString("am.monitortab.category.AddedMonitors.text"));
/* 2738 */                                       out.write("</a>\n  ");
/*      */ 
/*      */                                     }
/* 2741 */                                     else if (CategoryViewtype.equals("all monitors"))
/*      */                                     {
/* 2743 */                                       out.write("            <a href=\"/showresource.do?method=showResourceTypes\"   class=\"staticlinks\" onclick=\"javascript:setCookieval('showAllMonitors');\">");
/* 2744 */                                       out.print(FormatUtil.getString("am.monitortab.category.AllMonitors.text"));
/* 2745 */                                       out.write("</a>\n\n  ");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 2750 */                                   out.write("\n        </td>\n      </tr>\n      </table>\n    </td>\n    ");
/*      */                                   
/* 2752 */                                   String tempStl = "center";
/* 2753 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                                   {
/* 2755 */                                     tempStl = "right";
/*      */                                     
/* 2757 */                                     out.write("\n      <td align=\"right\" width=\"30%\" class=\"bodytext\" style=\"white-space:nowrap;\">\n      ");
/* 2758 */                                     if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin()) {
/* 2759 */                                       out.write("\n      ");
/*      */                                       
/* 2761 */                                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2762 */                                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2763 */                                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 2765 */                                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2766 */                                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2767 */                                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                         for (;;) {
/* 2769 */                                           out.write("\n        <span id=\"createNewMG\">");
/* 2770 */                                           out.print(FormatUtil.getString("am.monitortab.creategroup.text"));
/* 2771 */                                           out.write(" :  &nbsp;</span>\n        <select id=\"createMG\" onchange=\"javascript:changeMGURL(this)\" styleClass=\"formtext\" style=\"margin-right: 30px;display:none;\">\n          <option value=\"createNewMG\" selected>");
/* 2772 */                                           out.print(FormatUtil.getString("am.monitortab.selectgrouptype.text"));
/* 2773 */                                           out.write("</option>\n          <option value=\"1\">");
/* 2774 */                                           out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 2775 */                                           out.write("</option>\n          <option value=\"2\">");
/* 2776 */                                           out.print(FormatUtil.getString("am.webclient.mg.type.webappgroup"));
/* 2777 */                                           out.write("</option>\n          <option value=\"3\">");
/* 2778 */                                           out.print(FormatUtil.getString("am.webclient.mg.type.vcenter"));
/* 2779 */                                           out.write("</option>\n        </select>\n      ");
/* 2780 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2781 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2785 */                                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2786 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                       }
/*      */                                       
/* 2789 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2790 */                                       out.write("\n     ");
/*      */                                     }
/* 2792 */                                     out.write("\n\n      ");
/* 2793 */                                     out.print(FormatUtil.getString("am.monitortab.selectview.text"));
/* 2794 */                                     out.write(" :  &nbsp;\n\n      ");
/*      */                                     
/* 2796 */                                     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2797 */                                     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2798 */                                     _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 2800 */                                     _jspx_th_html_005fselect_005f0.setProperty("defaultmonitorsview");
/*      */                                     
/* 2802 */                                     _jspx_th_html_005fselect_005f0.setOnchange("javascript:changeUrl(this)");
/*      */                                     
/* 2804 */                                     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/* 2805 */                                     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2806 */                                     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2807 */                                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2808 */                                         out = _jspx_page_context.pushBody();
/* 2809 */                                         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2810 */                                         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2813 */                                         out.write("\n        ");
/*      */                                         
/* 2815 */                                         OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2816 */                                         _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2817 */                                         _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 2819 */                                         _jspx_th_html_005foption_005f0.setValue("showResourceTypesAll");
/* 2820 */                                         int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2821 */                                         if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2822 */                                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2823 */                                             out = _jspx_page_context.pushBody();
/* 2824 */                                             _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2825 */                                             _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2828 */                                             out.print(FormatUtil.getString("am.monitortab.bulkconfiguration.text"));
/* 2829 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2830 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2833 */                                           if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2834 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2837 */                                         if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2838 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                         }
/*      */                                         
/* 2841 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2842 */                                         out.write("\n        ");
/*      */                                         
/* 2844 */                                         OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2845 */                                         _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2846 */                                         _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 2848 */                                         _jspx_th_html_005foption_005f1.setValue("showResourceTypes");
/* 2849 */                                         int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2850 */                                         if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2851 */                                           if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2852 */                                             out = _jspx_page_context.pushBody();
/* 2853 */                                             _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2854 */                                             _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2857 */                                             out.print(FormatUtil.getString("am.monitortab.category.text"));
/* 2858 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2859 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2862 */                                           if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2863 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2866 */                                         if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2867 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                         }
/*      */                                         
/* 2870 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2871 */                                         out.write("\n        ");
/*      */                                         
/* 2873 */                                         OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2874 */                                         _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2875 */                                         _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 2877 */                                         _jspx_th_html_005foption_005f2.setValue("plasmaView");
/* 2878 */                                         int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2879 */                                         if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2880 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2881 */                                             out = _jspx_page_context.pushBody();
/* 2882 */                                             _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2883 */                                             _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 2886 */                                             out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 2887 */                                             int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2888 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 2891 */                                           if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2892 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 2895 */                                         if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2896 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                         }
/*      */                                         
/* 2899 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2900 */                                         out.write("\n        ");
/*      */                                         
/* 2902 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2903 */                                         _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2904 */                                         _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                         
/* 2906 */                                         _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 2907 */                                         int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2908 */                                         if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                           for (;;) {
/* 2910 */                                             out.write("\n        ");
/*      */                                             
/* 2912 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2913 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2914 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                             
/* 2916 */                                             _jspx_th_html_005foption_005f3.setValue("showMonitorGroupView");
/* 2917 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2918 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2919 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2920 */                                                 out = _jspx_page_context.pushBody();
/* 2921 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2922 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 2925 */                                                 out.print(FormatUtil.getString("am.monitortab.monitorgroupview.text"));
/* 2926 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2927 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 2930 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2931 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 2934 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2935 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 2938 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2939 */                                             out.write("\n        ");
/*      */                                             
/* 2941 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2942 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2943 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                             
/* 2945 */                                             _jspx_th_html_005foption_005f4.setValue("showGMapView");
/* 2946 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2947 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2948 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2949 */                                                 out = _jspx_page_context.pushBody();
/* 2950 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2951 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 2954 */                                                 out.print(FormatUtil.getString("am.monitortab.gmap.text"));
/* 2955 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2956 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 2959 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2960 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 2963 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2964 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 2967 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2968 */                                             out.write("\n        ");
/* 2969 */                                             if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2970 */                                               out.write("\n        ");
/*      */                                               
/* 2972 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2973 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2974 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                               
/* 2976 */                                               _jspx_th_html_005foption_005f5.setValue("showIconsView");
/* 2977 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2978 */                                               if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2979 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2980 */                                                   out = _jspx_page_context.pushBody();
/* 2981 */                                                   _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2982 */                                                   _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 2985 */                                                   out.print(FormatUtil.getString("am.monitortab.icons.text"));
/* 2986 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2987 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 2990 */                                                 if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2991 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 2994 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2995 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 2998 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2999 */                                               out.write("\n        ");
/*      */                                               
/* 3001 */                                               OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3002 */                                               _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3003 */                                               _jspx_th_html_005foption_005f6.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                               
/* 3005 */                                               _jspx_th_html_005foption_005f6.setValue("showDetailsView");
/* 3006 */                                               int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3007 */                                               if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3008 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3009 */                                                   out = _jspx_page_context.pushBody();
/* 3010 */                                                   _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3011 */                                                   _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 3014 */                                                   out.print(FormatUtil.getString("am.monitortab.table.text"));
/* 3015 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3016 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 3019 */                                                 if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3020 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 3023 */                                               if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3024 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                               }
/*      */                                               
/* 3027 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3028 */                                               out.write("\n        ");
/*      */                                             }
/* 3030 */                                             out.write("\n        ");
/*      */                                             
/* 3032 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3033 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3034 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                             
/* 3036 */                                             _jspx_th_html_005foption_005f7.setValue("showFlashView");
/* 3037 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3038 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3039 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3040 */                                                 out = _jspx_page_context.pushBody();
/* 3041 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3042 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3045 */                                                 out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/* 3046 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3047 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3050 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3051 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3054 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3055 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 3058 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3059 */                                             out.write("\n        ");
/* 3060 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3061 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3065 */                                         if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3066 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                         }
/*      */                                         
/* 3069 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3070 */                                         out.write("\n      ");
/* 3071 */                                         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3072 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3075 */                                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3076 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3079 */                                     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3080 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                     }
/*      */                                     
/* 3083 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3084 */                                     out.write("\n\n      ");
/* 3085 */                                     if (!com.manageengine.appmanager.plugin.PluginUtil.isPlugin()) {
/* 3086 */                                       out.write("\n      <span class=\"bodytext\">\n        ");
/*      */                                       
/* 3088 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3089 */                                       _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3090 */                                       _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 3092 */                                       _jspx_th_logic_005fnotPresent_005f2.setRole("OPERATOR");
/* 3093 */                                       int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3094 */                                       if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                         for (;;) {
/* 3096 */                                           out.write("\n          ");
/*      */                                           
/* 3098 */                                           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3099 */                                           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3100 */                                           _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                           
/* 3102 */                                           _jspx_th_c_005fif_005f0.setTest("${globalconfig['defaultmonitorsview'] != requestScope.defaultview}");
/* 3103 */                                           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3104 */                                           if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                             for (;;) {
/* 3106 */                                               out.write("\n            <input type=hidden name=\"method\" value=\"setDefaultMonitorsView\">\n        <a href=\"javascript:setMonitorsViewDefault()\" class=\"new-monitordiv-link\">");
/* 3107 */                                               out.print(FormatUtil.getString("am.monitortab.setasdefaultview.text"));
/* 3108 */                                               out.write(" </a>\n          ");
/* 3109 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3110 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3114 */                                           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3115 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                           }
/*      */                                           
/* 3118 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3119 */                                           out.write("\n        ");
/* 3120 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3121 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3125 */                                       if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3126 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                       }
/*      */                                       
/* 3129 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3130 */                                       out.write("\n      </span>\n      ");
/*      */                                     }
/* 3132 */                                     out.write("\n      </td>\n      ");
/*      */                                   }
/*      */                                   
/* 3135 */                                   out.write("\n      ");
/*      */                                   
/* 3137 */                                   String location = (String)pageContext.getAttribute("setdefaultlocation");
/* 3138 */                                   if ((location != null) && (location.equals("Googleview")) && (request.getAttribute("key_set") != null) && (request.getAttribute("key_set").equals("true")))
/*      */                                   {
/* 3140 */                                     out.write("\n      <td colspan=\"2\" align=\"");
/* 3141 */                                     out.print(tempStl);
/* 3142 */                                     out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 3143 */                                     out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" class=\"staticlinks\">");
/* 3144 */                                     out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 3145 */                                     out.write("</a>\n       </span>\n\t  </td> \n      ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3149 */                                   out.write("\n      ");
/*      */                                   
/* 3151 */                                   IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3152 */                                   _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3153 */                                   _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3155 */                                   _jspx_th_c_005fif_005f1.setTest("${AMActionForm.showMapView == true}");
/* 3156 */                                   int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3157 */                                   if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                     for (;;) {
/* 3159 */                                       out.write("\n      <td colspan=\"2\" align=\"");
/* 3160 */                                       out.print(tempStl);
/* 3161 */                                       out.write("\" class=\"bodytext tdindent\" nowrap=\"nowrap\">  ");
/* 3162 */                                       out.write("\n\t   <span class=\"bodytext\">\n        &nbsp;<a href=\"javascript:setDefault()\" id=\"savezoomlevel\" class=\"staticlinks\">");
/* 3163 */                                       out.print(FormatUtil.getString("am.webclient.gmap.defaultlocation.text"));
/* 3164 */                                       out.write("</a>\n       </span>\n\t  </td> \n      ");
/* 3165 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3166 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3170 */                                   if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3171 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                   }
/*      */                                   
/* 3174 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3175 */                                   out.write("\n  </tr>\n</table>\n");
/* 3176 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 3177 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3181 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 3182 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 3185 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 3186 */                               out.write("\n\n\n<SCRIPT LANGUAGE=\"Javascript1.2\">\nvar defaultview = \"");
/* 3187 */                               out.print(request.getAttribute("defaultview"));
/* 3188 */                               out.write("\";\nif(defaultview == \"showMonitorGroupView\")//No I18N\n{\n  $('#createMG').show();//No I18N\n  $('#createNewMG').show();//No I18N\n}\nelse\n{\n  $('#createMG').hide();//No I18N\n  $('#createNewMG').hide();//No I18N\n}\n//Set cookie function\nfunction setCookie(name,value,expdays)\n{\n       var expdate=new Date();   //No I18N\n       expdate.setDate(expdate.getDate() + expdays);\n       var val=escape(value) + ((expdays==null) ? \"\" : \"; expires=\"+expdate.toUTCString());  //No I18N\n       document.cookie=name + \"=\" + val;   //No I18N\n}\n\n// Get cookie function\nfunction getCookie(name)\n{\n       var i,x,y,arr=document.cookie.split(\";\");   //No I18N\n       y = null;\n       for (i=0;i<arr.length;i++)\n       {\n         x=arr[i].substr(0,arr[i].indexOf(\"=\"));   //No I18N\n         y=arr[i].substr(arr[i].indexOf(\"=\")+1);   //No I18N\n         x=x.replace(/^\\s+|\\s+$/g,\"\");   //No I18N\n         if (x==name)\n         {\n           return unescape(y);\n         }\n       }\n}\nfunction setCookieval(Category_type){\n  //alert(Category_type);\n  if(Category_type==\"showAddedMonitors\"){\n");
/* 3189 */                               out.write("    setCookie('Category_type','showAddedMonitors');  //No I18N\n  }\n  else{\n    setCookie('Category_type','showAllMonitors');  //No I18N\n  }\n}\n\nfunction setMonitorsViewDefault() {\n");
/* 3190 */                               if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3192 */                               out.write(10);
/* 3193 */                               out.write(32);
/* 3194 */                               out.write(32);
/* 3195 */                               if (_jspx_meth_logic_005fnotPresent_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 3197 */                               out.write("\n}\n\nfunction changeMGURL(a)\n{\n  var er = /^[0-9]+$/;\n  if(!er.test(a.value))\n  {\n    return;\n  }\n  location.href = '/admin/createapplication.do?method=createapp&grouptype='+a.value;\n}\n\nfunction changeUrl(a)\n{\n\t if(a.selectedIndex == 2 || a.selectedIndex == 7)\n\t {\n \t\tvar url = urlredirect[2];\n \t\tvar windowOpenOptions='scrollbars=1,resizable=1,width=900,height=650,left=50,screenX=50,screenY=25,top=25';\n \t\tvar name = \"PlasmaView\"; // NO I18N\n \t\t\n \t\tif(a.selectedIndex == 7)\n \t\t{\n \t\t\turl = '/GraphicalView.do?method=popUp&haid=0&isPopUp=true'; // NO I18N\n \t\t\twindowOpenOptions = 'scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25'; // NO I18N\n \t\t\tname = \"FlashView\"; // NO I18N\n \t\t}\n\t\twindow.open(url, name, windowOpenOptions);\n\n\t\tvar defaultview = \"");
/* 3198 */                               out.print(request.getAttribute("defaultview"));
/* 3199 */                               out.write("\";\n        \n        if(defaultview == \"showResourceTypesAll\")\n        {\n\t\t\ta.selectedIndex =0;\n        }\n        else if(defaultview == \"showResourceTypes\")\n        {\n            a.selectedIndex = 1;\n        }\n        else if(defaultview == \"showMonitorGroupView\")\n        {\n            a.selectedIndex = 3;\n        }\n        else if(defaultview == \"showGMapView\")\n        {\n\t\t\ta.selectedIndex = 4;\n        }\n        else if(defaultview == \"showIconsView\")\n        {\n            a.selectedIndex = 5;\n        }\n        else if(defaultview == \"showDetailsView\")\n        {\n            a.selectedIndex = 6;\n        }\n        return;\n\t}\n\tlocation.href=urlredirect[a.selectedIndex]; //NO I18N\n}\n</script>\n");
/* 3200 */                               out.write("\n\n\n\n\n");
/*      */                               
/* 3202 */                               if (systemsPresent)
/*      */                               {
/* 3204 */                                 if (request.getParameter("mas_host") != null)
/*      */                                 {
/* 3206 */                                   out.write("\n    <table class=\"messagebox\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" width=\"98%\">\n     <tbody><tr>\n\t  <td align=\"center\" width=\"4%\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" height=\"23\" width=\"23\">\n\t  </td>\n       <td class=\"message\" height=\"34\" width=\"94%\">\n\t   ");
/*      */                                   
/* 3208 */                                   if (!request.getParameter("serverId").equals("-1"))
/*      */                                   {
/* 3210 */                                     if (request.getParameter("serverId").equals("0"))
/*      */                                     {
/*      */ 
/* 3213 */                                       out.write("\n\t    ");
/* 3214 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.accessproblem.details"));
/* 3215 */                                       out.write("  \n\t  ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3219 */                                       out.write("\n\t    ");
/* 3220 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.passwordWrong.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port"), request.getParameter("serverId") }));
/* 3221 */                                       out.write("\n\t   ");
/*      */                                     }
/*      */                                   }
/*      */                                   else {
/* 3225 */                                     out.write(10);
/* 3226 */                                     out.write(9);
/* 3227 */                                     out.write(32);
/* 3228 */                                     out.print(FormatUtil.getString("am.webclient.managedserver.down.message", new String[] { request.getParameter("mas_host"), request.getParameter("mas_port") }));
/* 3229 */                                     out.write("\n     ");
/*      */                                   }
/* 3231 */                                   out.write("\n\t   </tr>\n\t   </tbody></table>\n\t  <table>\n\t   <tr>\n\t    <td class=\"monitorsheading\"><img src=\"../images/spacer.gif\" height=\"5\" width=\"99%\"></td>\n\t   </tr>\n\t  </table>\n  ");
/*      */                                 }
/* 3233 */                                 out.write("\n  <table>\n  <div style=\"float:right; margin-right:10px; text-align:right; width:310px;\">\n  ");
/* 3234 */                                 if (noOfObjects > 24) {
/* 3235 */                                   out.write(10);
/* 3236 */                                   out.write(9);
/* 3237 */                                   JspRuntimeLibrary.include(request, response, "/jsp/includes/NewPagingComp.jsp" + ("/jsp/includes/NewPagingComp.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("actionPath", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(actionPath), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("totalObj", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(noOfObjects), request.getCharacterEncoding()), out, true);
/* 3238 */                                   out.write(32);
/* 3239 */                                   out.write(10);
/* 3240 */                                   out.write(32);
/* 3241 */                                   out.write(32);
/*      */                                 }
/*      */                                 else {
/* 3244 */                                   out.write("\n\t  &nbsp;\n ");
/*      */                                 }
/*      */                                 
/* 3247 */                                 out.write("\n  </div>\n  </table>\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-size:8px;\">\n\t  \n\n  ");
/*      */                                 
/*      */ 
/* 3250 */                                 for (int j = 0; j < dataModel.size(); j++)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3261 */                                   if (j % 5 == 0)
/*      */                                   {
/*      */ 
/* 3264 */                                     out.write("\n  <tr> \n\t ");
/*      */                                   }
/*      */                                   
/* 3267 */                                   Map systemData = (HashMap)dataModel.get(j);
/* 3268 */                                   String sysType = FormatUtil.getString("am.webclient.monitor.systype.text");
/* 3269 */                                   String sysTypeImg = "icon_maps_unknown.gif";
/* 3270 */                                   sysType = (String)systemData.get("Resource_Type");
/* 3271 */                                   String shortName = (String)systemData.get("Short_Name");
/*      */                                   
/* 3273 */                                   String vmOsName = "";
/* 3274 */                                   if (sysType.toLowerCase().equalsIgnoreCase("Node"))
/*      */                                   {
/* 3276 */                                     sysTypeImg = "icon_maps_unknown.gif";
/* 3277 */                                     sysType = FormatUtil.getString("am.webclient.monitor.systype.text");
/* 3278 */                                     vmOsName = "Unknown";
/*      */                                   }
/* 3280 */                                   else if (sysType.toLowerCase().indexOf("linux") != -1)
/*      */                                   {
/* 3282 */                                     sysTypeImg = "icon_map_linuxos.gif";
/* 3283 */                                     vmOsName = "Linux";
/*      */                                   }
/* 3285 */                                   else if (sysType.toLowerCase().indexOf("centos") != -1)
/*      */                                   {
/* 3287 */                                     sysTypeImg = "icon_map_linuxos.gif";
/* 3288 */                                     vmOsName = "Linux";
/*      */                                   }
/* 3290 */                                   else if (sysType.toLowerCase().indexOf("indo") != -1)
/*      */                                   {
/* 3292 */                                     sysTypeImg = "icon_map_windowsos.gif";
/* 3293 */                                     vmOsName = "Windows";
/*      */                                   }
/* 3295 */                                   else if (sysType.toLowerCase().indexOf("sun") != -1)
/*      */                                   {
/* 3297 */                                     sysTypeImg = "icon_map_solarisos.gif";
/* 3298 */                                     vmOsName = "Sun Solaris";
/*      */                                   }
/* 3300 */                                   else if (sysType.toLowerCase().indexOf("aix") != -1)
/*      */                                   {
/* 3302 */                                     sysTypeImg = "icon_map_aix.gif";
/* 3303 */                                     vmOsName = "AIX";
/*      */                                   }
/* 3305 */                                   else if (sysType.toLowerCase().indexOf("hp") != -1)
/*      */                                   {
/* 3307 */                                     sysTypeImg = "icon_map_hpux.gif";
/* 3308 */                                     vmOsName = "HP-UX";
/*      */                                   }
/* 3310 */                                   else if (sysType.indexOf("Mac") != -1)
/*      */                                   {
/* 3312 */                                     sysTypeImg = "icon_map_macOS.gif";
/* 3313 */                                     vmOsName = "Mac OS";
/*      */                                   }
/* 3315 */                                   else if (sysType.indexOf("AS400/iSeries") != -1)
/*      */                                   {
/* 3317 */                                     sysTypeImg = "icon_map_as400OS.gif";
/* 3318 */                                     vmOsName = "AS400";
/*      */                                   }
/* 3320 */                                   else if (sysType.indexOf("FreeBSD") != -1)
/*      */                                   {
/* 3322 */                                     sysTypeImg = "icon_map_freebsd.gif";
/* 3323 */                                     vmOsName = "FreeBSD";
/*      */                                   }
/* 3325 */                                   else if (sysType.indexOf("Novell") != -1)
/*      */                                   {
/* 3327 */                                     sysTypeImg = "icon_map_novell.gif";
/* 3328 */                                     vmOsName = "Novell";
/*      */                                   }
/*      */                                   
/* 3331 */                                   if (shortName.equalsIgnoreCase("VirtualMachine")) {
/* 3332 */                                     shortName = FormatUtil.getString(vmOsName) + " - " + FormatUtil.getString("am.webclient.type.vm");
/*      */                                   }
/*      */                                   else {
/* 3335 */                                     shortName = FormatUtil.getString(shortName);
/*      */                                   }
/*      */                                   
/* 3338 */                                   String resourceName = (String)systemData.get("Resource_Disp_Name");
/* 3339 */                                   String resourceAva = (String)systemData.get("Resource_Ava");
/* 3340 */                                   String resAvaTip = FormatUtil.getString("am.webclient.monitor.systype.text");
/* 3341 */                                   String resourceID = (String)systemData.get("Resource_Id");
/* 3342 */                                   if (resourceAva.equals("1"))
/*      */                                   {
/* 3344 */                                     resAvaTip = FormatUtil.getString("am.webclient.monitor.networkview.tooltip");
/*      */                                   }
/* 3346 */                                   else if (resourceAva.equals("5"))
/*      */                                   {
/* 3348 */                                     resAvaTip = FormatUtil.getString("am.webclient.monitor.networkview.availabilityup.tooltip");
/*      */                                   }
/* 3350 */                                   List serviceNames = new ArrayList();
/* 3351 */                                   for (int k = 0; k < this.supportedTypes.length; k++)
/*      */                                   {
/* 3353 */                                     List servicesList = (ArrayList)systemData.get(this.supportedTypes[k] + "_Names");
/*      */                                     
/* 3355 */                                     if (servicesList != null)
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3361 */                                       List servicesAvaList = (ArrayList)systemData.get(this.supportedTypes[k] + "_Ava");
/* 3362 */                                       List servicesIdList = (ArrayList)systemData.get(this.supportedTypes[k] + "_Id");
/* 3363 */                                       for (int m = 0; m < servicesList.size(); m++)
/*      */                                       {
/* 3365 */                                         String serviceAva = "icon_map_unknown.gif";
/* 3366 */                                         String serAvaTip = FormatUtil.getString("am.webclient.monitor.systype.text");
/* 3367 */                                         if (((String)servicesAvaList.get(m)).equals("1"))
/*      */                                         {
/* 3369 */                                           serviceAva = "icon_map_down.gif";
/* 3370 */                                           serAvaTip = FormatUtil.getString("am.webclient.monitor.networkview.tooltip");
/*      */                                         }
/* 3372 */                                         else if (((String)servicesAvaList.get(m)).equals("5"))
/*      */                                         {
/* 3374 */                                           serviceAva = "icon_map_up.gif";
/* 3375 */                                           serAvaTip = FormatUtil.getString("am.webclient.monitor.networkview.availabilityup.tooltip");
/*      */                                         }
/* 3377 */                                         serviceNames.add(new String[] { (String)servicesList.get(m), FormatUtil.getString(this.supportedTypesDisplayName[k]), serviceAva, resourceName, sysTypeImg, resourceAva, resourceID, (String)servicesIdList.get(m), resAvaTip, serAvaTip, sysType });
/*      */                                       }
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3382 */                                   out.write("\n     <td width=\"22%\" class=\"arial10\" valign=\"top\" >\n      <table width=\"200\" height=\"100\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  background=\"../images/bg_maps.gif\" style=\"margin:20px 0px 0px 0px;\"  >\n    \t<tr>\n      \t   \n    <td valign=\"top\" width=\"10%\"> \n      <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n        \t<tr>\n\t \t      \t<td height=\"35\" align=\"left\" valign=\"center\" title=\"");
/* 3383 */                                   out.print(resAvaTip);
/* 3384 */                                   out.write("\">&nbsp;&nbsp;");
/* 3385 */                                   out.print(getSeverityImageForAvailability(Integer.parseInt(resourceAva)));
/* 3386 */                                   out.write("\n\t\t\t</td>\n       \t\t</tr>\n\n\n       \t\t<tr>\n       \t\t\t<td align=\"left\" valign=\"top\" title=\"");
/* 3387 */                                   out.print(sysType);
/* 3388 */                                   out.write("\"><a href=\"/showresource.do?resourceid=");
/* 3389 */                                   out.print(resourceID);
/* 3390 */                                   out.write("&method=showResourceForResourceID\" class=\"bodytext\" title=\"");
/* 3391 */                                   out.print(resourceName);
/* 3392 */                                   out.write("\"><img title=\"");
/* 3393 */                                   out.print(shortName);
/* 3394 */                                   out.write("\" src=\"../images/");
/* 3395 */                                   out.print(sysTypeImg);
/* 3396 */                                   out.write("\" hspace=\"7\" border=\"0\"></a>\n       \t\t\t</td>\n       \t\t</tr>\n\t       \t</table>\n           </td>\n      \t   <td align=\"right\" width=\"90%\">\n      \t   ");
/*      */                                   
/* 3398 */                                   if (serviceNames.size() > 0)
/*      */                                   {
/*      */ 
/* 3401 */                                     out.write("\n\t   \t<table align=\"center\" width=\"100%\" height=\"68\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          \t     <tr>\n\t\t\t    ");
/* 3402 */                                     out.print(getCellTagsForService(serviceNames, 0));
/* 3403 */                                     out.write("\n\t\t\t    ");
/* 3404 */                                     out.print(getCellTagsForService(serviceNames, 4));
/* 3405 */                                     out.write("\n           \t     </tr>\n\n           \t     <tr >\n\t\t     \t    ");
/* 3406 */                                     out.print(getCellTagsForService(serviceNames, 1));
/* 3407 */                                     out.write("\n\t\t     \t    ");
/* 3408 */                                     out.print(getCellTagsForService(serviceNames, 5));
/* 3409 */                                     out.write("\n           \t     </tr>\n           \t     <tr>\n\t\t     \t    ");
/* 3410 */                                     out.print(getCellTagsForService(serviceNames, 2));
/* 3411 */                                     out.write("\n\t\t     \t    ");
/* 3412 */                                     out.print(getCellTagsForService(serviceNames, 6));
/* 3413 */                                     out.write("\n           \t     </tr>\n           \t     <tr>\n\t\t     \t    ");
/* 3414 */                                     out.print(getCellTagsForService(serviceNames, 3));
/* 3415 */                                     out.write("\n\t\t     \t    ");
/* 3416 */                                     out.print(getCellTagsForService(serviceNames, 7));
/* 3417 */                                     out.write("\n           \t     </tr>\n           \t     \n\t   \t </table>\n\t\t <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t <tr>\n\t\t   <td align=\"right\" class=\"footer\">\n\t\t\t");
/*      */                                     
/* 3419 */                                     if (EnterpriseUtil.isAdminServer())
/*      */                                     {
/* 3421 */                                       out.write("\n\t\t\t ");
/* 3422 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3423 */                                       out.write("&nbsp;&nbsp;\n\t\t\t ");
/*      */                                     }
/* 3425 */                                     else if (request.isUserInRole("ADMIN"))
/*      */                                     {
/* 3427 */                                       out.write("\n\t\t\t  <a href=\"javascript:deleteSystem('");
/* 3428 */                                       out.print(resourceID);
/* 3429 */                                       out.write("')\" class=\"footer\">");
/* 3430 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3431 */                                       out.write("</a>&nbsp;&nbsp;\n\t\t\t");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3435 */                                       out.write("\n\t\t\t&nbsp;\n\t\t\t");
/*      */                                     }
/* 3437 */                                     out.write("\n\t\t\t</td>\n\t\t </tr>\n\t\t </table>\n\t   ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3443 */                                     out.write("\n\t   <table align=\"center\" width=\"100%\" height=\"68\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t   <tr><td>\n\t   \t<span class=\"bodytext\" width=\"80%\">");
/* 3444 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.noservicesrunning.text"));
/* 3445 */                                     out.write("&nbsp;</span>\n\t   </td></tr>\n\t   </table>\n\t <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t <tr>\n\t\t<td align=\"right\" class=\"footer\">\n\t\t");
/*      */                                     
/* 3447 */                                     if (EnterpriseUtil.isAdminServer())
/*      */                                     {
/* 3449 */                                       out.write("\n\t\t  ");
/* 3450 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3451 */                                       out.write("&nbsp;&nbsp;\n\t\t ");
/*      */                                     }
/* 3453 */                                     else if (request.isUserInRole("ADMIN"))
/*      */                                     {
/* 3455 */                                       out.write("\n\t\t<a href=\"javascript:deleteSystem('");
/* 3456 */                                       out.print(resourceID);
/* 3457 */                                       out.write("')\" class=\"footer\">");
/* 3458 */                                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3459 */                                       out.write("</a>&nbsp;&nbsp;\n\t\t");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3463 */                                       out.write("\n\t\t&nbsp;\n\t\t");
/*      */                                     }
/* 3465 */                                     out.write("\n\t\t</td>\n\t </tr>\n\t </table>\n\t   ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3469 */                                   out.write("\n\t    </td>\n       </tr>\n\t   \n       </table>\n       <div class=\"iconview-title\">\n\t   ");
/*      */                                   
/* 3471 */                                   if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(resourceID) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */                                   {
/*      */ 
/* 3474 */                                     out.write("\n\t\t <a href=\"/showresource.do?resourceid=");
/* 3475 */                                     out.print(resourceID);
/* 3476 */                                     out.write("&method=showResourceForResourceID&viewType=");
/* 3477 */                                     out.print(request.getParameter("method"));
/* 3478 */                                     out.write("\" class=\"bodytext\" title=\"");
/* 3479 */                                     out.print(resourceName);
/* 3480 */                                     out.write(40);
/* 3481 */                                     out.print(CommDBUtil.getManagedServerNameWithPort(resourceID));
/* 3482 */                                     out.write(41);
/* 3483 */                                     out.write(34);
/* 3484 */                                     out.write(62);
/* 3485 */                                     out.print(getTrimmedText(resourceName, 20));
/* 3486 */                                     out.write(40);
/* 3487 */                                     out.print(getTrimmedText(CommDBUtil.getManagedServerNameWithPort(resourceID), 17));
/* 3488 */                                     out.write(")</a>\n\t\t");
/*      */                                   }
/*      */                                   else {
/* 3491 */                                     out.write("\n\t<a href=\"/showresource.do?resourceid=");
/* 3492 */                                     out.print(resourceID);
/* 3493 */                                     out.write("&method=showResourceForResourceID&viewType=");
/* 3494 */                                     out.print(request.getParameter("method"));
/* 3495 */                                     out.write("\" class=\"bodytext\" title=\"");
/* 3496 */                                     out.print(resourceName);
/* 3497 */                                     out.write(34);
/* 3498 */                                     out.write(62);
/* 3499 */                                     out.print(getTrimmedText(resourceName, 30));
/* 3500 */                                     out.write("</a> \n\t\t");
/*      */                                   }
/* 3502 */                                   out.write("\n\t</div>\n \t</td>\n    ");
/*      */                                   
/* 3504 */                                   if (((j + 1) % 5 == 0) || (j + 1 == dataModel.size()))
/*      */                                   {
/* 3506 */                                     if ((j + 1 == dataModel.size()) && ((j + 1) % 5 != 0))
/*      */                                     {
/* 3508 */                                       for (int z = 0; z < 4 - (j + 1) % 4; z++)
/*      */                                       {
/*      */ 
/* 3511 */                                         out.write("\n         <td>&nbsp;</td>\n         ");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 3516 */                                     out.write("\n    </tr>   \n    ");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3522 */                                 out.write("\n</table>\n");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3528 */                                 out.write("\n <table width=\"100%\" class=\"monitorsview-decor\">\n <tr>\n ");
/*      */                                 
/* 3530 */                                 boolean isPrivileged = request.isUserInRole("ADMIN");
/* 3531 */                                 if ((!EnterpriseUtil.isAdminServer()) && (isPrivileged))
/*      */                                 {
/*      */ 
/* 3534 */                                   out.write("\n <td colspan=\"2\" class=\"bodytextbold align-center\">");
/* 3535 */                                   out.print(FormatUtil.getString("am.monitortab.iconview.nohost") + " " + com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"));
/* 3536 */                                   out.write(".&nbsp;&nbsp;<a href=\"/adminAction.do?method=reloadHostDiscoveryForm&haid=null&addtoha=false\" class=\"staticlinks\">");
/* 3537 */                                   out.print(FormatUtil.getString("am.webclient.common.clickhere.text"));
/* 3538 */                                   out.write("</a> ");
/* 3539 */                                   out.print(FormatUtil.getString("am.monitortab.addmonitors.text"));
/* 3540 */                                   out.write("</td>\n ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3545 */                                   out.write(" \n  <td colspan=\"2\" class=\"bodytextbold align-center\">");
/* 3546 */                                   out.print(FormatUtil.getString("am.monitortab.iconview.nohost") + " " + com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name"));
/* 3547 */                                   out.write(".</td>\n ");
/*      */                                 }
/* 3549 */                                 out.write("\n </tr>\n </table>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 3553 */                               out.write(10);
/* 3554 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3555 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3558 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3559 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3562 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3563 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 3566 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3567 */                           out.write(10);
/* 3568 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 3570 */                           out.write(32);
/* 3571 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3572 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3576 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3577 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 3580 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3581 */                         out.write(32);
/* 3582 */                         out.write(10);
/* 3583 */                         out.write(10);
/* 3584 */                         out.write(10);
/* 3585 */                         out.write(10);
/* 3586 */                         out.write(10);
/*      */                       }
/* 3588 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3589 */         out = _jspx_out;
/* 3590 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3591 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3592 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3595 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3601 */     PageContext pageContext = _jspx_page_context;
/* 3602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3604 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3605 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3606 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3608 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3609 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3610 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3612 */         out.write(" \n\t\talertUser();\n\t\treturn;\n\t");
/* 3613 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3614 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3618 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3619 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3620 */       return true;
/*      */     }
/* 3622 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3623 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3628 */     PageContext pageContext = _jspx_page_context;
/* 3629 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3631 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 3632 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3633 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3635 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/* 3637 */     _jspx_th_c_005fset_005f0.setProperty("defaultmonitorsview");
/*      */     
/* 3639 */     _jspx_th_c_005fset_005f0.setValue("showIconsView");
/* 3640 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3641 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3642 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3643 */       return true;
/*      */     }
/* 3645 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3651 */     PageContext pageContext = _jspx_page_context;
/* 3652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3654 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3655 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3656 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3658 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3660 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3661 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3662 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3663 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3664 */       return true;
/*      */     }
/* 3666 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3667 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3672 */     PageContext pageContext = _jspx_page_context;
/* 3673 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3675 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3676 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3677 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3679 */     _jspx_th_html_005fhidden_005f0.setProperty("zoomlevel");
/* 3680 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3681 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3682 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3683 */       return true;
/*      */     }
/* 3685 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3686 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3691 */     PageContext pageContext = _jspx_page_context;
/* 3692 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3694 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3695 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3696 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3698 */     _jspx_th_html_005fhidden_005f1.setProperty("zoomlocation");
/* 3699 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3700 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3701 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3702 */       return true;
/*      */     }
/* 3704 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3705 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3710 */     PageContext pageContext = _jspx_page_context;
/* 3711 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3713 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3714 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3715 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3717 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3718 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3719 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3721 */         out.write("\n    alertUser();\n    return;\n  ");
/* 3722 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3723 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3727 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3728 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3729 */       return true;
/*      */     }
/* 3731 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3737 */     PageContext pageContext = _jspx_page_context;
/* 3738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3740 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3741 */     _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3742 */     _jspx_th_logic_005fnotPresent_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3744 */     _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3745 */     int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3746 */     if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */       for (;;) {
/* 3748 */         out.write("\n  document.AMActionForm.submit();\n  ");
/* 3749 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3750 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3754 */     if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3755 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3756 */       return true;
/*      */     }
/* 3758 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3764 */     PageContext pageContext = _jspx_page_context;
/* 3765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3767 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3768 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3769 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3771 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3773 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3774 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3775 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3776 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3777 */       return true;
/*      */     }
/* 3779 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3780 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NetworkView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */