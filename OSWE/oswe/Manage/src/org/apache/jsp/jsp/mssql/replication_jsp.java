/*      */ package org.apache.jsp.jsp.mssql;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DataCollectionComponent;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ 
/*      */ public final class replication_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   55 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   58 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   59 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   60 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   67 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   72 */     ArrayList list = null;
/*   73 */     StringBuffer sbf = new StringBuffer();
/*   74 */     ManagedApplication mo = new ManagedApplication();
/*   75 */     if (distinct)
/*      */     {
/*   77 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   81 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   84 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   86 */       ArrayList row = (ArrayList)list.get(i);
/*   87 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   88 */       if (distinct) {
/*   89 */         sbf.append(row.get(0));
/*      */       } else
/*   91 */         sbf.append(row.get(1));
/*   92 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   95 */     return sbf.toString(); }
/*      */   
/*   97 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  100 */     if (severity == null)
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  104 */     if (severity.equals("5"))
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  108 */     if (severity.equals("1"))
/*      */     {
/*  110 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  115 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  122 */     if (severity == null)
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("1"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("4"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  134 */     if (severity.equals("5"))
/*      */     {
/*  136 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  141 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  147 */     if (severity == null)
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  151 */     if (severity.equals("5"))
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  155 */     if (severity.equals("1"))
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  161 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  167 */     if (severity == null)
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  171 */     if (severity.equals("1"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  175 */     if (severity.equals("4"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  179 */     if (severity.equals("5"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  185 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  191 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  197 */     if (severity == 5)
/*      */     {
/*  199 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  201 */     if (severity == 1)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  208 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  214 */     if (severity == null)
/*      */     {
/*  216 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  218 */     if (severity.equals("5"))
/*      */     {
/*  220 */       if (isAvailability) {
/*  221 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  224 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  227 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  229 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  231 */     if (severity.equals("1"))
/*      */     {
/*  233 */       if (isAvailability) {
/*  234 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  237 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  244 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  251 */     if (severity == null)
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("5"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("4"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  263 */     if (severity.equals("1"))
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  270 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  276 */     if (severity == null)
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("5"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("4"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("1"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  295 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  302 */     if (severity == null)
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  306 */     if (severity.equals("5"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  310 */     if (severity.equals("4"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  314 */     if (severity.equals("1"))
/*      */     {
/*  316 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  321 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  329 */     StringBuffer out = new StringBuffer();
/*  330 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  331 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  332 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  333 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  334 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  335 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  336 */     out.append("</tr>");
/*  337 */     out.append("</form></table>");
/*  338 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  345 */     if (val == null)
/*      */     {
/*  347 */       return "-";
/*      */     }
/*      */     
/*  350 */     String ret = FormatUtil.formatNumber(val);
/*  351 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  352 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  355 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  359 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  367 */     StringBuffer out = new StringBuffer();
/*  368 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  369 */     out.append("<tr>");
/*  370 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  372 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  374 */     out.append("</tr>");
/*  375 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  379 */       if (j % 2 == 0)
/*      */       {
/*  381 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  385 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  388 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  390 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  393 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  397 */       out.append("</tr>");
/*      */     }
/*  399 */     out.append("</table>");
/*  400 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  401 */     out.append("<tr>");
/*  402 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  403 */     out.append("</tr>");
/*  404 */     out.append("</table>");
/*  405 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  411 */     StringBuffer out = new StringBuffer();
/*  412 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  413 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  418 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  419 */     out.append("</tr>");
/*  420 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  423 */       out.append("<tr>");
/*  424 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  425 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  426 */       out.append("</tr>");
/*      */     }
/*      */     
/*  429 */     out.append("</table>");
/*  430 */     out.append("</table>");
/*  431 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  436 */     if (severity.equals("0"))
/*      */     {
/*  438 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  442 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  449 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  462 */     StringBuffer out = new StringBuffer();
/*  463 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  464 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  466 */       out.append("<tr>");
/*  467 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  468 */       out.append("</tr>");
/*      */       
/*      */ 
/*  471 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  473 */         String borderclass = "";
/*      */         
/*      */ 
/*  476 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  478 */         out.append("<tr>");
/*      */         
/*  480 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  481 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  482 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  488 */     out.append("</table><br>");
/*  489 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  490 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  492 */       List sLinks = secondLevelOfLinks[0];
/*  493 */       List sText = secondLevelOfLinks[1];
/*  494 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  497 */         out.append("<tr>");
/*  498 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  499 */         out.append("</tr>");
/*  500 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  502 */           String borderclass = "";
/*      */           
/*      */ 
/*  505 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  507 */           out.append("<tr>");
/*      */           
/*  509 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  510 */           if (sLinks.get(i).toString().length() == 0) {
/*  511 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  514 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  516 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  520 */     out.append("</table>");
/*  521 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  528 */     StringBuffer out = new StringBuffer();
/*  529 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  530 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  532 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  534 */         out.append("<tr>");
/*  535 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  536 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  540 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  542 */           String borderclass = "";
/*      */           
/*      */ 
/*  545 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  547 */           out.append("<tr>");
/*      */           
/*  549 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  550 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  551 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  554 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  557 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  562 */     out.append("</table><br>");
/*  563 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  564 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  566 */       List sLinks = secondLevelOfLinks[0];
/*  567 */       List sText = secondLevelOfLinks[1];
/*  568 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  571 */         out.append("<tr>");
/*  572 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  573 */         out.append("</tr>");
/*  574 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  576 */           String borderclass = "";
/*      */           
/*      */ 
/*  579 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  581 */           out.append("<tr>");
/*      */           
/*  583 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  584 */           if (sLinks.get(i).toString().length() == 0) {
/*  585 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  588 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  590 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  594 */     out.append("</table>");
/*  595 */     return out.toString();
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
/*  608 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  617 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  620 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  623 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  626 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  629 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  637 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  642 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  647 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  652 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  657 */     if (val != null)
/*      */     {
/*  659 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  663 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  668 */     if (val == null) {
/*  669 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  673 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  678 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  684 */     if (val != null)
/*      */     {
/*  686 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  690 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  696 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  701 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  705 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  710 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  715 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  720 */     String hostaddress = "";
/*  721 */     String ip = request.getHeader("x-forwarded-for");
/*  722 */     if (ip == null)
/*  723 */       ip = request.getRemoteAddr();
/*  724 */     InetAddress add = null;
/*  725 */     if (ip.equals("127.0.0.1")) {
/*  726 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  730 */       add = InetAddress.getByName(ip);
/*      */     }
/*  732 */     hostaddress = add.getHostName();
/*  733 */     if (hostaddress.indexOf('.') != -1) {
/*  734 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  735 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  739 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  744 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  750 */     if (severity == null)
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  754 */     if (severity.equals("5"))
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  758 */     if (severity.equals("1"))
/*      */     {
/*  760 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  765 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  770 */     ResultSet set = null;
/*  771 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  772 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  774 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  775 */       if (set.next()) { String str1;
/*  776 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  777 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  780 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  785 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  788 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  790 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  794 */     StringBuffer rca = new StringBuffer();
/*  795 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  796 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  799 */     int rcalength = key.length();
/*  800 */     String split = "6. ";
/*  801 */     int splitPresent = key.indexOf(split);
/*  802 */     String div1 = "";String div2 = "";
/*  803 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  805 */       if (rcalength > 180) {
/*  806 */         rca.append("<span class=\"rca-critical-text\">");
/*  807 */         getRCATrimmedText(key, rca);
/*  808 */         rca.append("</span>");
/*      */       } else {
/*  810 */         rca.append("<span class=\"rca-critical-text\">");
/*  811 */         rca.append(key);
/*  812 */         rca.append("</span>");
/*      */       }
/*  814 */       return rca.toString();
/*      */     }
/*  816 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  817 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  818 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  819 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  820 */     getRCATrimmedText(div1, rca);
/*  821 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  824 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  825 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  826 */     getRCATrimmedText(div2, rca);
/*  827 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  829 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  834 */     String[] st = msg.split("<br>");
/*  835 */     for (int i = 0; i < st.length; i++) {
/*  836 */       String s = st[i];
/*  837 */       if (s.length() > 180) {
/*  838 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  840 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  844 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  845 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  847 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  851 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  852 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  853 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  856 */       if (key == null) {
/*  857 */         return ret;
/*      */       }
/*      */       
/*  860 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  861 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  864 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  865 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  866 */       set = AMConnectionPool.executeQueryStmt(query);
/*  867 */       if (set.next())
/*      */       {
/*  869 */         String helpLink = set.getString("LINK");
/*  870 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  873 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  879 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  898 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  889 */         if (set != null) {
/*  890 */           AMConnectionPool.closeStatement(set);
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
/*  904 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  905 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  907 */       String entityStr = (String)keys.nextElement();
/*  908 */       String mmessage = temp.getProperty(entityStr);
/*  909 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  910 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  912 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  918 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  919 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  921 */       String entityStr = (String)keys.nextElement();
/*  922 */       String mmessage = temp.getProperty(entityStr);
/*  923 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  924 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  926 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  931 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  941 */     String des = new String();
/*  942 */     while (str.indexOf(find) != -1) {
/*  943 */       des = des + str.substring(0, str.indexOf(find));
/*  944 */       des = des + replace;
/*  945 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  947 */     des = des + str;
/*  948 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  955 */       if (alert == null)
/*      */       {
/*  957 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  959 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  961 */         return "&nbsp;";
/*      */       }
/*      */       
/*  964 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  966 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  969 */       int rcalength = test.length();
/*  970 */       if (rcalength < 300)
/*      */       {
/*  972 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  976 */       StringBuffer out = new StringBuffer();
/*  977 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  978 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  979 */       out.append("</div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  981 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  982 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  987 */       ex.printStackTrace();
/*      */     }
/*  989 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  995 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1000 */     ArrayList attribIDs = new ArrayList();
/* 1001 */     ArrayList resIDs = new ArrayList();
/* 1002 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1004 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1006 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1008 */       String resourceid = "";
/* 1009 */       String resourceType = "";
/* 1010 */       if (type == 2) {
/* 1011 */         resourceid = (String)row.get(0);
/* 1012 */         resourceType = (String)row.get(3);
/*      */       }
/* 1014 */       else if (type == 3) {
/* 1015 */         resourceid = (String)row.get(0);
/* 1016 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1019 */         resourceid = (String)row.get(6);
/* 1020 */         resourceType = (String)row.get(7);
/*      */       }
/* 1022 */       resIDs.add(resourceid);
/* 1023 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1024 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1026 */       String healthentity = null;
/* 1027 */       String availentity = null;
/* 1028 */       if (healthid != null) {
/* 1029 */         healthentity = resourceid + "_" + healthid;
/* 1030 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1033 */       if (availid != null) {
/* 1034 */         availentity = resourceid + "_" + availid;
/* 1035 */         entitylist.add(availentity);
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
/* 1049 */     Properties alert = getStatus(entitylist);
/* 1050 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1055 */     int size = monitorList.size();
/*      */     
/* 1057 */     String[] severity = new String[size];
/*      */     
/* 1059 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1061 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1062 */       String resourceName1 = (String)row1.get(7);
/* 1063 */       String resourceid1 = (String)row1.get(6);
/* 1064 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1065 */       if (severity[j] == null)
/*      */       {
/* 1067 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1071 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1073 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1075 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1078 */         if (sev > 0) {
/* 1079 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1080 */           monitorList.set(k, monitorList.get(j));
/* 1081 */           monitorList.set(j, t);
/* 1082 */           String temp = severity[k];
/* 1083 */           severity[k] = severity[j];
/* 1084 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1090 */     int z = 0;
/* 1091 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1094 */       int i = 0;
/* 1095 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1098 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1102 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1106 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1108 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1111 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1115 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1118 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1119 */       String resourceName1 = (String)row1.get(7);
/* 1120 */       String resourceid1 = (String)row1.get(6);
/* 1121 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1122 */       if (hseverity[j] == null)
/*      */       {
/* 1124 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1129 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1131 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1134 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1137 */         if (hsev > 0) {
/* 1138 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1139 */           monitorList.set(k, monitorList.get(j));
/* 1140 */           monitorList.set(j, t);
/* 1141 */           String temp1 = hseverity[k];
/* 1142 */           hseverity[k] = hseverity[j];
/* 1143 */           hseverity[j] = temp1;
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
/* 1155 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1156 */     boolean forInventory = false;
/* 1157 */     String trdisplay = "none";
/* 1158 */     String plusstyle = "inline";
/* 1159 */     String minusstyle = "none";
/* 1160 */     String haidTopLevel = "";
/* 1161 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1163 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1165 */         haidTopLevel = request.getParameter("haid");
/* 1166 */         forInventory = true;
/* 1167 */         trdisplay = "table-row;";
/* 1168 */         plusstyle = "none";
/* 1169 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1176 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1179 */     ArrayList listtoreturn = new ArrayList();
/* 1180 */     StringBuffer toreturn = new StringBuffer();
/* 1181 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1182 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1183 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1185 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1187 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1188 */       String childresid = (String)singlerow.get(0);
/* 1189 */       String childresname = (String)singlerow.get(1);
/* 1190 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1191 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1192 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1193 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1194 */       String unmanagestatus = (String)singlerow.get(5);
/* 1195 */       String actionstatus = (String)singlerow.get(6);
/* 1196 */       String linkclass = "monitorgp-links";
/* 1197 */       String titleforres = childresname;
/* 1198 */       String titilechildresname = childresname;
/* 1199 */       String childimg = "/images/trcont.png";
/* 1200 */       String flag = "enable";
/* 1201 */       String dcstarted = (String)singlerow.get(8);
/* 1202 */       String configMonitor = "";
/* 1203 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1204 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1206 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1208 */       if (singlerow.get(7) != null)
/*      */       {
/* 1210 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1212 */       String haiGroupType = "0";
/* 1213 */       if ("HAI".equals(childtype))
/*      */       {
/* 1215 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1217 */       childimg = "/images/trend.png";
/* 1218 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1219 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1220 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1222 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1224 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1226 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1227 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1230 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1232 */         linkclass = "disabledtext";
/* 1233 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1235 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1236 */       String availmouseover = "";
/* 1237 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1239 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1241 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1242 */       String healthmouseover = "";
/* 1243 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1245 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1248 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1249 */       int spacing = 0;
/* 1250 */       if (level >= 1)
/*      */       {
/* 1252 */         spacing = 40 * level;
/*      */       }
/* 1254 */       if (childtype.equals("HAI"))
/*      */       {
/* 1256 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1257 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1258 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1260 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1261 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1262 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1263 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1264 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1265 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1266 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1267 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1268 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1269 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1270 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1272 */         if (!forInventory)
/*      */         {
/* 1274 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1277 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1279 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1281 */           actions = editlink + actions;
/*      */         }
/* 1283 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1285 */           actions = actions + associatelink;
/*      */         }
/* 1287 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1288 */         String arrowimg = "";
/* 1289 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1291 */           actions = "";
/* 1292 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1293 */           checkbox = "";
/* 1294 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1296 */         if (isIt360)
/*      */         {
/* 1298 */           actionimg = "";
/* 1299 */           actions = "";
/* 1300 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1301 */           checkbox = "";
/*      */         }
/*      */         
/* 1304 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1306 */           actions = "";
/*      */         }
/* 1308 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1310 */           checkbox = "";
/*      */         }
/*      */         
/* 1313 */         String resourcelink = "";
/*      */         
/* 1315 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1317 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1321 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1324 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1330 */         if (!isIt360)
/*      */         {
/* 1332 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1336 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1339 */         toreturn.append("</tr>");
/* 1340 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1342 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1343 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1347 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1348 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1351 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1355 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1357 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1359 */             toreturn.append(assocMessage);
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1369 */         String resourcelink = null;
/* 1370 */         boolean hideEditLink = false;
/* 1371 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1373 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1374 */           hideEditLink = true;
/* 1375 */           if (isIt360)
/*      */           {
/* 1377 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1381 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1383 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1385 */           hideEditLink = true;
/* 1386 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1387 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1392 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1395 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1396 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1397 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1398 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1399 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1400 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1401 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1402 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1403 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1404 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1405 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1406 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1407 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1409 */         if (hideEditLink)
/*      */         {
/* 1411 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1413 */         if (!forInventory)
/*      */         {
/* 1415 */           removefromgroup = "";
/*      */         }
/* 1417 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1418 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1419 */           actions = actions + configcustomfields;
/*      */         }
/* 1421 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1423 */           actions = editlink + actions;
/*      */         }
/* 1425 */         String managedLink = "";
/* 1426 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1428 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1429 */           actions = "";
/* 1430 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1431 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1434 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1436 */           checkbox = "";
/*      */         }
/*      */         
/* 1439 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1441 */           actions = "";
/*      */         }
/* 1443 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1446 */         if (isIt360)
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1456 */         if (!isIt360)
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1464 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1467 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1474 */       StringBuilder toreturn = new StringBuilder();
/* 1475 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1476 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1477 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1478 */       String title = "";
/* 1479 */       message = EnterpriseUtil.decodeString(message);
/* 1480 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1481 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1482 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1484 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1486 */       else if ("5".equals(severity))
/*      */       {
/* 1488 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1492 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1494 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1495 */       toreturn.append(v);
/*      */       
/* 1497 */       toreturn.append(link);
/* 1498 */       if (severity == null)
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("5"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("4"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       else if (severity.equals("1"))
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1517 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1519 */       toreturn.append("</a>");
/* 1520 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1524 */       ex.printStackTrace();
/*      */     }
/* 1526 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1533 */       StringBuilder toreturn = new StringBuilder();
/* 1534 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1535 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1536 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1537 */       if (message == null)
/*      */       {
/* 1539 */         message = "";
/*      */       }
/*      */       
/* 1542 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1543 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1545 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1546 */       toreturn.append(v);
/*      */       
/* 1548 */       toreturn.append(link);
/*      */       
/* 1550 */       if (severity == null)
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       else if (severity.equals("5"))
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       else if (severity.equals("1"))
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1565 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1567 */       toreturn.append("</a>");
/* 1568 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1574 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1577 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1578 */     if (invokeActions != null) {
/* 1579 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1580 */       while (iterator.hasNext()) {
/* 1581 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1582 */         if (actionmap.containsKey(actionid)) {
/* 1583 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1588 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1592 */     String actionLink = "";
/* 1593 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1594 */     String query = "";
/* 1595 */     ResultSet rs = null;
/* 1596 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1597 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1598 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1599 */       actionLink = "method=" + methodName;
/*      */     }
/* 1601 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1602 */       actionLink = methodName;
/*      */     }
/* 1604 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1605 */     Iterator itr = methodarglist.iterator();
/* 1606 */     boolean isfirstparam = true;
/* 1607 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1608 */     while (itr.hasNext()) {
/* 1609 */       HashMap argmap = (HashMap)itr.next();
/* 1610 */       String argtype = (String)argmap.get("TYPE");
/* 1611 */       String argname = (String)argmap.get("IDENTITY");
/* 1612 */       String paramname = (String)argmap.get("PARAMETER");
/* 1613 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1614 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1615 */         isfirstparam = false;
/* 1616 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1618 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1622 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1626 */         actionLink = actionLink + "&";
/*      */       }
/* 1628 */       String paramValue = null;
/* 1629 */       String tempargname = argname;
/* 1630 */       if (commonValues.getProperty(tempargname) != null) {
/* 1631 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1634 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1635 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1636 */           if (dbType.equals("mysql")) {
/* 1637 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1640 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1642 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1644 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1645 */             if (rs.next()) {
/* 1646 */               paramValue = rs.getString("VALUE");
/* 1647 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1651 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1655 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1658 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1663 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1664 */           paramValue = rowId;
/*      */         }
/* 1666 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1667 */           paramValue = managedObjectName;
/*      */         }
/* 1669 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1670 */           paramValue = resID;
/*      */         }
/* 1672 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1673 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1676 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1678 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1679 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1680 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1682 */     return actionLink;
/*      */   }
/*      */   
/* 1685 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1686 */     String dependentAttribute = null;
/* 1687 */     String align = "left";
/*      */     
/* 1689 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1690 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1691 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1692 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1693 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1694 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1695 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1696 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1697 */       align = "center";
/*      */     }
/*      */     
/* 1700 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1701 */     String actualdata = "";
/*      */     
/* 1703 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1704 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1705 */         actualdata = availValue;
/*      */       }
/* 1707 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1708 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1712 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1713 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1716 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1722 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1723 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1724 */       toreturn.append("<table>");
/* 1725 */       toreturn.append("<tr>");
/* 1726 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1727 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1728 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1729 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1730 */         String toolTip = "";
/* 1731 */         String hideClass = "";
/* 1732 */         String textStyle = "";
/* 1733 */         boolean isreferenced = true;
/* 1734 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1735 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1736 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1737 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1739 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1740 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1741 */           while (valueList.hasMoreTokens()) {
/* 1742 */             String dependentVal = valueList.nextToken();
/* 1743 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1744 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1745 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1747 */               toolTip = "";
/* 1748 */               hideClass = "";
/* 1749 */               isreferenced = false;
/* 1750 */               textStyle = "disabledtext";
/* 1751 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1755 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1756 */           toolTip = "";
/* 1757 */           hideClass = "";
/* 1758 */           isreferenced = false;
/* 1759 */           textStyle = "disabledtext";
/* 1760 */           if (dependentImageMap != null) {
/* 1761 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1762 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1765 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1769 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1770 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1771 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1772 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1773 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1774 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1776 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1777 */           if (isreferenced) {
/* 1778 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1782 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1783 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1784 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1785 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1786 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1787 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1789 */           toreturn.append("</span>");
/* 1790 */           toreturn.append("</a>");
/* 1791 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1794 */       toreturn.append("</tr>");
/* 1795 */       toreturn.append("</table>");
/* 1796 */       toreturn.append("</td>");
/*      */     } else {
/* 1798 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1801 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1805 */     String colTime = null;
/* 1806 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1807 */     if ((rows != null) && (rows.size() > 0)) {
/* 1808 */       Iterator<String> itr = rows.iterator();
/* 1809 */       String maxColQuery = "";
/* 1810 */       for (;;) { if (itr.hasNext()) {
/* 1811 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1812 */           ResultSet maxCol = null;
/*      */           try {
/* 1814 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1815 */             while (maxCol.next()) {
/* 1816 */               if (colTime == null) {
/* 1817 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1820 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1829 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1831 */               if (maxCol != null)
/* 1832 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1834 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1829 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1831 */               if (maxCol != null)
/* 1832 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1834 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1839 */     return colTime;
/*      */   }
/*      */   
/* 1842 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1843 */     tablename = null;
/* 1844 */     ResultSet rsTable = null;
/* 1845 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1847 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1848 */       while (rsTable.next()) {
/* 1849 */         tablename = rsTable.getString("DATATABLE");
/* 1850 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1851 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1864 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1855 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1858 */         if (rsTable != null)
/* 1859 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1861 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1867 */     String argsList = "";
/* 1868 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1870 */       if (showArgsMap.get(row) != null) {
/* 1871 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1872 */         if (showArgslist != null) {
/* 1873 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1874 */             if (argsList.trim().equals("")) {
/* 1875 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1878 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1885 */       e.printStackTrace();
/* 1886 */       return "";
/*      */     }
/* 1888 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1893 */     String argsList = "";
/* 1894 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1897 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1899 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1900 */         if (hideArgsList != null)
/*      */         {
/* 1902 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1904 */             if (argsList.trim().equals(""))
/*      */             {
/* 1906 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1910 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1918 */       ex.printStackTrace();
/*      */     }
/* 1920 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1924 */     StringBuilder toreturn = new StringBuilder();
/* 1925 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1932 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1933 */       Iterator itr = tActionList.iterator();
/* 1934 */       while (itr.hasNext()) {
/* 1935 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1936 */         String confirmmsg = "";
/* 1937 */         String link = "";
/* 1938 */         String isJSP = "NO";
/* 1939 */         HashMap tactionMap = (HashMap)itr.next();
/* 1940 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1941 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1942 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1943 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1944 */           (actionmap.containsKey(actionId))) {
/* 1945 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1946 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1947 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1948 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1949 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1951 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1957 */           if (isTableAction) {
/* 1958 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1961 */             tableName = "Link";
/* 1962 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1963 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1964 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1965 */             toreturn.append("</a></td>");
/*      */           }
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1976 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1982 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1984 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1985 */       Properties prop = (Properties)node.getUserObject();
/* 1986 */       String mgID = prop.getProperty("label");
/* 1987 */       String mgName = prop.getProperty("value");
/* 1988 */       String isParent = prop.getProperty("isParent");
/* 1989 */       int mgIDint = Integer.parseInt(mgID);
/* 1990 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1992 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1994 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1995 */       if (node.getChildCount() > 0)
/*      */       {
/* 1997 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1999 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2001 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2003 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2007 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2012 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2014 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2016 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2018 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2022 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2025 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2026 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2028 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2032 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2034 */       if (node.getChildCount() > 0)
/*      */       {
/* 2036 */         builder.append("<UL>");
/* 2037 */         printMGTree(node, builder);
/* 2038 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2043 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2044 */     StringBuffer toReturn = new StringBuffer();
/* 2045 */     String table = "-";
/*      */     try {
/* 2047 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2048 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2049 */       float total = 0.0F;
/* 2050 */       while (it.hasNext()) {
/* 2051 */         String attName = (String)it.next();
/* 2052 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2053 */         boolean roundOffData = false;
/* 2054 */         if ((data != null) && (!data.equals(""))) {
/* 2055 */           if (data.indexOf(",") != -1) {
/* 2056 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2059 */             float value = Float.parseFloat(data);
/* 2060 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2063 */             total += value;
/* 2064 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2067 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2072 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2073 */       while (attVsWidthList.hasNext()) {
/* 2074 */         String attName = (String)attVsWidthList.next();
/* 2075 */         String data = (String)attVsWidthProps.get(attName);
/* 2076 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2077 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2078 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2079 */         String className = (String)graphDetails.get("ClassName");
/* 2080 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2081 */         if (percentage < 1.0F)
/*      */         {
/* 2083 */           data = percentage + "";
/*      */         }
/* 2085 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2087 */       if (toReturn.length() > 0) {
/* 2088 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2092 */       e.printStackTrace();
/*      */     }
/* 2094 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2100 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2101 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2102 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2103 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2104 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2105 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2106 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2107 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2108 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2111 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2112 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2113 */       splitvalues[0] = multiplecondition.toString();
/* 2114 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2117 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2122 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2123 */     if (thresholdType != 3) {
/* 2124 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2125 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2126 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2127 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2128 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2129 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2131 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2132 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2133 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2134 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2135 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2136 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2138 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2139 */     if (updateSelected != null) {
/* 2140 */       updateSelected[0] = "selected";
/*      */     }
/* 2142 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2147 */       StringBuffer toreturn = new StringBuffer("");
/* 2148 */       if (commaSeparatedMsgId != null) {
/* 2149 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2150 */         int count = 0;
/* 2151 */         while (msgids.hasMoreTokens()) {
/* 2152 */           String id = msgids.nextToken();
/* 2153 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2154 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2155 */           count++;
/* 2156 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2157 */             if (toreturn.length() == 0) {
/* 2158 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2160 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2161 */             if (!image.trim().equals("")) {
/* 2162 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2164 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2165 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2168 */         if (toreturn.length() > 0) {
/* 2169 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2173 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2176 */       e.printStackTrace(); }
/* 2177 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2183 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2189 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2190 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2209 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2213 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2225 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2229 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2235 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2246 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2249 */     JspWriter out = null;
/* 2250 */     Object page = this;
/* 2251 */     JspWriter _jspx_out = null;
/* 2252 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2256 */       response.setContentType("text/html;charset=UTF-8");
/* 2257 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2259 */       _jspx_page_context = pageContext;
/* 2260 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2261 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2262 */       session = pageContext.getSession();
/* 2263 */       out = pageContext.getOut();
/* 2264 */       _jspx_out = out;
/*      */       
/* 2266 */       out.write("<!--$Id$-->\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
/*      */       
/* 2268 */       request.setAttribute("HelpKey", "Monitors MSSQL Details");
/*      */       
/* 2270 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2271 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2273 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2284 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2285 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2286 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2289 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2290 */         String available = null;
/* 2291 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2292 */         out.write(10);
/*      */         
/* 2294 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2305 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2306 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2307 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2310 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2311 */           String unavailable = null;
/* 2312 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2313 */           out.write(10);
/*      */           
/* 2315 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2326 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2327 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2328 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2331 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2332 */             String unmanaged = null;
/* 2333 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2334 */             out.write(10);
/*      */             
/* 2336 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2347 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2348 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2349 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2352 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2353 */               String scheduled = null;
/* 2354 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2355 */               out.write(10);
/*      */               
/* 2357 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2368 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2369 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2370 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2373 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2374 */                 String critical = null;
/* 2375 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2376 */                 out.write(10);
/*      */                 
/* 2378 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2389 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2390 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2391 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2394 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2395 */                   String clear = null;
/* 2396 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2397 */                   out.write(10);
/*      */                   
/* 2399 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2410 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2411 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2412 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2415 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2416 */                     String warning = null;
/* 2417 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/*      */                     
/* 2421 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2422 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write("\n \n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */                     
/* 2429 */                     String bgcolor = "class=\"whitegrayborder\"";
/* 2430 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2431 */                     String redirect = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=14";
/* 2432 */                     String encodeurl = URLEncoder.encode(redirect);
/* 2433 */                     DataCollectionComponent dcc = new DataCollectionComponent();
/* 2434 */                     ArrayList resIDs = new ArrayList();
/* 2435 */                     if (resourceid != null) {
/* 2436 */                       resIDs.add(resourceid);
/*      */                     }
/* 2438 */                     ArrayList attribIDs = new ArrayList();
/*      */                     
/* 2440 */                     attribIDs.add("8029");
/* 2441 */                     attribIDs.add("8030");
/* 2442 */                     attribIDs.add("8031");
/* 2443 */                     attribIDs.add("8032");
/* 2444 */                     attribIDs.add("8037");
/* 2445 */                     attribIDs.add("3860");
/* 2446 */                     attribIDs.add("3861");
/*      */                     try {
/* 2448 */                       HashMap mapp = null;
/* 2449 */                       ArrayList alist = null;
/* 2450 */                       Iterator tt = null;
/* 2451 */                       if (request.getAttribute("REPL-DETAILS") != null) {
/* 2452 */                         mapp = (HashMap)request.getAttribute("REPL-DETAILS");
/*      */                       }
/* 2454 */                       if (mapp.get("SQL-REPL-PUBLICATIONS") != null) {
/* 2455 */                         alist = (ArrayList)mapp.get("SQL-REPL-PUBLICATIONS");
/*      */                       }
/* 2457 */                       if ((alist != null) && (alist.size() > 0)) {
/* 2458 */                         tt = alist.iterator();
/* 2459 */                         while (tt.hasNext()) {
/* 2460 */                           Properties publMap = (Properties)tt.next();
/* 2461 */                           resIDs.add(publMap.getProperty("PUBLICATIONID"));
/*      */                         }
/*      */                       }
/* 2464 */                       if (mapp.get("SQL-REPL-TRANSACTIONALSUBSCRIPTIONS") != null) {
/* 2465 */                         alist = (ArrayList)mapp.get("SQL-REPL-TRANSACTIONALSUBSCRIPTIONS");
/*      */                       }
/* 2467 */                       if ((alist != null) && (alist.size() > 0)) {
/* 2468 */                         tt = alist.iterator();
/* 2469 */                         while (tt.hasNext()) {
/* 2470 */                           Properties publMap = (Properties)tt.next();
/* 2471 */                           resIDs.add(publMap.getProperty("SUBSCRIPTIONID"));
/*      */                         }
/*      */                       }
/* 2474 */                       if (mapp.get("SQL-REPL-SNAPSHOTSUBSCRIPTIONS") != null) {
/* 2475 */                         alist = (ArrayList)mapp.get("SQL-REPL-SNAPSHOTSUBSCRIPTIONS");
/*      */                       }
/* 2477 */                       if ((alist != null) && (alist.size() > 0)) {
/* 2478 */                         tt = alist.iterator();
/* 2479 */                         while (tt.hasNext()) {
/* 2480 */                           Properties publMap = (Properties)tt.next();
/* 2481 */                           resIDs.add(publMap.getProperty("SUBSCRIPTIONID"));
/*      */                         }
/*      */                       }
/* 2484 */                       if (mapp.get("SQL-REPL-MERGESUBSCRIPTIONS") != null) {
/* 2485 */                         alist = (ArrayList)mapp.get("SQL-REPL-MERGESUBSCRIPTIONS");
/*      */                       }
/* 2487 */                       if ((alist != null) && (alist.size() > 0)) {
/* 2488 */                         tt = alist.iterator();
/* 2489 */                         while (tt.hasNext()) {
/* 2490 */                           Properties publMap = (Properties)tt.next();
/* 2491 */                           resIDs.add(publMap.getProperty("SUBSCRIPTIONID"));
/*      */                         }
/*      */                       }
/* 2494 */                       if ((mapp != null) && (mapp.get("SQL-REPL-AGENTDETAILS") != null)) {
/* 2495 */                         alist = (ArrayList)mapp.get("SQL-REPL-AGENTDETAILS");
/*      */                       }
/* 2497 */                       if ((alist != null) && (alist.size() > 0)) {
/* 2498 */                         tt = alist.iterator();
/* 2499 */                         while (tt.hasNext()) {
/* 2500 */                           Properties publMap = (Properties)tt.next();
/* 2501 */                           resIDs.add(publMap.getProperty("RESOURCEID"));
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e) {}
/*      */                     
/* 2507 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/*      */ 
/* 2510 */                     out.write(10);
/* 2511 */                     if (dcc.getStatusforComponent(resourceid, "SQLLOGSHIPPING"))
/*      */                     {
/* 2513 */                       out.write("\n<!--");
/*      */                       
/* 2515 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2516 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2517 */                       _jspx_th_c_005fif_005f0.setParent(null);
/*      */                       
/* 2519 */                       _jspx_th_c_005fif_005f0.setTest("${empty showOnlyDatabase}");
/* 2520 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2521 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/* 2523 */                           out.write("-->\n\t<br>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td class=\"tableheading\">");
/* 2524 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.name"));
/* 2525 */                           out.write("</td>\n\t\t<td class=\"tableheading\" align=\"right\" style=\"padding-right:6px;\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2526 */                           out.print(resourceid);
/* 2527 */                           out.write("&attributeIDs=3860,3861&attributeToSelect=3860&redirectto=");
/* 2528 */                           out.print(encodeurl);
/* 2529 */                           out.write("\" class=\"staticlinks\">");
/* 2530 */                           out.print(ALERTCONFIG_TEXT);
/* 2531 */                           out.write("</a>&nbsp;<a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2532 */                           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                             return;
/* 2534 */                           out.write("&dcComponentName=SQLLOGSHIPPING\"><img align=\"absmiddle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2535 */                           out.print(FormatUtil.getString("Disable"));
/* 2536 */                           out.write("\"></a></td>\n\t</tr>\n\t</table>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n\t\t<td class=\"columnheading\" width=\"14%\">");
/* 2537 */                           out.print(FormatUtil.getString("Database Name"));
/* 2538 */                           out.write("</td>\n\t\t<td class=\"columnheading\" width=\"8%\">");
/* 2539 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.agenttype"));
/* 2540 */                           out.write("</td>\n\t\t<td class=\"columnheading\" width=\"8%\" align=\"center\">");
/* 2541 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.status"));
/* 2542 */                           out.write("&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2543 */                           out.print(resourceid);
/* 2544 */                           out.write("&attributeid=3860')\">");
/* 2545 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3860")));
/* 2546 */                           out.write("</a></td>\n\t\t<td class=\"columnheading\" width=\"10%\" align=\"center\">");
/* 2547 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.timeelapsed"));
/* 2548 */                           out.write("&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2549 */                           out.print(resourceid);
/* 2550 */                           out.write("&attributeid=3861')\">");
/* 2551 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3861")));
/* 2552 */                           out.write("</a></td>\n\t\t<td class=\"columnheading\" width=\"15%\" align=\"center\">");
/* 2553 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.logtime"));
/* 2554 */                           out.write("</td>\n\t\t<td class=\"columnheading\" width=\"15%\" align=\"center\">");
/* 2555 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.errorlogtime"));
/* 2556 */                           out.write("</td>\n\t\t<td class=\"columnheading\" width=\"30%\" align=\"center\">");
/* 2557 */                           out.print(FormatUtil.getString("am.webclient.mssql.logshippingdetails.errormessage"));
/* 2558 */                           out.write("</td>\n\t</tr>\n\t");
/*      */                           
/* 2560 */                           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2561 */                           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2562 */                           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/* 2563 */                           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2564 */                           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                             for (;;) {
/* 2566 */                               out.write(10);
/* 2567 */                               out.write(9);
/* 2568 */                               out.write(9);
/* 2569 */                               if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                                 return;
/* 2571 */                               out.write(10);
/* 2572 */                               out.write(9);
/* 2573 */                               out.write(9);
/*      */                               
/* 2575 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2576 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2577 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2578 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2579 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 2581 */                                   out.write("\n\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t<td width=\"100%\" align=\"center\" colspan=\"7\" class=\"whitegrayborder\">");
/* 2582 */                                   out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2583 */                                   out.write("</td>\n\t\t\t\t</tr>\n\t\t");
/* 2584 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2585 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2589 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2590 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 2593 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2594 */                               out.write(10);
/* 2595 */                               out.write(9);
/* 2596 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2597 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2601 */                           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2602 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                           }
/*      */                           
/* 2605 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2606 */                           out.write("\n\t</table>\n<!--");
/* 2607 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2608 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2612 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2613 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                       }
/*      */                       
/* 2616 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2617 */                       out.write("-->\n");
/*      */                     } else {
/* 2619 */                       out.write("\n\t<br>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td class=\"tableheading\" width=\"100%\">");
/* 2620 */                       out.print(FormatUtil.getString("Log Shipping Status"));
/* 2621 */                       out.write("</td>\n\t</tr>\n\t</table>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr  height=\"45\" >\n\t\t<td class=\"whitegrayborder\" align=\"center\">");
/*      */                       
/* 2623 */                       org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2624 */                       _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2625 */                       _jspx_th_bean_005fmessage_005f0.setParent(null);
/*      */                       
/* 2627 */                       _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.mssqllogshipping.configure");
/*      */                       
/* 2629 */                       _jspx_th_bean_005fmessage_005f0.setArg0(resourceid);
/* 2630 */                       int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 2631 */                       if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 2632 */                         this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                       }
/*      */                       
/* 2635 */                       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 2636 */                       out.write(" </td>\n\t</tr>\n</table>\n");
/*      */                     }
/* 2638 */                     out.write("\n<br>\n<!--");
/*      */                     
/* 2640 */                     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2641 */                     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2642 */                     _jspx_th_c_005fif_005f5.setParent(null);
/*      */                     
/* 2644 */                     _jspx_th_c_005fif_005f5.setTest("${empty showOnlyDatabase}");
/* 2645 */                     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2646 */                     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                       for (;;) {
/* 2648 */                         out.write("-->\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t<tr>\n\t\t<td class=\"tableheadingbborder\" width=\"100%\">");
/* 2649 */                         out.print(FormatUtil.getString("am.mssql.replication.title.text"));
/* 2650 */                         out.write("</td>\n\t</tr>\n\t");
/* 2651 */                         if (!DBUtil.getGlobalConfigValueasBoolean("sqlReplMonStatus"))
/*      */                         {
/* 2653 */                           out.write("\n\t\t<tr>\n\t\t\t<td align=\"center\" class=\"bodytext\" style=\"height:25px\">");
/* 2654 */                           out.print(FormatUtil.getString("am.webclient.sqldbmanager.db.replication.disabled.text", new String[] { resourceid }));
/* 2655 */                           out.write(".</td>\n\t\t</tr>\n\t");
/*      */                         }
/*      */                         else {
/* 2658 */                           out.write("\n\t<tr>\n\t\t<td>\n\t\t\t<div id=\"replicationouter\" width=\"100%\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\t\t\n\t\t\t\t\t\t\t\t<tr class=\"columnheading-monitor\">\n\t\t\t\t\t\t\t\t\t<td class=\"btmborder\" width=\"70%\" height=\"35\"> ");
/* 2659 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.text"));
/* 2660 */                           out.write(" </td>\n\t\t\t\t\t\t\t\t\t<td class=\"btmborder\" width=\"30%\" height=\"35\" align=\"right\">\n\t\t\t\t\t\t\t\t\t\t<p class=\"switch1\"><a href=\"javascript:void(0)\"  class=\"\" onclick=\"expandAllLockForReplication(this);\">");
/* 2661 */                           out.print(FormatUtil.getString("am.mssql.replication.viewsubscriptions.text"));
/* 2662 */                           out.write("</a></p>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr height=\"45\" >\n\t\t\t\t\t\t<td colspan=\"2\" class=\"whitegrayborder\">\t\t\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> \n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">&nbsp;</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2663 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.status.text"));
/* 2664 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2665 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.publication.text"));
/* 2666 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2667 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.publicationtype.text"));
/* 2668 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2669 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.subscription.text"));
/* 2670 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2671 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.syncronizing.text"));
/* 2672 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2673 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.avgperformance.text"));
/* 2674 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">");
/* 2675 */                           out.print(FormatUtil.getString("am.mssql.replication.publications.worstperformance.text"));
/* 2676 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"columnheading\">&nbsp;</td>\t\t\t\t\t\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/*      */                           try
/*      */                           {
/* 2679 */                             HashMap mapp = (HashMap)request.getAttribute("REPL-DETAILS");
/* 2680 */                             ArrayList alist = null;
/* 2681 */                             if (mapp != null) {
/* 2682 */                               alist = (ArrayList)mapp.get("SQL-REPL-PUBLICATIONS");
/*      */                             }
/* 2684 */                             if ((alist != null) && (alist.size() > 0)) {
/* 2685 */                               Iterator tt = alist.iterator();
/* 2686 */                               while (tt.hasNext()) {
/* 2687 */                                 Properties publMap = (Properties)tt.next();
/* 2688 */                                 resIDs.add(publMap.getProperty("PUBLICATIONID"));
/* 2689 */                                 String idcd = publMap.getProperty("PUBLICATIONID") + "c";
/*      */                                 
/* 2691 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td  class=\"sqldb-data2\">\t\t\t\t\t\t\t\t\t\t \n\t\t\t\t\t\t\t\t\t\t<h3 class=\"expand1\" id=\"");
/* 2692 */                                 out.print(publMap.getProperty("PUBLICATIONID"));
/* 2693 */                                 out.write("\" onclick=\"toggleLockForReplication(this);\" > ");
/* 2694 */                                 out.write("\n\t\t\t\t\t\t\t\t\t\t\t<a title=\"");
/* 2695 */                                 out.print(FormatUtil.getString("Expand/Collapse"));
/* 2696 */                                 out.write("\" href=\"javascript:void(0)\" style=\"display: block;\">&nbsp;</a>\n\t\t\t\t\t\t\t\t\t\t</h3>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2697 */                                 out.print(publMap.getProperty("STATUS"));
/* 2698 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2699 */                                 out.print(publMap.getProperty("PUBLICATION"));
/* 2700 */                                 out.write(" </td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2701 */                                 out.print(publMap.getProperty("PUBLICATIONTYPE"));
/* 2702 */                                 out.write(" </td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2703 */                                 out.print(publMap.getProperty("SUBSCRIPTIONSCOUNT"));
/* 2704 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2705 */                                 out.print(publMap.getProperty("RUNNINGDISTRIBUTIONAGENTS"));
/* 2706 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2707 */                                 out.print(publMap.getProperty("AVERAGELATENCY"));
/* 2708 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">");
/* 2709 */                                 out.print(publMap.getProperty("WORSTLATENCY"));
/* 2710 */                                 out.write("</td>\t\t\n\t\t\t\t\t\t\t\t\t");
/* 2711 */                                 if (publMap.getProperty("PUBLICATIONTYPE").toString().equals("Transactional publication")) {
/* 2712 */                                   out.write("\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">&nbsp;</td>\t\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td colspan=\"10\" >\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 2713 */                                   alist = (ArrayList)mapp.get("SQL-REPL-TRANSACTIONALSUBSCRIPTIONS");
/* 2714 */                                   if (alist != null) {
/* 2715 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2716 */                                     out.print(idcd);
/* 2717 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2718 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.serialno.text"));
/* 2719 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2720 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.runningstatus.text"));
/* 2721 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"28%\" class=\"whitegrayrightalign-reports2\">");
/* 2722 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.subscription.text"));
/* 2723 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"whitegrayrightalign-reports2\">");
/* 2724 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.publication.text"));
/* 2725 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2726 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.performance.text"));
/* 2727 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2728 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.latency.text"));
/* 2729 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2730 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.expirationstatus.text"));
/* 2731 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"4%\" class=\"whitegrayrightalign-reports2\" align=\"center\" colspan=\"2\">");
/* 2732 */                                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2733 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\"></td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2734 */                                     Iterator tt1 = alist.iterator();
/* 2735 */                                     int cntt = 0;
/* 2736 */                                     while (tt1.hasNext()) {
/* 2737 */                                       Properties subscriptionsMap = (Properties)tt1.next();
/* 2738 */                                       cntt++;cntt = cntt;
/* 2739 */                                       if (publMap.getProperty("PUBLICATION").equals(subscriptionsMap.getProperty("PUBLICATION")))
/*      */                                       {
/*      */ 
/* 2742 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class='whitegrayrightalign-reports'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2743 */                                         out.print(cntt);
/* 2744 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2745 */                                         out.print(subscriptionsMap.getProperty("RUNNINGSTATUS"));
/* 2746 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"28%\" class=\"whitegrayrightalign-reports1\">[");
/* 2747 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBER"));
/* 2748 */                                         out.write(93);
/* 2749 */                                         out.write(46);
/* 2750 */                                         out.write(91);
/* 2751 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBERDB"));
/* 2752 */                                         out.write("] </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"whitegrayrightalign-reports1\">[");
/* 2753 */                                         out.print(subscriptionsMap.getProperty("PUBLISHERDB"));
/* 2754 */                                         out.write(93);
/* 2755 */                                         out.write(46);
/* 2756 */                                         out.write(91);
/* 2757 */                                         out.print(subscriptionsMap.getProperty("PUBLICATION"));
/* 2758 */                                         out.write("]</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2759 */                                         out.print(subscriptionsMap.getProperty("PERFORMANCE"));
/* 2760 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2761 */                                         out.print(subscriptionsMap.getProperty("CURRENTLATENCY"));
/* 2762 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2763 */                                         out.print(subscriptionsMap.getProperty("EXPIRATIONSTATUS"));
/* 2764 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"4%\" class=\"whitegrayrightalign-reports1\" align=\"center\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2765 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2766 */                                         out.write("&attributeid=8029')\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2767 */                                         out.print(getSeverityImageForHealth(alert.getProperty(subscriptionsMap.getProperty("SUBSCRIPTIONID") + "#" + "8029")));
/* 2768 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2769 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2770 */                                         out.write("&attributeIDs=8029,8031,8032&attributeToSelect=8029&redirectto=");
/* 2771 */                                         out.print(encodeurl);
/* 2772 */                                         out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2773 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 2774 */                                         out.write("\" border=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a> \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"  class=\"whitegrayrightalign-reports1\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2775 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2776 */                                         out.write("&attributeid=8032&period=-7',740,550)\">");
/* 2777 */                                         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2778 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2779 */                                         out.write("\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2780 */                                       } else { cntt = 0;
/*      */                                       } }
/* 2782 */                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/*      */                                   else {
/* 2785 */                                     out.write("\t\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2786 */                                     out.print(idcd);
/* 2787 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" colspan=\"5\" align=\"center\"> ");
/* 2788 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.nodata.text"));
/* 2789 */                                     out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/* 2791 */                                   out.write("\t\t\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/* 2792 */                                 } else if (publMap.getProperty("PUBLICATIONTYPE").toString().equals("Snapshot publication")) {
/* 2793 */                                   out.write("\t\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">&nbsp;</td>\t\n\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td colspan=\"10\" >\n\t\t\t\t\t\t\t\t\t\t\t");
/* 2794 */                                   alist = (ArrayList)mapp.get("SQL-REPL-SNAPSHOTSUBSCRIPTIONS");
/* 2795 */                                   if (alist != null) {
/* 2796 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2797 */                                     out.print(idcd);
/* 2798 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2799 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.serialno.text"));
/* 2800 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2801 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.runningstatus.text"));
/* 2802 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"28%\" class=\"whitegrayrightalign-reports2\">");
/* 2803 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.subscription.text"));
/* 2804 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"whitegrayrightalign-reports2\">");
/* 2805 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.publication.text"));
/* 2806 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2807 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.lastsynchronization.text"));
/* 2808 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2809 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.expirationstatus.text"));
/* 2810 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"4%\" align=\"center\" class=\"whitegrayrightalign-reports2\" colspan=\"2\">");
/* 2811 */                                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2812 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\"></td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2813 */                                     Iterator tt1 = alist.iterator();
/* 2814 */                                     int cntt = 0;
/* 2815 */                                     while (tt1.hasNext()) {
/* 2816 */                                       cntt++;
/* 2817 */                                       Properties subscriptionsMap = (Properties)tt1.next();
/* 2818 */                                       if (publMap.getProperty("PUBLICATION").equals(subscriptionsMap.getProperty("PUBLICATION"))) {
/* 2819 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class='whitegrayrightalign-reports'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2820 */                                         out.print(cntt);
/* 2821 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2822 */                                         out.print(subscriptionsMap.getProperty("RUNNINGSTATUS"));
/* 2823 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"28%\" class=\"whitegrayrightalign-reports1\">[");
/* 2824 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBER"));
/* 2825 */                                         out.write(93);
/* 2826 */                                         out.write(46);
/* 2827 */                                         out.write(91);
/* 2828 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBERDB"));
/* 2829 */                                         out.write("] </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"25%\" class=\"whitegrayrightalign-reports1\">[");
/* 2830 */                                         out.print(subscriptionsMap.getProperty("PUBLISHERDB"));
/* 2831 */                                         out.write(93);
/* 2832 */                                         out.write(46);
/* 2833 */                                         out.write(91);
/* 2834 */                                         out.print(subscriptionsMap.getProperty("PUBLICATION"));
/* 2835 */                                         out.write("]</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2836 */                                         if (subscriptionsMap.getProperty("LASTSYNCHRONIZATION") != null) {
/* 2837 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2838 */                                           out.print(subscriptionsMap.getProperty("LASTSYNCHRONIZATION"));
/* 2839 */                                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         } else {
/* 2841 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         }
/* 2843 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2844 */                                         out.print(subscriptionsMap.getProperty("EXPIRATIONSTATUS"));
/* 2845 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"4%\" align=\"center\" class=\"whitegrayrightalign-reports1\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2846 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2847 */                                         out.write("&attributeid=8029')\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2848 */                                         out.print(getSeverityImageForHealth(alert.getProperty(subscriptionsMap.getProperty("SUBSCRIPTIONID") + "#" + "8029")));
/* 2849 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp;\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2850 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2851 */                                         out.write("&attributeIDs=8029,8031&attributeToSelect=8029&redirectto=");
/* 2852 */                                         out.print(encodeurl);
/* 2853 */                                         out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2854 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 2855 */                                         out.write("\" border=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a> \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"  class=\"whitegrayrightalign-reports1\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2856 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2857 */                                         out.write("&attributeid=8032&period=-7',740,550)\">");
/* 2858 */                                         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2859 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2860 */                                         out.write("\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                     }
/* 2863 */                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/*      */                                   else {
/* 2866 */                                     out.write("\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2867 */                                     out.print(idcd);
/* 2868 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" colspan=\"5\" align=\"center\"> ");
/* 2869 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.nodata.text"));
/* 2870 */                                     out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/* 2872 */                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/*      */                                 } else {
/* 2874 */                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<td class=\"sqldb-data2\">&nbsp;</td>\t\n\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td colspan=\"10\" >\n\t\t\t\t\t\t\t\t\t\t");
/* 2875 */                                   alist = (ArrayList)mapp.get("SQL-REPL-MERGESUBSCRIPTIONS");
/* 2876 */                                   if (alist != null) {
/* 2877 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2878 */                                     out.print(idcd);
/* 2879 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2880 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.serialno.text"));
/* 2881 */                                     out.write("</td>\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"7%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2882 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.runningstatus.text"));
/* 2883 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"13%\" class=\"whitegrayrightalign-reports2\">");
/* 2884 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.subscription.text"));
/* 2885 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"13%\" class=\"whitegrayrightalign-reports2\">");
/* 2886 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.publication.text"));
/* 2887 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2888 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.performance.text"));
/* 2889 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2890 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.deliveryrate.text"));
/* 2891 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2892 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.lastsynchronization.text"));
/* 2893 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports2\">");
/* 2894 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.duration.text"));
/* 2895 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" width=\"7%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2896 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.connection.text"));
/* 2897 */                                     out.write("</td>\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"whitegrayrightalign-reports2\">");
/* 2898 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.expirationstatus.text"));
/* 2899 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"whitegrayrightalign-reports2\" colspan=\"2\">");
/* 2900 */                                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2901 */                                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports2\"></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2902 */                                     Iterator tt1 = alist.iterator();
/* 2903 */                                     int cntt = 0;
/* 2904 */                                     while (tt1.hasNext()) {
/* 2905 */                                       cntt++;
/* 2906 */                                       Properties subscriptionsMap = (Properties)tt1.next();
/* 2907 */                                       if (publMap.getProperty("PUBLICATION").equals(subscriptionsMap.getProperty("PUBLICATION"))) {
/* 2908 */                                         out.write("\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr class='whitegrayrightalign-reports'>\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2909 */                                         out.print(cntt);
/* 2910 */                                         out.write("</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"7%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2911 */                                         out.print(subscriptionsMap.getProperty("RUNNINGSTATUS"));
/* 2912 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"13%\" class=\"whitegrayrightalign-reports1\">[");
/* 2913 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBER"));
/* 2914 */                                         out.write(93);
/* 2915 */                                         out.write(46);
/* 2916 */                                         out.write(91);
/* 2917 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIBERDB"));
/* 2918 */                                         out.write("] </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"13%\" class=\"whitegrayrightalign-reports1\">[");
/* 2919 */                                         out.print(subscriptionsMap.getProperty("PUBLISHERDB"));
/* 2920 */                                         out.write(93);
/* 2921 */                                         out.write(46);
/* 2922 */                                         out.write(91);
/* 2923 */                                         out.print(subscriptionsMap.getProperty("PUBLICATION"));
/* 2924 */                                         out.write("]</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2925 */                                         out.print(subscriptionsMap.getProperty("PERFORMANCE"));
/* 2926 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2927 */                                         if (subscriptionsMap.getProperty("DELIVERYRATE") != null) {
/* 2928 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2929 */                                           out.print(subscriptionsMap.getProperty("DELIVERYRATE"));
/* 2930 */                                           out.write(" rows/sec</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         } else {
/* 2932 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"); }
/* 2933 */                                         if (subscriptionsMap.getProperty("LASTSYNCHRONIZATION") != null) {
/* 2934 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2935 */                                           out.print(subscriptionsMap.getProperty("LASTSYNCHRONIZATION"));
/* 2936 */                                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         } else {
/* 2938 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         }
/* 2940 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign-reports1\">");
/* 2941 */                                         out.print(subscriptionsMap.getProperty("DURATION"));
/* 2942 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2943 */                                         out.print(subscriptionsMap.getProperty("CONNECTIONTYPE"));
/* 2944 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"whitegrayrightalign-reports1\">");
/* 2945 */                                         out.print(subscriptionsMap.getProperty("EXPIRATIONSTATUS"));
/* 2946 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign-reports1\">&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"whitegrayrightalign-reports1\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2947 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2948 */                                         out.write("&attributeid=8029')\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2949 */                                         out.print(getSeverityImageForHealth(alert.getProperty(subscriptionsMap.getProperty("SUBSCRIPTIONID") + "#" + "8029")));
/* 2950 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t&nbsp;\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2951 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2952 */                                         out.write("&attributeIDs=8029,8031,8032,8037&attributeToSelect=8029&redirectto=");
/* 2953 */                                         out.print(encodeurl);
/* 2954 */                                         out.write("'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2955 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 2956 */                                         out.write("\" border=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td> \t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"  class=\"whitegrayrightalign-reports1\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2957 */                                         out.print(subscriptionsMap.getProperty("SUBSCRIPTIONID"));
/* 2958 */                                         out.write("&attributeid=8032&period=-7',740,550)\">");
/* 2959 */                                         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2960 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2961 */                                         out.write("\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                     }
/* 2964 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\t\t\n\t\t\t\t\t\t\t\t\t\t\t</div>\t\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/*      */                                   else {
/* 2967 */                                     out.write("\t\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"collapse\" id=\"");
/* 2968 */                                     out.print(idcd);
/* 2969 */                                     out.write("\" style=\"DISPLAY: none\"> \n\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"curve\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width='100%' border='0' cellpadding='0' cellspacing='0' >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayborder\" colspan=\"5\" align=\"center\"> ");
/* 2970 */                                     out.print(FormatUtil.getString("am.mssql.replication.subscriptions.nodata.text"));
/* 2971 */                                     out.write(" </td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/* 2973 */                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                             } else {
/* 2977 */                               out.write("\n\t\t\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t\t\t<td colspan=\"9\" class=\"whitegrayborder\" align=\"center\">  ");
/* 2978 */                               out.print(FormatUtil.getString("am.mssql.replication.subscriptions.nodata.text"));
/* 2979 */                               out.write("  </td>\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                             }
/*      */                           } catch (Exception e) {
/* 2982 */                             e.printStackTrace();
/*      */                           }
/* 2984 */                           out.write("\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n</div>\n</td>\n</tr>\n<tr>\n\t<td>\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr class=\"columnheading-monitor\">\n\t\t\t\t<td class=\"btmborder\" width=\"100%\" height=\"35\"> ");
/* 2985 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.text"));
/* 2986 */                           out.write(" </td>\n\t\t\t</tr>\n\t\t</table>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td>\n\t<div id=\"REPL_AGENT\" width=\"100%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr height=\"50px\">\n\t\t\t\t\t<td class=\"columnheading\" width=\"20%\">");
/* 2987 */                           out.print(FormatUtil.getString("am.mssql.replication.agentname.text"));
/* 2988 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" width=\"5%\" height=\"28\" >");
/* 2989 */                           out.print(FormatUtil.getString("am.mssql.replication.agenttype.text"));
/* 2990 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" width=\"5%\" height=\"28\" >");
/* 2991 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.status.text"));
/* 2992 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\">");
/* 2993 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.publicationdatabase.text"));
/* 2994 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\">");
/* 2995 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.laststarttime.text"));
/* 2996 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\" style=\"border-left:1px solid #F7F7F7;\">");
/* 2997 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.duration.text"));
/* 2998 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\">");
/* 2999 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.deliveryrate.text"));
/* 3000 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\">");
/* 3001 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.latency.text"));
/* 3002 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\" onmouseover=\"ddrivetip(this,event,'");
/* 3003 */                           if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                             return;
/* 3005 */                           out.write("',null,true,'#000000',15)\" onMouseOut=\"hideddrivetip()\">");
/* 3006 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.trans.text"));
/* 3007 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\" onmouseover=\"ddrivetip(this,event,'");
/* 3008 */                           if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                             return;
/* 3010 */                           out.write("',null,true,'#000000',10)\" onMouseOut=\"hideddrivetip()\">");
/* 3011 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.cmds.text"));
/* 3012 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" align=\"center\" width=\"5%\" height=\"28\" onmouseover=\"ddrivetip(this,event,'");
/* 3013 */                           if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                             return;
/* 3015 */                           out.write("',null,true,'#000000',140)\" onMouseOut=\"hideddrivetip()\">");
/* 3016 */                           out.print(FormatUtil.getString("am.mssql.replication.agent.avgcmds.text"));
/* 3017 */                           out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" width=\"50px\" align=\"center\" style=\"border-left:1px solid #F7F7F7;\">\n\t\t\t\t\t\t");
/* 3018 */                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3019 */                           out.write("\n\t\t\t\t\t</td>\t\t\t\t\t\n\t\t\t\t</tr>\t\t\n\t\t\t\t");
/*      */                           try {
/* 3021 */                             ArrayList alist = null;
/* 3022 */                             HashMap mapp = null;
/* 3023 */                             if (request.getAttribute("REPL-DETAILS") != null) {
/* 3024 */                               mapp = (HashMap)request.getAttribute("REPL-DETAILS");
/*      */                             }
/* 3026 */                             if (mapp.get("SQL-REPL-AGENTDETAILS") != null) {
/* 3027 */                               alist = (ArrayList)mapp.get("SQL-REPL-AGENTDETAILS");
/*      */                             }
/* 3029 */                             if ((alist != null) && (alist.size() > 0)) {
/* 3030 */                               int l = 0;
/* 3031 */                               for (int i = 0; i < alist.size(); i++) {
/* 3032 */                                 Properties publMap = (Properties)alist.get(i);
/* 3033 */                                 if (l % 2 == 0)
/*      */                                 {
/* 3035 */                                   bgcolor = "whitegrayborder";
/*      */                                 }
/*      */                                 else {
/* 3038 */                                   bgcolor = "yellowgrayborder";
/*      */                                 }
/* 3040 */                                 l++;
/*      */                                 
/* 3042 */                                 out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td style=\"height:30px\" width=\"30%\" valign=\"middle\" class=\"");
/* 3043 */                                 out.print(bgcolor);
/* 3044 */                                 out.write("\" title='");
/* 3045 */                                 out.print(publMap.getProperty("AGENTNAME"));
/* 3046 */                                 out.write("'>\n\t\t\t\t\t\t\t\t\t");
/*      */                                 
/* 3048 */                                 StringBuilder encodeJobUrlBuilder = new StringBuilder("/MSSql.do?method=replAgentHistory&resourceid=");
/* 3049 */                                 encodeJobUrlBuilder.append(publMap.getProperty("RESOURCEID")).append("&sqlresourceid=").append(resourceid).append("&agentid=").append(publMap.getProperty("AGENTID")).append("&agentname=").append(publMap.getProperty("AGENTNAME"));
/* 3050 */                                 String encodeJobUrl = encodeJobUrlBuilder.toString();
/*      */                                 
/* 3052 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<a id=\"");
/* 3053 */                                 out.print(i);
/* 3054 */                                 out.write("\" onmouseout=\"javascript:changelink('");
/* 3055 */                                 out.print(i);
/* 3056 */                                 out.write("')\" onmouseover=\"javascript:changelink1('");
/* 3057 */                                 out.print(i);
/* 3058 */                                 out.write("')\" onclick=\"fnOpenNewWindow(encodeURI('");
/* 3059 */                                 out.print(encodeJobUrl);
/* 3060 */                                 out.write("'))\" class=\"staticlinks-blue\">");
/* 3061 */                                 out.print(getTrimmedText(publMap.getProperty("AGENTNAME"), 50));
/* 3062 */                                 out.write("</a>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 3063 */                                 out.print(publMap.getProperty("AGENT"));
/* 3064 */                                 out.write("&nbsp;&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 3065 */                                 out.print(publMap.getProperty("STATUS"));
/* 3066 */                                 out.write("&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t");
/* 3067 */                                 if (publMap.getProperty("PUBLICATION") != null) {
/* 3068 */                                   out.write("\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<td width=\"15%\" class=\"whitegrayborder\">[");
/* 3069 */                                   out.print(publMap.getProperty("PUBLISHERDB"));
/* 3070 */                                   out.write(93);
/* 3071 */                                   out.write(46);
/* 3072 */                                   out.write(91);
/* 3073 */                                   out.print(publMap.getProperty("PUBLICATION"));
/* 3074 */                                   out.write("]</td>\t\t\t\t\t\t\n\t\t\t\t\t\t\t");
/*      */                                 } else {
/* 3076 */                                   out.write("\n\t\t\t\t\t\t\t\t<td  width=\"15%\" class=\"whitegrayborder\">");
/* 3077 */                                   out.print(FormatUtil.getString("am.mssql.replication.publication.unknown.text"));
/* 3078 */                                   out.write("</td>\n\t\t\t\t\t\t\t");
/*      */                                 }
/* 3080 */                                 out.write("\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\">");
/* 3081 */                                 out.print(publMap.getProperty("LASTSTARTTIME"));
/* 3082 */                                 out.write("&nbsp;&nbsp;</td>\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" style=\"border-left:1px solid #F7F7F7;\" align=\"right\">&nbsp;");
/* 3083 */                                 out.print(publMap.getProperty("DURATION"));
/* 3084 */                                 out.write("&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"right\">");
/* 3085 */                                 out.print(publMap.getProperty("DELIVERYRATE"));
/* 3086 */                                 out.write("&nbsp;&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"right\">");
/* 3087 */                                 out.print(publMap.getProperty("DELIVERYLATENCY"));
/* 3088 */                                 out.write("&nbsp;&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"right\">");
/* 3089 */                                 out.print(publMap.getProperty("TRANSACTIONS"));
/* 3090 */                                 out.write("&nbsp;&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"right\">");
/* 3091 */                                 out.print(publMap.getProperty("COMMANDS"));
/* 3092 */                                 out.write("&nbsp;&nbsp;</td>\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"right\">");
/* 3093 */                                 out.print(publMap.getProperty("AVG_COMMANDS"));
/* 3094 */                                 out.write("&nbsp;&nbsp;</td>\t\t\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" style=\"border-left:1px solid #F7F7F7;\">\n\t\t\t\t\t\t\t");
/* 3095 */                                 if (publMap.getProperty("RESOURCEID") != null) {
/* 3096 */                                   out.write("\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3097 */                                   out.print(publMap.getProperty("RESOURCEID"));
/* 3098 */                                   out.write("&attributeid=8030')\">\n\t\t\t\t\t\t\t\t\t");
/* 3099 */                                   out.print(getSeverityImageForHealth(alert.getProperty(publMap.getProperty("RESOURCEID") + "#" + "8030")));
/* 3100 */                                   out.write("\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t&nbsp;\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3101 */                                   out.print(publMap.getProperty("RESOURCEID"));
/* 3102 */                                   out.write("&attributeIDs=8030,8037&attributeToSelect=8030&redirectto=");
/* 3103 */                                   out.print(encodeurl);
/* 3104 */                                   out.write("'>\n\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" title=\"");
/* 3105 */                                   out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3106 */                                   out.write("\" border=\"0\" >\n\t\t\t\t\t\t\t\t</a> \n\t\t\t\t\t\t\t");
/*      */                                 } else {
/* 3108 */                                   out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t\t\t");
/*      */                                 }
/* 3110 */                                 out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\t\t\n\t\t\t\t\t");
/*      */                               }
/*      */                             }
/*      */                             else {
/* 3114 */                               out.write("\n\t\t\t\t\t<tr height=\"45\">\n\t\t\t\t\t\t<td class=\"whitegrayborder\" colspan=\"12\" align=\"center\">");
/* 3115 */                               out.print(FormatUtil.getString("am.mssql.replication.agent.nodata.text"));
/* 3116 */                               out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/*      */                             }
/*      */                           } catch (Exception e) {
/* 3119 */                             e.printStackTrace();
/*      */                           }
/* 3121 */                           out.write("\t\n\t\t\t</table>\n\t\t</div>\t\n\t\t\t</td></tr>\n\t");
/*      */                         }
/* 3123 */                         out.write("\n\t</table>\n<!--");
/* 3124 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3125 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3129 */                     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3130 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*      */                     }
/*      */                     else {
/* 3133 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3134 */                       out.write(45);
/* 3135 */                       out.write(45);
/* 3136 */                       out.write(62);
/*      */                     }
/* 3138 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3139 */         out = _jspx_out;
/* 3140 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3141 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3142 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3145 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3151 */     PageContext pageContext = _jspx_page_context;
/* 3152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3154 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3155 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3156 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3158 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3159 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3160 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3161 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3162 */       return true;
/*      */     }
/* 3164 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3165 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3170 */     PageContext pageContext = _jspx_page_context;
/* 3171 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3173 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3174 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3175 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3177 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty logshippingdetails}");
/* 3178 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3179 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3181 */         out.write("\n\t\t\t");
/* 3182 */         if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 3183 */           return true;
/* 3184 */         out.write(10);
/* 3185 */         out.write(9);
/* 3186 */         out.write(9);
/* 3187 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3188 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3192 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3193 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3194 */       return true;
/*      */     }
/* 3196 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3197 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3202 */     PageContext pageContext = _jspx_page_context;
/* 3203 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3205 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3206 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3207 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 3209 */     _jspx_th_c_005fforEach_005f0.setItems("${logshippingdetails}");
/*      */     
/* 3211 */     _jspx_th_c_005fforEach_005f0.setVar("details");
/*      */     
/* 3213 */     _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3214 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 3216 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3217 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 3219 */           out.write("\n\t\t\t<tr>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3220 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3221 */             return true;
/* 3222 */           out.write("</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3223 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3224 */             return true;
/* 3225 */           out.write("</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3226 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3227 */             return true;
/* 3228 */           out.write("</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3229 */           if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3230 */             return true;
/* 3231 */           out.write("&nbsp;</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3232 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3233 */             return true;
/* 3234 */           out.write("</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3235 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3236 */             return true;
/* 3237 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3238 */             return true;
/* 3239 */           out.write("</td>\n\t\t\t\t<td class=\"whitegrayborder\">");
/* 3240 */           if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3241 */             return true;
/* 3242 */           if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3243 */             return true;
/* 3244 */           out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/* 3245 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3246 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3250 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 3251 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3254 */         int tmp505_504 = 0; int[] tmp505_502 = _jspx_push_body_count_c_005fforEach_005f0; int tmp507_506 = tmp505_502[tmp505_504];tmp505_502[tmp505_504] = (tmp507_506 - 1); if (tmp507_506 <= 0) break;
/* 3255 */         out = _jspx_page_context.popBody(); }
/* 3256 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3258 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3259 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 3261 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3266 */     PageContext pageContext = _jspx_page_context;
/* 3267 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3269 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3270 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3271 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3273 */     _jspx_th_c_005fout_005f1.setValue("${details.DBNAME}");
/* 3274 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3275 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3276 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3277 */       return true;
/*      */     }
/* 3279 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3280 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3285 */     PageContext pageContext = _jspx_page_context;
/* 3286 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3288 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3289 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3290 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3292 */     _jspx_th_c_005fout_005f2.setValue("${details.AGENTTYPE}");
/* 3293 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3294 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3295 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3296 */       return true;
/*      */     }
/* 3298 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3299 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3304 */     PageContext pageContext = _jspx_page_context;
/* 3305 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3307 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3308 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3309 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3311 */     _jspx_th_c_005fout_005f3.setValue("${details.STATUS}");
/* 3312 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3313 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3314 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3315 */       return true;
/*      */     }
/* 3317 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3323 */     PageContext pageContext = _jspx_page_context;
/* 3324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3326 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody.get(FormatNumberTag.class);
/* 3327 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 3328 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3330 */     _jspx_th_fmt_005fformatNumber_005f0.setType("number");
/*      */     
/* 3332 */     _jspx_th_fmt_005fformatNumber_005f0.setPattern("#,###");
/*      */     
/* 3334 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${details.TIMESINCEBACKUP}");
/* 3335 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 3336 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 3337 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 3338 */       return true;
/*      */     }
/* 3340 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005ftype_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 3341 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3346 */     PageContext pageContext = _jspx_page_context;
/* 3347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3349 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3350 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3351 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3353 */     _jspx_th_c_005fout_005f4.setValue("${details.LOGTIME}");
/* 3354 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3355 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3356 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3357 */       return true;
/*      */     }
/* 3359 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3360 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3365 */     PageContext pageContext = _jspx_page_context;
/* 3366 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3368 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3369 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3370 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3372 */     _jspx_th_c_005fif_005f1.setTest("${!empty details.ERRORLOGTIME}");
/* 3373 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3374 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3376 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3377 */           return true;
/* 3378 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3383 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3384 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3385 */       return true;
/*      */     }
/* 3387 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3393 */     PageContext pageContext = _jspx_page_context;
/* 3394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3396 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3397 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3398 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3400 */     _jspx_th_c_005fout_005f5.setValue("${details.ERRORLOGTIME}");
/* 3401 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3402 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3403 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3404 */       return true;
/*      */     }
/* 3406 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3412 */     PageContext pageContext = _jspx_page_context;
/* 3413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3415 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3416 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3417 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3419 */     _jspx_th_c_005fif_005f2.setTest("${empty details.ERRORLOGTIME}");
/* 3420 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3421 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3423 */         out.write(45);
/* 3424 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3425 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3429 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3430 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3431 */       return true;
/*      */     }
/* 3433 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3439 */     PageContext pageContext = _jspx_page_context;
/* 3440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3442 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3443 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3444 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3446 */     _jspx_th_c_005fif_005f3.setTest("${!empty details.ERRORMESSAGE}");
/* 3447 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3448 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3450 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3451 */           return true;
/* 3452 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3453 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3457 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3458 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3459 */       return true;
/*      */     }
/* 3461 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3467 */     PageContext pageContext = _jspx_page_context;
/* 3468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3470 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3471 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3472 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3474 */     _jspx_th_c_005fout_005f6.setEscapeXml("false");
/*      */     
/* 3476 */     _jspx_th_c_005fout_005f6.setValue("${details.ERRORMESSAGE}");
/* 3477 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3478 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3479 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3480 */       return true;
/*      */     }
/* 3482 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3488 */     PageContext pageContext = _jspx_page_context;
/* 3489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3491 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3492 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3493 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3495 */     _jspx_th_c_005fif_005f4.setTest("${empty details.ERRORMESSAGE}");
/* 3496 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3497 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3499 */         out.write(45);
/* 3500 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3501 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3505 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3506 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3507 */       return true;
/*      */     }
/* 3509 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3510 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3515 */     PageContext pageContext = _jspx_page_context;
/* 3516 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3518 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3519 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3520 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/* 3521 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3522 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3523 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3524 */         out = _jspx_page_context.pushBody();
/* 3525 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3526 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3529 */         out.write("am.mssql.replication.agent.trans.tooltip.text");
/* 3530 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3531 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3534 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3535 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3538 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3539 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3540 */       return true;
/*      */     }
/* 3542 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3543 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3548 */     PageContext pageContext = _jspx_page_context;
/* 3549 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3551 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3552 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3553 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f5);
/* 3554 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3555 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3556 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3557 */         out = _jspx_page_context.pushBody();
/* 3558 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3559 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3562 */         out.write("am.mssql.replication.agent.cmds.tooltip.text");
/* 3563 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3564 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3567 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3568 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3571 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3572 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3573 */       return true;
/*      */     }
/* 3575 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3581 */     PageContext pageContext = _jspx_page_context;
/* 3582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3584 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3585 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3586 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f5);
/* 3587 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3588 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 3589 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3590 */         out = _jspx_page_context.pushBody();
/* 3591 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 3592 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3595 */         out.write("am.mssql.replication.agent.avgcmds.tooltip.text");
/* 3596 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 3597 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3600 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 3601 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3604 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3605 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3606 */       return true;
/*      */     }
/* 3608 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3609 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mssql\replication_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */