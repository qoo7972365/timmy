/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.tags.FormatTag;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class ShowWlogicServletData_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2183 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2189 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2190 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/includes/JBLeftArea.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2220 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2244 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2249 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2251 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2253 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2254 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2255 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2256 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2257 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2263 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2266 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2273 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2276 */     JspWriter out = null;
/* 2277 */     Object page = this;
/* 2278 */     JspWriter _jspx_out = null;
/* 2279 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2283 */       response.setContentType("text/html;charset=UTF-8");
/* 2284 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2286 */       _jspx_page_context = pageContext;
/* 2287 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2288 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2289 */       session = pageContext.getSession();
/* 2290 */       out = pageContext.getOut();
/* 2291 */       _jspx_out = out;
/*      */       
/* 2293 */       out.write("<!--$Id$-->\n\n\n\n");
/*      */       
/* 2295 */       request.setAttribute("HelpKey", "Monitors WebLogic Servlet Details");
/*      */       
/* 2297 */       out.write(10);
/* 2298 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2300 */       out.write("\n      ");
/* 2301 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/* 2303 */       out.write("\n       ");
/* 2304 */       if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
/*      */         return;
/* 2306 */       out.write("\n\n\n\n");
/* 2307 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2309 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2310 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2312 */       out.write(10);
/* 2313 */       out.write(10);
/* 2314 */       out.write(10);
/* 2315 */       out.write(10);
/*      */       
/* 2317 */       ArrayList resIDs1 = new ArrayList();
/* 2318 */       ArrayList attribIDs1 = new ArrayList();
/* 2319 */       String wls_resource_id = request.getParameter("resourceid");
/* 2320 */       String war_name = request.getParameter("warName");
/* 2321 */       String resource_name = request.getParameter("resource_name");
/* 2322 */       String type = request.getParameter("type");
/* 2323 */       if ((resource_name == null) || (resource_name.equals("")))
/*      */       {
/* 2325 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2326 */         String query1 = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + wls_resource_id;
/*      */         try
/*      */         {
/* 2329 */           ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 2330 */           if (rs.next())
/*      */           {
/* 2332 */             resource_name = rs.getString(1);
/*      */           }
/* 2334 */           rs.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */       }
/*      */       
/*      */ 
/* 2340 */       String ha_id = request.getParameter("haid");
/* 2341 */       String na_me = request.getParameter("name");
/* 2342 */       String webapp_id = request.getParameter("webAppID");
/*      */       
/* 2344 */       String redirect_to = java.net.URLEncoder.encode("/showWebAppServlets.do?method=getWebAppServletsData&warName=" + war_name + "&resourceid=" + wls_resource_id + "&webAppID=" + webapp_id + "&resourcename=" + resource_name + "&haid=" + ha_id + "&name=" + na_me + "&type=" + type);
/*      */       
/* 2346 */       String yaxis_invocation_count = FormatUtil.getString("am.webclient.weblogic.invocationcount.text");
/* 2347 */       String xaxis_servlet_name = FormatUtil.getString("am.webclient.weblogic.servletname.text");
/* 2348 */       String yaxis_exec_time = FormatUtil.getString("am.webclient.weblogic.executiontimems.text");
/*      */       
/*      */ 
/* 2351 */       out.write(10);
/* 2352 */       GetWLSGraph wlsGraph = null;
/* 2353 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2354 */       if (wlsGraph == null) {
/* 2355 */         wlsGraph = new GetWLSGraph();
/* 2356 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2358 */       out.write(10);
/* 2359 */       com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB dataHandler = null;
/* 2360 */       dataHandler = (com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 2361 */       if (dataHandler == null) {
/* 2362 */         dataHandler = new com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB();
/* 2363 */         _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */       }
/* 2365 */       out.write("\n\n  \n\n\n\n\n\n\n");
/* 2366 */       if (_jspx_meth_c_005fcatch_005f1(_jspx_page_context))
/*      */         return;
/* 2368 */       out.write(10);
/*      */       
/* 2370 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2371 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2372 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2374 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2375 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2376 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2378 */           out.write(32);
/* 2379 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2381 */           out.write(10);
/* 2382 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2384 */           out.write(10);
/* 2385 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2387 */           out.write(10);
/* 2388 */           out.write(10);
/* 2389 */           if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2391 */           out.write(10);
/* 2392 */           out.write(10);
/*      */           
/* 2394 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2395 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2396 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2398 */           _jspx_th_c_005fif_005f4.setTest("${!empty param.jbdetails}");
/* 2399 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2400 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */             for (;;) {
/* 2402 */               out.write(10);
/*      */               
/* 2404 */               PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2405 */               _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2406 */               _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_c_005fif_005f4);
/*      */               
/* 2408 */               _jspx_th_tiles_005fput_005f4.setName("ServerLeftArea");
/*      */               
/* 2410 */               _jspx_th_tiles_005fput_005f4.setType("string");
/* 2411 */               int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2412 */               if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 2413 */                 if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 2414 */                   out = _jspx_page_context.pushBody();
/* 2415 */                   _jspx_th_tiles_005fput_005f4.setBodyContent((BodyContent)out);
/* 2416 */                   _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2419 */                   out.write(32);
/* 2420 */                   out.write(32);
/* 2421 */                   out.write(10);
/* 2422 */                   out.write("<!--$Id$-->\n\n\n<script>\n");
/*      */                   
/* 2424 */                   if (request.getParameter("editPage") != null)
/*      */                   {
/* 2426 */                     out.write("\n   reconfigure();\n");
/*      */                   }
/*      */                   
/* 2429 */                   out.write("\n\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */                   
/* 2431 */                   String resourceid = request.getParameter("resourceid");
/* 2432 */                   String configure_link = (String)request.getAttribute("configurelink");
/*      */                   
/* 2434 */                   out.write("\n\n<script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n        var s = confirm(\"");
/* 2435 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2436 */                   out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=JBOSS-server&select=");
/* 2437 */                   out.print(resourceid);
/* 2438 */                   out.write("\";\n\t }\n\t    function confirmManage()\n \t {\n  var s = confirm(\"");
/* 2439 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2440 */                   out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2441 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 2443 */                   out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n        \t ");
/* 2444 */                   if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 2446 */                   out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2447 */                   out.print(request.getParameter("resourceid"));
/* 2448 */                   out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 2449 */                   out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2450 */                   out.write("\");\n\t      \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2451 */                   if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 2453 */                   out.write("\";\n         }\n       else { \n\t\t    var s = confirm(\"");
/* 2454 */                   out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2455 */                   out.write("\");\n    \t\tif (s){\n   \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2456 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 2458 */                   out.write("\"; //No I18N\n\t\t\t  }\n\t     } \n\t }\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 2459 */                   out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2460 */                   out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/* 2462 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2463 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2464 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/* 2465 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2466 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 2468 */                       out.write(10);
/* 2469 */                       out.write(32);
/* 2470 */                       out.write(32);
/*      */                       
/* 2472 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2473 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2474 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 2476 */                       _jspx_th_c_005fwhen_005f0.setTest("${param.method == 'showdetails' && param.all!='true'}");
/* 2477 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2478 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 2480 */                           out.write("\n         ");
/* 2481 */                           out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2482 */                           out.write(10);
/* 2483 */                           out.write(32);
/* 2484 */                           out.write(32);
/* 2485 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2486 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2490 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2491 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 2494 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2495 */                       out.write(10);
/* 2496 */                       out.write(32);
/* 2497 */                       out.write(32);
/*      */                       
/* 2499 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2500 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2501 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2502 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2503 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/* 2505 */                           out.write("\n\n<a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 2506 */                           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                             return;
/* 2508 */                           out.write("\"\n      class=\"new-left-links\">");
/* 2509 */                           out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2510 */                           out.write("</a>\n\n\n  ");
/* 2511 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2512 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2516 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2517 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/* 2520 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2521 */                       out.write(10);
/* 2522 */                       out.write(32);
/* 2523 */                       out.write(32);
/* 2524 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2525 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2529 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2530 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 2533 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2534 */                   out.write("\n  </td>\n  </tr>\n  <!--Alert configuration should be enabled for Admin and Demo users alone-->\n  ");
/*      */                   
/* 2536 */                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2537 */                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2538 */                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2540 */                   _jspx_th_c_005fif_005f5.setTest("${!empty ADMIN || !empty DEMO}");
/* 2541 */                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2542 */                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                     for (;;) {
/* 2544 */                       out.write("\n   <tr>\n          <td width=\"88%\" class=\"leftlinkstd\">\n       \t");
/*      */                       
/* 2546 */                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2547 */                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2548 */                       _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f5);
/* 2549 */                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2550 */                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                         for (;;) {
/* 2552 */                           out.write("\n         ");
/*      */                           
/* 2554 */                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2555 */                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2556 */                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 2558 */                           _jspx_th_c_005fwhen_005f1.setTest("${param.all=='true'}");
/* 2559 */                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2560 */                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                             for (;;) {
/* 2562 */                               out.write("\n                ");
/* 2563 */                               out.print(ALERTCONFIG_TEXT);
/* 2564 */                               out.write("\n         ");
/* 2565 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2566 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2570 */                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2571 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                           }
/*      */                           
/* 2574 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2575 */                           out.write("\n  \t");
/*      */                           
/* 2577 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2578 */                           _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2579 */                           _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2580 */                           int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2581 */                           if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                             for (;;) {
/* 2583 */                               out.write("\n          <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2584 */                               if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                 return;
/* 2586 */                               out.write("\"\n            class=\"new-left-links\">");
/* 2587 */                               out.print(ALERTCONFIG_TEXT);
/* 2588 */                               out.write("</a>\n              ");
/* 2589 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2590 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2594 */                           if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2595 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                           }
/*      */                           
/* 2598 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2599 */                           out.write("\n\t      ");
/* 2600 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2601 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2605 */                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2606 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                       }
/*      */                       
/* 2609 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2610 */                       out.write("\n            </td>\n        </tr>\n  ");
/* 2611 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2612 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2616 */                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2617 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                   }
/*      */                   
/* 2620 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2621 */                   out.write("\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */                   
/* 2623 */                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2624 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2625 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2627 */                   _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 2628 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2629 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/* 2631 */                       out.write("\n <tr>\n  <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2632 */                       out.print(request.getParameter("resourceid"));
/* 2633 */                       out.write("&type=");
/* 2634 */                       out.print(request.getParameter("type"));
/* 2635 */                       out.write("&moname=");
/* 2636 */                       out.print(request.getParameter("moname"));
/* 2637 */                       out.write("&method=showdetails&resourcename=");
/* 2638 */                       out.print(request.getParameter("resourcename"));
/* 2639 */                       out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n");
/* 2640 */                       out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2641 */                       out.write("</a> </td>\n </tr>\n");
/* 2642 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2643 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2647 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2648 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/* 2651 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2652 */                   out.write(10);
/* 2653 */                   out.write(10);
/*      */                   
/* 2655 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2656 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2657 */                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2659 */                   _jspx_th_c_005fif_005f6.setTest("${!empty ADMIN || !empty DEMO}");
/* 2660 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2661 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                     for (;;) {
/* 2663 */                       out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                       
/* 2665 */                       ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2666 */                       _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2667 */                       _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f6);
/* 2668 */                       int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2669 */                       if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                         for (;;) {
/* 2671 */                           out.write("\n    ");
/*      */                           
/* 2673 */                           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2674 */                           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2675 */                           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                           
/* 2677 */                           _jspx_th_c_005fwhen_005f2.setTest("${uri=='/reconfigure.do'}");
/* 2678 */                           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2679 */                           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                             for (;;) {
/* 2681 */                               out.write("\n        ");
/* 2682 */                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2683 */                               out.write(10);
/* 2684 */                               out.write(32);
/* 2685 */                               out.write(32);
/* 2686 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2687 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2691 */                           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2692 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                           }
/*      */                           
/* 2695 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2696 */                           out.write(10);
/* 2697 */                           out.write(32);
/* 2698 */                           out.write(32);
/*      */                           
/* 2700 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2701 */                           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2702 */                           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2703 */                           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2704 */                           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                             for (;;) {
/* 2706 */                               out.write("\n    <a href=\"/manageConfMons.do?haid=");
/* 2707 */                               if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                                 return;
/* 2709 */                               out.write("&method=editPreConfMonitor&resourceid=");
/* 2710 */                               out.print(request.getParameter("resourceid"));
/* 2711 */                               out.write("&type=JBoss:8080&resourcename=");
/* 2712 */                               out.print(request.getParameter("moname"));
/* 2713 */                               out.write("&displayName=");
/* 2714 */                               out.print(request.getParameter("resourcename"));
/* 2715 */                               out.write("\"\n      class=\"new-left-links\">");
/* 2716 */                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2717 */                               out.write("</a>\n  ");
/* 2718 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2719 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2723 */                           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2724 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                           }
/*      */                           
/* 2727 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2728 */                           out.write(10);
/* 2729 */                           out.write(32);
/* 2730 */                           out.write(32);
/* 2731 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2732 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2736 */                       if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2737 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                       }
/*      */                       
/* 2740 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2741 */                       out.write("\n  </td>\n  </tr>\n");
/* 2742 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2743 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2747 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2748 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                   }
/*      */                   
/* 2751 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2752 */                   out.write(10);
/* 2753 */                   out.write(10);
/*      */                   
/* 2755 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2756 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2757 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2759 */                   _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO}");
/* 2760 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2761 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 2763 */                       out.write(10);
/* 2764 */                       out.write(10);
/*      */                       
/* 2766 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2767 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2768 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 2770 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2771 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2772 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2774 */                           out.write("\n  <tr>\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2775 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2776 */                           out.write("</A></td>\n  \t</td>\n  </tr>\n");
/* 2777 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2778 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2782 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2783 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 2786 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2787 */                       out.write(10);
/* 2788 */                       out.write(10);
/*      */                       
/* 2790 */                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2791 */                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2792 */                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 2794 */                       _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 2795 */                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2796 */                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                         for (;;) {
/* 2798 */                           out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2799 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2800 */                           out.write("</a></td>\n\n");
/* 2801 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2802 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2806 */                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2807 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                       }
/*      */                       
/* 2810 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2811 */                       out.write("\n\n\n\n  </tr>\n  ");
/* 2812 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2813 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2817 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2818 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 2821 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2822 */                   out.write(10);
/*      */                   
/* 2824 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2825 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2826 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2828 */                   _jspx_th_c_005fif_005f8.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 2829 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2830 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 2832 */                       out.write("\n  <tr>\n  ");
/*      */                       
/* 2834 */                       if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                       {
/*      */ 
/* 2837 */                         out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2838 */                         out.print(FormatUtil.getString("Manage"));
/* 2839 */                         out.write("</A></td>\n    ");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 2845 */                         out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2846 */                         out.print(FormatUtil.getString("UnManage"));
/* 2847 */                         out.write("</A></td>\n    ");
/*      */                       }
/*      */                       
/*      */ 
/* 2851 */                       out.write("\n  </tr>\n  ");
/* 2852 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2853 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2857 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2858 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 2861 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2862 */                   out.write("\n\n\n\n");
/*      */                   
/* 2864 */                   if ((configure_link != null) && (!configure_link.equals("null")))
/*      */                   {
/*      */ 
/* 2867 */                     out.write("\n\n   ");
/*      */                     
/* 2869 */                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2870 */                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2871 */                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                     
/* 2873 */                     _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO}");
/* 2874 */                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2875 */                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                       for (;;) {
/* 2877 */                         out.write("\n    <tr>\n    <td class=\"leftlinkstd\" >\n\n    \t<a href=\"");
/* 2878 */                         out.print(request.getAttribute("configurelink"));
/* 2879 */                         out.write("\" onclick=\"javascript:return subAddCustom(this);\" class=\"new-left-links\">\n    \t  ");
/* 2880 */                         out.print(FormatUtil.getString("am.webclient.common.addcustomattributes.text"));
/* 2881 */                         out.write("\n    \t</a>\n    </td>\n  </tr>\n   ");
/* 2882 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2883 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2887 */                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2888 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                     }
/*      */                     
/* 2891 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2892 */                     out.write("\n   ");
/*      */                   }
/*      */                   
/*      */ 
/* 2896 */                   out.write(10);
/* 2897 */                   out.write(32);
/* 2898 */                   out.write(32);
/*      */                   
/* 2900 */                   IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2901 */                   _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2902 */                   _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2904 */                   _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN}");
/* 2905 */                   int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2906 */                   if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                     for (;;) {
/* 2908 */                       out.write("\n  \t   \t<tr>\n  \t       \t <td colspan=\"2\" class=\"leftlinkstd\">\n  \t       \t ");
/* 2909 */                       out.print(com.adventnet.appmanager.fault.FaultUtil.getAlertTemplateURL(request.getParameter("resourceid"), request.getParameter("name"), "JBOSS-server", "JBoss Server"));
/* 2910 */                       out.write("\n  \t       \t </td>\n  \t      \t</tr>\n  ");
/* 2911 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2912 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2916 */                   if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2917 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                   }
/*      */                   
/* 2920 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2921 */                   out.write(10);
/* 2922 */                   out.write(32);
/* 2923 */                   out.write(32);
/*      */                   
/* 2925 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2926 */                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2927 */                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2929 */                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2930 */                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2931 */                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                     for (;;) {
/* 2933 */                       out.write("\n    ");
/*      */                       
/* 2935 */                       String resourceid_poll = request.getParameter("resourceid");
/* 2936 */                       String resourcetype = request.getParameter("type");
/*      */                       
/* 2938 */                       out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2939 */                       out.print(resourceid_poll);
/* 2940 */                       out.write("&resourcetype=");
/* 2941 */                       out.print(resourcetype);
/* 2942 */                       out.write("\" class=\"new-left-links\"> ");
/* 2943 */                       out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2944 */                       out.write("</a></td>\n    </tr>\n  ");
/* 2945 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2946 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2950 */                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2951 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                   }
/*      */                   
/* 2954 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2955 */                   out.write(10);
/* 2956 */                   out.write(32);
/* 2957 */                   out.write(32);
/*      */                   
/* 2959 */                   PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2960 */                   _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2961 */                   _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                   
/* 2963 */                   _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 2964 */                   int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2965 */                   if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                     for (;;) {
/* 2967 */                       out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2968 */                       out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2969 */                       out.write("</a></td>\n        </td>\n    ");
/* 2970 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2971 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2975 */                   if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2976 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                   }
/*      */                   
/* 2979 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2980 */                   out.write("\n    ");
/* 2981 */                   out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                   
/* 2983 */                   if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                   {
/* 2985 */                     Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 2986 */                     String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                     
/* 2988 */                     String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 2989 */                     String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 2990 */                     if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                     {
/* 2992 */                       if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                       {
/*      */ 
/* 2995 */                         out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2996 */                         out.print(ciInfoUrl);
/* 2997 */                         out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2998 */                         out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2999 */                         out.write("</a></td>");
/* 3000 */                         out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3001 */                         out.print(ciRLUrl);
/* 3002 */                         out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3003 */                         out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3004 */                         out.write("</a></td>");
/* 3005 */                         out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                       }
/* 3009 */                       else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                       {
/*      */ 
/* 3012 */                         out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3013 */                         out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3014 */                         out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3015 */                         out.print(ciInfoUrl);
/* 3016 */                         out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3017 */                         out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3018 */                         out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3019 */                         out.print(ciRLUrl);
/* 3020 */                         out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3021 */                         out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3022 */                         out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/* 3027 */                   out.write("\n \n \n\n");
/* 3028 */                   out.write("\n  </table>\n");
/*      */                   
/*      */ 
/* 3031 */                   ArrayList attribIDs = new ArrayList();
/* 3032 */                   ArrayList resIDs = new ArrayList();
/* 3033 */                   resIDs.add(resourceid);
/* 3034 */                   attribIDs.add("308");
/* 3035 */                   attribIDs.add("309");
/* 3036 */                   Properties alert = getStatus(resIDs, attribIDs);
/*      */                   
/* 3038 */                   out.write("\n</td>\n  </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3039 */                   out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3040 */                   out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3041 */                   if (_jspx_meth_c_005fout_005f9(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 3043 */                   out.write("&attributeid=309')\" class=\"new-left-links\">");
/* 3044 */                   out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3045 */                   out.write("</a></td>\n        <td><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3046 */                   if (_jspx_meth_c_005fout_005f10(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 3048 */                   out.write("&attributeid=309')\">");
/* 3049 */                   out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "309")));
/* 3050 */                   out.write("</a></td>\n   </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3051 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 3053 */                   out.write("&attributeid=308')\" class=\"new-left-links\">");
/* 3054 */                   out.print(FormatUtil.getString("Availability"));
/* 3055 */                   out.write("</a></td>\n    <td width=\"51%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3056 */                   if (_jspx_meth_c_005fout_005f12(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 3058 */                   out.write("&attributeid=308')\">\n    ");
/* 3059 */                   out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "308")));
/* 3060 */                   out.write("</a>\n </td>\n  </tr>\n  ");
/* 3061 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                     return;
/* 3063 */                   out.write("\n</table></td>\n  </tr>\n    </tr>\n  <tr>\n  <td>&nbsp;\n\n  </td>\n  </tr>\n\n\n");
/* 3064 */                   out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                   
/*      */ 
/*      */ 
/* 3068 */                   boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3069 */                   if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */                   {
/* 3071 */                     showAssociatedBSG = false;
/*      */                     
/*      */ 
/*      */ 
/* 3075 */                     CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3076 */                     CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3077 */                     String loginName = request.getUserPrincipal().getName();
/* 3078 */                     CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                     
/* 3080 */                     if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                     {
/* 3082 */                       showAssociatedBSG = true;
/*      */                     }
/*      */                   }
/* 3085 */                   String monitorType = request.getParameter("type");
/* 3086 */                   ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3087 */                   boolean mon = conf1.isConfMonitor(monitorType);
/* 3088 */                   if (showAssociatedBSG)
/*      */                   {
/* 3090 */                     Hashtable associatedmgs = new Hashtable();
/* 3091 */                     String resId = request.getParameter("resourceid");
/* 3092 */                     request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 3093 */                     if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                     {
/* 3095 */                       mon = false;
/*      */                     }
/*      */                     
/* 3098 */                     if (!mon)
/*      */                     {
/* 3100 */                       out.write(10);
/* 3101 */                       out.write(10);
/*      */                       
/* 3103 */                       IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3104 */                       _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3105 */                       _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                       
/* 3107 */                       _jspx_th_c_005fif_005f12.setTest("${!empty associatedmgs}");
/* 3108 */                       int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3109 */                       if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                         for (;;) {
/* 3111 */                           out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3112 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3113 */                           out.write("</td>\n        </tr>\n        ");
/*      */                           
/* 3115 */                           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3116 */                           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3117 */                           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f12);
/*      */                           
/* 3119 */                           _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                           
/* 3121 */                           _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                           
/* 3123 */                           _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3124 */                           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                           try {
/* 3126 */                             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3127 */                             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                               for (;;) {
/* 3129 */                                 out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3130 */                                 if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3188 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3132 */                                 out.write("&method=showApplication\" title=\"");
/* 3133 */                                 if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3188 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3135 */                                 out.write("\"  class=\"new-left-links\">\n         ");
/* 3136 */                                 if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3188 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3138 */                                 out.write("\n    \t");
/* 3139 */                                 out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3140 */                                 out.write("\n         </a></td>\n        <td>");
/*      */                                 
/* 3142 */                                 PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3143 */                                 _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3144 */                                 _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/* 3146 */                                 _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 3147 */                                 int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3148 */                                 if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                   for (;;) {
/* 3150 */                                     out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3151 */                                     if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 3188 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 3153 */                                     out.write(39);
/* 3154 */                                     out.write(44);
/* 3155 */                                     out.write(39);
/* 3156 */                                     out.print(resId);
/* 3157 */                                     out.write(39);
/* 3158 */                                     out.write(44);
/* 3159 */                                     out.write(39);
/* 3160 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3161 */                                     out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3162 */                                     out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3163 */                                     out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3164 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3165 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3169 */                                 if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3170 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3188 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 3173 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3174 */                                 out.write("</td>\n        </tr>\n\t");
/* 3175 */                                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3176 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3180 */                             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3188 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/*      */                           }
/*      */                           catch (Throwable _jspx_exception)
/*      */                           {
/*      */                             for (;;)
/*      */                             {
/* 3184 */                               int tmp6111_6110 = 0; int[] tmp6111_6108 = _jspx_push_body_count_c_005fforEach_005f0; int tmp6113_6112 = tmp6111_6108[tmp6111_6110];tmp6111_6108[tmp6111_6110] = (tmp6113_6112 - 1); if (tmp6113_6112 <= 0) break;
/* 3185 */                               out = _jspx_page_context.popBody(); }
/* 3186 */                             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                           } finally {
/* 3188 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3189 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                           }
/* 3191 */                           out.write("\n      </table>\n ");
/* 3192 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3193 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3197 */                       if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3198 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                       }
/*      */                       
/* 3201 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3202 */                       out.write(10);
/* 3203 */                       out.write(32);
/*      */                       
/* 3205 */                       IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3206 */                       _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3207 */                       _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                       
/* 3209 */                       _jspx_th_c_005fif_005f13.setTest("${empty associatedmgs}");
/* 3210 */                       int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3211 */                       if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                         for (;;) {
/* 3213 */                           out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3214 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3215 */                           out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3216 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3217 */                           out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3218 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3219 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3223 */                       if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3224 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                       }
/*      */                       
/* 3227 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3228 */                       out.write(10);
/* 3229 */                       out.write(32);
/* 3230 */                       out.write(10);
/*      */ 
/*      */                     }
/* 3233 */                     else if (mon)
/*      */                     {
/*      */ 
/*      */ 
/* 3237 */                       out.write(10);
/*      */                       
/* 3239 */                       IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3240 */                       _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3241 */                       _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f4);
/*      */                       
/* 3243 */                       _jspx_th_c_005fif_005f14.setTest("${!empty associatedmgs}");
/* 3244 */                       int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3245 */                       if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                         for (;;) {
/* 3247 */                           out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 3248 */                           if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                             return;
/* 3250 */                           out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                           
/* 3252 */                           ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3253 */                           _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3254 */                           _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f14);
/*      */                           
/* 3256 */                           _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                           
/* 3258 */                           _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                           
/* 3260 */                           _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3261 */                           int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                           try {
/* 3263 */                             int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3264 */                             if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                               for (;;) {
/* 3266 */                                 out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3267 */                                 if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3269 */                                 out.write("&method=showApplication\" title=\"");
/* 3270 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3272 */                                 out.write("\"  class=\"staticlinks\">\n         ");
/* 3273 */                                 if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3275 */                                 out.write("\n    \t");
/* 3276 */                                 out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3277 */                                 out.write("</a></span>\t\n\t\t ");
/*      */                                 
/* 3279 */                                 PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3280 */                                 _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3281 */                                 _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                 
/* 3283 */                                 _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3284 */                                 int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3285 */                                 if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                   for (;;) {
/* 3287 */                                     out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3288 */                                     if (_jspx_meth_c_005fout_005f21(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3328 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 3290 */                                     out.write(39);
/* 3291 */                                     out.write(44);
/* 3292 */                                     out.write(39);
/* 3293 */                                     out.print(resId);
/* 3294 */                                     out.write(39);
/* 3295 */                                     out.write(44);
/* 3296 */                                     out.write(39);
/* 3297 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3298 */                                     out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3299 */                                     out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3300 */                                     out.write("\"  title=\"");
/* 3301 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3328 */                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                     }
/* 3303 */                                     out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3304 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3305 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3309 */                                 if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3310 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3313 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3314 */                                 out.write("\n\n\t\t \t");
/* 3315 */                                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3316 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3320 */                             if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3328 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/*      */                           }
/*      */                           catch (Throwable _jspx_exception)
/*      */                           {
/*      */                             for (;;)
/*      */                             {
/* 3324 */                               int tmp7137_7136 = 0; int[] tmp7137_7134 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7139_7138 = tmp7137_7134[tmp7137_7136];tmp7137_7134[tmp7137_7136] = (tmp7139_7138 - 1); if (tmp7139_7138 <= 0) break;
/* 3325 */                               out = _jspx_page_context.popBody(); }
/* 3326 */                             _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                           } finally {
/* 3328 */                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 3329 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                           }
/* 3331 */                           out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3332 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3333 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3337 */                       if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3338 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                       }
/*      */                       
/* 3341 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3342 */                       out.write(10);
/* 3343 */                       out.write(32);
/* 3344 */                       if (_jspx_meth_c_005fif_005f15(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                         return;
/* 3346 */                       out.write(32);
/* 3347 */                       out.write(10);
/*      */                     }
/*      */                     
/*      */                   }
/* 3351 */                   else if (mon)
/*      */                   {
/*      */ 
/* 3354 */                     out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3355 */                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                       return;
/* 3357 */                     out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                   }
/*      */                   
/*      */ 
/* 3361 */                   out.write(9);
/* 3362 */                   out.write(9);
/* 3363 */                   out.write("\n\n</table>\n\n\n");
/* 3364 */                   out.write(10);
/* 3365 */                   int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3366 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3369 */                 if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3370 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3373 */               if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3374 */                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */               }
/*      */               
/* 3377 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3378 */               out.write("                         \n");
/* 3379 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3380 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 3384 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3385 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */           }
/*      */           
/* 3388 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3389 */           out.write(10);
/*      */           
/* 3391 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3392 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3393 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3395 */           _jspx_th_tiles_005fput_005f5.setName("UserArea");
/*      */           
/* 3397 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 3398 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3399 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 3400 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3401 */               out = _jspx_page_context.pushBody();
/* 3402 */               _jspx_th_tiles_005fput_005f5.setBodyContent((BodyContent)out);
/* 3403 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3406 */               out.write(" \n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  ");
/*      */               
/* 3408 */               IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3409 */               _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3410 */               _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3412 */               _jspx_th_c_005fif_005f16.setTest("${empty param.jbdetails}");
/* 3413 */               int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3414 */               if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                 for (;;) {
/* 3416 */                   out.write(" \n  <tr>\n\t");
/*      */                   
/* 3418 */                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3419 */                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 3420 */                   _jspx_th_c_005fif_005f17.setParent(_jspx_th_c_005fif_005f16);
/*      */                   
/* 3422 */                   _jspx_th_c_005fif_005f17.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3423 */                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 3424 */                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                     for (;;) {
/* 3426 */                       out.write("\n\t\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3427 */                       out.print(BreadcrumbUtil.getHomePage(request));
/* 3428 */                       out.write(" &gt; ");
/* 3429 */                       out.print(BreadcrumbUtil.getHAPage(request));
/* 3430 */                       out.write(" &gt; ");
/* 3431 */                       out.print(BreadcrumbUtil.getServerDetailsPage(request, type));
/* 3432 */                       out.write(" &gt;  <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3433 */                       if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                         return;
/* 3435 */                       out.write("#webapp\" class=\"bcinactive\">");
/* 3436 */                       out.print(FormatUtil.getString("am.webclient.jboss.webapplications.text"));
/* 3437 */                       out.write("</a> &gt; <span class=\"bcactive\">");
/* 3438 */                       out.print(FormatUtil.getString("am.webclient.jboss.servletdetailsof.text"));
/* 3439 */                       out.write("&nbsp; ");
/* 3440 */                       if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                         return;
/* 3442 */                       out.write(" </span></td>\n\t");
/* 3443 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 3444 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3448 */                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 3449 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                   }
/*      */                   
/* 3452 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 3453 */                   out.write(10);
/* 3454 */                   out.write(9);
/*      */                   
/* 3456 */                   IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3457 */                   _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3458 */                   _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fif_005f16);
/*      */                   
/* 3460 */                   _jspx_th_c_005fif_005f18.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3461 */                   int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3462 */                   if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                     for (;;) {
/* 3464 */                       out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3465 */                       out.print(BreadcrumbUtil.getMonitorsPage());
/* 3466 */                       out.write(" &gt; ");
/* 3467 */                       out.print(BreadcrumbUtil.getMonitorResourceTypes(type));
/* 3468 */                       out.write(" &gt; ");
/* 3469 */                       out.print(BreadcrumbUtil.getServerDetailsPage(request, type));
/* 3470 */                       out.write(" &gt; <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3471 */                       if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                         return;
/* 3473 */                       out.write("#webapp\" class=\"bcinactive\">");
/* 3474 */                       out.print(FormatUtil.getString("am.webclient.jboss.webapplications.text"));
/* 3475 */                       out.write("</a> &gt; <span class=\"bcactive\">");
/* 3476 */                       out.print(FormatUtil.getString("am.webclient.jboss.servletdetailsof.text"));
/* 3477 */                       out.write("&nbsp;");
/* 3478 */                       if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                         return;
/* 3480 */                       out.write(" </span></td>\n\t");
/* 3481 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3482 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3486 */                   if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3487 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                   }
/*      */                   
/* 3490 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3491 */                   out.write("\n    </tr>\n");
/* 3492 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3493 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3497 */               if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3498 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */               }
/*      */               
/* 3501 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3502 */               out.write(" \n\n<!-- This is for JBoss BreadCrumbs -->\n");
/*      */               
/* 3504 */               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3505 */               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3506 */               _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3508 */               _jspx_th_c_005fif_005f19.setTest("${!empty param.jbdetails}");
/* 3509 */               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3510 */               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                 for (;;) {
/* 3512 */                   out.write("\n    <tr>\n\t");
/*      */                   
/* 3514 */                   IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3515 */                   _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3516 */                   _jspx_th_c_005fif_005f20.setParent(_jspx_th_c_005fif_005f19);
/*      */                   
/* 3518 */                   _jspx_th_c_005fif_005f20.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3519 */                   int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3520 */                   if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                     for (;;) {
/* 3522 */                       out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3523 */                       out.print(BreadcrumbUtil.getHomePage(request));
/* 3524 */                       out.write(" &gt; ");
/* 3525 */                       out.print(BreadcrumbUtil.getHAPage(request));
/* 3526 */                       out.write(" &gt; ");
/* 3527 */                       out.print(BreadcrumbUtil.getJBossDetailsPage(request, "JBOSS-server", BreadcrumbUtil.getDisplayName(request)));
/* 3528 */                       out.write(" &gt;  ");
/* 3529 */                       out.print(BreadcrumbUtil.getJBossWebAppPage(request, ""));
/* 3530 */                       out.write(" &gt; <span class=\"bcactive\"> Servlet Details of ");
/* 3531 */                       if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f20, _jspx_page_context))
/*      */                         return;
/* 3533 */                       out.write(" </span></td>\n\t");
/* 3534 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3535 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3539 */                   if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3540 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                   }
/*      */                   
/* 3543 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3544 */                   out.write(10);
/* 3545 */                   out.write(9);
/*      */                   
/* 3547 */                   IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3548 */                   _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3549 */                   _jspx_th_c_005fif_005f21.setParent(_jspx_th_c_005fif_005f19);
/*      */                   
/* 3551 */                   _jspx_th_c_005fif_005f21.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3552 */                   int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3553 */                   if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                     for (;;) {
/* 3555 */                       out.write("\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3556 */                       out.print(BreadcrumbUtil.getMonitorsPage());
/* 3557 */                       out.write(" &gt; ");
/* 3558 */                       out.print(BreadcrumbUtil.getMonitorResourceTypes("JBOSS-server"));
/* 3559 */                       out.write(" &gt; ");
/* 3560 */                       out.print(BreadcrumbUtil.getJBossDetailsPage(request, "JBOSS-server", BreadcrumbUtil.getDisplayName(request)));
/* 3561 */                       out.write(" &gt; <a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3562 */                       if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                         return;
/* 3564 */                       out.write("#webapp\" class=\"bcinactive\">Web Applications</a> &gt; <span class=\"bcactive\">Servlet Details of ");
/* 3565 */                       if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                         return;
/* 3567 */                       out.write(" </span></td>\n\t");
/* 3568 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3569 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3573 */                   if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3574 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                   }
/*      */                   
/* 3577 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3578 */                   out.write("\n    </tr>\n");
/* 3579 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3580 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3584 */               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3585 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */               }
/*      */               
/* 3588 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3589 */               out.write(" \n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n \n\n\n \n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n  <tr> \n    <td width=\"52%\" height=\"230\" valign=\"top\" > <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr> \n          <td class=\"tableheading\">");
/* 3590 */               out.print(FormatUtil.getString("am.webclient.weblogic.servletsinvocation.text"));
/* 3591 */               out.write("</td>\n        </tr>\n        <tr> \n          <td> \n            ");
/*      */               
/* 3593 */               wlsGraph.setParam((String)request.getAttribute("webAppID"), "SERVLET_INVCOUNT");
/*      */               
/* 3595 */               out.write("\n            ");
/*      */               
/* 3597 */               BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.get(BarChart.class);
/* 3598 */               _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 3599 */               _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3601 */               _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("wlsGraph");
/*      */               
/* 3603 */               _jspx_th_awolf_005fbarchart_005f0.setWidth("300");
/*      */               
/* 3605 */               _jspx_th_awolf_005fbarchart_005f0.setHeight("200");
/*      */               
/* 3607 */               _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */               
/* 3609 */               _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*      */               
/* 3611 */               _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(xaxis_servlet_name);
/*      */               
/* 3613 */               _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(yaxis_invocation_count);
/*      */               
/* 3615 */               _jspx_th_awolf_005fbarchart_005f0.setBaseItemLabel(true);
/* 3616 */               int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 3617 */               if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 3618 */                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3619 */                   out = _jspx_page_context.pushBody();
/* 3620 */                   _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 3621 */                   _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3624 */                   out.write(" \n            ");
/* 3625 */                   int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 3626 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3629 */                 if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 3630 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3633 */               if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 3634 */                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */               }
/*      */               
/* 3637 */               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 3638 */               out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"48%\" height=\"230\" valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"lrtbdarkborder\">\n        <tr> \n          <td  class=\"tableheading\">");
/* 3639 */               out.print(FormatUtil.getString("am.webclient.weblogic.servletsexecution.text"));
/* 3640 */               out.write("</td>\n        </tr>\n        <tr> \n          <td> \n            ");
/*      */               
/* 3642 */               wlsGraph.setParam((String)request.getAttribute("webAppID"), "SERVLET_EXECHIGH");
/*      */               
/* 3644 */               out.write("\n            ");
/*      */               
/* 3646 */               BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.get(BarChart.class);
/* 3647 */               _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 3648 */               _jspx_th_awolf_005fbarchart_005f1.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3650 */               _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("wlsGraph");
/*      */               
/* 3652 */               _jspx_th_awolf_005fbarchart_005f1.setWidth("300");
/*      */               
/* 3654 */               _jspx_th_awolf_005fbarchart_005f1.setHeight("200");
/*      */               
/* 3656 */               _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*      */               
/* 3658 */               _jspx_th_awolf_005fbarchart_005f1.setUrl(false);
/*      */               
/* 3660 */               _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel(xaxis_servlet_name);
/*      */               
/* 3662 */               _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel(yaxis_exec_time);
/*      */               
/* 3664 */               _jspx_th_awolf_005fbarchart_005f1.setBaseItemLabel(true);
/* 3665 */               int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 3666 */               if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 3667 */                 if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3668 */                   out = _jspx_page_context.pushBody();
/* 3669 */                   _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 3670 */                   _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 3673 */                   out.write(" \n            ");
/* 3674 */                   int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 3675 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 3678 */                 if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 3679 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 3682 */               if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 3683 */                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f1); return;
/*      */               }
/*      */               
/* 3686 */               this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005flegend_005fheight_005fdataSetProducer_005fbaseItemLabel.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 3687 */               out.write(" </td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr> \n    <td width=\"72%\" height=\"26\" class=\"tableheading\">");
/* 3688 */               out.print(FormatUtil.getString("am.webclient.weblogic.servletdetails.text"));
/* 3689 */               out.write(" -\n      <bean:write name=\"warName\"/></td>\n  </tr>\n</table>\n");
/*      */               
/* 3691 */               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3692 */               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3693 */               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f5);
/*      */               
/* 3695 */               _jspx_th_logic_005fnotEmpty_005f0.setName("data");
/* 3696 */               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3697 */               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                 for (;;) {
/* 3699 */                   out.write(" \n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrbborder\" align=\"left\">\n  <tr> \n    <td width=\"20%\" class=\"columnheadingnotop\">");
/* 3700 */                   out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3701 */                   out.write("</td>\n    <td class=\"columnheadingnotop\">");
/* 3702 */                   out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3703 */                   out.write("</td>\n    <td width=\"16%\" class=\"columnheadingnotop\" align=\"center\"><span class=\"bodytextbold\">");
/* 3704 */                   out.print(FormatUtil.getString("am.webclient.weblogic.exectimehigh.text"));
/* 3705 */                   out.write("</span></td>\n    <td width=\"16%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3706 */                   out.print(FormatUtil.getString("am.webclient.weblogic.exectimelow.text"));
/* 3707 */                   out.write("</td>\n    <td width=\"17%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3708 */                   out.print(FormatUtil.getString("am.webclient.weblogic.exectimeaverage.text"));
/* 3709 */                   out.write("</td>\n    <td width=\"14%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3710 */                   out.print(FormatUtil.getString("am.webclient.weblogic.invocationcount.text"));
/* 3711 */                   out.write("</td>\n    <td width=\"18%\" class=\"columnheadingnotop\" align=\"center\">");
/* 3712 */                   out.print(ALERTCONFIG_TEXT);
/* 3713 */                   out.write("</td>\n  </tr>\n  ");
/*      */                   
/* 3715 */                   ArrayList al_ser = (ArrayList)request.getAttribute("data");
/* 3716 */                   if (al_ser != null)
/*      */                   {
/* 3718 */                     for (int i = 0; i < al_ser.size(); i++)
/*      */                     {
/* 3720 */                       resIDs1.add(((Hashtable)al_ser.get(i)).get("ID"));
/*      */                     }
/* 3722 */                     attribIDs1.add("226");
/*      */                   }
/* 3724 */                   Properties alert1 = getStatus(resIDs1, attribIDs1);
/* 3725 */                   int k_ser = 0;
/*      */                   
/* 3727 */                   out.write(10);
/* 3728 */                   out.write(32);
/* 3729 */                   out.write(32);
/*      */                   
/* 3731 */                   ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3732 */                   _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 3733 */                   _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                   
/* 3735 */                   _jspx_th_c_005fforEach_005f2.setItems("${data}");
/*      */                   
/* 3737 */                   _jspx_th_c_005fforEach_005f2.setVar("row");
/*      */                   
/* 3739 */                   _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 3740 */                   int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                   try {
/* 3742 */                     int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 3743 */                     if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                       for (;;) {
/* 3745 */                         out.write(32);
/* 3746 */                         if (_jspx_meth_c_005fif_005f22(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3748 */                         out.write(32);
/* 3749 */                         if (_jspx_meth_c_005fif_005f25(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3751 */                         out.write(" \n  <td class=\"whitegrayborder\">");
/* 3752 */                         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3754 */                         out.write("</td>\n  <td align=\"center\" class=\"whitegrayborder\" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3755 */                         if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3757 */                         out.write("&attributeid=226')\">\n  ");
/*      */                         
/* 3759 */                         if (al_ser != null)
/*      */                         {
/* 3761 */                           out.println(getSeverityImageForHealth(alert1.getProperty(((Hashtable)al_ser.get(k_ser)).get("ID") + "#" + "226")));
/*      */                         }
/*      */                         
/* 3764 */                         out.write("\n  <td align=\"center\" class=\"whitegrayborder\">");
/* 3765 */                         if (_jspx_meth_am_005fFormat_005f0(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3767 */                         out.write("</td>\n  <td align=\"center\" class=\"whitegrayborder\">");
/* 3768 */                         if (_jspx_meth_am_005fFormat_005f1(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3770 */                         out.write("</td>\n  <td align=\"center\" class=\"whitegrayborder\">");
/* 3771 */                         if (_jspx_meth_am_005fFormat_005f2(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3773 */                         out.write("</td>\n  <td align=\"center\" class=\"whitegrayborder\">");
/* 3774 */                         if (_jspx_meth_am_005fFormat_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3776 */                         out.write("</td>\n  <td class=\"whitegrayborder\" align=center><a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3777 */                         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                         }
/* 3779 */                         out.write("&attributeIDs=226,214,215&attributeToSelect=226&redirectto=");
/* 3780 */                         out.print(redirect_to);
/* 3781 */                         out.write("'class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" border=\"0\"></a></td>\n  </tr>\n  ");
/*      */                         
/* 3783 */                         k_ser++;
/*      */                         
/* 3785 */                         out.write(10);
/* 3786 */                         out.write(32);
/* 3787 */                         out.write(32);
/* 3788 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 3789 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3793 */                     if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3801 */                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/* 3797 */                       int tmp10340_10339 = 0; int[] tmp10340_10337 = _jspx_push_body_count_c_005fforEach_005f2; int tmp10342_10341 = tmp10340_10337[tmp10340_10339];tmp10340_10337[tmp10340_10339] = (tmp10342_10341 - 1); if (tmp10342_10341 <= 0) break;
/* 3798 */                       out = _jspx_page_context.popBody(); }
/* 3799 */                     _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                   } finally {
/* 3801 */                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 3802 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */                   }
/* 3804 */                   out.write(" \n  \n</table>\n");
/* 3805 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3806 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3810 */               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3811 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */               }
/*      */               
/* 3814 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3815 */               out.write("\n<br>\n<br>\n </td> </tr> \n\n<tr> \n   ");
/* 3816 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 3817 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3820 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3821 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3824 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3825 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 3828 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 3829 */           out.write(32);
/* 3830 */           if (_jspx_meth_tiles_005fput_005f6(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3832 */           out.write(32);
/* 3833 */           out.write(10);
/* 3834 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3835 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3839 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3840 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3843 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3844 */         out.write(32);
/* 3845 */         out.write(10);
/*      */       }
/* 3847 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3848 */         out = _jspx_out;
/* 3849 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3850 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3851 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3854 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3860 */     PageContext pageContext = _jspx_page_context;
/* 3861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3863 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3864 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3865 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 3867 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3868 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3870 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3871 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3873 */           out.write(32);
/* 3874 */           out.write(10);
/* 3875 */           out.write(9);
/* 3876 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3877 */             return true;
/* 3878 */           out.write(32);
/* 3879 */           out.write(10);
/* 3880 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3881 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3885 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3886 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3889 */         int tmp195_194 = 0; int[] tmp195_192 = _jspx_push_body_count_c_005fcatch_005f0; int tmp197_196 = tmp195_192[tmp195_194];tmp195_192[tmp195_194] = (tmp197_196 - 1); if (tmp197_196 <= 0) break;
/* 3890 */         out = _jspx_page_context.popBody(); }
/* 3891 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3893 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3894 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3901 */     PageContext pageContext = _jspx_page_context;
/* 3902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3904 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3905 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3906 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3908 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3910 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3911 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3912 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3913 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3914 */       return true;
/*      */     }
/* 3916 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3917 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3922 */     PageContext pageContext = _jspx_page_context;
/* 3923 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3925 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3926 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3927 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3929 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3930 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3931 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3933 */         out.write(" \n      \t");
/* 3934 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3935 */           return true;
/* 3936 */         out.write("\n      ");
/* 3937 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3938 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3942 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3943 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3944 */       return true;
/*      */     }
/* 3946 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3952 */     PageContext pageContext = _jspx_page_context;
/* 3953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3955 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3956 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3957 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3959 */     _jspx_th_c_005fset_005f0.setVar("haidparam");
/* 3960 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3961 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3962 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3963 */         out = _jspx_page_context.pushBody();
/* 3964 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3965 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3968 */         out.write("&haid=");
/* 3969 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3970 */           return true;
/* 3971 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3975 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3976 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3979 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3980 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3981 */       return true;
/*      */     }
/* 3983 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3989 */     PageContext pageContext = _jspx_page_context;
/* 3990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3992 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3993 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3994 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3996 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3997 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3998 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3999 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4000 */       return true;
/*      */     }
/* 4002 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4008 */     PageContext pageContext = _jspx_page_context;
/* 4009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4011 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 4012 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4013 */     _jspx_th_c_005fset_005f1.setParent(null);
/*      */     
/* 4015 */     _jspx_th_c_005fset_005f1.setVar("redirect");
/*      */     
/* 4017 */     _jspx_th_c_005fset_005f1.setScope("request");
/* 4018 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4019 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4020 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4021 */         out = _jspx_page_context.pushBody();
/* 4022 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4023 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4026 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 4027 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 4028 */           return true;
/* 4029 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 4030 */           return true;
/* 4031 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4032 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4035 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4036 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4039 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4040 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 4041 */       return true;
/*      */     }
/* 4043 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 4044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4049 */     PageContext pageContext = _jspx_page_context;
/* 4050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4052 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4053 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4054 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4056 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 4057 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4058 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4059 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4060 */       return true;
/*      */     }
/* 4062 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4063 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4068 */     PageContext pageContext = _jspx_page_context;
/* 4069 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4071 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4072 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4073 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4075 */     _jspx_th_c_005fout_005f2.setValue("${haidparam}");
/* 4076 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4077 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4078 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4079 */       return true;
/*      */     }
/* 4081 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4087 */     PageContext pageContext = _jspx_page_context;
/* 4088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4090 */     CatchTag _jspx_th_c_005fcatch_005f1 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 4091 */     _jspx_th_c_005fcatch_005f1.setPageContext(_jspx_page_context);
/* 4092 */     _jspx_th_c_005fcatch_005f1.setParent(null);
/*      */     
/* 4094 */     _jspx_th_c_005fcatch_005f1.setVar("invalidhaid");
/* 4095 */     int[] _jspx_push_body_count_c_005fcatch_005f1 = { 0 };
/*      */     try {
/* 4097 */       int _jspx_eval_c_005fcatch_005f1 = _jspx_th_c_005fcatch_005f1.doStartTag();
/* 4098 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f1 != 0) {
/*      */         for (;;) {
/* 4100 */           out.write(10);
/* 4101 */           if (_jspx_meth_fmt_005fparseNumber_005f1(_jspx_th_c_005fcatch_005f1, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f1))
/* 4102 */             return true;
/* 4103 */           out.write(10);
/* 4104 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f1.doAfterBody();
/* 4105 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4109 */       if (_jspx_th_c_005fcatch_005f1.doEndTag() == 5)
/* 4110 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 4113 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f1; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 4114 */         out = _jspx_page_context.popBody(); }
/* 4115 */       _jspx_th_c_005fcatch_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 4117 */       _jspx_th_c_005fcatch_005f1.doFinally();
/* 4118 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f1);
/*      */     }
/* 4120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f1(JspTag _jspx_th_c_005fcatch_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f1) throws Throwable
/*      */   {
/* 4125 */     PageContext pageContext = _jspx_page_context;
/* 4126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4128 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f1 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 4129 */     _jspx_th_fmt_005fparseNumber_005f1.setPageContext(_jspx_page_context);
/* 4130 */     _jspx_th_fmt_005fparseNumber_005f1.setParent((Tag)_jspx_th_c_005fcatch_005f1);
/*      */     
/* 4132 */     _jspx_th_fmt_005fparseNumber_005f1.setVar("wnhaid");
/*      */     
/* 4134 */     _jspx_th_fmt_005fparseNumber_005f1.setValue("${param.haid}");
/* 4135 */     int _jspx_eval_fmt_005fparseNumber_005f1 = _jspx_th_fmt_005fparseNumber_005f1.doStartTag();
/* 4136 */     if (_jspx_th_fmt_005fparseNumber_005f1.doEndTag() == 5) {
/* 4137 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 4138 */       return true;
/*      */     }
/* 4140 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f1);
/* 4141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4146 */     PageContext pageContext = _jspx_page_context;
/* 4147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4149 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4150 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 4151 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4153 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 4155 */     _jspx_th_tiles_005fput_005f0.setValue("WebLogic Server - Web Application Servlet Statistics");
/* 4156 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 4157 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 4158 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4159 */       return true;
/*      */     }
/* 4161 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 4162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4167 */     PageContext pageContext = _jspx_page_context;
/* 4168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4170 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4171 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 4172 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4174 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4175 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 4176 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 4178 */         out.write(10);
/* 4179 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 4180 */           return true;
/* 4181 */         out.write(10);
/* 4182 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 4183 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4187 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 4188 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4189 */       return true;
/*      */     }
/* 4191 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 4192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4197 */     PageContext pageContext = _jspx_page_context;
/* 4198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4200 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4201 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4202 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4204 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4206 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 4207 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4208 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4209 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4210 */       return true;
/*      */     }
/* 4212 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4213 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4218 */     PageContext pageContext = _jspx_page_context;
/* 4219 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4221 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4222 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4223 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4225 */     _jspx_th_c_005fif_005f2.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4226 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4227 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4229 */         out.write(10);
/* 4230 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4231 */           return true;
/* 4232 */         out.write(10);
/* 4233 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4234 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4238 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4239 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4240 */       return true;
/*      */     }
/* 4242 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4243 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4248 */     PageContext pageContext = _jspx_page_context;
/* 4249 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4251 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4252 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 4253 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4255 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 4257 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 4258 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 4259 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4260 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4261 */       return true;
/*      */     }
/* 4263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 4264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4269 */     PageContext pageContext = _jspx_page_context;
/* 4270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4272 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4273 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4274 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4276 */     _jspx_th_c_005fif_005f3.setTest("${empty param.jbdetails}");
/* 4277 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4278 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 4280 */         out.write(10);
/* 4281 */         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 4282 */           return true;
/* 4283 */         out.write(32);
/* 4284 */         out.write(10);
/* 4285 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4286 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4290 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4291 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4292 */       return true;
/*      */     }
/* 4294 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4300 */     PageContext pageContext = _jspx_page_context;
/* 4301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4303 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4304 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4305 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4307 */     _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */     
/* 4309 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/WlogicLeftArea.jsp");
/* 4310 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4311 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4312 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4313 */       return true;
/*      */     }
/* 4315 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 4316 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4321 */     PageContext pageContext = _jspx_page_context;
/* 4322 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4324 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4325 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4326 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4328 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 4329 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4330 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4331 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4332 */       return true;
/*      */     }
/* 4334 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4335 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4340 */     PageContext pageContext = _jspx_page_context;
/* 4341 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4343 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4344 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4345 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4347 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 4348 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4349 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4351 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 4352 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4353 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4357 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4358 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4359 */       return true;
/*      */     }
/* 4361 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4367 */     PageContext pageContext = _jspx_page_context;
/* 4368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4370 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4371 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4372 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4374 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 4375 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4376 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4377 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4378 */       return true;
/*      */     }
/* 4380 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4381 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4386 */     PageContext pageContext = _jspx_page_context;
/* 4387 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4389 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4390 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4391 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4393 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 4394 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4395 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4396 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4397 */       return true;
/*      */     }
/* 4399 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4400 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4405 */     PageContext pageContext = _jspx_page_context;
/* 4406 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4408 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4409 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4410 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4412 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 4413 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4414 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4415 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4416 */       return true;
/*      */     }
/* 4418 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4419 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4424 */     PageContext pageContext = _jspx_page_context;
/* 4425 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4427 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4428 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4429 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 4431 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 4432 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4433 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4434 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4435 */       return true;
/*      */     }
/* 4437 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4443 */     PageContext pageContext = _jspx_page_context;
/* 4444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4446 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4447 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4448 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 4450 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 4451 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4452 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4453 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4454 */       return true;
/*      */     }
/* 4456 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4462 */     PageContext pageContext = _jspx_page_context;
/* 4463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4465 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4466 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4467 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4469 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 4470 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4471 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4472 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4473 */       return true;
/*      */     }
/* 4475 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4481 */     PageContext pageContext = _jspx_page_context;
/* 4482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4484 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4485 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4486 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4488 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 4489 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4490 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4491 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4492 */       return true;
/*      */     }
/* 4494 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4495 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4500 */     PageContext pageContext = _jspx_page_context;
/* 4501 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4503 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4504 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4505 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4507 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 4508 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4509 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4510 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4511 */       return true;
/*      */     }
/* 4513 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4519 */     PageContext pageContext = _jspx_page_context;
/* 4520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4522 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4523 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4524 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4526 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 4527 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4528 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4529 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4530 */       return true;
/*      */     }
/* 4532 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4538 */     PageContext pageContext = _jspx_page_context;
/* 4539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4541 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4542 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4543 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4545 */     _jspx_th_c_005fif_005f11.setTest("${!empty wtaresourceid}");
/* 4546 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4547 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4549 */         out.write("\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td colspan=\"2\"><a href=\"/showresource.do?type=WTA&method=showdetails&resourceid=");
/* 4550 */         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 4551 */           return true;
/* 4552 */         out.write("\" class=\"new-left-links\">Web Transactions</a> ");
/* 4553 */         out.write("\n </td>\n  </tr>\n  ");
/* 4554 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4555 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4559 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4560 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4561 */       return true;
/*      */     }
/* 4563 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4564 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4569 */     PageContext pageContext = _jspx_page_context;
/* 4570 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4572 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4573 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4574 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4576 */     _jspx_th_c_005fout_005f13.setValue("${wtaresourceid}");
/* 4577 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4578 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4579 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4580 */       return true;
/*      */     }
/* 4582 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4583 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4588 */     PageContext pageContext = _jspx_page_context;
/* 4589 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4591 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4592 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4593 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4595 */     _jspx_th_c_005fout_005f14.setValue("${ha.key}");
/* 4596 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4597 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4598 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4599 */       return true;
/*      */     }
/* 4601 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4607 */     PageContext pageContext = _jspx_page_context;
/* 4608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4610 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4611 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4612 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4614 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 4615 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4616 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4617 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4618 */       return true;
/*      */     }
/* 4620 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4626 */     PageContext pageContext = _jspx_page_context;
/* 4627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4629 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4630 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4631 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4633 */     _jspx_th_c_005fset_005f2.setVar("monitorName");
/* 4634 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4635 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4636 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4637 */         out = _jspx_page_context.pushBody();
/* 4638 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4639 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4640 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4643 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4644 */           return true;
/* 4645 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4646 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4649 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4650 */         out = _jspx_page_context.popBody();
/* 4651 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4654 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4655 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4656 */       return true;
/*      */     }
/* 4658 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4664 */     PageContext pageContext = _jspx_page_context;
/* 4665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4667 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4668 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4669 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 4671 */     _jspx_th_c_005fout_005f16.setValue("${ha.value}");
/* 4672 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4673 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4674 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4675 */       return true;
/*      */     }
/* 4677 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4678 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4683 */     PageContext pageContext = _jspx_page_context;
/* 4684 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4686 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4687 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4688 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 4690 */     _jspx_th_c_005fout_005f17.setValue("${ha.key}");
/* 4691 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4692 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4693 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4694 */       return true;
/*      */     }
/* 4696 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4702 */     PageContext pageContext = _jspx_page_context;
/* 4703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4705 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4706 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4707 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 4709 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4710 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4711 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4712 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4713 */       return true;
/*      */     }
/* 4715 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4721 */     PageContext pageContext = _jspx_page_context;
/* 4722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4724 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4725 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4726 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4728 */     _jspx_th_c_005fout_005f18.setValue("${ha.key}");
/* 4729 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4730 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4731 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4732 */       return true;
/*      */     }
/* 4734 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4740 */     PageContext pageContext = _jspx_page_context;
/* 4741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4743 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4744 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4745 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4747 */     _jspx_th_c_005fout_005f19.setValue("${ha.value}");
/* 4748 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4749 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4750 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4751 */       return true;
/*      */     }
/* 4753 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4754 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4759 */     PageContext pageContext = _jspx_page_context;
/* 4760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4762 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4763 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 4764 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4766 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 4767 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 4768 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 4769 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4770 */         out = _jspx_page_context.pushBody();
/* 4771 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4772 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 4773 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4776 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4777 */           return true;
/* 4778 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 4779 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4782 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 4783 */         out = _jspx_page_context.popBody();
/* 4784 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4787 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 4788 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 4789 */       return true;
/*      */     }
/* 4791 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 4792 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4797 */     PageContext pageContext = _jspx_page_context;
/* 4798 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4800 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4801 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4802 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 4804 */     _jspx_th_c_005fout_005f20.setValue("${ha.value}");
/* 4805 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4806 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4808 */       return true;
/*      */     }
/* 4810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4816 */     PageContext pageContext = _jspx_page_context;
/* 4817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4819 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4820 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4821 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4823 */     _jspx_th_c_005fout_005f21.setValue("${ha.key}");
/* 4824 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4825 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4827 */       return true;
/*      */     }
/* 4829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4835 */     PageContext pageContext = _jspx_page_context;
/* 4836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4838 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4839 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4840 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4842 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 4843 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4844 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4845 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4846 */       return true;
/*      */     }
/* 4848 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4854 */     PageContext pageContext = _jspx_page_context;
/* 4855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4857 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4858 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4859 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4861 */     _jspx_th_c_005fif_005f15.setTest("${empty associatedmgs}");
/* 4862 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4863 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 4865 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4866 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4867 */           return true;
/* 4868 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 4869 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 4870 */           return true;
/* 4871 */         out.write("</td>\n\t ");
/* 4872 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4873 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4877 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4878 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4879 */       return true;
/*      */     }
/* 4881 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4887 */     PageContext pageContext = _jspx_page_context;
/* 4888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4890 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4891 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4892 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4894 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4895 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4896 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4897 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4898 */       return true;
/*      */     }
/* 4900 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4906 */     PageContext pageContext = _jspx_page_context;
/* 4907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4909 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4910 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4911 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 4913 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 4914 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4915 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4916 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4917 */       return true;
/*      */     }
/* 4919 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4925 */     PageContext pageContext = _jspx_page_context;
/* 4926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4928 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4929 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4930 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 4932 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4933 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4934 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4935 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4936 */       return true;
/*      */     }
/* 4938 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4944 */     PageContext pageContext = _jspx_page_context;
/* 4945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4947 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4948 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4949 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 4951 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 4952 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4953 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4955 */       return true;
/*      */     }
/* 4957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4963 */     PageContext pageContext = _jspx_page_context;
/* 4964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4966 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4967 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 4968 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 4970 */     _jspx_th_c_005fout_005f23.setValue("${param.warName}");
/* 4971 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 4972 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 4973 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4974 */       return true;
/*      */     }
/* 4976 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4982 */     PageContext pageContext = _jspx_page_context;
/* 4983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4985 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4986 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 4987 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 4989 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 4990 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 4991 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 4992 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4993 */       return true;
/*      */     }
/* 4995 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5001 */     PageContext pageContext = _jspx_page_context;
/* 5002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5004 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5005 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5006 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 5008 */     _jspx_th_c_005fout_005f25.setValue("${param.warName}");
/* 5009 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5010 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5011 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5012 */       return true;
/*      */     }
/* 5014 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5020 */     PageContext pageContext = _jspx_page_context;
/* 5021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5023 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5024 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5025 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 5027 */     _jspx_th_c_005fout_005f26.setValue("${param.warName}");
/* 5028 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5029 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5030 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5031 */       return true;
/*      */     }
/* 5033 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5039 */     PageContext pageContext = _jspx_page_context;
/* 5040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5042 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5043 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5044 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 5046 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 5047 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5048 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5049 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5050 */       return true;
/*      */     }
/* 5052 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5058 */     PageContext pageContext = _jspx_page_context;
/* 5059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5061 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5062 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5063 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 5065 */     _jspx_th_c_005fout_005f28.setValue("${param.warName}");
/* 5066 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5067 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5068 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5069 */       return true;
/*      */     }
/* 5071 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f22(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5077 */     PageContext pageContext = _jspx_page_context;
/* 5078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5080 */     IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5081 */     _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 5082 */     _jspx_th_c_005fif_005f22.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5084 */     _jspx_th_c_005fif_005f22.setTest("${row.health != 1}");
/* 5085 */     int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 5086 */     if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */       for (;;) {
/* 5088 */         out.write(10);
/* 5089 */         out.write(32);
/* 5090 */         out.write(32);
/* 5091 */         if (_jspx_meth_c_005fif_005f23(_jspx_th_c_005fif_005f22, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5092 */           return true;
/* 5093 */         out.write(32);
/* 5094 */         if (_jspx_meth_c_005fif_005f24(_jspx_th_c_005fif_005f22, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5095 */           return true;
/* 5096 */         out.write(32);
/* 5097 */         int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 5098 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5102 */     if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 5103 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 5104 */       return true;
/*      */     }
/* 5106 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 5107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5112 */     PageContext pageContext = _jspx_page_context;
/* 5113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5115 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5116 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 5117 */     _jspx_th_c_005fif_005f23.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 5119 */     _jspx_th_c_005fif_005f23.setTest("${status.count %2 == 1}");
/* 5120 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 5121 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 5123 */         out.write("\n  <tr class=\"oddrowbgcolor\" > ");
/* 5124 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 5125 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5129 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 5130 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 5131 */       return true;
/*      */     }
/* 5133 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 5134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f24(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5139 */     PageContext pageContext = _jspx_page_context;
/* 5140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5142 */     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5143 */     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 5144 */     _jspx_th_c_005fif_005f24.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 5146 */     _jspx_th_c_005fif_005f24.setTest("${status.count %2 == 0}");
/* 5147 */     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 5148 */     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */       for (;;) {
/* 5150 */         out.write(" \n  <tr class=\"evenrowbgcolor\" > ");
/* 5151 */         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 5152 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5156 */     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 5157 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 5158 */       return true;
/*      */     }
/* 5160 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 5161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f25(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5166 */     PageContext pageContext = _jspx_page_context;
/* 5167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5169 */     IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5170 */     _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 5171 */     _jspx_th_c_005fif_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5173 */     _jspx_th_c_005fif_005f25.setTest("${row.health == 1}");
/* 5174 */     int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 5175 */     if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */       for (;;) {
/* 5177 */         out.write(" \n  <tr  > ");
/* 5178 */         int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 5179 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5183 */     if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 5184 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 5185 */       return true;
/*      */     }
/* 5187 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 5188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5193 */     PageContext pageContext = _jspx_page_context;
/* 5194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5196 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5197 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5198 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5200 */     _jspx_th_c_005fout_005f29.setValue("${row.Name}");
/* 5201 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5202 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5204 */       return true;
/*      */     }
/* 5206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5212 */     PageContext pageContext = _jspx_page_context;
/* 5213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5215 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5216 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5217 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5219 */     _jspx_th_c_005fout_005f30.setValue("${row.ID}");
/* 5220 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5221 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5222 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5223 */       return true;
/*      */     }
/* 5225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f0(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5231 */     PageContext pageContext = _jspx_page_context;
/* 5232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5234 */     FormatTag _jspx_th_am_005fFormat_005f0 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 5235 */     _jspx_th_am_005fFormat_005f0.setPageContext(_jspx_page_context);
/* 5236 */     _jspx_th_am_005fFormat_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5238 */     _jspx_th_am_005fFormat_005f0.setType("Number");
/* 5239 */     int _jspx_eval_am_005fFormat_005f0 = _jspx_th_am_005fFormat_005f0.doStartTag();
/* 5240 */     if (_jspx_eval_am_005fFormat_005f0 != 0) {
/* 5241 */       if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 5242 */         out = _jspx_page_context.pushBody();
/* 5243 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 5244 */         _jspx_th_am_005fFormat_005f0.setBodyContent((BodyContent)out);
/* 5245 */         _jspx_th_am_005fFormat_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5248 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_am_005fFormat_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5249 */           return true;
/* 5250 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f0.doAfterBody();
/* 5251 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5254 */       if (_jspx_eval_am_005fFormat_005f0 != 1) {
/* 5255 */         out = _jspx_page_context.popBody();
/* 5256 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 5259 */     if (_jspx_th_am_005fFormat_005f0.doEndTag() == 5) {
/* 5260 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 5261 */       return true;
/*      */     }
/* 5263 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f0);
/* 5264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_am_005fFormat_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5269 */     PageContext pageContext = _jspx_page_context;
/* 5270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5272 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5273 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5274 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_am_005fFormat_005f0);
/*      */     
/* 5276 */     _jspx_th_c_005fout_005f31.setValue("${row.execHigh}");
/* 5277 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5278 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5280 */       return true;
/*      */     }
/* 5282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f1(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5288 */     PageContext pageContext = _jspx_page_context;
/* 5289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5291 */     FormatTag _jspx_th_am_005fFormat_005f1 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 5292 */     _jspx_th_am_005fFormat_005f1.setPageContext(_jspx_page_context);
/* 5293 */     _jspx_th_am_005fFormat_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5295 */     _jspx_th_am_005fFormat_005f1.setType("Number");
/* 5296 */     int _jspx_eval_am_005fFormat_005f1 = _jspx_th_am_005fFormat_005f1.doStartTag();
/* 5297 */     if (_jspx_eval_am_005fFormat_005f1 != 0) {
/* 5298 */       if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 5299 */         out = _jspx_page_context.pushBody();
/* 5300 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 5301 */         _jspx_th_am_005fFormat_005f1.setBodyContent((BodyContent)out);
/* 5302 */         _jspx_th_am_005fFormat_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5305 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_am_005fFormat_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5306 */           return true;
/* 5307 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f1.doAfterBody();
/* 5308 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5311 */       if (_jspx_eval_am_005fFormat_005f1 != 1) {
/* 5312 */         out = _jspx_page_context.popBody();
/* 5313 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 5316 */     if (_jspx_th_am_005fFormat_005f1.doEndTag() == 5) {
/* 5317 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 5318 */       return true;
/*      */     }
/* 5320 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f1);
/* 5321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_am_005fFormat_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5326 */     PageContext pageContext = _jspx_page_context;
/* 5327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5329 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5330 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 5331 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_am_005fFormat_005f1);
/*      */     
/* 5333 */     _jspx_th_c_005fout_005f32.setValue("${row.execLow}");
/* 5334 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 5335 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 5336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5337 */       return true;
/*      */     }
/* 5339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 5340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f2(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5345 */     PageContext pageContext = _jspx_page_context;
/* 5346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5348 */     FormatTag _jspx_th_am_005fFormat_005f2 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 5349 */     _jspx_th_am_005fFormat_005f2.setPageContext(_jspx_page_context);
/* 5350 */     _jspx_th_am_005fFormat_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5352 */     _jspx_th_am_005fFormat_005f2.setType("Number");
/* 5353 */     int _jspx_eval_am_005fFormat_005f2 = _jspx_th_am_005fFormat_005f2.doStartTag();
/* 5354 */     if (_jspx_eval_am_005fFormat_005f2 != 0) {
/* 5355 */       if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 5356 */         out = _jspx_page_context.pushBody();
/* 5357 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 5358 */         _jspx_th_am_005fFormat_005f2.setBodyContent((BodyContent)out);
/* 5359 */         _jspx_th_am_005fFormat_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5362 */         if (_jspx_meth_c_005fout_005f33(_jspx_th_am_005fFormat_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5363 */           return true;
/* 5364 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f2.doAfterBody();
/* 5365 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5368 */       if (_jspx_eval_am_005fFormat_005f2 != 1) {
/* 5369 */         out = _jspx_page_context.popBody();
/* 5370 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 5373 */     if (_jspx_th_am_005fFormat_005f2.doEndTag() == 5) {
/* 5374 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 5375 */       return true;
/*      */     }
/* 5377 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f2);
/* 5378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_am_005fFormat_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5383 */     PageContext pageContext = _jspx_page_context;
/* 5384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5386 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5387 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 5388 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_am_005fFormat_005f2);
/*      */     
/* 5390 */     _jspx_th_c_005fout_005f33.setValue("${row.execAvg}");
/* 5391 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 5392 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 5393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5394 */       return true;
/*      */     }
/* 5396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 5397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fFormat_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5402 */     PageContext pageContext = _jspx_page_context;
/* 5403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5405 */     FormatTag _jspx_th_am_005fFormat_005f3 = (FormatTag)this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.get(FormatTag.class);
/* 5406 */     _jspx_th_am_005fFormat_005f3.setPageContext(_jspx_page_context);
/* 5407 */     _jspx_th_am_005fFormat_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5409 */     _jspx_th_am_005fFormat_005f3.setType("Number");
/* 5410 */     int _jspx_eval_am_005fFormat_005f3 = _jspx_th_am_005fFormat_005f3.doStartTag();
/* 5411 */     if (_jspx_eval_am_005fFormat_005f3 != 0) {
/* 5412 */       if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 5413 */         out = _jspx_page_context.pushBody();
/* 5414 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 5415 */         _jspx_th_am_005fFormat_005f3.setBodyContent((BodyContent)out);
/* 5416 */         _jspx_th_am_005fFormat_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5419 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_am_005fFormat_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 5420 */           return true;
/* 5421 */         int evalDoAfterBody = _jspx_th_am_005fFormat_005f3.doAfterBody();
/* 5422 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5425 */       if (_jspx_eval_am_005fFormat_005f3 != 1) {
/* 5426 */         out = _jspx_page_context.popBody();
/* 5427 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 5430 */     if (_jspx_th_am_005fFormat_005f3.doEndTag() == 5) {
/* 5431 */       this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 5432 */       return true;
/*      */     }
/* 5434 */     this._005fjspx_005ftagPool_005fam_005fFormat_0026_005ftype.reuse(_jspx_th_am_005fFormat_005f3);
/* 5435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_am_005fFormat_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5440 */     PageContext pageContext = _jspx_page_context;
/* 5441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5443 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5444 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 5445 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_am_005fFormat_005f3);
/*      */     
/* 5447 */     _jspx_th_c_005fout_005f34.setValue("${row.invocationTotal}");
/* 5448 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 5449 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 5450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5451 */       return true;
/*      */     }
/* 5453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 5454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 5459 */     PageContext pageContext = _jspx_page_context;
/* 5460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5462 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5463 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 5464 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 5466 */     _jspx_th_c_005fout_005f35.setValue("${row.ID}");
/* 5467 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 5468 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 5469 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5470 */       return true;
/*      */     }
/* 5472 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 5473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f6(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5478 */     PageContext pageContext = _jspx_page_context;
/* 5479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5481 */     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5482 */     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 5483 */     _jspx_th_tiles_005fput_005f6.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5485 */     _jspx_th_tiles_005fput_005f6.setName("footer");
/*      */     
/* 5487 */     _jspx_th_tiles_005fput_005f6.setValue("/jsp/footer.jsp");
/* 5488 */     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 5489 */     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 5490 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 5491 */       return true;
/*      */     }
/* 5493 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 5494 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ShowWlogicServletData_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */