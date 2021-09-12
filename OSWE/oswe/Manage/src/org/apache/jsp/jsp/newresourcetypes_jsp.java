/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
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
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class newresourcetypes_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  817 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div1, rca);
/*  819 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  822 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  823 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  858 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  859 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  862 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  863 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  864 */       set = AMConnectionPool.executeQueryStmt(query);
/*  865 */       if (set.next())
/*      */       {
/*  867 */         String helpLink = set.getString("LINK");
/*  868 */         DBUtil.searchLinks.put(key, helpLink);
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
/*  976 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1292 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1990 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2181 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2187 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2188 */   static { _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2220 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2232 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2239 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2242 */     JspWriter out = null;
/* 2243 */     Object page = this;
/* 2244 */     JspWriter _jspx_out = null;
/* 2245 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2249 */       response.setContentType("text/html;charset=UTF-8");
/* 2250 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2252 */       _jspx_page_context = pageContext;
/* 2253 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2254 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2255 */       session = pageContext.getSession();
/* 2256 */       out = pageContext.getOut();
/* 2257 */       _jspx_out = out;
/*      */       
/* 2259 */       out.write(10);
/* 2260 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2262 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2263 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2265 */       out.write(10);
/* 2266 */       out.write(10);
/* 2267 */       out.write(10);
/* 2268 */       out.write(10);
/* 2269 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2270 */       String message = (String)request.getAttribute("typemessage");
/*      */       
/* 2272 */       ManagedApplication mo1 = new ManagedApplication();
/* 2273 */       Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2274 */       boolean isConfMonitor = false;
/* 2275 */       ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2276 */       if (message != null)
/*      */       {
/* 2278 */         out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2279 */         out.print(message);
/* 2280 */         out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */       }
/*      */       
/*      */ 
/* 2284 */       out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2285 */       out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2286 */       out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2287 */       if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2288 */         DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2289 */         if (frm != null) {
/* 2290 */           frm.set("type", "UrlSeq");
/*      */         }
/*      */       }
/*      */       
/* 2294 */       if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2295 */         DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2296 */         if (frm != null) {
/* 2297 */           frm.set("type", "UrlMonitor");
/*      */         }
/*      */       }
/*      */       
/* 2301 */       if ("RBM".equals(request.getParameter("type"))) {
/* 2302 */         DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2303 */         if (frm != null) {
/* 2304 */           frm.set("type", "RBM");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2309 */       out.write("\n\n    ");
/*      */       
/* 2311 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2312 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2313 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/* 2315 */       _jspx_th_c_005fif_005f0.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 2316 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2317 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/* 2319 */           out.write("\n     ");
/*      */           
/* 2321 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2322 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2323 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */           
/* 2325 */           _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */           
/* 2327 */           _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */           
/* 2329 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */           
/* 2331 */           _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 2332 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2333 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2334 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2335 */               out = _jspx_page_context.pushBody();
/* 2336 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2337 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2340 */               out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */               
/* 2342 */               if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */               {
/*      */ 
/*      */ 
/* 2346 */                 out.write("\n\n\t <optgroup label=\"");
/* 2347 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2348 */                 out.write("\">\n                                        \n                                        ");
/*      */                 
/* 2350 */                 OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2351 */                 _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2352 */                 _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2354 */                 _jspx_th_html_005foption_005f0.setValue("AIX");
/* 2355 */                 int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2356 */                 if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2357 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2358 */                     out = _jspx_page_context.pushBody();
/* 2359 */                     _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2360 */                     _jspx_th_html_005foption_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2363 */                     out.print(FormatUtil.getString("AIX"));
/* 2364 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2365 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2368 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2369 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2372 */                 if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2373 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                 }
/*      */                 
/* 2376 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 2377 */                 out.write("\n                                        ");
/*      */                 
/* 2379 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2380 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2381 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2383 */                 _jspx_th_html_005foption_005f1.setValue("AS400");
/* 2384 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2385 */                 if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2386 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2387 */                     out = _jspx_page_context.pushBody();
/* 2388 */                     _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2389 */                     _jspx_th_html_005foption_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2392 */                     out.print(FormatUtil.getString("AS400/iSeries"));
/* 2393 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2394 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2397 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2398 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2401 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2402 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/* 2405 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2406 */                 out.write("\n                                        ");
/*      */                 
/* 2408 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2409 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2410 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2412 */                 _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 2413 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2414 */                 if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2415 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2416 */                     out = _jspx_page_context.pushBody();
/* 2417 */                     _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2418 */                     _jspx_th_html_005foption_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2421 */                     out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 2422 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2423 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2426 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2427 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2430 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2431 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/* 2434 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2435 */                 out.write("\n                                        ");
/*      */                 
/* 2437 */                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2438 */                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2439 */                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2441 */                 _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 2442 */                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2443 */                 if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2444 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2445 */                     out = _jspx_page_context.pushBody();
/* 2446 */                     _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2447 */                     _jspx_th_html_005foption_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2450 */                     out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 2451 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2452 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2455 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2456 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2459 */                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2460 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                 }
/*      */                 
/* 2463 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2464 */                 out.write("\n                                        ");
/*      */                 
/* 2466 */                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2467 */                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2468 */                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2470 */                 _jspx_th_html_005foption_005f4.setValue("Linux");
/* 2471 */                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2472 */                 if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2473 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2474 */                     out = _jspx_page_context.pushBody();
/* 2475 */                     _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2476 */                     _jspx_th_html_005foption_005f4.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2479 */                     out.print(FormatUtil.getString("Linux"));
/* 2480 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2481 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2484 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2485 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2488 */                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2489 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                 }
/*      */                 
/* 2492 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2493 */                 out.write("\n                                        ");
/*      */                 
/* 2495 */                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2496 */                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2497 */                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2499 */                 _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 2500 */                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2501 */                 if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2502 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2503 */                     out = _jspx_page_context.pushBody();
/* 2504 */                     _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2505 */                     _jspx_th_html_005foption_005f5.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2508 */                     out.print(FormatUtil.getString("Mac OS"));
/* 2509 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2510 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2513 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2514 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2517 */                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2518 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                 }
/*      */                 
/* 2521 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2522 */                 out.write("\n                                        ");
/*      */                 
/* 2524 */                 OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2525 */                 _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2526 */                 _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2528 */                 _jspx_th_html_005foption_005f6.setValue("Novell");
/* 2529 */                 int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2530 */                 if (_jspx_eval_html_005foption_005f6 != 0) {
/* 2531 */                   if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2532 */                     out = _jspx_page_context.pushBody();
/* 2533 */                     _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 2534 */                     _jspx_th_html_005foption_005f6.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2537 */                     out.print(FormatUtil.getString("Novell"));
/* 2538 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 2539 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2542 */                   if (_jspx_eval_html_005foption_005f6 != 1) {
/* 2543 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2546 */                 if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2547 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                 }
/*      */                 
/* 2550 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 2551 */                 out.write("\n                                        ");
/*      */                 
/* 2553 */                 OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2554 */                 _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2555 */                 _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2557 */                 _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 2558 */                 int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2559 */                 if (_jspx_eval_html_005foption_005f7 != 0) {
/* 2560 */                   if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2561 */                     out = _jspx_page_context.pushBody();
/* 2562 */                     _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 2563 */                     _jspx_th_html_005foption_005f7.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2566 */                     out.print(FormatUtil.getString("Sun Solaris"));
/* 2567 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 2568 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2571 */                   if (_jspx_eval_html_005foption_005f7 != 1) {
/* 2572 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2575 */                 if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2576 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                 }
/*      */                 
/* 2579 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 2580 */                 out.write("\n                                        ");
/*      */                 
/* 2582 */                 OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2583 */                 _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2584 */                 _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2586 */                 _jspx_th_html_005foption_005f8.setValue("Windows");
/* 2587 */                 int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2588 */                 if (_jspx_eval_html_005foption_005f8 != 0) {
/* 2589 */                   if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2590 */                     out = _jspx_page_context.pushBody();
/* 2591 */                     _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 2592 */                     _jspx_th_html_005foption_005f8.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2595 */                     out.print(FormatUtil.getString("Windows"));
/* 2596 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 2597 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2600 */                   if (_jspx_eval_html_005foption_005f8 != 1) {
/* 2601 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2604 */                 if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2605 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                 }
/*      */                 
/* 2608 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 2609 */                 out.write("\n                                        ");
/*      */                 
/* 2611 */                 OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2612 */                 _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2613 */                 _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2615 */                 _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 2616 */                 int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2617 */                 if (_jspx_eval_html_005foption_005f9 != 0) {
/* 2618 */                   if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2619 */                     out = _jspx_page_context.pushBody();
/* 2620 */                     _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 2621 */                     _jspx_th_html_005foption_005f9.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2624 */                     out.print(FormatUtil.getString("Windows Cluster"));
/* 2625 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 2626 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2629 */                   if (_jspx_eval_html_005foption_005f9 != 1) {
/* 2630 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2633 */                 if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2634 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                 }
/*      */                 
/* 2637 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 2638 */                 out.write("\n                                        \n\n  ");
/*      */                 
/* 2640 */                 ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 2641 */                 if ((rows1 != null) && (rows1.size() > 0))
/*      */                 {
/* 2643 */                   for (int i = 0; i < rows1.size(); i++)
/*      */                   {
/* 2645 */                     ArrayList row = (ArrayList)rows1.get(i);
/* 2646 */                     String res = (String)row.get(0);
/* 2647 */                     String dname = (String)row.get(1);
/* 2648 */                     String values = props.getProperty(res);
/* 2649 */                     if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                     {
/*      */ 
/* 2652 */                       out.write("\n\t\t\t\t");
/*      */                       
/* 2654 */                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2655 */                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2656 */                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/* 2658 */                       _jspx_th_html_005foption_005f10.setValue(values);
/* 2659 */                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2660 */                       if (_jspx_eval_html_005foption_005f10 != 0) {
/* 2661 */                         if (_jspx_eval_html_005foption_005f10 != 1) {
/* 2662 */                           out = _jspx_page_context.pushBody();
/* 2663 */                           _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 2664 */                           _jspx_th_html_005foption_005f10.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2667 */                           out.write(32);
/* 2668 */                           out.print(FormatUtil.getString(dname));
/* 2669 */                           int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 2670 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2673 */                         if (_jspx_eval_html_005foption_005f10 != 1) {
/* 2674 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2677 */                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2678 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                       }
/*      */                       
/* 2681 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 2682 */                       out.write("\n\t\t\t");
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/* 2688 */                 String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                 
/* 2690 */                 String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                 
/*      */ 
/* 2693 */                 if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                 {
/*      */ 
/* 2696 */                   categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 2697 */                   categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                 }
/* 2699 */                 for (int c = 0; c < categoryLink.length; c++)
/*      */                 {
/* 2701 */                   ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 2702 */                   if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                   {
/*      */ 
/*      */ 
/* 2706 */                     StringBuffer queryBuf = new StringBuffer();
/* 2707 */                     queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 2708 */                     queryBuf.append(categoryLink[c] + "'");
/* 2709 */                     queryBuf.append(" ");
/* 2710 */                     queryBuf.append("and RESOURCETYPE in");
/* 2711 */                     queryBuf.append(" ");
/* 2712 */                     queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 2713 */                     if (categoryLink[c].equals("APP"))
/*      */                     {
/* 2715 */                       queryBuf.append(" ");
/* 2716 */                       queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 2717 */                       queryBuf.append(" ");
/*      */                     }
/* 2719 */                     else if (categoryLink[c].equals("SER"))
/*      */                     {
/* 2721 */                       queryBuf.append(" ");
/* 2722 */                       queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 2723 */                       queryBuf.append(" ");
/*      */                     }
/* 2725 */                     else if (categoryLink[c].equals("CAM"))
/*      */                     {
/* 2727 */                       queryBuf.append(" ");
/* 2728 */                       queryBuf.append("and RESOURCETYPE != 'directory'");
/* 2729 */                       queryBuf.append(" ");
/*      */                     }
/* 2731 */                     queryBuf.append(" ");
/* 2732 */                     queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 2733 */                     ArrayList rows = mo1.getRows(queryBuf.toString());
/* 2734 */                     if ((rows != null) && (rows.size() != 0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/* 2739 */                       out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 2740 */                       out.print(FormatUtil.getString(categoryTitle[c]));
/* 2741 */                       out.write(34);
/* 2742 */                       out.write(62);
/* 2743 */                       out.write(10);
/*      */                       
/*      */ 
/* 2746 */                       for (int i = 0; i < rows.size(); i++)
/*      */                       {
/* 2748 */                         ArrayList row = (ArrayList)rows.get(i);
/* 2749 */                         String res = (String)row.get(0);
/* 2750 */                         if (res.equals("file"))
/*      */                         {
/* 2752 */                           res = "File / Directory Monitor";
/*      */                         }
/* 2754 */                         String dname = (String)row.get(1);
/* 2755 */                         String values = props.getProperty(res);
/* 2756 */                         if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                         {
/*      */ 
/* 2759 */                           if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                           {
/*      */ 
/* 2762 */                             out.write("\n\t\t\t\t \t");
/*      */                             
/* 2764 */                             OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2765 */                             _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 2766 */                             _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                             
/* 2768 */                             _jspx_th_html_005foption_005f11.setValue(values);
/* 2769 */                             int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 2770 */                             if (_jspx_eval_html_005foption_005f11 != 0) {
/* 2771 */                               if (_jspx_eval_html_005foption_005f11 != 1) {
/* 2772 */                                 out = _jspx_page_context.pushBody();
/* 2773 */                                 _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 2774 */                                 _jspx_th_html_005foption_005f11.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2777 */                                 out.write(32);
/* 2778 */                                 out.print(FormatUtil.getString(dname));
/* 2779 */                                 int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 2780 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2783 */                               if (_jspx_eval_html_005foption_005f11 != 1) {
/* 2784 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2787 */                             if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 2788 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                             }
/*      */                             
/* 2791 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 2792 */                             out.write("\n\t\t\t\t");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/* 2797 */                       if (categoryLink[c].equals("VIR"))
/*      */                       {
/*      */ 
/* 2800 */                         out.write("\n\t\t\t\t\t");
/*      */                         
/* 2802 */                         OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2803 */                         _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 2804 */                         _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/* 2806 */                         _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 2807 */                         int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 2808 */                         if (_jspx_eval_html_005foption_005f12 != 0) {
/* 2809 */                           if (_jspx_eval_html_005foption_005f12 != 1) {
/* 2810 */                             out = _jspx_page_context.pushBody();
/* 2811 */                             _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 2812 */                             _jspx_th_html_005foption_005f12.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2815 */                             out.write(32);
/* 2816 */                             out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 2817 */                             int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 2818 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2821 */                           if (_jspx_eval_html_005foption_005f12 != 1) {
/* 2822 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2825 */                         if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 2826 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                         }
/*      */                         
/* 2829 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 2830 */                         out.write("\n\t\t\t\t");
/*      */                       }
/*      */                     }
/*      */                   } }
/* 2834 */                 String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 2835 */                 if (!usertype.equals("F"))
/*      */                 {
/* 2837 */                   if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                   {
/* 2839 */                     out.write("\n    </optgroup> <optgroup label=\"");
/* 2840 */                     out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 2841 */                     out.write("\">\n     ");
/*      */                     
/* 2843 */                     OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2844 */                     _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 2845 */                     _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                     
/* 2847 */                     _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 2848 */                     int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 2849 */                     if (_jspx_eval_html_005foption_005f13 != 0) {
/* 2850 */                       if (_jspx_eval_html_005foption_005f13 != 1) {
/* 2851 */                         out = _jspx_page_context.pushBody();
/* 2852 */                         _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 2853 */                         _jspx_th_html_005foption_005f13.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2856 */                         out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 2857 */                         out.write(32);
/* 2858 */                         int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 2859 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2862 */                       if (_jspx_eval_html_005foption_005f13 != 1) {
/* 2863 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2866 */                     if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 2867 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                     }
/*      */                     
/* 2870 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 2871 */                     out.write("\n\n     ");
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 
/*      */               }
/* 2877 */               else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */               {
/*      */ 
/* 2880 */                 out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 2881 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2882 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 2884 */                 OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2885 */                 _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 2886 */                 _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2888 */                 _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 2889 */                 int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 2890 */                 if (_jspx_eval_html_005foption_005f14 != 0) {
/* 2891 */                   if (_jspx_eval_html_005foption_005f14 != 1) {
/* 2892 */                     out = _jspx_page_context.pushBody();
/* 2893 */                     _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 2894 */                     _jspx_th_html_005foption_005f14.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2897 */                     out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 2898 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 2899 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2902 */                   if (_jspx_eval_html_005foption_005f14 != 1) {
/* 2903 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2906 */                 if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 2907 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                 }
/*      */                 
/* 2910 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 2911 */                 out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 2912 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 2913 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 2915 */                 OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2916 */                 _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 2917 */                 _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2919 */                 _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 2920 */                 int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 2921 */                 if (_jspx_eval_html_005foption_005f15 != 0) {
/* 2922 */                   if (_jspx_eval_html_005foption_005f15 != 1) {
/* 2923 */                     out = _jspx_page_context.pushBody();
/* 2924 */                     _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 2925 */                     _jspx_th_html_005foption_005f15.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2928 */                     out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 2929 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 2930 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2933 */                   if (_jspx_eval_html_005foption_005f15 != 1) {
/* 2934 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2937 */                 if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 2938 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                 }
/*      */                 
/* 2941 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 2942 */                 out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 2943 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 2944 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 2946 */                 OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2947 */                 _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 2948 */                 _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2950 */                 _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 2951 */                 int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 2952 */                 if (_jspx_eval_html_005foption_005f16 != 0) {
/* 2953 */                   if (_jspx_eval_html_005foption_005f16 != 1) {
/* 2954 */                     out = _jspx_page_context.pushBody();
/* 2955 */                     _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 2956 */                     _jspx_th_html_005foption_005f16.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2959 */                     out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 2960 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 2961 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2964 */                   if (_jspx_eval_html_005foption_005f16 != 1) {
/* 2965 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2968 */                 if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 2969 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                 }
/*      */                 
/* 2972 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 2973 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 2975 */                 OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2976 */                 _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 2977 */                 _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 2979 */                 _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 2980 */                 int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 2981 */                 if (_jspx_eval_html_005foption_005f17 != 0) {
/* 2982 */                   if (_jspx_eval_html_005foption_005f17 != 1) {
/* 2983 */                     out = _jspx_page_context.pushBody();
/* 2984 */                     _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 2985 */                     _jspx_th_html_005foption_005f17.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2988 */                     out.write(32);
/* 2989 */                     out.print(FormatUtil.getString("Service Monitoring"));
/* 2990 */                     out.write(32);
/* 2991 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 2992 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2995 */                   if (_jspx_eval_html_005foption_005f17 != 1) {
/* 2996 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2999 */                 if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3000 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                 }
/*      */                 
/* 3003 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3004 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3006 */                 OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3007 */                 _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3008 */                 _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3010 */                 _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3011 */                 int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3012 */                 if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3013 */                   if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3014 */                     out = _jspx_page_context.pushBody();
/* 3015 */                     _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3016 */                     _jspx_th_html_005foption_005f18.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3019 */                     out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3020 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3021 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3024 */                   if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3025 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3028 */                 if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3029 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                 }
/*      */                 
/* 3032 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3033 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3035 */                 OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3036 */                 _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3037 */                 _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3039 */                 _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3040 */                 int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3041 */                 if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3042 */                   if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3043 */                     out = _jspx_page_context.pushBody();
/* 3044 */                     _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3045 */                     _jspx_th_html_005foption_005f19.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3048 */                     out.print(FormatUtil.getString("SNMP"));
/* 3049 */                     out.write(" (V1 or V2c)");
/* 3050 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3051 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3054 */                   if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3055 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3058 */                 if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3059 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                 }
/*      */                 
/* 3062 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3063 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3065 */                 OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3066 */                 _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3067 */                 _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3069 */                 _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3070 */                 int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3071 */                 if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3072 */                   if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3073 */                     out = _jspx_page_context.pushBody();
/* 3074 */                     _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3075 */                     _jspx_th_html_005foption_005f20.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3078 */                     out.print(FormatUtil.getString("Telnet"));
/* 3079 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3080 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3083 */                   if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3084 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3087 */                 if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3088 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                 }
/*      */                 
/* 3091 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3092 */                 out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3093 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3094 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 3096 */                 OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3097 */                 _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3098 */                 _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3100 */                 _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3101 */                 int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3102 */                 if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3103 */                   if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3104 */                     out = _jspx_page_context.pushBody();
/* 3105 */                     _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3106 */                     _jspx_th_html_005foption_005f21.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3109 */                     out.write(32);
/* 3110 */                     out.print(FormatUtil.getString("Apache Server"));
/* 3111 */                     out.write(32);
/* 3112 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3113 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3116 */                   if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3117 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3120 */                 if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3121 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                 }
/*      */                 
/* 3124 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3125 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3127 */                 OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3128 */                 _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3129 */                 _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3131 */                 _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3132 */                 int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3133 */                 if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3134 */                   if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3135 */                     out = _jspx_page_context.pushBody();
/* 3136 */                     _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3137 */                     _jspx_th_html_005foption_005f22.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3140 */                     out.print(FormatUtil.getString("PHP"));
/* 3141 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3142 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3145 */                   if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3146 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3149 */                 if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3150 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                 }
/*      */                 
/* 3153 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3154 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3156 */                 OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3157 */                 _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3158 */                 _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3160 */                 _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3161 */                 int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3162 */                 if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3163 */                   if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3164 */                     out = _jspx_page_context.pushBody();
/* 3165 */                     _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3166 */                     _jspx_th_html_005foption_005f23.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3169 */                     out.print(FormatUtil.getString("HTTP-URLs"));
/* 3170 */                     out.write(32);
/* 3171 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3172 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3175 */                   if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3176 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3179 */                 if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3180 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                 }
/*      */                 
/* 3183 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3184 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3186 */                 OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3187 */                 _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3188 */                 _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3190 */                 _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3191 */                 int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3192 */                 if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3193 */                   if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3194 */                     out = _jspx_page_context.pushBody();
/* 3195 */                     _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3196 */                     _jspx_th_html_005foption_005f24.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3199 */                     out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3200 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3201 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3204 */                   if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3205 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3208 */                 if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3209 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                 }
/*      */                 
/* 3212 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3213 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3215 */                 OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3216 */                 _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3217 */                 _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3219 */                 _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3220 */                 int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3221 */                 if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3222 */                   if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3223 */                     out = _jspx_page_context.pushBody();
/* 3224 */                     _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3225 */                     _jspx_th_html_005foption_005f25.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3228 */                     out.write(32);
/* 3229 */                     out.print(FormatUtil.getString("Web Server"));
/* 3230 */                     out.write(32);
/* 3231 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3232 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3235 */                   if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3236 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3239 */                 if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3240 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                 }
/*      */                 
/* 3243 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3244 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3246 */                 OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3247 */                 _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3248 */                 _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3250 */                 _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3251 */                 int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3252 */                 if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3253 */                   if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3254 */                     out = _jspx_page_context.pushBody();
/* 3255 */                     _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3256 */                     _jspx_th_html_005foption_005f26.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3259 */                     out.write(32);
/* 3260 */                     out.print(FormatUtil.getString("Web Service"));
/* 3261 */                     out.write(32);
/* 3262 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3263 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3266 */                   if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3267 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3270 */                 if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3271 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                 }
/*      */                 
/* 3274 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3275 */                 out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3276 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3277 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 3279 */                 OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3280 */                 _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3281 */                 _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3283 */                 _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3284 */                 int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3285 */                 if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3286 */                   if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3287 */                     out = _jspx_page_context.pushBody();
/* 3288 */                     _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3289 */                     _jspx_th_html_005foption_005f27.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3292 */                     out.print(FormatUtil.getString("Mail Server"));
/* 3293 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3294 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3297 */                   if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3298 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3301 */                 if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3302 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                 }
/*      */                 
/* 3305 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3306 */                 out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3307 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3308 */                 out.write("\">\n\t\t\t   ");
/*      */                 
/* 3310 */                 OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3311 */                 _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3312 */                 _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3314 */                 _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 3315 */                 int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3316 */                 if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3317 */                   if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3318 */                     out = _jspx_page_context.pushBody();
/* 3319 */                     _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3320 */                     _jspx_th_html_005foption_005f28.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3323 */                     out.write(32);
/* 3324 */                     out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 3325 */                     out.write(32);
/* 3326 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3327 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3330 */                   if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3331 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3334 */                 if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3335 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                 }
/*      */                 
/* 3338 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3339 */                 out.write("\n\t\t\t   ");
/*      */                 
/* 3341 */                 OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3342 */                 _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 3343 */                 _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3345 */                 _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 3346 */                 int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 3347 */                 if (_jspx_eval_html_005foption_005f29 != 0) {
/* 3348 */                   if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3349 */                     out = _jspx_page_context.pushBody();
/* 3350 */                     _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 3351 */                     _jspx_th_html_005foption_005f29.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3354 */                     out.print(FormatUtil.getString("Script Monitor"));
/* 3355 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 3356 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3359 */                   if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3360 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3363 */                 if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 3364 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                 }
/*      */                 
/* 3367 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 3368 */                 out.write("\n\n    ");
/*      */ 
/*      */               }
/* 3371 */               else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */               {
/*      */ 
/* 3374 */                 out.write("\n        ");
/*      */                 
/* 3376 */                 OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3377 */                 _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 3378 */                 _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3380 */                 _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 3381 */                 int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 3382 */                 if (_jspx_eval_html_005foption_005f30 != 0) {
/* 3383 */                   if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3384 */                     out = _jspx_page_context.pushBody();
/* 3385 */                     _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 3386 */                     _jspx_th_html_005foption_005f30.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3389 */                     out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3390 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 3391 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3394 */                   if (_jspx_eval_html_005foption_005f30 != 1) {
/* 3395 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3398 */                 if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 3399 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                 }
/*      */                 
/* 3402 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 3403 */                 out.write("\n       ");
/*      */                 
/* 3405 */                 OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3406 */                 _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 3407 */                 _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3409 */                 _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 3410 */                 int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 3411 */                 if (_jspx_eval_html_005foption_005f31 != 0) {
/* 3412 */                   if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3413 */                     out = _jspx_page_context.pushBody();
/* 3414 */                     _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 3415 */                     _jspx_th_html_005foption_005f31.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3418 */                     out.write(32);
/* 3419 */                     out.print(FormatUtil.getString("JBoss Server"));
/* 3420 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 3421 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3424 */                   if (_jspx_eval_html_005foption_005f31 != 1) {
/* 3425 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3428 */                 if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 3429 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                 }
/*      */                 
/* 3432 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 3433 */                 out.write("\n      ");
/*      */                 
/* 3435 */                 OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3436 */                 _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 3437 */                 _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3439 */                 _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 3440 */                 int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 3441 */                 if (_jspx_eval_html_005foption_005f32 != 0) {
/* 3442 */                   if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3443 */                     out = _jspx_page_context.pushBody();
/* 3444 */                     _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 3445 */                     _jspx_th_html_005foption_005f32.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3448 */                     out.print(FormatUtil.getString("Tomcat Server"));
/* 3449 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 3450 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3453 */                   if (_jspx_eval_html_005foption_005f32 != 1) {
/* 3454 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3457 */                 if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 3458 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                 }
/*      */                 
/* 3461 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 3462 */                 out.write("\n       ");
/*      */                 
/* 3464 */                 OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3465 */                 _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 3466 */                 _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3468 */                 _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 3469 */                 int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 3470 */                 if (_jspx_eval_html_005foption_005f33 != 0) {
/* 3471 */                   if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3472 */                     out = _jspx_page_context.pushBody();
/* 3473 */                     _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 3474 */                     _jspx_th_html_005foption_005f33.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3477 */                     out.write(32);
/* 3478 */                     out.print(FormatUtil.getString("WebLogic Server"));
/* 3479 */                     out.write(32);
/* 3480 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 3481 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3484 */                   if (_jspx_eval_html_005foption_005f33 != 1) {
/* 3485 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3488 */                 if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 3489 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                 }
/*      */                 
/* 3492 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 3493 */                 out.write("\n      ");
/*      */                 
/* 3495 */                 OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3496 */                 _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 3497 */                 _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3499 */                 _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 3500 */                 int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 3501 */                 if (_jspx_eval_html_005foption_005f34 != 0) {
/* 3502 */                   if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3503 */                     out = _jspx_page_context.pushBody();
/* 3504 */                     _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 3505 */                     _jspx_th_html_005foption_005f34.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3508 */                     out.write(32);
/* 3509 */                     out.print(FormatUtil.getString("WebSphere Server"));
/* 3510 */                     out.write(32);
/* 3511 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 3512 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3515 */                   if (_jspx_eval_html_005foption_005f34 != 1) {
/* 3516 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3519 */                 if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 3520 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                 }
/*      */                 
/* 3523 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 3524 */                 out.write("\n      ");
/*      */                 
/* 3526 */                 OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3527 */                 _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 3528 */                 _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3530 */                 _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 3531 */                 int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 3532 */                 if (_jspx_eval_html_005foption_005f35 != 0) {
/* 3533 */                   if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3534 */                     out = _jspx_page_context.pushBody();
/* 3535 */                     _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 3536 */                     _jspx_th_html_005foption_005f35.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3539 */                     out.print(FormatUtil.getString("Web Transactions"));
/* 3540 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 3541 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3544 */                   if (_jspx_eval_html_005foption_005f35 != 1) {
/* 3545 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3548 */                 if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 3549 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                 }
/*      */                 
/* 3552 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 3553 */                 out.write("\n      ");
/*      */                 
/* 3555 */                 OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3556 */                 _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 3557 */                 _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3559 */                 _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 3560 */                 int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 3561 */                 if (_jspx_eval_html_005foption_005f36 != 0) {
/* 3562 */                   if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3563 */                     out = _jspx_page_context.pushBody();
/* 3564 */                     _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 3565 */                     _jspx_th_html_005foption_005f36.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3568 */                     out.print(FormatUtil.getString("Mail Server"));
/* 3569 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 3570 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3573 */                   if (_jspx_eval_html_005foption_005f36 != 1) {
/* 3574 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3577 */                 if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 3578 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                 }
/*      */                 
/* 3581 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 3582 */                 out.write("\n      ");
/*      */                 
/* 3584 */                 OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3585 */                 _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 3586 */                 _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3588 */                 _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 3589 */                 int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 3590 */                 if (_jspx_eval_html_005foption_005f37 != 0) {
/* 3591 */                   if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3592 */                     out = _jspx_page_context.pushBody();
/* 3593 */                     _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 3594 */                     _jspx_th_html_005foption_005f37.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3597 */                     out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3598 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 3599 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3602 */                   if (_jspx_eval_html_005foption_005f37 != 1) {
/* 3603 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3606 */                 if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 3607 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                 }
/*      */                 
/* 3610 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 3611 */                 out.write("\n      ");
/*      */                 
/* 3613 */                 OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3614 */                 _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 3615 */                 _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3617 */                 _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 3618 */                 int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 3619 */                 if (_jspx_eval_html_005foption_005f38 != 0) {
/* 3620 */                   if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3621 */                     out = _jspx_page_context.pushBody();
/* 3622 */                     _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 3623 */                     _jspx_th_html_005foption_005f38.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3626 */                     out.write(32);
/* 3627 */                     out.print(FormatUtil.getString("Service Monitoring"));
/* 3628 */                     out.write(32);
/* 3629 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 3630 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3633 */                   if (_jspx_eval_html_005foption_005f38 != 1) {
/* 3634 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3637 */                 if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 3638 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                 }
/*      */                 
/* 3641 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 3642 */                 out.write("\n      ");
/*      */                 
/* 3644 */                 OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3645 */                 _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 3646 */                 _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3648 */                 _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 3649 */                 int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 3650 */                 if (_jspx_eval_html_005foption_005f39 != 0) {
/* 3651 */                   if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3652 */                     out = _jspx_page_context.pushBody();
/* 3653 */                     _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 3654 */                     _jspx_th_html_005foption_005f39.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3657 */                     out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3658 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 3659 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3662 */                   if (_jspx_eval_html_005foption_005f39 != 1) {
/* 3663 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3666 */                 if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 3667 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                 }
/*      */                 
/* 3670 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 3671 */                 out.write("\n      ");
/*      */                 
/* 3673 */                 OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3674 */                 _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 3675 */                 _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3677 */                 _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 3678 */                 int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 3679 */                 if (_jspx_eval_html_005foption_005f40 != 0) {
/* 3680 */                   if (_jspx_eval_html_005foption_005f40 != 1) {
/* 3681 */                     out = _jspx_page_context.pushBody();
/* 3682 */                     _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 3683 */                     _jspx_th_html_005foption_005f40.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3686 */                     out.print(FormatUtil.getString("SNMP"));
/* 3687 */                     out.write(" (V1 or V2c)");
/* 3688 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 3689 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3692 */                   if (_jspx_eval_html_005foption_005f40 != 1) {
/* 3693 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3696 */                 if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 3697 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                 }
/*      */                 
/* 3700 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 3701 */                 out.write("\n      ");
/*      */                 
/* 3703 */                 OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3704 */                 _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 3705 */                 _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3707 */                 _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 3708 */                 int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 3709 */                 if (_jspx_eval_html_005foption_005f41 != 0) {
/* 3710 */                   if (_jspx_eval_html_005foption_005f41 != 1) {
/* 3711 */                     out = _jspx_page_context.pushBody();
/* 3712 */                     _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 3713 */                     _jspx_th_html_005foption_005f41.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3716 */                     out.write(32);
/* 3717 */                     out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 3718 */                     out.write(32);
/* 3719 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 3720 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3723 */                   if (_jspx_eval_html_005foption_005f41 != 1) {
/* 3724 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3727 */                 if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 3728 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                 }
/*      */                 
/* 3731 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 3732 */                 out.write("\n      ");
/*      */                 
/* 3734 */                 OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3735 */                 _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 3736 */                 _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3738 */                 _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 3739 */                 int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 3740 */                 if (_jspx_eval_html_005foption_005f42 != 0) {
/* 3741 */                   if (_jspx_eval_html_005foption_005f42 != 1) {
/* 3742 */                     out = _jspx_page_context.pushBody();
/* 3743 */                     _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 3744 */                     _jspx_th_html_005foption_005f42.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3747 */                     out.print(FormatUtil.getString("Script Monitor"));
/* 3748 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 3749 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3752 */                   if (_jspx_eval_html_005foption_005f42 != 1) {
/* 3753 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3756 */                 if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 3757 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                 }
/*      */                 
/* 3760 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 3761 */                 out.write("\n       ");
/*      */ 
/*      */               }
/* 3764 */               else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */               {
/*      */ 
/* 3767 */                 out.write("\n        ");
/*      */                 
/* 3769 */                 OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3770 */                 _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 3771 */                 _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3773 */                 _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 3774 */                 int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 3775 */                 if (_jspx_eval_html_005foption_005f43 != 0) {
/* 3776 */                   if (_jspx_eval_html_005foption_005f43 != 1) {
/* 3777 */                     out = _jspx_page_context.pushBody();
/* 3778 */                     _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 3779 */                     _jspx_th_html_005foption_005f43.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3782 */                     out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3783 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 3784 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3787 */                   if (_jspx_eval_html_005foption_005f43 != 1) {
/* 3788 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3791 */                 if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 3792 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                 }
/*      */                 
/* 3795 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 3796 */                 out.write("\n       ");
/*      */                 
/* 3798 */                 OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3799 */                 _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 3800 */                 _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3802 */                 _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 3803 */                 int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 3804 */                 if (_jspx_eval_html_005foption_005f44 != 0) {
/* 3805 */                   if (_jspx_eval_html_005foption_005f44 != 1) {
/* 3806 */                     out = _jspx_page_context.pushBody();
/* 3807 */                     _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 3808 */                     _jspx_th_html_005foption_005f44.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3811 */                     out.print(FormatUtil.getString("Microsoft .NET"));
/* 3812 */                     out.write(32);
/* 3813 */                     out.write(32);
/* 3814 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 3815 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3818 */                   if (_jspx_eval_html_005foption_005f44 != 1) {
/* 3819 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3822 */                 if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 3823 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                 }
/*      */                 
/* 3826 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 3827 */                 out.write("\n      ");
/*      */                 
/* 3829 */                 OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3830 */                 _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 3831 */                 _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3833 */                 _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 3834 */                 int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 3835 */                 if (_jspx_eval_html_005foption_005f45 != 0) {
/* 3836 */                   if (_jspx_eval_html_005foption_005f45 != 1) {
/* 3837 */                     out = _jspx_page_context.pushBody();
/* 3838 */                     _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 3839 */                     _jspx_th_html_005foption_005f45.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3842 */                     out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 3843 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 3844 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3847 */                   if (_jspx_eval_html_005foption_005f45 != 1) {
/* 3848 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3851 */                 if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 3852 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                 }
/*      */                 
/* 3855 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 3856 */                 out.write("\n      ");
/*      */                 
/* 3858 */                 OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3859 */                 _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 3860 */                 _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3862 */                 _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 3863 */                 int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 3864 */                 if (_jspx_eval_html_005foption_005f46 != 0) {
/* 3865 */                   if (_jspx_eval_html_005foption_005f46 != 1) {
/* 3866 */                     out = _jspx_page_context.pushBody();
/* 3867 */                     _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 3868 */                     _jspx_th_html_005foption_005f46.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3871 */                     out.print(FormatUtil.getString("Exchange Server"));
/* 3872 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 3873 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3876 */                   if (_jspx_eval_html_005foption_005f46 != 1) {
/* 3877 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3880 */                 if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 3881 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                 }
/*      */                 
/* 3884 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 3885 */                 out.write("\n\t  ");
/*      */                 
/* 3887 */                 OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3888 */                 _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 3889 */                 _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3891 */                 _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 3892 */                 int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 3893 */                 if (_jspx_eval_html_005foption_005f47 != 0) {
/* 3894 */                   if (_jspx_eval_html_005foption_005f47 != 1) {
/* 3895 */                     out = _jspx_page_context.pushBody();
/* 3896 */                     _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 3897 */                     _jspx_th_html_005foption_005f47.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3900 */                     out.write(32);
/* 3901 */                     out.print(FormatUtil.getString("IIS Server"));
/* 3902 */                     out.write(32);
/* 3903 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 3904 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3907 */                   if (_jspx_eval_html_005foption_005f47 != 1) {
/* 3908 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3911 */                 if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 3912 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                 }
/*      */                 
/* 3915 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 3916 */                 out.write("\n      ");
/*      */                 
/* 3918 */                 OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3919 */                 _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 3920 */                 _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3922 */                 _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 3923 */                 int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 3924 */                 if (_jspx_eval_html_005foption_005f48 != 0) {
/* 3925 */                   if (_jspx_eval_html_005foption_005f48 != 1) {
/* 3926 */                     out = _jspx_page_context.pushBody();
/* 3927 */                     _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 3928 */                     _jspx_th_html_005foption_005f48.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3931 */                     out.write(32);
/* 3932 */                     out.print(FormatUtil.getString("Service Monitoring"));
/* 3933 */                     out.write(32);
/* 3934 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 3935 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3938 */                   if (_jspx_eval_html_005foption_005f48 != 1) {
/* 3939 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3942 */                 if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 3943 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                 }
/*      */                 
/* 3946 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 3947 */                 out.write("\n\t  ");
/*      */                 
/* 3949 */                 OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3950 */                 _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 3951 */                 _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3953 */                 _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 3954 */                 int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 3955 */                 if (_jspx_eval_html_005foption_005f49 != 0) {
/* 3956 */                   if (_jspx_eval_html_005foption_005f49 != 1) {
/* 3957 */                     out = _jspx_page_context.pushBody();
/* 3958 */                     _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 3959 */                     _jspx_th_html_005foption_005f49.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3962 */                     out.print(FormatUtil.getString("SNMP"));
/* 3963 */                     out.write(" (V1 or V2c)");
/* 3964 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 3965 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3968 */                   if (_jspx_eval_html_005foption_005f49 != 1) {
/* 3969 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3972 */                 if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 3973 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                 }
/*      */                 
/* 3976 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 3977 */                 out.write("\n      ");
/*      */                 
/* 3979 */                 OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3980 */                 _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 3981 */                 _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 3983 */                 _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 3984 */                 int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 3985 */                 if (_jspx_eval_html_005foption_005f50 != 0) {
/* 3986 */                   if (_jspx_eval_html_005foption_005f50 != 1) {
/* 3987 */                     out = _jspx_page_context.pushBody();
/* 3988 */                     _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 3989 */                     _jspx_th_html_005foption_005f50.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3992 */                     out.print(FormatUtil.getString("Script Monitor"));
/* 3993 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 3994 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3997 */                   if (_jspx_eval_html_005foption_005f50 != 1) {
/* 3998 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4001 */                 if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4002 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                 }
/*      */                 
/* 4005 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4006 */                 out.write(10);
/*      */ 
/*      */               }
/* 4009 */               else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */               {
/*      */ 
/* 4012 */                 out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4013 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4014 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 4016 */                 OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4017 */                 _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4018 */                 _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4020 */                 _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4021 */                 int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4022 */                 if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4023 */                   if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4024 */                     out = _jspx_page_context.pushBody();
/* 4025 */                     _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4026 */                     _jspx_th_html_005foption_005f51.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4029 */                     out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4030 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4031 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4034 */                   if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4035 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4038 */                 if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4039 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                 }
/*      */                 
/* 4042 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4043 */                 out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4044 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4045 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 4047 */                 OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4048 */                 _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4049 */                 _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4051 */                 _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4052 */                 int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4053 */                 if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4054 */                   if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4055 */                     out = _jspx_page_context.pushBody();
/* 4056 */                     _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4057 */                     _jspx_th_html_005foption_005f52.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4060 */                     out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4061 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4062 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4065 */                   if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4066 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4069 */                 if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4070 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                 }
/*      */                 
/* 4073 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4074 */                 out.write("\n\t\t\t");
/*      */                 
/* 4076 */                 OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4077 */                 _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4078 */                 _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4080 */                 _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4081 */                 int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4082 */                 if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4083 */                   if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4084 */                     out = _jspx_page_context.pushBody();
/* 4085 */                     _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4086 */                     _jspx_th_html_005foption_005f53.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4089 */                     out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4090 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4091 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4094 */                   if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4095 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4098 */                 if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4099 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                 }
/*      */                 
/* 4102 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4103 */                 out.write("\n\t\t\t");
/*      */                 
/* 4105 */                 OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4106 */                 _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4107 */                 _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4109 */                 _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4110 */                 int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4111 */                 if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4112 */                   if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4113 */                     out = _jspx_page_context.pushBody();
/* 4114 */                     _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4115 */                     _jspx_th_html_005foption_005f54.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4118 */                     out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4119 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4120 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4123 */                   if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4124 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4127 */                 if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4128 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                 }
/*      */                 
/* 4131 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4132 */                 out.write("\n\t\t\t");
/*      */                 
/* 4134 */                 OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4135 */                 _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4136 */                 _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4138 */                 _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4139 */                 int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4140 */                 if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4141 */                   if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4142 */                     out = _jspx_page_context.pushBody();
/* 4143 */                     _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4144 */                     _jspx_th_html_005foption_005f55.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4147 */                     out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4148 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4149 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4152 */                   if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4153 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4156 */                 if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4157 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                 }
/*      */                 
/* 4160 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4161 */                 out.write("\n\t\t\t");
/*      */                 
/* 4163 */                 OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4164 */                 _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4165 */                 _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4167 */                 _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4168 */                 int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4169 */                 if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4170 */                   if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4171 */                     out = _jspx_page_context.pushBody();
/* 4172 */                     _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4173 */                     _jspx_th_html_005foption_005f56.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4176 */                     out.print(FormatUtil.getString("Sybase"));
/* 4177 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4178 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4181 */                   if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4182 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4185 */                 if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4186 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                 }
/*      */                 
/* 4189 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4190 */                 out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4191 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4192 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 4194 */                 OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4195 */                 _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4196 */                 _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4198 */                 _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4199 */                 int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4200 */                 if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4201 */                   if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4202 */                     out = _jspx_page_context.pushBody();
/* 4203 */                     _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4204 */                     _jspx_th_html_005foption_005f57.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4207 */                     out.write(32);
/* 4208 */                     out.print(FormatUtil.getString("Service Monitoring"));
/* 4209 */                     out.write(32);
/* 4210 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4211 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4214 */                   if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4215 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4218 */                 if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4219 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                 }
/*      */                 
/* 4222 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4223 */                 out.write("\n\t\t\t");
/*      */                 
/* 4225 */                 OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4226 */                 _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4227 */                 _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4229 */                 _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4230 */                 int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4231 */                 if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4232 */                   if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4233 */                     out = _jspx_page_context.pushBody();
/* 4234 */                     _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4235 */                     _jspx_th_html_005foption_005f58.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4238 */                     out.print(FormatUtil.getString("SNMP"));
/* 4239 */                     out.write(" (V1 or V2c)");
/* 4240 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4241 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4244 */                   if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4245 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4248 */                 if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4249 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                 }
/*      */                 
/* 4252 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4253 */                 out.write("</optgroup>");
/* 4254 */                 out.write("\n\t\t\t<optgroup label=\"");
/* 4255 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4256 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 4258 */                 OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4259 */                 _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4260 */                 _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4262 */                 _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4263 */                 int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4264 */                 if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4265 */                   if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4266 */                     out = _jspx_page_context.pushBody();
/* 4267 */                     _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4268 */                     _jspx_th_html_005foption_005f59.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4271 */                     out.print(FormatUtil.getString("HTTP-URLs"));
/* 4272 */                     out.write(32);
/* 4273 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4274 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4277 */                   if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4278 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4281 */                 if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4282 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                 }
/*      */                 
/* 4285 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4286 */                 out.write("\n\t\t\t");
/*      */                 
/* 4288 */                 OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4289 */                 _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4290 */                 _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4292 */                 _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4293 */                 int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4294 */                 if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4295 */                   if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4296 */                     out = _jspx_page_context.pushBody();
/* 4297 */                     _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4298 */                     _jspx_th_html_005foption_005f60.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4301 */                     out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4302 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4303 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4306 */                   if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4307 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4310 */                 if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4311 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                 }
/*      */                 
/* 4314 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 4315 */                 out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4316 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4317 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 4319 */                 OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4320 */                 _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 4321 */                 _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 4323 */                 _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 4324 */                 int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 4325 */                 if (_jspx_eval_html_005foption_005f61 != 0) {
/* 4326 */                   if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4327 */                     out = _jspx_page_context.pushBody();
/* 4328 */                     _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 4329 */                     _jspx_th_html_005foption_005f61.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 4332 */                     out.print(FormatUtil.getString("Script Monitor"));
/* 4333 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 4334 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 4337 */                   if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4338 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 4341 */                 if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 4342 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                 }
/*      */                 
/* 4345 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 4346 */                 out.write(10);
/* 4347 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 4351 */               out.write("\n\n\n\n      ");
/* 4352 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4353 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4356 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4357 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4360 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4361 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/* 4364 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4365 */           out.write("\n                      \n      ");
/* 4366 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4367 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4371 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4372 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/* 4375 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4376 */         out.write("\n      ");
/* 4377 */         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */           return;
/* 4379 */         out.write("\n      </td>\n      \n      ");
/* 4380 */         if (request.getParameter("type") != null) {
/* 4381 */           isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 4382 */           String restype = request.getParameter("type");
/* 4383 */           if (restype.indexOf(":") != -1) {
/* 4384 */             restype = restype.substring(0, restype.indexOf(":"));
/*      */           }
/* 4386 */           if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 4387 */             out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 4388 */             out.print(restype);
/* 4389 */             out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 4390 */             out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 4391 */             out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */           }
/*      */         }
/* 4394 */         out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 4395 */         out.write(32);
/* 4396 */         out.write(32);
/*      */       }
/* 4398 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4399 */         out = _jspx_out;
/* 4400 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4401 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 4402 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4405 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4411 */     PageContext pageContext = _jspx_page_context;
/* 4412 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4414 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4415 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4416 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 4418 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 4419 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4420 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4422 */         out.write("\n      ");
/* 4423 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4424 */           return true;
/* 4425 */         out.write("\n      ");
/* 4426 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4427 */           return true;
/* 4428 */         out.write("\n      ");
/* 4429 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4430 */           return true;
/* 4431 */         out.write("\n      ");
/* 4432 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4433 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4437 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4438 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4439 */       return true;
/*      */     }
/* 4441 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4447 */     PageContext pageContext = _jspx_page_context;
/* 4448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4450 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4451 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4452 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 4453 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4454 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4456 */         out.write("\n        ");
/* 4457 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4458 */           return true;
/* 4459 */         out.write("\n        ");
/* 4460 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4461 */           return true;
/* 4462 */         out.write("\n\n        ");
/* 4463 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 4464 */           return true;
/* 4465 */         out.write("\n      ");
/* 4466 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4467 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4471 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4472 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4473 */       return true;
/*      */     }
/* 4475 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4481 */     PageContext pageContext = _jspx_page_context;
/* 4482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4484 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4485 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4486 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4488 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 4489 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4490 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 4492 */         out.write("\n          ");
/* 4493 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 4494 */           return true;
/* 4495 */         out.write("\n        ");
/* 4496 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4501 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4502 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4503 */       return true;
/*      */     }
/* 4505 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4511 */     PageContext pageContext = _jspx_page_context;
/* 4512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4514 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4515 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4516 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4518 */     _jspx_th_c_005fout_005f0.setValue("Web Transaction Monitor");
/* 4519 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4520 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4521 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4522 */       return true;
/*      */     }
/* 4524 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4530 */     PageContext pageContext = _jspx_page_context;
/* 4531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4533 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4534 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 4535 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4537 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 4538 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 4539 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 4541 */         out.write("\n          ");
/* 4542 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 4543 */           return true;
/* 4544 */         out.write("\n        ");
/* 4545 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 4546 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4550 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 4551 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 4552 */       return true;
/*      */     }
/* 4554 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 4555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4560 */     PageContext pageContext = _jspx_page_context;
/* 4561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4563 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4564 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4565 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 4567 */     _jspx_th_c_005fout_005f1.setValue("Tomcat Server");
/* 4568 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4569 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4570 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4571 */       return true;
/*      */     }
/* 4573 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4574 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4579 */     PageContext pageContext = _jspx_page_context;
/* 4580 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4582 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4583 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4584 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 4585 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4586 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 4588 */         out.write("\n         ");
/* 4589 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 4590 */           return true;
/* 4591 */         out.write("\n        ");
/* 4592 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4593 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4597 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4598 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4599 */       return true;
/*      */     }
/* 4601 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4607 */     PageContext pageContext = _jspx_page_context;
/* 4608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4610 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 4611 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4612 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4614 */     _jspx_th_c_005fout_005f2.setValue("${param.type}");
/* 4615 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4616 */     if (_jspx_eval_c_005fout_005f2 != 0) {
/* 4617 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 4618 */         out = _jspx_page_context.pushBody();
/* 4619 */         _jspx_th_c_005fout_005f2.setBodyContent((BodyContent)out);
/* 4620 */         _jspx_th_c_005fout_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4623 */         out.write(45);
/* 4624 */         int evalDoAfterBody = _jspx_th_c_005fout_005f2.doAfterBody();
/* 4625 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4628 */       if (_jspx_eval_c_005fout_005f2 != 1) {
/* 4629 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4632 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 4634 */       return true;
/*      */     }
/* 4636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f2);
/* 4637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4642 */     PageContext pageContext = _jspx_page_context;
/* 4643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4645 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4646 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4647 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4649 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 4650 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4651 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4652 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4653 */       return true;
/*      */     }
/* 4655 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4661 */     PageContext pageContext = _jspx_page_context;
/* 4662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4664 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 4665 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 4666 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4668 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 4669 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 4670 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 4671 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4672 */       return true;
/*      */     }
/* 4674 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 4675 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\newresourcetypes_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */