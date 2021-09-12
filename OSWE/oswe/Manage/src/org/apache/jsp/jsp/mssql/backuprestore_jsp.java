/*      */ package org.apache.jsp.jsp.mssql;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DataCollectionComponent;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class backuprestore_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  673 */       val = new SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  904 */     Properties temp = FaultUtil.getStatus(entitylist, false);
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
/*  918 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/* 1023 */       String healthid = AMAttributesCache.getHealthId(resourceType);
/* 1024 */       String availid = AMAttributesCache.getAvailabilityId(resourceType);
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2213 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2217 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2221 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
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
/* 2243 */       ServletConfig config = pageContext.getServletConfig();
/* 2244 */       session = pageContext.getSession();
/* 2245 */       out = pageContext.getOut();
/* 2246 */       _jspx_out = out;
/*      */       
/* 2248 */       out.write("<!--$Id: jobs.jsp,v 98993:58741423c085 2013/11/06 13:15:04 annapoorani.r $-->\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
/*      */       
/* 2250 */       request.setAttribute("HelpKey", "Monitors MSSQL Details");
/*      */       
/* 2252 */       out.write("\n\n\n\n\n\n\n\n\n");
/* 2253 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2255 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2256 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2257 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2259 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2261 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2266 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2267 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2268 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2271 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2272 */         String available = null;
/* 2273 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2274 */         out.write(10);
/*      */         
/* 2276 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2277 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2278 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2280 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2282 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2287 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2288 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2289 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2292 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2293 */           String unavailable = null;
/* 2294 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2295 */           out.write(10);
/*      */           
/* 2297 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2298 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2299 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2301 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2303 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2308 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2309 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2310 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2313 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2314 */             String unmanaged = null;
/* 2315 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2316 */             out.write(10);
/*      */             
/* 2318 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2319 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2320 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2322 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2324 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2329 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2330 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2331 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2334 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2335 */               String scheduled = null;
/* 2336 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2337 */               out.write(10);
/*      */               
/* 2339 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2340 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2341 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2343 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2345 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2350 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2351 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2352 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2355 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2356 */                 String critical = null;
/* 2357 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2358 */                 out.write(10);
/*      */                 
/* 2360 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2361 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2362 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2364 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2366 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2371 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2372 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2373 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2376 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2377 */                   String clear = null;
/* 2378 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2379 */                   out.write(10);
/*      */                   
/* 2381 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2382 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2383 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2385 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2387 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2392 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2393 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2394 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2397 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2398 */                     String warning = null;
/* 2399 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2400 */                     out.write(10);
/* 2401 */                     out.write(10);
/*      */                     
/* 2403 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2404 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2406 */                     out.write(10);
/* 2407 */                     out.write(10);
/* 2408 */                     out.write(10);
/* 2409 */                     out.write("\n \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT> ");
/* 2410 */                     out.write(" \n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/mssql.js\"></SCRIPT>\n");
/* 2411 */                     if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                       return;
/* 2413 */                     out.write(10);
/*      */                     
/*      */ 
/* 2416 */                     String name = null;
/* 2417 */                     float downtime = 0.0F;
/* 2418 */                     Properties data = (Properties)request.getAttribute("performance");
/*      */                     
/*      */ 
/*      */ 
/* 2422 */                     name = (String)request.getAttribute("name");
/* 2423 */                     String haid = null;
/* 2424 */                     String appname = null;
/* 2425 */                     String search = null;
/* 2426 */                     String tab = "1";
/* 2427 */                     String bgcolour = "class=\"whitegrayborder\"";
/* 2428 */                     haid = (String)request.getAttribute("haid");
/* 2429 */                     appname = (String)request.getAttribute("appName");
/* 2430 */                     String details = request.getParameter("details");
/* 2431 */                     String showdata = (String)request.getAttribute("showdata");
/* 2432 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2433 */                     String displayname = "";
/* 2434 */                     ArrayList attribIDs = new ArrayList();
/* 2435 */                     ArrayList resIDs = new ArrayList();
/* 2436 */                     attribIDs.add("3838");
/* 2437 */                     attribIDs.add("3839");
/* 2438 */                     attribIDs.add("3833");
/* 2439 */                     attribIDs.add("3834");
/* 2440 */                     attribIDs.add("3864");
/* 2441 */                     if (resourceid != null) {
/* 2442 */                       resIDs.add(resourceid);
/*      */                     }
/* 2444 */                     ArrayList backupDetails1 = (ArrayList)request.getAttribute("BACKUPS");
/* 2445 */                     if (backupDetails1 != null) {
/* 2446 */                       for (int j = 0; j < backupDetails1.size(); j++) {
/* 2447 */                         Properties backupProperties1 = new Properties();
/* 2448 */                         backupProperties1 = (Properties)backupDetails1.get(j);
/* 2449 */                         resIDs.add(backupProperties1.getProperty("RESOURCEID"));
/*      */                       }
/*      */                     }
/*      */                     
/* 2453 */                     out.write(10);
/*      */                     
/* 2455 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2456 */                     if (request.getAttribute("displayname") == null) {
/* 2457 */                       displayname = request.getParameter("resourcename");
/*      */                     } else {
/* 2459 */                       displayname = (String)request.getAttribute("displayname");
/*      */                     }
/* 2461 */                     String redirect = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=6";
/* 2462 */                     String encodeurl = URLEncoder.encode(redirect);
/* 2463 */                     DataCollectionComponent dcc = new DataCollectionComponent();
/*      */                     
/* 2465 */                     out.write("\n  <br>\n  <form action=\"/MSSqlDispatch.do?method=backupManagementActions\" name=\"managebackup\" method=\"post\" style=\"display:inline\">\n  <input type=\"hidden\" name=\"resourceid\"/>\n  <input type=\"hidden\" name=\"ids\"/>\n  \t<!-- ");
/*      */                     
/* 2467 */                     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2468 */                     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2469 */                     _jspx_th_c_005fif_005f1.setParent(null);
/*      */                     
/* 2471 */                     _jspx_th_c_005fif_005f1.setTest("${empty showOnlyJobs}");
/* 2472 */                     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2473 */                     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                       for (;;) {
/* 2475 */                         out.write(" -->\n\t");
/*      */                         
/* 2477 */                         if ((resourceid != null) && (dcc.getStatusforComponent(resourceid, "SQLBACKUP")))
/*      */                         {
/*      */                           try {
/* 2480 */                             ArrayList backupDetails = (ArrayList)request.getAttribute("BACKUPS");
/* 2481 */                             if ((backupDetails == null) || (backupDetails.size() == 0))
/*      */                             {
/* 2483 */                               out.write("\n  \t    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n      \t<tr><td colspan=\"10\" height=\"31\" class=\"tableheading\">");
/* 2484 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.tableheading"));
/* 2485 */                               out.write(" </td>\n \t\t<td style=\"padding-right:6px;\" class=\"tableheading\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2486 */                               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                 return;
/* 2488 */                               out.write("&dcComponentName=SQLBACKUP\"><img align=\"middle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2489 */                               out.print(FormatUtil.getString("Disable"));
/* 2490 */                               out.write("\"></a></td>\n\t\t</table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n      \t<tr   height=\"45\" >\n      \t<td class=\"whitegrayborder\" colspan=\"10\" align=\"center\">");
/* 2491 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2492 */                               out.write("</td>\n      \t</tr>\n");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2496 */                               out.write("\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n      \t<tr><td colspan=\"9\" height=\"31\" class=\"tableheading\">");
/* 2497 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.tableheading"));
/* 2498 */                               out.write(" </td>\n \t\t<td align=\"right\" class=\"tableheading\"> \n\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2499 */                               out.print(resourceid);
/* 2500 */                               out.write("&attributeIDs=3839,3864&attributeToSelect=3839&redirectto=");
/* 2501 */                               out.print(encodeurl);
/* 2502 */                               out.write("\" class=\"staticlinks\">");
/* 2503 */                               out.print(ALERTCONFIG_TEXT);
/* 2504 */                               out.write("</a>\n\t\t&nbsp;<a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2505 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                 return;
/* 2507 */                               out.write("&dcComponentName=SQLBACKUP\"><img align=\"absmiddle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2508 */                               out.print(FormatUtil.getString("Disable"));
/* 2509 */                               out.write("\"></a></td>\n\t\t</table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\n\t\t<tr>\n\t\t<td height=\"31\" class=\"AlarmInnerBoxBg btmborder\" colspan=\"12\">&nbsp;\t\n\t\t<select onchange=\"invokeBKUPActions(");
/* 2510 */                               out.print(resourceid);
/* 2511 */                               out.write(");\" class=\"formtext\" style=\"position:relative;\" id=\"operation\">\n\t\t\t<option VALUE=\"selectdbactions\" selected=true>--");
/* 2512 */                               out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 2513 */                               out.write("--</option>\n\t\t\t<option value=\"enableMonitoring\">");
/* 2514 */                               out.print(FormatUtil.getString("am.webclient.mssql.dbdetails.enablemonitoring"));
/* 2515 */                               out.write("</option>\n\t\t\t<option value=\"disableMonitoring\">");
/* 2516 */                               out.print(FormatUtil.getString("am.webclient.mssql.dbdetails.disablemonitoring"));
/* 2517 */                               out.write("</option>\n\t\t    <option value=\"disableMonitoringReset\">");
/* 2518 */                               out.print(FormatUtil.getString("am.disableresetmonitoring"));
/* 2519 */                               out.write("</option>\n\t\t</select>\t\t  \n\t\t</td>\n\t\t</tr>\n\n      \t<tr class=\"bodytextbold\">\n      \t<td width=\"3%\" height=\"28\"  class=\"columnheading\"><input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAllbkup(this,'selectdb')\"> </td>\n        <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 2520 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.dbname"));
/* 2521 */                               out.write("</td>\n        <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 2522 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.backupstart"));
/* 2523 */                               out.write("</td>\n        <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 2524 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.backupfinish"));
/* 2525 */                               out.write("</td>\n\t     <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 2526 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.totaltime"));
/* 2527 */                               out.write("</td>\n        <td width=\"7%\" height=\"28\"  class=\"columnheading\">");
/* 2528 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.expiration"));
/* 2529 */                               out.write("</td>\n        <td width=\"6%\"  height=\"28\"  class=\"columnheading\">");
/* 2530 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.Damaged"));
/* 2531 */                               out.write("<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2532 */                               out.print(resourceid);
/* 2533 */                               out.write("&attributeid=3864')\">");
/* 2534 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3864")));
/* 2535 */                               out.write("</a>&nbsp;</td>\n       <td width=\"5%\" height=\"28\"  class=\"columnheading\">");
/* 2536 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.type"));
/* 2537 */                               out.write("</td>  \n        <td width=\"5%\" height=\"28\"  class=\"columnheading\">");
/* 2538 */                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.size"));
/* 2539 */                               out.write("</td>\n        <td width=\"18%\" height=\"28\"  class=\"columnheading\">");
/* 2540 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.phyname"));
/* 2541 */                               out.write("</td>\n        <td width=\"6%\"  height=\"28\"  class=\"columnheading\">");
/* 2542 */                               out.print(FormatUtil.getString("am.webclient.mssqldetails.backupage"));
/* 2543 */                               out.write("<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2544 */                               out.print(resourceid);
/* 2545 */                               out.write("&attributeid=3839')\">");
/* 2546 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3839")));
/* 2547 */                               out.write("</a>&nbsp;</td>\n        <td width=\"6%\"  height=\"28\"  class=\"columnheading\" valign=\"middle\">\n        \t&nbsp;\n        \t");
/* 2548 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2549 */                               out.write("\n        </td>\n        </tr>\t\t\n\t\t\n\t\t\n\t\t");
/*      */                               
/* 2551 */                               int l = 0;
/* 2552 */                               for (int i = 0; i < backupDetails.size(); i++) {
/* 2553 */                                 Properties backupProperties = new Properties();
/* 2554 */                                 backupProperties = (Properties)backupDetails.get(i);
/* 2555 */                                 if (backupProperties != null) { String bgcolor;
/* 2556 */                                   String bgcolor; if (l % 2 == 0)
/*      */                                   {
/* 2558 */                                     bgcolor = "whitegrayborder";
/*      */                                   }
/*      */                                   else {
/* 2561 */                                     bgcolor = "yellowgrayborder";
/*      */                                   }
/* 2563 */                                   l++;
/* 2564 */                                   String bcolor = "whitegrayborder";
/* 2565 */                                   if (DBUtil.disableDBIds.contains(backupProperties.getProperty("RESOURCEID"))) {
/* 2566 */                                     bcolor = "disabledtext";
/*      */                                   }
/*      */                                   
/* 2569 */                                   out.write(" \n\n \t<tr class=\"");
/* 2570 */                                   out.print(bgcolor);
/* 2571 */                                   out.write("\" >\n    \t<td class=\"");
/* 2572 */                                   out.print(bgcolor);
/* 2573 */                                   out.write("\"> <input type=\"checkbox\" name=\"selectdb\" value='");
/* 2574 */                                   out.print(URLEncoder.encode(backupProperties.getProperty("RESOURCEID"), "UTF-8"));
/* 2575 */                                   out.write("'/></td>\n    \t<td class=\"");
/* 2576 */                                   out.print(bcolor);
/* 2577 */                                   out.write("\"   id=\"");
/* 2578 */                                   out.print(backupProperties.getProperty("RESOURCEID"));
/* 2579 */                                   out.write("\"title='");
/* 2580 */                                   out.print(backupProperties.getProperty("DBNAME"));
/* 2581 */                                   out.write(39);
/* 2582 */                                   out.write(62);
/* 2583 */                                   out.write(32);
/* 2584 */                                   out.print(getTrimmedText(backupProperties.getProperty("DBNAME"), 35));
/* 2585 */                                   out.write("</td>\n    \t<td class=\"");
/* 2586 */                                   out.print(bgcolor);
/* 2587 */                                   out.write(34);
/* 2588 */                                   out.write(62);
/* 2589 */                                   out.print(backupProperties.getProperty("BACKUPSTART"));
/* 2590 */                                   out.write("</td>\n    \t<td class=\"");
/* 2591 */                                   out.print(bgcolor);
/* 2592 */                                   out.write(34);
/* 2593 */                                   out.write(62);
/* 2594 */                                   out.print(backupProperties.getProperty("BACKUPEND"));
/* 2595 */                                   out.write("</td>\n\t\t<td class=\"");
/* 2596 */                                   out.print(bgcolor);
/* 2597 */                                   out.write(34);
/* 2598 */                                   out.write(62);
/* 2599 */                                   out.print(backupProperties.getProperty("TOTALTIME"));
/* 2600 */                                   out.write("</td>\n    \t<td class=\"");
/* 2601 */                                   out.print(bgcolor);
/* 2602 */                                   out.write(34);
/* 2603 */                                   out.write(62);
/* 2604 */                                   out.print(backupProperties.getProperty("EXPIRATION"));
/* 2605 */                                   out.write("</td>\n\t\t<td class=\"");
/* 2606 */                                   out.print(bgcolor);
/* 2607 */                                   out.write(34);
/* 2608 */                                   out.write(62);
/* 2609 */                                   out.print(backupProperties.getProperty("DAMAGED"));
/* 2610 */                                   out.write("</td>\n        <td class=\"");
/* 2611 */                                   out.print(bgcolor);
/* 2612 */                                   out.write("\" title=\"");
/* 2613 */                                   out.print(backupProperties.getProperty("BKUPTYPEDesc"));
/* 2614 */                                   out.write(34);
/* 2615 */                                   out.write(62);
/* 2616 */                                   out.print(backupProperties.getProperty("BKUPTYPE"));
/* 2617 */                                   out.write("</td> \n    \t<td class=\"");
/* 2618 */                                   out.print(bgcolor);
/* 2619 */                                   out.write(34);
/* 2620 */                                   out.write(62);
/* 2621 */                                   out.print(FormatUtil.formatNumber(backupProperties.getProperty("BKUPSIZE")));
/* 2622 */                                   out.write("</td>\n    \t<td class=\"");
/* 2623 */                                   out.print(bgcolor);
/* 2624 */                                   out.write(34);
/* 2625 */                                   out.write(62);
/* 2626 */                                   out.print(backupProperties.getProperty("PHYNAME"));
/* 2627 */                                   out.write("</td>\n    \t<td class=\"");
/* 2628 */                                   out.print(bgcolor);
/* 2629 */                                   out.write(34);
/* 2630 */                                   out.write(62);
/* 2631 */                                   out.print(FormatUtil.formatNumber(backupProperties.getProperty("BKUPAGE")));
/* 2632 */                                   out.write("</td>\n    \t    \t<td class=\"");
/* 2633 */                                   out.print(bgcolor);
/* 2634 */                                   out.write("\" valign=\"middle\">\n    \t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2635 */                                   out.print(backupProperties.getProperty("RESOURCEID"));
/* 2636 */                                   out.write("&attributeid=3838')\">\n        \t\t");
/* 2637 */                                   out.print(getSeverityImageForHealth(alert.getProperty(backupProperties.getProperty("RESOURCEID") + "#" + "3838")));
/* 2638 */                                   out.write("\n        \t</a>\n        \t&nbsp;\n    \t\t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2639 */                                   out.print(backupProperties.getProperty("RESOURCEID"));
/* 2640 */                                   out.write("&attributeIDs=3833,3834,3838&attributeToSelect=3833&redirectto=");
/* 2641 */                                   out.print(encodeurl);
/* 2642 */                                   out.write("'class=\"staticlinks\">\n    \t\t\t<img src=\"/images/icon_associateaction.gif\" title=\"");
/* 2643 */                                   out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 2644 */                                   out.write("\" border=\"0\" />\n    \t\t</a>\n    \t</td>    \t   \t\n\t</tr>\n");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           catch (Exception exception) {
/* 2650 */                             exception.printStackTrace();
/*      */                           }
/*      */                           
/* 2653 */                           out.write("\n\t</table>\n\t");
/*      */                         }
/*      */                         else {
/* 2656 */                           out.write("\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t      \t<tr>\n\t\t\t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 2657 */                           out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.tableheading"));
/* 2658 */                           out.write(" </td>\n\t\t\t\t<td style=\"padding-right:6px;\" class=\"tableheading\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2659 */                           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2661 */                           out.write("&dcComponentName=SQLBACKUP\"><img align=\"middle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2662 */                           out.print(FormatUtil.getString("Disable"));
/* 2663 */                           out.write("\"></a></td>\n           </tr>     \n\t  </table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n      \t<tr   height=\"45\" >\n      \t<td class=\"whitegrayborder\" align=\"center\">");
/*      */                           
/* 2665 */                           MessageTag _jspx_th_bean_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(MessageTag.class);
/* 2666 */                           _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2667 */                           _jspx_th_bean_005fmessage_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                           
/* 2669 */                           _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.mssqlbackup.configure");
/*      */                           
/* 2671 */                           _jspx_th_bean_005fmessage_005f0.setArg0(resourceid);
/* 2672 */                           int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 2673 */                           if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 2674 */                             this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                           }
/*      */                           
/* 2677 */                           this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 2678 */                           out.write(" </td>\n      \t</tr>\n      \t</table>\n      \t");
/*      */                         }
/* 2680 */                         out.write("\n      \t</form>\n\t<br>\n <br>\n  \t");
/*      */                         
/* 2682 */                         if ((resourceid != null) && (dcc.getStatusforComponent(resourceid, "SQLRESTORE")))
/*      */                         {
/*      */                           try {
/* 2685 */                             ArrayList restoreDetail = (ArrayList)request.getAttribute("RESTORE");
/* 2686 */                             if ((restoreDetail == null) || (restoreDetail.size() == 0))
/*      */                             {
/* 2688 */                               out.write("\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n      \t<tr>\n\t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 2689 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.tableheading"));
/* 2690 */                               out.write(" </td>\n \t\t<td style=\"padding-right:6px;\" class=\"tableheading\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2691 */                               if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                 return;
/* 2693 */                               out.write("&dcComponentName=SQLRESTORE\"><img align=\"absmiddle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2694 */                               out.print(FormatUtil.getString("Disable"));
/* 2695 */                               out.write("\"></a></td>\n        </tr>\n\t\t</table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n      \t<tr height=\"45\" >\n      \t<td class=\"whitegrayborder\" colspan=\"6\" align=\"center\">");
/* 2696 */                               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 2697 */                               out.write("</td>\n      \t</tr>\n\n");
/*      */                             }
/*      */                             else
/*      */                             {
/* 2701 */                               out.write("\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n      \t<tr>\n\t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 2702 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.tableheading"));
/* 2703 */                               out.write(" </td>\n \t\t<td class=\"tableheading\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2704 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                 return;
/* 2706 */                               out.write("&dcComponentName=SQLRESTORE\"><img align=\"middle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2707 */                               out.print(FormatUtil.getString("Disable"));
/* 2708 */                               out.write("\"></a></td>\n        </tr> \n\t\t</table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\n\t\t<tr class=\"bodytextbold\">\n        <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 2709 */                               out.print(FormatUtil.getString("am.webclient.mssql.backupdetails.dbname"));
/* 2710 */                               out.write("</td>\n        <td width=\"15%\" height=\"28\"  class=\"columnheading\">");
/* 2711 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.restoreby"));
/* 2712 */                               out.write("</td>\n\t    <td width=\"10%\" height=\"28\"  class=\"columnheading\">");
/* 2713 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.started"));
/* 2714 */                               out.write("</td>\n\t\t<td width=\"30%\" height=\"28\"  class=\"columnheading\">");
/* 2715 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.restorefrom"));
/* 2716 */                               out.write("</td>\n\t    <td width=\"30%\" height=\"28\"  class=\"columnheading\">");
/* 2717 */                               out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.restoreto"));
/* 2718 */                               out.write("</td>\n     \t</tr>\t\t\n \n\t\t");
/*      */                               
/* 2720 */                               int l = 0;
/* 2721 */                               for (int i = 0; i < restoreDetail.size(); i++) {
/* 2722 */                                 Properties backupProperties = new Properties();
/* 2723 */                                 backupProperties = (Properties)restoreDetail.get(i);
/* 2724 */                                 if (backupProperties != null) { String bgcolor;
/* 2725 */                                   String bgcolor; if (l % 2 == 0)
/*      */                                   {
/* 2727 */                                     bgcolor = "whitegrayborder";
/*      */                                   }
/*      */                                   else {
/* 2730 */                                     bgcolor = "yellowgrayborder";
/*      */                                   }
/* 2732 */                                   l++;
/*      */                                   
/* 2734 */                                   out.write(" \n    \t<tr class=\"");
/* 2735 */                                   out.print(bgcolor);
/* 2736 */                                   out.write("\">\n    \t<td class=\"");
/* 2737 */                                   out.print(bgcolor);
/* 2738 */                                   out.write("\" title='");
/* 2739 */                                   out.print(backupProperties.getProperty("DBNAME"));
/* 2740 */                                   out.write(39);
/* 2741 */                                   out.write(62);
/* 2742 */                                   out.print(getTrimmedText(backupProperties.getProperty("DBNAME"), 35));
/* 2743 */                                   out.write("</td>\n    \t<td class=\"");
/* 2744 */                                   out.print(bgcolor);
/* 2745 */                                   out.write(34);
/* 2746 */                                   out.write(62);
/* 2747 */                                   out.print(backupProperties.getProperty("RESTOREDBY"));
/* 2748 */                                   out.write("</td>\n    \t<td class=\"");
/* 2749 */                                   out.print(bgcolor);
/* 2750 */                                   out.write(34);
/* 2751 */                                   out.write(62);
/* 2752 */                                   out.print(backupProperties.getProperty("STARTED"));
/* 2753 */                                   out.write("</td>\n\t\t<td class=\"");
/* 2754 */                                   out.print(bgcolor);
/* 2755 */                                   out.write(34);
/* 2756 */                                   out.write(62);
/* 2757 */                                   out.print(FormatUtil.addBlankLiterals(backupProperties.getProperty("RSTFROM"), 40));
/* 2758 */                                   out.write("</td>\n\t\t<td class=\"");
/* 2759 */                                   out.print(bgcolor);
/* 2760 */                                   out.write(34);
/* 2761 */                                   out.write(62);
/* 2762 */                                   out.print(FormatUtil.addBlankLiterals(backupProperties.getProperty("RSTTO"), 40));
/* 2763 */                                   out.write("</td>\n\t</tr>\n");
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           catch (Exception exception) {
/* 2769 */                             exception.printStackTrace();
/*      */                           }
/*      */                           
/* 2772 */                           out.write("\n\t</table>\n\t");
/*      */                         }
/*      */                         else {
/* 2775 */                           out.write("\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t      \t<tr>\n\t\t\t\t<td width=\"100%\" height=\"31\" class=\"tableheading\">");
/* 2776 */                           out.print(FormatUtil.getString("am.webclient.mssql.restoredetails.tableheading"));
/* 2777 */                           out.write(" </td>\n\t\t\t\t<td style=\"padding-right:6px;\" class=\"tableheading\" align=\"right\"><a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showconfigureMSSQL=true&resourceID=");
/* 2778 */                           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                             return;
/* 2780 */                           out.write("&dcComponentName=SQLRESTORE\"><img align=\"absmiddle\" src=\"/images/icon_disable.gif\" border=\"0\" alt=\"Disable\" title=\"");
/* 2781 */                           out.print(FormatUtil.getString("Disable"));
/* 2782 */                           out.write("\"></a></td>\n\t\t\t</tr>\n      \t</table>\n      \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n      \t<tr   height=\"45\" >\n      \t<td class=\"whitegrayborder\" align=\"center\">");
/*      */                           
/* 2784 */                           MessageTag _jspx_th_bean_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(MessageTag.class);
/* 2785 */                           _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2786 */                           _jspx_th_bean_005fmessage_005f1.setParent(_jspx_th_c_005fif_005f1);
/*      */                           
/* 2788 */                           _jspx_th_bean_005fmessage_005f1.setKey("am.webclient.mssqlrestore.configure");
/*      */                           
/* 2790 */                           _jspx_th_bean_005fmessage_005f1.setArg0(resourceid);
/* 2791 */                           int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 2792 */                           if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 2793 */                             this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1); return;
/*      */                           }
/*      */                           
/* 2796 */                           this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 2797 */                           out.write(" </td>\n      \t</tr>\n      \t</table>\n      \t");
/*      */                         }
/* 2799 */                         out.write("\n<!--");
/* 2800 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2801 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2805 */                     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2806 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                     }
/*      */                     else {
/* 2809 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2810 */                       out.write(45);
/* 2811 */                       out.write(45);
/* 2812 */                       out.write(62);
/*      */                     }
/* 2814 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2815 */         out = _jspx_out;
/* 2816 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2817 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2818 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2821 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2827 */     PageContext pageContext = _jspx_page_context;
/* 2828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2830 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2831 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2832 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 2834 */     _jspx_th_c_005fif_005f0.setTest("${not empty showOnlyJobs}");
/* 2835 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2836 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2838 */         out.write("\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2839 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 2840 */           return true;
/* 2841 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<br>\n");
/* 2842 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2843 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2847 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2848 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2849 */       return true;
/*      */     }
/* 2851 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2857 */     PageContext pageContext = _jspx_page_context;
/* 2858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2860 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2861 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2862 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2864 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2866 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2867 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2868 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2869 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2870 */       return true;
/*      */     }
/* 2872 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2878 */     PageContext pageContext = _jspx_page_context;
/* 2879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2881 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2882 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2883 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2885 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 2886 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2887 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2888 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2889 */       return true;
/*      */     }
/* 2891 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2897 */     PageContext pageContext = _jspx_page_context;
/* 2898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2900 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2901 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2902 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2904 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 2905 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2906 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2907 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2908 */       return true;
/*      */     }
/* 2910 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2916 */     PageContext pageContext = _jspx_page_context;
/* 2917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2919 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2920 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2921 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2923 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 2924 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2925 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2926 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2927 */       return true;
/*      */     }
/* 2929 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2935 */     PageContext pageContext = _jspx_page_context;
/* 2936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2938 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2939 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2940 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2942 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 2943 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2944 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2946 */       return true;
/*      */     }
/* 2948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2954 */     PageContext pageContext = _jspx_page_context;
/* 2955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2957 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2958 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2959 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2961 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 2962 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2963 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2964 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2965 */       return true;
/*      */     }
/* 2967 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2973 */     PageContext pageContext = _jspx_page_context;
/* 2974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2976 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2977 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2978 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2980 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 2981 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2982 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2983 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2984 */       return true;
/*      */     }
/* 2986 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2987 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\mssql\backuprestore_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */