/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class SAPDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  673 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  845 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  931 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/* 1386 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1431 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1479 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/*      */           catch (java.sql.SQLException e) {
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
/* 1829 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1831 */               if (maxCol != null)
/* 1832 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1834 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1829 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/* 1990 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/*      */ 
/*      */   private String getStyleClass(int current, int tabno)
/*      */   {
/* 2186 */     if (current == tabno) {
/* 2187 */       return "yellowgrayborder";
/*      */     }
/*      */     
/* 2190 */     return "lrtbdarkborder";
/*      */   }
/*      */   
/*      */ 
/* 2194 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2200 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2201 */   static { _jspx_dependants.put("/jsp/includes/SAPRightArea.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2231 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2255 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2259 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2260 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2263 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2264 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2265 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2270 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2276 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2277 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2284 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2287 */     JspWriter out = null;
/* 2288 */     Object page = this;
/* 2289 */     JspWriter _jspx_out = null;
/* 2290 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2294 */       response.setContentType("text/html;charset=UTF-8");
/* 2295 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2297 */       _jspx_page_context = pageContext;
/* 2298 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2299 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2300 */       session = pageContext.getSession();
/* 2301 */       out = pageContext.getOut();
/* 2302 */       _jspx_out = out;
/*      */       
/* 2304 */       out.write("<!DOCTYPE html>\n");
/* 2305 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n");
/*      */       
/* 2307 */       request.setAttribute("HelpKey", "Monitors SAP Server Details");
/*      */       
/* 2309 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2311 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2312 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2313 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2315 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2316 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2317 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2319 */           out.write(10);
/* 2320 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2322 */           out.write(10);
/* 2323 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2325 */           out.write(10);
/* 2326 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2328 */           out.write(10);
/* 2329 */           out.write(10);
/*      */           
/* 2331 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2332 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2333 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2335 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2337 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2338 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2339 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2340 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2341 */               out = _jspx_page_context.pushBody();
/* 2342 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2343 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2346 */               out.write(10);
/* 2347 */               out.write(10);
/* 2348 */               com.adventnet.appmanager.client.sap.SAPGraph sapGraph = null;
/* 2349 */               sapGraph = (com.adventnet.appmanager.client.sap.SAPGraph)_jspx_page_context.getAttribute("sapGraph", 2);
/* 2350 */               if (sapGraph == null) {
/* 2351 */                 sapGraph = new com.adventnet.appmanager.client.sap.SAPGraph();
/* 2352 */                 _jspx_page_context.setAttribute("sapGraph", sapGraph, 2);
/*      */               }
/* 2354 */               out.write(10);
/* 2355 */               com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2356 */               wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2357 */               if (wlsGraph == null) {
/* 2358 */                 wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2359 */                 _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */               }
/* 2361 */               out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/sap.js\"></SCRIPT>\n\n<script>\nfunction submitForm(editform)\n{\n\t");
/* 2362 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2364 */               out.write(10);
/* 2365 */               out.write(9);
/*      */               
/* 2367 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2368 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2369 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2371 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2372 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2373 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2375 */                   out.write("\n        var dispname = trimAll(editform.displayname.value);\n\tif(dispname=='')\n\t{\n\t\talert('");
/* 2376 */                   out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2377 */                   out.write("');\n\t\treturn;\n\t}\n\tpoll=trimAll(editform.pollInterval.value);\n\tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0' )\n\t{\n\t\talert('");
/* 2378 */                   out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 2379 */                   out.write("');\n\t\treturn;\n\t}\n        var poll=trimAll(editform.username.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2380 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.username"));
/* 2381 */                   out.write("');\n\t\treturn;\n\t}\n        poll=trimAll(editform.password.value);\n\tif(poll == '')\n\t{\n\t\talert('");
/* 2382 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/* 2383 */                   out.write("');\n\t\treturn;\n\t}\n\t\n\teditform.submit();\n\t");
/* 2384 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2385 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2389 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2390 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2393 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2394 */               out.write("\n}\n function invokePage(resourceid, url, prop1, prop2)\n    {\n        if(resourceid == 0)\n        {\n            alert('");
/* 2395 */               out.print(FormatUtil.getString("am.webclient.configurealert.selectmonitor.alertMgs"));
/* 2396 */               out.write("');\t//NO I18N\n            return false;\n        }\n        MM_openBrWindow(url, prop1, prop2);\n     }\n $(document).ready(function(){\n\t\tvar list=document.getElementsByName('usedRouterString')\t\t//NO I18N\n\t\tif(list[0].checked){\n\t\t\tjavascript:showRow(\"routerString\");\t//NO I18N\n\t\t}\n\t})\n\n\n\n</script>\n");
/*      */               
/* 2398 */               String resourceid = request.getParameter("resourceid");
/* 2399 */               String datatypestr = request.getParameter("datatype");
/* 2400 */               int datatype = 1;
/* 2401 */               if (datatypestr != null) {
/* 2402 */                 datatype = Integer.parseInt(datatypestr);
/*      */               }
/*      */               
/* 2405 */               String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2406 */               String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2407 */               String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2408 */               String yaxis_utilization = FormatUtil.getString("UTILIZATION");
/* 2409 */               String yaxis_enqueuerequests = FormatUtil.getString("ENQUEUEREQUESTS");
/* 2410 */               String yaxis_frontendresponsetime = FormatUtil.getString("FRONTENDRESPONSETIME");
/* 2411 */               String yaxis_servicequeue = FormatUtil.getString("SERVICEQUEUE");
/* 2412 */               String yaxis_queuelength = FormatUtil.getString("QUEUELENGTH");
/* 2413 */               String yaxis_servicequeuepages = FormatUtil.getString("SERVICEQUEUEPAGES");
/* 2414 */               String yaxis_loadplusgentime = FormatUtil.getString("LOADPLUSGENTIME");
/* 2415 */               String yaxis_dbrequesttime = FormatUtil.getString("DBREQUESTTIME");
/* 2416 */               String yaxis_queuetime = FormatUtil.getString("QUEUETIME");
/* 2417 */               String yaxis_esact = FormatUtil.getString("ESACT");
/* 2418 */               String yaxis_pagingrate = FormatUtil.getString("PAGINGRATE");
/*      */               
/*      */ 
/* 2421 */               out.write(10);
/* 2422 */               out.write(10);
/* 2423 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2425 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2426 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2427 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2429 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2431 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2433 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2435 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2436 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2437 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2438 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2441 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2442 */               String available = null;
/* 2443 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2444 */               out.write(10);
/*      */               
/* 2446 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2447 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2448 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2450 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2452 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2454 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2456 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2457 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2458 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2459 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2462 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2463 */               String unavailable = null;
/* 2464 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2465 */               out.write(10);
/*      */               
/* 2467 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2468 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2469 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2471 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2473 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2475 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2477 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2478 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2479 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2480 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2483 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2484 */               String unmanaged = null;
/* 2485 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2486 */               out.write(10);
/*      */               
/* 2488 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2489 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2490 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2492 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2494 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2496 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2498 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2499 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2500 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2501 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2504 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2505 */               String scheduled = null;
/* 2506 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2507 */               out.write(10);
/*      */               
/* 2509 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2510 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2511 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2513 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2515 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2517 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2519 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2520 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2521 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2522 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2525 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2526 */               String critical = null;
/* 2527 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2528 */               out.write(10);
/*      */               
/* 2530 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2531 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2532 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2534 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2536 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2538 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2540 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2541 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2542 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2543 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2546 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2547 */               String clear = null;
/* 2548 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2549 */               out.write(10);
/*      */               
/* 2551 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2552 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2553 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2555 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2557 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2559 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2561 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2562 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2563 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2564 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2567 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2568 */               String warning = null;
/* 2569 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2570 */               out.write(10);
/* 2571 */               out.write(10);
/*      */               
/* 2573 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2574 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2576 */               out.write(10);
/* 2577 */               out.write(10);
/* 2578 */               out.write(10);
/* 2579 */               out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>        \n\t");
/*      */               
/* 2581 */               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/*      */               
/* 2583 */               String haid = request.getParameter("haid");
/* 2584 */               String haName = null;
/* 2585 */               if (haid != null)
/*      */               {
/* 2587 */                 haName = (String)ht.get(haid);
/*      */               }
/*      */               
/* 2590 */               out.write(10);
/* 2591 */               out.write(9);
/*      */               
/* 2593 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2594 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2595 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2597 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && !param.haid=='null' && (empty invalidhaid)}");
/* 2598 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2599 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2601 */                   out.write("\n      \t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2602 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 2603 */                   out.write(" &gt; ");
/* 2604 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2605 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2606 */                   out.print(request.getParameter("resourcename"));
/* 2607 */                   out.write(" </span></td>\n\t");
/* 2608 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2609 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2613 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2614 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2617 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2618 */               out.write(10);
/* 2619 */               out.write(9);
/*      */               
/* 2621 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2622 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2623 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2625 */               _jspx_th_c_005fif_005f3.setTest("${(empty param.haid || param.haid=='null'|| (!empty invalidhaid))}");
/* 2626 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2627 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2629 */                   out.write("\t\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2630 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 2631 */                   out.write(" &gt; ");
/* 2632 */                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes("SAP"));
/* 2633 */                   out.write(" &gt; <span class=\"bcactive\">");
/* 2634 */                   out.print(request.getParameter("resourcename"));
/* 2635 */                   out.write(" </span></td>\n\t");
/* 2636 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2637 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2641 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2642 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2645 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2646 */               out.write("\n\t\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n\n\n");
/*      */               
/* 2648 */               if ("true".equals(request.getParameter("editPage")))
/*      */               {
/* 2650 */                 out.write("\n<div id=\"Reconfigure\" style=\"display:block\">\n");
/*      */               } else {
/* 2652 */                 out.write("\n<div id=\"Reconfigure\" style=\"display:none\">\n");
/*      */               }
/* 2654 */               out.write("\n\n<br>\n");
/*      */               
/* 2656 */               String type = request.getParameter("type");
/* 2657 */               String pollInterval = request.getParameter("pollInterval");
/* 2658 */               if (pollInterval == null) {
/* 2659 */                 pollInterval = "5";
/*      */               }
/* 2661 */               String displayname = request.getParameter("resourcename");
/*      */               
/*      */ 
/* 2664 */               out.write(10);
/* 2665 */               out.write(10);
/*      */               
/* 2667 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2668 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2669 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2671 */               _jspx_th_html_005fform_005f0.setAction("/sap");
/*      */               
/* 2673 */               _jspx_th_html_005fform_005f0.setMethod("get");
/*      */               
/* 2675 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2676 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2677 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 2679 */                   out.write("\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2680 */                   out.print(request.getParameter("name"));
/* 2681 */                   out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2682 */                   out.print(type);
/* 2683 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"editMonitor\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2684 */                   out.print(request.getParameter("resourceid"));
/* 2685 */                   out.write("\">\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 2686 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2687 */                   out.write("\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:reconfigure()\" >\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">");
/* 2688 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2689 */                   out.write("</span>\n</td>\n</tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2690 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.displayname"));
/* 2691 */                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 2692 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2694 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\"  onmouseover=\"ddrivetip(this,event,'");
/* 2695 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2696 */                   out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2697 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.connect.withrouterstring"));
/* 2698 */                   out.write("</a></label></TD>\n\t<TD height=\"28\" colspan=\"2\" >  ");
/* 2699 */                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2701 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR id=\"routerString\" style=\"display:none\">\n\t<TD height=\"28\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\"  onmouseover=\"ddrivetip(this,event,'");
/* 2702 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2703 */                   out.write("',false,true,'#000000',200,'lightyellow')\" onmouseout=\"hideddrivetip()\">");
/* 2704 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.sap.routerstring"));
/* 2705 */                   out.write("</a><span class=\"mandatory\">*</span></label></TD>\n\t<TD height=\"28\" colspan=\"2\" > ");
/* 2706 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2708 */                   out.write("\n\t</TD>\n\t</TR>\n        <TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2709 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2710 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 2711 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2713 */                   out.write("\n\t</TD>\n\t</TR>\n        <TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2714 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2715 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 2716 */                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2718 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">");
/* 2719 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 2720 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 2721 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2723 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 2724 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval.units"));
/* 2725 */                   out.write("</span>\n\t</TD>\n\t</TR>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"40%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"60%\" class=\"tablebottom\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons\" value=\"");
/*      */                   
/* 2727 */                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2728 */                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2729 */                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2731 */                   _jspx_th_c_005fif_005f4.setTest("${empty enabled}");
/* 2732 */                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2733 */                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                     for (;;) {
/* 2735 */                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 2736 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2737 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2741 */                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2742 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                   }
/*      */                   
/* 2745 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2746 */                   if (_jspx_meth_c_005fif_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2748 */                   out.write("\" onClick=\"javascript:submitForm(this.form)\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 2749 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2750 */                   out.write("\" onClick=\"javascript:reconfigure()\" />\n     </td>\n  </tr>\n</table>\n");
/* 2751 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2752 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2756 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2757 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2760 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2761 */               out.write("\n<br>\n<br>\n</div>\n\n<br>\n\n");
/*      */               
/* 2763 */               String selected = "";
/* 2764 */               if (datatype == 1) {
/* 2765 */                 selected = "Overview";
/*      */               }
/* 2767 */               else if (datatype == 2) {
/* 2768 */                 selected = "Background";
/*      */               }
/* 2770 */               else if (datatype == 3) {
/* 2771 */                 selected = "Buffer";
/*      */               }
/* 2773 */               else if (datatype == 4) {
/* 2774 */                 selected = "Dialog";
/*      */               }
/* 2776 */               else if (datatype == 5) {
/* 2777 */                 selected = "Enqueue";
/*      */               }
/* 2779 */               else if (datatype == 6) {
/* 2780 */                 selected = "OS";
/*      */               }
/* 2782 */               else if (datatype == 7) {
/* 2783 */                 selected = "Spool";
/*      */               }
/* 2785 */               else if (datatype == 8) {
/* 2786 */                 selected = "Alerts";
/*      */               }
/* 2788 */               else if (datatype == 9) {
/* 2789 */                 selected = "Backgroundjob";
/*      */               }
/* 2791 */               else if (datatype == 10) {
/* 2792 */                 selected = "Rfc";
/*      */               }
/*      */               
/* 2795 */               String titles = "Overview,Background,Buffer,Dialog,Enqueue,OS,Spool,Alerts,Background Jobs";
/* 2796 */               if (com.adventnet.appmanager.util.DBUtil.getGlobalConfigValueasBoolean("am.sap.rfc.monitoring.enabled")) {
/* 2797 */                 titles = titles + ",RFC";
/*      */               }
/*      */               
/* 2800 */               out.write(10);
/* 2801 */               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("OverviewTip,BackgroundTip,BufferTip,DialogTip,EnqueueTip,OSTip,SpoolTip,AlertsTip,BackgroundJobTip,RFCTip", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("getOverviewData,getBackgroundData,getBufferData,getDialogData,getEnqueueData,getOSData,getSpoolData,getAlertData,getBackgroundjobData,getRFCData", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selected), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/* 2802 */               out.write("\n\n<table border=\"0\" width=\"100%\">\n<tr>\n<td>\n\n<div id=\"data\">\n");
/*      */               
/* 2804 */               if (datatype == 1)
/*      */               {
/* 2806 */                 out.write(10);
/* 2807 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/overview.jsp" + ("/jsp/sap/overview.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2808 */                 out.write(10);
/*      */ 
/*      */               }
/* 2811 */               else if (datatype == 2)
/*      */               {
/* 2813 */                 out.write(10);
/* 2814 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/background.jsp" + ("/jsp/sap/background.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2815 */                 out.write(10);
/*      */ 
/*      */               }
/* 2818 */               else if (datatype == 3)
/*      */               {
/* 2820 */                 out.write(10);
/* 2821 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/buffer.jsp" + ("/jsp/sap/buffer.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2822 */                 out.write(10);
/*      */ 
/*      */               }
/* 2825 */               else if (datatype == 4)
/*      */               {
/* 2827 */                 out.write(10);
/* 2828 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/dialog.jsp" + ("/jsp/sap/dialog.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2829 */                 out.write(10);
/*      */ 
/*      */               }
/* 2832 */               else if (datatype == 5)
/*      */               {
/* 2834 */                 out.write(10);
/* 2835 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/enqueue.jsp" + ("/jsp/sap/enqueue.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2836 */                 out.write(10);
/*      */ 
/*      */               }
/* 2839 */               else if (datatype == 6)
/*      */               {
/* 2841 */                 out.write(10);
/* 2842 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/os.jsp" + ("/jsp/sap/os.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2843 */                 out.write(10);
/*      */ 
/*      */               }
/* 2846 */               else if (datatype == 7)
/*      */               {
/* 2848 */                 out.write(10);
/* 2849 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/spool.jsp" + ("/jsp/sap/spool.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2850 */                 out.write(10);
/*      */ 
/*      */               }
/* 2853 */               else if (datatype == 8)
/*      */               {
/* 2855 */                 out.write(10);
/* 2856 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/alerts.jsp" + ("/jsp/sap/alerts.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2857 */                 out.write(10);
/*      */ 
/*      */               }
/* 2860 */               else if (datatype == 9)
/*      */               {
/* 2862 */                 out.write(10);
/* 2863 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/backgroundjob.jsp" + ("/jsp/sap/backgroundjob.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2864 */                 out.write(10);
/*      */ 
/*      */               }
/* 2867 */               else if (datatype == 10)
/*      */               {
/* 2869 */                 out.write(10);
/* 2870 */                 JspRuntimeLibrary.include(request, response, "/jsp/sap/RFC.jsp" + ("/jsp/sap/RFC.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noredirect", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("false", request.getCharacterEncoding()), out, true);
/* 2871 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 2875 */               out.write("\n</div>\n\n</td>\n</tr>\n</table>\n");
/* 2876 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2877 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2880 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2881 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2884 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2885 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 2888 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2889 */           out.write(10);
/* 2890 */           out.write(10);
/* 2891 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2893 */           out.write(10);
/*      */           
/* 2895 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2896 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 2897 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2899 */           _jspx_th_tiles_005fput_005f5.setName("ServerLeftArea");
/*      */           
/* 2901 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 2902 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 2903 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 2904 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 2905 */               out = _jspx_page_context.pushBody();
/* 2906 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 2907 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2910 */               out.write(10);
/* 2911 */               out.write("<!--$Id$-->\n\n<script>\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */               
/* 2913 */               String resourceid = request.getParameter("resourceid");
/* 2914 */               String configure_link = (String)request.getAttribute("configurelink");
/* 2915 */               String type = "SAP";
/* 2916 */               String Availability_attribIDs = "3702";
/* 2917 */               String Health_attribIDs = "3703";
/* 2918 */               if ((request.getAttribute("type") != null) && (((String)request.getAttribute("type")).equalsIgnoreCase("SAP-CCMS")))
/*      */               {
/* 2920 */                 type = "SAP-CCMS";
/* 2921 */                 Availability_attribIDs = "4000";
/* 2922 */                 Health_attribIDs = "4001";
/*      */               }
/*      */               
/* 2925 */               out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n        var s = confirm(\"");
/* 2926 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2927 */               out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 2928 */               out.print(type);
/* 2929 */               out.write("&select=");
/* 2930 */               out.print(resourceid);
/* 2931 */               out.write("\";\n\t }\n\t        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2932 */               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2933 */               out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2934 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2936 */               out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2937 */               out.print(request.getParameter("resourceid"));
/* 2938 */               out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 2939 */               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2940 */               out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2941 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2943 */               out.write("\";\n             }\n           else { \n        \n        var s = confirm(\"");
/* 2944 */               out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2945 */               out.write("\");\n        if (s){\n      \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2946 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 2948 */               out.write("\"; //No I18N\n\t\t\t  }\n\t       } \n\t }\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 2949 */               out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2950 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 2952 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2953 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2954 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/* 2955 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2956 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2958 */                   out.write(10);
/* 2959 */                   out.write(32);
/* 2960 */                   out.write(32);
/*      */                   
/* 2962 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2963 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2964 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2966 */                   _jspx_th_c_005fwhen_005f0.setTest("${(param.method == 'showdetails' && param.all!='true') || param.method=='showCCMSDetails'}");
/* 2967 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2968 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2970 */                       out.write("\n         ");
/* 2971 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2972 */                       out.write(10);
/* 2973 */                       out.write(32);
/* 2974 */                       out.write(32);
/* 2975 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2976 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2980 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2981 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 2984 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2985 */                   out.write(10);
/* 2986 */                   out.write(32);
/* 2987 */                   out.write(32);
/*      */                   
/* 2989 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2990 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2991 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2992 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2993 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2995 */                       out.write("\n\t<a href=\"/showresource.do?resourceid=");
/* 2996 */                       if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                         return;
/* 2998 */                       out.write("&method=showResourceForResourceID");
/* 2999 */                       if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                         return;
/* 3001 */                       out.write("\"\n      class=\"new-left-links\">");
/* 3002 */                       out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3003 */                       out.write("</a>\n  ");
/* 3004 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3005 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3009 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3010 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 3013 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3014 */                   out.write(10);
/* 3015 */                   out.write(32);
/* 3016 */                   out.write(32);
/* 3017 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3018 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3022 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3023 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 3026 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3027 */               out.write("\n  </td>\n  </tr>\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */               
/* 3029 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3030 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3031 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3033 */               _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 3034 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3035 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 3037 */                   out.write("\n   <tr>\n          <td width=\"88%\" class=\"leftlinkstd\">\n       \t");
/*      */                   
/* 3039 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3040 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3041 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f6);
/* 3042 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3043 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 3045 */                       out.write("\n         ");
/*      */                       
/* 3047 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3048 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3049 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 3051 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.all=='true'}");
/* 3052 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3053 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 3055 */                           out.write("\n                ");
/* 3056 */                           out.print(ALERTCONFIG_TEXT);
/* 3057 */                           out.write("\n         ");
/* 3058 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3059 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3063 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3064 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 3067 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3068 */                       out.write("\n  \t");
/*      */                       
/* 3070 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3071 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3072 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 3073 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3074 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 3076 */                           out.write("\n          <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 3077 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                             return;
/* 3079 */                           out.write("\"\n            class=\"new-left-links\">");
/* 3080 */                           out.print(ALERTCONFIG_TEXT);
/* 3081 */                           out.write("</a>\n              ");
/* 3082 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3083 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3087 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3088 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 3091 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3092 */                       out.write("\n\t      ");
/* 3093 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3094 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3098 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3099 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 3102 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3103 */                   out.write("\n            </td>\n        </tr>\n  ");
/* 3104 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3105 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3109 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3110 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 3113 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3114 */               out.write("\n <!-- Edit link should be enabled for Enterprise Edition when SSO is enabled -->\n  ");
/*      */               
/* 3116 */               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3117 */               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3118 */               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3120 */               _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 3121 */               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3122 */               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                 for (;;) {
/* 3124 */                   out.write("\n\t<tr>\n\t\t<td class=\"leftlinkstd\">\n\t\t<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3125 */                   out.print(request.getParameter("resourceid"));
/* 3126 */                   out.write("&method=showdetails&type=");
/* 3127 */                   out.print(request.getParameter("type"));
/* 3128 */                   out.write("&moname=");
/* 3129 */                   out.print(request.getParameter("moname"));
/* 3130 */                   out.write("&viewType=showResourceTypes&aam_jump=true&editPage=true\" class=\"new-left-links\">");
/* 3131 */                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3132 */                   out.write("</a>\n\t\t</td>\n\t</tr>\n  ");
/* 3133 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3134 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3138 */               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3139 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */               }
/*      */               
/* 3142 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3143 */               out.write("\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 3145 */               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3146 */               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3147 */               _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3149 */               _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO}");
/* 3150 */               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3151 */               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                 for (;;) {
/* 3153 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                   
/* 3155 */                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3156 */                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3157 */                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f7);
/* 3158 */                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3159 */                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                     for (;;) {
/* 3161 */                       out.write("\n    ");
/*      */                       
/* 3163 */                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3164 */                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3165 */                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                       
/* 3167 */                       _jspx_th_c_005fwhen_005f2.setTest("${uri=='/reconfigure.do'}");
/* 3168 */                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3169 */                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                         for (;;) {
/* 3171 */                           out.write("\n        ");
/* 3172 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3173 */                           out.write(10);
/* 3174 */                           out.write(32);
/* 3175 */                           out.write(32);
/* 3176 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3177 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3181 */                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3182 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                       }
/*      */                       
/* 3185 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3186 */                       out.write(10);
/* 3187 */                       out.write(32);
/* 3188 */                       out.write(32);
/*      */                       
/* 3190 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3191 */                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3192 */                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 3193 */                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3194 */                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                         for (;;) {
/* 3196 */                           out.write("\n    <a href=\"javascript:reconfigure()\"\n      class=\"new-left-links\">");
/* 3197 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3198 */                           out.write("</a>\n  ");
/* 3199 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3200 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3204 */                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3205 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                       }
/*      */                       
/* 3208 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3209 */                       out.write(10);
/* 3210 */                       out.write(32);
/* 3211 */                       out.write(32);
/* 3212 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3213 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3217 */                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3218 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                   }
/*      */                   
/* 3221 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3222 */                   out.write("\n  </td>\n  </tr>\n");
/* 3223 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3224 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3228 */               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3229 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */               }
/*      */               
/* 3232 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3233 */               out.write(10);
/* 3234 */               out.write(10);
/*      */               
/* 3236 */               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3237 */               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3238 */               _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3240 */               _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO}");
/* 3241 */               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3242 */               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                 for (;;) {
/* 3244 */                   out.write(10);
/*      */                   
/* 3246 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3247 */                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3248 */                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                   
/* 3250 */                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3251 */                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3252 */                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 3254 */                       out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3255 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3256 */                       out.write("</A></td>\n  \t</td>\n  </tr>\n");
/* 3257 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3258 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3262 */                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3263 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                   }
/*      */                   
/* 3266 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3267 */                   out.write(10);
/*      */                   
/* 3269 */                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3270 */                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3271 */                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f8);
/*      */                   
/* 3273 */                   _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3274 */                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3275 */                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                     for (;;) {
/* 3277 */                       out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3278 */                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3279 */                       out.write("</a></td>\n\n");
/* 3280 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3281 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3285 */                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3286 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                   }
/*      */                   
/* 3289 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3290 */                   out.write("\n\n  </tr>\n  ");
/* 3291 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3292 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3296 */               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3297 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */               }
/*      */               
/* 3300 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3301 */               out.write(10);
/*      */               
/* 3303 */               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3304 */               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3305 */               _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3307 */               _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3308 */               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3309 */               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                 for (;;) {
/* 3311 */                   out.write("\n  <tr>\n  ");
/*      */                   
/* 3313 */                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                   {
/*      */ 
/* 3316 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3317 */                     out.print(FormatUtil.getString("Manage"));
/* 3318 */                     out.write("</A></td>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 3324 */                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3325 */                     out.print(FormatUtil.getString("UnManage"));
/* 3326 */                     out.write("</A></td>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 3330 */                   out.write("\n  </tr>\n  ");
/* 3331 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3332 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3336 */               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3337 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */               }
/*      */               
/* 3340 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3341 */               out.write("\n\n\n\n");
/*      */               
/* 3343 */               if ((configure_link != null) && (!configure_link.equals("null")))
/*      */               {
/*      */ 
/* 3346 */                 out.write("\n\n   ");
/*      */                 
/* 3348 */                 IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3349 */                 _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3350 */                 _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                 
/* 3352 */                 _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN || !empty DEMO}");
/* 3353 */                 int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3354 */                 if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                   for (;;) {
/* 3356 */                     out.write("\n    <tr>\n    <td class=\"leftlinkstd\" >\n\n    \t<a href=\"");
/* 3357 */                     out.print(request.getAttribute("configurelink"));
/* 3358 */                     out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 3359 */                     out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 3360 */                     out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 3361 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3362 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3366 */                 if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3367 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                 }
/*      */                 
/* 3370 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3371 */                 out.write("\n   ");
/*      */               }
/*      */               
/*      */ 
/* 3375 */               out.write("\n\n  ");
/*      */               
/* 3377 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3378 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3379 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3381 */               _jspx_th_c_005fif_005f11.setTest("${!empty ADMIN  && type!='SAP-CCMS'}");
/* 3382 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3383 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 3385 */                   out.write("\n  \t   \t<tr>\n  \t       \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t       \t ");
/* 3386 */                   out.print(FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "SAP", "SAP Server"));
/* 3387 */                   out.write("\n  \t       \t </td>\n  \t      \t</tr>\n  ");
/* 3388 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3389 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3393 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3394 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 3397 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3398 */               out.write("\n   ");
/*      */               
/* 3400 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3401 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3402 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3404 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3405 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3406 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 3408 */                   out.write("\n    ");
/*      */                   
/* 3410 */                   String resourceid_poll = request.getParameter("resourceid");
/* 3411 */                   String resourcetype_poll = request.getParameter("type");
/*      */                   
/* 3413 */                   out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3414 */                   out.print(resourceid_poll);
/* 3415 */                   out.write("&resourcetype=");
/* 3416 */                   out.print(resourcetype_poll);
/* 3417 */                   out.write("\" class=\"new-left-links\"> ");
/* 3418 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3419 */                   out.write("</a></td>\n    </tr>\n    ");
/* 3420 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3421 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3425 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3426 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 3429 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3430 */               out.write("\n    ");
/*      */               
/* 3432 */               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3433 */               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3434 */               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3436 */               _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3437 */               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3438 */               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                 for (;;) {
/* 3440 */                   out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3441 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3442 */                   out.write("</a></td>\n          </td>\n    ");
/* 3443 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3444 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3448 */               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3449 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */               }
/*      */               
/* 3452 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3453 */               out.write("\n    ");
/* 3454 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 3456 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 3458 */                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3459 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 3461 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3462 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3463 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 3465 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 3468 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3469 */                     out.print(ciInfoUrl);
/* 3470 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3471 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3472 */                     out.write("</a></td>");
/* 3473 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3474 */                     out.print(ciRLUrl);
/* 3475 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3476 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3477 */                     out.write("</a></td>");
/* 3478 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 3482 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 3485 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3486 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3487 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3488 */                     out.print(ciInfoUrl);
/* 3489 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3490 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3491 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3492 */                     out.print(ciRLUrl);
/* 3493 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3494 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3495 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 3500 */               out.write("\n \n \n\n");
/* 3501 */               out.write("\n  </table>\n");
/*      */               
/*      */ 
/* 3504 */               ArrayList attribIDs = new ArrayList();
/* 3505 */               ArrayList resIDs = new ArrayList();
/* 3506 */               resIDs.add(resourceid);
/* 3507 */               attribIDs.add(Availability_attribIDs);
/* 3508 */               attribIDs.add(Health_attribIDs);
/* 3509 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/* 3511 */               out.write("\n</td>\n  </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3512 */               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3513 */               out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3514 */               if (_jspx_meth_c_005fout_005f6(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3516 */               out.write("&attributeid=");
/* 3517 */               out.print(Health_attribIDs);
/* 3518 */               out.write("')\" class=\"new-left-links\">");
/* 3519 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3520 */               out.write("</a></td>\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3521 */               if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3523 */               out.write("&attributeid=");
/* 3524 */               out.print(Health_attribIDs);
/* 3525 */               out.write("')\">");
/* 3526 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + Health_attribIDs)));
/* 3527 */               out.write("</a></td>\n   </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3528 */               if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3530 */               out.write("&attributeid=");
/* 3531 */               out.print(Availability_attribIDs);
/* 3532 */               out.write("')\" class=\"new-left-links\">");
/* 3533 */               out.print(FormatUtil.getString("Availability"));
/* 3534 */               out.write("</a></td>\n    <td width=\"51%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3535 */               if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                 return;
/* 3537 */               out.write("&attributeid=");
/* 3538 */               out.print(Availability_attribIDs);
/* 3539 */               out.write("')\">\n    ");
/* 3540 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + Availability_attribIDs)));
/* 3541 */               out.write("</a>\n </td>\n  </tr>\n</table></td>\n  </tr>\n    </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n\n<br>\n");
/* 3542 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 3546 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3547 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 3549 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 3553 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3554 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3555 */                 String loginName = request.getUserPrincipal().getName();
/* 3556 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 3558 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 3560 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 3563 */               String monitorType = request.getParameter("type");
/* 3564 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3565 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 3566 */               if (showAssociatedBSG)
/*      */               {
/* 3568 */                 Hashtable associatedmgs = new Hashtable();
/* 3569 */                 String resId = request.getParameter("resourceid");
/* 3570 */                 request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3571 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 3573 */                   mon = false;
/*      */                 }
/*      */                 
/* 3576 */                 if (!mon)
/*      */                 {
/* 3578 */                   out.write(10);
/* 3579 */                   out.write(10);
/*      */                   
/* 3581 */                   IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3582 */                   _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3583 */                   _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3585 */                   _jspx_th_c_005fif_005f12.setTest("${!empty associatedmgs}");
/* 3586 */                   int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3587 */                   if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                     for (;;) {
/* 3589 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3590 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3591 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 3593 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3594 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3595 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f12);
/*      */                       
/* 3597 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 3599 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 3601 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3602 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 3604 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3605 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 3607 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3608 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3610 */                             out.write("&method=showApplication\" title=\"");
/* 3611 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3613 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 3614 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3616 */                             out.write("\n    \t");
/* 3617 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3618 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 3620 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3621 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3622 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 3624 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3625 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3626 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 3628 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3629 */                                 if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3631 */                                 out.write(39);
/* 3632 */                                 out.write(44);
/* 3633 */                                 out.write(39);
/* 3634 */                                 out.print(resId);
/* 3635 */                                 out.write(39);
/* 3636 */                                 out.write(44);
/* 3637 */                                 out.write(39);
/* 3638 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3639 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3640 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3641 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3642 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3643 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3647 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3648 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 3651 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3652 */                             out.write("</td>\n        </tr>\n\t");
/* 3653 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3654 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3658 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3666 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3662 */                           int tmp10233_10232 = 0; int[] tmp10233_10230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp10235_10234 = tmp10233_10230[tmp10233_10232];tmp10233_10230[tmp10233_10232] = (tmp10235_10234 - 1); if (tmp10235_10234 <= 0) break;
/* 3663 */                           out = _jspx_page_context.popBody(); }
/* 3664 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3666 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3667 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 3669 */                       out.write("\n      </table>\n ");
/* 3670 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3671 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3675 */                   if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3676 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                   }
/*      */                   
/* 3679 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3680 */                   out.write(10);
/* 3681 */                   out.write(32);
/*      */                   
/* 3683 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3684 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3685 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3687 */                   _jspx_th_c_005fif_005f13.setTest("${empty associatedmgs}");
/* 3688 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3689 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 3691 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3692 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3693 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3694 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3695 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3696 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3697 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3701 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3702 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 3705 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3706 */                   out.write(10);
/* 3707 */                   out.write(32);
/* 3708 */                   out.write(10);
/*      */ 
/*      */                 }
/* 3711 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 3715 */                   out.write(10);
/*      */                   
/* 3717 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3718 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3719 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f5);
/*      */                   
/* 3721 */                   _jspx_th_c_005fif_005f14.setTest("${!empty associatedmgs}");
/* 3722 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3723 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 3725 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3726 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                         return;
/* 3728 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 3730 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3731 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3732 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 3734 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 3736 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 3738 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3739 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 3741 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3742 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 3744 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3745 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3747 */                             out.write("&method=showApplication\" title=\"");
/* 3748 */                             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3750 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 3751 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3753 */                             out.write("\n    \t");
/* 3754 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3755 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 3757 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3758 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3759 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 3761 */                             _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3762 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3763 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 3765 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3766 */                                 if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3768 */                                 out.write(39);
/* 3769 */                                 out.write(44);
/* 3770 */                                 out.write(39);
/* 3771 */                                 out.print(resId);
/* 3772 */                                 out.write(39);
/* 3773 */                                 out.write(44);
/* 3774 */                                 out.write(39);
/* 3775 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3776 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3777 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3778 */                                 out.write("\"  title=\"");
/* 3779 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3781 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3782 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3783 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3787 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3788 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3791 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3792 */                             out.write("\n\n\t\t \t");
/* 3793 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3794 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3798 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3806 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3802 */                           int tmp11259_11258 = 0; int[] tmp11259_11256 = _jspx_push_body_count_c_005fforEach_005f1; int tmp11261_11260 = tmp11259_11256[tmp11259_11258];tmp11259_11256[tmp11259_11258] = (tmp11261_11260 - 1); if (tmp11261_11260 <= 0) break;
/* 3803 */                           out = _jspx_page_context.popBody(); }
/* 3804 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3806 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3807 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 3809 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3810 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3811 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3815 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3816 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 3819 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3820 */                   out.write(10);
/* 3821 */                   out.write(32);
/* 3822 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                     return;
/* 3824 */                   out.write(32);
/* 3825 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 3829 */               else if (mon)
/*      */               {
/*      */ 
/* 3832 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3833 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f5, _jspx_page_context))
/*      */                   return;
/* 3835 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3839 */               out.write(9);
/* 3840 */               out.write(9);
/* 3841 */               out.write("\n\n</table>\n\n\n");
/* 3842 */               out.write(10);
/* 3843 */               response.setContentType("text/html;charset=UTF-8");
/* 3844 */               out.write(10);
/* 3845 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 3846 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3849 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3850 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3853 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3854 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 3857 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 3858 */           out.write(10);
/* 3859 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3860 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3864 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3865 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3868 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3869 */         out.write(10);
/* 3870 */         out.write(10);
/*      */       }
/* 3872 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3873 */         out = _jspx_out;
/* 3874 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3875 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3876 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3879 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3885 */     PageContext pageContext = _jspx_page_context;
/* 3886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3888 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3889 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3890 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3892 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3894 */     _jspx_th_tiles_005fput_005f0.setValue("SAP - Snapshot");
/* 3895 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3896 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3897 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3898 */       return true;
/*      */     }
/* 3900 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3906 */     PageContext pageContext = _jspx_page_context;
/* 3907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3909 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3910 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3911 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3913 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3914 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3915 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3917 */         out.write(10);
/* 3918 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3919 */           return true;
/* 3920 */         out.write(10);
/* 3921 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3922 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3926 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3927 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3928 */       return true;
/*      */     }
/* 3930 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3936 */     PageContext pageContext = _jspx_page_context;
/* 3937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3939 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3940 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3941 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3943 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3945 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3946 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3947 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3948 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3949 */       return true;
/*      */     }
/* 3951 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3957 */     PageContext pageContext = _jspx_page_context;
/* 3958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3960 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3961 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3962 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3964 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3965 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3966 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3968 */         out.write(10);
/* 3969 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3970 */           return true;
/* 3971 */         out.write(10);
/* 3972 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3973 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3977 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3978 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3979 */       return true;
/*      */     }
/* 3981 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3987 */     PageContext pageContext = _jspx_page_context;
/* 3988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3990 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3991 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3992 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3994 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3996 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3997 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3998 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4012 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4015 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4016 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4017 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4019 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 4020 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4021 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4025 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4026 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4027 */       return true;
/*      */     }
/* 4029 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4035 */     PageContext pageContext = _jspx_page_context;
/* 4036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4038 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4039 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4040 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4042 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4044 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 4046 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 4047 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4048 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4049 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4050 */       return true;
/*      */     }
/* 4052 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4058 */     PageContext pageContext = _jspx_page_context;
/* 4059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4061 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4062 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4063 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4065 */     _jspx_th_html_005fcheckbox_005f0.setProperty("usedRouterString");
/*      */     
/* 4067 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:showSAPRouterString(this);");
/* 4068 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4069 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4070 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4071 */       return true;
/*      */     }
/* 4073 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4079 */     PageContext pageContext = _jspx_page_context;
/* 4080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4082 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4083 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4084 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4086 */     _jspx_th_html_005ftext_005f1.setProperty("routerString");
/*      */     
/* 4088 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 4089 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4090 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4091 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4092 */       return true;
/*      */     }
/* 4094 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4100 */     PageContext pageContext = _jspx_page_context;
/* 4101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4103 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4104 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4105 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4107 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 4109 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 4111 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 4112 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4113 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4114 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4115 */       return true;
/*      */     }
/* 4117 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4123 */     PageContext pageContext = _jspx_page_context;
/* 4124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4126 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 4127 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 4128 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4130 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 4132 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 4134 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 4135 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 4136 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 4137 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4138 */       return true;
/*      */     }
/* 4140 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 4141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4146 */     PageContext pageContext = _jspx_page_context;
/* 4147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4149 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4150 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4151 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4153 */     _jspx_th_html_005ftext_005f3.setProperty("pollInterval");
/*      */     
/* 4155 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 4157 */     _jspx_th_html_005ftext_005f3.setSize("15");
/* 4158 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4159 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4160 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4161 */       return true;
/*      */     }
/* 4163 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4169 */     PageContext pageContext = _jspx_page_context;
/* 4170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4172 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4173 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 4174 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4176 */     _jspx_th_c_005fif_005f5.setTest("${!empty enabled}");
/* 4177 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 4178 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 4180 */         out.write("Edit Monitor");
/* 4181 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 4182 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4186 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 4187 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4188 */       return true;
/*      */     }
/* 4190 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 4191 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4196 */     PageContext pageContext = _jspx_page_context;
/* 4197 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4199 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4200 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 4201 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4203 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 4205 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 4206 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 4207 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 4208 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4209 */       return true;
/*      */     }
/* 4211 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 4212 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4217 */     PageContext pageContext = _jspx_page_context;
/* 4218 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4220 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4221 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4222 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4224 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 4225 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4226 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4227 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4228 */       return true;
/*      */     }
/* 4230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4236 */     PageContext pageContext = _jspx_page_context;
/* 4237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4239 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4240 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4241 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4243 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4244 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4245 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4246 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4247 */       return true;
/*      */     }
/* 4249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4255 */     PageContext pageContext = _jspx_page_context;
/* 4256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4258 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4259 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4260 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4262 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4263 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4264 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4265 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4266 */       return true;
/*      */     }
/* 4268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4274 */     PageContext pageContext = _jspx_page_context;
/* 4275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4277 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4278 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4279 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4281 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4282 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4283 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4284 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4285 */       return true;
/*      */     }
/* 4287 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4293 */     PageContext pageContext = _jspx_page_context;
/* 4294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4296 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4297 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4298 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4300 */     _jspx_th_c_005fout_005f4.setValue("${haid}");
/* 4301 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4302 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4304 */       return true;
/*      */     }
/* 4306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4312 */     PageContext pageContext = _jspx_page_context;
/* 4313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4315 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4316 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4317 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4319 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 4320 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4321 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4323 */       return true;
/*      */     }
/* 4325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4331 */     PageContext pageContext = _jspx_page_context;
/* 4332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4334 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4335 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4336 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4338 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4339 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4340 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4341 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4342 */       return true;
/*      */     }
/* 4344 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4345 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4350 */     PageContext pageContext = _jspx_page_context;
/* 4351 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4353 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4354 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4355 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4357 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 4358 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4359 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4360 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4361 */       return true;
/*      */     }
/* 4363 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4364 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4369 */     PageContext pageContext = _jspx_page_context;
/* 4370 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4372 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4373 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4374 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4376 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4377 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4378 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4379 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4380 */       return true;
/*      */     }
/* 4382 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4388 */     PageContext pageContext = _jspx_page_context;
/* 4389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4391 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4392 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4393 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4395 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 4396 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4397 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4398 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4399 */       return true;
/*      */     }
/* 4401 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4407 */     PageContext pageContext = _jspx_page_context;
/* 4408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4410 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4411 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4412 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4414 */     _jspx_th_c_005fout_005f10.setValue("${ha.key}");
/* 4415 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4416 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4417 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4418 */       return true;
/*      */     }
/* 4420 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4426 */     PageContext pageContext = _jspx_page_context;
/* 4427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4429 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4430 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4431 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4433 */     _jspx_th_c_005fout_005f11.setValue("${ha.value}");
/* 4434 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4435 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4436 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4437 */       return true;
/*      */     }
/* 4439 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4445 */     PageContext pageContext = _jspx_page_context;
/* 4446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4448 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4449 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4450 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4452 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 4453 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4454 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4455 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4456 */         out = _jspx_page_context.pushBody();
/* 4457 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4458 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4459 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4462 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4463 */           return true;
/* 4464 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4465 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4468 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4469 */         out = _jspx_page_context.popBody();
/* 4470 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4473 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4474 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4475 */       return true;
/*      */     }
/* 4477 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4483 */     PageContext pageContext = _jspx_page_context;
/* 4484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4486 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4487 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4488 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4490 */     _jspx_th_c_005fout_005f12.setValue("${ha.value}");
/* 4491 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4492 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4493 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4494 */       return true;
/*      */     }
/* 4496 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4502 */     PageContext pageContext = _jspx_page_context;
/* 4503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4505 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4506 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4507 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 4509 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 4510 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4511 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4512 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4513 */       return true;
/*      */     }
/* 4515 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4516 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4521 */     PageContext pageContext = _jspx_page_context;
/* 4522 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4524 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4525 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4526 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4528 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4529 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4530 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4531 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4532 */       return true;
/*      */     }
/* 4534 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4540 */     PageContext pageContext = _jspx_page_context;
/* 4541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4543 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4544 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4545 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4547 */     _jspx_th_c_005fout_005f14.setValue("${ha.key}");
/* 4548 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4549 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4550 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4551 */       return true;
/*      */     }
/* 4553 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4554 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4559 */     PageContext pageContext = _jspx_page_context;
/* 4560 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4562 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4563 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4564 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4566 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 4567 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4568 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4569 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4570 */       return true;
/*      */     }
/* 4572 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4573 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4578 */     PageContext pageContext = _jspx_page_context;
/* 4579 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4581 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4582 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4583 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4585 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 4586 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4587 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4588 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4589 */         out = _jspx_page_context.pushBody();
/* 4590 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4591 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4592 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4595 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4596 */           return true;
/* 4597 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4598 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4601 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4602 */         out = _jspx_page_context.popBody();
/* 4603 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4606 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4607 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4608 */       return true;
/*      */     }
/* 4610 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4616 */     PageContext pageContext = _jspx_page_context;
/* 4617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4619 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4620 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4621 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4623 */     _jspx_th_c_005fout_005f16.setValue("${ha.value}");
/* 4624 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4625 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4626 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4627 */       return true;
/*      */     }
/* 4629 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4635 */     PageContext pageContext = _jspx_page_context;
/* 4636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4638 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4639 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4640 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4642 */     _jspx_th_c_005fout_005f17.setValue("${ha.key}");
/* 4643 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4644 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4645 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4646 */       return true;
/*      */     }
/* 4648 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4654 */     PageContext pageContext = _jspx_page_context;
/* 4655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4657 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4658 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4659 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4661 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 4662 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4663 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4664 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4665 */       return true;
/*      */     }
/* 4667 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4673 */     PageContext pageContext = _jspx_page_context;
/* 4674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4676 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4677 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4678 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4680 */     _jspx_th_c_005fif_005f15.setTest("${empty associatedmgs}");
/* 4681 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4682 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4684 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4685 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4686 */           return true;
/* 4687 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 4688 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4689 */           return true;
/* 4690 */         out.write("</td>\n\t ");
/* 4691 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4692 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4696 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4697 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4698 */       return true;
/*      */     }
/* 4700 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4706 */     PageContext pageContext = _jspx_page_context;
/* 4707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4709 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4710 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4711 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4713 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4714 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4715 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4716 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4717 */       return true;
/*      */     }
/* 4719 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4725 */     PageContext pageContext = _jspx_page_context;
/* 4726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4728 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4729 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4730 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4732 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 4733 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4734 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4735 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4736 */       return true;
/*      */     }
/* 4738 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4744 */     PageContext pageContext = _jspx_page_context;
/* 4745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4747 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4748 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4749 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f5);
/*      */     
/* 4751 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4752 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4753 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4754 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4755 */       return true;
/*      */     }
/* 4757 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4758 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SAPDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */