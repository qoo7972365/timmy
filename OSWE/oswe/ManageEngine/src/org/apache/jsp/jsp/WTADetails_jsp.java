/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
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
/*      */ public final class WTADetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*   74 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/* 2183 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2189 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2190 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/includes/WTALeftArea.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2193 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2226 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2256 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2261 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2265 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2272 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2275 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2276 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2277 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2278 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2279 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/* 2283 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2284 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2291 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2294 */     JspWriter out = null;
/* 2295 */     Object page = this;
/* 2296 */     JspWriter _jspx_out = null;
/* 2297 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2301 */       response.setContentType("text/html;charset=UTF-8");
/* 2302 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2304 */       _jspx_page_context = pageContext;
/* 2305 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2306 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2307 */       session = pageContext.getSession();
/* 2308 */       out = pageContext.getOut();
/* 2309 */       _jspx_out = out;
/*      */       
/* 2311 */       out.write("<!DOCTYPE html>\n");
/* 2312 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/* 2314 */       request.setAttribute("HelpKey", "Web Transaction Monitor");
/*      */       
/* 2316 */       out.write("\n\n\n\n\n\n\n\n\n\n  \n\n");
/* 2317 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2319 */       out.write(10);
/*      */       
/* 2321 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2322 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2323 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2325 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/WTAServerLayout.jsp");
/* 2326 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2327 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2329 */           out.write(10);
/* 2330 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2332 */           out.write(10);
/* 2333 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2335 */           out.write(32);
/* 2336 */           out.write(32);
/* 2337 */           out.write(10);
/* 2338 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2340 */           out.write(10);
/*      */           
/* 2342 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2343 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2344 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2346 */           _jspx_th_tiles_005fput_005f3.setName("ServerLeftArea");
/*      */           
/* 2348 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2349 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2350 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2351 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2352 */               out = _jspx_page_context.pushBody();
/* 2353 */               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2354 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2357 */               out.write(10);
/* 2358 */               out.write("<!--$Id$-->\n\n<script>\n");
/*      */               
/* 2360 */               if (request.getParameter("editPage") != null)
/*      */               {
/* 2362 */                 out.write("\n   toggleWTADiv('edit');\n");
/*      */               }
/*      */               
/* 2365 */               out.write("\nfunction reconfigure()\n{\n\ttoggleDiv('Reconfigure');\n\t//hideDiv('snapshot');\n}\n</script>\n");
/*      */               
/* 2367 */               String resourceid = request.getParameter("resourceid");
/* 2368 */               String wtaresourceid = (String)request.getAttribute("wtaresourceid");
/* 2369 */               String wtaresourcename = (String)request.getAttribute("wtaresourcename");
/*      */               
/* 2371 */               out.write("\n\n<script language=\"JavaScript\">\n\n\tfunction confirmDelete()\n \t {\n\t");
/* 2372 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2374 */               out.write(10);
/* 2375 */               out.write(9);
/*      */               
/* 2377 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2378 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2379 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2381 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2382 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2383 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2385 */                   out.write("\n        var s = confirm(\"");
/* 2386 */                   out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2387 */                   out.write("\");\n        if (s)\n        document.location.href=\"/deleteMO.do?method=deleteMO&type=WTA&select=");
/* 2388 */                   out.print(resourceid);
/* 2389 */                   out.write("\";\n\t");
/* 2390 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2391 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2395 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2396 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2399 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2400 */               out.write("\n\t }\n\t  function confirmManage()\n\t  \t {\n\n\t \t\t ");
/* 2401 */               if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2403 */               out.write("\n\n\t   var s = confirm(\"");
/* 2404 */               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2405 */               out.write("\");\n\t   if (s)\n\t  \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2406 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 2408 */               out.write("\";\n\t \t }\n\n\n</script>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"   class=\"leftmnutables\">\n\n  <tr>\n    <td class=\"leftlinksheading\">");
/* 2409 */               out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2410 */               out.write("</td>\n  </tr>\n\n<!-- Edit link should be enabled for ADMIN and DEMO Users alone -->\n");
/*      */               
/* 2412 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2413 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2414 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2416 */               _jspx_th_logic_005fpresent_005f2.setRole("ENTERPRISEADMIN");
/* 2417 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2418 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2420 */                   out.write("\n <tr>\n  <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2421 */                   out.print(request.getParameter("resourceid"));
/* 2422 */                   out.write("&type=");
/* 2423 */                   out.print(request.getParameter("type"));
/* 2424 */                   out.write("&moname=");
/* 2425 */                   out.print(request.getParameter("moname"));
/* 2426 */                   out.write("&method=showdetails&resourcename=");
/* 2427 */                   out.print(request.getParameter("resourcename"));
/* 2428 */                   out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n");
/* 2429 */                   out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2430 */                   out.write("</a> </td>\n </tr>\n");
/* 2431 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2432 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2436 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2437 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/* 2440 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2441 */               out.write(10);
/* 2442 */               out.write(10);
/*      */               
/* 2444 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2445 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2446 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2448 */               _jspx_th_c_005fif_005f2.setTest("${!empty ADMIN || !empty DEMO}");
/* 2449 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2450 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 2452 */                   out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n ");
/*      */                   
/* 2454 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2455 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2456 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f2);
/* 2457 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2458 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 2460 */                       out.write("\n    ");
/*      */                       
/* 2462 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2463 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2464 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 2466 */                       _jspx_th_c_005fwhen_005f0.setTest("${uri=='/reconfigure.do'}");
/* 2467 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2468 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 2470 */                           out.write("\n        ");
/* 2471 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2472 */                           out.write(10);
/* 2473 */                           out.write(32);
/* 2474 */                           out.write(32);
/* 2475 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2476 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2480 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2481 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 2484 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2485 */                       out.write(10);
/* 2486 */                       out.write(32);
/* 2487 */                       out.write(32);
/*      */                       
/* 2489 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2490 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2491 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2492 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2493 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/* 2495 */                           out.write("\n    <a href=\"javascript:toggleWTADiv('edit');\"  class=\"new-left-links\">");
/* 2496 */                           out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2497 */                           out.write("</a>\n  ");
/* 2498 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2499 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2503 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2504 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/* 2507 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2508 */                       out.write(10);
/* 2509 */                       out.write(32);
/* 2510 */                       out.write(32);
/* 2511 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2512 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2516 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2517 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 2520 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2521 */                   out.write("\n  </td>\n  </tr>\n");
/* 2522 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2523 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2527 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2528 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 2531 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2532 */               out.write(10);
/* 2533 */               out.write(10);
/*      */               
/* 2535 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2536 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2537 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2539 */               _jspx_th_c_005fif_005f3.setTest("${!empty ADMIN || !empty DEMO}");
/* 2540 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2541 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2543 */                   out.write("\n  <tr class=\"RCAHeader\">\n    <td class=\"leftlinkstd\" >\n     <A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2544 */                   out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2545 */                   out.write("</A>\n  \t</td>\n  </tr>\n  <tr class=\"RCAHeader\" onmouseover=\"this.className='RCAHeaderHover'\" onmouseout=\"this.className='RCAHeader'\">\n  <td style=\"font-family:Arial, Helvetica, sans-serif; height:26px;\">\n  ");
/*      */                   
/* 2547 */                   FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2548 */                   _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2549 */                   _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                   
/* 2551 */                   _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                   
/* 2553 */                   _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                   
/* 2555 */                   _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2556 */                   int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2557 */                   if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                     for (;;) {
/* 2559 */                       out.write("\n  <input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2560 */                       out.print(request.getParameter("name"));
/* 2561 */                       out.write("\">\n  <input type=\"hidden\" name=\"type\" value=\"");
/* 2562 */                       if (_jspx_meth_c_005fout_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2564 */                       out.write("\">\n  <input type=\"hidden\" name=\"method\" value=\"configureWTA\">\n  <input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2565 */                       if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2567 */                       out.write("\">\n  <input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2568 */                       if (_jspx_meth_c_005fout_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2570 */                       out.write("\">\n  <input type=\"hidden\" name=\"moname\" value=\"");
/* 2571 */                       if (_jspx_meth_c_005fout_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2573 */                       out.write("\">\n  <input type=\"hidden\" name=\"displayname\" value=\"");
/* 2574 */                       if (_jspx_meth_c_005fout_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2576 */                       out.write("\">\n  <input type=\"hidden\" name=\"pollInterval\" value=\"");
/* 2577 */                       if (_jspx_meth_c_005fout_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2579 */                       out.write("\">\n  <input type=\"hidden\" name=\"instrumentationEnabled\" value=\"");
/* 2580 */                       if (_jspx_meth_c_005fout_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2582 */                       out.write("\">\n  <input type=\"hidden\" name=\"samplingFactor\" value=\"");
/* 2583 */                       if (_jspx_meth_c_005fout_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2585 */                       out.write("\">\n  <input type=\"hidden\" name=\"maxURL\" value=\"");
/* 2586 */                       if (_jspx_meth_c_005fout_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2588 */                       out.write("\">\n  <input type=\"hidden\" name=\"packageInclude\" value=\"");
/* 2589 */                       if (_jspx_meth_c_005fout_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2591 */                       out.write("\">\n  <input type=\"hidden\" name=\"packageList\" value=\"");
/* 2592 */                       if (_jspx_meth_c_005fout_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2594 */                       out.write("\">\n  <input type=\"hidden\" name=\"tracingEnabled\" value=\"");
/* 2595 */                       if (_jspx_meth_c_005fout_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2597 */                       out.write("\">\n  <input type=\"hidden\" name=\"maxDepth\" value=\"");
/* 2598 */                       if (_jspx_meth_c_005fout_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2600 */                       out.write("\">\n  <input type=\"hidden\" name=\"maxChildren\" value=\"");
/* 2601 */                       if (_jspx_meth_c_005fout_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2603 */                       out.write("\">\n  <!--input type=\"hidden\" name=\"fullTraceCount\" value=\"");
/* 2604 */                       if (_jspx_meth_c_005fout_005f15(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 2606 */                       out.write("\"-->\n  ");
/*      */                       
/* 2608 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2609 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2610 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2612 */                       _jspx_th_c_005fif_005f4.setTest("${AMActionForm.instrumentationEnabled}");
/* 2613 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2614 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 2616 */                           out.write("\n    <a  href=\"#\" onclick=\"javascript:submitForm2(this)\" class=\"bodytext\" style=\"text-indent:8px; \"><span class=\"bodytext\" style=\"color:#595959;\">");
/* 2617 */                           out.print(FormatUtil.getString("am.webclient.wta.disable"));
/* 2618 */                           out.write("</span></a>\n  ");
/* 2619 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2620 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2624 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2625 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 2628 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2629 */                       out.write(10);
/* 2630 */                       out.write(32);
/* 2631 */                       out.write(32);
/*      */                       
/* 2633 */                       IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2634 */                       _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2635 */                       _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2637 */                       _jspx_th_c_005fif_005f5.setTest("${!AMActionForm.instrumentationEnabled}");
/* 2638 */                       int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2639 */                       if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                         for (;;) {
/* 2641 */                           out.write("\n    <a  href=\"#\" onclick=\"javascript:submitForm2(this)\" class=\"bodytext\" style=\"text-indent:8px;\"><span class=\"bodytext\" style=\"color:#595959;\">");
/* 2642 */                           out.print(FormatUtil.getString("am.webclient.wta.enable"));
/* 2643 */                           out.write("</span></a>\n  ");
/* 2644 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2645 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2649 */                       if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2650 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                       }
/*      */                       
/* 2653 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2654 */                       out.write("\n    ");
/* 2655 */                       String help = FormatUtil.getString("am.webclient.wta.configurationdetails.enabledisablemonitor.help");
/* 2656 */                       out.write("\n&nbsp;&nbsp;&nbsp;<img onMouseOver=\"ddrivetip(this,event,'<span class=bodytext>");
/* 2657 */                       out.print(help);
/* 2658 */                       out.write("</span>',false,true,'#000000',200,'lightyellow')\" src=\"/images/icon_quicknote_help.gif\" onMouseOut=\"hideddrivetip()\"/>");
/* 2659 */                       out.write(10);
/* 2660 */                       out.write(10);
/* 2661 */                       out.write(10);
/* 2662 */                       int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2663 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2667 */                   if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2668 */                     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                   }
/*      */                   
/* 2671 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2672 */                   out.write("\n  </td>\n  </tr>\n  ");
/* 2673 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2674 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2678 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2679 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2682 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2683 */               out.write(10);
/*      */               
/* 2685 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2686 */               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2687 */               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2689 */               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2690 */               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2691 */               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                 for (;;) {
/* 2693 */                   out.write(10);
/* 2694 */                   out.write(32);
/* 2695 */                   out.write(32);
/*      */                   
/* 2697 */                   String resourceid_poll = request.getParameter("resourceid");
/* 2698 */                   String resourcetype_poll = request.getParameter("type");
/*      */                   
/* 2700 */                   out.write("\n    <tr>\n    <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n    <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 2701 */                   out.print(resourceid_poll);
/* 2702 */                   out.write("&resourcetype=");
/* 2703 */                   out.print(resourcetype_poll);
/* 2704 */                   out.write("\" class=\"new-left-links\"> ");
/* 2705 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2706 */                   out.write("</a></td>\n  </tr>\n  ");
/* 2707 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2708 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2712 */               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2713 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */               }
/*      */               
/* 2716 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2717 */               out.write(10);
/* 2718 */               out.write(32);
/* 2719 */               out.write(32);
/*      */               
/* 2721 */               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2722 */               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2723 */               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2725 */               _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 2726 */               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2727 */               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                 for (;;) {
/* 2729 */                   out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 2730 */                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 2731 */                   out.write("</a></td>\n        </td>\n    ");
/* 2732 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2733 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2737 */               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2738 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */               }
/*      */               
/* 2741 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2742 */               out.write("\n    ");
/* 2743 */               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */               
/* 2745 */               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */               {
/* 2747 */                 Map<com.me.apm.cmdb.APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 2748 */                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                 
/* 2750 */                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 2751 */                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 2752 */                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                 {
/* 2754 */                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                   {
/*      */ 
/* 2757 */                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2758 */                     out.print(ciInfoUrl);
/* 2759 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2760 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2761 */                     out.write("</a></td>");
/* 2762 */                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2763 */                     out.print(ciRLUrl);
/* 2764 */                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 2765 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2766 */                     out.write("</a></td>");
/* 2767 */                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                   }
/* 2771 */                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                   {
/*      */ 
/* 2774 */                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 2775 */                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 2776 */                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2777 */                     out.print(ciInfoUrl);
/* 2778 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 2779 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 2780 */                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 2781 */                     out.print(ciRLUrl);
/* 2782 */                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 2783 */                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 2784 */                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/* 2789 */               out.write("\n \n \n\n");
/* 2790 */               out.write("\n  </table>\n\n\n\n");
/* 2791 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */               
/*      */ 
/*      */ 
/* 2795 */               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 2796 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */               {
/* 2798 */                 showAssociatedBSG = false;
/*      */                 
/*      */ 
/*      */ 
/* 2802 */                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 2803 */                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 2804 */                 String loginName = request.getUserPrincipal().getName();
/* 2805 */                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                 
/* 2807 */                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                 {
/* 2809 */                   showAssociatedBSG = true;
/*      */                 }
/*      */               }
/* 2812 */               String monitorType = request.getParameter("type");
/* 2813 */               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 2814 */               boolean mon = conf1.isConfMonitor(monitorType);
/* 2815 */               if (showAssociatedBSG)
/*      */               {
/* 2817 */                 Hashtable associatedmgs = new Hashtable();
/* 2818 */                 String resId = request.getParameter("resourceid");
/* 2819 */                 request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 2820 */                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                 {
/* 2822 */                   mon = false;
/*      */                 }
/*      */                 
/* 2825 */                 if (!mon)
/*      */                 {
/* 2827 */                   out.write(10);
/* 2828 */                   out.write(10);
/*      */                   
/* 2830 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2831 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2832 */                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2834 */                   _jspx_th_c_005fif_005f6.setTest("${!empty associatedmgs}");
/* 2835 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2836 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                     for (;;) {
/* 2838 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 2839 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 2840 */                       out.write("</td>\n        </tr>\n        ");
/*      */                       
/* 2842 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2843 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2844 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                       
/* 2846 */                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                       
/* 2848 */                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                       
/* 2850 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 2851 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 2853 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2854 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 2856 */                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 2857 */                             if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2915 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2859 */                             out.write("&method=showApplication\" title=\"");
/* 2860 */                             if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2915 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2862 */                             out.write("\"  class=\"new-left-links\">\n         ");
/* 2863 */                             if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2915 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2865 */                             out.write("\n    \t");
/* 2866 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 2867 */                             out.write("\n         </a></td>\n        <td>");
/*      */                             
/* 2869 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2870 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2871 */                             _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 2873 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN");
/* 2874 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2875 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 2877 */                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 2878 */                                 if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fpresent_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2915 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2880 */                                 out.write(39);
/* 2881 */                                 out.write(44);
/* 2882 */                                 out.write(39);
/* 2883 */                                 out.print(resId);
/* 2884 */                                 out.write(39);
/* 2885 */                                 out.write(44);
/* 2886 */                                 out.write(39);
/* 2887 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 2888 */                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 2889 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 2890 */                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 2891 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2892 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2896 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2897 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2915 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2900 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2901 */                             out.write("</td>\n        </tr>\n\t");
/* 2902 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2903 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2907 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2915 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 2911 */                           int tmp4162_4161 = 0; int[] tmp4162_4159 = _jspx_push_body_count_c_005fforEach_005f0; int tmp4164_4163 = tmp4162_4159[tmp4162_4161];tmp4162_4159[tmp4162_4161] = (tmp4164_4163 - 1); if (tmp4164_4163 <= 0) break;
/* 2912 */                           out = _jspx_page_context.popBody(); }
/* 2913 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 2915 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 2916 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 2918 */                       out.write("\n      </table>\n ");
/* 2919 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2920 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2924 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2925 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                   }
/*      */                   
/* 2928 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2929 */                   out.write(10);
/* 2930 */                   out.write(32);
/*      */                   
/* 2932 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2933 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2934 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2936 */                   _jspx_th_c_005fif_005f7.setTest("${empty associatedmgs}");
/* 2937 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2938 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 2940 */                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 2941 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 2942 */                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 2943 */                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 2944 */                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 2945 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2946 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2950 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2951 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 2954 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2955 */                   out.write(10);
/* 2956 */                   out.write(32);
/* 2957 */                   out.write(10);
/*      */ 
/*      */                 }
/* 2960 */                 else if (mon)
/*      */                 {
/*      */ 
/*      */ 
/* 2964 */                   out.write(10);
/*      */                   
/* 2966 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2967 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2968 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2970 */                   _jspx_th_c_005fif_005f8.setTest("${!empty associatedmgs}");
/* 2971 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2972 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 2974 */                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 2975 */                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                         return;
/* 2977 */                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                       
/* 2979 */                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2980 */                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 2981 */                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 2983 */                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                       
/* 2985 */                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                       
/* 2987 */                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 2988 */                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                       try {
/* 2990 */                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 2991 */                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                           for (;;) {
/* 2993 */                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 2994 */                             if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3055 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 2996 */                             out.write("&method=showApplication\" title=\"");
/* 2997 */                             if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3055 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 2999 */                             out.write("\"  class=\"staticlinks\">\n         ");
/* 3000 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3055 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3002 */                             out.write("\n    \t");
/* 3003 */                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3004 */                             out.write("</a></span>\t\n\t\t ");
/*      */                             
/* 3006 */                             PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3007 */                             _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3008 */                             _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                             
/* 3010 */                             _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 3011 */                             int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3012 */                             if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                               for (;;) {
/* 3014 */                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3015 */                                 if (_jspx_meth_c_005fout_005f23(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3055 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3017 */                                 out.write(39);
/* 3018 */                                 out.write(44);
/* 3019 */                                 out.write(39);
/* 3020 */                                 out.print(resId);
/* 3021 */                                 out.write(39);
/* 3022 */                                 out.write(44);
/* 3023 */                                 out.write(39);
/* 3024 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3025 */                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3026 */                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3027 */                                 out.write("\"  title=\"");
/* 3028 */                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 3055 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                 }
/* 3030 */                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3031 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3032 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3036 */                             if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3037 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3055 */                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                             }
/* 3040 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3041 */                             out.write("\n\n\t\t \t");
/* 3042 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3043 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3047 */                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3055 */                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 3051 */                           int tmp5188_5187 = 0; int[] tmp5188_5185 = _jspx_push_body_count_c_005fforEach_005f1; int tmp5190_5189 = tmp5188_5185[tmp5188_5187];tmp5188_5185[tmp5188_5187] = (tmp5190_5189 - 1); if (tmp5190_5189 <= 0) break;
/* 3052 */                           out = _jspx_page_context.popBody(); }
/* 3053 */                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                       } finally {
/* 3055 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 3056 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                       }
/* 3058 */                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3059 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3060 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3064 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3065 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 3068 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3069 */                   out.write(10);
/* 3070 */                   out.write(32);
/* 3071 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 3073 */                   out.write(32);
/* 3074 */                   out.write(10);
/*      */                 }
/*      */                 
/*      */               }
/* 3078 */               else if (mon)
/*      */               {
/*      */ 
/* 3081 */                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 3082 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3084 */                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */               }
/*      */               
/*      */ 
/* 3088 */               out.write(9);
/* 3089 */               out.write(9);
/* 3090 */               out.write("\n</td>\n</tr>\n</table>\n\n\n");
/* 3091 */               out.write(10);
/* 3092 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3093 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3096 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3097 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3100 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3101 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3104 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3105 */           out.write(10);
/* 3106 */           out.write(10);
/* 3107 */           out.write(10);
/*      */           
/* 3109 */           PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3110 */           _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3111 */           _jspx_th_tiles_005fput_005f4.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3113 */           _jspx_th_tiles_005fput_005f4.setName("Summary");
/*      */           
/* 3115 */           _jspx_th_tiles_005fput_005f4.setType("string");
/* 3116 */           int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3117 */           if (_jspx_eval_tiles_005fput_005f4 != 0) {
/* 3118 */             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3119 */               out = _jspx_page_context.pushBody();
/* 3120 */               _jspx_th_tiles_005fput_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3121 */               _jspx_th_tiles_005fput_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3124 */               out.write(10);
/* 3125 */               out.write(10);
/* 3126 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 3128 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3129 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 3130 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3132 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 3134 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 3136 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 3138 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 3139 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 3140 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 3141 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 3144 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 3145 */               String available = null;
/* 3146 */               available = (String)_jspx_page_context.findAttribute("available");
/* 3147 */               out.write(10);
/*      */               
/* 3149 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3150 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 3151 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3153 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 3155 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 3157 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 3159 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 3160 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 3161 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 3162 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 3165 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 3166 */               String unavailable = null;
/* 3167 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 3168 */               out.write(10);
/*      */               
/* 3170 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3171 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 3172 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3174 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 3176 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 3178 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 3180 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 3181 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 3182 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 3183 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 3186 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 3187 */               String unmanaged = null;
/* 3188 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 3189 */               out.write(10);
/*      */               
/* 3191 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3192 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 3193 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3195 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 3197 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 3199 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 3201 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 3202 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 3203 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 3204 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 3207 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 3208 */               String scheduled = null;
/* 3209 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 3210 */               out.write(10);
/*      */               
/* 3212 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3213 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 3214 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3216 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 3218 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 3220 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 3222 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 3223 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 3224 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 3225 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 3228 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 3229 */               String critical = null;
/* 3230 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 3231 */               out.write(10);
/*      */               
/* 3233 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3234 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 3235 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3237 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 3239 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 3241 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 3243 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 3244 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 3245 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 3246 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 3249 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 3250 */               String clear = null;
/* 3251 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 3252 */               out.write(10);
/*      */               
/* 3254 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 3255 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 3256 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3258 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 3260 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 3262 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 3264 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 3265 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 3266 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 3267 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 3270 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 3271 */               String warning = null;
/* 3272 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 3273 */               out.write(10);
/* 3274 */               out.write(10);
/*      */               
/* 3276 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 3277 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 3279 */               out.write(10);
/* 3280 */               out.write(10);
/* 3281 */               out.write(10);
/* 3282 */               out.write(10);
/* 3283 */               out.write(10);
/*      */               
/* 3285 */               com.adventnet.appmanager.struts.actions.AdminActions am = new com.adventnet.appmanager.struts.actions.AdminActions();
/* 3286 */               int days = am.daysRemainingForCustomer();
/* 3287 */               String trialmessage = null;
/* 3288 */               if (days < 30)
/*      */               {
/* 3290 */                 trialmessage = FormatUtil.getString("am.webclient.wta.trialperiodmessageforcustomer.text", new String[] { FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction"), String.valueOf(30 - days) });
/*      */               }
/*      */               else
/*      */               {
/* 3294 */                 trialmessage = FormatUtil.getString("am.webclient.net.trialperiodmessageforcustomer.today.text", new String[] { FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction") });
/*      */               }
/*      */               
/* 3297 */               HashMap systeminfo = (HashMap)request.getAttribute("wtasysteminfo");
/* 3298 */               Properties systemprops = (Properties)request.getAttribute("systemprops");
/* 3299 */               String host = systemprops.getProperty("serverName");
/* 3300 */               String port = systemprops.getProperty("port");
/* 3301 */               String count = systemprops.getProperty("count");
/* 3302 */               String errorcount = systemprops.getProperty("errorcount");
/*      */               
/* 3304 */               String resourceid = request.getParameter("resourceid");
/* 3305 */               ArrayList attribIDs = new ArrayList();
/* 3306 */               ArrayList resIDs = new ArrayList();
/* 3307 */               resIDs.add(resourceid);
/* 3308 */               attribIDs.add("8000");
/* 3309 */               attribIDs.add("8001");
/* 3310 */               Properties alert = getStatus(resIDs, attribIDs);
/*      */               
/*      */ 
/*      */ 
/* 3314 */               out.write("\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n\n");
/*      */               
/* 3316 */               String type = request.getParameter("type");
/* 3317 */               String pollInterval = request.getParameter("pollInterval");
/* 3318 */               if (pollInterval == null) {
/* 3319 */                 pollInterval = "5";
/*      */               }
/*      */               
/*      */ 
/* 3323 */               out.write("\n\n  ");
/*      */               
/* 3325 */               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 3326 */               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 3327 */               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3329 */               _jspx_th_html_005fform_005f1.setAction("/adminAction");
/*      */               
/* 3331 */               _jspx_th_html_005fform_005f1.setMethod("get");
/*      */               
/* 3333 */               _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/* 3334 */               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 3335 */               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                 for (;;) {
/* 3337 */                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n    <td height=\"28\"   class=\"tableheading\">");
/* 3338 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails"));
/* 3339 */                   out.write("\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 3340 */                   out.print(request.getParameter("name"));
/* 3341 */                   out.write("\">\n<!--input type=\"hidden\" name=\"haid\" value=\"");
/* 3342 */                   out.print(request.getParameter("haid"));
/* 3343 */                   out.write("\"-->\n<!--input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 3344 */                   out.print(type);
/* 3345 */                   out.write("\"-->\n<input type=\"hidden\" name=\"type\" value=\"");
/* 3346 */                   out.print(type);
/* 3347 */                   out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureWTA\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 3348 */                   out.print(request.getParameter("resourceid"));
/* 3349 */                   out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 3350 */                   out.print(request.getParameter("resourcename"));
/* 3351 */                   out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 3352 */                   out.print(request.getParameter("moname"));
/* 3353 */                   out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:toggleWTADiv('edit')\" >\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">Hide</span>\n</td>\n</tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3354 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.displayname"));
/* 3355 */                   out.write("<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 3356 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3358 */                   out.write("\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3359 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval"));
/* 3360 */                   out.write(" <span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 3361 */                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3363 */                   out.write("<span class=\"bodytext\">&nbsp;");
/* 3364 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.pollinginterval.units"));
/* 3365 */                   out.write("</span>\n\t</TD>\n\t</TR>\n    <tr style=\"background-color:#eeeeee\"><td colspan=\"2\"><span class=\"bodytext\"><b>");
/* 3366 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.agentsettings"));
/* 3367 */                   out.write("</b></span></td></tr>\n    <tr>\n    <TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3368 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.enablemonitor"));
/* 3369 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 3370 */                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3372 */                   out.write(" &nbsp;&nbsp;&nbsp;\n        ");
/* 3373 */                   String help = FormatUtil.getString("am.webclient.wta.configurationdetails.enablemonitor.help");
/* 3374 */                   out.write("\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3375 */                   out.print(help);
/* 3376 */                   out.write("</span>')\"/>\n\t</TD>\n    </tr>\n    <tr>\n    <td colspan=\"2\">\n    ");
/* 3377 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3379 */                   out.write("\n    ");
/* 3380 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3382 */                   out.write("\n    <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\">\n    <TR>\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3383 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.samplingfactor"));
/* 3384 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 3385 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.samplingfactor.help");
/* 3386 */                   out.write("\n        ");
/* 3387 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3389 */                   out.write("&nbsp;&nbsp;&nbsp;\n        <img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3390 */                   out.print(help);
/* 3391 */                   out.write("</span>')\"/>\n\t</TD>\n\t</TR>\n    <tr>\n    <TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3392 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.maxurl"));
/* 3393 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"77%\">\n    ");
/* 3394 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.maxurl.help");
/* 3395 */                   out.write(10);
/* 3396 */                   out.write(9);
/* 3397 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3399 */                   out.write("&nbsp;&nbsp;&nbsp;\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3400 */                   out.print(help);
/* 3401 */                   out.write("</span>')\"/>\n\t</TD>\n\t</TR>\n    <tr>\n    <td colspan=\"2\" class=\"bodytext\">\n    <table cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tr>\n    <td width=\"23%\" class=\"bodytext\" style=\"vertical-align:middle;\">");
/* 3402 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.packagelist"));
/* 3403 */                   out.write("</td>\n    <td style=\"width:10px\">");
/* 3404 */                   if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3406 */                   out.write("</td>\n    <td width=\"10%\" class=\"bodytext\" style=\"vertical-align:middle;\">");
/* 3407 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.packagelist.include"));
/* 3408 */                   out.write("</td>    \n    <td style=\"width:10px\">");
/* 3409 */                   if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3411 */                   out.write("</td>\n    <td class=\"bodytext\" style=\"vertical-align:middle;\">");
/* 3412 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.packagelist.exclude"));
/* 3413 */                   out.write(" &nbsp;&nbsp;&nbsp;\n        ");
/* 3414 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.packagelist.help");
/* 3415 */                   out.write("\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3416 */                   out.print(help);
/* 3417 */                   out.write("</span>')\"/>\n    </td>\n    </tr>\n    </table>\n    </td>\n    </tr>\n    <tr>\n    <td width=\"23%\" valign=\"top\"><span class=\"bodytext\">");
/* 3418 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.packagelist.text"));
/* 3419 */                   out.write("</span></td>\n    <TD height=\"28\" width=\"77%\" class=\"bodytext\">\n    ");
/* 3420 */                   if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3422 */                   out.write("\n    </TD>\n    </tr>\n    <tr>\n    <TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3423 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing"));
/* 3424 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"77%\">\n\t");
/* 3425 */                   if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3427 */                   out.write("&nbsp;&nbsp;&nbsp;\n");
/* 3428 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing.help");
/* 3429 */                   out.write("\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3430 */                   out.print(help);
/* 3431 */                   out.write("</span>')\"/>\n\t</TD>\n    </tr>\n\n    ");
/* 3432 */                   if (_jspx_meth_logic_005fnotEqual_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3434 */                   out.write("\n    ");
/* 3435 */                   if (_jspx_meth_logic_005fequal_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3437 */                   out.write("\n    <TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3438 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing.maxdepth"));
/* 3439 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"77%\">\n        ");
/* 3440 */                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3442 */                   out.write("&nbsp;&nbsp;&nbsp;\n");
/* 3443 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing.maxdepth.help");
/* 3444 */                   out.write("\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3445 */                   out.print(help);
/* 3446 */                   out.write("</span>')\"/>\n\t</TD>\n    </tr>\n    ");
/* 3447 */                   if (_jspx_meth_logic_005fnotEqual_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3449 */                   out.write("\n    ");
/* 3450 */                   if (_jspx_meth_logic_005fequal_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3452 */                   out.write("\n\t<TD height=\"28\" width=\"23%\" class=\"bodytext\">");
/* 3453 */                   out.print(FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing.maxmethod"));
/* 3454 */                   out.write("</TD>\n\t<TD height=\"28\" width=\"27%\">\n        ");
/* 3455 */                   if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3457 */                   out.write("&nbsp;&nbsp;&nbsp;\n");
/* 3458 */                   help = FormatUtil.getString("am.webclient.wta.configurationdetails.enabletracing.maxmethod.help");
/* 3459 */                   out.write("\n<img onmouseover=\"this.style.cursor='pointer'\" src=\"/images/icon_quicknote_help.gif\" onclick=\"ddrivetip(this,event,'<span class=bodytext>");
/* 3460 */                   out.print(help);
/* 3461 */                   out.write("</span>')\"/>\n\t</TD>\n    </tr>\n    </table>\n    </div>\n    </td>\n    </tr>\n    <!--tr>\n    <TD height=\"28\" width=\"23%\" class=\"bodytext\" align=\"right\">Enable full trace for </TD>\n    <TD height=\"28\" width=\"77%\" class=\"bodytext\" colspan=\"3\">\n        ");
/* 3462 */                   if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3464 */                   out.write(" transactions\n    </TD>\n    </tr-->\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"40%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"60%\" class=\"tablebottom\">\n\n    <input name=\"but1\" type=\"button\"  class=\"buttons\" value=\"");
/*      */                   
/* 3466 */                   IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3467 */                   _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3468 */                   _jspx_th_c_005fif_005f12.setParent(_jspx_th_html_005fform_005f1);
/*      */                   
/* 3470 */                   _jspx_th_c_005fif_005f12.setTest("${empty enabled}");
/* 3471 */                   int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3472 */                   if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                     for (;;) {
/* 3474 */                       out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 3475 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3476 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3480 */                   if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3481 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                   }
/*      */                   
/* 3484 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3485 */                   if (_jspx_meth_c_005fif_005f13(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                     return;
/* 3487 */                   out.write("\" onClick=\"javascript:submitForm()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons\" value=\"");
/* 3488 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3489 */                   out.write("\" onClick=\"javascript:toggleWTADiv('edit')\" />\n     </td>\n  </tr>\n</table>\n");
/* 3490 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3491 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3495 */               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3496 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */               }
/*      */               
/* 3499 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3500 */               out.write("\n</div>\n<div id=\"monitorinformation\" style=\"DISPLAY: block;\"><table width=\"97%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"lrtbdarkborder\">\n          <tr>\n            <td colspan=\"4\" class=\"tableheadingbborder\" >");
/* 3501 */               out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.heading"));
/* 3502 */               out.write(" </td>\n          </tr>\n          <!--tr>\n            <td width=\"25%\" class=\"monitorinfoodd\" >Display Name </td>\n            <td colspan=\"3\" class=\"monitorinfoodd\">");
/* 3503 */               out.write("</td>\n            <td width=\"25%\" class=\"monitorinfoodd\">Host </td>\n            <td width=\"25%\" class=\"leftlinkstd\">");
/* 3504 */               out.write("</td>\n          </tr-->\n          <tr>\n          <td  width=\"20%\" class=\"monitorinfoeven\">");
/* 3505 */               out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.host"));
/* 3506 */               out.write("</td>\n          <td  width=\"30%\" class=\"monitorinfoodd\">\n            <span class=\"bodytext\">");
/* 3507 */               out.print(host);
/* 3508 */               out.write("</span>&nbsp;\n            ");
/*      */               
/* 3510 */               String encodeurl = java.net.URLEncoder.encode("/showresource.do?type=WTA&method=showdetails&resourceid=" + resourceid, "UTF-8");
/* 3511 */               out.println("<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=8000,8001&attributeToSelect=8001&redirectto=" + encodeurl + "' class=\"links\">");
/* 3512 */               out.println("<img src=\"/images/icon_associateaction.gif\" border=\"0\" align=\"absmiddle\">");
/* 3513 */               out.println("</a>");
/*      */               
/* 3515 */               out.write("\n            </td>\n          <td  width=\"20%\" class=\"monitorinfoodd\">");
/* 3516 */               out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.port"));
/* 3517 */               out.write("</td>\n          <td  width=\"30%\" class=\"monitorinfoodd\"><span class=\"bodytext\">");
/* 3518 */               out.print(port);
/* 3519 */               out.write("</span></td>\n          </tr>\n\t\t  ");
/*      */               
/* 3521 */               String hostName = InetAddress.getLocalHost().getHostName();
/* 3522 */               String portNumber = System.getProperty("webserver.port");
/*      */               
/* 3524 */               out.write("\n\t\t  ");
/*      */               
/* 3526 */               PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3527 */               _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3528 */               _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3530 */               _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN");
/* 3531 */               int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3532 */               if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                 for (;;) {
/* 3534 */                   out.write("\n\t\t  <tr>\n\t\t  <td width=\"20%\" class=\"monitorinfoodd\">");
/* 3535 */                   out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 3536 */                   out.write(" </td>\n\t\t  <td width=\"30%\" class=\"monitorinfoodd\">");
/* 3537 */                   out.print(hostName);
/* 3538 */                   out.write(95);
/* 3539 */                   out.print(portNumber);
/* 3540 */                   out.write("</td>\n\t\t  <td  width=\"20%\" class=\"monitorinfoodd\"></td>\n\t\t  <td  width=\"30%\" class=\"monitorinfoodd\"></td>\n\t\t  </tr>\n\t\t  ");
/* 3541 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3542 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3546 */               if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3547 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */               }
/*      */               
/* 3550 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3551 */               out.write("\n          <tr>\n          <td  width=\"20%\" class=\"monitorinfoeven\">");
/* 3552 */               out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.totalurl"));
/* 3553 */               out.write("</td>\n          <td  width=\"30%\" class=\"monitorinfoodd\"><span class=\"bodytext\">");
/* 3554 */               out.print(count);
/* 3555 */               out.write("</span></td>\n          <td  width=\"20%\" class=\"monitorinfoodd\">");
/* 3556 */               out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.errorurl"));
/* 3557 */               out.write("</td>\n          <td  width=\"30%\" class=\"monitorinfoodd\"><span class=\"bodytext\">");
/* 3558 */               out.print(errorcount);
/* 3559 */               out.write("</span></td>\n          </tr>\n          <!--tr>\n          <td  width=\"25%\" class=\"monitorinfoeven\">Last Polled at</td>\n          <td  width=\"25%\" class=\"monitorinfoodd\">&nbsp;</td>\n          <td  width=\"25%\" class=\"monitorinfoodd\">Next Poll at</td>\n          <td  width=\"25%\" class=\"monitorinfoodd\">&nbsp;</td>\n          </tr-->\n          <!--\n          <tr>\n            <td class=\"monitorinfoeven\" valign=\"top\">Health</td>\n            <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3560 */               out.write("&attributeid=309')\">");
/* 3561 */               out.write("</a>\n\t\t\t ");
/* 3562 */               out.write("\n\t\t\t</td>\n          </tr>\n          -->\n          <!--tr>\n\n          </tr-->\n\n          ");
/*      */               
/* 3564 */               EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3565 */               _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3566 */               _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3568 */               _jspx_th_logic_005fempty_005f0.setName("wtasysteminfo");
/* 3569 */               int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3570 */               if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                 for (;;) {
/* 3572 */                   out.write("\n          <tr>\n            <td class=\"monitorinfoodd\">");
/* 3573 */                   out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.lastpolledat"));
/* 3574 */                   out.write("</td>\n            <td class=\"leftlinkstd\">-</td>\n            <td class=\"monitorinfoeven\">");
/* 3575 */                   out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.nextpollat"));
/* 3576 */                   out.write("</td>\n            <td class=\"leftlinkstd\">-</td>\n          </tr>\n\n          ");
/* 3577 */                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3578 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3582 */               if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3583 */                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */               }
/*      */               
/* 3586 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3587 */               out.write("\n\n        ");
/*      */               
/* 3589 */               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3590 */               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3591 */               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_tiles_005fput_005f4);
/*      */               
/* 3593 */               _jspx_th_logic_005fnotEmpty_005f0.setName("wtasysteminfo");
/* 3594 */               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3595 */               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                 for (;;) {
/* 3597 */                   out.write("\n          <tr>\n            <td class=\"monitorinfoeven\">");
/* 3598 */                   out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.lastpolledat"));
/* 3599 */                   out.write("</td>\n            <td class=\"monitorinfoeven\">");
/* 3600 */                   out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 3601 */                   out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 3602 */                   out.print(FormatUtil.getString("am.webclient.wta.monitorinformation.nextpollat"));
/* 3603 */                   out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 3604 */                   out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 3605 */                   out.write("</td>\n          </tr>\n\t\t");
/* 3606 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3607 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3611 */               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3612 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */               }
/*      */               
/* 3615 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3616 */               out.write("\n        <tr>\n\n            <td class=\"monitorinfoeven\">");
/* 3617 */               out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 3618 */               out.write("</td>\n            <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3619 */               if (_jspx_meth_c_005fout_005f24(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                 return;
/* 3621 */               out.write("&attributeid=8000')\" class=\"staticlinks\">\n                ");
/* 3622 */               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "8000")));
/* 3623 */               out.write("</a>\n            </td>\n            <td class=\"monitorinfoeven\">");
/* 3624 */               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3625 */               out.write("</td>\n            <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3626 */               if (_jspx_meth_c_005fout_005f25(_jspx_th_tiles_005fput_005f4, _jspx_page_context))
/*      */                 return;
/* 3628 */               out.write("&attributeid=8001')\">");
/* 3629 */               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "8001")));
/* 3630 */               out.write("</a></td>\n        </tr>\n        <tr><td colspan=\"4\">\n        <table width=\"100%\">");
/* 3631 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 3632 */               out.write("</table>\n        </td></tr>\n\t</table>\n    </div>\n    \n");
/* 3633 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f4.doAfterBody();
/* 3634 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3637 */             if (_jspx_eval_tiles_005fput_005f4 != 1) {
/* 3638 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3641 */           if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3642 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4); return;
/*      */           }
/*      */           
/* 3645 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f4);
/* 3646 */           out.write(10);
/* 3647 */           out.write(10);
/*      */           
/* 3649 */           PutTag _jspx_th_tiles_005fput_005f5 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3650 */           _jspx_th_tiles_005fput_005f5.setPageContext(_jspx_page_context);
/* 3651 */           _jspx_th_tiles_005fput_005f5.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3653 */           _jspx_th_tiles_005fput_005f5.setName("UserArea");
/*      */           
/* 3655 */           _jspx_th_tiles_005fput_005f5.setType("string");
/* 3656 */           int _jspx_eval_tiles_005fput_005f5 = _jspx_th_tiles_005fput_005f5.doStartTag();
/* 3657 */           if (_jspx_eval_tiles_005fput_005f5 != 0) {
/* 3658 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3659 */               out = _jspx_page_context.pushBody();
/* 3660 */               _jspx_th_tiles_005fput_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3661 */               _jspx_th_tiles_005fput_005f5.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3664 */               out.write("\n <a name=\"jbtop\"> </a>       \n");
/* 3665 */               com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB dataHandler = null;
/* 3666 */               dataHandler = (com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB)_jspx_page_context.getAttribute("dataHandler", 1);
/* 3667 */               if (dataHandler == null) {
/* 3668 */                 dataHandler = new com.adventnet.appmanager.server.wlogic.bean.GetDataFromDB();
/* 3669 */                 _jspx_page_context.setAttribute("dataHandler", dataHandler, 1);
/*      */               }
/* 3671 */               out.write("  \n\n\n\n\n");
/* 3672 */               out.write("\n\n  \n");
/*      */               
/* 3674 */               String resourceid = request.getParameter("resourceid");
/*      */               
/*      */ 
/*      */ 
/* 3678 */               String encodeurl = java.net.URLEncoder.encode("/showresource.do?haid=" + request.getParameter("haid") + "&type=JBOSS-server&method=showdetails&resourceid=" + resourceid);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3701 */               request.setAttribute("isfromresourcepage", "true");
/*      */               
/*      */ 
/* 3704 */               out.write("\n<table width=\"79%\" ><tr><td>");
/* 3705 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3706 */               out.write("</td></tr></table>\n<div id=\"WebTransactionAnalyzer\" style=\"DISPLAY: block\">\n");
/* 3707 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/wtaurls.jsp", out, false);
/* 3708 */               out.write("\n</div>\n<br><br>\n<div id=\"fulltrace\" style=\"DISPLAY: none\">\n</div>\n");
/* 3709 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f5.doAfterBody();
/* 3710 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3713 */             if (_jspx_eval_tiles_005fput_005f5 != 1) {
/* 3714 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3717 */           if (_jspx_th_tiles_005fput_005f5.doEndTag() == 5) {
/* 3718 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5); return;
/*      */           }
/*      */           
/* 3721 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f5);
/* 3722 */           out.write(10);
/* 3723 */           out.write(32);
/* 3724 */           if (_jspx_meth_tiles_005fput_005f6(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3726 */           out.write(10);
/* 3727 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3728 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3732 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3733 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3736 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3737 */         out.write("      \n      \n");
/*      */       }
/* 3739 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3740 */         out = _jspx_out;
/* 3741 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3742 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3743 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3746 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3752 */     PageContext pageContext = _jspx_page_context;
/* 3753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3755 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3756 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3757 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 3759 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3760 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3762 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3763 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3765 */           out.write(10);
/* 3766 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3767 */             return true;
/* 3768 */           out.write(10);
/* 3769 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3770 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3774 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3775 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3778 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 3779 */         out = _jspx_page_context.popBody(); }
/* 3780 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3782 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3783 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3790 */     PageContext pageContext = _jspx_page_context;
/* 3791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3793 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3794 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3795 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3797 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3799 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3800 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3801 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3802 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3803 */       return true;
/*      */     }
/* 3805 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3806 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3811 */     PageContext pageContext = _jspx_page_context;
/* 3812 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3814 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3815 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3816 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3818 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3820 */     _jspx_th_tiles_005fput_005f0.setValue("Web Transaction Analysis");
/* 3821 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3822 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3823 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3824 */       return true;
/*      */     }
/* 3826 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3827 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3832 */     PageContext pageContext = _jspx_page_context;
/* 3833 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3835 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3836 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3837 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3839 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3840 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3841 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3843 */         out.write(10);
/* 3844 */         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3845 */           return true;
/* 3846 */         out.write(10);
/* 3847 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3848 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3852 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3853 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3854 */       return true;
/*      */     }
/* 3856 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3862 */     PageContext pageContext = _jspx_page_context;
/* 3863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3865 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3866 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3867 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3869 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3871 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=0");
/* 3872 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3873 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3874 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3875 */       return true;
/*      */     }
/* 3877 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3878 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3883 */     PageContext pageContext = _jspx_page_context;
/* 3884 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3886 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3887 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3888 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3890 */     _jspx_th_c_005fif_005f1.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3891 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3892 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3894 */         out.write(10);
/* 3895 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3896 */           return true;
/* 3897 */         out.write(10);
/* 3898 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3899 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3903 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3904 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3905 */       return true;
/*      */     }
/* 3907 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3913 */     PageContext pageContext = _jspx_page_context;
/* 3914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3916 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3917 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3918 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3920 */     _jspx_th_tiles_005fput_005f2.setName("Header");
/*      */     
/* 3922 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/header.jsp?tabtoselect=1");
/* 3923 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3924 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3925 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3926 */       return true;
/*      */     }
/* 3928 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3929 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3934 */     PageContext pageContext = _jspx_page_context;
/* 3935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3937 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3938 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3939 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3941 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3942 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3943 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3945 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 3946 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3947 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3951 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3952 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3953 */       return true;
/*      */     }
/* 3955 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3961 */     PageContext pageContext = _jspx_page_context;
/* 3962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3964 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3965 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3966 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3968 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3969 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3970 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3972 */         out.write("\n\t \t\t\t alertUser();\n\t \t\t \treturn false;\n\t \t\t");
/* 3973 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3974 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3978 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3979 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3980 */       return true;
/*      */     }
/* 3982 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3988 */     PageContext pageContext = _jspx_page_context;
/* 3989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3991 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3992 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3993 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3995 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3996 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3997 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3999 */       return true;
/*      */     }
/* 4001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4007 */     PageContext pageContext = _jspx_page_context;
/* 4008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4010 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4011 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4012 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4014 */     _jspx_th_c_005fout_005f1.setValue("${param.type}");
/* 4015 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4016 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4018 */       return true;
/*      */     }
/* 4020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4026 */     PageContext pageContext = _jspx_page_context;
/* 4027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4029 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4030 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4031 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4033 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 4034 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4035 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4037 */       return true;
/*      */     }
/* 4039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4045 */     PageContext pageContext = _jspx_page_context;
/* 4046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4048 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4049 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 4050 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4052 */     _jspx_th_c_005fout_005f3.setValue("${param.resourcename}");
/* 4053 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 4054 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 4055 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4056 */       return true;
/*      */     }
/* 4058 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 4059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4064 */     PageContext pageContext = _jspx_page_context;
/* 4065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4067 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4068 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 4069 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4071 */     _jspx_th_c_005fout_005f4.setValue("${param.moname}");
/* 4072 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 4073 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 4074 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4075 */       return true;
/*      */     }
/* 4077 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 4078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4083 */     PageContext pageContext = _jspx_page_context;
/* 4084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4086 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4087 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 4088 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4090 */     _jspx_th_c_005fout_005f5.setValue("${AMActionForm.displayname}");
/* 4091 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 4092 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 4093 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4094 */       return true;
/*      */     }
/* 4096 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 4097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4102 */     PageContext pageContext = _jspx_page_context;
/* 4103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4105 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4106 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4107 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4109 */     _jspx_th_c_005fout_005f6.setValue("${AMActionForm.pollInterval}");
/* 4110 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4111 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4113 */       return true;
/*      */     }
/* 4115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4121 */     PageContext pageContext = _jspx_page_context;
/* 4122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4124 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4125 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4126 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4128 */     _jspx_th_c_005fout_005f7.setValue("${AMActionForm.instrumentationEnabled}");
/* 4129 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4130 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4132 */       return true;
/*      */     }
/* 4134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4140 */     PageContext pageContext = _jspx_page_context;
/* 4141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4143 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4144 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4145 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4147 */     _jspx_th_c_005fout_005f8.setValue("${AMActionForm.samplingFactor}");
/* 4148 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4149 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4150 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4151 */       return true;
/*      */     }
/* 4153 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4159 */     PageContext pageContext = _jspx_page_context;
/* 4160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4162 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4163 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4164 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4166 */     _jspx_th_c_005fout_005f9.setValue("${AMActionForm.maxURL}");
/* 4167 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4168 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4169 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4170 */       return true;
/*      */     }
/* 4172 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4178 */     PageContext pageContext = _jspx_page_context;
/* 4179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4181 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4182 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4183 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4185 */     _jspx_th_c_005fout_005f10.setValue("${AMActionForm.packageInclude}");
/* 4186 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4187 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4188 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4189 */       return true;
/*      */     }
/* 4191 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4197 */     PageContext pageContext = _jspx_page_context;
/* 4198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4200 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4201 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4202 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4204 */     _jspx_th_c_005fout_005f11.setValue("${AMActionForm.packageList}");
/* 4205 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4206 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4207 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4208 */       return true;
/*      */     }
/* 4210 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4216 */     PageContext pageContext = _jspx_page_context;
/* 4217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4219 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4220 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4221 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4223 */     _jspx_th_c_005fout_005f12.setValue("${AMActionForm.tracingEnabled}");
/* 4224 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4225 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4226 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4227 */       return true;
/*      */     }
/* 4229 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4235 */     PageContext pageContext = _jspx_page_context;
/* 4236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4238 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4239 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4240 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4242 */     _jspx_th_c_005fout_005f13.setValue("${AMActionForm.maxDepth}");
/* 4243 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4244 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4245 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4246 */       return true;
/*      */     }
/* 4248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4254 */     PageContext pageContext = _jspx_page_context;
/* 4255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4257 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4258 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4259 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4261 */     _jspx_th_c_005fout_005f14.setValue("${AMActionForm.maxChildren}");
/* 4262 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4263 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4265 */       return true;
/*      */     }
/* 4267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4273 */     PageContext pageContext = _jspx_page_context;
/* 4274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4276 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4277 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4278 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4280 */     _jspx_th_c_005fout_005f15.setValue("${AMActionForm.fullTraceCount}");
/* 4281 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4282 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4283 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4284 */       return true;
/*      */     }
/* 4286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4292 */     PageContext pageContext = _jspx_page_context;
/* 4293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4295 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4296 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4297 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4299 */     _jspx_th_c_005fout_005f16.setValue("${ha.key}");
/* 4300 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4301 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4303 */       return true;
/*      */     }
/* 4305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4311 */     PageContext pageContext = _jspx_page_context;
/* 4312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4314 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4315 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4316 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4318 */     _jspx_th_c_005fout_005f17.setValue("${ha.value}");
/* 4319 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4320 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4322 */       return true;
/*      */     }
/* 4324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4330 */     PageContext pageContext = _jspx_page_context;
/* 4331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4333 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4334 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4335 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4337 */     _jspx_th_c_005fset_005f0.setVar("monitorName");
/* 4338 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4339 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4340 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4341 */         out = _jspx_page_context.pushBody();
/* 4342 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 4343 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 4344 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4347 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4348 */           return true;
/* 4349 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4350 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4353 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4354 */         out = _jspx_page_context.popBody();
/* 4355 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 4358 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4359 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4360 */       return true;
/*      */     }
/* 4362 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4368 */     PageContext pageContext = _jspx_page_context;
/* 4369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4371 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4372 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4373 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 4375 */     _jspx_th_c_005fout_005f18.setValue("${ha.value}");
/* 4376 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4377 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4378 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4379 */       return true;
/*      */     }
/* 4381 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4382 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4387 */     PageContext pageContext = _jspx_page_context;
/* 4388 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4390 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4391 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4392 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 4394 */     _jspx_th_c_005fout_005f19.setValue("${ha.key}");
/* 4395 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4396 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4397 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4398 */       return true;
/*      */     }
/* 4400 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4406 */     PageContext pageContext = _jspx_page_context;
/* 4407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4409 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4410 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4411 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 4413 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4414 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4415 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4416 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4417 */       return true;
/*      */     }
/* 4419 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4420 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4425 */     PageContext pageContext = _jspx_page_context;
/* 4426 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4428 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4429 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4430 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4432 */     _jspx_th_c_005fout_005f20.setValue("${ha.key}");
/* 4433 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4434 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4435 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4436 */       return true;
/*      */     }
/* 4438 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4439 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4444 */     PageContext pageContext = _jspx_page_context;
/* 4445 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4447 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4448 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4449 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4451 */     _jspx_th_c_005fout_005f21.setValue("${ha.value}");
/* 4452 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4453 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4454 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4455 */       return true;
/*      */     }
/* 4457 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4463 */     PageContext pageContext = _jspx_page_context;
/* 4464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4466 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4467 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4468 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4470 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 4471 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4472 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4473 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4474 */         out = _jspx_page_context.pushBody();
/* 4475 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4476 */         _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 4477 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4480 */         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4481 */           return true;
/* 4482 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4483 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4486 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4487 */         out = _jspx_page_context.popBody();
/* 4488 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4491 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4492 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4493 */       return true;
/*      */     }
/* 4495 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4501 */     PageContext pageContext = _jspx_page_context;
/* 4502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4504 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4505 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4506 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 4508 */     _jspx_th_c_005fout_005f22.setValue("${ha.value}");
/* 4509 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4510 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4511 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4512 */       return true;
/*      */     }
/* 4514 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4515 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4520 */     PageContext pageContext = _jspx_page_context;
/* 4521 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4523 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4524 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 4525 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4527 */     _jspx_th_c_005fout_005f23.setValue("${ha.key}");
/* 4528 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 4529 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 4530 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4531 */       return true;
/*      */     }
/* 4533 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4539 */     PageContext pageContext = _jspx_page_context;
/* 4540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4542 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4543 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4544 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 4546 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 4547 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4548 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4549 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4550 */       return true;
/*      */     }
/* 4552 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4553 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4558 */     PageContext pageContext = _jspx_page_context;
/* 4559 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4561 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4562 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 4563 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4565 */     _jspx_th_c_005fif_005f9.setTest("${empty associatedmgs}");
/* 4566 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 4567 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 4569 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4570 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 4571 */           return true;
/* 4572 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 4573 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 4574 */           return true;
/* 4575 */         out.write("</td>\n\t ");
/* 4576 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 4577 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4581 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 4582 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4583 */       return true;
/*      */     }
/* 4585 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 4586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4591 */     PageContext pageContext = _jspx_page_context;
/* 4592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4594 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4595 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4596 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4598 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4599 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4600 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4601 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4602 */       return true;
/*      */     }
/* 4604 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4610 */     PageContext pageContext = _jspx_page_context;
/* 4611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4613 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4614 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4615 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 4617 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 4618 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4619 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4620 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4621 */       return true;
/*      */     }
/* 4623 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4629 */     PageContext pageContext = _jspx_page_context;
/* 4630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4632 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 4633 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4634 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4636 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 4637 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4638 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4639 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4640 */       return true;
/*      */     }
/* 4642 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4648 */     PageContext pageContext = _jspx_page_context;
/* 4649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4651 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4652 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 4653 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4655 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 4657 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 4659 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 4660 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 4661 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 4662 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4663 */       return true;
/*      */     }
/* 4665 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 4666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4671 */     PageContext pageContext = _jspx_page_context;
/* 4672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4674 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4675 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4676 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4678 */     _jspx_th_html_005ftext_005f1.setProperty("pollInterval");
/*      */     
/* 4680 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 4682 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 4683 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4684 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4685 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4686 */       return true;
/*      */     }
/* 4688 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4694 */     PageContext pageContext = _jspx_page_context;
/* 4695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4697 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4698 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4699 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4701 */     _jspx_th_html_005fcheckbox_005f0.setProperty("instrumentationEnabled");
/*      */     
/* 4703 */     _jspx_th_html_005fcheckbox_005f0.setValue("true");
/*      */     
/* 4705 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("showWTASettings(this)");
/* 4706 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4707 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4708 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4709 */       return true;
/*      */     }
/* 4711 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4717 */     PageContext pageContext = _jspx_page_context;
/* 4718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4720 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4721 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4722 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4724 */     _jspx_th_c_005fif_005f10.setTest("${AMActionForm.instrumentationEnabled}");
/* 4725 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4726 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4728 */         out.write("\n    <div id=\"wtasettings\" style=\"display:block;\">\n    ");
/* 4729 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4730 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4734 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4735 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4736 */       return true;
/*      */     }
/* 4738 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4744 */     PageContext pageContext = _jspx_page_context;
/* 4745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4747 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4748 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4749 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4751 */     _jspx_th_c_005fif_005f11.setTest("${!AMActionForm.instrumentationEnabled}");
/* 4752 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4753 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 4755 */         out.write("\n    <div id=\"wtasettings\" style=\"display:none;\">\n    ");
/* 4756 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4757 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4761 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4762 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4763 */       return true;
/*      */     }
/* 4765 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4771 */     PageContext pageContext = _jspx_page_context;
/* 4772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4774 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4775 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4776 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4778 */     _jspx_th_html_005ftext_005f2.setProperty("samplingFactor");
/*      */     
/* 4780 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 4782 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 4783 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4784 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4785 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4786 */       return true;
/*      */     }
/* 4788 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4794 */     PageContext pageContext = _jspx_page_context;
/* 4795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4797 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4798 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4799 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4801 */     _jspx_th_html_005ftext_005f3.setProperty("maxURL");
/*      */     
/* 4803 */     _jspx_th_html_005ftext_005f3.setSize("15");
/*      */     
/* 4805 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/* 4806 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4807 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4808 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4809 */       return true;
/*      */     }
/* 4811 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4817 */     PageContext pageContext = _jspx_page_context;
/* 4818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4820 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4821 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 4822 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4824 */     _jspx_th_html_005fradio_005f0.setProperty("packageInclude");
/*      */     
/* 4826 */     _jspx_th_html_005fradio_005f0.setValue("true");
/* 4827 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 4828 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 4829 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4830 */       return true;
/*      */     }
/* 4832 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 4833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4838 */     PageContext pageContext = _jspx_page_context;
/* 4839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4841 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4842 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 4843 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4845 */     _jspx_th_html_005fradio_005f1.setProperty("packageInclude");
/*      */     
/* 4847 */     _jspx_th_html_005fradio_005f1.setValue("false");
/* 4848 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 4849 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 4850 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4851 */       return true;
/*      */     }
/* 4853 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 4854 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4859 */     PageContext pageContext = _jspx_page_context;
/* 4860 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4862 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4863 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4864 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4866 */     _jspx_th_html_005ftextarea_005f0.setProperty("packageList");
/*      */     
/* 4868 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 4870 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*      */     
/* 4872 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/* 4873 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4874 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4875 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4876 */       return true;
/*      */     }
/* 4878 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4884 */     PageContext pageContext = _jspx_page_context;
/* 4885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4887 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 4888 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 4889 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4891 */     _jspx_th_html_005fcheckbox_005f1.setProperty("tracingEnabled");
/*      */     
/* 4893 */     _jspx_th_html_005fcheckbox_005f1.setValue("true");
/*      */     
/* 4895 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("javascript:toggleWTAConfig(this)");
/* 4896 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 4897 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 4898 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4899 */       return true;
/*      */     }
/* 4901 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 4902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEqual_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4907 */     PageContext pageContext = _jspx_page_context;
/* 4908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4910 */     NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 4911 */     _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 4912 */     _jspx_th_logic_005fnotEqual_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4914 */     _jspx_th_logic_005fnotEqual_005f0.setProperty("tracingEnabled");
/*      */     
/* 4916 */     _jspx_th_logic_005fnotEqual_005f0.setName("AMActionForm");
/*      */     
/* 4918 */     _jspx_th_logic_005fnotEqual_005f0.setValue("true");
/* 4919 */     int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 4920 */     if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */       for (;;) {
/* 4922 */         out.write("\n    <tr id=\"maxdepth\" style=\"display:none\">\n    ");
/* 4923 */         int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 4924 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4928 */     if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 4929 */       this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 4930 */       return true;
/*      */     }
/* 4932 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 4933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4938 */     PageContext pageContext = _jspx_page_context;
/* 4939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4941 */     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 4942 */     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 4943 */     _jspx_th_logic_005fequal_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4945 */     _jspx_th_logic_005fequal_005f0.setProperty("tracingEnabled");
/*      */     
/* 4947 */     _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */     
/* 4949 */     _jspx_th_logic_005fequal_005f0.setValue("true");
/* 4950 */     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 4951 */     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */       for (;;) {
/* 4953 */         out.write("\n    <tr id=\"maxdepth\">\n    ");
/* 4954 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 4955 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4959 */     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 4960 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 4961 */       return true;
/*      */     }
/* 4963 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 4964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4969 */     PageContext pageContext = _jspx_page_context;
/* 4970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4972 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 4973 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4974 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4976 */     _jspx_th_html_005ftext_005f4.setProperty("maxDepth");
/*      */     
/* 4978 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 4980 */     _jspx_th_html_005ftext_005f4.setSize("15");
/* 4981 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4982 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4983 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4984 */       return true;
/*      */     }
/* 4986 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEqual_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4992 */     PageContext pageContext = _jspx_page_context;
/* 4993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4995 */     NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 4996 */     _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/* 4997 */     _jspx_th_logic_005fnotEqual_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 4999 */     _jspx_th_logic_005fnotEqual_005f1.setProperty("tracingEnabled");
/*      */     
/* 5001 */     _jspx_th_logic_005fnotEqual_005f1.setName("AMActionForm");
/*      */     
/* 5003 */     _jspx_th_logic_005fnotEqual_005f1.setValue("true");
/* 5004 */     int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/* 5005 */     if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */       for (;;) {
/* 5007 */         out.write("\n    <tr id=\"maxchildren\" style=\"display:none\">\n    ");
/* 5008 */         int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/* 5009 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5013 */     if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/* 5014 */       this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 5015 */       return true;
/*      */     }
/* 5017 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 5018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5023 */     PageContext pageContext = _jspx_page_context;
/* 5024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5026 */     EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 5027 */     _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 5028 */     _jspx_th_logic_005fequal_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5030 */     _jspx_th_logic_005fequal_005f1.setProperty("tracingEnabled");
/*      */     
/* 5032 */     _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */     
/* 5034 */     _jspx_th_logic_005fequal_005f1.setValue("true");
/* 5035 */     int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 5036 */     if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */       for (;;) {
/* 5038 */         out.write("\n    <tr id=\"maxchildren\">\n    ");
/* 5039 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 5040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5044 */     if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 5045 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 5046 */       return true;
/*      */     }
/* 5048 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 5049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5054 */     PageContext pageContext = _jspx_page_context;
/* 5055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5057 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5058 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 5059 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5061 */     _jspx_th_html_005ftext_005f5.setProperty("maxChildren");
/*      */     
/* 5063 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*      */     
/* 5065 */     _jspx_th_html_005ftext_005f5.setSize("15");
/* 5066 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 5067 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 5068 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5069 */       return true;
/*      */     }
/* 5071 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 5072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5077 */     PageContext pageContext = _jspx_page_context;
/* 5078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5080 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 5081 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 5082 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5084 */     _jspx_th_html_005ftext_005f6.setProperty("fullTraceCount");
/*      */     
/* 5086 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/* 5088 */     _jspx_th_html_005ftext_005f6.setSize("15");
/* 5089 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 5090 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 5091 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 5092 */       return true;
/*      */     }
/* 5094 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 5095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5100 */     PageContext pageContext = _jspx_page_context;
/* 5101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5103 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5104 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 5105 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 5107 */     _jspx_th_c_005fif_005f13.setTest("${!empty enabled}");
/* 5108 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 5109 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 5111 */         out.write("Edit Monitor");
/* 5112 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 5113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5117 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 5118 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5119 */       return true;
/*      */     }
/* 5121 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 5122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5127 */     PageContext pageContext = _jspx_page_context;
/* 5128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5130 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5131 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5132 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5134 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 5135 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5136 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5138 */       return true;
/*      */     }
/* 5140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_tiles_005fput_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5146 */     PageContext pageContext = _jspx_page_context;
/* 5147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5149 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5150 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5151 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_tiles_005fput_005f4);
/*      */     
/* 5153 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 5154 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5155 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5156 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5157 */       return true;
/*      */     }
/* 5159 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f6(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5165 */     PageContext pageContext = _jspx_page_context;
/* 5166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5168 */     PutTag _jspx_th_tiles_005fput_005f6 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5169 */     _jspx_th_tiles_005fput_005f6.setPageContext(_jspx_page_context);
/* 5170 */     _jspx_th_tiles_005fput_005f6.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5172 */     _jspx_th_tiles_005fput_005f6.setName("footer");
/*      */     
/* 5174 */     _jspx_th_tiles_005fput_005f6.setValue("/jsp/footer.jsp");
/* 5175 */     int _jspx_eval_tiles_005fput_005f6 = _jspx_th_tiles_005fput_005f6.doStartTag();
/* 5176 */     if (_jspx_th_tiles_005fput_005f6.doEndTag() == 5) {
/* 5177 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 5178 */       return true;
/*      */     }
/* 5180 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f6);
/* 5181 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WTADetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */