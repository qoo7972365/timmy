/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.HostResourceBean;
/*      */ import com.adventnet.appmanager.bean.MemGraph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class HostLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  819 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  820 */     getRCATrimmedText(div1, rca);
/*  821 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  824 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  825 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  860 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  861 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  864 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  865 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  866 */       set = AMConnectionPool.executeQueryStmt(query);
/*  867 */       if (set.next())
/*      */       {
/*  869 */         String helpLink = set.getString("LINK");
/*  870 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
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
/*  978 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1294 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
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
/* 1992 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
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
/* 2047 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
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
/* 2189 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2190 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2227 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2231 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2248 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2251 */     JspWriter out = null;
/* 2252 */     Object page = this;
/* 2253 */     JspWriter _jspx_out = null;
/* 2254 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2258 */       response.setContentType("text/html;charset=UTF-8");
/* 2259 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2261 */       _jspx_page_context = pageContext;
/* 2262 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2263 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2264 */       session = pageContext.getSession();
/* 2265 */       out = pageContext.getOut();
/* 2266 */       _jspx_out = out;
/*      */       
/* 2268 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/* 2269 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2271 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2282 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2283 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2284 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2288 */         String available = null;
/* 2289 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2290 */         out.write(10);
/*      */         
/* 2292 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2303 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2304 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2305 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2309 */           String unavailable = null;
/* 2310 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2311 */           out.write(10);
/*      */           
/* 2313 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2324 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2325 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2326 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2330 */             String unmanaged = null;
/* 2331 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2332 */             out.write(10);
/*      */             
/* 2334 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2345 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2346 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2347 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2351 */               String scheduled = null;
/* 2352 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2353 */               out.write(10);
/*      */               
/* 2355 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2366 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2367 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2368 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2372 */                 String critical = null;
/* 2373 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2374 */                 out.write(10);
/*      */                 
/* 2376 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2387 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2388 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2389 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2393 */                   String clear = null;
/* 2394 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2395 */                   out.write(10);
/*      */                   
/* 2397 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2408 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2409 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2410 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2414 */                     String warning = null;
/* 2415 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/*      */                     
/* 2419 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2420 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     out.write("\n\n\n\n");
/* 2426 */                     MemGraph memgraph = null;
/* 2427 */                     memgraph = (MemGraph)_jspx_page_context.getAttribute("memgraph", 1);
/* 2428 */                     if (memgraph == null) {
/* 2429 */                       memgraph = new MemGraph();
/* 2430 */                       _jspx_page_context.setAttribute("memgraph", memgraph, 1);
/*      */                     }
/* 2432 */                     out.write(10);
/* 2433 */                     HostResourceBean hrbean = null;
/* 2434 */                     hrbean = (HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2435 */                     if (hrbean == null) {
/* 2436 */                       hrbean = new HostResourceBean();
/* 2437 */                       _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */                     }
/* 2439 */                     out.write(10);
/* 2440 */                     out.write(10);
/* 2441 */                     ManagedApplication mo = null;
/* 2442 */                     synchronized (application) {
/* 2443 */                       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2444 */                       if (mo == null) {
/* 2445 */                         mo = new ManagedApplication();
/* 2446 */                         _jspx_page_context.setAttribute("mo", mo, 4);
/*      */                       }
/*      */                     }
/* 2449 */                     out.write("\n\n<script>\n");
/*      */                     
/* 2451 */                     if (request.getParameter("editPage") != null)
/*      */                     {
/* 2453 */                       out.write("\n   beforetoggleDiv('edit');\n ");
/*      */                     }
/*      */                     
/* 2456 */                     out.write("\nfunction beforetoggleDiv(divname)\n{\n\tchangeport();\n\ttoggleDiv(divname);\n}\nfunction changeToWMIMode(divname)\n{\n\tvar os = '");
/* 2457 */                     out.print(System.getProperty("os.name").toLowerCase());
/* 2458 */                     out.write("';\n\tif (os.indexOf(\"windows\") == -1) {\n\t\talert('");
/* 2459 */                     out.print(FormatUtil.getString("am.webclinet.common.wmimode.monitoring.supported"));
/* 2460 */                     out.write("');\n\t\treturn false;\n\t}\n\tchangeport();\n\ttoggleDiv(divname);\n}\n\nfunction confirmDelete()\n  {\n  var s = confirm(\"");
/* 2461 */                     out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2462 */                     out.write("\")\n  if (s)\n  document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2463 */                     out.print(request.getParameter("type"));
/* 2464 */                     out.write("&select=");
/* 2465 */                     if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                       return;
/* 2467 */                     out.write("\";\n  }\n\n     function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2468 */                     out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2469 */                     out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2470 */                     if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                       return;
/* 2472 */                     out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 2473 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2475 */                     out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2476 */                     out.print(request.getParameter("resourceid"));
/* 2477 */                     out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 2478 */                     out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2479 */                     out.write("\");\n\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2480 */                     if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                       return;
/* 2482 */                     out.write("\";\n          }\n       else { \n    \t   \tvar s = confirm(\"");
/* 2483 */                     out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2484 */                     out.write("\");\n    \t\tif (s){\n  \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2485 */                     if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                       return;
/* 2487 */                     out.write("\"; //No I18N\n\t\t\t }\n\t   \t} \n\t }\n</script>\n");
/* 2488 */                     if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                       return;
/* 2490 */                     out.write(10);
/* 2491 */                     ArrayList menupos = new ArrayList(5);
/* 2492 */                     if (request.isUserInRole("OPERATOR"))
/*      */                     {
/*      */ 
/* 2495 */                       menupos.add("179");
/* 2496 */                       menupos.add("200");
/* 2497 */                       menupos.add("222");
/* 2498 */                       menupos.add("242");
/* 2499 */                       menupos.add("158");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 2505 */                       menupos.add("385");
/*      */                     }
/* 2507 */                     pageContext.setAttribute("menupos", menupos);
/* 2508 */                     out.write(10);
/*      */                     
/* 2510 */                     Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 2511 */                     String allowOPRTelnet = (String)globals.get("allowOPRTelnet");
/* 2512 */                     String allowOPRProcess = (String)globals.get("allowOPRProcess");
/* 2513 */                     String allowAdminTelnet = (String)globals.get("allowAdminTelnet");
/* 2514 */                     pageContext.setAttribute("allowOPRTelnet", allowOPRTelnet);
/* 2515 */                     pageContext.setAttribute("allowOPRProcess", allowOPRProcess);
/* 2516 */                     pageContext.setAttribute("allowAdminTelnet", allowAdminTelnet);
/* 2517 */                     Properties ids = (Properties)request.getAttribute("ids");
/* 2518 */                     String network = null;
/* 2519 */                     Vector v = (Vector)request.getAttribute("category");
/* 2520 */                     String haid = (String)request.getAttribute("haid");
/* 2521 */                     String appname = (String)request.getAttribute("appName");
/* 2522 */                     String resourceid = request.getParameter("resourceid");
/* 2523 */                     String resourcename = (String)request.getAttribute("name");
/* 2524 */                     String mode = (String)request.getAttribute("mode");
/* 2525 */                     Properties memprops1 = null;
/*      */                     
/*      */ 
/*      */ 
/* 2529 */                     String resType = (String)request.getAttribute("hostResType");
/*      */                     
/*      */ 
/* 2532 */                     String SPercent = null;
/* 2533 */                     String SValue = null;
/* 2534 */                     hrbean.setresourceName(resourcename);
/* 2535 */                     hrbean.setResourceId(resourceid);
/* 2536 */                     long maxcollectiontime = hrbean.getmaxcollectiontime();
/* 2537 */                     Hashtable memdata = memgraph.getMemoryData(resourcename, maxcollectiontime, resourceid);
/* 2538 */                     for (Enumeration e = memdata.keys(); e.hasMoreElements();)
/*      */                     {
/* 2540 */                       String entity = (String)e.nextElement();
/* 2541 */                       memprops1 = (Properties)memdata.get(entity);
/*      */                     }
/* 2543 */                     if (resType.equals("AIX"))
/*      */                     {
/* 2545 */                       int Percent = 0;
/* 2546 */                       SPercent = Integer.toString(Percent);
/* 2547 */                       int Value = 0;
/* 2548 */                       SValue = Integer.toString(Value);
/*      */                     }
/*      */                     else
/*      */                     {
/*      */                       try
/*      */                       {
/* 2554 */                         SPercent = memprops1.getProperty("CURVALUE");
/* 2555 */                         SValue = memprops1.getProperty("CURVALUEMB");
/*      */                       }
/*      */                       catch (Exception e) {}
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/* 2563 */                     if (v.size() == 0)
/*      */                     {
/* 2565 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2566 */                       out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2567 */                       out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 2568 */                       if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                         return;
/* 2570 */                       out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\"><br>\n     ");
/* 2571 */                       out.print(FormatUtil.getString("am.webclient.hostleftpage.startmonitor"));
/* 2572 */                       out.write(" </td>\n  </tr>\n</table>\n\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 2578 */                       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 2579 */                       out.print(FormatUtil.getString("am.webclient.hostleftpage.hostdetail"));
/* 2580 */                       out.write("</td>\n  </tr>\n  <tr>\n  ");
/*      */                       
/* 2582 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2583 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2584 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2585 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2586 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2588 */                           out.write(10);
/* 2589 */                           out.write(32);
/* 2590 */                           out.write(32);
/*      */                           
/* 2592 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2593 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2594 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2596 */                           _jspx_th_c_005fwhen_005f0.setTest("${(param.all=='true') || param.editProcess=='true'}");
/* 2597 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2598 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2600 */                               out.write("\n   <td  class=\"leftlinkstd\"><a href=\"/showresource.do?resourceid=");
/* 2601 */                               out.print(resourceid);
/* 2602 */                               out.write("&method=showResourceForResourceID#Availability\" class=\"new-left-links\">");
/* 2603 */                               out.print(FormatUtil.getString("am.webclient.mssqldetails.snapshot"));
/* 2604 */                               out.write("</a></td>\n  ");
/* 2605 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2606 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2610 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2611 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 2614 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2615 */                           out.write(10);
/* 2616 */                           out.write(32);
/* 2617 */                           out.write(32);
/*      */                           
/* 2619 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2620 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2621 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2622 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2623 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 2625 */                               out.write("\n    <td  class=\"leftlinkstd\">");
/* 2626 */                               out.print(FormatUtil.getString("am.webclient.mssqldetails.snapshot"));
/* 2627 */                               out.write("</td>\n  ");
/* 2628 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2629 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2633 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2634 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 2637 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2638 */                           out.write(10);
/* 2639 */                           out.write(32);
/* 2640 */                           out.write(32);
/* 2641 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2642 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2646 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2647 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 2650 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2651 */                       out.write("\n  </tr>\n  ");
/*      */                       
/*      */ 
/* 2654 */                       ArrayList attribIDs = new ArrayList();
/* 2655 */                       ArrayList resIDs = new ArrayList();
/*      */                       
/* 2657 */                       attribIDs.add(ids.getProperty("Health"));
/* 2658 */                       attribIDs.add(ids.getProperty("Availability"));
/* 2659 */                       resIDs.add(resourceid);
/* 2660 */                       Properties alert = getStatus(resIDs, attribIDs);
/*      */                       
/*      */ 
/* 2663 */                       out.write("\n\n  ");
/*      */                       
/* 2665 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2666 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2667 */                       _jspx_th_c_005fif_005f1.setParent(null);
/*      */                       
/* 2669 */                       _jspx_th_c_005fif_005f1.setTest("${!empty ADMIN || !empty DEMO}");
/* 2670 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2671 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 2673 */                           out.write(10);
/* 2674 */                           out.write(32);
/* 2675 */                           out.write(32);
/*      */                           
/* 2677 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2678 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2679 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                           
/* 2681 */                           _jspx_th_c_005fif_005f2.setTest("${empty param.editProcess}");
/* 2682 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2683 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 2685 */                               out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n    ");
/*      */                               
/* 2687 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2688 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2689 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f2);
/* 2690 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2691 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2693 */                                   out.write(10);
/* 2694 */                                   out.write(32);
/* 2695 */                                   out.write(32);
/*      */                                   
/* 2697 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2698 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2699 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2701 */                                   _jspx_th_c_005fwhen_005f1.setTest("${(! empty param.reconfigure && param.reconfigure=='true') || (! empty param.addProcess && param.addProcess=='true')}");
/* 2702 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2703 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2705 */                                       out.write("\n    <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2706 */                                       if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                                         return;
/* 2708 */                                       out.write("&alert=true\" class=\"new-left-links\">");
/* 2709 */                                       out.print(ALERTCONFIG_TEXT);
/* 2710 */                                       out.write("</a>\n  ");
/* 2711 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2712 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2716 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2717 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2720 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2721 */                                   out.write(10);
/* 2722 */                                   out.write(32);
/* 2723 */                                   out.write(32);
/*      */                                   
/* 2725 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2726 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2727 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2729 */                                   _jspx_th_c_005fwhen_005f2.setTest("${(param.all=='true')}");
/* 2730 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2731 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 2733 */                                       out.write("\n  \t   ");
/* 2734 */                                       out.print(ALERTCONFIG_TEXT);
/* 2735 */                                       out.write(10);
/* 2736 */                                       out.write(9);
/* 2737 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2738 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2742 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2743 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 2746 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2747 */                                   out.write(10);
/* 2748 */                                   out.write(32);
/* 2749 */                                   out.write(32);
/*      */                                   
/* 2751 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2752 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2753 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2754 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2755 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2757 */                                       out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2758 */                                       if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                         return;
/* 2760 */                                       out.write("\" class=\"new-left-links\">");
/* 2761 */                                       out.print(ALERTCONFIG_TEXT);
/* 2762 */                                       out.write("</a>\n  ");
/* 2763 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2764 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2768 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2769 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2772 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2773 */                                   out.write(10);
/* 2774 */                                   out.write(32);
/* 2775 */                                   out.write(32);
/* 2776 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2777 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2781 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2782 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2785 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2786 */                               out.write("\n  </td>\n  </tr>\n  ");
/* 2787 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2788 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2792 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2793 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/* 2796 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2797 */                           out.write(10);
/* 2798 */                           out.write(32);
/* 2799 */                           out.write(32);
/* 2800 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2801 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2805 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2806 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                       }
/*      */                       
/* 2809 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2810 */                       out.write(10);
/* 2811 */                       out.write(32);
/* 2812 */                       out.write(32);
/*      */                       
/* 2814 */                       if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(request.getParameter("resourceid")) < EnterpriseUtil.RANGE) && (!request.isUserInRole("OPERATOR")) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                       {
/* 2816 */                         out.write("\n\t  <tr>\n\t\t  <td class=\"leftlinkstd\" > <a href=\"javascript:beforetoggleDiv('edit')\" class=\"new-left-links\">\n\t\t  ");
/* 2817 */                         out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2818 */                         out.write("</a> </td>\n  </tr>\n  ");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 2823 */                         out.write(10);
/* 2824 */                         out.write(32);
/*      */                         
/* 2826 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2827 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2828 */                         _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                         
/* 2830 */                         _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2831 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2832 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 2834 */                             out.write("\n  <tr>\n    <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2835 */                             out.print(request.getParameter("resourceid"));
/* 2836 */                             out.write("&type=");
/* 2837 */                             out.print(request.getParameter("type"));
/* 2838 */                             out.write("&moname=");
/* 2839 */                             out.print(request.getParameter("moname"));
/* 2840 */                             out.write("&method=showdetails&resourcename=");
/* 2841 */                             out.print(request.getParameter("resourcename"));
/* 2842 */                             out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n     ");
/* 2843 */                             out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2844 */                             out.write("</a> </td>\n  </tr>\n ");
/* 2845 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2846 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2850 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2851 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                         }
/*      */                         
/* 2854 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2855 */                         out.write(10);
/* 2856 */                         out.write(32);
/*      */                       }
/* 2858 */                       out.write(10);
/* 2859 */                       out.write(32);
/* 2860 */                       out.write(32);
/*      */                       
/* 2862 */                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2863 */                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2864 */                       _jspx_th_c_005fif_005f3.setParent(null);
/*      */                       
/* 2866 */                       _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2867 */                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2868 */                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                         for (;;) {
/* 2870 */                           out.write("\n  <tr>\n    ");
/*      */                           
/* 2872 */                           if ("Node".equalsIgnoreCase(request.getParameter("type")))
/*      */                           {
/* 2874 */                             out.write("\n     <td class=\"leftlinkstd\" > <a href=\"/showresource.do?resourceid=");
/* 2875 */                             out.print(request.getParameter("resourceid"));
/* 2876 */                             out.write("&type=");
/* 2877 */                             out.print(request.getParameter("type"));
/* 2878 */                             out.write("&moname=");
/* 2879 */                             out.print(request.getParameter("moname"));
/* 2880 */                             out.write("&method=showdetails&resourcename=");
/* 2881 */                             out.print(request.getParameter("resourcename"));
/* 2882 */                             out.write("&editPage=true\" class=\"new-left-links\">\n      ");
/* 2883 */                             out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2884 */                             out.write("</a> </td>\n    ");
/*      */                           }
/*      */                           else
/*      */                           {
/* 2888 */                             out.write("\n\n    <td class=\"leftlinkstd\" > <a href=\"/manageConfMons.do?haid=");
/* 2889 */                             out.print(haid);
/* 2890 */                             out.write("&method=editPreConfMonitor&resourceid=");
/* 2891 */                             out.print(request.getParameter("resourceid"));
/* 2892 */                             out.write("&type=");
/* 2893 */                             out.print(request.getParameter("type"));
/* 2894 */                             out.write("&componentName=host&mode=");
/* 2895 */                             out.print(mode);
/* 2896 */                             out.write("&tabId=true&\" class=\"new-left-links\">\n      ");
/* 2897 */                             out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2898 */                             out.write("</a> </td>\n    ");
/*      */                           }
/*      */                           
/* 2901 */                           out.write("\n  </tr>\n  ");
/* 2902 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2903 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2907 */                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2908 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                       }
/*      */                       
/* 2911 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2912 */                       out.write(10);
/* 2913 */                       out.write(32);
/* 2914 */                       out.write(32);
/*      */                       
/* 2916 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2917 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2918 */                       _jspx_th_c_005fif_005f4.setParent(null);
/*      */                       
/* 2920 */                       _jspx_th_c_005fif_005f4.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2921 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2922 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2924 */                           out.write("\n  <tr>\n  ");
/*      */                           
/* 2926 */                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                           {
/*      */ 
/* 2929 */                             out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2930 */                             out.print(FormatUtil.getString("Manage"));
/* 2931 */                             out.write("</A></td>\n    ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2937 */                             out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2938 */                             out.print(FormatUtil.getString("UnManage"));
/* 2939 */                             out.write("</A></td>\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 2943 */                           out.write("\n  </tr>\n  ");
/* 2944 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2945 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2949 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2950 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2953 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2954 */                       out.write("\n  \n    ");
/*      */                       
/* 2956 */                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2957 */                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2958 */                       _jspx_th_c_005fif_005f5.setParent(null);
/*      */                       
/* 2960 */                       _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO }");
/* 2961 */                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2962 */                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                         for (;;) {
/* 2964 */                           out.write("\n        ");
/*      */                           
/* 2966 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2967 */                           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2968 */                           _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                           
/* 2970 */                           _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2971 */                           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2972 */                           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                             for (;;) {
/* 2974 */                               out.write("\n            <tr>\n                <td colspan=\"2\" class=\"leftlinkstd\">\n                    <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">\n                        ");
/* 2975 */                               out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2976 */                               out.write("\n                    </A>\n                </td>\n            </tr>\n        ");
/* 2977 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2978 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2982 */                           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2983 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                           }
/*      */                           
/* 2986 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2987 */                           out.write("\n        ");
/*      */                           
/* 2989 */                           PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2990 */                           _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2991 */                           _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f5);
/*      */                           
/* 2993 */                           _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2994 */                           int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2995 */                           if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                             for (;;) {
/* 2997 */                               out.write("\n\n            <td height=\"21\" class=\"leftlinkstd\">\n                <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n                    ");
/* 2998 */                               out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2999 */                               out.write("\n                </a>\n            </td>\n        ");
/* 3000 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3001 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3005 */                           if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3006 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                           }
/*      */                           
/* 3009 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3010 */                           out.write("\n    ");
/* 3011 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3012 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3016 */                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3017 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                       }
/*      */                       
/* 3020 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3021 */                       out.write("\n\n  ");
/*      */                       
/* 3023 */                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3024 */                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3025 */                       _jspx_th_c_005fif_005f6.setParent(null);
/*      */                       
/* 3027 */                       _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 3028 */                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3029 */                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                         for (;;) {
/* 3031 */                           out.write("\n  <tr>\n  ");
/*      */                           
/* 3033 */                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3034 */                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3035 */                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/* 3037 */                           _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3038 */                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3039 */                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                             for (;;) {
/* 3041 */                               out.write("\n  <td class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3042 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.addprocess"));
/* 3043 */                               out.write("</a> </td>\n  ");
/* 3044 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3045 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3049 */                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3050 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                           }
/*      */                           
/* 3053 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3054 */                           out.write(10);
/* 3055 */                           out.write(32);
/* 3056 */                           out.write(32);
/*      */                           
/* 3058 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3059 */                           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3060 */                           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/* 3062 */                           _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3063 */                           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3064 */                           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                             for (;;) {
/* 3066 */                               out.write("\n   <td class=\"leftlinkstd\"><a href=\"javascript:void(0)\" class=\"new-left-links\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('../HostResource.do?getProcessList=true&resourceid=");
/* 3067 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_logic_005fnotPresent_005f1, _jspx_page_context))
/*      */                                 return;
/* 3069 */                               out.write("&PercentMB=");
/* 3070 */                               out.print(SPercent);
/* 3071 */                               out.write("&MBValue=");
/* 3072 */                               out.print(SValue);
/* 3073 */                               out.write("&resType=");
/* 3074 */                               out.print(resType);
/* 3075 */                               out.write("','950','500','100','100')\">");
/* 3076 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.addprocess"));
/* 3077 */                               out.write("</a></td>\n    <!-- <td class=\"leftlinkstd\" > <a href=\"javascript:toggleDiv('addprocess')\" class=\"new-left-links\">\n      Add Process Monitoring</a> </td>-->\n\t ");
/* 3078 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3079 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3083 */                           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3084 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                           }
/*      */                           
/* 3087 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3088 */                           out.write("\n  </tr>\n  ");
/* 3089 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3090 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3094 */                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3095 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                       }
/*      */                       
/* 3098 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3099 */                       out.write("\n  \n  ");
/*      */                       
/* 3101 */                       if ((mode != null) && (mode.equalsIgnoreCase("WMI")))
/*      */                       {
/* 3103 */                         out.write("\n    ");
/*      */                         
/* 3105 */                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3106 */                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3107 */                         _jspx_th_c_005fif_005f7.setParent(null);
/*      */                         
/* 3109 */                         _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO }");
/* 3110 */                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3111 */                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                           for (;;) {
/* 3113 */                             out.write("\n      <tr>\n      ");
/*      */                             
/* 3115 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3116 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3117 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 3119 */                             _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3120 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3121 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 3123 */                                 out.write("\n      <td class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n      ");
/* 3124 */                                 out.print(FormatUtil.getString("am.webclient.hostleftpage.addservice"));
/* 3125 */                                 out.write("</a> </td>\n      ");
/* 3126 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3127 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3131 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3132 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                             }
/*      */                             
/* 3135 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3136 */                             out.write("\n      ");
/*      */                             
/* 3138 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3139 */                             _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3140 */                             _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 3142 */                             _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3143 */                             int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3144 */                             if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                               for (;;) {
/* 3146 */                                 out.write("\n      <td class=\"leftlinkstd\"><a href=\"javascript:void(0)\" class=\"new-left-links\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('../HostResource.do?getServiceList=true&resourceid=");
/* 3147 */                                 if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                                   return;
/* 3149 */                                 out.write("&resType=");
/* 3150 */                                 out.print(resType);
/* 3151 */                                 out.write("','950','500','100','100')\">");
/* 3152 */                                 out.print(FormatUtil.getString("am.webclient.hostleftpage.addservice"));
/* 3153 */                                 out.write("</a></td>\n      ");
/* 3154 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3155 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3159 */                             if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3160 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                             }
/*      */                             
/* 3163 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3164 */                             out.write("\n      </tr>\n    ");
/* 3165 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3166 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3170 */                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3171 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                         }
/*      */                         
/* 3174 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3175 */                         out.write("\n    ");
/*      */                       }
/*      */                       
/*      */ 
/* 3179 */                       out.write(10);
/* 3180 */                       out.write(32);
/* 3181 */                       out.write(32);
/*      */                       
/* 3183 */                       IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3184 */                       _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3185 */                       _jspx_th_c_005fif_005f8.setParent(null);
/*      */                       
/* 3187 */                       _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO}");
/* 3188 */                       int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3189 */                       if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                         for (;;) {
/* 3191 */                           out.write("\n  <tr>\n  ");
/*      */                           
/* 3193 */                           PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3194 */                           _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3195 */                           _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f8);
/*      */                           
/* 3197 */                           _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3198 */                           int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3199 */                           if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                             for (;;) {
/* 3201 */                               out.write("\n  <td class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3202 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.addScheduledTasks"));
/* 3203 */                               out.write("</a> </td>\n  ");
/* 3204 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3205 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3209 */                           if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3210 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                           }
/*      */                           
/* 3213 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3214 */                           out.write(10);
/* 3215 */                           out.write(32);
/* 3216 */                           out.write(32);
/*      */                           
/* 3218 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3219 */                           _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3220 */                           _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fif_005f8);
/*      */                           
/* 3222 */                           _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3223 */                           int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3224 */                           if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                             for (;;) {
/* 3226 */                               out.write("\n   <td class=\"leftlinkstd\"><a href=\"javascript:void(0)\" class=\"new-left-links\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('../HostResource.do?getScheduledTasksList=true&resourceid=");
/* 3227 */                               if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fnotPresent_005f3, _jspx_page_context))
/*      */                                 return;
/* 3229 */                               out.write("&resType=");
/* 3230 */                               out.print(resType);
/* 3231 */                               out.write("','950','500','100','100')\">");
/* 3232 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.addScheduledTasks"));
/* 3233 */                               out.write("</a></td> ");
/* 3234 */                               out.write("\n    <!-- <td class=\"leftlinkstd\" > <a href=\"javascript:toggleDiv('addprocess')\" class=\"new-left-links\">\n      Add Process Monitoring</a> </td>-->\n   ");
/* 3235 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3236 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3240 */                           if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3241 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                           }
/*      */                           
/* 3244 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3245 */                           out.write("\n  </tr>\n  ");
/* 3246 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3247 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3251 */                       if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3252 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                       }
/*      */                       
/* 3255 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3256 */                       out.write(10);
/* 3257 */                       out.write(32);
/* 3258 */                       out.write(32);
/*      */                       
/* 3260 */                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3261 */                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3262 */                       _jspx_th_c_005fif_005f9.setParent(null);
/*      */                       
/* 3264 */                       _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO}");
/* 3265 */                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3266 */                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                         for (;;) {
/* 3268 */                           out.write("\n  <tr>\n  ");
/*      */                           
/* 3270 */                           PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3271 */                           _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3272 */                           _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f9);
/*      */                           
/* 3274 */                           _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3275 */                           int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3276 */                           if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                             for (;;) {
/* 3278 */                               out.write("\n  <td class=\"leftlinkstd\" ><a href=\"javascript:alertUser()\"\n              class=\"new-left-links\">");
/* 3279 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescriptsalone"));
/* 3280 */                               out.write("\n              </a></td>\n  ");
/* 3281 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3282 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3286 */                           if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3287 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                           }
/*      */                           
/* 3290 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3291 */                           out.write(10);
/* 3292 */                           out.write(32);
/* 3293 */                           out.write(32);
/*      */                           
/* 3295 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3296 */                           _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3297 */                           _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f9);
/*      */                           
/* 3299 */                           _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3300 */                           int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3301 */                           if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                             for (;;) {
/* 3303 */                               out.write("\n    <td class=\"leftlinkstd\" > <a href='/showresource.do?type=Script Monitor&method=getAssociateMonitors&hostid=");
/* 3304 */                               out.print(resourceid);
/* 3305 */                               out.write("'\n              class=\"new-left-links\">");
/* 3306 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associatescriptsalone"));
/* 3307 */                               out.write("\n              </a></td>\n\t ");
/* 3308 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3309 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3313 */                           if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3314 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                           }
/*      */                           
/* 3317 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3318 */                           out.write("\n  </tr>\n\n  <tr>\n  ");
/*      */                           
/* 3320 */                           PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3321 */                           _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3322 */                           _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f9);
/*      */                           
/* 3324 */                           _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3325 */                           int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3326 */                           if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                             for (;;) {
/* 3328 */                               out.write("\n  <td class=\"leftlinkstd\" ><a href=\"javascript:alertUser()\"\n              class=\"new-left-links\">");
/* 3329 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associateurls"));
/* 3330 */                               out.write("\n              </a></td>\n  ");
/* 3331 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3332 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3336 */                           if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3337 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                           }
/*      */                           
/* 3340 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3341 */                           out.write(10);
/* 3342 */                           out.write(32);
/* 3343 */                           out.write(32);
/*      */                           
/* 3345 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3346 */                           _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3347 */                           _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f9);
/*      */                           
/* 3349 */                           _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3350 */                           int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3351 */                           if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                             for (;;) {
/* 3353 */                               out.write("\n    <td class=\"leftlinkstd\" > <a href='/showresource.do?type=UrlMonitor&method=getAssociateMonitors&hostid=");
/* 3354 */                               out.print(resourceid);
/* 3355 */                               out.write("'\n              class=\"new-left-links\">");
/* 3356 */                               out.print(FormatUtil.getString("am.webclient.hostleftpage.associateurls"));
/* 3357 */                               out.write("\n              </a></td>\n         ");
/* 3358 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3359 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3363 */                           if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3364 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                           }
/*      */                           
/* 3367 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3368 */                           out.write("\n  </tr>\n  ");
/* 3369 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3370 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3374 */                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3375 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                       }
/*      */                       
/* 3378 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3379 */                       out.write(10);
/* 3380 */                       out.write(32);
/* 3381 */                       out.write(32);
/*      */                       
/* 3383 */                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3384 */                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3385 */                       _jspx_th_c_005fif_005f10.setParent(null);
/*      */                       
/* 3387 */                       _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 3388 */                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3389 */                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                         for (;;) {
/* 3391 */                           out.write("\n    \t <tr>\n    \t <td colspan=\"2\" class=\"leftlinkstd\">\n    \t ");
/* 3392 */                           out.print(FaultUtil.getAlertTemplateURL(resourceid, resourcename, resType, resType));
/* 3393 */                           out.write("\n    \t </td>\n     \t</tr>\n  ");
/* 3394 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3395 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3399 */                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3400 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                       }
/*      */                       
/* 3403 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3404 */                       out.write("\n   ");
/*      */                       
/* 3406 */                       IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3407 */                       _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3408 */                       _jspx_th_c_005fif_005f11.setParent(null);
/*      */                       
/* 3410 */                       _jspx_th_c_005fif_005f11.setTest("${!empty ADMIN || !empty DEMO}");
/* 3411 */                       int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3412 */                       if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                         for (;;) {
/* 3414 */                           out.write("\n    \t <tr>\n    \t <td colspan=\"2\" class=\"leftlinkstd\">\n\t\t\t<a  class=\"new-left-links\" href=\"javascript:void(0)\" onClick=\"MM_openBrWindow('/HostResourceDispatch.do?method=showHardwareConfiguration&resourceid=");
/* 3415 */                           out.print(resourceid);
/* 3416 */                           out.write("','','width=500,height=350, scrollbars=yes,resizable=yes')\"> ");
/* 3417 */                           out.print(FormatUtil.getString("am.hardware.configuration.text"));
/* 3418 */                           out.write(" </a>\n    \t </td>\n     \t</tr>\n  ");
/* 3419 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3420 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3424 */                       if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3425 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                       }
/*      */                       
/* 3428 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3429 */                       out.write("\n   ");
/*      */                       
/* 3431 */                       IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3432 */                       _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3433 */                       _jspx_th_c_005fif_005f12.setParent(null);
/*      */                       
/* 3435 */                       _jspx_th_c_005fif_005f12.setTest("${!empty ADMIN || !empty DEMO}");
/* 3436 */                       int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3437 */                       if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                         for (;;) {
/* 3439 */                           out.write("\n    \t <tr>\n\t\t\t<td class=\"leftlinkstd\" colspan=\"2\"> <a href=\"#\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=performance&showDiskIOLink=true&frommondetails=false&resourceid=");
/* 3440 */                           out.print(resourceid);
/* 3441 */                           out.write("',1150,600,300,100)\" class=\"new-left-links\">");
/* 3442 */                           out.print(FormatUtil.getString("am.webclient.hostleftpage.datacollection.settings.text"));
/* 3443 */                           out.write("</a></td>\n     \t</tr>\n  ");
/* 3444 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3445 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3449 */                       if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3450 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                       }
/*      */                       
/* 3453 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3454 */                       out.write("  \n  ");
/*      */                       
/* 3456 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3457 */                       _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3458 */                       _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */                       
/* 3460 */                       _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3461 */                       int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3462 */                       if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                         for (;;) {
/* 3464 */                           out.write(10);
/* 3465 */                           out.write(32);
/* 3466 */                           out.write(32);
/*      */                           
/* 3468 */                           String resourceid_poll = request.getParameter("resourceid");
/* 3469 */                           String resourcetype = request.getParameter("type");
/*      */                           
/* 3471 */                           out.write("\n    <tr>\n    <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n    <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3472 */                           out.print(resourceid_poll);
/* 3473 */                           out.write("&resourcetype=");
/* 3474 */                           out.print(resourcetype);
/* 3475 */                           out.write("\" class=\"new-left-links\"> ");
/* 3476 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3477 */                           out.write("</a></td>\n  </tr>\n  ");
/* 3478 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3479 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3483 */                       if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3484 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                       }
/*      */                       
/* 3487 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3488 */                       out.write(10);
/* 3489 */                       out.write(32);
/* 3490 */                       out.write(32);
/*      */                       
/* 3492 */                       PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3493 */                       _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3494 */                       _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                       
/* 3496 */                       _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3497 */                       int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3498 */                       if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                         for (;;) {
/* 3500 */                           out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3501 */                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3502 */                           out.write("</a></td>\n      </td>\n    ");
/* 3503 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3504 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3508 */                       if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3509 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                       }
/*      */                       
/* 3512 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3513 */                       out.write("\n     ");
/* 3514 */                       out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                       
/* 3516 */                       if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                       {
/* 3518 */                         Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3519 */                         String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                         
/* 3521 */                         String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3522 */                         String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3523 */                         if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                         {
/* 3525 */                           if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                           {
/*      */ 
/* 3528 */                             out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3529 */                             out.print(ciInfoUrl);
/* 3530 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3531 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3532 */                             out.write("</a></td>");
/* 3533 */                             out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3534 */                             out.print(ciRLUrl);
/* 3535 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3536 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3537 */                             out.write("</a></td>");
/* 3538 */                             out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                           }
/* 3542 */                           else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                           {
/*      */ 
/* 3545 */                             out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3546 */                             out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3547 */                             out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3548 */                             out.print(ciInfoUrl);
/* 3549 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3550 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3551 */                             out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3552 */                             out.print(ciRLUrl);
/* 3553 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3554 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3555 */                             out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/* 3560 */                       out.write("\n \n \n\n");
/* 3561 */                       out.write("\n</table>\n\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  ");
/*      */                       
/* 3563 */                       PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3564 */                       _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3565 */                       _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                       
/* 3567 */                       _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 3568 */                       int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3569 */                       if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                         for (;;) {
/* 3571 */                           out.write("\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3572 */                           out.print(FormatUtil.getString("am.webclient.serverinfo"));
/* 3573 */                           out.write("</td>\n  </tr>\n  <tr>\n\t\t <td class=\"leftlinkstd\"><a href=\"javascript:void(0)\" class=\"new-left-links\" style=\"font-weight:normal;\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&show=true&resourceid=");
/* 3574 */                           out.print(resourceid);
/* 3575 */                           out.write("&PercentMB=");
/* 3576 */                           out.print(SPercent);
/* 3577 */                           out.write("&MBValue=");
/* 3578 */                           out.print(SValue);
/* 3579 */                           out.write("&resType=");
/* 3580 */                           out.print(resType);
/* 3581 */                           out.write("')\">");
/* 3582 */                           out.print(FormatUtil.getString("am.webclient.hostresource.showprocess"));
/* 3583 */                           out.write("</a></td>\n  </tr>\n  ");
/* 3584 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3585 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3589 */                       if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3590 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                       }
/*      */                       
/* 3593 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3594 */                       out.write(10);
/* 3595 */                       out.write(32);
/*      */                       
/* 3597 */                       PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3598 */                       _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3599 */                       _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */                       
/* 3601 */                       _jspx_th_logic_005fpresent_005f10.setRole("OPERATOR,USERS");
/* 3602 */                       int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3603 */                       if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                         for (;;) {
/* 3605 */                           out.write(10);
/* 3606 */                           out.write(32);
/* 3607 */                           out.write(32);
/*      */                           
/* 3609 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3610 */                           _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3611 */                           _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                           
/* 3613 */                           _jspx_th_logic_005fnotPresent_005f7.setRole("ADMIN");
/* 3614 */                           int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3615 */                           if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                             for (;;) {
/* 3617 */                               out.write("\n   ");
/*      */                               
/* 3619 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3620 */                               _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3621 */                               _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_logic_005fnotPresent_005f7);
/*      */                               
/* 3623 */                               _jspx_th_logic_005fnotPresent_005f8.setRole("ENTERPRISEADMIN");
/* 3624 */                               int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3625 */                               if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                                 for (;;) {
/* 3627 */                                   out.write(10);
/* 3628 */                                   out.write(9);
/*      */                                   
/* 3630 */                                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3631 */                                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3632 */                                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                   
/* 3634 */                                   _jspx_th_c_005fif_005f13.setTest("${allowOPRProcess=='true' || allowOPRTelnet=='true'}");
/* 3635 */                                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3636 */                                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                     for (;;) {
/* 3638 */                                       out.write("\n\t\t<tr>\n\t\t  <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3639 */                                       out.print(FormatUtil.getString("am.webclient.serverinfo"));
/* 3640 */                                       out.write("</td>\n\t\t</tr>\n\t");
/* 3641 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3642 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3646 */                                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3647 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                   }
/*      */                                   
/* 3650 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3651 */                                   out.write(10);
/* 3652 */                                   out.write(9);
/*      */                                   
/* 3654 */                                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3655 */                                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3656 */                                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                   
/* 3658 */                                   _jspx_th_c_005fif_005f14.setTest("${allowOPRProcess=='true'}");
/* 3659 */                                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3660 */                                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                     for (;;) {
/* 3662 */                                       out.write("\n\t<tr>\n\t\t <td class=\"leftlinkstd\"><a href=\"javascript:void(0)\" class=\"new-left-links\" style=\"font-weight:normal;\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&show=true&resourceid=");
/* 3663 */                                       out.print(resourceid);
/* 3664 */                                       out.write("&PercentMB=");
/* 3665 */                                       out.print(SPercent);
/* 3666 */                                       out.write("&MBValue=");
/* 3667 */                                       out.print(SValue);
/* 3668 */                                       out.write("&resType=");
/* 3669 */                                       out.print(resType);
/* 3670 */                                       out.write("')\">");
/* 3671 */                                       out.print(FormatUtil.getString("am.webclient.hostresource.showprocess"));
/* 3672 */                                       out.write("</a></td>\n\t</tr>\n\t");
/* 3673 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3674 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3678 */                                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3679 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                   }
/*      */                                   
/* 3682 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3683 */                                   out.write(10);
/* 3684 */                                   out.write(9);
/* 3685 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3686 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3690 */                               if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3691 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                               }
/*      */                               
/* 3694 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3695 */                               out.write("\n   ");
/* 3696 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3697 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3701 */                           if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3702 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                           }
/*      */                           
/* 3705 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3706 */                           out.write(10);
/* 3707 */                           out.write(32);
/* 3708 */                           out.write(32);
/* 3709 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3710 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3714 */                       if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3715 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                       }
/*      */                       
/* 3718 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3719 */                       out.write(10);
/* 3720 */                       out.write(32);
/* 3721 */                       out.write(32);
/*      */                       
/* 3723 */                       PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3724 */                       _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 3725 */                       _jspx_th_logic_005fpresent_005f11.setParent(null);
/*      */                       
/* 3727 */                       _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 3728 */                       int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 3729 */                       if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                         for (;;) {
/* 3731 */                           out.write("\n  \t");
/*      */                           
/* 3733 */                           IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3734 */                           _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3735 */                           _jspx_th_c_005fif_005f15.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                           
/* 3737 */                           _jspx_th_c_005fif_005f15.setTest("${allowAdminTelnet=='true'}");
/* 3738 */                           int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3739 */                           if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                             for (;;) {
/* 3741 */                               out.write(10);
/* 3742 */                               out.write(9);
/* 3743 */                               if ((mode != null) && ((mode.equals("SSH")) || (mode.equals("TELNET"))))
/*      */                               {
/* 3745 */                                 out.write("\n     \t\t<tr>\n        \t\t<td width=\"51%\" class=\"leftlinkstd\" > <a href='#'  class=\"new-left-links\" onClick=\"fnOpenNewWindow('/HostResource.do?TelnetClient=true&resourceid=");
/* 3746 */                                 out.print(resourceid);
/* 3747 */                                 out.write("')\">");
/* 3748 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.executecommands"));
/* 3749 */                                 out.write("</a></td>\n      \t\t</tr> ");
/*      */                               }
/* 3751 */                               out.write("\n      \t");
/* 3752 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3753 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3757 */                           if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3758 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                           }
/*      */                           
/* 3761 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3762 */                           out.write(10);
/* 3763 */                           out.write(32);
/* 3764 */                           out.write(32);
/* 3765 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 3766 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3770 */                       if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 3771 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                       }
/*      */                       
/* 3774 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 3775 */                       out.write("\n\n  ");
/*      */                       
/* 3777 */                       PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3778 */                       _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 3779 */                       _jspx_th_logic_005fpresent_005f12.setParent(null);
/*      */                       
/* 3781 */                       _jspx_th_logic_005fpresent_005f12.setRole("OPERATOR");
/* 3782 */                       int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 3783 */                       if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                         for (;;) {
/* 3785 */                           out.write("\n\t  ");
/*      */                           
/* 3787 */                           IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3788 */                           _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3789 */                           _jspx_th_c_005fif_005f16.setParent(_jspx_th_logic_005fpresent_005f12);
/*      */                           
/* 3791 */                           _jspx_th_c_005fif_005f16.setTest("${allowOPRTelnet=='true'}");
/* 3792 */                           int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3793 */                           if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                             for (;;) {
/* 3795 */                               out.write(10);
/* 3796 */                               out.write(9);
/* 3797 */                               out.write(9);
/* 3798 */                               if ((mode != null) && ((mode.equals("SSH")) || (mode.equals("TELNET"))))
/*      */                               {
/* 3800 */                                 out.write("\n\t\t\t<tr>\n\t\t\t\t<td width=\"51%\" class=\"leftlinkstd\" > <a href='#'  class=\"new-left-links\" onClick=\"fnOpenNewWindow('/HostResource.do?TelnetClient=true&resourceid=");
/* 3801 */                                 out.print(resourceid);
/* 3802 */                                 out.write("')\">");
/* 3803 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.executecommands"));
/* 3804 */                                 out.write("</a></td>\n\t\t\t</tr> ");
/*      */                               }
/* 3806 */                               out.write("\n\t  ");
/* 3807 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3808 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3812 */                           if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3813 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                           }
/*      */                           
/* 3816 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3817 */                           out.write(10);
/* 3818 */                           out.write(32);
/* 3819 */                           out.write(32);
/* 3820 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 3821 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3825 */                       if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 3826 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                       }
/*      */                       
/* 3829 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 3830 */                       out.write("\n</table>\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3831 */                       out.print(FormatUtil.getString("am.webclient.dotnet.rca"));
/* 3832 */                       out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" >\n    \t <a  class=\"new-left-links\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3833 */                       out.print(resourceid);
/* 3834 */                       out.write("&attributeid=");
/* 3835 */                       out.print(ids.getProperty("Health"));
/* 3836 */                       out.write("')\"> ");
/* 3837 */                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3838 */                       out.write(" </a>\n\n\t     </td>\n\t\t     <td width=\"51%\" > <a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3839 */                       out.print(resourceid);
/* 3840 */                       out.write("&attributeid=");
/* 3841 */                       out.print(ids.getProperty("Health"));
/* 3842 */                       out.write("')\">");
/* 3843 */                       out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + ids.getProperty("Health"))));
/* 3844 */                       out.write("</a></td>\n\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n  <td width=\"49%\" >\n  <a href=\"javascript:void(0)\"  class=\"new-left-links\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3845 */                       out.print(resourceid);
/* 3846 */                       out.write("&attributeid=");
/* 3847 */                       out.print(ids.getProperty("Availability"));
/* 3848 */                       out.write("')\">");
/* 3849 */                       out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3850 */                       out.write("</a></td>\n  <td width=\"51%\" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3851 */                       out.print(resourceid);
/* 3852 */                       out.write("&attributeid=");
/* 3853 */                       out.print(ids.getProperty("Availability"));
/* 3854 */                       out.write("')\"  >");
/* 3855 */                       out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + ids.getProperty("Availability"))));
/* 3856 */                       out.write("</a></td>\n  </tr>\n</table>\n<br>\n  ");
/* 3857 */                       out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                       
/*      */ 
/*      */ 
/* 3861 */                       boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3862 */                       if (EnterpriseUtil.isIt360MSPEdition)
/*      */                       {
/* 3864 */                         showAssociatedBSG = false;
/*      */                         
/*      */ 
/*      */ 
/* 3868 */                         CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3869 */                         CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3870 */                         String loginName = request.getUserPrincipal().getName();
/* 3871 */                         CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                         
/* 3873 */                         if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                         {
/* 3875 */                           showAssociatedBSG = true;
/*      */                         }
/*      */                       }
/* 3878 */                       String monitorType = request.getParameter("type");
/* 3879 */                       ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3880 */                       boolean mon = conf1.isConfMonitor(monitorType);
/* 3881 */                       if (showAssociatedBSG)
/*      */                       {
/* 3883 */                         Hashtable associatedmgs = new Hashtable();
/* 3884 */                         String resId = request.getParameter("resourceid");
/* 3885 */                         request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3886 */                         if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                         {
/* 3888 */                           mon = false;
/*      */                         }
/*      */                         
/* 3891 */                         if (!mon)
/*      */                         {
/* 3893 */                           out.write(10);
/* 3894 */                           out.write(10);
/*      */                           
/* 3896 */                           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3897 */                           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3898 */                           _jspx_th_c_005fif_005f17.setParent(null);
/*      */                           
/* 3900 */                           _jspx_th_c_005fif_005f17.setTest("${!empty associatedmgs}");
/* 3901 */                           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3902 */                           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                             for (;;) {
/* 3904 */                               out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3905 */                               out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3906 */                               out.write("</td>\n        </tr>\n        ");
/*      */                               
/* 3908 */                               ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3909 */                               _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3910 */                               _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f17);
/*      */                               
/* 3912 */                               _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                               
/* 3914 */                               _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                               
/* 3916 */                               _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3917 */                               int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                               try {
/* 3919 */                                 int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3920 */                                 if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                   for (;;) {
/* 3922 */                                     out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3923 */                                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3981 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 3925 */                                     out.write("&method=showApplication\" title=\"");
/* 3926 */                                     if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
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
/*      */ 
/*      */ 
/* 3981 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 3928 */                                     out.write("\"  class=\"new-left-links\">\n         ");
/* 3929 */                                     if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
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
/* 3981 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 3931 */                                     out.write("\n    \t");
/* 3932 */                                     out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3933 */                                     out.write("\n         </a></td>\n        <td>");
/*      */                                     
/* 3935 */                                     PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3936 */                                     _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 3937 */                                     _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                     
/* 3939 */                                     _jspx_th_logic_005fpresent_005f13.setRole("ADMIN");
/* 3940 */                                     int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 3941 */                                     if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                       for (;;) {
/* 3943 */                                         out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3944 */                                         if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fpresent_005f13, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
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
/*      */ 
/* 3981 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 3946 */                                         out.write(39);
/* 3947 */                                         out.write(44);
/* 3948 */                                         out.write(39);
/* 3949 */                                         out.print(resId);
/* 3950 */                                         out.write(39);
/* 3951 */                                         out.write(44);
/* 3952 */                                         out.write(39);
/* 3953 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3954 */                                         out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3955 */                                         out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3956 */                                         out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3957 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 3958 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3962 */                                     if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 3963 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
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
/* 3981 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 3966 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 3967 */                                     out.write("</td>\n        </tr>\n\t");
/* 3968 */                                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3969 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3973 */                                 if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3981 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/*      */                               }
/*      */                               catch (Throwable _jspx_exception)
/*      */                               {
/*      */                                 for (;;)
/*      */                                 {
/* 3977 */                                   int tmp11004_11003 = 0; int[] tmp11004_11001 = _jspx_push_body_count_c_005fforEach_005f0; int tmp11006_11005 = tmp11004_11001[tmp11004_11003];tmp11004_11001[tmp11004_11003] = (tmp11006_11005 - 1); if (tmp11006_11005 <= 0) break;
/* 3978 */                                   out = _jspx_page_context.popBody(); }
/* 3979 */                                 _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                               } finally {
/* 3981 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 3982 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                               }
/* 3984 */                               out.write("\n      </table>\n ");
/* 3985 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3986 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3990 */                           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3991 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                           }
/*      */                           
/* 3994 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3995 */                           out.write(10);
/* 3996 */                           out.write(32);
/*      */                           
/* 3998 */                           IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3999 */                           _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4000 */                           _jspx_th_c_005fif_005f18.setParent(null);
/*      */                           
/* 4002 */                           _jspx_th_c_005fif_005f18.setTest("${empty associatedmgs}");
/* 4003 */                           int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4004 */                           if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                             for (;;) {
/* 4006 */                               out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4007 */                               out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4008 */                               out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4009 */                               out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4010 */                               out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4011 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4012 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4016 */                           if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4017 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                           }
/*      */                           
/* 4020 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4021 */                           out.write(10);
/* 4022 */                           out.write(32);
/* 4023 */                           out.write(10);
/*      */ 
/*      */                         }
/* 4026 */                         else if (mon)
/*      */                         {
/*      */ 
/*      */ 
/* 4030 */                           out.write(10);
/*      */                           
/* 4032 */                           IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4033 */                           _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 4034 */                           _jspx_th_c_005fif_005f19.setParent(null);
/*      */                           
/* 4036 */                           _jspx_th_c_005fif_005f19.setTest("${!empty associatedmgs}");
/* 4037 */                           int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 4038 */                           if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                             for (;;) {
/* 4040 */                               out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                               
/* 4042 */                               ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4043 */                               _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4044 */                               _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f19);
/*      */                               
/* 4046 */                               _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                               
/* 4048 */                               _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                               
/* 4050 */                               _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4051 */                               int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                               try {
/* 4053 */                                 int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4054 */                                 if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                   for (;;) {
/* 4056 */                                     out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4057 */                                     if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                     {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 4059 */                                     out.write("&method=showApplication\" title=\"");
/* 4060 */                                     if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                     {
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
/*      */ 
/*      */ 
/* 4115 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 4062 */                                     out.write("\"  class=\"staticlinks\">\n         ");
/* 4063 */                                     if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                     {
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
/* 4115 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 4065 */                                     out.write("\n    \t");
/* 4066 */                                     out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4067 */                                     out.write("</a></span>\t\n\t\t ");
/*      */                                     
/* 4069 */                                     PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4070 */                                     _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 4071 */                                     _jspx_th_logic_005fpresent_005f14.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                     
/* 4073 */                                     _jspx_th_logic_005fpresent_005f14.setRole("ADMIN");
/* 4074 */                                     int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 4075 */                                     if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                       for (;;) {
/* 4077 */                                         out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4078 */                                         if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                         {
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
/*      */ 
/* 4115 */                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                         }
/* 4080 */                                         out.write(39);
/* 4081 */                                         out.write(44);
/* 4082 */                                         out.write(39);
/* 4083 */                                         out.print(resId);
/* 4084 */                                         out.write(39);
/* 4085 */                                         out.write(44);
/* 4086 */                                         out.write(39);
/* 4087 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4088 */                                         out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4089 */                                         out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4090 */                                         out.write("\"  title=\"<fmt:message key=\"am.webclient.quickremoval.monitorgroup.txt\" />\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4091 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 4092 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4096 */                                     if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 4097 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
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
/* 4115 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 4100 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 4101 */                                     out.write("\n\n\t\t \t");
/* 4102 */                                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4103 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4107 */                                 if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/*      */                               }
/*      */                               catch (Throwable _jspx_exception)
/*      */                               {
/*      */                                 for (;;)
/*      */                                 {
/* 4111 */                                   int tmp11956_11955 = 0; int[] tmp11956_11953 = _jspx_push_body_count_c_005fforEach_005f1; int tmp11958_11957 = tmp11956_11953[tmp11956_11955];tmp11956_11953[tmp11956_11955] = (tmp11958_11957 - 1); if (tmp11958_11957 <= 0) break;
/* 4112 */                                   out = _jspx_page_context.popBody(); }
/* 4113 */                                 _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                               } finally {
/* 4115 */                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4116 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                               }
/* 4118 */                               out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4119 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 4120 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4124 */                           if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 4125 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                           }
/*      */                           
/* 4128 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 4129 */                           out.write(10);
/* 4130 */                           out.write(32);
/* 4131 */                           if (_jspx_meth_c_005fif_005f20(_jspx_page_context))
/*      */                             return;
/* 4133 */                           out.write(32);
/* 4134 */                           out.write(10);
/*      */                         }
/*      */                         
/*      */                       }
/* 4138 */                       else if (mon)
/*      */                       {
/*      */ 
/* 4141 */                         out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 4145 */                       out.write(9);
/* 4146 */                       out.write(9);
/* 4147 */                       out.write("\n\n\n\n");
/*      */                     }
/*      */                     
/*      */ 
/* 4151 */                     out.write(10);
/*      */                   }
/* 4153 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4154 */         out = _jspx_out;
/* 4155 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4156 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4157 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4160 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4166 */     PageContext pageContext = _jspx_page_context;
/* 4167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4169 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4170 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4171 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 4173 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4174 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4175 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4176 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4177 */       return true;
/*      */     }
/* 4179 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4185 */     PageContext pageContext = _jspx_page_context;
/* 4186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4188 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4189 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4190 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 4192 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4193 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4194 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4195 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4196 */       return true;
/*      */     }
/* 4198 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4204 */     PageContext pageContext = _jspx_page_context;
/* 4205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4207 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4208 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4209 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 4211 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4212 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4213 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4215 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 4216 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4221 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4222 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4223 */       return true;
/*      */     }
/* 4225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4231 */     PageContext pageContext = _jspx_page_context;
/* 4232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4234 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4235 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4236 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 4238 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4239 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4240 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4241 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4242 */       return true;
/*      */     }
/* 4244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4250 */     PageContext pageContext = _jspx_page_context;
/* 4251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4253 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4254 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4255 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 4257 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4258 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4259 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4261 */       return true;
/*      */     }
/* 4263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4269 */     PageContext pageContext = _jspx_page_context;
/* 4270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4272 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4273 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 4274 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 4276 */     _jspx_th_c_005fif_005f0.setTest("${param.reconfigure=='true' || param.addProcess=='true'}");
/* 4277 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 4278 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 4280 */         out.write(10);
/* 4281 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 4282 */           return true;
/* 4283 */         out.write(10);
/* 4284 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 4285 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4289 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 4290 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4291 */       return true;
/*      */     }
/* 4293 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 4294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4299 */     PageContext pageContext = _jspx_page_context;
/* 4300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4302 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4303 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4304 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4306 */     _jspx_th_c_005fset_005f0.setVar("baseurl");
/* 4307 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4308 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4309 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4310 */         out = _jspx_page_context.pushBody();
/* 4311 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4312 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4315 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4316 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 4317 */           return true;
/* 4318 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4319 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4322 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4323 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4326 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4327 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4328 */       return true;
/*      */     }
/* 4330 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4336 */     PageContext pageContext = _jspx_page_context;
/* 4337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4339 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4340 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4341 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4343 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 4344 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4345 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4346 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4347 */       return true;
/*      */     }
/* 4349 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4355 */     PageContext pageContext = _jspx_page_context;
/* 4356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4358 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4359 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4360 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 4362 */     _jspx_th_c_005fout_005f5.setValue("${selectedskin}");
/*      */     
/* 4364 */     _jspx_th_c_005fout_005f5.setDefault("${initParam.defaultSkin}");
/* 4365 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4366 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4367 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4368 */       return true;
/*      */     }
/* 4370 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4376 */     PageContext pageContext = _jspx_page_context;
/* 4377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4379 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4380 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4381 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 4383 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4384 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4385 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4386 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4387 */       return true;
/*      */     }
/* 4389 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4395 */     PageContext pageContext = _jspx_page_context;
/* 4396 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4398 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4399 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4400 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4402 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 4403 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4404 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4405 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4406 */       return true;
/*      */     }
/* 4408 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_logic_005fnotPresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4414 */     PageContext pageContext = _jspx_page_context;
/* 4415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4417 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4418 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4419 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_logic_005fnotPresent_005f1);
/*      */     
/* 4421 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4422 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4423 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4424 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4425 */       return true;
/*      */     }
/* 4427 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4428 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4433 */     PageContext pageContext = _jspx_page_context;
/* 4434 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4436 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4437 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4438 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 4440 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 4441 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4442 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4443 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4444 */       return true;
/*      */     }
/* 4446 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fnotPresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4452 */     PageContext pageContext = _jspx_page_context;
/* 4453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4455 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4456 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4457 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fnotPresent_005f3);
/*      */     
/* 4459 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 4460 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4461 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4463 */       return true;
/*      */     }
/* 4465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4466 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4471 */     PageContext pageContext = _jspx_page_context;
/* 4472 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4474 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4475 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4476 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4478 */     _jspx_th_c_005fout_005f11.setValue("${ha.key}");
/* 4479 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4480 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4481 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4482 */       return true;
/*      */     }
/* 4484 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4485 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4490 */     PageContext pageContext = _jspx_page_context;
/* 4491 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4493 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4494 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4495 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4497 */     _jspx_th_c_005fout_005f12.setValue("${ha.value}");
/* 4498 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4499 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4500 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4501 */       return true;
/*      */     }
/* 4503 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4509 */     PageContext pageContext = _jspx_page_context;
/* 4510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4512 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4513 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4514 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4516 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 4517 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4518 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4519 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4520 */         out = _jspx_page_context.pushBody();
/* 4521 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4522 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4523 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4526 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4527 */           return true;
/* 4528 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4529 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4532 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4533 */         out = _jspx_page_context.popBody();
/* 4534 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4537 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4538 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4539 */       return true;
/*      */     }
/* 4541 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4547 */     PageContext pageContext = _jspx_page_context;
/* 4548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4550 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4551 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4552 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4554 */     _jspx_th_c_005fout_005f13.setValue("${ha.value}");
/* 4555 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4556 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4558 */       return true;
/*      */     }
/* 4560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fpresent_005f13, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4566 */     PageContext pageContext = _jspx_page_context;
/* 4567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4569 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4570 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4571 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fpresent_005f13);
/*      */     
/* 4573 */     _jspx_th_c_005fout_005f14.setValue("${ha.key}");
/* 4574 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4575 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4576 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4577 */       return true;
/*      */     }
/* 4579 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4585 */     PageContext pageContext = _jspx_page_context;
/* 4586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4588 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4589 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4590 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4592 */     _jspx_th_c_005fout_005f15.setValue("${ha.key}");
/* 4593 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4594 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4595 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4596 */       return true;
/*      */     }
/* 4598 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4604 */     PageContext pageContext = _jspx_page_context;
/* 4605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4607 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4608 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4609 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4611 */     _jspx_th_c_005fout_005f16.setValue("${ha.value}");
/* 4612 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4613 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4614 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4615 */       return true;
/*      */     }
/* 4617 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4623 */     PageContext pageContext = _jspx_page_context;
/* 4624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4626 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4627 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4628 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4630 */     _jspx_th_c_005fset_005f2.setVar("monitorName");
/* 4631 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4632 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4633 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4634 */         out = _jspx_page_context.pushBody();
/* 4635 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4636 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4637 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4640 */         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4641 */           return true;
/* 4642 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4646 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4647 */         out = _jspx_page_context.popBody();
/* 4648 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4651 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4652 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4653 */       return true;
/*      */     }
/* 4655 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4661 */     PageContext pageContext = _jspx_page_context;
/* 4662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4664 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4665 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4666 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 4668 */     _jspx_th_c_005fout_005f17.setValue("${ha.value}");
/* 4669 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4670 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4671 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4672 */       return true;
/*      */     }
/* 4674 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4680 */     PageContext pageContext = _jspx_page_context;
/* 4681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4683 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4684 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4685 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 4687 */     _jspx_th_c_005fout_005f18.setValue("${ha.key}");
/* 4688 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4689 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4691 */       return true;
/*      */     }
/* 4693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4699 */     PageContext pageContext = _jspx_page_context;
/* 4700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4702 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4703 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4704 */     _jspx_th_c_005fif_005f20.setParent(null);
/*      */     
/* 4706 */     _jspx_th_c_005fif_005f20.setTest("${empty associatedmgs}");
/* 4707 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4708 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 4710 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"><fmt:message key=\"am.webclient.urlmonitor.none.text\"/></td>\n\t ");
/* 4711 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4712 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4716 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4717 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4718 */       return true;
/*      */     }
/* 4720 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4721 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */